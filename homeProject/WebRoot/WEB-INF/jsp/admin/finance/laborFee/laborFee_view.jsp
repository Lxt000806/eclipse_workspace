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
<title>人工费用管理-编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_workCard.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
    .SelectRed{
		background-color:pink;
	}
	</style>
	<script type="text/javascript">
	var excelName="人工费用明细表";
	var excelTable="dataTable";
	$(function(){
		function getData(data){
			if(!data){
				return false;	
			}
			$("#cardID").val(data.CardID);
			
		}
		$("#appCZY").openComponent_employee({showValue:"${laborFee.appCZY}",showLabel:"${appCZY.nameChi}",readonly:true });
		$("#confirmCZY").openComponent_employee({showValue:"${laborFee.confirmCZY}",showLabel:"${confirmCZY.nameChi}",readonly:true });
		$("#payCZY").openComponent_employee({showValue:"${laborFee.payCZY}",showLabel:"${payCZY.nameChi}",readonly:true });
		$("#actName").openComponent_workCard({callBack:getData,showValue:"${laborFee.actName}",showLabel:"${appCZY.nameChi}"});
		$("#status_NAME").attr("disabled","disabled");
	});
	//新增是否允许重复
	$(function(){
		$("#account_btn").hide();
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({firstSelect:"itemType1",
									firstValue:"${laborFee.itemType1 }",
								});
								
		var gridOption = {
			url:"${ctx}/admin/laborFee/goDetailJqGrid",
			postData:{no:$.trim("${laborFee.no }")},
			height:$(document).height()-$("#content-list").offset().top-360,
			rowNum:10000000,
			sortable: true,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 94, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "custcode", index: "custcode", width: 65, label: "客户编号", sortable: true, align: "left"},
                {name: "custtype", index: "custtype", width: 65, label: "客户类型编号", sortable: true, align: "left", hidden: true},
                {name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
				{name: "address", index: "address", width: 112, label: "楼盘地址", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 60, label: "档案号", sortable: true, align: "left"},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 80, label: "客户结算状态", sortable: true, align: "left"},
				{name: "custcheckdate", index: "custcheckdate", width: 110, label: "客户结算日期", sortable: true, align: "left", formatter: formatTime},
				{name: "feetypedescr", index: "feetypedescr", width: 75, label: "费用类型", sortable: true, align: "left"},
				{name: "feetype", index: "feetype", width: 75, label: "费用类型", sortable: true, align: "left",hidden:true},
				{name: "appsendno", index: "appsendno", width: 75, label: "送货单号", sortable: true, align: "left"},
				{name: "issetitemdescr", index: "issetitemdescr", width: 75, label: "套餐材料", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 75, label: "领料单号", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 65, label: "金额", sortable: true, align: "right", sum: true},
				{name: "amount1", index: "amount1", width: 60, label: "配送费", sortable: true, align: "right",},
				{name: "amount2", index: "amount2", width:65, label: "配送费（自动生成）", sortable: true, align: "right",},
				{name: "autoazf", index: "autoazf", width:85, label: "安装费(自动生成)", sortable: true, align: "right",hidden: true },
				{name: "hadamount", index: "hadamount", width: 75, label: "已报销金额", sortable: true, align: "right", sum: true},
				{name: "sendnohaveamount", index: "sendnohaveamount", width: 75, label: "本单已报销", sortable: true, align: "right"},
				{name: "actname", index: "actname", width: 65, label: "户名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 119, label: "卡号", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 168, label: "备注", sortable: true, align: "left"},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "片区", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 108, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 75, label: "最后更新人员", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 68, label: "操作标志", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "refcustcode", index: "refcustcode", width: 100, label: "关联客户", sortable: true, align: "left", hidden: true},
				{name: "refcustaddress", index: "refcustaddress", width: 150, label: "关联楼盘", sortable: true, align: "left"},
				{name: "faulttype", index: "faulttype", width: 150, label: "faulttype", sortable: true, align: "left",hidden:true},
				{name: "faulttypedescr", index: "faulttypedescr", width: 90, label: "责任人类型", sortable: true, align: "left"},
				{name: "faultman", index: "faultman", width: 150, label: "faultman", sortable: true, align: "left",hidden:true},
				{name: "faultmandescr", index: "faultmandescr", width: 80, label: "责任人", sortable: true, align: "left"},
				{name: "prjqualityfee", index: "prjqualityfee", width: 150, label: "项目经理质保金余额", sortable: true, align: "right"},
				{name: "projectman", index: "projectman", width: 150, label: "项目经理", sortable: true, align: "right",hidden:true},
				{name: "projectmandescr", index: "projectmandescr", width: 150, label: "项目经理", sortable: true, align: "right",hidden:true},
				{name: "cutcheckoutno", index: "cutcheckoutno", width: 100, label: "cutcheckoutno", sortable: true, align: "left",hidden:true},
			],
			loadonce: true,
			gridComplete:function(){
				var ids = $("#dataTable").getDataIDs();
				for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable").getRowData(ids[i]);
              		if($.trim(rowData.feetype)=="01"){
              			if(rowData.amount!=rowData.amount1||rowData.amount!=rowData.amount2||
              					rowData.amount1!=rowData.amount2){
	                   		$('#'+ids[i]).find("td").addClass("SelectRed");
              			}
              	   	}
             	}
				if($.trim("${laborFee.itemType1}")=='JC'){
             		$("#dataTable").jqGrid('showCol', "autoazf");
             	};
        	},
		};
		var detailJson = Global.JqGrid.allToJson("dataTable","itcode");
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	});
	
	function save(){
		$("#dataForm").bootstrapValidator("validate");
		if(!$("#dataForm").data("bootstrapValidator").isValid()) return;
		var Ids =$("#dataTable").getDataIDs();
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
		if(actName==""||cardID==""){
			for(var i=0; i<actNameArr.length;i++){
				if(actNameArr[i]==""){
					art.dialog({
						content:"主表和明细表必须有一处填入卡号和户名",
					});
					return false;
				}
			}
			return;
		}
		
		$("#itemType1").removeAttr("disabled","disabled");
		var param= Global.JqGrid.allToJson("dataTable");
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
			art.dialog({content: "请选择材料类型1",width: 200});
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
							documentno:v.documentNp,
							checkstatusdescr:v.checkStatus,
							custcheckdate:v.checkDate,
							feetypedescr:v.feeTypeDescr,
							feeType:v.feeType,
							appsendno:v.appSendNo,
							iano:v.IaNo,
							amount:v.amount,
							hadamount:v.haveAmount,
							sendnohaveamount:v.sendNoHaveAmount,
							actname:v.actName,
							cardid:v.cardID,
							remarks:v.detailRemarks,
							lastupdate:new Date(),
							lastupdatedby:"${laborFee.appCZY}",
							actionlog:"Add",
							expired:"F",
						};
						Global.JqGrid.addRowData("dataTable",json);
					});
					$("#itemType1").attr("disabled","disabled");
				 }
			} 
		});
	};
	
	function fastAdd(){
		var ret= selectDataTableRow("dataTable");
		var itcode = Global.JqGrid.allToJson("dataTable","itcode");
			itCodeArr= itcode.fieldJson.split(",");
		var fromWHCode = $.trim($("#fromWHCode").val());
		var toWHCode = $.trim($("#toWHCode").val());
		var adjType = $.trim($("#adjType").val());
		if(fromWHCode ==""){
			art.dialog({content: "请选择源仓库编号",width: 200});
			return false;
		}
		if(toWHCode ==""){
			art.dialog({content: "请选择目的仓库编号",width: 200});
			return false;
		}
		if(fromWHCode==toWHCode){
			art.dialog({
				content:"非法操作,与目的仓库相同!",
			});
			return false;
		}
		Global.Dialog.showDialog("saveAdd",{
			  title:"人工费用明细——新增",
			  url:"${ctx}/admin/itemTransferHeader/goFastAdd",
			  postData:{fromWHCode:$("#fromWHCode").val(),toWHCode:$("#toWHCode").val(),itCodeArr:itCodeArr},
			  height: 480,
			  width:800,
			    returnFun:function(data){
				  if(data){
					  $.each(data,function(k,v){
						  var json = {
							itcode:v.itCode,
							itdescr:v.itDescr,
							itemtype2descr:v.itemType2Descr,
							trfqty:v.trfQty,
							avgcost:v.avgCost,
							amountcost:myRound(v.trfQty)*myRound(v.avgCost),
							fromafterqty:v.fromAfterQty,
							toafterqty:v.toAfterQty,
							detailremarks:v.detailRemarks,
						  };
						  Global.JqGrid.addRowData("dataTable",json);
					  });
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
						feeType:ret.feetype,
						amount:ret.amount,
						appSendNo:ret.appsendno,
						itemType1:itemType1,
						detailRemarks:ret.remarks
						},
				height:480,          
				width:800,
				returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							var json = {
								custcode:v.custCode,
								address:v.address,
								documentno:v.documentNp,
								checkstatusdescr:v.checkStatus,
								custcheckdate:v.checkDate,
								feetypedescr:v.feeTypeDescr,
								feetype:v.feeType,
								appsendno:v.fromAfterQty,
								iano:v.toAfterQty,
								amount:v.amount,
								hadamount:v.haveAmount,
								sendnohaveamount:v.sendNoHaveAmount,
								actname:v.actName,
								cardid:v.cardID,
								remarks:v.detailRemarks,
								lastupdate:new Date(),
								lastupdatedby:"${laborFee.appCZY}",
								actionlog:"Add",
								expired:"F",
						  	};
	  					$("#dataTable").setRowData(rowId,json);
					  	});
						$("#itemType1").attr("disabled","disabled");
				 	}
			  	} 
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
	//查看
	function view(){
		var ret= selectDataTableRow("dataTable");
		var rowId = $("#dataTable").jqGrid("getGridParam","selrow");
		$("#itemType1").removeAttr("disabled","disabled");
		var itemType1 = $.trim($("#itemType1").val());
	 	if(ret){
			Global.Dialog.showDialog("saveAdd",{
				title:"人工费用明细——查看",
				url:"${ctx}/admin/laborFee/goAddView",
				postData:{custCode:ret.custcode,
						actName:ret.actname,
						feeType:ret.feetype,
						amount:ret.amount,
						appSendNo:ret.appsendno,
						itemType1:itemType1,
						detailRemarks:ret.remarks,
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
	function validateRefresh(){
		$("#dataForm").data("bootstrapValidator")
				.updateStatus("openComponent_wareHouse_fromWHCode", "NOT_VALIDATED",null)  
                   .validateField("openComponent_wareHouse_fromWHCode")
                   .updateStatus("openComponent_wareHouse_toWHCode", "NOT_VALIDATED",null)  
                   .validateField("openComponent_wareHouse_toWHCode")
                   .updateStatus("date", "NOT_VALIDATED",null)  
                   .validateField("date")  ;
	}
	function setExcel(name,tableId){
		excelName=name;
		excelTable=tableId;
	}
	function doExcel(){
		doExcelNow(excelName,excelTable,'dataForm');
	}
	</script>
</head>
  
<body>
	<div class="body-box-form">
		<div class="content-form">
  			<div class="panel panel-system">
   				<div class="panel-body">
      				<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn" style="color:#777777; cursor:default">
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
										<input type="text" id="no" name="no" style="width:160px;" value="${laborFee.no }" readonly="readonly"/>                                             
									</li>
									<li>
										<label>状态</label>
										<house:xtdmMulit id="status" dictCode="LABORFEESTATUS" selectedValue="${laborFee.status}"></house:xtdmMulit>
									</li>
									<li>
										<label>申请日期</label>
										<input type="text" style="width:160px;" id="date" name="date" class="i-date" value="<fmt:formatDate value='${laborFee.date}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" disabled="true"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>户名</label>
										<input type="text" id="actName" name="actName" style="width:160px;" value="${laborFee.actName }"/>
									</li>
									<li>
										<label><span class="required">*</span>材料类型1</label>
										<select id="itemType1" name="itemType1" style="width: 160px;"  disabled="true"></select>
									</li>
									<li>
										<label>申请人</label>
										<input type="text" id="appCZY" name="appCZY" style="width:160px;" value="${laborFee.appCZY }"/>
									</li>
									<li class="form-validate">
										<label><span class="required">*</span>卡号</label>
										<input type="text" id="cardID" name="cardID" style="width:160px;" value="${laborFee.cardID }"/>
									</li>
									<li>
										<label><span class="required">*</span>开户行</label>
										<input type="text" id="bank" name="bank" style="width:160px;" value="${laborFee.bank }" />                                             
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
										<label>审核人</label>
										<input type="text" id="confirmCZY" name="confirmCZY" style="width:160px;" value="${laborFee.confirmCZY}" disabled="true"/>
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
	    		<div class="btn-group-sm">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
						<span>输出到excel</span>
					</button>
					<button type="button" class="btn btn-system" id="viewSendDetail" onclick="viewSendDetail()">
						<span>查看发货明细</span>
					</button>
					<button type="button" class="btn btn-system" id="viewItemReq" onclick="viewItemReq()">
						<span>查看材料需求</span>
					</button>
					<button type="button" class="btn btn-system" id="viewCutDetail" onclick="viewCutDetail()">
						<span>查看加工明细</span>
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
		      	<li>
                    <a href="#tab_custType" data-toggle="tab" onclick="setExcel('按客户类型汇总','dataTable_custType')">按客户类型汇总</a>
                </li>
                <li>
                    <a href="#tab_company" data-toggle="tab" onclick="setExcel('按公司汇总','dataTable_company')">按公司汇总</a>
                </li>
		   	</ul>	
		   	<div class="tab-content">  
				<div id="content-list1" class="tab-pane fade in active">
					<table id="dataTable"></table>
				</div>	
		        <div id="tab_account"  class="tab-pane fade "> 
		         	<jsp:include page="tab_account.jsp"></jsp:include>
		        </div> 
		        <div id="tab_custType"  class="tab-pane fade" > 
                    <jsp:include page="tab_custType.jsp"></jsp:include>
                </div>
                <div id="tab_company"  class="tab-pane fade" > 
                    <jsp:include page="tab_company.jsp"></jsp:include>
                </div>
		    </div>
		    
		</div>
	</div>
</body>
</html>
