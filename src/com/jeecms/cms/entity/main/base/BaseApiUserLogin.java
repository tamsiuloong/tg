package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_api_user_login table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_api_user_login"
 */

public abstract class BaseApiUserLogin  implements Serializable {

	public static String REF = "ApiUserLogin";
	public static String PROP_LOGIN_TIME = "loginTime";
	public static String PROP_SESSSION_KEY = "sesssionKey";
	public static String PROP_USERNAME = "username";
	public static String PROP_ID = "id";
	public static String PROP_LOGIN_COUNT = "loginCount";


	// constructors
	public BaseApiUserLogin () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseApiUserLogin (Long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseApiUserLogin (
		Long id,
		String sesssionKey,
		String username,
		java.util.Date loginTime,
		Integer loginCount) {

		this.setId(id);
		this.setSessionKey(sesssionKey);
		this.setUsername(username);
		this.setLoginTime(loginTime);
		this.setLoginCount(loginCount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Long id;

	// fields
	private String sessionKey;
	private String username;
	private java.util.Date loginTime;
	private Integer loginCount;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="id"
     */
	public Long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: session_key
	 */
	public String getSessionKey () {
		return sessionKey;
	}

	/**
	 * Set the value related to the column: session_key
	 * @param sesssionKey the sesssion_key value
	 */
	public void setSessionKey (String sessionKey) {
		this.sessionKey = sessionKey;
	}


	/**
	 * Return the value associated with the column: username
	 */
	public String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * @param username the username value
	 */
	public void setUsername (String username) {
		this.username = username;
	}


	/**
	 * Return the value associated with the column: login_time
	 */
	public java.util.Date getLoginTime () {
		return loginTime;
	}

	/**
	 * Set the value related to the column: login_time
	 * @param loginTime the login_time value
	 */
	public void setLoginTime (java.util.Date loginTime) {
		this.loginTime = loginTime;
	}


	/**
	 * Return the value associated with the column: login_count
	 */
	public Integer getLoginCount () {
		return loginCount;
	}

	/**
	 * Set the value related to the column: login_count
	 * @param loginCount the login_count value
	 */
	public void setLoginCount (Integer loginCount) {
		this.loginCount = loginCount;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ApiUserLogin)) return false;
		else {
			com.jeecms.cms.entity.main.ApiUserLogin apiUserLogin = (com.jeecms.cms.entity.main.ApiUserLogin) obj;
			if (null == this.getId() || null == apiUserLogin.getId()) return false;
			else return (this.getId().equals(apiUserLogin.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}