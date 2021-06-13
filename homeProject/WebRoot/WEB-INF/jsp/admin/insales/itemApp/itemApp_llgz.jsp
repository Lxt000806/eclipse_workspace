<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>领料跟踪页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script type="text/javascript">
			function doLlgzExcel(){
				var activeTab = $("#llgzTbasUl li[class='active']").children().attr("id");
				var dataTableName = "";
				var url = "${ctx}/admin/itemApp/doLlgzExcel";
				var postDataUrl = "";
				if(activeTab == "a_unCheck"){
					dataTableName += "unCheckDataTable";
					postDataUrl += "&itemType1="+$("#unCheckItemType1").val()
					   + "&itemType2="+$("#unCheckItemType2").val()
					   + "&appDateFrom="+$("#appDateFrom").val()
					   + "&appDateTo="+$("#appDateTo").val()
					   + "&mainManCode="+$("#unCheckMainManCode").val();
				}else if(activeTab == "a_confirmReturn"){
					dataTableName += "confirmReturnDataTable";
					postDataUrl += "&itemType1="+$("#confirmReturnItemType1").val()
					   + "&itemType2="+$("#confirmReturnItemType2").val()
					   + "&mainManCode="+$("#confirmReturnMainManCode").val();
				}else if(activeTab == "a_unReceiveBySpl"){
					dataTableName += "unReceiveBySplDataTable";
					postDataUrl += "&itemType1="+$("#unReceiveBySplItemType1").val()
					    + "&itemType2="+$("#unReceiveBySplItemType2").val()
					    + "&confirmDateFrom="+$("#confirmDateFrom").val()
						+ "&confirmDateTo="+$("#confirmDateTo").val()
						+ "&mainManCode="+$("#unReceiveBySplMainManCode").val()
						+ "&supplCode="+$("#unReceiveBySplSupplCode").val();
				}else{
					dataTableName += "unSendBySplDataTable";
					postDataUrl += "&itemType1="+$("#unSendBySplItemType1").val()
					    + "&itemType2="+$("#unSendBySplItemType2").val()
					    + "&arriveDateFrom="+$("#arriveDateFrom").val()
						+ "&arriveDateTo="+$("#arriveDateTo").val()
						+ "&mainManCode="+$("#unSendBySplMainManCode").val()
						+ "&supplCode="+$("#unSendBySplSupplCode").val()
						+ "&region="+$("#region").val()+"&prjRegion="+$("#prjRegion").val();
				}
				url += "?dataTableName="+dataTableName+postDataUrl;
				doExcelAll(url, dataTableName, "llgzPage_form"); 
			}
			function llgzTabsChange(tabName){
				if(tabName == "a_unSendBySpl"){
					$("#lldgzMemoBut").removeAttr("disabled");
				}else{
					$("#lldgzMemoBut").attr("disabled",true);
				}
			}
			function doLldgzMemo(){
				var activeTab = $("#llgzTbasUl li[class='active']").children().attr("id");
				if(activeTab == "a_unSendBySpl"){
					var ret = selectDataTableRow("unSendBySplDataTable");
					if(ret){
		 	        	Global.Dialog.showDialog("llgzMemo",{
			        	  	title:"领料跟踪--备注",
			        	  	url:"${ctx}/admin/itemApp/goLlgzMemo",
			        	  	postData:{
			        	  		no:ret.no
			        	  	},
			        	  	height: 350,
			        	  	width:800,
			        	  	returnFun:function(){
								$("#unSendBySplDataTable").jqGrid("setGridParam",{
									url:"${ctx}/admin/itemApp/goUnSendBySplJqGrid",
									postData:{
										itemType1:$("#unSendBySplItemType1").val(),
										itemType2:$("#unSendBySplItemType2").val(),
										arriveDateFrom:$("#arriveDateFrom").val(),
										arriveDateTo:$("#arriveDateTo").val(),
										mainManCode:$("#unSendBySplMainManCode").val(),
										supplCode:$("#unSendBySplSupplCode").val()
									},
									page:1,
									sortname:""
								}).trigger("reloadGrid");
							}
			        	}); 
					}else{
						art.dialog({
							content:"请选择一条记录"
						});
					}
				}
			}
			function goLlgzView(){
				var activeTab = $("#llgzTbasUl li[class='active']").children().attr("id");
				var dataTableName = "";
				if(activeTab == "a_unCheck"){
					dataTableName += "unCheckDataTable";
				}else if(activeTab == "a_confirmReturn"){
					dataTableName += "confirmReturnDataTable";
				}else if(activeTab == "a_unReceiveBySpl"){
					dataTableName += "unReceiveBySplDataTable";
				}else{
					dataTableName += "unSendBySplDataTable";
				}
				
				var ret = selectDataTableRow(dataTableName);
				
				if(ret){
		        	Global.Dialog.showDialog(ret.type=="S"?"itemAppSend":"itemAppReturn",{
		        	  title:ret.type=="S"?"领料单--查看":"领料退回--查看",
		        	  url:"${ctx}/admin/itemApp/goLlgzView",
		        	  postData:{
		        	  	type:ret.type,
		        	  	no:ret.no
		        	  },
		        	  height: 730,
		        	  width:ret.type=="S"?1400:1300,
		        	});
				}else{
					art.dialog({
						content:"请选择一条记录"
					});
				}
			}
		</script>
	</head>
	<body>
	
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button id="viewBut" type="button" class="btn btn-system" onclick="goLlgzView()">查看</button>	
						<button id="lldgzMemoBut" type="button" class="btn btn-system" onclick="doLldgzMemo()" disabled>领料单跟踪备注</button>
						<button id="excelBut" type="button" class="btn btn-system" onclick="doLlgzExcel()">输出到Excel</button>	
						<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>	
					</div>
				</div>
			</div>
		</div>
		
		<form action="" method="post" id="llgzPage_form">
			<input type="hidden" name="jsonString" value="" />
		</form>
		<ul id="llgzTbasUl" class="nav nav-tabs" >
		    <li class="active"><a id="a_unCheck" href="#unCheck" data-toggle="tab" onclick="llgzTabsChange('a_unCheck')">未审核领料单</a></li>  
		    <li><a id="a_confirmReturn" href="#confirmReturn" data-toggle="tab" onclick="llgzTabsChange('a_confirmReturn')">审核退回领料单</a></li> 
		    <li><a id="a_unReceiveBySpl" href="#unReceiveBySpl" data-toggle="tab" onclick="llgzTabsChange('a_unReceiveBySpl')">供应商未接收领料单</a></li> 
		    <li><a id="a_unSendBySpl" href="#unSendBySpl" data-toggle="tab" onclick="llgzTabsChange('a_unSendBySpl')">供应商未发货领料单</a></li>  
		</ul>  
	    <div class="tab-content" >  
			<div id="unCheck"  class="tab-pane fade in active"> 
				<jsp:include page="itemApp_llgz_unCheck.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="confirmReturn"  class="tab-pane fade" > 
				<jsp:include page="itemApp_llgz_confirmReturn.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="unReceiveBySpl"  class="tab-pane fade"> 
				<jsp:include page="itemApp_llgz_unReceiveBySpl.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="unSendBySpl"  class="tab-pane fade"> 
				<jsp:include page="itemApp_llgz_unSendBySpl.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
		</div>	
	</body>
</html>

