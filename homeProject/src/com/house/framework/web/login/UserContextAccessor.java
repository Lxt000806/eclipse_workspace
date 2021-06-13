package com.house.framework.web.login;

/**
 * 用户上下文访问器接口
 * 开发员实现该接口，同时让spring管理实现类，则用户登入时会自动载入开发人员的数据到UserContext中
 */
public interface UserContextAccessor {

	void addCustomDatas(UserContext uc);
}
