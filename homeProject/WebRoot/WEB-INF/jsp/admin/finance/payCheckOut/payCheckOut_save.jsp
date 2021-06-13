<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.web.login.UserContext"%>
<%@ page import="com.house.framework.commons.conf.CommonConstant"%>

<!DOCTYPE html>
<html>
<head>
	<title>新增账单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>  
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

	<script type="text/javascript">
		var m_umState="${payCheckOut.m_umState}";
		var checkSeq = 0;
		$("#tabs").tabs();
		// 初始化表单头部
		$(function(){
			$("#appCZY").openComponent_employee({
				showValue:'${payCheckOut.appCZY}', 
				showLabel:'${payCheckOut.appCZYDescr}', 
				readonly:true
			});
			$("#confirmCZY").openComponent_employee({
				showValue:'${payCheckOut.confirmCZY}', 
				showLabel:'${payCheckOut.confirmCZYDescr}', 
				readonly:true
			});
			var lastCellRowId;
			var url="${ctx}/admin/payCheckOut/goDetailJqGrid";
			if(m_umState=="A"){
				url="";
				$("#passBtn,#cancelBtn,#returnBtn").attr("disabled",true);
			}else if(m_umState=="M"){
				$("#passBtn,#cancelBtn,#returnBtn").attr("disabled",true);
			}else if(m_umState=="C"){
				$("#documentNo").removeAttr("readonly").prev("label").attr("style","color:blue");
				$("#saveBtn,#returnBtn,#add,#delete").attr("disabled",true);
			}else if(m_umState=="RC"){
				$("#saveBtn,#cancelBtn,#passBtn,#add,#delete").attr("disabled",true);
			}else if(m_umState=="V"){
				$("#saveBtn,#cancelBtn,#returnBtn,#passBtn,#add,#delete,#remarks").attr("disabled",true);
			}
			var gridOption = {
				height:$(document).height()-$("#content-list").offset().top-80,
				rowNum:10000000,
			 	url:url,
				postData:{
		            no:'${payCheckOut.no}'
		        }, 
				styleUI: 'Bootstrap', 
				colModel : [
					{name: "checkouttype", index: "checkouttype", width: 50, label: "记账类型", sortable: true, align: "left", hidden: true},
					{name: "checkouttypedescr", index: "checkouttypedescr", width: 80, label: "记账类型", sortable: true, align: "left", formatter: checkoutTypeFmatter},
					{name: "ischeckout", index: "ischeckout", width: 90, label: "是否记账", sortable: true, align: "left", hidden: true},
					{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
					{name: "code", index: "code", width: 70, label: "客户编号", sortable: true, align: "left"},
					{name: "custdescr", index: "custdescr", width: 65, label: "姓名", sortable: true, align: "left"},
					{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
					{name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left"},
					{name: "regiondescr", index: "regiondescr", width: 50, label: "片区", sortable: true, align: "left", frozen: true},
					{name: "custstatus", index: "custstatus", width: 70, label: "客户状态", sortable: true, align: "left"},
					{name: "endcode", index: "endcode", width: 70, label: "结束代码", sortable: true, align: "left"},
					{name: "date", index: "date", width: 80, label: "付款日期", sortable: true, align: "left", formatter:formatDate},
					{name: "amount", index: "amount", width: 70, label: "付款金额", sortable: true, align: "right", sum: true},
					{name: "procedurefee", index: "procedurefee", width: 60, label: "手续费", sortable: true, align: "right", sum: true},
					{name: "actualamount", index: "actualamount", width: 70, label: "实际到账", sortable: true, align: "right", sum: true},
					{name: "rcvactdescr", index: "rcvactdescr", width: 180, label: "账户", sortable: true, align: "left"},
					{name: "posdescr", index: "posdescr", width: 200, label: "pos机", sortable: true, align: "left"},
					{name: "custpaytypedescr", index: "custpaytypedescr", width: 80, label: "类型", sortable: true, align: "left"},
					{name: "custpaytype", index: "custpaytype", width: 80, label: "类型", sortable: true, align: "left",hidden:true},
					{name: "payno", index: "payno", width: 80, label: "收款单号", sortable: true, align: "left"},
					{name: "referno", index: "referno", width: 80, label: "参考号", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 380, label: "备注", sortable: true, align: "left"},
					{name: "pk", index: "pk", width: 77, label: "付款单号", sortable: true, align: "left"},
					{name: "checkseq", index: "checkSeq", width: 60, label: "结算顺序号", sortable: true, align: "left",hidden:true},
			     ],	
				gridComplete:function(){
					var needSum1 = $("#dataTable").getCol("procedurefee",false,"sum");
					$("#dataTable").footerData("set",{"procedurefee":myRound(needSum1,2)});
					var needSum2 = $("#dataTable").getCol("actualamount",false,"sum");
					$("#dataTable").footerData("set",{"actualamount":myRound(needSum2,2)});
					var needSum3 = $("#dataTable").getCol("amount",false,"sum");
					$("#dataTable").footerData("set",{"amount":myRound(needSum3,2)});
				}	
			};
			Global.JqGrid.initJqGrid("dataTable",gridOption);
			$("#custPay").on("click",function(){
				var ret = selectDataTableRow();
				rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("custPay",{
					title:"客户付款",
					url:"${ctx}/admin/custPay/goReturnPayCustPay?code="+ret.code,
					postData:{
						m_umState:"V",
					},
					height:750,
					width:1300,
					returnFun: goto_query
				});
			});
		});

        function checkoutTypeFmatter (cellvalue, options, rowObject) {
            if (cellvalue) return cellvalue
            
		    switch (rowObject.checkouttype) {
		        case '1':
		            return '设计定金'
		        case '2':
		            return '工程款'
		        default:
		            return ''
		    }
		}

        // 新增按钮单击事件
		function add0(){
			var pk = Global.JqGrid.allToJson("dataTable","pk");
			// 临时存储已选择的新增付款单记账pk
			var arr = new Array();
			arr = pk.fieldJson.split(",");
            var ids=$("#dataTable").jqGrid("getDataIDs");
			Global.Dialog.showDialog("save",{
				title:"新增付款单记账",
				url:"${ctx}/admin/payCheckOut/goAdd",
				postData:{
					arr:arr
				},
				height:680,
				width:1300,
				returnFun:function(data){
					if(data){
						$.each(data,function(k,v){
							checkSeq++;
							var json = {
								documentno : v.documentno,
								code : v.code,
								custdescr : v.custdescr,
								custtypedescr : v.custtypedescr,
								address : v.address,
								custstatus : v.custstatus,
								date : v.date,
								amount : v.amount,
								procedurefee : v.procedurefee,
								actualamount : v.actualamount,
								rcvactdescr : v.rcvactdescr,
								posdescr : v.posdescr,
								payno : v.payno,
								pk : v.pk,
								remarks : v.remarks,
								regiondescr : v.regiondescr,
								checkseq : ids.length+k,
								custpaytype:v.custpaytype,
								custpaytypedescr:v.custpaytypedescr,
								checkouttype: v.checkouttype
							};
							$("#dataTable").jqGrid("addRowData", (k+1+ids.length), json);
						});
					}
				} 
			});
		}
        
        // 删除按钮单击事件
		function del(){
		    var id = $("#dataTable").jqGrid("getGridParam","selrow");
		  	if(!id){
				art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
				return false;
            }
	        art.dialog({
				 content:"是否确认要删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					Global.JqGrid.delRowData("dataTable",id);
				},
				cancel: function () {
						return true;
				}
			}); 
		}

		// 保存相关按钮单击事件
		function save(){
			var param = Global.JqGrid.allToJson("dataTable");
			var Ids = $("#dataTable").getDataIDs();
			if(Ids == null || Ids == ''){
				art.dialog({
					content:'请先增加要结算的付款单相应信息！',
				});
				return false;
			}
			Global.Form.submit("dataForm","${ctx}/admin/payCheckOut/doSave",param,function(ret){
				if(ret.rs == true){
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
		}
		// 审核相关按钮单击事件
		function confirm(status,name){
			var records = $("#dataTable").jqGrid("getGridParam","records");	
			var content="是否确认要取消付款单？";
			if($("#documentNo").val()=="" && status=="2"){
				art.dialog({
					content:"请输入凭证号！",
				});
				return;
			}
			if(status=="2" || status=="4"){
				content="是否确认要"+name+"["+records+"]条付款单？";
			}
			art.dialog({
				 content:content,
				 lock: true,
				 ok: function () {
					$.ajax({
						url:"${ctx}/admin/payCheckOut/doCheck",
						type:"post",
						data:{
							no:$("#no").val(),remarks:$("#remarks").val(),status:status,
							documentNo:$("#documentNo").val(),confirmCZY:$("#confirmCZY").val(),
						},
						dataType:"json",
						cache:false,
						async:false,
						error: function(ret){
							showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
						},
						success:function(ret){
							$("#_form_token_uniq_id").val(ret.token.token);
							art.dialog({
								content:ret.msg,
								time:500,
								beforeunload: function () {
									closeWin();
								}
							});
						}
					}); 
				},
				cancel: function () {
						return true;
				}
			}); 
			
		}
	</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn"
						onclick="save()">保存</button>
							<button type="button" class="btn btn-system " id="passBtn"
						onclick="confirm('2','审核通过')">审核通过</button>
							<button type="button" class="btn btn-system " id="returnBtn"
						onclick="confirm('4','反审核')">反审核</button>
						<button type="button" class="btn btn-system " id="cancelBtn"
						onclick="confirm('3','审核取消')">审核取消</button>
						<button type="button" class="btn btn-system " id="excel"
						onclick="doExcelNow('收入记账明细')">导出Excel</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState"
						value="${payCheckOut.m_umState}" />
					<ul class="ul-form">
					<div style="float:left;">
						<div class="validate-group">
							<li><label><span class="required"></span>账单流水</label> <input
								type="text" id="no" name="no" value="${payCheckOut.no}"
								placeholder="保存自动生成" readonly="readonly" />
							</li>
							<li><label>申请日期</label> <input type="text" id="date"
								name="date" class="i-date" readonly="readonly"
								value="<fmt:formatDate value='${payCheckOut.date}' pattern='yyyy-MM-dd HH:mm:ss'/>" />
							</li>
						</div>
						<div class="validate-group">
						<li class="form-validate"><label>账单状态</label> <house:xtdm
									id="status" dictCode="WHChkOutStatus"
									value="${payCheckOut.status}" disabled="true"></house:xtdm>
							</li>
							<li><label>申请人</label> <input type="text" id="appCZY"
								name="appCZY" style="width:160px;" value="${payCheckOut.appCZY}"
								readonly="readonly" />
							</li>
						</div>
						<div class="validate-group">
						<li id="trdocumentNo"><label>凭证号</label> <input type="text"
								id="documentNo" name="documentNo"
								value="${payCheckOut.documentNo}" readonly="readonly" />
							</li>
								<li><label>审核日期</label> <input type="text" id="confirmDate"
								name="confirmDate" class="i-date"
								value="<fmt:formatDate value='${payCheckOut.confirmDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								readonly="readonly" />
							</li>
						</div>
							<div class="validate-group">
						<li><label>记账日期</label> <input type="text" id="checkDate"
								name="checkDate" class="i-date"
								value="<fmt:formatDate value='${payCheckOut.checkDate}' pattern='yyyy-MM-dd HH:mm:ss'/>"
								readonly="readonly" />
							</li>
							<li><label>审核人</label> <input type="text" id="confirmCZY"
								name="confirmCZY" value="${payCheckOut.confirmCZY}"
								readonly="readonly" />
							</li>
						</div>
					</div>
					<div>
						<div class="validate-group" style="height:120px">
							<li><label class="control-textarea">备注</label> <textarea
									id="remarks" name="remarks"
									style="height:120px;width:180px ">${payCheckOut.remarks}</textarea>
							</li>
						</div>
					</div>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#main" data-toggle="tab">主项目</a></li>
			</ul>
			<div class="tab-content">
				<ul class="ul-form">
					<div id="main" class="tab-pane fade in active">
						<form action="" method="post" id="page_form" class="form-search">
							<input type="hidden" name="jsonString" value="" />
							<house:token></house:token>
							<div class="pageContent">
								<div class="btn-panel">
									<div class="btn-group-sm" style="padding-top: 5px;">
										<button type="button" class="btn btn-system " id="add"
											onclick="add0()">
											<span>新增</span>
										</button>
										<button type="button" class="btn btn-system " id="delete"
											onclick="del()">
											<span>删除</span>
										</button>
										<button type="button" class="btn btn-system"  id="custPay">
											<span>客户付款</span>
										</button>
									</div>
								</div>
								<div id="content-list">
									<table id="dataTable"></table>
								</div>
							</div>
						</form>
					</div>
				</ul>
			</div>
		</div>
</body>
</html>
