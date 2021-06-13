<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp"%>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
</head>

<body>
<div class="body-box-form">
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#basicInformation" data-toggle="tab">基本信息</a></li>
            <li class=""><a href="#members" data-toggle="tab">班组成员</a></li>
        </ul>
        <div class="tab-content">
            <div id="basicInformation" class="tab-pane fade in active">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
                            <house:token></house:token>
                            <input type="hidden" name="jsonString" value=""/>
                            <input type="hidden" id="beforeEmpCode" name="beforeEmpCode" value="${worker.empCode}"/>
                            <input type="hidden" id="expired" name="expired" value="${worker.expired}"/>
                            <ul class="ul-form">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <li>
                                            <label><span class="required">*</span>编码</label>
                                            <input type="text" id="code" name="code" style="width:160px;" value="${worker.code}"
                                                   placeholder="保存自动生成" readonly="readonly"/>
                                        </li>
                                        <li>
                                            <label><span class="required">*</span>姓名</label>
                                            <input type="text" id="nameChi" name="nameChi" style="width:160px;" value="${worker.nameChi}" readonly="readonly"/>
                                        </li>
                                        <house:authorize authCode="WORKER_PHONE">
                                            <li class="form-validate">
                                                <label><span class="required">*</span>电话</label>
                                                <input type="text" id="phone" name="phone" style="width:160px;"
                                                       value="${worker.phone}" readonly="readonly"/>
                                            </li>
                                        </house:authorize>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>身份证号</label>
                                            <input type="text" id="idnum" name="idnum" style="width:160px;"
                                                   value="${worker.idnum}" readonly="readonly"/>
                                        </li>
                                        <li>
                                            <label><span class="required">*</span>卡号1</label>
                                            <input type="text" id="cardId" name="cardId" style="width:160px;"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"
                                                   value="${worker.cardId}" readonly="readonly"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>开户行及支行1</label>
                                            <input type="text" id="bank" name="bank" value="${worker.bank}" readonly/>
                                        </li>
                                        <li>
                                            <label>卡号2</label>
                                            <input type="text" id="cardId2" name="cardId2" style="width:160px;"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"
                                                   value="${worker.cardId2}" readonly="readonly"/>
                                        </li>
                                        <li>
                                            <label>开户行及支行2</label>
                                            <input type="text" id="bank2" name="bank2" value="${worker.bank2}" readonly/>
                                        </li>
                                        <li>
                                            <label>介绍人</label>
                                            <input type="text" id="introduceEmp" name="introduceEmp" style="width:160px;"/>
                                        </li>
                                        <li>
                                            <label><span class="required">*</span>签约类型</label>
                                            <house:xtdm id="isSign" dictCode="WSIGNTYPE" style="width:160px;" value="${worker.isSign}" disabled="true"/>
                                        </li>
                                        <li class="form-validate">
                                            <label>签约时间</label>
                                            <input type="text" id="signDate" name="signDate"
                                                   class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                                   value="<fmt:formatDate value='${worker.signDate}' pattern='yyyy-MM-dd'/>" disabled/>
                                        </li>
                                        <li>
                                            <label>状态</label>
                                            <house:xtdm id="isLeave" dictCode="WORKERSTS" style="width:160px;" value="${worker.isLeave}"/>
                                        </li>
                                        <li id='lileave'>
                                            <label>离职日期</label>
                                            <input type="text" id="leaveDate" name="leaveDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${worker.leaveDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
                                        </li>
                                    </div>
                                    <div class="col-sm-4">
                                        <li>
                                            <label><span class="required">*</span>工种分类</label>
                                            <select id="workType12" name="workType12" disabled="disabled"></select>
                                        </li>
                                        <li>
                                            <label>所属分组</label>
                                            <select id="workType12Dept" name="workType12Dept" disabled="disabled"></select>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>工人级别</label>
                                            <house:xtdm id="level" dictCode="WORKERLEVEL" style="width:160px;" value="${worker.level}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>班组人数</label>
                                            <input type="text" id="num" name="num" style="width:160px;"
                                                   onkeyup="value=value.replace(/[^\.\d]/g,'')" value="${worker.num}" readonly/>
                                        </li>
                                        <li>
                                            <label>工作效率比例</label>
                                            <input type="text" id="efficiency" name="efficiency" style="width:160px;" onkeyup="value=value.replace(/[^\.\d]/g,'')"
                                                   value="${worker.efficiency}" readonly="readonly"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>是否自动安排</label>
                                            <house:xtdm id="isAutoArrange" dictCode="YESNO" style="width:160px;" value="${worker.isAutoArrange}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>归属事业部</label>
                                            <house:dict id="department1" dictCode="" sql="select Code,Code+' '+Desc1 Department1 from tdepartment1 where DepType='3'"
                                                        sqlValueKey="Code" sqlLableKey="Department1" value="${worker.department1}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>所属工程部</label>
                                            <house:department2 id="department2" dictCode="" depType="3" disabled="true" value="${worker.department2}"/>
                                        </li>
                                        <li>
                                            <label>一级居住区域</label>
                                            <select id="liveRegion" name="liveRegion" disabled="disabled"></select>
                                        </li>
                                        <li>
                                            <label>二级居住区域</label>
                                            <select id="liveRegion2" name="liveRegion2" disabled="disabled"></select>
                                        </li>
                                        <li>
                                            <label>归属专盘</label>
                                            <house:dict id="spcBuilder" dictCode="" sql="select Code,Code+' '+Descr SpcBuilder from tSpcBuilder"
                                                        sqlValueKey="Code" sqlLableKey="SpcBuilder" value="${worker.spcBuilder}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>归属区域</label>
                                            <house:DictMulitSelect id="belongRegion" dictCode="" sql="select Code,Descr Descr1 from tRegion where code <> ''"
                                                                   sqlValueKey="Code" sqlLableKey="Descr1" selectedValue="${worker.belongRegion}"></house:DictMulitSelect>
                                        </li>
                                        <li class="form-validate">
                                            <label id="empCodeLabel">员工编号</label>
                                            <input type="text" id="empCode" name="empCode" style="width:160px;"/>
                                        </li>
                                    </div>
                                    <div class="col-sm-4">
                                        <li class="form-validate">
                                            <label>项目经理类型</label>
                                            <house:xtdm id="isSupvr" dictCode="PRJMANTYPE" style="width:160px;" value="${worker.isSupvr}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label>监理星级</label>
                                            <house:dict id="prjLevel" dictCode="" sql="select Code,Code+' '+Descr PrjLevel from tPrjlevel"
                                                        sqlValueKey="Code" sqlLableKey="PrjLevel" value="${worker.prjLevel}"/>
                                        </li>
                                        <li>
                                            <label>工程大区</label>
                                            <house:dict id="prjRegionCode" dictCode="" sql="select Code,Code+' '+Descr PrjRegionCode from tPrjRegion"
                                                        sqlValueKey="Code" sqlLableKey="PrjRegionCode" value="${worker.prjRegionCode}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>承接工地类型</label>
                                            <house:xtdmMulit id="rcvPrjType" dictCode="" sql="select NOTE,CBM from tXTDM where ID='RCVPRJTYPE'"
                                                             sqlValueKey="CBM" sqlLableKey="NOTE" selectedValue="${worker.rcvPrjType}">
                                            </house:xtdmMulit>
                                        </li>
                                        <li>
                                            <label>交通工具</label>
                                            <house:xtdm id="vehicle" dictCode="VEHICLE" style="width:160px;" value="${worker.vehicle}" disabled="true"/>
                                        </li>
                                        <li>
                                            <label>住址</label>
                                            <input type="text" id="address" name="address" style="width:160px;" value="${worker.address}" readonly="readonly"/>
                                        </li>
                                        <li>
                                            <label>允许材料下单</label>
                                            <house:xtdm id="allowItemApp" dictCode="YESNO" style="width:160px;" value="${worker.allowItemApp}"/>
                                        </li>
                                        <li>
                                            <label>工人分类</label>
                                            <house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;" value="${worker.workerClassify}"/>
                                        </li>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <li>
                                            <label class="control-textarea">备注</label>
                                            <textarea id="remarks" name="remarks" rows="2" style="height: 50px;" readonly="readonly">${worker.remarks}</textarea>
                                        </li>
                                        <li>
                                            <label>过期</label>
                                            <input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}"
                                                   onclick="checkExpired(this)" ${worker.expired=="T"?"checked":""} disabled="disabled"/>
                                        </li>
                                    </div>
                                </div>
                            </ul>
                        </form>
                    </div>
                </div>
            </div>
            <div id="members" class="tab-pane fade">
                <div class="panel panel-system">
                    <div class="panel-body">
                        <div class="btn-group-xs">
                            <button class="btn btn-system" onclick="viewMember()"><span>查看</span></button>
                        </div>
                    </div>
                </div>
                <div id="content-list">
                    <table id="memberTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function() {
        /* 联动传入数据-开始 */
        Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12", "workType12", "workType12Dept")
        var dataSet = {
            firstSelect: "workType12",
            firstValue: "${worker.workType12}",
            secondSelect: "workType12Dept",
            secondValue: "${worker.workType12Dept}"
        }
        Global.LinkSelect.setSelect(dataSet)
        /* 结束 */
        Global.LinkSelect.initSelect("${ctx}/admin/worker/region", "liveRegion", "liveRegion2")
        Global.LinkSelect.setSelect({
            firstSelect: "liveRegion",
            firstValue: "${worker.liveRegion}",
            secondSelect: "liveRegion2",
            secondValue: "${worker.liveRegion2}"
        })

        /* 显示员工编号、姓名 */
        $("#introduceEmp").openComponent_employee({showValue: "${worker.introduceEmp}", showLabel: "${worker.introduceEmpDescr}", readonly: true})
        $("#empCode").openComponent_employee({showValue: "${worker.empCode}", showLabel: "${worker.empCodeDescr}", readonly: true})

        $("#isSign option[value='']").remove() /* 删除Select中索引值为""的Option(请选择...) */
        $("#isLeave option[value='']").remove()


        /* 开始需要执行的方法 */
        changeSignDate()
        changeType()

        /* 是否签约触发事件 */
        function changeSignDate() {
            var isSign = $.trim($("#isSign").val())
            $("#signDate").prop("disabled", true)
            if (isSign == 0) {
                $("#signDate").prev().find("span").remove()/* 找到label中的span，并将它移除 */
            } else {
                $("#signDate").prev().prepend("<span class='required'>*</span>")
            }
        }

        /* 工种分类是监理后触发事件 */
        function changeType() {
            var workType = $.trim("${worker.workType12}")
            $("#empCodeLabel").find("span").remove()
            $("#isSupvr").prev().find("span").remove()
            $("#prjLevel").prev().find("span").remove()
            if (workType == 06) {
                $("#empCodeLabel").prepend("<span class='required'>*</span>")
                $("#isSupvr").prev().prepend("<span class='required'>*</span>")
                $("#prjLevel").prev().prepend("<span class='required'>*</span>")
            }
            $("#empCode").attr("disabled", true)
            $("#isSupvr").attr("disabled", true)
            $("#prjLevel").attr("disabled", true)
        }

        $("#rcvPrjType_NAME").attr("disabled", true)

        Global.JqGrid.initJqGrid("memberTable", {
            url: "${ctx}/admin/worker/goMemberJqGrid",
            postData: {code: "${worker.code}"},
            height: 500,
            cellsubmit: "clientArray",
            colModel: [
                {name: "workercode", index: "workercode", width: 80, label: "班组编号", align: "left"},
                {name: "code", index: "code", label: "成员编号", width: 80, align: "left"},
                {name: "namechi", index: "namechi", label: "名字", width: 80, align: "left"},
                {name: "phone", index: "phone", label: "手机号", width: 80, align: "right"},
                {name: "isheadman", index: "isheadman", label: "是否班组长", width: 90, align: "left", hidden: true},
                {name: "isheadmandescr", index: "isheadmandescr", label: "是否班组长", width: 90, align: "left", formatter: headManDescr},
                {name: "idnum", index: "idnum", label: "身份证号", width: 120, align: "right"},
                {name: "cardid", index: "cardid", label: "卡号1", width: 120, align: "right"},
                {name: "bank", index: "bank", label: "发卡行1", width: 100, align: "left"},
                {name: "cardid2", index: "cardid2", label: "卡号2", width: 120, align: "right"},
                {name: "bank2", index: "bank2", label: "发卡行2", width: 100, align: "left"},
                {name: "salaryratio", index: "salaryratio", label: "工资分配比例", width: 100, align: "right"},
                {name: "expired", index: "expired", label: "过期", width: 40, align: "left"},
            ]
        })

    })

    function headManDescr(cellvalue, options, rowObject) {
        if (rowObject.isheadmandescr)
            return rowObject.isheadmandescr

        switch (rowObject.isheadman) {
            case '0':
                return '否'
            case '1':
                return '是'
            default:
                return ''
        }
    }

    function viewMember() {
        var grid = $("#memberTable")
        var selectedRowId = grid.getGridParam("selrow")

        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }

        Global.Dialog.showDialog("viewMember", {
            title: "班组成员-查看",
            url: "${ctx}/admin/worker/viewMember",
            postData: grid.getRowData(selectedRowId),
            height: 330,
            width: 680
        })
    }

</script>
</html>
