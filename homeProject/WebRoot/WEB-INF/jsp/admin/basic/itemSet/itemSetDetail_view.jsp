<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加材料套餐包明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
function getData(data){
	if (!data) return;
	var dataSet = {
		firstSelect:"itemType1",
		firstValue:'${itemsetdetail.itemtype1}',
	};
	Global.LinkSelect.setSelect(dataSet);
	$('#itemcode').val(data.code);
	$('#itemcodedescr').val(data.descr);
	}	
//校验函数					
$(function() {	
	$("#itemcode").openComponent_item({callBack: getData,condition:{itemType1:'${itemSetDetail.itemtype1}',readonly:"1",},valueOnly:'1'});	
	$("#itemcode").setComponent_item({showValue:'${itemSetDetail.itemcode}'});
});
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
				<ul class="ul-form">
					<li>
						<label><span class="required">*</span>材料编号</label>
						<input type="text" id="itemcode" name="itemcode" style="width:160px;" value="${itemSetDetail.itemcode}" readonly="true"/>
					</li>
					<li>
						<label>材料名称</label>
						<input type="text" id="itemcodedescr" name="itemcodedescr" style="width:160px;" value="${itemSetDetail.itemDescr}" readonly="true"/>
					</li>
					<li>
					<label><span class="required"  >*</span>套餐单价</label>
						<input id="unitprice" name="unitprice" id="unitprice"  style="IME-MODE: disabled; WIDTH: 160px" 
							onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')"  size="14"  type="text" value="${itemSetDetail.unitprice}" />								
					</li>				
					<li hidden="true">
						<label>材料类型</label>
						<input type="text" id="itemtype1" name="itemtype1"  value="${itemSetDetail.itemtype1}"/>
					</li>
			</form>
		</div>
	</div>
</div>
<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
</body>
</html>
