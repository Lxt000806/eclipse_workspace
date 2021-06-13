<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";

%>
<!DOCTYPE html>
<html>
<head>
	<title>采购订单管理——订货</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript"> 
	function save(existsThreeDays){
		if(existsThreeDays==0){
			art.dialog({
				content:"是否确定订货",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
			    	var param= Global.JqGrid.selectToJson("dataTable");
					Global.Form.submit("page_form","${ctx}/admin/purchase/doAppItem",param,function(ret){
						if(ret.rs==true){
							art.dialog({
								content:ret.msg,
								time:1000,
								beforeunload:function(){
									closeWin();
								}
							}); 			
						}else{
							$("#_form_token_uniq_id").val(ret.token.token);
							art.dialog({
								content:ret.msg,
								width:200
							});
						}
					});
			    	return true;
				},
				cancel: function () {
					return true;
				}
			});
		}else{
			var param= Global.JqGrid.selectToJson("dataTable");
			Global.Form.submit("page_form","${ctx}/admin/purchase/doAppItem",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					}); 			
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		}
	}
	$(function(){
		$("#supplier").openComponent_supplier({condition:{itemType1:'${purchase.itemType1 }'}});	
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		Global.LinkSelect.setSelect({firstSelect:'itemType1',
								firstValue:'${purchase.itemType1 }',
								});
		$("#appItem").on("click",function(){
			var ids = $("#dataTable").jqGrid('getGridParam','selarrrow');
			if(ids.length==0){
				art.dialog({
					content:"请选择一条或多条数据",
				});
				return;
			}
			var existsThreeDays=0;	
			for(var i=0;i<ids.length;i++){
				if($.trim($("#dataTable").jqGrid('getRowData',ids[i]).threedayslater)=='1'){
					existsThreeDays=1;
				}
				console.log(444);
			}
			
			if(existsThreeDays==1){
				console.log(222);
				art.dialog({
					content:"存在采购单订货时间为3天之后是否确认订货?",
					lock: true,
					width: 200,
					height: 100,
					ok: function () {
				    	save(existsThreeDays);
					},
					cancel: function () {
						return true;
					}
				});
			}else{
				save(existsThreeDays);
			}
		});
		//初始化表格	
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/purchase/getAppItemPage",
			postData:{appItemDateTo:$.trim($("#appItemDateTo").val())},
			height:$(document).height()-$("#content-list").offset().top-50,
			rowNum:10000000,
			multiselect: true,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 80, label: "采购单号", sortable: true, align: "left"},
				{name: "item1descr", index: "item1descr", width: 80, label: "材料类型1", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 80, label: "供应商", sortable: true, align: "left"},
				{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 140, label: "楼盘", sortable: true, align: "left",hidden:true},
				{name: "date", index: "date", width: 110, label: "申请时间", sortable: true, align: "left",formatter:formatTime},
				{name: "planorderdate", index: "planorderdate", width: 80, label: "订货日期", sortable: true, align: "left",formatter:formatDate},
				{name: "amount", index: "amount", width: 70, label: "订货金额", sortable: true, align: "right",},
				{name: "arrivedate", index: "arrivedate", width: 80, label: "到货日期", sortable: true, align: "left",formatter:formatDate},
				{name: "threedayslater", index: "threedayslater", width: 80, label: "订货时间超过3天", sortable: true, align: "left",hidden:true},
				{name: "appprjdescr", index: "appprjdescr", width: 110, label: "订货节点", sortable: true, align: "left"},
				{name: "appcomfirmdate", index: "appcomfirmdate", width: 110, label: "订货节点验收时间", sortable: true, align: "left",formatter:formatDate},
				{name: "prjdescr", index: "prjdescr", width: 110, label: "当前验收节点", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 90, label: "当前验收时间", sortable: true, align: "left",formatter:formatDate},
				{name: "softnamechi", index: "softnamechi", width: 80, label: "软装设计师", sortable: true, align: "left"},
				]
		});
		
		$("#viewJD").on("click",function(){
			var ret = selectDataTableRow();
         	  if (ret) {	
             	Global.Dialog.showDialog("Update",{ 
              	  title:"查看客户工程进度",
              	  url:"${ctx}/admin/prjProg/goView?id="+ret.custcode,
              	  height: 715,
              	  width:1100,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
	});
		function changeItemType(){
			console.log($.trim($("#itemType1").val()));
			$("#supplier").openComponent_supplier({condition:{itemType1:$.trim($("#itemType1").val())}});	
			checkItemType1();
		}
		
		function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_supplier_supplier").val('');
		$("#page_form select").val('');
		$("#supplier").val('');
		$("#itemType12").val('');
		$.fn.zTree.getZTreeObj("tree_itemType12").checkAllNodes(false);
	} 
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="pageContent">
				<div class="panel panel-system">
					<div class="panel-body">
						<div class="btn-group-xs">
							<button type="button" class="btn btn-system" id="appItem">
								<span>订货</span>
							</button>
							<button type="button" class="btn btn-system" id="viewJD">
								<span>查看进度</span>
							</button>
							<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
								<span>关闭</span>
							</button>
						</div>
					</div>
				</div>
				<div class="body-box-list">
					<div class="query-form">
						<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
							<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
								<li>
									<label>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;" onchange="changeItemType()" ></select>
								</li>
								<li>
									<label>材料分类</label>
									<house:DictMulitSelect id="itemType12" dictCode="" 
									sql="select * from tItemType12 where itemType1 in ('${purchase.itemRight }') " 
									sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
								</li>
								<li>
									<label>供应商</label>
									<input type="text" id="supplier" name="supplier" style="width:160px;"/>
								</li>
								<li>
									<label>计划订货日期从</label>
									<input type="text" id="appItemDateFrom" name="appItemDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="appItemDateTo" name="appItemDateTo" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.appItemDateTo}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li>
									<label>楼盘</label>
									<input type="text" id="address" name="address" style="width:160px;"/>
								</li>
								<li class="search-group" >
									<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
									<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
								</li>
							</ul>	
						</form>
					</div>
				</div>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
			</div>
		</div>
	</body>	
</html>
