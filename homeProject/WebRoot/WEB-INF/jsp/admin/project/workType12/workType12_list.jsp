<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>工种分类12</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
function goConfig(){
    var ret = selectDataTableRow();
    if(!ret){
        art.dialog(
            {
                content:"请选择一条记录"
            }
        );
    }
    Global.Dialog.showDialog(
        "goConfig",
        {
            title:"工种分类12--配置工种施工需准备材料",
            url:"${ctx}/admin/workType12/goConfig?id=" + ret.worktype12code,
            height:550,
            width:1000,
            returnFun:goto_query
        }
    );
}


function update() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("workType12Update", {
            title : "工种分类12--编辑",
            url : "${ctx}/admin/workType12/goUpdate?id=" + ret.worktype12code,
            height : 680,
            width : 900,
            returnFun : goto_query
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

function view() {
    var ret = selectDataTableRow();
    if (ret) {
        Global.Dialog.showDialog("workType12", {
            title : "工种分类12--查看",
            url : "${ctx}/admin/workType12/goDetail?id=" + ret.worktype12code,
            height : 600,
            width : 900,
        });
    } else {
        art.dialog({
            content : "请选择一条记录"
        });
    }
}

function goSetBefWorkType(){
    var ret = selectDataTableRow();
    if(ret){
        Global.Dialog.showDialog("setBefWorkType", {
            id:"setBefWorkType",
            title : "工种分类12--设置上一工种",
            url : "${ctx}/admin/workType12/goSetBefWorkType?id=" + ret.worktype12code,
            height : 500,
            width : 600,
            returnFun : goto_query
        });
    }else{
        art.dialog({
            content : "请选择一条记录"
        });
    }

}
    /**初始化表格*/
    $(function() {
        Global.JqGrid.initJqGrid("dataTable", {
            url : "${ctx}/admin/workType12/goJqGrid",
            height : $(document).height() - $("#content-list").offset().top
                    - 90,
            styleUI : 'Bootstrap',
            colModel : [
                    {name: "worktype12code", index: "code", width: 60, label: "编号", sortable: true, align: "left"},
                    {name: "worktype12name", index: "descr", width: 100, label: "名称", sortable: true, align: "left"},
                    {name: "worktype1name", index: "worktype1", width: 100, label: "工种分类1", sortable: true, align: "left"},
                    {name: "beginprjitemname", index: "beginprjitem", width: 100, label: "开始施工项目", sortable: true, align: "left"},
                    {name: "prjitemname", index: "prjitem", width: 100, label: "结束施工项目", sortable: true, align: "left"},
                    {name: "offerworktype2name", index: "offerworktype2", width:100, label: "人工工种分类2", sortable: true, align: "left"},
                    {name: "perqualityfee", index: "perqualityfee", width: 130, label: "签约每工地扣质保金", sortable: true, align: "right"},
                    {name: "signqualityfee", index: "signqualityfee", width: 110, label: "签约质保金总额", sortable: true, align: "right"},
                    {name: "paynum", index: "paynum", width: 70, label: "付款期数", sortable: true, align: "right"},
                    {name: "minday", index: "minday", width: 100, label: "最少施工天数", sortable: true, align: "right"},
                    {name: "maxday", index: "maxday", width: 100, label: "最大施工天数", sortable: true, align: "right"},
                    {name: "qualityfeebegin", index: "qualityfeebegin", width: 100, label: "质保金起扣点", sortable: true, align: "right"},
                    {name: "mustpay", index: "mustpay", width: 130, label: "是否付款才允许申请", sortable: true, align: "left"},
                    {name: "appminday", index: "appminday", width:120, label: "申请最小延后天数", sortable: true, align: "right"},
                    {name: "appmaxday", index: "appmaxday", width:120, label: "申请最大延后天数", sortable: true, align: "right"},
                    {name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "right"},
                    {name: "needworktype2reqname", index: "needworktype2req", width: 150, label: "需要工种分类2人工需求", sortable: true, align: "left"},
                    {name: "befsameworktype12name", index: "befsameworktype12", width: 100, label: "上一同类工种", sortable: true, align: "left"},
                    {name: "confprjitemname", index: "confprjitem", width: 100, label: "验收施工项目", sortable: true, align: "left"},
                    {name: "begincheckdescr", index: "begincheckdescr", width: 120, label: "工期考核启动工种", sortable: true, align: "left"},
                    {name: "minsalaryprovideamount", index: "minsalaryprovideamount", width: 100, label: "最低发放金额", sortable: true, align: "right"},
                    {name: "isregistermall", index: "isregistermall", width: 100, label: "是否用于自装通", sortable: true, align: "left", hidden: true},
                    {name: "isregistermalldescr", index: "isregistermalldescr", width: 100, label: "是否用于自装通", sortable: true, align: "left"},
                    {name: "conftypedescr", index: "conftypedescr", width: 70, label: "验收类型", sortable: true, align: "left"},
                    {name: "expired", index: "expired", width: 70, label: "过期标志", sortable: true, align: "left"},
                    {name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "right", formatter:formatTime},
                    {name: "lastupdatedby", index: "lastupdatedby", width: 60, label: "修改人", sortable: true, align: "left"},
                    {name: "actionlog", index: "actionlog", width: 60, label: "操作", sortable: true, align: "left"}
            ]
        });
        $("#picModelSet").on("click", function () {
            var ret = selectDataTableRow();
            if(!ret){
                art.dialog(
                    {
                        content:"请选择一条记录"
                    }
                );
            }
            Global.Dialog.showDialog(
                "picModelSet",
                {
                    title:"工种分类12--图片模板配置",
                    url:"${ctx}/admin/workType12/goPicModelSet",
                    postData:{code: ret.worktype12code},
                    height:575,
                    width:953,
                    returnFun:goto_query
                }
            );
        });
        $("#qualityFeeSet").on("click", function () {
            var ret = selectDataTableRow();
            if(!ret){
                art.dialog(
                    {
                        content:"请选择一条记录"
                    }
                );
            }
            Global.Dialog.showDialog(
                "qualityFeeSet",
                {
                    title:"工种分类12--质保金配置",
                    url:"${ctx}/admin/workType12/goQualityFeeSet",
                    postData:{code: ret.worktype12code},
                    height:575,
                    width:953,
                    returnFun:goto_query
                }
            );
        });
    });
</script>
</head>

<body>
    <div class="body-box-list">
        <div class="query-form">
            <form action="" method="post" id="page_form" class="form-search">
                <input type="hidden" name="jsonString" value="" />
                <ul class="ul-form">
                    <li><label>工种分类12</label><input type="text" id="descr"
                        name="descr" style="width:160px;" value="${workType12.descr}" />
                    </li>
                    <li><label>工种分类1</label> <input type="text" id="workType1Name" name="workType1Name"
                        style="width:160px;" value="${workType12.workType1}" />
                    </li>
                    <li>
						<label>是否用于自装通</label>
						<house:xtdm id="isRegisterMall" dictCode="YESNO"></house:xtdm>
					</li>
                    <li class="search-group"><input type="checkbox" id="expired"
                        name="expired" value="${endProfPer.expired}"
                        onclick="hideExpired(this)" ${endProfPer.expired!='T' ?'checked':'' }/>
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>

        <div class="clear_float"></div>
        <div class="btn-panel">
            <div class="btn-group-sm">
                <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                <button type="button" class="btn btn-system " onclick="goSetBefWorkType()">设置上一工种</button>
                <house:authorize authCode="WORKERTYPE12_VIEW">
                    <button id="btnView" type="button" class="btn btn-system"
                        onclick="view()">查看</button>
                </house:authorize>
                <button type="button" class="btn btn-system " onclick="goConfig()">配置施工材料</button>
                <button type="button" class="btn btn-system " id="picModelSet">图片模板配置</button><%--add by zb on 20190430--%>
                <button type="button" class="btn btn-system " id="qualityFeeSet">质保金配置</button><%--add by cjg on 20190829--%>
            </div>
        </div>
        <div id="content-list">
            <table id="dataTable"></table>
            <div id="dataTablePager"></div>
        </div>
    </div>
</body>
</html>
