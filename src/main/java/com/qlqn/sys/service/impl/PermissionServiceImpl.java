package com.qlqn.sys.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.qlqn.bean.SysPermissionBean;
import com.qlqn.bean.SysPermissionBeanBo;
import com.qlqn.bean.SysRolePermissionBean;
import com.qlqn.dao.SysPermissionDao;
import com.qlqn.dao.SysRolePermissionDao;
import com.qlqn.dao.SysUserRoleDao;
import com.qlqn.sys.service.PermissionService;
import com.qlqn.utils.Utils;
@Service
public class PermissionServiceImpl implements PermissionService{
private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public Set<String> findPermissionByUserId(Long userId) {
		Set<String> set =new HashSet<String>();
		List<String> ls=sysPermissionDao.find("findPermissionByUserId",userId );
		set.addAll(ls);
		return set;
	}
	@Override
	public void save(SysPermissionBean uPermission) {
		sysPermissionDao.insert(uPermission);
		
	}
	@Override
	public SysPermissionBean selectByPrimaryKey(Long id) {
		SysPermissionBean uPermission=sysPermissionDao.get("selectByPrimaryKey",id);
		return uPermission;
	}
	@Override
	public void updateTO(SysPermissionBean uPermission) {
		sysPermissionDao.update("updateByPrimaryKeySelective", uPermission);
		// TODO Auto-generated method stub
		
	}
	@Override
	public void remove(Long id) {
		sysPermissionDao.delete("deleteByPrimaryKey", id);
		
	}
	@Override
	public String findPermission(Map<String, Object> params) {
		//初始化分页对象
		PageInfo<SysPermissionBean> pageInfo =null;
		try {
			//查询用户列表
			List<SysPermissionBean> list = sysPermissionDao.find("findPermission",params);
			if(!Utils.isNullString(list)){
				pageInfo=new PageInfo<SysPermissionBean>(list);
			}
		} catch (Exception e) {
			logger.error("查询权限列表异常，{}",e.getMessage()); 
			e.printStackTrace();
		}
		return JSON.toJSONString(pageInfo);
	}
	
	@Override
	public List<Map<Object, Object>> findAllUserMenuPermission() {
		// TODO Auto-generated method stub
		return sysPermissionDao.find("findAllUserMenuPermission","");
	}
	@Transactional
	@Override
	public int savePermission(SysPermissionBean sysPermissionBean,Long adminId) {
			int result =0;
				//获得父级节点
			List<SysPermissionBean> permission1  = sysPermissionDao.find("getPermissionbyMyselfID", sysPermissionBean.getParentId());
			//设置层级 添加的此条权限层级比父级层级加1，如果层级小于3，则说明要添加权限类型为菜单，否则为功能按钮
			long level = permission1.get(0).getLevel()+1;
			sysPermissionBean.setLevel(level);
			boolean falg=false;
			if(level == 0||level == 1||level == 2){
				sysPermissionBean.setType(1L);
				falg=true;
			}else{
				result =1;
				//暂时不支持功能权限控制
				sysPermissionBean.setType(2L);
			}
			if(falg){
				//查询以sysPermissionBean.getParentId()为父级的权限条数，即同级权限数量
				List<SysPermissionBean> permission2  = sysPermissionDao.find("getPermissionbyParentId",sysPermissionBean.getParentId());
				//设置序列
				if(null != permission2 && permission2.size()>0){
					sysPermissionBean.setSequenceNum(new Long((long)(permission2.size()+1)));
					String myselfId = permission2.get(permission2.size()-1).getMyselfId();
					String myselfId1 = myselfId.substring(myselfId.indexOf("-")+1, myselfId.length());
					int myselfId2 = Integer.parseInt(myselfId1)+1;
					sysPermissionBean.setMyselfId("antifraud-"+String.valueOf(myselfId2));
				}else{
					sysPermissionBean.setSequenceNum(1L);
					String myselfId = sysPermissionBean.getParentId();
					if(myselfId.equals("antifraud-0")){
						myselfId = myselfId.substring(0,myselfId.indexOf("-"));
						sysPermissionBean.setMyselfId(String.valueOf(myselfId)+"10");
					}else{
						sysPermissionBean.setMyselfId(String.valueOf(myselfId)+"01");
					}
				}
				//新增资源
				result = sysPermissionDao.insert("insertSelective", sysPermissionBean);
				if(result>0){
					List<Long> lis=sysUserRoleDao.find("findRoleByUserId", adminId);
					SysRolePermissionBean entity = new SysRolePermissionBean(lis.get(0),sysPermissionBean.getId());
					result=result+sysRolePermissionDao.insert("insertSelective", entity);
				}
			}
			return result;
	}
	
