package com.qlqn.sys.service;

import org.springframework.stereotype.Component;

@Component
public interface MobileCodeService {
	/**
	 * 检查密码连续输入错误次数 ,统计
	 * @param seriesErrorKey 账号KEY，非空
	 * @param failMillisecond 验证码过期时间（毫秒），可为空，默认：1000 * 60 * 60 * 24
	 * @param maxErrorCount 最多错误次数，可为空，默认：5
	 * @param cleanFlag 是否清除当天连续次数
	 * @return
	 * @throws Exception
	 */
	public String seriesErrCountVerify(String seriesErrorKey , String type, Integer failMillisecond, Integer maxErrorCount,Boolean cleanFlag) throws Exception;
}
