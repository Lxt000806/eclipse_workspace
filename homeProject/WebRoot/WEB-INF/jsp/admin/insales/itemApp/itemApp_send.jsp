<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理发货页面</title>
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
				if($("#m_umState").val() == "S"){//仓库发货
					$("#supplCodeDescr").parent().attr("hidden",true);
					$("#supplCode").parent().attr("hidden",true);
					$("#itemSendBatchNo").parent().removeAttr("hidden");
					$("#sendCount").parent().removeAttr("hidden");
					$("#manySendRsn").parent().removeAttr("hidden");
					if("${data.delivType }"=="4"){
						$("#delivType").attr("disabled",true);
					}else{
						/* $("#delivType").val("1");
						$("#delivType").parent().attr("hidden",true); */
					} 
					/* $("#delivType").attr("disabled",true); */
				}else if($("#m_umState").val() == "P"){//供应商直送
					$("#sendOutBut").addClass("hidden");
					$("#whcode").parent().attr("hidden",true);
					$("#whcodeDescr").parent().attr("hidden",true);
		   			$("#openComponent_itemSendBatch_itemSendBatchNo").attr("readonly",true);
					$("#openComponent_itemSendBatch_itemSendBatchNo").next().attr("data-disabled",true).css({
						"color":"#888"
					});
				}
				if($("#m_umState").val() == "V"){
		   			$("#openComponent_supplier_supplCode").attr("readonly",true);
					$("#openComponent_supplier_supplCode").next().attr("data-disabled",true).css({
						"color":"#888"
					});
		   			$("#openComponent_wareHouse_whcode").attr("readonly",true);
					$("#openComponent_wareHouse_whcode").next().attr("data-disabled",true).css({
						"color":"#888"
					});
				}
				
    			$("#sendCount").customComponent({
    				btnWidth: 60,
    				btnContent: "查看",
    				btnEvent: function(){
			        	Global.Dialog.showDialog("sendCountView",{
			        	  title:"已发货--查看",
			        	  url:"${ctx}/admin/itemPreApp/goSendCountView",
			        	  postData:{
			        	  	no: $("#no").val()
			        	  },
			        	  height: 600,
			        	  width:800,
			        	});
    				}
    			});
    			changeSendDate();
			});
			//修改发货时间触发
			function changeSendDate(){
				var itemType1="${data.itemType1}";
				var confirmDate="${data.confirmDate}";
				var notifySendDate="${data.notifySendDate}";
    			var sendDate=$("#sendDate").val();
    			if(notifySendDate!="" && notifySendDate!=null){
    				confirmDate=notifySendDate;
    			}
    			var days=dateMinus(confirmDate,sendDate);
    			if(days>72 && itemType1=="ZC"){
    				$("#delayReson,#delayRemark").parent().removeClass("hidden");
    				$("#isDelayed").val("1");
    			}else{
    				$("#delayReson,#delayRemark").parent().addClass("hidden");
    				$("#delayReson,#delayRemark").val("");
    				$("#isDelayed").val("0");
    			}
			}
			//日期相减
			function dateMinus(date2,date1){ 
				var date22 = new Date(date2.replace(/-/g, "/"));
				var date11 = new Date(date1.replace(/-/g, "/"));
				var days = date11.getTime() - date22.getTime(); 
				var day = parseInt(days / (1000 * 60 * 60 )); 
				if(isNaN(day)){
					return "";
				}
				return day; 
			}
			function getDoSendBefore(checkSupplCode){
				var isDelayed=$("#isDelayed").val();
				var delayReson=$("#delayReson").val();
				var delayRemark=$("#delayRemark").val();
				var no=$("#no").val();
				var custCode=$("#custCode").val();
				
				if(isDelayed=="1"){
					if(delayReson==""){
						art.dialog({
							content: "未及时发货，请填写延误原因！"
						});
						return;
					}
					if(delayRemark==""){
						art.dialog({
							content: "未及时发货，请填写延误说明！"
						});
						return;
					}
				}
				var supplCodes = $("#itemAppSendApplyDetailDataTable").jqGrid("getCol","code").join(",");
				$.ajax({
					url : "${ctx}/admin/itemApp/getDoSendBefore",
					data: {
						m_umState:$("#m_umState").val(),
						sendDate:$("#sendDate").val(),
						whcode:$("#whcode").val(),
						supplCode:$("#supplCode").val(),
						checkSupplCode:checkSupplCode == undefined?true:checkSupplCode,
						supplCodes:supplCodes,
						no:no,
						custCode:custCode
					},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "发货出现异常"
						});
				    },
				    success: function(obj){
				    	if(obj.datas.code == 1){
				    		art.dialog({
				    			content:"确定要进行发货吗",
				    			ok:function(){
				    				send();
				    			},
				    			cancel:function(){}
				    		});
				    	}else if(obj.datas.code == 400002){
				    		art.dialog({
				    			content:obj.datas.msg,
				    			ok:function(){
				    				getDoSendBefore(false);
				    			},
				    			cancel:function(){}
				    		});
				    	}else if(obj.datas.code == 400003){
				    		art.dialog({
				    			content:obj.datas.msg,
				    			ok:function(){
				    				send();
				    			},
				    			cancel:function(){}
				    		});
				    	}else{
				    		art.dialog({
				    			content:obj.datas.msg
				    		});
				    	}
				    }
	        	});
			}
			function send(){
				var param= Global.JqGrid.allToJson("itemAppSendApplyDetailDataTable");
				
/* 				$.extend(param,{
					m_umState:$("#m_umState").val(),
					no:$("#no").val(),
					whcode:$("#whcode").val(),
					sendDate:$("#sendDate").val(),
					itemSendBatchNo:$("#itemSendBatchNo").val(),
					supplCode:$("#supplCode").val()
				}); */
				
				$.extend(param, $("#dataForm").jsonForm());
				
	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doSend",
					data: param,
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
				        art.dialog({
							content: "发货保存出现异常"
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
			//下架
			function sendOut(){
				var ret=selectDataTableRow("itemAppSendApplyDetailDataTable");
				if($("#whcode").val()==""){
					art.dialog({
				    	content:"请选择仓库编号！"
				    });
				    return;
				}
				if(ret){
				 	Global.Dialog.showDialog("down",{
						title:"库位发货--下架",
						url:"${ctx}/admin/itemApp/goSoldOut",
						postData:{
							code:$("#whcode").val(),
							desc1:$("#whcodeDescr").val(),
							itCode:ret.itemcode,
							itemDescr:ret.itemdescr
						},
						height : 330,
						width : 460,
						returnFun:goto_query
					}); 
				}else{
					art.dialog({
				    	content:"请选择一条记录！"
				    });
				}
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			    	<div class="btn-group-xs" >
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="getDoSendBefore()">发货</button>
	    				<button id="sendOutBut" type="button" class="btn btn-system" onclick="sendOut()">下架</button>
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
						<input type="hidden" id="isDelayed" name="isDelayed" value="0" />
						<house:token></house:token>
						<ul class="ul-form">
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" value="${data.whcode }"/>
							</li>
							<li>
								<label>仓库名称</label>
								<input type="text" id="whcodeDescr" name="whcodeDescr" value="${data.whcodeDescr }"/>
							</li>
							<li>
								<label>供应商编号</label>
								<input type="text" id="supplCode" name="supplCode" value="${data.supplCode }"/>
							</li>
							<li>
								<label>供应商名称</label>
								<input type="text" id="supplCodeDescr" name="supplCodeDescr" value="${data.supplCodeDescr }"/>
							</li>
							<%-- <li hidden>
								<label>配送方式</label>
								<house:xtdm id="delivType" dictCode="DELIVTYPE"  value="${data.delivType }"></house:xtdm>
							</li> --%>
							<li>
								<label>发货日期</label>
								<input type="text" id="sendDate" name="sendDate" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" onchange="changeSendDate()"
									value="<fmt:formatDate value="${data.sendDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							</li>
							<li hidden>
								<label>配送批次</label>
								<input type="text" id="itemSendBatchNo" name="itemSendBatchNo"  value="${data.itemSendBatchNo }"/>
							</li>
							<li hidden>
								<label>已发货次数</label>
								<input type="text" id="sendCount" name="sendCount" value="${data.sendCount }" readonly/>
							</li>
							<li hidden>
								<label>多次配送原因</label>
								<house:xtdm id="manySendRsn" dictCode="MSENDRSN" value="${data.manySendRsn }"></house:xtdm>
							</li>
							<li>
								<label>配送方式</label>
								<house:xtdm id="delivType"  dictCode="DELIVTYPE" value="${data.delivType}" ></house:xtdm>
							</li>
							<li class="hidden">
								<label>延误原因</label>
								<house:xtdm id="delayReson" dictCode="APPDELAYRESON"  unShowValue="0,1,2,3"></house:xtdm>
							</li>
							<li class="hidden">
								<label class="control-textarea">延误说明</label>
								<textarea id="delayRemark" name="delayRemark" style="width:635px;" rows="3"></textarea>
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


