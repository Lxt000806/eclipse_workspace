package com.house.home.serviceImpl.design;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.UnionPayUtils;
import com.house.home.dao.design.CustPayTranDao;
import com.house.home.entity.design.CustPayTran;
import com.house.home.service.design.CustPayTranService;

@SuppressWarnings("serial")
@Service
public class CustPayTranServiceImpl extends BaseServiceImpl implements CustPayTranService {
	@Autowired
	private CustPayTranDao custPayTranDao;
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustPayTran custPayTran) {
		return custPayTranDao.findPageBySql(page, custPayTran);
	}

	@Override
	public Result doCustPayTran(CustPayTran custPayTran) {
		
		if ("2".equals(custPayTran.getM_umState())) { // "2"表示刷卡成功
			// 从银联开放平台获取卡性质（借记卡/贷记卡）、发卡行编码
			String cardInfo = UnionPayUtils.getCardInfo(custPayTran.getCardId());
			JSONObject jsonObject = JSONObject.parseObject(cardInfo);
			JSONObject data = jsonObject.getJSONObject("data");
			if (data != null) {
				custPayTran.setBankCode(data.getString("issInsId")); // 发卡行编码
				custPayTran.setCardAttr(data.getString("cardAttr")); // 卡性质（借记卡/贷记卡）
			}
		}
		
		Result result = custPayTranDao.doCustPayTran(custPayTran);
		if (!"1".equals(result.getCode())) {
			CustPayTran cpt = this.get(CustPayTran.class, custPayTran.getPk());
			if (cpt != null) {
				cpt.setExceptionRemarks(result.getInfo());
				this.update(cpt);
			}
		}
		return result;
	}

	@Override
	public void doRePrint(CustPayTran custPayTran) {
		custPayTranDao.doRePrint(custPayTran);
		
	}

	@Override
	public Result doUpdateProcedureFee(CustPayTran custPayTran) {
	
		return custPayTranDao.doUpdateProcedureFee(custPayTran); 
	}
}
