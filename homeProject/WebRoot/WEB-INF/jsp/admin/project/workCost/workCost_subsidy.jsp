<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>选择防水补贴生成日期</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		var date = new Date();
		var year = date.getFullYear();
		for(var i=2016;i<=year;i++){
			$("#year").append($("<option/>").text(i).attr("value",i));
		}
		for(var i=1;i<=12;i++){
			$("#month").append($("<option/>").text(i).attr("value",i));
		}
		//默认上个月时间
		var date=new Date();
		var year=date.getFullYear();
		var month=date.getMonth();
		if(month==0){//如果是一月，要变成去年12月
			month=12;
			year-=1;
		}
		$("#year").val(year);
		$("#month").val(month);
		$("#saveBtn").on("click", function() {
			var isFacingSubsidy=$("#isFacingSubsidy").val();
			var isSalary=$("#isSalary").val();
			var tip="";
			if(isSalary=="0" && isFacingSubsidy=="0"){
				art.dialog({
			       	content: "请勾选要生成的工资类型！",
			    });
		        return;
			}else if(isSalary=="1" && isFacingSubsidy=="0"){
				tip="该时间内没有防水工资生成或已经全部生成完毕！";
			}else if(isSalary=="0" && isFacingSubsidy=="1"){
				tip="该时间内没有防水饰面补贴生成或已经全部生成完毕！";
			}else{
				tip="该时间内没有防水工资及饰面补贴生成或已经全部生成完毕！";
			}
			var datas = $("#dataForm").jsonForm();
			$.ajax({
				url : "${ctx}/admin/workCostDetail/doSubsidy",
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
					if(obj.length<1){
						art.dialog({
					       	content: tip,
					    });
					    return;
					}
					Global.Dialog.returnData = obj;
					closeWin();
				}
			});
		});
	});
	//修改checkbox值
	function changeBox(id){
		var status=$("#"+id).prop("checked");
		if(status){
			$("#"+id).val("1");
		}else{
			$("#"+id).val("0");
		}
	}
</script>
</head>
<body >
	<div class="body-box-list" >
		<div class="panel panel-info" style="width:420px;height:220px">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
					<input type="checkbox" style="position:absolute;left:80px;top:25px;"
					id="isSalary" name="isSalary" onclick="changeBox('isSalary')"
					value="0" />
					<p style=" position:absolute;left:100px;top:28px;">防水工资</p>
					<input type="checkbox" style="position:absolute;left:220px;top:25px;"
					id="isFacingSubsidy" name="isFacingSubsidy" onclick="changeBox('isFacingSubsidy')"
					value="0" />
					<p style=" position:absolute;left:240px;top:28px;">防水饰面补贴</p>
					<li style="position:absolute;left:15px;top:80px;">&nbsp;&nbsp; <select id="year"
							name="year"></select> <select id="month" name="month"></select>
						</li>
						<input type="hidden" id="custCodes" name="custCodes"
							value="${workCostDetail.custCodes }">
						<li style="position:absolute;left:90px;top:150px;">
							<button type="button" class="btn btn-system" id="saveBtn">
								<span>确认</span>
							</button>
							<button type="button" class="btn btn-system" id="closeBut"
								onclick="closeWin(false)">
								<span>取消</span>
							</button>
						</li>
					</ul>
				</form>
			</div>
		</div>

	</div>

</body>
</html>
