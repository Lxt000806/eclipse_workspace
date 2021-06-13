<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看预付金管理页面</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body >
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>供应商编号</label>
						<input type="text" id="splcode" name="splcode" value="${supplierPrepayDetail.splcode}" readonly="true" />
					</li>
					<li>
						<label>供应商名称</label>
						<input type="text" id="spldescr" name="spldescr" value="${supplierPrepayDetail.spldescr}" readonly="true"/>
					</li>			
					<li>
						<label>预付金额</label>
						<input name="prepaybalance" id="prepaybalance" type="text" value="${supplierPrepayDetail.prepaybalance}" readonly="true"/>								
					</li>
					<li>
						<label><span class="required">*</span>付款金额</label>
						<input name="amount" id="amount" style="IME-MODE: disabled; 
								onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" value="${supplierPrepayDetail.amount}" />								
					<li>
						<label class="control-textarea" >备注：</label>
						<textarea id="remarks" name="remarks">${supplierPrepayDetail.remarks}</textarea>
					</li>
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1" value="${supplierPrepayDetail.itemtype1}"/>
						<label>类型</label>
						<input type="text" id="type" name="aaa" value="${supplierPrepayDetail.type}"/>
					</li>
				</ul>
			</form>
		</div>
	</div>
</div>
</body>
</html>
