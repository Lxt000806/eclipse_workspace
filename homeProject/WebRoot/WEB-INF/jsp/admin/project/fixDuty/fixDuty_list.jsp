<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>工人定责管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value="" /> 
				<ul class="ul-form">
					<li><label>定责申请单号</label> <input type="text" id="no" name="no" />
					</li>
					<li><label>客户编号</label> <input type="text" id="custCode"
						name="custCode" />
					</li>
					<li><label>楼盘地址</label> <input type="text" id="address"
						name="address" />
					</li>
					 <li><label>状态</label> <house:xtdmMulit id="status"
							dictCode="FIXSTATUS" selectedValue="4,5"></house:xtdmMulit>
					</li>
					<li><label>申请人类型</label> <house:xtdm id="appManType"
							dictCode="APPMANTYPE" onchange="changeType()"></house:xtdm>
					</li>
					 <li hidden><label>申请员工</label> <input type="text" id="appCzy"
						name="appCzy" />
					</li>
					<li><label>申请工人</label> <input type="text" id="appWorkerCode"
						name="appWorkerCode" />
					</li>
                    <li>
                        <label>定责来源</label> 
                        <house:xtdm id="type" dictCode="FIXTYPE" style="width:160px;"/>
                    </li>
					<li><label>申请日期</label> <input type="text" id="dateFrom"
						name="dateFrom" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li><label>至</label> <input type="text" id="dateTo"
						name="dateTo" class="i-date"
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>工种</label>
						<house:DictMulitSelect id="workType12" dictCode="" sql="select a.* from tWorkType12 a where  (
						(select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh}') is null 
						or( select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh }') ='' ) or  Code in(
							select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
							(select PrjRole from tCZYBM where CZYBH='${USER_CONTEXT_KEY.czybh }') or pr.prjrole = ''
							 )   " 
						sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" 
							sql="select Code,Desc1 from tDepartment2 where Expired='F' and DepType='3' order by Department1,DispSeq" 
							sqlValueKey="Code" sqlLableKey="Desc1">
						</house:DictMulitSelect>
					</li>
					<li>
                        <label>客户类型</label>
                        <house:custTypeMulit id="custType"></house:custTypeMulit>
                    </li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${fixDuty.expired}"
						onclick="hideExpired(this)" ${fixDuty.expired!='T' ?'checked':'' }/>
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
					<house:authorize authCode="FIXDUTY_ADD">
						<button type="button" class="btn btn-system" onclick="goAdd()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_ADDDESIGNDUTY">
						<button type="button" class="btn btn-system" onclick="goAddDesignDuty()">
							<span>新增设计定责</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_UPDATE">
						<button type="button" class="btn btn-system" onclick="goUpdate()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_TOAUDIT">
						<button type="button" class="btn btn-system" onclick="doToaudit()">
							<span>提交</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_RETURN">
						<button type="button" class="btn btn-system" onclick="doReturn()">
							<span>复核退回</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_VIEW">
						<button type="button" class="btn btn-system" onclick="go('goView','查看')">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_CONFIRM">
						<button type="button" class="btn btn-system" onclick="go('goConfirm','定责')">
							<span>定责</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_RETCFM">
						<button type="button" class="btn btn-system" onclick="go('goRetCfm','收回定责')">
							<span>收回定责</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_MANAGECFM">
						<button type="button" class="btn btn-system" onclick="go('goManageCfm','审批')">
							<span>审批</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_CONFIRMBACK">
						<button type="button" class="btn btn-system" onclick="go('goManageCb','反审核')">
							<span>反审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_PROVIDE">
						<button type="button" class="btn btn-system" onclick="updateMultyStatus()">
							<span>发放</span>
						</button>
					</house:authorize>
					<house:authorize authCode="FIXDUTY_CANCEL">
						<button type="button" class="btn btn-system" onclick="go('goCancel','取消')">
							<span>取消</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system"
						onclick="detailQuery()">
						<span>明细查询</span>
					</button>
					<house:authorize authCode="FIXDUTY_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">导出Excel</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
	   <script type="text/javascript">

    function go(m_umState,content){
        var ret =selectDataTableRow();
        if((m_umState=="goManageCfm" || m_umState=="goRetCfm") && ret.status!="5"){
            art.dialog({
                content:"只有已判责状态的定责单可进行"+content+"操作！",
            });
            return;
        }else if(m_umState=="goConfirm" && ret.status!="4"){
            art.dialog({
                content:"只有质检已确认状态的定责单可进行"+content+"操作！",
            });
            return;
        }else if(m_umState=="goCancel" && ret.status=="7"){
            art.dialog({
                content:"财务已发放状态不允许取消！",
            });
            return;
        }else if(m_umState=="goCancel" && ret.status=="8"){
            art.dialog({
                content:"已经是取消状态！",
            });
            return;
        }else if(m_umState=="goManageCb" && ret.status!="6"){
            art.dialog({
                content:"只有已审批状态的定责单可进行"+content+"操作！",
            });
            return;
        }   
                
        Global.Dialog.showDialog("view",{
            title:"工人定责管理--"+content,
            postData:{map:JSON.stringify(ret),m_umState:m_umState,content:content},
            url:"${ctx}/admin/fixDuty/"+m_umState,
            height: 750,
            width:1350,
            returnFun: goto_query 
        });
    }
    //清空
    function clearForm(){
        $("#page_form input[type='text']").val("");
        $("select").val("");
        $("#status").val("");
        $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
        $("#workType12").val("");
        $.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
        $("#department2").val("");
        $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
        $("#custType").val("")
        $.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false)
    } 
    /**初始化表格*/
    $(function(){

        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/fixDuty/goJqGrid",
            height:$(document).height()-$("#content-list").offset().top-80,
            styleUI: "Bootstrap", 
            postData:{
                status: $("#status").val(),
                custType: $("#custType").val()
            },
            //multiselect: true,
            colModel : [
                {name: "no", index: "no", width: 100, label: "定责申请编号", sortable: true, align: "left"},
                {name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
                {name: "custtype", index: "custtype", width: 80, label: "客户类型编号", sortable: true, align: "left", hidden:true},
                {name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
                {name: "custwkpk", index: "custwkpk", width: 80, label: "安排pk", sortable: true, align: "left",hidden:true},
                {name: "worktype12", index: "worktype12", width: 80, label: "工种分类12", sortable: true, align: "left",hidden:true},
                {name: "workercode", index: "workercode", width: 80, label: "安排工人编号", sortable: true, align: "left",hidden:true},
                {name: "workername", index: "workername", width: 80, label: "安排工人", sortable: true, align: "left",hidden:true},
                {name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
                {name: "cwstatusdescr", index: "cwstatusdescr", width: 80, label: "工地状态", sortable: true, align: "left"},
                {name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种", sortable: true, align: "left"},
                {name: "appmantypedescr", index: "appmantypedescr", width: 110, label: "申请人类型", sortable: true, align: "left"},
                {name: "statusdescr", index: "statusdescr", width: 90, label: "状态", sortable: true, align: "left"},
                {name: "appdate", index: "appdate", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
                {name: "appmandescr", index: "appmandescr", width: 80, label: "申请人", sortable: true, align: "left"},
                {name: "offerprj", index: "offerprj", width: 100, label: "人工金额", sortable: true, align: "right"},
                {name: "material", index: "material", width: 120, label: "材料金额", sortable: true, align: "right"},
                {name: "remarks", index: "remarks", width: 100, label: "描述", sortable: true, align: "left"},
                {name: "status", index: "status", width: 120, label: "状态", sortable: true, align: "left",hidden:true},
                {name: "prjconfirmdate", index: "prjconfirmdate", width: 120, label: "项目经理确认时间", sortable: true, align: "left", formatter: formatTime},
                {name: "prjmandescr", index: "prjmandescr", width: 100, label: "确认项目经理", sortable: true, align: "left"},
                {name: "department2", index: "department2", width: 80, label: "工程部", sortable: true, align: "left"},
                {name: "prjremark", index: "prjremark", width: 120, label: "项目经理确认说明", sortable: true, align: "left"},
                {name: "designmanname", index: "designmanname", width: 80, label: "设计师", sortable: true, align: "left"},
                {name: "designmanphone", index: "designmanphone", width: 90, label: "设计师电话", sortable: true, align: "left"},
                {name: "cfmdate", index: "cfmdate", width: 120, label: "金额确认日期", sortable: true, align: "left", formatter: formatTime},
                {name: "cfmmandescr", index: "cfmmandescr", width: 80, label: "金额确认人", sortable: true, align: "left"},
                {name: "cfmofferprj", index: "cfmofferprj", width: 90, label: "确认人工金额", sortable: true, align: "right"},
                {name: "cfmmaterial", index: "cfmmaterial", width: 90, label: "确认材料金额", sortable: true, align: "right"},
                {name: "cfmremark", index: "cfmremark", width: 110, label: "专员确认说明", sortable: true, align: "left"},
                {name: "dutymandescr", index: "dutymandescr", width: 80, label: "判责人", sortable: true, align: "left"},
                {name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
                {name: "fixamount", index: "fixamount", width: 80, label: "判责金额", sortable: true, align: "right"},
                {name: "managecfmdate", index: "managecfmdate", width: 120, label: "经理审批时间", sortable: true, align: "left", formatter: formatTime},
                {name: "managecfmmandescr", index: "managecfmmandescr", width: 80, label: "经理审批人", sortable: true, align: "left"},
                {name: "providemandescr", index: "providemandescr", width: 80, label: "发放人", sortable: true, align: "left"},
                {name: "providedate", index: "providedate", width: 120, label: "发放时间", sortable: true, align: "left", formatter: formatTime},
                {name: "payamount", index: "payamount", width: 80, label: "发放金额", sortable: true, align: "right"},
                {name: "cancelremark", index: "cancelremark", width: 110, label: "取消说明", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
                {name: "cfmreturnremark", index: "cfmreturnremark", width: 80, label: "cfmreturnremark", sortable: true, align: "left", hidden: true},
                {name: "projectman", index: "projectman", width: 80, label: "projectman", sortable: true, align: "left", hidden: true},
                {name: "projectmandescr", index: "projectmandescr", width: 80, label: "projectmandescr", sortable: true, align: "left", hidden: true},
                {name: "appmantype", index: "appmantype", width: 80, label: "appmantype", sortable: true, align: "left", hidden: true},
                {name: "appworkercode", index: "appworkercode", width: 80, label: "appworkercode", sortable: true, align: "left", hidden: true},
                {name: "isprjallduty", index: "isprjallduty", width: 80, label: "isprjallduty", sortable: true, align: "left", hidden: true},
                {name: "designman", index: "designman", width: 80, label: "designman", sortable: true, align: "left", hidden: true},
                {name: "designriskfund", index: "designriskfund", width: 80, label: "designriskfund", sortable: true, align: "left", hidden: true},
                {name: "prjmanriskfund", index: "prjmanriskfund", width: 80, label: "prjmanriskfund", sortable: true, align: "left", hidden: true},
                {name: "type", index: "type", width: 80, label: "类型", sortable: true, align: "left", hidden: true},
            ],
        });
        $("#custCode").openComponent_customer();
        $("#appCzy").openComponent_employee();
        $("#appWorkerCode").openComponent_worker();
    });
    function doExcel(){
        var url = "${ctx}/admin/perfCycle/doExcel";
        doExcelAll(url);
    }

    function updateMultyStatus(content,newStatus){
        Global.Dialog.showDialog("updateMultyStatus",{
            title:"工人定责管理--发放",
            url:"${ctx}/admin/fixDuty/goProvide",
            height: 650,
            width:1300,
            returnFun: goto_query 
        });
    }
    function changeType(){
        var appManType=$("#appManType").val();
        if(appManType=="1"){
            $("#appCzy").val("");
            $("#openComponent_employee_appCzy").val("");
            $("#appCzy").parent().hide();
            $("#appWorkerCode").parent().show();
        }else if(appManType=="2"){
            $("#appCzy").parent().show();
            $("#openComponent_worker_appWorkerCode").val("");
            $("#appWorkerCode").val("");
            $("#appWorkerCode").parent().hide();
        }else{
            $("#appCzy").val("");
            $("#openComponent_employee_appCzy").val("");
            $("#appCzy").parent().show();
            $("#openComponent_worker_appWorkerCode").val("");
            $("#appCzy").parent().hide();
            $("#appWorkerCode").parent().hide();
        }
    }
    function detailQuery(){
        Global.Dialog.showDialog("detailQuery",{
            title:"工人定责管理--明细查询",
            url:"${ctx}/admin/fixDuty/goDetailQuery",
            height: 800,
            width:1300,
            returnFun: goto_query 
        });
    }
        function doReturn(){
        var ret = selectDataTableRow();     
        if (ret) {
            if(ret.status=='4'){
                art.dialog({
                    content:"<label style='width:100%;text-align:center'>您确定要退回复核吗？</label><br><label class='control-textarea' style='width:100px'>复核退回说明：</label> "+
                                "<textarea id='cfmReturnRemark' name='cfmReturnRemark' style='height:120px;width:300px '>${returnPay.cfmReturnRemark}</textarea>",
                    lock: true,
                    width: 200,
                    height: 100,
                    ok: function () {
                        $.ajax({
                            url:"${ctx}/admin/fixDuty/doReturn",
                            data:{No:ret.no,cfmReturnRemark:$("#cfmReturnRemark").val()},
                            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                            dataType:"json",
                            type:"post",
                            cache:false,
                            error:function(){
                                art.dialog({
                                    content:"执行复核退回异常"
                                });
                            },
                            success:function(obj){
                                if(obj.rs){
                                    art.dialog({
                                        content:obj.msg,
                                        time:1000,
                                        beforeunload: function () {
                                            goto_query();
                                        }
                                    });
                                }else{
                                    art.dialog({
                                        content:obj.msg
                                    });
                                }
                            }
                        });
                        return true;
                    },
                    cancel: function () {
                        return true;
                    }
                });
            }else{
                art.dialog({content: "只有状态为质检已复核的才可以复核退回",width: 200});
                return false;
            }
        }else{
            art.dialog({
                content: "请选择一条记录"
            });
        }
    }
    function doToaudit(){
        var ret = selectDataTableRow();     
        if (ret) {
            if(ret.status=='1'){
                art.dialog({
                    content:"您确定要提交吗？",
                    lock: true,
                    width: 200,
                    height: 100,
                    ok: function () {
                        $.ajax({
                            url:"${ctx}/admin/fixDuty/doToaudit",
                            data:{No:ret.no},
                            contentType:"application/x-www-form-urlencoded; charset=UTF-8",
                            dataType:"json",
                            type:"post",
                            cache:false,
                            error:function(){
                                art.dialog({
                                    content:"提交异常"
                                });
                            },
                            success:function(obj){
                                if(obj.rs){
                                    art.dialog({
                                        content:obj.msg,
                                        time:1000,
                                        beforeunload: function () {
                                            goto_query();
                                        }
                                    });
                                }else{
                                    art.dialog({
                                        content:obj.msg
                                    });
                                }
                            }
                        });
                        return true;
                    },
                    cancel: function () {
                        return true;
                    }
                });
            }else{
                art.dialog({content: "只有状态为工人申请的才可以提交 ",width: 200});
                return false;
            }
        }else{
            art.dialog({
                content: "请选择一条记录"
            });
        }
    }
    
    function goAdd(){
        Global.Dialog.showDialog("add",{
            title:"工人定责管理--新增",
            url:"${ctx}/admin/fixDuty/goAdd",
            postData:{
                m_umState:"A",
            },
            height: 650,
            width:1100,
            returnFun: goto_query 
        });
    }
    
    function goAddDesignDuty(){
        Global.Dialog.showDialog("view",{
            title:"工人定责管理--新增设计定责",
            url:"${ctx}/admin/fixDuty/goAddDesignDuty",
            postData:{
                m_umState:"AD",
            },
            height: 650,
            width:1300,
            returnFun: goto_query 
        });
    }
    
    function goUpdate(){
        var ret = selectDataTableRow();
        if(ret){
            if(ret.status!='1'){
                art.dialog({
                    content: "只有状态为工人申请的定则单才可进行编辑"
                });
                return false;
            }
            Global.Dialog.showDialog("update",{
                title:"工人定责管理--编辑",
                url:"${ctx}/admin/fixDuty/goUpdate",
                postData:{
                    m_umState:"M",
                    no:ret.no,
                    address:ret.address,
                    workType12:ret.worktype12,
                    workerName:ret.workername,
                    custCode:ret.custcode
                },
                height: 650,
                width:1100,
                returnFun: goto_query 
            });
        }else{
            art.dialog({
                    content: "请选择一条记录"
            });
        }
    }
    
    function doExcel(){
        var url = "${ctx}/admin/fixDuty/doExcel"; 
        doExcelAll(url);
    }
    </script>
</body>
</html>
