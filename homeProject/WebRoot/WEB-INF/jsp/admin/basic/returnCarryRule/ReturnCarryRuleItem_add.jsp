<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>匹配材料--添加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
});
function saveBtn(){
	var selectRows = [];
	var datas=$("#dataForm").jsonForm();
	if(""!=$.trim($("#itemType1").val())){
		datas.itemtype1=$("#itemType1").val();
	}
	if(""!=$.trim($("#itemType2").val())){
		datas.itemtype2=$("#itemType2").val();
	}
	if(""!=$.trim($("#itemType2").val())){
		//获取当前选项
		var options = $("#itemType2 option:selected");
		//获取选中项目的文本
		var arr = options.text().split(" ");
		datas.itemtype2desc=arr[1];
	} 
	if(""!=$.trim($("#itemType3").val())){
		datas.itemtype3=$("#itemType3").val();
	}  
	if(""!=$.trim($("#itemType3").val())){
		//获取当前选项
		var options = $("#itemType3 option:selected");
		//获取选中项目的文本
		var arr = options.text().split(" ");
		datas.itemtype3desc=arr[1];
	}
	datas.expired="F";
	datas.actionlog="ADD";
	datas.lastupdate= new Date();
	datas.lastupdatedby=${czy};
 	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
};
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
		    	<div class="panel-body" >
		    		<div class="btn-group-xs" >
						<button type="button" class="btn btn-system" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="text" name="pk" id="pk" hidden="true"/>
						<input type="text" name="number" id="number" hidden="true">
						<ul class="ul-form">
						<li>
							<label style="width:120px">材料类型1</label>
							<select id="itemType1"></select>
						</li>	
						<li>
							<label style="width:120px">材料类型2</label>
							<select id="itemType2" name="itemType2"></select>
						</li>	
						<li>
							<label style="width:120px">材料类型3</label>
							<select id="itemType3" name="itemType3"></select>
						</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
