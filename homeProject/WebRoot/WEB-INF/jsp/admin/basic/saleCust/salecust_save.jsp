<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加销售客户信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function keyUpOnlyNumber(_this){
    _this.value = _this.value.replace(/[^\d]/g,'');
}

function afterPasteOnlyNumber(_this){
    _this.value = _this.value.replace(/[^\d]/g,'');
}

function save(){
    //校验数据
	$("#dataForm").bootstrapValidator('validate');
    if (!$("#dataForm").data('bootstrapValidator').isValid()) return;

	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/saleCust/doSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
}

//校验函数
$(function() {
	$("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {
                desc1:{
                    validators:{
                        notEmpty:{
                            message:"客户名称1不能为空"
                        }
                    }
                }, 
                mobile1:{
                    validators : {
                        numeric:{
                            message:"手机号只能是数字"  
                        },
                        notEmpty : {
                            message : "请填写手机号码1"
                        },
                        stringLength : {
                            min : 11,
                            max : 11,
                            message : "手机号必须是11位"
                        }
                    }
                }, 
                mobile2:{
                    validators : {
                        stringLength : {
                            min : 11,
                            max : 11,
                            message : "手机号必须是11位"
                        }
                    }
                }
            },
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        });
});

</script>

</head>	

	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
                <div class="panel-body">
                    <div class="btn-group-xs">
                        <button type="button" class="btn btn-system" id="saveBtn" onclick="save()">
                            <span>保存</span>
                        </button>
                        <button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(true)">
                            <span>关闭</span>
                        </button>
                    </div>
                </div>
            </div>
			<div class="panel panel-info">
			    <div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
						   <div class="validate-group">
							   <li class="form-validate">
			                       <label><span class="required">*</span>客户名称1</label>
			                       <input type="text" id="desc1" name="desc1" value="${saleCust.desc1.trim()}" />
		                       </li>
		                       <li class="form-validate">
		                           <label>地址</label>
		                           <input type="text" id="address" name="address" value="${saleCust.address.trim()}" />
		                       </li>
	                       </div>
	                       <li class="form-validate">
	                           <label>联系人</label>
	                           <input type="text" id="contact" name="contact" value="${saleCust.contact.trim()}" />
	                       </li>
	                       <div class="validate-group">
		                       <li class="form-validate">
		                           <label><span class="required">*</span>手机号码1</label>
		                           <input type="text" id="mobile1" name="mobile1" maxLength="11" value="${saleCust.mobile1.trim()}"
		                              onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
		                       </li>
		                   </div>
	                       <li class="form-validate">
	                           <label>手机号码2</label>
	                           <input type="text" id="mobile2" name="mobile2" maxLength="11" value="${saleCust.mobile2.trim()}"
	                                   onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
	                       </li>
	                       <li class="form-validate">
	                           <label>传真1</label>
	                           <input type="text" id="fax1" name="fax1" value="${saleCust.fax1.trim()}"
	                                   onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
	                       </li>
	                       <li class="form-validate">
	                           <label>传真2</label>
	                           <input type="text" id="fax2" name="fax2" value="${saleCust.fax2.trim()}"
	                                   onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
	                       </li>
	                       <li class="form-validate">
	                           <label>电话1</label>
	                           <input type="text" id="phone1" name="phone1" value="${saleCust.phone1.trim()}"
	                                   onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
	                       </li>
	                       <li class="form-validate">
	                           <label>电话2</label>
	                           <input type="text" id="phone2" name="phone2" value="${saleCust.phone2.trim()}"
	                                   onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)" />
	                       </li>
	                       <li class="form-validate">
	                           <label>邮件地址1</label>
	                           <input type="text" id="email1" name="email1" value="${saleCust.email1.trim()}" />
	                       </li>
	                       <li class="form-validate">
	                           <label>邮件地址2</label>
	                           <input type="text" id="email2" name="email2" value="${saleCust.email2.trim()}" />
	                       </li>
	                       <li class="form-validate">
	                           <label>QQ</label>
	                           <input type="text" id="qq" name="qq" value="${saleCust.qq.trim()}"
	                               onkeyup="keyUpOnlyNumber(this)" onafterpaste="afterPasteOnlyNumber(this)"  />
	                       </li>
	                       <li class="form-validate">
	                           <label>MSN</label>
	                           <input type="text" id="msn" name="msn" value="${saleCust.msn.trim()}" />
	                       </li>
	                       <li class="form-validate">
	                           <label>纪念日1</label>
	                           <input type="text" id="remDate1" name="remDate1" class="i-date"
	                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                                value="${saleCust.remDate1.trim()}" />
	                       </li>
	                       <li class="form-validate">
	                           <label>纪念日2</label>
	                           <input type="text" id="remDate2" name="remDate2" class="i-date"
	                                onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
	                                value="${saleCust.remDate2.trim()}" />
	                       </li>
	                       <li class="form-validate">
	                         <label class="control-textarea">备注</label>
	                         <textarea id="remarks" name="remarks" rows="4" style="width:450px;">${saleCust.remarks.trim()}</textarea>
	                     </li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>
	</html>
