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
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system" >
		    <div class="panel-body">
		      	<div class="btn-group-xs" >
					<button type="submit" class="btn btn-system " id="orderBtn" onclick="doOrder()">
						<span>预约</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="row">
							<div class="col-sm-12">
								<input type="hidden" name="workerCode" value="${workerArrange.workerCode}">
								<input type="hidden" name="workType12" value="${workerArrange.workType12}">
								<li>
									<label>编号</label>
									<input type="text" id="pk" name="pk" style="width:160px;" value="${workerArrange.pk}" readonly="readonly"/>
								</li>
								<li>
									<label>姓名</label>
									<input type="text" id="workerName" name="workerName" style="width:160px;"
										value="${workerArrange.workerName}" readonly="readonly"/>
								</li>
                                <li>    
                                    <label>工种分类</label>
                                    <select id="work_type12" name="workType12" disabled="disabled"></select>
                                </li>
								<li>
									<label>日期</label>
									<input type="text" id="date" name="date" class="i-date"
										value="<fmt:formatDate value='${workerArrange.date}' pattern='yyyy-MM-dd'/>" readonly="readonly"/>
								</li>
								<li>	
									<label>班次</label>
									<house:dict id="dayType" dictCode="" sql="select CBM, (CBM + ' ' + NOTE) Descr from tXTDM where ID = 'DAYTYPE'"
									 	sqlValueKey="CBM" sqlLableKey="Descr" value="${workerArrange.dayType}" disabled="true"/>
								</li>
								<li>
									<label>预约编号</label>
									<input type="text" id="no" name="no" style="width:160px;" value="${workerArrange.no}" readonly="readonly"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>预约楼盘</label>
									<input type="text" id="custCode" name="custCode" style="width:160px;"/>
								</li>
							</div>
						</div>
					</ul>	
				</form>
			</div>
		</div>
	</div>
</body>	
<script>
$(function() {

	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","work_type12","work_type12_dept");
	$("#work_type12").val("${workerArrange.workType12}");

	$("#custCode").openComponent_customer({
	   callBack: refreshCustCodeStatus,
	   condition: {
           status: "4",
       }
	});
	
    $("#page_form").bootstrapValidator({
        excluded: [':disabled'],
        fields: {  
            openComponent_customer_custCode: {
                validators: {
                    notEmpty: {
                        message:'请选择预约楼盘'
                    }
                }
            }
        },
    });
	
});

function doOrder() {

    $("#page_form").bootstrapValidator("validate");
    if(!$("#page_form").data("bootstrapValidator").isValid()) return;
	
	var datas = $("#page_form").serialize();
	$.ajax({
		url: "${ctx}/admin/workerArrange/doOrder",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj) {
			showAjaxHtml({"hidden": false, "msg": "预约工人出错"});
	    },
	    success: function(obj) {
	    	if (obj.rs) {
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	} else {
	    		$("#_form_token_uniq_id").val(obj.token.token);
	    		art.dialog({
					content: obj.msg,
				});
	    	}
    	}
	});
}

function refreshCustCodeStatus() {
    $("#page_form").data("bootstrapValidator")
        .updateStatus("openComponent_customer_custCode", "NOT_VALIDATED", null)
        .validateField("custCode")
}

</script>
</html>
