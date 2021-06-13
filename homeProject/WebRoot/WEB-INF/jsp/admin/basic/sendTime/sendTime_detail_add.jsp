<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>快速预报价模板--增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
function save(){
	var itemType2=$("#itemType2").val();
	var itemDesc= $("#itemDesc").val();
	if(itemDesc=="" && itemType2==""){
		art.dialog({
			content : "材料类型2和材料名称不能同时为空！",
			width : 200
		});
		return;
	}
	var datas = $("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}
//校验函数
$(function() {
	var m_umState='${sendTime.m_umState}';
	if(m_umState=="V"){
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
		$("#saveBtn").attr("disabled",true);
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
					<input type="hidden" id="itemType2Descr" name="itemType2Descr"
						value="${sendTime.itemType2Descr }">
					<ul class="ul-form">
						<div class="validate-group row">
							<li><label style="width:200px">材料类型2</label> <house:dict id="itemType2"
									dictCode=""
									sql="select rtrim(Code)+' '+Descr fd,Code from tItemType2 where Expired='F' and ItemType1=
               '${sendTime.itemType1 }' order by DispSeq"
									sqlValueKey="Code" sqlLableKey="fd"
									value="${sendTime.itemType2}"
									onchange="getDescrByCode('itemType2','itemType2Descr')">
								</house:dict></li>
						</div>
						<div class="validate-group row">
							<li><label style="width:200px">材料名称</label> <input type="text" id="itemDesc"
								name="itemDesc" style="width:160px;"
								value="${sendTime.itemDesc}" />
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
