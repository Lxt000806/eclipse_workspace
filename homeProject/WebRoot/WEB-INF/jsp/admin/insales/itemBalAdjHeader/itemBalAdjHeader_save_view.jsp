<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>仓库调整明细-查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
</head>
 
<body>
<div class="body-box-form" >
	<div class="content-form">
  			<!-- panelBar -->
  			<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
		
		<div class="infoBox" id="infoBoxDiv"></div>
		
		<div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
							<ul class="ul-form">
							<li hidden="true">
								<label>allCode</label>
								<input type="text" id="allItCode" name="allItCode" style="width:160px;" value="${itemBalAdjDetail.allItCode}"/>
							</li>
							<li hidden="true">
								<label>noRepeat</label>
								<input type="text" id="noRepeat" name="noRepeat" style="width:160px;" value="${itemBalAdjDetail.noRepeat}"/>
							</li>
							<li hidden="true">
								<label>adjType</label>
								<input type="text" id="adjType" name="adjType" style="width:160px;" value="${itemBalAdjDetail.adjType}"/>
							</li>
							<li >
								<label><span class="required">*</span>材料编号</label>
								<input type="text" id="itCode" name="itCode" style="width:160px;" value="${itemBalAdjDetail.itCode}"/>
								<span>&nbsp&nbsp&nbsp&nbsp</span>
							</li>
							<li >
								<label>现存数量</label>
								<input type="text" id="allQty" name="allQty" style="width:160px;" placeholder="0"  value="${itemBalAdjDetail.allQty}" readonly="true"/>
								<input type="text" id="uom" name="uom" value="${itemBalAdjDetail.uom}" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li >
								<label>当前移动成本</label>
								<input type="text" id="cost" name="cost" style="width:160px;" placeholder="0.0" value="${itemBalAdjDetail.cost}" readonly="true"/>
								<span>元</span>
							</li>
							<li >
								<label>未上架数量</label>
								<input type="text" id="noPosiQty" name="noPosiQty" style="width:160px;" placeholder="0" value="${itemBalAdjDetail.noPosiQty}" readonly="true"/>
								<input type="text" id="uom2" name="uom2" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li >
								<label><span class="required">*</span>调整成本</label>
								<input type="text" id="adjCost" name="adjCost" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" onblur="changeAmount()" value="${itemBalAdjDetail.adjCost}"/>
								<span>元</span>
							</li>
							<li >
								<label><span class="required">*</span>调整数量</label>
								<input type="text" id="adjQty" name="adjQty" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" onblur="changeAmount()" value="${itemBalAdjDetail.adjQty}"/>
								<input type="text" id="uom1" name="uom1" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li >
								<label>调整后成本</label>
								<input type="text" id="aftCost" name="aftCost" style="width:160px;" placeholder="0.0" value="${itemBalAdjDetail.aftCost}" readonly="true"/>
								<span>元</span>
							</li>
							<li >
								<label>调整后数量</label>
								<input type="text" id="qty" name="qty" style="width:160px;" placeholder="0" value="${itemBalAdjDetail.qty}" readonly="true"/>
								<input type="text" id="uom2" name="uom2" style="width:50px; outline:none; background:transparent; border:none" readonly="true"/>
							</li>
							<li >
								<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks"   rows="2">${itemBalAdjDetail.remarks }</textarea>
							</li>
							<li hidden="true">
								<label>材料名称</label>
								<input type="text" id="itDescr" name="itDescr" style="width:160px;" value="${itemBalAdjDetail.itDescr}"/>
							</li>
							<li hidden="true">
								<label>最后修改时间</label>
								<input type="text" id="lastUpdate" name="lastUpdate" style="width:160px;" value="<fmt:formatDate value='${itemBalAdjDetail.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>"/>
							</li>
							<li hidden="true">
								<label>最后修改人</label>
								<input type="text" id="lastUpdatedBy" name="lastUpdatedBy" style="width:160px;" value="${itemBalAdjDetail.lastUpdatedBy}"/>
							</li>
							<li hidden="true">
								<label>仓库编号</label>
								<input type="text" id="whCode" name="whCode"   value="${itemBalAdjDetail.whCode}"/>
							</li>
						</ul>
			</form>
			</div>
		</div> <!-- edit-form end -->
	</div>			
</div>
</body>
</html>
