<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>匹配材料--编辑</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:$.trim("${map.itemtype1[0]}"),
								secondSelect:"itemType2",
								secondValue:$.trim("${map.itemtype2[0]}"),
								thirdSelect:"itemType3",
								thirdValue:$.trim("${map.itemtype3[0]}"),
	});
});
$(function(){
	$("#itemType2").val($.trim("${map.itemtype2[0]}"));
    $("#itemType3").val($.trim("${map.itemtype3[0]}"));		
});	
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
		    	<div class="panel-body" >
		    		<div class="btn-group-xs" >
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
							<select id="itemType1" name="itemType1" disabled="disabled"></select>
						</li>
						<li>
							<label style="width:120px">材料类型2</label>
							<select id="itemType2" name="itemType2" disabled="disabled"></select>
						</li>	
						<li>
							<label style="width:120px">材料类型3</label>
							<select id="itemType3" name="itemType3" disabled="disabled"></select>
						</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
