<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/*工地报备*/
	var gridOption ={
		url:"${ctx}/admin/prjProg/goBuilderRepJqGrid",
		postData:{custCode:"${customer.code}"},
	    rowNum:10000000,
		height: $(document).height()-$("#content-list-5").offset().top-245,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "LastUpdate", index: "LastUpdate", width: 120, label: "报备日期", sortable: true, align: "left",formatter:formatTime} 	,		
				{name: "buildstatusdescr", index: "buildstatusdescr", width: 120, label: "停工原因", sortable: true, align: "left",} 	,		
				{name: "Remark", index: "Remark", width: 400, label: "说明", sortable: true, align: "left",} 	,		
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_builderRep",gridOption);
});
</script>
<div class="body-box-list" style="margin-top: 0px;">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form_builderRep" method="post">
            <input type="hidden" id="expired" name="expired"/>
            <input type="hidden" name="jsonString" value="" />
            <ul class="ul-form">
                <li>
                    <label>施工阶段</label>
                    <house:dict id="progStage" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
                        sql="select Code, Code + ' ' + Descr Descr from tProgStage"></house:dict>
                </li>
                <li>
                    <button type="button" class="btn btn-system btn-xs" onclick="goto_query('dataTable_builderRep', 'page_form_builderRep')">查询</button>
                </li>
            </ul>
        </form>
    </div>
	<div id="content-list-5" >
		<table id="dataTable_builderRep"></table>
		<div id="dataTable_builderRepPager"></div>
	</div>
</div>
