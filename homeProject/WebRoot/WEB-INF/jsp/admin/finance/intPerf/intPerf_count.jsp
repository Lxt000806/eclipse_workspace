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
	function doExcel(){
		var url = "${ctx}/admin/intPerfDetail/doExcel";
		doExcelAll(url);
	}
	function doCount(){
		$.ajax({
			url:"${ctx}/admin/intPerfDetail/doCount",
			type: "post",
			data: {no:"${no}",prjPerfNo:"${prjPerfNo }"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
				art.dialog({
					content	:"生成数据成功",			
				});
		    }
		 });
	}
	function viewDetail(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("viewDetail",{
			title:"业绩计算--查看明细",
			postData:{custCode:ret.custcode,isCupboard:ret.iscupboard,intPerfEndDate:"${endDate}"},
			url:"${ctx}/admin/intPerfDetail/goViewDetail",
			height: 700,
		 	width:1300,
			returnFun: goto_query 
		});
	}
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
	function update(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"业绩计算--编辑",
			postData:{pk:ret.pk,m_umState:'M'},
			url:"${ctx}/admin/intPerfDetail/goUpdate",
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
			postData:{no:"${no}",dateFrom:"${beginDate}",dateTo:"${endDate}"},
			height:$(document).height()-$("#content-list").offset().top-102,
			styleUI: "Bootstrap", 
			colModel : [
			  {name : "pk",index : "pk",width : 100,label:"pk",sortable : true,align : "left",hidden:true},
			  {name : "custcode",index : "custcode",width : 100,label:"客户编号",sortable : true,align : "left",hidden:true},
			  {name : "documentno",index : "documentno",width : 75,label:"档案号",sortable : true,align : "left"},
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
			  {name : "region1descr",index : "region1descr",width : 100,label:"一级区域",sortable : true,align : "left"},
			  {name : "region2descr",index : "region2descr",width : 75,label:"二级区域",sortable : true,align : "left"},
			  {name : "chgnum",index : "chgnum",width : 75,label:"变更次数",sortable : true,align : "right",sum:true},
			  {name : "discamount",index : "discamount",width : 75,label:"优惠金额",sortable : true,align : "right",sum:true},
			  {name : "signdate",index : "signdate",width : 90,label:"签订日期",sortable : true,align : "left",formatter:formatDate},
			  {name : "appnum",index : "appnum",width : 75,label:"领料次数",sortable : true,align : "right",sum:true,title:"领料次数=发货次数-退回次数"},
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
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#custType").val("");
		$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="doCount" onclick="doCount()">
						<span>业绩数据生成</span>
					</button>
					<button type="button" class="btn btn-system" id="update"  onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="viewDetail()">
						<span>查看明细</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>导出excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" /> 
				<input type="hidden" style="width:160px;" id="no" name="no" value="${no }" />
				<input type="hidden" name="dateFrom" value="${beginDate}">
				<input type="hidden" name="dateTo" value="${endDate}">
				<ul class="ul-form">
					<li><label>楼盘</label> <input type="text" style="width:160px;"
						id="address" name="address" />
					</li>
					<li><label>发放类型</label> <house:xtdm id="type"
							dictCode="INTPERFTYPE" style="width:160px;"></house:xtdm>
					</li>
					<li><label>设计师</label> <input type="text" style="width:160px;"
						id="designMan" name="designMan" />
					</li>
					<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
							dictCode="YESNO" style="width:160px;"></house:xtdm>
					</li>
					<li><label>客户编号</label> <input type="text"
						style="width:160px;" id="custCode" name="custCode" />
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
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
