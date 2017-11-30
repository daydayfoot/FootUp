package com.qlqn.common.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.qlqn.common.dao.ISimpleDao;




/**
 * SimpleDao<T>
 * DAO层的公共父类，所有的DAO类都要继承此类
 * @author lilin
 * @date 2017-03-03
 */
public abstract class SimpleDaoImpl<T> implements ISimpleDao<T> {
	
	protected static final String GET = "get";
    protected static final String FIND = "find";
    protected static final String PAGE = "page";
    protected static final String BATCH_INSERT = "batchInsert";
    protected static final String INSERT = "insert";
    protected static final String UPDATE = "update";
    protected static final String DELETE = "delete";
    protected static final String BATCH_DELETE = "batch_delete";

    @Resource(name = "dynamicSqlSessionTemplate")
    private SqlSession sqlSessionTemplate;
    protected Class<T> entityClass;
    protected String className;

    public SqlSession getSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }

    public String getClassName() {
        return this.className;
    }

    /***
     * 拼接 mybatisMappId
     * @param methodName
     * @return
     */
    public String getMapperMethod(String methodName) {
        return new StringBuilder(className).append(".").append(methodName).toString();
    }

    /***
     * 构造函数 初始化数据
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public SimpleDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.entityClass = ((Class) params[0]);
        this.className = entityClass.getName();
    }

    /***
     * 新增实体
     * @param entity
     * @return
     */
    public int insert(T entity) {
        return insert(INSERT, entity);
    }
    /***
     * 批量插入集合对象
     * @param entityList
     * @return
     */
    public int insert(List<T>  entityList) {
        return insert(BATCH_INSERT, entityList);
    }

    /***
     * 插入单个对象
     * @param mapperMethod mappId
     * @param entity
     * @return
     */
    public int insert(String mapperMethod, T entity) {
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entity);
    }
    /***
     *  插入对象集合
     * @param mapperMethod
     * @param entityList
     * @return
     */
    public int insert(String mapperMethod, List<T> entityList) {
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), entityList);
    }

    public int insert(String mapperMethod, Map<String,Object> objectMap){
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod), objectMap);
    }
    /*插入任意对象*/
    public <I> int insertObject(String mapperMethod,I params) {
        return getSqlSessionTemplate().insert(getMapperMethod(mapperMethod),params);
    }



    /***
     * 修改单个对象
     * @param entity
     * @return
     */
    public int update(T entity) {
        return update(UPDATE, entity);
    }

    /***
     * 修改单个对象，参数自定义
     * @param mapperMethod
     * params
     * @return
     */
    public <I> int updateByObject(String mapperMethod, I params) {
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), params);
    }

    /***
     * 修改单个对象，参数是 map集合，参数可以封装到map中
     * @param objectMap
     * @return
     */
    public int update(Map<String,Object> objectMap){
        return update(UPDATE,objectMap);
    }

    /***
     * 修改单个对象，自定义mappid
     * @param mapperMethod
     * @param objectMap
     * @return
     */
    public int update(String mapperMethod, Map<String,Object> objectMap){
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), objectMap);
    }

    /****
     *  修改单个实体对象，自定义mappid
     * @param mapperMethod
     * @param entity
     * @return
     */
    public int update(String mapperMethod, T entity) {
        return getSqlSessionTemplate().update(getMapperMethod(mapperMethod), entity);
    }

    /***
     * 泛型文档
     * http://blog.csdn.net/jinuxwu/article/details/6771121
     * @param obj
     * @param <I>
     * @return
     */
    public <I> int delete(I obj) {
        return delete(DELETE, obj);
    }

    /****
     *
     * @param mapperMethod mappid
     * @param params 参数， 任何类型都可以
     * @param <I>
     * @return
     */
    public <I> int delete(String mapperMethod, I params) {
        return getSqlSessionTemplate().delete(getMapperMethod(mapperMethod), params);
    }

    public <I> I get(Object obj) {
        return get(GET, obj);
    }

    /* (non-Javadoc)
   * @see com.kld.basedao.ISimpleDao#get(java.lang.String, I)
   * 如果想传递map或者list，直接传递就可以，
   * 类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法。
   */
    public <I> I get(String mapperMethod, Object obj) {
        return getSqlSessionTemplate().selectOne(getMapperMethod(mapperMethod), obj);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(I)
 */

    public <I> List<I> find(Object params) {
        return find(FIND, params);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find(java.lang.String, I)
 */

    public <I> List<I> find(String mapperMethod, Object params) {
        return getSqlSessionTemplate().selectList(getMapperMethod(mapperMethod), params);
    }

  /* (non-Javadoc)
 * @see com.kld.basedao.ISimpleDao#find()
 */

    public <I> List<I> find() {
        return getSqlSessionTemplate().selectList(getMapperMethod(FIND));
    }
}