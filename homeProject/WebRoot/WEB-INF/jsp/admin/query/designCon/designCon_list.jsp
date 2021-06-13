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
    <title>设计进度跟踪</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_czybm.js?v=${v}" type="text/javascript"></script>
    <style type="text/css">
        .table > tbody > tr.success > td {
            background-color: #D0EEF3;
            color: #000;
        }
    </style>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <input type="hidden" id="expired" name="expired"/>
            <ul class="ul-form">
                <li>
                    <label>量房日期从</label>
                    <input type="text" id="measureDateFrom" name="measureDateFrom" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>                        
                    <label>至</label>
                    <input type="text" id="measureDateTo" name="measureDateTo" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>报价日期从</label>
                    <input type="text" id="priceDateFrom" name="priceDateFrom" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>                        
                    <label>至</label>
                    <input type="text" id="priceDateTo" name="priceDateTo" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>设计出图从</label>
                    <input type="text" id="designDrawingDateFrom" name="designDrawingDateFrom" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>                        
                    <label>至</label>
                    <input type="text" id="designDrawingDateTo" name="designDrawingDateTo" class="i-date"
                        onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
                </li>
                <li>
                    <label>一级部门</label>
                    <house:DictMulitSelect id="department1" dictCode=""
                            sql="${sql}" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1('','','','','${czybh}')"></house:DictMulitSelect>
                </li>
                <li>
                    <label>二级部门</label>
                    <house:DictMulitSelect id="department2" dictCode="" sql="select code, desc1 from tDepartment2 where 1=2" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment2()"></house:DictMulitSelect>
                </li>
                <li hidden>
                    <label>三级部门</label>
                    <house:DictMulitSelect id="department3" dictCode="" sql="select code, desc1 from tDepartment3 where 1=2"
                            sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
                </li>
                <li>
                    <label>楼盘</label>
                    <input type="text" name="address"/>
                </li>
                <li>
                    <label>设计状态</label>
                    <select name="designStatus">
                        <option value="">请选择</option>
                        <option value="unmeasured">未量房</option>
                        <option value="undrawn">未出图</option>
                        <option value="unpriced">未报价</option>
                    </select>
                </li>
                <li>
                    <label>联系人角色</label>
                    <select id="contactRole" name="contactRole">
                        <option value="">请选择</option>
                        <option value="01">业务员</option>
                        <option value="00">设计师</option>
                        <option value="24">翻单员</option>
                    </select>
                </li>
                <li>
                    <label>客户状态</label>
                    <house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" unShowValue="4,5"></house:xtdmMulit>
                </li>
                <li>
                    <label>员工编号</label>
                    <input type="text" id="czybh" name="czybh" />
                </li>
                <li>
                    <input type="checkbox" name="expired_show" onclick="hideExpired(this)" checked/>
                    <label for="expired_show" style="margin-left: -3px; text-align: left;">隐藏过期</label>
                    <button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
                    <button type="button" class="btn btn-system btn-sm" onclick="clearForm()">清空</button>
                </li>
            </ul>
        </form>
    </div>
