<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>自动拆单</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_itemPreApp.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/customComponent.js?v=${v}" type="text/javascript"></script>
		<script type="text/javascript">
			function ajaxPost(url, data, successFun){
			 	$.ajax({
					url:url,
					type:"post",
			    	data:data,
					dataType:"json",
					cache:false,
					async:false,
					error:function(obj){			    		
						art.dialog({
							content:"访问出错,请重试",
							time:3000,
							beforeunload: function () {}
						});
					},
					success:successFun
				});	
			}
			function EditValid(str, closeFlag){
				if(!str || str.trim() == ""){
					art.dialog({
						content:"请输入完整的信息",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});
					return true;
				}
				return false;
			}
			function save(sendTypeCheck, applyQtyCheck, closeFlag){
				if($("#itemType1").val().trim() == "JC" && $("#isSpecReq").val().trim()=="1"&&$("#isSetItem").val().trim()=="1"){
					var checkqty=true;
					var rows = $("#dataTable_applyDetail").jqGrid("getRowData");
					$.each(rows, function(i,row){
						if (parseInt(row.specreqpk) > 0) {
							var ljxd=parseFloat(row.sendedqty)+parseFloat(row.confirmedqty)+parseFloat(row.applyqty)+parseFloat(row.qty);
							var jd=parseFloat(row.reqqty);
							if(ljxd>jd){
								art.dialog({
									content:"材料["+row.itemdescr+"]累计下单数量["+ljxd+"]大于解单数量["+jd+"]，不允许保存",
								});
								checkqty=false;
								return false;
							}
						}
					});
					if(!checkqty){
						return;
					}
				}
				$("#isTipExit").val("F");
				if(EditValid($("#appNo").val(), true)){
					return;
				}
				if(EditValid($("#custCode").val(), true)){
					return;
				}
				if($("#itemType1").val().trim() == ""){
					art.dialog({
						content:"请选择材料类型1!",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});
					return ;
				}
/* 	  			if($("#itemType1").val().trim() == "JZ" || $("#itemType1").val().trim() == "RZ"){
	  				if($("#itemType2").val().trim() == ""){
						art.dialog({
							content:"请选择材料类型2!",
							ok:function(){
								if(closeFlag == true){
									closeWin();
								}
							}
						});
						return ;
	  				}
	  			} */
	  		/* 	if($("#delivType").val() == ""){
					art.dialog({
						content:"请选择配送方式!",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});
					return ;
	  			} */
				if(EditValid($("#otherCost").val(), true)){
					return;
				}
				
				var ids=$("#dataTable_applyDetail").jqGrid("getDataIDs");
				
				if(ids.length == 0){
					art.dialog({
						content:"领料明细不能空",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});				
					return ;
				}
				if($("#projectMan").val().trim() == "" && $("#projectManDescr").val().trim() == ""){
					art.dialog({
						content:"请选择项目经理",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});				
					return ;
				}
				if($("#phone").val().trim() == ""){
					art.dialog({
						content:"请选择项目经理电话",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});				
					return ;
				}
		/* 		if($("#sendType").val() == ""){
					art.dialog({
						content:"请选择发货类型",
						ok:function(){
							if(closeFlag == true){
								closeWin();
							}
						}
					});				
					return ;
				} */
				var param=Global.JqGrid.allToJson("dataTable_applyDetail");
				
				if(!sendTypeCheck || sendTypeCheck == ""){
					sendTypeCheck = "1";
				}
				if(!applyQtyCheck || applyQtyCheck == ""){
					applyQtyCheck = "1";
				}
				
				$.extend(param,{
					sendTypeCheck:sendTypeCheck,
					applyQtyCheck:applyQtyCheck
				});
				for(var i=0;i<param.datas.length;i++){
					if(parseFloat(param.datas[i].qty)<=0 && $("#m_umState").val() == "C"){
						art.dialog({
							content:"材料【"+param.datas[i].itemcode+"-"+param.datas[i].itemdescr+"】采购数量为【"+param.datas[i].qty+"】,不允许保存!"
						});
						return;
					}
				}
				var itemType1= $.trim($("#itemType1").val());
				var itemType2= $.trim($("#itemType2").val());
				var custCode= $.trim($("#custCode").val());
				
				if(itemType1=="JZ"&& itemType2=="JZFS"){
					$.ajax({
						url:'${ctx}/admin/itemApp/isExistFSGR',
						type: 'post',
						data: {custCode:custCode},
						dataType: 'json',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
							if("true"==$.trim(obj)){
					    		art.dialog({
					    			content:"防水人工成本已生成，不允许领取防水材料！",
					    		});
					    		return;
					    	}else{
					    		param.datas = [];
								Global.Form.submit("dataForm", "${ctx}/admin/itemPreApp/doSave_autoOrder", param, function(ret){
									//var data = listStrToJson(ret.msg);
									if(ret.rs==true){
										$("#_form_token_uniq_id").val(ret.token.token); 
										var returnCode = ret.datas.code;//data[0].code;
										var returnInfo = ret.datas.info;//data[0].info;
										if($("#preAppNo").val() != ""){
											if(returnCode == "1" && !(closeFlag == true)){
												art.dialog({
													content:returnInfo,
													time:1000
												});
												$("#dataTable_applyDetail").jqGrid("clearGridData");
												$("#sendType").val("");
												$("#itemType2").removeAttr("disabled");
												$("#isService").removeAttr("disabled");
											}else{
												if($("#continuityAdd").val() == "1"){
													art.dialog({
														content:"保存成功",
														time:1000
													});
						   							Global.JqGrid.clearJqGrid("dataTable_applyDetail");
												}else{
													closeWin();
												}
											}
										}else{
											if($("#continuityAdd").val() == "1"){
												art.dialog({
													content:"保存成功",
													time:1000
												});
					   							Global.JqGrid.clearJqGrid("dataTable_applyDetail"); 
											}else{
												closeWin();
											}
										}				
									}else{
										$("#_form_token_uniq_id").val(ret.token.token);
										var returnCode = ret.datas.code;//data[0].code;
										var sendTypeCheck = ret.datas.sendTypeCheck;//data[0].sendTypeCheck;
										var applyQtyCheck = ret.datas.applyQtyCheck;//data[0].applyQtyCheck;
										var returnInfo = ret.datas.info;//data[0].info; 
										if(returnCode != "-2" && returnCode != "-3"){
											if(returnCode == -4){
												$("#isTipExit").val("T");
											}else if(returnCode == -5){
												$("#isTipExit").val("F");
											}
											art.dialog({
												content:returnInfo
											});
										}else{
											if(returnCode == "-2" && sendTypeCheck == "1"){
												sendTypeCheck = "0";
											}
											if(returnCode == "-3" && applyQtyCheck == "1"){
												applyQtyCheck = "0";
											}
											art.dialog({
												content:returnInfo,
												ok:function(){
													save(sendTypeCheck, applyQtyCheck, closeFlag);
												},
												cancel:function(){}
											});
										}
									} 
								});					    	
					    	}					    
						}
					});	
					return;				
				}
				if(itemType1=="JZ"&& itemType2=="JZSD"){
					$.ajax({
						url:'${ctx}/admin/itemApp/isExistJZSD',
						type: 'post',
						data: {custCode:custCode,itemType2:itemType2},
						dataType: 'json',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
							if("true"==$.trim(obj)){
					    		art.dialog({
					    			content:"水电工资已申请，不允许申请水电材料！",
					    		});
					    		return;
					    	}else{
					    		param.datas = [];
								Global.Form.submit("dataForm", "${ctx}/admin/itemPreApp/doSave_autoOrder", param, function(ret){
									//var data = listStrToJson(ret.msg);
									if(ret.rs==true){
										$("#_form_token_uniq_id").val(ret.token.token); 
										var returnCode = ret.datas.code;//data[0].code;
										var returnInfo = ret.datas.info;//data[0].info;
										if($("#preAppNo").val() != ""){
											if(returnCode == "1" && !(closeFlag == true)){
												art.dialog({
													content:returnInfo,
													time:1000
												});
												$("#dataTable_applyDetail").jqGrid("clearGridData");
												$("#sendType").val("");
												$("#itemType2").removeAttr("disabled");
												$("#isService").removeAttr("disabled");
											}else{
												if($("#continuityAdd").val() == "1"){
													art.dialog({
														content:"保存成功",
														time:1000
													});
						   							Global.JqGrid.clearJqGrid("dataTable_applyDetail");
												}else{
													closeWin();
												}
											}
										}else{
											if($("#continuityAdd").val() == "1"){
												art.dialog({
													content:"保存成功",
													time:1000
												});
					   							Global.JqGrid.clearJqGrid("dataTable_applyDetail"); 
											}else{
												closeWin();
											}
										}				
									}else{
										$("#_form_token_uniq_id").val(ret.token.token);
										var returnCode = ret.datas.code;//data[0].code;
										var sendTypeCheck = ret.datas.sendTypeCheck;//data[0].sendTypeCheck;
										var applyQtyCheck = ret.datas.applyQtyCheck;//data[0].applyQtyCheck;
										var returnInfo = ret.datas.info;//data[0].info; 
										if(returnCode != "-2" && returnCode != "-3"){
											if(returnCode == -4){
												$("#isTipExit").val("T");
											}else if(returnCode == -5){
												$("#isTipExit").val("F");
											}
											art.dialog({
												content:returnInfo
											});
										}else{
											if(returnCode == "-2" && sendTypeCheck == "1"){
												sendTypeCheck = "0";
											}
											if(returnCode == "-3" && applyQtyCheck == "1"){
												applyQtyCheck = "0";
											}
											art.dialog({
												content:returnInfo,
												ok:function(){
													save(sendTypeCheck, applyQtyCheck, closeFlag);
												},
												cancel:function(){}
											});
										}
									} 
								});					    	
					    	}					    
						}
					});	
					return;				
				}
				param.datas = [];
				Global.Form.submit("dataForm", "${ctx}/admin/itemPreApp/doSave_autoOrder", param, function(ret){
					//var data = listStrToJson(ret.msg);
					if(ret.rs==true){
						$("#_form_token_uniq_id").val(ret.token.token); 
						var returnCode = ret.datas.code;//data[0].code;
						var returnInfo = ret.datas.info;//data[0].info;
						if($("#preAppNo").val() != ""){
							if(returnCode == "1" && !(closeFlag == true)){
								art.dialog({
									content:returnInfo,
									time:1000
								});
								$("#dataTable_applyDetail").jqGrid("clearGridData");
								$("#sendType").val("");
								$("#itemType2").removeAttr("disabled");
								$("#isService").removeAttr("disabled");
							}else{
								if($("#continuityAdd").val() == "1"){
									art.dialog({
										content:"保存成功",
										time:1000
									});
		   							Global.JqGrid.clearJqGrid("dataTable_applyDetail");
								}else{
									closeWin();
								}
							}
						}else{
							if($("#continuityAdd").val() == "1"){
								art.dialog({
									content:"保存成功",
									time:1000
								});
	   							Global.JqGrid.clearJqGrid("dataTable_applyDetail"); 
							}else{
								closeWin();
							}
						}				
					}else{
						$("#_form_token_uniq_id").val(ret.token.token);
						var returnCode = ret.datas.code;//data[0].code;
						var sendTypeCheck = ret.datas.sendTypeCheck;//data[0].sendTypeCheck;
						var applyQtyCheck = ret.datas.applyQtyCheck;//data[0].applyQtyCheck;
						var returnInfo = ret.datas.info;//data[0].info; 
						if(returnCode != "-2" && returnCode != "-3"){
							if(returnCode == -4){
								$("#isTipExit").val("T");
							}else if(returnCode == -5){
								$("#isTipExit").val("F");
							}
							art.dialog({
								content:returnInfo
							});
						}else{
							if(returnCode == "-2" && sendTypeCheck == "1"){
								sendTypeCheck = "0";
							}
							if(returnCode == "-3" && applyQtyCheck == "1"){
								applyQtyCheck = "0";
							}
							art.dialog({
								content:returnInfo,
								ok:function(){
									save(sendTypeCheck, applyQtyCheck, closeFlag);
								},
								cancel:function(){}
							});
						}
					} 
				});
			}
	 		function sendTypeChange(){
	 			 if($("#sendType").val()=="2"){
	 				if($("#delivType").val() == ""){
	 					$("#delivType").val("1");
	 				}
	 			}
			} 
			function isSetItemChange(){
	   			if($("#isSetItem").val()=="0" && $("#itemType1").val().trim() != "JZ"){
					$("#dataTable_applyDetail").jqGrid("hideCol", "intproddescr");
					$("#dataTable_applyDetail").jqGrid("hideCol", "color");
					$("#dataTable_applyDetail").jqGrid("hideCol", "sqlcodedescr");
	   			}else{
					$("#dataTable_applyDetail").jqGrid("showCol", "intproddescr");
					$("#dataTable_applyDetail").jqGrid("showCol", "color");
					$("#dataTable_applyDetail").jqGrid("showCol", "sqlcodedescr");
	   			}
	   			if($("#itemType1").val().trim() == "JZ"){
					$("#dataTable_applyDetail").jqGrid("hideCol", "fixareadescr");
	   			}else{
					$("#dataTable_applyDetail").jqGrid("showCol", "fixareadescr");
	   			}
	   			if($("#itemType1").val().trim() == "JC"){
					$("#dataTable_applyDetail").jqGrid("showCol", "intproddescr");
	   			}else{
					$("#dataTable_applyDetail").jqGrid("hideCol", "intproddescr");
	   			}
	   			ajaxPost("${ctx}/admin/itemPreApp/getHasInSetReq",
	   				{
	   					custCode:$("#custCode").val(),
	   					itemType1:$("#itemType1").val()
	   				},
	   				function (data){
	   					$("#hasInSetReq").val(data);
						if($("#isSetItem").val()=="0" || $("#hasInSetReq").val().trim() == "1"){
							$("#yyxxzBtn").removeAttr("disabled");
							if($("#itemType1").val().trim() == "JC" && $("#isSetItem").val()=="1"){
								$("#ksxzBtn").removeAttr("disabled");
							}else{
								$("#ksxzBtn").attr("disabled", true);
							}
						}else{
							$("#yyxxzBtn").attr("disabled", true);
							$("#ksxzBtn").removeAttr("disabled");
						}
						isEnableImportExcel();
	   				}
	   			);
			}
			function itemType1Change(firstLoading){
				if($("#m_umState").val().trim() != "V" && ($("#m_checkStatus").val().trim() == "3" || $("#m_checkStatus").val().trim() == "4") && $("#itemType1").val().trim() == "JZ"){
					art.dialog({
						content:"楼盘"+$("#address").val()+"处于项目经理提成要领或项目经理已领状态，不允许新增领料单"
					});
					$("#itemType1").val("");
					return;
				}
				
	   			
				//修改itemType1，更新传给仓库的itemType1 
	   			$("#wareHouseCode").openComponent_wareHouse({
	   				showValue:"${itemPreMeasure.wareHouseCode}",
	   				showLabel:"${itemPreMeasure.wareHouseDescr}",
	   				condition: {
	   					itemType1:$("#itemType1").val(),
	   					showLastSendSupplier: "1",
	 					custCode:$("#custCode").val()
	   				}
	   			});
	   			
				fillIsSetItem();
				checkIsSpecReq();
				if($("#itemType1").val().trim() == "ZC"){
					$("#isService").parent().removeAttr("hidden");
					$("#measureRemark").parent().css({
						"padding-left":"298px"
					});
					$("#splRemark").parent().css({
						"padding-left":"298px"
					});
				}else{
					$("#isService").parent().attr("hidden", true);
					$("#isService").val("0");
					$("#measureRemark").parent().css({
						"padding-left":"586px"
					});
					$("#splRemark").parent().css({
						"padding-left":"586px"
					});
				}
				
				if($("#itemType1").val().trim() == "JZ"){
					$("#dataTable_applyDetail").jqGrid("showCol", "color");
					$("#dataTable_applyDetail").jqGrid("showCol", "sqlcodedescr");
				}else{
					$("#dataTable_applyDetail").jqGrid("hideCol", "color");
					$("#dataTable_applyDetail").jqGrid("hideCol", "sqlcodedescr");
				}
				if($("#itemType1").val().trim() == "JC"){
					$("#dataTable_applyDetail").jqGrid("showCol", "declareqty");
					$("#dataTable_applyDetail").jqGrid("showCol", "differencesperf");
					$("#isCupboard").parent().removeAttr("hidden");
				}else{
					$("#dataTable_applyDetail").jqGrid("hideCol", "declareqty");
					$("#dataTable_applyDetail").jqGrid("hideCol", "differencesperf");
					$("#isCupboard").parent().attr("hidden",true);
				}
		    	isEnableImportExcel();
				if(!(firstLoading == true)){
					ajaxPost("${ctx}/admin/itemPreApp/getItemType2Opt",
						{
							"itemType1":$("#itemType1").val().trim()
						},
						function(data){
							$("#itemType2").html(data.html);
							$("#itemType2").children().each(function(index, value){
								if("${itemPreMeasure.itemType2}" == $(this).val()){
									$(this).attr("selected", true);
								}else{
									$(this).removeAttr("selected");
								}
							});
						}
					);
				}
			}
			function fillIsSetItem(){
				var ret = $("#dataTable_applyDetail").jqGrid("getDataIDs");
	
				if(ret.length > 0){
					$("#isSetItem").attr("disabled", true);
					return;
				}
				if($("#preAppNo").val() != "" && $("#itemType1").val().trim() == "JZ"){
					$("#isSetItem").attr("disabled", true);
					$("#isSetItem").val("1");
					isSetItemChange();
					return;
				}
				if($("#custCode").val() == "" || $("#itemType1").val() == ""){
					return ;
				} 
				ajaxPost("${ctx}/admin/itemPreApp/getCanInOutPlan",
					{
						"itemType1":$("#itemType1").val().trim(),
						"custCode":$("#custCode").val()
					},
					function(data){
						if(data.isQueryEmpty == "0"){
							if(data.canInPlan == "1" && data.canOutPlan == "1"){
								$("#isSetItem").removeAttr("disabled");
							}else{
								$("#isSetItem").attr("disabled", true);
								if(data.canInPlan == "1"){
									$("#isSetItem").val("0");
								}else{
									$("#isSetItem").val("1");
								}
							}
							isSetItemChange();
						}
					}
				);
			}
			function yyxxz(){
				$("#delNum").val(0);
				$("#isDel").val("0");
				if($("#m_umState").val()=="V"){
					return;
				}
				if($("#custCode").val() == ""){
					art.dialog({
						content:"请先选择客户!"
					});
					return ;
				}
				if($("#itemType1").val() == ""){
					art.dialog({
						content:"请选择材料类型1！"
					});
					return ;
				}
				/* if($("#itemType1").val().trim() == "JZ" || $("#itemType1").val().trim() == "RZ"){
					if($("#itemType2").val() == ""){
						art.dialog({
							content:"请选择材料类型2"
						});
						return ;
					}
				} */
				if($("#isCupboard").val() == "" && $("#itemType1").val().trim()=="JC"){
					art.dialog({
						content:"请选择是否橱柜！"
					});
					return ;
				}
				var pks=$("#dataTable_applyDetail").getCol("reqpk", false);
				Global.Dialog.showDialog("yyxxz", {
					title:"领料明细--增加",
					url:"${ctx}/admin/itemPreApp/goYyxxz_addll",
					postData:{ 
						custCode:$("#custCode").val(),
						itemType1:$("#itemType1").val(),
						isSetItem:$("#isSetItem").val(),
						isService:$("#isService").val(),
						pks:pks,appNo:$("#appNo").val(),
						m_umState:"A",
						isCupboard:$("#isCupboard").val(),
						isAutoOrder:"1"
					},
					height:650,
					width:1100,
					returnFun:function(data){
						if(data){
							var arr=$("#dataTable_applyDetail").jqGrid("getRowData");
							$.each(data,function(k,v){
								 v.lastupdate=getNowFormatDate();
								 v.lastupdatedby=$("#czybh").val();
								 v.actionlog="ADD";
								 v.expired="F";
								 arr.push(v);
							});
					  		var params=JSON.stringify(arr); 
					  		$("#dataTable_applyDetail").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",datatype:"json",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
							selectFirstRow();
							setSplEditByItem();
						}
					}
				});
			}
			function ksxz(){
				if($("#m_umState").val()=="V"){
					return;
				}
				if($("#itemType1").val().trim() == ""){
					art.dialog({
						content:"请选择材料类型1!"
					});
					return;
				}
				/* if($("#itemType1").val().trim() == "JZ" || $("#itemType1").val().trim() == "RZ"){
					if($("#itemType2").val() == ""){
						art.dialog({
							content:"请选择材料类型2"
						});
						return ;
					}
				} */
				if($("#itemType1").val().trim() == "JC" && $("#isCupboard").val().trim() == ""){
					art.dialog({
						content:"请选是否橱柜!"
					});
					return ; 
				}
				if($("#custCode").val() == ""){
					art.dialog({
						content:"请先选择客户!",
						ok:function(){
							$("#custCode").focus();
						}
					});
					return;
				}
				var isSetItem = "";
				if($("#itemType1").val().trim() != "JZ"){
					isSetItem = "1";
				}
				
				var specreqpks = $("#dataTable_applyDetail").getCol("specreqpk", false);
				var itemCodes = $("#dataTable_applyDetail").getCol("itemcode", false);
				var url="";
				var postData="";
				if($("#itemType1").val().trim()=="JC" && $("#isSpecReq").val().trim()=="1" ){
					url="${ctx}/admin/itemPreApp/goJddr_addll";
					postData={itemType1:$("#itemType1").val(),itemType2:$("#itemType2").val(),isSetItem:isSetItem,
							  custCode:$("#custCode").val(),specreqpks:specreqpks,isCupboard:$("#isCupboard").val(),
							  appNo:$("#appNo").val()};
				}else{
					url="${ctx}/admin/itemPreApp/goKsxz_addll";
					postData={itemType1:$("#itemType1").val(),itemType2:$("#itemType2").val(),isSetItem:isSetItem,
							  custCode:$("#custCode").val(),appNo:$("#appNo").val(),itemCodes:itemCodes};
				}
				Global.Dialog.showDialog("ksxz",{
					title:"领料明细--快速新增",
					url:url,
					postData:postData,
					height:730,
					width:1200,
					returnFun:function(data){
						if(data){
							$.each(data,function(i,rowData){
								$.extend(rowData,{
									lastupdate:new Date(),
									lastupdatedby:$("#czybh").val(),
									actionlog:"ADD",
									expired:"F"
								});
								Global.JqGrid.addRowData("dataTable_applyDetail", rowData);
							});
							selectFirstRow();
							setSplEditByItem();
						}
					}
				});
			}
			function cysqdr(){
				if($("#m_umState").val()=="V"){
					return;
				}
				if($("#preAppNo").val() == ""){
					art.dialog({
						content:"请先选择预申请单号!",
						ok:function(){
							$("#preAppNo").focus();
						}
					});
					return;
				}
				/* if($("#itemType1").val().trim() == "JZ" || $("#itemType1").val().trim() == "RZ"){
					if($("#itemType2").val() == ""){
						art.dialog({
							content:"请选择材料类型2"
						});
						return ;
					}
				} */
				
				var preAppDTPks= $("#dataTable_applyDetail").getCol("preappdtpk", false);
				var reqPks= $("#dataTable_applyDetail").getCol("reqpk", false);
				var itemCodes = $("#dataTable_applyDetail").getCol("itemcode", false);
				
				Global.Dialog.showDialog("cysqdr", {
					title:"领料明细--预申请导入",
					url:"${ctx}/admin/itemPreApp/goPreApply_addll",
					postData:{ 
						custCode:$("#custCode").val(),
						itemType1:$("#itemType1").val(),
						itemType2:$("#itemType2").val(),
						preAppNo:$("#preAppNo").val(),
						isService:$("#isService").val(),
						appNo:$("#appNo").val(),
						m_umState:"A",
						preAppDTPks:preAppDTPks,
						reqPks:reqPks,
						itemCodes:itemCodes,
						isSetItem:$("#isSetItem").val()
					},
					height:650,
					width:1200,
					returnFun:function(data){
						if(data){
							$.each(data,function(i,rowData){
								$.extend(rowData,{
									lastupdate:new Date(),
									lastupdatedby:$("#czybh").val(),
									actionlog:"ADD",
									expired:"F"
								});
								Global.JqGrid.addRowData("dataTable_applyDetail", rowData);
							});
	     					selectFirstRow();
							setSplEditByItem();
						}
					}
				});
			}
			function del(){
				$("#delNum").val(0);
				$("#isDel").val("1");
				$("#isTipExit").val("T");
				if($("#m_umState").val()=="V"){
					return;
				}
				var id = $("#dataTable_applyDetail").jqGrid("getGridParam", "selrow");
				
			    if (id) {
				    art.dialog({
						content: "是删除该记录?",
						ok:function(){
	     					var ret = selectDataTableRow("dataTable_applyDetail");
						    var rowNum=$("#dataTable_applyDetail").jqGrid('getGridParam','records');
						    $('#dataTable_applyDetail').delRowData(id);
						    var params=JSON.stringify($("#dataTable_applyDetail").jqGrid("getRowData"));
							$("#dataTable_applyDetail").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");  
							if(ret.ordername=="不下单"){
								cantordernum-=1;
								if(parseInt(cantordernum)>0){
									$("#tip").show().html("不下单数量："+cantordernum);
						        }else{
						        	$("#tip").hide();
						        }
							}
						    setTimeout(function(){moveToNext(id,rowNum);$("#delBtn").focus();},100);
						},
						cancel:function(){
	     					selectFirstRow();
	     				}
					});
			    }else{
				    art.dialog({
						content: "请选择一条记录"
					});
				}
			}
			function doExcel(){
				doExcelNow("领料明细", "dataTable_applyDetail", "dataForm");
			}
			function ckylcl(){
				var appNo = "";
				if($("#m_umState").val().trim() != "A"){
					appNo = $("#appNo").val();
				}
				var itemCodes = $("#dataTable_applyDetail").getCol("itemcode",false);
				Global.Dialog.showDialog("ylcl",{
					title:"领料明细--查看已领材料",
					url:"${ctx}/admin/itemPreApp/goYlcl_addll",
					postData:{
						custCode:$("#custCode").val(),
						appNo:appNo,
						itemCodes:itemCodes
					},
					height:600,
					width:820
				});
			}
			$(function(){
	   			$("#projectMan").openComponent_employee({
	   				showValue:"${itemPreMeasure.projectMan}",
	   				showLabel:"${itemPreMeasure.projectManDescr}",
	   				callBack:fillData,
	   				condition:{
	   					position:"94"
	   				},
			 		blurFun:function(){
			 			var str = $("#projectMan").val();
						if(str == ""){
							$("#projectManDescr").val($("#openComponent_employee_projectMan").val());
						}else{
							var componentStr = $("#openComponent_employee_projectMan").val();
							$("#projectManDescr").val(componentStr.substring(componentStr.indexOf("|")+1));
						}
			 		}
				});
	   			$("#wareHouseCode").openComponent_wareHouse({
	   				showValue:"${itemPreMeasure.wareHouseCode}",
	   				showLabel:"${itemPreMeasure.wareHouseDescr}",
	   				condition: {
	   					itemType1:$("#itemType1").val(),
	   					showLastSendSupplier: "1",
	 					custCode:$("#custCode").val()
	   				}
	   			});
	   			
	   			$("#custCode").openComponent_customer({
	   				showValue:"${itemPreMeasure.custCode}",
	   				showLabel:"${itemPreMeasure.custCodeDescr}",
	   				condition:{
	   					status:"4,5",
						disabledEle:"status_NAME"
	   				},
	   				//isValidate:false,
	   				callBack:function(ret){
	   					$("#independCustPhone").html("");
	   					if((ret.checkstatus.trim() == "3" || ret.checkstatus.trim() == "4") && $("#itemType1").val()=="JZ"){
	   						art.dialog({
	   							content:"楼盘【"+ret.address+"】处于项目经理提成要领或项目经理已领状态，不允许新增领料单"
	   						});
	   						$("#projectManDescr").val("");
	   						$("#phone").val("");
	   						$("#independCustPhone").html("");
	   						$("#address").val("");
							$("#addressOtherInfo").val("");
							$("#department").val("");
							$("#documentNo").val("");
							$("#area").val("");
							$("#custType").val("");
	  						$("#openComponent_employee_projectMan").val("");
	   						return;
	   					}
	  					$("#projectManDescr").val(ret.projectmandescr);
						$("#projectMan").val("");
						$("#openComponent_employee_projectMan").val("");
						$("#department").val("");
						$("#phone").val("");
						if(ret.projectman){
							$("#projectMan").setComponent_employee({
								showValue:ret.projectman,
								showLabel:ret.projectmandescr,
								callBack:fillData
							});
						}
						if(ret.projectmanphone){
							$("#phone").val(ret.projectmanphone);
						}
						if(ret.isaddallinfo && ret.isaddallinfo == "0"){
		    				$("#independCustPhone").html("客户电话:"+ret.mobile1);
						}
						$("#address").val(ret.address);
						$("#addressOtherInfo").val(ret.address);
						$("#department").val(ret.dept1descr+" "+ret.dept2descr);
						$("#documentNo").val(ret.documentno);
						$("#area").val(ret.area);
						$("#custType").val(ret.custtype);
						$("#regionCode").val(ret.regioncode);
						fillIsSetItem();
						isIndependCust(ret.code);
					}
	   			});	
	   			$("#openComponent_customer_custCode").on("blur", function(){
	   				if($("#openComponent_customer_custCode").val() == ""){
	   					$("#custCode").val("");
	   					$("#department").val("");
	   					$("#area").val("");
	   					$("#documentNo").val("");
	   					$("#address").val("");
	   					$("#addressOtherInfo").val("");
	   				}
	   			});
	   			//$("#openComponent_customer_custCode").attr("readonly", true);
	   			$("#preAppNo").openComponent_itemPreApp({
	   				showValue:"${itemPreMeasure.preAppNo}",
	   				width:1200,
	   				height:650,
	   				condition:{
	   					unShowStatus:"1,2,5,6",
	   					status:"3,4",
	   					requestPage:"listAddll",
	   					czybh:$("#czybh").val()
	   				}
	   			});
	   			$("#openComponent_itemPreApp_preAppNo").attr("readonly", true);
				$("#openComponent_itemPreApp_preAppNo").next().attr("data-disabled", true).css({
					"color":"#888"
				});
				$("#dataForm").bootstrapValidator({
					message : "This value is not valid",
					feedbackIcons : {/*input状态样式图片*/
						validating : "glyphicon glyphicon-refresh"
					},
					fields: {
						openComponent_customer_custCode:{  
							validators: {  
								notEmpty: {  
									message: "客户不能为空"  
								} ,	             
								remote: {
						            message: "",
						            url: "${ctx}/admin/customer/getCustomer",
						            data: getValidateVal,  
						            delay:4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
						        }
							}  
						}
					},
					submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
				});	
				//改写窗口右上角关闭按钮事件
		   		var titleCloseEle = parent.$("div[aria-describedby=dialog_autoOrder]").children(".ui-dialog-titlebar");
		   		$(titleCloseEle[0]).children("button").remove();
		   		$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
		   		+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
		   		$(titleCloseEle[0]).children("button").on("click", exit); 
			});
			
	  		function fillData(ret){
	  			if(ret.phone){
	  				$("#phone").val(ret.phone);
	  			}else if(ret.Phone){
	  				$("#phone").val(ret.Phone);
	  			}else{
	  				$("#phone").val("");
	  			}
	  			
	  			if(ret.namechi){
	  				$("#projectManDescr").val(ret.namechi);
	  			}else if(ret.NameChi){
	  				$("#projectManDescr").val(ret.NameChi);
	  			}else{
	  				$("#projectManDescr").val("");
	  			}
	  			
	  			if(ret.department1descr){
					$("#department").val(ret.department1descr+" "+ret.department2descr);
	  			}else if(ret.department1Descr){
					$("#department").val(ret.department1Descr+" "+ret.department2Descr);
	  			}else{
	  				$("#department").val("");
	  			}
			}
			
			function setSplEditByItem(){
				var rets = $("#dataTable_applyDetail").jqGrid("getCol", "itemtype2", true);
				
				if(rets.length <= 0){
					$("#sendType").val("");
				}else{
					
					var ret = $("#dataTable_applyDetail").jqGrid("getRowData", rets[0].id);
					if($("#sendType").val() == ""){
						$("#sendType").val(ret.sendtype);
	   					sendTypeChange();
					}
		   			var itemType2Opts = $("#itemType2 option");
		   			var itemType2s=[];
		   			
	 	   			for(var i=1;i<itemType2Opts.length;i++){
		   				itemType2s.push(itemType2Opts[i].value);
		   			}
		   			
					for(var i=0;i<rets.length;i++){
						var j=0;
						for(;j<itemType2s.length;j++){
							if(rets[i].value.trim() == itemType2s[j].trim()){
								break;
							}
						}
						if(j<itemType2s.length){
							$("#itemType2").attr("disabled", "disabled");
							break;
						}
					} 
				}
				$("#isTipExit").val("T");
				remarksDiffColor();	
			}
			
			window.onload = function (){
				$("#isTipExit").val("F");
	   			$("#itemAppType").val("S");
	   			$("#itemAppType").attr("disabled", true);
	   			if("${itemPreMeasure.costRight}"=="1"){
					$("#dataTable_applyDetail").jqGrid("showCol", "cost");
					$("#dataTable_applyDetail").jqGrid("showCol", "allcost");
					$("#dataTable_applyDetail").jqGrid("showCol", "projectcost");
					$("#dataTable_applyDetail").jqGrid("showCol", "allprojectcost");
	   			}
				if($("#m_umState").val().trim() == "A"){
					$("#returnBut").css("display", "none");
					$("#cancelBut").css("display", "none");
					sendTypeChange();
	   				$("#appNo").attr("readonly", true);
	   				$("#itemAppStatus").val("OPEN");
	   				$("#itemAppStatus").attr("disabled", true);
	   				$("#expired").val("F");
	   				$("#pnlCZY").attr("hidden", true);
	   				if($("#preAppNo").val().trim() != ""){
	   					itemType1Change(true);
			   			isSetItemChange();   			
			   			$("#openComponent_customer_custCode").attr("readonly", true);
						$("#openComponent_customer_custCode").next().attr("data-disabled", true).css({
							"color":"#888"
						});
			   			$("#openComponent_itemPreApp_preAppNo").attr("readonly", true);
						$("#openComponent_itemPreApp_preAppNo").next().attr("data-disabled", true).css({
							"color":"#888"
						});
	        			$("#itemType1").attr("disabled", true);
	        			$("#isSetItem").attr("disabled", true);
		        		$("#cysqdrBtn").removeAttr("disabled");
	        			
			   			if($("#measureRemark").val() != ""){
			   				$("#measureRemark").parent().removeAttr("hidden");
			   			} 
			   			if($("#serviceTip").html() != ""){
			   				$("#serviceTip").parent().next().css({
			   					"padding-left":(298-$("#serviceTip").width()-3)+"px"
			   				});
			   			}
	   				}
	   				if("${itemPreMeasure.itemType1}" != "" && "${itemPreMeasure.addFromPage}" == "itemApp_list"){
	   					itemType1Change();
	   				}
	   				return;
				}
				if($("#m_umState").val().trim() == "M"){
					$("#returnBut").css("display", "none");
					$("#cancelBut").css("display", "none");
	   				$("#appNo").attr("readonly", true);
/* 	   				$("#itemAppStatus").val("OPEN"); */
	   				$("#itemAppStatus").attr("disabled", true);
		   			$("#openComponent_itemPreApp_preAppNo").attr("readonly", true);
					$("#openComponent_itemPreApp_preAppNo").next().attr("data-disabled", true).css({
						"color":"#888"
					});
		        	$("#cysqdrBtn").removeAttr("disabled");
				}else if($("#m_umState").val().trim() == "C"){
					$("#saveBut").html("审核");
	   				$("#itemAppStatus").attr("disabled", true);
					$("#appNo").attr("readonly", true);
	   				$("#openComponent_employee_projectMan").attr("readonly", true);	
	   				$("#phone").attr("readonly", true);
			   		$("#openComponent_customer_custCode").attr("readonly", true);
	       			$("#itemType1").attr("disabled", true);
	       			$("#isSetItem").attr("disabled", true);
	       			$("#delivType").attr("disabled", true);	
					$("#openComponent_customer_custCode").next().attr("data-disabled", true).css({
						"color":"#888"
					});
	        		$("#cysqdrBtn").attr("disabled", true);
	        		$("#delBtn").attr("disabled", true);
	   				$("#expired").val("F");
	   				$("#pnlCZY").removeAttr("hidden");
	   				$("#pnlCZY").children("li").attr("hidden", true);
	   				$("#appCzyDescr").parent().removeAttr("hidden");
	   				$("#date").parent().removeAttr("hidden");
	   				$("#signDate").parent().removeAttr("hidden");
	   				$("#confirmCzyDescr").parent().removeAttr("hidden");
	   				$("#confirmCzyDescr").parent().css({
	   					"visibility":"hidden"
	   				});
	   				$("#pnlFee").removeAttr("hidden");
	   				ajaxPost("${ctx}/admin/itemPreApp/getCustPayInfo", 
	   					{
	   						custCode:$("#custCode").val()
	   					}, 
						function (data){
							$.each(data,function(i,v){
								$("#"+i).val(v);
							});
							//var wdz = data.contractFee + data.designFee + data.zjje - data.payFee;
							//$("#wdz").val(myRound(wdz, 0));
						}
					);
					$("#dataTable_applyDetail").jqGrid("showCol", "cost");
					$("#dataTable_applyDetail").jqGrid("showCol", "projectcost");
					$("#dataTable_applyDetail").jqGrid("showCol", "price");
					$("#dataTable_applyDetail").jqGrid("showCol", "processcost");
					$("#dataTable_applyDetail").jqGrid("showCol", "differences");
					$("#dataTable_applyDetail").jqGrid("showCol", "pricedifferences");
					$("#confirmCzy").val($("#czybh").val());
				}else if($("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "Q"){
					if($("#m_umState").val().trim() == "V"){
						$("#dataTable_applyDetail").jqGrid("showCol", "price");
						$("#saveBut").attr("disabled", true);
					}
					$("#returnBut").css("display", "none");
					$("#cancelBut").css("display", "none");
					ajaxPost("${ctx}/admin/itemPreApp/getSendCount", 
						{
							no:$("#appNo").val()
						}, 
						function(data){
							if(data > 0){
								$("#sendViewBut").css({
									"display":""
								});
							}
						}
					);
					$("#openComponent_customer_custCode").next().attr("data-disabled", true).css({
						"color":"#888"
					});
	        		$("#cysqdrBtn").attr("disabled", true);
	        		$("#delBtn").attr("disabled", true);
	
	       			$("#itemType1").attr("disabled", true);
	       			$("#itemType2").attr("disabled", true);
	   				$("#expired").val("F");
				    $("#delivType").attr("disabled", true);
	   			
					$("#dataTable_applyDetail").jqGrid("showCol", "sendqty");
					$("#dataTable_applyDetail").jqGrid("showCol", "shortqty");
					$("#splRemark").parent().removeAttr("hidden");
	    			$("#pnlSpl2").removeAttr("hidden");
	    			$("#sendCount").customComponent({
	    				btnWidth: 60,
	    				btnContent: "查看",
	    				btnEvent: function(){
				        	Global.Dialog.showDialog("sendCountView",{
				        	  title:"已发货--查看",
				        	  url:"${ctx}/admin/itemPreApp/goSendCountView",
				        	  postData:{
				        	  	no: $("#appNo").val()
				        	  },
				        	  height: 600,
				        	  width:800,
				        	});
	    				}
	    			});
				}
				itemType1Change();
	   			isSetItemChange();  
				if($("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "C" || $("#m_umState").val().trim() == "Q"){
					$("#ksxzBtn").attr("disabled", true);
				}
				
			   	sendTypeChange();
			    if($("#preAppNo").val().trim() != ""){
			   		$("#openComponent_customer_custCode").attr("readonly", true);
					$("#openComponent_customer_custCode").next().attr("data-disabled", true).css({
						"color":"#888"
					});
					$("#itemType1").attr("disabled", true);
					$("#isSetItem").attr("disabled", true);
			    }
			   	if($("#m_umState").val().trim() == "C"){
			   		if($("#isSetItem").val() == "1"){
						checkCost();
			   		}
			   		$("#needCheckQty").val("1");
			   		checkQtyByItemType2();
			   		checkCustStatus();
			   		checkCustPay();
			   		if($("#itemType1").val()=="JC" && $("#isSpecReq").val().trim()=="1"){
				   		checkIntCostPay();
			   		}
			   	}
			   	if($("#m_umState").val().trim() != "A" && $("#m_umState").val().trim() != "M"){
			   		$("#sendType").attr("disabled", true);
			   	}
	  			if($("#m_umState").val().trim() == "Q"){
	    			$("#yyxxzBtn").attr("disabled", true);
	    			$("#ksxzBtn").attr("disabled", true);
	  			}
	  			if($("#projectManDescr").val()){
	  				$("#openComponent_employee_projectMan").val($("#projectManDescr").val());
	  			}
		    	isEnableImportExcel();
			};
			function itemType2Change(){
				var itemType1 = $("#itemType1").val();
				var itemType2 = $("#itemType2").val();
				if(itemType1.trim() == "JZ" && itemType2.trim() == "JZFS"){
					ajaxPost("${ctx}/admin/itemPreApp/getFsArea",
						{
							"custCode":$("#custCode").val().trim()
						},
						function(data){
							$("#fsArea").html(data);
							$("#fsArea").css("display", "block");
							$("#fsArea").css({
								"left":($("#fsArea").parent().children("select").offset().left+160)+"px"
							});
						}
					);
				}else{
					$("#fsArea").css("display", "none");
				}
				$("#itemType2Save").val($("#itemType2").val());
				
			}
			function checkCost(){
				ajaxPost("${ctx}/admin/itemPreApp/checkCost",
					{
						itemType2:$("#itemType2Save").val(),
						custCode:$("#custCode").val(),
						custType:$("#custType").val(),
						itemType1:$("#itemType1").val()
					},
					function(data){
						if(data.tipVisible){
							$("#itemCostTip").html(data.tips);
						}
					}
				);
			}
		    function checkQty(){
		    	if($("#isSetItem").val()=="0"){
					var param= Global.JqGrid.allToJson("dataTable_applyDetail");
					$.extend(param,{
						custCode:$("#custCode").val(),
						itemType1:$("#itemType1").val(),
						appNo:$("#appNo").val()
					});
					
					ajaxPost("${ctx}/admin/itemPreApp/checkQty", param,
						function(data){
							if(data.tipVisible){
								$("#itemQtyTip").html("<button id=\"itemQtyTipBtn\" class=\"btn btn-link\" style=\"margin:0px;padding:0px;font-size:12px;color:red\">"+data.tips+"</button>");
								$("#itemQtyTipBtn").on("click",function(){
									Global.Dialog.showDialog("itemQtyTip",{
										title:"审核之后,以下材料的已领料数量将大于需求数量",
						        	  	url:"${ctx}/admin/itemPreApp/goTipClickPage",
						        	  	postData:{
						        	  		resultList:data.resultList,
						        	  		m_umState:"A"
						        	  	},
						        	  	height: 500,
						        	 	width:800,
						        	});
								}).mouseover(function(){
									$("#itemQtyTipBtn").css({
										"font-weight":"bold",
										"font-style":"italic"
									});
								}).mouseout(function(){
									$("#itemQtyTipBtn").css({
										"font-weight":"",
										"font-style":""
									});
								});
							}
						}
					);
		    	}else{
		    		var rows = $("#dataTable_applyDetail").jqGrid("getRowData");
		    		var totalDifferences=0;
		    		var pks="";
					$.each(rows, function(i,row){
						var differences=parseFloat(row.differences);
						if(differences>0){
							totalDifferences+=differences;
							if(pks!=""){
								pks+=",";
							}
							pks+=row.specreqpk;
						}
					});
					if(totalDifferences>0){
			    		$("#itemQtyTip").html("<button id=\"itemQtyTipBtn\" class=\"btn btn-link\" style=\"margin:0px;padding:0px;font-size:12px;color:red\">审核之后,有些材料的已领料数量将大于解单数量(点击查看明细)!累计超预算金额"+totalDifferences+"</button>");
								$("#itemQtyTipBtn").on("click",function(){
									Global.Dialog.showDialog("itemQtyTip",{
										title:"审核之后,以下材料的已领料数量将大于解单数量",
							        	url:"${ctx}/admin/itemPreApp/goTipClickPage",
							        	postData:{
							        	  	resultList:pks,
							        	  	m_umState:"J",
							        	  	appNo:$("#appNo").val(),
							        	  	custCode:$("#custCode").val()
							        	},
							        	height: 500,
							        	width:800,
							     });
								}).mouseover(function(){
									$("#itemQtyTipBtn").css({
										"font-weight":"bold",
										"font-style":"italic"
									});
								}).mouseout(function(){
									$("#itemQtyTipBtn").css({
										"font-weight":"",
										"font-style":""
									});
								});
					}
		    	}
		    }
		    function checkQtyByItemType2(){
				ajaxPost("${ctx}/admin/itemPreApp/checkQtyByItemType2",
					{
						custCode:$("#custCode").val(),
						itemType1:$("#itemType1").val(),
						appNo:$("#appNo").val()
					},
					function(data){
						if(data.tipVisible){
							$("#itemQtyTipByItemType1Tip").html("<button id=\"itemQtyTipByItemType1TipBtn\" class=\"btn btn-link\" style=\"margin:0px;padding:0px;font-size:12px;color:red\">"+data.tips+"</button>");
							$("#itemQtyTipByItemType1TipBtn").on("click",function(){
								Global.Dialog.showDialog("itemQtyTipByItemType1Tip",{
					        		title:"审核之后,以下品类的已领料数量将大于需求数量",
					        	  	url:"${ctx}/admin/itemPreApp/goTipClickPage",
					        	  	postData:{
					        	  		resultList:data.resultList,
					        	  		m_umState:"V"
					        	  	},
					        	  	height: 500,
					        	  	width:800,
					        	});
							}).mouseover(function(){
								$("#itemQtyTipByItemType1TipBtn").css({
									"font-weight":"bold",
									"font-style":"italic"
								});
							}).mouseout(function(){
								$("#itemQtyTipByItemType1TipBtn").css({
									"font-weight":"",
									"font-style":""
								});
							});
						}
					}
				);
		    }
		    function checkCustStatus(){
				ajaxPost("${ctx}/admin/itemPreApp/checkCustStatus",
					{
						custCode:$("#custCode").val()
					},
					function(data){
						if(data.tipVisible){
							$("#custStatusTip").html(data.tips);
						}
					}
				);
		    }
		    function checkCustPay(){
				ajaxPost("${ctx}/admin/itemPreApp/checkCustPay",
					{
						custCode:$("#custCode").val(),
						appNo:$("#appNo").val(),
					 	firstPay:$("#firstPay").val(),
					 	secondPay:$("#secondPay").val(),
					 	thirdPay:$("#thirdPay").val(),
					 	fourPay:$("#fourPay").val(),
					 	designFee:$("#designFee").val(),
					 	chgFee:$("#zjje").val(),
					 	custPay:$("#payFee").val()
					 },
					function(data){
						if(data.tipVisible){
							$("#custPayTip").html(data.tips);
						}
					}
				);
		    }
		    function checkIntCostPay(){
				ajaxPost("${ctx}/admin/itemApp/doSubmitCheck",
					{
						custCode:$("#custCode").val(),
						isCupboard:$("#isCupboard").val(),
						itemType1:$("#itemType1").val(),
						m_umState:'C'
					},
					function(data){
						if(data.msg!="null&null"){
							var msg=data.msg.split("&")[0];
							//var json=listStrToJson();
							$("#intCostTip").html("<button id=\"intCostTipBtn\" class=\"btn btn-link\" style=\"margin:0px;padding:0px;font-size:12px;color:red\">"+msg+"</button>");
							$("#intCostTipBtn").on("click",function(){
								Global.Dialog.showDialog("itemQtyTip",{
									title:"成本控制明细",
					        	  	url:"${ctx}/admin/itemPreApp/goIntCostTipClickPage",
					        	  	postData:{
					        	  		custCode:$("#custCode").val(),
										isCupboard:$("#isCupboard").val(),
										itemType1:$("#itemType1").val(),
										resultList:data.msg.split("&")[1]
					        	  	},
					        	  	height: 600,
					        	 	width:800,
					        	});
							}).mouseover(function(){
								$("#intCostTipBtn").css({
									"font-weight":"bold",
									"font-style":"italic"
								});
							}).mouseout(function(){
								$("#intCostTipBtn").css({
									"font-weight":"",
									"font-style":""
								});
							});
							//$("#intCostTip").html(data.msg);
						}
					}
				);
		    }
		    function selectFirstRow(){
				var ids = $("#dataTable_applyDetail").jqGrid("getDataIDs");
	     		if(ids.length > 0){
					$("#dataTable_applyDetail").jqGrid("setSelection", ids[0]);
					
		   			$("#openComponent_itemPreApp_preAppNo").attr("readonly", true);
					$("#openComponent_itemPreApp_preAppNo").next().attr("data-disabled", true).css({
						"color":"#888"
					});
		    		if($("#preAppNo").val() == ""){
		    			$("#itemType1").attr("disabled", true);
			   			$("#openComponent_customer_custCode").attr("readonly", true);
						$("#openComponent_customer_custCode").next().attr("data-disabled", true).css({
							"color":"#888"
						});
		    		}
		    		$("#isService").attr("disabled", true);
		    		$("#itemType2").attr("disabled", true);
		    		$("#isCupboard").attr("disabled", true);
		    	}else{
		   			$("#openComponent_itemPreApp_preAppNo").removeAttr("readonly", true);
					$("#openComponent_itemPreApp_preAppNo").next().attr("data-disabled", false).css({
						"color":""
					});
	     			if($("#preAppNo").val() == ""){
		    			$("#itemType1").removeAttr("disabled");
			   			$("#openComponent_customer_custCode").removeAttr("readonly");
						$("#openComponent_customer_custCode").next().attr("data-disabled", false).css({
							"color":""
						});
	     			}
		    		$("#isService").removeAttr("disabled");
		    		$("#itemType2").removeAttr("disabled");
		    		$("#isCupboard").removeAttr("disabled");
		    	}
		    	fillIsSetItem();
		    }
		    function checkReturn(){
		    	$("#isTipExit").val("F");
		    	if($("#m_umState").val() == "V"){
		    		return;
		    	}
				var ids = $("#dataTable_applyDetail").jqGrid("getDataIDs");
				if(ids.length <= 0){
					art.dialog({
						content:"领料明细为空,审核退回失败"
					});
					return;
				}
				art.dialog({
					content:"确定要进行审核退回操作吗?",
					ok:function(){
						var param= Global.JqGrid.allToJson("dataTable_applyDetail");
						$.extend(param,{
							sendTypeCheck : "0",
							applyQtyCheck : "0"
						});
						var formData = $("#dataForm").jsonForm();
						formData.m_umState = "R";
						formData.expired = "";
						$.extend(param,formData);
						
						ajaxPost("${ctx}/admin/itemPreApp/doSave_autoOrder", param,
							function(ret){
								//var data = listStrToJson(ret.msg);
								var returnInfo = ret.datas.info;//data[0].info; 
								if(ret.rs == true){
									art.dialog({
										content:returnInfo,
										ok:function(){
											closeWin();
										}
									});
								}else{
									$("#_form_token_uniq_id").val(ret.token.token);
									art.dialog({
										content:returnInfo
									});
								}
							}
						);
					},
					cancel:function(){}
				});
		    }
		    function cancel(){
		    	$("#isTipExit").val("F");
		    	if($("#m_umState").val() == "V"){
		    		return;
		    	}
				var ids = $("#dataTable_applyDetail").jqGrid("getDataIDs");
				if(ids.length <= 0){
					art.dialog({
						content:"领料明细为空,取消领料失败"
					});
					return;
				}
				art.dialog({
					content:"确定要取消该领料单吗?",
					ok:function(){
						var param= Global.JqGrid.allToJson("dataTable_applyDetail");
						$.extend(param,{
							sendTypeCheck : "0",
							applyQtyCheck : "0"
						});
						var formData = $("#dataForm").jsonForm();
						formData.m_umState = "D";
						formData.expired = "";
						$.extend(param,formData);
						
						ajaxPost("${ctx}/admin/itemPreApp/doSave_autoOrder",param,
							function(ret){
								//var data = listStrToJson(ret.msg);
								var returnInfo = ret.datas.info;//data[0].info; 
								if(ret.rs == true){
									art.dialog({
										content:returnInfo,
										ok:function(){
											closeWin();
										}
									});
								}else{
									$("#_form_token_uniq_id").val(ret.token.token);
									art.dialog({
										content:returnInfo
									});
								}
							}
						);
					},
					cancel:function(){}
				});
		    }
		    function remarksDiffColor(){
				if(showReqRemarks()){
					var remarks = $("#dataTable_applyDetail").jqGrid("getCol", "preremarks", true);
					var reqremarks = $("#dataTable_applyDetail").jqGrid("getCol", "reqremarks", true);
					for(var i=0;i<remarks.length;i++){
						if(remarks[i].value != reqremarks[i].value && reqremarks[i].value != ""){
							$("#"+remarks[i].id+" td").css("background", "#FFAA00");
						}else{
							$("#"+remarks[i].id+" td").css("background", "");
						} 
					}
				}
		    }
		    function qtyDiffColor(){
		    	if($("#itemType1").val().trim() == "JC"){
					var declareQtys = $("#dataTable_applyDetail").jqGrid("getCol", "declareqty", true);
					var qtys = $("#dataTable_applyDetail").jqGrid("getCol", "qty", true);
					for(var i=0;i<declareQtys.length;i++){
						if(declareQtys[i].value != qtys[i].value){
					 		$("#dataTable_applyDetail tr[id="+declareQtys[i].id+"] td[aria-describedby=dataTable_applyDetail_declareqty]").css("color", "rgb(249, 116, 116)");
					 		$("#dataTable_applyDetail tr[id="+declareQtys[i].id+"] td[aria-describedby=dataTable_applyDetail_qty]").css("color", "rgb(249, 116, 116)");
						}else{
					 		$("#dataTable_applyDetail tr[id="+declareQtys[i].id+"] td[aria-describedby=dataTable_applyDetail_declareqty]").css("color","");
					 		$("#dataTable_applyDetail tr[id="+declareQtys[i].id+"] td[aria-describedby=dataTable_applyDetail_qty]").css("color","");
						} 
					}
		    	}
		    }
		    function showReqRemarks(){
/* 				if($("#itemType1").val().trim() == "ZC" && $("#preAppNo").val() != "" && $("#isSetItem").val() == "0"){
					$("#dataTable_applyDetail").jqGrid("showCol", "reqremarks");
					return true;
				}else{
					$("#dataTable_applyDetail").jqGrid("hideCol", "reqremarks");
					return false;
				} */
				return true;
		    }
		    function sendView(){
	        	Global.Dialog.showDialog("itemAppAddSendView",{
	        		title:"分批发货明细",
	        	  	url:"${ctx}/admin/itemPreApp/goSendView",
	        	  	postData:{
	        	  		no:$("#appNo").val()
	        	  	},
	        	 	height: 600,
	        	  	width:900
	        	});
		    }
		    function exit(){
				var isRefresh=false;//是否刷新
				var oldRemarks="${itemPreMeasure.remarks }";
				var newRemarks=$("#remarks").val();
				if(oldRemarks!=newRemarks){//修改了备注才刷新
					isRefresh=true;
				}
		    	if($("#isTipExit").val() == "T" && $("#m_umState").val() != "V" && !$("#saveBut").attr("disabled")){
		    		art.dialog({
		    			content:"是否保存被更改的数据",
		    			button: [{
							value: "取消",
							callback:function(){}
						}, {
				            value: "否",
				            callback: function () {
		    					closeWin(isRefresh);
				            }
				        }],
				        ok:function(){
		    				save(null, null, true);
				        }
		    		});
		    	}else{
		    		closeWin(isRefresh);
		    	}
		    }
		     function isEnableImportExcel(){
		    	if(($("#m_umState").val() == "A" || $("#m_umState").val() == "M") && $("#itemType1").val() != "JC" && $("#isSpecReq").val().trim()=="0" && $("#isSetItem").val() != "" && $("#preAppNo").val() == ""){
		    		$("#importExcelBtn").removeAttr("disabled");
		    	}else{
		    		$("#importExcelBtn").attr("disabled", true);
		    	}
		    } 
		     function preAppNoChange(){
		    	isEnableImportExcel();
		    }
		    function importExcel(){
		    	if($("#m_umState").val() == "V"){
		    		return;
		    	}
		    	if($("#itemType1").val() == ""){
		    		art.dialog({
		    			content:"请选择材料类型1"
		    		});
		    		return;
		    	}
		    	if($("#custCode").val() == ""){
		    		art.dialog({
		    			content:"请先选择客户"
		    		});
		    		return;
		    	}
				
				var itemCodes=$("#dataTable_applyDetail").getCol("itemcode", false);
				
				Global.Dialog.showDialog("importExcel",{
					title:"领料管理--从Excel导入",
					url:"${ctx}/admin/itemPreApp/goImportExcel",
					postData:{
						itemType1:$("#itemType1").val(),
						itemType2:$("#itemType2").val(),
						no:$("#appNo").val(),
						custCode:$("#custCode").val(),
						itemCodes:itemCodes
					},
					height:600,
					width:1100,
					returnFun:function (data){
						if(data){
							$("#isTipExit").val("T");
							$.each(data.datas,function(i,rowData){
								$.extend(rowData,{
									lastupdate:new Date(),
									lastupdatedby:$("#czybh").val(),
									actionlog:"ADD",
									expired:"F"
								});
								Global.JqGrid.addRowData("dataTable_applyDetail", rowData);
							});
							
							$("#dataTable_applyDetail").footerData("set", {
								allcost:$("#dataTable_applyDetail").getCol("allcost", false, "sum") ,
								allprojectcost:$("#dataTable_applyDetail").getCol("allprojectcost", false, "sum") ,
								weight:$("#dataTable_applyDetail").getCol("weight", false, "sum") ,
								numdescr:$("#dataTable_applyDetail").getCol("numdescr", false, "sum")
							});
							selectFirstRow();
							setSplEditByItem();
						}
					}
				});	
		    }
		    function nextPage(){
		    	if($("#a_mainInfo").parent().attr("class").indexOf("active")!=-1){
		    		$("#otherInfo").css({
		    			"height":$("#itemAppFromDiv").height()-23
		    		});
		    		$("#a_otherInfo").tab("show");
		    	}else{
		    		$("#a_mainInfo").tab("show");
		    	}
		    }
		    function isIndependCust(custCode){
		    	ajaxPost("${ctx}/admin/itemPreApp/isIndependCust?custCode="+custCode, {}, function(data){
		    		if(data.isIndependCust){
		    			$("#independCustPhone").html("客户电话:"+data.phone);
		    		}
		    	});
		    }
		    function showHideForm(flag){//true 显示 false 隐藏
				var nowShow = $("#itemAppUl li[class*='active']").children().attr("id");
				
				if(nowShow == "a_mainInfo"){//为了防止页面错位,限制在主页才生效
			    	if(flag){
			    		$("#showHidePart").css("display", "");
			    		$("#showButton").attr("hidden", true);
			    		$("#hideButton").removeAttr("hidden");
			    	}else{
			    		$("#showHidePart").css("display", "none");
			    		$("#hideButton").attr("hidden", true);
			    		$("#showButton").removeAttr("hidden");
			    	}
					
					$("#dataTable_applyDetail").setGridHeight($(document).height()-$("#itemAppFromDiv").height()-240);
				/* 	var rows = $("#dataTable_applyDetail").jqGrid("getRowData"); //保存数据 
					$.jgrid.gridUnload("dataTable_applyDetail");  //移除表格
					initApplyDetailDataTable(null, $(document).height()-$("#itemAppFromDiv").height()-260);//重新初始化表格
					//插入数据
					$.each(rows, function(index, value){
						$("#dataTable_applyDetail").jqGrid("addRowData", index, value);
					});
					//因为重新刷新表格,此方法涉及表格的列隐藏和显示 add by zzr 2018/06/11 begin 
					//PS:itemType1Change参数为true防止刷新材料类型2
		   			if("${itemPreMeasure.costRight}"=="1"){
						$("#dataTable_applyDetail").jqGrid("showCol", "cost");
						$("#dataTable_applyDetail").jqGrid("showCol", "allcost");
						$("#dataTable_applyDetail").jqGrid("showCol", "projectcost");
						$("#dataTable_applyDetail").jqGrid("showCol", "allprojectcost");
		   			}
					if($("#m_umState").val().trim() == "C"){
						$("#dataTable_applyDetail").jqGrid("showCol", "cost");
						$("#dataTable_applyDetail").jqGrid("showCol", "projectcost");
						$("#dataTable_applyDetail").jqGrid("showCol", "price");
						$("#dataTable_applyDetail").jqGrid("showCol", "processcost");
						$("#dataTable_applyDetail").jqGrid("showCol", "differences");
						$("#dataTable_applyDetail").jqGrid("showCol", "pricedifferences");
						$("#confirmCzy").val($("#czybh").val());
					}else if($("#m_umState").val().trim() == "V" || $("#m_umState").val().trim() == "Q"){
						$("#dataTable_applyDetail").jqGrid("showCol", "sendqty");
						$("#dataTable_applyDetail").jqGrid("showCol", "shortqty");
					}
					itemType1Change(true);
					isSetItemChange();
					//因为重新刷新表格,此方法涉及表格的列隐藏和显示 add by zzr 2018/06/11 end */
				}
		    }
		    
			function unItemAppMaterial(){
				var appNo = "";
				if($("#m_umState").val().trim() != "A"){
					appNo = $("#appNo").val();
				}
				var itemCodes = $("#dataTable_applyDetail").getCol("itemcode",false);
				Global.Dialog.showDialog("ylcl",{
					title:"领料明细--查看未领材料",
					url:"${ctx}/admin/itemPreApp/goUnItemAppMaterial",
					postData:{
						custCode:$("#custCode").val(),
						itemType1: $("#itemType1").val(),
						itemType2: $("#itemType2").val(),
					},
					height:600,
					width:1350
				});
			}
			function checkIsSpecReq(){
				ajaxPost("${ctx}/admin/itemPreApp/checkIsSpecReq",
	   				{
	   					itemType1:$("#itemType1").val()
	   				},
	   				function (data){
	   					$("#isSpecReq").val(data);
	   				}
	   			);
			}
			function add(isInsert){
				$("#delNum").val(0);
				$("#isDel").val("0");
				var posi = $('#dataTable_applyDetail').jqGrid('getGridParam', 'selrow')-1;
			 	var ret = selectDataTableRow("dataTable_applyDetail");
			 	var preAppDTPks= $("#dataTable_applyDetail").getCol("preappdtpk", false);
				var reqPks= $("#dataTable_applyDetail").getCol("reqpk", false);
				var itemCodes = $("#dataTable_applyDetail").getCol("itemcode", false);
				var tip="新增";
				if(isInsert=="1"){
				 	if(!ret){
					 	art.dialog({
							content: "请选择一条记录"
						});
						return;	
				 	}
				 	tip="添加到分组";
				}
			 	//var url="${ctx}/admin/baseCheckItemPlan/goQuickSave?custCode=${map.code}&itemType1=JZ"+baseItemType1Str;
				Global.Dialog.showDialog("insert",{
					  title:"自动拆单--"+tip,
					  url:"${ctx}/admin/itemPreApp/goInsertAutoOrder",
					  postData:{ 
						  custCode:$("#custCode").val(),
						  itemType1:$("#itemType1").val(),
						  itemType2:$("#itemType2").val(),
						  preAppNo:$("#preAppNo").val(),
						  isService:$("#isService").val(),
						  appNo:$("#appNo").val(),
						  m_umState:"A",
						  preAppDTPks:preAppDTPks,
						  reqPks:reqPks,
						  itemCodes:itemCodes,
						  isSetItem:$("#isSetItem").val(),
						  ordername:isInsert=="1"?ret.ordername:"",
						  ordernamecode:isInsert=="1"?ret.ordernamecode:"",
						  isInsert:isInsert
					  },
					  height:650,
					  width:1200,
					  resizable:false, 
					  returnFun: function(data){
						  var arr=$("#dataTable_applyDetail").jqGrid("getRowData");
						  $.each(data,function(k,v){
							 v.lastupdate=getNowFormatDate();
							 v.lastupdatedby=$("#czybh").val();
							 v.actionlog="ADD";
							 v.expired="F";
							 if(v.ordername=="不下单"){
							 	cantordernum+=1;
								$("#tip").show().html("不下单数量："+cantordernum);
							 }
							 arr.push(v);
						 });
				  		 var params=JSON.stringify(arr); 
				  		 $("#dataTable_applyDetail").jqGrid("setGridParam",{url:"${ctx}/admin/baseCheckItemPlan/goTmpJqGrid",datatype:"json",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
					}
				});
			}
			//离焦更新备注
			function updateRemarks(){
		    	$.ajax({
					url : "${ctx}/admin/itemPreApp/updateRemarks",
					type : "post",
					data : {preAppNo:$("#preAppNo").val(),remarks:$("#remarks").val()},
					dataType : "json",
					async:false,
					cache : false
				});
			}
			function photoDownload(){
				var number = Global.JqGrid.allToJson("dataTable_material", "photoname").datas.length;
				if(number <= 0 ){
					art.dialog({
						content:"该记录没有图片",
						time:3000
					});
					return;
				}
				window.open("${ctx}/admin/itemPreApp/downLoad?no="+$("#preAppNo").val());
			}
			function reloadJqGrid(){
				$("#dataTable_applyDetail").jqGrid("setGridParam",{
					url:"${ctx}/admin/itemPreApp/goAutoOrderJqGrid",
					postData:{
						custCode:$("#custCode").val(),
						itemType1:$("#itemType1").val(),
						itemType2:$("#itemType2").val(),
						preAppNo:$("#preAppNo").val(),
						isService:$("#isService").val(),
						preAppDTPks:"",
						reqPks:"",
						appNo:$("#appNo").val(),
						m_umState:$("#m_umState").val(),
						isSetItem:$("#isSetItem").val(),
						itemCodes:""
					},
					page:1,
					sortname:''
				}).trigger("reloadGrid");
			}
		//修改是否直接提交审核
		function changeIsCheck(){
			var isChecked=$("#isChecked").prop("checked")==true?"1":"0";
			$("#isSubCheck").val(isChecked);
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs" style="float:left">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">保存</button>
	    				<button id="photoBut" type="button" class="btn btn-system" onclick="photoDownload()">图片下载</button>
	    				<button id="returnBut" type="button" class="btn btn-system" onclick="checkReturn()">审核退回</button>
	    				<button id="cancelBut" type="button" class="btn btn-system" onclick="cancel()">取消领料</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="exit()">关闭</button>
	    				<button id="sendViewBut" type="button" class="btn btn-system" onclick="sendView()" style="display:none">分批发货明细</button>
	    				<button id="nextPageBut" type="button" class="btn btn-system" onclick="nextPage()">▶</button>
	    				<input type="checkbox" style="position:absolute;left:300px;top:8px;"
					id="isChecked" name="isChecked" onclick="changeIsCheck()" checked value="1"
					 />
					 <li hidden >
					 <house:dict id="whcode" dictCode="" style='width:160px;border-radius:5px;'
					 				sql="select Code,Code+' '+Desc1 Desc1 from tWareHouse where ItemType1='JZ' and Expired='F' " 
									 sqlValueKey="Code" sqlLableKey="Desc1" onchange="" ></house:dict>							
					 </li>
					<p style=" position:absolute;left:315px;top:13px;">直接提交审核</p>
					</div>
					<div class="container" style="margin-left:20px;float:left;width:75%">
						<div class="row">
							<span id="independCustPhone" style="color:red"></span>
						</div>
						<div class="row">
							<div class="col-md-6" >
								<span id="custStatusTip" style="color:red"></span>
							</div>
							<div class="col-md-6" >
								<span id="itemQtyTip" style="color:red"></span>
								<span id="itemCostTip" style="color:red"></span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6" >
								<span id="itemQtyTipByItemType1Tip" style="color:red"></span>
							</div>
							<div class="col-md-6" >
								<span id="intCostTip" style="color:red"></span>
							</div>
							<div class="col-md-6" >
								<span id="custPayTip" style="color:red"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="itemAppFromDiv" class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" id="m_umState" name="m_umState" value="A" />
						<input type="hidden" id="hasInSetReq" name="hasInSetReq" value="${itemPreMeasure.hasInSetReq }" />
						<input type="hidden" id="czybh" name="czybh" value="${itemPreMeasure.czybh }" />
						<input type="hidden" id="expired" name="expired" value="${itemPreMeasure.expired }" />
						<input type="hidden" name="m_checkStatus" id="m_checkStatus" value="${itemPreMeasure.m_checkStatus }"/>
						<input type="hidden" name="materWorkType2" id="materWorkType2" value="${itemPreMeasure.materWorkType2 }"/>
						<input type="hidden" name="custType" id="custType" value="${itemPreMeasure.custType }"/>
						<input type="hidden" name="itemType2Save" id="itemType2Save" value="${itemPreMeasure.itemType2 }"/>
						<input type="hidden" name="needCheckQty" id="needCheckQty" value="0"/>
						<input type="hidden" name="isTipExit" id="isTipExit" value="F"/>
						<input type="hidden" name="continuityAdd" id="continuityAdd" value="${itemPreMeasure.continuityAdd }"/>
						<input type="hidden" name="isSpecReq" id="isSpecReq" value="${itemPreMeasure.isSpecReq}"/>
						<input type="hidden" name="regionCode" id="regionCode" value="${itemPreMeasure.regionCode}"/>
						<input type="hidden" name="isSubCheck" id="isSubCheck" value="1"/>
						<input type="hidden" name="isDel" id="isDel" value="0"/>
						<input type="hidden" name="delNum" id="delNum" value="0"/>
						<house:token></house:token>
						<ul class="ul-form">
							<ul id="itemAppUl" class="nav nav-tabs" hidden>
							    <li class="active"><a id="a_mainInfo" href="#mainInfo" data-toggle="tab">主信息</a></li>  
							    <li><a id="a_otherInfo" href="#otherInfo" data-toggle="tab">其他信息</a></li>  
							</ul>  
						    <div class="tab-content">  
								<div id="mainInfo"  class="tab-pane fade in active" style="border:0px"> 
									<div class="validate-group">
										<li class="form-validate">
											<label>客户编号</label>
											<input type="text" id="custCode" name="custCode" value="${itemPreMeasure.custCode}" readonly="true"/>
										</li>
										<li>	
											<label>其他费用</label>
											<input type="text" id="otherCost" name="otherCost" value="${itemPreMeasure.otherCost}" />
											<span style="color:black;">元</span><span style="color:red;font-size:12px;">正数:应付给供应商的金额 负数:供应商退还的金额</span> 
										</li>
										<li>
											<label>领料单号</label>
											<input type="text" id="appNo" name="appNo" value="${itemPreMeasure.appNo }" />
										</li>
									</div>
									<li>
										<label>楼盘</label>
										<input type="text" id="address" name="address" value="${itemPreMeasure.address }" />
									</li>
									<li>
										<label>档案号</label>
										<input type="text" id="documentNo" name="documentNo" value="${itemPreMeasure.documentNo}" readonly/>
									</li>
									<li>
										<label>面积</label>
										<input type="text" id="area" name="area" value="${itemPreMeasure.area }" readonly="true"/>
									</li>	
									<li>
										<label>预申请单号</label>
										<input type="text" id="preAppNo" name="preAppNo" value="${itemPreMeasure.preAppNo}" />
									</li>
									<div id="showHidePart">
										<li>
											<label>材料类型1</label>
											<house:dict id="itemType1" dictCode="" 
														sql="select rtrim(Code)+' '+Descr fd,Code from tItemType1  where Expired='F' and code in ${itemPreMeasure.itemRight }   order by DispSeq " 
														sqlValueKey="Code" sqlLableKey="fd" value="${itemPreMeasure.itemType1}"  onchange="itemType1Change()">
											</house:dict>
										</li>
										<%-- <li hidden>
											<label>配送方式</label>
											<house:xtdm id="delivType" dictCode="DELIVTYPE" value="${itemPreMeasure.delivType }"></house:xtdm>
										</li> --%>
										<li>
											<label>领料类型</label>
											<house:xtdm id="itemAppType" dictCode="ITEMAPPTYPE" value="${itemPreMeasure.itemAppType }" ></house:xtdm>
										</li>
									<%-- 	<li>
											<label>材料类型2</label>
											<house:dict id="itemType2" dictCode="" 
														sql="select distinct a.Code,(a.Code+' '+a.Descr) Descr from tItemType2 a  left outer join  tItem b on b.ItemType2=a.code    left outer join tItemPreAppDetail c on c.ItemCode=b.code   where a.ItemType1='${itemPreMeasure.itemType1 }' and c.no='${itemPreMeasure.preAppNo }'   order by a.Code" 
														sqlValueKey="Code" sqlLableKey="Descr" value="${itemPreMeasure.itemType2 }" onchange="itemType2Change()">
											</house:dict>
											<span id="fsArea" style="top:5px;display:none;position:absolute;font-size:12px"></span>
										</li> --%>
										<li>
											<label>单据状态</label>
											<house:xtdm id="itemAppStatus" dictCode="ITEMAPPSTATUS" value="${itemPreMeasure.itemAppStatus }"></house:xtdm>
										</li>
										<li>
											<label>项目经理</label>
											<input type="hidden" id="projectManDescr" name="projectManDescr" value="${itemPreMeasure.projectManDescr }" />
											<input type="text" id="projectMan" name="projectMan" value="${itemPreMeasure.projectMan}" />
											<input type="text" id="department" name="department" 
												readonly value="${itemPreMeasure.department1Descr } ${itemPreMeasure.department2Descr }" 
												style="width:120px;font-size:12px;color:black;border:0px;background:none" />
										
										</li>
										<%--  <li hidden>
											<label>发货类型</label>
											<house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="1" onchange="sendTypeChange()"></house:xtdm>
										</li> --%>
										<li>
											<label>电话号码</label>
											<input type="text" id="phone" name="phone" value="${itemPreMeasure.phone}" />
										</li>
										<li ${itemPreMeasure.itemType1=='ZC'?'hidden':''}>
											<label>是否套餐材料</label>
											<house:xtdm id="isSetItem" dictCode="YESNO" value="${itemPreMeasure.isSetItem }" onchange="isSetItemChange()"></house:xtdm>
										</li>
										<li style="padding-left:10px">
											<label class="control-textarea">备注</label>
											<textarea id="remarks" name="remarks" onblur="updateRemarks()">${itemPreMeasure.remarks }</textarea>
										</li>
										<li>
											<label>是否服务产品</label>
											<house:xtdm id="isService" dictCode="YESNO" value="${itemPreMeasure.isService }" onchange="reloadJqGrid()"></house:xtdm>
											<span id="serviceTip" name="serviceTip" style="color:red;font-size:12px;">${itemPreMeasure.serviceTip }</span>
										</li>
										<li hidden>
											<label>是否橱柜</label>
											<house:xtdm id="isCupboard" dictCode="YESNO" value="${itemPreMeasure.isCupboard }"></house:xtdm>
											<%-- <span id="serviceTip" name="serviceTip" style="color:red;font-size:12px;">${itemPreMeasure.serviceTip }</span> --%>
										</li>
										<li hidden>
											<label class="control-textarea" style="top:-30px">测量数据</label>
											<textarea id="measureRemark" style="height:78px">${itemPreMeasure.measureRemark }</textarea>
										</li>
										<li hidden style="padding-left:298px">
											<label class="control-textarea" >供应商备注</label>
											<textarea id="splRemark" style="" readonly="true">${itemPreMeasure.splRemark }</textarea>
										</li>
									
									</div>
								</div> 
								<div id="otherInfo"  class="tab-pane fade" style="border:0px">
									<li>
										<label>楼盘</label>
										<input type="text" id="addressOtherInfo" name="address" value="${itemPreMeasure.address }" />
									</li>
									<div id="pnlCZY" >
										<li hidden>
											<label>签订时间</label>
											<input type="text" id="signDate" value="${itemPreMeasure.signDate }" readonly="true"/>
										</li>
										<li>
											<label>申请人员</label>
											<input type="text" id="appCzyDescr" value="${itemPreMeasure.appCzyDescr }" readonly="true"/>
										</li>
										<li>
											<label>申请时间</label>
											<input type="text" id="date" value="${itemPreMeasure.date }" readonly="true"/>
										</li>
										<li>
											<label>审核人员</label>
											<input type="hidden" id="confirmCzy" value="${itemPreMeasure.confirmCzy }" readonly="true"/>
											<input type="text" id="confirmCzyDescr" value="${itemPreMeasure.confirmCzyDescr }" readonly="true"/>
										</li>
										<li>
											<label>审核时间</label>
											<input type="text" id="confirmDate" value="${itemPreMeasure.confirmDate }" readonly="true"/>
										</li>
										<li>
											<label>发货人员</label>
											<input type="text" id="sendCzyDescr" value="${itemPreMeasure.sendCzyDescr }" readonly="true"/>
										</li>
										<li>
											<label>发货时间</label>
											<input type="text" id="sendDate" value="${itemPreMeasure.sendDate }" readonly="true"/>
										</li>
										<li>
											<label>采购单号</label>
											<input type="text" id="puno" value="${itemPreMeasure.puno }" readonly="true"/>
										</li>
										<li>
											<label>其他费用调整</label>
											<input type="text" id="otherCostAdj" value="${itemPreMeasure.otherCostAdj }" readonly="true"/>
										</li>
									</div>
									<div id="pnlFee" hidden>
										<li>
											<label>首批付款</label>
											<input type="text" id="firstPay" readonly="true"/>
										</li>
										<li>
											<label>二批付款</label>
											<input type="text" id="secondPay" readonly="true"/>
										</li>
										<li>
											<label>三批付款</label>
											<input type="text" id="thirdPay" readonly="true"/>
										</li>
										<li>
											<label>尾批付款</label>
											<input type="text" id="fourPay" readonly="true"/>
										</li>
										<li>
											<label>设计费</label>
											<input type="text" id="designFee" readonly="true"/>
										</li>
										<li>
											<label>增减金额</label>
											<input type="text" id="zjje" readonly="true"/>
										</li>
										<li>
											<label>已付款</label>
											<input type="text" id="payFee" readonly="true"/>
										</li>
										<li>
											<label>未达账</label>
											<input type="text" id="wdz" readonly="true"/>
										</li>
										<li>
											<label>本期付款期数</label>
											<input type="text" id="nowNum" readonly="true"/>
										</li>
										<li>
											<label>本期应付余额</label>
											<input type="text" id="nowShouldPay" readonly="true"/>
										</li>
										<li>
											<label>下期应付余额</label>
											<input type="text" id="nextShouldPay" readonly="true"/>
										</li>
									</div>
									<div id="pnlSpl2" hidden>
										<li>
											<label>供应商状态</label>
											<input type="hidden" id="splStatus" value="${itemPreMeasure.splStatus }" readonly="true"/>
											<input type="text" id="splStatusDescr" value="${itemPreMeasure.splStatusDescr }" readonly="true"/>
										</li>
										<li>
											<label>预计发货时间</label>
											<input type="text" id="arriveDate" value="${itemPreMeasure.arriveDate }" readonly="true"/>
										</li>
										<li>
											<label>到厂日期</label>
											<input type="text" id="arriveSplDate" value="${itemPreMeasure.arriveSplDate }" readonly="true"/>
										</li>
										<li>
											<label>供应商接收人</label>
											<input type="text" value="${itemPreMeasure.splRcvCZYDescr}" readonly/>
										</li>
										<li>
											<label>供应商接收日期</label>
											<input type="text" value="${itemPreMeasure.splRcvDate}" readonly/>
										</li>
										<li>
											<label>已发货次数</label>
											<input type="text" id="sendCount" value="${itemPreMeasure.sendCount}" readonly/>
										</li>
									</div>
							
								</div>
							</div>	
							
						</ul>
						<div id="showButton" style="display:position;color:blue;cursor:pointer" hidden onclick="showHideForm(true)">展开</div>
						<div id="hideButton" style="display:position;color:blue;cursor:pointer" onclick="showHideForm(false)">隐藏</div>
					</form>
				</div>
			</div>
			<div  class="container-fluid" >  
				<ul class="nav nav-tabs" >
				    <li class="active"><a href="#itemPreAppManage_tabView_autoOrderApplyDetail" data-toggle="tab">领料申请明细</a></li>  
					<li class=""><a href="#itemPreAppManage_tabView_materialPhoto" data-toggle="tab">石材开关图片</a></li> 
				</ul>  
			    <div class="tab-content">  
					<div id="itemPreAppManage_tabView_autoOrderApplyDetail"  class="tab-pane fade in active"> 
			         	<jsp:include page="itemPreAppManage_tabView_autoOrderApplyDetail.jsp">
			         		<jsp:param value="" name=""/>
			         	</jsp:include>
					</div> 
					<div id="itemPreAppManage_tabView_materialPhoto"  class="tab-pane fade "> 
			         	<jsp:include page="itemPreAppManage_tabView_materialPhoto.jsp">
			         		<jsp:param value="${itemPreMeasure.preAppNo}" name="no"/>
			         	</jsp:include>
					</div>
				</div>	
			</div>	
		</div>
	</body>
</html>


