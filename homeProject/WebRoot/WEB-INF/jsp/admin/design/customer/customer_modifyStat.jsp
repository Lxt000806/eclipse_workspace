<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改电话</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	//验证
	var datas = {code : '${customer.code}',status:$("#status").val()};
	$.ajax({
		url:'${ctx}/admin/customer/doModifyStat',
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
	$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				status:{
					validators:{
						notEmpty:{
							message:"请输入完整的信息"
						}
					}
				}
			}
		});

});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save()">保存</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<input type="hidden" id="oldPhone" name="oldPhone" value="${customer.mobile1 }"/>
					<ul class="ul-form">
						<li>
							<label><span class="required">*</span>客户编号</label>
							<input type="text" style="width:160px;" id="code" name="code" value="${customer.code }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>客户名称</label>
							<input type="text" style="width:160px;" id="descr" name="descr" value="${customer.descr }" readonly="readonly"/>
						</li>
						<li>
							<label><span class="required">*</span>楼盘</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${customer.address }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>客户状态</label>
							<c:if test="${customer.status=='2'}">
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" unShowValue="3,4,5" value="${customer.status }" ></house:xtdm>
							</c:if>
							<c:if test="${customer.status=='3'}">
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" unShowValue="4,5" value="${customer.status }" ></house:xtdm>
							</c:if>
							<c:if test="${customer.status=='1' or customer.status=='4' or customer.status=='5'}">
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" unShowValue="2,3" value="${customer.status }" disabled="true"></house:xtdm>
							</c:if>
						</li>
					</ul>
            	</form>
            </div>
          </div>
</div>
</body>
</html>
