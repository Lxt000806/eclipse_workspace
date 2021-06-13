<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>查看发包公式</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
<div class="body-box-form">
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<ul class="ul-form">
						<div class="row">
							<li style="max-height: 120px;"><label
								class="control-textarea" style="top: -35px;width:145px;">发包公式说明</label>
								<textarea id="ctrlExprRemarks" name="ctrlExprRemarks"
									style="height: 50px;width: 500px;">${map.CtrlExprRemarks}</textarea>
							</li>
						</div>
						<div class="row">
							<li style="max-height: 120px;"><label
								class="control-textarea" style="top: -35px;width:145px;">发包计算结果</label>
								<textarea id="ctrlExprWithNum" name="ctrlExprWithNum"
									style="height: 50px;width: 500px;">${map.CtrlExprWithNum}</textarea>
							</li>
						</div>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>

