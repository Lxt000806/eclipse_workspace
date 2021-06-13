<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>采购订单管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_saleCust.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_salesInvoice.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	function doExcel(){
		var url = "${ctx}/admin/purchase/doExcel";
		doExcelAll(url);
	}
	$(function(){
		$("#whcode").openComponent_wareHouse();	
		$("#supplier").openComponent_supplier();	
		$("#itCode").openComponent_item();	
		$("#sino").openComponent_salesInvoice();	
		$("#appItemMan").openComponent_employee();	
		$("#applyMan").openComponent_employee();	
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/purchase/goJqGrid",
			postData:{status:"${purchase.status}"},
			sortable: true,
			height:$(document).height()-$("#content-list").offset().top-100,
			colModel : [
				{name:'sumarrivqty',	index:'sumarrivqty',	width:90,	label:'arrivqty',	sortable:true,align:'left',hidden:true},
				{name:'no',			index:'no',			width:90,	label:'采购单号',	sortable:true,align:'left',},
				{name:'saletype',	index:'saletype',	width:90,	label:'saletype',	sortable:true,align:'left', hidden:true},
				{name:'status', 	index:'status',		width:80,	label:'采购单状态',sortable:true,align:'left',hidden:true,},// hidden:true
				{name:'statusdescr', 	index:'statusdescr',		width:80,	label:'采购单状态',sortable:true,align:'left',},
				{name:'date',		index:'date',		width:85,	label:'采购日期',	sortable:true,align:'left',	formatter: formatDate},
				{name:'appitemdate',		index:'appitemdate',		width:85,	label:'订货日期',	sortable:true,align:'left',	formatter: formatDate},
				{name:'arrivedate',	index:'arrivedate',	width:120,	label:'到货日期',	sortable:true,align:'left',	formatter: formatDate,hidden:true},
				{name:'type',		index:'type',		width:60,	label:'采购类型',	sortable:true,align:'left',hidden:true,},//,hidden:true
				{name:'typedescr',	index:'typedescr',	width:70,	label:'采购类型',	sortable:true,align:'left',},
				{name:'itemtypedescr',index:'itemtypedescr',			width:90,	label:'材料类型1',	sortable:true,align:'left',},
				{name:'issetitem',	index:'issetitem',	width:70,	label:'套餐材料',	sortable:true,align:'left',hidden:true},
				{name:'yesno3',		index:'yesno3',		width:70,	label:'套餐材料',	sortable:true,align:'left',},
				{name:'isservice',	index:'isservice',	width:90,	label:'是否服务产品',	sortable:true,align:'left',hidden:true},
				{name:'yesno2',		index:'yesno2',		width:90,	label:'是否服务产品',	sortable:true,align:'left',},
				{name:'delivtype',	index:'delivtype',	width:70,	label:'配送地点',		sortable:true,align:'left',hidden:true}, 
				{name:'delivtypedescr',	index:'delivtypedescr',	width:70,	label:'配送地点',		sortable:true,align:'left',}, 
			 	{name:'whcode',		index:'whcode',width:200,	label:'仓库编号',	sortable:true,	align:'left',hidden:true	},
			 	{name:'whcodedescr',index:'whcodedescr',width:100,	label:'仓库名称',	sortable:true,	align:'left',},
				{name:'custdescr',	index:'custdescr',	width:70,	label:'客户名称',	sortable:true,align:'left',},
				{name:'documentno',	index:'documentno',	width:90,	label:'档案号',	sortable:true,align:'left',},
				{name:'sumarrivqty',	index:'sumarrivqty',	width:150,	label:'sumArrivQty',	sortable:true,align:'left',	hidden:true},
				{name:'custcode',	index:'custcode',	width:90,	label:'客户编号',	sortable:true,align:'left',},
				{name:'custtypedescr',	index:'custtypedescr',	width:90,	label:'客户类型',	sortable:true,align:'left',},
				{name:'applymandescr',	index:'applymandescr',	width:90,	label:'下单员',	sortable:true,align:'left',},
				{name:'address',	index:'address',	width:130,	label:'楼盘地址',	sortable:true,align:'left',},
				{name:'supdescr',	index:'supdescr',	width:90,	label:'供应商名称',	sortable:true,align:'left',},
				{name: 'amount', index: 'amount', width: 60, label: '金额', sortable: true, align: 'right', sum: true},
				{name: 'remainamount', index: 'remainamount', width: 100, label: '应付/付退余款', sortable: true, align: 'right', sum: true},
				{name: 'firstamount', index: 'firstamount', width: 90, label: '付/付退订金', sortable: true, align: 'left', sum: true},
				{name: 'secondamount', index: 'secondamount', width: 105, label: '付/付退到货款', sortable: true, align: 'right', sum: true},
				{name: 'projectamount', index: 'projectamount', width: 115, label: '项目经理结算总价', sortable: true, align: 'right'},
				{name: 'othercost', index: 'othercost', width: 75, label: '其他费用', sortable: true, align: 'right'},
				{name:'remarks',	index:'remarks',	width:290,	label:'备注',		sortable:true,align:'left',}, 
			 	{name: 'ischeckout', index: 'ischeckout', width: 80, label: '是否结账', sortable: true, align: 'left',hidden:true},
			 	{name: 'yesno1', index: 'yesno1', width: 80, label: '是否结账', sortable: true, align: 'left'},
				{name:'splstatusdescr', 	index:'splstatusdescr',		width:80,	label:'结算单状态',sortable:true,align:'left',},// hidden:true
				{name:'scstatusdescr', 	index:'scstatusdescr',		width:80,	label:'结算单状态',sortable:true,align:'left',hidden:true},// hidden:true
				{name: 'checkoutno', index: 'checkoutno', width: 100, label: '结账单号', sortable: true, align: 'left'},
				{name: 'payremark', index: 'payremark', width: 219, label: '记账说明', sortable: true, align: 'left'},
				{name: 'payno', index: 'payremark', width: 80, label: '付款单号', sortable: true, align: 'left'},
				{name: 'supstatusdescr', index: 'payremark', width: 80, label: '付款单状态', sortable: true, align: 'left'},
				{name: 'paydate', index: 'payremark', width: 80, label: '付款日期', sortable: true, align: 'left',formatter:formatDate},
				{name: 'sino', index: 'sino', width: 100, label: '销售单号', sortable: true, align: 'left'},
				{name:'confirmdate',	index:'confirmdate',	width:150,	label:'入库时间',		sortable:true,align:'left',formatter: formatTime}, 
				{name:'confirmczy',	index:'confirmczy',	width:70,	label:'入库人员',		sortable:true,align:'left',}, 
				{name: 'lastupdate', index: 'lastupdate', width: 140, label: '最后更新时间', sortable: true, align: 'left', formatter: formatTime},
				{name: 'lastupdatedby', index: 'lastupdatedby', width: 140, label: '最后更新人员', sortable: true, align: 'left'},
				{name: 'expired', index: 'expired', width: 100, label: '是否过期', sortable: true, align: 'left'},
				{name: 'actionlog', index: 'actionlog', width: 100, label: '操作日志', sortable: true, align: 'left'},
				{name: 'isappitem', index: 'isappitem', width: 100, label: 'isappitem', sortable: true, align: 'left',hidden:true},
			
			],
			ondblClickRow: function(){
				view();
			}
		});
		if('${costRight}'=='0'){
			jQuery("#dataTable").setGridParam().hideCol("amount").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("remainamount").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("secondamount").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("firstamount").trigger("reloadGrid");
			jQuery("#dataTable").setGridParam().hideCol("projectamount").trigger("reloadGrid");
		}
	
		//采购入库
		$("#save").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"采购单——增加",
				url:"${ctx}/admin/purchase/goSave",
				postData:{deliveType:'1',purType:''},
				height:800,
				width:1250,
				returnFun:goto_query
			});
		}); 
		//采购退回
		$("#returnSave").on("click",function(){
			Global.Dialog.showDialog("ReturnSave",{
				title:"采购退回——增加",
				url:"${ctx}/admin/purchase/goReturnSave",
				height:800,
				width:1250,
				returnFun:goto_query
			});
		});
		$("#whBack").on("click",function(){
			Global.Dialog.showDialog("WHBack",{
				title:"采购单——仓库直接退回",
				url:"${ctx}/admin/purchase/goWHBack",
				postData:{deliveType:'1',purType:''},
				height:800,
				width:1250,
				returnFun:goto_query
			});
		}); 
		//采购到工地
		$("#preSave").on("click",function(){
			Global.Dialog.showDialog("preSave",{
				title:"采购到工地——增加",
				url:"${ctx}/admin/purchase/goPreSave",
				postData:{deliveType:''},
				height:800,
				width:1050,
				returnFun:goto_query
			});
		});
			
		//工地退回
		$("#returnPreSave").on("click",function(){
			Global.Dialog.showDialog("ReturnPreSave",{
				title:"工地退回——增加",
				url:"${ctx}/admin/purchase/goReturnPreSave",
				postData:{deliveType:'',purType:'1'},
				height:800,
				width:1050,
				returnFun:goto_query
			});
		}); 
			
		//编辑
		$("#update").on("click",function(){
			var ret = selectDataTableRow();
			if(ret.sumarrivqty=='0'||ret.sumarrivqty==''){
				url="${ctx}/admin/purchase/goUpdate?id=";
			}else{
				url='${ctx}/admin/purchase/goPartUpdate?id=';
			}
         	if (ret) {	
				if ($.trim(ret.status)!='OPEN'){
					art.dialog({
						content: "采购单已经生效,不能再进行更改!"
					});
					return;
				} 
				Global.Dialog.showDialog("Update",{ 
              		title:"采购单——编辑",
              		url:url+ret.no,
              		height: 700,
              		width:1250,
              		returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		//采购费
		$("#purchFee").on("click",function(){
			var ret = selectDataTableRow();
         	if (ret) {	
				/* if ($.trim(ret.status)!='OPEN'){
					art.dialog({
						content: "采购单已经生效,不能再进行更改!"
					});
					return;
				}  */
				if ($.trim(ret.type)!='S'){
					art.dialog({
						content: "只有采购入库单才能录入采购费!"
					});
					return;
				} 
				/* Global.Dialog.showDialog("purchFee",{ 
              		title:"采购单——采购费用录入",
	              	url:"${ctx}/admin/purchase/goPurchFeeSave",
	              	postData:{no:ret.no},
              		height: 700,
              		width:850,
              		returnFun:goto_query
              	}); */
              	Global.Dialog.showDialog("purchFee",{ 
              		title:"采购单——采购费用录入",
	              	url:"${ctx}/admin/purchase/goPurchFeeList",
	              	postData:{no:ret.no},
              		height: 700,
              		width:850,
              		returnFun:goto_query
              	}); 
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
			
		//采购退回确认
		$("#purchReturnCheckOut").on("click",function(){
			var ret = selectDataTableRow();
			if(ret){
            	if ( $.trim(ret.status)!='OPEN'){
            		art.dialog({
            			content: "订单已生效，不能更改"
            		});
            		return;
            	}else{
					if($.trim(ret.type)!='R' || $.trim(ret.delivtype)!='1'){
						art.dialog({
							content:"只能对采购退回进行修改"
						});
						return;  
					}
            	}
             	Global.Dialog.showDialog("purchReturnCheckOut",{ 
	              	title:"采购退回--确认",
	              	url:"${ctx}/admin/purchase/goPurchReturnCheckOut?id="+ret.no,
	              	height: 700,
	              	width:1250,
	              	returnFun:goto_query
            	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
		
		//部分到货
		$("#partArrive").on("click",function(){
			var ret = selectDataTableRow();
				if (ret) {			
					if ($.trim(ret.type)!='S' || $.trim(ret.delivtype)!='1' || $.trim(ret.status)!='OPEN'){
						art.dialog({
            				content: "只有未全部到货的【采购入库单】才允许做部分到货"
            			});
            		return;
            	}   
            	Global.Dialog.showDialog("purchasePartArriv",{ 
	              	title:"部分到货编辑",
	              	url:"${ctx}/admin/purchase/goPartArrive?id="+ret.no,
	              	height: 700,
	              	width:1250,
	              	returnFun:goto_query
				});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
		});
			
		//全部到货 订单类型为采购   到货地点为仓库   订单状态为开   **  sumarrivqty
		$("#allArrive").on("click",function(){
			var ret = selectDataTableRow();
        	if (ret) {	
           		if ($.trim(ret.type)!='S'|| $.trim(ret.delivtype)!='1' || $.trim(ret.status)!='OPEN'|| $.trim(ret.sumarrivqty)!= 0 ){
           			art.dialog({
           				content: "只有未到货的【采购入库单】才允许做全部到货！"
           			});
           			return;
           		}   
            	Global.Dialog.showDialog("purchaseAllArriv",{
					title:"全部到货编辑",
              	  	url:"${ctx}/admin/purchase/goAllArrive?id="+ret.no,
             		height: 700,
             		width:1250,
             	  	returnFun:goto_query
             	});
			} else {
           		art.dialog({
       				content: "请选择一条记录"
       			});
           	}
		});
			
		//明细查询
		$("#detail").on("click",function(){
			Global.Dialog.showDialog("detail",{
				title:"明细查询",
				url:"${ctx}/admin/purchase/goDetail",
				height:770,
				width:1300,
			});
		});
		
		//采购跟踪
		$("#purchGZ").on("click",function(){
			Global.Dialog.showDialog("purchGZ",{
				title:"采购单跟踪",
				url:"${ctx}/admin/purchase/goPurchGZ",
				height:680,
				width:1200,
			});
		});
		
		$("#appItem").on("click",function(){
			Global.Dialog.showDialog("Save",{
				title:"采购单——订货",
				url:"${ctx}/admin/purchase/goAppItem",
				//postData:{deliveType:'1',purType:''},
				height:680,
				width:1250,
				returnFun:goto_query
			});
		});
		
		//到货单查询
		$("#arrDetail").on("click",function(){
			Global.Dialog.showDialog("arrDetail",{
				title:"到货单查询",
				url:"${ctx}/admin/purchase/goArrDetail",
				height:730,
				width:1150,
			});
		});
		
		$("#cancelItem").on("click",function(){
			var ret = selectDataTableRow();
			if($.trim(ret.isappitem)=='0'||$.trim(ret.status)!='OPEN'){
				art.dialog({
					content:"不是订货单状态或者采购单不是打开状态不能撤销订货",
				});
				return false;
			}		
			art.dialog({
				 content:"是否撤销",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/purchase/cancelAppItem",
						type:'post',
						data:{no:ret.no},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							art.dialog({
								content:'撤销成功',
							});
							$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						}
					});
				},
				cancel: function () {
						return true;
				}
		});	
	});
		
		
		//打印
		$("#print").on("click",function(){
			var ret = selectDataTableRow();
			Global.Dialog.showDialog("print",{
				title:"采购单——打印",
				url:"${ctx}/admin/purchase/goPrint?no="+ret.no,
				height:320,
				width:500,
			});
		});
		onCollapse(0);
	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_supplier_supplier").val('');
		$("#openComponent_salesInvoice_sino").val('');
		$("#openComponent_item_itCode").val('');
	
		$("#page_form select").val('');
		$("#splStatusDescr_NAME").val('');
		$("#status_NAME").val('');
		$("#splStatusDescr").val('');
		$("#status").val('');
		$("#dateFrom1").val('');
		$("#arriveDateTo").val('');
		$("#arriveDateFrom").val('');
		$("#dateTo1").val('');
		$("#no").val('');
		$("#itCode").val('');
		$("#sino").val('');
		$("#supplier").val('');
		$("#remarks").val('');
		$("#address").val('');
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_splStatusDescr").checkAllNodes(false);
		
		var treeObj = $.fn.zTree.getZTreeObj("tree_status");
		treeObj.refresh();
	} 
	
	function view(){
		var ret = selectDataTableRow();
	 	if(ret){
		 	var tit;
		 	if(ret.type=='R'){
		 		if(ret.delivtype=='2'){
					tit="工地退回——查看"
		 		}else{
			 		tit="采购退回——查看"
		 		}
		 	}else{
		 		if(ret.delivtype=='2'){
		 			tit="采购到工地——查看"
		 		} else {
			 		tit="采购单——查看"
		 		}
		 	}
		 	
		 	if(ret.delivtype=='1'){
		 		url='${ctx}/admin/purchase/goViewNew?id=';
		 	}else{
		 		url='${ctx}/admin/purchase/goViewNew2?id=';
		 	}
		 	
			Global.Dialog.showDialog("PurchaseView",{
				title:tit,
				url: url + ret.no,
				height:730,
				width:1250,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	}
	
	function viewPurchFee(){
		Global.Dialog.showDialog("viewPurchFee",{
			title:"采购费查询",
	 		url:"${ctx}/admin/purchase/goViewPurchFee",
			height:770,
			width:1250,
		});
	}
	
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="costRight" name="costRight" value="${costRight}" />
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>采购单号</label>
							<input type="text" id="no" name="no" style="width:160px;" value="${purchase.no }"/>
						</li>
						<li>
							<label>供应商</label>
							<input type="text" id="supplier" name="supplier" style="width:160px;" value="${purchase.supplier }"/>
						</li>
						<li>
							<label>采购单状态</label>
							<house:xtdmMulit id="status" dictCode="PURCHSTATUS" selectedValue="${purchase.status}"></house:xtdmMulit>                     
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address"  style="width:160px;" value="${purchase.address }" />
						</li>
						
						<li>
							<label>采购类型</label>
							<house:xtdm id="type" dictCode="PURCHTYPE"  style="width:160px;" ></house:xtdm>
						</li>
						<li>
							<label>是否结账</label>
							<house:xtdm id="isCheckOut" dictCode="YESNO"  style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" style="width: 160px;"  ></select>
						</li>
						<li>
							<label>材料类型2</label>
								<select id="itemType2" name="itemType2" style="width: 160px;" ></select>
						</li>
						<li id="loadMore" >
							<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
						<div  class="collapse " id="collapse" >
							<ul>
								<li>
									<label>销售单号</label>
									<input type="text" id="sino" name="sino"  style="width:160px;" value="${purchase.sino }"/>
								</li>
								<li>
									<label>销售类型</label>
									<select id="saleType" name="saleType" style="width: 160px;" value="${purchase.saleType }">
										<option value="">请选择...</option>
										<option value="1">1 领料生成</option>
										<option value="2">2 直接开单</option>
									</select>
								</li>
								<li>
									<label>材料编号</label>
									<input type="text" id="itCode" name="itCode"  style="width:160px;" value="${purchase.itCode }" />
								</li>
								<li>
									<label>配送方式</label>
									<house:xtdm  id="delivType" dictCode="PURCHDELIVTYPE"   style="width:160px;" value="${purchase.delivType}"></house:xtdm>
								</li>
								<li>
									<label>采购日期</label>
									<input type="text" id="dateFrom1" name="dateFrom1" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo1" name="dateTo1" 	 class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
								</li>
								<li>
									<label>入库日期</label>
									<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
								</li>
								<li>
									<label>订货时间从</label>
									<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
								</li>
								<li>
									<label>至</label>
									<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
								</li>
								<li>
									<label>订货人</label>
									<input type="text" id="appItemMan" name="appItemMan"  style="width:160px;" value="${purchase.appItemMan }"/>
								</li>
								<li>
									<label>是否服务产品</label>
									<house:xtdm  id="isService" dictCode="YESNO"   style="width:160px;" value="${purchase.isService}"></house:xtdm>
								</li>
								<li>
									<label>结算单状态</label>
									<house:xtdmMulit id="splStatusDescr" dictCode="SPLCKOTSTATUS" selectedValue="${purchase.splStatusDescr}"></house:xtdmMulit>                     
								</li>
								<li >
									<label class="control-textarea"> 备注</label>
									<textarea id="remarks" name="remarks" rows="2">${purchase.remarks }</textarea>
								</li>
								<li>
									<label>仓库编号</label>
									<input type="text" id="whcode" name="whcode"  style="width:160px;" value="${purchase.whcode }" />
								</li>
								<li>
                                    <label>下单员</label>
                                    <input type="text" id="applyMan" name="applyMan"/>
                                </li>
								<li class="search-group-shrink" >
									<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse" >收起</button>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
								</li>
							</ul>
						</div>
					</ul>		
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<!-- panelBar -->
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<house:authorize authCode="PURCHASE_SAVE">
						<button type="button" class="btn btn-system "  id="save">
							<span>采购入库</span>
						</button>
					</house:authorize>	
					<house:authorize authCode="PURCHASE_BACK">
						<button type="button" class="btn btn-system "  id="returnSave">
							<span>采购退回</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_WHBACK">
						<button type="button" class="btn btn-system "  id="whBack">
							<span>仓库直接退回</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_EDIT">
						<button type="button" class="btn btn-system "  id="update">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_PURCHFEE">
						<button type="button" class="btn btn-system " id="purchFee">
							<span>采购费用录入</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_BACKCONFIRM">
						<button type="button" class="btn btn-system "  id="purchReturnCheckOut">
							<span>采购退回确认</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_PART">
						<button type="button" class="btn btn-system "  id="partArrive">
							<span>部分到货</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_ALL">
						<button type="button" class="btn btn-system " id="allArrive"><span>全部到货</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()" id="view">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_VIEWPURCHFEE">
						<button type="button" class="btn btn-system "  onclick="viewPurchFee()" id="viewPurchFee">
							<span>采购费用查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_MXVIEW">
						<button type="button" class="btn btn-system "  id="detail">
							<span>明细查询</span>
						</button>	
					</house:authorize>
					<house:authorize authCode="PURCHASE_CGGZ">
						<button type="button" class="btn btn-system "  id="purchGZ">
							<span>采购跟踪</span>
						</button>	
					</house:authorize>
					<house:authorize authCode="PURCHASE_DHDVIEW">
						<button type="button" class="btn btn-system " id="arrDetail">
							<span>到货明细查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_APPITEM">
						<button type="button" class="btn btn-system " id="appItem">
							<span>订货</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_CANCELITEM">
						<button type="button" class="btn btn-system " id="cancelItem">
							<span>撤销订货</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PURCHASE_EXCEL">
						<button type="button" class="btn btn-system "  onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>	
					</house:authorize>
					<house:authorize authCode="PURCHASE_PRINT">
						<button type="button" class="btn btn-system "  id="print" >
							<span>打印</span>
						</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>	
</html>
