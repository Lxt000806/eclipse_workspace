<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>系统代码列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("xtdmAdd",{
		  title:"添加系统代码",
		  url:"${ctx}/admin/xtdm/goSave",
		  height: 600,
		  width:1000,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("xtdmUpdate",{
		  title:"修改系统代码",
		  url:"${ctx}/admin/xtdm/goUpdate?id="+ret.ID+"&cbm="+ret.CBM+"&ibm="+ret.IBM,
		  height:600,
		  width:1000,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("xtdmView",{
		  title:"查看系统代码",
		  url:"${ctx}/admin/xtdm/goDetail?id="+ret.ID+"&cbm="+ret.CBM+"&ibm="+ret.IBM,
		  height:600,
		  width:1000
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var url = "${ctx}/admin/xtdm/doDelete";
	beforeDel(url);
}

/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/xtdm/goJqGrid",
			multiselect: true,
			height:$(document).height()-$("#content-list").offset().top-75,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'ID',index : 'ID',width : 150,label:'对象ID',sortable : true,align : "left"},
		      {name : 'CBM',index : 'CBM',width : 100,label:'字母编码',sortable : true,align : "left"},
		      {name : 'IBM',index : 'IBM',width : 100,label:'数字编码',sortable : true,align : "left"},
		      {name : 'IDNOTE',index : 'IDNOTE',width : 150,label:'中文对象说明',sortable : true,align : "left"},
		      {name : 'NOTE',index : 'NOTE',width : 150,label:'中文含义',sortable : true,align : "left"},
		      {name : 'NOTE_E',index : 'NOTE_E',width : 150,label:'英文含义',sortable : true,align : "left"},
		      {name : 'IDNOTE_E',index : 'IDNOTE_E',width : 150,label:'英文对象说明',sortable : true,align : "left"},
		      {name : 'Expired',index : 'Expired',width : 50,label:'过期',sortable : true,align : "left"}
            ]
		});
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
			      <input type="hidden" id="expired" name="expired"/>
				<ul class="ul-form">
					<li>
						<label>对象ID</label>
						<input type="text" id="id" name="id" style="width:160px;" value="${xtdm.id}"/>
					</li>
					<li>
						<label>中文对象说明</label>
						<input type="text" id="idnote" name="idnote"/>
					</li>
					<li>
						<label>中文含义</label>
						<input type="text" id="note" name="note"/>
					</li>
					<li>
					    <input type="checkbox" id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>
                        <label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system" onclick="goto_query()">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<!--panelBar-->
			<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
            	<house:authorize authCode="XTDM_SAVE">
                    	<button type="button" class="btn btn-system " onclick="add()">
					       <span>添加</span>
                </house:authorize>
                <house:authorize authCode="XTDM_UPDATE">
                    	<button type="button" class="btn btn-system " onclick="update()">
					       <span>编辑 </span>
				</house:authorize>
				<house:authorize authCode="XTDM_VIEW">
                    	<button type="button" class="btn btn-system " onclick="view()">
					       <span>查看</span>
				</house:authorize>
                <house:authorize authCode="XTDM_DELETE">
						<button type="button" class="btn btn-system " onclick="del()">
							<span>删除</span>
                 </house:authorize>
                 </div>
			</div><!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>


