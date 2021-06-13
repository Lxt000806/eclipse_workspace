<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<title>领料退回-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
	<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("returnAddDataTable",{
				url:"${ctx}/admin/itemApp/getReturnAddJqGrid",
				postData:{
					oldNo:"${data.oldNo}",
					reqPks:"${data.reqPks}",
					custCode:"${data.custCode}"
				},
				height:450,
				rowNum: 10000,
				styleUI:"Bootstrap",
				multiselect: true,
				colModel:[
					{name: "pk", index: "pk", width: 70, label: "编号", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 100, label: "区域", sortable: true, align: "left"},
					{name: "intproddescr", index: "intproddescr", width: 100, label: "集成成品", sortable: true, align: "left"},
					{name: "itemcode", index: "itemcode", width: 100, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 260, label: "材料名称", sortable: true, align: "left"},
					{name: "suppldescr", index: "suppldescr", width: 167, label: "供应商", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 100, label: "领料数量", sortable: true, align: "right"},
					{name: "reqqty", index: "reqqty", width: 93, label: "需求数量", sortable: true, align: "right"},
					{name: "sendqty", index: "sendqty", width: 100, label: "已发货数量", sortable: true, align: "right"},
					{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
					
					{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
					{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
					{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
					{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
					{name: "uomdescr", index: "uomdescr", width: 76, label: "单位", sortable: true, align: "left", hidden: true},
					{name: "sendedqty", index: "sendedqty", width: 109, label: "总共已发货数量", sortable: true, align: "right", hidden: true},
					{name: "confirmedqty", index: "confirmedqty", width: 92, label: "已审核数量", sortable: true, align: "right", hidden: true},
					{name: "returnappqty", index: "returnappqty", width: 100, label: "退回申请数量", sortable: true, align: "right", hidden: true},
					{name: "cost", index: "cost", width: 80, label: "成本", sortable: true, align: "right", hidden: true},
					{name: "allcost", index: "allcost", width: 90, label: "成本总价", sortable: true, align: "right", sum: true, hidden: true},
					{name: "projectcost", index: "projectcost", width: 112, label: "项目经理结算价", sortable: true, align: "right", hidden: true},
					{name: "allprojectcost", index: "allprojectcost", width: 120, label: "项目经理结算总价", sortable: true, align: "right", sum: true, hidden: true},
					{name: "processcost", index: "processcost", width: 92, label: "其它费用", sortable: true, align: "right", hidden: true},
					{name: "weight", index: "weight", width: 85, label: "总重量", sortable: true, align: "right", sum: true, hidden: true},
					{name: "numdescr", index: "numdescr", width: 85, label: "总片数", sortable: true, align: "right", hidden: true},
					{name: "confirmedqty", index: "confirmedqty", width: 85, label: "confirmedqty", sortable: true, align: "right", hidden: true},
					
					{name: "aftqty", index: "aftqty", width: 85, label: "aftqty", sortable: true, align: "right", hidden: true},
					{name: "aftcost", index: "aftcost", width: 85, label: "aftcost", sortable: true, align: "right", hidden: true},
					{name: "preappdtpk", index: "preappdtpk", width: 85, label: "preappdtpk", sortable: true, align: "left", hidden: true},
					{name: "shortqty", index: "shortqty", width: 85, label: "shortqty", sortable: true, align: "right", hidden: true},
					{name: "declareqty", index: "declareqty", width: 85, label: "declareqty", sortable: true, align: "right", hidden: true},
					{name: "specreqpk", index: "specreqpk", width: 75, label: "specreqpk", sortable: true, align: "left", hidden: true},
					
				]
			}); 
		});
		function selectAll(flag){	
			Global.JqGrid.jqGridSelectAll("returnAddDataTable", flag);
			$("#cb_returnAddDataTable").get(0).checked = flag;
		}
		function save(){			
			var ids=$("#returnAddDataTable").jqGrid("getGridParam", "selarrrow");
			if(ids.length == 0){
				art.dialog({
					content:"请选择数据后保存"
				});				
				return ;
			}
			var selectRows = [];
			$.each(ids,function(i,id){
				var rowData = $("#returnAddDataTable").jqGrid("getRowData", id);
				selectRows.push(rowData);
			});
    		Global.Dialog.returnData = selectRows;
    		closeWin();
		}
	</script>
</head>
    
<body>
	<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body" >
		    	<div class="btn-group-xs">
    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
    				<button id="selectAllBut" type="button" class="btn btn-system" onclick="selectAll(true)">全选</button>
    				<button id="unSelectAllBut" type="button" class="btn btn-system" onclick="selectAll(false)">不选</button>
    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">退出</button>
				</div>
			</div>
		</div>
		<div>
			<table id="returnAddDataTable"></table>
		</div>
	</div>
</body>
</html>


