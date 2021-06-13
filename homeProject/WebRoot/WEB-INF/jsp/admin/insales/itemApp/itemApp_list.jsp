<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>ItemApp列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
		function goto_query(){
			if($("#itemCodeDescr").val() != "" && ($("#custCode").val() == "" && $("#custAddress").val() == "")){
				art.dialog({
					content:"材料名称不为空，请输入客户编号或楼盘地址"
				});
				return;
			}

			var postData = $("#page_form").jsonForm();

			if(postData && postData.status && postData.status.indexOf("SEND") >= 0){
				postData.IsMaterialSendJob = "";
				$("#IsMaterialSendJob").val("");
			}

			$("#dataTable").jqGrid("setGridParam", {
				url:"${ctx}/admin/itemApp/goJqGrid",
				postData: postData,
				page:1
			}).trigger("reloadGrid");
		}
		
		function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#page_form select").val("");
			$("#custType").val("");
			$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
			$("#whcode").val("");
			$.fn.zTree.getZTreeObj("tree_whcode").checkAllNodes(false);
			$("#productType").val("");
			$.fn.zTree.getZTreeObj("tree_productType").checkAllNodes(false);
			$("#delayReson").val("");
            $.fn.zTree.getZTreeObj("tree_delayReson").checkAllNodes(false);
		}
		
		function del(){
			var ids = [];
			$("div.content-list").find("table>tbody").find("input[type='checkbox']").each(function(){
				if(this.checked){
					ids.push(this.value);
				}
			});
			if(ids.length <1){
				 art.dialog({
					content: "请选择需要删除的记录",
					lock: true
				 });
				return ;
			}
			art.dialog({
				 content:"您确定要删除记录吗？",
				 lock: true,
				 width: 260,
				 height: 100,
				 ok: function () {
			        $.ajax({
						url : "${ctx}/admin/itemApp/doDelete",
						data : "deleteIds="+ids.join(","),
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "删除记录出现异常"
							});
					    },
					    success: function(obj){
					    	if(obj.rs){
					    		art.dialog({
									content: obj.msg,
									time: 1000,
									beforeunload: function () {
										goto_query();
								    }
								});
					    	}else{
					    		art.dialog({
									content: obj.msg
								});
					    	}
						}
					});
			    	return true;
				},
				cancel: function () {
					return true;
				}
			});
		}
		
		function doExcel(){
			$.form_submit($("#page_form").get(0), {
				"action": "${ctx}/admin/itemApp/doExcel"
			});
		}
		/**初始化表格*/
		$(function(){
		    $("#supplCode").openComponent_supplier({
		    	condition:{
		    		itemRight:"${itemApp.itemRight}"
		    	}
		    });
			$("#custCode").openComponent_customer({
				condition:{
					mobileHidden:"1",
					status:"4,5",
					disabledEle:"status_NAME"
				}
			});
			$("#appCzy").openComponent_employee();
/* 			$("#itemCode").openComponent_item(); */
	
			//初始化材料类型1，2，3三级联动
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2");
	
	      	//初始化表格
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-95,
				sortable:true,
				mustUseSort:true,
				colModel : [			
					{name: "no", index: "no", width: 75, label: "领料单号", sortable: true, align: "left", frozen: true},
					{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left", frozen: true},
					{name: "address", index: "address", width: 200, label: "楼盘", sortable: true, align: "left", frozen: true},
					{name: "region1descr", index: "region1descr", width: 50, label: "片区", sortable: true, align: "left", frozen: true},
					{name: "regiondescr", index: "regiondescr", width: 70, label: "配送区域", sortable: true, align: "left", frozen: true},
					
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "status", index: "status", width: 120, label: "状态", sortable: true, align: "left",hidden:true},
					{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
					{name: "typedescr", index: "typedescr", width: 70, label: "领料类型", sortable: true, align: "left"},
					{name: "itemtype1descr", index: "itemtype1descr", width: 75, label: "材料类型1", sortable: true, align: "left"},
					{name: "itemtype2descr", index: "itemtype2descr", width: 75, label: "材料类型2", sortable: true, align: "left"},
					{name: "issetitemdescr", index: "issetitemdescr", width: 70, label: "套餐材料", sortable: true, align: "left"},
					{name: "isservicedescr", index: "isservicedescr", width: 80, label: "服务性产品", sortable: true, align: "left"},
					{name: "iscupboarddescr", index: "iscupboarddescr", width: 80, label: "是否橱柜", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
					{name: "toconfirmdate", index: "toconfirmdate", width: 120, label: "提交审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastpaydate", index: "lastpaydate", width: 120, label: "最后付款日期", sortable: true, align: "left", formatter: formatTime},
					{name: "sendtypedescr", index: "sendtypedescr", width: 75, label: "发货类型", sortable: true, align: "left"},
					{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
					{name: "warehouse", index: "warehouse", width: 100, label: "仓库", sortable: true, align: "left"},
					{name: "preappno", index: "preappno", width: 80, label: "预申请单号", sortable: true, align: "left"},
					{name: "delivtypedescr", index: "delivtypedescr", width: 85, label: "配送方式", sortable: true, align: "left"},
					{name: "splstatus", index: "splstatus", width: 96, label: "供应商状态", sortable: true, align: "left",hidden:true},
					{name: "splstatusdescr", index: "splstatusdescr", width: 80, label: "供应商状态", sortable: true, align: "left"},
					{name: "arrivedate", index: "arrivedate", width: 95, label: "预计到货时间", sortable: true, align: "left", formatter: formatTime},
					{name: "splremark", index: "splremark", width: 150, label: "供应商备注", sortable: true, align: "left"},
					{name: "appname", index: "appname", width: 75, label: "申请人员", sortable: true, align: "left"},
					{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
					{name: "confirmname", index: "confirmname", width: 75, label: "审批人员", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
					{name: "notifysenddate", index: "notifysenddate", width: 120, label: "通知发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "senddate", index: "senddate", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
					{name: "arriveconfirmdate", index: "arriveconfirmdate", width: 120, label: "到货确认日期", sortable: true, align: "left", formatter: formatTime},
					{name: "sendname", index: "sendname", width: 75, label: "发货人员", sortable: true, align: "left"},
					{name: "delayresondescr", index: "delayresondescr", width: 70, label: "延误原因", sortable: true, align: "left"},
					{name: "delayremark", index: "delayremark", width: 170, label: "延误说明", sortable: true, align: "left"},
					{name: "producttypedescr", index: "producttypedescr", width: 80, label: "产品类型", sortable: true, align: "left"},
					{name: "arrivespldate", index: "arrivespldate", width: 75, label: "到厂日期", sortable: true, align: "left", formatter: formatTime},
					{name: "amount", index: "amount", width: 70, label: "成本总价", sortable: true, align: "right", hidden: true},
					{name: "projectamount", index: "projectamount", width: 120, label: "项目经理结算总价", sortable: true, align: "right", hidden: true},
					{name: "puno", index: "puno", width: 85, label: "采购单号", sortable: true, align: "left"},
					{name: "whcheckoutstatusdescr", index: "whcheckoutstatusdescr", width: 95, label: "出库记账状态", sortable: true, align: "left"},
					{name: "whcheckoutno", index: "whcheckoutno", width: 110, label: "出库记账单号", sortable: true, align: "left"},
					{name: "whcheckoutdocumentno", index: "whcheckoutdocumentno", width: 105, label: "出库记账凭证号", sortable: true, align: "left"},
					{name: "custdescr", index: "custdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
					{name: "projectman", index: "projectman", width: 70, label: "项目经理", sortable: true, align: "left"},
					{name: "phone", index: "phone", width: 75, label: "电话", sortable: true, align: "left"},
					{name: "prjdept2descr", index: "prjdept2descr", width: 65, label: "部门", sortable: true, align: "left"},
					{name: "mainmandescr", index: "mainmandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
					{name: "sendbatchno", index: "sendbatchno", width: 75, label: "配送批次", sortable: true, align: "left"},
					{name: "printtimes", index: "printtimes", width: 70, label: "打印次数", sortable: true, align: "right"},
					{name: "printczydescr", index: "printczydescr", width: 65, label: "打印人", sortable: true, align: "left"},
					{name: "printdate", index: "printdate", width: 140, label: "打印日期", sortable: true, align: "left", formatter: formatTime},
					{name: "softtnstallleader", index: "softtnstallleader", width: 95, label: "软装安装组长", sortable: true, align: "left"},
					{name: "checkstatusdescr", index: "checkstatusdescr", width: 90, label: "客户结算状态", sortable: true, align: "left"},
					{name: "whreceivename", index: "whreceivename", width: 75, label: "收货人", sortable: true, align: "left"},
					{name: "whreceivedate", index: "whreceivedate", width: 120, label: "收货时间", sortable: true, align: "left", formatter: formatTime},
					{name: "replenishno", index: "replenishno", width: 75, label: "补货单号", sortable: true, align: "left"},
					{name: "entrustprocstatusdescr", index: "entrustprocstatusdescr", width: 100, label: "委托加工状态", sortable: true, align: "left"},
					{name: "entrustprocsenddate", index: "entrustprocsenddate", width: 120, label: "委外发出日期", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
					
					{name: "type", index: "type", width: 120, label: "type", sortable: true, align: "left",hidden:true},
					{name: "materworktype2", index: "materworktype2", width: 120, label: "materworktype2", sortable: true, align: "left",hidden:true},
					{name: "custtype", index: "custtype", width: 120, label: "custtype", sortable: true, align: "left",hidden:true},
					{name: "itemtype1", index: "itemtype1", width: 120, label: "itemtype1", sortable: true, align: "left",hidden:true},
					{name: "itemtype2", index: "itemtype2", width: 120, label: "itemtype2", sortable: true, align: "left",hidden:true},
					{name: "custcode", index: "custcode", width: 120, label: "custcode", sortable: true, align: "left",hidden:true},
					{name: "whcode", index: "whcode", width: 120, label: "whcode", sortable: true, align: "left",hidden:true},
					{name: "iscupboard", index: "iscupboard", width: 120, label: "iscupboard", sortable: true, align: "left",hidden:true},
					{name: "sendtype", index: "sendtype", width: 120, label: "sendtype", sortable: true, align: "left",hidden:true},
					{name: "delivtype", index: "delivtype", width: 120, label: "delivtype", sortable: true, align: "left",hidden:true}
	  
	            ],
	            gridComplete:function(){
                	$(".ui-jqgrid-bdiv").scrollTop(0);
	            	frozenHeightReset("dataTable");
	            	$("#submitCheckItemApp").removeAttr("disabled");
	            },
	            ondblClickRow:function(rowid,iRow,iCol,e){
	            	
					var ret = $("#dataTable").jqGrid("getRowData",rowid);
					
					if(ret){
			        	Global.Dialog.showDialog(ret.type=="S"?"itemAppSend":"itemAppReturn",{
			        	  title:ret.type=="S"?"领料单--查看":"领料退回--查看",
			        	  url:"${ctx}/admin/itemApp/goLlgzView",
			        	  postData:{
			        	  	type:ret.type,
			        	  	no:ret.no
			        	  },
			        	  height: 730,
			        	  width:ret.type=="S"?1400:1300,
			        	});
					}
	            }
			});
			
			$("#dataTable").jqGrid("setFrozenColumns");
			
	        //新增领料
	        $("#addItemApp").on("click",function(){
	            //新增窗口
	        	Global.Dialog.showDialog("itemAppAdd",{
	        		title:"新增领料单",
	        	  	url:"${ctx}/admin/itemApp/goSave",
	        	  	height: 730,
	        	  	width:1400,
	        	  	returnFun:goto_query
	        	});
	        });
	        //领料退回
	        $("#returnItemApp").on("click",function(){
	        	Global.Dialog.showDialog("returnAdd",{
	        		title:"领料退回--新增",
	        		url:"${ctx}/admin/itemApp/goLlxxth",
	        	  	height: 730,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
	        });
	        //领料修改
	        $("#updateItemApp").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	if(ret){
	        		if(ret.status.trim() != "OPEN" && ret.status.trim() != "CONRETURN"
        				 && ret.status.trim() != "WAITCON"){	//领料单状态“待审核”，要允许修改领料单 add by zb on 20200328
	        			art.dialog({
	        				content:"领料单处于【"+ret.statusdescr+"】状态,不能再作修改"
	        			});
	        			return;
	        		}
	        		if(ret.splstatus.trim() >= "4"){
	        			art.dialog({
	        				content:"供应商已作确认的领料单,不能再作修改"
	        			});
	        			return;
	        		}
	        		var width = 1300;
	        		var title = "领料退回--修改";
	        		if(ret.type == "S"){
	        			width = 1400;
	        			title = "领料单--修改";
	        		}
		        	Global.Dialog.showDialog("itemAppUpdate",{
		        		title:title,
		        	  	url:"${ctx}/admin/itemApp/goUpdate",
		        	  	height: 730,
		        	  	width:width,
		        	  	postData:{
		        	  		no:ret.no,
		        	  		type:ret.type
		        	  	},
		        	  	returnFun:goto_query
		        	});
	        	}else{
	        		art.dialog({
	        			content:"请选择一条记录"
	        		});
	        	}
	        });
	        //提交审核
	        $("#submitCheckItemApp").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	if(ret){
	        		if(ret.status.trim() != "OPEN" && ret.status.trim() != "CONRETURN" && ret.status.trim() != "WAITCON"){
	        			art.dialog({
	        				content:"领料单处于【"+ret.statusdescr+"】状态,不能进行提交审核"
	        			});
	        			return ;
	        		}
	        		if(ret.type.trim()!="S"){
	        			art.dialog({
	        				content:"只有领料类型为【发货】才能进行提交审核操作"
	        			});
	        			return ;
	        		}
	        		var result=hasZero();
	        		if(result=="1"){
	        			art.dialog({
	        				content:"存在下单数量为0不允许提交审核"
	        			});
	        			return ;
	        		}
	        		$("#submitCheckItemApp").attr("disabled",true);
		        	$.ajax({
						url : "${ctx}/admin/itemApp/doSubmitCheck",
						data: {
							custCode:ret.custcode,
							appNo:ret.no,
							m_umState:'TC',
							isCupboard:ret.iscupboard,
							itemType1:ret.itemtype1,
							itemType2:ret.itemtype2,
							custType:ret.custtype
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "审核出现异常",
							});
							$("#submitCheckItemApp").removeAttr("disabled");
					    },
					    success: function(obj){
					    		if(obj.msg=="审核通过"){
								    art.dialog({
								    	content:obj.msg,
								    	time:1000
								    });
					    		}else{
					    			art.dialog({
								    	content:obj.msg.split("&")[0],
								    });
					    		}
							    goto_query();
					    }
		        	});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }
	        });
	        //审核
	        $("#checkItemApp").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	if(ret){
	        		if(ret.status.trim() != "OPEN" && ret.status.trim() != "CONRETURN" && ret.status.trim() != "WAITCON"){
	        			art.dialog({
	        				content:"领料单处于【"+ret.statusdescr+"】状态,不能进行审核"
	        			});
	        			return ;
	        		}
		        	$.ajax({
						url : "${ctx}/admin/itemApp/getSendReturnCheckRight",
						data: {
							type:ret.type,
							no:ret.no
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "审核出现异常"
							});
					    },
					    success: function(obj){
					    	if(obj.hasCheckRight){
	        					var width = 1300;
				        		var title = "领料退回--审核";
				        		if(ret.type == "S"){
				        			width = 1400;
				        			title = "领料单--审核";
				        		}
					        	Global.Dialog.showDialog("itemAppCheck",{
					        		title:title,
					        	  	url:"${ctx}/admin/itemApp/goCheck",
					        	  	height: 730,
					        	  	width:width,
					        	  	postData:{
					        	  		no:ret.no,
					        	  		type:ret.type,
					        	  		materWorkType2:ret.materworktype2,
					        	  		custType:ret.custtype
					        	  	},
					        	 	returnFun:goto_query
					        	});
					    	}
					    	if(obj.errorFlag){
					    		art.dialog({
					    			content:obj.errorInfo
					    		});
					    	}
					    }
		        	});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }
	        });
	        //反审核
	        $("#returnCheckItemApp").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	if(ret){
		        	$.ajax({
						url : "${ctx}/admin/itemApp/getUnCheckRight",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "反审核出现异常"
							});
					    },
					    success: function(obj){
					    	if(!obj.rs){
					    		art.dialog({content:obj.msg});
					    		return;
					    	}
					    	art.dialog({
					    		content:"确认要对["+ret.no+"]领料单进行反审核操作吗",
					    		ok:function(){
					    			$.ajax({
										url : "${ctx}/admin/itemApp/doUnCheck",
										data: {
											no:ret.no.trim()
										},
										contentType: "application/x-www-form-urlencoded; charset=UTF-8",
										dataType: "json",
										type: "post",
										cache: false,
										error: function(){
									        art.dialog({
												content: "反审核出现异常"
											});
									    },
									    success:function(obj){
										  art.dialog({
										  	content:obj.msg,
										  	ok:function(){
										  		goto_query();
										  	}
										  });
									    }
					    			});
					    		},
					    		cancel:function(){}
					    	});
					    }
		        	});
		        }else{
		        	art.dialog({content:"请选择一条记录"});
		        }
	        });
	        //供应商直送
	        $("#supplierSend").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoSendBefore",
						data: {
							no:ret.no.trim(),
							m_umState:"P"
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "供应商直送出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppSupplierSend",{
					        		title:"领料管理 - 供应商发货",
					        	  	url:"${ctx}/admin/itemApp/goSendBySuppl",
					        	  	postData:{
					        	  		no:ret.no.trim(),
					        	  		custCode:ret.custcode,
					        	  		m_umState:"P"
					        	  	},
					        	  	height: 600,
					        	  	width:1250,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }
	        });
	        //仓库收货
	        $("#whReceive").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	if(ret.status.trim()!="CONFIRMED"){
	        		art.dialog({
		        		content:"请选择审核状态的领料单！"
		        	});
		        	return;
	        	}
	        	if(ret.delivtype!="4"){
	        		art.dialog({
		        		content:"请选择配送方式为发货到公司仓的领料单！"
		        	});
		        	return;
	        	}
	        	if(ret.sendtype!="1"){
	        		art.dialog({
		        		content:"请选择发货类型为供应商直送的领料单！"
		        	});
		        	return;
	        	}
	        	if(ret.splstatus!="0" && ret.splstatus!="2" && ret.splstatus!="6"){
	        		art.dialog({
		        		content:"供应商状态未处于未接收、已接收、发送公司仓，不允许该操作！"
		        	});
		        	return;
	        	}
	        	if(ret){
					Global.Dialog.showDialog("itemAppSupplierSend",{
		        		title:"领料管理 - 仓库收货",
		        	  	url:"${ctx}/admin/itemApp/goWhReceive",
		        	  	postData:{
		        	  		no:ret.no.trim(),
		        	  		custCode:ret.custcode,
		        	  	},
		        	  	height: 600,
		        	  	width:1250,
		        	  	returnFun:goto_query
		        	});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }
	        });
	        //仓库发货
	        $("#warehouseSendAll").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	var delivType=ret.delivtype;
	        	if(ret.delivtype=="4" && ret.splstatus!="7"){
        			art.dialog({
		        		content:"配送方式为发货公司仓时，供应商状态为公司仓已收货的可操作！"
		        	});
		        	return;
	        	}
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoSendBefore",
						data: {
							no:ret.no.trim(),
							m_umState:"S"
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "仓库发货出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppWarehouseSend",{
					        		title:"领料管理 - 仓库发货",
					        	  	url:"${ctx}/admin/itemApp/goSend",
					        	  	postData:{
					        	  		no:ret.no.trim(),
					        	  		custCode:ret.custcode,
					        	  		m_umState:"S",
					        	  		whcode:ret.whcode,
					        	  		whcodeDescr:ret.warehouse
					        	  	},
					        	  	height: 600,
					        	  	width:1250,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	        	
	        });
	        //仓库分批发货
	        $("#warehouseSendSome").on("click",function(){
	        
       			var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoSendBatchBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "仓库分批发货出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppWarehouseSendSome",{
					        		title:"领料管理 - 仓库分批发货",
					        	 	url:"${ctx}/admin/itemApp/goSendBatch",
					        	  	postData:{
					        	  		no:ret.no.trim()
					        	  	},
					        	  	height: 650,
					        	  	width:1250,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	     	
	        });
	        //缺货
	        $("#shortage").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoShortageBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "缺货操作出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppShortage",{
					        		title:"领料管理 - 缺货",
					        	  	url:"${ctx}/admin/itemApp/goShortage",
					        	  	postData:{
					        	  		no:ret.no.trim()
					        	  	},
					        	  	height: 550,
					        	  	width:1250,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	     	
	        });
	        //发货备注
	        $("#sendMemo").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoSendMemoBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "发货备注出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppSendMemo",{
					        		title:"领料管理 - 发货备注",
					        	  	url:"${ctx}/admin/itemApp/goSendMemo",
					        	  	postData:{
					        	  		no:ret.no.trim()
					        	  	},
					        	  	height: 300,
					        	  	width:800,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	     	
	        });
	        //配送批次
	        $("#connectSendBatch").on("click",function(){
	        
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoConnSendBatchBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "配送批次出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppConnSendBatch",{
					        		title:"关联配送批次",
					        	  	url:"${ctx}/admin/itemApp/goConnSendBatch",
					        	  	postData:{
					        	  		no:ret.no.trim(),
					        	  		sendBatchNo:ret.sendbatchno
					        	  	},
					        	  	height: 300,
					        	  	width:800,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	
	        });
	        //设计师确认
	        $("#designerConf").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
					$.ajax({
						url : "${ctx}/admin/itemApp/getGoDesignerConfBefore",
						data: {
							no:ret.no.trim()
						},
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						dataType: "json",
						type: "post",
						cache: false,
						error: function(){
					        art.dialog({
								content: "配送批次出现异常"
							});
					    },
					    success:function(obj){
					    	if(obj.rs){
					        	Global.Dialog.showDialog("itemAppConnSendBatch",{
					        		title:"领料单--设计师确认",
					        	  	url:"${ctx}/admin/itemApp/goDesignerConf",
					        	  	postData:{
					        	  		no:ret.no.trim(),
					        	  		sendBatchNo:ret.sendbatchno
					        	  	},
					        	  	height: 730,
					        	  	width:1400,
					        	  	returnFun:goto_query
					        	});
					    	}else{
					    		art.dialog({
					    			content:obj.msg
					    		});
					    	}
					    }
	    			});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	
	        });
	        //领料跟踪
	        $("#llgz").on("click",function(){
	        	Global.Dialog.showDialog("llgz",{
	        	  title:"领料跟踪",
	        	  url:"${ctx}/admin/itemApp/goLlgz",
	        	  height: 650,
	        	  width:1300,
	        	  returnFun:goto_query
	        	});
	        });
	        //查看
	        $("#showItemApp").on("click",function(){
				var ret = selectDataTableRow("dataTable");
				
				if(ret){
		        	Global.Dialog.showDialog(ret.type=="S"?"itemAppSend":"itemAppReturn",{
		        		title:ret.type=="S"?"领料单--查看":"领料退回--查看",
		        	  	url:"${ctx}/admin/itemApp/goLlgzView",
		        	  	postData:{
		        	  		type:ret.type,
		        	  		no:ret.no
		        	  	},
		        	  	height: 730,
		        	  	width:ret.type=="S"?1400:1300,
		        	});
				}else{
					art.dialog({
						content:"请选择一条记录"
					});
				}
	        });
	        //明细查询
	        $("#showItemAppDetail").on("click",function(){
	        	Global.Dialog.showDialog("detailQuery",{
	        		title:"领料明细查询",
	        	  	url:"${ctx}/admin/itemApp/goDetailQuery",
	        	  	height: 650,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
	        });
	        //发货单查询
	        $("#llglSendList").on("click",function(){
	        	Global.Dialog.showDialog("llglSendList",{
	        		title:"发货单查询",
	        	  	url:"${ctx}/admin/itemApp/goLlglSendList",
	        	  	height: 650,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
	        });
	        //发送短信通知
	        $("#itemAppMsg").on("click",function(){
				var ret = selectDataTableRow("dataTable");
				
				if(ret){
		        	Global.Dialog.showDialog("itemAppMsg",{
		        		title:"领料短信发送",
		        	  	url:"${ctx}/admin/itemApp/goItemAppMsg",
		        	  	postData:{
		        	  		no:ret.no, 
		        	  		custCode:ret.custcode
		        	  	},
		        	  	height: 600,
		        	 	width: 900,
		        	  	returnFun:goto_query
		        	});
				}else{
					art.dialog({
						content:"请选择一条记录"
					});
				}
	        }); 
	        
	        //打印
	        $("#itemAppPrint").on("click",function(){
	        	var ret = selectDataTableRow("dataTable");
	        	
	        	if(ret){
	        		Global.Dialog.showDialog("itemAppPrint",{
		        		title:"领料单--打印",
		        	  	url:"${ctx}/admin/itemApp/goPrint",
		        	  	postData:{
		        	  		no:ret.no.trim()
		        	  	},
		        	  	height: 380,
		        	 	width:670,
		        	  	returnFun:goto_query
		        	});
		        }else{
		        	art.dialog({
		        		content:"请选择一条记录"
		        	});
		        }	
	        });
	        
	        //批量打印
	        $("#itemAppBatchPrint").on("click",function(){
	        	Global.Dialog.showDialog("itemAppPrintBatch",{
	        		title:"领料单--批量打印",
	        	  	url:"${ctx}/admin/itemApp/goPrintBatch",
	        	  	height: 650,
	        	  	width:1200,
	        	  	returnFun:goto_query
	        	});
	        });
	        //输出到Excel
	        $("#itemAppExcel").on("click",function(){
				var str = $("#dataTablePager_right div").html();
				if(str != ""){
					str = str.substring(str.indexOf("共")+1,str.indexOf("条"));
					str = str.replace(/,/g,"");
					if(parseInt(str) > 100000){
						art.dialog({content:"数据超过100000条,无法导出"});
						return;
					}
					doExcelAll("${ctx}/admin/itemApp/doExcelItemAppList"
					); 
				}else{
					art.dialog({content:"请先检索出数据"});
				}
	        });
	        
	        //连续新增
	        $("#contAddItemApp").on("click",function(){
	            //新增窗口
	        	Global.Dialog.showDialog("itemAppContAdd",{
	        		title:"新增领料单",
	        	  	url:"${ctx}/admin/itemApp/goContAdd",
	        	  	height: 730,
	        	  	width:1400,
	        	  	returnFun:goto_query
	        	});
	        });
	        //委托加工登记
	        $("#entrustProcessSign").on("click", function () {
	        	var ret = selectDataTableRow();
				if (ret) {
					if ("CONFIRMED" == $.trim(ret.status)) {
						Global.Dialog.showDialog("entrustProcessSign", {
							title: "委托加工登记",
							url: "${ctx}/admin/itemApp/goEntrustProcessSign",
							postData: {no: ret.no, custAddress: ret.address},
							height: 330,
							width: 450,
							returnFun: goto_query
						});
					} else {
						art.dialog({content: "只有领料单状态为‘已审核’才可以操作"});
					}
				} else {
					art.dialog({content: "请选择一条记录"});
				}
	        });

/* 	        $("#itemType1").click(function(){
	        	$("#itemCode").openComponent_item({condition:{itemType1:$("#itemType1").val().trim()}});
	        });  */		
			onCollapse(44); 
			$("#itemAppListBtnGroup button").css("margin-top", "5px");
			if("${itemApp.costRight}" == "1"){
				$("#dataTable").jqGrid("showCol", "amount");
				$("#dataTable").jqGrid("showCol", "projectamount");
			}
		});
		function isDiffQty(){
			if($("#diffQty").val() == "T"){
				$("#diffQty").val("F");
			}else{
				$("#diffQty").val("T");
			}
		}
		function hasZero(){
			var result="";
			var ret = selectDataTableRow("dataTable");
			$.ajax({
				url : "${ctx}/admin/itemApp/hasZero",
				data: {
					no:ret.no.trim()
				},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				dataType: "json",
				type: "post",
				cache: false,
				async:false,
				error: function(){
			    
			    },
			    success:function(obj){
			    	result=obj;
			    }
   			});
   			return result;
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value="${itemApp.expired}"/>
					<ul class="ul-form">
						<li>
							<label>领料单号</label>
							<input type="text" id="no" name="no"/>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="custAddress" name="custAddress"/>
						</li>
						<li>
							<label>领料单状态</label>
							<house:xtdmMulit id="status" dictCode="ITEMAPPSTATUS" selectedValue="${itemApp.status}"></house:xtdmMulit>
						</li>
						<li>
							<label>领料类型</label>
							<house:xtdm id="type" dictCode="ITEMAPPTYPE"></house:xtdm>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1"></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2"></select>
						</li>
						<li>
							<label>发货类型</label>
							<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE"/>
						</li>
						<li>
							<label>供应商编号</label>
							<input type="text" id="supplCode" name="supplCode"/>
						</li>
						<li>
							<label>供应商状态</label>
							<house:xtdm id="splStatus" dictCode="APPSPLSTATUS"></house:xtdm>
						</li>
						<li>
							<label>仓库编号</label>
							<house:xtdmMulit id="whcode" dictCode="" sql="select Desc1 fd,Code from tWareHouse" sqlValueKey="Code" sqlLableKey="fd"></house:xtdmMulit>
						</li>
						<li>
							<label>档案编号</label>
							<input type="text" id="custDocumentNo" name="custDocumentNo"/>
						</li>
						<li>
							<label>是否通知发货</label> 
								<house:xtdm id="IsMaterialSendJob" dictCode="YESNO"
									value="${itemApp.isMaterialSendJob}"></house:xtdm>
						</li>
							
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
						<div  class="collapse" id="collapse" >
							<ul>
								<li>
									<label>客户类型</label>
									<house:custTypeMulit id="custType"  ></house:custTypeMulit>
								</li>
								<li>
									<label>申请日期</label>
									<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>配送方式</label>
									<house:xtdm id="delivType" dictCode="DELIVTYPE"/>
								</li>
								<li>
									<label>申请人员</label>
									<input type="text" id="appCzy" name="appCzy"/>
								</li>
								<li>
									<label>客户编号</label>
									<input type="text" id="custCode" name="custCode"/>
								</li>
								<li>
									<label>套餐材料</label>
									<house:xtdm id="isSetItem" dictCode="YESNO"/>
								</li>
								<li>
									<label>审核日期从</label>
									<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>服务性产品</label>
									<house:xtdm id="isService" dictCode="YESNO"/>
								</li>
								<li>
									<label>预申请单号</label>
									<input type="text" id="preAppNo" name="preAppNo"/>
								</li>
								<li>
									<label>材料名称</label>
									<input type="text" id="itemCodeDescr" name="itemCodeDescr"/>
								</li>
								<li>
									<label>发货单号</label>
									<input type="text" id="sendNo" name="sendNo"/>
								</li>
								<li>
									<label>发货日期从</label>
									<input type="text" id="sendDateFrom" name="sendDateFrom" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>至</label>
									<input type="text" id="sendDateTo" name="sendDateTo" class="i-date" 
											onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
								</li>
								<li>
									<label>备注</label>
									<input type="text" id="remarks" name="remarks"/>
								</li>
								<li>
									<label>工程分部</label>
									<house:DictMulitSelect id="department2" dictCode="" sql="select Code,Desc1 from tDepartment2 where DepType='3' and Expired='F' order by Code" 
															sqlValueKey="Code" sqlLableKey="Desc1">
									</house:DictMulitSelect>
								</li>
								<li>
									<label for="checkStatus">客户结算状态</label>
									<house:xtdmMulit id="checkStatus" dictCode="CheckStatus"/>
								</li>
								<li>
									<label>工程大区</label>
									<house:dict id="prjRegion" dictCode="" sql="select  Code,code+' '+descr Descr from tPrjRegion where expired ='F' " 
									sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
								</li>
								<li>
									<label for="productType">产品类型</label>
									<house:xtdmMulit id="productType" dictCode="APPPRODUCTTYPE"/>
								</li>
								<li>
                                    <label>延误原因</label>
                                    <house:xtdmMulit id="delayReson" dictCode="APPDELAYRESON"/>
                                </li>
                                <li>
                                    <label>补货单号</label>
                                    <input type="text" id="intReplenishNo" name="intReplenishNo"/>
                                </li>
								<li class="search-group-shrink" >
									<button data-toggle="collapse" class="btn btn-sm btn-link" data-target="#collapse">收起</button>
									<input type="checkbox" id="expired_show" name="expired_show"
											value="${itemApp.expired}" onclick="hideExpired(this)"
											${itemApp.expired!='T'?'checked':'' }/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
									<input type="checkbox" id="diffQty" name="diffQty" onclick="isDiffQty()" value="F"/><p>申报数量与采购数量不相等</p>
									<button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
									<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
								</li>
							</ul>
						</div>
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			
			<div class="btn-panel">
				<div id="itemAppListBtnGroup" class="btn-group-sm">
	            	<house:authorize authCode="ITEMAPP_ADD">
						<button id="addItemApp" type="button" class="funBtn funBtn-system">新增领料</button>
	                </house:authorize>
	            	<house:authorize authCode="ITEMAPP_CONTADD">
						<button id="contAddItemApp" type="button" class="funBtn funBtn-system">连续新增</button>
	                </house:authorize>
	            	<house:authorize authCode="ITEMAPP_BACK">
						<button id="returnItemApp" type="button" class="funBtn funBtn-system">领料退回</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_UPDATE">
						<button id="updateItemApp" type="button" class="funBtn funBtn-system">领料修改</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_TOAUDIT">
					<button id="submitCheckItemApp" type="button" class="funBtn funBtn-system">提交审核</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_AUDIT">
						<button id="checkItemApp" type="button" class="funBtn funBtn-system">审核</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_REAUDIT">
						<button id="returnCheckItemApp" type="button" class="funBtn funBtn-system">反审核</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SUPPLIER">
						<button id="supplierSend" type="button" class="funBtn funBtn-system">供应商直送</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SEND">
						<button id="warehouseSendAll" type="button" class="funBtn funBtn-system">仓库发货</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SENDBATCH">
						<button id="warehouseSendSome" type="button" class="funBtn funBtn-system">仓库分批发货</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SHORTAGE">
						<button id="shortage" type="button" class="funBtn funBtn-system">缺货</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SENDMEMO">
						<button id="sendMemo" type="button" class="funBtn funBtn-system">发货备注</button>
	                </house:authorize>
	                 <house:authorize authCode="ITEMAPP_WHRECEIVE">
						<button id="whReceive" type="button" class="funBtn funBtn-system">仓库收货</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_CONNECTSENDBATCH">
						<button id="connectSendBatch" type="button" class="funBtn funBtn-system">配送批次</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_DESIGNERCONF">
						<button id="designerConf" type="button" class="funBtn funBtn-system">设计师确认</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_LLGZ">
						<button id="llgz" type="button" class="funBtn funBtn-system">领料跟踪</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_VIEW">
						<button id="showItemApp" type="button" class="funBtn funBtn-system">查看</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_MXVIEW">
						<button id="showItemAppDetail" type="button" class="funBtn funBtn-system">明细查询</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_LLGLSENDLIST">
						<button id="llglSendList" type="button" class="funBtn funBtn-system">发货单查询</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_SENDSMS">
						<button id="itemAppMsg" type="button" class="funBtn funBtn-system">发送短信通知</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_PRINT">
						<button id="itemAppPrint" type="button" class="funBtn funBtn-system">打印</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_PRINTBATCH">
						<button id="itemAppBatchPrint" type="button" class="funBtn funBtn-system">批量打印</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_ENTRUSTPROCESSSIGN">
						<button id="entrustProcessSign" type="button" class="funBtn funBtn-system">委托加工登记</button>
	                </house:authorize>
	                <house:authorize authCode="ITEMAPP_EXCEL">
						<button id="itemAppExcel" type="button" class="funBtn funBtn-system">输出至excel</button>
	                </house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


