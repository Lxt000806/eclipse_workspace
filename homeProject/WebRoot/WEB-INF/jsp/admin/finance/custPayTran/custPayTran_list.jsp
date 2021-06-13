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
    <title>pos机付款对账</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
		$("#status").val("");
      	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
      	$("#cmpCode").val("");
      	$.fn.zTree.getZTreeObj("tree_cmpCode").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custPayTran/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "cmpdescr", index: "cmpdescr", width: 80, label: "分公司", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘地址", sortable: true, align: "left"},
				{name: "custstatus", index: "custstatus", width: 80, label: "客户状态", sortable: true, align: "left"},
				{name: "appczy", index: "appczy", width: 90, label: "操作员编号", sortable: true, align: "left"},
				{name: "zwxm", index: "zwxm", width: 90, label: "操作员姓名", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 90, label: "手机号", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "typedescr", index: "typedescr", width: 70, label: "类型", sortable: true, align: "left"},
				{name: "date", index: "date", width: 85, label: "付款日期", sortable: true, align: "left", formatter: formatDate},
				{name: "tranamount", index: "tranamount", width: 85, label: "交易总金额", sortable: true, align: "right"},
				{name: "payamount", index: "payamount", width: 90, label: "实际扣款金额", sortable: true, align: "right"},
				{name: "rcvact", index: "rcvact", width: 80, label: "收款账号", sortable: true, align: "left"},
				{name: "taxpayeecode", index: "taxpayeecode", width: 80, label: "收款单位编号", sortable: true, align: "left", hidden: true},
				{name: "taxpayeedescr", index: "taxpayeedescr", width: 80, label: "收款单位", sortable: true, align: "left"},
				{name: "compname", index: "compname", width: 100, label: "商家名称", sortable: true, align: "left"},
				{name: "procedurefee", index: "procedurefee", width: 70, label: "手续费", sortable: true, align: "right"},
				{name: "adddate", index: "adddate", width: 85, label: "登记日期", sortable: true, align: "left", formatter: formatDate},
				{name: "payno", index: "payno", width: 100, label: "收款单号", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 120, label: "卡号", sortable: true, align: "left"},
				{name: "cardattr", index: "cardattr", width: 70, label: "卡性质", sortable: true, align: "left", hidden: true},
				{name: "cardattrdescr", index: "cardattrdescr", width: 70, label: "卡性质", sortable: true, align: "left"},
				{name: "bankcode", index: "bankcode", width: 100, label: "发卡行编码", sortable: true, align: "left"},
				{name: "bankname", index: "bankname", width: 100, label: "发卡行名称", sortable: true, align: "left"},
				{name: "traceno", index: "traceno", width: 80, label: "凭证号", sortable: true, align: "left"},
				{name: "referno", index: "referno", width: 80, label: "参考号", sortable: true, align: "left"},
				{name: "issigndescr", index: "issigndescr", width: 65, label: "客户签名", sortable: true, align: "left"},
				{name: "printtimes", index: "printtimes", width: 90, label: "打印次数", sortable: true, align: "right"},
				{name: "printczy", index: "printczy", width: 90, label: "最后打印人员", sortable: true, align: "left"},
				{name: "printdate", index: "printdate", width: 120, label: "最后打印时间", sortable: true, align: "left", formatter: formatTime},
				{name: "reprintremarks", index: "reprintremarks", width: 150, label: "重打说明", sortable: true, align: "left"},
				{name: "exceptionremarks", index: "exceptionremarks", width: 150, label: "异常说明", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
				{name: "compname", index: "compname", width: 80, label: "商户名称", sortable: true, align: "left",hidden:true},
				{name: "compcode", index: "compcode", width: 80, label: "商户编码", sortable: true, align: "left",hidden:true},
				{name: "status", index: "status", width: 70, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "rcvactcode", index: "rcvactcode", width: 70, label: "收款账号", sortable: true, align: "left",hidden:true},
				{name: "poscode", index: "poscode", width: 70, label: "pos机", sortable: true, align: "left",hidden:true},
				{name: "paytype", index: "paytype", width: 70, label: "付款方式", sortable: true, align: "left",hidden:true},
				
			],
		});
		$("#appCzy").openComponent_czybm();
		$("#custCode").openComponent_customer();
	});
	function doExcel(){
		var url = "${ctx}/admin/custPayTran/doExcel";
		doExcelAll(url);
	}
	function doRePrint(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(ret.status.trim()!="2"){
			art.dialog({
				content:"请选择已完成的数据！",
			});
			return;
		}
		// 重打小票时要先填重打说明（必填），然后才能打印 modify by zb on 20190531
		Global.Dialog.showDialog("rePrint",{
			title:"重打小票",
			url:"${ctx}/admin/custPayTran/goRePrint",
			postData:{map:JSON.stringify(ret)},
			height:260,
			width:700,
			returnFun: goto_query
		});
		/*Global.Print.showPrint("custPayTran.jasper", 
			{
				pk:ret.pk,
				LogoFile : "<%=basePath%>jasperlogo/logo.jpg",
				SUBREPORT_DIR: "<%=jasperPath%>" 
			}, null, 
			function(){
				art.dialog({
					content:"是否已打印报表?",
					ok:function(){
						$.ajax({
							url:"${ctx}/admin/custPayTran/doRePrint",
							type:"post",
							data:{pk:ret.pk},
							cache:false,
							async:false,
							error:function(obj){
								showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
							},
							success:function(obj){
								art.dialog({
									content : "操作成功！",
									time : 1000,
									beforeunload : function() {
										goto_query();
									}
								});
							}
						});
					},
					cancel:function(){},
					okValue:"是",
					cancelValue:"否"
				});
			}
		); */

	}
	function view(){
		var ret = selectDataTableRow();
		Global.Dialog.showDialog("view",{
			  title:"查看",
			  url:"${ctx}/admin/custPayTran/goView",
			  postData:{map:JSON.stringify(ret)},
			  height:660,
			  width:700,
			  returnFun: goto_query
		});
	}
	function updateProcedureFee(){
		var ret = selectDataTableRow();
		if(ret.status.trim()!="2"){
			art.dialog({
				content:"请选择已完成的数据！",
			});
			return;
		}
		if(parseFloat(ret.procedurefee)!=0){
			art.dialog({
				content:"手续费不为0不能作修改！",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			  title:"修改手续费",
			  url:"${ctx}/admin/custPayTran/goUpdateProcedureFee",
			  postData:{map:JSON.stringify(ret)},
			  height:660,
			  width:700,
			  returnFun: goto_query
		});
	}

	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>操作员</label>  <input type="text" id="appCzy" name="appCzy"
						style="width:160px;" />
					</li>
					<li><label>客户编号</label>  <input type="text" id="custCode" name="custCode"
						style="width:160px;" />
					</li>
					<li><label>楼盘</label>  <input type="text" id="address" name="address"
						style="width:160px;" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="CPTRANSTAT"></house:xtdmMulit>
					</li>
					<li><label>类型</label> <house:xtdm id="type"
							dictCode="CPTRANTYPE"></house:xtdm>
					</li>
					<li><label>电子签字</label> <house:xtdm id="isSign"
							dictCode="YESNO"></house:xtdm>
					</li>
					<li><label>付款日期从</label> <input type="text"
							id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value='${custPayTran.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li><label>至</label> <input type="text" id="installDateTo"
						name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value='${custPayTran.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label for="">分公司</label>
						<house:DictMulitSelect id="cmpCode" dictCode="" 
							sql="select rtrim(Code) Code,Desc1,Desc2 from tCompany where Expired<>'T' order by Code" 
							sqlLableKey="Desc2" sqlValueKey="Code">
						</house:DictMulitSelect>
					</li>
					<li>
					   <label>收款单位</label>
					   <house:dict id="payeeCode" dictCode=""
					       sql="select rtrim(Code) Code, rtrim(Code) + ' ' + Descr Descr from tTaxPayee where Expired = 'F'"
					       sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="CUSTPAYTRAN_VIEW">
						<button type="button" class="btn btn-system"
							onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTPAYTRAN_REPRINT">
						<button type="button" class="btn btn-system"
							onclick="doRePrint()">
							<span>重打小票</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTPAYTRAN_UPDATEPROCEDUREFEE">
						<button type="button" class="btn btn-system"
							onclick="updateProcedureFee()">
							<span>手续费修改</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTPAYTRAN_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()"
							title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</house:authorize>
				</div>
			</div> 
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
