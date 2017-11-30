package com.qlqn.common.datasource;

import javax.sql.DataSource;


/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key 
 * @classname DataSourceHolder
 * @version
 */
public class DataSourceHolder {

	/**
	 * 写库对应的数据源key 
	 */
	private static final String MASTER = "master";

	/**
	 * 读库对应的数据源key  
	 */
	private static final String SLAVE = "slave";

	/**
	 * dataSource master or slave
	 */
	private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

	/**
	 * master local
	 */
	private static final ThreadLocal<DataSource> masterLocal = new ThreadLocal<DataSource>();

	/**
	 * master local
	 */
	private static final ThreadLocal<DataSource> slaveLocal = new ThreadLocal<DataSource>();

	private static void setDataSource(String dataSourceKey) {
		dataSources.set(dataSourceKey);
	}

	private static String getDataSource() {
		return (String) dataSources.get();
	}

	public static void setMaster() {
		setDataSource(MASTER);
	}

	public static void setSlave() {
		setDataSource(SLAVE);
	}
	
	public static void setMaster(DataSource master) {
		masterLocal.set(master);
	}
	
	public static void setSlave(DataSource slave) {
		slaveLocal.set(slave);
	}

	
	public static boolean isMaster() {
		return getDataSource() == MASTER;
	}

	public static boolean isSlave() {
		return getDataSource() == SLAVE;
	}

	public static void clearDataSource() {
		dataSources.remove();
		masterLocal.remove();
		slaveLocal.remove();
	}
}
