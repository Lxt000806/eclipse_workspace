<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>完成ItemPreMeasure</title>
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
		url:"${ctx}/admin/supplierItemPreMeasure/goJqGridDetail?id=${preAppNo}",
		colModel : [
		  {name : 'ItemCode',index : 'ItemCode',width : 100,label:'材料编号',align : "left",count:true},
		  {name : 'itemcodedescr',index : 'itemcodedescr',width : 200,label:'材料名称',align : "left"},
		  {name : 'fixareadescr',index : 'fixareadescr',width : 200,label:'装修区域',align : "left"},
		  {name : 'Qty',index : 'Qty',width : 60,label:'数量',align : "right",sum:true},
		  {name : 'uom',index : 'uom',width : 60,label:'单位',align : "left"},
		  {name : 'Remarks',index : 'Remarks',width : 350,label:'备注',align : "left"}
          ]
	});
	$('div.ui-jqgrid-bdiv').css("height",350);
});

function finish(){
	$.ajax({
		url:'${ctx}/admin/supplierItemPreMeasure/doFinish',
		type: 'post',
		data: {'measureRemark':$("#measureRemark").val(),'pk':${pk},'delayReson':$("#delayReson").val()},
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '完成出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
	    		art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
	
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="panel panel-system">
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
	      <button type="button" id="finishBut" class="btn btn-system" onclick="finish()">完成</button>
	      <button type="button" class="btn btn-system" onclick="closeWin(false)">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info">  
        <div class="panel-body">
            <form role="form" class="form-search" id="dataForm" action="" method="post" target="targetFrame">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<ul class="ul-form">
					<li>
						<label class="control-textarea">测量数据</label>
						<textarea id="measureRemark" name="measureRemark" maxlength="1000">${itemPreMeasure.measureRemark}</textarea>
					</li>
					<li>
						<label>延误原因</label>
						<house:xtdm id="delayReson" dictCode="APPDELAYRESON" value="${itemPreMeasure.delayReson}" 
						unShowValue="${unShowValue }" headerLabel=""></house:xtdm>
					</li>
				</ul>
           	</form>
         </div>
    </div>
	<div id="content-list">
		<table id= "dataTable"></table> 
		<div id="dataTablePager"></div>
	</div> 
</div>
</body>
</html>
