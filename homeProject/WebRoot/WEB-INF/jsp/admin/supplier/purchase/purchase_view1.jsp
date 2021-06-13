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
	<title>采购明细——查看</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

</head>	
	<body>
		<div class="body-box-list">
			<div class="content-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
			</div>
         <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="expired"  name="expired" value="${purchase.expired }"/>
					<ul class="ul-form">
						<li>
							<label>材料编号</label>
							<input type="text" id="itCode" name="itCode" style="width:160px;" value="${purchaseDetail.itcode}"/>
							</li>
						<li>
							<label>材料名称</label>
							<input type="text" id="itdescr" name="itdescr" style="width:160px;" value="${purchaseDetail.itdescr}"/>
							</li>
						<li>
							<label>退回数量</label>
							<input type="text" id="qtycal" name="qtycal" style="width:160px;" value="${purchaseDetail.qtyCal}"/>
							</li>
						<li>
							<label>库存数量</label>
							<input type="text" id="allqty" name="allqty" style="width:160px;" value="${purchaseDetail.allqty }"/>
							</li>
						<li>
							<label>可用数量</label>
							<input type="text" id="useqty" name="useqty" style="width:160px;" value="${purchaseDetail.allqty+purchaseDetail.purqty-purchaseDetail.appqty-purchaseDetail.saleqty-purchaseDetail.applyqty}"readonly= 'readonly'/>
							</li>
						<li>
							<label>在途采购数量</label>
							<input type="text" id="purqty" name="purqty" style="width:160px;" value="${purchaseDetail.purqty }" readonly= 'readonly'/>
						</li>
						<li>
							<label>领料审核数</label>
							<input type="text" id="appQty" name="appQty" style="width:160px;" value="${purchaseDetail.appqty}" readonly= 'readonly'/>
							</li>
						<li>
							<label>领料申请数</label>
							<input type="text" id="applyQty" name="applyQty" style="width:160px;" value="${purchaseDetail.applyqty }" readonly= 'readonly'/>
							</li>
						<li>
							<label>销售申请数</label>
							<input type="text" id="saleQty" name="saleQty" style="width:160px;" value="${purchaseDetail.saleqty}" value="${purchaseDetail.allqty }"/>
						</li>
						<li>
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" rows="2" >${purchaseDetail.remarks }</textarea>
						</li>
					</ul>
				</form>
				</div>
				</div>
			</div>
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">仓库结存</a></li>
	   	 </ul>
			<div id="tabs-1">
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
			</div>
			
<script type="text/javascript">
	$("#tabs").tabs();
$(function(){
		var lastCellRowId;
		var gridOption = {	
			url:"${ctx}/admin/itemWHBal/goJqGrid",
			postData:{itCode:'${purchaseDetail.itcode}'},
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			colModel : [
			{name:'whcode', index:'whcode', width:80, label:'仓库编号', sortable:true ,align:"left"},
			{name:'desc1', index:'descr', width:200, label:'仓库名称', sortable:true ,align:"left" ,count:true},
			{name:'qtycal', index:'allqty', width:80, label:'当前库存', sortable:true ,align:"left" ,sum:true},
		]
	};	
		  /*   var detailJson = Global.JqGrid.selectToJson("dataTable","no");
			if($.trim($("#itcode").val())!=''){
				$.extend(gridOption,{
				url:"${ctx}/admin/itemWHBal/goJqGrid",
				postData:{itcode:$.trim($("#itcode").val())},
				});
			}  */
			Global.JqGrid.initJqGrid("dataTable",gridOption);
	
});
	
	
	
</script>
	</body>
</html>
