<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购入库</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <script type="text/javascript">
$(function() {
	document.getElementById("docPic").src = "${url}";	
	$("#code").openComponent_customer({showValue:"${customer.code}",showLabel:"${customer.descr}",readonly:true});
	$("#confirmCzyDescr").openComponent_employee({showValue:"${custDoc.confirmCZY}",showLabel:"${confirmCzyDescr }",readonly:true});
	$("#download").on("click",function(){
		window.open("${ctx}/admin/custDoc/download?docNameArr="+"${custDoc.docName}"+"&docType2Descr="+'变更图纸'+"&custCode="+"${custDoc.custCode}");
	});
});
</script>
  <body>
  	 <div class="body-box-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="download" >
						<span>下载</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<div class="validate-group row" >
							<li>
								<label>客户编号</label>
								<input type="text" id="code" name="code"  style="width:160px;"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true"/>
							</li>
							<li>
								<label>资料名称</label>
								<input type="text" id="docDescr" name="docDescr" style="width:160px;" value="${custDoc.descr }"readonly="true"/>
							</li>
							<li>
								<label>状态</label>
								<house:dict id="status" dictCode="" sql="select IBM Code,note Descr from txtdm where id ='PICPRGSTS' and cbm in ('1','2','3','4') " 
								sqlValueKey="Code" sqlLableKey="Descr" value="${custDoc.status }" disabled="true"></house:dict>
							</li>
							<li>
								<label>审核人员</label>
								<input type="text" id="confirmCzyDescr" name="confirmCzyDescr" style="width:160px;" value="${confirmCzyDescr }"/>
							</li>
							<li>
								<label>审核日期</label>
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" disabled="true" value="<fmt:formatDate value='${custDoc.confirmDate}' pattern='yyyy-MM-dd hh:MM:ss'/>" />
							</li>
							<li>
								<label class="control-textarea">审核说明备注</label>
								<textarea id="confirmRemark" name="confirmRemark" rows="2" readonly="true">${custDoc.confirmRemark }</textarea>
  							</li>
						</div>
 					</ul>	
  				</form>
  			</div>
  		</div>	
		<div style="width:46.5%; float:left; margin-left:0px; ">
			<img id="docPic" src=" " onload="AutoResizeImage(430,430,'docPic');" width="451" height="440" >  
		</div>	
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
	
</script>
  </body>
</html>

















