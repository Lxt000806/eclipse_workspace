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
	<title>薪酬方案新增</title>
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
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
	</style>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			salarySchemeType:{
				validators:{
					notEmpty:{
						message:"方案类型不能为空"
					}
				}
			},
			descr:{
				validators:{
					notEmpty:{
						message:"方案名称不能为空"
					}
				}
			},
			beginMon:{
				validators:{
					notEmpty:{
						message:"生效时间不能为空"
					}
				}
			},
			status:{
				validators:{
					notEmpty:{
						message:"状态不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-100,
		rowNum:10000,
		cellEdit:true,
		cellsubmit: "clientArray",
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:false ,align:"left",hidden:true},
			{name:"salaryitem", index:"salaryitemcode", width:80, label:"项目编号", sortable:false,align:"left",},
			{name:"salaryitemdescr", index:"salaryitemdescr", width:150, label:"项目名称", sortable:false,align:"left",},
			{name:"isshow", index:"isshow", width:80, label:"显示标识", sortable:false,align:"left", hidden:true},
			{name:"isshowdescr", index:"isshowdescr", width:80, label:"显示标识", sortable:false ,align:"center",formatter:checkBtn},
			{name:"dispseq", index:"dispseq", width:80, label:"排序", sortable:true ,align:"right",},
			{name:"isrptshow", index:"isrptshow", width:80, label:"工资单显示", sortable:false,align:"left", hidden:true},
			{name:"isrptshowdescr", index:"isrptshowdescr", width:80, label:"工资单显示", sortable:false ,align:"center",formatter:checkRptBtn},
			{name:"rptdispseq", index:"rptdispseq", width:80, label:"工资单序号", sortable:true ,align:"right",editable:true},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$("#"+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$("#"+ids[id-1]).find("td").addClass("SelectBG");
		},
	}; 

	function checkBtn(v,x,r){
		if(r.isshow == "1"){
			return "<input type='checkbox' checked onclick='checkRow("+x.rowId+",this)' />";
		} else {
			return "<input type='checkbox' onclick='checkRow("+x.rowId+",this)' />";
		}
	}
	
	function checkRptBtn(v,x,r){
		if(r.isrptshow == "1"){
			return "<input type='checkbox' checked onclick='checkRptRow("+x.rowId+",this)' />";
		} else {
			return "<input type='checkbox' onclick='checkRptRow("+x.rowId+",this)' />";
		}
	}
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});

function checkRow(id,e){
	if($(e).is(":checked")){
		$("#dataTable").setCell(id, "isshow", "1");
	} else {
		$("#dataTable").setCell(id, "isshow", "0");
	}
}

function checkRptRow(id,e){
	if($(e).is(":checked")){
		$("#dataTable").setCell(id, "isrptshow", "1");
		$("#dataTable").setCell(id, "rptdispseq", "0");
		$($("#dataTable").find("tr")[id]).find(".not-editable-cell").removeClass("not-editable-cell");
	} else {
		$("#dataTable").setCell(id, "isrptshow", "0");
		$("#dataTable").jqGrid("setCell", id, "rptdispseq", "", "not-editable-cell");
		$("#dataTable").setCell(id, "rptdispseq", null, {readonly:"readonly"} , {editable:false});
	}
}

function itemSet(){
	var rowDatas=$("#dataTable").jqGrid("getRowData");
	var salaryItemCodes = "";
	
	if(rowDatas.length>0){
		for(var i = 0; i < rowDatas.length; i++){
			if(salaryItemCodes == ""){
				salaryItemCodes = rowDatas[i].salaryitem+",";
			} else {
				salaryItemCodes += rowDatas[i].salaryitem+",";
			}
		}
	}
	
	if(salaryItemCodes.length > 0){
		salaryItemCodes =salaryItemCodes.substring(0, salaryItemCodes.length-1); 
	}
	
	Global.Dialog.showDialog("itemSet",{
		title:"项目配置",
		postData:{code:$("#code").val(), salaryItemCodes:salaryItemCodes,detailJson: JSON.stringify(rowDatas)},
		url:"${ctx}/admin/salaryScheme/goSchemeItemSet",
		height:690,
		width:920,
        resizable: true,
		returnFun:function(data){
			if(data){
				$("#dataTable").jqGrid("clearGridData");
				$.each(data,function(k,v){
					v.salaryitem = v.code;
					v.salaryitemdescr = v.descr;
					v.dispseq = myRound(k)+1;
					$("#dataTable").addRowData(myRound(k)+1, v, "last");
					$("#dataTable").jqGrid("setCell", myRound(k)+1, "rptdispseq", "", "not-editable-cell");
				});
			}
		}
	});	
}

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

	var param= Global.JqGrid.allToJson("dataTable");
	if(param.datas.length == 0){
		art.dialog({
			content:"请先配置薪酬项目",
		});
		return;
	}
	Global.Form.submit("dataForm","${ctx}/admin/salaryScheme/doSave",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content:ret.msg,
				time:1000,
				beforeunload:function(){
					closeWin();
				}
			});				
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			art.dialog({
				content:ret.msg,
				width:200
			});
		}
	});
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			      <div class="btn-group-xs">
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>方案名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" />
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>方案类型</label>
									<house:dict id="salarySchemeType" dictCode="" sql="select Code+' '+descr descr,code from tSalarySchemeType where expired = 'F'" 
												sqlValueKey="code" sqlLableKey="descr"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>生效时间</label>
									<input type="text" id="beginMon" name="beginMon" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"/>
								</li>
								<li>
									<label>结束时间</label>
									<input type="text" id="endMon" name="endMon" class="i-date" 
										style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="1"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">方案说明</label>
									<textarea id="remarks" name="remarks" rows="3"></textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid">  
				<ul class="nav nav-tabs"> 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">薪酬项目</a></li>
			    </ul> 
				<div id="content-list">
					<div id="tab_detail" class="tab-pane fade in active">
						<div class="pageContent">
							<div class="panel panel-system">
								<div class="panel-body">
									<div class="btn-group-xs">
										<button style="align:left" type="button"
											class="btn btn-system viewFlag" onclick="itemSet()">
											<span>项目配置 </span>
										</button>
									</div>
								</div>
							</div>
							<div id="content-list">
								<table id="dataTable"></table>
							</div>
						</div>
					</div>
				</div>	
			</div>	
		</div>
	</body>	
</html>
