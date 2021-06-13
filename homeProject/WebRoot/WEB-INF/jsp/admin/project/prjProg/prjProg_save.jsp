<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>模板设定</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  	
  		<div class="content-form">
  			<!-- panelBar -->
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
  			<div class="infoBox" id="infoBoxDiv"></div>
		<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
         			<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;"  value="${customer.code}" readonly="readonly" />
							</li>
							<li>
								<label>面积</label>
							<input type="text" id="area" name="area" style="width:160px;"  value="${customer.area}" readonly="readonly"/>
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"  value="${customer.descr}" readonly="readonly"/>
							</li>
							<li>
								<label>客户类型</label>
								<house:xtdm id="custType" dictCode="CUSTTYPE"  style="width:160px;" value="${customer.custType }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT"  style="width:160px;" value="${customer.layout }" disabled="true"></house:xtdm>
							</li>
							<li>
								<label>施工工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:160px;"  value="${customer.constructDay}" readonly="readonly"/>
							</li>
							<li>
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${customer.address}" readonly="readonly"/>
							</li>
							<li>
								<label><span class="required">*</span>开工日期</label>
								<input type="text" id="confirmBegin" name="confirmBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${customer.confirmBegin}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label><span class="required">*</span>模板编号</label>
								<input type="text" id="prjProgTempNo" name="prjProgTempNo" style="width:160px;"  value="${customer.prjProgTempNo}" />
							</li>
							<li>
								<label><span class="required">*</span>模板名称</label>
							<input type="text" id="prjProgTempNoDescr" readonly="true" name="prjProgTempNoDescr" style="width:160px;"  value="${customer.prjProgTempNoDescr}" />
							</li>
							<li hidden="true">
								<label>isPrjProgTemp</label>
									<input type="text" id="isPrjProgTemp" name="isPrjProgTemp" style="width:160px;"  value="${customer.isPrjProgTemp}" readonly="readonly"/>
							</li>
							<li hidden="true">
								<label>projectManDescr</label>
									<input type="text" id="projectManDescr" name="projectManDescr" style="width:160px;"  value="${customer.projectManDescr}" readonly="readonly"/>
  							</li>
  							<li hidden="true">
								<label>prjProgTempType</label>
									<input type="text" id="prjProgTempType" name="prjProgTempType" style="width:160px;"  value="${customer.prjProgTempType}" readonly="readonly"/>
  							</li>
						</ul>
  				</form>
  				</div>
  				</div>
  		</div>
  	</div> 

<script src="${resourceRoot}/pub/component_prjProgTemp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	function getData(data){
		if (!data) return;
		if(data.CustType!="" && data.CustType!=$("#custType").val()){
			$("#openComponent_prjProgTemp_prjProgTempNo").val("");
			$("#openComponent_prjProgTemp_prjProgTempNoDescr").val("");
			art.dialog({
				content:"模板适用客户类型不匹配！",
			});
			return false;
		}
		$('#prjProgTempNoDescr').val(data.Descr);
		$('#prjProgTempType').val(data.Type);
	 } 
	$("#prjProgTempNo").openComponent_prjProgTemp({callBack:getData,showValue:'${customer.prjProgTempNo}'});
	
	//保存
	$("#saveBtn").on("click",function(){
			var prjProgTempNo = $.trim($("#prjProgTempNo").val());
			var isPrjProgTemp = $.trim($("#isPrjProgTemp").val());
			var projectManDescr = $.trim($("#projectManDescr").val());
		$.ajax({
			url:'${ctx}/admin/prjProg/isConfirm',
			type: 'post',
			data: {custCode:'${customer.code}'},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
				if(obj=='0'){
					var content = "";
					if(projectManDescr==null||projectManDescr==''){
						art.dialog({
							content:"无法保存，该楼盘未设置项目经理",
						});
						return false;
					} 
					
					if(prjProgTempNo==null||prjProgTempNo==''){
						art.dialog({
							content:"请输入工程进度模板模板信息",
						});
						return false;
					} 
					if (isPrjProgTemp==''||isPrjProgTemp==null){
						content="是否保存？";
					}else{
						content="该楼盘已存在工程进度，是否清除，重新生成？";
					}
					//验证
					var datas = $("#dataForm").serialize();
					 art.dialog({
							 content:content,
							 lock: true,
							 width: 200,
							 height: 100,
							 ok: function () {
						    	$.ajax({
									url:'${ctx}/admin/prjProg/doSave',
									type: 'post',
									data: datas,
									dataType: 'json',
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
								    },
								    success: function(obj){
								    	if(obj.rs){
								    		art.dialog({
												content: '保存成功',
												time: 1000,
												beforeunload: function () {
								    				closeWin();
											    }
											});
								    	}else{
								    		$("#_form_token_uniq_id").val(obj.token.token);
								    		art.dialog({
												content: obj.msg,
												width: 200
											});
								    	}
								    }
								 });
							},
							cancel: function () {
								return true;
							}
						});					
			
							}else{
								art.dialog({
									content:'该楼盘存在施工项目已验收/结束，不能修改模板',
								});
							}
					    }
					 });
		
		
	});
});
</script>
  </body>
</html>
