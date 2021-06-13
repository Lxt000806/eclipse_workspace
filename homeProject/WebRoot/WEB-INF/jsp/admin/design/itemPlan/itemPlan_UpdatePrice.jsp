<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>调整单价</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
function save(){
	if( "${baseItemPlan.m_umState}"=="A"){
		if($("#unitPrice").val()!=""){
			if (parseFloat($("#unitPrice").val())<1){
				art.dialog({
					content: "人工单价上浮系数不能小于1!"
				});
				return;	
			}
		}
		if($("#material").val()!=""){
			if (parseFloat($("#material").val())<1){
					art.dialog({
					content: "材料单价上浮系数不能小于1!"
				});
				return;	
			}
		}
	}
	/* Global.Dialog.returnData ={'unitPrice':$("#unitPrice").val()?$("#unitPrice").val():1,'material':$("#material").val()?$("#material").val():1} ; */
	Global.Dialog.returnData ={'unitPrice':$("#unitPrice").val(),'material':$("#material").val()} ;
	closeWin();
}

</script>

</head>

<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="save()">保存</button>
					<button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<house:token></house:token>
					<ul class="ul-form">
						<div class="validate-group row">
							<div class="col-sm-6">
								<li class="form-validate"><label>人工单价上浮系数</label> <input id="unitPrice" name="unitPrice"
									type="number" step="0.01">
								</li>
							</div>
							<div class="col-sm-6">
								<li class="form-validate"><label>材料单价上浮系数</label> <input id="material" name="material" type="number"
									step="0.01">
								</li>
							</div>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
