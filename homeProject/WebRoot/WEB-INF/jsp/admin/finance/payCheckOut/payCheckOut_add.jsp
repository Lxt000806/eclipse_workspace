<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增付款单记账</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
    <META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
    <META HTTP-EQUIV="expires" CONTENT="0" />
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_company.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
	var selectRows = [];
    $(function(){
        $("#custCode").openComponent_customer();
        $("#cmpCode").openComponent_company();
        $("#openComponent_company_cmpCode").prop("readonly", true);
        //初始化表格
        Global.JqGrid.initJqGrid("dataTable",{
            url:"${ctx}/admin/payCheckOut/goCustPayJqGrid",
            postData:{
                allDetailInfo:$("#allDetailInfo").val(),custStatus:"3,4,5"
            },
            multiselect: true,
            height:$(document).height()-$("#content-list").offset().top-82,
            styleUI: 'Bootstrap', 
            colModel : [
                {name: "pk", index: "pk", width: 70, label: "付款单号", sortable: true, align: "left"},
                {name: "cmpcode", index: "cmpcode", width: 70, label: "公司编号", sortable: true, align: "left", hidden: true},
                {name: "cmpdesc2", index: "cmpdesc2", width: 140, label: "公司", sortable: true, align: "left"},
                {name: "documentno", index: "documentNo", width: 70, label: "档案编号", sortable: true, align: "left"},
                {name: "custdescr", index: "custDescr", width: 70, label: "客户名", sortable: true, align: "left"},
                {name: "address", index: "address", width: 160, label: "楼盘", sortable: true, align: "left"},
                {name: "custstatus", index: "custStatus", width: 70, label: "客户状态", sortable: true, align: "left"},
                {name: "date", index: "date", width: 80, label: "付款日期", sortable: true, align: "left", formatter:formatDate},
                {name: "amount", index: "amount", width: 85, label: "付款金额", sortable: true, align: "right"},
                {name: "procedurefee", index: "procedureFee", width: 60, label: "手续费", sortable: true, align: "right"},
                {name: "rcvactdescr", index: "rcvactdescr", width: 180, label: "账户", sortable: true, align: "left"},
                {name: "payno", index: "payNo", width: 70, label: "收款单号", sortable: true, align: "left"},
                {name: "custpaytypedescr", index: "custpaytypedescr", width: 80, label: "类型", sortable: true, align: "left"},
				{name: "custpaytype", index: "custpaytype", width: 80, label: "类型", sortable: true, align: "left",hidden:true},
                {name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastUpdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter:formatTime},
                {name: "code", index: "code", width: 85, label: "客户编号", sortable: true, align: "left", hidden: true},
                {name: "checkseq", index: "checkSeq", width: 60, label: "结算顺序号", sortable: true, align: "left",hidden:true},
                {name: "actualamount", index: "actualAmount", width: 85, label: "实际到账", sortable: true, align: "left", hidden: true},
                {name: "custtypedescr", index: "custtypedescr", width: 85, label: "客户类型", sortable: true, align: "left", hidden: true},
                {name: "posdescr", index: "posdescr", width:200, label: "pos机", sortable:true, align:"left", hidden: true},
                {name: "regiondescr", index: "regiondescr", width: 50, label: "片区", sortable: true, align: "left",hidden:true},
                {name: "checkouttype", index: "checkouttype", width: 50, label: "记账类型", sortable: true, align: "left", hidden: true},
            ],
        });
        //全选
        $("#selAll").on("click",function(){
            Global.JqGrid.jqGridSelectAll("dataTable",true);
        });
        //全不选
        $("#selNone").on("click",function(){
            Global.JqGrid.jqGridSelectAll("dataTable",false);
        });
        //保存    
        $("#saveBtn").on("click",function(){
        	var allDetailInfo=$("#allDetailInfo").val();
        	console.log(allDetailInfo);
            var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
            if(ids.length == 0){
                Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");  
                return;
            }
            $.each(ids,function(k,id){
                var row = $("#dataTable").jqGrid('getRowData', id);
                selectRows.push(row);
                if(allDetailInfo != ""){
					allDetailInfo += ",";
				}
				allDetailInfo += row.pk;
            });
            $("#allDetailInfo").val(allDetailInfo)
            goto_query();
        });
        //改写窗口右上角关闭按钮事件
   		var titleCloseEle = parent.$("div[aria-describedby=dialog_save]").children(".ui-dialog-titlebar");
   		$(titleCloseEle[0]).children("button").remove();
   		$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
   									+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
   		$(titleCloseEle[0]).children("button").on("click", saveClose); 
    });
    // 需要修改的部分
    function clearForm(){
        $("#page_form input[type='text']").val('');
        $("#rcvAct").val('');
        $("#custStatus").val('');
        $.fn.zTree.getZTreeObj("tree_custStatus").checkAllNodes(false);
    }
    function saveClose(){
    	Global.Dialog.returnData = selectRows;
    	closeWin();
    }
</script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="saveClose()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form role="form" class="form-search" id="page_form" action=""
					method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value="" /> <input
						type="hidden" id="allDetailInfo" name="allDetailInfo"
						value="${payCheckOut.allDetailInfo}" />
					<ul class="ul-form">
                        <li>
                            <label for="cmpCode">公司编号</label>
                            <input type="text" id="cmpCode" name="cmpCode">
                        </li>
						<li><label>客户编号</label> <input type="text" id="custCode"
							name="custCode" style="width:160px;" />
						</li>
						<li><label>楼盘</label> <input type="text" id="address"
							name="address" style="width:160px;" />
						</li>
						<li><label>付款单号</label> <input type="text" id="payCheckOutNo"
							name="payCheckOutNo" style="width:160px;"
							/>
						</li>
						<li><label>付款日期从</label> <input type="text" id="dateFrom"
							name="dateFrom" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li><label>至</label> <input type="text" id="dateTo"
							name="dateTo" class="i-date" style="width:160px;"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li class="form-validate"><label>收款账号</label> <house:DictMulitSelect
								id="rcvAct" dictCode=""
								sql="select Code,Descr from tRcvAct where Expired='F'"
								sqlValueKey="Code" sqlLableKey="Descr" >
							</house:DictMulitSelect></li>
						<li><label>客户状态</label> <house:xtdmMulit id="custStatus"
								dictCode="CUSTOMERSTATUS" selectedValue="3,4,5"></house:xtdmMulit>
						</li>
						<li>
						    <label>登记日期从</label>
						    <input type="text" id="addDateFrom" name="addDateFrom" class="i-date"
                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                        </li>
                        <li>
                            <label>至</label>
                            <input type="text" id="addDateTo" name="addDateTo" class="i-date"
                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                        </li>
						<li style="float:right;padding-right:105px;padding-top:8px">
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
