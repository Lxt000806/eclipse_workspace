package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustTag;
import com.house.home.entity.design.CustTagGroup;
import com.house.home.entity.design.ResrCust;

public interface CustTagService extends BaseService{
	
	public List<Map<String, Object>> getCustTagGroup();
	
	public List<Map<String, Object>> getCustTagByGroupPk(Integer tagGroupPk);
	
	public Result doSaveCustTagMapper(String resrCustCode,String czybh,String tagPkListString);
	
	public Result doBatchSetCustTag(ResrCust resrCust);
	
	public List<Map<String, Object>> findCustTagsForTree();
	
	Page<Map<String, Object>> findCustTagGroupPageBySql(Page<Map<String, Object>> page, CustTagGroup custTagGroup);

    Page<Map<String, Object>> findCustTagPageBySql(Page<Map<String, Object>> page, CustTag custTag);

    Result doSave(CustTagGroup custTagGroup, UserContext userContext);

    Result doUpdate(CustTagGroup custTagGroup, UserContext userContext);

    Integer countTagUsage(Integer tagPk);
}
