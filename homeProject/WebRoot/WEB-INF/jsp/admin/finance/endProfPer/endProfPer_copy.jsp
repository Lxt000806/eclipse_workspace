<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>材料毛利率--复制</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
    $("#custCode").openComponent_customer({showLabel:"${endProfPer.CustDescr}",showValue:"${endProfPer.CustCode}",condition:{status:"5"} ,callBack: getData});
    function getData(data){
        if (!data) return;
        $("#custDescr").val(data.descr),
        $("#custAddress").val(data.address),
        validateRefresh('openComponent_customer_custCode'); 
    }
    $("#dataForm").bootstrapValidator("addField", "openComponent_customer_custCode", { 
         validators: {  
             remote: {
              message: '',
              url: '${ctx}/admin/customer/getCustomer',
              data: getValidateVal,  
              delay: 2 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
            }
         }  
     });
});


    

    $(function() {
        //初始化一二级区域联动
        Global.LinkSelect.initSelect("${ctx}/admin/builder/regionCodeByAuthority","regionCode","regionCode2");
             Global.LinkSelect.setSelect({firstSelect:'regionCode',
                                firstValue:'${builder.RegionCode}',
                                secondSelect:'regionCode2',
                                secondValue:'${builder.RegionCode2}'
                                });  
                                
        $("#saveBtn").on("click", function() {
            /* $("#dataForm").bootstrapValidator('validate');
            if (!$("#dataForm").data('bootstrapValidator').isValid()){
                return;
            } */
                
            var datas = $("#dataForm").serialize();
            $.ajax({
                url : "${ctx}/admin/endProfPer/doAdd",
                type : "post",
                data : datas,
                dataType : "json",
                cache : false,
                error : function(obj) {
                    showAjaxHtml({
                        "hidden" : false,
                        "msg" : "保存数据出错~"
                    });
                },
                success : function(obj) {
                    if (obj.rs) {
                        art.dialog({
                            content : obj.msg,
                            time : 1000,
                            beforeunload : function() {
                                closeWin();
                            }
                        });
                    } else {
                        $("#_form_token_uniq_id").val(obj.token.token);
                        art.dialog({
                            content : obj.msg,
                            width : 200
                        });
                    }
                }
            });
        });
    });
</script>
</head>
<body>
    <form action="" method="post" id="dataForm" class="form-search">
        <div class="body-box-form">
            <div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn">
                            <span>保存</span>
                        </button>
                        <button type="button" class="btn btn-system" id="closeBut"
                            onclick="closeWin(false)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="panel panel-info">
                <div class="panel-body">
                    <input type="hidden" name="jsonString" value="" />
                    <ul class="ul-form">
                        <div class="validate-group row">
                            <li class="form-validate"><label style="width:185px;"><span
                                    class="required">*</span>客户编号</label> <input type="text" id="custCode"
                                maxlength="10" name="custCode" style="width:160px;" value="${endProfPer.CustCode}"
                                data-bv-notempty data-bv-notempty-message="客户编号不能为空" />
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
            <div class="container-fluid" id="id_detail">
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab_info" data-toggle="tab">主项目</a>
                    </li>
                </ul>
                <ul class="ul-form">
                    <div class="tab-content">
                        <div id="tab_info" class="tab-pane fade in active">
                            <jsp:include page="endProfPer_copyTab.jsp"></jsp:include>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
    </form>
</body>
</html>
