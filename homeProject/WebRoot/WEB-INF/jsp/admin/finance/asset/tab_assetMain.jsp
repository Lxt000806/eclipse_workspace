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
	<title>固定资产查看主页签</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
</head>
	<body>
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li>
							<label>资产编号</label>
							<input type="text" id="code" name="code" style="width:160px;" value="${asset.code }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>资产名称</label>
							<input type="text" id="descr" name="descr" style="width:160px;" value="${asset.descr }" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label>类别编号</label>
							<input type="text" id="assetType" name="assetType" style="width:160px;"/>
						</li>
						<li class="form-validate">
							<label>规格型号</label>
							<input type="text" id="model" name="model" style="width:160px;" value="${asset.model}" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>单位</label>
							<house:dict id="uom" dictCode="" sql="select Code,code+' '+descr Descr from tUom where expired = 'F' "  
								sqlValueKey="Code" sqlLableKey="Descr" value="${asset.uom }" disabled="true"></house:dict>                  
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>增加方式</label>
							<house:xtdm id="addType" dictCode="ASSETADDTYPE" value="${asset.addType }" disabled="true"></house:xtdm>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>部门</label>
							<input type="text" id="department" name="department" style="width:160px;" value="${asset.department }"/>
						</li>
						<li>
							<label>使用人</label>
							<input type="text" id="useMan" name="useMan" value="${asset.useMan }"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>状态</label>
							<house:xtdm id="status" dictCode="ASSETSTATUS" value="${asset.status }" disabled="true"></house:xtdm>
						</li>
						<li class="form-validate">
							<label>存放地点</label>
							<input type="text" style="width:160px;" id="address" name="address" value="${asset.address }" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>数量</label>
							<input type="text" style="width:160px;" id="qty" name="qty" value="${asset.qty }" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>原值</label>
							<input type="text" style="width:160px;" id="originalValue" name="originalValue" value="${asset.originalValue }" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>开始使用时间</label>
							<input type="text" id="beginDate" name="beginDate" class="i-date" 
							style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value='${asset.beginDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
							
						</li>
						<li class="form-validate">
							<label><span class="required">*</span>累计折旧</label>
							<input type="text" id="totalDeprAmount" name="totalDeprAmount" style="width:160px;" value="${asset.totalDeprAmount }" onkeyup="getNetValue()" readonly="readonly"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>使用年限</label>
							<input type="text" id="useYear" name="useYear" style="width:160px;" value="${asset.useYear }" onkeyup="getRemainDeprMonth();getDeprAmountPerMonth()" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>折旧方法</label>
							<house:xtdm id="deprType" dictCode="ASSETDEPRTYPE" value="${asset.deprType }" disabled="true"></house:xtdm>
						</li>
					</div>
					
					<div class="validate-group row">
						<li class="form-validate">
							<label><span class="required">*</span>已计提月份</label>
							<input type="text" id="deprMonth" name="deprMonth" style="width:160px;" value="${asset.deprMonth }" onkeyup="getRemainDeprMonth()" readonly="readonly"/>
						</li>
						<li class="form-validate">
							<label>剩余月份</label>
							<input type="text" id="remainDeprMonth" name="remainDeprMonth" style="width:160px;" value="${asset.remainDeprMonth }" placeholder="自动计算" readonly="true"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label>净值</label>
							<input type="text" id="netValue" name="netValue" style="width:160px;" value="${asset.netValue }" placeholder="自动计算" readonly="true"/>
						</li>
						<li class="form-validate">
							<label>月折旧额</label>
							<input type="text" id="deprAmountPerMonth" name="deprAmountPerMonth" style="width:160px;" value="${asset.deprAmountPerMonth }" placeholder="自动计算" readonly="true"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" readonly="readonly">${asset.remarks }</textarea>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</body>	
</html>
