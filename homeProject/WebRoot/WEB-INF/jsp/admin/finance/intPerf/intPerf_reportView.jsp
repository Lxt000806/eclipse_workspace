<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>业绩计算</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	function view(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"业绩计算--查看",
			postData:{pk:ret.pk,m_umState:'V'},
			url:"${ctx}/admin/intPerfDetail/goDetail",
			height: 380,
		 	width:750,
			returnFun: goto_query 
		});
	}
	/**初始化表格*/
	$(function(){
		if("${view}"=="view"){
			$("#doCount").attr("disabled","disabled");
			$("#update").attr("disabled","disabled");
		}
		$("#custCode").openComponent_customer({condition:{status:"4,5"}});	
		$("#designMan").openComponent_employee();	
		
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/intPerfDetail/goJqGrid",
			postData:{designMan:"${designMan}",isReportView:"1",no:"${no}",isCupboard:"${isCupboard}"},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  {name : "pk",index : "pk",width : 100,label:"pk",sortable : true,align : "left",hidden:true},
			  {name : "documentNo",index : "documentno",width : 75,label:"档案号",sortable : true,align : "left"},
			  {name : "address",index : "address",width : 120,label:"楼盘地址",sortable : true,align : "left"},
			  {name : "custtypedescr",index : "custtypedescr",width : 75,label:"客户类型",sortable : true,align : "left"},
			  {name : "designmandescr",index : "designmandescr",width : 75,label:"设计师",sortable : true,align : "left"},
			  {name : "type",index : "type",width : 100,label:"类型编号",sortable : true,align : "left",hidden:true},
			  {name : "typedescr",index : "typedescr",width : 75,label:"发放类型",sortable : true,align : "left"},
			  {name : "iscupboard",index : "iscupboard",width : 75,label:"是否橱柜",sortable : true,align : "left",hidden:true},
			  {name : "iscupboarddescr",index : "iscupboarddescr",width : 75,label:"是否橱柜",sortable : true,align : "left"},
			  {name : "perfamount",index : "perfamount",width : 75,label:"业绩",sortable : true,align : "right",sum:true},
			  {name : "perfamount_set",index : "perfamount_set",width : 85,label:"套餐内业绩",sortable : true,align : "right",sum:true},
			  {name : "totalperfamount",index : "totalperfamount",width : 75,label:"总业绩",sortable : true,align : "right",sum:true},
			  {name : "ismodifieddescr",index : "ismodifieddescr",width : 75,label:"是否修改",sortable : true,align : "left"},
			  {name : "lastupdate",index : "lastupdate",width : 100,label:"最后更新时间",sortable : true,align : "left",formatter:formatDate},
			  {name : "lastupdatedby",index : "lastupdatedby",width : 100,label:"最后更新人",sortable : true,align : "left"},
			  {name : "expired",index : "expired",width : 100,label:"是否过期",sortable : true,align : "left"},
			  {name : "actionlog",index : "actionlog",width : 100,label:"操作类型",sortable : true,align : "left"},
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
