package com.qlqn.common.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;


/**
 * 数据源切换管理
 * @classname DynamicDataSourceTransactionManager
 * @version
 */
public class DynamicDataSourceTransactionManager extends
		DataSourceTransactionManager {

	private static final long serialVersionUID = 216058589879602201L;

	public DynamicDataSourceTransactionManager(){
		setGlobalRollbackOnParticipationFailure(false);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		boolean readOnly = definition.isReadOnly();
		if(readOnly){
			DataSourceHolder.setSlave();
		}else{
			DataSourceHolder.setMaster();
		}		
		super.doBegin(transaction, definition);
	}
	
	@Override
	protected void doCleanupAfterCompletion(Object transaction){
		super.doCleanupAfterCompletion(transaction);
		DataSourceHolder.clearDataSource();
	}
}
