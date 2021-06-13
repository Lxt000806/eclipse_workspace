<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
function lldChange(tabName){
	$("#lldmx_title").html(tabName+"领料明细");
	var dataTableName = "";
	if(tabName == "主材"){
		dataTableName = "dataTable_lld_zcll";
	}else if(tabName == "服务"){
		dataTableName = "dataTable_lld_fwll";
	}else if(tabName == "集成"){
		dataTableName = "dataTable_lld_jcll";
	}else if(tabName == "软装"){
		dataTableName = "dataTable_lld_rzll";
	} 
	$("#dataTable_lld_llmx").jqGrid("clearGridData");
	var ret = selectDataTableRow(dataTableName);
	if(ret){
		$("#dataTable_lld_llmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLldLlmxJqGrid",postData:{custCode:$("#code").val(),no:ret.no,},page:1}).trigger("reloadGrid");
	}
}
</script>
<div class="panel panel-info">
	<div  class="container-fluid" style="margin:5px 5px 0px 5px;">  
		<ul id="lldUl" class="nav nav-tabs" >
		    <li class="active"><a id="lld_zcll" href="#tabView_lld_zcll" data-toggle="tab" onclick="lldChange('主材')">主材领料</a></li>  
		    <li><a id="lld_fwll" href="#tabView_lld_fwll" data-toggle="tab" onclick="lldChange('服务')">服务领料</a></li>  
		    <li><a id="lld_jcll" href="#tabView_lld_jcll" data-toggle="tab" onclick="lldChange('集成')">集成领料</a></li>  
		    <li><a id="lld_rzll" href="#tabView_lld_rzll" data-toggle="tab" onclick="lldChange('软装')">软装领料</a></li>  
		</ul>  
	    <div class="tab-content" >  
			<div id="tabView_lld_zcll"  class="tab-pane fade in active"> 
				<jsp:include page="tabView_lld_zcll.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_lld_fwll"  class="tab-pane fade"> 
				<jsp:include page="tabView_lld_fwll.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_lld_jcll"  class="tab-pane fade"> 
				<jsp:include page="tabView_lld_jcll.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
			<div id="tabView_lld_rzll"  class="tab-pane fade"> 
				<jsp:include page="tabView_lld_rzll.jsp">
					<jsp:param value="" name=""/>
				</jsp:include>
			</div> 
		</div>	
	</div>	
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<div id="lldmx_title" style="float:left">主材领料明细</div><button type="button" class="btn btn-system" onclick="doExcel('dataTable_lld_llmx')">导出excel</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" style="margin:0px 5px 5px 5px">
		<jsp:include page="tabView_lld_llmx.jsp">
			<jsp:param value="" name=""/>
		</jsp:include>
	</div>
</div>
