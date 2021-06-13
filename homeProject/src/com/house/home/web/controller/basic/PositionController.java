package com.house.home.web.controller.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.cache.ICacheManager;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Position;
import com.house.home.entity.insales.WareHouse;
import com.house.home.service.basic.PositionService;

@Controller
@RequestMapping("/admin/position")
public class PositionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;
    @Resource(name = "positionCacheManager")
    private ICacheManager positionCacheManager;
    
    /**
     * 查询JqGrid表格数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/goJqGrid")
    @ResponseBody
    public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
            HttpServletResponse response,Position position) throws Exception {

        Page<Map<String,Object>> page = this.newPageForJqGrid(request);
        positionService.findPageBySql(page, position);
        return new WebPage<Map<String,Object>>(page);
    }
    /**
     * 员工列表
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/goList")
    public ModelAndView goList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return new ModelAndView("admin/basic/position/position_list");
    }
    
    /**
     * 跳转到新增员工页面
     * @return
     */
    @RequestMapping("/goSave")
    public ModelAndView goSave(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(required = false) String id){
        
        Position position = null;
        if(id == null){
            position = new Position();
        }else{
            position = positionService.get(Position.class, id);
        }
        if(position == null){
            position = new Position();
        }
        
        return new ModelAndView("admin/basic/position/position_save")
            .addObject("position", position);
    }
    /**
     * 跳转到修改员工页面
     * @return
     */
    @RequestMapping("/goUpdate")
    public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
            String id){
        logger.debug("跳转到修改员工页面");
        Position position = null;
        if (StringUtils.isNotBlank(id)){
            position = positionService.get(Position.class, id);
        }else{
            position = new Position();
        }
        
        return new ModelAndView("admin/basic/position/position_update")
            .addObject("position", position);
    }
    
    /**
     * 跳转到查看员工页面
     * @return
     */
    @RequestMapping("/goDetail")
    public ModelAndView goDetail(HttpServletRequest request,
                                 HttpServletResponse response, 
                                 String id){
        
        Position position = positionService.get(Position.class, id);
        
        return new ModelAndView("admin/basic/position/position_detail")
                .addObject("position", position);
    }
    /**
     * 添加职位信息
     * @param request
     * @param response
     * @param role
     */
    @RequestMapping("/doSave")
    public void doSave(HttpServletRequest request, HttpServletResponse response, Position position){
        try{
            position.setLastUpdate(new Date());
            position.setLastUpdatedBy(getUserContext(request).getCzybh());
            position.setActionLog("ADD");
            position.setExpired("F");
            positionService.doSave(position);
            positionCacheManager.refresh();
            ServletUtils.outPrintSuccess(request, response);
        }catch(Exception e){
            e.printStackTrace();
            ServletUtils.outPrintFail(request, response, "添加职位信息失败，请重试！");
        }
    }
    
    /**
     * 修改职位
     * @param request
     * @param response
     * @param role
     */
    @RequestMapping("/doUpdate")
    public void doUpdate(HttpServletRequest request,
                         HttpServletResponse response, 
                         Position position){
        try{
            if (!positionService.codeIsRepeative(position.getCode())){//职位编号不存在
                ServletUtils.outPrintFail(request, response, "该职位不存在！");
                return;
            }
            position.setLastUpdate(new Date());
            position.setLastUpdatedBy(getUserContext(request).getCzybh());
            position.setActionLog("EDIT");
            positionService.update(position);
            positionCacheManager.refresh();
            ServletUtils.outPrintSuccess(request, response);
        }catch(Exception e){
            ServletUtils.outPrintFail(request, response, "修改职位信息失败，请重试");
        }
    }
    
    /**
     * 删除员工
     * @param request
     * @param response
     * @param roleId
     */
    @RequestMapping("/doDelete")
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response, 
                         String deleteIds){
        if(StringUtils.isBlank(deleteIds)){
            ServletUtils.outPrintFail(request, response, "职位编号不能为空,删除失败");
            return;
        }
        List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
        for(String deleteId : deleteIdList){
            if(deleteId != null){
                Position position = this.positionService.get(Position.class, deleteId);
                if(position == null)
                    continue;
                this.positionService.delete(position);
            }
        }
        positionCacheManager.refresh();
        ServletUtils.outPrintSuccess(request, response,"删除成功");
    }
    
    /**
     * 导出到Excel
     */
    @RequestMapping("/doExcel")
    public void doExcel(HttpServletRequest req, HttpServletResponse resp, Position position) {
        Page<Map<String, Object>> page = this.newPage(req);
        page.setPageSize(-1);
        positionService.findPageBySql(page, position);
        getExcelList(req);
        ServletUtils.flushExcelOutputStream(req, resp,
                page.getResult(),
                "职位信息_" + DateUtil.DateToString(new Date(), "yyyyMMdd"),
                columnList, titleList, sumList);
    }
    
    @RequestMapping("/changePosition")
    public void changeDepartment1s(HttpServletRequest request,HttpServletResponse response,String code) {
        logger.debug("异步加载二级部门");
        String virtualRootLabel = "请选择";
        String virtualRootId = "_VIRTUAL_RO0T_ID_";
/*      String sqlValueKey = "code";
        String sqlLableKey = "desc1";*/
        String sqlValueKey = "code";
        String sqlLableKey = "name";
        String retStr = "[]";
        if (StringUtils.isBlank(code)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("strSelect", retStr);
            try {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(jsonObject.toJSONString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
/*      String sql = "select a.code,a.desc1 "
                +"from tDepartment2 a inner join (select * from fStrToTable('"+code+"',',')) t on a.Department1=t.item "
                +"where a.Expired='F' order by a.Department1";*/
        String sql = "select a.code,a.desc2 from tposition";
        List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<String, Object>();
        item.put("id", virtualRootId);
        item.put("pId", "");
        item.put("name", virtualRootLabel);
        item.put("isParent", true);
        item.put("open", true);
        rsList.add(item);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringContextHolder.getApplicationContext().getBean("jdbcTemplate");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            for(Map<String, Object> map : list){
                
                item = new HashMap<String, Object>();
                item.put("id", map.get(sqlValueKey));
                item.put("pId", virtualRootId);
                item.put("name", map.get(sqlValueKey)+" "+map.get(sqlLableKey)+" "+positionService.getDepLeader(map.get(sqlValueKey).toString()));
                rsList.add(item);
            }
        }
        retStr = JSON.toJSONString(rsList);
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("strSelect", retStr);
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    @RequestMapping("/goCode")
    public ModelAndView goCode(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ModelAndView("admin/basic/position/position_code");
    }
    /**
	 * 根据ID查询职位信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPosition")
	@ResponseBody
	public JSONObject getPosition(HttpServletRequest request,HttpServletResponse response,String id){
		if(StringUtils.isEmpty(id)){
			return this.out("传入的id为空", false);
		}
		Position position = positionService.get(Position.class, id);
		if(position == null){
			return this.out("系统中不存在code="+id+"的职位信息", false);
		}
		return this.out(position, true);
	}
}
