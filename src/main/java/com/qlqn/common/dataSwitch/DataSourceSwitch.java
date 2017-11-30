package com.qlqn.common.dataSwitch;
/**
 * 数据源切换
 * @classname DataSourceSwitch
 * @version
 */
public class DataSourceSwitch {

	 private static final ThreadLocal<String> CONTEXTHOLDER  = new ThreadLocal<String>();

	    /**
	     *  设置数据源类型.
	     * @param jdbcType
	     */
	    public static void setDataSourceType(final String jdbcType) {
	        CONTEXTHOLDER.set(jdbcType);

	    }

	    /**
	     *切换数据源.
	     */
	    public static void setDatasource(String type) {
	        clearDataSourceType();
	        setDataSourceType(type);
	    }

	    /**
	     *获取当前数据源.
	     * @return
	     */
	    public static String getDataSourceType() {

	        return (String) CONTEXTHOLDER.get();

	    }

	    /**
	     *清除所有数据源
	     */
	    public static void clearDataSourceType() {

	        CONTEXTHOLDER.remove();

	    }
}
