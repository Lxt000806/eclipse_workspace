<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加ItemType1</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
    Global.LinkSelect.setSelect({firstSelect:"itemType1",
								firstValue:"${itemType3.itemType1}",
								secondSelect:"itemType2",
								secondValue:"${itemType3.itemType2}"});
	$("#intInstallFee").val(myRound("${itemType3.intInstallFee}",2));
});
</script>
</head>   
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
    		<div class="btn-group-xs" >
   				<button id="closeBut" type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info" >  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="text" id="lastUpDatedBy" name="lastUpDatedBy" hidden="true">
				<input type="text" id="actionLog" name="actionLog" hidden="true">
				<ul class="ul-form">					  
					<li>
						<label>编码</label>
						<input type="text" id="code" name="code" value="${itemType3.code}" readonly="readonly" />
					</li>
					<li class="form-validate">
						<label>名称</label>
						<input type="text" id="descr" name="descr" value="${itemType3.descr}" readonly="readonly"/>
					</li>
					<li>
						<label>材料类型1</label>
						<select id="itemType1" name="itemType1" disabled="disabled"></select>
					</li>
					<li>
						<label>材料类型2</label>
						<select id="itemType2" name="itemType2" disabled="disabled"></select>
					</li>
					<li>
						<label>安装费用</label>
						<input type="text" id="intInstallFee" name="intInstallFee" value="${itemType3.intInstallFee}" readonly="readonly"/>
					</li>
					<li>
						<label>显示顺序</label>
						<input type="text" id="dispSeq" name="dispSeq" value="${itemType3.dispSeq}" readonly="readonly"/>
					</li>
					<li>
						<label>拆单类型</label>
						<house:xtdm id="orderType" dictCode="ORDERTYPE" value="${itemType3.orderType}" disabled="true"></house:xtdm>				
					</li> 
				</ul>	
				<div class="validate-group row" style="margin-left: 90px;">
					<li>
						<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show"
						 onclick="checkExpired(this)" ${itemType3.expired=="T"?"checked":""}/>
					</li>
			   </div>		
			</form>
		</div>
  	</div>
</div>
</body>
</html>
