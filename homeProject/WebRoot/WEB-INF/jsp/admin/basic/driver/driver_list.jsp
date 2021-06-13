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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>司机信息列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("driverAdd",{
		title:"添加司机信息",
		url:"${ctx}/admin/driver/goSave",
		width: 710,
		height: 481,
		returnFun: goto_query 
	});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("driverUpdate",{
		  title:"修改司机信息",
		  url:"${ctx}/admin/driver/goUpdate?id="+ret.Code,
		  width:710,
		  height:520, 
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function copy(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("driverCopy",{
		  title:"复制司机信息",
		  url:"${ctx}/admin/driver/goSave?id="+ret.Code,
		  height:600,
		  width:800,
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
      Global.Dialog.showDialog("driverView",{
		  title:"查看司机信息",
		  url:"${ctx}/admin/driver/goDetail?id="+ret.Code,
		  width:710,
		  height:470,
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var ret = selectDataTableRow();
	if (ret) {
    	art.dialog({
    		content:"确认删除该记录",
    		ok:function(){
    			$.ajax({
    				url:"${ctx}/admin/driver/doDelete?deleteIds=" + ret.Code,
    				type:"post",
    				dataType:"json",
    				cache:false,
    				error:function(obj){
    					art.dialog({
    						content:"访问出错,请重试",							
    						time: 1000,
							beforeunload: function () {
								goto_query();
							}
    					});
    				},
    				success:function(obj){
    					if(obj.rs){
							goto_query();
						}else{
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content: obj.msg,
								width: 200
							});
						}
    				}
    			});
    		},
    		cancel:function(){
    			goto_query();
    		}
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/driver/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/driver/goJqGrid",
			multiselect: false,
			styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'Code',index : 'Code',width : 60,label:'编号',sortable : true,align : "left"},
		      {name : 'drivertypedescr',index : 'driverTypedescr',width : 80,label:'司机类型',sortable : true,align : "left"},
		      {name : 'NameChi',index : 'NameChi',width : 60,label:'姓名',sortable : true,align : "left"},
		      {name : 'genderdescr',index : 'genderdescr',width : 60,label:'性别',sortable : true,align : "left"},
		      {name : 'CarNo',index : 'CarNo',width : 70,label:'车牌号',sortable : true,align : "left"},
		      {name : 'Phone',index : 'Phone',width : 80,label:'电话',sortable : true,align : "left"},
		      {name : 'IDNum',index : 'IDNum',width : 120,label:'身份证号',sortable : true,align : "left"},
		      {name : 'Address',index : 'Address',width : 120,label:'家庭住址',sortable : true,align : "left"},
		      {name : 'JoinDate',index : 'JoinDate',width : 80,label:'加入日期',sortable : true,align : "left",formatter:formatDate},
		      {name : 'LeaveDate',index : 'LeaveDate',width : 80,label:'离开日期',sortable : true,align : "left",formatter:formatDate},
		      {name : 'Remarks',index : 'Remarks',width : 100,label:'备注',sortable : true,align : "left"},
		      {name : 'LastUpdate',index : 'LastUpdate',width : 135,label:'最后修改时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'LastUpdatedBy',index : 'LastUpdatedBy',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'Expired',index : 'Expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : "ActionLog",index : "ActionLog",width : 70,label:"修改操作",sortable : true,align : "left"},
            ]
		});
});
</script>
<style>
	.panel-body{
		padding-top : 5px;
		padding-bottom : 5px;
	}
</style>
</head>
	
<body>
	<div class="body-box-list">
		<!-- query -->
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${driver.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<div class="panel-body">
						<li>
							<label>姓名</label>
							<input type="text" id="nameChi" name="nameChi" style="width:160px;"  value="${driver.nameChi }" />
						</li>
						<li>
							<label>电话</label>
							<input type="text" id="phone" name="phone" style="width:160px;"  value="${driver.phone }" />
						</li>
						<li>
							<label>司机类型</label>
							<house:xtdm id="driverType" dictCode="DRIVERTYPE" value="${driver.driverType }"></house:xtdm>
						</li>
						<li class="search-group">
							<div>
								<input type="checkbox" id="expired_show" name="expired_show" 
									value="${driver.expired }" onclick="hideExpired(this)" 
									${driver.expired!='T'?'checked':'' }/>隐藏过期记录&nbsp;
								<button type="button" class="btn btn-system " onclick="goto_query()">查询</button>
								<button type="button" class="btn btn-system " onclick="clearForm()">清空</button>
							</div>
							
						</li>
					</div>
				</ul>
			</form>
		</div>
		<!--query-form-->
			<div class="pageContent">
				<div class="btn-panel">
					<div class="btn-group-sm">
						<button type="button" class="btn btn-system " onclick="add()">新增</button>
						<button type="button" class="btn btn-system " onclick="update()">修改</button>
						<house:authorize authCode="DRIVER_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">查看</button>
						</house:authorize>
						<button type="button" class="btn btn-system " onclick="doExcel()">输出到Excel</button>
					</div>
				</div>
			</div>
			<!--panelBar end-->
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>

</html>
