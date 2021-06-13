<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>提示点击弹窗页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
			$(function(){
				if("${data.m_umState}" == "A"){
					resultJson = listStrToJson("${data.resultList}");
					Global.JqGrid.initJqGrid("overPlanItemDataTable",{
						height:360,
						styleUI:"Bootstrap",
						colModel:[
							{name: "itemcode", index: "itemcode", width: 163, label: "材料编号", sortable: true, align: "left"},
							{name: "itemdescr", index: "itemdescr", width: 385, label: "材料名称", sortable: true, align: "left"}
						]
					});
					for(var i=0;i<resultJson.length;i++){
						Global.JqGrid.addRowData("overPlanItemDataTable",resultJson[i]);
					}
				}else if("${data.m_umState}" == "V"){
					resultJson = listStrToJson("${data.resultList}");
					Global.JqGrid.initJqGrid("overPlanByItemType2DataTable",{
						height:330,
						styleUI:'Bootstrap',
						colModel:[
							{name: "ItemType2Descr", index: "ItemType2Descr", width: 197, label: "材料类型2", sortable: true, align: "left"},
							{name: "Amount", index: "Amount", width: 119, label: "超预算金额", sortable: true, align: "left", sum: true}
						]
					});
					for(var i=0;i<resultJson.length;i++){
						Global.JqGrid.addRowData("overPlanByItemType2DataTable",resultJson[i]);
					}
				} else if("${data.m_umState}" == "J"){
					Global.JqGrid.initJqGrid("overSeqItemDataTable",{
						url:"${ctx}/admin/itemPreApp/goItemAppDetailJqGrid",
						postData:{m_umState:"C",appNo:"${data.appNo}",custCode:"${data.custCode}"
						,isSetItem:"1",itemType1:"JC",seqpks:"${data.resultList}"},
						height:330,
						styleUI:'Bootstrap',
						colModel:[
							{name: "itemcode", index: "itemcode", width: 70, label: "材料编号", sortable: true, align: "left"},
							{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
							{name: "qty", index: "qty", width: 70, label: "采购数量", sortable: true, align: "right"},
							{name: "reqqty", index: "reqqty", width: 70, label: "预算数量", sortable: true, align: "right"},
							{name: "sendedqty", index: "sendedqty", width: 80, label: "总共已发货", sortable: true, align: "right"},
							{name: "confirmedqty", index: "confirmedqty", width: 80, label: "已审核数量", sortable: true, align: "right"},
							{name: "differences", index: "differences", width: 80, label: "成本差异额 ", sortable: true, align: "right"},
						]
					});
				} 
			});
		</script>
	</head>
	<body>
	
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button id="closeBut" type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>	
					</div>
				</div>
			</div>
			<table id="overPlanByItemType2DataTable"></table>
			<table id="overPlanItemDataTable"></table>
			<table id="overSeqItemDataTable"></table>
		</div>
	</body>
</html>

