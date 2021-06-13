<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>领料管理分批发货页面</title>
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
				$("#itemSendBatchNo").openComponent_itemSendBatch({
					showValue:"${data.itemSendBatchNo}",
					condition:{
						m_umState:"${data.m_umState}"
					},
					valueOnly:true
				});
				$("#openComponent_itemSendBatch_itemSendBatchNo").attr("readonly",true);
				
		        //初始化表格
				Global.JqGrid.initEditJqGrid("itemAppPSendApplyDetailDataTable",{
					url:"${ctx}/admin/itemApp/getPSendAppDtlJqGrid",
					postData:{
						no:$("#no").val(),
						custCode:$("#custCode").val()
					},
					height:370,
					rowNum: 10000,
					multiselect: true,
					colModel : [
						{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
						{name: "itemcode", index: "itemcode", width: 74, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
						{name: "supplierdescr", index: "supplierdescr", width: 163, label: "供应商名称", sortable: true, align: "left"},
						{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
						{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
						{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
						{name: "fixareadescr", index: "fixareadescr", width: 80, label: "装修区域", sortable: true, align: "left"},
						{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
						{name: "intproddescr", index: "intproddescr", width: 80, label: "集成产品", sortable: true, align: "left"},
						{name: "qty", index: "qty", width: 77, label: "领料数量", sortable: true, align: "right", sum: true},
						{name: "sendqty", index: "sendqty", width: 89, label: "已发货数量", sortable: true, align: "right", sum: true},
						{name: "thesendqty", index: "thesendqty", width: 107, label: "本次发货数量", sortable: true, align: "right", sum: true,editable:true,editrules: {number:true,required:true,custom:true,custom_func:function(value, colname,colId,rowId){var ret = $("#itemAppPSendApplyDetailDataTable").jqGrid("getRowData", rowId);;if(ret.qty - ret.sendqty < value) return [false,"<br/><br/>发货数量不能大于领料数量-已发货数量"]; return [true];}}},
						{name: "reqqty", index: "reqqty", width: 80, label: "需求数量", sortable: true, align: "right", sum: true},
						{name: "sendedqty", index: "sendedqty", width: 110, label: "总共已发货数量", sortable: true, align: "right", sum: true},
						{name: "confirmedqty", index: "confirmedqty", width: 85, label: "已审核数量", sortable: true, align: "right", sum: true},
						{name: "color", index: "color", width: 84, label: "色号", sortable: true, align: "left", editable:true},
						{name: "remarks", index: "remarks", width: 196, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
						{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
						{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
						{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true},
						
						{name: "code", index: "code", width: 84, label: "sp.code", sortable: true, align: "left", hidden: true},
						{name: "cost", index: "cost", width: 84, label: "cost", sortable: true, align: "right", hidden: true},
						{name: "aftqty", index: "aftqty", width: 84, label: "aftqty", sortable: true, align: "right", hidden: true},
						{name: "aftcost", index: "aftcost", width: 84, label: "aftcost", sortable: true, align: "right", hidden: true},
						{name: "projectcost", index: "projectcost", width: 84, label: "projectcost", sortable: true, align: "right", hidden: true},
						{name: "preappdtpk", index: "preappdtpk", width: 84, label: "preappdtpk", sortable: true, align: "left", hidden: true},
						{name: "shortqty", index: "shortqty", width: 84, label: "shortqty", sortable: true, align: "right", hidden: true},
						{name: "declareqty", index: "declareqty", width: 84, label: "declareqty", sortable: true, align: "right", hidden: true}
		            ],
		            gridComplete:function(){
		            	$("table[id=itemAppPSendApplyDetailDataTable] tbody tr td[aria-describedby=itemAppPSendApplyDetailDataTable_thesendqty]").css("background-color","#FFAA00");
		            	changeSendDate();
		            },
					beforeSelectRow:function(id,e){
						var cellIndex = e.target.cellIndex;
						var checked = e.target.checked;
						if( checked == undefined){
							setTimeout(function(){
								if($("#itemAppPSendApplyDetailDataTable tr[id="+id+"][class*='success']").length==1){
									if(cellIndex != 14 && cellIndex != 18){
										$("#itemAppPSendApplyDetailDataTable tr[id="+id+"][class*='success'] td").removeClass("success");
				 						$("#itemAppPSendApplyDetailDataTable tr[id="+id+"][class*='success']").removeClass("success").attr("aria-selected","false");
				 						$("#itemAppPSendApplyDetailDataTable tr[id="+id+"] td[aria-describedby=itemAppPSendApplyDetailDataTable_thesendqty]").css("background-color","#FFAA00");
				 						$("#itemAppPSendApplyDetailDataTable tr[id="+id+"] td[aria-describedby=itemAppPSendApplyDetailDataTable_color]").css("background-color","#FFAA00");
							   	    	$("#itemAppPSendApplyDetailDataTable").setSelection(id);							
						   	    	}
								}else{
									$("#itemAppPSendApplyDetailDataTable tr[id="+id+"]").addClass("success");
				 					$("#itemAppPSendApplyDetailDataTable tr[id="+id+"] td[aria-describedby=itemAppPSendApplyDetailDataTable_thesendqty]").css("background-color","");
				 					$("#itemAppPSendApplyDetailDataTable tr[id="+id+"] td[aria-describedby=itemAppPSendApplyDetailDataTable_color]").css("background-color","");
						   	    	$("#itemAppPSendApplyDetailDataTable").setSelection(id);
								}
							},10);
						}else{
							if(!checked){					
								setTimeout(function(){
									$("#dataTable tr[id="+id+"] td").removeClass("success");
								},10);
							}
						}
					},
					onSelectRow:function(){
	                   changeSendDate();
	                },
				});
				if($("#m_umState").val() == "V"){
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
    			//全选框，触发延误控制
    			$("#cb_itemAppPSendApplyDetailDataTable").click(function(){
    				changeSendDate();
    			});
			});
			//修改发货时间触发
			function changeSendDate(){
				var itemType1="${data.itemType1}";
				var confirmDate="${data.confirmDate}";
    			var sendDate=$("#sendDate").val();
    			var days=dateMinus(confirmDate,sendDate);
    			var selectedIds = $("#itemAppPSendApplyDetailDataTable").jqGrid('getGridParam', 'selarrrow');//选中的行id
    			var allIds = $("#itemAppPSendApplyDetailDataTable").getDataIDs();//所有行id
    			if(days>72 && itemType1=="ZC" && selectedIds.length==allIds.length){
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
				var day = parseInt(days / (1000 * 60 * 60)); 
				if(isNaN(day)){
					return "";
				}
				return day; 
			}
			function getDoSendBatchBefore(){
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
				var ids = $("#itemAppPSendApplyDetailDataTable").jqGrid("getGridParam", "selarrrow");
				var isSelNum = 0;
				var theSendQty = 0;
				var pks="";
				for(var i=0;i<ids.length;i++){
					var ret = $("#itemAppPSendApplyDetailDataTable").jqGrid("getRowData",ids[i]);
					if(parseFloat(ret.thesendqty) != 0){
						theSendQty += parseFloat(ret.thesendqty);
						isSelNum++;
						pks+=ret.pk+",";
					}
				}
				$.ajax({
					url : "${ctx}/admin/itemApp/getDoSendBatchBefore",
					data: {
						whcode:$("#whcode").val(),
						isSelNum:isSelNum,
						theSendQty:theSendQty,
						pks:pks,
						itemType1:"${data.itemType1}",
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
				    			content:"本次发货总数为【"+obj.datas.theSendQty+"】，确定要进行发货吗?",
				    			ok:function(){
				    				sendBatch();
				    			},
				    			cancel:function(){}
				    		});
				    	}else if(obj.datas.code == 400003){
				    		art.dialog({
				    			content:obj.datas.msg,
				    			ok:function(){
				    				sendBatch();
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
			function sendBatch(){
				var param= Global.JqGrid.selectToJson("itemAppPSendApplyDetailDataTable");

/* 				$.extend(param,{
					no:$("#no").val(),
					whcode:$("#whcode").val(),
					itemType1:$("#itemType1").val(),
					custCode:$("#custCode").val(),
					sendDate:$("#sendDate").val(),
					remarks:$("#remarks").val(),
					itemSendBatchNo:$("#itemSendBatchNo").val()
				}); */
				
				$.extend(param, $("#dataForm").jsonForm());
				
	        	$.ajax({
	    			url : "${ctx}/admin/itemApp/doSendBatch",
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
				    	art.dialog({
				    		content:obj.msg,
				    		ok:function(){
				    			if(obj.rs){
				    				closeWin();
				    			}else{
				    				$("#_form_token_uniq_id").val(obj.token.token);
				    			}
				    		}
				    	});
				    	
				    }
	    		});
			}
			//下架
			function sendOut(){
				var ids = $("#itemAppPSendApplyDetailDataTable").jqGrid('getGridParam', 'selarrrow');
				var ret=selectDataTableRow("itemAppPSendApplyDetailDataTable");
				if($("#whcode").val()==""){
					art.dialog({
				    	content:"请选择仓库编号！"
				    });
				    return;
				}
				if(ids.length>1){
					art.dialog({
				    	content:"每次只能选择一条记录进行下架操作！"
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
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="getDoSendBatchBefore()">发货</button>
	    				<button id="sendOutBut" type="button" class="btn btn-system" onclick="sendOut()">下架</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<house:token></house:token>
						<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState }" />
						<input type="hidden" id="no" name="no" value="${data.no }" />
						<input type="hidden" id="custCode" name="custCode" value="${data.custCode }" />
						<input type="hidden" id="itemType1" name="itemType1" value="${data.itemType1 }" />
						<input type="hidden" id="remarks" name="remarks" value="${data.remarks }" />
						<input type="hidden" id="isDelayed" name="isDelayed" value="0" />
						<ul class="ul-form">
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" value="${data.whcode }"/>
							</li>
							<li>
								<label>仓库名称</label>
								<input type="text" id="whcodeDescr" name="whcodeDescr" value="${data.whcodeDescr }" />
							</li>
							<li>
								<label>发货日期</label>
								<input type="text" id="sendDate" name="sendDate" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" onchange="changeSendDate()"
									value="<fmt:formatDate value="${data.sendDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							</li>
							<li>
								<label>配送批次</label>
								<input type="text" id="itemSendBatchNo" name="itemSendBatchNo" value="${data.itemSendBatchNo }"/>
							</li>
							<li>
								<label>已发货次数</label>
								<input type="text" id="sendCount" name="sendCount" value="${data.sendCount }" readonly/>
							</li>
							<li>
								<label>多次配送原因</label>
								<house:xtdm id="manySendRsn" dictCode="MSENDRSN" value="${data.manySendRsn }"></house:xtdm>
							</li>
							<li>
								<label>配送方式</label>
								<house:xtdm id="delivType" dictCode="DELIVTYPE" value="${data.delivType}"></house:xtdm>
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
				    <li class="active"><a href="#itemAppPSendApplyDetail" data-toggle="tab">领料申请明细</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="itemAppPSendApplyDetail"  class="tab-pane fade in active"> 
						<table id="itemAppPSendApplyDetailDataTable"></table>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


