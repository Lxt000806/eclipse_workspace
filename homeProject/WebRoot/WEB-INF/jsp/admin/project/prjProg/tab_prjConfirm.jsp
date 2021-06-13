<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProgConfirm/goPrjConfirmJqGrid",
			postData:{custCode:"${customer.code}"},
		    rowNum:10000000,
			height:390,
		styleUI: 'Bootstrap', 
			colModel : [
				{name: "custcode", index: "custcode", width: 104, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "prjitem", index: "prjitem", width: 104, label: "施工项目", sortable: true, align: "left",hidden:true},
				{name: "prjitemdescr", index: "prjitemdescr", width: 104, label: "施工项目", sortable: true, align: "left"},
				{name: "ispassdescr", index: "ispassdescr", width: 84, label: "验收通过", sortable: true, align: "left"},
				{name: "prjleveldescr", index: "prjleveldescr", width: 84, label: "验收级别", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 84, label: "验收说明", sortable: true, align: "left"},
				{name: "confczydescr", index: "confczydescr", width: 84, label: "验收人", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "验收日期", sortable: true, align: "left", formatter: formatTime},
				{name: "custcode", index: "custcode", width: 84, label: "位置正常", sortable: true, align: "left",hidden:true },
				{name: "address", index: "address", width: 144, label: "地理位置", sortable: true, align: "left"},
			], 
 		};
       //初始化送货申请明细
	   Global.JqGrid.initJqGrid("dataTable_confirm",gridOption);


});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button style="align:left" type="button" class="btn btn-system "  id="viewConfPhoto">
							<span>查看验收图片 </span>
						</button>
		            </div>  
		          </div> 
		          </div> 		
			<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable_confirm"></table>
				</div>
		</div>



