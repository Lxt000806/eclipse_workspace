<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
	String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style>
		.ui-jqgrid .ui-jqgrid-view{
			margin: 0px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="empSave">
						<span>新增</span>
					</button>
					<button type="button" class="btn btn-system" id="empEdit">
						<span>编辑</span>
					</button>
					<button type="button" class="btn btn-system" id="empDelete">
						<span>删除</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" defer>
		$(function() {
			var gridOption = {
				height: $(document).height()-$("#content-list").offset().top-36,
				rowNum: 10000000,
				colModel: [
		    		{name: "pk", index: "pk", width: 30, label: "pk", sortable: true, align: "left", hidden: true},
					{name: "basechgno", index: "basechgno", width: 100, label: "基础增减单号", sortable: true, align: "left", hidden: true},
					{name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left"},
					{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
					{name: "role", index: "role", width: 80, label: "角色", sortable: true, align: "left", hidden: true},
					{name: "roledescr", index: "roledescr", width: 80, label: "角色", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width:85, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "最后更新人员", sortable: true, align: "left", hidden: true},
					{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left", hidden: true},
					{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true}
				],
				ondblClickRow: function(){
                    doUpdate();
                },
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);
			// 将字符串转换成object导入到表格中
			if ("[]"!='${chgStakeholderList}') {
				var arrayString = '${chgStakeholderList}'.replace(/[\[\]]/g,"");
				var listArray = arrayString.split("\@zb");
				var chgStakeholderObject = {};
				$.each(listArray, function (i, v) {
					chgStakeholderObject = JSON.parse(v);
					Global.JqGrid.addRowData("dataTable", chgStakeholderObject);
				});
			}
			$("#empSave").on("click", function () {
				var users = Global.JqGrid.allToJson("dataTable","empcode");
				var keys = users.keys;
				Global.Dialog.showDialog("empSave",{
					title:"基装增减干系人——新增",
					url:"${ctx}/admin/baseItemChg/goEmpAdd",
					postData:{m_umState: "A", keys: keys},
					height:200,
					width:464,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								$.extend(v,{
									role: "01",
									roledescr: "业务员",
									lastupdate: new Date(),
									actionlog: "ADD",
									expired: "F"
								});
								Global.JqGrid.addRowData("dataTable",v);
							});
							var rowIds = $("#dataTable").jqGrid("getDataIDs");
							$("#dataTable").jqGrid("setSelection",rowIds[rowIds.length-1]);
						}
					}
				});
			});
			$("#empEdit").on("click", function () {
				doUpdate();
			});
			$("#empDelete").on("click", function () {
				var ret=selectDataTableRow();
				var id = $("#dataTable").jqGrid("getGridParam","selrow");
				if(!id){
					art.dialog({content: "请选择一条记录进行删除操作",width: 220});
					return false;
				}
				art.dialog({
					content:"是否删除该记录？",
					lock: true,
					width: 200,
					height: 80,
					ok: function () {
						Global.JqGrid.delRowData("dataTable",id);
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
			});
			$("#closeBut").on("click", function () {
				doClose();
			});
			replaceCloseEvt("chgStakeholder", doClose);
		});
		function doUpdate() {
			var ret=selectDataTableRow();
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
			if(!ret){
				art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
				return false;
			}
			var users = Global.JqGrid.allToJson("dataTable","empcode");
			var keys = users.keys;
			var index = keys.indexOf(ret.empcode);
            if (index > -1) keys.splice(index, 1); //去掉选择的项
            Global.Dialog.showDialog("empEdit",{
            	title:"基装增减干系人——编辑",
            	url:"${ctx}/admin/baseItemChg/goEmpAdd",
            	postData:{
            		empCode: ret.empcode,
            		empDescr: ret.empname,
            		keys: keys
            	},
            	height:200,
            	width:464,
            	returnFun : function(data){
            		if(data){
            			$.each(data,function(k,v){
            				$.extend(v,{
            					lastupdate: new Date(),
            				});
            				$("#dataTable").setRowData(rowId,v);
            			});
            		}
            	}
            });
		}
		function doClose() {
			/*if (!rows || rows.length==0){
				art.dialog({content: "无数据保存"});
				return;
			}
			var selectRows = [];
			$.each(ids,function(k,id){
				var row = $("#dataTable").jqGrid("getRowData", id);
				selectRows.push(row);
			});*/
			var rows = $("#dataTable").jqGrid("getRowData");
			Global.Dialog.returnData = rows;
			closeWin();
		}
	</script>
</body>	
</html>
