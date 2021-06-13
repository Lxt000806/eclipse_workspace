<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//控制checkbox是否可编辑
	var setIsConfirm=true;
	var button='${workCost.button}';
	if(button=="examine"){
		setIsConfirm=false;
	}
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/workCostDetail/goJqGrid",
			postData:{no:"${workCost.no}",button:button},
		    rowNum:10000000,
			height:260,
			sortable:true,
			styleUI: 'Bootstrap', 
			searchBtn:true,
			colModel : [
				{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left",hidden:true},
				{name: "salarytype", index: "salarytype", width: 50, label: "工资类型", sortable: true, align: "left", hidden: true},
				{name: "no", index: "no", width: 100, label: "批次号", sortable: true, align: "left", hidden: true},
				{name: "checkStatus", index: "checkStatus", width: 100, label: "客户结算状态", sortable: true, align: "left", hidden: true},
				{name: "custstatus", index: "custstatus", width: 100, label: "客户状态", sortable: true, align: "left", hidden: true},
				{name: "custcode", index: "custcode", width: 100, label: "客户编号", sortable: true, align: "left", hidden: true},
				{name: "refcustcode", index: "refcustcode", width: 100, label: "关联客户编号", sortable: true, align: "left", hidden: true},
				{name: "refcustdescr", index: "refcustdescr", width: 100, label: "关联客户名", sortable: true, align: "left", hidden: true},
				{name: "applyman", index: "applyman", width: 100, label: "申请人", sortable: true, align: "left", hidden: true},
				{name: "worktype2", index: "worktype2", width: 100, label: "工资类型2", sortable: true, align: "left", hidden: true},
				{name: "worktype1", index: "worktype1", width: 100, label: "工资类型1", sortable: true, align: "left", hidden: true},
				{name: "iswithhold", index: "iswithhold", width: 100, label: "是否预扣", sortable: true, align: "left", hidden: true},
				{name: "status", index: "status", width: 100, label: "状态", sortable: true, align: "left", hidden: true},
				{name: "type", index: "type", width: 100, label: "申请类型", sortable: true, align: "left", hidden: true},
				{name: "custdescr", index: "custdescr", width: 100, label: "客户名称", sortable: true, align: "left", hidden: true},
				{name: "prjitem", index: "prjitem", width: 100, label: "项目名", sortable: true, align: "left", hidden: true},
				{name: "enddate", index: "enddate", width: 140, label: "完成时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "confirmdate", index: "confirmdate", width: 140, label: "验收时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
				{name: "rcvcost", index: "rcvcost", width: 100, label: "领取金额", sortable: true, align: "left", hidden: true},
				{name: "withholdcost", index: "withholdcost", width: 100, label: "预扣金额", sortable: true, align: "left", hidden: true},
				{name: "gotamount", index: "gotamount", width: 100, label: "已领工资", sortable: true, align: "left", hidden: true},
				{name: "totalamount", index: "totalamount", width: 100, label: "合同总价", sortable: true, align: "left", hidden: true},
				{name: "confirmczy", index: "confirmczy", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
				{name: "confirmczydescr", index: "confirmczydescr", width: 100, label: "验收人", sortable: true, align: "left", hidden: true},
				{name: "isconfirm", index: "isconfirm", width: 80, label: "审核标志", sortable: true, align: "center",formatter:"checkbox", editoptions: {value:"1:0"}, formatoptions:{disabled:setIsConfirm}},
				{name: "checkconfirm", index: "checkconfirm", width: 80, label: "审核标志", sortable: true, align: "center",hidden:true},
				{name: "custtypedescr", index: "custtypedescr", width: 120, label: "客户类型", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "refaddress", index: "refaddress", width: 120, label: "关联楼盘", sortable: true, align: "left"},
				{name: "signdate", index: "signdate", width: 90, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left"},
				{name: "applymandescr", index: "applymandescr", width: 65, label: "申请人", sortable: true,sorttype:"text", align: "left",},
				{name: "statusdescr", index: "statusdescr", width: 61, label: "状态", sortable: true, align: "left"},
				{name: "salarytypedescr", index: "salarytypedescr", width: 70, label: "工资类型", sortable: true, align: "left"},
				{name: "worktype1descr", index: "worktype1descr", width: 70, label: "工种分类1", sortable: true, align: "left"},
				{name: "worktype2descr", index: "worktype2descr", width: 70, label: "工种分类2", sortable: true, align: "left"},
				{name: "appamount", index: "appamount", width: 70, label: "申请金额", sortable: true, align: "right", sum: true,sorttype:"number"},
				{name: "remarks", index: "remarks", width: 135, label: "请款说明", sortable: true, align: "left"},
				{name: "confirmamount", index: "confirmamount", width: 70, label: "审批金额", sortable: true,sorttype:"number", align: "right", sum: true,editable:!setIsConfirm, 
				editrules: {number:true,required:true,custom:true,custom_func:function(value, colNames){
				var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");var ret = $("#detailDataTable").jqGrid('getRowData',rowId);
				if(value>parseFloat(ret.appamount)){return[false, "审批金额不能大于申请金额！"];}
				if(value<parseFloat(ret.qualityfeebegin) ||value<parseFloat(ret.qualityfee) ) {$("#detailDataTable").jqGrid('setCell',rowId,"realamount",value);
				$("#detailDataTable").jqGrid('setCell',rowId,"qualityfee","0");return [true, ""];}else{$("#detailDataTable").jqGrid('setCell',rowId,"realamount",value-ret.qualityfee);return [true, ""];}}}},
				{name: "workerplanoffer", index: "workerplanoffer", width: 95, label: "定额工资", sortable: true, align: "right",sorttype:"number"},
				{name: "confirmremark", index: "confirmremark", width: 130, label: "审批说明", sortable: true, align: "left",editable:!setIsConfirm,edittype:'textarea'},
				{name: "issigndescr", index: "issigndescr", width: 70, label: "签约类型", sortable: true, align: "left"},
				{name: "qualityfee", index: "qualityfee", width: 70, label: "质保金", sortable: true, sorttype:"number",align: "right", sum: true,editable:!setIsConfirm, editrules: {number:true,required:true}},
				{name: "realamount", index: "realamount", width: 70, label: "实发金额", sortable: true,sorttype:"number", align: "right", sum: true},
				{name: "actname", index: "actname", width: 70, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 150, label: "卡号", sortable: true, align: "left"},
				{name: "cardid2", index: "cardid2", width: 150, label: "卡号2", sortable: true, align: "left"},
				{name: "iswithholddescr", index: "iswithholddescr", width: 80, label: "是否预扣", sortable: true, align: "left"},
				{name: "withholdno", index: "withholdno", width: 95, label: "预扣单号", sortable: true, align: "left"},
				{name: "custctrl", index: "custctrl", width: 100, label: "人工发包额", sortable: true, sorttype:"number",align: "right", sum: true},
				{name: "custctrl_kzx", index: "custctrl_kzx", width: 110, label: "人工发包控制", sortable: true,sorttype:"number", align: "right", sum: true},
				{name: "custcost", index: "custcost", width: 90, label: "人工总成本", sortable: true,sorttype:"number", align: "right", sum: true},
				{name: "leavecustcost", index: "leavecustcost", width: 100, label: "单项工种余额", sortable: true,sorttype:"number", align: "right"},
				{name: "allleavecustcost", index: "allleavecustcost", width: 100, label: "总发包余额", sortable: true,sorttype:"number", align: "right"},
				{name: "allcustctrl", index: "allcustctrl", width: 90, label: "总发包额", sortable: true,sorttype:"number", align: "right", sum: true},
				{name: "allcustcost", index: "allcustcost", width: 90, label: "总成本", sortable: true,sorttype:"number", align: "right", sum: true},
				{name: "iswateritemctrldescr", index: "iswateritemctrldescr", width: 90, label: "水电发包工人", sortable: true,align: "left"},
				{name: "refprepk", index: "refprepk", width: 80, label: "预申请PK", sortable: true, align: "left"},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "workercode", index: "workercode", width: 84, label: "工人编号", sortable: true, align: "left", hidden: true},
				{name: "qualityfeebegin", index: "qualityfeebegin", width: 120, label: "质保金起始金额", sortable: true,sorttype:"number", align: "right"},
				{name: "iswateritemctrl", index: "iswateritemctrl", width: 120, label: "水电发包工人", sortable: true,align: "left", hidden: true},
				{name: "hasplan", index: "hasplan", width: 120, label: "是否解单", sortable: true,align: "left", hidden: true},
				{name: "custtypetype", index: "custtypetype", width: 70, label: "是否套餐", sortable: true,align: "left",formatter:typeFmatter},
				{name: "idnum", index: "idnum", width: 120, label: "工人身份证号", sortable: true, align: "left"},
				{name: "reffixdutymanpk", index: "reffixdutymanpk", width: 120, label: "关联定责人员PK", sortable: true, align: "left", hidden: true},
				{name: "faulttype", index: "faulttype", width: 150, label: "faulttype", sortable: true, align: "left",hidden: true},
				{name: "faulttypedescr", index: "faulttypedescr", width: 120, label: "责任人类型", sortable: true, align: "left", edittype:'select', editable:true,
				    editoptions:{
				        value: "0:请选择;1:员工（项目经理）;2:工人;3:公司",
				        dataEvents:[{type:'focus', fn: checkEditable},
				            {type: 'change', fn: setFaultTypeAndDisplayFaultManColumn}]
				    }
				},
				{name: "faultman", index: "faultman", width: 150, label: "faultman", sortable: true, align: "left",hidden:true},
				{name: "faultmandescr", index: "faultmandescr", width: 80, label: "责任人", sortable: true, align: "left", hidden: false, editable:true,
				    editoptions:{
                        dataEvents:[{type:'focus', fn: setEmployeeComponent}]
                    }
				},
				{name: "faultmanworker", index: "faultmandescrworker", width: 80, label: "责任人", sortable: true, align: "left", hidden: true, editable:true,
				    editoptions:{
                        dataEvents:[{type:'focus', fn: setWorkerComponent}]
                    }
				},
				{name: "prjqualityfee", index: "prjqualityfee", width: 150, label: "项目经理质保金余额", sortable: true, align: "right"},
				{name: "refprojectman", index: "refprojectman", width: 80, label: "关联楼盘项目经理", sortable: true, align: "left",hidden:true},
				{name: "refprojectmandescr", index: "refprojectmandescr", width: 80, label: "关联楼盘项目经理", sortable: true, align: "left",hidden:true},
				{name: "workcostimportitemno", index: "workcostimportitemno", width: 80, label: "导入项目编号", sortable: true, align: "left",hidden:true},
                {name: "documentno", index: "documentno", width: 80, label: "档案编号", sortable: true, align: "left"},
			],
			loadonce:true,
			beforeSelectRow: function (id) {
	          setTimeout(function () {
	            relocate(id, 'detailDataTable');
	          }, 100);
	        },
			gridComplete:function(){
				var baseOneCtrl="${BaseOneCtrl}";//单项超过比例
				var baseAllCtrl="${BaseAllCtrl}";//总发包成本控制
				checkBox();//刷新点击事件
				if('${workCost.button}'=="examine" || '${workCost.button}'=="returnExamine"){
		             var ids = $("#detailDataTable").getDataIDs();
		             var flag=true;
		             for(var i=0;i<ids.length;i++){
		                 var rowData = $("#detailDataTable").getRowData(ids[i]);
		                 //人工总成本超过人工发包控制110% 行背景显示为黄色
		                 if(rowData.hasplan==1){
		                 	if(parseFloat(rowData.confirmamount)>parseFloat(rowData.workerplanoffer)){
		                 		 $('#'+ids[i]).find("td").css("background-color","orange");
		                 		 flag=false;
		                 	}
		                 }else{
			                 if(rowData.custtypetype.trim()=="1" && parseFloat(rowData.custcost)>parseFloat(rowData.custctrl_kzx)){
			                     $('#'+ids[i]).find("td").css("background-color","orange");
			                     flag=false;
			                 }
		                 }
		                 //总成本金额超过总发包额92% 行背景显示为红色
		                 if(parseFloat(rowData.allcustcost)>parseFloat(rowData.allcustctrl)*baseAllCtrl){
		                     $('#'+ids[i]).find("td").css("background-color","#FF6699");
		                     flag=false;
		                 }
		                 if(flag){
		                 	rowData.isconfirm="1";
		                 	$("#detailDataTable").setRowData(ids[i], rowData);
		                 	checkBox();
		                 }
		                 flag=true;
		             }
				}
	        },
	        beforeSaveCell:function(rowId,name,val,iRow,iCol){
	    		var ret = $("#detailDataTable").jqGrid('getRowData', rowId);
				if(name=="qualityfee"){//修改质保金额
			    	//计算实发金额
			    	var realamount;
			    	if(!isNaN(val)){
				    	if(val!=""){
				    		realamount=(parseFloat(ret.confirmamount)-parseFloat(val)).toFixed(2);
				    	}else{
				    		realamount=" ";
				    	}
			    	}else{
			    		realamount=ret.realamount;
			    	}
		    		$("#detailDataTable").jqGrid('setCell',rowId,"realamount",realamount);
	    		}
	    	},
	    	afterSaveCell:function(id,name,val,iRow,iCol){
	    		var ret = $("#detailDataTable").jqGrid('getRowData', id);
	    		
	    		// 责任人类型与责任人在单元格事件回调中保存
	    		if (name === "faulttypedescr"
	    		    || name === "faultmandescr"
	    		    || name === "faultmanworker") return
	    		
	    		doCheck(ret.pk,name,val);
	    	},
	        ondblClickRow:function(){
				d_view();
            },	
 		};
 	
       //初始化集成进度明细
	   Global.JqGrid.initEditJqGrid("detailDataTable",gridOption);
	   changeGrid();
	   
	   $("#fixDuty").on("click", function () {
			var ids=$("#detailDataTable").jqGrid("getDataIDs");
			var newId=1;
			if(ids.length>0){
				newId=parseInt(ids[ids.length-1],0)+1;
			}
			var pk = (ids.length+1)*-1;
			var type=$("#type").val();
			if (type=="") {
				art.dialog({
					content: "请先选择申请类型！",
				});
				return;
			}
			var fdmpks = Global.JqGrid.allToJson("detailDataTable","reffixdutymanpk");
			var keys = fdmpks.keys;//获取invoiceNos的数组
			Global.Dialog.showDialog("fixDuty", {
				title: "基础人工成本明细——工人定责导入",
				url: "${ctx}/admin/workCost/goFixDuty",
				postData: {"keys": keys},
				height: 700,
				width: 1300,
				returnFun:function(data){
					if(data.length >0){
						$.each(data, function(i,rowData){
							rowData.pk = pk--;
							rowData.confirmamount = rowData.appamount;
							rowData.workercode = rowData.appworkercode;
							if ("1" != type) {
								rowData.workercode = "";
								rowDate.cardid = "";
								rowData.actname = "";
								rowData.cardid2 = "";
							} else {
								if(rowData.salarytype=="05" || rowData.salarytype==""){
									rowData.iswithhold = "0";
									rowData.iswithholddescr = "否";
								}else{
									if(rowData.checkstatus=="1" || rowData.checkstatus=="2"){
										rowData.iswithhold = "0";
										rowData.iswithholddescr = "否";
									}else{
										rowData.iswithhold = "1";
										rowData.iswithholddescr = "是";
									}
								}
							}
							rowData.lastupdate = new Date().getTime();
							rowData.expired = "F";
							rowData.lastupdatedby = "${sessionScope.USER_CONTEXT_KEY.czybh}";
							rowData.actionlog = "ADD";
							$.ajax({
								url:'${ctx}/admin/workCostDetail/findPrjByCodeWork',
								type: 'post',
								data: {'custCode':rowData.custcode,'workType2':rowData.worktype2},
								dataType: 'json',
								cache: false,
								async: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
								},
								success: function(obj){
									rowData.prjitem = obj.PrjItem!=null?obj.PrjItem:"";
									rowData.enddate = obj.EndDate!=null?new Date(parseInt(obj.EndDate)).format("yyyy-MM-dd hh:mm:ss"):"";
									rowData.confirmdate = obj.ConfirmDate!=null?new Date(parseInt(obj.ConfirmDate)).format("yyyy-MM-dd hh:mm:ss"):"";
									rowData.confirmczydescr = obj.ConfirmCZYDescr!=null?obj.ConfirmCZYDescr:"";
									rowData.totalamount = obj.Amount!=null?obj.Amount:"0";
									rowData.gotamount = obj.ConfirmAmount!=null?obj.ConfirmAmount:"0";
									rowData.workerplanoffer = obj.WorkerPlanOffer==undefined?"0":obj.WorkerPlanOffer;
								}
							});
							$.ajax({
								url:"${ctx}/admin/workCostDetail/findCostByCodeWork",
								type: "post",
								data: {"custCode":rowData.custcode,"workType2":rowData.worktype2},
								dataType: "json",
								cache: false,
								async: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
								},
								success: function(obj){
									rowData.allcustctrl = obj.AllCustCtrl;
									rowData.allcustcost = obj.AllCustCost;
									rowData.allleavecustcost = obj.AllLeaveCustCost;
									if(rowData.worktype2!=""){
										rowData.custctrl = obj.CustCtrl;
										rowData.custctrl_kzx = (parseFloat(obj.CustCtrl)*1.1).toFixed(2);//保留两位小数
										rowData.custcost = obj.CustCost;
										rowData.leavecustcost = obj.LeaveCustCost;
									}
								}
							});
							$("#detailDataTable").jqGrid("addRowData", newId++, rowData);
						});	
					}
				}
			});		
		});
});

    //根据type和button改变表格显示的列
	 function changeGrid(){
	 	var type=$("#type").val();
	 	var button='${workCost.button}';
	 	if(type=="1"){
		 	$("#detailDataTable").setGridParam().showCol("cardid");
		 	$("#detailDataTable").setGridParam().showCol("cardid2");
		 	$("#detailDataTable").setGridParam().showCol("iswithholddescr");
		 	$("#detailDataTable").setGridParam().showCol("withholdno");
		 	$("#detailDataTable").setGridParam().showCol("actname");
		 	$("#subsidy").removeAttr("disabled");
		 	$("#waterItem").removeAttr("disabled");
		 	$("#fixDuty").removeAttr("disabled");
		 	$("#batchCrtDetail").removeAttr("disabled");
	 	}else if(type=="2"){
			$("#subsidy").attr("disabled","disabled");
			$("#waterItem").attr("disabled","disabled");
			$("#fixDuty").attr("disabled","disabled");
	 		$("#batchCrtDetail").removeAttr("disabled");
		 	$("#detailDataTable").setGridParam().hideCol("cardid");
		 	$("#detailDataTable").setGridParam().hideCol("cardid2");
		 	$("#detailDataTable").setGridParam().hideCol("iswithholddescr");
		 	$("#detailDataTable").setGridParam().hideCol("withholdno");
		 	$("#detailDataTable").setGridParam().hideCol("actname");
	 	}else{
	 		$("#batchCrtDetail").attr("disabled","disabled");
	 	}
	 	if(button=="add" || button=="update"){
	 		$("#detailDataTable").setGridParam().hideCol("qualityfee");
		 	$("#detailDataTable").setGridParam().hideCol("realamount");
	 	}
	 	if(button=="view" || button=="sign"){
	 		$("#detailDataTable").setGridParam().hideCol("custctrl");
	 		$("#detailDataTable").setGridParam().hideCol("custctrl_kzx");
	 		$("#detailDataTable").setGridParam().hideCol("custcost");
	 		$("#detailDataTable").setGridParam().hideCol("leavecustcost");
	 		$("#detailDataTable").setGridParam().hideCol("allleavecustcost");
	 		$("#detailDataTable").setGridParam().hideCol("allcustctrl");
	 		$("#detailDataTable").setGridParam().hideCol("allcustcost");
	 	}
	 }
	 //新增
	function d_add() {
		var ids=$("#detailDataTable").jqGrid("getDataIDs");
		var ret = $("#detailDataTable").jqGrid('getRowData',ids[ids.length-1]);
		var newId=1;
		if(ids.length>0){
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		var type=$("#type").val();
			if(type==""){
				art.dialog({
	       			content: "请先选择申请类型！",
	       		});
	       		return;
			}
		Global.Dialog.showDialog("save", {
			title : "基础人工成本明细——新增",
			url : "${ctx}/admin/workCostDetail/goSave",
			postData : {
				'type' : type,'button':'add',
			},
			height : 700,
			width : 700,
			returnFun : function(data) {
				if (data) {
					$.each(data, function(k, v) {
						var json = {
							custcode : v.custCode,no : v.no,applyman : v.applyMan,
							salarytype : v.salaryType,worktype2 : v.workType2,remarks : v.remarks,
							iswithhold : v.isWithHold,status : v.status,applyman : v.applyMan,
							worktype1 : v.workType1,isconfirm : v.isConfirm,
							documentno : v.documentNo,address : v.address,checkstatusdescr : v.checkStatusDescr,
							applymandescr : v.applyManDescr,statusdescr : v.statusDescr,salarytypedescr : v.salaryTypeDescr,
							worktype1descr : v.workType1Descr,worktype2descr : v.workType2Descr,appamount : v.appAmount,
							confirmamount : v.appAmount,confirmremark : v.confirmRemark,actname : v.actName,
							cardid : v.cardId,cardid2 : v.cardId2,issigndescr : v.isSignDescr,
							qualityfee : v.qualityFee,realamount : v.realAmount,iswithholddescr : v.isWithHoldDescr,
							withholdno : v.withHoldNo,custctrl : v.custCtrl,custctrl_kzx : v.custCtrl_Kzx,
							custcost : v.custCost,leavecustcost : v.leaveCustCost,allleavecustcost : v.allLeaveCustCost,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : v.lastUpdatedBy,
							actionlog : "ADD",allcustctrl : v.allCustCtrl,allcustcost :v.allCustCost,
							refprepk :v.refPrePk,workercode :v.workerCode,qualityfeebegin :v.qualityFeeBegin,
							custdescr :v.custDescr,prjitem :v.prjItem,enddate :v.endDate,
							confirmdate :v.confirmDate,rcvcost :v.rcvCost,withholdcost :v.withHoldCost,
							gotamount :v.gotAmount,totalamount :v.totalAmount,confirmczy :v.confirmCzy,
							confirmczydescr :v.confirmCzyDescr,signdate :v.signDate,custstatus:v.custStatus,
							checkstatus:v.checkStatus,pk:(ids.length+1)*-1,iswateritemctrl:v.isWaterItemCtrl,iswateritemctrldescr:v.isWaterItemCtrlDescr,
							workerplanoffer:v.workerPlanOffer,refcustcode:v.refCustCode,refcustdescr:v.refCustDescr,refaddress:v.refAddress,idnum:v.idnum,
							faulttype:v.faultType,faulttypedescr:v.faultTypeDescr,faultman:v.faultMan,faultmandescr:v.faultManDescr,
							prjqualityfee:v.prjQualityFee,refprojectman:v.refProjectMan,refprojectmandescr:ret.refProjectManDescr,
							custtypedescr: v.custTypeDescr
						};
						$("#detailDataTable").jqGrid("addRowData", newId, json);
					});
				}
			}
		});
	}
	//编辑
	function d_update() {
		var type=$("#type").val();
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");//选中行id
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);//根据id获取选中行data
		var type=$("#type").val();
			if(type==""){
				art.dialog({
	       			content: "请先选择申请类型！",
	       		});
	       		return;
			}
		if(ret.isconfirm=="1"){
			art.dialog({
	       		content: "有审核标志，无法编辑",
	       	});
	       	return;
		}
		if (rowId) {
		Global.Dialog.showDialog("update", {
			title : "基础人工成本明细——编辑",
			url : "${ctx}/admin/workCostDetail/goSave",
			postData : {
				'type' : type,'button':'update','custStatus':ret.custstatus,'no':ret.no,
				'custCode':ret.custcode,'salaryType':ret.salarytype,'workType2':ret.worktype2,'remarks':ret.remarks,'pk':ret.pk,
				'isWithHold':ret.iswithhold,'status':ret.status,'applyMan':ret.applyman,'workType1':ret.worktype1,
				'isConfirm':ret.isconfirm,'documentNo':ret.documentno,'address':ret.address,'checkStatusDescr':ret.checkstatusdescr,
				'applyManDescr':ret.applymandescr,'statusDescr':ret.statusdescr,'salaryTypeDescr':ret.salarytypedescr,'workType1Descr':ret.worktype1descr,
				'workType2Descr':ret.worktype2descr,'appAmount':ret.appamount,'confirmAmount':ret.confirmamount,'confirmRemark':ret.confirmremark,
				'actName':ret.actname,'cardId':ret.cardid,'cardId2':ret.cardid2,'isSignDescr':ret.issigndescr,
				'qualityFee':ret.qualityfee,'realAmount':ret.realamount,'isWithHoldDescr':ret.iswithholddescr,'withHoldNo':ret.withholdno,
				'custCtrl':ret.custctrl,'custCtrl_Kzx':ret.custctrl_kzx,'custCost':ret.custcost,'leaveCustCost':ret.leavecustcost,'allLeaveCustCost':ret.allleavecustcost,
				'custDescr':ret.custdescr,'prjItem':ret.prjitem,'endDate':ret.enddate,'confirmDate':ret.confirmdate,'rcvCost':ret.rcvcost,
				'withHoldCost':ret.withholdcost,'gotAmount':ret.gotamount,'totalAmount':ret.totalamount,'confirmCzy':ret.confirmczy,'confirmCzyDescr':ret.confirmczydescr,
				'allCustCtrl':ret.allcustctrl,'allCustCost':ret.allcustcost,'signDate':ret.signdate,'workerCode':ret.workercode,'qualityFeeBegin':ret.qualityfeebegin,
				'isWaterItemCtrl':ret.iswateritemctrl,'isWaterItemCtrlDescr':ret.iswateritemctrldescr,workerPlanOffer:ret.workerplanoffer,
				'refCustCode':ret.refcustcode,'refCustDescr':ret.refcustdescr,'refAddress':ret.refaddress,'idnum':ret.idnum,refPrePk:ret.refprepk,
				faultType:ret.faulttype,faultTypeDescr:ret.faulttypedescr,faultMan:ret.faultman,faultManDescr:ret.faultmandescr,
				prjQualityFee:ret.prjqualityfee,refProjectMan:ret.refprojectman,refProjectManDescr:ret.refprojectmandescr,
				custTypeDescr: ret.custtypedescr
			},
			height : 700,
			width : 700,
			returnFun : function(data) {
				if (data) {
					$.each(data, function(k, v) {
						var json = {
							custcode : v.custCode,no : v.no,applyman : v.applyMan,
							worktype2 : v.workType2,remarks : v.remarks,
							iswithhold : v.isWithHold,status : v.status,applyman : v.applyMan,
							salarytype : v.salaryType,worktype1 : v.workType1,isconfirm : v.isConfirm,
							documentno : v.documentNo,address : v.address,checkstatusdescr : v.checkStatusDescr,
							applymandescr : v.applyManDescr,statusdescr : v.statusDescr,salarytypedescr : v.salaryTypeDescr,
							worktype1descr : v.workType1Descr,worktype2descr : v.workType2Descr,appamount : v.appAmount,
							confirmamount : v.appAmount,confirmremark : v.confirmRemark,actname : v.actName,
							cardid : v.cardId,cardid2 : v.cardId2,issigndescr : v.isSignDescr,
							qualityfee : v.qualityFee,realamount : v.realAmount,iswithholddescr : v.isWithHoldDescr,
							withholdno : v.withHoldNo,custctrl : v.custCtrl,custctrl_kzx : v.custCtrl_Kzx,
							custcost : v.custCost,leavecustcost : v.leaveCustCost,allleavecustcost : v.allLeaveCustCost,
							lastupdate : new Date().getTime(),expired : "F",lastupdatedby : v.lastUpdatedBy,
							actionlog : "ADD",allcustctrl : v.allCustCtrl,allcustcost :v.allCustCost,
							refprepk :v.refPrePk,workercode :v.workerCode,qualityfeebegin :v.qualityFeeBegin,
							custdescr :v.custDescr,prjitem :v.prjItem,enddate :v.endDate,
							confirmdate :v.confirmDate,rcvcost :v.rcvCost,withholdcost :v.withHoldCost,
							gotamount :v.gotAmount,totalamount :v.totalAmount,confirmczy :v.confirmCzy,
							confirmczydescr :v.confirmCzyDescr,signdate :v.signDate,custstatus:v.custStatus,
							checkstatus:v.checkStatus,iswateritemctrl:v.isWaterItemCtrl,iswateritemctrldescr:v.isWaterItemCtrlDescr,
							workerplanoffer:v.workerPlanOffer,refcustcode:v.refCustCode,refcustdescr:v.refCustDescr,
							refaddress:v.refAddress,idnum:v.idnum,
							faulttype:v.faultType,faulttypedescr:v.faultTypeDescr,faultman:v.faultMan,faultmandescr:v.faultManDescr,
							prjqualityfee:v.prjQualityFee,refprojectman:v.refProjectMan,refprojectmandescr:ret.refProjectManDescr,
							custtypedescr: v.custTypeDescr
						};
						$("#detailDataTable").setRowData(rowId, json);
					});
				}
			}
		});
	  }else{
		  art.dialog({    	
			  content: "请选择一条记录"
		  });
	  }
	}
	//查看
	function d_view() {
		var type=$("#type").val();
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("view", {
			title : "基础人工成本明细——查看",
			url : "${ctx}/admin/workCostDetail/goSave",
			postData : {
				'type' : type,'button':'view','custStatus':ret.custstatus,'no':ret.no,
				'custCode':ret.custcode,'salaryType':ret.salarytype,'workType2':ret.worktype2,'remarks':ret.remarks,'pk':ret.pk,
				'isWithHold':ret.iswithhold,'status':ret.status,'applyMan':ret.applyman,'workType1':ret.worktype1,
				'isConfirm':ret.isconfirm,'documentNo':ret.documentno,'address':ret.address,'checkStatusDescr':ret.checkstatusdescr,
				'applyManDescr':ret.applymandescr,'statusDescr':ret.statusdescr,'salaryTypeDescr':ret.salarytypedescr,'workType1Descr':ret.worktype1descr,
				'workType2Descr':ret.worktype2descr,'appAmount':ret.appamount,'confirmAmount':ret.confirmamount,'confirmRemark':ret.confirmremark,
				'actName':ret.actname,'cardId':ret.cardid,'cardId2':ret.cardid2,'isSignDescr':ret.issigndescr,
				'qualityFee':ret.qualityfee,'realAmount':ret.realamount,'isWithHoldDescr':ret.iswithholddescr,'withHoldNo':ret.withholdno,
				'custCtrl':ret.custctrl,'custCtrl_Kzx':ret.custctrl_kzx,'custCost':ret.custcost,'leaveCustCost':ret.leavecustcost,'allLeaveCustCost':ret.allleavecustcost,
				'custDescr':ret.custdescr,'prjItem':ret.prjitem,'endDate':ret.enddate,'confirmDate':ret.confirmdate,'rcvCost':ret.rcvcost,
				'withHoldCost':ret.withholdcost,'gotAmount':ret.gotamount,'totalAmount':ret.totalamount,'confirmCzy':ret.confirmczy,'confirmCzyDescr':ret.confirmczydescr,
				'allCustCtrl':ret.allcustctrl,'allCustCost':ret.allcustcost,'signDate':ret.signdate,'workerCode':ret.workercode,'qualityFeeBegin':ret.qualityfeebegin,
				'refCustCode':ret.refcustcode,'refCustDescr':ret.refcustdescr,'refAddress':ret.refaddress,'idnum':ret.idnum,workerPlanOffer:ret.workerplanoffer,refPrePk:ret.refprepk,
				faultType:ret.faulttype,faultTypeDescr:ret.faulttypedescr,faultMan:ret.faultman,faultManDescr:ret.faultmandescr,
				prjQualityFee:ret.prjqualityfee,refProjectMan:ret.refprojectman,refProjectManDescr:ret.refprojectmandescr,
				custTypeDescr: ret.custtypedescr
			},
			height : 700,
			width : 700,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录"
		});
	  }
	}
	
	//删除
	function d_delete(){
	 	var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！"});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("detailDataTable",id);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	
	//从预申请导入
	function d_inport(){
		var pks = $("#detailDataTable").getCol("refprepk", false).join(",");
		var ret=$("#detailDataTable").jqGrid('getRowData',1);
		Global.Dialog.showDialog("view", {
			title : "基础人工成本明细——从预申请导入",
			url : "${ctx}/admin/workCostDetail/addFromPre",
			postData : {
				'refPrePks':pks,'appNo':ret.no
			},
 			height:620,
			width:1300,
			returnFun:function(data){
					if(data.length > 0){
						var ids = $("#detailDataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});	
					}
			}
		});
	}
	
	//生成防水工资及补贴
	function d_subsidy(){
		var custcodes="";
	 	var ids=$("#detailDataTable").jqGrid("getDataIDs");
	 	$.each(ids,function(i,id){
			var rowData = $("#detailDataTable").getRowData(id);
			//遍历表格，找到workType2为019和020的custcode，以逗号拼接
			if(rowData['worktype2']=='019' || rowData['worktype2']=='020'){
				custcodes+=rowData['custcode']+",";
			}
		});
		if(custcodes!="")
		custcodes=custcodes.substring(0, custcodes.length-1);//去掉最后一个逗号  
		Global.Dialog.showDialog("subsidy", {
			title : "基础人工成本明细——生成防水工资及补贴",
			url : "${ctx}/admin/workCostDetail/goSubsidy",
			postData : {
				'custCodes':custcodes
			},
			height:320,
			width:450,
			returnFun:function(data){
					if(data.length >0){
						var ids = $("#detailDataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});	
					}
			}
		});
	}
	//生成水电拍照费
	function d_batchCrtDetail(){
		/* var custcodes="";
	 	var ids=$("#detailDataTable").jqGrid("getDataIDs");
	 	$.each(ids,function(i,id){
			var rowData = $("#detailDataTable").getRowData(id);
			//遍历表格，找到workType2 为005，工资类型为2，备注为水电拍照费的custcode，以逗号拼接
			if(rowData['worktype2']=='005' && rowData['salarytype']=='02' && rowData['remarks'].indexOf('水电拍照工费')>=0){
				custcodes+=rowData['custcode']+",";
			}
		});
		if(custcodes!="")
		custcodes=custcodes.substring(0, custcodes.length-1);//去掉最后一个逗号   */
		var rets = $("#detailDataTable").jqGrid("getRowData");
		Global.Dialog.showDialog("batchCrtDetail", {
			title : "基础人工成本明细——批量生成明细",
			url : "${ctx}/admin/workCostDetail/goBatchCrtDetail",
			postData : {
				'jsonString':encodeURI(JSON.stringify(rets)),'type':$("#type").val()
			},
			height:330,
			width:450,
			returnFun:function(data){
				if(data.length >0){
					var ids = $("#detailDataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						rowData.lastupdatedby="${USER_CONTEXT_KEY.czybh}";
						$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	}
	//取消
	function d_cancel(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var ret=$("#detailDataTable").jqGrid('getRowData',id);
		if(id){
			if(ret.status!='1'){
				art.dialog({    	
					content: "只能对申请状态的记录进行取消操作!"
				});
				return;
			}
			art.dialog({
				 content:"是否确认要取消",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					ret['status']='3';
					ret['statusdescr']='取消';
					ret['confirmamount']='0';
					ret['qualityfee']='0';
					$("#detailDataTable").setRowData(id, ret);
					checkBox();//刷新点击事件
					$.ajax({
						url : "${ctx}/admin/workCostDetail/doCancel",
						type : "post",
						data : {pk:ret.pk,confirmRemark:ret.confirmremark,refPrePk:ret.refprepk},
						dataType : "json",
						cache : false
					});
				},
				cancel: function () {
						return true;
				}
			}); 
		}else{
			art.dialog({    	
				content: "请选择一条记录"
			});
		}
		
	}
	//恢复
	function d_renew(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var ret=$("#detailDataTable").jqGrid('getRowData',id);
		if(id){
			if(ret.status!='3'){
				art.dialog({    	
					content: "只能对取消状态的记录进行恢复操作!"
				});
				return;
			}
			art.dialog({
				 content:"是否确认要恢复",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					ret['status']='1';
					ret['statusdescr']='申请';
					ret['confirmamount']=ret.appamount;
					checkBox();//刷新点击事件
					$("#detailDataTable").setRowData(id, ret);
					$.ajax({
						url : "${ctx}/admin/workCostDetail/doRenew",
						type : "post",
						data : {pk:ret.pk,confirmRemark:ret.confirmremark,refPrePk:ret.refprepk},
						dataType : "json",
						cache : false
					});
				},
				cancel: function () {
						return true;
				}
			}); 
		}else{
			art.dialog({    	
				content: "请选择一条记录"
			});
		}
		
	}
	//收支明细
	function d_income(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#detailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("income", {
			title : "基础人工成本明细——客户收支明细",
			url : "${ctx}/admin/itemSzQuery/goJcszxx",
			postData : {
				'code':ret.refcustcode.trim()==''?ret.custcode:ret.refcustcode,//售后工地调出关联楼盘收支明细
				'isWorkcost':'1'
			},
			height : 730,
			width : 1350,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录"
		});
	  }
	}
	//生成水电材料奖惩
	function d_waterItem(){
		var rets = $("#detailDataTable").jqGrid("getRowData");
		$.ajax({
			url : "${ctx}/admin/workCostDetail/isCreatedWaterItem",
			type : "post",
			data : {'workCostDetailJson':JSON.stringify(rets)},
			dataType : "json",
			cache : false,
			success:function(list){
				if(list.length>0){
					Global.Dialog.showDialog("waterItem", {
						title : "基础人工成本明细——生成水电材料奖惩",
						url : "${ctx}/admin/workCostDetail/goWaterItem",
						postData : {
							
						}, 
						height : 700,
						width : 970,
						returnFun:function(data){
							if(data.length > 0){
								var ids = $("#detailDataTable").jqGrid("getDataIDs");
								$.each(data, function(i,rowData){
									if(rowData.clamount_wfh<=0){
										rowData['lastupdatedby']="${workCost.lastUpdatedBy}";
										rowData['lastupdate']=new Date();
										$("#detailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
									}
								});	
							}
						}
					});
				}else{
					art.dialog({    	
						content: "已生成过或已经没有可生成的水电材料奖惩工资！"
					});
				}
			}
		});
	}
	//导出excel
	function doDetailExcel(){
		var ids = $("#detailDataTable").getDataIDs();
		var rows = $("#detailDataTable").jqGrid("getRowData");
		var preName="基础人工成本明细表";
		var pageFormId="form";
		var colModel = $("#detailDataTable").jqGrid('getGridParam','colModel');
		//遍历所有表格数据，把审核标志列中的数字改为汉字，去除工人卡号里面的空格
		 for(var i=0;i<ids.length;i++){
		 	if(rows[i].isconfirm=="1"){
		 		rows[i]['isconfirm']="已审核";
		 	}else{
		 		rows[i]['isconfirm']="未审核";
		 	}
		 	rows[i].cardid=(rows[i].cardid).replace(new RegExp(" ","gm"),"");
		 	rows[i].cardid2=(rows[i].cardid2).replace(new RegExp(" ","gm"),"");
		 	// rows[i].cardid3=(rows[i].cardid3).replace(new RegExp(" ","gm"),"");
		 	// rows[i].cardid4=(rows[i].cardid4).replace(new RegExp(" ","gm"),"");
		 }
		if (!rows || rows.length==0){
			art.dialog({
				content: "无数据导出"
			});
			return;
		}
		var datas = {
			colModel: JSON.stringify(colModel),
			rows: JSON.stringify(rows),
			preName: preName
		};
		$.form_submit($("#"+pageFormId).get(0), {
			"action": ctx+"/system/doExcel",
			"jsonString": JSON.stringify(datas)
		});

	}
	//表格中审核保存
	function doCheck(pk,col,value){
		$.ajax({
			url : "${ctx}/admin/workCostDetail/doCheck",
			type : "post",
			data : {pk:pk,col:col,value:value},
			dataType : "json",
			async:false,
			cache : false
		});
	}
	//审核单选框点击
	function checkBox(){
		//checkbox点击事件，控制审批金额是否可编辑
		$("#detailDataTable tbody tr[class~=jqgrow] td[aria-describedby=detailDataTable_isconfirm] input").on("click", function(){
			    $("#detailDataTable").jqGrid("setSelection", $(this).parent().parent()[0].id);//选中此行
			    var id = $("#detailDataTable").jqGrid("getGridParam","selrow");//获取行id
			   //var cell=$("#detailDataTable").jqGrid("getCell",id,"confirmamount");//获取审核金额单元格
			    $("#detailDataTable").jqGrid('setCell', id, 'confirmamount', '', 'review-'+id);//初始化cell样式
			    $("#detailDataTable").jqGrid('setCell', id, 'qualityfee', '', 'review2-'+id);//初始化cell样式
			    $("#detailDataTable").jqGrid('setCell', id, 'confirmremark', '', 'review3-'+id);//初始化cell样式
			    if($(this).val() == "0"){
				    $(this).val("1");
				    $("#detailDataTable").jqGrid('setCell',id,"isconfirm","1");
				    $("#detailDataTable").jqGrid('setCell', id, 'qualityfee', '', 'not-editable-cell');//单元格不可编辑
				    $("#detailDataTable").jqGrid('setCell', id, 'confirmamount', '', 'not-editable-cell');//单元格不可编辑
				    $("#detailDataTable").jqGrid('setCell', id, 'confirmremark', '', 'not-editable-cell');//单元格不可编辑
			    }else{
			        $(this).val("0");
			        $("#detailDataTable").jqGrid('setCell',id,"isconfirm","0");
			        $(".review-"+id).removeClass('not-editable-cell');//单元格可编辑
			        $(".review2-"+id).removeClass('not-editable-cell');//单元格可编辑
			        $(".review3-"+id).removeClass('not-editable-cell');//单元格可编辑
			    }
			    var ret = $("#detailDataTable").jqGrid('getRowData', id);
			    doCheck(ret.pk,"isconfirm",$(this).val());
			    checkBox();//刷新点击事件
		});
	}
	function typeFmatter(cellvalue, options, rowObject){  
	    var value="";
	    if(cellvalue=="1"){
	    	value= "否";
	    }else if(cellvalue=="2"){
	    	value= "是";
	    } 
	    return value;
	};
	
	function checkEditable(ev) {
	    var grid = $("#detailDataTable")
        var rowid = grid.getGridParam("selrow")
        var custCode = grid.getCell(rowid, "custcode")
                
        if ("${AftCustCode}".indexOf(custCode) === -1) {
            art.dialog({content: "非售后类型客户不允许编辑"})
            return
        }
	}
	
	function setFaultTypeAndDisplayFaultManColumn(ev) {
	    var grid = $("#detailDataTable")
	    var rowid = grid.getGridParam("selrow")
	    var rowData = grid.getRowData(rowid)
	    var custCode = grid.getCell(rowid, "custcode")
	    
	    grid.setCell(rowid, "faulttype", ev.target.value)
	    
	    if (ev.target.value === "1") {
	        grid.hideCol("faultmanworker")
	        grid.showCol("faultmandescr")
	        
	        doCheck(rowData.pk, "FaultType", "1")
            doCheck(rowData.pk, "FaultMan", rowData.refprojectman)
	        
	        grid.setCell(rowid, "faultman", rowData.refprojectman)
            grid.setCell(rowid, "faultmandescr", rowData.refprojectmandescr)
	    } else if (ev.target.value === "2") {
	        grid.hideCol("faultmandescr")
	        grid.showCol("faultmanworker")
	    } else if (ev.target.value === "3") {
	        doCheck(rowData.pk, "FaultType", "3")
	        doCheck(rowData.pk, "FaultMan", "")
	        
	        grid.setCell(rowid, "faultman", "", "", "", true)
	        grid.setCell(rowid, "faultmandescr", "", "", "", true)
	        grid.setCell(rowid, "faultmanworker", "", "", "", true)
	    }
	}
	
	function setEmployeeComponent(ev) {
	    var grid = $("#detailDataTable")
        var rowid = grid.getGridParam("selrow")
        var faultType = grid.getCell(rowid, "faulttype").trim()
                
        if(!faultType || faultType === "0") {
            art.dialog({content: "请先选择责任人类型"})
            return
        }
        
        if(faultType === "3") {
            art.dialog({content: "公司类型责任无需责任人"})
            return
        }
        
	    $(ev.target).openComponent_employeeDescr({onSelect: setFaultManWithEmployee})
	}
	
	function setFaultManWithEmployee(data) {
        var grid = $("#detailDataTable")
        var rowid = grid.getGridParam("selrow")
        var rowData = grid.getRowData(rowid)
        
        grid.setCell(rowid, "faultman", data.number)
        grid.setCell(rowid, "faultmandescr", data.namechi)
        
        doCheck(rowData.pk, "FaultType", "1")
        doCheck(rowData.pk, "FaultMan", data.number)
    }
	
	function setWorkerComponent(ev) {
	    var grid = $("#detailDataTable")
        var rowid = grid.getGridParam("selrow")
        var faultType = grid.getCell(rowid, "faulttype").trim()
        
        if(!faultType || faultType === "0") {
            art.dialog({content: "请先选择责任人类型"})
            return
        }
        
        if(faultType === "3") {
            art.dialog({content: "公司类型责任无需责任人"})
            return
        }
        
	    $(ev.target).openComponent_workerDescr({onSelect: setFaultManWithWorker})
	}
	
	function setFaultManWithWorker(data) {
	    var grid = $("#detailDataTable")
        var rowid = grid.getGridParam("selrow")
        var rowData = grid.getRowData(rowid)
        
        grid.setCell(rowid, "faultman", data.code)
        grid.setCell(rowid, "faultmanworker", data.namechi)
        
        doCheck(rowData.pk, "FaultType", "2")
        doCheck(rowData.pk, "FaultMan", data.code)
	}
	
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>

			<div class="btn-group-xs">
				<c:choose>
					<c:when
						test="${workCost.button=='update' || workCost.button=='add'}">
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_add()">
							<span>新增 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_update()">
							<span>编辑 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_delete()">
							<span>删除 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_cancel()">
							<span>取消 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_renew()">
							<span>恢复</span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_income()">
							<span>收支明细 </span>
						</button>
					</c:when>
					<c:when
						test="${workCost.button=='examine' || workCost.button=='returnExamine'}">
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_add()">
							<span>新增 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_update()">
							<span>编辑 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_delete()">
							<span>删除 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_cancel()">
							<span>取消 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_renew()">
							<span>恢复</span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							onclick="d_income()">
							<span>收支明细 </span>
						</button>
					</c:when>
					<c:when
						test="${workCost.button=='sign' || workCost.button=='view'}">
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_add()">
							<span>新增 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_update()">
							<span>编辑 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_delete()">
							<span>删除 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_cancel()">
							<span>取消 </span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_renew()">
							<span>恢复</span>
						</button>
						<button style="align:left" type="button" class="btn btn-system "
							disabled="disabled" onclick="d_income()">
							<span>收支明细 </span>
						</button>
					</c:when>
				</c:choose>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="d_view()">
					<span>查看 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="doDetailExcel()">
					<span>导出excel </span>
				</button>
				<c:if test="${workCost.button == 'add' || workCost.button == 'update'}">
					<button style="align:left" type="button" class="btn btn-system "
						onclick="d_inport()">
						<span>从预申请导入 </span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="subsidy" disabled="disabled" onclick="d_subsidy()">
						<span>生成防水工资及补贴</span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="waterItem" disabled="disabled" onclick="d_waterItem()">
						<span>生成水电材料奖惩</span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="batchCrtDetail" disabled="disabled" onclick="d_batchCrtDetail()">
						<span>批量生成明细</span>
					</button> 
					<button style="align:left" type="button" class="btn btn-system " id="fixDuty" 
							disabled="disabled">
						<span>导入定责工资</span>
					</button>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



