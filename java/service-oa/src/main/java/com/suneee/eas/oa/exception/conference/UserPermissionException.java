/**
 * @Title: UserPermissionException.java 
 * @Package com.suneee.eas.oa.exception.conference 
 * @Description: TODO(用一句话描述该文件做什么) 
 */ 
package com.suneee.eas.oa.exception.conference;

/**
 * @ClassName: UserPermissionException 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Company: 深圳象翌
 * @author xiongxianyun
 * @date 2018-08-10 09:29:51 
 *
 */
public class UserPermissionException extends Exception{
	/**
	 * @Fields serialVersionUID:TODO(用一句话描述这个变量表示什么) 
	 */ 
	private static final long serialVersionUID = -5458927621270565214L;

	public UserPermissionException(String message, Throwable cause){
		super(message, cause);
	}
	
	public UserPermissionException(String message){
		super(message);
	}
}
