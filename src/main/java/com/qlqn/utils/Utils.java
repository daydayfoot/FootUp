package com.qlqn.utils;

import java.text.DecimalFormat;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Utils {

	private static DecimalFormat df = new DecimalFormat("#.00");

	// 判空
	public static boolean isNullString(Object str) {
		return (null == str || "".equals(str.toString().trim()));
	}

	// 格式化小数（保留两位小数）
	public static Double formatDecimal(Double formatValue) {
		if (!isNullString(formatValue))
			return Double.valueOf(df.format(formatValue));
		return 0.00;
	}

	// 转字符串
	public static String objct2str(Object value) {
		if (!isNullString(value))
			return value.toString().trim();
		return "";
	}

	/**
	 * 转码
	 * @param ori
	 * @return
	 */
	public static String convertUnicode(String ori) {
		char aChar;
		int len = ori.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = ori.charAt(x++);
			if (aChar == '\\') {
				aChar = ori.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = ori.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);

		}
		return outBuffer.toString();

	}
	
	
	
	 public static String chinaToUnicode(String str){  
	        String result="";  
	        for (int i = 0; i < str.length(); i++){  
	            int chr1 = (char) str.charAt(i);  
	            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
	                result+="\\u" + Integer.toHexString(chr1);  
	            }else{  
	                result+=str.charAt(i);  
	            }  
	        }  
	        return result;  
	 }
	 
	 public static String getYearOfBirthday( String id ) {
	        String birth = "";

	        if (id.length() == 18)
	        {
	            birth = id.substring(6, 10);

	        }
	        else if (id.length() == 15)
	        {
	            birth = "19" + id.substring(6, 8);
	        }
	        return birth;
	    }

	/**
	 * 判断实体类中有一个属性不为空
	 * @param vo
	 * @return
	 */
	public static boolean isNotNull(Object vo){
		try {
			Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(vo));
			if(!Utils.isNullString(map)){
				for (Map.Entry<String, Object> entry : map.entrySet()){  
					if(!Utils.isNullString(entry.getValue())){
						return true;
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 身份证脱敏
	 * @param cid 身份证
	 * @return
	 */
	public static String cidDesensitization(String cid){
		if(!isNullString(cid)){
			if(cid.length() == 18){
				cid = cid.substring(0,10)+"XXXX"+cid.substring(14);
			}else if(cid.length() == 15){
				cid = cid.substring(0,8)+"XXXX"+cid.substring(12);
			}
		}
		return cid;
	}
	
	/**
	 * 手机号脱敏
	 * @param phone 手机号
	 * @return
	 */
	public static String phoneDesensitization(String phone){
		if(!isNullString(phone)){
			if(phone.length() == 11){
				phone = phone.substring(0,3)+"XXXX"+phone.substring(7);
			}
		}
		return phone;
	}
}
