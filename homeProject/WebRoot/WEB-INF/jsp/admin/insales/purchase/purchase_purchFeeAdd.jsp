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
	<title>添加采购费用</title>
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
							<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
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
									<label><span class="required">*</span>费用类型</label>
									<house:dict id="feeType" dictCode="" sql="select a.code Code,a.descr Descr from tSupplFeeType a" 
										sqlValueKey="code" sqlLableKey="descr" value="${purchaseFee.feeType}"></house:dict>
								</li>
								<li>
									<label><span class="required">*</span>总价</label>
									<input type="text" id="amount" name="amount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${purchaseFee.amount}"/>
								</li>
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${purchaseFee.remarks }</textarea>
	  							</li>
							</ul>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#saveBtn").on("click",function(){
		if($("#feeType").val()==""){
			art.dialog({
				content:"请选择费用类型",
			});
			return;
		}
		if($("#amount").val()==""){
			art.dialog({
				content:"请填写总价",
			})
			return;
		}
		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		datas.feeTypeDescr=$("#feeType option:selected").text();
	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	}); 
});	
</script>
	</body>
</html>
