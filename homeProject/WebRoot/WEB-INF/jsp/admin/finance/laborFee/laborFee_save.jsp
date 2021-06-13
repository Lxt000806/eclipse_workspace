<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>人工费用管理-新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
	var outItemType1="", actName="";
	$(function(){
		function getData(data){
			if(!data){
				return false;	
			}
			$("#cardID").val(data.CardID);
			
		}
		$("#appCZY").openComponent_employee({showValue:"${laborFee.appCZY}",showLabel:"${appDescr}",readonly:true });
		$("#actName").openComponent_workCard({callBack:getData});
		$("#status_NAME").attr("disabled","disabled");
	});
	//新增是否允许重复
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		var gridOption = {
			url:"${ctx}/admin/laborFee/goDetailJqGrid",
			postData:{no:"*****"},
			height:$(document).height()-$("#content-list").offset().top-360,
			rowNum:10000000,
			styleUI: "Bootstrap",
			sortable: true, 
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "custcode", index: "custcode", width: 65, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 112, label: "楼盘地址", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 60, label: "档案号", sortable: true, align: "left"},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left"},
				{name: "custcheckdate", index: "custcheckdate", width: 120, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
				{name: "feetypedescr", index: "feetypedescr", width: 75, label: "费用类型", sortable: true, align: "left"},
				{name: "feetype", index: "feetype", width: 75, label: "费用类型", sortable: true, align: "left",hidden:true},
				{name: "appsendno", index: "appsendno", width: 83, label: "送货单号", sortable: true, align: "left"},
				{name: "issetitem", index: "issetitem", width: 75, label: "是否套餐", sortable: true, align: "left",hidden:true},
				{name: "issetitemdescr", index: "issetitemdescr", width: 75, label: "是否套餐", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 75, label: "领料单号", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 65, label: "金额", sortable: true, align: "right", sum: true,formatter:DiyFmatter},
				{name: "amount1", index: "amount1", width: 60, label: "配送费", sortable: true, align: "right",hidden:true},
				{name: "amount2", index: "amount2", width:65, label: "配送费（自动生成）", sortable: true, align: "right",hidden:true},
				{name: "hadamount", index: "hadamount", width: 80, label: "已报销金额", sortable: true, align: "right", sum: true},
				{name: "sendnohaveamount", index: "sendnohaveamount", width: 80, label: "本单已报销", sortable: true, align: "right"},
				{name: "actname", index: "actname", width: 65, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 119, label: "卡号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 168, label: "备注", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "片区", sortable: true, align: "left"},
				{name: "workercode", index: "workercode", width: 100, label: "工人编号", sortable: true, align: "right",hidden:true},
				{name: "custworkpk", index: "custworkpk", width: 100, label: "工地工人pk", sortable: true, align: "right",hidden:true},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 75, label: "最后更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "refcustcode", index: "refcustcode", width: 100, label: "关联客户编号", sortable: true, align: "left", hidden: true},
				{name: "refcustaddress", index: "refcustaddress", width: 150, label: "关联楼盘", sortable: true, align: "left"},
				{name: "faulttype", index: "faulttype", width: 150, label: "faulttype", sortable: true, align: "left",hidden:true},
				{name: "faulttypedescr", index: "faulttypedescr", width: 90, label: "责任人类型", sortable: true, align: "left"},
				{name: "faultman", index: "faultman", width: 150, label: "faultman", sortable: true, align: "left",hidden:true},
				{name: "faultmandescr", index: "faultmandescr", width: 80, label: "责任人", sortable: true, align: "left"},
				{name: "prjqualityfee", index: "prjqualityfee", width: 150, label: "项目经理质保金余额", sortable: true, align: "right"},
				{name: "projectman", index: "projectman", width: 150, label: "项目经理", sortable: true, align: "right",hidden:true},
				{name: "projectmandescr", index: "projectmandescr", width: 150, label: "项目经理", sortable: true, align: "right",hidden:true},
				{name: "cutcheckoutno", index: "cutcheckoutno", width: 100, label: "加工批次号", sortable: true, align: "left"},
			],
			loadonce: true,
		};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
		function DiyFmatter (cellvalue, options, rowObject){ 
		    return myRound(cellvalue,2);
		}
	});
	
	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var Ids =$("#dataTable").getDataIDs();
		var accountIds =$("#dataTable_account").getDataIDs();
		var actName=$.trim($("#actName").val());
		var cardID=$.trim($("#cardID").val());
		var actNameList = Global.JqGrid.allToJson("dataTable","actname");
			actNameArr = actNameList.fieldJson.split(",");
	 		if(Ids==""||Ids==null){
				art.dialog({
					content:"明细无数据,保存无意义!",
				});
				return;
			}
		var acrNameInput=$.trim($("#openComponent_workCard_actName").val());
		if(actName=="" && acrNameInput!=""){
			actName=acrNameInput;
			$("#actName").val(acrNameInput);
		}
		
		if((actName==""||cardID=="") && (accountIds== "" || accountIds == null)){
			art.dialog({
				content:"主表和收款账号表必须有一处填入卡号和户名",
			});
			return;
		}
		$("#itemType1").removeAttr("disabled","disabled");
		var param= Global.JqGrid.allToJson("dataTable",null,null,true);
		var detailParam= Global.JqGrid.allToJson("dataTable_account");
		param["accountDetailJson"] = detailParam["detailJson"];
		
		Global.Form.submit("dataForm","${ctx}/admin/laborFee/doSave",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
				art.dialog({
					content:ret.msg,
					width:200
				});
			}
		});
	};
	//新增
	function add(){
		var ret= selectDataTableRow("dataTable");
		var itemType1 = $.trim($("#itemType1").val());
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1！",width: 200});
			return false;
		}
		Global.Dialog.showDialog("saveAdd",{
			title:"人工费用管理——增加",
			url:"${ctx}/admin/laborFee/goAdd",
			postData:{itemType1:itemType1},
			height: 480,
			width:800,
			returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							custcode:v.custCode,
							address:v.address,
							documentno:v.documentNo,
							checkstatusdescr:v.checkStatus,
							custcheckdate:v.checkDate,
							feetypedescr:v.feeTypeDescr,
							feetype:v.feeType,
							appsendno:v.appSendNo,
							iano:v.iaNo,
							amount:v.amount,
							amount1:0,
							amount2:0,
							issetitem:v.isSetItem,
							issetitemdescr:v.isSetItem==""?"":(v.isSetItem=="0"?"否":"是"),
							hadamount:v.sendNoHaveAmount,
							sendnohaveamount:v.haveAmount,
							actname:v.actName,
							cardid:v.cardID,
							remarks:v.detailRemarks,
							lastupdate:new Date(),
							lastupdatedby:"${laborFee.appCZY}",
							actionlog:"Add",
							expired:"F",
							refcustcode:v.refCustCode,
							refcustaddress: v.refCustAddress,
							faulttype:v.faultType,
							faultman:v.faultMan,
							faulttypedescr:v.faultTypeDescr,
							faultmandescr:v.faultManDescr,
							prjqualityfee:v.prjQualityFee,
							prjectman:v.projectMan,
							prjectmandescr:v.projectManDescr,
							regiondescr: v.regionDescr
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					outItemType1=$("#itemType1").val();
					$("#itemType1").attr("disabled","disabled");
				 }
			} 
		});
	};
	//编辑
	function update(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		$("#itemType1").removeAttr("disabled","disabled");
		var itemType1 = $.trim($("#itemType1").val());
	 	if(ret){
			Global.Dialog.showDialog("SaveUpdate",{
				title:"人工费用管理——编辑",
				url:"${ctx}/admin/laborFee/goAddUpdate",
				postData:{custCode:ret.custcode,
						actName:ret.actname,
						cardID:ret.cardid,
						feeType:ret.feetype,
						amount:ret.amount,
						appSendNo:ret.appsendno,
						itemType1:itemType1,
						detailRemarks:ret.remarks,
						haveAmount:ret.hadamount,
						sendNoHaveAmount:ret.sendnohaveamount,
						iaNo:ret.iano,
						isSetItem:ret.issetitem,
						refCustCode:ret.refcustcode,
						refCustAddress: ret.refcustaddress,
						faultType:ret.faulttype,
						faultTypeDescr:ret.faulttypedescr,
						faultMan:ret.faultman,
						faultManDescr:ret.faultmandescr,
						prjQualityFee:ret.prjqualityfee,
						projectMan:ret.projectman,
						refCustCode: ret.refcustcode,
						projectManDescr:ret.projectmandescr,
						cutCheckOutDTPK:ret.cutcheckoutdtpk,
						regionDescr: ret.regiondescr
				},
				height:480,          
				width:800,
				returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							custcode:v.custCode,
							address:v.address,
							documentno:v.documentNo,
							checkstatusdescr:v.checkStatus,
							custcheckdate:v.checkDate,
							feetypedescr:v.feeTypeDescr,
							feetype:v.feeType,
							appsendno:v.fromAfterQty,
							iano:v.toAfterQty,
							amount:v.amount,
							amount1:0,
							amount2:0,
							issetitem:v.isSetItem,
							issetitemdescr:v.isSetItem==""?"":(v.isSetItem=="0"?"否":"是"),
							hadamount:v.sendNoHaveAmount,
							sendnohaveamount:v.haveAmount,
							actname:v.actName,
							cardid:v.cardID,
							remarks:v.detailRemarks,
							lastupdate:new Date(),
							lastupdatedby:"${laborFee.appCZY}",
							actionlog:"Add",
							expired:"F",
							refcustcode:v.refCustCode,
							refcustaddress: v.refCustAddress,
							faulttype:v.faultType,
							faultman:v.faultMan,
							faulttypedescr:v.faultTypeDescr,
							faultmandescr:v.faultManDescr,
							prjqualityfee:v.prjQualityFee,
							prjectman:v.projectMan,
							prjectmandescr:v.projectManDescr,
							cutcheckoutdtpk:v.cutCheckOutDTPK,
							regiondescr: v.regionDescr
						  };
	  						$("#dataTable").setRowData(rowId,json);
					  });
					$("#itemType1").attr("disabled","disabled");
				  	
				  	var amountList = Global.JqGrid.allToJson("dataTable","amount");
				  	var sum = 0;
				  	if(amountList.keys.length>0){
				  		console.log(2);
				  		var values = amountList.keys;
					  	for(var i = 0; i <values.length; i++){
					  		sum += myRound(values[i], 4);
					  	}
					  	$("#dataTable").footerData("set",{"amount":myRound(sum,4)},false);		
				  	}
				  }
			  } 
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}
	};  
	
	//删除
	function del(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除该工人费用单",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				var Ids =$("#dataTable").getDataIDs();
				if(Ids==0){
					$("#itemType1").removeAttr("disabled","disabled");
				}
			},
			cancel: function () {
					return true;
			}
		});
	};
	
	function excelImport(){
		var ret= selectDataTableRow("dataTable");
		var itemType1 = $.trim($("#itemType1").val());
		if(itemType1 ==""){
			art.dialog({content: "请选择材料类型1",width: 200});
			return false;
		}
		Global.Dialog.showDialog("excelImport",{
			title:"人工费用管理——excel导入",
			url:"${ctx}/admin/laborFee/goExcelImport",
			postData:{itemType1:itemType1},
			height: 600,
			width:1350,
			returnFun:function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							custcode:v.custcode,
							address:v.address,
							documentno:v.documentno,
							checkstatusdescr:v.checkstatusdescr,
							custcheckdate:v.custcheckdate,
							feetypedescr:v.feetypedescr,
							feetype:v.feetype,
							appsendno:v.appsendno,
							iano:v.iano,
							amount:v.amount,
							amount1:0,
							amount2:0,
							hadamount:v.sendnohaveamount,
							sendnohaveamount:v.hadamount,
							actname:v.actname,
							cardid:v.cardid,
							remarks:v.remarks,
							lastupdate:new Date(),
							lastupdatedby:"${laborFee.appCZY}",
							actionlog:"ADD",
							expired:"F",
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					outItemType1=$("#itemType1").val();
					$("#itemType1").attr("disabled","disabled");
				 }
			} 
		});
	};
	
	//查看
	function view(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	 	if(ret){
			Global.Dialog.showDialog("addView",{
				title:"人工费用管理——查看",
				url:"${ctx}/admin/laborFee/goAddView",
				postData:{custCode:ret.custcode,
						actName:ret.actname,
						cardID:ret.cardid,
						feeType:ret.feetype,
						amount:ret.amount,
						appSendNo:ret.appsendno,
						itemType1:itemType1,
						detailRemarks:ret.remarks,
						haveAmount:ret.hadamount,
						sendNoHaveAmount:ret.sendnohaveamount,
						iaNo:ret.iano,
						refCustCode:ret.refcustcode,
						faultType:ret.faulttype,
						faultTypeDescr:ret.faulttypedescr,
						faultMan:ret.faultman,
						faultManDescr:ret.faultmandescr,
						prjQualityFee:ret.prjqualityfee,
						projectMan:ret.projectman,
						projectManDescr:ret.projectmandescr
				},
				height:480,          
				width:800,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	
	function viewSendDetail(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	 	if(ret){
			Global.Dialog.showDialog("viewSendDetail",{
				title:"发货明细查看",
				url:"${ctx}/admin/laborFee/goViewSendDetail",
				postData:{appSendNo:ret.appsendno},
				height:550,          
				width:900,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	
	function viewCutDetail(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		if(ret.feetype!="10"){
			art.dialog({
				content:"费用类型为瓷砖加工费可操作"
			});	
			return;
		}
	 	if(ret){
			Global.Dialog.showDialog("viewCutDetail",{
				title:"查看加工明细",
				url:"${ctx}/admin/laborFee/goViewCutDetail",
				postData:{cutCheckOutNo:ret.cutcheckoutno,iano:ret.iano},
				height:600,          
				width:1200,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	
	function viewItemReq(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
	 	if(ret){
			Global.Dialog.showDialog("viewItemReq",{
				title:"材料需求查看",
				url:"${ctx}/admin/laborFee/goViewItemReq",
				postData:{custCode:ret.custcode,itemType1:$.trim($("#itemType1").val())},
				height:680,          
				width:1000,
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});		
		}	 
	};
	//配送费导入
	function sendFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
					content:"请选择材料类型1！"
			});	
			return;
		}
		
		var nos = $("#dataTable").getCol("appsendno", false).join(",");
		var ret=$("#dataTable").jqGrid('getRowData',1);
		Global.Dialog.showDialog("sendFeeImport", {
			title : "人工费用管理——搬运费导入",
			url : "${ctx}/admin/laborFee/sendFeeImport",
			postData : {
				'nos':nos,
				'itemType1':itemType1
			},
 			height:620,
			width:1300,
			returnFun:function(data){
					if(data.length > 0){
						var ids = $("#dataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							rowData.lastupdate=new Date();
							rowData.lastupdatedby="${laborFee.appCZY}",
							rowData.actionlog="ADD",
							rowData.expired="F",
							$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});	
						$("#itemType1").attr("disabled","disabled");
					}
			}
		});
	}
	
	function intFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
					content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="JC"){
			art.dialog({
				content:"只有集成才能进行集成安装费导入！"
			});		
			return;
		}
	    var jq = Global.JqGrid.allToJson("dataTable").datas;
		var ret=$("#dataTable").jqGrid('getRowData',1);
		var nos="";
		var custCodes="";
		var jcazbtCustCodes="";
		var jcazjfCustCodes="";
		var cgazbtCustCodes="";
		var cgazjfCustCodes="";
		for(var i=0;i<jq.length;i++){
			if(jq[i].feetype.toLowerCase()=="cgazf"){
				custCodes=custCodes+jq[i].custcode+",";
			}
			if(jq[i].feetype.toLowerCase()=="jcazf"){
				nos=nos+jq[i].custcode+",";
			}
			if(jq[i].feetype.toLowerCase()=="jcazbt" ){
				jcazbtCustCodes=jcazbtCustCodes+jq[i].custcode+",";
			}
			if(jq[i].feetype.toLowerCase()=="cgazbt"){
				cgazbtCustCodes=cgazbtCustCodes+jq[i].custcode+",";
			}
			if(jq[i].feetype.toLowerCase()=="jcazjf"){
				jcazjfCustCodes=jcazjfCustCodes+jq[i].custcode+",";
			}
			if(jq[i].feetype.toLowerCase()=="cgazjf"){
				cgazjfCustCodes=cgazjfCustCodes+jq[i].custcode+",";
			}
		}
		Global.Dialog.showDialog("intFeeImport", {
			title : "人工费用管理——导入集成安装费",
			url : "${ctx}/admin/laborFee/goIntFeeImport",
			postData : {
				'nos':nos,
				"custCodes":custCodes,
				"jcazbtCustCodes":jcazbtCustCodes,
				"jcazjfCustCodes":jcazjfCustCodes,
				"cgazbtCustCodes":cgazbtCustCodes,
				"cgazjfCustCodes":cgazjfCustCodes
			},
 			height:620,
			width:1300,
			returnFun:function(data){
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}",
						rowData.actionlog="ADD",
						rowData.expired="F",
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	// 导入浴室柜安装费
	function bathFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
				content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="ZC"){
			art.dialog({
				content:"只有主材才能进行浴室柜安装费导入！"
			});		
			return;
		}
		var jq = Global.JqGrid.allToJson("dataTable").datas;
		var ret=$("#dataTable").jqGrid('getRowData',1);
		var custCodes="";
		for(var i=0;i<jq.length;i++){
			if(jq[i].feetype.toLowerCase()=="ysgazf"){
				custCodes=custCodes+jq[i].custcode+",";
			}
		}
		Global.Dialog.showDialog("cupFeeImport", {
			title : "人工费用管理——导入浴室柜安装费",
			url : "${ctx}/admin/laborFee/goBathFeeImport",
			postData : {
				"custCodes":custCodes,
			},
			height:620,
			width:1100,
			returnFun:function(data){
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						/*rowData.appsendno=rowData.no;*/
						rowData.appsendno=rowData.appsendno;
						rowData.confirmDate=rowData.paydate;
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}";
						rowData.actionlog="ADD";
						rowData.expired="F";
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	// 导入窗帘灯具搬运费
	function transFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
				content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="RZ"){
			art.dialog({
				content:"只有软装才能进行窗帘灯具搬运费导入！！"
			});		
			return;
		}
		var jq = Global.JqGrid.allToJson("dataTable").datas;
		var ret=$("#dataTable").jqGrid('getRowData',1);
		var custCodes="";
		for(var i=0;i<jq.length;i++){
			if(jq[i].feetype=="63" || jq[i].feetype=="64" || jq[i].feetype=="65"){
				custCodes=custCodes+jq[i].custcode+",";
			}
		}
		Global.Dialog.showDialog("TransImport", {
			title : "人工费用管理——导入窗帘灯具搬运费",
			url : "${ctx}/admin/laborFee/goTransImport",
			postData : {
				"custCodes":custCodes
			},
			height:620,
			width:1100,
			returnFun:function(data){
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}";
						rowData.actionlog="ADD";
						rowData.expired="F";
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	function installFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
					content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="RZ"){
			art.dialog({
				content:"只有软装才能进行窗帘灯具安装费导入！"
			});		
			return;
		}
	    var jq = Global.JqGrid.allToJson("dataTable").datas;
		var ret=$("#dataTable").jqGrid('getRowData',1);
		var nos="";
		var custCodes="";
		for(var i=0;i<jq.length;i++){
			if((jq[i].feetype=="15" || jq[i].feetype=="22")){
				custCodes=custCodes+jq[i].appsendno+",";
				nos=nos+jq[i].appsendno+",";
			}
		}
		Global.Dialog.showDialog("inStallFeeImport", {
			title : "人工费用管理——导入窗帘灯具安装费",
			url : "${ctx}/admin/laborFee/goInStallFeeImport",
			postData : {
				'nos':nos,
				"custCodes":custCodes
			},
 			height:620,
			width:1300,
			returnFun:function(data){
				console.log(data);
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}",
						rowData.actionlog="ADD",
						rowData.expired="F",
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	//仓库装卸费导入
	function whInstallFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
					content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="ZC"){
			art.dialog({
				content:"只有主材才能导入仓库装卸费！"
			});		
			return;
		}
	    var jq = Global.JqGrid.allToJson("dataTable").datas;
		var ret=$("#dataTable").jqGrid('getRowData',1);
		var nos="";
		for(var i=0;i<jq.length;i++){
			if((jq[i].feetype=="04")){
				nos=nos+jq[i].appsendno+",";
			}
		}
		Global.Dialog.showDialog("whInstallFeeImport", {
			title : "人工费用管理——导入仓库装卸费",
			url : "${ctx}/admin/laborFee/goWhInstallFeeImport",
			postData : {
				'nos':nos,
			},
 			height:620,
			width:1300,
			returnFun:function(data){
				console.log(data);
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}",
						rowData.actionlog="ADD",
						rowData.expired="F",
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	
	function tileCutFeeImport(){
		var itemType1=$("#itemType1").val();
		if(itemType1 ==""){
			art.dialog({
					content:"请选择材料类型1！"
			});	
			return;
		}
		if(itemType1!="ZC"){
			art.dialog({
				content:"只有主材才能导入瓷砖加工费！"
			});		
			return;
		}
	    var jq = Global.JqGrid.allToJson("dataTable").datas;
		var nos = [];
		
		// 导入瓷砖加工费时，批次号与领料单号要同时限制
		// 格式 CCO0000033_IA00703925
		for (var i = 0; i < jq.length; i++) {
			if ((jq[i].feetype == "10")) {
				nos.push(jq[i].cutcheckoutno.trim() + "_" + jq[i].iano.trim());
			}
		}
		Global.Dialog.showDialog("tileCutFeeImport", {
			title : "人工费用管理——导入瓷砖加工费",
			url : "${ctx}/admin/laborFee/goTileCutFeeImport",
			postData : {
				'nos': nos.join(","),
			},
 			height:620,
			width:1300,
			returnFun:function(data){
				console.log(data);
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdate=new Date();
						rowData.lastupdatedby="${laborFee.appCZY}",
						rowData.actionlog="ADD",
						rowData.expired="F",
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	
	function changeItemType1(){
		var itemType1=$("#itemType1").val();
		if(itemType1=="ZC"){
			$("#sendFeeImport,#tileCutFeeImport,#bathFeeImport,#whInstallFeeImport").removeClass("hidden");
			$("#transFeeImport,#installFeeImport,#intFeeImport").addClass("hidden");
		}else if(itemType1=="JC"){
			$("#intFeeImport,#sendFeeImport").removeClass("hidden");
			$("#tileCutFeeImport,#bathFeeImport,#transFeeImport,#installFeeImport,#whInstallFeeImport").addClass("hidden");
		}else if(itemType1=="RZ"){
			$("#transFeeImport,#installFeeImport").removeClass("hidden");
			$("#sendFeeImport,#tileCutFeeImport,#bathFeeImport,#intFeeImport,#whInstallFeeImport").addClass("hidden");
		}else{
			$("#sendFeeImport,#tileCutFeeImport,#bathFeeImport,#transFeeImport,#installFeeImport,#intFeeImport,#whInstallFeeImport").addClass("hidden");
		}
	}
	
	function setExcel(name, id){
		if(id == "dataTable_account"){
			$("#mainBtn").hide();
			$("#accountBtn").show();	
		} else {
			$("#mainBtn").show();
			$("#accountBtn").hide();	
		}
	}
	
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
 						<button type="button" class="btn btn-system" style="color:#777777; cursor:default">
 							<span>审核通过</span>
 						</button>	
 						<button type="button" class="btn btn-system" style="color:#777777; cursor:default">
 							<span>审核取消</span>
 						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="edit-form">
				<div class="panel panel-info">  
	         		<div class="panel-body">
	         			<form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
							<input type="hidden" name="jsonString" value=""/>
							<ul class="ul-form">
								<li>
									<label>单号</label>
									<input type="text" id="No" name="No" style="width:160px;" placeholder="保存自动生成" value="${laborFee.no }" readonly="readonly"/>                                             
								</li>
								<li>
									<label>状态</label>
									<house:xtdmMulit id="status" dictCode="BALADJSTATUS" selectedValue="${laborFee.status}"></house:xtdmMulit>
								</li>
								<li>
									<label>申请日期</label>
									<input type="text" style="width:160px;" id="date" name="date" class="i-date" value="<fmt:formatDate value='${laborFee.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>申请人</label>
									<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${laborFee.appCZY }"/>
								</li>
								<li>
									<label><span class="required">*</span>材料类型1</label>
									<select id="itemType1" name="itemType1" style="width: 160px;" onchange="changeItemType1()"></select>
								</li>
								<li>
									<label><span class="required">*</span>开户行</label>
									<input type="text" id="bank" name="bank" style="width:160px;" value="${laborFee.bank }" />                                             
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>户名</label>
									<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFee.actName }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>卡号</label>
									<input type="text" id="cardID" name="cardID" style="width:160px;" value="${laborFee.cardID }"/>
								</li>
								<li>
									<label>审核人</label>
									<input type="text" id="confirmCZY" name="confirmCZY" style="width:160px;" value="${laborFee.confirmCZY}" disabled="true"/>
								</li>
								<li>
									<label>审核日期</label>
									<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${laborFee.confirmDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>付款人</label>
									<input type="text" id="payCZY" name="payCZY" style="width:160px;" value="${laborFee.payCZY}" disabled="true"/>
								</li>
								<li>
									<label>付款日期</label>
									<input type="text" style="width:160px;" id="payDate" name="payDate" class="i-date" value="<fmt:formatDate value='${laborFee.payDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
								</li>
								<li>
									<label>凭证号</label>
									<input type="text" id="documentNo" name="documentNo" style="width:160px;" value="${laborFee.documentNo}" disabled="true"/>
								</li>
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" style="width:160px">${laborFee.remarks }</textarea>
								</li>
							</ul>	
						</form>
					</div>
				</div>
			</div> <!-- edit-form end -->
			<div class="btn-panel">
	    		<div class="btn-group-sm" id="mainBtn">
					<button type="button" class="funBtn funBtn-system" id="add" onclick="add()">
						<span>新增</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="update" onclick="update()">
						<span>编辑</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="delete" onclick="del()">
						<span>删除</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="excelImport" onclick="excelImport()">
						<span>从excel导入</span>
					</button>
					<button type="button" class="funBtn funBtn-system" onclick="doExcelNow('人工费用明细表','dataTable','dataForm')" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="viewSendDetail" onclick="viewSendDetail()">
						<span>查看发货明细</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="viewItemReq" onclick="viewItemReq()">
						<span>查看材料需求</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="viewCutDetail" onclick="viewCutDetail()">
						<span>查看加工明细</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="sendFeeImport" onclick="sendFeeImport()">
						<span>配送费导入</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="tileCutFeeImport" onclick="tileCutFeeImport()">
						<span>导入瓷砖加工费</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="bathFeeImport" onclick="bathFeeImport()">
						<span>导入浴室柜安装费</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="whInstallFeeImport" onclick="whInstallFeeImport()">
						<span>导入仓库装卸费</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="transFeeImport" onclick="transFeeImport()">
						<span>导入窗帘灯具搬运费</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="installFeeImport" onclick="installFeeImport()">
						<span>导入窗帘灯具安装费</span>
					</button>
					<button type="button" class="funBtn funBtn-system hidden" id="intFeeImport" onclick="intFeeImport()">
						<span>导入集成安装费</span>
					</button>
				</div>
				
	    		<div class="btn-group-sm" id="accountBtn" hidden="true">
					<button type="button" class="funBtn funBtn-system" onclick="addAccount()">
						<span>新增</span>
					</button>
					<button type="button" class="funBtn funBtn-system" onclick="dtlImpAccount()">
						<span>从明细导入</span>
					</button>
					<button type="button" class="funBtn funBtn-system" onclick="updateAccount()">
						<span>编辑</span>
					</button>
					<button type="button" class="funBtn funBtn-system" id="delRow" onclick="delRow()">
						<span>删除</span>
					</button>
				</div>
			</div>		
			<ul class="nav nav-tabs">
		    	<li class="active">
		      		<a href="#content-list1" data-toggle="tab" onclick="setExcel('人工费用明细表','dataTable')">人工费用明细</a>
		      	</li>
		      	<li class="">
		      		<a href="#tab_account" data-toggle="tab" onclick="setExcel('收款账号','dataTable_account')">收款账号</a>
		      	</li>
		   	</ul>	
		   	<div class="tab-content">  
				<div id="content-list1" class="tab-pane fade in active">
					<table id="dataTable"></table>
				</div>	
		        <div id="tab_account"  class="tab-pane fade "> 
		         	<jsp:include page="tab_account.jsp"></jsp:include>
		        </div> 
		    </div>		
		</div>
	</div>
</body>
</html>
