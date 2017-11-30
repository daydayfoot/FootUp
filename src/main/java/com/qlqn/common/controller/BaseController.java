package com.qlqn.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.qlqn.bean.SysUserBean;
import com.qlqn.utils.Utils;

public class BaseController  {


    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String sortBy;   //排序字段名
    protected String sortOrder;   //排序升序？倒序 asc,desc

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    public int getPageSize() {
        int pageSize;
        if("".equals(request.getParameter("pageSize"))||request.getParameter("pageSize")==null){
            pageSize = 10;//默认每页显示10
        }else{
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }
        return pageSize;
    }

    public int getPageNum() {
        int pageNum;
        if("".equals(request.getParameter("pageNum"))||request.getParameter("pageNum")==null){
            pageNum = 1;
        }else{
            pageNum=Integer.parseInt(request.getParameter("pageNum"));
        }
        return pageNum;

    }

    public String getSortBy(){
        this.sortBy = request.getParameter("sort");
        return this.sortBy;
    }

    public String getSortOrder(){
        this.sortOrder = request.getParameter("order");
        return this.sortOrder;
    }
    
    /**
     * 获取使用者登录账号
     * @return
     */
    public String getUserAccount(){
    	SysUserBean user = ControllerUtil.getSessionUser();
    	if(!Utils.isNullString(user)){
    		return user.getAccount();
    	}
    	return "";
	}

}
