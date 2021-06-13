package com.house.home.serviceImpl.design;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.Result;
import com.house.framework.commons.fileUpload.impl.CustContractRule;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.FileHelper;
import com.house.framework.commons.utils.FileUploadUtils;
import com.house.framework.commons.utils.JasperReportUtils;
import com.house.framework.commons.utils.NumberToCnUtil;
import com.house.framework.commons.utils.PdfUtils;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.commons.utils.WordUtils;
import com.house.framework.commons.utils.esign.ESignUtils;
import com.house.framework.commons.utils.esign.entity.PosBeanInfo;
import com.house.home.dao.design.CustContractDao;
import com.house.home.dao.design.CustProfitDao;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.CustContract;
import com.house.home.entity.design.CustContractFile;
import com.house.home.entity.design.CustContractTempMapper;
import com.house.home.entity.design.CustContractValue;
import com.house.home.entity.design.CustProfit;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustContractService;

@SuppressWarnings("serial")
@Service
public class CustContractServiceImpl extends BaseServiceImpl implements CustContractService {

	@Autowired
	private CustContractDao custContractDao;
	@Autowired
	private CustProfitDao custProfitDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContract custContract){
		return custContractDao.findPageBySql(page, custContract);
	}

	@Override
	public Map<String, Object> getConInfo(CustContract custContract) {
		return custContractDao.getConInfo(custContract);
	}
	
	@Override
	public Map<String, Object> getHisConInfo(CustContract custContract) {
		return custContractDao.getHisConInfo(custContract);
	}

	@Override
	public List<Map<String, Object>> hasCon(CustContract custContract) {
		return custContractDao.hasCon(custContract);
	}

	@Override
	public Result doCreate(CustContract custContract,Customer customer,CustProfit...custProfits) {
		//保存销售合同信息表
		custContract.setConDescr(customer.getAddress()+custContractDao.getConTypeDescr(custContract.getConType()));
		custContractDao.save(custContract);
		
		//更新客户表
		custContractDao.update(customer);
		
		//更新客户毛利分析表
		if(custProfits != null && custProfits.length>0){
			custProfitDao.doCustProfitForProc(custProfits[0]);
		}
		
		return new Result(Result.SUCCESS_CODE, "创建成功");
	}

	@Override
	public Result getConNo(String custType) {
		return custContractDao.getConNo(custType);
	}

	@Override
	public Map<String, Object> getContractTemp(String custType, String type,String builderCode) {
		return custContractDao.getContractTemp(custType, type ,builderCode);
	}

	@Override
	public Map<String, Object> getOldCustType(String custCode) {
		return custContractDao.getOldCustType(custCode);
	}
	
	@Override
	public Result createDoc(CustContract custContract, Customer customer,String postData,boolean isSubmit){
		Result result = new Result();
		CustType custType = this.get(CustType.class, customer.getCustType());
		CustProfit custProfit = this.get(CustProfit.class, customer.getCode());
		
		//工程总造价=合同总额-基础直接费优惠-基础其他优惠1-基础其他优惠2-设计费返还（收标准设计费时）+税金
		Double baseDiscPerFee = (1-custProfit.getBaseDiscPer())*customer.getBaseFeeDirct();
		baseDiscPerFee = Double.parseDouble(String.format("%.0f", baseDiscPerFee));//保留整数
		Double contractFee = customer.getContractFee()-baseDiscPerFee-custProfit.getBaseDisc1()
				-custProfit.getBaseDisc2()+customer.getTax();
		if ("2".equals(custType.getContractFeeRepType())) {
			contractFee = contractFee-custProfit.getReturnDesignFee();
		}
		customer.setContractFee(contractFee);
		
		//根据合同pk删除掉合同表达式取值
		custContractDao.delValueByCustContractPK(custContract.getPk());
		
		//生成合同表达式到取值表
		createCustContractValue(custContract,customer);
		
		//合并后文件名
		String mergerPdfName = "merge.pdf";
		
		//临时文件夹路径
		String tempFilePath = System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/temp/contract/"+custContract.getPk()+"/"; 
		
		//每次开始生成时，删除临时文件
		FileHelper.delFile(tempFilePath);
		
		//创建临时文件夹
		File file = new File(tempFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		//施工合同生成主合同和合同附件
		if("1".equals(custContract.getConType())){
			Map<String, Object> againSignMap = custContractDao.getOldCustType(custContract.getCustCode());//重签记录
			Map<String, Object> latelySignedConMap = custContractDao.latelySignedCon(custContract);//最近一条已签约记录
			Integer oldConPk = null ;
			if(latelySignedConMap != null){
				oldConPk = Integer.parseInt(latelySignedConMap.get("PK").toString());//最近一条已签约合同pk
			}
			Map<String, Object> mainConTemp = new HashMap<String, Object>();//主合同模板
			Map<String, Object> appendConTemp = new HashMap<String, Object>();//合同附件模板
			
			//没重签或者有重签但是换了客户类型，都按当前客户类型 的模板，否则使用原客户类型模板
			if(againSignMap == null || againSignMap != null && !customer.getCustType().equals(againSignMap.get("CustType").toString())){
				mainConTemp = custContractDao.getContractTemp(customer.getCustType(), "1" , customer.getBuilderCode());
				appendConTemp = custContractDao.getContractTemp(customer.getCustType(), "2", customer.getBuilderCode());
			}else {
				mainConTemp = custContractDao.getHisContractTemp(oldConPk, null, "1" );
				appendConTemp = custContractDao.getHisContractTemp(oldConPk, null, "2");
				//重签但是找不到原模板，也按当前
				if (mainConTemp == null) {
					mainConTemp = custContractDao.getContractTemp(customer.getCustType(), "1" , customer.getBuilderCode());
				}
				if (appendConTemp == null) {
					appendConTemp = custContractDao.getContractTemp(customer.getCustType(), "2", customer.getBuilderCode());
				}
			}
			
			//主合同word填充并转为pdf
			if (mainConTemp == null) {
				return new Result(Result.FAIL_CODE, "生成合同失败，未找到主合同模板");
			}
			
			//保存到销售合同对应模板表
			createCustContractTempMapper(Integer.parseInt(mainConTemp.get("PK").toString()), custContract);
			
			//填充并转成pdf
			fillDataAndConvertToPdf(mainConTemp,tempFilePath,customer,custContract,custProfit,"mainCon");
			
			//合同附件word填充并转为pdf
			if (appendConTemp == null && "2".equals(custType.getType())) {
				return new Result(Result.FAIL_CODE, "生成合同失败，未找到合同附件模板");
			}
			if("2".equals(custType.getType())){
				//保存到销售合同对应模板表
				createCustContractTempMapper(Integer.parseInt(appendConTemp.get("PK").toString()), custContract);
				
				//填充并转成pdf
				fillDataAndConvertToPdf(appendConTemp,tempFilePath,customer,custContract,custProfit,"appendCon");
			}
			
			//jasper转为pdf
			convertJasperToPdf(postData, tempFilePath);
			
		}else {
			//设计协议模板
			Map<String, Object> designConTemp = custContractDao.getContractTemp(customer.getCustType(), "3", customer.getBuilderCode());
			
			if (designConTemp == null) {
				return new Result(Result.FAIL_CODE, "生成设计协议失败，未找到设计协议模板");
			}
			
			//保存到销售合同对应模板表
			createCustContractTempMapper(Integer.parseInt(designConTemp.get("PK").toString()), custContract);
			
			//填充并转成pdf
			fillDataAndConvertToPdf(designConTemp,tempFilePath,customer,custContract,custProfit,"designCon");
			
		}
		
		//将文件信息保存到销售合同文件表
		if(isSubmit){
			createCustContractFiles(custContract, tempFilePath);
		}
		
		//合并pdf并上传到服务器,返回rule,设置上传后的文件名为源文件名
		CustContractRule rule = mergePdf(tempFilePath,mergerPdfName,isSubmit);
		
		//把返回的文件名存到info中,把最后生成好的路径放到code中
		if(rule != null){
			result.setInfo(rule.getFullName());
		}
		result.setCode(tempFilePath+mergerPdfName);
		
		return result;
		
	}
	
	/**
	 * 保存到销售合同对应模板表
	 * @param conTempPk
	 * @param conPk
	 */
	public void createCustContractTempMapper(Integer conTempPk,CustContract custContract){
		CustContractTempMapper contractTempMapper = new CustContractTempMapper();
		contractTempMapper.setConTempPk(conTempPk);
		contractTempMapper.setConPk(custContract.getPk());
		contractTempMapper.setExpired("F");
		contractTempMapper.setLastUpdate(new Date());
		contractTempMapper.setActionLog("ADD");
		contractTempMapper.setLastUpdatedBy(custContract.getLastUpdatedBy());
		this.save(contractTempMapper);
	}
	
	/**
	 * 保存到合同表达式取值表
	 * @param custContract
	 */
	public void createCustContractValue(CustContract custContract,Customer customer){
		try {
			//签约时间
			ConvertUtils.register(new DateConverter(null), java.util.Date.class); 
			CustContractValue signDateValue = new CustContractValue();
			signDateValue.setCustContractPk(custContract.getPk());
			signDateValue.setTheKey("SignDate");
			signDateValue.setValue(DateUtil.format(new Date(),"yyyy-MM-dd"));
			signDateValue.setLastUpdate(new Date());
			signDateValue.setLastUpdatedBy(custContract.getLastUpdatedBy());
			signDateValue.setExpired("F");
			signDateValue.setActionLog("ADD");
			custContractDao.save(signDateValue);
			
			//签约时间
			CustContractValue signDate1Value = new CustContractValue();
			BeanUtils.copyProperties(signDate1Value, signDateValue);
			signDate1Value.setTheKey("SignDate1");
			custContractDao.save(signDate1Value);
			
			//设计费
			Map<String, Object> dataMap = this.custContractDao.getConInfo(custContract);
			CustContractValue designFeeValue = new CustContractValue();
			BeanUtils.copyProperties(designFeeValue, signDateValue);
			String designFee = dataMap.get("designFee").toString();
			designFeeValue.setTheKey("DesignFee");
			designFeeValue.setValue(designFee);
			custContractDao.save(designFeeValue);
			
			//保存设计费大写
			CustContractValue designFeeDXValue = new CustContractValue();
			BeanUtils.copyProperties(designFeeDXValue, signDateValue);
			String designFeeDX = NumberToCnUtil.number2CNMontrayUnit(Double.valueOf(designFee));
			designFeeDXValue.setValue(designFeeDX);
			designFeeDXValue.setTheKey("DesignFeeDX");
			custContractDao.save(designFeeDXValue);
			
			//保存工程造价大写
			CustContractValue contractFeeDXValue = new CustContractValue();
			BeanUtils.copyProperties(contractFeeDXValue, signDateValue);
			String contractFeeDX = NumberToCnUtil.number2CNMontrayUnit(customer.getContractFee());
			contractFeeDXValue.setValue(contractFeeDX);
			contractFeeDXValue.setTheKey("ContractFeeDX");
			custContractDao.save(contractFeeDXValue);
			
			//住房结构（户型）名称
			CustContractValue layOutValue = new CustContractValue();
			BeanUtils.copyProperties(layOutValue, signDateValue);
			String layoutDescr = custContractDao.getXtdmNote(customer.getLayout(),"Layout");
			layOutValue.setTheKey("Layout");
			layOutValue.setValue(layoutDescr);
			custContractDao.save(layOutValue);
			
			//承包方式名称
			CustContractValue conModeValue = new CustContractValue();
			BeanUtils.copyProperties(conModeValue, signDateValue);
			String conModeDescr = custContractDao.getXtdmNote(custContract.getConMode(),"CONTRACTMODE");
			conModeValue.setTheKey("ConMode");
			conModeValue.setValue(conModeDescr);
			custContractDao.save(conModeValue);
			
			//签约人（乙方代表）电话
			CustContractValue partBRepPhoneValue = new CustContractValue();
			BeanUtils.copyProperties(partBRepPhoneValue, signDateValue);
			Employee employee = custContractDao.get(Employee.class, customer.getDesignMan());
			partBRepPhoneValue.setTheKey("PartBRepPhone");
			partBRepPhoneValue.setValue(employee.getPhone());
			custContractDao.save(partBRepPhoneValue);
			
			//公司名称
			Map<String, Object> cmpMap = custContractDao.getCmpInfo(customer);
			CustContractValue cmpnyNameValue = new CustContractValue();
			BeanUtils.copyProperties(cmpnyNameValue, signDateValue);
			cmpnyNameValue.setTheKey("CmpnyName");
			cmpnyNameValue.setValue(cmpMap.get("CmpnyName").toString());
			custContractDao.save(cmpnyNameValue);
			
			//公司地址
			CustContractValue cmpnyAddressValue = new CustContractValue();
			BeanUtils.copyProperties(cmpnyAddressValue, signDateValue);
			cmpnyAddressValue.setTheKey("CmpnyAddress");
			cmpnyAddressValue.setValue(cmpMap.get("CmpnyAddress").toString());
			custContractDao.save(cmpnyAddressValue);
			
			//公司电话
			CustContractValue cmpnyPhoneValue = new CustContractValue();
			BeanUtils.copyProperties(cmpnyPhoneValue, signDateValue);
			cmpnyPhoneValue.setTheKey("CmpnyPhone");
			cmpnyPhoneValue.setValue(cmpMap.get("CmpnyPhone").toString());
			custContractDao.save(cmpnyPhoneValue);
			
			//施工方式名称
			CustContractValue constructTypeValue = new CustContractValue();
			BeanUtils.copyProperties(constructTypeValue, signDateValue);
			String constructTypeDescr = custContractDao.getXtdmNote(customer.getConstructType(),"CONSTRUCTTYPE");
			constructTypeValue.setTheKey("ConstructType");
			constructTypeValue.setValue(constructTypeDescr);
			custContractDao.save(constructTypeValue);
			
			//设计风格名称
			CustContractValue designStyleValue = new CustContractValue();
			BeanUtils.copyProperties(designStyleValue, signDateValue);
			String designStyleDescr = custContractDao.getXtdmNote(customer.getDesignStyle(),"DESIGNSTYLE");
			designStyleValue.setTheKey("DesignStyle");
			designStyleValue.setValue(designStyleDescr);
			custContractDao.save(designStyleValue);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Result doSubmit(CustContract custContract, Customer customer,String postData) {
		//非草稿状态不允许提交审核
		if (!"1".equals(custContract.getStatus())) {
			return new Result(Result.FAIL_CODE, "非草稿状态，不能提交审核");
		}
		
		//若赠送项目中存在审批级别不为空的记录，则当前的优惠审批状态必须是‘已审批’
		List<Map<String, Object>> hasConfirmLevelList =  custContractDao.hasConfirmLevel(customer);
		List<Map<String, Object>> isConfirmedDiscList =  custContractDao.isConfirmedDisc(customer);
		if(hasConfirmLevelList != null && hasConfirmLevelList.size()>0 
				&& (isConfirmedDiscList == null || isConfirmedDiscList.size() == 0) ){
			return new Result(Result.FAIL_CODE, "补充协议存在需要审批的条款，请先提交优惠审批");
		}
		
		//生成合同文件
		Result result = this.createDoc(custContract, customer, postData, true);
		
		if("0".equals(result.getCode())){
			return new Result(Result.FAIL_CODE, result.getInfo());
		}
		
		//更新合同表
		custContract.setStatus("2");
	  	custContract.setOriginalDoc(result.getInfo());
	  	this.update(custContract);
	  	
		return new Result(Result.SUCCESS_CODE, "提交审核成功");
		
	}
	
	/**
	 * 填充word并转成pdf
	 * @param conTempMap
	 */
	@SuppressWarnings({ "unchecked" })
	public void fillDataAndConvertToPdf(Map<String, Object> conTempMap,String tempFilePath,Customer customer,CustContract custContract,CustProfit custProfit,String pdfName){
		List<Map<String, Object>> conTempFieldList = custContractDao.getContractTempField(Integer.parseInt(conTempMap.get("PK").toString()));
		Map<String, Object> conTempFieldMap = new HashMap<String, Object>();
		Map<String, String> exMap = new CaseInsensitiveMap();
		Map<String, String> temMap = new HashMap<String, String>();
		String expression = "";//表达式
		String descr = "";//最后要保存的值
		String[] exArr = {};//拆分后的数组
		String table = "";//表名
		String col = "";//列名
		OutputStream os = null;
		InputStream is = null;
		try {
			if(conTempFieldList != null && conTempFieldList.size() > 0){
				for (Map<String, Object> map : conTempFieldList) {
					expression = map.get("Expression").toString();
					//含有表达式的，从表达式中的表对应的实体中取值
					if(expression.startsWith("${") && expression.endsWith("}")){
						expression = expression.replace("${", "").replace("}", "");
						//判断是否有【.】符号，有的从tCustomer,tCustContract,tCustProfit表取，否则从tCustContractValue表取
						if(expression.indexOf(".") != -1){
							exArr = StringUtils.split(expression,".");
							table = exArr[0];
							col = exArr[1];
							//目前暂时只支持客户表和合同表的表达式
							if("tCustomer".equalsIgnoreCase(table)){
								temMap = BeanUtils.describe(customer);
								//日期格式化，把后面的0去掉
								if(customer.getBeginDate() != null)
									temMap.put("beginDate", DateUtil.format(customer.getBeginDate(),"yyyy-MM-dd"));
								if(customer.getExpectMoveIntoDate() != null)
									temMap.put("expectMoveIntoDate", DateUtil.format(customer.getExpectMoveIntoDate(),"yyyy-MM-dd"));
							}else if("tCustContract".equalsIgnoreCase(table)){
								temMap = BeanUtils.describe(custContract);
							}else if("tCustProfit".equalsIgnoreCase(table)){
								temMap = BeanUtils.describe(custProfit);
							}else{
								descr = "-";
							}
							//表达式对应的实体是否有匹配到map，未匹配到还是取原来的值
							if(temMap != null){
								//遍历temMap，存入支持忽略大小写的exMap
								for (String key : temMap.keySet()) {
									exMap.put(key, temMap.get(key));
							    }
								if(StringUtils.isNotBlank(exMap.get(col))){
									descr = exMap.get(col);
								}else {
									descr = "-";
								}
							}else{
								descr = "-";
							}
						}else {
							descr = custContractDao.getCustContractValue(custContract.getPk(), expression);
						}
					}else{
						descr = expression;
					} 
					
					//值为空的设置为-
					if(StringUtils.isBlank(descr)){
						descr = "-";
					}
					
					conTempFieldMap.put(map.get("Code").toString(), descr);
				}
			}
			System.out.println(conTempFieldMap);
			//先把模板文件下载到临时文件夹，再获取路径进行填充
			String append = StringUtils.split(conTempMap.get("FileName").toString(),".")[1];//模板文件拓展名
			//新建一个文件夹放模板文件，因为格式不一样，跟pdf放在一起合并时候会报错
			File file = new File(tempFilePath+"conTemp/");
			if(!file.exists()){
				file.mkdirs();
			}
			String tempFileName = "conTemp/conTemp."+append;
			is = FileUploadUtils.download(conTempMap.get("FileName").toString());
			os= new FileOutputStream(tempFilePath+tempFileName);
			Streams.copy(is, os, true);
			WordUtils.fillDataAndConvertToPdf(tempFilePath+tempFileName,
					tempFilePath+System.currentTimeMillis()+pdfName+".pdf", conTempFieldMap);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Jasper转为pdf
	 * @param postData
	 * @param tempFilePath
	 */
	@SuppressWarnings("unchecked")
	public void convertJasperToPdf(String postData,String tempFilePath){
		if(!StringUtils.isEmpty(postData)){
			JSONObject obj = JSONObject.parseObject(postData);
			String report = obj.getString("report");
			Map<String,Object> param = new HashMap<String,Object>();
			if(!StringUtils.isEmpty(postData)){
				param = JSONObject.parseObject(postData, Map.class);
			}
			param.put("SUBREPORT_DIR", JasperReportUtils.getReportPath());
			JasperReportUtils.saveMultiPDFStream(report, param, tempFilePath);
		}
	}
	
	/**
	 * 合并pdf
	 * @param tempFilePath
	 * @param mergerPdfName
	 * @param custCode
	 */
	public CustContractRule mergePdf(String tempFilePath,String mergerPdfName,boolean isSubmit){
		List<File> pdfs = FileHelper.getFiles(tempFilePath);
		String[] pdfPaths = new String[pdfs.size()];
		for (int i = 0; i < pdfPaths.length; i++) {
			pdfPaths[i] = pdfs.get(i).getAbsolutePath();
		}
		CustContractRule rule = null;
		try {
			PdfUtils.mergePdfFiles(pdfPaths, tempFilePath+mergerPdfName);
			
			//审核提交时才上传
			if(isSubmit){
				InputStream is = new FileInputStream(tempFilePath+mergerPdfName);
				rule = new CustContractRule(mergerPdfName);
				FileUploadUtils.upload(is, rule.getFileName(),rule.getFilePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rule;
	}
	
	/**
	 * 一步发起签署
	 * @param custContract
	 */
	public Result oneStepStart(CustContract custContract){
		
		//源文件名
		String tempFileName = "originalDoc.pdf";
		
		//tomcat目录下创建临时文件夹
		String tempFilePath = System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/temp/contract/"+custContract.getPk()+"/originalDoc/"; 
		File file = new File(tempFilePath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		//生成token
		ESignUtils.generateESignToken(false);
		
		//创建个人账号
		JSONObject personAcctJson = ESignUtils.createPersonAcct(custContract.getPartyAid(), 
				custContract.getPartyAname(), "CRED_PSN_CH_IDCARD", custContract.getPartyAid(),
				custContract.getPartyAphone(), null);
		
		JSONObject personAcctDataJson = personAcctJson.getJSONObject("data");
		
		//如果账号已存在，则更新账户信息
		if(53000000 == personAcctJson.getInteger("code")){
			ESignUtils.modifyPerAccByThirdId(custContract.getPartyAid(), custContract.getPartyAname(), custContract.getPartyAid(), custContract.getPartyAphone());
		}
		
		//data为空，说明返回错误，报错提示
		if(personAcctDataJson == null){
			return new Result(Result.FAIL_CODE, personAcctJson.getString("message"));
		}
		
		String acctId = personAcctDataJson.getString("accountId");

		//通过上传方式创建文件，先下载到本地，在获取上传路径
		InputStream is = FileUploadUtils.download(custContract.getOriginalDoc());
		OutputStream os = null;
		try {
			os= new FileOutputStream(tempFilePath+tempFileName);
			Streams.copy(is, os, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//文件名 = 楼盘+合同类型.pdf
		String fileName = custContract.getConDescr()+".pdf";
		JSONObject uploadJson = ESignUtils.createFileByUpload(tempFilePath+tempFileName, fileName, acctId);
		String uploadUrl = uploadJson.getString("uploadUrl");
		String fileId = uploadJson.getString("fileId");
		
		//文件流上传文件
		ESignUtils.streamUpload(tempFilePath+tempFileName, uploadUrl);
		
	  	//易签宝收款单位表数据
	  	Map<String, Object> payeeESignMap = custContractDao.getTaxPayeeESign(custContract);
	  	String orgId = "";
	  	String sealId = "";
	  	if(payeeESignMap == null || payeeESignMap.get("SealId") == null 
	  			||(payeeESignMap.get("SealId") != null && StringUtils.isBlank(payeeESignMap.get("SealId").toString()))) {
	  		return new Result(Result.FAIL_CODE, "未找到对应的公司印章");
	  	}
	  	sealId = payeeESignMap.get("SealId").toString();
	  	orgId = payeeESignMap.get("OrgId").toString();
	  	System.out.println(payeeESignMap);
	  	List<CustContractFile> custContractFiles = custContractDao.findCrossPageSealFilesByConPk(custContract.getPk());
	  	List<PosBeanInfo> crossPagePosBeanInfos = null;
	  	if (custContractFiles != null && custContractFiles.size() > 0) {
	  		crossPagePosBeanInfos = new ArrayList<PosBeanInfo>(custContractFiles.size());
	  		for (CustContractFile custContractFile : custContractFiles) {
	  			String posPage = custContractFile.getBeginPageNum() + "-" + custContractFile.getEndPageNum();
				crossPagePosBeanInfos.add(new PosBeanInfo(posPage, null, 150F));
			}
	  	}
	  	
		//一步发起签署
		JSONObject flowJson = ESignUtils.oneStepFlow(fileId, fileName, acctId ,custContract.getConDescr(), orgId, crossPagePosBeanInfos,sealId);
		
		//删除临时文件
	  	FileHelper.delFile(tempFilePath);
	  	
		return new Result(Result.SUCCESS_CODE, flowJson.getString("flowId"));
	}

	@Override
	public Result doConfirmPass(CustContract custContract,PersonMessage personMessage) {
		//调用e签宝接口，一步发起签署
		Result result = oneStepStart(custContract);
		if(Result.SUCCESS_CODE.equals(result.getCode())){
			//生成消息通知申请人，消息类型‘其他’，‘customer.address’&‘的合同财务审核通过。’
			this.save(personMessage);
			
			//保存流程id到合同表，更新合同信息表
			custContract.setFlowId(result.getInfo());
			this.update(custContract);
			
			return new Result(Result.SUCCESS_CODE, "审核成功");
		}
		
		return result;
	}

	@Override
	public void doConfirmCancel(CustContract custContract,PersonMessage personMessage) {
		//生成消息通知申请人，消息类型‘其他’，‘customer.address’&‘的合同财务审核取消，请及时处理。’
		this.save(personMessage);
		
		//更新合同信息表
		this.update(custContract);
	}

	@Override
	public Result doCancel(CustContract custContract) {
		//草稿、已申请、已审核状态的才能取消合同
		if(!("1".equals(custContract.getStatus()) || "2".equals(custContract.getStatus())
			 || "3".equals(custContract.getStatus()))){
			return new Result(Result.FAIL_CODE, "当前状态，不能取消合同");
		}
		
		//施工合同，如果是审核状态的，要撤回签署任务；设计协议，如果是已申请状态的，要撤回签署任务
		if("3".equals(custContract.getStatus()) && "1".equals(custContract.getConType()) 
				|| "2".equals(custContract.getStatus()) && "2".equals(custContract.getConType())){
			//生成token
			ESignUtils.generateESignToken(false);
			//调用e签宝接口，撤回客户签署任务，使链接失效，合同作废
			ESignUtils.revokeSignFlow(custContract.getFlowId());
		}
		
		//更新合同信息表
		custContract.setStatus("7");
		this.update(custContract);
		
		return new Result(Result.SUCCESS_CODE, "取消合同成功");
	}
	
	/**
	 * 防止重复提交
	 * 利用redis单句指令的原子性，不管是多线程、分布式、集群，同一个key只有一次返回true
	 * @param key 流程Id
	 * @return
	 */
	public Boolean setExpireNx(final String key) {
		RedisConnection connection = RedisUtil.getRedisConnection();
		if (connection == null) return true; // 未安装redis，此处直接通过
		
		try {
			connection.select(0);
			
			String value = UUID.randomUUID().toString();
			connection.setNX(key.getBytes(), value.getBytes());
			connection.expire(key.getBytes(), 60L);
			byte[] nx = connection.get(key.getBytes());
			if(nx != null) {
				String result = new String(nx);
				if (value.equals(result)) {
					return true;
				}
			}
			return false;
		} finally {
			if (connection != null) connection.close();
		}
	}

	@Override
	public void eSignCallBack(Map<String, Object> map) {
		String action = map.get("action").toString();
		
		//流程结束回调
		if("SIGN_FLOW_FINISH".equals(action)){
			//流程Id
			String flowId = map.get("flowId").toString();
			//同一个流程Id只执行一次回调
			if(!setExpireNx(flowId)){
				return;
			}
			
			//任务状态2-已完成: 所有签署人完成签署；3-已撤销: 发起方撤销签署任务；5-已过期: 签署截止日到期后触发；7-已拒签
			String flowStatus = map.get("flowStatus").toString();
			String statusDescription = map.get("statusDescription").toString();	//拒签或失败时，附加的原因描述
			
			//根据流程Id查合同并转化为实体
			CustContract custContract = new CustContract();
			Map<String, Object> custConMap = custContractDao.getCustConByFlowId(flowId);
			BeanConvertUtil.mapToBean(custConMap, custContract);
			Customer customer = this.get(Customer.class, custContract.getCustCode());
			CustProfit custProfit = this.get(CustProfit.class, custContract.getCustCode());
			String conTypeDescr = custContractDao.getConTypeDescr(custContract.getConType());//合同类型
			
			//生成消息
			PersonMessage personMessage = new PersonMessage();
			personMessage.setMsgType("19");
			personMessage.setRcvCzy(custContract.getAppCzy());
			personMessage.setCrtDate(new Date());
			personMessage.setSendDate(new Date());
			personMessage.setRcvType("3");
			personMessage.setRcvStatus("0");
			personMessage.setIsPush("1");
			personMessage.setPushStatus("0");
			personMessage.setTitle("电子合同签署结果");
			
			if("2".equals(flowStatus)){//已完成
				//生成token
				ESignUtils.generateESignToken(false);
				
				//获取下载链接，上传到服务器
				JSONObject jsonObject = ESignUtils.downlodDocument(flowId);
				OutputStream os =  null;
				try {
					//通过链接获取输入流，上传到服务器
					URL url = new URL(jsonObject.getString("fileUrl"));
					InputStream is = url.openStream();
					//Tomcat临时目录
        			String tempFilePath = System.getProperty("catalina.home").replaceAll("\\\\", "/")
        					+"/temp/photo/"; 
        			File file = new File(tempFilePath);
        			if(!file.exists()){
        				file.mkdirs();
        			}
					try {
						os= new FileOutputStream(tempFilePath+custContract.getConNo()+".pdf");
						Streams.copy(is, os, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					FileInputStream inputStream = new FileInputStream(tempFilePath+custContract.getConNo()+".pdf");
					CustContractRule rule = new CustContractRule(custContract.getConNo()+".pdf");
					FileUploadUtils.upload(inputStream, rule.getFileName(),rule.getFilePath());
					custContract.setEffectDoc(rule.getFullName());
					FileHelper.delFile(tempFilePath);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(os!=null){
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				//通知申请人
				personMessage.setMsgText(customer.getAddress()+"的"+conTypeDescr+"已成功签署，请知悉。");
				this.save(personMessage);
				
				//更新合同表状态为已签约，甲方签署日期
				custContract.setStatus("4");
				custContract.setPartASignDate(new Date());
				this.update(custContract);
				
				//更新客户表签订日期
				if(customer.getSignDate() == null){
					customer.setSignDate(new Date());
				}
				customer.setReturnDesignFee(custProfit.getReturnDesignFee());
				customer.setDesignFee(custProfit.getDesignFee());
				customer.setStdDesignFee(custProfit.getStdDesignFee());
				customer.setPosition(custContractDao.getPositionCode(custProfit.getPosition()));
				this.update(customer);
				
			}if("5".equals(flowStatus)){//超时失效
				//通知申请人
				personMessage.setMsgText(customer.getAddress()+"的"+conTypeDescr+"超时未签约，请知悉。");
				this.save(personMessage);
				
				//施工合同，如果是审核状态的；设计协议，如果是已申请状态的需要控制超时
				if("3".equals(custContract.getStatus()) && "1".equals(custContract.getConType()) 
						|| "2".equals(custContract.getStatus()) && "2".equals(custContract.getConType())){
					custContract.setStatus("7");
					custContract.setEndDate(new Date());
					custContract.setEndRemarks("超时未签约");
					this.update(custContract);
				}
				
			}else if("7".equals(flowStatus)){
				//通知申请人
				personMessage.setMsgText(customer.getAddress()+"的"+conTypeDescr+"客户拒绝签署，请知悉。");
				this.save(personMessage);
				
				//更新合同表
				custContract.setStatus("5");
				custContract.setEndDate(new Date());
				custContract.setEndRemarks(statusDescription);
				this.update(custContract);
			}
		}
		
	}

	@Override
	public String getConTypeDescr(String type) {
		return custContractDao.getConTypeDescr(type);
	}

	@Override
	public List<Map<String, Object>> latelyCon(CustContract custContract) {
		return custContractDao.latelyCon(custContract);
	}

	@Override
	public void doUpdate(CustContract custContract, Customer customer,CustProfit...custProfits) {
		//更新销售合同信息表
		custContractDao.update(custContract);
		//更新客户表
		custContractDao.update(customer);
		
		//更新客户毛利分析表
		if(custProfits != null && custProfits.length>0){
			custContractDao.update(custProfits[0]);
		}
	}

	@Override
	public Map<String, Object> hasItemPlan(String custCode) {
		return custContractDao.hasItemPlan(custCode);
	}

	@Override
	public Map<String, Object> getHisContractTemp(Integer conPk,Integer conTempPk, String type) {
		return custContractDao.getHisContractTemp(conPk, conTempPk, type);
	}

	@Override
	public Map<String, Object> latelySignedCon(CustContract custContract) {
		return custContractDao.latelySignedCon(custContract);
	}

	/**
	 * 从tempFilePath目录获取文件信息，保存到销售合同文件表
	 * @param custContract
	 * @param tempFilePath
	 */
	private void createCustContractFiles(CustContract custContract, String tempFilePath){
		List<File> pdfs = FileHelper.getFiles(tempFilePath);
		int dispSeq = 0;
		int firstPageInMergedPdf = 1;
		for (File file : pdfs) {
		
			CustContractFile custContractFile = new CustContractFile();
			custContractFile.setConPk(custContract.getPk());
			custContractFile.setDescr(getFileDescr(file.getName()));
			
			int beginPageNum, endPageNum, totalPageNum;
			beginPageNum = firstPageInMergedPdf;
			totalPageNum = PdfUtils.getNumberOfPages(file.getAbsolutePath());
			endPageNum = beginPageNum + totalPageNum - 1;
			firstPageInMergedPdf += totalPageNum;
			custContractFile.setBeginPageNum(beginPageNum);
			custContractFile.setEndPageNum(endPageNum);
			custContractFile.setTotalPageNum(totalPageNum);
			
			if (totalPageNum >= 2) {
				custContractFile.setIsCrossPageSeal("1");
			} else {
				custContractFile.setIsCrossPageSeal("0");
			}
			
			custContractFile.setRemarks("");
			custContractFile.setDispSeq(++dispSeq);
			custContractFile.setLastUpdate(new Date());
			custContractFile.setLastUpdatedBy(custContract.getLastUpdatedBy());
			custContractFile.setExpired("F");
			custContractFile.setActionLog("ADD");
			this.save(custContractFile);
		}
	}
	
	public String getFileDescr(String fileName){
		if(fileName.indexOf("mainCon") != -1){
			return "主合同";
		}else if(fileName.indexOf("appendCon") != -1){
			return "合同附件";
		}else if(fileName.indexOf("designCon") != -1){
			return "设计协议";
		}else if(fileName.indexOf("mlfx") != -1){
			return "造价分析";
		}else if(fileName.indexOf("bcxy") != -1){
			return "补充协议";
		}else if(fileName.indexOf("main_cover") != -1){
			return "基础预算封面";
		}else if(fileName.indexOf("JZ") != -1){
			return "基础预算报表";
		}else if(fileName.indexOf("ZC_cover") != -1){
			return "主材预算封面";
		}else if(fileName.indexOf("ZC") != -1){
			return "主材预算报表";
		}else if(fileName.indexOf("service_cover") != -1){
			return "服务产品预算封面";
		}else if(fileName.indexOf("service") != -1){
			return "服务产品预算报表";
		}else if(fileName.indexOf("JC_cover") != -1){
			return "集成衣柜橱柜预算封面";
		}else if(fileName.indexOf("JC") != -1){
			return "集成衣柜橱柜预算报表";
		}else if(fileName.indexOf("RZ_itemSet_cover") != -1){
			return "软装预算封面（按套餐包汇总）";
		}else if(fileName.indexOf("RZ_itemSet") != -1){
			return "软装预算报表（按套餐包汇总）";
		}else if(fileName.indexOf("RZ_cover") != -1){
			return "软装预算封面";
		}else if(fileName.indexOf("RZ") != -1){
			return "软装预算报表";
		}else if(fileName.indexOf("TC_main_cover") != -1){
			return "预算报价表封面";
		}else if(fileName.indexOf("TC") != -1){
			return "预算报价表";
		}else if(fileName.indexOf("basicPrj") != -1){
			return "基础报价表";
		}else if(fileName.indexOf("individul") != -1){
			return "个性化报价表";
		}
		return "";
	}

	@Override
	public List<Map<String, Object>> hasDraftCon(CustContract custContract) {
		return custContractDao.hasDraftCon(custContract);
	}

	@Override
	public Result doSendDesign(CustContract custContract,Customer customer) {
		//生成合同文件
		Result result = this.createDoc(custContract, customer, "", true);
		
		if("0".equals(result.getCode())){
			return new Result(Result.FAIL_CODE, result.getInfo());
		}
		
	  	custContract.setOriginalDoc(result.getInfo());
	  	this.update(custContract);
		
		//调用e签宝接口，一步发起签署
		result = oneStepStart(custContract);
		if(Result.SUCCESS_CODE.equals(result.getCode())){
			//保存流程id到合同表，更新合同信息表
			custContract.setFlowId(result.getInfo());
			this.update(custContract);
			
			return new Result(Result.SUCCESS_CODE, "已成功发送");
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getDefaultReport(Customer customer) {
		return custContractDao.getDefaultReport(customer);
	}
	
	public static void main(String[] args) {

		BigDecimal bg = new BigDecimal(15.265);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println(f1);
		
		DecimalFormat df=new DecimalFormat("#.00");
		System.out.println(Double.parseDouble(df.format(15.265)));
		 
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		System.out.println(nf.format(15.265));
		
		Double db = Double.parseDouble(String.format("%.2f", 15.265));
		System.out.println(db);
	}
}
