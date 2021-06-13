<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>选择批量生成明细生成日期</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	$(function() {
		var year = parseFloat(new Date().getFullYear());
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
			if($("#workCostImportItemNo").val()==""){
				art.dialog({
			       	content: "请选择导入项目",
			    });
			    return;
			}
			var tip="该时间内没有明细生成或已经全部生成完毕";
			
			var waitDialog=art.dialog({
				content: "导入中，请稍候...", 
				lock: true,
				esc: false,
				unShowOkButton: true
			});
			
			$.ajax({
				url : "${ctx}/admin/workCostDetail/doBatchCrtDetail",
				type : "post",
				data : {
					workCostImportItemNo:$("#workCostImportItemNo").val(),
					workCostDetailJson:decodeURI("${workCostDetail.jsonString}"),
					year:$("#year").val(),month:$("#month").val()
				},
				dataType : "json",
				cache : false,
				error : function(obj) {
					waitDialog.close();
					showAjaxHtml({
						"hidden" : false,
						"msg" : "保存数据出错~"
					});
				},
				success : function(obj) {
					waitDialog.close();
					if(obj.length<1){
						art.dialog({
					       	content: "该时间内没有明细或已经全部生成完毕",
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
		<div class="panel panel-info" style="width:420px;height:250px">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<li style="position:absolute;left:20px;top:50px;">
							<label>导入项目</label>
							<house:dict id="workCostImportItemNo"  dictCode=""
								sql="select no,no+' '+descr descr from tWorkCostImportItem where Expired='F' and WorkCostType='${workCostDetail.type}' order by no  "
								sqlLableKey="descr" sqlValueKey="no"   />
						</li>
						<li style="position:absolute;left:20px;top:100px;">
							<label>生成时间</label>
							<select id="year"name="year" style="width:77px"></select> 
							<select id="month" name="month" style="width:77px"></select>
						</li>
						<li style="position:absolute;left:95px;top:150px;">
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
</body>
</html>
