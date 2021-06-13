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
	<title>预算管理空间编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script type="text/javascript">
	var oldDatas;
	function exitPage(){
		closeWin();
	}
	$(function() {
		console.log("${map.fixareatype[0]}");
		$("#fixAreaType").val($.trim("${map.fixareatype[0]}"));
		$("#descr").val("${map.preplanareadescr[0]}");
		$("#area").val("${map.area[0]}");
		$("#perimeter").val("${map.perimeter[0]}");
		$("#height").val("${map.height[0]}");
		$("#paveType").val("${map.pavetype[0]}");
		$("#showerWidth").val("${map.showerwidth[0]}");
		$("#showerLength").val("${map.showerlength[0]}");
		$("#beamLength").val("${map.beamlength[0]}");
		$("#custCode").val("${map.custcode[0]}");
		$("#dispSeq").val("${map.dispseq[0]}");
		$("#pk").val("${map.pk[0]}");
		$("#fixAreaType").attr("disabled",true);
		oldDatas=JSON.stringify($("#dataForm").jsonForm());
		changeType();
		var gridOption = {
			url:"${ctx}/admin/itemPlan/getDoorWindList",
			postData:{pk:"${map.pk[0]}"},
			height:$(document).height()-$("#content-list").offset().top-55,
			rowNum:10000000,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "type", index: "type", width: 101, label: "类型", sortable: true, align: "left",hidden:true},
				{name: "typedescr", index: "typedescr", width: 101, label: "类型", sortable: true, align: "left"},
				{name: "length", index: "length", width: 91, label: "长度", sortable: true, align: "left"},
				{name: "oldlength", index: "oldlength", width: 91, label: "原长度", sortable: true, align: "left"},
				{name: "height", index: "height", width: 90, label: "高度", sortable: true, align: "left"},
				{name: "oldheight", index: "oldheight", width: 90, label: "原高度", sortable: true, align: "left"},
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		
		$("#addDoorWind").on("click",function(){
			oldDatas="";
			Global.Dialog.showDialog("addDoorWind",{
				title:"门窗——新增",
				url:"${ctx}/admin/itemPlan/goAddDoorWind",
				height: 480,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								type:v.type,
								typedescr:$.trim(v.type)=="1"?"门":"窗",
								length:v.length,
								height:v.height,
							};
							Global.JqGrid.addRowData("dataTable",json);
						});
					}
				} 
			});
		});
		
		$("#updateDoorWind").on("click",function(){
			oldDatas="";
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			var rowId=$("#dataTable").jqGrid("getGridParam","selrow");
			var param =$("#dataTable").jqGrid("getRowData",rowId);
		
			Global.Dialog.showDialog("updateDoorWind",{
				title:"门窗——编辑",
				url:"${ctx}/admin/itemPlan/goUpdateDoorWind",
				postData:param,
				height: 480,
				width:750,
			    returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								type:v.type,
								typedescr:$.trim(v.type)=="1"?"门":"窗",
								length:v.length,
								height:v.height,
							};
			   				$('#dataTable').setRowData(rowId,json);
						});
					}
				} 
			});
		});
		
		$("#delDetail").on("click",function(){
			oldDatas="";
			var id = $("#dataTable").jqGrid("getGridParam","selrow");
			if(!id){
				art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
				return false;
			}
			art.dialog({
				content:"是删除该记录？",
				lock: true,
				width: 100,
				height: 80,
				ok: function () {
					Global.JqGrid.delRowData("dataTable",id);
				},
				cancel: function () {
					return true;
				}
			});
		});
		
	});
	 
	function save(){
		var datas = $("#dataForm").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		
		Global.Form.submit("dataForm","${ctx}/admin/itemPlan/doUpdatePrePlanArea",param,function(ret){
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
	};
	
	
	function changeType(){
		var fixAreaType = $.trim($("#fixAreaType").val());
		if(fixAreaType=="1"||fixAreaType=="7"){//height 2.6
			$("#height").val(2.6);
			if(fixAreaType=="1"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}else if(fixAreaType=="7"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}
		}else if(fixAreaType=="2"||fixAreaType=="3"||fixAreaType=="5"||fixAreaType=="6"){//height 2.4
			$("#height").val(2.4);
			if(fixAreaType=="6" || fixAreaType=="5" ){
				$("#beamLength_li").css("display","block");//显示
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}else if(fixAreaType=="3"){
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","block");//显示
				$("#showerWidth_li").css("display","block");//显示
			}else{
				$("#beamLength_li").css("display","none");
				$("#showerLength_li").css("display","none");
				$("#showerWidth_li").css("display","none");
			}
		}else if(fixAreaType=="4"){//height 2.8
			$("#height").val(2.8);
			$("#beamLength_li").css("display","none");
			$("#showerLength_li").css("display","none");//显示
			$("#showerWidth_li").css("display","none");
		}else{
			$("#beamLength_li").css("display","none");
			$("#showerLength_li").css("display","none");//显示
			$("#showerWidth_li").css("display","none");
		}
		//clareText();
	}
	function clareText(){
		$("#paveType").val("");
		$("#paveLength").val("");
		$("#showerLength").val("");
		$("#showerWidth").val("");
		$("#area").val("");
		$("#perimeter").val("");
		$("#descr").val("");
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="closeBut" onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="custCode" name="custCode" >
						<input type="hidden" id="dispSeq" name="dispSeq"/>
						<input type="hidden" id="pk" name="pk"/>
						<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>空间类型</label>
								<house:dict id="fixAreaType" dictCode="" onchange="changeType()" sqlValueKey="Code" sqlLableKey="Descr"
								sql="select code,code+' '+descr descr from tFixAreaType where expired ='F'"   ></house:dict>
							</li>
							<li class="form-validate">
								<label>空间名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>面积</label>
								<input type="text" id="area" name="area" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>周长</label>
								<input type="text" id="perimeter" name="perimeter" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li class="form-validate">
								<label>高度</label>
								<input type="text" id="height" name="height" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="paveType_li" class="form-validate">
								<label>铺贴类型</label>
								<house:xtdm  id="paveType" dictCode="PAVETYPE" style="width:160px;"></house:xtdm>
							</li>
							<li id="showerWidth_li" class="form-validate">
								<label>淋浴房宽</label>
								<input type="text" id="showerWidth" name="showerWidth" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="showerLength_li" class="form-validate">
								<label>淋浴房长</label>
								<input type="text" id="showerLength" name="showerLength" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
							<li id="beamLength_li" class="form-validate">
								<label>包梁长度</label>
								<input type="text" id="beamLength" name="beamLength" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;"/>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<ul class="nav nav-tabs" >
	      		<li class="active"><a data-toggle="tab">门窗</a></li>
			</ul>
			<div id="content-list">
				<table id= "dataTable"></table>
			</div>
		</div>
	</body>	
</html>
