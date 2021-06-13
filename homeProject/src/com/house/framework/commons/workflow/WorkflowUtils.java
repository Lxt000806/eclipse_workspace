package com.house.framework.commons.workflow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author HenryYan
 */
public class WorkflowUtils {

	private static Logger logger = LoggerFactory.getLogger(WorkflowUtils.class);

	/**
	 * 转换流程节点类型为中文说明
	 * 
	 * @param type
	 *            英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<String, String>();
		types.put("userTask", "用户任务");
		types.put("serviceTask", "系统任务");
		types.put("startEvent", "开始节点");
		types.put("endEvent", "结束节点");
		types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
		types.put("inclusiveGateway", "并行处理任务");
		types.put("callActivity", "子流程");
		return types.get(type) == null ? type : types.get(type);
	}

	/**
	 * 导出图片文件到硬盘
	 * 
	 * @return 文件的全路径
	 */
	public static String exportDiagramToFile(
			RepositoryService repositoryService,
			ProcessDefinition processDefinition, String exportDir)
			throws IOException {
		String diagramResourceName = processDefinition.getDiagramResourceName();
		String key = processDefinition.getKey();
		int version = processDefinition.getVersion();
		String diagramPath = "";

		InputStream resourceAsStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), diagramResourceName);
		byte[] b = new byte[resourceAsStream.available()];

		@SuppressWarnings("unused")
		int len = -1;
		resourceAsStream.read(b, 0, b.length);

		// create file if not exist
		String diagramDir = exportDir + "/" + key + "/" + version;
		File diagramDirFile = new File(diagramDir);
		if (!diagramDirFile.exists()) {
			diagramDirFile.mkdirs();
		}
		diagramPath = diagramDir + "/" + diagramResourceName;
		File file = new File(diagramPath);

		// 文件存在退出
		if (file.exists()) {
			// 文件大小相同时直接返回否则重新创建文件(可能损坏)
			logger.debug("diagram exist, ignore... : {}", diagramPath);
			return diagramPath;
		} else {
			file.createNewFile();
		}

		logger.debug("export diagram to : {}", diagramPath);

		// wirte bytes to file
		FileUtils.writeByteArrayToFile(file, b, true);
		return diagramPath;
	}

	/**
	 * 解析参数
	 */
	public static Map<String, Object> convertParamMapToFormProp(Map<String, String[]> parameterMap){
		Map<String, Object> formProperties = new HashMap<String, Object>();
		IdentityHashMap<String, Object> tables = new IdentityHashMap<String, Object>();
		Set<String> keys = parameterMap.keySet();
		List<String> keysList = new ArrayList<String>(keys);
		Collections.sort(keysList);
		for(int i = 0; i < keysList.size(); i++){
			if(keysList.get(i).indexOf("fp__") == -1){
				formProperties.put(keysList.get(i), parameterMap.get(keysList.get(i))[0]);
				continue;
			}
			Map<String, Object> datas = new LinkedHashMap<String, Object>();
			int count = 0;
			for(int j = i; j < keysList.size(); j++){
				if(keysList.get(j).lastIndexOf("__") != -1 
					&& keysList.get(i).substring(0, keysList.get(i).lastIndexOf("__")).equals(keysList.get(j).substring(0, keysList.get(j).lastIndexOf("__")))){
					datas.put(keysList.get(j).substring(keysList.get(j).lastIndexOf("__") + 2), parameterMap.get(keysList.get(j))[0]);
					count++;
				}else{
					break;
				}
			}
			i += count - 1;
			String tableInfo = keysList.get(i).substring(4, keysList.get(i).lastIndexOf("__"));
			tables.put(tableInfo.substring(0, tableInfo.lastIndexOf("__")), datas);
		}
		formProperties.put("tables", tables);
		return formProperties;
	}
	
	/**
	 * 根据表名，参数自动生成insert的sql语句
	 */
	public static String autoProduceSql(String tableName, Map<String, Object> data){
		boolean isNull = false;
		String values = "";
		String params = "";
		for(Entry<String, Object> entry : data.entrySet()){
			if(!"PK".equals(entry.getKey())){
				if(StringUtils.isNotBlank(values)){
					values += ",";
					params += ",";
				}
				if(!"".equals(entry.getValue().toString()) && !"wfprocinstno".equals(entry.getKey().toLowerCase())){//当values有一个不为空时 设置变量isNull 为false
					isNull = true;
				}
				if(StringUtils.isNotBlank(entry.getValue().toString())){
					values += "'"+entry.getValue()+"'";
				} else {
					values = values +"null";
				}
				params += entry.getKey();
				}
			}
		if(isNull){//有一个值不为空才保存
			return "insert into "+tableName+"("+params+") values("+values+")";
		} else {
			return null;
		}
	}
	
	public static String getUpdateCust_tableSql(String wfProcInstNo,Map<String, Object> map){
		String sql="";
		sql = "update "+map.get("table").toString()+" set ";
		for (String key : map.keySet()){
			if(!"table".equals(key)){
				sql = sql+key+" = '" +map.get(key).toString()+"',";
			}
		}
		sql = sql.substring(0,sql.length() - 1);
		sql += " where WfprocInstNo = '"+ wfProcInstNo+"'";
		return sql;
	}
	
	public static String getUpdateCust_tableByPkSql(String wfProcInstNo,Map<String, Object> map){
		String sql="";
		sql = "update "+map.get("table").toString()+" set ";
		for (String key : map.keySet()){
			if(!"table".equals(key)){
				sql = sql+key+" = '" +map.get(key).toString()+"',";
			}
		}
		sql = sql.substring(0,sql.length() - 1);
		sql += " where Pk = '"+ wfProcInstNo+"'";
		return sql;
	}
}
