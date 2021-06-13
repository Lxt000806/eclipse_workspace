<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>搜寻--销售客户</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript">
 $(function(){
	    var mobileHidden=false
		//mobileHidden 存在时  能根据电话查询
		if('${saleCust.mobileHidden}'=='1'){
			  mobileHidden=true
		}
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			//url:"${ctx}/admin/saleCust/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel:[
				{name:'Code',	  index:'Code',		width:80, 	label:'客户编号',		sortable: true,align:"left"},
				{name:'Desc1',	  index:'Desc1',	width:100, 	label:'客户名称1',		sortable: true,align:"left"},
				{name:'Address',  index:'Address',	width:100, 	label:'楼盘地址',		sortable: true,align:"left"},
				{name:'Contact',  index:'Contact',	width:80, 	label:'联系人',		sortable: true,align:"left"},
				{name:'Phone1',   index:'Phone1', 	width:100, 	label:'电话1',		sortable: true,align:"left", hidden:mobileHidden},
				{name:'Phone2',   index:'Phone2', 	width:100, 	label:'电话2',		sortable: true,align:"left", hidden:mobileHidden},
				{name:'Mobile1',  index:'Mobile1',	width:100, 	label:'手机1',		sortable: true,align:"left", hidden:mobileHidden},
			 	{name:'Amount',  index:'Amount',	width:80,	label:'消费金额',		sortable: true,align:"left", hidden:mobileHidden},
		 	],
		ondblClickRow:function(rowid,iRow,iCol,e){
		var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
          	Global.Dialog.returnData = selectRow;
          	Global.Dialog.closeDialog();
          }
	});
        
});
function goto_query(){
 	var postData = $("#page_form").jsonForm();
 	var url="${ctx}/admin/saleCust/goJqGrid";
 	if("${saleCust.returnCount}" && $("#code").val()==""  
 			&& $("#address").val()=="" && $("#desc1").val()=="" ){
 		art.dialog({
	  			content:"请选择查询条件",
	  		});
	  		return;	
 	}
 	
 	$("#dataTable").jqGrid("setGridParam", {
     	url: url,
     	postData: postData
   	}).trigger("reloadGrid");
}    
</script>
	</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action=""  method="post" id="page_form" class="form-search">
			<input type="hidden" id="returnCount" name="returnCount" value="${saleCust.returnCount}" />
					<ul class="ul-form">
					<li>
						<label>客户名称</label> <input type="text" id="desc1" name="desc1" value="${saleCust.desc1 }" />
					</li>
					<li>
						<label>客户编号</label> <input type="text" id="code" name="code" value="${saleCust.code }" />
					</li>
					<li>
						<label>地址</label> <input type="text" id="address" name="address" value="${saleCust.address }" />
					</li>
					<li>
						<label>联系人</label> <input type="text" id="contact" name="contact" value="${saleCust.contact }" />
					</li>
					<button type="button" class="btn btn-system btn-sm" onclick="goto_query();"  >查询</button>
				</ul>	
			</form>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
