<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>麦田客户分配管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">

	function add(){
		var ret =selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		if(ret.status!="1"){
			art.dialog({
				content:"请选择已接收状态的记录",
			});
			return;
		}
		Global.Dialog.showDialog("add",{
			title:"麦田客户分配--添加客户",
			postData:{mtCustInfoPK:ret.pk},
			url:"${ctx}/admin/customer/goSave",
			height: 600,
			width:1200,
			returnFun: goto_query 
		});
	}
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	} 
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/mtCustInfoAssign/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-80,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 100, label: "pk", sortable: true, align: "left",hidden: true},
				{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left"},
				{name: "custphone", index: "custphone", width: 80, label: "客户电话", sortable: true, align: "left"},
				{name: "gender", index: "gender", width: 50, label: "性别", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "perf", index: "perf", width: 120, label: "业绩额", sortable: true, align: "left",},
				{name: "perfcompdate", index: "perfcompdate", width: 120, label: "业绩计算时间", sortable: true, align: "left",formatter: formatDate},
				{name: "senddate", index: "senddate", width: 120, label: "业绩发送时间", sortable: true, align: "left",formatter: formatDate},
				{name: "statusdescr", index: "statusdescr", width: 70, label: "状态", sortable: true, align: "left"},
				{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "left"},
				{name: "layout", index: "layout", width: 80, label: "户型", sortable: true, align: "left"},
				{name: "isfixtures", index: "isfixtures", width: 70, label: "是否装修", sortable: true, align: "left"},
				{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 80, label: "麦田大区", sortable: true, align: "left"},
				{name: "region2", index: "region2", width: 80, label: "麦田区域", sortable: true, align: "left"},
				{name: "shopname", index: "shopname", width: 80, label: "麦田门店", sortable: true, align: "left"},
				{name: "manage", index: "manage", width: 80, label: "麦田经纪人", sortable: true, align: "left"},
				{name: "managephone", index: "managephone", width: 110, label: "经纪人电话", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "czy", index: "czy", width: 70, label: "对接人", sortable: true, align: "left"},
				{name: "departmentdescr", index: "departmentdescr", width: 70, label: "对接部门", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 70, label: "接收时间", sortable: true, align: "left", formatter: formatDate},
				{name: "yjaddress", index: "yjaddress", width: 120, label: "有家客户楼盘", sortable: true, align: "left"},
				{name: "yjstatus", index: "yjstatus", width: 80, label: "楼盘状态", sortable: true, align: "left"},
				{name: "setdate", index: "setdate", width: 90, label: "下定时间", sortable: true, align: "left", formatter: formatDate},
				{name: "signdate", index: "signdate", width: 90, label: "签单时间", sortable: true, align: "left", formatter: formatDate},
				{name: "businessman", index: "businessman", width: 70, label: "业务员", sortable: true, align: "left"},
				{name: "custremarks", index: "custremarks", width: 120, label: "备注", sortable: true, align: "left"},
				{name: "conremarks", index: "conremarks", width: 120, label: "跟踪情况", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 120, label: "有家客户号", sortable: true, align: "left",hidden:true},
				{name: "designmandescr", index: "designmandescr", width: 70, label: "设计师", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 70, label: "设计部", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
			],
		});
		$("#czybh").openComponent_czybm(); 
	});
	function cancel(){
		var ret = selectDataTableRow();
		if (ret) {
			if(ret.status!="1"){
				art.dialog({
					content:"请选择已接收状态的记录",
				});
				return;
			}
			art.dialog({
				content : "确定要取消该条记录吗？",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/mtCustInfoAssign/doCancel?PK=" + ret.pk,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "取消出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query();
							} else {
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content : obj.msg,
									width : 200
								});
							}
						}
					});
				},
				cancel : function() {
					goto_query();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	function view(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"麦田客户分配--查看",
			url:"${ctx}/admin/mtCustInfoAssign/goView",
			postData:{map:JSON.stringify(ret)},
			height: 400,
			width:800,
			returnFun: goto_query 
		});
	}
	function setCzy(){
		Global.Dialog.showDialog("setCzy",{
			title:"麦田客户分配--设置对接人",
			url:"${ctx}/admin/mtCustInfoAssign/goSetCzy",
			height: 600,
			width:800,
			returnFun: goto_query 
		});
	}
	function custConView(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("view",{
			title:"跟踪情况--查看",
			url:"${ctx}/admin/mtCustInfoAssign/goCustConView",
			postData:{custCode:ret.custcode},
			height: 600,
			width:1200,
			returnFun: goto_query 
		});
	}
	
	function addCustCode(){
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		status = $.trim(ret.status);
		console.log(status);
/* 		if(status!="2"&&status!="3"&&status!="4"&&status!="6"){
			art.dialog({
				content:"状态为已添加、业绩已计算、业绩已发送、发送异常才能进行关联意向客户操作",
			});
			return;
		} */
		console.log(ret.pk);
		Global.Dialog.showDialog("addCustCode",{
			title:"关联意向客户",
			url:"${ctx}/admin/mtCustInfoAssign/goAddCustCode",
			postData:{PK: ret.pk},
			height: 540,
			width:850,
			returnFun: goto_query 
		});
	}
	
	function doExcel(){
		var url = "${ctx}/admin/mtCustInfoAssign/doExcel";
		doExcelAll(url);
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="" /> 
				<ul class="ul-form">
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" />
					</li>
					<li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="MTCUSTINFOSTAT"></house:xtdmMulit>
					</li>
					<li><label>登记时间</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>						
						<label>是否装修</label>
						<house:xtdm id="isFixtures" dictCode="YESNO" ></house:xtdm>													
					</li>
					<li>						
						<label>户型</label>
						<house:xtdm id="layout" dictCode="LAYOUT" ></house:xtdm>													
					</li>
					<li>						
						<label>是否分配</label>
						<house:xtdm id="isDistribute" dictCode="YESNO" ></house:xtdm>													
					</li>
					<li class="form-validate"><label>对接人</label> 
						<input type="text" id="czybh" name="czybh" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${mtCustInfo.expired}"
						onclick="hideExpired(this)" ${mtCustInfo.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="MTCUSTINFOASSIGN_SAVE">
						<button type="button" class="btn btn-system" onclick="add()">
							<span>添加客户</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_CANCEL">
						<button type="button" class="btn btn-system" onclick="cancel()">
							<span>取消</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_VIEW">
						<button type="button" class="btn btn-system" onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_SETCZY">
						<button type="button" class="btn btn-system" onclick="setCzy()">
							<span>设置对接人</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_CUSTCON">
						<button type="button" class="btn btn-system" onclick="custConView()">
							<span>查看跟踪情况</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_ADDCUSTCODE">
						<button type="button" class="btn btn-system" onclick="addCustCode()">
							<span>关联意向客户</span>
						</button>
					</house:authorize>
					<house:authorize authCode="MTCUSTINFOASSIGN_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">
							<span>输出到Excel</span>
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
