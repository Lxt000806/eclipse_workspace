<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system view" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="code" id="code" value="${resrCust.code }" />
					<ul class="ul-form">
						<div class="validate-group row " >
							<li><label>创建人</label> <input
								type="text" id="crtCzy" name="crtCzy" style="width:160px;"
								value="${resrCust.crtCzy }" />
							</li>
							<li>
								<label>创建时间</label>
								<input type="text" id="crtDate" name="crtDate" class="i-date" value="<fmt:formatDate value='${resrCust.crtDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly />
							</li>
						</div>
						<div class="row">
							<li>
								<label>跟单人员</label>
								<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${resrCust.businessMan }" />
							</li>
							<li>
								<label>派单时间</label>
								<input type="text" id="dispatchDate" name="dispatchDate" class="i-date" value="<fmt:formatDate value='${resrCust.dispatchDate}' pattern='yyyy-MM-dd HH:mm:ss' />" readonly/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${resrCust.descr }" readonly/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"  value="${resrCust.address }" readonly/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>手机号码</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;" value="${resrCust.mobile1 }" readonly/>
							</li>
							<li>
								<label>手机号码2</label>
								<input type="text" id="mobile2" name="mobile2" style="width:160px;" value="${resrCust.mobile2 }" readonly/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>客户类别</label>
								<house:xtdm id="constructType" dictCode="CUSTCLASS" value="${resrCust.constructType }" disabled="true"></house:xtdm>                     
							</li>
							<li>
								<label>客户分类</label>
								<house:xtdm id="custKind" dictCode="CUSTKIND" value="${resrCust.custKind }" disabled="true"></house:xtdm>      
							</li>
						</div>
						<div class="row">
							<li>
								<label>客户来源</label>
								<house:xtdm id="source" dictCode="CUSTOMERSOURCE" value="${resrCust.source }" disabled="true" ></house:xtdm>
							</li>
							<li>
								<label>网络渠道</label>
								<house:dict id="netChanel" dictCode="" 
									sql="select code,code+' '+descr descr from tCustNetCnl where expired= 'F' order by dispSeq " value="${resrCust.netChanel.trim() }" sqlValueKey="Code" sqlLableKey="Descr" disabled="true"></house:dict>
							</li>
						</div>
						<div class="row">
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remark" name="remark" readonly>${resrCust.remark}</textarea>
							</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#tabView_item" data-toggle="tab">意向客户信息</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_item" class="tab-pane fade in active">
						<div class="panel-body">
							<input type="hidden" name="jsonString" value="" />
							<ul class="ul-form">
								<div class="validate-group row">
									<li>
										<label>意向客户</label> 
										<input type="text" id="custCode" name="custCode"/>
									</li>
									<li>
										<label>意向楼盘</label> 
										<input type="text" id="custAddress" name="custAddress" style="width:160px;" readonly/>
									</li>
								</div>
								<div class="row">
									<li>
										<label>意向客户状态</label>
										<house:xtdm id="status" dictCode="CUSTOMERSTATUS"  ></house:xtdm>
									</li>
									<li>
										<label>到店时间</label> 
										<input type="text" id="visitDate" name="visitDate" style="width:160px;" class="i-date" readonly/>
									</li>
								</div>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		function save(){
			var custCode=$("#custCode").val();
			if(custCode==""){
				art.dialog({
					content: "请选择意向客户编号！",
				});
				return;
			}
			var result=hasCustCode();
			if(result=="1"){
				art.dialog({
					 content:'该资源客户存在关联信息，是否继续覆盖？',
					 lock: true,
					 ok: function () {
			  			doSave();
				    	return true;
					},
					cancel: function () {
						return true;
					}
				});
			}else{
				doSave();
			}
		}
		$("#custCode").openComponent_customer({
			condition:{resrCustCode:"${resrCust.code }"},
			callBack:function(data){
				$("#status").removeAttr("disabled");
				$("#custAddress").val(data.address);
				$("#status").val(data.status);
				$("#status").attr("disabled",true);
				$("#visitDate").val(data.visitdate);
			}
		});
		$("#businessMan").openComponent_employee({showValue:'${resrCust.businessMan}',showLabel:'${resrCust.businessManDescr}',readonly:true});	
		$("#crtCzy").openComponent_employee({showValue:'${resrCust.crtCzy}',showLabel:'${resrCust.crtCzyDescr}',readonly:true});	
	
	//是否已存在关联意向客户
	function hasCustCode(){
		var result="";
		$.ajax({
			url:'${ctx}/admin/ResrCust/hasCustCode',
			type: 'post',
			data: {code:$("#code").val()},
			dataType: 'text',
			cache: false,
			async:false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
				result=obj;
		    }	
		 });
		 return result;
	}
	
	function doSave(){
		$.ajax({
			url: "${ctx}/admin/ResrCust/doAddCustCode",
			type: "post",
			data: $("#page_form").jsonForm(),
			dataType: "json",
			cache: false,
			error: function(obj) {
				showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
			},
			success: function(obj) {
				if(obj.rs) {
					art.dialog({
						content: obj.msg,
						time: 700,
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
	}
	</script>
</body>
</html>
