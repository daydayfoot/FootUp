package com.qlqn.sys.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qlqn.bean.SysUserBean;
import com.qlqn.common.enums.EnumSysManage;
import com.qlqn.sys.service.UserService;
import com.qlqn.utils.VerifyCodeUtil;

@Controller
@RequestMapping(value = "/validateCode")
public class MobileCodeController {
	private static final Logger logger = LoggerFactory.getLogger(MobileCodeController.class);
	@Autowired
	private UserService userService;
	 @RequestMapping("/getVerifyCodeImage")  
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        //设置页面不缓存  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 6, null);  
        //将验证码放到HttpSession里面  
        Session  session = SecurityUtils.getSubject().getSession();;
        session.setAttribute("hnair_antifraud_web_image_verify_Code", verifyCode);  
        logger.debug("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");  
        //设置输出的内容的类型为JPEG图像  
        response.setContentType("image/jpeg");  
        BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 140, 30, 3, true, Color.WHITE, Color.BLACK, null);  
        //写给浏览器  
        ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());  
    } 
	/**
	 * 用户名/手机号是否存在
	 * @param request
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="/selectByNameOrMobile", method=RequestMethod.POST)  
    public Map<String, String> selectByNameOrMobile(HttpServletRequest request){
    	String unameOrMobile=request.getParameter("unameOrMobile") == null ? "" : request.getParameter("unameOrMobile").trim();
    	Map<String, String> map=new HashMap<String, String>();
		if ("".equals(unameOrMobile)) {
			map.put("code", EnumSysManage.FAILE.getCode());
		}else{
			SysUserBean sysUserBean=null;
			try {
				sysUserBean = userService.selectByNameOrMobile(unameOrMobile);
				if (null != sysUserBean && null !=sysUserBean.getStatus()) {
					//1:冻结 2：注销   3:锁定
					long status=sysUserBean.getStatus();
					if(status==1L){
						map.put("code", EnumSysManage.FAILE.getCode());
						map.put("msg", "账号已停用");
					}else if(status==2L){
						map.put("code", EnumSysManage.FAILE.getCode());
						map.put("msg", "账号已注销");
					}else if(status==3L){
						map.put("code", EnumSysManage.FAILE.getCode());
						map.put("msg", "账号已锁定");
					}else{
						map.put("code", EnumSysManage.SUCCESS.getCode());
					}
				}else{
					map.put("code", EnumSysManage.FAILE.getCode());
					map.put("msg", "账号不存在");
				}
			} catch (Exception e) {
				map.put("code", EnumSysManage.FAILE.getCode());
				map.put("msg", EnumSysManage.SYSFAILE.getName());
				logger.error("检测账号唯一性异常，{}",e.getMessage()); 
			}
		}
  		return map;
    }
}
