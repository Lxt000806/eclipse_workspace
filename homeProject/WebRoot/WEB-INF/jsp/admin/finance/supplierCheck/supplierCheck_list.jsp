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
		<title>supplierCheck列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
		
		<script type="text/javascript">

		$(function(){
			$("#splCode").openComponent_supplier();
			$("#custCode").openComponent_customer();
			$("#appCZY").openComponent_czybm();
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1");
			
	        //初始化表格
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-100,
				colModel : [			
					{name: "no", index: "no", width: 80, label: "结算单号", sortable: true, align: "left"},
					{name: "splcode", index: "splcode", width: 75, label: "供应商编号", sortable: true, align: "left"},
					{name: "splcodedescr", index: "splcodedescr", width: 120, label: "供应商名称 ", sortable: true, align: "left"},
					{name: "date", index: "date", width: 120, label: "结算日期", sortable: true, align: "left", formatter: formatTime},
					{name: "statusdescr", index: "statusdescr", width: 80, label: "结算单状态", sortable: true, align: "left"},
					{name: "proctrackstatus", index: "proctrackstatus", width: 79, label: "proctrackstatus", sortable: true, align: "left",hidden:true },
					{name: "discapprovestatus", index: "discapprovestatus", width: 79, label: "付款单审批状态", sortable: true, align: "left",formatter: clickOpt },
					{name: "wfprocinstno", index: "wfprocinstno", width: 79, label: "流程审批状态", sortable: true, align: "left", hidden: true},
					{name: "procstatusdescr", index: "procstatusdescr", width: 79, label: "流程审批状态", sortable: true, align: "left", hidden: true},
					{name: "paytypedescr", index: "paytypedescr", width: 70, label: "付款方式 ", sortable: true, align: "left"},
					{name: "othercost", index: "othercost", width: 70, label: "其他费用", sortable: true, align: "right"},
					{name: "payamount", index: "payamount", width: 70, label: "应付金额", sortable: true, align: "right", sum: true},
					{name: "paidamount", index: "paidamount", width: 70, label: "已付金额", sortable: true, align: "right"},
					{name: "nowamount", index: "nowamount", width: 70, label: "现付金额", sortable: true, align: "right"},
					{name: "preamount", index: "preamount", width: 80, label: "预付款支付", sortable: true, align: "right"},
					{name: "begindate", index: "begindate", width: 120, label: "开始日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "enddate", index: "enddate", width: 120, label: "结束日期", sortable: true, align: "left", formatter: formatTime, hidden: true},
					{name: "paybalance", index: "paybalance", width: 70, label: "应付余额", sortable: true, align: "right", sum: true},
					{name: "payczydescr", index: "payczydescr", width: 70, label: "付款人", sortable: true, align: "left"},
					{name: "paydate", index: "paydate", width: 120, label: "付款日期", sortable: true, align: "left", formatter: formatTime},
					{name: "documentno", index: "documentno", width: 120, label: "凭证号", sortable: true, align: "left"},
					{name: "appczydescr", index: "appczydescr", width: 70, label: "申请人", sortable: true, align: "left"},
					{name: "confirmczydescr", index: "confirmczydescr", width: 70, label: "审核人", sortable: true, align: "left"},
					{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
					{name: "remark", index: "remark", width: 120, label: "备注", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"},
					
					{name: "status", index: "status", width: 116, label: "status", sortable: true, align: "left",hidden:true},
					{name: "payczy", index: "payczy", width: 116, label: "payczy", sortable: true, align: "left",hidden:true},
					{name: "itemtype1", index: "itemtype1", width: 116, label: "itemtype1", sortable: true, align: "left",hidden:true}
					
	            ]
			});
		});
		function clickOpt(cellvalue, options, rowObject){
			if(rowObject==null){
		       	return "";
			}
			if(cellvalue == null){
				return "";
			}
			
			return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"viewProc('"
		     			+rowObject.no+"')\">"+cellvalue+"</a>";
		}
		
		function viewProc(no){
			Global.Dialog.showDialog("goViewProcTrack",{ 
			title:"流程审批情况",
			url:"${ctx}/admin/supplierCheck/goViewProcTrack",
			postData:{no: no},
				height: 702,
				width:1130,
				returnFun:goto_query
			});
		}
		
		function save(){
        	Global.Dialog.showDialog("addCheck",{
        		title:"供应商结算管理--新增结算",
        	  	url:"${ctx}/admin/supplierCheck/goSave",
        	  	height: 700,
        	  	width:1300,
        	  	returnFun:goto_query
        	});
		}
		function update(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
				if(ret.status.trim() != "1"){
					art.dialog({
						content:"结账单状态为"+ret.statusdescr+",不允许进行编辑"
					});
					return;
				}
				if(ret.proctrackstatus == "审批中" || ret.proctrackstatus == "已审批"){
					art.dialog({
						content:"存在审批中或已审批的单据，无法编辑"
					});
					return;
				}
				
	        	Global.Dialog.showDialog("updateCheck",{
	        		title:"供应商结算管理--编辑 【最后更新时间:"+ret.lastupdate+"     最后更新人员:"+ret.lastupdatedby+"】",
	        	  	url:"${ctx}/admin/supplierCheck/goUpdate?no="+ret.no,
	        	  	height: 700,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function check(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
				if(ret.status.trim() != "1"){
					art.dialog({
						content:"结账单状态为"+ret.statusdescr+",不允许进行审核"
					});
					return;
				}
	        	Global.Dialog.showDialog("gysjsCheck",{
	        		title:"供应商结算管理--审核 【最后更新时间:"+ret.lastupdate+"     最后更新人员:"+ret.lastupdatedby+"】",
	        	  	url:"${ctx}/admin/supplierCheck/goCheck?no="+ret.no,
	        	  	height: 700,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function unCheck(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
				if(ret.status.trim() != "2"){
					art.dialog({
						content:"结账单状态为"+ret.statusdescr+",不允许进行反审核"
					});
					return;
				}
				
				if(ret.procstatusdescr == "审批中"){
					art.dialog({
						content:"存在未完成的审批流，需要审批流完成，才可以反审核"
					});
					return;
				}
			 	$.ajax({
					url:"${ctx}/admin/supplierCheck/checkSupplierPay",
					type:"post",
			    	data:{
			    		no:ret.no
			    	},
					dataType:"json",
					cache:false,
					error:function(obj){			    		
						art.dialog({
							content:"访问出错,请重试",
							time:3000,
							beforeunload: function () {}
						});
					},
					success:function(data){
						if(!data){
				        	Global.Dialog.showDialog("gysjsUnCheck",{
				        		title:"供应商结算管理--反审核 【最后更新时间:"+ret.lastupdate+"     最后更新人员:"+ret.lastupdatedby+"】",
				        	  	url:"${ctx}/admin/supplierCheck/goCheckBack?no="+ret.no,
				        	  	height: 650,
				        	  	width:1300,
				        	  	returnFun:goto_query
				        	});
						}else{
							art.dialog({
								content:"已存在相应的供应商付款单，不允许进行反审核"
							});
						}
					}
				});	
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function inputNo(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
				if(ret.status.trim() != "2" || ret.payczy != ""){
					art.dialog({
						content:"结账单状态为审核且出纳未签字，才允许录入凭证号"
					});
					return;
				}
			 	
	        	Global.Dialog.showDialog("inputDocument",{
	        		title:"供应商结算管理--编辑 【最后更新时间:"+ret.lastupdate+"     最后更新人员:"+ret.lastupdatedby+"】",
	        	  	url:"${ctx}/admin/supplierCheck/goInputDocument?no="+ret.no,
	        	  	height: 700,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function view(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
	        	Global.Dialog.showDialog("gysjsView",{
	        		title:"供应商结算管理--查看 【最后更新时间:"+ret.lastupdate+"     最后更新人员:"+ret.lastupdatedby+"】",
	        	  	url:"${ctx}/admin/supplierCheck/goView?no="+ret.no,
	        	  	height: 700,
	        	  	width:1300,
	        	  	returnFun:goto_query
	        	});
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function print(){
			var ret = selectDataTableRow("dataTable");
			if(ret){
				Global.Dialog.showDialog("print",{
					title:"出库记账——打印",
					url:"${ctx}/admin/supplierCheck/goPrint",
					postData:{no: ret.no, itemType1: ret.itemtype1, wfProcInstNo: ret.wfprocinstno},
					height:200,
					width:650,
				});
				<%-- if(ret.itemtype1.trim()!='LP'){
					$.ajax({
						url:"${ctx}/admin/supplierCheck/doPrintBefore",
						type:"post",
				    	data:{
				    		no:ret.no
				    	},
						dataType:"json",
						cache:false,
						error:function(obj){			    		
							art.dialog({
								content:"访问出错,请重试",
								time:3000,
								beforeunload: function () {}
							});
						},
						success:function(data){
							var jasper = "";
							if(data){
								if(data.page == "1"){
									jasper = "supplierCheck_report1.jasper";
								}else{
									jasper = "supplierCheck_report2.jasper";
								}
								
								Global.Print.showPrint(jasper, 
									{
										no:data.no,
										companyName:data.cmpnyName,
										titles:data.titles,
										LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
										SUBREPORT_DIR: "<%=jasperPath%>" 
									}
								);
							}else{
								art.dialog({
									content:"打印出错"
								});
							}
						}
					});	
				}else{
					Global.Dialog.showDialog("print",{
						title:"礼品出库记账——打印",
						url:"${ctx}/admin/supplierCheck/goLpPrint?no="+ret.no,
						height:320,
						width:500,
					});
				} --%>
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function excel(){
			doExcelAll("${ctx}/admin/supplierCheck/doExcel"); 
		}
		function onlyUnCheckChange(){
			if($("#onlyUnCheck").val() == "T"){
				$("#onlyUnCheck").val("F");
			}else{
				$("#onlyUnCheck").val("T");
			}
		}
		function goto_query(){
			$("#dataTable").jqGrid("setGridParam",{
				url:"${ctx}/admin/supplierCheck/goJqGrid",
				postData:$("#page_form").jsonForm(),
				page:1
			}).trigger("reloadGrid");
		}
		function clearForm(){
			$("#page_form input[type='text']").val('');
			$("#page_form select").val('');
			$("#status").val("");
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}
		
		function goProcApply(){
			var ret = selectDataTableRow();
		    if (ret) {    	
		    	if(ret.statusdescr=="取消"){
		    		art.dialog({
						content:"结算单为取消状态，不允许提交审批",
					});
					return;
		    	}
		    	if(ret.procstatusdescr == "审批中"){
					art.dialog({
						content:"存在未审批完成的单据，无法再次发起",
					});
					return;
				}
			    
		    	Global.Dialog.showDialog("procApply",{
					title:"采购报销单",
					url:"${ctx}/admin/supplierCheck/goWfProcApply",
					postData:{no: ret.no,},
					height:700,
					width:1200,
					returnFun: goto_query,
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录",
				});
		    };
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>供应商编号</label>
							<input type="text" id="splCode" name="splCode"/>
						</li>
						<li>
							<label>付款方式</label>
							<house:xtdm id="payType" dictCode="PAYTYPE"></house:xtdm>
						</li>
						<li>
							<label>结算单号</label>
							<input type="text" id="no" name="no"/>
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"/>
						</li>
						<li>
							<label>结算日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" 
								   onFocus="WdatePicker({skin:'whyGreen', dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>结算单状态</label>
							<house:xtdmMulit id="status" dictCode="SPLCKOTSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li>
							<label>凭证号</label>
							<input type="text" id="documentNo" name="documentNo"/>
						</li>
						<li>
							<label>备注</label>
							<input type="text" id="remark" name="remark"/>
						</li>
						<li>
						    <label>申请人</label>
						    <input type="text" id="appCZY" name="appCZY"/>
						</li>
						<li>
						    <label>供应商类型</label>
						    <select id="itemType1" name="itemType1"></select>
						</li>
						<li class="search-group-shrink">
							<input type="checkbox" id="onlyUnCheck" name="onlyUnCheck" onclick="onlyUnCheckChange()" value="T" checked/><p>只显示未结清</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			
			<div class="btn-panel">
				<div class="btn-group-sm">
	            	<house:authorize authCode="SUPPLIERCHECK_SAVE">
						<button type="button" class="funBtn funBtn-system" onclick="save()">新增结算</button>
	                </house:authorize>
	                
					<button type="button" class="funBtn funBtn-system" onclick="update()">编辑</button>
					
	                <house:authorize authCode="SUPPLIERCHECK_CHECK">
						<button type="button" class="funBtn funBtn-system" onclick="check()">审核</button>
	                </house:authorize>
	                <house:authorize authCode="SUPPLIERCHECK_UNCHECK">
						<button type="button" class="funBtn funBtn-system" onclick="unCheck()">反审核</button>
	                </house:authorize>
	                
					<button type="button" class="funBtn funBtn-system" onclick="inputNo()">录入凭证号</button>

					<house:authorize authCode="SUPPLIERCHECK_PROCAPPLY">
						<button type="button" class="funBtn funBtn-system" onclick="goProcApply()">提交审批</button>
	                </house:authorize>
	                <house:authorize authCode="SUPPLIERCHECK_VIEW">
						<button type="button" class="funBtn funBtn-system" onclick="view()">查看</button>
	                </house:authorize>
						
					<button type="button" class="funBtn funBtn-system" onclick="print()">打印</button>
	                
					<button type="button" class="funBtn funBtn-system" onclick="excel()">输出到Excel</button>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


