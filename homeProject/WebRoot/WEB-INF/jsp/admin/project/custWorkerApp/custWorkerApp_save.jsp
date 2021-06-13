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
	<title>工程部工人申请新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	$("#custCode").openComponent_customer({callBack:getData,condition:{custWorkerCustStatus:'4',purchStatus:'4'}});
			
			$("#page_form").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					workType12:{  
						validators: {  
							notEmpty: {  
								message: '必填字段'  
							}
						}  
					},
					openComponent_customer_custCode:{  
				        validators: {  
				            notEmpty: {  
				         	   message: '客户编号不能为空'  
				            },
				            remote: {
					            message: '',
					            url: '${ctx}/admin/customer/getCustomer',
					            data: getValidateVal,  
					            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
					        }
				        }  
				     },
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
			});	
	function getData(data){
		if(!data) return;
		$('#address').val(data.address);
		$('#page_form').data('bootstrapValidator')  
                    .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
                    .validateField('openComponent_customer_custCode');  
		if(data.projectman==''||data.projectman==null){
			$("#openComponent_employee_projectMan").val('');
			$('#projectMan').val('');
		}else{
			$('#projectMan').val(data.projectman);
			$.ajax({
				url:"${ctx}/admin/CustWorkerApp/ajaxGetProjectManDescr",
				type:'post',
				data:{no:data.projectman},
				dataType:'json',
				cache:false,
				error:function(obj){
					
					showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
				},
				success:function(obj){
					if (obj){
						$("#projectMan").openComponent_employee({showValue:obj.projectMan,showLabel:obj.projectManDescr,readonly:true});
					}
				}
			});
		}
	}		
			
			
});

$(function(){
	$("#projectMan").openComponent_employee({readonly:true});

	$("#saveBtn").on("click",function(){
		var projectMan = $.trim($("#projectMan").val());
		var custCode = $.trim($("#custCode").val());
		
		if(custCode!=''&&custCode!=null){
			if(projectMan==''||projectMan==null){
				art.dialog({
					content:'该楼盘不存在项目经理,不允许保存',
				});
				return false;
			}
		}
			$("#page_form").bootstrapValidator('validate');
			if(!$("#page_form").data('bootstrapValidator').isValid()) return;
			var datas = $("#page_form").serialize();
		$.ajax({
			url:'${ctx}/admin/CustWorkerApp/doSave',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		if(obj.msg=='保存成功'){
			    		art.dialog({
							content: obj.msg,
							time: 1000,
							beforeunload: function () {
								 $("#openComponent_customer_custCode").val('');
								 $("#custCode").val('');
								 $("#appComeDate").val('');
								 $("#address").val('');
								 $("#remarks").val('');
								 $("#workType12_NAME").val('');
								 $("#workType12").val('');
								 $("#openComponent_employee_projectMan").val('');
								 $('#projectMan').val('');		    				
						    }
						});
		    		}else{
		    			 
		    		}
		    	}else{
					$("#_form_token_uniq_id").val(obj.token.token);
		    		if(obj.msg=="已安排工人"){
			    		art.dialog({
							content: "该楼盘存在待安排的同种工人申请单，无法再次申请",
							width: 200
						});
		    		}else{
						datas = $("#page_form").serialize();
						art.dialog({
							content:obj.msg,
							lock: true,
							width: 200,
							height: 100,
							ok: function () {
								$.ajax({
									url:'${ctx}/admin/CustWorkerApp/doSave2',
									type:'post',
									data:datas,
									dataType:'json',
									cache:false,
									error:function(obj){
										showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
									},
									success:function(obj){
										art.dialog({
											content: obj.msg,
											time: 1000,
											beforeunload: function () {
								    			$("#openComponent_customer_custCode").val('');
												 $("#custCode").val('');
												 $("#workType12_NAME").val('');
												 $("#appComeDate").val('');
												 $("#address").val('');
												 $("#workType12").val('');
										    }
										});
									}
								});
							},
							cancel: function () {
									return true;
							}
						});
		    		}
		    	}
		    }
		 });
	});
	
});

function exitPage(){
	closeWin();
}
function validateRefresh(){
	 $('#page_form').data('bootstrapValidator')
			 .updateStatus('appDate', 'NOT_VALIDATED',null)
			 .validateField('appDate')
			 .updateStatus('appComeDate', 'NOT_VALIDATED',null)
			 .validateField('appComeDate'); 
}
function validateRefresh_custCode(){
	 $('#page_form').data('bootstrapValidator')  
                    .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
                    .validateField('openComponent_customer_custCode');  
}
function getMinDate(){
	var minDate = new Date("${custWorkerApp.appDateFrom}");
    return formatDate(minDate);
}
function getMaxDate(){
	var maxDate = new Date("${custWorkerApp.appDateTo}");
	return formatDate(maxDate);
}

</script>
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
			  			<house:token></house:token>
						<ul class="ul-form">
						<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorkerApp.custCode }" />
							</li>
							<li>
								<label><span class="required">*</span>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }" readonly="true" />
							</li>
						</div>	
						<div class="validate-group row" >
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${custWorkerApp.projectMan }" readonly="true" />
							</li>
							<%-- <li class="form-validate">
								<label><span class="required">*</span>工种</label>
								<house:DataSelect id="WorkType12" className="WorkType12" keyColumn="code" valueColumn="descr"  ></house:DataSelect>
							</li> --%>
							<li class="form-validate">
								<label><span class="required">*</span>工种</label>
								<house:dict id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
								(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
								or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
									select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
									(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
									 ) " sqlValueKey="Code" sqlLableKey="Descr"  value="${prjConfirmApp.workType12 }"></house:dict>
							</li>
						</div>	
						<div class="validate-group row" >							
							<li class="form-validate">
								<label><span class="required">*</span>申请进场时间</label>
								<input type="text" id="appComeDate" required data-bv-notempty-message="申请进场时间不能为空" name="appComeDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({onpicked:validateRefresh(),skin:'whyGreen',dateFmt:'yyyy-MM-dd', minDate: '#F{getMinDate()}', maxDate: '#F{getMaxDate()}'})" value="<fmt:formatDate value='${custWorkerApp.appComeDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:160px;" value="1" disabled="true"></house:xtdm>
							</li>
						</div>	
						<div class="validate-group row" >							
							<li class="form-validate">
								<label><span class="required">*</span>申请时间</label>
								<input type="text" id="appDate" required data-bv-notempty-message="申请时间不能为空" name="appDate" class="i-date" style="width:160px;" onFocus="WdatePicker({onpicked:validateRefresh(),skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value='${custWorkerApp.appDate}' pattern='yyyy-MM-dd HH:mm:ss HH:mm:ss'/>" />
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${custWorkerApp.remarks }</textarea>
							</li>
						</div>	
							
				</form>
				</div>
				</div>
			</div>
		</div>
	</body>	
</html>
