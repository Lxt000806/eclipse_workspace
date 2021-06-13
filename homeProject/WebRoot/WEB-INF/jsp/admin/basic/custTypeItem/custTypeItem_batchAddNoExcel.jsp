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
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="exportData" id="exportData">
					<input type="hidden" name="jsonString" value="" /> 
					<ul class="ul-form">
						<div class="validate-group" >
							<li class="form-validate">
									<label><span class="required">*</span>客户类型</label>
									<house:DictMulitSelect id="custType" dictCode="" sql="select code ,desc1 descr from tcustType where expired='F'  " 
									sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
							</li>
							<li class="form-validate">
								<label style="width:120px"><span class="required">*</span>优惠额度计算方式</label>
								<house:xtdm id="discAmtCalcType" dictCode="GIFTDACALCTYPE" value="${custTypeItem.discAmtCalcType}"></house:xtdm>
							</li>
							<li class="form-validate">
								<label>适用装修区域</label>
								<house:xtdm id="fixAreaType" dictCode="SetItemFixArea" value="${custTypeItem.fixAreaType }"></house:xtdm>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">材料明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="d_add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="d_del()">
										<span>删除 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_detail"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		var itemDetailNum = 0;
		var gridOption1 = {
			sortable : true,
			height : 280,
			rowNum : 10000000,
			colModel : [ 
				{name: "itemcode", index: "itemcode", width: 85, label: "材料编号", sortable: true, align: "left"},
				{name: "itemdescr", index: "itemdescr", width: 140, label: "材料名称", sortable: true, align: "left"},
				{name: "newprice", index: "newprice", width: 80, label: "升级价", sortable: true, align: "left",editable:true,editrules: {number:true}},
				{name: "newprojectcost", index: "newprojectcost", width: 68, label: "升级结算价", sortable: true, align: "left",editable:true,editrules: {number:true}},
				{name: "remark", index: "remark", width: 130, label: "描述", sortable: true, align: "left",editable:true},
				{name: "remarks", index: "remarks", width: 130, label: "备注", sortable: true, align: "left" },
				{name: "price", index: "price", width: 100, label: "原材料单价", sortable: true, align: "left"},
				{name: "projectcost", index: "projectcost", width: 130, label: "原项目经理结算价", sortable: true, align: "left"},
			],
			gridComplete:function(){
				$("#dataTable_detail").jqGrid('resetSelection');
        	},
	        beforeSelectRow:function(id){
        	    setTimeout(function(){
           			relocate(id,"dataTable_detail");
          	    },100);
            },
		};
		Global.JqGrid.initEditJqGrid("dataTable_detail", gridOption1);
		
		$("#saveBtn").on("click", function() {
			$("#page_form").bootstrapValidator("validate");
			if (!$("#page_form").data("bootstrapValidator").isValid()){
				return;
			}
			if ($.trim($("#custType").val())==""){
				art.dialog({
					content: "请选择客户类型",
					width: 200
				});
				return false;
			}

			var datas = $("#page_form").jsonForm();
			var param = Global.JqGrid.allToJson("dataTable_detail");
			
			if (param.datas.length == 0) {
				art.dialog({
					content : "请先添加材料明细信息",
					width : 220
				});
				return;
			}
			/* 将datas（json）合并到param（json）中 */
			$.extend(param, datas);
			var custTypeNum = datas.custType.split(",").length;
			var count = $("#dataTable_detail").getGridParam("reccount");
			art.dialog({
				content : "客户类型包括"+datas.custType_NAME+",共添加"+custTypeNum*count+"条记录是否确认？",
				lock : true,
				ok : function() {
					doSave(param);
				},
				cancel : function() {
					return true;
				}
			});
		});
		
		$("#page_form").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: {  
				discAmtCalcType:{  
					validators: {  
						notEmpty: {  
							message: "优惠额度计算方式不能为空"  
						}
					}  
				}
			},
			submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
	});

	function d_add() {
		Global.Dialog.showDialog("detailAdd", {
			title : "材料明细--增加",
			url : "${ctx}/admin/item/goCode",
			postData : {
				isMultiselect : 1
			},
			height : 600,
			width : 1100,
			returnFun : function(v) {
				for(var i = 0; i < v.length ; i++){
					var json = {
						itemcode : v[i].code,
						itemdescr : v[i].descr,
						newprice : 0,
						newprojectcost : 0,
						remarks : v[i].remark,
						price : parseFloat(v[i].price),
						projectcost : parseFloat(v[i].projectcost)
					};
					Global.JqGrid.addRowData("dataTable_detail", json);
				}
			}
		});
	}
	//删除
	function d_del() {
		var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow");
		if (!id) {
			art.dialog({
				content : "请选择一条记录进行删除操作！"
			});
			return false;
		}
		art.dialog({
			content : "是否确认要删除",
			lock : true,
			ok : function() {
				Global.JqGrid.delRowData("dataTable_detail", id);
			},
			cancel : function() {
				return true;
			}
		});
	}
	
	function doSave(param) {
		console.log(param)
		$.ajax({
			url : "${ctx}/admin/custTypeItem/doSaveBatchAddNoExcelForProc",
			type : "post",
			data : param,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
</script>
</html>
