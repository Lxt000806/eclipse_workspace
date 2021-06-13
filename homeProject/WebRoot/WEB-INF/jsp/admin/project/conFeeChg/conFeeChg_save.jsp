<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>合同费用增减管理--新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}"type="text/javascript"></script>
<script type="text/javascript">
function save(m_umState,status,statusDescr){
	var custCode=$("#custCode").val();
	var chgType=$("#chgType").val();
	var chgAmount=$("#chgAmount").val();
	var itemType1=$("#itemType1").val();
	if(custCode==""){
		art.dialog({
		     content: "请填写客户编号！",
	    });
	    return;
	}
	if(chgType==""){
		art.dialog({
		     content: "请选择费用类型！",
	    });
	    return;
	}
	if(chgAmount==""){
		art.dialog({
		     content: "请录入增减金额！",
	    });
	    return;
	}
	if(chgAmount==0){
		art.dialog({
		     content: "增减金额不能为0！",
	    });
	    return;
	}
	if(m_umState=="A" || m_umState=="M"){
		if(chgType=="2"){
			if(itemType1==""){
				art.dialog({
				     content: "费用类型为管理费，材料类型1字段必选！",
			    });
			    return;
			}
		}
	}
	$("#m_umState").val(m_umState);
	$("#status").val(status);
	if(m_umState=="C" || m_umState=="B"){
		art.dialog({
				 content:"是否确认要"+statusDescr+"该条合同费用信息？",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					doSave();
				},
				cancel: function () {
						return true;
				}
		}); 
	}else{
		doSave();
	}

}
function doSave(){
	$("#isService").removeAttr("disabled");
	$("#isCupboard").removeAttr("disabled");
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/conFeeChg/doSave',
		type: 'post',
		data: datas,
		async : false,
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
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
		Global.LinkSelect.setSelect({firstSelect:'itemType1',firstValue:'${conFeeChg.itemType1}'});
		changeItemType1();
		if("${conFeeChg.pk}"==""){
			$("#pk").attr('placeholder','保存时生成');
		}
		$("#custCode").openComponent_customer({
			showValue:"${conFeeChg.custCode}",
			showLabel:"${conFeeChg.custDescr}",
			condition:{status:"4",mobileHidden:"1",laborFeeCustStatus:"1,2,3,5"},
			callBack:function(data){
				$("#custDescr").val(data.descr);
				$("#address").val(data.address);
			}
		});
		$("#openComponent_customer_custCode").attr("readonly",true);
		if("${conFeeChg.m_umState}"=="A" || "${conFeeChg.m_umState}"=="M"){
			$("#confirmBtn").attr("disabled",true);
			$("#confirmCancelBtn").attr("disabled",true);
			$("#returnConfirmBtn").attr("disabled",true);
		}else if("${conFeeChg.m_umState}"=="C"){
			$("#saveBtn").attr("disabled",true);
			$("#returnConfirmBtn").attr("disabled",true);
		}else if("${conFeeChg.m_umState}"=="RC"){
			$("#confirmBtn").attr("disabled",true);
			$("#confirmCancelBtn").attr("disabled",true);
			$("#saveBtn").attr("disabled",true);
		}else if("${conFeeChg.m_umState}"=="V"){
			$("#confirmBtn").attr("disabled",true);
			$("#saveBtn").attr("disabled",true);
			$("#confirmCancelBtn").attr("disabled",true);
			$("#returnConfirmBtn").attr("disabled",true);
			$("#custCode").setComponent_customer({readonly: true});
			$("input").attr("disabled","true");
			$("select").attr("disabled","true");
			$("textarea").attr("disabled","true");
		}
		
});
function changeItemType1(){
	var itemType1=$("#itemType1").val();
	if(itemType1=="ZC"){
		$("#isCupboard").val("0");
		$("#isService").removeAttr("disabled");
		$("#isCupboard").attr("disabled",true);
	}else if(itemType1=="JC"){
		$("#isService").val("0");
		$("#isCupboard").removeAttr("disabled");
		$("#isService").attr("disabled",true);
	}else{
		$("#isCupboard").val("0");
		$("#isService").val("0");
		$("#isCupboard").attr("disabled",true);
		$("#isService").attr("disabled",true);
	}
}
</script>
<style type="text/css">

</style>
</head>
    
<body>
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      <button type="button" id="saveBtn" class="btn btn-system" onclick="save('${conFeeChg.m_umState}','OPEN')">保存</button>
	       <button type="button" id="confirmBtn" class="btn btn-system" onclick="save('C','CONFIRMED','审核通过')">审核通过</button>
	        <button type="button" id="confirmCancelBtn" class="btn btn-system" onclick="save('C','CANCEL','审核取消')">审核取消</button>
	         <button type="button" id="returnConfirmBtn" class="btn btn-system" onclick="save('B','OPEN','反审核')">反审核</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	   	</div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
				<form role="form" class="form-search" id="dataForm" action=""
					method="post" target="targetFrame">
					<house:token></house:token>
					<ul class="ul-form">
					<input type="hidden" id="m_umState" name="m_umState"/>
					<input type="hidden" id="status" name="status"/>
						<div class="validate-group row">
								<li><label>费用流水</label> <input type="text"
									style="width:160px;" id="pk" name="pk" value="${conFeeChg.pk}"
									readonly="readonly" />
								</li>
							<li><label>客户编号</label> <input type="text" id="custCode"
								name="custCode" value="${conFeeChg.custCode}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>客户名称</label> <input type="text" id="custDescr"
								name="custDescr" style="width:160px;"
								value="${conFeeChg.custDescr }" readonly="readonly"/>
							</li>
							<li><label>楼盘地址</label> <input type="text" id="address"
								name="address" style="width:160px;"
								value="${conFeeChg.address }" readonly="readonly"/>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>费用类型</label> <house:xtdm id="chgType"
									dictCode="CHGTYPE" value="${conFeeChg.chgType}"></house:xtdm>
							</li>
							<li><label>增减金额</label> <input type="text" id="chgAmount"
								name="chgAmount" style="width:160px;"
								value="${conFeeChg.chgAmount}" />
							</li>
						</div>
						<div class="validate-group row">
							<li><label>材料类型1</label> <select id="itemType1"
								name="itemType1" style="width: 160px;"
								value="${conFeeChg.itemType1 }" onchange="changeItemType1()"></select> </label>
							</li>
							<li><label>是否服务性产品</label> <house:xtdm id="isService"
									dictCode="YESNO" value="${conFeeChg.isService}"></house:xtdm>
							</li>
						</div>
						<div class="validate-group row">
							<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
									dictCode="YESNO" value="${conFeeChg.isCupboard}"></house:xtdm>
							</li>
							<li><label>增减单号</label> <input type="text" id="chgNo"
								name="chgNo" style="width:160px;" value="${conFeeChg.chgNo }" readonly="readonly"/>
							</li>
						</div>
						<div class="validate-group row">
							<li class="form-validate"><label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks"
									style="overflow-y:scroll; overflow-x:hidden; height:75px; " />${conFeeChg.remarks
								}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
	</div>
</div>
</body>
</html>
