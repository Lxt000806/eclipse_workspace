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
	<title>施工合同管理转施工</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript">
	function saveBtn(){
		var endCode = $("#endCode").val();
		if("1"!=endCode){
			art.dialog({
				content:"确定要对["+"${customer.code}"+"]["+"${customer.descr}"+"]客户进行结转操作?",
				lock: true,
				width: 200,
				height: 90,
				okValue:"是",
				cancelValue:"否",
				ok: function () {
					doSave();
				},
				cancel: function () {
					return ;
				}
			});			
		}else{
			 $.ajax({
				url:"${ctx}/admin/customerSghtxx/checkDesignFee",
				type: "post",
				data: {custCode:$.trim("${customer.code}")},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
			    	if($.trim(obj)=="true"){
						art.dialog({
							content:"确定要对["+"${customer.code}"+"]["+"${customer.descr}"+"]客户进行结转么吗?",
							lock: true,
							width: 200,
							height: 90,
							okValue:"是",
							cancelValue:"否",
							ok: function () {
								doSave();
							},
							cancel: function () {
								return ;
							}
						});			
					}else{
						art.dialog({
							content:"设计费>=40元/㎡才允许结转纯设计!",
						});
						return;
					}
			    }
			});
		}
	}
	
	function doSave(){
		var datas = $("#dataForm").serialize();
		console.log(datas);
		 $.ajax({
			url:"${ctx}/admin/customerSghtxx/doSaveJz",
			type: "post",
			data: datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
					art.dialog({
						content:obj.msg,
						time:500,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					$("#saveBtn").removeAttr("disabled",true);
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="code" value="${customer.code }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
								<label><span class="required">*</span>结束代码</label>
								<house:dict id="endCode" dictCode=""
									 sql="select cbm ,rtrim(CBM)+' '+NOTE descr from tXTDM where ID='CUSTOMERENDCODE' AND CBM IN('1','2','5','6') ORDER BY IBM ASC  " 
									 sqlValueKey="cbm" sqlLableKey="descr"  value="${customer.endCode }"></house:dict>
							</li>
								<li>
									<label><span class="required">*</span>结转时间</label>
										<input type="text" id="endDate" name="endDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${customer.endDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
								</li>
							</div>	
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">结束说明</label>
									<textarea id="endRemark" name="endRemark" rows="3"></textarea>
	  							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
