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
	<title>材料发货分析-发货类型查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script>
/* 根据当前显示表不同导出excel不同 */
var ExcTableId="dataTable_tile";
var tableName="瓷砖明细表"; 
function changeToTile(){
	ExcTableId="dataTable_tile";
	tableName="瓷砖明细表";
}
function changeToFloor(){
	ExcTableId="dataTable_floor";
	tableName="地板明细表";
}
function changeToToilet(){
	ExcTableId="dataTable_toilet";
	tableName="马桶明细表";
}
function changeToCabinet(){
	ExcTableId="dataTable_Cabinet";
	tableName="浴室柜明细表";
}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body" >
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<button type="button" class="btn btn-system " onclick="doExcelNow(tableName,ExcTableId,'page_form')" title="导出当前excel数据">
							<span>导出excel</span>
						</button>
					</div>
				</div>
			</div>
		 	<form type="hidden" role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="sendDateFrom" name="sendDateFrom" class="i-date"
	            	onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	            	value="<fmt:formatDate value='${itemApp.sendDateFrom}' pattern='yyyy-MM-dd'/>"/>
		      	<input type="hidden" id="sendDateTo" name="sendDateTo" class="i-date"
               		onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
               		value="<fmt:formatDate value='${itemApp.sendDateTo}' pattern='yyyy-MM-dd'/>"/>
           		<input type="hidden" id="sendType" name="sendType" value="${itemApp.sendType}"/><!-- 发货类型 -->
           		<input type="hidden" id="custCode" name="custCode" value="${itemApp.custCode}"/>
		        <input type="hidden" id="groupType" name="groupType" value="${itemApp.groupType}"/>
		        <input type="hidden" id="itemType1" name="itemType1" value="${itemApp.itemType1}"/>
		        <input type="hidden" id="itemType2" name="itemType2" value="${itemApp.itemType2}"/>
		        <input type="hidden" id="itemType3" name="itemType3" value="${itemApp.itemType3}"/>
		        <input type="hidden" id="itemCode" name="itemCode" value="${itemApp.itemCode}"/>
		        <input type="hidden" id="sqlCode" name="sqlCode" value="${itemApp.sqlCode}"/>
		        <input type="hidden" id="supplCode" name="supplCode" value="${itemApp.supplCode}"/>
		        <input type="hidden" id="sendCzy" name="sendCzy" value="${itemApp.sendCzy}"/>
		        <input type="hidden" id="department2" name="department2" value="${itemApp.department2}"/>
		        <input type="hidden" id="containCmpCust" name="containCmpCust" value="${itemApp.containCmpCust}"/>
	          	<input type="hidden" id="custType" name="custType" value="${itemApp.custType}"/>
		        <input type="hidden" id="isSetItem" name="isSetItem" value="${itemApp.isSetItem}"/>
  			</form>
			<div class="container-fluid">  
				<ul class="nav nav-tabs"> 
			        <li class="active"><a href="#tab_tile" data-toggle="tab" onclick="changeToTile()">瓷砖</a></li>
			        <li class=""><a href="#tab_floor" data-toggle="tab" onclick="changeToFloor()">地板</a></li>
			        <li class=""><a href="#tab_cabinet" data-toggle="tab" onclick="changeToCabinet()">浴室柜</a></li>
			        <li class=""><a href="#tab_toilet" data-toggle="tab" onclick="changeToToilet()">马桶</a></li>
			    </ul> 
			    <div class="tab-content">  
			        <div id="tab_tile" class="tab-pane fade in active"> 
			         	<jsp:include page="tab_tile.jsp"></jsp:include>
			        </div> 
			        <div id="tab_floor" class="tab-pane fade "> 
			         	<jsp:include page="tab_floor.jsp"></jsp:include>
			        </div> 
			        <div id="tab_cabinet" class="tab-pane fade "> 
			         	<jsp:include page="tab_cabinet.jsp"></jsp:include>
			        </div>
			        <div id="tab_toilet" class="tab-pane fade "> 
			         	<jsp:include page="tab_toilet.jsp"></jsp:include>
			        </div> 
			    </div>  
			</div>
		</div>
	</body>	
</html>
