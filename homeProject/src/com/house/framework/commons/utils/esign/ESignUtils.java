package com.house.framework.commons.utils.esign;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.house.framework.commons.conf.SystemConfig;
import com.house.framework.commons.utils.esign.entity.Doc;
import com.house.framework.commons.utils.esign.entity.FlowConfigInfo;
import com.house.framework.commons.utils.esign.entity.FlowInfo;
import com.house.framework.commons.utils.esign.entity.PosBeanInfo;
import com.house.framework.commons.utils.esign.entity.Signer;
import com.house.framework.commons.utils.esign.entity.SignerAccount;
import com.house.framework.commons.utils.esign.entity.SignfieldInfo;

/**
 * 对接e签宝
 */
public class ESignUtils {
	
	private static Logger logger = LoggerFactory.getLogger(ESignUtils.class);
	
	/**
	 * token
	 */
	public static String ESIGN_TOKEN;
	
	/**
	 * token的时间
	 */
	private static long ESIGN_TOKEN_EXPIRED_TIME;
	
	/**
	 * 获取token
	 * @param force 强制获取token；如果为false，token未过期不重新获取。
	 */
	public static void generateESignToken(boolean force) {
		long tokenTime = System.currentTimeMillis();
		//测试时候可能会有其他地方刷新token，先注释掉，每次都重新获取
		/*if (!force) {
			if (tokenTime <= ESIGN_TOKEN_EXPIRED_TIME - ESignBean.TOKEN_PRE_TIME) { // token未过期，不重新获取
				return;
			}
		}*/
		
		try {
			
			JSONObject json = HttpHelper.doCommHttp(RequestType.GET,
					ESignBean.getToken_URL(ESignBean.PROJECT_ID, ESignBean.PROJECT_SECRET), null);
			
			json = JSONHelper.castDataJson(json, JSONObject.class);
			
			ESIGN_TOKEN = json.getString("token");
			ESIGN_TOKEN_EXPIRED_TIME = json.getLongValue("expiresIn");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建个人账号
	 * @param thirdPartyUserId 用户唯一标识，可传入第三方平台的用户 id、证件号、手机号、邮箱等，如果设置则作为账号唯一性字段，相同信息不可重复创建。（个人用户与机构的唯一标识不可重复）【必填】
	 * @param name 姓名【必填】
	 * @param idType 证件类型，默认CRED_PSN_CH_IDCARD【必填】
	 * @param idNumber 证件号（非实名签署时必填）
	 * @param mobile 手机号【可空】
	 * @param email 邮箱地址【可空】
	 * @return
	 */
	public static JSONObject createPersonAcct(String thirdPartyUserId, String name, String idType, String idNumber,
			String mobile, String email)  {

		JSONObject param = new JSONObject();
		param.put("thirdPartyUserId", thirdPartyUserId);
		param.put("name", name);
		param.put("idType", idType);
		param.put("idNumber", idNumber);
		param.put("mobile", mobile);
		param.put("email", email);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.createPerAcc_URL(), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 根据第三方账号修改账户信息
	 * @param thirdPartyUserId
	 * @param name
	 * @param idNumber
	 * @param mobile
	 * @return
	 */
	public static JSONObject modifyPerAccByThirdId(String thirdPartyUserId, String name, String idNumber,String mobile)  {

		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("idNumber", idNumber);
		param.put("mobile", mobile);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.modifyPerAccByThirdId_URL(thirdPartyUserId), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 创建机构账号
	 * @param thirdPartyUserId 用户唯一标识，可传入第三方平台的用户 id、证件号、手机号、邮箱等，如果设置则作为账号唯一性字段，相同信息不可重复创建。（个人用户与机构的唯一标识不可重复）【必填】
	 * @param creatorId 创建人Id【必填】
	 * @param name 机构名称【必填】
	 * @param idType 证件类型，默认CRED_ORG_USCC【必填】
	 * @param idNumber 证件号【必填】
	 * @return
	 */
	public static JSONObject createOrgAcct(String thirdPartyUserId, String creatorId, String name, String idType,
			String idNumber)  {

		JSONObject param = new JSONObject();
		param.put("thirdPartyUserId", thirdPartyUserId);
		param.put("creator", creatorId);
		param.put("name", name);
		param.put("idType", idType);
		param.put("idNumber", idNumber);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.createOrgAcc_URL(), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 修改机构
	 * @param orgId
	 * @param name
	 * @return
	 */
	public static JSONObject modifyOrgAcct(String orgId,String name,String orgLegalName,String orgLegalIdNumber)  {

		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("orgLegalName", orgLegalName);
		param.put("orgLegalIdNumber", orgLegalIdNumber);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.PUT, ESignBean.modifyOrgAccById_URL(orgId), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 注销机构账号
	 * @param orgId
	 * @return
	 */
	public static JSONObject logoutOrgAcc(String orgId)  {

		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.DELETE, ESignBean.logoutOrgAccById_URL(orgId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 创建机构印章
	 * @param orgId
	 * @param htext
	 * @param qtext
	 * @return
	 */
	public static JSONObject createOrgSeal(String orgId,String htext,String qtext,
			String color,String central,String type)  {

		JSONObject param = new JSONObject();
		param.put("type", type); //类型
		param.put("central", central); //中心图案类型
		param.put("color", color); //印章颜色
		param.put("htext", htext); //横向文
		param.put("qtext", qtext); //下弦文
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.createOfficialSeal_URL(orgId), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 查询机构
	 * @param orgId 机构账号
	 */
	public static JSONObject queryOrgAccById(String orgId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.queryOrgAccById_URL(orgId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 查询机构印章
	 * @param orgId 机构账号
	 */
	public static JSONArray queryOrgSeal(String orgId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.queryOrgSeal_URL(orgId, 1, 30), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (json != null) { 	
			json = JSONHelper.castDataJson(json, JSONObject.class);
			if (json != null) {
				return json.getJSONArray("seals");
			}
		}
		
		return null;
	}
	
	/**
	 * 删除机构印章
	 * @param orgId
	 * @param sealId
	 * @return
	 */
	public static JSONObject deleteSealByOrgId(String orgId,String sealId)  {

		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.DELETE, ESignBean.deleteSealByOrgId_URL(orgId, sealId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 查询个人账号
	 * @param thirdId 第三方账号
	 */
	public static JSONObject queryAccByThirdId(String thirdId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.queryAccByThirdId_URL(thirdId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 通过上传方式创建文件
	 * @param filePath
	 * @param fileName
	 * @param acctId
	 * @return
	 */
	public static JSONObject createFileByUpload(String filePath,String fileName,String acctId)  {

		File file = new File(filePath);
		if(!file.exists()) {
			logger.error("e签宝-文件不存在");
		}
		
		JSONObject param = new JSONObject();
		param.put("contentMd5", FileHelper.getContentMD5(filePath));
		param.put("contentType", ESignBean.PDF_TYPE);
		param.put("fileName", fileName);
		param.put("fileSize", String.valueOf(file.length()));
		param.put("accountId", acctId);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.fileUpload_URL(), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 *文件流上传文件
	 * @param filePath  文件路径
	 * @param uploadUrl 上传方式创建文件时返回的uploadUrl
	 * @return
	 */
	public static Object streamUpload(String filePath, String uploadUrl) {

		byte[] bytes = FileHelper.getBytes(filePath);
		String contentMd5 = FileHelper.getContentMD5(filePath);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doUploadHttp(RequestType.PUT, uploadUrl, bytes, contentMd5, ESignBean.PDF_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONHelper.castDataJson(json, Object.class);
	}
	
	/**
	 * 一步发起签署流程
	 * @param fileId
	 * @param fileName
	 * @param signerAccountId
	 * @param businessScene
	 * @return
	 */
	public static JSONObject oneStepFlow(String fileId, String fileName, String signerAccountId,String businessScene,String orgId,List<PosBeanInfo> crossPagePosBeanInfos,String sealId)  {

		// 待签文件列表，这里模拟只有一个待签文件
		Doc doc = new Doc(fileId, fileName);
		List<Doc> docs = Lists.newArrayList(doc);

		// 流程配置，可以不配置，使用默认配置
		String noticeDeveloperUrl = SystemConfig.getProperty("noticeDeveloperUrl", "", "eSign"); //"http://petexu.com:8080/notice/signature";//
		FlowConfigInfo flowConfigInfo = new FlowConfigInfo(noticeDeveloperUrl, "1,2", null);
		FlowInfo flowInfo = new FlowInfo(true,true,businessScene,flowConfigInfo,new Date().getTime()+ESignBean.EXPIRED_TIME);
		//平台方签署信息
		//骑缝章
		List<SignfieldInfo> platformList = Lists.newArrayList();
		if (crossPagePosBeanInfos != null && crossPagePosBeanInfos.size()>0) {
			for (PosBeanInfo posBeanInfo : crossPagePosBeanInfos) {
				SignfieldInfo perforationPostSignfield = new SignfieldInfo(true, null, fileId, null, null, 2, posBeanInfo, null,sealId);
				platformList.add(perforationPostSignfield);
			}
		}
		//正常章
		analysisCommon(platformList, fileId, "盖章处",true,sealId);
		SignerAccount orgAccount = new SignerAccount(orgId, orgId);//签署方账号
		Signer platformSigner = new Signer(false, 2, orgAccount, platformList, null);
		//用户方签署信息，只有正常章
		List<SignfieldInfo> userList = Lists.newArrayList();
		analysisCommon(userList, fileId, "签字处",false,null);
		SignerAccount signAccount = new SignerAccount(signerAccountId, signerAccountId);//签署方账号
		Signer userSigner = new Signer(false, 1, signAccount, userList, null); // 签署方
		
		List<Signer> signers = Lists.newArrayList(platformSigner, userSigner);
		
		JSONObject param = new JSONObject();
		param.put("docs", docs);
		param.put("flowInfo", flowInfo);
		param.put("signers", signers);
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.ONE_STEP_FLOW,param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 签署流程开启
	 * @param flowId 创建签署流程时返回的签署流程ID
	 */
	public static JSONObject startSignFlow(String flowId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.PUT, ESignBean.startFlows_URL(flowId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 流程文档下载
	 * @param flowId 创建签署流程时返回的签署流程ID
	 */
	public static JSONObject downlodDocument(String flowId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.aboutDocument_URL(flowId, ""), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//docs数组的取第一个
		json = JSONHelper.castDataJson(json, JSONObject.class);
		JSONArray jsonArray = json.getJSONArray("docs");
		JSONObject fileJson = jsonArray.getJSONObject(0);
		return fileJson;
	}
	
	/**
	 * 流程查询
	 * @param flowId 创建签署流程时返回的签署流程ID
	 */
	public static JSONObject queryFlows(String flowId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.queryFlows_URL(flowId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 撤销签署流程
	 * @param flowId 创建签署流程时返回的签署流程ID
	 */
	public static JSONObject revokeSignFlow(String flowId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.PUT, ESignBean.revokeFlows_URL(flowId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 设置静默签署
	 * @param accountId 账号
	 */
	public static JSONObject setSignAuth(String accountId) {
		
		JSONObject json = null;
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.signAuth_URL(accountId), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 机构认证
	 * @param accountId 机构账号
	 * @param agentAccountId 办理人账号
	 * @return
	 */
	public static JSONObject orgIdentity(String accountId,String agentAccountId) {
		
		JSONObject json = null;
		JSONObject param = new JSONObject();
		param.put("agentAccountId", agentAccountId);
		
		try {
			json = HttpHelper.doCommHttp(RequestType.POST, ESignBean.orgIdentity_URL(accountId), param.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JSONHelper.castDataJson(json, JSONObject.class);
	}
	
	/**
	 * 搜索关键字坐标
	 * @param flowId
	 * @param fileId
	 * @param keyWords
	 * @return
	 */
	public static JSONArray searchWordsPosition(String fileId, String keywords){
		JSONObject json = null;
		JSONObject param = new JSONObject();
		
		param.put("keywords", keywords);
		
		try {
			json = HttpHelper.doCommHttp(RequestType.GET, ESignBean.getKeywordsPosition_URL(fileId,keywords),null );
		} catch (Exception e) {
			e.printStackTrace();
		}
		//取到存放坐标的json数组
		JSONArray jsonArray = json.getJSONArray("data");
		return jsonArray;
	}
	
	/**
	 * 根据搜索到的关键字json数组，解析正常章
	 * @param list
	 * @param fileId
	 * @return
	 */
	public static void analysisCommon(List<SignfieldInfo> list,String fileId,String keywords,boolean autoExecute,String sealId){
		JSONArray keywordsList = searchWordsPosition(fileId, keywords) ;
		for (int k = 0; k < keywordsList.size(); k++) {
			JSONObject keyword = keywordsList.getJSONObject(k);
			JSONArray positionList = keyword.getJSONArray("positionList");
			for (int i = 0; i < positionList.size(); i++) {
				JSONObject coordAndPage = positionList.getJSONObject(i);
				JSONArray coordinateList = coordAndPage.getJSONArray("coordinateList");
				for (int j = 0; j < coordinateList.size(); j++) {
					JSONObject pos = coordinateList.getJSONObject(j);
					PosBeanInfo commonPosBean = new PosBeanInfo(coordAndPage.getString("pageIndex"), pos.getFloatValue("posx"), pos.getFloatValue("posy"));
					SignfieldInfo commonSignfield = new SignfieldInfo(autoExecute, null, fileId, autoExecute == false ? "0" :null, null, 1, commonPosBean, null,sealId); 
					list.add(commonSignfield);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		//生成token
		//generateESignToken(false);
		//downlodDocument("4fd688b55dd34fa3a2d7f57b71f06369");
		
		//searchWordsPosition("da6e50eab1ac44afae6d6e6577f1d02c","楼盘");
		
		//下载文档
		//System.out.println(jsonObject);
		
		//生成token
		generateESignToken(false);
		//System.out.println(queryFlows("7dd26f4703174d64a839fa0f1a0d0cf8"));
		//创建个人账号
//		JSONObject personAcctJson = createPersonAcct("cjg", "陈嘉庚", "CRED_PSN_CH_IDCARD", "350181199508121917", "13124023483", null);
//		JSONObject personAcctDataJson = personAcctJson.getJSONObject("data");
//		String acctId = personAcctDataJson.getString("accountId");
//		
//		//创建机构账号
//		JSONObject orgAcctJson = createOrgAcct("BBBB", acctId, "**********有限公司", null, "52227058XT51M4AL62");
//		String orgId = orgAcctJson.getString("orgId");
//		
//		//通过上传方式创建文件
//		String filePath = "D:/WukongAPI_V2_java/files/test.pdf";
//		String fileName = "劳动合同.pdf";
//		JSONObject uploadJson = createFileByUpload(filePath, fileName, acctId);
//		String uploadUrl = uploadJson.getString("uploadUrl");
//		String fileId = uploadJson.getString("fileId");
//		
//		//文件流上传文件
//		streamUpload(filePath, uploadUrl);
//		
//		//一步发起签署
//		JSONObject flowJson = oneStepFlow(fileId, fileName, acctId,"一步发起","f103deacfea34a1e982a1b2398b3ebab",null,"da1833c9-c723-45e2-8e92-5f7ba1f8c666");
//		String flowId = flowJson.getString("flowId");
//		
//		//签署流程开启
//		startSignFlow(flowId);
//		
		//创建机构印章
		//JSONObject sealJson = createOrgSeal("f103deacfea34a1e982a1b2398b3ebab", "合同专用章(3)", "3501020231259");
		//System.out.println(sealJson);
		/*JSONArray sealJson = queryOrgSeal("f103deacfea34a1e982a1b2398b3ebab");
		for (int i = 0; i < sealJson.size(); i++) {
			JSONObject jsonObject = sealJson.getJSONObject(i);
			if (jsonObject.getString("sealId").equals("c2c95064-2bbd-4d8e-a610-70d58b51b411")) {
				System.out.println(jsonObject.getString("url"));
			}
		}*/
		//System.out.println(sealJson);
		JSONObject jsonObject = queryOrgAccById("f103deacfea34a1e982a1b2398b3ebab");
		System.out.println(jsonObject);
	}
	
}
