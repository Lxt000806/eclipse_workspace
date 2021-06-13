<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
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
		.SelectBG{
          	background-color:#FF7575!important;
        }  
	</style>
<script type="text/javascript" defer> 
$(function() {
	postData = $("#page_form").jsonForm();

	var gridOption = {
		url:"${ctx}/admin/itemAppTemp/goAreaDetailJqGrid",
		postData:{no:"${itemAppTempArea.no}",m_umState:"${m_umState}"},
		sortable: true,
		height : $(document).height()-$("#content-list").offset().top - 33,
		rowNum : 10000000,
		loadonce: true,
		colModel : [
			{name: "pk", index: "pk", width: 80, label: "模板明细pk", sortable: true, align: "left", hidden:true},
    		{name : "itcode",index : "itcode",width : 80,label:"材料编号",sortable : true,align : "left"},
    		{name : "itdescr",index : "itdescr",width : 240,label:"材料名称",sortable : true,align : "left"},
			{name : "qty",index : "qty",width : 70,label:"数量",sortable : true,align : "right"},
    		{name : "caltype",index : "caltype",width : 80,label:"计算类型pk",sortable : true,align : "left",hidden:true},
    		{name : "caltypedescr",index : "caltypedescr",width : 80,label:"计算类型",sortable : true,align : "left"},
  			{name : "dispseq",index : "dispseq",width : 100,label:"显示顺序",sortable : true,align : "right",hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
			{name: "itexpired", index: "itexpired", width: 90, label: "材料过期标志", sortable: true, align: "left", hidden:true},
		],
		ondblClickRow: function(){
			view();
		},
		// 如果过期变红
		gridComplete:function(){
	   		changeTdRed();
		},
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	/* 明细新增 */
	$("#detailSave").on("click",function(){
		var pks = Global.JqGrid.allToJson("dataTable","pk");
		var pkKeys = pks.keys;
		var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
		var itCodeKeys = itCodes.keys;
		var dispSeqs = Global.JqGrid.allToJson("dataTable","dispseq");
		var dispSeqKeys = dispSeqs.keys;
		dispSeqKeys.sort(function(x, y){
			if (!isNaN(x) && !isNaN(y)) {// 是否为数字类型
				x = Number(x);//转成数字
				y = Number(y);
			}
			return x-y;//-：y>x，+：y<x
		});// 对‘显示顺序’进行排序(因为获得是字符串，所以调用参数转成数字类型去排序)
		var lastDispSeq;
		if ("" == dispSeqKeys) {// 当为空时赋值为0
			lastDispSeq = 0;
		} else {
			lastDispSeq = dispSeqKeys[dispSeqKeys.length - 1];
		}
		
		Global.Dialog.showDialog("detailSave",{
			title:"领料申请模板材料——新增",
			url:"${ctx}/admin/itemAppTemp/goDetailSave",
			postData:{
				pks:pkKeys,
				itCodes:itCodeKeys,
				m_umState:"A",
				itemType1:"${itemType1}",
			},
			height:300,
			width:425,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							pk:parseInt(lastDispSeq) + 1,
							itcode:v.itCode,
							itdescr:v.itDescr,
							qty:v.qty,
							caltype:v.calType,
							caltypedescr:v.calTypeDescr,
							dispseq : parseInt(lastDispSeq) + 1,
							lastupdatedby:v.lastupdatedby,
							lastupdate:new Date(),
							expired:"F",
							actionlog:"ADD",
							itexpired:v.itExpired,
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	$("#dataTable td[aria-describedby='dataTable_dispseq']:contains('"+(parseInt(lastDispSeq) + 1)+"')")
					  		.parent().attr("id",parseInt(lastDispSeq) + 1);// 替换rowId
					  	$("#dataTable").setSelection(parseInt(lastDispSeq) + 1);
					  	scrollToPosi(parseInt(lastDispSeq) + 1);
					});
				}
			}
		});
	});
	
	$("#detailUpdate").on("click",function(){
		var ret=selectDataTableRow();
		/* 选择数据的id */
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		
		var pks = Global.JqGrid.allToJson("dataTable","pk");
		var pkKeys = pks.keys;
		var itCodes = Global.JqGrid.allToJson("dataTable","itcode");
		var itCodeKeys = itCodes.keys;
		var dispSeqs = Global.JqGrid.allToJson("dataTable","dispseq");
		var dispSeqKeys = dispSeqs.keys;
		dispSeqKeys.sort(function(x, y){
			if (!isNaN(x) && !isNaN(y)) {
				x = Number(x);
				y = Number(y);
			}
			return x-y;
		});
		var lastDispSeq;
		if ("" == dispSeqKeys) {
			lastDispSeq = 0;
		} else {
			lastDispSeq = dispSeqKeys[dispSeqKeys.length - 1];
		}
		
		Global.Dialog.showDialog("detailUpdate",{
			title:"领料申请模板材料——编辑",
			url:"${ctx}/admin/itemAppTemp/goDetailUpdate",
			postData:{
				m_umState:"M",
				pks:pkKeys,
				itCodes:itCodeKeys,
				itemType1:"${itemType1}",
				pk:ret.pk,
				itcode:ret.itcode,
				itDescr:ret.itdescr,
				qty:ret.qty,
				calType:ret.caltype,
				itExpired:ret.itexpired,
			},
			height:300,
			width:425,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							itcode:v.itCode,
							itdescr:v.itDescr,
							qty:v.qty,
							caltype:v.calType,
							caltypedescr:v.calTypeDescr,
							lastupdatedby:v.lastupdatedby,
							lastupdate:new Date(),
							itexpired:v.itExpired,
					  	};
					   	$("#dataTable").setRowData(rowId,json);
					   	changeTdRed();
					});
				}
			}
		});
	});

	$("#detailDelete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
				return true;
			}
		});
		
	});

	var originalData = $("#page_form").serialize();
	$("#closeBut").on("click",function(){
		var newData = $("#page_form").serialize();
		var param= Global.JqGrid.allToJson("dataTable");
		//去掉token
		var arr = originalData.split("&");
		arr.splice(0,1);
		originalData = arr.toString();
		arr = newData.split("&");
		arr.splice(0,1);
		newData = arr.toString();
		
		if (param.datas.length != 0||newData != originalData) {
			art.dialog({
				content: "数据已变动,是否保存？",
				width: 200,
				okValue: "确定",
				ok: function () {
					doSave();
				},
				cancelValue: "取消",
				cancel: function () {
					closeWin();
				}
			});
		} else {
			closeWin();
		}
	});
});
// 改变td的颜色
function changeTdRed(){
	var ids = $("#dataTable").getDataIDs();
 	for(var i=0;i<ids.length;i++){
		var rowData = $("#dataTable").getRowData(ids[i]);
		if("T" == rowData.itexpired){
			$('#'+ids[i]).find("td").addClass("SelectBG");
		} else {
			$('#'+ids[i]).find("td").removeClass("SelectBG");
		}                   
	}
}

