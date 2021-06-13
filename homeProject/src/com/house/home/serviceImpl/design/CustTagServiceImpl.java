package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.design.CustTagDao;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.CustTag;
import com.house.home.entity.design.CustTagGroup;
import com.house.home.entity.design.CustTagMapper;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.design.CustTagService;
import com.sun.accessibility.internal.resources.accessibility;

@SuppressWarnings("serial")
@Service
public class CustTagServiceImpl extends BaseServiceImpl implements CustTagService{
	
	@Autowired
	private CustTagDao custTagDao;

	@Override
	public List<Map<String, Object>> getCustTagGroup(){
		return custTagDao.getCustTagGroup();
	}

	@Override
	public List<Map<String, Object>> getCustTagByGroupPk(Integer tagGroupPk) {
		return custTagDao.getCustTagByGroupPk(tagGroupPk);
	}

	@Override
	public Result doSaveCustTagMapper(String resrCustCode, String czybh,String tagPks) {
		Integer successedNum = 0;
		String tagPkList[] = tagPks.split(",");
		Date nowDate = new Date();
		String remarks = ""; 
		List<Map<String, Object>> oldCustTagList = custTagDao.getCustTagMapper(resrCustCode,czybh);

		if(oldCustTagList.size()>0){
			remarks +="原客户标签：";
			for (int i = 0; i < oldCustTagList.size(); i++) {
				remarks += oldCustTagList.get(i).get("TagDescr")+",";
				CustTagMapper ctm = get(CustTagMapper.class, Integer.parseInt(oldCustTagList.get(i).get("PK")+""));
				delete(ctm);
			}
		}else{
			remarks +="原客户标签：无";
		}
		
		if(tagPkList.length>0 && StringUtils.isNotBlank(tagPkList[0])){
			remarks +="现客户标签：";
			for (int i = 0; i < tagPkList.length; i++) {
				CustTagMapper custTagMapper = new CustTagMapper();
				custTagMapper.setResrCustCode(resrCustCode);
				custTagMapper.setCustCode("");
				custTagMapper.setTagPk(Integer.parseInt(tagPkList[i]));
				custTagMapper.setLastUpdate(nowDate);
				custTagMapper.setLastUpdatedBy(czybh);
				custTagMapper.setActionLog("ADD");
				custTagMapper.setExpired("F");
				CustTag custTag = get(CustTag.class, Integer.parseInt(tagPkList[i]));
				remarks += custTag.getDescr()+",";
				save(custTagMapper);
			}
			remarks = remarks.substring(0, remarks.length()-1);
		}else{
			remarks +="现客户标签：无";
		}
		
		CustCon custCon = new CustCon();
		custCon.setType("2");
		custCon.setConDate(nowDate);
		custCon.setConMan(czybh);
		custCon.setResrCustCode(resrCustCode);
		custCon.setExpired("F");
		custCon.setActionLog("ADD");
		custCon.setLastUpdate(nowDate);
		custCon.setLastUpdatedBy(czybh);
		custCon.setCustCode("");
		custCon.setRemarks(remarks);
		save(custCon);
		successedNum ++;
		if(successedNum>0){
			return new Result(Result.SUCCESS_CODE, "修改客户标签成功");
		}else{
			return new Result(Result.FAIL_CODE, "修改客户标签失败");
		}
	}
	
	@Override
	public Result doBatchSetCustTag(ResrCust resrCust) {
		return custTagDao.doBatchSetCustTag(resrCust);
	}

	@Override
	public List<Map<String, Object>> findCustTagsForTree() {
		return custTagDao.findCustTagsForTree();
	}
	
	@Override
    public Page<Map<String, Object>> findCustTagGroupPageBySql(
            Page<Map<String, Object>> page, CustTagGroup custTagGroup) {
        return custTagDao.findCustTagGroupPageBySql(page, custTagGroup);
    }
    
    @Override
    public Page<Map<String, Object>> findCustTagPageBySql(
            Page<Map<String, Object>> page, CustTag custTag) {
        return custTagDao.findCustTagPageBySql(page, custTag);
    }

    @Override
    public Result doSave(CustTagGroup custTagGroup, UserContext userContext) {
        
        if (StringUtils.isBlank(custTagGroup.getDescr())) {
            return new Result(Result.FAIL_CODE, "保存失败，标签组名称为空");
        }
        
        if (custTagGroup.getDispSeq() == null) {
            custTagGroup.setDispSeq(0);
        }
        
        if (StringUtils.isBlank(custTagGroup.getIsMulti())) {
            custTagGroup.setIsMulti("0");
        }
        
        
        custTagGroup.setCrtCzy(userContext.getCzybh());
        return custTagDao.saveCustTagGroupByProc(custTagGroup);
    }

    @Override
    public Result doUpdate(CustTagGroup custTagGroup, UserContext userContext) {

        if (StringUtils.isBlank(custTagGroup.getDescr())) {
            return new Result(Result.FAIL_CODE, "保存失败，标签组名称为空");
        }
        
        if (custTagGroup.getDispSeq() == null) {
            custTagGroup.setDispSeq(0);
        }
        
        if (StringUtils.isBlank(custTagGroup.getIsMulti())) {
            custTagGroup.setIsMulti("0");
        }
        
        custTagGroup.setLastUpdatedBy(userContext.getCzybh());
        
        return custTagDao.updateCustTagGroupByProc(custTagGroup);
    }

    @Override
    public Integer countTagUsage(Integer tagPk) {
        return custTagDao.countTagUsage(tagPk);
    }
	
}
