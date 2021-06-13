<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>修改ItemAppDetail</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
var parentWin=window.opener;
$(function(){
	$document = $(document);
	$('div.panelBar', $document).panelBar();
	
});

function save(){
	if(!$("#dataForm").valid()) {return false;}//表单校验
	if($("#infoBoxDiv").css("display")!='none'){return false;}
	
	//验证
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/itemAppDetail/doUpdate',
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
						if(parentWin != null)
				        	parentWin.goto_query();
	    				closeWin();
				    }
				});
	    	}else{
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
	$("#dataForm").validate({
		rules: {
				"pk": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"no": {
				validIllegalChar: true,
				maxlength: 20
				},
				"reqPk": {
				digits: true,
				maxlength: 10
				},
				"fixAreaPk": {
				digits: true,
				required: true,
				maxlength: 10
				},
				"intProdPk": {
				digits: true,
				maxlength: 10
				},
				"itemCode": {
				validIllegalChar: true,
				maxlength: 20
				},
				"qty": {
				number: true,
				maxlength: 19
				},
				"remarks": {
				validIllegalChar: true,
				maxlength: 200
				},
				"lastUpdate": {
				required: true,
				maxlength: 23
				},
				"lastUpdatedBy": {
				validIllegalChar: true,
				maxlength: 10
				},
				"expired": {
				validIllegalChar: true,
				maxlength: 1
				},
				"actionLog": {
				validIllegalChar: true,
				maxlength: 10
				},
				"cost": {
				number: true,
				maxlength: 19
				},
				"aftQty": {
				number: true,
				maxlength: 19
				},
				"aftCost": {
				number: true,
				maxlength: 19
				},
				"sendQty": {
				number: true,
				maxlength: 19
				},
				"projectCost": {
				number: true,
				maxlength: 19
				}
		}
	});
});

</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
		<div class="panelBar">
			<ul>
				<li >
					<a href="javascript:void(0)" class="a1" id="saveBut" onclick="save()">
					   <span>保存</span>
					</a>	
				</li>
				<li id="closeBut" onclick="closeWin(false)">
					<a href="javascript:void(0)" class="a2">
						<span>关闭</span>
					</a>
				</li>
				<li class="line"></li>
			</ul>
			<div class="clear_float"> </div>
		</div>
		<div class="infoBox" id="infoBoxDiv"></div>
		<div class="edit-form">
			<form action="" method="post" id="dataForm">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr>	
							<td class="td-label"><span class="required">*</span>PK</td>
							<td class="td-value">
							<input type="text" id="pk" name="pk" style="width:160px;"  value="${itemAppDetail.pk}" />
							</td>
							<td class="td-label">批次号</td>
							<td class="td-value">
							<input type="text" id="no" name="no" style="width:160px;"  value="${itemAppDetail.no}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">领料需求标识</td>
							<td class="td-value">
							<input type="text" id="reqPk" name="reqPk" style="width:160px;"  value="${itemAppDetail.reqPk}" />
							</td>
							<td class="td-label"><span class="required">*</span>区域编码</td>
							<td class="td-value">
							<input type="text" id="fixAreaPk" name="fixAreaPk" style="width:160px;"  value="${itemAppDetail.fixAreaPk}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">集成成品PK</td>
							<td class="td-value">
							<input type="text" id="intProdPk" name="intProdPk" style="width:160px;"  value="${itemAppDetail.intProdPk}" />
							</td>
							<td class="td-label">材料编号</td>
							<td class="td-value">
							<input type="text" id="itemCode" name="itemCode" style="width:160px;"  value="${itemAppDetail.itemCode}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">数量</td>
							<td class="td-value">
							<input type="text" id="qty" name="qty" style="width:160px;"  value="${itemAppDetail.qty}" />
							</td>
							<td class="td-label">Remarks</td>
							<td class="td-value">
							<input type="text" id="remarks" name="remarks" style="width:160px;"  value="${itemAppDetail.remarks}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label"><span class="required">*</span>LastUpdate</td>
							<td class="td-value">
							<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;"  value="${itemAppDetail.lastUpdate}" />
							</td>
							<td class="td-label">LastUpdatedBy</td>
							<td class="td-value">
							<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;"  value="${itemAppDetail.lastUpdatedBy}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">Expired</td>
							<td class="td-value">
							<input type="text" id="expired" name="expired" style="width:160px;"  value="${itemAppDetail.expired}" />
							</td>
							<td class="td-label">ActionLog</td>
							<td class="td-value">
							<input type="text" id="actionLog" name="actionLog" style="width:160px;"  value="${itemAppDetail.actionLog}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">移动平均成本</td>
							<td class="td-value">
							<input type="text" id="cost" name="cost" style="width:160px;"  value="${itemAppDetail.cost}" />
							</td>
							<td class="td-label">变动后数量</td>
							<td class="td-value">
							<input type="text" id="aftQty" name="aftQty" style="width:160px;"  value="${itemAppDetail.aftQty}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">变动后平均成本</td>
							<td class="td-value">
							<input type="text" id="aftCost" name="aftCost" style="width:160px;"  value="${itemAppDetail.aftCost}" />
							</td>
							<td class="td-label">发货数量</td>
							<td class="td-value">
							<input type="text" id="sendQty" name="sendQty" style="width:160px;"  value="${itemAppDetail.sendQty}" />
							</td>
						</tr>
						<tr>	
							<td class="td-label">项目经理结算价</td>
							<td class="td-value" colspan="3">
							<input type="text" id="projectCost" name="projectCost" style="width:160px;"  value="${itemAppDetail.projectCost}" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</div>
</body>
</html>
