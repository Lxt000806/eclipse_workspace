<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBut">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="perfPk" id="perfPk" value="" />
					<input type="hidden" name="PK" id="PK" value="${mtCustInfo.PK }" />
					<input type="hidden" name="existsCust" id="existsCust" value="" />
					<input type="hidden" name="oldCustCode" id="oldCustCode" value="${customer.code}" />
					<input type="hidden" name="oldPerfPk" id="oldPerfPk" value="${customer.perfPK}" />
					<ul class="ul-form">
						<li class="form-validate">
							<label>客户名称</label> 
							<input type="text" id="custDescr" name="custDescr" style="width:160px;" readonly="readonly" value="${mtCustInfo.custDescr}" />
						</li>
						<li>
							<label>客户电话</label> 
							<input type="text" id="custPhone" name="custPhone" style="width:160px;" readonly="readonly" value="${mtCustInfo.custPhone}">
						</li>
						<li>
							<label>性别</label> 
							<input type="text" id="gender" name="gender" style="width:160px;" readonly="readonly" value="${mtCustInfo.gender}">
						</li>
						<li>
							<label>楼盘地址</label> 
							<input type="text" id="address" name="address" style="width:160px;" readonly="readonly" value="${mtCustInfo.address}">
						</li>
						<li>
							<label>面积</label> 
							<input type="text" id="area" name="area" style="width:160px;" readonly="readonly" value="${mtCustInfo.area}">
						</li>
						<%-- <li>
							<label>有家客户楼盘</label> 
							<input type="text" id="yjaddress" name="yjaddress" style="width:160px;" readonly="readonly" value="${mtCustInfo.yjaddress}">
						</li> --%>
						<li>
							<label>户型</label> 
							<input type="text" id="layout" name="layout" style="width:160px;" readonly="readonly" value="${mtCustInfo.layout}">
						</li>
						<li>
							<label>是否装修</label> 
							<input type="text" id="isFixtures" name="isFixtures" style="width:160px;" readonly="readonly" value="${mtCustInfo.isFixtures}">
						</li>
						<li>
							<label>状态</label> 
							<house:xtdm  id="status" dictCode="MTCUSTINFOSTAT"   style="width:160px;" value="${mtCustInfo.status}" disabled="true"></house:xtdm>
						</li>
						
						<li>
							<label>业务员</label> 
							<input type="text" id="businessman" name="businessman" style="width:160px;" readonly="readonly" value="${mtCustInfo.businessMan}">
						</li>
						<li>
							<label>麦田大区</label> 
							<input type="text" id="regiondescr" name="regiondescr" style="width:160px;" readonly="readonly" value="${mtCustInfo.regionDescr}" />
						</li>
						<li>
							<label>麦田区域</label> <input type="text" id="region2" name="region2" style="width:160px;" readonly="readonly" value="${mtCustInfo.region2}">
						</li>
						<li>
							<label>麦田门店</label> 
							<input type="text" id="shopname" name="shopname" style="width:160px;" readonly="readonly" value="${mtCustInfo.shopName}">
						</li>
						<li>
							<label>麦田经理</label> 
							<input type="text" id="manage" name="manage" style="width:160px;" readonly="readonly" value="${mtCustInfo.manage}">
						</li>
						<li>
							<label>麦田经理电话</label> 
							<input type="text" id="managePhone" name="managePhone" style="width:160px;" readonly="readonly" value="${mtCustInfo.managePhone}">
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" >  
				<li class="active"><a href="#tab_addCustCode" data-toggle="tab">意向客户信息</a></li>
			</ul> 
			<div class="tab-content">  
				<div id="#tab_addCustCode"  class="tab-pane fade in active"> 
		         	<form action="" method="post" id="page_form" class="form-search">
						<ul class="ul-form">
							<li class="form-validate">
								<label>意向客户</label> 
								<input type="text" id="custCode" name="custCode" style="width:160px;" readonly="readonly"/>
							</li>
							<li>
								<label>意向楼盘</label> 
								<input type="text" id="custAddress" name="custAddress" style="width:160px;" readonly="readonly" value="${customer.address}">
							</li>
							<li>
								<label>业绩时间</label> 
								<input type="text" id="date" name="date" class="i-date" style="width:160px;" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
									 value="<fmt:formatDate value='${mtCustInfo.perfCompDate}' pattern='yyyy-MM-dd'/>" disabled="true"/>
							</li>
						</ul>
					</form>
		        </div> 
		    </div>
		</div>
	</div>
</body>

<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	function getData(data){
		if(!data){
			$("#perfPk").val("");
			return;
		}
		var oldPerfPk = $("#oldPerfPk").val();
		var status = $("#status").val();
		if((data.perfpk !="" || (oldPerfPk != "" && oldPerfPk != 0)) && (status =="3" || status=="4")){
			art.dialog({
				content:"关联客户业绩变动（可能影响麦田系统业绩），是否继续？",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$("#perfPk").val(data.perfpk);
					$("#custAddress").val(data.address);
					$("#date").val(data.achievedate);
					if(data.code != ""){
						checkCust(data.code);
					}
				},
				cancel: function () {
					$("#date").val("");
					$("#custAddress").val("");
					$("#openComponent_customer_custCode").val("");
					return true;
				}
			});	
		} else {
			$("#perfPk").val(data.perfpk);
			$("#custAddress").val(data.address);
			$("#date").val(data.achievedate);
			if(data.code != ""){
				checkCust(data.code);
			}
		}
	}
	
	$("#custCode").openComponent_customer({callBack:getData,showLabel:"${customer.descr}",showValue:"${customer.code}"});	
	
	$("#saveBut").on("click", function(){
		var perfPk = $.trim($("#perfPk").val());
		var status = $.trim($("#status").val());
		var existsCust = $.trim($("#existsCust").val());
		if(existsCust=="false"){
			art.dialog({
				content:"该客户已有关联麦田客户，是否确定修改？",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					save();
				},
				cancel: function () {
					return true;
				}
			});	
		} else {
			save();
		}
	});
});

function save(){
	var datas = $("#page_form").serialize();
	var custCode = $("#custCode").val();
	var perfPk = $("#perfPk").val();
	datas = datas +"&perfPk="+perfPk+"&custCode="+custCode;
	console.log(datas);
	$.ajax({
		url:"${ctx}/admin/mtCustInfoAssign/doAddCustCode",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){ 
			if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin(true);
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

function checkCust(custCode){
	$.ajax({
		url:"${ctx}/admin/mtCustInfoAssign/checkCust",
		type:'post',
		data:{custCode: custCode},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '出错~'});
		},
		success:function(obj){
			$("#existsCust").val(obj);
		}
	});
}

</script>
</html>
