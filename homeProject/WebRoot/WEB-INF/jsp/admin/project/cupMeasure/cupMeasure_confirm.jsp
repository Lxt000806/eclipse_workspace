<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>集成安装确认--确认</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
function save(){
	if("${map.iscupboard}"=="1"){
		var cupDownHigh=$("#cupDownHigh").val();
		var cupUpHigh=$("#cupUpHigh").val();
	}else{
		var cupDownHigh=$("#cupDownHigh").val();
		var cupUpHigh=$("#cupUpHigh").val();
	}
	
	if(cupDownHigh==""){
		art.dialog({
			content : "请输入橱柜地柜延米！",
			width : 200
		});
		return;
	}
	if(cupUpHigh==""){
		art.dialog({
			content : "请输入橱柜吊柜延米！",
			width : 200
		});
		return;
	}
	$.ajax({
		url : "${ctx}/admin/cupInstallConfirm/doConfirm",
		type : "post",
		data : $("#dataForm").jsonForm(),
		dataType : "json",
		cache : false,
		error : function(obj) {
			showAjaxHtml({
				"hidden" : false,
				"msg" : "保存数据出错~"
			});
		},
		success : function(obj) {
			if (obj.rs) {
				art.dialog({
					content : obj.msg,
					time : 1000,
					beforeunload : function() {
						closeWin();
					}
				});
			} else {
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content : obj.msg,
					width : 200
				});
			}
		}
	});
}
//校验函数
$(function() {
	var m_umState='${map.m_umState}';
	$("#custType").attr("disabled",true);
	$("#custCode").openComponent_customer({
		showValue:"${map.custcode}",
		showLabel:"${map.custdescr}",
		readonly:true
	});
	if(m_umState=="goView"){
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
	}
	if("${map.iscupboard}"=="1"){
		document.getElementById("cupDownHighText").innerHTML="橱柜地柜延米";
		document.getElementById("cupUpHighText").innerHTML="橱柜吊柜延米";
	}else{
		document.getElementById("cupDownHighText").innerHTML="浴室地柜延米";
		document.getElementById("cupUpHighText").innerHTML="浴室吊柜延米";
	}
	
});

</script>
<style type="text/css">

</style>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system"
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
						<li><label>客户编号</label> <input type="text"
							id="custCode" name="custCode" />
						</li>
						<li><label>客户类型</label> <house:dict
									id="custType" dictCode=""
									sql="select code ,code +' ' + desc1 descr from tcustType where expired='F'"
									sqlValueKey="code" sqlLableKey="descr"
									value="${map.custtype.trim()}">
								</house:dict>
						</li>
						<li><label>楼盘</label> <input type="text" id="address"
							name="address" style="width:451px;" value="${map.address}" readonly/>
						</li>
						<li>
							<label id="cupDownHighText"></label> 
							<input type="number" id="cupDownHigh" name="cupDownHigh" value="${map.cupdownhigh }"/>
						</li>
						<li>
							<label id="cupUpHighText"></label> 
							<input type="number" id="cupUpHigh" name="cupUpHigh" value="${map.cupuphigh }"/>
						</li>
						<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
							dictCode="YESNO" value="${map.iscupboard }" disabled="true"></house:xtdm>
						</li>
						<li>
							<label class="control-textarea">确认说明</label>
							<textarea id="remarks" name="remarks" style="width:451px;" rows="2">${map.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
