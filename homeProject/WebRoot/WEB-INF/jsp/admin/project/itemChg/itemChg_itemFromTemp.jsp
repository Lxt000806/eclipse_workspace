<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加干系人</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_prePlanTemp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">

//校验函数
$(function() {
	/* $("#no").openComponent_prePlanTemp(); */
	$("#no").openComponent_prePlanTemp({condition:{custType:'${itemChg.custType}'}});
	/* $("#no").openComponent_prePlanTemp({condition:{custType:'${itemPlan.custType}'},callBack:function(){validateRefresh('openComponent_prePlanTemp_no')}});
	$("#dataForm").bootstrapValidator("addField", "openComponent_prePlanTemp_no", {
        validators: {         
            remote: {
	            message: '',
	           url: '${ctx}/admin/basePlanTemp/getBasePlanTemp',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
        }
    }); */
});
function save(){
	if($("#no").val()){
		art.dialog({
			 content:'保存后将清除原来增减材料，同时生成新的增减材料，是否确定保存？',
			 lock: true,
			 width: 260,
			 height: 100,
			 ok: function () {
			 	var postData={tempNo:$("#no").val(),custCode:'${itemChg.custCode}'};
			 	$.ajax({
					url:"${ctx}/admin/itemChg/doItemChgFromTemp",
					type: 'POST',
					data:postData,
					dataType: 'json',
					cache: false,
				    success: function(obj){
				  		if(obj.rs){
				  			var tempNo = $("#no").val();
				  			var map = {datas:JSON.parse(obj.datas),tempNo:tempNo,tempDescr:$("#openComponent_prePlanTemp_no").val()};
				  			Global.Dialog.returnData =map;
					  		
					  		closeWin();
				  		}else{
				  			art.dialog({
								content: obj.msg
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
			content: "请选择模板编号！"
		});
	}
}


</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button id="saveBtn" type="button" class="btn btn-system"  onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input  type="hidden"  type="text" id="custCode" name="custCode" value="${itemChg.custCode}"/>
					<ul class="ul-form">
						<li><label>模板编号</label> <input type="text" id="no" name="no" />
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
