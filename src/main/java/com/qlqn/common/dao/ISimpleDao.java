package com.qlqn.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

/**
 * SimpleDao<T>
 * DAO层的公共父类，所有的DAO类都要继承此类
 *
 * @author lilin
 * @date 2017-03-03
 */
public interface ISimpleDao<T> {
	SqlSession getSqlSessionTemplate();

	String getClassName();

	String getMapperMethod(String methodName);

	int insert(T entity);

	int insert(List<T> entityList);

	int insert(String mapperMethod, T entity);

	int insert(String mapperMethod,List<T> entityList);

	int insert(String mapperMethod,Map<String,Object> map);

	<I> int insertObject(String mapperMethod,I obj);

	int update(T entity);



	<I> int updateByObject(String mapperMethod,I obj);


	int update(String mapperMethod, T entity);

	int update(String mapperMethod,Map<String,Object> map);

	int update(Map<String,Object> map);


	<I> int delete(I id);

	<I> int delete(String mapperMethod, I params);

	<I> I get(Object id);

	<I> I get(String mapperMethod, Object id);

	<I> List<I> find(Object params);

	<I> List<I> find(String mapperMethod, Object params);

	<I> List<I> find();
}
