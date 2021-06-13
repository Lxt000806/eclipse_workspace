<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>添加材料明细</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>

<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" id="saveBtn" class="btn btn-system ">保存</button>
                <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
                <input type="hidden" id="itemType3Descr" name="itemType3Descr" value="${longFeeRule.itemType3Descr}"/>
                <div hidden>
                    <select id="itemType1" name="itemType1" value="${longFeeRule.itemType1}"></select>
                    <select id="itemType2" name="itemType2" value="${longFeeRule.itemType2}"></select>
                </div>
                <ul class="ul-form">
                    <li class="form-validate">
                        <label>材料类型3</label>
                        <select id="itemType3" name="itemType3" onchange="onItemType3Change()"></select>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function() {
        Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority", "itemType1", "itemType2", "itemType3");
        Global.LinkSelect.setSelect({
            firstSelect: "itemType1",
            secondSelect: "itemType2",
            thirdSelect: "itemType3",
            firstValue: '${longFeeRule.itemType1}',
            secondValue: '${longFeeRule.itemType2}',
            thirdValue: '${longFeeRule.itemType3}',
            disabled: "true"
        });

        $("#dataForm").bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {/*input状态样式图片*/
            },
            fields: {
                itemType3: {
                    validators: {
                        notEmpty: {
                            message: '材料类型3不能为空'
                        }
                    }
                },
            },
            submitButtons: 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });

        if ("${longFeeRule.m_umState}" === "V") {
            $("#itemType3,#saveBtn").attr("disabled", true);
        }

        $("#saveBtn").on("click", function() {
            $("#dataForm").bootstrapValidator('validate'); //验证表单
            if (!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
            var itemType3 = $("#itemType3").val();
            var itemType3s = "${longFeeRule.itemType3s}";
            if ("${longFeeRule.m_umState}" == "M") {
                if (itemType3s.indexOf(itemType3) > -1 && itemType3 != "${longFeeRule.itemType3}") {
                    art.dialog({
                        content: "材料类型3重复！"
                    });
                    return;
                }
            } else {
                if (itemType3s.indexOf(itemType3) > -1) {
                    art.dialog({
                        content: "材料类型3重复！"
                    });
                    return;
                }
            }
            var selectRows = [];
            var datas = $("#dataForm").jsonForm();
            selectRows.push(datas);
            Global.Dialog.returnData = selectRows;
            closeWin();
        });

    });

    function onItemType3Change() {
        var str = $("#itemType3").find("option:selected").text();
        str = str.split(' ');
        str = str[1];
        document.getElementById("itemType3Descr").value = str;
    }
</script>
</body>
</html>
