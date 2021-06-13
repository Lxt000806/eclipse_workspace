<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>员工工作经验--添加</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function saveBtn(){
	$("#dataForm").bootstrapValidator("validate");
	if(!$("#dataForm").data("bootstrapValidator").isValid()) return;	
	
	var selectRows = [];
	var datas=$("#dataForm").jsonForm();
	if(""!=$.trim($("#company").val())){
		datas.promType1Descr=$("#company").val();
	}
	if(""!=$.trim($("#position").val())){
		datas.promType2Descr=$("#position").val();
	} 
	if(""!=$.trim($("#salary").val())){
		datas.promRsnDescr=$("#salary").val();
	} 
	if(""!=$.trim($("#begindate").val())){
		datas.begindate=$("#begindate").val();
	} 
	if(""!=$.trim($("#enddate").val())){
		datas.enddate=$("#enddate").val();
	}
	if(""!=$.trim($("#leaversn").val())){
		datas.leaversn=$("#leaversn").val();
	}
	datas.expired="F";
	datas.actionlog="ADD";
	datas.lastupdate= new Date();
	datas.lastupdatedby=1;
 	selectRows.push(datas);
	Global.Dialog.returnData = selectRows;
	closeWin();
};
$(function() {
	$("#dataForm").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
			begindate : {
				validators : {
					notEmpty : {
						message : '开始时间不能为空'
					}
				}
			},
			enddate : {
				validators : {
					notEmpty : {
						message : '结束时间不能为空',
					}
				}
			},
			salary : {
				validators : {
					numeric: {
	                    message:'只能输入数字'
	                }
				}
			},
		},
		submitButtons : 'input[type="submit"]'
	}); 
});
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
		    	<div class="panel-body" >
		    		<div class="btn-group-xs" >
						<button type="button" class="btn btn-system" onclick="saveBtn()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="text" name="pk" id="pk" hidden="true"/>
						<input type="text" name="number" id="number" hidden="true">
						<ul class="ul-form">
							<li>
								<label>公司</label>
								<input type="text" name="company" id="company"/>
								</li>
							<li>
								<label>职位</label>
								<input type="text" id="position" name="position"/>
							</li>
							<li class="form-validate">
								<label>工薪</label>
								<input type="text" id="salary" name="salary"/>
							</li>
							<li class="form-validate">
								<label>开始时间</label>
								<input type="text" id="begindate" name="begindate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li class="form-validate">
								<label>结束时间</label>
								<input type="text" id="enddate" name="enddate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
							</li>
							<li>
								<label class="control-textarea">离职原因</label>
								<textarea id="leaversn" name="leaversn" rows="3"></textarea>
							</li> 	
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
