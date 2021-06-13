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
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="submit" class="btn btn-system " id="saveBtn">
                    <span>保存</span>
                </button>
                <button type="button" class="btn btn-system" id="closeBut">
                    <span>关闭</span>
                </button>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <ul class="nav nav-tabs">
            <li class="active" id="tab_basicInformation"><a href="#basicInformation" data-toggle="tab">基本信息</a></li>
            <li class="" id="tab_members"><a href="#members" data-toggle="tab">班组成员</a></li>
        </ul>
        <div class="tab-content">
            <div id="basicInformation" class="tab-pane fade in active">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form action="" method="post" id="page_form" class="form-search">
                            <house:token></house:token>
                            <input type="hidden" name="jsonString" value=""/>
                            <ul class="ul-form">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <li>
                                            <label><span class="required">*</span>编码</label>
                                            <input type="text" id="code" name="code" style="width:160px;" value="${worker.code}"
                                                   placeholder="保存自动生成" readonly="readonly"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>姓名</label>
                                            <input type="text" id="nameChi" name="nameChi" style="width:160px;" value="${worker.nameChi}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>电话</label>
                                            <input type="text" id="phone" name="phone" style="width:160px;"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{3})|(\d{4}))(?=\d)/g,'$1 ');"
                                                   value="${worker.phone}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>身份证号</label>
                                            <input type="text" id="idnum" name="idnum" style="width:160px;"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^xX\d]/g,'').replace(/(^(\d{6})|(\d{8}))(?=\d)/g,'$1 ');"
                                                   value="${worker.idnum}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>卡号1</label>
                                            <input type="text" id="cardId" name="cardId" style="width:160px;" placeholder="建行卡"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"
                                                   value="${worker.cardId}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>开户行及支行1</label>
                                            <input type="text" id="bank" name="bank" placeholder="如：建行福州铜盘支行">
                                        </li>
                                        <li class="form-validate">
                                            <label>卡号2</label>
                                            <input type="text" id="cardId2" name="cardId2" style="width:160px;"
                                                   onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');"
                                                   value="${worker.cardId2}"/>
                                        </li>
                                        <li>
                                            <label>开户行及支行2</label>
                                            <input type="text" id="bank2" name="bank2" placeholder="如：建行福州铜盘支行">
                                        </li>
                                        <li>
                                            <label>介绍人</label>
                                            <input type="text" id="introduceEmp" name="introduceEmp" style="width:160px;" value="${worker.introduceEmp}"/>
                                        </li>
                                        <li>
                                            <label><span class="required">*</span>签约类型</label>
                                            <house:xtdm id="isSign" dictCode="WSIGNTYPE" style="width:160px;" value="0" onchange="changeSignDate()"/>
                                        </li>
                                        <li class="form-validate">
                                            <label>签约时间</label>
                                            <input type="text" id="signDate" name="signDate"
                                                   class="i-date" style="width:160px;" onFocus="WdatePicker({onpicked:validateRefresh_signDate(), skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                                                   value="<fmt:formatDate value='${worker.signDate}' pattern='yyyy-MM-dd'/>" disabled/>
                                        </li>
                                        <li>
                                            <label>状态</label>
                                            <house:xtdm id="isLeave" dictCode="WORKERSTS" style="width:160px;" value="0"/>
                                        </li>
                                        <li id='lileave'>
                                            <label>离职日期</label>
                                            <input type="text" id="leaveDate" name="leaveDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${worker.leaveDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
                                        </li>
                                    </div>
                                    <div class="col-sm-4">
                                        <li class="form-validate">
                                            <label><span class="required">*</span>工种分类</label>
                                            <select id="workType12" name="workType12" value="${worker.workType12}"></select>
                                        </li>
                                        <li>
                                            <label>所属分组</label>
                                            <select id="workType12Dept" name="workType12Dept"></select>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>工人级别</label>
                                            <house:xtdm id="level" dictCode="WORKERLEVEL" style="width:160px;" value="${worker.level}"/>
                                        </li>
                                        <li>
                                            <label>班组人数</label>
                                            <input type="text" id="num" name="num" style="width:160px;"
                                                onkeyup="value=value.replace(/[^\.\d]/g,'')" value="1" readonly/>
                                        </li>
                                        <li>
                                            <label>工作效率比例</label>
                                            <input type="text" id="efficiency" name="efficiency" style="width:160px;" onkeyup="value=value.replace(/[^\.\d]/g,'')" value="1"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>是否自动安排</label>
                                            <house:xtdm id="isAutoArrange" dictCode="YESNO" style="width:160px;" value="${worker.isAutoArrange}"/>
                                        </li>
                                        <li>
                                            <label>归属事业部</label>
                                            <house:dict id="department1" dictCode="" sql="select Code,Code+' '+Desc1 Department1 from tdepartment1 where DepType='3'"
                                                        sqlValueKey="Code" sqlLableKey="Department1" value="${worker.department1}"/>
                                        </li>
                                        <li>
                                            <label>所属工程部</label>
                                            <house:department2 id="department2" dictCode="" depType="3"></house:department2>
                                        </li>
                                        <li>
                                            <label>一级居住区域</label>
                                            <select id="liveRegion" name="liveRegion" value="${worker.liveRegion}"></select>
                                        </li>
                                        <li>
                                            <label>二级居住区域</label>
                                            <select id="liveRegion2" name="liveRegion2" value="${worker.liveRegion2}"></select>
                                        </li>
                                        <li>
                                            <label>归属专盘</label>
                                            <house:dict id="spcBuilder" dictCode="" sql="select Code,Code+' '+Descr SpcBuilder from tSpcBuilder"
                                                        sqlValueKey="Code" sqlLableKey="SpcBuilder" value="${worker.spcBuilder}"/>
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
                                            <label>是否监理</label>
                                            <house:xtdm id="isSupvr" dictCode="PRJMANTYPE" style="width:160px;" value="${worker.isSupvr}"/>
                                        </li>
                                        <li class="form-validate">
                                            <label>监理星级</label>
                                            <house:dict id="prjLevel" dictCode="" sql="select Code,Code+' '+Descr PrjLevel from tPrjlevel"
                                                        sqlValueKey="Code" sqlLableKey="PrjLevel" value="${worker.prjLevel}"/>
                                        </li>
                                        <li>
                                            <label>工程大区</label>
                                            <house:dict id="prjRegionCode" dictCode="" sql="select Code,Code+' '+Descr PrjRegionCode from tPrjRegion where expired ='F' "
                                                        sqlValueKey="Code" sqlLableKey="PrjRegionCode" value="${worker.prjRegionCode}"/>
                                        </li>
                                        <li>
                                            <label>承接工地类型</label>
                                            <house:xtdmMulit id="rcvPrjType" dictCode="" sql="select NOTE,CBM from tXTDM where ID='RCVPRJTYPE'"
                                                             sqlValueKey="CBM" sqlLableKey="NOTE">
                                            </house:xtdmMulit>
                                        </li>
                                        <li>
                                            <label>交通工具</label>
                                            <house:xtdm id="vehicle" dictCode="VEHICLE" style="width:160px;" value="${worker.vehicle}"/>
                                        </li>
                                        <li>
                                            <label>住址</label>
                                            <input type="text" id="address" name="address" style="width:160px;"/>
                                        </li>
                                        <li>
                                            <label>允许材料下单</label>
                                            <house:xtdm id="allowItemApp" dictCode="YESNO" style="width:160px;" value="0"/>
                                        </li>
                                        <li class="form-validate">
                                            <label><span class="required">*</span>工人分类</label>
                                            <house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;"/>
                                        </li>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <li>
                                            <label class="control-textarea">备注</label>
                                            <textarea id="remarks" name="remarks" rows="2" style="height: 50px;">${worker.remarks}</textarea>
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
                            <button class="btn btn-system" onclick="addMember()"><span>新增</span></button>
                            <button class="btn btn-system" onclick="updateMember()"><span>编辑</span></button>
                            <button class="btn btn-system" onclick="deleteMember()"><span>删除</span></button>
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
        Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12", "workType12", "workType12Dept")/* 区域下拉栏联动 */
        Global.LinkSelect.initSelect("${ctx}/admin/worker/region", "liveRegion", "liveRegion2")
        $("#introduceEmp").openComponent_employee()/* 员工搜索控件 */
        $("#empCode").openComponent_employee()

        $("#isSign option[value='']").remove() /* 删除Select中索引值为""的Option(请选择...) */
        $("#isLeave option[value='']").remove()

        $("#page_form").bootstrapValidator({
            message: "请输入完整的信息",
            feedbackIcons: {/*input状态样式图片*/
                validating: "glyphicon glyphicon-refresh"
            },
            fields: {
                nameChi: {
                    validators: {
                        notEmpty: {
                            message: "请输入完整的信息"
                        },
                        stringLength: {
                            min: 1,
                            max: 20,
                            message: "长度必须在1-20之间"
                        }
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: "电话长度必须11位，请重新输入"
                        },
                        stringLength: {
                            min: 13,
                            max: 13,
                            message: "电话长度必须11位，请重新输入"
                        }
                    }
                },
                idnum: {
                    validators: {
                        notEmpty: {
                            message: "身份证不允许为空"
                        },
                        stringLength: {
                            min: 20,
                            max: 20,
                            message: "身份证长度必须18位，请重新输入"
                        }
                    }
                },
                cardId: {
                    validators: {
                        notEmpty: {
                            message: "卡号不允许为空"
                        }
                    }
                },
                bank: {
                    validators: {
                        notEmpty: {
                            message: "发卡行1不允许为空"
                        }
                    }
                },
                workType12: {
                    validators: {
                        notEmpty: {
                            message: "请选择工种分类"
                        }
                    }
                },
                level: {
                    validators: {
                        notEmpty: {
                            message: "工人级别未选择"
                        }
                    }
                },
                isAutoArrange: {
                    validators: {
                        notEmpty: {
                            message: "请选择是否自动安排"
                        }
                    }
                },
            }
        })

        /* 开始需要执行的方法 */
        changeSignDate()
        changeType()
        /* 获取原始form数据 */
        var originalData = $("#page_form").serialize()

        /*手机号码限制*/
        $("#phone").on("input propertychange", function() {
            var val = $(this).val()
            /* 去除空格，不然会把空格算上 */
            val = val.replace(/\s/g, "")
            if (val.length > 11) {
                var realVal = val.substring(0, 11)
                $(this).val(realVal)
            }
        })

        $("#idnum").on("input propertychange", function() {
            var val = $(this).val()
            val = val.replace(/\s/g, "")
            if (val.length > 18) {
                var realVal = val.substring(0, 18)
                $(this).val(realVal)
            }
        })

        /* 离焦触发 */
        $("#phone").blur(function() {
            $("#page_form").data("bootstrapValidator")
                .updateStatus("phone", "NOT_VALIDATED", null)
                .validateField("phone")
        })
        $("#idnum").blur(function() {
            $("#page_form").data("bootstrapValidator")
                .updateStatus("idnum", "NOT_VALIDATED", null)
                .validateField("idnum")
        })


        $("#workType12").change(function() {
            changeType()
        })

        /* 关闭按钮绑定数据是否更改校验 */
        $("#closeBut").on("click", function() {
            var changeData = $("#page_form").serialize()
            if (originalData != changeData) {
                art.dialog({
                    content: "数据已变动,是否保存？",
                    width: 200,
                    okValue: "确定",
                    ok: function() {
                        doSave()
                    },
                    cancelValue: "取消",
                    cancel: function() {
                        closeWin()
                    }
                })
            } else {
                closeWin()
            }

        })

        /* 保存 */
        $("#saveBtn").on("click", function() {
            doSave()
        })
        
        Global.JqGrid.initJqGrid("memberTable", {
		    height: 500,
		    cellsubmit: "clientArray",
		    colModel: [
		        {name: "workercode", index: "workercode", width: 80, label: "班组编号", align: "left", sortable: false},
		        {name: "code", index: "code", label: "成员编号", width: 80, align: "left", sortable: false},
		        {name: "namechi", index: "namechi", label: "名字", width: 80, align: "left", sortable: false},
		        {name: "phone", index: "phone", label: "手机号", width: 80, align: "right", sortable: false},
		        {name: "isheadman", index: "isheadman", label: "是否班组长", width: 90, align: "left", sortable: false, hidden: true},
		        {name: "isheadmandescr", index: "isheadmandescr", label: "是否班组长", width: 90, align: "left", sortable: false, formatter: headManDescr},
		        {name: "idnum", index: "idnum", label: "身份证号", width: 120, align: "right", sortable: false},
		        {name: "cardid", index: "cardid", label: "卡号1", width: 120, align: "right", sortable: false},
		        {name: "bank", index: "bank", label: "发卡行1", width: 100, align: "left", sortable: false},
		        {name: "cardid2", index: "cardid2", label: "卡号2", width: 120, align: "right", sortable: false},
		        {name: "bank2", index: "bank2", label: "发卡行2", width: 100, align: "left", sortable: false},
		        {name: "salaryratio", index: "salaryratio", label: "工资分配比例", width: 100, align: "right", sortable: false},
		        {name: "expired", index: "expired", label: "过期", width: 40, align: "left", sortable: false},
		    ]
		})

    }) // end of $(function() {})
    
    function headManDescr(cellvalue, options, rowObject) {
        
        switch (rowObject.isheadman) {
            case '0':
                return '否'
            case '1':
                return '是'
            default:
                return ''
        }
    }

    function validateRefresh_signDate() {
        $("#page_form").data("bootstrapValidator")
            .updateStatus("signDate", "NOT_VALIDATED", null)
            .validateField("signDate")
    }

    function validateRefresh() {
        $("#page_form").data("bootstrapValidator")
            .updateStatus("openComponent_employee_empCode", "NOT_VALIDATED", null)/* 重置某一单一验证字段验证规则  */
            .validateField("openComponent_employee_empCode")/* 触发指定字段的验证 */
    }

    function changeSignDate() {
        var isSign = $.trim($("#isSign").val())
        $("#page_form")
            .bootstrapValidator("addField", "signDate", {  /* 动态添加校验 */
                validators: {
                    notEmpty: {
                        message: "工人签约时间未选择",
                    }
                }
            })
        $("#page_form")
            .bootstrapValidator("addField", "workerClassify", {  /* 动态添加校验 */
                validators: {
                    notEmpty: {
                        message: "请选择工人分类",
                    }
                }
            })
        $("#page_form").data("bootstrapValidator").updateStatus("signDate", "NOT_VALIDATED", null)
        $("#signDate").prop("disabled", true)
        $("#page_form").data("bootstrapValidator").updateStatus("workerClassify", "NOT_VALIDATED", null)
        $("#workerClassify").prop("disabled", true)
        if (isSign == 0) {
            $("#signDate").val("")// 清空内容
            $("#signDate").prev().find("span").remove()/* 找到label中的span，并将它移除 */
            $("#page_form").bootstrapValidator("removeField", "signDate")/* 删除指定字段校验 */
            $("#workerClassify").val("")// 清空内容
            $("#workerClassify").prev().find("span").remove()/* 找到label中的span，并将它移除 */
            $("#page_form").bootstrapValidator("removeField", "workerClassify")/* 删除指定字段校验 */
        } else {
            $("#signDate").prev().find("span").remove()/* 找到label中的span，并将它移除 */
            $("#signDate").prev().prepend("<span class='required'>*</span>")
            $("#signDate").prop("disabled", false)/* 1.6以下版本在IE6使用JQuery的removeAttr方法删除disabled是无效的。 */
            $("#workerClassify").prev().find("span").remove()/* 找到label中的span，并将它移除 */
            $("#workerClassify").prev().prepend("<span class='required'>*</span>")
            $("#workerClassify").prop("disabled", false)/* 1.6以下版本在IE6使用JQuery的removeAttr方法删除disabled是无效的。 */
        }
    }

    /* 工种分类是监理后触发事件 */
    function changeType() {
        var workType = $.trim($("#workType12").val())
        $("#page_form")
            .bootstrapValidator("addField", "openComponent_employee_empCode", {  /* 动态添加校验 */
                validators: {
                    notEmpty: {
                        message: "当工种分类为项目经理时,员工编号必填",
                    }
                }
            })
            .bootstrapValidator("addField", "isSupvr", {  /* 动态添加校验 */
                validators: {
                    notEmpty: {
                        message: "当工种分类为项目经理时,是否监理必填",
                    }
                }
            })
            .bootstrapValidator("addField", "prjLevel", {  /* 动态添加校验 */
                validators: {
                    notEmpty: {
                        message: "当工种分类为项目经理时,监理星级必填",
                    }
                }
            })
        /* 重置校验 */
        $("#page_form").data("bootstrapValidator")
            .updateStatus("openComponent_employee_empCode", "NOT_VALIDATED", null)
            .updateStatus("isSupvr", "NOT_VALIDATED", null)
            .updateStatus("prjLevel", "NOT_VALIDATED", null)
        $("#empCodeLabel").find("span").remove()
        $("#isSupvr").prev().find("span").remove()
        $("#prjLevel").prev().find("span").remove()
        if (workType == 06) {
            $("#empCode").openComponent_employee({callBack: validateRefresh})/* 回调validateRefresh */
            $("#empCodeLabel").prepend("<span class='required'>*</span>")
            $("#isSupvr").prev().prepend("<span class='required'>*</span>")
            $("#prjLevel").prev().prepend("<span class='required'>*</span>")
            $("#isSupvr").attr("disabled", false)
            $("#prjLevel").attr("disabled", false)

        } else {
            $("#empCode").openComponent_employee()
            $("#isSupvr").val("")
            $("#prjLevel").val("")
            $("#isSupvr").attr("disabled", true)
            $("#prjLevel").attr("disabled", true)
            $("#page_form").bootstrapValidator("removeField", "openComponent_employee_empCode")/* 删除指定字段校验 */
            $("#page_form").bootstrapValidator("removeField", "isSupvr")
            $("#page_form").bootstrapValidator("removeField", "prjLevel")
        }
    }

    function doSave() {
    	// 在班组成员页验证无效，需先切换在基本信息页
    	if ($("ul li a[href='#members']").parent().hasClass("active")){	
    		$("#tab_basicInformation").addClass("active");
   			$("#basicInformation").addClass("tab-pane fade in active");
   			$("#tab_members").removeClass("active");
   			$("#members").removeClass("tab-pane fade in active");
   			$("#members").addClass("tab-pane fade");	
    	}	
    	
        $("#page_form").bootstrapValidator("validate")
        if (!$("#page_form").data("bootstrapValidator").isValid()) return
        if ($("#isLeave").val() == "1" && $("#leaveDate").val() == "") {
            art.dialog({content: "离职日期不能为空"})
            return
        }
        
        var datas = $("#page_form").jsonForm()
        
        datas.cardId = datas.cardId.replace(/\s+/g, "")
        datas.cardId2 = datas.cardId2.replace(/\s+/g, "")
        
        if (datas.cardId2 && !datas.bank2) {
            art.dialog({content: "请输入开户行及支行2"})
            return
        } else if (!datas.cardId2 && datas.bank2) {
            art.dialog({content: "请输入卡号2"})
            return
        }
        
        var grid = $("#memberTable")
        var rows = grid.getRowData()
        var salaryRatio = 0
        
        for (var i = 0; i < rows.length; i++)
            if (rows[i].expired === 'F')
                salaryRatio += parseFloat(rows[i].salaryratio)
            
        if (salaryRatio > 1) {
            art.dialog({content: "成员工资比例不能小于0"})
            return
        }
        
        datas.memberJson = JSON.stringify(rows)
        
        var idnum = $("#idnum").val()
        
        /* 验证身份证是否重复 */
        $.ajax({
            url: "${ctx}/admin/worker/checkIdNum",
            type: "post",
            data: {idNum: idnum},
            dataType: "text",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "请求数据出错"})
            },
            success: function(obj) {
                if (obj == "true") {
                    doSaveAjax(datas)
                } else {
                    art.dialog({
                        content: "工人中已存在相同身份证号,是否继续保存",
                        width: 200,
                        okValue: "确定",
                        ok: function() {
                            doSaveAjax(datas)
                        },
                        cancelValue: "取消",
                        cancel: function() {
                        }
                    })
                }
            }
        })
    }

    function doSaveAjax(datas) {
        $.ajax({
            url: "${ctx}/admin/worker/doSave",
            type: "post",
            data: datas,
            dataType: "json",
            cache: false,
            error: function(obj) {
                showAjaxHtml({"hidden": false, "msg": "保存数据出错"})
            },
            success: function(obj) {
                if (obj.rs) {
                    art.dialog({
                        content: obj.msg,
                        time: 1000,
                        beforeunload: function() {
                            closeWin()
                        }
                    })
                } else {
                    $("#_form_token_uniq_id").val(obj.token.token)
                    art.dialog({
                        content: obj.msg,
                        width: 200
                    })
                }
            }
        })
    }
    
    function addMember() {
	    Global.Dialog.showDialog("addMember", {
	        title: "班组成员-新增",
	        url: "${ctx}/admin/worker/addMember",
	        height: 400,
	        width: 680,
	        returnFun: function(data) {
	            var grid = $("#memberTable")
	            grid.addRowData(generateRowId(), data, 'first')
	            
	            updateWorkerNum()
	        }
	    })
	}
	
	function updateMember() {
	    var grid = $("#memberTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
	    Global.Dialog.showDialog("addMember", {
	        title: "班组成员-编辑",
	        url: "${ctx}/admin/worker/updateMember",
	        postData: grid.getRowData(selectedRowId),
	        height: 400,
	        width: 680,
	        returnFun: function(data) {
                grid.setRowData(selectedRowId, data)
                updateWorkerNum()
            }
	    })
	}
	
	function deleteMember() {
	    var grid = $("#memberTable")
        var selectedRowId = grid.getGridParam("selrow")
        
        if (!selectedRowId) {
            art.dialog({content: '请选择一条记录'})
            return
        }
        
        grid.delRowData(selectedRowId)
        
        updateWorkerNum()
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
	
    function updateWorkerNum() {
        var grid = $("#memberTable")
        var rows = grid.getRowData()
        var num = 0
        
        for (var i = 0; i < rows.length; i++)
            if (rows[i].expired === 'F') num++
        
        $("#num").val(num + 1)
    }

    function generateRowId() {
        var grid = $("#memberTable")
        var ids = grid.getDataIDs()
        
        var maxId = 0
        for (var i = 0; i < ids.length; i++) {
            var temporaryId = parseInt(ids[i])
            maxId = maxId >= temporaryId ? maxId : temporaryId
        }

        return ++maxId
    }
</script>
</html>
