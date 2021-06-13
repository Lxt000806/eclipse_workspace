<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>预算管理软装发货页面</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/customComponent.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
			.panelBar{
				height:46px;
			}
		</style>
		<script type="text/javascript">
			var befTheSendQty;
			$(function(){
				$("#whcode").openComponent_wareHouse({
					showValue:"${data.whcode}",
					showLabel:"${data.whcodeDescr}",
					callBack:function(ret){
						$("#whcodeDescr").val(ret.desc1);
					}
				});
				$("#sendCount").customComponent({
    				btnWidth: 60,
    				btnContent: "查看",
    				btnEvent: function(){
			        	Global.Dialog.showDialog("sendCountView",{
			        	   title:"已发货--查看",
			        	  url:"${ctx}/admin/itemPlan/goSoftSendCountView",
			        	  postData:{
			        	  	custCode: $("#custCode").val(),
			        	  	itemType1: $("#itemType1").val()
			        	  },
			        	  height: 600,
			        	  width:800,
			        	});
    				}
    			});
		        //初始化表格
				Global.JqGrid.initEditJqGrid("itemPlanSoftSendDataTable",{
					url:"${ctx}/admin/itemPlan/goSoftSendJqGrid",
					postData:{
						custCode:$("#custCode").val()
					},
					height:370,
					rowNum: 10000,
					multiselect: true,
					colModel : [
						{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
						{name: "fixareadescr", index: "fixareadescr", width: 80, label: "区域名称", sortable: true, align: "left"},
						{name: "itemcode", index: "itemcode", width: 74, label: "材料编号", sortable: true, align: "left"},
						{name: "itemdescr", index: "itemdescr", width: 180, label: "材料名称", sortable: true, align: "left"},
						{name: "supplierdescr", index: "supplierdescr", width: 163, label: "供应商名称", sortable: true, align: "left"},
						{name: "itemtype2descr", index: "itemtype2descr", width: 85, label: "材料类型2", sortable: true, align: "left", hidden: true},
						{name: "no", index: "no", width: 80, label: "批次号", sortable: true, align: "left", hidden: true},
						{name: "reqpk", index: "reqpk", width: 80, label: "领料标识", sortable: true, align: "left", hidden: true},
						{name: "fixareapk", index: "fixareapk", width: 84, label: "fixareapk", sortable: true, align: "left", hidden: true},
						{name: "intprodpk", index: "intprodpk", width: 84, label: "intprodpk", sortable: true, align: "left", hidden: true},
						{name: "intproddescr", index: "intproddescr", width: 80, label: "集成产品", sortable: true, align: "left", hidden: true},
						{name: "qty", index: "qty", width: 77, label: "领料数量", sortable: true, align: "right", sum: true, hidden: true},
						{name: "reqqty", index: "reqqty", width: 80, label: "需求数量", sortable: true, align: "right", sum: true},
						{name: "sendqty", index: "sendqty", width: 89, label: "已发货数量", sortable: true, align: "right", sum: true},
						{name: "thesendqty", index: "thesendqty", width: 107, label: "本次发货数量", sortable: true, align: "right", sum: true,editable:true,editrules: {number:true,required:true,minValue:0}},
						{name: "color", index: "color", width: 84, label: "色号", sortable: true, align: "left", editable:true},
						{name: "remarks", index: "remarks", width: 196, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 84, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
						{name: "lastupdatedby", index: "lastupdatedby", width: 84, label: "更新人员", sortable: true, align: "left", hidden: true},
						{name: "expired", index: "expired", width: 84, label: "是否过期", sortable: true, align: "left", hidden: true},
						{name: "actionlog", index: "actionlog", width: 84, label: "操作", sortable: true, align: "left", hidden: true},
						{name: "code", index: "code", width: 84, label: "供应商编号", sortable: true, align: "left", hidden: true},
						{name: "cost", index: "cost", width: 84, label: "cost", sortable: true, align: "right", hidden: true},
						{name: "aftqty", index: "aftqty", width: 84, label: "aftqty", sortable: true, align: "right", hidden: true},
						{name: "aftcost", index: "aftcost", width: 84, label: "aftcost", sortable: true, align: "right", hidden: true},
						{name: "projectcost", index: "projectcost", width: 84, label: "projectcost", sortable: true, align: "right", hidden: true},
						{name: "preappdtpk", index: "preappdtpk", width: 84, label: "preappdtpk", sortable: true, align: "left", hidden: true},
						{name: "shortqty", index: "shortqty", width: 84, label: "shortqty", sortable: true, align: "right", hidden: true},
						{name: "declareqty", index: "declareqty", width: 84, label: "declareqty", sortable: true, align: "right", hidden: true},
						{name: "itemtype2", index: "itemtype2", width: 84, label: "材料类型2", sortable: true, align: "right", hidden: true},	
		            ],
		            grouping: true, // 是否分组,默认为true
		            groupingView: {
		              groupField: ['itemtype2descr'], // 按照哪一列进行分组
		              groupColumnShow: [false], // 是否显示分组列
		              groupText: ['<b>材料类型2：{0}({1})</b>'], // 表头显示的数据以及相应的数据量
		              groupCollapse: false, // 加载数据时是否只显示分组的组信息
		              groupDataSorted: true, // 分组中的数据是否排序
		              groupOrder: ['asc'], // 分组后的排序
		              groupSummary: [false], // 是否显示汇总.如果为true需要在colModel中进行配置
		              summaryType: 'max', // 运算类型，可以为max,sum,avg</div>
		              summaryTpl: '<b>Max: {0}</b>',
		              showSummaryOnHide: true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		            },
		            gridComplete:function(){
		            	$("table[id=itemPlanSoftSendDataTable] tbody tr td[aria-describedby=itemPlanSoftSendDataTable_thesendqty]").css("background-color","#FFAA00");
		            },
					
					beforeEditCell:function(rowid,cellname,value,iRow,iCol){
			            data_iRow=iRow;
			            data_iCol=iCol;
			            befTheSendQty=$('#dataTable').getCell(rowid,"thesendqty");
	       	 		},
	                afterSaveCell:function(rowId,name,val,iRow,iCol){
	      			  var rowData = $("#itemPlanSoftSendDataTable").jqGrid("getRowData",rowId);
	      			  if(rowData.reqqty-rowData.sendqty < val){
		      			  art.dialog({
			                 	content: "本次发货数量不能大于需求数量-已发货数量"
			              });
			   			  return befTheSendQty;
	      			  }else{
	      				  rowData["qty"] = rowData.thesendqty;
	      		    	  Global.JqGrid.updRowData("itemPlanSoftSendDataTable",rowId,rowData); 
	      			  } 
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
						   	    	$("#itemAppPSendApplyDetailDataTable").setSelection(id);							
					   	    	}
							}else{
								$("#itemAppPSendApplyDetailDataTable tr[id="+id+"]").addClass("success");
			 					$("#itemAppPSendApplyDetailDataTable tr[id="+id+"] td[aria-describedby=itemAppPSendApplyDetailDataTable_thesendqty]").css("background-color","");
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
				});
				if($("#m_umState").val() == "V"){
					$("#openComponent_wareHouse_whcode").attr("readonly",true);
					$("#openComponent_wareHouse_whcode").next().attr("data-disabled",true).css({
						"color":"#888"
					});
					$("#sendBut").hide();
					
				}
				getSendCount();
			});
			
			function getDoSendBatchBefore(){
				var ids = $("#itemPlanSoftSendDataTable").jqGrid("getGridParam", "selarrrow");
				if(ids.length == 0){
					art.dialog({
						content:"请选择需要发货的材料"
					});				
					return ;
				}
				var isSelNum = 0;
				var theSendQty = 0;
				var itemType2Num=1;
				var itemType2=$("#itemPlanSoftSendDataTable").jqGrid("getRowData", ids[0]).itemtype2; 
				$.each(ids,function(i,id){
					if(itemType2!=$("#itemPlanSoftSendDataTable").jqGrid("getRowData", id).itemtype2){
						itemType2Num=2;
						return false;
					}
				}); 
				if(itemType2Num>1){
					art.dialog({
						content:"选择的材料类型2不同，请分开进行多次发货"
					});				
					return ;
				}
				for(var i=0;i<ids.length;i++){
					var ret = $("#itemPlanSoftSendDataTable").jqGrid("getRowData",ids[i]);
					if(parseFloat(ret.thesendqty) != 0){
						theSendQty += parseFloat(ret.thesendqty);
						isSelNum++;
					}
				}
				$.ajax({
					url : "${ctx}/admin/itemApp/getDoSendBatchBefore",
					data: {
						whcode:$("#whcode").val(),
						isSelNum:isSelNum,
						theSendQty:theSendQty,
						itemType1: $("#itemType1").val()
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
				    				sendBatch(itemType2);
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
			function sendBatch(itemType2){
				var param= Global.JqGrid.selectToJson("itemPlanSoftSendDataTable");	
				param.wareHouseCode=$("#whcode").val();
				param.itemType2=itemType2;
				$.extend(param, $("#dataForm").jsonForm());
	        	$.ajax({
	    			url : "${ctx}/admin/itemPlan/doSoftSend",
					data: param,
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
				    	if(obj.rs){
				    		art.dialog({
								content: obj.msg,
							});
				    		getSendCount();
				    		$("#itemPlanSoftSendDataTable").jqGrid("setGridParam",{postData:$("#dataForm").jsonForm(),page:1}).trigger("reloadGrid");
				    		$("#_form_token_uniq_id").val(obj.token.token);
		    			}else{
		    				art.dialog({
								content: obj.msg,
							});
		    				$("#_form_token_uniq_id").val(obj.token.token);
		    			} 	
				    }
	    		});
			}
			function getSendCount(){
				$.ajax({
					url:"${ctx}/admin/itemPreApp/getSoftSendCount",
					type:"post",
					data:{
						custCode: "${itemPreMeasure.custCode}",
		        	  	itemType1:"${itemPreMeasure.itemType1}"	
		        	},
					dataType:"json",
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
					},
					success:function(obj){
						if(obj){
							$("#sendCount").val(obj);
						}else{
							$("#sendCount").val(0);
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
	    				<button id="sendBut" type="button" class="btn btn-system" onclick="getDoSendBatchBefore()">发货</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="closeWin()">关闭</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm"  class="form-search">
						<house:token></house:token>
						<input type="hidden" id="m_umState" name="m_umState" value="${itemPreMeasure.m_umState}"  />
						<input type="hidden" id="custCode" name="custCode" value="${itemPreMeasure.custCode}" />
						<input type="hidden" id="itemType1" name="itemType1" value="${itemPreMeasure.itemType1}" />
						<input type="hidden" id="isDelayed" name="isDelayed" value="0" />
						<input type="hidden" id="delivType" name="delivType" value="1" />
						<ul class="ul-form">
							<li>
								<label>仓库编号</label>
								<input type="text" id="whcode" name="whcode" />
							</li>
							<li>
								<label>仓库名称</label>
								<input type="text" id="whcodeDescr" name="whcodeDescr"  />
							</li>
							<li>
								<label>发货日期</label>
								<input type="text" id="sendDate" name="sendDate" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									value="<fmt:formatDate value="${itemPreMeasure.sendDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							</li>
						
							<li>
								<label>已发货次数</label>
								<input type="text" id="sendCount" name="sendCount"  readonly/>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div  class="container-fluid" >  
				<ul class="nav nav-tabs" >
				    <li class="active"><a href="#itemPlanSoftSendDetail" data-toggle="tab">软装材料明细</a></li>  
				</ul>  
			    <div class="tab-content">  
					<div id="itemPlanSoftSendDetail"  class="tab-pane fade in active"> 
						<table id="itemPlanSoftSendDataTable"></table>
					</div> 
				</div>	
			</div>	
		</div>
	</body>
</html>


