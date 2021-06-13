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
	<title>结算工地减项分析_明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
	<body>
		<div class="pageContent">
			<div id="content-list">
				<div id="tabDetail">
					<table id= "dataTable" ></table>
				</div>
			</div>
		</div>
		<script type="text/javascript"> 
		 	var postData = {};
		 	postData.dateFrom =formatDate("${customer.dateFrom}");
		 	postData.dateTo =formatDate("${customer.dateTo}");
		 	postData.itemType1="${customer.itemType1}";
		 	postData.department1="${customer.department1}";
		 	postData.department2="${customer.department2}";
		 	postData.custType="${customer.custType}";
		 	postData.itemType12="${customer.itemType12}";
		 	postData.isCupboard="${customer.isCupboard}";
		 	postData.designMan="${customer.designMan}";
		 	postData.projectMan="${customer.projectMan}";
		 	postData.showType="${customer.showType}";
			$(function(){
				//初始化表格
				var colModel_ZC = [
					{name:"address", index:"address", width:190, label:"楼盘", sortable:true, align:"left"},
					{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
					{name:"area", index:"area", width:70, label:"面积", sortable:true, align:"right"},
					{name:"designmandescr", index:"designmandescr", width:80, label:"设计师", sortable:true, align:"left"},
					{name:"designdept1", index:"designdept1", width:80, label:"事业部", sortable:true, align:"left"},
					{name:"designdept2", index:"designdept2", width:80, label:"设计部", sortable:true, align:"left"},
					{name:"projectmandescr", index:"projectmandescr", width:80, label:"项目经理", sortable:true, align:"left"},
					{name:"prjdept2", index:"prjdept2", width:80, label:"工程部", sortable:true,align:"left"},
					{name:"itemtypedescr", index:"itemtypedescr", width:80, label:"材料分类", sortable:true, align:"left"},
					{name:"planamount", index:"planamount", width:80, label:"预算金额", sortable:true, align:"right", sum:true},
					{name:"allcost", index:"allcost", width:80, label:"结算金额", sortable:true,align:"right",sum:true},
					{name:"discamount", index:"discamount", width:80, label:"减项金额", sortable:true, align:"right", sum:true, formatter:formatMoney},
					{name:"discper", index:"discper", width:80, label:"减项占比", sortable:true,align:"right", formatter:divFormat},
				];
				var colModel_JC = [
					{name:"address", index:"address", width:190, label:"楼盘", sortable:true, align:"left"},
					{name:"custtypedescr", index:"custtypedescr", width:80, label:"客户类型", sortable:true, align:"left"},
					{name:"area", index:"area", width:70, label:"面积", sortable:true, align:"right"},
					{name:"designmandescr", index:"designmandescr", width:80, label:"设计师", sortable:true, align:"left"},
					{name:"designdept1", index:"designdept1", width:80, label:"事业部", sortable:true, align:"left"},
					{name:"designdept2", index:"designdept2", width:80, label:"设计部", sortable:true, align:"left"},
					{name:"projectmandescr", index:"projectmandescr", width:80, label:"项目经理", sortable:true, align:"left"},
					{name:"prjdept2", index:"prjdept2", width:80, label:"工程部", sortable:true,align:"left"},
					{name:"iscup", index:"iscup", width:80, label:"是否橱柜", sortable:true, align:"left"},
					{name:"planamount", index:"planamount", width:80, label:"预算金额", sortable:true, align:"right", sum:true},
					{name:"allcost", index:"allcost", width:80, label:"结算金额", sortable:true,align:"right",sum:true},
					{name:"discamount", index:"discamount", width:80, label:"减项金额", sortable:true, align:"right", sum:true, formatter:formatMoney},
					{name:"discper", index:"discper", width:80, label:"减项占比", sortable:true,align:"right", formatter:divFormat},
				];
				var jqGridOption={
					url:"${ctx}/admin/prjMinusAnaly/goDetailJqGrid",
					postData:postData,
					datatype:"json",
					height:$(document).height()-$("#content-list").offset().top-70,
					styleUI: "Bootstrap",
					rowNum:100000,
				};
				if(postData.itemType1=="ZC"){
					Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
						page: 1,
						colModel: colModel_ZC
					}));
				} else if(postData.itemType1=="JC"){
					Global.JqGrid.initJqGrid("dataTable", $.extend(jqGridOption, {
						page: 1,
						colModel: colModel_JC
					}));
				}
			});
			function divFormat (cellvalue, options, rowObject){ 
			    return myRound(cellvalue*100,4)+"%";
			}	
			function formatMoney(cellvalue, options, rowObject){
				return myRound(cellvalue,0);
			}
		</script>
	</body>	
</html>
