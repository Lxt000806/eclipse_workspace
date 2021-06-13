package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PersonMessage;

public interface PersonMessageService extends BaseService {

	/**PersonMessage分页信息
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PersonMessage personMessage);
	
	/**获取员工推送信息
	 * @return
	 */
	public List<Map<String,Object>> getEmployeePushList();
	/**获取所有未填明日施工计划的项目经理
	 * @return
	 */
	public List<Map<String,Object>> getProjectManReportList();
	/**更新推送消息
	 * @return
	 */
	public void updateEmployeePushList(String phone);

	/**获取消息条数
	 * @param personMessage
	 * @return
	 */
	public long getMessageCount(PersonMessage personMessage);
	/**获取未读待办消息条数
	 * @param personMessage
	 * @return
	 */
	public long getNotConfirmedMessageCount(PersonMessage personMessage);

	/**批量修改为已读
	 * @param personMessage
	 */
	public void updateBatch(PersonMessage personMessage);
	
	/**PersonMessage分页信息（接口用）
	 * @param page
	 * @param personMessage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlForClient(Page<Map<String,Object>> page, PersonMessage personMessage);
	
	public Page<Map<String,Object>> findPageBySqlForBS(Page<Map<String,Object>> page, PersonMessage personMessage);
	
	public Map<String, Object> getPersonMessage(String pk);
	
	public Page<Map<String,Object>> getDelayExecList(Page<Map<String,Object>> page, Integer msgPk);
	
	public  Map<String, Object> getMsgInfo(Integer pk);
	
	public PersonMessage getPersonMessageByCondition(PersonMessage personMessage);
	
	public Boolean existsRelatedRecord(PersonMessage personMessage);
	
}
