<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>报表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_mainCommi.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
		$(function(){
		 	if("${m_umState}"=="V"){
				$(".viewFlag").attr("disabled",true);
			}
			$("#no").openComponent_mainCommi();
		});
		//查询
		function doQuery(){
			goto_query("dlDataTable","goDlReportJqGrid");
			goto_query("fdlDataTable","goFdlReportJqGrid");
		}
		//重新加载表格 
		function goto_query(tableId,type){
			$("#"+tableId).jqGrid(
			 "setGridParam",{
				datatype:"json",
				postData:$("#page_form").jsonForm(),
				page:1,
				url:"${ctx}/admin/mainCommi/"+type
			 }
			).trigger("reloadGrid"); 
		}
		//打开的tab
		function activeTab(){
			var fdlActive=$("#tabFdl").attr("class");
			if(fdlActive=="active"){
				return "fdlDataTable";
			}
			return "dlDataTable";
		}
		//导出excel
		function doExcel(){
			var selectedTab=activeTab();
			if(selectedTab=="fdlDataTable"){
				doExcelNow("非独立销售","fdlDataTable");
			}else{
				doExcelNow("独立销售","dlDataTable");
			}
		}
		//清空
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#custType").val("");
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
		} 
		//回车键搜索
		function keyQuery(){
		 	if (event.keyCode==13)  //回车键的键值为13
		  	$("#qr").click(); //调用按钮
		}
	</script>
	</head>

<body onkeydown="keyQuery();">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button id="doExcel" type="button" class="btn btn-system"
						onclick="doExcel()">导出excel</button>
					<button id="closeBut" type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState"  />
				<ul class="ul-form">
					<div class="validate-group row">
						<li><label>统计周期</label> <input type="text" id="no" name="no" 
							 />
						</li>
						<li>
							<label>客户类型</label>
							<house:custTypeMulit id="custType" ></house:custTypeMulit>
						</li>
						<li>
						<label for="">分公司</label>
						<house:DictMulitSelect id="cmpCode" dictCode="" 
							sql="select rtrim(Code) Code,Desc1,Desc2 from tCompany where Expired<>'T' order by Code" 
							sqlLableKey="Desc2" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					</div>
					<div class="validate-group row">
						<li class="search-group-shrink">
								<button type="button" id="qr" class="btn btn-sm btn-system" onclick="doQuery()">查询</button>
								<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</div>
		</div>
		</ul>
		</form>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li id="tabFdl" class="active"><a
				href="#tab_Fdl" data-toggle="tab">非独立销售</a>
			</li>
			 <li id="tabDl" class=""><a
				href="#tab_Dl" data-toggle="tab">独立销售</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="tab_Fdl" class="tab-pane fade in active">
				<jsp:include page="mainCommi_tab_fdlReport.jsp"></jsp:include>
			</div>
			<div id="tab_Dl" class="tab-pane fade ">
				<jsp:include page="mainCommi_tab_dlReport.jsp"></jsp:include>
			</div>
		</div>
	</div> 
</body>
</html>
