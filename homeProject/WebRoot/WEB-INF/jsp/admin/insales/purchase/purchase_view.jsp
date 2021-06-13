<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>查看采购单</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/purchase/goJqGridDetail?no=${purchase.no}",
		height:$(document).height()-$("#content-list").offset().top-105,
		colModel : [
		  {name : 'itemcode',index : 'itemcode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemdescr',index : 'itemdescr',width : 220,label:'材料名称',align : "left"},
		  {name : 'qtycal',index : 'qtycal',width : 100,label:'数量',align : "right",sum:true},
		  {name : 'uomdescr',index : 'uomdescr',width : 100,label:'单位',align : "left"},
		  {name : 'remarks',index : 'a.remarks',width : 350,label:'备注',align : "left"}
           ]
	});   
});

</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
            	<ul class="ul-form">
						<li>
							<label>采购单号</label>
							<input type="text" id="no" name="no" style="width:160px;"  value="${purchase.no}" readonly="readonly"/>
						</li>
						<li>
							<label>采购类型</label>
							<house:xtdm id="type" dictCode="PURCHTYPE" value="${purchase.type}" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>单据状态</label>
							<house:xtdm id="status" dictCode="PURCHSTATUS" value="${purchase.status}" disabled="true"></house:xtdm>
						</li>
						<li>						
							<label>其他费用</label>
							<input type="text" id="otherCost" name="otherCost" style="width:160px;"  value="${purchase.otherCost}" readonly="readonly"/>
						</li>
						<li>
							<label>采购日期</label>
							<input type="text" id="date" name="date" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${purchase.date}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>其他费用调整</label>
							<input type="text" id="otherCostAdj" name="otherCostAdj" style="width:160px;"  value="${purchase.otherCostAdj}" readonly="readonly" />
						</li>
						<li>						
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${purchase.custCode}" readonly="readonly" />
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"  value="${purchase.address}" readonly="readonly" />
						</li>
						<li>						
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks" readonly="readonly">${purchase.remarks}</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
    <div class="pageContent">
		<div id="content-list">
			<table id="dataTable"></table> 
			<div id="dataTablePager"></div>
		</div> 
	</div>
</div>
</body>
</html>
