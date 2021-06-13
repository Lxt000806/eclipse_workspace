<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>基础项目管理修改业绩比例</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	$(function() {
		$("#dataForm").bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {/*input状态样式图片*/
				validating : 'glyphicon glyphicon-refresh'
			},
			fields: {  
				offerPri:{  
					validators: {  
						notEmpty: {  
							message: '人工单价不能为空'  
						}
					}  
				},
				material:{  
					validators: {  
						notEmpty: {  
							message: '材料单价不能为空'  
						}
					}  
				},
			},
			submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
		});
		
	});
	$(function(){
		var gridOption ={
			url:"${ctx}/admin/baseItem/goDetailJqGrid",
			height:$(document).height()-$("#content-list").offset().top-72,
			styleUI:"Bootstrap", 
			rowNum:100000,
			postData:{code:"${baseItem.code }"},
			colModel : [
				{name: "address", index: "address", width: 229, label: "楼盘地址", sortable: true, align: "left"},
				{name: "fixareadescr", index: "fixareadescr", width: 160, label: "装修区域", sortable: true, align: "left"},
				{name: "baseitemdescr", index: "baseitemdescr", width: 273, label: "基础项目", sortable: true, align: "left", hidden: true},
				{name: "qty", index: "qty", width: 79, label: "数量", sortable: true, align: "left"},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left", hidden: true},
				{name: "unitprice", index: "unitprice", width: 83, label: "人工单价", sortable: true, align: "left"},
				{name: "material", index: "material", width: 80, label: "材料单价", sortable: true, align: "left"},
				{name: "lineamount", index: "lineamount", width: 80, label: "总价", sortable: true, align: "left", sum: true},
				{name: "remark", index: "remark", width: 267, label: "备注", sortable: true, align: "left", hidden: true}
			],
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator('validate');
			if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
			var datas = $("#dataForm").serialize();
			$.ajax({
				url:"${ctx}/admin/baseItem/doUpdatePrice",
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
		});
	});
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row">
								<li>
									<label>基础项目编号</label> 
									<input type="text" id="code" name="code" style="width:160px;" value="${baseItem.code }" readonly="readonly" />
								</li>
								<li class="form-validate">
									<label>基装项目名称</label> 
									<input type="text" id="descr" name="descr" style="width:160px;" value="${baseItem.descr }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>人工单价</label> 
									<input type="text" id="offerPri" name="offerPri" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.offerPri }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>材料单价</label> 
									<input type="text" id="material" name="material" onkeyup="value=value.replace(/[^\?\d.]/g,'')" style="width:160px;" value="${baseItem.material }"/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active">
			        	<a href="#tab_detail" data-toggle="tab">主项目</a>
			        </li>
			    </ul> 
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
