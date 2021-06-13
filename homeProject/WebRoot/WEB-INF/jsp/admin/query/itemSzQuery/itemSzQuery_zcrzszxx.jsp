<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<title>主材软装收支信息</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp"%>
		<script type="text/javascript">
			function doExcel(tableName){
				var firstTab = $("#zcrzszxxUl li[class='active']").children().attr("id");
				var secondTab = "";
				if(firstTab == 'a_lld'){
					secondTab = $("#lldUl li[class='active']").children().attr("id");
				}else if(firstTab == 'a_clzj'){
					secondTab = $("#clzjUl li[class='active']").children().attr("id");
				}
				
				var dataTableName = "";
				var dataFormName = "page_form_zcrzszxx";
				var postDataUrl = "";
				if("a_zcrzszxx"==firstTab){
					dataTableName = "dataTable_zcrzszxx";
				}else if("a_lld"==firstTab){
					if("lld_zcll"==secondTab){
						dataTableName = "dataTable_lld_zcll";
					}else if("lld_fwll"==secondTab){
						dataTableName = "dataTable_lld_fwll";
					}else if("lld_jcll"==secondTab){
						dataTableName = "dataTable_lld_jcll";
					}else {
						dataTableName = "dataTable_lld_rzll";
						dataFormName = "page_form_lld_rz";
					}
				}else if("a_clzj"==firstTab){
					if("clzj_zczj"==secondTab){
						dataTableName = "dataTable_clzj_zczj";
					}else if("clzj_fwxcpzj"==secondTab){
						dataTableName = "dataTable_clzj_fwxcpzj";
					}else if("clzj_jczj"==secondTab){
						dataTableName = "dataTable_clzj_jczj";
					}else {
						dataTableName = "dataTable_clzj_rzzj";
					}
					
				}else if("a_rgfymx"==firstTab){
					dataTableName = "dataTable_rgfymx_zcrzszxx";
					dataFormName = "page_form_rgfymx_zcrzszxx";
				}else if("a_rzmx"==firstTab){
					dataTableName = "dataTable_rzmx";
				}else{
					dataTableName = "dataTable_cgd";
					dataFormName = "page_form_cgd";
				}
				
				if(tableName != undefined){
		        	var id = $("#"+dataTableName).jqGrid("getGridParam", "selrow");
				    if (id) {
				      var ret = $("#"+dataTableName).jqGrid("getRowData",id);
				      postDataUrl +="&no="+ret.no.trim();
				    }
				    dataTableName = tableName;
				}
				
				var url = "${ctx}/admin/itemSzQuery/doExcel_zcrzszxx?custCode="+$("#code").val()+"&firstTab="+firstTab+"&secondTab="+secondTab+postDataUrl;
				doExcelAll(url,dataTableName,dataFormName);
				
			}
			function check(str){
				if(str == "lld"){
					if($("#lld_zcll").parent().attr("class") == "active"){
						var ret = selectDataTableRow("dataTable_lld_zcll");
						if(ret){
							$("#dataTable_lld_llmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLldLlmxJqGrid",postData:{custCode:$("#code").val(),no:ret.no,},page:1}).trigger("reloadGrid");
						}
					}
				}else if(str == 'cgd'){
					var ids = $("#dataTable_cgd").jqGrid("getDataIDs");
					if(ids.length > 0){
						var ret = $("#dataTable_cgd").jqGrid("getRowData",ids[0]);
						$("#dataTable_cgdmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goCgdmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
					}
				}else if(str == 'clzj'){
					$("#dataTable_clzj_zjmx").jqGrid("hideCol",["iscommi","intproddescr","sqlcodedescr","cost"]);	
					var ret = selectDataTableRow("dataTable_clzj_zczj");
					if(ret){
						$("#dataTable_clzj_zjmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goClzjZjmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
					}
				}
			}
			window.onload = function(){
				if("${customer.costRight}" == "1"){	
					$("#dataTable_lld_zcll").jqGrid("showCol","clcost");
					$("#dataTable_lld_zcll").jqGrid("showCol","bcost");
					$("#dataTable_lld_fwll").jqGrid("showCol","clcost");
					$("#dataTable_lld_fwll").jqGrid("showCol","bcost");
					$("#dataTable_lld_jcll").jqGrid("showCol","clcost");
					$("#dataTable_lld_jcll").jqGrid("showCol","bcost");
					$("#dataTable_lld_rzll").jqGrid("showCol","clcost");
					$("#dataTable_lld_rzll").jqGrid("showCol","bcost");
					$("#dataTable_lld_llmx").jqGrid("showCol","bcost");
				}else{
					$("#dataTable_lld_zcll").jqGrid("hideCol","clcost");
					$("#dataTable_lld_zcll").jqGrid("hideCol","bcost");
					$("#dataTable_lld_fwll").jqGrid("hideCol","clcost");
					$("#dataTable_lld_fwll").jqGrid("hideCol","bcost");
					$("#dataTable_lld_jcll").jqGrid("hideCol","clcost");
					$("#dataTable_lld_jcll").jqGrid("hideCol","bcost");
					$("#dataTable_lld_rzll").jqGrid("hideCol","clcost");
					$("#dataTable_lld_rzll").jqGrid("hideCol","bcost");
					$("#dataTable_lld_llmx").jqGrid("hideCol","bcost");
					$("#dataTable_cgd").jqGrid("hideCol","amount");
					$("#dataTable_cgd").jqGrid("hideCol","secondamount");
					$("#dataTable_cgdmx").jqGrid("hideCol","unitprice");
					$("#dataTable_cgdmx").jqGrid("hideCol","amount");
				}
			};
		</script>
	</head>
	<body>
		<form action="" method="post" id="page_form_zcrzszxx">
			<input type="hidden" name="jsonString" value="" />
		</form>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
			  				<button id="doExcelBut" type="button" class="btn btn-system "   onclick="doExcel()">输出到Excel</button>
							<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<input type="hidden" id="code" name="code" value="${customer.code }" />
			<div  class="container-fluid" >  
				<ul id="zcrzszxxUl" class="nav nav-tabs" >
				    <li class="active"><a id="a_zcrzszxx" href="#tabView_zcrzszxx" data-toggle="tab">主材软装收支信息</a></li>  
				    <li><a id="a_lld" href="#tabView_lld" data-toggle="tab" onclick="check('lld')">领料单</a></li>  
				    <li><a id="a_clzj" href="#tabView_clzj" data-toggle="tab" onclick="check('clzj')">材料增减</a></li>  
				    <li><a id="a_rgfymx" href="#tabView_rgfymx" data-toggle="tab">人工费用明细</a></li>  
				    <li><a id="a_rzmx" href="#tabView_rzmx" data-toggle="tab">软装明细</a></li>  
				    <li><a id="a_cgd" href="#tabView_cgd" data-toggle="tab" onclick="check('cgd')">采购单</a></li>  
				</ul>  
			    <div class="tab-content" >  
					<div id="tabView_zcrzszxx"  class="tab-pane fade in active" style="border:0px;height:600px;"> 
			         	<jsp:include page="tabView_zcrzszxx.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_lld"  class="tab-pane fade" style="border:0px;height:600px"> 
			         	<jsp:include page="tabView_lld.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_clzj"  class="tab-pane fade" style="border:0px;height:600px"> 
		         		<jsp:include page="tabView_clzj.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_rgfymx"  class="tab-pane fade" style="border:0px;height:600px"> 
		         		<jsp:include page="tabView_rgfymx.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_rzmx"  class="tab-pane fade" style="border:0px;height:600px"> 
		         		<jsp:include page="tabView_rzmx.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="tabView_cgd"  class="tab-pane fade" style="border:0px;height:600px"> 
		         		<jsp:include page="tabView_cgd.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


