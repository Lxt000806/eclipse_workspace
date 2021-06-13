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
	<title>工程部工人申请修改申请</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	function getAddress(data){
		if(!data) return;
		$('#page_form').data('bootstrapValidator')  
                    .updateStatus('openComponent_customer_custCode', 'NOT_VALIDATED',null)  
                    .validateField('openComponent_customer_custCode');  
		$('#address').val(data.address);
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
	
	$("#projectMan").openComponent_employee({showValue:'${custWorkerApp.projectMan}',showLabel:'${custWorkerApp.projectManDescr}',readonly:true});
	$("#custCode").openComponent_customer({callBack:getAddress,showValue:'${custWorkerApp.custCode}',showLabel:'${custWorkerApp.custDescr}',condition:{purchStatus:'4'}});
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
});

$(function(){
	
//$("#custCode").openComponent_customer({callBack:getAddress,showValue:'${custWorkerApp.custCode}',showLabel:'${custWorkerApp.custDescr}',condition:{purchStatus:'4'}});
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
			url:'${ctx}/admin/CustWorkerApp/doUpdateApp',
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
						content: obj.msg,
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
	});
	
});
function validateRefresh(){
	 $('#page_form').data('bootstrapValidator')  
                    .updateStatus('appDate', 'NOT_VALIDATED',null)  
                    .validateField('appDate') 
   	 $('#page_form').data('bootstrapValidator')  
                    .updateStatus('appComeDate', 'NOT_VALIDATED',null)  
                    .validateField('appComeDate') 
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li hidden="true">
								<label><span class="required">*</span>pk</label>
								<input type="text" id="pk" name="pk" style="width:160px;" value="${custWorkerApp.pk }" />
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="${custWorkerApp.custCode }" />
							</li>
							<li>
								<label><span class="required">*</span>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${custWorkerApp.address }" readonly="true"/>
							</li>
							<li>
								<label>项目经理</label>
								<input type="text" id="projectMan" name="projectMan" style="width:160px;" value="${custWorkerApp.projectMan }" readonly="true" />
							</li>
							<li class="form-validate"	>
								<%-- <label><span class="required">*</span>工种</label>
								<house:DataSelect id="workType12" className="WorkType12" keyColumn="code" valueColumn="descr" value="${custWorkerApp.workType12 }"></house:DataSelect>
								 --%>
								 <label>工种</label>
								<house:dict id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
								(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
								or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
									select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
									(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
									 ) " sqlValueKey="code" sqlLableKey="descr" value="${custWorkerApp.workType12}" ></house:dict>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm  id="status" dictCode="WORKERAPPSTS"   style="width:160px;" value="1" disabled="true"></house:xtdm>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>申请进场时间</label>
								<input type="text" id="appComeDate" onchange="validateRefresh()" required data-bv-notempty-message="申请时间不能为空"  name="appComeDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custWorkerApp.appComeDate}' pattern='yyyy-MM-dd '/>" />
							</li>
							<li  class="form-validate">
								<label><span class="required">*</span>申请时间</label>
								<input type="text" id="appDate" onchange="validateRefresh()" required data-bv-notempty-message="申请时间不能为空" name="appDate" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<fmt:formatDate value='${custWorkerApp.appDate}' pattern='yyyy-MM-dd hh:mm:ss'/>" />
							</li>
							<li>
								<label class="control-textarea"> 备注</label>
								<textarea id="remarks" name="remarks" rows="2">${custWorkerApp.remarks }</textarea>
							</li>
						</ul>	
				</form>
				</div>
				</div>
			</div>
		</div>
	</body>	
</html>
