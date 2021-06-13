<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>薪酬项目新增分类定义</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
var zTree;
var setting = {
	check: {
		enable: false,
		nocheckInherit: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	view:{
		showIcon:false
	},
	callback: {
		onDblClick:function(event, treeId, treeNode) {
			if(treeNode.isParent == false){
				var elem = document.getElementById("filterFormulaShow");
				var index = getFocus(elem);
				setFormulaShow(treeNode.name,index);
		    }
		}
	}
};

var algorithm = {"+":"+", "-":"-", ">":">", "<":"<", "≥":"≥", 
		"≤":"≤", "<>":"<>", "=":"=", "且":"and", "包含":"in", "case语句":"case when  then  else  end ",
		"x":"*", "÷":"/", "(":"(", ")":")", "或":"or", "不含":"not in"};

var caseCode = {"当":"case when ","那么":"then","否则":"else","结束":"end"};

function setAlgorithm(e,val){
	
	var elem = document.getElementById("filterFormulaShow");
	var index = getFocus(elem);
	
	var formulaShow = val;
	var formula = e.value;
	setFormulaShow(formulaShow, index);
}

function setFormula(val){
	var formula = $("#filterFormula").val();
	
    $("#filterFormula").val(formula+val+" ");
}

function setFormulaShow(val,index ){
	var formulaShow = $("#filterFormulaShow").val();
	if(index == 0){
		index = formulaShow.length;
	}
	
	var preStr = formulaShow.substring(0,index);
	var sufStr =formulaShow.substring(formulaShow.length -(formulaShow.length - index));

	if(index == formulaShow.length){
	    $("#filterFormulaShow").val(formulaShow+val+" ");
	} else {
	    $("#filterFormulaShow").val(preStr+" "+val+" "+sufStr+" ");
	}
	
	selectRange("filterFormulaShow", myRound(index)+myRound(val.length)+2);
}

$(function() {
	var zNodes = ${node };
	$.fn.zTree.init($("#tree"), setting, zNodes);
	zTree = $.fn.zTree.getZTreeObj("tree");
	
	if($("#tree_1_switch") && $("#tree").find("li").length > 0 ){
		for(var i = 1; i<=$("#tree").find("li").length; i++){
			$("#tree_"+i+"_switch").click();
		}
	}
	
	for (var key in algorithm) {  
		var val = key;
		if(key=="case语句"){
			val = "当  那么  否则  结束";
		}
		$("#algorithm_div").append('<button type="button" class="btn btn-system " id="'+key+'" value="'+
					algorithm[key]+'" onclick="setAlgorithm(this,\''+val+'\')"><span>'+key+'</span></button>');		
	}
	
	
});

function doSave(){
	
	var filterFormulaShow = $.trim($("#filterFormulaShow").val());
	var filterFormula= $.trim($("#filterFormula").val());
	var datas= {filterFormulaShow:filterFormulaShow,filterFormula:filterFormula};
	
	if(filterFormulaShow!="" && filterFormula==""){
		art.dialog({
			content:"未生成公式是否确定保存",
			lock: true,
			ok: function () {
				Global.Dialog.returnData = datas;
				closeWin();
			},
			cancel: function () {
					return true;
			}
		});	
	} else {
		Global.Dialog.returnData = datas;
		closeWin();
	}
	
}

function changeMon(id){
	var date = $("#"+id+"Replace").val();
	$("#"+id).val(date.replace("-",""));
}

function getRange(e){
	$("#formulaShowRange").val(getFocus(e));
	return;
}

function getFormula(){
	$("#filterFormula").val("");
	var formulaShow = $("#filterFormulaShow").val();
	var list = formulaShow.split(" ");
	var zNode = ${node};
	var formula = "";
	var flag = false;
	for(var i = 0; i < list.length; i++){
		flag = false;
		if (list[i] ==""){
			flag = true;
			continue;
		}
		for(var key in algorithm ){
			if(list[i] == key){
				flag = true;
				formula += algorithm[key]+" ";
				continue;
			}
		}
		for(var caseKey in caseCode){
			if(list[i] == caseKey){
				flag = true;
				formula += caseCode[caseKey]+" ";
				continue;
			}
		}
		for(var j = 0;j< zNode.length;j++){
			if(zNode[j].name == list[i]){
				flag = true;
				formula += zNode[j].code+" ";
				continue;
			}
		}
		if(!flag){
			formula += list[i]+" ";
		}
	}
	console.log(formula);
	$("#filterFormula").val(formula);
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="beginMon" id = "beginMon" value=""/>
						<input type="hidden" id="endMon" name="endMon" value=""/>
						<div class="panel-body" style="padding: 0;padding-bottom: 10px">
					    	<div class="btn-group-xs" id="algorithm_div" style="padding-top: 0">
							</div>
						</div>
						<div class="body-box-list" style="width:30%;float:right;">
							<div style="background-color: rgb(237,245,247);height:24px;text-align: center;line-height: 24px;font-weight: 900px">变量</div>
							<ul id="tree" class="ztree" style="width:270px; overflow:auto;max-height: 270px"></ul>
						</div>
						<div style="width:68%; max-height: 330px">
							<div class="validate-group row" style="height:120px">
								<label class="control-textarea" style="width:80px;margin-left:20px"><span class="required">*</span>条件显示</label>
								<textarea id="filterFormulaShow" name="filterFormulaShow" rows="8" style="width:480px;float:right"
									onclick="getRange(this)">${salaryItemStatCfg.filterFormulaShow }</textarea>
							</div>
					    	<div class="btn-group-xs" style="padding-left:120px;padding-top:5px">
								<button type="button" class="btn btn-system " id="closeBut" onclick="getFormula()">
									<span>生成公式</span>
								</button>
							</div>
							<div class="validate-group row" style="heitht:120px;padding-top:8px">
								<label class="control-textarea" style="width:80px;;margin-left:20px"><span class="required">*</span>条件公式</label>
								<textarea id="filterFormula" name="filterFormula" rows="8" style="width:480px;float:right" 
											onclick="getRange(this)">${salaryItemStatCfg.filterFormula}</textarea>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
