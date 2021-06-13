<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>发货通知</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
		/**初始化表格*/
		$(function(){
			Global.JqGrid.initJqGrid("dataTableMx",{
				height:200,
				styleUI:"Bootstrap",
				rowNum:10000,
				colModel:[
					{name: "no", index: "no", width: 70, label: "领料单号", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审核时间", sortable: true, align: "left",formatter: formatTime},
					{name: "sendtypedescr", index: "sendtypedescr", width: 70, label: "发货类型", sortable: true, align: "left"},
					{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
					{name: "whdescr", index: "whdescr", width: 100, label: "仓库", sortable: true, align: "left"},
				],
			});
			var datas = ${rets};
			Global.JqGrid.initJqGrid("dataTable",{
				height:280,
				styleUI:"Bootstrap",
				datatype: 'local',
				data:datas,
				cellEdit:true,
				cellsubmit:'clientArray',
				rowNum:10000,
				colModel:[
					{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left",hidden:true},
					{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype1", index: "itemtype1", width: 70, label: "材料类型1", sortable: true, align: "left",hidden:true},
					{name: "jobtype", index: "jobtype", width: 100, label: "项目任务", sortable: true, align: "left",hidden:true},
					{name: "jobtypedescr", index: "jobtypedescr", width: 80, label: "项目任务", sortable: true, align: "left"},
					{name: "shouldsendnode", index: "shouldsendnode", width: 80, label: "应送货节点", sortable: true, align: "left"},
					{name: "nodedatetype", index: "nodedatetype", width: 70, label: "节点类型", sortable: true, align: "left"},
					{name: "nodetriggerdate", index: "nodetriggerdate", width: 90, label: "节点触发时间", sortable: true, align: "left",formatter: formatDate},
					{name: "moneyinfull", index: "moneyinfull", width: 70, label: "款项到位", sortable: true, align: "left"},
					{name: "shouldbanlance", index: "shouldbanlance", width: 55, label: "差额", sortable: true, align: "right"},
					{name: "sendremarks", index: "sendremarks", width: 190, label: "发货备注", sortable: true, align: "left",editable:true,edittype:'text',},
					{name: "paynum", index: "paynum", width: 200, label: "付款期数", sortable: true, align: "left",hidden:true},
				],
				gridComplete:function(){
					$("#dataTable").setSelection("1", true);
					var ret = selectDataTableRow("dataTable");
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/sendNotice/goItemAppJqGrid",
							postData:{code:ret.custcode,jobType:ret.jobtype},
							page:1,
							sortname:""
						}).trigger("reloadGrid");
					}
				},
				beforeSelectRow:function(id){
					var ret = $("#dataTable").jqGrid("getRowData", id);
					if(ret){
						$("#dataTableMx").jqGrid("setGridParam",{
							url:"${ctx}/admin/sendNotice/goItemAppJqGrid",
							postData:{code:ret.custcode,jobType:ret.jobtype},
							page:1,
							sortname:""
						}).trigger("reloadGrid");
					}
					setTimeout(function(){
	           			relocate(id);
	          	    },100);
	          	}
			});
		});
		
		function doSave() {
			var rets = $("#dataTable").jqGrid("getRowData");
			var params = {"prjJobJson": JSON.stringify(rets)};
			Global.Form.submit("dataForm","${ctx}/admin/sendNotice/doSendNotice",params,function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
		    		art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
		</script>
	</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
			    		<button id="saveBtn" type="button" class="btn btn-system" onclick="doSave()">保存</button>	
						<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
			</form>
			<table id="dataTable"></table>
			<table id="dataTableMx"></table>
		</div>
	</body>
</html>
