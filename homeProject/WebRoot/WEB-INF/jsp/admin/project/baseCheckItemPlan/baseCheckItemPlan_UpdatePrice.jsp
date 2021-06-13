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
	function save() {
		if($.trim($("#workType12").val())==""){
				art.dialog({
					content: "工种类型12不能为空!"
				});
				return;	
		};
		Global.Dialog.returnData = {
			'workType12' : $.trim($("#workType12").val()),
			'offerpriPer' : $("#offerpriPer").val(),
			'materialPer' : $("#materialPer").val(),
			'prjofferpriPer' : $("#prjofferpriPer").val(),
			'prjmaterialPer' : $("#prjmaterialPer").val()
		};
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
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
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
								<li class="form-validate"><label>工种分类12</label> <house:dict
										id="workType12" dictCode=""
										sql="select Code,Code+' '+Descr Descr from tWorkType12 where Expired='F' order by Code"
										sqlValueKey="Code" sqlLableKey="Descr" />
								</li>
							</div>
							<div class="col-sm-6">
								<li class="form-validate"><label>人工单价调整系数</label> <input
									id="offerpriPer" name="offerpriPer" type="number" step="0.01">
								</li>
							</div>
							<div class="col-sm-6">
								<li class="form-validate"><label>材料单价调整系数</label> <input
									id="materialPer" name="materialPer" type="number" step="0.01">
								</li>
							</div>
							<div class="col-sm-6">
								<li class="form-validate"><label>经理人工单价调整系数</label> <input
									id="prjofferpriPer" name="prjofferpriPer" type="number"
									step="0.01">
								</li>
							</div>
							<div class="col-sm-6">
								<li class="form-validate"><label>经理材料单价调整系数</label> <input
									id="prjmaterialPer" name="prjmaterialPer" type="number"
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
