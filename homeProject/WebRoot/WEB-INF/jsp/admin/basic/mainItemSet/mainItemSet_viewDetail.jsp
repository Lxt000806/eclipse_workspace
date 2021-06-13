<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>编辑主材套餐包明细</title>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
    <script src="${resourceRoot}/pub/component_custTypeItem.js?v=${v}"type="text/javascript"></script>
    <script type="text/javascript">

        $(function() {
            $("#itemcode").openComponent_item({
			    showValue: '${map.itemcode}',
			    showLabel: '${map.itemdescr}',
			    callBack: function(data) {
			        $("#itemdescr").val(data.descr);
			        getAlgorithm(data.code);
			        findCutType(data.code);
			    }
			});
			            
            $("#custtypeitempk").openComponent_custTypeItem({
			    showValue: '${map.custtypeitempk}',
			    showLabel: '${map.itemdescr}',
			    condition: {custType: "${map.custType}"},
			    callBack: function(data) {
			        $("#itemcode").val(data.code);
			        $("#itemdescr").val(data.descr);
			        getAlgorithm(data.code);
			        findCutType(data.code);
			    }
			});
			
			getAlgorithm("${map.itemcode}");
			findCutType("${map.itemcode}");
            changeAlgorithm();
            
		    if ("${map.isoutset}" === "0") {
		        $("#itemCodeLi").hide();
		    } else {
		        $("#custTypeItemLi").hide();
		    }

        });

		function getAlgorithm(itemCode) {
		    $.ajax({
		        url: '${ctx}/admin/item/getAlgorithm',
		        type: 'post',
		        data: {
		            'code': itemCode,
		        },
		        async: false,
		        dataType: 'json',
		        cache: false,
		        error: function(obj) {
		            showAjaxHtml({
		                "hidden": false,
		                "msg": '保存数据出错~'
		            });
		
		        },
		        success: function(obj) {
		            $("#algorithm").empty();
		            $("#algorithm").append($("<option/>").text("请选择...").attr("value", ""));
		            if (obj.length === 0) {
		                $("#algorithm").attr("disabled", true);
		            } else {
		                $("#algorithm").removeAttr("disabled");
		                $.each(obj, function(i, o) {
		                    $("#algorithm").append($("<option/>").text(o.fd).attr("value", o.Code));
		                });
		                $("#algorithm").val("${map.algorithm}");
		            }
		        }
		    });
		}
		
        function findCutType(itemCode) {
            $.ajax({
                url: '${ctx}/admin/prePlanTempDetail/getQtyByCutType',
                type: 'post',
                data: {
                    'itemCode': itemCode
                },
                async: false,
                dataType: 'json',
                cache: false,
                error: function(obj) {
                    showAjaxHtml({
                        "hidden": false,
                        "msg": '保存数据出错~'
                    });
        
                },
                success: function(obj) {
                    $("#cuttype").empty();
                    $("#cuttype").append($("<option/>").text("请选择...").attr("value", ""));
                    $("#cuttype").removeAttr("disabled");
                    $.each(obj, function(i, o) {
                        $("#cuttype").append($("<option/>").text(o.fd).attr("value", o.Code));
                    });
                    $("#cuttype").val("${map.cuttype}");
                }
            });
        }
        
        function changeAlgorithm() {
            if ($("#algorithm").val() == "") {
                $("#cuttype").val("");
                $("#cuttype").attr("disabled", true);
                return;
            }
            $.ajax({
                url: '${ctx}/admin/algorithm/checkIsCalCutFee',
                type: 'post',
                data: {
                    'code': $("#algorithm").val(),
                },
                async: false,
                dataType: 'json',
                cache: false,
                error: function(obj) {
                    showAjaxHtml({
                        "hidden": false,
                        "msg": '保存数据出错~'
                    });
                },
                success: function(obj) {
                    if (obj[0].isCalCutFee != "1") {
                        $("#cuttype").val("");
                        $("#cuttype").attr("disabled", true);
                    } else {
                        $("#cuttype").removeAttr("disabled");
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="body-box-form">
    <div class="panel panel-system">
        <div class="panel-body">
            <div class="btn-group-xs">
                <button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
                <input type="hidden" id="itemdescr" name="itemdescr" value="${map.itemdescr}"/>
                <input type="hidden" name="expired" value="F"/>
                <input type="hidden" name="actionlog" value="EDIT">
                <house:token></house:token>
                <ul class="ul-form">
                    <li id="itemCodeLi" class="form-validate">
                        <label><span class="required">*</span>材料编号</label>
                        <input type="text" id="itemcode" name="itemcode"/>
                    </li>
                    <li id="custTypeItemLi" class="form-validate">
                        <label><span class="required">*</span>套餐材料PK</label>
                        <input type="text" id="custtypeitempk" name="custtypeitempk"/>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>算法</label>
                        <select id="algorithm" name="algorithm"></select>
                    </li>
                    <li class="form-validate">
                        <label>算法系数</label>
                        <input type="text" id="algorithmper" name="algorithmper" value="${map.algorithmper}"/>
                    </li>
                    <li class="form-validate">
                        <label>算法扣除数</label>
                        <input type="text" id="algorithmdeduct" name="algorithmdeduct" value="${map.algorithmdeduct}"/>
                    </li>
                    <li class="form-validate">
                        <label>数量</label>
                        <input type="text" id="qty" name="qty" value="${map.qty}"/>
                    </li>
                    <li class="form-validate">
                        <label>切割类型</label>
                        <select id="cuttype" name="cuttype" value="${map.cuttype}"></select>
                    </li>
                    <li class="form-validate">
                        <label><span class="required">*</span>材料类型说明</label>
                        <house:xtdm id="itemtypecode" dictCode="ITEMTYPEDESCR" value="${map.itemtypecode}"></house:xtdm>
                    </li>
                </ul>
                <ul class="ul-form">
                    <li>
                        <label class="control-textarea">备注</label>
                        <textarea id="remarks" name="remarks">${map.remarks}</textarea>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
</body>
</html>
