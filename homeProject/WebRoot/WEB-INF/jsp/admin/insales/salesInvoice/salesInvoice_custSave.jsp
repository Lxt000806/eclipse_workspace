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
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="saveBtn" onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin: 0px;">  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="expired">
					<ul class="ul-form">
						<div class="row">
							<li>
								<label><span class="required">*</span>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;"
									placeholder="保存自动生成" readonly="readonly"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>客户名称1</label>
								<input type="text" id="desc1" name="desc1" style="width:160px;"/>
							</li>
							<li>
								<label>地址</label>
								<input type="text" id="address" name="address" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li>
								<label>联系人</label>
								<input type="text" id="contact" name="contact" style="width:160px;"/>
							</li>
						</div>
						<div class="row">
							<li class="form-validate">
								<label><span class="required">*</span>手机号码1</label>
								<input type="text" id="mobile1" name="mobile1" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{3})|(\d{4}))(?=\d)/g,'$1 ');" />
							</li>
							<li>
								<label>手机号码2</label>
								<input type="text" id="mobile2" name="mobile2" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(^(\d{3})|(\d{4}))(?=\d)/g,'$1 ');" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>传真1</label>
								<input type="text" id="fax1" name="fax1" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\+\d]/g,'');" />
							</li>
							<li>
								<label>传真2</label>
								<input type="text" id="fax2" name="fax2" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\+\d]/g,'');" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>电话1</label>
								<input type="text" id="phone1" name="phone1" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\-\+\d]/g,'');" />
							</li>
							<li>
								<label>电话2</label>
								<input type="text" id="phone2" name="phone2" style="width:160px;"
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\+\-\d]/g,'');" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>邮件地址1</label>
								<input type="text" id="Email1" name="Email1" style="width:160px;" />
							</li>
							<li>
								<label>邮件地址2</label>
								<input type="text" id="Email2" name="Email2" style="width:160px;" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>QQ</label>
								<input type="text" id="qq" name="qq" style="width:160px;" 
									onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'');"/>
							</li>
							<li>
								<label>MSN</label>
								<input type="text" id="msn" name="msn" style="width:160px;" />
							</li>
						</div>
						<div class="row">
							<li>
								<label>纪念日1</label>
								<input type="text" id="remDate1" name="remDate1"
									class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value=""/>
							</li>
							<li>
								<label>纪念日2</label>
								<input type="text" id="remDate2" name="remDate2"
									class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value=""/>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;">
								<label class="control-textarea" style="top: -80px;">备注</label>
								<textarea id="remarks" name="remarks" style="height: 100px;"></textarea>
							</li>
						</div>
						<div class="row">
							<li>
								<label>过期</label>
								<input type="checkbox" id="expired_show" name="expired_show" value="" 
									onclick="checkExpired(this)" ${itemAppTemp.expired=="T"?"checked":""}/>
							</li>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>	
<script>
$(function() {
	$("#page_form").bootstrapValidator({
		message : "请输入完整的信息",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: { 
			desc1:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
			mobile1:{
				validators: {  
					notEmpty: {  
						message: "请输入完整的信息"  
					}, 
				}  
			},
		}
	});
	
    $("#mobile1").on("input propertychange", function() {
    	var val = $(this).val();
        // 去除空格，不然会把空格算上
        val = val.replace(/\s/g,"");
        if (val.length > 11) {
        	var realVal = val.substring(0, 11);
            $(this).val(realVal);
        }
    });

	$("#mobile2").on("input propertychange", function() {
    	var val = $(this).val();
        // 去除空格，不然会把空格算上
        val = val.replace(/\s/g,"");
        if (val.length > 11) {
        	var realVal = val.substring(0, 11);
            $(this).val(realVal);
        }
    });

});

function doSave(){
	$("#page_form").bootstrapValidator("validate");
	if(!$("#page_form").data("bootstrapValidator").isValid()) return;
		
	var datas = $("#page_form").jsonForm();
	$.ajax({
		url: "${ctx}/admin/salesInvoice/doCustSave",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "请求数据出错"});
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

</script>
</html>
