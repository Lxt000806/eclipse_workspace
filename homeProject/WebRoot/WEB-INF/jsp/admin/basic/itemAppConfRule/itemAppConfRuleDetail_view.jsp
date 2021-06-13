<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>领料审核规则明细信息--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.setSelect({
								firstSelect:"itemType2",
								firstValue:"${map.itemtype2[0]}",
								secondSelect:"itemType3",
								secondValue:"${map.itemtype3[0]}",
	});
});
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
						<input type="text" name="itemType1" id="itemType1" value="${map.itemType1[0]}" hidden="true"/>
						<ul class="ul-form">
							<li>
								<label>材料类型2</label>
								<select id="itemType2"  name="itemType2" disabled="disabled"></select>
								</li>
							<li>
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3" disabled="disabled"></select>
							</li>
							<li class="form-validate">
								<label>材料名称</label>
								<input type="text" id="itemDesc" name="itemDesc" value="${map.itemdesc[0]}" readonly="readonly"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
