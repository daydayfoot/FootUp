package com.qlqn.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Strings;

/**
 * String工具类，包含base64编解码以及16进制转换算法，主要用于计算签名
 */
public final class StringUtil {

    public static final String CHARSET_NAME_UTF8 = "UTF-8";
    public static final char[] digital = "0123456789ABCDEF".toCharArray();
    public static final String DEFAULT_DATA_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    
    public static String format(Date date){
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATA_TIME_FORMAT);
        return format.format(date);
    }
    
    public static String encodeHexStr(final byte[] bytes){
        if(bytes == null){
            return null;
        }
        char[] result = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            result[i*2] = digital[(bytes[i] & 0xf0) >> 4];
            result[i*2 + 1] = digital[bytes[i] & 0x0f];
        }
        return new String(result);
    }
    
    public static byte[] decodeHexStr(final String str) {
        if(str == null){
            return null;
        }
        char[] charArray = str.toCharArray();
        if(charArray.length%2 != 0){
            throw new RuntimeException("hex str length must can mod 2, str:" + str);
        }
        byte[] bytes = new byte[charArray.length/2];
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            int b;
            if(c >= '0' && c <= '9'){
                b = (c-'0')<<4;
            }else if(c >= 'A' && c <= 'F'){
                b = (c-'A'+10)<<4;
            }else{
                throw new RuntimeException("unsport hex str:" + str);
            }
            c = charArray[++i];
            if(c >= '0' && c <= '9'){
                b |= c-'0';
            }else if(c >= 'A' && c <= 'F'){
                b |= c-'A'+10;
            }else{
                throw new RuntimeException("unsport hex str:" + str);
            }
            bytes[i/2] = (byte)b;
        }
        return bytes;
    }
    public static String toString(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, CHARSET_NAME_UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String toString(final byte[] bytes, String charset) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charset);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static byte[] toBytes(final String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(CHARSET_NAME_UTF8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public static String formatJson(String str){
    	str = str.replace("'", "");
    	str = str.replace("&","&amp;");
        //FormatXMLStr = replace(FormatXMLStr,"<","&lt;")
        //FormatXMLStr = replace(FormatXMLStr,">","&gt;")
        //FormatXMLStr = replace(FormatXMLStr,"""","&quot;")
    	return str;
    }
    
    //将值为null的数据，值替换为""
    public static String trimNull(Object des) {  
        try {  
            if (des == null)  
                return "";  
            else  
                return des.toString();  
        } catch (NullPointerException npe) {  
            return "";  
        }  
    } 
    
   //通过反射，将对象内的所有字段，字符类型的空值替换为""
    public static <T> void trim(T o) {  
        if (o != null) {  
            try {  
            	//得到对象内的所有字段
                Field[] fields = o.getClass().getFields();  
                //遍历检查, 所有String字段做相应处理, 能过以上得出以下方法
                for (int j = 0; j < fields.length; j++) {  
                    if (fields[j].getType().getName().equals("java.lang.String")  
                            && fields[j].get(o) == null) {  
                        fields[j].set(o, "");//设置为空字串  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    private StringUtil() {
    
    }
    
    /**
	 * 判断字符串是否为""或null
	 */
	public static boolean isNullOrEmpty(String msg) {
		return Strings.isNullOrEmpty(msg);
	}

	/**
	 * 判断字符串是否为空白字符串（包括""）或null
	 */
	public static boolean isNullOrBlank(String msg) {
		boolean flag = true;
		if (Strings.isNullOrEmpty(msg)) {
			return flag;
		} else {
			for (int i = 0, length = msg.length(); i < length; i++) {
				if (!Character.isWhitespace(msg.charAt(i))) {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * object to String， null返回""
	 */
	public static String objectToString(Object obj) {
		String result = "";
		if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	/**
	 * 如果一个字符串为null返回"",避免空指针问题
	 */
	public static String nullToEmpty(String msg) {
		return Strings.nullToEmpty(msg);
	}
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param src
	 *            需判断的字符串
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isNotEmpty(String src) {
		return !isEmpty(src);
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param src
	 *            需判断的对象
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isNotEmpty(Object src) {
		return !isEmpty(src);
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 *            需判断的集合
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 *            需判断的Map
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 *            需判断的数组
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	
	
	/**
	 * 字符串类型为空的判断,为空返回true
	 * 
	 * @param src
	 *            需判断的字符串
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isEmpty(String src) {
		return src == null || src.length() == 0;
	}

	/**
	 * 对象为空的判断
	 * 
	 * @param src
	 *            需判断的对象
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isEmpty(Object src) {
		if (src == null) {
			return true;
		} else if (src instanceof String) {
			return isEmpty((String) src);
		} else if (src instanceof Map) {
			return isEmpty((Map<?, ?>) src);
		} else if (src instanceof Collection) {
			return isEmpty((Collection<?>) src);
		} else if (src.getClass().isArray()) {
			return isEmpty((Object[]) src);
		} else {
			return src == null;
		}
	}

	/**
	 * 集合为空的判断
	 * 
	 * @param src
	 *            需判断的集合
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}

	/**
	 * Map为空的判断
	 * 
	 * @param src
	 *            需判断的Map
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 数组为空的判断
	 * 
	 * @param src
	 *            需判断的数组
	 * @return boolean
	 * @throws
	 * @exception
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * 读取项目内jsonFile
	 * @param filePath
	 * @return
	 */
	public static String localJsonFileToString(String filePath){
			if(null != filePath){
				String read = "";
				String readStr = "";
				try {
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
					while ((read = br.readLine()) != null) {
						readStr = readStr + read;
					}
					readStr = readStr.substring(0, readStr.length());
					return readStr;
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				return "";
			}
			
			return "";
		}
	/**
	 * 把数组转换成set
	 * @param array
	 * @return
	 */
	public static Set<?> array2Set(Object[] array) {
		Set<Object> set = new TreeSet<Object>();
		for (Object id : array) {
			if(null != id){
				set.add(id);
			}
		}
		return set;
	}
    /**
	 * 校验String参数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean verifyParams(String... params) {
		List<String> list = Arrays.asList(params);
		for (String string : list) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 获取随机数
	 * @param max	随机数最大值，如：999999
	 * @param min	随机数最小值，如：100000
	 * @return
	 */
	public static String calRandomScore(int max, int min) {
		Random random = new Random();
		Integer randomSum = random.nextInt(max) % (max - min + 1) + min;
		return randomSum+"";
	}
}