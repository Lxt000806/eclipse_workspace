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
	$.ajax({
		url:'${ctx}/admin/ResrCust/doUpdateCon',
		type: 'post',
		data: $("#dataForm").jsonForm(),
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
});

</script>

</head>
    
<body>
<div class="body-box-form" >
<div class="panel panel-system">
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "   onclick="save()">保存</button>
      <button type="button" class="btn btn-system "  onclick="closeWin(false)">关闭</button>
      </div>
   </div>
	</div>
		 <div class="panel panel-info">  
                <div class="panel-body">
                    <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
							<house:token></house:token>
							<input type="hidden" name="m_umState" id="m_umState" value="M"/>
							<input type="hidden" id="pk" name="pk" value="${custCon.pk}" />
						<ul class="ul-form">
						<div class="validate-group">
							<li class="form-validate">
							<label  >跟踪人</label>
							<input type="text" id="conMan" name="conMan"  value="${custCon.conMan}"    />
							</li>
							<li class="form-validate" >
							<label >跟踪时间 </label>
								<input type="text"   style="width:160px;" id="conDate" name="conDate" class="i-date" value="<fmt:formatDate value='${custCon.conDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" onchange="validateRefresh('conDate')"  required data-bv-notempty-message="联系时间不能为空"  />
							</li>
							</div>
							<div class="validate-group">
								<li class="form-validate" >
									<label>下次联系时间 </label>
									<input type="text" id="nextConDate" name="nextConDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${custCon.nextConDate}' pattern='yyyy-MM-dd'/>"  />
								</li>
								<li>
									<label>跟踪方式</label>
									<house:xtdm id="conWay" dictCode="CONWAY" value="${custCon.conWay}"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group">
							<li  class="form-validate">
							<label class="control-textarea">联系内容</label>
								<textarea   id="remarks" name="remarks"  rows="3">${custCon.remarks }</textarea>
							</li>
						</div>
						</ul>   
		            </form>
                </div>  
         
        </div> 
</div>
</body>
</html>
