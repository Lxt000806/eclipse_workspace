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
	<title>薪酬项目编辑分类定义</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
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
				var elem = document.getElementById("formulaShow");
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
	
	var elem = document.getElementById("formulaShow");
	var index = getFocus(elem);
	
	var formulaShow = val;
	var formula = e.value;
	setFormulaShow(formulaShow, index);
}

function setFormula(val){
	var formula = $("#formula").val();
	
    $("#formula").val(formula+val+" ");
}

function setFormulaShow(val,index ){
	var formulaShow = $("#formulaShow").val();
	if(index == 0){
		index = formulaShow.length;
	}

	var preStr = formulaShow.substring(0,index);
	var sufStr =formulaShow.substring(formulaShow.length -(formulaShow.length - index));
	 
	if(index == formulaShow.length){
	    $("#formulaShow").val(formulaShow+val+" ");
	} else {
	    $("#formulaShow").val(preStr+" "+val+" "+sufStr+" ");
	}
	
	selectRange("formulaShow", myRound(index)+myRound(val.length)+2);
}

$(function() {
	
	$("#filterFormula").openComponent_salaryEmp({callBack:validateRefresh});	

	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			openComponent_employee_filterFormula:{
				validators:{
					notEmpty:{
						message:"考核人员不能为空"
					}
				}
			},
			beginMon:{
				validators:{
					notEmpty:{
						message:"开始时间不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
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
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
    var datas = $("#dataForm").serialize();
	
    var formula = $.trim($("#formula").val());
    var formulaShow = $.trim($("#formulaShow").val());
    
    if(formula == ""){
		art.dialog({
			content:"条件公式不能为空",
		});
		return;
	}
	
	if(formulaShow == ""){
		art.dialog({
			content:"公式显示不能为空",
		});
		return;
	}
    
	datas+="&formula="+encodeURIComponent(formula);
	datas+= "&formulaShow="+encodeURIComponent(formulaShow);
	
	$.ajax({
		url:"${ctx}/admin/salaryItem/doSaveDefine",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(true);
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

function changeMon(id){
	var date = $("#"+id+"Replace").val();
	$("#"+id).val(date.replace("-",""));
}

function getRange(e){
	$("#formulaShowRange").val(getFocus(e));
	return;
}

function getFormula(){
	$("#formula").val("");
	var formulaShow = $("#formulaShow").val();
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
	$("#formula").val(formula);
}

function checkDate(id){
	$("#dataForm").data("bootstrapValidator").updateStatus(id, "NOT_VALIDATED",null).validateField(id);
}

function validateRefresh(){
	$("#dataForm").data("bootstrapValidator")
    .updateStatus("openComponent_employee_filterFormula", "NOT_VALIDATED",null)  
    .validateField("openComponent_employee_filterFormula");
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
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
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
						<input type="hidden" id="formulaShowRange" name="formulaShowRange" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>薪酬代码</label>
									<input type="text" id="salaryItem" name="salaryItem" style="width:160px;" 
												readonly="true"  value="${salaryItem.code }"/>
								</li>
								<li class="form-validate">
									<label>薪酬名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" 
												readonly="true" value="${salaryItem.descr }"/>
								</li>
							</div>
							<div class="validate-group row" hidden="true">
								<li class="form-validate" >
									<label ><span class="required">*</span>条件说明</label>
									<input id="filterRemarks" name="filterRemarks" type="text" value="${salaryItemStatCfg.filterRemarks }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>考核人员</label>
									<input type="text" id="filterFormula" name="filterFormula" value=""/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>生效时间</label>
									<input type="text" id="beginMon" name="beginMon" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM',onpicked:checkDate('beginMon')})"/>
								</li>
								<li>
									<label>结束时间</label>
									<input type="text" id="endMon" name="endMon" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">算法说明</label>
									<textarea id="remarks" name="remarks" rows="3">${salaryItemStatCfg.remarks }</textarea>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>计算方式</label>
									<house:xtdm  id="calcMode" dictCode="SALCALCMODE" style="width:160px;" disabled="true"
										value="1"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>计算优先级</label>
									<house:dict id="filterLevel" dictCode="" sql="select 0 code ,0 descr" 
												sqlValueKey="code" sqlLableKey="descr" value="0" disabled="true"></house:dict>
								</li>
							</div>
						</ul>
					</form>
					<div style="height: 2px;border-bottom: 1px solid black"></div>
					<div class="validate-group row" style="width:98%;padding-top:10px;padding-right:5px">
					    <div class="panel-body" >
					    	<div class="btn-group-xs" id="algorithm_div">
							</div>
						</div>
						<div class="body-box-list" style="width:30%;float:right;">
							<div style="background-color: rgb(237,245,247);height:24px;text-align: center;line-height: 24px;font-weight: 900px">变量</div>
							<ul id="tree" class="ztree" style="width:300px; overflow:auto;;max-height: 270px"></ul>
						</div>
						<div style="width:68%">
							<div class="validate-group row" style="height:120px">
								<label class="control-textarea" style="width:80px;margin-left:20px"><span class="required">*</span>计算表达式</label>
								<textarea id="formulaShow" name="formulaShow" rows="8" style="width:480px;float:right"
									onclick="getRange(this)">${salaryItemStatCfg.formulaShow }</textarea>
							</div>
					    	<div class="btn-group-xs" style="padding-left:120px;padding-top:5px">
								<button type="button" class="btn btn-system " id="closeBut" onclick="getFormula()">
									<span>生成公式</span>
								</button>
							</div>
							<div class="validate-group row" style="heitht:120px;padding-top:8px">
								<label class="control-textarea" style="width:80px;;margin-left:20px"><span class="required">*</span>计算表达式</label>
								<textarea id="formula" name="formula" rows="8" style="width:480px;float:right" 
											onclick="getRange(this)">${salaryItemStatCfg.formula}</textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
