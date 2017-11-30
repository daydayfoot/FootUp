package com.qlqn.utils;

import java.io.IOException;

import redis.clients.jedis.JedisCluster;
/**
 * redis 工具类
 * @author LHM
 *
 */
public class RedisCacheUtil {
	
	private static JedisCluster jedisCluster;

	public  JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public  void setJedisCluster(JedisCluster jedisCluster) {
		RedisCacheUtil.jedisCluster = jedisCluster;
	};

	/**
	 * 简单的Get
	 * @param <T>
	 * @param key
	 * @param requiredType
	 * @return
	 * @throws IOException 
	 */
	public static <T> T get(String key , Class<T>...requiredType) throws IOException{
		try {
			byte[] skey = SerializeUtil.serialize(key);
			return SerializeUtil.deserialize(jedisCluster.get(skey),requiredType);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	/**
	 * 简单的set
	 * @param key
	 * @param value
	 * @throws IOException 
	 */
	public static void set(Object key ,Object value) throws IOException{
		try {
			byte[] skey = SerializeUtil.serialize(key);
			byte[] svalue = SerializeUtil.serialize(value);
			jedisCluster.set(skey, svalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 过期时间的
	 * @param key
	 * @param value
	 * @param timer （秒）
	 * @throws IOException 
	 */
	public static void setex(Object key, Object value, int timer) throws IOException {
		try {
			byte[] skey = SerializeUtil.serialize(key);
			byte[] svalue = SerializeUtil.serialize(value);
			jedisCluster.setex(skey, timer, svalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	/**
	   * 指定String  key  删除
	   * @param key
	   */
	  public static void delete(String key) {
		  jedisCluster.del(key);
	  }
	  /**
	   *  取出 缓存 数据
	   * @param key
	   * @return
	   */
	  public static String  get(String key) {
		  String value = jedisCluster.get(key);
	      return value;
	  }
	  
		/**
		 * 获取数据
		 * 
		 * @param key
		 * @return
		 */
		public static byte[] get(byte[] key) {

			byte[] value = null;

			// JedisCluster jedis =null;
			try {
				// jedis = new JedisCluster(jedisClusterNode, config);
				value = jedisCluster.get(key);
			} catch (Exception e) {
				// 释放redis对象
				// close(jedis);
				e.printStackTrace();
			} finally {
				// 返还到连接池
				// close(jedis);
			}

			return value;
		}
	   /**
	    * 存入缓存数据
	    * @param key
	    * @param obj
	    */
	  public static  void set(String key,String value) {
		  jedisCluster.set(key, value);
	  }
	  /**
	   * 存入缓存数
	   * @param key
	   * @param value
	   */
	  public static void set(byte[] key, byte[] value) {

			// JedisCluster jedis =null;
			try {
				// jedis = new JedisCluster(jedisClusterNode, config);
				jedisCluster.set(key, value);
			} catch (Exception e) {
				// 释放redis对象
				// close(jedis);
				e.printStackTrace();
			} finally {
				// 返还到连接池
				// close(jedis);
			}
		}
	  /**
	   * 存入缓存数据
	   * @param key
	   * @param value
	   * @param time
	   */
	  public static void set(byte[] key, byte[] value, int time) {
			// JedisCluster jedis =null;
			try {
				// jedis = new JedisCluster(jedisClusterNode, config);
				jedisCluster.set(key, value);
				jedisCluster.expire(key, time);
			} catch (Exception e) {
				// 释放redis对象
				// close(jedis);
				e.printStackTrace();
			} finally {
				// 返还到连接池
				// close(jedis);
			}
		}
	
	  /**
	   * 删除 key 存贮
	   * @param key
	   * @return
	   */
	  public static Long  del(String key) {
		  Long value = jedisCluster.del(key);
	      return value;
	  }
	  /**
	   * 设置 过期时间 单位秒
	   * @param key
	   * @param value
	   * @param seconds
	   * @return
	   */
	  public static  void setTimeSecond(String key,String value,int seconds ) {
		   jedisCluster.setex(key, seconds, value);
	  }
	  /**
	   * 设置 过期时间 单位毫秒
	   * @param key
	   * @param value
	   * @param milliseconds
	   * @return
	   */
	  public static  void setTimeMilliseconds(String key,String value,long milliseconds ) {
		   jedisCluster.psetex(key, milliseconds, value);
	  }
	  /**
	   * 设置 过期时间 以天为单位
	   * @param key
	   * @param value
	   * @param day
	   * @return
	   */
	  public static  void setTimeDay(String key,String value,int day ) {
		   jedisCluster.psetex(key, day*24*60*60, value);
	  }
	  /**
	   * 设置 过期时间 以小时为单位
	   * @param key
	   * @param value
	   * @param Hour
	   * @return
	   */
	  public static  void setTimeHour(String key,String value,int Hour ) {
		   jedisCluster.psetex(key, Hour*60*60, value);
	  }
	  /**
	   * 设置 过期时间 以分钟为单位
	   * @param key
	   * @param value
	   * @param minute
	   * @return
	   */
	  public static  void setTimeMinute(String key,String value,int minute ) {
		   jedisCluster.psetex(key, minute*60, value);
	  }
		/**
		 * redis使用接口
		 * @param key
		 * @param value
		 * @param time
		 * @throws Exception 
		 */
		public static void setRedis(String key, Object value, Integer time) throws Exception {
			if (time != null) {
				set(key.getBytes(), ObjectUtil.objectToBytes(value), time);
			} else {
				set(key.getBytes(), ObjectUtil.objectToBytes(value));
			}
		}
		public static void setRedis(String key, Object value) throws Exception {
			set(key.getBytes(), ObjectUtil.objectToBytes(value));
		}
		public static Object getRedis(String key) throws Exception {
			if (null != RedisCacheUtil.get(key.getBytes())) {
				return ObjectUtil.bytesToObject(RedisCacheUtil.get(key.getBytes()));
			}else{
				return null;
			}
		}
}