function view(){
	var ret=selectDataTableRow();
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("detailView",{
		title:"领料申请模板材料——查看",
		url:"${ctx}/admin/itemAppTemp/goDetailUpdate",
		postData:{
			m_umState:"V",
			pk:ret.pk,
			itcode:ret.itcode,
			itDescr:ret.itdescr,
			qty:ret.qty,
			calType:ret.caltype,
			itExpired:ret.itexpired,
		},
		height:300,
		width:425,
	});
}
// 向上
function doTop(table) {
	if (!table)  table = "dataTable";
	var rowId = $("#" + table).jqGrid("getGridParam", "selrow");
	// var arr = rowId.split("_");
	var id = parseInt(rowId);
	var replaceId = $("#dataTable tr[id='"+rowId+"']").prev().attr("id");
	if (id) {
		if (id > 1) {
			var thisRow =$("#" + table).jqGrid("getRowData",id);
			var targeRow =$("#" + table).jqGrid("getRowData",replaceId);
			$("#" + table).setRowData(id,targeRow);
			$("#" + table).setRowData(replaceId,thisRow);
			$("#" + table).setCell(id, "dispseq", thisRow.dispseq);
			$("#" + table).setCell(replaceId, "dispseq", targeRow.dispseq);
			$("#" + table).setSelection(replaceId);
			scrollToPosi(replaceId);
			changeTdRed();
		}
	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
// 向下
function doBottom(table) {
	if (!table)  table = "dataTable";
	var rowId = $("#" + table).jqGrid("getGridParam", "selrow");
	// var arr = rowId.split("_");
	var id = parseInt(rowId);
	var replaceId = $("#dataTable tr[id='"+rowId+"']").next().attr("id");
	var rowNum = $("#" + table).jqGrid("getGridParam", "records");
	if (id) {
		if (id < parseInt(rowNum)) {
			var thisRow =$("#" + table).jqGrid("getRowData",id);
			var targeRow =$("#" + table).jqGrid("getRowData",replaceId);
			$("#" + table).setRowData(id,targeRow);
			$("#" + table).setRowData(replaceId,thisRow);
			$("#" + table).setCell(id, "dispseq", thisRow.dispseq);
			$("#" + table).setCell(replaceId, "dispseq", targeRow.dispseq);
			$("#" + table).setSelection(replaceId);
			changeTdRed();
		}
	} else {
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
/* 主页面保存方法 */
function doSave(){
	var datas = $("#page_form").jsonForm();
	var param= Global.JqGrid.allToJson("dataTable");
	/*if(param.datas.length == 0){
		art.dialog({content: "无法保存，请输入材料信息",width: 220});
		return;
	}*/
	// 将datas（json）合并到param（json）中
	$.extend(param,datas);
	param.m_umState = "${m_umState}";
	// console.log(param);

	$.ajax({
		url:"${ctx}/admin/itemAppTemp/doAreaSave",
		type: "post",
		data: param,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错"});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
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

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
		    <div class="panel-body">
		    	<div class="btn-group-xs">
		    		<button type="button" class="btn btn-system" id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="iatno" value="${iatno}"/>
					<ul class="ul-form">
						<div class="row">
							<li>
								<label>面积区间编号</label>
								<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" 
									value="${itemAppTempArea.no}" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>从面积</label>
								<input type="text" id="fromArea" name="fromArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${itemAppTempArea.fromArea}" />
							</li>
							<li>
								<label>到面积</label>
								<input type="text" id="toArea" name="toArea" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
									value="${itemAppTempArea.toArea}"/>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >
			    <li class="active"><a href="#item_tabView" data-toggle="tab">材料</a></li>  
			</ul>
		    <div class="tab-content">  
				<div id="item_tabView"  class="tab-pane fade in active"> 
		         	<div class="body-box-list">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="detailSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="detailUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="detailDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="view" onclick="view()">
									<span>查看</span>
								</button>
								<button type="button" class="btn btn-system" id="up" onclick="doTop()">
									<span>向上</span>
								</button>
								<button type="button" class="btn btn-system" id="down" onclick="doBottom()">
									<span>向下</span>
								</button>
								<button type="button" class="btn btn-system" id="excel" onclick="doExcelNow('领料申请模板材料')">
									<span>导出Excel</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
							<!-- <div id="dataTablePager"></div> -->
						</div>
					</div>
				</div> 
			</div>	
		</div>
	</div>
</body>	

</html>
