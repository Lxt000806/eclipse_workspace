package com.house.home.serviceImpl.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.interceptor.DefaultThreadFactory;
import com.house.home.dao.basic.ExcelTaskDao;
import com.house.home.dao.design.ResrCustDao;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.ExcelTask;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustExcelFailed;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.service.basic.ExcelTaskService;

@SuppressWarnings("serial")
@Service
public class ExcelTaskServiceImpl extends BaseServiceImpl implements ExcelTaskService,InitializingBean {

	private ExecutorService executor;
	private ArrayBlockingQueue<List<?>> queue = new ArrayBlockingQueue<List<?>>(2 << 10);
	private ExcelTask excelTask=null;
	
	@Autowired
	private ExcelTaskDao excelTaskDao;
	@Autowired
	private ResrCustDao resrCustDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ExcelTask excelTask){
		return excelTaskDao.findPageBySql(page, excelTask);
	}
	
	@Override
	public void addExcelList(List<?> list, ExcelTask excelTask) {
		queue.add(list);
		this.excelTask=excelTask;
	}
	@Override
	public Integer getMaxExcelTaskPk() {
		return excelTaskDao.getMaxExcelTaskPk();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// CPU 核心数
		int coreCpuNum = Runtime.getRuntime().availableProcessors();
		executor = Executors.newFixedThreadPool(coreCpuNum,new DefaultThreadFactory("importResrCust"));
		new Thread(new Runnable(){
			public void run() {
				while (true) {
					try {
						//System.out.println("进入线程");
						List<?> list=null;
						list = queue.poll(1L, TimeUnit.SECONDS);
						exeByUrl(list);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		}).start();
	}
	
	/**
	 * 根据路径调用线程
	 * 目前只有资源客户管理模块，有新加的，加个else if，写一个新的线程类和保存类。
	 * @param list
	 */
	public void exeByUrl(List<?> list){
		if (list != null) {
			if(excelTask.getUrl().equals("/admin/ResrCust/loadExcel")){
				List<ResrCust>resrCustList=new ArrayList<ResrCust>();
				for (int i = 0; i < list.size(); i++) {
			        resrCustList.add((ResrCust) list.get(i));
				}
				executor.execute(new ResrCustTask(resrCustList));
			}/*else if(){
				
			}*/
		}
		
	}
	
	/**
	 * 资源客户管理线程类
	 * @author cjg
	 *
	 */
	class ResrCustTask implements Runnable{
		ExcelTask excelTask=new ExcelTask();
		private List<ResrCust> resrCustList = null;
	
		public ResrCustTask(List<ResrCust> resrCustList){
			this.resrCustList = resrCustList;
		}
		
		@Override
		public void run() {
			_saveResrCust(resrCustList);
		}
		
	}
	
	/**
	 * 资源客户管理保存
	 * @param resrCustList
	 */
	public void _saveResrCust(List<ResrCust> resrCustList){
		ResrCust resrCust=new ResrCust();
		Employee crtCzy=get(Employee.class,excelTask.getLastUpdatedBy());//创建人实体
		Integer failedNum=0;//失败数量
		Integer successNum=0;//成功数量
		List<ResrCust> validResrCustList=new ArrayList<ResrCust>();
		
		List<Map<String,Object>> netChannels = resrCustDao.findAllNetChannels();
		
		for (int i = 0; i < resrCustList.size(); i++) {
		    
		    String lackFields = "";
		    //导入失败实体构造通用初始值
		    ResrCustExcelFailed resrCustExcelFailed = new ResrCustExcelFailed(this.excelTask.getPk());
		    //循环取资源客户实体
		    resrCust=resrCustList.get(i);
			resrCust.setStatus("1");
		    try {
		        
                // 判断是否缺少必填字段:楼盘，手机号
                if (StringUtils.isBlank(resrCust.getAddress())) {
                    lackFields += "楼盘地址 ";
                }

                if (StringUtils.isBlank(resrCust.getMobile1())) {
                    lackFields += "手机号 ";
                }  
                
			    if(StringUtils.isNotBlank(lackFields)){
			        resrCustExcelFailed.setFileRows(i+2);//失败行数=集合索引+2
			        resrCustExcelFailed.setFailedReason("缺少必填字段："+lackFields);
			        resrCustDao.save(resrCustExcelFailed);
			        failedNum++;
			        continue;
			    }
			
    			//面积不为空，判断是否数字,包含小数点
    			if(StringUtils.isNotBlank(resrCust.getAreaForExcel())){
    				if(!com.house.framework.commons.utils.StringUtils.isNumeric(resrCust.getAreaForExcel())){
    					resrCustExcelFailed.setFileRows(i+2);
    					resrCustExcelFailed.setFailedReason("面积必须为纯数字");
    					resrCustExcelFailed.setFailedFieldContent(resrCust.getAreaForExcel());
    					resrCustExcelFailed.setFailedFieldName("面积");
    					resrCustDao.save(resrCustExcelFailed);
    					failedNum++;
    					continue;
    				}else if(resrCust.getAreaForExcel().indexOf(".")!=-1){//带小数，四舍五入
    					resrCust.setArea(Math.round(Float.parseFloat(resrCust.getAreaForExcel())));
    				}else{//整数
    					resrCust.setArea(Integer.parseInt(resrCust.getAreaForExcel()));
    				}
    			}
    			//判断手机号是否数字
    			if(!StringUtils.isNumeric(resrCust.getMobile1())){
    				resrCustExcelFailed.setFileRows(i+2);
    				resrCustExcelFailed.setFailedReason("手机号必须为纯数字");
    				resrCustExcelFailed.setFailedFieldContent(resrCust.getMobile1());
    				resrCustExcelFailed.setFailedFieldName("手机号");
    				resrCustDao.save(resrCustExcelFailed);
    				failedNum++;
    				continue;
    			}
    			//判断手机号是否重复  
    			Map<String, Object> repeatMap=resrCustDao.findExsitsCust(resrCust,this.excelTask.getLastUpdatedBy());
    			if(null!=repeatMap){
    				resrCustExcelFailed.setFileRows(i+2);
    				resrCustExcelFailed.setFailedReason("本部门存在重复客户");
    				resrCustExcelFailed.setExistsCustName(repeatMap.get("Descr")==null?"":repeatMap.get("Descr").toString());
    				resrCustExcelFailed.setExistsBusinessMan(repeatMap.get("BusinessMan")==null?"":repeatMap.get("BusinessMan").toString());
    				resrCustExcelFailed.setFailedFieldName("手机号");
    				resrCustExcelFailed.setFailedFieldContent(resrCust.getMobile1());
    				resrCustDao.save(resrCustExcelFailed);
    				failedNum++;
    				continue;
    			}
    			
    			if (StringUtils.isNotBlank(resrCust.getBuilderNum())
    			        && resrCust.getBuilderNum().length() > 20) {
    			    
    			    resrCustExcelFailed.setFileRows(i + 2);
                    resrCustExcelFailed.setFailedReason("楼栋号不能超过20个字符");
                    resrCustExcelFailed.setFailedFieldName("楼栋号");
                    resrCustExcelFailed.setFailedFieldContent(resrCust.getBuilderNum());
                    resrCustDao.save(resrCustExcelFailed);
                    
                    failedNum++;
                    continue;
                }
    			
    			// 设置网络渠道，将 中文名字转换为对应编码
    			// 张海洋 20200803
    			if (StringUtils.isNotBlank(resrCust.getNetChanel())) {
    			    String originalValue = resrCust.getNetChanel();
    			    
                    for (Map<String, Object> netChannel : netChannels) {
                        if (netChannel.get("Descr").equals(resrCust.getNetChanel())) {
                            resrCust.setNetChanel((String) netChannel.get("Code"));
                            resrCust.setSource("3");
                            continue;
                        }
                    }
                    
                    if (originalValue.equals(resrCust.getNetChanel())) {
                        resrCustExcelFailed.setFileRows(i + 2);
                        resrCustExcelFailed.setFailedReason("\"" + resrCust.getNetChanel() + "\" 渠道暂不支持");
                        resrCustExcelFailed.setFailedFieldName("网络渠道");
                        resrCustExcelFailed.setFailedFieldContent(resrCust.getNetChanel());
                        resrCustDao.save(resrCustExcelFailed);
                        
                        failedNum++;
                        continue;
                    }
                }
    			
    			//无异常，导入给自己或导入到公海的，直接保存；导入给同事的，添加到有效集合中根据同事人数平均分配；设置通用默认值
    			resrCust.setCrtDate(new Date());
    			resrCust.setLastUpdate(resrCust.getCrtDate());
    			resrCust.setExpired("F");
    			resrCust.setActionLog("ADD");
    			resrCust.setLastUpdatedBy(excelTask.getLastUpdatedBy());
    			resrCust.setCustResStat("1");
    			resrCust.setCrtCzy(excelTask.getLastUpdatedBy());
    			resrCust.setCrtCZYDept(crtCzy.getDepartment());
    			resrCust.setLayout("0");
    			resrCust.setConstructType("1");
    			resrCust.setNoValidCount(0);
    			resrCust.setValidDispatchCount(0);
    			resrCust.setPublicResrLevel("0");
    			if (StringUtils.isBlank(resrCust.getSource())) {                
    			    resrCust.setSource("1");
                }
    			
    			resrCust.setCustKind("0");
    			resrCust.setStatus("0");
    			resrCust.setAddrProperty("1");
    			//名称转代码 :性别，项目名称
    			if(StringUtils.isNotBlank(resrCust.getGender())){
    				if("男".equals(resrCust.getGender().trim())){
    					resrCust.setGender("M");
    				}else if("女".equals(resrCust.getGender().trim())){
    					resrCust.setGender("F");
    				}
    			}
    			if(StringUtils.isNotBlank(resrCust.getBuilderCode())){
    				String builderCode=resrCustDao.getBuilderCode(resrCust.getBuilderCode());
    				resrCust.setBuilderCode(builderCode);
    			}else{
    			    resrCust.setBuilderCode("");
    			}
    			if("1".equals(excelTask.getImportPlan()) || "2".equals(excelTask.getImportPlan())){
    				
    				//导入给自己，要设置跟单人和派单时间
    				if("1".equals(excelTask.getImportPlan())){
    					resrCust.setBusinessMan(excelTask.getEmps());
    					resrCust.setDispatchDate(resrCust.getCrtDate());
    					
    					//设置所属部门，所属部门大于二级时取其二级部门，公海等级设置为个人
    					Employee businessMan=resrCustDao.get(Employee.class,resrCust.getBusinessMan());
    					if(StringUtils.isNotBlank(businessMan.getDepartment3())){
    						Department2 department2=resrCustDao.get(Department2.class,businessMan.getDepartment2());
    						resrCust.setDepartment(department2.getDepartment());
    					}else{
    						resrCust.setDepartment(businessMan.getDepartment());
    					}
    					resrCust.setPublicResrLevel("0");
    				}else {
    					resrCust.setBusinessMan("");
    					resrCust.setPublicResrLevel("1");
    				}
    				
    				if(StringUtils.isBlank(excelTask.getResrCustPoolNo())){
                        resrCustExcelFailed.setFileRows(i + 2);
                        resrCustExcelFailed.setFailedReason("没有线索池，导入失败");
                        resrCustExcelFailed.setFailedFieldName("");
                        resrCustExcelFailed.setFailedFieldContent(resrCust.getNetChanel());
                        resrCustDao.save(resrCustExcelFailed);
                        
                        failedNum++;
                        continue;
    				} else {
    					resrCust.setResrCustPoolNo(excelTask.getResrCustPoolNo());
    				}
    				//获取资源客户编号
    				String resrCustCode = getSeqNo("tResrCust");
    				resrCust.setCode(resrCustCode);
    				resrCustDao.save(resrCust);
    				
    			}else{
    				resrCust.setResrCustPoolNo(excelTask.getResrCustPoolNo());
    				validResrCustList.add(resrCust);
    			}
    			
                successNum++;
			
            } catch (Exception e) {
                e.printStackTrace();
                
                resrCustExcelFailed.setFileRows(i + 2);
                resrCustExcelFailed.setFailedReason("程序异常");
                resrCustDao.save(resrCustExcelFailed);
                
                failedNum++;
                continue;
            }
		    
		// end of for
		}
		
		//导入给同事，保存资源客户信息
		if("3".equals(excelTask.getImportPlan())){
			String emps[]=excelTask.getEmps().split(",");//拆分同事数组
			int empsNum=emps.length;//同事数量
			int validResrCustNum=validResrCustList.size();//资源客户总数
			int avgNum=validResrCustNum/empsNum;//平均每人分得资源客户个数
			int leftNum=validResrCustNum%empsNum;//平均分完的余数
			int j=0;
			//平均数大于0，先平均分
			if(avgNum>0){
				for(int k=0;k<empsNum;k++){
					for (; j < avgNum; j++) {
						resrCust=validResrCustList.get(j);
						resrCust.setBusinessMan(emps[k]);
						resrCust.setDispatchDate(resrCust.getCrtDate());
						String resrCustCode = getSeqNo("tResrCust");
						resrCust.setCode(resrCustCode);
						
						//设置所属部门，所属部门大于二级时取其二级部门，公海等级设置为个人
						Employee businessMan=resrCustDao.get(Employee.class,resrCust.getBusinessMan());
						if(StringUtils.isNotBlank(businessMan.getDepartment3())){
							Department2 department2=resrCustDao.get(Department2.class,businessMan.getDepartment2());
							resrCust.setDepartment(department2.getDepartment());
						}else{
							resrCust.setDepartment(businessMan.getDepartment());
						}
						resrCust.setPublicResrLevel("0");
						
						resrCustDao.save(validResrCustList.get(j));
					}
					avgNum+=validResrCustNum/empsNum;//分配完一个同事，往后加一个平均数的值，分配给下一个员工
				}
			}
			//把余数分给前几个人
			for (int l = 0; l < leftNum; l++) {
				resrCust=validResrCustList.get(j+l);
				resrCust.setBusinessMan(emps[l]);
				resrCust.setDispatchDate(resrCust.getCrtDate());
				String resrCustCode = getSeqNo("tResrCust");
				resrCust.setCode(resrCustCode);
				
				//设置所属部门，所属部门大于二级时取其二级部门，公海等级设置为个人
				Employee businessMan=resrCustDao.get(Employee.class,resrCust.getBusinessMan());
				if(StringUtils.isNotBlank(businessMan.getDepartment3())){
					Department2 department2=resrCustDao.get(Department2.class,businessMan.getDepartment2());
					resrCust.setDepartment(department2.getDepartment());
				}else{
					resrCust.setDepartment(businessMan.getDepartment());
				}
				resrCust.setPublicResrLevel("0");
				
				resrCustDao.save(resrCust);
			}
		}
		
		//结束时更新任务状态为已完成
		excelTask.setStatus("2");
		excelTask.setResult("<font color='orange'>"+successNum+"</font>个成功，<font color='red'>"+failedNum+"</font>个失败");
		excelTaskDao.update(excelTask);
	}
}
