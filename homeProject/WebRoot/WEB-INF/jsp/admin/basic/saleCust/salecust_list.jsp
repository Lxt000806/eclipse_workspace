<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" 	content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma"  			CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" 	CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" 			CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" 	CONTENT="IE=edge" />
	<title>客户销售管理列表</title>
	<%@ include file="/commons/jsp/common.jsp"%>


<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("saleCustAdd",{
		title:"添加销售客户信息",
		url:"${ctx}/admin/saleCust/goSave",
		height:500,
		width:700,
		returnFun: goto_query
	});
}

function copy(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("saleCustCopy",{
			title:"复制销售客户信息",
			url:"${ctx}/admin/saleCust/goSave?id="+ret.Code,
			height:500,
			width:700,
			returnFun:goto_query
		});
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
}

function view(){
	var ret=selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("salecustView",{
			title:"查看客户信息",
			url:"${ctx}/admin/saleCust/goView?id="+ret.Code,
			height:550,
			width:700,
			returnFun:goto_query
		});
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
}

function update(){
	var ret = selectDataTableRow();
	if(ret){
			Global.Dialog.showDialog("saleCustUpdate",{
			title:"修改销售客户信息",
			url:"${ctx}/admin/saleCust/goUpdate?id="+ret.Code,
			height:550,
			width:700,
			returnFun:goto_query
		});
	}else {
		art.dialog({
			content:"请选择一条记录"
		});
	}
}

function doExcel(){
	var url = "${ctx}/admin/saleCust/doExcel";
	doExcelAll(url);
}

/** 初始化表格 */
 $(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/saleCust/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel:[
			{name:'Code',	  index:'code',		width:80, 	label:'客户编号',		sortable: true,align:"left"},
			{name:'Desc1',	  index:'desc1',	width:100, 	label:'客户名称1',		sortable: true,align:"left"},
			{name:'Address',  index:'address',	width:150, 	label:'地址',			sortable: true,align:"left"},
			{name:'Contact',  index:'contact',	width:80, 	label:'联系人',		sortable: true,align:"left"},
			{name:'Phone1',   index:'phone1', 	width:90, 	label:'电话1',		sortable: true,align:"left"},
			{name:'Phone2',   index:'phone2', 	width:90, 	label:'电话2',		sortable: true,align:"left"},
			{name:'Fax1',	  index:'fax1',		width:80, 	label:'传真1',		sortable: true,align:"left"},
			{name:'Fax2',	  index:'fax2',		width:80, 	label:'传真2',		sortable: true,align:"left"},
			{name:'Mobile1',  index:'mobile1',	width:90, 	label:'手机1',		sortable: true,align:"left"},
			{name:'Mobile2',  index:'mobile2',	width:90, 	label:'手机2',		sortable: true,align:"left"},
			{name:'Email1' ,  index:'email1', 	width:100, 	label:'邮箱地址1',		sortable: true,align:"left"},
			{name:'Email2' ,  index:'email2', 	width:100, 	label:'邮箱地址2',		sortable: true,align:"left"},
		 	{name:'QQ',		  index:'qq',		width:80,	label:'QQ',			sortable: true,align:"left"},
		 	{name:'MSN',	  index:'msn',		width:80,	label:'MSN',		sortable: true,align:"left"},
		 	{name:'RemDate1', index:'remDate1',	width:80,	label:'纪念日1',		sortable: true,align:"left"},
			{name:'RemDate2', index:'remDate2',	width:80,	label:'纪念日2',		sortable: true,align:"left"},
			{name:'Remarks',  index:'remarks',   width:200,   label:'备注'    ,   sortable: true,align:"left"},
			{name:'Expired',  index:'expired',   width:70,   label:'过期标志'    ,   sortable: true,align:"left"},
			{name:'LastUpdate',index:'lastUpdate', width:130,label:'最后修改时间', 	sortable: true,align:"left",formatter:formatTime},
	 		{name:'LastUpdatedBy',index:'lastUpdatedBy',width:80, label:'最后修改人', sortable:true,align:"left"},
	 		{name:'ActionLog',  index:'actionLog',  width:50,   label:'操作'    ,   sortable: true,align:"left"}
		]
	});
	
	$("#fileUpload").on('change', function () {
	    if (typeof (FileReader) != "undefined") {
	        var image_holder = $("#image-holder");
	        image_holder.empty();
	        var reader = new FileReader();
	        reader.onload = function (e) {
	            $("<img />", {
	                "src": e.target.result,
	                "class": "thumb-image",
	                //"onload":"AutoResizeImage(110,110,'image-holder')",
	                "width":"120",
	                "height":"120"
	            }).appendTo(image_holder);
	        }
	        //onload="AutoResizeImage(500,500,'docPic');" width="521" height="510"
	        image_holder.show();
	        reader.readAsDataURL($(this)[0].files[0]);
	    } else {
	        alert("你的浏览器不支持FileReader.");
	    }
	});
	
});
	
</script>

</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
            <form action="" method="post" id="page_form" class="form-search">
                <input type="hidden" name="jsonString" value="" />
                <ul class="ul-form">
                    <li>
                        <label>客户编号</label>
                        <input type="text" id="code" name="code" />
                    </li>
                     <li>
                        <label>客户名称</label>
                        <input type="text" id="desc1" name="desc1" />
                    </li>
                    <li>
                        <label>地址</label>
                        <input type="text" id="address" name="address" />
                    </li>
                    <li>
                        <label>手机号码</label>
                        <input type="text" id="mobile1" name="mobile1" />
                    </li>
                    <li>
                        <label>联系人</label>
                        <input type="text" id="contact" name="contact" />
                    </li>
                    <li class="search-group"><input type="checkbox" id="expired"
                        name="expired" checked="checked" onclick="hideExpired(this)" />
                        <p>隐藏过期</p>
                        <button type="button" class="btn  btn-sm btn-system "
                            onclick="goto_query();">查询</button>
                        <button type="button" class="btn btn-sm btn-system "
                            onclick="clearForm();">清空</button></li>
                </ul>
            </form>
        </div>
		
		
		<div class="pageContent">
			<div class="btn-panel">
            <div class="btn-group-sm">
                <button type="button" class="btn btn-system " onclick="add()">新增</button>
                <button type="button" class="btn btn-system " onclick="copy()">复制</button>
                <button type="button" class="btn btn-system " onclick="update()">编辑</button>
                <house:authorize authCode="SALECUST_VIEW">
                    <button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
                </house:authorize>
                <button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
            </div>
        </div>
			<div id="wrapper" hidden="true">       
			  <input id="fileUpload" type="file" /><br />
			  <div id="image-holder" > </div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>
