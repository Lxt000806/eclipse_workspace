<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
function clzjChange(tabName){
	$("#clzjmx_title").html(tabName+"增减明细");
	var dataTableName = "";
	var colModels = $("#dataTable_clzj_zjmx").jqGrid("getGridParam","colModel");
	$.each(colModels,function(index,item){
		if(index > 0){
			if(index != 10){
				$("#dataTable_clzj_zjmx").jqGrid("showCol",item.name);
			}else{
				$("#dataTable_clzj_zjmx").jqGrid("hideCol",item.name);
			}
		}
	});
	if(tabName == "主材"){
		dataTableName = "dataTable_clzj_zczj";
		$("#dataTable_clzj_zjmx").jqGrid("hideCol",["iscommi","intproddescr","sqlcodedescr","cost"]);
	}else if(tabName == "服务性产品"){
		dataTableName = "dataTable_clzj_fwxcpzj";
		$("#dataTable_clzj_zjmx").jqGrid("hideCol",["intproddescr","sqlcodedescr","sqlcodedescr","cost"]);
	}else if(tabName == "集成"){
		dataTableName = "dataTable_clzj_jczj";
		$("#dataTable_clzj_zjmx").jqGrid("hideCol",["iscommi","sqlcodedescr","tmplineamount","cost","processcost"]);
	}else if(tabName == "软装"){
		dataTableName = "dataTable_clzj_rzzj";
		$("#dataTable_clzj_zjmx").jqGrid("hideCol",["iscommi","intproddescr","cost"]);
	} 
	$("#dataTable_clzj_zjmx").jqGrid("clearGridData");
	var ret = selectDataTableRow(dataTableName);
	if(ret){
		$("#dataTable_clzj_zjmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goClzjZjmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
	}
}
</script>
<div class="panel panel-info">
	<div  class="container-fluid" style="margin:5px 5px 0px 5px;">  
		<ul id="clzjUl" class="nav nav-tabs" >
		    <li class="active"><a id="clzj_zczj" href="#tabView_clzj_zczj" data-toggle="tab" onclick="clzjChange('主材')">主材增减</a></li>  
		    <li><a id="clzj_fwxcpzj" href="#tabView_clzj_fwxcpzj" data-toggle="tab" onclick="clzjChange('服务性产品')">服务性产品增减</a></li>  
		    <li><a id="clzj_jczj" href="#tabView_clzj_jczj" data-toggle="tab" onclick="clzjChange('集成')">集成增减</a></li>  
		    <li><a id="clzj_rzzj" href="#tabView_clzj_rzzj" data-toggle="tab" onclick="clzjChange('软装')">软装增减</a></li>  
		</ul>  
	    <div class="tab-content" >  
			<div id="tabView_clzj_zczj"  class="tab-pane fade in active"> 
				<jsp:include page="tabView_clzj_zczj.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_clzj_fwxcpzj"  class="tab-pane fade"> 
				<jsp:include page="tabView_clzj_fwxcpzj.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_clzj_jczj"  class="tab-pane fade"> 
				<jsp:include page="tabView_clzj_jczj.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_clzj_rzzj"  class="tab-pane fade"> 
				<jsp:include page="tabView_clzj_rzzj.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
		</div>	
	</div>	
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<div id="clzjmx_title" style="float:left">主材增减明细</div><button type="button" class="btn btn-system" onclick="doExcel('dataTable_clzj_zjmx')">导出excel</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" style="margin:0px 5px 5px 5px">
		<jsp:include page="tabView_clzj_zjmx.jsp">
			<jsp:param value="" name=""/>
		</jsp:include>
	</div>
</div>
