<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改CustCon</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
//验证
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/custCon/doUpdate',
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
}

//校验函数
$(function() {
	$("#conMan").openComponent_employee({showLabel:"${custCon.conManDescr}",showValue:"${custCon.conMan}",callBack:function(){validateRefresh('openComponent_employee_conMan');}});
	if("${custCon.custCode}"!=""){
		$("#custCode").openComponent_customer({
			showLabel:"${custCon.descr}",
			showValue:"${custCon.custCode}",
			readonly:"${custCon.resrCustCode}"==""?false:true,//存在资源客户编号，不能修改意向客户
			callBack:fillData
		});
	}
	$("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
             
                validating : 'glyphicon glyphicon-refresh'
            },
                fields: {  
	        openComponent_customer_custCode: {  
	        validators: {  
	            notEmpty: {  
	            message: '客户编号不能为空'  
	            },
	             remote: {
		            message: '',
		            url: '${ctx}/admin/customer/getCustomer',
		            data: getValidateVal,  
		            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		        }     
	        }  
	       },
	       remarks:{
                validators:{
                    notEmpty: {
                        message: '联系内容不能为空'
                    },
                    stringLength : {
                        min :'${minLength}',
                        message : '联系内容长度不能小于'+'${minLength}'
                    }
                }
            },
	        openComponent_employee_conMan: {  
	        validators: {  
	            notEmpty: {  
	            message: '联系人不能为空'  
	            },
	             remote: {
		            message: '',
		            url: '${ctx}/admin/employee/getEmployee',
		            data: getValidateVal,  
		            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
		        }     
	        }  
	       }   
    	},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();
   		
	});
});
function fillData(ret){
	$("#custAddress").val(ret.address);
	$("#designManDescr").val(ret.designmandescr?ret.designmandescr:ret.designManDescr);
	$("#businessManDescr").val(ret.businessmandescr?ret.businessmandescr:ret.businessManDescr);
	validateRefresh('openComponent_customer_custCode');
}


function hideExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}

</script>

</head>
    
<body>
<div class="body-box-form" >
<div class="panel panel-system">
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "   onclick="validateDataForm()">保存</button>
      <button type="button" class="btn btn-system "  onclick="closeWin()">关闭</button>
      </div>
   </div>
	</div>
		 <div class="panel panel-info">  
                <div class="panel-body">
                    <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
						<house:token></house:token>
						<input type="hidden" name="m_umState" id="m_umState" value="M"/>
						<input type="hidden" id="pk" name="pk" value="${custCon.pk}" />
						<input type="hidden" id="expired" name="expired" value="${custCon.expired}" />
						<ul class="ul-form">
							<div class="validate-group">
								<c:if test="${custCon.custCode!=''}">
									<li class="form-validate" >
										<label><span class="required">*</span>客户编号</label>
										  <input type="text" id="custCode" name="custCode" class="form-control" value="${custCon.custCode}">	
									</li>
									<li >
										<label>楼盘</label>
										<input readonly="readonly" type="text" id="custAddress" name="custAddress"   value="${custCon.custAddress}" />
									</li>
								</c:if>
								<c:if test="${custCon.custCode==''}">
									<li class="form-validate" >
										<label>客户编号</label>
										  <input type="text" id="resrCustCode" name="resrCustCode" class="form-control" readonly value="${custCon.resrCustCode}">	
									</li>
									<li >
										<label>楼盘</label>
										<input readonly="readonly"  type="text" id="resrAddress" name="resrAddress"   value="${custCon.resrAddress}" />
									</li>
								</c:if>
								<li  >
									<label >设计师</label>
									<input readonly="readonly"  type="text" id="designManDescr" name="designManDescr"   value="${custCon.designManDescr}" />
								</li>
							</div>
							<div class="validate-group">
								<li>
									<label>业务员</label>
									<input readonly="readonly" Descr="NameChi"  type="text" id="businessManDescr" name="businessManDescr"   value="${custCon.businessManDescr}" />
								</li>
								
								<li class="form-validate" >
									<label ><span class="required">*</span>联系时间 </label>
									<input type="text"   style="width:160px;" id="conDate" name="conDate" class="i-date" value="<fmt:formatDate value='${custCon.conDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" onchange="validateRefresh('conDate')"  required data-bv-notempty-message="联系时间不能为空"  />
								</li>
								<li class="form-validate">
									<label  ><span class="required">*</span>联系人</label>
									<input type="text" id="conMan" name="conMan"  value="${custCon.conManDescr}"    />
								</li>
							</div>
							<div class="validate-group">
								<li>
									<label >下次联系时间 </label>
									<input type="text" id="nextConDate" name="nextConDate" class="i-date" value="<fmt:formatDate value='${custCon.nextConDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>跟踪方式</label>
									<house:xtdm id="conWay" dictCode="CONWAY" value="${custCon.conWay }" ></house:xtdm>
								</li>
							</div>
							<div class="validate-group">
								<li  class="form-validate">
									<label class="control-textarea"><span class="required">*</span>联系内容</label>
									<textarea   id="remarks" name="remarks"  rows="3">${custCon.remarks }</textarea>
								</li>
								<li>
									<label></label>
									<input type="checkbox" id="expired_show" name="expired_show" value="${custCon.expired}" onclick="hideExpired(this)" ${custCon.expired=='T'?'checked':'' }/>过期
								</li>
							</div>
						</ul>   
		            </form>
                </div>  
         
        </div> 
</div>
</body>
</html>
