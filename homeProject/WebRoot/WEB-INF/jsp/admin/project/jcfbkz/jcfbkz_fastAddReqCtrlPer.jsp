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
	<title>特定发包快速新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>	
	<body>
		<div class="body-box-list">
  			<div class="content-form">
				<div class="panel panel-system" >
					<div class="panel-body" >
						<div class="btn-group-xs" >
							<button type="button" class="btn btn-system " id="saveBtn">
								<span>保存</span>
							</button>
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWindows()">
								<span>关闭</span>
							</button>
						</div>
					</div>
				</div>
  				<div class="panel panel-info" >  
					<div class="panel-body">			 
						<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
							<ul class="ul-form">
								<li class="form-validate">
									<label>基础项目</label>
									<input type="text" id="baseItemCode" name="baseItemCode" style="width:160px;" value="${customer.baseItemCode}"/>
								</li>
								<li>
									<label>分包比例</label>
									<input type="text" id="baseCtrlPer" name="baseCtrlPer" style="width:160px;"
									 	onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${customer.baseCtrlPer}"/>
								</li>
								<li>
									<label class="control-textarea" >补贴说明</label>
									<textarea id="remarks" name="remarks" rows="4" >${customer.remarks }</textarea>
								</li>
							</ul>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_baseItem.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var selectRows = [];
$(function(){
	var codes = $.trim("${customer.codes}");
	$("#baseItemCode").openComponent_baseItem({showValue:"${customer.baseItemCode}",showLabel:"${customer.descr}"});	

	$("#saveBtn").on("click",function(){
		var exists = false;
		if($("#baseItemCode").val()==""){
			art.dialog({
				content:"请选择基础项目",
			});
			return;
		}
		if($("#baseCtrlPer").val()==""){
			art.dialog({
				content:"请填写分包比例",
			})
			return;
		}
		if(codes!=""){
			codeList = $.trim(codes).split(",");
			for(var i = 0 ;i<=codeList.length;i++){
				if($.trim($("#baseItemCode").val())==$.trim(codeList[i])){
					exists =  true;
					break;
				}
			}
		}
		if(exists == true){
			art.dialog({
				content:"已存在该基础项目,不允许插入重复的特定基础分包比例!",
			});
			return;
		}else{
			codes = codes+","+$.trim($("#baseItemCode").val());
		}
		var datas=$("#page_form").jsonForm();
		datas.baseItemDescr=$("#openComponent_baseItem_baseItemCode").val().split("|")[1];
		selectRows.push(datas);
		$("#baseItemCode").val("");
		$("#baseCtrlPer").val("");
		$("#remarks").val("");
		$("#openComponent_baseItem_baseItemCode").val("");
		
	}); 
});	
function closeWindows(){
	var arr = [];
	var arrTemp = [];
	$.each(selectRows,function(k,v){
		arr.push(v);
	});
	Global.Dialog.returnData = arr;
	closeWin();
}

</script>
	</body>
</html>
