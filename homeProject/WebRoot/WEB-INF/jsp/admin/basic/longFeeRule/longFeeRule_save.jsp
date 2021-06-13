<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	
	chooseSupplierOrWarehouse($("#sendType").get(0));
	
	var dataSet = {
		firstSelect:"itemType1",
		secondSelect:"itemType2",
		firstValue:'${longFeeRule.itemType1}',
		secondValue:'${longFeeRule.itemType2}'
	}; 
	Global.LinkSelect.setSelect(dataSet);
	
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
		},
		fields: {  
			itemType1:{  
				validators: {  
					notEmpty: {  
						message: '材料类型1不能为空'  
					}
				}  
			},
			itemType2:{  
				validators: {  
					notEmpty: {  
						message: '材料类型2不能为空'  
					}
				}  
			},
			calType:{  
				validators: {  
					notEmpty: {  
						message: '计算方式不能为空'  
					}
				}  
			},
			sendType:{  
				validators: {  
					notEmpty: {  
						message: '发货类型不能为空'  
					}
				}  
			},
		},
		submitButtons : 'input[type="submit"]'
	});
	
	if("${longFeeRule.m_umState}"=="V"){
		$("#saveBtn,input,textarea,select,#addItem,#updateItem,#delItem").attr("disabled",true);
	}
	
	if ("${longFeeRule.m_umState}" === "C") {
	    $("#no").val("");
	}
	
	$("#supplCode").openComponent_supplier({showValue:'${longFeeRule.supplCode}',showLabel:'${longFeeRule.supplDescr}',condition:{ReadAll:"1"},
	      callBack:function(){validateRefresh('openComponent_supplier_supplCode')}});
	
    $("#wHCode").openComponent_wareHouse({
        showValue: '${longFeeRule.wHCode}'
    });
    $("#openComponent_wareHouse_wHCode").blur();
	
	$("#dataForm").bootstrapValidator("addField", "openComponent_supplier_supplCode", {  
	       validators: {  
	           remote: {
	            message: '',
	            url: '${ctx}/admin/supplier/getSupplier',
	            data: getValidateVal,  
	            delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
	        }
	       }  
	 });
	
});
		
function save(){
	$("#dataForm").bootstrapValidator('validate'); //验证表单
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return; //验证表单
	
	var detail = $("#dataTable").jqGrid("getRowData");
	var itemDetail = $("#itemDataTable").jqGrid("getRowData");
	var params = {
	    "longFeeRuleDetailJson": JSON.stringify(detail),
	    "itemDetailJson": JSON.stringify(itemDetail)
	};
	
	Global.Form.submit("dataForm","${ctx}/admin/longFeeRule/doSave",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
			    	closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			 art.dialog({
				content: ret.msg,
			});
		}
					
	});
};
function validateRefresh(){
	$('#dataForm').data('bootstrapValidator')
                  .updateStatus('itemType1', 'NOT_VALIDATED',null)  
                  .validateField('itemType1')
                  .updateStatus('itemType2', 'NOT_VALIDATED',null)  
                  .validateField('itemType2');          
}

function chooseSupplierOrWarehouse(element) {
    var supplCodeLi = $("#supplCode").parent();
    var wHCodeLi = $("#wHCode").parent();

    if (element.value === "1") {
        supplCodeLi.show();
        wHCodeLi.hide();
    } else if (element.value === "2") {
        supplCodeLi.hide();
        wHCodeLi.show();
    } else {
        supplCodeLi.hide();
        wHCodeLi.hide();
    }
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "onclick="save()">保存</button>
					<button type="button" class="btn btn-system " onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="m_umState" name="m_umState" value="${longFeeRule.m_umState}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group">
								<li><label><span class="required">*</span>编号</label> <input
									type="text" id="no" name="no" readonly="true" value="${longFeeRule.no}"
									placeholder="保存时生成" />
								</li>
							    <li class="form-validate">
		                            <label><span class="required">*</span>计算方式</label>
		                            <house:xtdm id="calType" dictCode="LongFeeCalType" value="${longFeeRule.calType}"></house:xtdm>
		                        </li>
							</div>
							<div class="validate-group">
								<li class="form-validate"><label><span
										class="required">*</span>材料类型1</label> <select id="itemType1"
									name="itemType1"></select>
								</li>
								<li class="form-validate"><label><span
										class="required">*</span>材料类型2</label> <select id="itemType2"
									name="itemType2"></select>
								</li>
							</div>
							<div class="validate-group">
							     <li class="form-validate">
		                            <label><span class="required">*</span>发货类型</label>
		                            <house:xtdm id="sendType" dictCode="ITEMAPPSENDTYPE" value="${longFeeRule.sendType}"
		                                        onchange="chooseSupplierOrWarehouse(this)"></house:xtdm>
		                        </li>
		                        <li class="form-validate">
		                            <label>供应商</label>
		                            <input type="text" id="supplCode" name="supplCode" value="${longFeeRule.supplCode}"/>
		                        </li>
		                        <li class="form-validate">
		                            <label>仓库</label>
		                            <input type="text" id="wHCode" name="wHCode" value="${longFeeRule.wHCode}"/>
		                        </li>
							</div>
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks">${longFeeRule.remarks}</textarea>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
 		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_detail" data-toggle="tab">远程费规则明细</a></li>
				<li><a href="#tab_itemDetail" data-toggle="tab">匹配材料</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<jsp:include page="longFeeRule_tab_detail.jsp"></jsp:include>
				</div>
				<div id="tab_itemDetail" class="tab-pane fade in">
					<jsp:include page="longFeeRule_tab_itemDetail.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