	@Override
	public Map<String, Object> getPermissionById(long id) {
		// TODO Auto-generated method stub
		return sysPermissionDao.get("getPermissionById",id);
	}
	
	@Override
	public int saveEditPermission(SysPermissionBean sysPermissionBean) {
		int result = 0;
		try {
			//获得父级节点
			List<SysPermissionBean> permission1  = sysPermissionDao.find("getPermissionbyMyselfID", sysPermissionBean.getParentId());
			//设置层级 添加的此条权限层级比父级层级加1，如果层级小于3，则说明要添加权限类型为菜单，否则为功能按钮
			long level = permission1.get(0).getLevel()+1;
			sysPermissionBean.setLevel(level);
			boolean falg=false;
			if(level == 0||level == 1||level == 2){
				sysPermissionBean.setType(1L);
				falg=true;
			}else{
				result =1;
				sysPermissionBean.setType(2L);
			}
			if(falg){
				//查询以sysPermissionBean.getParentId()为父级的权限条数，即同级权限数量
				List<SysPermissionBean> permission2  = sysPermissionDao.find("getPermissionbyParentId", sysPermissionBean.getParentId());
				//设置序列
				boolean flag = false;
				int intFlag = 0;
				if(null != permission2 && permission2.size()>0){
					for (int i = 0; i < permission2.size(); i++) {
						String id = String.valueOf(permission2.get(i).getId());
						String ids = String.valueOf(sysPermissionBean.getId());
						if(id.equals(ids)){
							flag = true;
							intFlag = i;
						}
					}
					if(flag == false){
						sysPermissionBean.setSequenceNum(new Long((long)(permission2.size()+1)));
						String myselfId = permission2.get(permission2.size()-1).getMyselfId();
						String myselfId1 = myselfId.substring(myselfId.indexOf("-")+1, myselfId.length());
						int myselfId2 = Integer.parseInt(myselfId1)+1;
						sysPermissionBean.setMyselfId("antifraud-"+String.valueOf(myselfId2));
					}else{
						sysPermissionBean.setSequenceNum(permission2.get(intFlag).getSequenceNum());
						sysPermissionBean.setMyselfId(permission2.get(intFlag).getMyselfId());
					}
				}else{
					sysPermissionBean.setSequenceNum(new Long((long)1));
					String myselfId = sysPermissionBean.getParentId();
					if(myselfId.equals("antifraud-0")){
						myselfId = myselfId.substring(0,myselfId.indexOf("-"));
						sysPermissionBean.setMyselfId(String.valueOf(myselfId)+"10");
					}else{
						sysPermissionBean.setMyselfId(String.valueOf(myselfId)+"01");
					}
				}
				//新增资源
				result=2;
				result = result+sysPermissionDao.insert("saveEditPermission", sysPermissionBean);
			}
		} catch (Exception e) {
			logger.error("权限编辑异常"+e.getCause().getMessage()); 
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<SysPermissionBeanBo> selectPermissionByRoleId(Long loginUserRoleId, Long id) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("loginUserRoleId", loginUserRoleId+"");
		map.put("id", id+"");
		
		return sysPermissionDao.find("selectPermissionByRoleId", map);
	}
	@Override
	public List<SysPermissionBeanBo> selectPermissionAdminByRoleId(Long id) {
		return sysPermissionDao.find("selectPermissionAdminByRoleId",id);
	}
	
	
	

}
