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
	<title>基础分包控制</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	var workType1 = "";
	function doExcel(){
		var tableId="";
		if($.trim($("#jcys_tab").attr("class"))=="active"){
			tableId="dataTable";
		}else if($.trim($("#jczj_tab").attr("class"))=="active"){
			tableId="dataTableJczj";
		}else if($.trim($("#jczfb_tab").attr("class"))=="active"){
			tableId="dataTableJczfb";
		}else{
			tableId="dataTable";
		}	
		var url = "${ctx}/admin/jcfbkz/doExcel?tableId="+tableId;
		doExcelAll(url,tableId);
	}
	$(function(){
		$("#ctrlPer").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("ctrlPer",{
				title:"分包比例",
				url:"${ctx}/admin/jcfbkz/goCtrlPer",
				postData:{code:$("#code").val()},
				height:350,
				width:700,
				returnFun:goQuery
			});
		});
		
		$("#reqCtrlPer").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("reqCtrlPer",{
				title:"特定分包比例设置",
				url:"${ctx}/admin/jcfbkz/goReqCtrlPer",
				postData:{code:$("#code").val()},
				height:750,
				width:1000,
				returnFun:goQuery
			});
		});
		
		$("#butiePer").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("butiePer",{
				title:"分包补贴",
				url:"${ctx}/admin/jcfbkz/goButiePer",
				postData:{code:$("#code").val()},
				height:350,
				width:700,
				returnFun:goQuery
			});
		}); 
		
		$("#planDetail").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("planDetail",{
				title:"基础发包控制——预算分包明细",
				url:"${ctx}/admin/jcfbkz/goPlanDetail",
				postData:{code:$("#code").val(),workType1:$("#jcysWorkType1").val(),m_umState:"Y",no:$("#no").val()},
				height:730,
				width:1200,
				returnFun:goQuery
			});
		}); 
		
		$("#chgDetail").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("chgDetail",{
				title:"基础发包控制——增减分包明细",
				url:"${ctx}/admin/jcfbkz/goPlanDetail",
				postData:{code:$("#code").val(),workType1:$("#jczjWorkType1").val(),no:$("#no").val(),m_umState:"Z"},
				height:730,
				width:1200,
				returnFun:goQuery
			});
		}); 
		
		$("#allCtrlDetail").on("click",function(){
			if($.trim($("#code").val())==""){
				art.dialog({
					content:"请选择客户编号",
				})
				return;
			}
			Global.Dialog.showDialog("allCtrlDetail",{
				title:"基础发包控制——总发包明细",
				url:"${ctx}/admin/jcfbkz/goPlanDetail",
				postData:{no:$("#no").val(),code:$("#code").val(),workType1:$("#jczfbWorkType1").val(),m_umState:"X"},
				height:730,
				width:1200,
				returnFun:goQuery
			});
		}); 
		
		function gotoQuery(data){
			query_jcys();
			query_jczj();
			query_jczfb();
			$("#projectCtrlAdj").val("");
			$("#ctrlAdjRemark").val("");
			$("#address").val(data.address);
			
		}
		$("#code").openComponent_customer({condition:{status:'5'}, callBack:gotoQuery});
	});
	
	function goQuery(){
		query_jcys();
		query_jczj();
		query_jczfb();
		$("#projectCtrlAdj").val("");
		$("#ctrlAdjRemark").val("");
	}
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="workType1" name="workType1" style="width:160px;"/>
					<ul class="ul-form">
						<li>
							<label>客户编号</label>
							<input type="text" id="code" name="code" style="width:160px;"/>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;" readonly="true"/>
						</li>
						<li>
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goQuery();">查询</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
	   			<div class="btn-group-sm">
					<house:authorize authCode="JCFBKZ_CTRLPER">
						<button type="button" class="btn btn-system" id="ctrlPer"><span>分包比例</span>
						</button>
					</house:authorize>
					
					<house:authorize authCode="JCFBKZ_REQCTRLPER">
						<button type="button" class="btn btn-system" id="reqCtrlPer"><span>特定分包比例</span> 
						</button>
					</house:authorize>
					
					<house:authorize authCode="JCFBKZ_BUTIEPER">
						<button type="button" class="btn btn-system" id="butiePer"><span>分包补贴</span> 
						</button>
					</house:authorize>
					
					<button type="button" class="btn btn-system" id="planDetail"><span>预算明细</span> 
					</button>
					
					<button type="button" class="btn btn-system" id="chgDetail"><span>增减明细</span> 
					</button>
					
					<button type="button" class="btn btn-system" id="allCtrlDetail"><span>总发包明细</span> 
					</button>
					
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>			
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active" id = "jcys_tab"><a href="#tab_jcys" data-toggle="tab">基础预算</a></li>
		        <li class="" id = "jczj_tab"><a href="#tab_jczj" data-toggle="tab">基础增减</a></li>
			    <li class="" id = "jczfb_tab"><a href="#tab_jczfb" data-toggle="tab">基础总发包</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_jcys" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_jcys.jsp"></jsp:include>
		        </div> 
		        <div id="tab_jczj" class="tab-pane fade "> 
		         	<jsp:include page="tab_jczj.jsp"></jsp:include>
		        </div> 
		        <div id="tab_jczfb" class="tab-pane fade "> 
		         	<jsp:include page="tab_jczfb.jsp"></jsp:include>
		        </div> 
		    </div>  
	</body>	
</html>
