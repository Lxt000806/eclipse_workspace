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
	<%@ include file="/commons/jsp/common.jsp"%>

	<style>
		.table-responsive {
			margin: 0px;
		}
		.ui-jqgrid-hdiv {
			width: auto !important;
		}
		.ui-jqgrid-bdiv {
			width: auto !important;
		}
	</style>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system view" id="saveBtn">
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
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="m_umState" name="m_umState" style="width:160px;" value="A"/>
					<ul class="ul-form">
						<div class="validate-group row">
							<li class="form-validate">
								<label>编号</label> 
								<input type="text" id="code" name="code" style="width:160px;"
									value="${algorithm.code}"/>
							</li>
							<li class="form-validate">
								<label>名称</label> 
								<input type="text" id="descr" name="descr" style="width:160px;"
									value="${algorithm.descr}"/>
							</li>
							<li hidden="true"><label>是否计算切割费</label> <house:xtdm id="isCalCutFee"
                                     dictCode="YESNO" value="${algorithm.isCalCutFee}"></house:xtdm>
                             </li>
                             <li >
								 <label class="control-textarea">备注</label>
								 <textarea id="remarks" name="remarks" >${algorithm.remarks}</textarea>
					    	</li>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabView_prjType" data-toggle="tab">算法适用材料</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_prjType" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="prjTypeSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="prjTypeUpdate">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="prjTypeDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="prjTypeView" onclick="view()">
									<span>查看</span>
								</button>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">
$(function() {
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-35,
		styleUI: "Bootstrap", 
		rowNum : 10000000,
		colModel : [
			{name: "code", index: "code", width: 50, label: "编码", sortable: true, align: "left",hidden:true},
			{name: "itemtype2", index: "itemtype2", width: 160, label: "材料类型2", sortable: true, align: "left",hidden:true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 160, label: "材料类型2", sortable: true, align: "left"},
			{name: "itemtype3", index: "itemtype3", width: 160, label: "材料类型3", sortable: true, align: "left",hidden:true},
			{name: "itemtype3descr", index: "itemtype3descr", width: 160, label: "材料类型3", sortable: true, align: "left"},
			{name: "uom", index: "uom", width: 160, label: "单位", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "过期标志", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"}
		],
		loadonce: true,
		ondblClickRow: function(){
			view();
		},
	});

	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {  
			code:{  
				validators: {  
					notEmpty: {  
						message: "编号不能为空"  
					},
					remote: {
			            message: "该编号重复",
			            url: "${ctx}/admin/algorithm/checkCode",
			            delay:1000, //每输入一个字符，就发ajax请求，服务器压力还是太大，
			            type:"post", //设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			        }
				}  
			},
			descr:{  
				validators: {  
					notEmpty: {  
						message: "名称不能为空"  
					},
					remote: {
			            message: "该名称重复",
			            url: "${ctx}/admin/algorithm/checkDescr",
			            delay:1000, //每输入一个字符，就发ajax请求，服务器压力还是太大，
			            type:"post", //设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
			        }
				}  
			},
		},
		submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});

	var originalData = $("#page_form").serialize();

	$("#closeBut").on("click",function(){
		closeWin();
	});

	$("#saveBtn").on("click", function() {
		doSave();
	});

	/* 明细新增 */
	$("#prjTypeSave").on("click",function(){
		Global.Dialog.showDialog("detailAdd",{
			title:"算法适用材料——新增",
			url:"${ctx}/admin/algorithm/goDetailAdd",
			postData:{m_umState:"A"},
			height:308,
			width:450,
			returnFun : function(data){
				console.log(data);
				if(data){
					$.each(data,function(k,v){
						var json = {
							code : $("#code").val(),
							uom : v.uom,
							itemtype2descr : v.itemType2Descr,
							itemtype3descr : v.itemType3Descr,
							itemtype2 : v.itemType2,
							itemtype3 : v.itemType3,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "ADD",
							expired : "F"
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					});
				}
			}
		});
	});
	
	$("#prjTypeUpdate").on("click",function(){
		var ret=selectDataTableRow();
		/* 选择数据的id */
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!ret){
			art.dialog({content: "请选择一条记录进行编辑操作",width: 220});
			return false;
		}
		
		Global.Dialog.showDialog("DetailUpdate",{
			title:"算法适用材料——编辑",
			url:"${ctx}/admin/algorithm/goDetailAdd",
			postData:{itemType2:ret.itemtype2.trim(),itemType3:ret.itemtype3.trim(), m_umState:"M",code:ret.code,uom:ret.uom},
			height:308,
			width:450,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							code : $("#code").val(),
							uom : v.uom,
							itemtype2descr : v.itemType2Descr,
							itemtype3descr : v.itemType3Descr,
							itemtype2 : v.itemType2,
							itemtype3 : v.itemType3,
							lastupdatedby : v.lastupdatedby,
							lastupdate : new Date(),
							actionlog : "EDIT",
							expired : "F"
					  	};
					   	$("#dataTable").setRowData(rowId,json);
					});
				}
			}
		});
	});

	/* 明细删除 */
	$("#prjTypeDelete").on("click",function(){
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
});

function view(){
	var ret=selectDataTableRow();
	/* 选择数据的id */
	var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	if(!ret){
		art.dialog({content: "请选择一条记录进行查看操作",width: 220});
		return false;
	}
	
	Global.Dialog.showDialog("prjTypeView",{
		title:"算法适用材料——查看",
		url:"${ctx}/admin/algorithm/goDetailAdd",
		postData:{itemType2:ret.itemtype2.trim(),itemType3:ret.itemtype3.trim(), m_umState:"V",code:ret.code,uom:ret.uom},
		height:308,
		width:450,
	});
}

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
	
	var datas = $("#page_form").jsonForm();
	var param= Global.JqGrid.allToJson("dataTable");
/* 	if(param.datas.length == 0){
		art.dialog({content: "请输入算法使用材料信息",width: 250});
		return;
	} */
	/* 将datas（json）合并到param（json）中 */
	$.extend(param,datas);
	$.ajax({
		url:"${ctx}/admin/algorithm/doSave",
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
</html>
