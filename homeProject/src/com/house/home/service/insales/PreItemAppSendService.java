package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.PreItemAppSend;

public interface PreItemAppSendService extends BaseService {

	/**PreItemAppSend分页信息
	 * @param page
	 * @param preItemAppSend
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PreItemAppSend preItemAppSend,UserContext uc);

	/**
	 * 材料信息
	 * @author	created by zb
	 * @date	2019-4-9--下午6:16:49
	 * @param page
	 * @param preItemAppSend
	 * @return
	 */
	public Page<Map<String,Object>> findItemPageBySql(Page<Map<String, Object>> page,
			PreItemAppSend preItemAppSend);
	/**
	 * 保存存储过程
	 * @author	created by zb
	 * @date	2019-4-12--上午11:11:02
	 * @param preItemAppSend
	 * @return
	 */
	public Result doSave(PreItemAppSend preItemAppSend);
	/**
	 * 仓库分批发货存储过程
	 * @author	created by zb
	 * @date	2019-4-19--下午2:39:28
	 * @param preItemAppSend
	 * @return
	 */
	public Result doCkfhsqSend(PreItemAppSend preItemAppSend);
	/**
	 * 判断是否部分发货
	 * @author	created by zb
	 * @date	2019-4-23--下午2:45:10
	 * @param preItemAppSend
	 */
	public List<Map<String, Object>> isHaveSend(PreItemAppSend preItemAppSend);
	/**
	 * 部分发货存储过程
	 * @author	created by zb
	 * @date	2019-4-24--下午3:52:25
	 * @param preItemAppSend
	 * @return
	 */
	public Result doCkfhsqSendByPart(PreItemAppSend preItemAppSend);
	/**
	 * 仓库发货app--获取list
	 * @author	created by zb
	 * @date	2019-9-17--上午10:47:17
	 * @param page
	 * @param id
	 * @param address
	 * @param itemType1 
	 * @param uc 
	 * @return
	 */
	public Page<Map<String,Object>> findWarehouseSendList(Page<Map<String,Object>> page, String id, String address, String itemType1, UserContext uc);
	
}
