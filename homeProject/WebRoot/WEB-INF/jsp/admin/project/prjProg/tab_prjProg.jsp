<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:390,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "PK", index: "PK", width: 170, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "CustCode", index: "CustCode", width: 170, label: "客户编号", sortable: true, align: "left",hidden:true},
				{name: "PrjItem", index: "PrjItem", width: 170, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "prjdescr", index: "prjdescr", width: 120, label: "施工项目", sortable: true, align: "left"},
				{name: "PlanBegin", index: "PlanBegin", width: 80, label: "计划开始日", sortable: true, align: "left",formatter: formatDate},
				{name: "BeginDate", index: "BeginDate", width: 80, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "PlanEnd", index: "planEnd", width: 80, label: "计划结束日", sortable: true, align: "left",formatter: formatDate},
				{name: "EndDate", index: "EndDate", width: 80, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "photonum", index: "photonum", width: 60, label: "施工图片数", sortable: true, align: "left",},
				{name: "ConfirmCZY", index: "ConfirmCZY", width: 60, label: "验收人", sortable: true, align: "left",hidden:true},
				{name: "confirmdescr", index: "confirmdescr", width: 60, label: "验收人", sortable: true, align: "left"},
				{name: "ConfirmDate", index: "ConfirmDate", width: 80, label: "验收日期", sortable: true, align: "left", formatter: formatDate},
				{name: "prjleveldescr", index: "prjleveldescr", width: 60, label: "验收评级", sortable: true, align: "left"},
				{name: "ConfirmDesc", index: "ConfirmDesc", width: 120, label: "验收说明", sortable: true, align: "left"},
				{name: "LastUpdate", index: "LastUpdate", width: 123, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedby", width: 60, label: "操作人员", sortable: true, align: "left"},
				{name: "Expired", index: "Expired", width: 60, label: "是否过期", sortable: true, align: "left"},
				{name: "ActionLog", index: "ActionLog", width: 60, label: "操作代码", sortable: true, align: "left"}				
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button style="align:left" type="button" class="btn btn-system "  id="addView">
							<span>查看</span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "  id="viewConsPhoto">
							<span>查看施工图片</span>
						</button>
		            </div>  
		          </div> 
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable"></table>
				</div>
		</div>



