package com.house.home.web.controller.basic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.SaleCust;
import com.house.home.service.basic.SaleCustService;

@Controller
@RequestMapping("/admin/saleCust")
public class SaleCustController extends BaseController{
    
	@Autowired
	private SaleCustService saleCustService;
	
	/**
	 *	表格数据 
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public  WebPage<Map<String , Object>> getJqGrid(HttpServletRequest request, 
			HttpServletResponse response,SaleCust saleCust) throws Exception{
		
		Page<Map<String, Object>> page=this.newPageForJqGrid(request);
		
		saleCustService.findPageBySql(page, saleCust);
		
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * 根据id查询Purchase详细信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSaleCust")
	@ResponseBody
	public JSONObject getPurchase(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		SaleCust saleCust= saleCustService.get(SaleCust.class, id);
		if(saleCust == null){
			return this.out("系统中不存在code="+id+"的材料信息", false);
		}
		return this.out(saleCust, true);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response) throws Exception{
		
		return new ModelAndView("admin/basic/saleCust/salecust_list");
	}
	
	
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response, SaleCust saleCust)throws Exception{
		
		return new ModelAndView("admin/basic/saleCust/salecust_code").addObject("saleCust",saleCust);
	}
	
	
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response,String id){
		SaleCust saleCust=null;
		try{
    		if(StringUtils.isNotBlank(id)){
    		    saleCust = saleCustService.get(SaleCust.class,id);
    		    SimpleDateFormat dateFormatter = new SimpleDateFormat();
                if(saleCust.getRemDate1() != null && saleCust.getRemDate1().trim().length() != 0){
                    dateFormatter.applyPattern("yyyyMMdd");
                    Date remDate1 = dateFormatter.parse(saleCust.getRemDate1());
                    dateFormatter.applyPattern("yyyy-MM-dd");
                    saleCust.setRemDate1(dateFormatter.format(remDate1));
                }
                
                if(saleCust.getRemDate2() != null && saleCust.getRemDate2().trim().length() != 0){
                    dateFormatter.applyPattern("yyyyMMdd");
                    Date remDate2 = dateFormatter.parse(saleCust.getRemDate2());
                    dateFormatter.applyPattern("yyyy-MM-dd");
                    saleCust.setRemDate2(dateFormatter.format(remDate2));
                }
    		
    		}else{
    			saleCust = new SaleCust();
    		}
		}catch(ParseException e){
            e.printStackTrace();
        }
		
		return new ModelAndView("admin/basic/saleCust/salecust_save").addObject("saleCust",saleCust);
	}
	
	
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,HttpServletResponse response, String id){
		SaleCust saleCust= null;
		try {
    		if(StringUtils.isNotBlank(id)){
    			saleCust = saleCustService.get(SaleCust.class,id);
    			SimpleDateFormat dateFormatter = new SimpleDateFormat();
    			if(saleCust.getRemDate1() != null && saleCust.getRemDate1().trim().length() != 0){
    			    dateFormatter.applyPattern("yyyyMMdd");
                    Date remDate1 = dateFormatter.parse(saleCust.getRemDate1());
                    dateFormatter.applyPattern("yyyy-MM-dd");
                    saleCust.setRemDate1(dateFormatter.format(remDate1));
    			}
    			
    			if(saleCust.getRemDate2() != null && saleCust.getRemDate2().trim().length() != 0){
                    dateFormatter.applyPattern("yyyyMMdd");
                    Date remDate2 = dateFormatter.parse(saleCust.getRemDate2());
                    dateFormatter.applyPattern("yyyy-MM-dd");
                    saleCust.setRemDate2(dateFormatter.format(remDate2));
                }
    		}else{
    			saleCust = new SaleCust();
    		}
		} catch (ParseException e) {
            e.printStackTrace();
        }
		return new ModelAndView("admin/basic/saleCust/salecust_update").addObject("saleCust", saleCust);
	}
	
	
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,HttpServletResponse response, String id){
		SaleCust saleCust =saleCustService.get(SaleCust.class,id);
		try{
    		SimpleDateFormat dateFormatter = new SimpleDateFormat();
            if(saleCust.getRemDate1() != null && saleCust.getRemDate1().trim().length() != 0){
                dateFormatter.applyPattern("yyyyMMdd");
                Date remDate1 = dateFormatter.parse(saleCust.getRemDate1());
                dateFormatter.applyPattern("yyyy-MM-dd");
                saleCust.setRemDate1(dateFormatter.format(remDate1));
            }
            
            if(saleCust.getRemDate2() != null && saleCust.getRemDate2().trim().length() != 0){
                dateFormatter.applyPattern("yyyyMMdd");
                Date remDate2 = dateFormatter.parse(saleCust.getRemDate2());
                dateFormatter.applyPattern("yyyy-MM-dd");
                saleCust.setRemDate2(dateFormatter.format(remDate2));
            }
		}catch(ParseException e){
		    e.printStackTrace();
		}
		return new ModelAndView("admin/basic/saleCust/salecust_detail").addObject("saleCust",saleCust);
	}
	
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, SaleCust saleCust){
		try{
		    SimpleDateFormat dateFormatter = new SimpleDateFormat();
            
            if(saleCust.getRemDate1() != null && saleCust.getRemDate1().trim().length() != 0){
                dateFormatter.applyPattern("yyyy-MM-dd");
                Date remDate1 = dateFormatter.parse(saleCust.getRemDate1());
                dateFormatter.applyPattern("yyyyMMdd");
                saleCust.setRemDate1(dateFormatter.format(remDate1));
            }
            
            if(saleCust.getRemDate2() != null && saleCust.getRemDate2().trim().length() != 0){
                dateFormatter.applyPattern("yyyy-MM-dd");
                Date remDate2 = dateFormatter.parse(saleCust.getRemDate2());
                dateFormatter.applyPattern("yyyyMMdd");
                saleCust.setRemDate2(dateFormatter.format(remDate2));
            }
            
			String str = saleCustService.getSeqNo("tSaleCust");
			saleCust.setCode(str);
			saleCust.setLastUpdate(new Date());
			saleCust.setExpired("F");
			saleCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			saleCust.setActionLog("ADD");
			this.saleCustService.save(saleCust);
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response, "添加销售客户信息失败");
		}
	}
	
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, SaleCust saleCust){
		try {
		    SimpleDateFormat dateFormatter = new SimpleDateFormat();
		    
		    if(saleCust.getRemDate1() != null && saleCust.getRemDate1().trim().length() != 0){
		        dateFormatter.applyPattern("yyyy-MM-dd");
		        Date remDate1 = dateFormatter.parse(saleCust.getRemDate1());
		        dateFormatter.applyPattern("yyyyMMdd");
		        saleCust.setRemDate1(dateFormatter.format(remDate1));
		    }
		    
		    if(saleCust.getRemDate2() != null && saleCust.getRemDate2().trim().length() != 0){
		        dateFormatter.applyPattern("yyyy-MM-dd");
                Date remDate2 = dateFormatter.parse(saleCust.getRemDate2());
                dateFormatter.applyPattern("yyyyMMdd");
                saleCust.setRemDate2(dateFormatter.format(remDate2));
            }
		    
			saleCust.setLastUpdate(new Date());
			saleCust.setLastUpdatedBy(getUserContext(request).getCzybh());
			saleCust.setActionLog("EDIT");
			this.saleCustService.update(saleCust);
			ServletUtils.outPrintSuccess(request, response);
		} catch (Exception e) {
		    e.printStackTrace();
			ServletUtils.outPrintFail(request, response,"修改销售客户信息失败");
		}
	}

	
	/**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, SaleCust saleCust) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        saleCustService.findPageBySql(page, saleCust);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "销售客户信息_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
	
	public void getDoc(String docName,String dbName,String className) throws IOException{
		//String docName="project";//basic/design/insales/project...
		//String dbName="tprjProg";//
		//String className="PrjProg";
		String pathEntity = "D:\\src\\com\\house\\home\\entity\\"+docName+"\\"+className+".java";
        String pathDao = "D:\\src\\com\\house\\home\\dao\\"+docName+"\\"+className+"Dao.java";
        String pathService = "D:\\src\\com\\house\\home\\service\\"+docName+"\\"+className+"Service.java";
        String pathServiceImpl = "D:\\src\\com\\house\\home\\serviceImpl\\"+docName+"\\"+className+"ServiceImpl.java";
        String pathController = "D:\\src\\com\\house\\home\\web\\controller\\"+docName+"\\"+className+"Controller.java";
		File fileEntity=new File(pathEntity);
		File fileDao=new File(pathDao);
		File fileService=new File(pathService);
		File fileServiceImpl=new File(pathServiceImpl);
		File fileController=new File(pathController);
		//判断文件是否存在
		if(!fileEntity.exists()){
			fileEntity.getParentFile().mkdirs();          
        }
		fileEntity.createNewFile();
		if(!fileDao.exists()){
			fileDao.getParentFile().mkdirs();          
        }
		fileDao.createNewFile();
		if(!fileService.exists()){
			fileService.getParentFile().mkdirs();          
        }
		fileService.createNewFile();
		if(!fileServiceImpl.exists()){
			fileServiceImpl.getParentFile().mkdirs();          
        }
		fileServiceImpl.createNewFile();
		if(!fileController.exists()){
			fileController.getParentFile().mkdirs();          
        }
		fileController.createNewFile();
		
		FileWriter fwEntity = new FileWriter(fileEntity, true);
		FileWriter fwDao = new FileWriter(fileDao, true);
		FileWriter fwService = new FileWriter(fileService, true);
		FileWriter fwServiceImpl = new FileWriter(fileServiceImpl, true);
		FileWriter fwController = new FileWriter(fileController, true);
        BufferedWriter bwEntity = new BufferedWriter(fwEntity);
        BufferedWriter bwDao = new BufferedWriter(fwDao);
        BufferedWriter bwService = new BufferedWriter(fwService);
        BufferedWriter bwServiceImpl = new BufferedWriter(fwServiceImpl);
        BufferedWriter bwController = new BufferedWriter(fwController);
       //entity
        String EntityImpore="package com.house.home.entity."+docName+";\r\nimport java.util.Date;\r\nimport javax.persistence.Column;\r\nimport javax.persistence.Entity;\r\nimport javax.persistence.Id;\r\nimport javax.persistence.Table;\r\n";
        EntityImpore+="import com.house.framework.commons.orm.BaseEntity;\r\n";
        EntityImpore+="@Entity\r\n@Table(name = \""+dbName+"\")\r\n";
        EntityImpore+="public class "+className+" extends BaseEntity{\r\n\r\n	private static final long serialVersionUID = 1L;\r\n@Id\r\n";

    	List<Map<String , Object>>list=saleCustService.getDBcol( dbName);
		if(list!=null){
	    	for(int i=0;i<list.size();i++){
				EntityImpore+="	@Column(name = \""+list.get(i).get("ColName")+"\")\r\n";
				if("nchar".equals(list.get(i).get("ColType").toString())||"nvarchar".equals(list.get(i).get("ColType").toString())){
					EntityImpore+="	private String "+(new StringBuilder()).append(Character.toLowerCase(list.get(i).get("ColName").toString().charAt(0))).append(list.get(i).get("ColName").toString().substring(1)).toString()+";\r\n";
				}else if("datetime".equals(list.get(i).get("ColType"))){
					EntityImpore+="	private Date "+(new StringBuilder()).append(Character.toLowerCase(list.get(i).get("ColName").toString().charAt(0))).append(list.get(i).get("ColName").toString().substring(1)).toString()+";\r\n";
				}else if("int".equals(list.get(i).get("ColType"))){
					EntityImpore+="	private Integer "+(new StringBuilder()).append(Character.toLowerCase(list.get(i).get("ColName").toString().charAt(0))).append(list.get(i).get("ColName").toString().substring(1)).toString()+";\r\n";
				}else if("money".equals(list.get(i).get("ColType"))){
					EntityImpore+="	private Double "+(new StringBuilder()).append(Character.toLowerCase(list.get(i).get("ColName").toString().charAt(0))).append(list.get(i).get("ColName").toString().substring(1)).toString()+";\r\n";
				}else{
					EntityImpore+="	private String "+(new StringBuilder()).append(Character.toLowerCase(list.get(i).get("ColName").toString().charAt(0))).append(list.get(i).get("ColName").toString().substring(1)).toString()+";\r\n";
				}
	    	}
		}
		
		/*if("LastUpdate".equals(key)){
			EntityImpore+="	@Column(name = \""+key+"\")\r\n";
			EntityImpore+="	private Date "+(new StringBuilder()).append(Character.toLowerCase(key.charAt(0))).append(key.substring(1)).toString()+";\r\n";
		}else{
			EntityImpore+="	@Column(name = \""+key+"\")\r\n";
			EntityImpore+="	private String "+(new StringBuilder()).append(Character.toLowerCase(key.charAt(0))).append(key.substring(1)).toString()+";\r\n";
		}*/
		
		
		
