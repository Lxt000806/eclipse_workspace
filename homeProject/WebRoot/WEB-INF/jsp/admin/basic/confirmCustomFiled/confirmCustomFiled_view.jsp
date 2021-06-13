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
	<title>验收填写值新增</title>
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
	chgType();
});

function chgType(){
	var type = $("#type").val();
	if(type == "1"){
		$("#options_li").show();
	} else {
		$("#options_li").hide();
	}
}
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
						<input type="hidden" name="expired" id="expired" value="${confirmCustomFiled.expired }" />
						<ul class="ul-form">
	                        <div class="validate-group row">
								<li class="form-validate">
									<label>编号</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${confirmCustomFiled.code }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>填写项目名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${confirmCustomFiled.descr }"/>
								</li>
							</div>
	                        <div class="validate-group row">
								<li>
		                            <label>施工项目</label>
		                            <house:dict id="prjItem" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
									    sql="select Code, Descr  from tPrjItem1 where Expired = 'F' order by Seq" value="${confirmCustomFiled.prjItem}"></house:dict>
		                        </li>
								<li class="form-validate">
									<label>填写类型</label>
									<house:xtdm id="type" dictCode="CONFFILEDTYPE"  style="width:160px;" value="${confirmCustomFiled.type }" onchange="chgType()"></house:xtdm>
								</li>
							</div>
	                        <div class="validate-group row">
		                        <li id="options_li">
		                            <label>列表项目</label>
									<input type="text" id="options" name="options" style="width:160px;" value="${confirmCustomFiled.options }"/>
		                        </li>
		                        <li>
		                            <label>顺序</label>
									<input type="text" id="dispSeq" name="dispSeq" style="width:160px;" value="${confirmCustomFiled.dispSeq }"/>
		                        </li>
							</div>
	                        <div class="validate-group row">
								<li>
		                            <label>过期</label>
		                            <input type="checkbox" name="expiredCheckbox"
		                                   ${confirmCustomFiled.expired == 'T' ? 'checked' : ''} onclick="checkExpired(this)"/>
		                        </li>
							</div>
						</ul>	
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