</div>
<div class="pageContent">
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="DESIGNCON_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="DESIGNCON_EXCEL">
                <button type="button" class="btn btn-system" onclick="exportExcel()">
                    <span>导出Excel</span>
                </button>
            </house:authorize>
        </div>
    </div>
    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#czybh").openComponent_czybm();
    
        Global.JqGrid.initJqGrid("dataTable", {
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "address", index: "address", width: 130, label: "楼盘", sortable: true, align: "left", frozen: true},
                {name: "custname", index: "custname", width: 80, label: "客户名称", sortable: true, align: "left", frozen: true, cellattr: clickable},
                {name: "code", index: "code", width: 80, label: "客户编号 ", sortable: true, align: "left", hidden: true},
                {name: "conman", index: "conman", width: 80, label: "联系人", sortable: true, align: "left", hidden: true},
                {name: "conmandescr", index: "conmandescr", width: 80, label: "联系人", sortable: true, align: "left"},
                {name: "conwaydescr", index: "conwaydescr", width: 80, label: "联系方式", sortable: true, align: "left"},
                {name: "condate", index: "condate", width: 80, label: "最近联系时间", sortable: true, align: "left", formatter: formatDate},
                {name: "conremarks", index: "conremarks", width: 300, label: "跟踪内容", sortable: true, align: "left"},
                {name: "nextcondate", index: "nextcondate", width: 80, label: "下次联系时间", sortable: true, align: "left", formatter: formatDate},
                {name: "signinpk", index: "signinpk", width: 80, label: "签到", sortable: true, align: "left", hidden: true},
                {name: "measuredate", index: "measuredate", width: 80, label: "量房日期", sortable: true, align: "left", cellattr: clickable, formatter: formatDate},
                {name: "designdrawingdate", index: "designdrawingdate", width: 80, label: "设计出图", sortable: true, align: "left", cellattr: clickable, formatter: formatDate},
                {name: "visitdate", index: "visitdate", width: 80, label: "到店日期", sortable: true, align: "left", formatter: formatDate},
                {name: "setdate", index: "setdate", width: 80, label: "下定日期", sortable: true, align: "left", formatter: formatDate},
                {name: "pricedate", index: "pricedate", width: 80, label: "报价日期", sortable: true, align: "left", formatter: formatDate},
                {name: "renderingdate", index: "renderingdate", width: 80, label: "效果图出图", sortable: true, align: "left", cellattr: clickable, formatter: formatDate},
                {name: "businessmanname", index: "businessmanname", width: 100, label: "业务员", sortable: true, align: "left"},
                {name: "designmanname", index: "designmanname", width: 100, label: "设计师", sortable: true, align: "left"},
                {name: "againmanname", index: "againmanname", width: 100, label: "翻单员", sortable: true, align: "left"},
                {name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
                {name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
            ],
            onCellSelect: showDetail,
            gridComplete: function() {
				$(".ui-jqgrid-bdiv").scrollTop(0);
				frozenHeightReset("dataTable");
			    $(".frozen-div, .frozen-bdiv").css("width", "unset");
	        },
        })
        
        $("#dataTable").setFrozenColumns().setGridParam({url: "${ctx}/admin/designCon/goJqGrid"})
    })
    
    function clickable(rowId, val, rawObject, cm, rdata) {
        return "style='cursor: pointer; color: #198EDE;"
    }
    
    function showDetail(rowid, iCol, cellcontent, e) {   
        var rowData = $("#dataTable").getRowData(rowid)
        var colModel = $("#dataTable").getGridParam("colModel")
        var columnName = colModel[iCol].name
        
        switch (columnName) {
            case "custname":
                view(rowData.code);
            break;
            case "measuredate":
                viewSignIn(rowData.signinpk);
            break;
            case "designdrawingdate":
                viewCustDoc(rowData.code, "3,10");
            break;
            case "renderingdate":
                viewCustDoc(rowData.code, "2");
            break;
        }
            
    }
    
    function view(custCode) {
        var ret = selectDataTableRow()
        if (ret) {
            Global.Dialog.showDialog("designConView", {
                title: "设计进度跟踪--查看",
                url: "${ctx}/admin/designCon/goView",
                postData: {
                    custCode: custCode || ret.code,
                    contactRole: $("#contactRole").val(),
                    conMan: $("#czybh").val()
                },
                height: 700,
                width: 1250
            })
        } else {
            art.dialog({content: "请选择一条记录"})
        }
    }
    
    function viewSignIn(pk) {
        if (pk) {
            Global.Dialog.showDialog("designConViewSignIn", {
	          title:"设计进度跟踪--查看签到信息",
	          url: "${ctx}/admin/designCon/viewSignIn",
	          postData: {
	              pk: pk
	          },
	          height: 650,
	          width: 1000
	        });
        }
    }
    
    function viewCustDoc(custCode, docType2) {
        Global.Dialog.showDialog("designConViewCustDoc", {
            title:"设计进度跟踪--查看项目资料",
            url: "${ctx}/admin/designCon/viewCustDoc",
            postData: {
                custCode: custCode,
                docType2: docType2
            },
            height: 700,
            width: 1250
        });
    }

    function exportExcel() {
        doExcelAll("${ctx}/admin/designCon/doExcel")
    }
    
    function clearForm() {
	    $("#page_form input").val('');
	    $("#page_form select").val('');
	
	    $.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	    $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	    $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	    $.fn.zTree.getZTreeObj("tree_department3").checkAllNodes(false);
	    $.fn.zTree.init($("#tree_department2"), {}, []);
	    $.fn.zTree.init($("#tree_department3"), {}, []);
	}

</script>
</body>
</html>
