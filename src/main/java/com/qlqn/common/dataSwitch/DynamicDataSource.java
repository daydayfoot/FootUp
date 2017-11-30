package com.qlqn.common.dataSwitch;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 设置数据源
 * @classname DynamicDataSource
 * @version
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	@Override
    protected Object determineCurrentLookupKey() {
		// 在进行DAO操作前，通过上下文环境变量，获得数据源的类型  
        return DataSourceSwitch.getDataSourceType();
    }
}
