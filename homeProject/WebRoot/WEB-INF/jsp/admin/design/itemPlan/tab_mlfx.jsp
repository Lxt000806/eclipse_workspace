<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
.form-search .ul-form li label {
    width: 130px;
    margin-right: 0px;
}
</style>
<script type="text/javascript">
	function closeWin(isCallBack,isPrevent){
	if(!isPrevent )return;
	Global.Dialog.closeDialog(isCallBack);
	}
	function reload(){
		location.reload(); 
	}
</script>
</head>
<body>
	<div class="body-box-list">
		<div>
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<div class="row">
						<div class="col-sm-6">
							<li><label>基础毛利</label> <input type="text" value="${profitAnalyseBean.baseProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>基础单位毛利</label> <input type="text" value="${profitAnalyseBean.baseProfit_area}"
								disabled="disabled" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>主材毛利</label> <input type="text" value="${profitAnalyseBean.mainProfit}"
								disabled="disabled" />
							</li>
						</div>
						<div class="col-sm-6">
							<li><label>主材单位毛利 </label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.mainProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>软装毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.softProfit}" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>软装单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.softProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>集成毛利</label> <input type="text" value="${profitAnalyseBean.intProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>集成单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.intProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>橱柜毛利</label> <input type="text" value="${profitAnalyseBean.cupProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>橱柜单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.cupProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>服务性产品毛利</label> <input type="text" value="${profitAnalyseBean.mainServProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>服务性产品单位毛利</label> <input type="text" disabled="disabled"
								style="position: relative;top:-10px" value="${profitAnalyseBean.mainServProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>设计费毛利</label> <input type="text" value="${profitAnalyseBean.designProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>设计费单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.designProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>管理费毛利</label> <input type="text" value="${profitAnalyseBean.manageProfit}"
								disabled="disabled" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>管理费单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.manageProfit_area}" />
							</li>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<li><label>合计毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.sumProfit}" />
							</li>
						</div>

						<div class="col-sm-6">
							<li><label>合计单位毛利</label> <input type="text" disabled="disabled"
								value="${profitAnalyseBean.sumProfit_area}" />
							</li>
						</div>
					</div>
				</ul>
			</form>
		</div>
	</div>
</body>
</html>
