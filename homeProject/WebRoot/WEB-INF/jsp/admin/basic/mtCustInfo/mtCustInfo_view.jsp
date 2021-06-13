<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" style="margin-bottom: 10px;">
			<div class="panel-body">
				<form action="" method="post" id="page_form" class="form-search">
					<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li class="form-validate"><label>客户名称</label> <input
							type="text" id="custdescr" name="custdescr" style="width:160px;"
							readonly="readonly" value="${map.custdescr}" />
						</li>
						<li><label>客户电话</label> <input type="text" id="custphone"
							name="custphone" style="width:160px;" readonly="readonly"
							value="${map.custphone}">
						</li>
						<li><label>性别</label> <input type="text" id="gender"
							name="gender" style="width:160px;" readonly="readonly"
							value="${map.gender}">
						</li>
						<li><label>楼盘地址</label> <input type="text" id="address"
							name="address" style="width:160px;" readonly="readonly"
							value="${map.address}">
						</li>
						<li><label>面积</label> <input type="text"
							id="area" name="area" style="width:160px;"
							readonly="readonly" value="${map.area}">
						</li>
						<li><label>有家客户楼盘</label> <input type="text" id="yjaddress"
							name="yjaddress" style="width:160px;" readonly="readonly"
							value="${map.yjaddress}">
						</li>
						<li><label>户型</label> <input type="text" id="layout"
							name="layout" style="width:160px;" readonly="readonly"
							value="${map.layout}">
						</li>
						<li><label>是否装修</label> <input type="text" id="isfixtures"
							name="isfixtures" style="width:160px;" readonly="readonly"
							value="${map.isfixtures}">
						</li>
						<li><label>状态</label> <input type="text" id="statusdescr"
							name="statusdescr" style="width:160px;" readonly="readonly"
							value="${map.statusdescr}">
						</li>
						
						<li><label>业务员</label> <input type="text" id="businessman"
							name="businessman" style="width:160px;" readonly="readonly"
							value="${map.businessman}">
						</li>

						<li><label>麦田大区</label> <input type="text" id="regiondescr"
							name="regiondescr" style="width:160px;" readonly="readonly"
							value="${map.regiondescr}" />
						</li>
						<li><label>麦田区域</label> <input type="text" id="region2"
							name="region2" style="width:160px;" readonly="readonly"
							value="${map.region2}">
						</li>
						<li><label>麦田门店</label> <input type="text" id="shopname"
							name="shopname" style="width:160px;" readonly="readonly"
							value="${map.shopname}">
						</li>
						<li><label>麦田经理</label> <input type="text"
							id="manage" name="manage" style="width:160px;"
							readonly="readonly" value="${map.manage}">
						</li>
						<li><label>麦田经理电话</label> <input type="text" id="managephone"
							name="managephone" style="width:160px;" readonly="readonly"
							value="${map.managephone}">
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"> 
$(function() {

});

</script>
</html>
