package com.qlqn.common.datasource;

 
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 定义动态数据源，实现通过集成Spring提供的AbstractRoutingDataSource，只需要实现determineTargetDataSource方法即可 
 * 由于DynamicDataSource是单例的，线程不安全的，所以采用ThreadLocal保证线程安全，由DataSourceHolder完成。 
 * @classname DynamicDataSource
 * @version
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	
	private AtomicInteger counter = new AtomicInteger();
	
	/**
	 * master从库 dataSource
	 */
	private DataSource master;
	
	/**
	 * slaves
	 */
	private List<DataSource> slaves;

	@Override
	protected Object determineCurrentLookupKey() {
		//do nothing
		return null;
	}
	
	@Override
	public void afterPropertiesSet(){
		//do nothing
	}

	 
	@Override
	protected DataSource determineTargetDataSource() {
		DataSource returnDataSource = null;
		if(DataSourceHolder.isMaster()){
			returnDataSource = master;
		}else if(DataSourceHolder.isSlave()){
			int count = counter.incrementAndGet();
			if(count>1000000){
				counter.set(0);
			}
			int n = slaves.size();
			int index = count%n;
			returnDataSource = slaves.get(index);
		}else{
			returnDataSource = master;
		}
		if(returnDataSource instanceof ComboPooledDataSource){
			ComboPooledDataSource source = (ComboPooledDataSource)returnDataSource;
			String jdbcUrl = source.getJdbcUrl();
			logger.debug("jdbcUrl:" + jdbcUrl);
		}		
		return returnDataSource;
	}

	public DataSource getMaster() {
		return master;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public List<DataSource> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves) {
		this.slaves = slaves;
	}
	
}
