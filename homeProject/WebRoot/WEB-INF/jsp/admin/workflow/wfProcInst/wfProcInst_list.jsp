<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>流程实例列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		.SelectBG{
			background-color:white!important;
		}
		.SelectBlack{
			background-color:white!important;
			color:black!important;
		}
	</style>
	
<script type="text/javascript">
var type = ${type}; // 1.待我审批、2.我已审批、3.我发起的
// 查看详情
function view(taskid,actprocinstid,title,status) {
	var m_umState ="";
	if(status =="审批中"){
		m_umState = "C"; 
	}
    /* var ret = selectDataTableRow();
    if (!ret) {
       art.dialog({content: "请选择一条记录"});
    } */
    Global.Dialog.showDialog("view", {
		title: title,
		url: "${ctx }/admin/wfProcInst/goView?taskId=" + taskid + "&processInstanceId=" + actprocinstid + "&type=" + type +"&m_umState="+m_umState,
		height: 700,
		width: 1280,
		close:false
    });
}

// 查看流程图（当前任务突出显示）
function viewTraceImage() {
    var ret = selectDataTableRow();
    if (!ret) {
       art.dialog({content: "请选择一条记录"});
    }
    Global.Dialog.showDialog("轨迹图", {
        title: "轨迹图 -- "+ret.title,
        url: "${ctx }/admin/wfProcInst/getTraceImage?processInstanceId="+ret.id,
        height: 600,
        width: 1000
    });
}

function cancel(){
    var ret = selectDataTableRow();
    if (!ret) {
       art.dialog({content: "请选择一条记录"});
    }
    art.dialog({
		content: "是否确定进行撤销操作？",
        lock: true,
        width: 260,
        height: 100,
        ok: function () {
            $.ajax({
                url: "${ctx }/admin/wfProcInst/doCancelTask?taskId="+ret.taskid+"&m_umState=M",
                type: "post",
                data: {},
                dataType: "json",
                cache: false,
                error: function(obj){
                    showAjaxHtml({"hidden": false, "msg": "提交数据出错~"});
                },
                success: function(obj){
                    if(obj.rs){
                        art.dialog({
                            content: obj.msg,
                            time: 1000,
                            beforeunload: function () {
                                $("#_form_token_uniq_id").val(obj.datas.token);
                                closeWin();
                            }
                        });
                    }else{
                        $("#_form_token_uniq_id").val(obj.token.token);
                        art.dialog({
                            content: obj.msg,
                            width: 200
                        });
                    }
				}
			});
		},
		cancel: function () {
			return true;
		}
	});
}

$(function(){  
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable", {
		url: "${ctx}/admin/wfProcInst/findWfProcInst",
		postData: {type: type},
		height: $(document).height()-$("#content-list").offset().top-80,
		colModel: [  
			{name: "no", index: "title", width: 200, label: "单据名称", sortable: true, align: "center"
				,formatter:function(cellvalue, options, rowObject){
	        		if(rowObject==null || !rowObject.no){
	          			return '';
	          		}
	        		return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"view('"
	        			+rowObject.taskid+"','"+rowObject.actprocinstid+"','"+rowObject.title+"','"+rowObject.statusdescr+"')\">"+rowObject.title+"</a>";
		    	}
		    }, 
			{name: "title", index: "title", width: 200, label: "单据标题", sortable: true, align: "left",hidden:true}, 
			{name: "summary", index: "summary", width:400, label: "单据摘要", sortable: true, align: "left", formatter:function(cellvalue, options, rowObject){return cellvalue.replace(/\<br\/\>/g, ";").replace(/\<none\/\>/g, "");}}, 
			{name: "starttime", index: "starttime", width: 126, label: "发起时间", sortable: true, align: "left", formatter: formatTime},
			{name: "endtime", index: "endtime", width: 126, label: "完成时间", sortable: true, align: "left", formatter: formatTime}, 
			{name: "statusdescr", index: "statusdescr", width:80, label: "状态", sortable: true, align: "left"},
			{name: "taskname", index: "taskname", width:120, label: "任务名称", sortable: true, align: "left"},
			{name: "assigneedescr", index: "assigneedescr", width:120, label: "当前执行人", sortable: true, align: "left"},
			{name: "actprocinstid", index: "actprocinstid", width:120, label: "流程实例ID", sortable: true, align: "left",hidden:true},
			{name: "taskid", index: "taskid", width:120, label: "任务ID", sortable: true, align: "left",hidden:true }
	    ],
	    gridComplete:function(){
			$("#1").find("td").addClass("SelectBlack");
		},
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBlack");
		},
	});
	
	if(type != "3"){
		jQuery("#dataTable").setGridParam().hideCol("assigneedescr").trigger("reloadGrid");
	}
	
	$("#startUserId").openComponent_czybm();
}); 

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_czybm_startUserId").val('');
	$("#startUserId").val('');
	$("#status_NAME").val('');
	$("#wfProcNo_NAME").val('');
	$("#status").val('');
	$("#wfProcNo").val('');
	$("#dateFrom").val('');
	$("#dateTo").val('');
	$("#endDateFrom").val('');
	$("#endDateTo").val('');
	$.fn.zTree.getZTreeObj("tree_wfProcNo").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}

function doExcel(){
	var url = "${ctx}/admin/wfProcInst/doCopyExcel";
	doExcelAll(url,"dataTable","page_form");
}
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="type" id="type" value="${type }" />
				<ul class="ul-form">
					<li>
						<label>摘要</label>
						<input type="text" id="summary" name="summary" />
					</li>
					<li>
						<label>单据类型</label>
						<house:DictMulitSelect id="wfProcNo" dictCode="" 
						sql="select no code, descr descr from tWfProcess " 
						sqlValueKey="Code" sqlLableKey="Descr"  ></house:DictMulitSelect>
					</li>
					<li>
	                   <label>发起时间</label>
	                   <input type="text" id="startTimeFrom" name="startTimeFrom" class="i-date" 
	                           onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="startTimeTo" name="startTimeTo" class="i-date" 
                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                    </li>
                    <c:if test="${type != 1}">
                    <li>
                       <label>审批完成时间</label>
                       <input type="text" id="endTimeFrom" name="endTimeFrom" class="i-date" 
                               onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                    </li>
                    <li>
                        <label>至</label>
                        <input type="text" id="endTimeTo" name="endTimeTo" class="i-date" 
                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
                    </li>
					<li>
                        <label>状态</label>
                        <house:xtdmMulit id="status" dictCode="WFPROCINSTSTAT"></house:xtdmMulit>
                    </li>
                    </c:if>
                    <c:if test="${type != 3}">
					<li>
                        <label>申请人</label>
                        <input type="text" id="startUserId" name="startUserId" style="width:160px;"/>
                    </li>
                    </c:if>
                     <c:if test="${type == 4}">
					<li>
                        <label>抄送状态</label>
						<house:xtdm id="rcvStatus" dictCode="WFPICOPYRCVSTAT"  style="width:160px;" ></house:xtdm>
                    </li>
                    </c:if>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div id="content-list">
            <c:if test="${type == 4}">
				<div class="btn-panel">
					<div class="btn-group-sm">
						<button type="button" class="btn btn-system" onclick="doExcel()" title="导出检索条件数据">
							<span>导出excel</span>
						</button>
					</div>
				</div>
			</c:if>	
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