        EntityImpore+="\r\n\r\n}";
        //dao
        String daoImpore="package com.house.home.dao."+docName+";\r\nimport com.house.framework.commons.orm.BaseDao;"+"\r\nimport org.springframework.stereotype.Repository;\r\n"+""+
        		"@SuppressWarnings(\"serial\")\r\n@Repository\r\n" +
        		"public class "+className+"Dao extends BaseDao{\r\n\r\n\r\n\r\n}";
        //service
        String serviceImpore="package com.house.home.service."+docName+";\r\n\r\nimport com.house.framework.commons.orm.BaseService;"+"\r\n\r\n"+"public interface "+className+"Service extends BaseService{\r\n\r\n}";
        //impl
        String implImpore="package com.house.home.serviceImpl."+docName+";\r\n\r\n";
        	implImpore+="import com.house.framework.commons.orm.BaseServiceImpl;\r\nimport org.springframework.stereotype.Service;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport com.house.home.dao."+docName+"."+className+"Dao;";
        	implImpore+="\r\nimport com.house.home.service."+docName+"."+className+"Service;\r\n\r\n";
        	implImpore+="@SuppressWarnings(\"serial\")\r\n@Service \r\n";
        	implImpore+="public class "+className+"ServiceImpl extends BaseServiceImpl implements "+className+"Service {\r\n";
        	implImpore+="	@Autowired\r\n	private  "+className+"Dao "+ (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString()+"Dao;";
        	implImpore+="\r\n\r\n}";
        //controller
        String controllerImpore="package com.house.home.web.controller."+docName+";\r\n\r\n";
	    	controllerImpore+="import com.house.framework.web.controller.BaseController;\r\n\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.stereotype.Controller;\r\nimport com.house.home.service."+docName+"."+className+"Service;\r\nimport org.springframework.web.bind.annotation.RequestMapping;";
	    	controllerImpore+="\r\n@Controller\r\n@RequestMapping(\"/admin/"+(new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString()+"\")\r\n";
	    	controllerImpore+="public class "+className+"Controller extends BaseController{\r\n";
	    	controllerImpore+="	@Autowired\r\n	private  "+className+"Service "+ (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString()+"Service;";
	    	controllerImpore+="\r\n\r\n}";
	    	
	    bwEntity.write(EntityImpore);
        bwEntity.flush();
        bwEntity.close();
        fwEntity.close();
        
        bwDao.write(daoImpore);
        bwDao.flush();
        bwDao.close();
        fwDao.close();
        
        bwService.write(serviceImpore);
        bwService.flush();
        bwService.close();
        fwService.close();
        
        bwServiceImpl.write(implImpore);
        bwServiceImpl.flush();
        bwServiceImpl.close();
        fwServiceImpl.close();
        
        bwController.write(controllerImpore);
        bwController.flush();
        bwController.close();
        fwController.close();
	}

	
}	
	
	
	


	
	

	
