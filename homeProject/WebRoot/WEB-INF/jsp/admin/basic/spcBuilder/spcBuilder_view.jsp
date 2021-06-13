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
	<title>专盘管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	$("#leaderCode").openComponent_employee({showValue:"${spcBuilder.leaderCode}",showLabel:"${leaderDescr}"});	

	var lastCellRowId;
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/builder/goSpcBuilderJqGrid",
		postData:{spcBuilder:$.trim($("#code").val())},
		height:$(document).height()-$("#content-list").offset().top-105,
		colModel : [
		  	{name:'Code', index:'Code', width:80, label:'项目编号', sortable:true ,align:"left"},
			{name:'Descr', index:'Descr', width:80, label:'项目名称', sortable:true ,align:"left"},
			{name:'Adress', index:'Adress', width:80, label:'楼盘地址', sortable:true ,align:"left"},
			{name:'regiondescr1', index:'regiondescr1', width:80, label:'区域', sortable:true ,align:"left"},
			{name:'regiondescr2', index:'regiondescr2', width:80, label:'二级区域', sortable:true ,align:"left"},
			{name:'delivcode', index:'delivnum', width:80, label:'批次号', sortable:true ,align:"left"},
			{name:'buildercode', index:'buildercode', width:300, label:'楼号', sortable:true ,align:"left"},
           ]
	});
		

	
		
});
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
								<li>
									<label>专盘编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${spcBuilder.code }"   readonly="readonly"/>
								</li>
								<li>
									<label>专盘名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${spcBuilder.descr }" readonly="readonly"/>
								</li>
								<li >
								<label>队长</label>
									<input type="text" id="leaderCode" name="leaderCode" style="width:160px;" value="${spcBuilder.leaderCode }"/>
								</li>
								<li class="form-validate">
									<label>楼盘类型</label>
									<house:xtdm id="type" dictCode="SPCBUILDERTYPE" value="${spcBuilder.type }"></house:xtdm>                     
								</li>
								<li >
									<label>项目类型</label>
									<house:xtdm id="builderType" dictCode="BUILDERTYPE" value="${spcBuilder.builderType }"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label>交房时间</label>
									<input type="text" id="delivDate" name="delivDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${spcBuilder.delivDate}' pattern='yyyy-MM-dd'/>"/>
								</li>
								<li >
									<label>总户数</label>
									<input type="text" id="totalQty" name="totalQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.totalQty }"/>
								</li>
								<li class="form-validate">
									<label>交房户数</label>
									<input type="text" id="delivQty" name="delivQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.delivQty }"/>
								</li>
								<li >
									<label>总开工户数</label>
									<input type="text" id="totalBeginQty" name="totalBeginQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.totalBeginQty }"/>
								</li>
								<li class="form-validate">
									<label>业主自装户数</label>
									<input type="text" id="selfDecorQty" name="selfDecorQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.selfDecorQty }"/>
								</li>
								<li >
									<label>装修公司开工户数</label>
									<input type="text" id="decorCmpBeginQty" name="decorCmpBeginQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmpBeginQty}"/>
								</li>
								<li class="form-validate">
									<label>装修公司1</label>
									<input type="text" id="decorCmp1" name="decorCmp1" style="width:160px;" value="${spcBuilder.decorCmp1 }"/>
								</li>
								<li >
									<label>套数1</label>
									<input type="text" id="decorCmp1Qty" name="decorCmp1Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp1Qty }"/>
								</li>
								<li class="form-validate">
									<label>装修公司2</label>
									<input type="text" id="decorCmp2" name="decorCmp2" style="width:160px;" value="${spcBuilder.decorCmp2 }"/>
								</li>
								<li >
									<label>套数2</label>
									<input type="text" id="decorCmp2Qty" name="decorCmp2Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp2Qty }"/>
								</li>
								<li class="form-validate">
									<label>装修公司3</label>
									<input type="text" id="decorCmp3" name="decorCmp3" style="width:160px;" value="${spcBuilder.decorCmp3 }"/>
								</li>
								<li >
									<label>套数3</label>
									<input type="text" id="decorCmp3Qty" name="decorCmp3Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp3Qty }"/>
								</li>
								<li class="form-validate">
									<label>装修公司4</label>
									<input type="text" id="decorCmp4" name="decorCmp4" style="width:160px;" value="${spcBuilder.decorCmp4 }"/>
								</li>
								<li >
									<label>套数4</label>
									<input type="text" id="decorCmp4Qty" name="decorCmp4Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp4Qty }"/>
								</li>
								<li class="form-validate">
									<label>装修公司5</label>
									<input type="text" id="decorCmp5" name="decorCmp5" style="width:160px;"  value="${spcBuilder.decorCmp5 }"/>
								</li>
								<li >
									<label>套数5</label>
									<input type="text" id="decorCmp5Qty" name="decorCmp5Qty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d]/g,'')" value="${spcBuilder.decorCmp5Qty }"/>
								</li>
								<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${spcBuilder.remarks }</textarea>
  								</li>
						</ul>	
				</form>
				</div>
			</div>
			<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_detail" data-toggle="tab">项目列表</a></li>
		    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
