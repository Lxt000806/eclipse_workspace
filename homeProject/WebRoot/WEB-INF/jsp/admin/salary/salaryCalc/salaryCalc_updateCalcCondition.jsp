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
	<title>薪酬计算修改计算月份、方案</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			salaryMon:{
				validators:{
					notEmpty:{
						message:"月份不能为空"
					}
				}
			},
			salaryScheme:{
				validators:{
					notEmpty:{
						message:"薪酬方案不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
    
});

function saveCondition(){

	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
    var salaryMon = $.trim($("#salaryMon").val());
    var salaryScheme = $.trim($("#salaryScheme").val());
    var datas = {};
    
    datas.salaryScheme = salaryScheme;
    datas.salaryMon = salaryMon;
    datas.firstCalcTime = firstCalcTime;
    
	Global.Dialog.returnData = datas;
	closeWin(true);
}

function getSalaryStatus(){
	var mon = $("#salaryMon").val();
	var salaryScheme = $("#salaryScheme").val();
	$.ajax({
		url:"${ctx}/admin/salaryCalc/getSalaryStatusCtrl?salaryMon="+mon+"&salaryScheme="+salaryScheme,
		type: "post",
		data: {},
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
	    },
	    success: function(obj){
	    	$("#status").val(obj.status);
	    }
	});
}
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="saveCondition()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="firstCalcTime" name="firstCalcTime" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>月份</label>
									<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon  order by salaryMon desc"  
										 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }" onchange="getSalaryStatus()"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>薪酬方案</label>
									<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
										 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }" onchange="getSalaryStatus()"></house:dict>
								</li>
								<li>
									<label >薪酬计算状态</label>
									<input type="text" id="status" name="status" value="${salaryData.status }" readonly/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
