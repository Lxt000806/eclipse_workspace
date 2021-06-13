<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增结算-新增</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">
			var returnJsonData = {
				selectRows:[],
				maxCheckSeq:0
			};
			$(function(){
				$("#splCode").openComponent_supplier({
					showValue:"${data.splCode}",
					showLabel:"${data.splName}"
				});
	   			$("#openComponent_supplier_splCode").attr("readonly", true);
				$("#openComponent_supplier_splCode").next().attr("data-disabled", true).css({
					"color":"#888"
				});
				$("#cmpCode").openComponent_company();
				$("#openComponent_company_cmpCode").attr("readonly", true);
				Global.JqGrid.initEditJqGrid("addPurchaseDataTable", {
					url:"${ctx}/admin/supplierCheck/goJqGridAddPurchase",
					postData:$("#addPurchaseDataForm").jsonForm(),
					height:300,
					rowNum:1000000,
					colModel : [			
						{name: "ischeckout", index: "ischeckout", width: 60, label: "是否结帐", sortable: false, align: "center", formatter:"checkbox", editoptions: {value:"1:0"}, formatoptions:{disabled:false}},
						{name: "no", index: "no", width: 80, label: "采购单号", sortable: true, align: "left"},
						{name: "appno", index: "appno", width: 80, label: "领料单号", sortable: true, align: "left"},
						{name: "isservicedescr", index: "isservicedescr", width: 85, label: "是否服务产品", sortable: true, align: "left"},
						{name: "documentno", index: "documentno", width: 100, label: "档案号", sortable: true, align: "left"},
						{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
						{name: "typedescr", index: "typedescr", width: 80, label: "采购单类型", sortable: true, align: "left"},
						{name: "date", index: "date", width: 120, label: "采购日期", sortable: true, align: "left", formatter: formatTime},
						{name: "othercost", index: "othercost", width: 65, label: "其它费用", sortable: true, align: "right", sum: true},
						{name: "othercostadj", index: "othercostadj", width: 95, label: "其它费用调整", sortable: true, align: "right", sum: true, editable:true, editrules: {number:true,required:true}},
						{name: "amount", index: "amount", width: 104, label: "amount", sortable: true, align: "right", sum: true, hidden: true},
						{name: "showamount", index: "showamount", width: 70, label: "材料金额", sortable: true, align: "right", sum: true},
						{name: "splamount", index: "splamount", width: 70, label: "对账金额", sortable: true, align: "right", editable:true, editrules: {number:true,required:true}},
						{name: "diffamount", index: "diffamount", width: 50, label: "差额", sortable: true, align: "right", editable:true, editrules: {number:true,required:true}},
						{name: "sumamount", index: "sumamount", width: 60, label: "总金额", sortable: true, align: "right", sum: true},
						{name: "firstamount", index: "firstamount", width: 70, label: "已付定金", sortable: true, align: "right", editable:true, editrules: {number:true,required:true}},
						{name: "remainamount", index: "remainamount", width: 70, label: "应付余额", sortable: true, align: "right", editable:true, editrules: {number:true,required:true},formatter:"number", formatoptions:{decimalPlaces: 2}},
						{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
						{name: "type", index: "type", width: 160, label: "type", sortable: true, align: "left", hidden: true},
						{name: "delivtype", index: "delivtype", width: 160, label: "delivtype", sortable: true, align: "left", hidden: true},
						{name: "checkseq", index: "checkseq", width: 160, label: "checkseq", sortable: true, align: "left", hidden: true},
						
						{name: "status", index: "status", width: 160, label: "status", sortable: true, align: "left", hidden: true},
						{name: "whcode", index: "whcode", width: 160, label: "whcode", sortable: true, align: "left", hidden: true},
						{name: "supplier", index: "supplier", width: 160, label: "supplier", sortable: true, align: "left", hidden: true},
						{name: "checkoutno", index: "checkoutno", width: 160, label: "checkoutno", sortable: true, align: "left", hidden: true},
						{name: "xmjljsj", index: "xmjljsj", width: 160, label: "xmjljsj", sortable: true, align: "left", hidden: true},
						{name: "itemtype1", index: "itemtype1", width: 160, label: "itemtype1", sortable: true, align: "left", hidden: true},
						{name: "secondamount", index: "secondamount", width: 160, label: "secondamount", sortable: true, align: "left", hidden: true},
						{name: "issetitemdescr", index: "issetitemdescr", width: 79, label: "issetitemdescr", sortable: true, align: "left", hidden: true},
						{name: "itemtype2descr", index: "itemtype2descr", width: 79, label: "itemtype2descr", sortable: true, align: "left", hidden: true},
						{name: "warehousedesc", index: "warehousedesc", width: 79, label: "warehousedesc", sortable: true, align: "left", hidden: true},
						{name: "chaochue", index: "chaochue", width: 79, label: "chaochue", sortable: true, align: "left", hidden: true},
						{name: "projectothercost", index: "projectothercost", width: 79, label: "projectothercost", sortable: true, align: "left", hidden: true},
						{name: "xmjljszj", index: "xmjljszj", width: 79, label: "xmjljszj", sortable: true, align: "left", hidden: true},
						{name: "intinstallfee", index: "intinstallfee", width: 79, label: "intinstallfee", sortable: true, align: "left", hidden: true},
						{name: "sumsaleamount", index: "sumsaleamount", width: 79, label: "sumsaleamount", sortable: true, align: "left", hidden: true},
						{name: "payremark", index: "payremark", width: 79, label: "payremark", sortable: true, align: "left", hidden: true},
						{name: "checkstatusdescr", index: "checkstatusdescr", width: 79, label: "checkstatusdescr", sortable: true, align: "left", hidden: true},
						{name: "processcost", index: "processcost", width: 79, label: "processcost", sortable: true, align: "left", hidden: true},
						{name: "isservice", index: "isservice", width: 99, label: "是否服务产品", sortable: true, align: "left",hidden:true},
						{name: "custcode", index: "custcode", width: 99, label: "custcode", sortable: true, align: "left",hidden:true},
						{name: "cmpname", index: "cmpname", width: 99, label: "签约公司", sortable: true, align: "left",hidden:true},
						{name: "custtype", index: "custtype", width: 99, label: "custtype", sortable: true, align: "left",hidden:true},
						{name: "custtypedescr", index: "custtypedescr", width: 99, label: "custtypedescr", sortable: true, align: "left",hidden:true},
						{name : "splstatusdescr",index : "splstatusdescr",width : 80,label:'供应商状态',sortable : true,align : "left"},
                        {name : "splstatus",index : "splstatus",width : 80,label:'供应商状态',sortable : true,align : "left",hidden:true},
                        {name: "sourcedescr", index: "sourcedescr", width: 70, label: "客户来源", sortable: true, align: "left",hidden:true},
                        {name: "checkconfirmremarks", index: "checkconfirmremarks", width: 120, label: "结算审核说明", sortable: true, align: "left",hidden:true},
		     
		            ],
		            gridComplete:function(){
		            	$("#addPurchaseDataTable tbody tr[class~=jqgrow] td[aria-describedby=addPurchaseDataTable_ischeckout] input").on("click", function(){
		            		var choiceId = $(this).parent().parent()[0].id;
		            		var checks = $("#addPurchaseDataTable tbody tr[class~=jqgrow] td[aria-describedby=addPurchaseDataTable_ischeckout] input[value=1]");
		            		var checksNum = checks.length;
			  				var choiceCheckSeq = $(this).parent().parent().children("[aria-describedby=addPurchaseDataTable_checkseq]")[0].title;
		            		if($(this).val() == "0"){
		            			$(this).val("1");
			            		$("#addPurchaseDataTable").jqGrid("setCell", choiceId, "checkseq", checksNum+1);
		            		}else{
		            			$(this).val("0");
			            		for(var i = 0;i < checks.length;i++){
			            			var checkseq = parseInt($(checks[i]).parent().parent().children("[aria-describedby=addPurchaseDataTable_checkseq]")[0].title);
			            			if($(checks[i]).parent().parent()[0].id != choiceId 
			            				&& checkseq > parseInt(choiceCheckSeq)){
			            				
			            				$("#addPurchaseDataTable").jqGrid("setCell", $(checks[i]).parent().parent()[0].id, "checkseq", checkseq-1);
			            			}
			            		}
		            		}		            		
		            		$("#addPurchaseDataTable").jqGrid("setSelection", choiceId);
		            	});
		            	var checkSeq = 1;
		            	var rets = $("#addPurchaseDataTable tbody tr[class~=jqgrow] td[aria-describedby=addPurchaseDataTable_ischeckout] input[value=1]");

		            	for(var i = 0; i < rets.length ; i++){
		            		$("#addPurchaseDataTable").jqGrid("setCell", $(rets[i]).parent().parent()[0].id, "checkseq", checkSeq++);
		            	}
		            	var rowData = $("#addPurchaseDataTable").jqGrid('getRowData');
						$.each(rowData,function(k,v){
			             	if(v.splstatus == "2"){ 
			             		$("#addPurchaseDataTable").jqGrid('setCell', k+1, 'projectothercost', '', 'not-editable-cell');
			             		$("#addPurchaseDataTable").jqGrid('setCell', k+1, 'othercost', '', 'not-editable-cell');
			             		$("#addPurchaseDataTable").jqGrid('setCell', k+1, 'othercostadj', '', 'not-editable-cell');	
			             		$("#addPurchaseDataTable").jqGrid('setCell', k+1, 'splamount', '', 'not-editable-cell');
			             		$("#addPurchaseDataTable").jqGrid('setCell', k+1, 'diffamount', '', 'not-editable-cell');	
			             	}
			            })
		            },
					beforeSelectRow:function(id){
						setTimeout(function(){
							relocate(id, "addPurchaseDataTable");
						},10);
					},
					beforeSaveCell:function(rowId, name, val, iRow, iCol){
						var ret = $("#addPurchaseDataTable").jqGrid("getRowData", rowId);
						ret[name]=val;
						$("#addPurchaseDataTable").jqGrid("setCell", rowId, "sumamount", parseFloat(ret.othercost)+parseFloat(ret.othercostadj)+(ret.type=="S"?parseFloat(ret.amount):-1.0*parseFloat(ret.amount)));
						$("#addPurchaseDataTable").footerData("set", {
							sumamount:$("#addPurchaseDataTable").getCol("sumamount", false, "sum") 
						});
					}
				});
			});
			function isNormalChange(){
				if($("#isNormal").val() == "T"){
					$("#isNormal").val("F");
				}else{
					$("#isNormal").val("T");
				}
			}
			function goto_query(){
				var data = $("#addPurchaseDataForm").jsonForm();
				
				$("#addPurchaseDataTable").jqGrid("setGridParam", {
					url:"${ctx}/admin/supplierCheck/goJqGridAddPurchase",
					postData:$("#addPurchaseDataForm").jsonForm(),
					page:1
				}).trigger("reloadGrid");
			}
			function view(){
				var ret = selectDataTableRow("addPurchaseDataTable");
				if(ret){
					var title = "";
					if(ret.type == "S"){
						if(ret.delivtype == "1"){
							title = "采购单--查看";
						}else{
							title = "采购到工地--查看";
						}
					}else{
						if(ret.delivtype == "1"){
							title = "采购退回--查看";
						}else{
							title = "工地退回--查看";
						}
					}
					var url = "";
					
					if(ret.delivtype=="1"){
						url="${ctx}/admin/purchase/goViewNew";
					}else{
						url="${ctx}/admin/purchase/goViewNew2";
					}
			       	Global.Dialog.showDialog("PurhaseView",{
						title:title,
			       	 	url:url,
			       	  	postData:{
			       	  		id:ret.no,
			       	  		fromPage:"supplierCheck",
			       	  		isService:ret.isservice
			       	  	},
			       	  	height: 730,
			       	  	width:1250
			       	}); 
				}else{
					art.dialog({
						content:"请选择一条记录"
					});
				}
			}
			function save(){
				var rets = $("#addPurchaseDataTable tbody tr[class~=jqgrow] td[aria-describedby=addPurchaseDataTable_ischeckout] input[value=1]");
				if(rets.length == 0){
					art.dialog({
						content:"请选择要结算的相应信息"
					});
					return;
				}	
				var nos = $("#nos").val();
				for(var i=0;i<rets.length;i++){
					if(nos != ""){
						nos += ",";
					}
					nos += $(rets[i]).parent().parent().children("td[aria-describedby=addPurchaseDataTable_no]").attr("title").trim();
					var data = $("#addPurchaseDataTable").jqGrid("getRowData", $(rets[i]).parent().parent().attr("id"));
					data.checkseq = parseInt(data.checkseq) + parseInt($("#maxCheckSeq").val());
					//data.xmjljsj = 0;
					returnJsonData.selectRows.push(data);
				}
				var maxCheckSeqIndex = 0;
				for(var i = 1;i < returnJsonData.selectRows.length;i++){
					if(parseInt(returnJsonData.selectRows[i].checkseq) >= parseInt(returnJsonData.selectRows[maxCheckSeqIndex].checkseq)){
						maxCheckSeqIndex = i;
					}
				}
				$("#maxCheckSeq").val(returnJsonData.selectRows[maxCheckSeqIndex].checkseq);
				$("#nos").val(nos);
				goto_query();
			}
			function selectAllChange(){
				if($("#selectAll").val() == "T"){
					$("#selectAll").val("F");
				}else{
					$("#selectAll").val("T");
				}
				goto_query();
			}
			function addPurchCloseWin(){
				returnJsonData.maxCheckSeq = parseInt($("#maxCheckSeq").val());
    			Global.Dialog.returnData = returnJsonData;
				closeWin();
			}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
	    				<button id="saveBut" type="button" class="btn btn-system" onclick="save()">确认新增</button>
	    				<button id="viewBut" type="button" class="btn btn-system" onclick="view()">查看</button>
	    				<button id="closeBut" type="button" class="btn btn-system" onclick="addPurchCloseWin()">关闭</button>
					</div>
				</div>
			</div>
			<input type="hidden" id="items" name="items" value=""/>
			<div class="panel panel-info" >  
				<div class="panel-body" style="padding:0px;">
					<form action="" method="post" id="addPurchaseDataForm" class="form-search">
						<input type="hidden" id="jsonString" name="jsonString" value=""/>
						<input type="hidden" id="m_umState" name="m_umState" value="${data.m_umState}"/>
						<input type="hidden" id="nos" name="nos" value="${data.nos}"/>
						<input type="hidden" id="maxCheckSeq" name="maxCheckSeq" value="${data.maxCheckSeq}"/>
						<input type="hidden" id="checkOutNo" name="checkOutNo" value="${data.checkOutNo }"/>
						<ul class="ul-form">
							<li>
								<label>供应商编号</label>
								<input type="text" id="splCode" name="splCode" value="${data.splCode}"/>
								<input type="hidden" id="splCodeDescr" name="splCodeDescr" value="${data.splName}"/>
							</li>
							<li>
								<label>公司</label>
								<input type="text" id="cmpCode" name="cmpCode" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address"/>
							</li>
							<li>
								<label>档案号</label>
								<input type="text" id="documentNo" name="documentNo"/>
							</li>
							<li>
								<label>采购类型</label>
								<house:xtdm id="purchType" dictCode="PURCHTYPE"></house:xtdm>
							</li>
							<li>
								<label>采购日期从</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
									   onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label>至</label>
								<input type="text" id="dateTo" name="dateTo" class="i-date" 
									   onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li class="search-group-shrink">
								<input type="checkbox" id="isNormal" name="isNormal" onclick="isNormalChange()" value="F"/><p>包含到货异常单&nbsp;&nbsp;&nbsp;&nbsp;</p>
								<input type="checkbox" id="selectAll" name="selectAll" onclick="selectAllChange()" value=""/><p>全选</p>
								<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							</li>
						</ul>
					</form>
				</div>
			</div>
			<div  class="container-fluid" >  
				<table id="addPurchaseDataTable"></table>
			</div>	
		</div>
	</body>
</html>


