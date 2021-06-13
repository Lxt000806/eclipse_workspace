<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理仓库收货页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_itemSendBatch.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/customComponent.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
			.panelBar{
				height:46px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$("#whcode").openComponent_wareHouse({
					showValue:"${data.whcode}",
					showLabel:"${data.whcodeDescr}",
					condition:{itemType1:"${data.itemType1}"},
					callBack:function(ret){
						$("#whcodeDescr").val(ret.desc1);
					}
				});
				$("#supplCode").openComponent_supplier({
					showValue:"${data.supplCode}",
					showLabel:"${data.supplCodeDescr}",
					callBack:function(ret){
						$("#supplCodeDescr").val(ret.Descr);
					},
					condition:{
						itemRight:"${data.itemRight}"
					}
				});
				$("#itemSendBatchNo").openComponent_itemSendBatch({
					showValue:"${data.itemSendBatchNo}",
					condition:{
						m_umState:"${data.m_umState}"
					},
					valueOnly:true
				});
				$("#openComponent_itemSendBatch_itemSendBatchNo").attr("readonly",true);
				
		        //初始化表格
				Global.JqGrid.initEditJqGrid("itemAppSendApplyDetailDataTable",{
					url:"${ctx}/admin/itemApp/getSendAppDtlJqGrid",
					postData:{
						no:$("#no").val(),
						custCode:$("#custCode").val()
					},
					height:330,
					rowNum: 10000,
					colModel : [
						{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
						{name: "itemcode", index: "itemcode", width: 80, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
						{name: "supplierdescr", index: "supplierdescr", width: 170, label: "供应商名称", sortable: true, align: "left"},
						{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
						{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
						{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
						{name: "fixareadescr", index: "fixareadescr", width: 80, label: "装修区域", sortable: true, align: "left"},
						{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
						{name: "intproddescr", index: "intproddescr", width: 80, label: "集成产品", sortable: true, align: "left"},
						{name: "qty", index: "qty", width: 65, label: "数量", sortable: true, align: "right"},
						{name: "reqqty", index: "reqqty", width: 80, label: "需求数量", sortable: true, align: "right"},
						{name: "sendedqty", index: "sendedqty", width: 90, label: "已发货数量", sortable: true, align: "right"},
						{name: "confirmedqty", index: "confirmedqty", width: 85, label: "已审核数量", sortable: true, align: "right"},
						{name: "color", index: "color", width: 84, label: "色号", sortable: true, align: "left", editable:true},
						{name: "remarks", index: "remarks", width: 196, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
						{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
						{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
						{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true},
						
						{name: "code", index: "code", width: 84, label: "sp.code", sortable: true, align: "left", hidden: true}
		            ],
		            gridComplete:function(){
		            	if($("#m_umState").val() == "P"){
				            if("${data.supplCode}" == ""){
								var ids = $("#itemAppSendApplyDetailDataTable").jqGrid("getDataIDs");
								
								if(ids.length > 0){
									var ret = $("#itemAppSendApplyDetailDataTable").jqGrid("getRowData",ids[0]);
									$("#supplCode").openComponent_supplier({
										showValue:ret.code,
										showLabel:ret.supplierdescr,
										callBack:function(ret){
											$("#supplCodeDescr").val(ret.Descr);
										},
										condition:{
											itemRight:"${data.itemRight}"
										}
									});
									$("#supplCodeDescr").val(ret.supplierdescr);
								}
							}
		            	}
		            },
					beforeSelectRow:function(id){
						setTimeout(function(){
							relocate(id, "itemAppSendApplyDetailDataTable");
						},10);
					}
				});
			});
			
			function doWhReceive(){
	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doWhReceive",
					data: $("#dataForm").jsonForm(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "收货保存出现异常"
						});
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
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="doWhReceive()">收货</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }"/>
						<input type="hidden" id="no" name="no" value="${data.no }" />
						<input type="hidden" id="custCode" name="custCode" value="${data.custCode }"/>
						<house:token></house:token>
						<ul class="ul-form">
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode" value="${data.supplCode }"/>
							</li>
							<li>
								<label>供应商名称</label>
								<input type="text" id="supplCodeDescr" name="supplCodeDescr" value="${data.supplCodeDescr }"/>
							</li>
							<li>
								<label>收货仓库编号</label>
								<input type="text" id="whcode" name="whcode" value="${data.whcode }"/>
							</li>
							<li>
								<label>收货仓库名称</label>
								<input type="text" id="whcodeDescr" name="whcodeDescr" value="${data.whcodeDescr }"/>
							</li>
							<li id="intReplenishNo_li" hidden="intReplenishNo_li">
								<label>补货单号</label>
								<input type="text" id="intReplenishNo" name="intReplenishNo" value="${data.intReplenishNo }"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks">${data.remarks}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div  class="container-fluid" >  
				<ul class="nav nav-tabs" >
				    <li class="active"><a href="#itemAppSendApplyDetail" data-toggle="tab">领料申请明细</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="itemAppSendApplyDetail"  class="tab-pane fade in active"> 
						<table id="itemAppSendApplyDetailDataTable"></table>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


