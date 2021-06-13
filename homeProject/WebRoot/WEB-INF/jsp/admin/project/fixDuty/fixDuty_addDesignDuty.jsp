<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="exportData" id="exportData">
					<input type="hidden" name="jsonString" value="" /> 
					<input type="hidden" name="dataChanged" value="0" /> 
					<input type="hidden" id="workType12" name="workType12" value="${fixDuty.workType12 }" /> 
					<input type="hidden" id="appWorkerCode" name="appWorkerCode" value="${fixDuty.appWorkerCode }" />
					<input type="hidden" id="prjManRiskFund" name="prjManRiskFund" />
					<input type="hidden" id="designRiskFund" name="designRiskFund" />
					<input type="hidden" id="designMan" name="designMan" />
					<ul class="ul-form">
						<li class="form-validate"><label>定责申请编号</label> <input
							type="text" id="no" name="no" style="width:160px;"
							readonly="readonly" value="${fixDuty.no}" placeholder="保存时生成" />
						</li>
						<li><label><span class="required">*</span>客户编号</label> <input type="text" id="custCode"
							name="custCode" style="width:160px;"
							value="${fixDuty.custCode}">
						</li>
						<li><label>楼盘</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${fixDuty.address}">
						</li>
						<li><label>申请人</label> <input type="text" id="appCzy"
							name="appCzy" style="width:160px;" readonly="readonly"
							value="${fixDuty.appCzy}">
						</li>
						<li><label>人工金额</label> <input type="text" id="offerPrj"
                            name="offerPrj" style="width:160px;" readonly="readonly"
                            value="${fixDuty.offerPrj}" />
                        </li>
                        <li><label>材料金额</label> <input type="text" id="material"
                            name="material" style="width:160px;" readonly="readonly"
                            value="${fixDuty.material}">
                        </li>
						<li><label class="control-textarea">备注</label> <textarea
								id="remarks" name="remarks" rows="4">${fixDuty.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_man" data-toggle="tab">定责人员</a></li>
				<li class=""><a href="#tab_detail" data-toggle="tab">定责明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_man" class="tab-pane fade in active">
					<div class="pageContent">
						<div class="panel panel-system">
							<div class="panel-body">
								<div class="btn-group-xs">
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="m_add()">
										<span>新增 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="m_update()">
										<span>编辑 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system viewFlag" onclick="m_del()">
										<span>删除 </span>
									</button>
									<button style="align:left" type="button"
										class="btn btn-system " onclick="m_view()">
										<span>查看 </span>
									</button>
								</div>
							</div>
						</div>
						<div id="content-list">
							<table id="dataTable_man"></table>
						</div>
					</div>
				</div>
				<div id="tab_detail" class="tab-pane fade">
				    <div class="pageContent">
				        <div class="panel panel-system">
				            <div class="panel-body">
				                <div class="btn-group-xs">
				                    <button style="align:left" type="button"
				                            class="btn btn-system viewFlag" onclick="d_add()">
				                        <span>新增 </span>
				                    </button>
				                    <button style="align:left" type="button"
				                            class="btn btn-system viewFlag" onclick="d_update()">
				                        <span>编辑 </span>
				                    </button>
				                    <button style="align:left" type="button"
				                            class="btn btn-system viewFlag" onclick="d_del()">
				                        <span>删除 </span>
				                    </button>
				                    <button style="align:left" type="button"
				                            class="btn btn-system " onclick="d_view()">
				                        <span>查看 </span>
				                    </button>
				                </div>
				            </div>
				        </div>
				        <div id="content-list">
				            <table id="dataTable_detail"></table>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("#custCode").openComponent_customer({
			callBack : function(data){
				$("#designMan").val(data.designman);
				$("#designRiskFund").val(data.designriskfund);
				$("#prjManRiskFund").val(data.prjmanriskfund);
				$("#address").val(data.address);
			}	
		});
		
		var manGridOption = {
			sortable : true,
			height : 210,
			rowNum : 10000000,
			colModel : [ 
				{name: "faulttypedescr", index: "faulttypedescr", width: 100, label: "责任人类型", sortable: true, align: "left"},
				{name: "empnamechi", index: "empnamechi", width: 70, label: "员工姓名", sortable: true, align: "left"},
				{name: "workernamechi", index: "workernamechi", width: 70, label: "工人姓名", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 70, label: "供应商", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 80, label: "金额", sortable: true, align: "right",sum:true},
				{name: "riskfund", index: "riskfund", width: 100, label: "使用风控基金", sortable: true, align: "right",sum:true},
				{name: "issalarydescr", index: "issalarydescr", width: 70, label: "正常工资", sortable: true, align: "left"},
				{name: "confirmamount", index: "confirmamount", width: 70, label: "已发工资", sortable: true, align: "right"},
				{name: "paidamount", index: "paidamount", width: 80, label: "已支付金额", sortable: true, align: "right", hidden: true},
				{name: "remark", index: "remark", width: 180, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
				{name: "no", index: "no", width: 70, label: "no", sortable: true, align: "left",hidden:true},
				{name: "empcode", index: "empcode", width: 70, label: "empcode", sortable: true, align: "left",hidden:true},
				{name: "workercode", index: "workercode", width: 70, label: "workercode", sortable: true, align: "left",hidden:true},
				{name: "supplcode", index: "supplcode", width: 70, label: "supplcode", sortable: true, align: "left",hidden:true},
				{name: "issalary", index: "issalary", width: 70, label: "issalary", sortable: true, align: "left",hidden:true},
				{name: "faulttype", index: "faulttype", width: 70, label: "faulttype", sortable: true, align: "left",hidden:true},
				{name: "pk", index: "pk", width: 70, label: "pk", sortable: true, align: "left",hidden:true}
			],
		};
		Global.JqGrid.initJqGrid("dataTable_man", manGridOption)
		
		var detailGridOption = {
		    sortable: true,
		    height: 230,
		    rowNum: 10000000,
		    colModel: [
		        {name: "baseitemcode", index: "baseitemcode", width: 120, label: "基础项目", sortable: true, align: "left", hidden: true},
		        {name: "baseitemdescr", index: "baseitemdescr", width: 280, label: "基础项目", sortable: true, align: "left"},
		        {name: "qty", index: "qty", width: 80, label: "数量", sortable: true, align: "right"},
		        {name: "offerpri", index: "offerpri", width: 70, label: "人工单价", sortable: true, align: "right"},
		        {name: "material", index: "material", width: 70, label: "材料单价", sortable: true, align: "right"},
		        {name: "allofferpri", index: "allofferpri", width: 70, label: "人工总价", sortable: true, align: "right", sum: true},
		        {name: "allmaterial", index: "allmaterial", width: 70, label: "材料总价", sortable: true, align: "right", sum: true},
		        {name: "remark", index: "remark", width: 200, label: "描述", sortable: true, align: "left"},
		        {name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime, hidden: true},
		        {name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left", hidden: true},
		        {name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left", hidden: true},
		        {name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left", hidden: true}],
		}
		Global.JqGrid.initJqGrid("dataTable_detail", detailGridOption)
	});
	
	function m_add(){
		var ids=$("#dataTable_man").jqGrid("getDataIDs");
		var ret = $("#dataTable_man").jqGrid('getRowData',ids[ids.length-1]);
		var ids=$("#dataTable_man").jqGrid("getDataIDs");
		var newId=1;
		if($("#custCode").val()==""){
			art.dialog({
				content: "请先选择客户编号！"
			});
			return;
		}
		if(ids.length>0){
			newId=parseInt(ids[ids.length-1],0)+1;
		} 
		var confirmAmount = 0;
		if(ret.length>0){
			confirmAmount = ret.confirmamount;
		}
		var balances = calculateBalances()
		var leftRiskFundJson=leftRiskFund();
		Global.Dialog.showDialog("m_save", {
			title : "定责人员--增加",
			url : "${ctx}/admin/fixDuty/goManAdd",
			postData:{
				m_umState:"A",
				fromPage: "designer",
				no:"${map.no}",
				projectMan:"${map.projectman}",
				projectManDescr:"${map.projectmandescr}",
				appManType:"${map.appmantype}",
				appWorkerCode:"${map.appworkercode}",
				appManDescr: "${map.appmandescr}",
				custCode:$("#custCode").val(),
				confirmAmount:confirmAmount,
				leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
				leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
				totalDiscBalance: balances.totalDiscBalance,
                discBalance: balances.discBalance,
				designMan:$("#designMan").val(),
				isAddDesign:"1"
			},
		    height:450,
		    width:720,
			returnFun : function(v) {
				
				var json = {
					faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
					workernamechi:v.workerNamechi,suppldescr:v.supplDescr,issalarydescr:v.isSalaryDescr,
					empcode:v.empCode,workercode:v.workerCode,supplcode:v.supplCode,
					amount:v.amount,empnamechi:v.empNamechi,pk:(ids.length+1)*-1,
					issalary:v.isSalary,faulttype:v.faultType, paidamount: v.paidAmount,
					expired:"F",actionlog:"ADD",lastupdate:new Date(),lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
					remark:v.remark,confirmamount:(v.workerCode == "" ? "": v.confirmAmount),riskfund:v.riskFund
				};									
				$("#dataTable_man").jqGrid("addRowData", newId, json);
				$("#dataChanged").val("1");
			}
		});
	}
	function m_update(){
		var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
		var ret = $("#dataTable_man").jqGrid('getRowData',id);
		if(!id){
			art.dialog({
				content: "请选择一条记录！",width: 200
			});
			return false;
		} 
		var leftRiskFundJson=leftRiskFund();
		var balances = calculateBalances()
		Global.Dialog.showDialog("m_update", {
			title : "定责人员--编辑",
			url : "${ctx}/admin/fixDuty/goManUpdate",
			postData:{
				map:JSON.stringify(ret),
				m_umState:"M",
				fromPage: "designer",
				no:"${map.no}",
				projectMan:"${map.projectman}",
				projectManDescr:"${map.projectmandescr}",
				appManType:"${map.appmantype}",
				appWorkerCode:"${map.appworkercode}",
				appManDescr: "${map.appmandescr}",
				custCode: $("#custCode").val(),
				confirmAmount:ret.confirmamount,
				leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
				leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
				totalDiscBalance: balances.totalDiscBalance,
                discBalance: balances.discBalance,
				designMan:$("#designMan").val(),
				isAddDesign:"1"
			},
		    height:450,
		    width:720,
			returnFun : function(v) {
				var json = {
					faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
					workernamechi:v.workerNamechi,suppldescr:v.supplDescr,issalarydescr:v.isSalaryDescr,
					empcode:v.empCode,workercode:v.workerCode,supplcode:v.supplCode,
					faulttypedescr:v.faultTypeDescr,amount:v.amount,empnamechi:v.empNamechi,
					issalary:v.isSalary,faulttype:v.faultType,pk:v.pk, paidamount: v.paidAmount,
					expired:"F",actionlog:"EDIT",lastupdate:new Date(),lastupdatedby:"${sessionScope.USER_CONTEXT_KEY.czybh}",
					remark:v.remark,confirmamount:(v.workerCode == "" ? "": v.confirmAmount),riskfund:v.riskFund
				};
				$("#dataTable_man").setRowData(id, json);
				$("#dataChanged").val("1");
				//编辑时更新合计金额
				var sumAmount=parseFloat($("#dataTable_man").footerData().amount);
				$("#dataTable_man").footerData('set', { "amount": sumAmount-parseFloat(ret.amount)+parseFloat(json.amount)});   
			}
		});
	}
	function m_view(){
		var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
		var ret = $("#dataTable_man").jqGrid('getRowData',id);
		if(!id){
			art.dialog({
				content: "请选择一条记录！",width: 200
			});
			return false;
		} 
		var leftRiskFundJson=leftRiskFund();
		var balances = calculateBalances()
		Global.Dialog.showDialog("m_view", {
			title : "定责人员--查看",
			url : "${ctx}/admin/fixDuty/goManUpdate",
			postData:{
				map:JSON.stringify(ret),
				m_umState:"V",
				fromPage: "designer",
				no:"${map.no}",
				projectMan:"${map.projectman}",
				projectManDescr:"${map.projectmandescr}",
				appManType:"${map.appmantype}",
				appWorkerCode:"${map.appworkercode}",
				appManDescr: "${map.appmandescr}",
				custCode: $("#custCode").val(),
				confirmAmount:ret.confirmamount,
				leftDesignRiskFund:leftRiskFundJson.leftDesignRiskFund,
				leftPrjManRiskFund:leftRiskFundJson.leftPrjManRiskFund,
				totalDiscBalance: balances.totalDiscBalance,
				discBalance: balances.discBalance,
				designMan:$("#designMan").val(),
				isAddDesign:"1"
			},
		    height:450,
		    width:720
		});
	}
	//删除
	function m_del(){
	 	var id = $("#dataTable_man").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({
				content: "请选择一条记录进行删除操作！"
			});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable_man",id);
				$("#dataChanged").val("1");
			},
			cancel: function () {
					return true;
			}
		}); 
	}
		$("#saveBtn").on("click", function() {
			var custCode=$("#custCode").val();
			if(custCode==""){
				art.dialog({
					content : "请填写客户编号！",
				});
				return;
			}
			var unPassType=isOverRiskFund();
			if(unPassType=="1"){
			    // 不再限制设计师风控基金是否超支
// 				art.dialog({
// 					content : "累计已使用设计师风控基金超出可使用风控基金总额，保存失败！",
// 				});
// 				return;
			}else if(unPassType=="2"){
				art.dialog({
					content : "累计已使用项目经理风控基金超出可使用风控基金总额，保存失败！",
				});
				return;
			}
			var datas = $("#page_form").jsonForm();
			var json = {};
			var rows = $("#dataTable_man").jqGrid("getRowData");
			json['fixDutyManJson'] = JSON.stringify(rows);
		 	if (rows.length == 0) {
				art.dialog({
					content : "请先添加定责人员信息",
					width : 220
				});
				return;
			}
			
			var detailRows = $("#dataTable_detail").jqGrid("getRowData")
			json['detailJson'] = JSON.stringify(detailRows)
			
			$.extend(datas, json);
			doSave(datas);  
		});
	
	function doSave(param) {
		$.ajax({
			url : "${ctx}/admin/fixDuty/doSaveDeignDuty",
			type : "post",
			data : param,
			dataType : "json",
			cache : false,
			error : function(obj) {
				showAjaxHtml({
					"hidden" : false,
					"msg" : "保存数据出错"
				});
			},
			success : function(obj) {
				if (obj.rs) {
					art.dialog({
						content : obj.msg,
						time : 1000,
						beforeunload : function() {
							closeWin();
						}
					});
				} else {
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content : obj.msg,
						width : 200
					});
				}
			}
		});
	}
	
	//风控基金余额
	// 总优惠余额
	function leftRiskFund(){
		var designRiskFund=$("#designRiskFund").val();//楼盘可使用设计师风控基金
		var prjManRiskFund=$("#prjManRiskFund").val();//楼盘可使用项目经理风控基金
		var thisDesignRiskFund=0;//本单累计设计师风控金额
		var thisPrjManRiskFund=0;//本单累计项目经理风控金额
		var otherRiskFundJson=OtherRiskFund();
		var otherDesignRiskFund=parseFloat(otherRiskFundJson.DesignRiskFund);//其他单累计设计师风控金额
		var otherPrjManRiskFund=parseFloat(otherRiskFundJson.PrjManRiskFund);//其他单累计项目经理风控金额
		var designMan=$("#designMan").val();//楼盘设计师
		var rowData= $("#dataTable_man").jqGrid('getRowData');
		$.each(rowData, function(k, v){//员工编号与楼盘设计师相同的判断设计师风控基金，否则判断项目经理风控基金
			if(v.faulttype=="1" && v.empcode==designMan){
				thisDesignRiskFund+=parseFloat(v.riskfund);
			}else{
				thisPrjManRiskFund+=parseFloat(v.riskfund);
			}
		});
		
		var leftDesignRiskFund=designRiskFund-(thisDesignRiskFund+otherDesignRiskFund);
		var leftPrjManRiskFund=prjManRiskFund-(thisPrjManRiskFund+otherPrjManRiskFund);
		var returnJson = {
		    leftDesignRiskFund: leftDesignRiskFund,
		    leftPrjManRiskFund: leftPrjManRiskFund,
		};
		
		return returnJson;
	}
	//是否超过可使用风控基金总额
	function isOverRiskFund(){
		var unPassType="0";//无法通过的标识，默认0通过
		var leftRiskFundJson=leftRiskFund();
		if(leftRiskFundJson.leftDesignRiskFund<0){//设计师风控基金余额小于0
			unPassType="1";
		}
		if(leftRiskFundJson.leftPrjManRiskFund<0){//项目经理风控基金余额小于0
			unPassType="2";
		}
		return unPassType;	
	}
	//其他单累计风控金额
	function OtherRiskFund(){
		var riskFundJson={};
		$.ajax({
			url:"${ctx}/admin/fixDuty/getOtherRiskFund",
			type:"post",
	    	data:{custCode:$("#custCode").val(),no:$("#no").val()},
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
			success:function(data){
				riskFundJson=data;
			}
		});	
		return riskFundJson;
	}
	
	function calculateBalances() {
	    var discounts = summarizeDiscounts()
	    var designMan = $("#designMan").val()
	    var grid = $("#dataTable_man")
	    var ids = grid.getDataIDs()
	    
	    var sumFrontendRiskOfThisDuty = 0
	    for (var i = 0; i < ids.length; i++) {
	        var rowData = grid.getRowData(ids[i])
	        
	        if (rowData.faulttype === "1"
	            && rowData.empcode === designMan) {
	            sumFrontendRiskOfThisDuty += parseFloat(rowData.riskfund)
	        }
	    }
	    	    
	    return {
	        totalDiscBalance: discounts.TotalDiscBalance - sumFrontendRiskOfThisDuty,
	        discBalance: discounts.DiscBalance
	    }
	}
	
	function summarizeDiscounts(){
		var discounts = {}
		
		$.ajax({
			url: "${ctx}/admin/fixDuty/summarizeDiscounts",
			type: "post",
	    	data: {
	    	    custCode: $("#custCode").val(),
	    	    fixDutyNo: "-1"
	    	},
			dataType: "json",
			cache: false,
			async: false,
			success: function(data) {
				discounts = data
			}
		})
		
		return discounts
	}
	
	function d_add() {
	    if (!$("#custCode").val()) {
	        art.dialog({
	            content: "请先选择客户编号！",
	        })
	        return
	    }
	    Global.Dialog.showDialog("detailAdd", {
	        title: "定责明细--增加",
	        url: "${ctx}/admin/fixDuty/goDetailAdd",
	        postData: {
	            m_umState: "A",
	            fromPage: "designer",
	            workType12: $("#workType12").val(),
	            custCode: $("#custCode").val()
	        },
	        height: 400,
	        width: 720,
	        returnFun: function(v) {
	            var json = {
	                baseitemdescr: v[0].baseItemDescr,
	                baseitemcode: v[0].baseItemCode,
	                material: v[0].material,
	                offerpri: v[0].offerPri,
	                qty: v[0].qty,
	                allofferpri: parseFloat(v[0].offerPri) * parseFloat(v[0].qty),
	                allmaterial: parseFloat(v[0].material) * parseFloat(v[0].qty),
	                expired: "F",
	                actionlog: "ADD",
	                lastupdate: new Date(),
	                lastupdatedby: "${sessionScope.USER_CONTEXT_KEY.czybh}",
	                remark: v[0].remarks
	            }
	            Global.JqGrid.addRowData("dataTable_detail", json)
	            $("#offerPrj").val($("#dataTable_detail").getCol("allofferpri", false, "sum"))
	            $("#material").val($("#dataTable_detail").getCol("allmaterial", false, "sum"))
	        }
	    })
	}
	
	function d_update() {
	    var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow")
	    var ret = $("#dataTable_detail").jqGrid('getRowData', id)
	    if (!id) {
	        art.dialog({
	            content: "请选择一条记录！",
	            width: 200
	        })
	        return false
	    }
	    Global.Dialog.showDialog("detailUpdate", {
	        title: "定责明细--编辑",
	        url: "${ctx}/admin/fixDuty/goDetailUpdate",
	        postData: {
	            m_umState: "M",
	            fromPage: "designer",
	            workType12: $("#workType12").val(),
	            custCode: $("#custCode").val(),
	            baseCheckItemCode: "",
	            baseCheckItemDescr: "",
	            baseItemCode: ret.baseitemcode,
	            baseItemDescr: ret.baseitemdescr,
	            qty: ret.qty,
	            offerPri: ret.offerpri,
	            material: ret.material,
	            remarks: ret.remark
	        },
	        height: 400,
	        width: 720,
	        returnFun: function(v) {
	            var json = {
	                baseitemdescr: v[0].baseItemDescr,
	                baseitemcode: v[0].baseItemCode,
	                material: v[0].material,
	                offerpri: v[0].offerPri,
	                qty: v[0].qty,
	                allofferpri: parseFloat(v[0].offerPri) * parseFloat(v[0].qty),
	                allmaterial: parseFloat(v[0].material) * parseFloat(v[0].qty),
	                expired: "F",
	                actionlog: "EDIT",
	                lastupdate: new Date(),
	                lastupdatedby: "${sessionScope.USER_CONTEXT_KEY.czybh}",
	                remark: v[0].remarks
	            }
	            $("#dataTable_detail").setRowData(id, json)
	            $("#dataChanged").val("1")
	            var allOfferPri = $("#dataTable_detail").getCol("allofferpri", false, "sum")
	            var allMaterial = $("#dataTable_detail").getCol("allmaterial", false, "sum")
	            $("#dataTable_detail").footerData("set", {"allofferpri": myRound(allOfferPri, 2)})
	            $("#dataTable_detail").footerData("set", {"allmaterial": myRound(allMaterial, 2)})
	            $("#offerPrj").val(allOfferPri)
	            $("#material").val(allMaterial)
	        }
	    })
	}
	
	function d_view() {
	    var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow")
	    var ret = $("#dataTable_detail").jqGrid('getRowData', id)
	    if (!id) {
	        art.dialog({
	            content: "请选择一条记录！",
	            width: 200
	        })
	        return false
	    }
	    Global.Dialog.showDialog("detailView", {
	        title: "定责明细--查看",
	        url: "${ctx}/admin/fixDuty/goDetailView",
	        postData: {
	            m_umState: "V",
	            fromPage: "designer",
	            baseCheckItemCode: "",
	            baseCheckItemDescr: "",
	            baseItemCode: ret.baseitemcode,
	            baseItemDescr: ret.baseitemdescr,
	            qty: ret.qty,
	            remarks: ret.remark
	        },
	        height: 400,
	        width: 720,
	    })
	}
	
	function d_del() {
	    var id = $("#dataTable_detail").jqGrid("getGridParam", "selrow")
	    if (!id) {
	        art.dialog({
	            content: "请选择一条记录进行删除操作！"
	        })
	        return false
	    }
	    art.dialog({
	        content: "是否确认要删除",
	        lock: true,
	        ok: function() {
	            Global.JqGrid.delRowData("dataTable_detail", id)
	            $("#offerPrj").val($("#dataTable_detail").getCol("allofferpri", false, "sum"))
	            $("#material").val($("#dataTable_detail").getCol("allmaterial", false, "sum"))
	            $("#dataChanged").val("1")
	        },
	        cancel: function() {
	            return true
	        }
	    })
	}
</script>
</html>
