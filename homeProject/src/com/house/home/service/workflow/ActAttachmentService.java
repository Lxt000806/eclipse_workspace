package com.house.home.service.workflow;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.workflow.ActAttachment;

public interface ActAttachmentService extends BaseService {

	/**ActAttachment分页信息
	 * @param page
	 * @param actAttachment
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActAttachment actAttachment);
	
}
