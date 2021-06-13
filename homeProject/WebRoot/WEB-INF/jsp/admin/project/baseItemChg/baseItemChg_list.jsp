<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>BaseItemChg列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("#page_form select").val("");
		$("#remarks").val("");
		$("#status").val("");
		$("#prjStatus").val("")
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_prjStatus").checkAllNodes(false);
	}
	function zczj(operType){
		Global.Dialog.showDialog("baseItemChgAdd",{
		  	title:"搜寻--客户编号",
		  	url:"${ctx}/admin/baseItemChg/goSave?operType="+operType,
		  	height: 600,
		  	width:1000,
		  	returnFun: goto_query
		});
	}
	
	function update(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.status.trim()!="1"){
	    		art.dialog({
					content: "只有已申请状态的增减单可以进行编辑"
				});	
	    		return;
	    	}
	    	if((ret.prjstatus.trim()=="" || ret.prjstatus.trim() == "0" || ret.prjstatus.trim()=="3")){
		    	 Global.Dialog.showDialog("baseItemChgUpdate",{
				  	title:"基装增减—编辑",
				  	url:"${ctx}/admin/baseItemChg/goUpdate",
				  	postData:{"id":ret.no,"appCzyDescr":ret.appczydescr,"documentNo":ret.documentno,"address":ret.address,
				  		"confirmCzyDescr":ret.confirmczydescr,"statusDescr":ret.statusdescr,"custType":ret.custtype},
				  	height:window.screen.height-130,
				  	width:window.screen.width-180,
				  	returnFun: goto_query
				});
	    	}else{
		    	Global.Dialog.showDialog("baseItemChgUpdate",{
				  	title:"基装增减—编辑",
				  	url:"${ctx}/admin/baseItemChg/goUpdate",
				  	postData:{"id":ret.no,"appCzyDescr":ret.appczydescr,"documentNo":ret.documentno,"address":ret.address,
				  		"confirmCzyDescr":ret.confirmczydescr,"statusDescr":ret.statusdescr,"custType":ret.custtype},
				  	height:window.screen.height-130,
				  	width:window.screen.width-180,
				  	returnFun: goto_query
				});
	    	}
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	// 增加部门经理确认功能
	// 张海洋 20200609
    function deptLeaderConfirm() {
        var row = selectDataTableRow()

        if (!row) {
            art.dialog({content: '请选择一条记录'})
            return
        }

        if (row.prjstatus !== '2') {
            art.dialog({content: '非提交状态不允许确认'})
            return
        }

        if (row.status.trim() !== '1') {
            art.dialog({content: '非申请状态不允许确认'})
            return
        }

        Global.Dialog.showDialog("baseItemChgDeptLeaderConfirm", {
            title: "基装增减—部门经理确认",
            url: "${ctx}/admin/baseItemChg/goDeptLeaderConfirm",
            postData: {id: row.no.trim()},
            height: window.screen.height - 130,
            width: window.screen.width - 180,
            returnFun: goto_query
        })
    }
	
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	 Global.Dialog.showDialog("baseItemChgDetail",{
			  	title:"基装增减—查看",
			  	url:"${ctx}/admin/baseItemChg/goUpdate",
			  	postData:{"id":ret.no,"appCzyDescr":ret.appczydescr,"documentNo":ret.documentno,"address":ret.address,
			  		"confirmCzyDescr":ret.confirmczydescr,"statusDescr":ret.statusdescr,"custType":ret.custtype,operType:"V"},
			  	height:window.screen.height-130,
			  	width:window.screen.width-180
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function detail(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	 Global.Dialog.showDialog("baseItemChgDetail",{
			  	title:"基装增减—明细",
			  	url:"${ctx}/admin/baseItemChg/goDetail",
			  	postData:{},
			  	height:window.screen.height-130,
			  	width:window.screen.width-180
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function del(){
		var url = "${ctx}/admin/baseItemChg/doDelete";
		beforeDel(url);
	}
	function goDetailQuery(){
	  	Global.Dialog.showDialog("baseItemChgDetailView",{
		  	title:"基装增减--明细查询",
		  	url:"${ctx}/admin/baseItemChg/goDetailQuery",
		  	height:window.screen.height-130,
		  	width:window.screen.width-40
		});
	}
	//导出EXCEL
	function doExcel(){
		var url = "${ctx}/admin/baseItemChg/doExcel";
		doExcelAll(url);
	}
	//增减业绩
	function zjyj(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.status.trim()!="2"){
		    	art.dialog({
					content: "只有审核状态的增减单才能设置是否计算业绩！"
				});
				return;
	    	}
	    	if(ret.perfpk!=""){
		    	art.dialog({
					content: "此增减单已计算业绩，不能进行此操作！"
				});
				return;
	    	}
	    	$.ajax({
				url:"${ctx}/admin/baseItemChg/beforeGoZjyj?no="+ret.no+"&status="+ret.status
		  			+"&perfPk="+ret.perfpk+"&iscalPerf="+ret.iscalperf,
				type: "post",
				data: {},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    	},
		     	success: function(obj){
			    	if(obj.rs){
			    		Global.Dialog.showDialog("baseItemChgZjyj",{
						  	title:"是否计算增减业绩",
						  	url:"${ctx}/admin/baseItemChg/goZjyj?no="+ret.no+"&iscalPerf="+ret.iscalperf,
						  	height:350,
						  	width:500,
						   	returnFun: goto_query
						});
			    	}else{
			    		//$("#_form_token_uniq_id").val(obj.token.token);
			    		art.dialog({
							content: obj.msg,
							width: 200
						});
			    	}
			    }
		 	});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	//审核
	function confirm(){
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.status.trim()!="1"){
		    	art.dialog({
					content: "该增减项已审核或取消,不能审核！"
				});
				return;
	    	}
	    	if(ret.prjstatus.trim()!="3" && ret.prjstatus.trim()!="0" && ret.prjstatus.trim()!=""){
		    	art.dialog({
					content: "变更单项目经理提交状态不是接收状态,不能审核！"
				});
				return;
	    	}
	      	Global.Dialog.showDialog("baseItemChgConfirm",{
			  	title:"基装增减--审核",
			  	url:"${ctx}/admin/baseItemChg/goUpdate",
			  	postData:{"id":ret.no,"appCzyDescr":ret.appczydescr,"documentNo":ret.documentno,"address":ret.address,
			  		"confirmCzyDescr":ret.confirmczydescr,"statusDescr":ret.statusdescr,"custType":ret.custtype,"operType":"S"},
			  	height:window.screen.height-100,
			  	width:window.screen.width-180,
			  	returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	 //打印
	function doPrint(){
		var row = selectDataTableRow();
		if(!row){
			art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
			return false;
		}
		if ("1" == row.prjstatus || "2" == row.prjstatus || "6" == row.prjstatus) {
			art.dialog({content: "项目经理提交的或者未接收处理的，不允许打印。",width: 200});
			return false;
		}
	    var reportName = "baseItemChgDetail.jasper";
	  	Global.Print.showPrint(reportName, {
			No: row.no,
			SUBREPORT_DIR: "<%=jasperPath%>" 
		});
	}
	  
	  
	function doAllPrint() {
      Global.Dialog.showDialog("baseItemChgView", {
        title: "增减单--批量打印",
        url: "${ctx}/admin/baseItemChg/goQPrint",
        height: 700,
        width: 1200
      });
    } 
    
         
	function goto_query(){
		var datas=$("#page_form").jsonForm();
		if(datas.itemDescr && !datas.custCode){
			art.dialog({
				content: "请先选择客户编号再进行材料名称查询！"
			});
			return ;
		}
		$(".s-ico").css("display","none");
		$("#dataTable").jqGrid("setGridParam",{postData:datas,page:1,sortname:""}).trigger("reloadGrid");
	}
	
	function receive(){
		var ret = selectDataTableRow();
		if(ret.status.trim()!='1' || (ret.prjstatus.trim()!='7' && ret.prjstatus.trim()!='2')){
			art.dialog({
				content:"项目经理提交状态为确认且增减单状态为申请状态才能做接收操作",
			});
			return false;
		}		
		art.dialog({
			content:"是否接收",
			lock: true,
			width: 100,
			height: 60,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/baseItemChg/doPrjReceive",
					type:'post',
					data:{no:ret.no},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						art.dialog({
							content:'接收成功',
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
					}
				});
			},
			cancel: function () {
					return true;
			}
		});	
	}
	
	function returnApp(){
		var ret = selectDataTableRow();
		if(ret.status.trim()!='1' || ret.prjstatus.trim() !='3'){
			art.dialog({
				content:"项目经理提交状态为接收且增减单状态为申请状态才能做退回申请操作",
			});
			return false;
		}		
		art.dialog({
			content:"是否确定退回",
			lock: true,
			width: 100,
			height: 60,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/baseItemChg/doPrjReturn",
					type:'post',
					data:{no:ret.no},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						art.dialog({
							content:'退回成功',
						});
						$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
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
<div class="body-box-list">
	<div class="query-form">
		<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame" >
		<input type="hidden" id="expired" name="expired" value="${baseItemChg.expired}" />
		<input type="hidden" name="jsonString" value="" />
		<ul class="ul-form">
			<li>
				<label>客户编号</label>
				<input type="text" id="custCode" name="custCode" value="${baseItemChg.custCode}" />
			</li>
			<li>
				<label>楼盘</label>
				<input type="text" id="address" name="address" value="${baseItemChg.address}" />
			</li>
			<li>
				<label>增减单号</label>
				<input type="text" id="no" name="no" value="${baseItemChg.no}" />
			</li>
			<li>
				<label>变更日期</label>
				<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${baseItemChg.dateFrom}' pattern='yyyy-MM-dd'/>" />
			</li>
			<li>	
				<label>至</label>
				<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${baseItemChg.dateTo}' pattern='yyyy-MM-dd'/>" />
			</li>
			<li>
				<label>增减单状态</label>
				<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='ITEMCHGSTATUS'" 
				selectedValue="${baseItemChg.status}"></house:xtdmMulit>
			</li>
			<li>
				<label>项目经理提交状态</label>
				<house:xtdmMulit id="prjStatus" dictCode="PRJSTATUS" selectedValue=""></house:xtdmMulit>                     
			</li>
			<li>
				<label>审核日期</label>
				<input type="text" id="confirmDateFrom" name="confirmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${baseItemChg.confirmDateFrom}' pattern='yyyy-MM-dd'/>" />
			</li>
			<li>	
				<label>至</label>
				<input type="text" id="confirmDateTo" name="confirmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value='${baseItemChg.confirmDateTo}' pattern='yyyy-MM-dd'/>" />
			</li>
			<li>
				<label>申请人部门</label>
				<input type="text" id="applyManDept" name="applyManDept" value="${baseItemChg.applyManDept}" />
			</li>
		    <li><label>非独立销售</label> <house:xtdm id="isAddAllInfo"
                       dictCode="YESNO" value="${baseItemChg.isAddAllInfo}"></house:xtdm>
            </li>
            <li>
				<label>备注</label>
				<input type="text" id="remarks" name="remarks" value="${baseItemChg.remarks}" />
			</li>
			<li class="search-group-shrink">
				<input type="checkbox" id="expired_show" name="expired_show" value="${baseItemChg.expired}" 
					onclick="hideExpired(this)" ${baseItemChg.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
				<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
				<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
			</li>
		  </ul>
		</form>
	</div>
    <div class="btn-panel">
    	<div class="btn-group-sm">
   			<house:authorize authCode="BASEITEMCHG_ZCZJ">
     		<button type="button" class="btn btn-system" onclick="zczj('A')">基装增减</button>
     		</house:authorize>
   			<house:authorize authCode="BASEITEMCHG_SET">
     		<button type="button" class="btn btn-system" onclick="zczj('J')">套餐内减项</button>
    		</house:authorize>
  			<house:authorize authCode="BASEITEMCHG_UPDATE">
     		<button type="button" class="btn btn-system" onclick="update()">编辑</button>
     		</house:authorize>
     		<house:authorize authCode="BASEITEMCHG_DEPTLEADERCONFIRM">
            <button type="button" class="btn btn-system" onclick="deptLeaderConfirm()">部门经理确认</button>
            </house:authorize>
		    <house:authorize authCode="BASEITEMCHG_AUDIT">
		    <button type="button" class="btn btn-system" onclick="confirm()">审核</button>
		    </house:authorize>
		    <house:authorize authCode="BASEITEMCHG_JS">
		    <button type="button" class="btn btn-system" onclick="receive()">接收</button>
		    </house:authorize>
		    <house:authorize authCode="BASEITEMCHG_THSQ">
		    <button type="button" class="btn btn-system" onclick="returnApp()">退回申请</button>
		    </house:authorize>
		    <house:authorize authCode="BASEITEMCHG_ZJYJ">
		    <button type="button" class="btn btn-system" onclick="zjyj()">增减业绩</button>
		    </house:authorize>
     		<house:authorize authCode="BASEITEMCHG_DETAIL">
     		<button type="button" class="btn btn-system" onclick="detail()">明细查询</button>
     		</house:authorize>
     		<house:authorize authCode="BASEITEMCHG_VIEW">
     		<button type="button" class="btn btn-system" onclick="view()">查看</button>
     		</house:authorize>
     		<button type="button" class="btn btn-system" onclick="doPrint()">打印</button>
     		<button type="button" class="btn btn-system" onclick="doAllPrint()">批量打印</button>
      		<house:authorize authCode="BASEITEMCHG_EXCEL">
     		<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
     		</house:authorize>
     	</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</div>
<script type="text/javascript">
/**初始化表格*/
$(function(){
	//初始化材料类型1，2，3三级联动
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/baseItemChg/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		colModel : [
			{name: "documentno", index: "documentno", width: 70, label: "档案编号", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "增减单号", sortable: true, align: "left"},
			{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left"},
			{name: "customerdescr", index: "customerdescr", width: 70, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 95, label: "基装增减状态", sortable: true, align: "left"},
			{name: "status", index: "status", width: 95, label: "基装增减状态", sortable: true, align: "left",hidden:true},
			{name: "prjstatus", index: "prjstatus", width: 95, label: "项目经理状态", sortable: true, align: "left",hidden:true},
			{name: "prjstatusdescr", index: "prjstatusdescr", width: 120, label: "项目经理提交状态", sortable: true, align: "left",},
			{name: "date", index: "date", width: 120, label: "变更日期", sortable: true, align: "left", formatter: formatTime},
			{name: "appczydescr", index: "appczydescr", width: 80, label: "申请人", sortable: true, align: "left"},
			{name: "deptleaderconfirmdate", index: "deptleaderconfirmdate", width: 120, label: "部门经理确认日期", sortable: true, align: "left", formatter: formatTime},
			{name: "deptleaderconfirmczyname", index: "deptleaderconfirmczyname", width: 110, label: "部门经理确认人", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmczydescr", index: "confirmczydescr", width: 60, label: "审核人", sortable: true, align: "left"},
			{name: "befamount", index: "befamount", width: 85, label: "优惠前金额", sortable: true, align: "right", sum: false},
			{name: "discamount", index: "discamount", width: 85, label: "优惠金额", sortable: true, align: "right", sum: false},
			{name: "amount", index: "amount", width: 85, label: "实际总价", sortable: true, align: "right", sum: false},
			{name: "managefee", index: "managefee", width: 85, label: "管理费", sortable: true, align: "right"},
			{name: "iscalperf", index: "iscalperf", width: 102, label: "是否计算业绩", sortable: true, align: "left",hidden:true},
			{name: "iscalperfdescr", index: "iscalperfdescr", width: 102, label: "是否计算业绩", sortable: true, align: "left"},
			{name: "isaddallinfodescr", index: "isaddallinfodescr", width: 90, label: "非独立销售", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
			{name: "perfpk", index: "perfpk", width: 80, label: "perfpk", sortable: true, align: "left", hidden: true}
        ],
       	ondblClickRow:function(){
        	view();
        }
	});
	$("#appCzy").openComponent_employee();
	$("#applyManDept").openComponent_department();
	$("#custCode").openComponent_customer({showLabel:"${baseItemChg.descr}",showValue:"${baseItemChg.custCode}",
		condition:{"status":"4","disabledEle":"status_NAME","mobileHidden":"1"}});
	onCollapse(44);
});
</script>
</body>
</html>


