<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<style type="text/css">
		.panel-info {
			margin: 0px;
		}
		.panel-info .panel-body {
			height: 210px;
		}
		.form-search {
			padding-top: 55px;
		}
	</style>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
					<ul class="ul-form">
							<li hidden="true">
								<label>材料类型1</label>
								<select id="itemType1" name="itemType1" style="width: 160px;" value="ZC" ></select>
							</li>
							<li>
								<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width: 160px;"></select>
							</li>
							<li hidden="true">
								<label>材料类型2</label>
								<input id="itemType2Descr" name="itemType2Descr" style="width: 160px;" />
							</li>
							<li>
								<label>材料类型3</label>
								<select id="itemType3" name="itemType3" style="width: 160px;"></select>
							</li>
							<li hidden="true">
								<label>材料类型3</label>
								<input id="itemType3Descr" name="itemType3Descr" style="width: 160px;"  />
							</li>
							<li>
								<label>单位</label> 
								<input type="text" id="uom" name="uom" style="width:160px;"
									value="${algorithm.uom}"/>
							</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>	
<script type="text/javascript"> 
$(function() {
Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'ZC',
		secondSelect:"itemType2",
		secondValue:'${algorithm.itemType2}',
		thirdSelect:"itemType3",
		thirdValue:'${algorithm.itemType3}'
	};
	Global.LinkSelect.setSelect(dataSet);
	switch ("${algorithm.m_umState}") {
	case "M":
		$("#uom").val("${algorithm.uom}");
		break;
	case "V":
		//不显示保存按钮
		$("#saveBtn").hide();
		$("input").attr("disabled",true);
		$("select").attr("disabled",true);
		break;
	default:
		break;
	}
	$("#saveBtn").on("click",function(){
		var itemType2=$("#itemType2").val();
		var itemType3=$("#itemType3").val();
		var uom=$("#uom").val();
		if(itemType2=="" && itemType3=="" && uom==""){
			art.dialog({
				content :"保存时信息不能全部为空！",
				width : 250
			});
			return;
		}
 		var selectRows = [];
		var datas=$("#page_form").jsonForm();
		datas['itemType2Descr']=getDescr("itemType2");
		datas['itemType3Descr']=getDescr("itemType3");
	 	selectRows.push(datas);
		Global.Dialog.returnData = selectRows;
		closeWin();
	});
	
});
//获取descr
function getDescr(id){
	var selectText=$("#"+id).find("option:selected").text();
	var arr = selectText.split(" ");
	var descr=arr[arr.length-1];
	if(descr=="请选择..."){
		descr="";
	}
	return descr;
}
</script>
</html>
