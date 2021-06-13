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
		.form-search .ul-form li label {
			width: 65px;
		}
		.jqGrid_light_yellow_background {
			background-color: #ffff7d;
		}
	</style>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemApp.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_itemSendBatch.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<form action="" method="post" id="page_form" class="form-search">
		<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system view" id="submitBtn">
							<span>提交</span>
						</button>
						<button type="button" class="btn btn-system view" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system view" id="cancelBtn">
							<span>取消</span>
						</button>
						<button type="button" class="btn btn-system view" id="returnBtn">
							<span>退回</span>
						</button>
						<button type="button" class="btn btn-system view" id="sendBtn">
							<span>发货</span>
						</button>
						<button type="button" class="btn btn-system view" id="partSendBtn">
							<span>部分发货</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" style="margin-bottom: 10px;">
				<div class="panel-body">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" value="${m_umState}">
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-md-6">
								<li>
									<label><span class="required">*</span>单号</label>
									<input type="text" id="no" name="no" style="width:160px;" 
										value="${preItemAppSend.no}" placeholder="保存自动生成" readonly/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>领料单号</label>
									<input type="text" id="iaNo" name="iaNo" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>客户编号</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"
										value="${preItemAppSend.custcode}" readonly/>
									<span id="address">${preItemAppSend.address}</span>
								</li>
							<!-- </div>
							<div class="col-md-3"> -->
								<li class="form-validate">
									<label for="itemType1"><span class="required">*</span>材料类型1</label>
									<house:dict id="itemType1" dictCode="" 
										sql="select rtrim(Code)+' '+Descr fd,Code from tItemType1 order by DispSeq asc" 
										sqlValueKey="Code" sqlLableKey="fd" value="${preItemAppSend.itemtype1}"/>
								</li>
								<li class="form-validate">
									<label for="whCode"><span class="required">*</span>仓库</label>
									<input type="text" id="whCode" name="whCode"/>
								</li>
								<!-- <li class="form-validate">
									<label for="whDescr">仓库名称</label>
									<input type="text" id="whDescr" name="whDescr" value="${preItemAppSend.desc1}" readonly/>
								</li> -->
							</div>
							<div class="col-md-6">
								<li class="form-validate">
									<label for="status">状态</label>
									<house:dict id="status" dictCode="" 
										sql="select rtrim(IBM) IBM, rtrim(IBM)+' '+NOTE fd from txtdm where id ='PRESENDSTATUS' order by IBM asc" 
										sqlValueKey="IBM" sqlLableKey="fd" value="${preItemAppSend.status}"/>
								</li>
								<li>
									<label for="appCzy">申请人员</label>
									<input type="text" id="appCzy" name="appCzy" readonly/>
								</li>
								<li>
									<label>申请日期</label>
									<input type="text" id="appDate" name="appDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${preItemAppSend.date}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled/>
								</li>
							<!-- </div>
							<div class="col-md-3"> -->
							    <li hidden>
                                    <label>配送方式</label>
                                    <house:xtdm id="delivType" dictCode="DELIVTYPE" value="${preItemAppSend.itemappdelivtype}"></house:xtdm>
                                </li>
								<li>
									<label for="sendCzy">发货人员</label>
									<input type="text" id="sendCzy" name="sendCzy" readonly/>
								</li>
								<li>
									<label>发货日期</label>
									<input type="text" id="sendDate" name="sendDate" class="i-date" style="width:160px;" 
										onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
										value="<fmt:formatDate value='${preItemAppSend.senddate}' pattern='yyyy-MM-dd HH:mm:ss'/>" disabled/>
								</li>
								<li class="form-validate">
									<label for="sendBachNo">配送批次</label>
									<input type="text" id="sendBachNo" name="sendBachNo">
								</li>
							</div>
							<div class="col-md-12">
								<li style="max-height: 120px;">
									<label class="control-textarea" st/yle="top: -44px;">备注</label>
									<textarea id="remarks" name="remarks" 
										style="height: 60px;">${preItemAppSend.remarks}</textarea>
								</li>
							</div>
						</div>
					</ul>
				</div>
			</div>
			<div class="container-fluid" id="id_detail">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tabView_item" data-toggle="tab">发货申请明细</a>
					</li>
				</ul>
				<div class="tab-content">
					<div id="tabView_item" class="tab-pane fade in active">
						<div class="btn-panel">
							<div class="btn-group-sm" style="padding-top: 5px;">
								<button type="button" class="btn btn-system" id="itemSave">
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="itemDelete">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="itemExcel">
									<span>输出到Excel</span>
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
	<script type="text/javascript">
		var originalData, originalDataTable;
		var m_umState="${m_umState}";
		$("#itemType1").prop("disabled", true);
		$("#status").prop("disabled", true);
		function view(){
			var ret=selectDataTableRow();
			var rowId = $("#dataTable").jqGrid("getGridParam","selrow");/* 选择数据的id */
			if(!ret){
				art.dialog({content: "请选择一条记录进行查看操作",width: 220});
				return false;
			}
			Global.Dialog.showDialog("userView",{
				title:"角色成员——查看",
				url:"${ctx}/admin/preItemAppSend/goWin",
				postData:{id:ret.id_,first:ret.first_,last:ret.last_, m_umState:"V"},
				height:308,
				width:450,
			});
		}
		function submitClick() {//提交
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var param= Global.JqGrid.allToJson("dataTable");
			var isWrong = false;
			if(param.datas.length == 0){
				art.dialog({content: "发货申请明细不能空",width: 220});
				return;
			}
			$.each(param.datas, function (i, v) {
				if (0 == parseFloat(v.qty)) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+"】本次发货数量为【"
						+v.qty+"'】,不允许保存",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return;
				}
				if (parseFloat(v.qty)-parseFloat(v.sendqty)-parseFloat(v.allsendqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加已发货数量总量超过领料数量,不允许保存",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return;
				}
			});
			if (isWrong) return false;
			var status = $("#status").val(), m_umState=$("#m_umState").val();
			$("#status").val("2");
			// $("#m_umState").val("T");
			Global.Form.submit("page_form", "${ctx}/admin/preItemAppSend/doSave", param, function(ret){
				$("#status").val(status);
				// $("#m_umState").val(m_umState);
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 700,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
						width: 200
					});
				}
			});
		}
		function saveClick() {//保存
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			var param= Global.JqGrid.allToJson("dataTable");
			var isWrong = false;
			if(param.datas.length == 0){
				art.dialog({content: "发货申请明细不能空",width: 220});
				return;
			}
			$.each(param.datas, function (i, v) {
				if (0 == parseFloat(v.sendqty)) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+"】本次发货数量为【"
						+v.sendqty+"'】,不允许保存",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return false;
				}
				if (parseFloat(v.qty)-parseFloat(v.sendqty)-parseFloat(v.allsendqty)-parseFloat(v.appqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加已发货数量加已申请数量总量超过领料数量,不允许保存",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return false;
				}
			});
			if (!isWrong) doSave(param);
		}
		function cancelClick() {//取消
			if ("Q" != m_umState) return;
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			art.dialog({
				content: "是否取消？",
				width: 200,
				okValue: "确定",
				ok: function () {
					var param= Global.JqGrid.allToJson("dataTable");
					doSave(param);
				},
				cancelValue: "取消",
				cancel: function () {
					return;
				}
			});
		}
		function returnClick() {//退回
			if ("H" != m_umState) return;
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			art.dialog({
				content: "是否退回？",
				width: 200,
				okValue: "确定",
				ok: function () {
					var param= Global.JqGrid.allToJson("dataTable");
					doSave(param);
				},
				cancelValue: "取消",
				cancel: function () {
					return;
				}
			});
		}
		function sendClick() {//发货
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			
			if (!$("#delivType").val()) {
			    art.dialog({content: "请选择配送方式"})
			    return
			}
			
			var param= Global.JqGrid.allToJson("dataTable");
			var isWrong = false;
			$.each(param.datas, function (i, v) {
				if (parseFloat(v.thisappsendqty) - parseFloat(v.sendqty) - parseFloat(v.hassendqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加本次已发货数量总量超过本次申请数量,不允许发货",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return false;
				}
				if (parseFloat(v.qty)-parseFloat(v.sendqty)-parseFloat(v.allsendqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加已发货数量总量超过领料数量,不允许发货",width: 400});
					$("#dataTable").jqGrid("setSelection",i+1);
					isWrong = true;
					return false;
				}
			});
			if (isWrong) return false;
			art.dialog({
				content: "是否发货？",
				width: 200,
				okValue: "确定",
				ok: function () {
					Global.Form.submit("page_form", "${ctx}/admin/preItemAppSend/doCkfhsqSend", null, function(ret){
						if(ret.rs==true){
							art.dialog({
								content: ret.msg,
								time: 700,
								beforeunload: function () {
									closeWin();
								}
							});
						}else{
							$("#_form_token_uniq_id").val(ret.token.token);
							art.dialog({
								content: ret.msg,
							});
						}
					});
				},
				cancelValue: "取消",
				cancel: function () {
					return;
				}
			});
		}
		function partSendClick() {//部分发货
			$("#page_form").bootstrapValidator("validate");
			if(!$("#page_form").data("bootstrapValidator").isValid()) return;
			
	        if (!$("#delivType").val()) {
                art.dialog({content: "请选择配送方式"})
                return
            }
			
			var param = {},datas = [],isSelNum = 0,isWrong = false;
			var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
			$.each(ids, function (i, id) {
				var row = $("#dataTable").jqGrid("getRowData", id);
				datas.push(row);
				if (0 != parseFloat(row.sendqty)) isSelNum++;
			});
			if(isSelNum == 0){
				art.dialog({content: "没有要发货领料明细，请选择要发货的材料明细<br/>且本次发货数量不为0",width: 220});
				return;
			}
			$.each(datas, function (j, v) {
				if (parseFloat(v.thisappsendqty) - parseFloat(v.sendqty) - parseFloat(v.hassendqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加本次已发货数量总量超过本次申请数量,不允许发货",width: 400});
					isWrong = true;
					return false;
				}
				if (parseFloat(v.qty)-parseFloat(v.sendqty)-parseFloat(v.allsendqty) < 0) {
					art.dialog({content: "材料【"+v.itemcode+"-"+v.itemdescr+
						"】本次发货数量加已发货数量总量超过领料数量,不允许发货",width: 400});
					isWrong = true;
					return false;
				}
			});
			if (isWrong) return false;
			art.dialog({
				content: "是否发货？",
				width: 200,
				okValue: "确定",
				ok: function () {
					var param = {"detailJson": JSON.stringify(datas)};
					Global.Form.submit("page_form", "${ctx}/admin/preItemAppSend/doCkfhsqSendByPart", param, function(ret){
						if(ret.rs==true){
							art.dialog({
								content: ret.msg,
								time: 700,
								beforeunload: function () {
									closeWin();
								}
							});
						}else{
							$("#_form_token_uniq_id").val(ret.token.token);
							art.dialog({
								content: ret.msg,
							});
						}
					});
				},
				cancelValue: "取消",
				cancel: function () {return;}
			});
		}
		function doSave(param) { //存储过程
			Global.Form.submit("page_form", "${ctx}/admin/preItemAppSend/doSave", param, function(ret){
				if(ret.rs==true){
					art.dialog({
						content: ret.msg,
						time: 700,
						beforeunload: function () {
							closeWin();
						}
					});
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content: ret.msg,
					});
				}
			});
		}
		function doClose() {
			var newData = $("#page_form").serialize();
			var param= Global.JqGrid.allToJson("dataTable");
			if (param.detailJson != originalDataTable || newData != originalData) {
				art.dialog({
					content: "数据已变动,是否保存？",
					width: 200,
					okValue: "确定",
					ok: function () {
						saveClick();
					},
					cancelValue: "取消",
					cancel: function () {
						closeWin();
					}
				});
			} else {
				closeWin();
			}
		}
		function setIADetail(data) { //设置材料申请信息
			if (data) {
				$("#custCode").val(data.custcode);
				$("#address").text(data.address+"(档案编号:"+data.documentno+")");
				$("#itemType1").val($.trim(data.itemtype1));
				$("#whCode").setComponent_wareHouse({
					showValue: data.whcode,
					showLabel: data.whcodedescr,
					callBack: setWHDescr
				});
				$("#whDescr").val(data.whcodedescr);
				$("#page_form").data("bootstrapValidator")  
					.updateStatus("openComponent_itemApp_iaNo", "NOT_VALIDATED", null)
					.validateField("openComponent_itemApp_iaNo") 
					.updateStatus("openComponent_wareHouse_whCode", "NOT_VALIDATED", null)
					.validateField("openComponent_wareHouse_whCode");
				$.ajax({
					url:"${ctx}/admin/preItemAppSend/goSendDetailJqGrid",
					type:"post",
					data:{no: $("#iaNo").val(), pks: ""},
					dataType:"json",
					async:false, 
					success:function (data) {
						if (data) {
							$.each(data.rows,function(i,v){
								var sendQty = parseFloat(v.qty)-parseFloat(v.sendqtydescr);
								var json = {
									lastupdatedby: "${sessionScope.USER_CONTEXT_KEY.czybh}",
									lastupdate: new Date(),
									expired: "F",
									actionlog: "ADD"
							  	};
								$.ajax({
									url:"${ctx}/admin/preItemAppSend/getSendDetail",
									type:"post",
									data:{no: v.no, refPk: v.pk, sendQty: sendQty},
									dataType:"json",
									async:false, 
									success:function (data2) {
										if (data2) {
											$.extend(data2, json);
											json = data2;
										}
									}
								});
							  	Global.JqGrid.addRowData("dataTable",json);
							});
						}
					}
				});
			}
		}
		function setWHDescr(data) { //设置仓库名称
			$("#whDescr").val(data.desc1);
			$("#page_form").data("bootstrapValidator")  
				.updateStatus("openComponent_wareHouse_whCode", "NOT_VALIDATED", null)
				.validateField("openComponent_wareHouse_whCode");
		}
		function afterInput() { //详细有数据，就不可以修改领料单号
			var hadData = Global.JqGrid.allToJson("dataTable");
			if (hadData.datas.length>0) {
				$("#iaNo").setComponent_itemApp({disabled: true});
			} else {
				$("#iaNo").setComponent_itemApp({disabled: false});
			}
		}
		$(function() {
			if ("A" == m_umState) {
				$("#cancelBtn").remove();
				$("#returnBtn").remove();
				$("#sendBtn").remove();
				$("#partSendBtn").remove();
				$("#iaNo").openComponent_itemApp({
					condition: {
						status: "CONFIRMED",
						type: "S",
						sendType: "2",
						itemRight: "${sessionScope.USER_CONTEXT_KEY.itemRight}",
						isAllApp: "1",
					},
					callBack: setIADetail,
				});
				$("#whCode").openComponent_wareHouse({
					callBack: setWHDescr
				});
				$("#status").val(0);
				$("#appCzy").val("${sessionScope.USER_CONTEXT_KEY.czybh}|${sessionScope.USER_CONTEXT_KEY.zwxm}");
				$("#appDate").val(formatTime(new Date()));
				$("#sendBachNo").openComponent_itemSendBatch({
					condition: {
						m_umState: "S",
					},
					disabled: true,
				});
			} else {
				$("#iaNo").openComponent_itemApp({
					showValue: "${preItemAppSend.iano}",
					condition: {
						status: "CONFIRMED",
						type: "S",
						sendType: "2",
						itemRight: "${sessionScope.USER_CONTEXT_KEY.itemRight}",
						isAllApp: "1",
					},
					callBack: setIADetail,
				});
				$("#whCode").openComponent_wareHouse({
					showValue: "${preItemAppSend.whcode}",
					showLabel: "${preItemAppSend.desc1}",
					callBack: setWHDescr,
					disabled: true,
				});
				$("#appCzy").val("${preItemAppSend.appczydescr}"?"${preItemAppSend.appczy}|${preItemAppSend.appczydescr}":"${preItemAppSend.appczy}");
				$("#sendCzy").val("${preItemAppSend.sendapp}"?"${preItemAppSend.sendczy}|${preItemAppSend.sendapp}":"${preItemAppSend.sendczy}");
				$("#sendBachNo").openComponent_itemSendBatch({
					condition: {
						m_umState: "S",
					},
					disabled: true,
				});
				if ("M" != m_umState) {
					$("#itemSave").remove();
					$("#itemDelete").remove();
				}
				switch (m_umState) {
					case "M":
						$("#cancelBtn").remove();
						$("#returnBtn").remove();
						$("#sendBtn").remove();
						$("#partSendBtn").remove();
						$("#whCode").setComponent_wareHouse({
							disabled: false,
						});
						break;
					case "T":
						$("#saveBtn").remove();
						$("#cancelBtn").remove();
						$("#returnBtn").remove();
						$("#sendBtn").remove();
						$("#partSendBtn").remove();
						break;
					case "Q":
						$("#submitBtn").remove();
						$("#saveBtn").remove();
						$("#returnBtn").remove();
						$("#sendBtn").remove();
						$("#partSendBtn").remove();
						break;
					case "H":
						$("#submitBtn").remove();
						$("#saveBtn").remove();
						$("#cancelBtn").remove();
						$("#sendBtn").remove();
						$("#partSendBtn").remove();
						break;
					case "F":
						$("#submitBtn").remove();
						$("#saveBtn").remove();
						$("#cancelBtn").remove();
						$("#returnBtn").remove();
						$("#partSendBtn").remove();
						$("#whCode").setComponent_wareHouse({
							disabled: false,
						});
						$("#sendBachNo").setComponent_itemSendBatch({
							disabled: false,
						});
						$("#sendCzy").parent().hide()
						$("#delivType").parent().show()
						break;
					case "S":
						$("#submitBtn").remove();
						$("#saveBtn").remove();
						$("#cancelBtn").remove();
						$("#returnBtn").remove();
						$("#sendBtn").remove();
						$("#whCode").setComponent_wareHouse({
							disabled: false,
						});
						$("#sendBachNo").setComponent_itemSendBatch({
							disabled: false,
						});
						$("#sendCzy").parent().hide()
						$("#delivType").parent().show()
						break;
					default:
						$("#submitBtn").remove();
						$("#saveBtn").remove();
						$("#cancelBtn").remove();
						$("#returnBtn").remove();
						$("#sendBtn").remove();
						$("#partSendBtn").remove();
						$("#sendBachNo").setComponent_itemSendBatch({
							showValue: "${preItemAppSend.sendbatchno}",
						});
						break;
				}
			}
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable",{
				url: "${ctx}/admin/preItemAppSend/goItemJqGrid",
				postData: postData,
				height: $(document).height()-$("#content-list").offset().top-70,
				styleUI: "Bootstrap", 
				rowNum: 10000000,
				cellEdit: true,
				cellsubmit: "clientArray",
				multiselect : "S"==m_umState,
				colModel: [
					{name: "pk", index: "pk", width: 60, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "refpk", index: "refpk", width: 60, label: "领料申请明细pk", sortable: true, align: "left", hidden: true},
					{name: "no", index: "no", width: 60, label: "编号", sortable: true, align: "left", hidden: true},
					{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
					{name: "itemdescr", index: "itemdescr", width: 160, label: "材料名称", sortable: true, align: "left"},
					{name: "fixareadescr", index: "fixareadescr", width: 120, label: "装修区域", sortable: true, align: "left"},
					{name: "sqldescr", index: "sqldescr", width: 70, label: "品牌", sortable: true, align: "left"},
					{name: "qty", index: "qty", width: 80, label: "领料数量", sortable: true, align: "right", sum: true},
					{name: "allsendqty", index: "allsendqty", width: 90, label: "已发货数量", sortable: true, align: "right", sum: true},
					{name: "appqty", index: "appqty", width: 90, label: "已申请数量", sortable: true, align: "right", sum: true},
					{name: "thisappsendqty", index: "thisappsendqty", width: 90, label: "本次申请数", sortable: true, align: "right", sum: true},
					{name: "hassendqty", index: "hassendqty", width: 90, label: "本次已发货数", sortable: true, align: "right", sum: true},
					{name: "sendqty", index: "sendqty", width: 100, label: "本次发货数量", sortable: true, align: "right", sum: true, 
						editable:m_umState=="F"?false:true, editrules: {number:true, required:true}, classes: "jqGrid_light_yellow_background"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter:formatTime, hidden: true},
					{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left", hidden: true},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left", hidden: true},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left", hidden: true}
				],
				loadonce: true,
				onCellSelect:function(rowid){
					if ("S"!=m_umState) $("#dataTable").jqGrid("setSelection",rowid);
				},
				loadComplete: function(){
					originalDataTable = Global.JqGrid.allToJson("dataTable").detailJson;
					originalData = $("#page_form").serialize();
				},
				gridComplete:function(){
					afterInput();
				},
				afterSaveCell : function(rowid,name,val,iRow,iCol) {
					if ("sendqty" == name) {
						var sendqtySum = $("#dataTable").getCol("sendqty",false,"sum");
						$("#dataTable").footerData("set",{"sendqty":myRound(sendqtySum,2)});
					}
				},
			});
			$("#page_form").bootstrapValidator({
				message : "请输入完整的信息",
				feedbackIcons : {
					validating : "glyphicon glyphicon-refresh"
				},
				fields: {  
					openComponent_itemApp_iaNo:{  
						validators: {  
							notEmpty: {  
								message: "请选择仓库发货单号"  
							}
						}  
					},
					openComponent_wareHouse_whCode:{
						validators: {
							notEmpty: {
								message: "仓库编号不能为空"
							}
						}
					},
				},
				submitButtons : "input[type='submit']"
			});
			replaceCloseEvt("update", doClose);//替换窗口右上角关闭事件
			$("#closeBtn").on("click",function(){
				doClose();
			});
			$("#saveBtn").on("click", function() {
				saveClick();
			});
			$("#submitBtn").on("click", function () {
				submitClick();
			});
			$("#cancelBtn").on("click", function () {
				cancelClick();
			});
			$("#returnBtn").on("click", function () {
				returnClick();
			});
			$("#sendBtn").on("click", function () {
				sendClick();
			});
			$("#partSendBtn").on("click", function () {
				partSendClick();
			});
			// 明细新增
			$("#itemSave").on("click",function(){
				if ("V" == m_umState) return false;
				$("#page_form").bootstrapValidator("validate");
				if(!$("#page_form").data("bootstrapValidator").isValid()) return;
				var ids = Global.JqGrid.allToJson("dataTable","refpk");
				var keys = ids.keys;
				Global.Dialog.showDialog("itemSave",{
					title:"发货申请明细——新增",
					url:"${ctx}/admin/preItemAppSend/goSendDetail",
					postData:{m_umState:"R", keys:keys, no: $("#iaNo").val()},
					width:965,
					height:625,
					returnFun : function(data){
						if(data){
							$.each(data,function(k,v){
								var sendQty = parseFloat(v.qty)-parseFloat(v.sendqtydescr);
								var json = {
									lastupdatedby: "${sessionScope.USER_CONTEXT_KEY.czybh}",
									lastupdate: new Date(),
									expired: "F",
									actionlog: "ADD"
							  	};
								$.ajax({
									url:"${ctx}/admin/preItemAppSend/getSendDetail",
									type:"post",
									data:{no: v.no, refPk: v.pk, sendQty: sendQty},
									dataType:"json",
									async:false, 
									success:function (data2) {
										if (data2) {
											$.extend(data2, json);
											json = data2;
										}
									}
								});
							  	Global.JqGrid.addRowData("dataTable",json);
							});
						}
					}
				});
			});
			/* 明细删除 */
			$("#itemDelete").on("click",function(){
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
						var rowIds = $("#dataTable").jqGrid("getDataIDs");
						$("#dataTable").jqGrid("setSelection",rowIds[0]);
					},
					cancel: function () {
						return true;
					}
				});
				
			});
			$("#itemExcel").on("click", function () {//导出Excel
				doExcelNow("仓库发货申请明细");
			});
		});
	</script>
</body>
</html>
