<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>材料定额管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${setItemQuota.expired }"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label >面积从</label>
						<input type="text" id="fromArea" name="fromArea" value="${setItemQuota.fromArea}"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
					</li>
					<li>
						<label >面积到</label>
						<input type="text" id="toArea" name="toArea" value="${setItemQuota.toArea}"  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
					</li>
					<li>
						<label>客户类型</label>
						<house:dict id="custType" dictCode="" sql=" select code,desc1 from tCusttype where expired='F' " 
                                    sqlValueKey="code" sqlLableKey="desc1" />
					</li>	
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${courseType.expired }" onclick="hideExpired(this)" ${courseType.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system" id="save" onclick="add()">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="update" onclick="update()">
					<span>编辑</span>
				</button>
				<house:authorize authCode="SETITEMQUOTA_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button>
			</div>
		</div>

		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

<script type="text/javascript">
	var postData = $("#page_form").jsonForm();
	$(function(){
		Global.JqGrid.initJqGrid("dataTable", {
			url: "${ctx}/admin/setItemQuota/goJqGrid",
			postData:postData ,
			height: $(document).height() - $("#content-list").offset().top - 70,
			styleUI: "Bootstrap", 
			colModel: [
				{name : "no", index:"no", width:100, label:"编号", sortable:true, align:"left"},
				{name : "custtypedescr", index:"custtypedescr", width:100, label:"客户类型", sortable:true, align:"left"},
	      		{name : "fromarea",index : "fromarea",width : 100,label:"面积从",sortable : true,align : "left"},
	     		{name : "toarea",index : "toarea",width : 100,label:"面积到",sortable : true,align : "left"},
	     		{name : "remark",index : "remark",width : 150,label:"备注",sortable : true,align : "left"},
				{name : "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name : "lastupdatedby", index: "lastupdatedby", width: 93, label: "最后更新人员", sortable: true, align: "left"},
				{name : "expired", index: "expired", width: 65, label: "是否过期", sortable: true, align: "left"},
				{name : "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"}
	           ]
		});
	});
	
	function add(){	
		Global.Dialog.showDialog("setItemQuotaAdd",{			
			title:"套餐材料定额--新增",
			url:"${ctx}/admin/setItemQuota/goSave",
			height: 750,
			width:850,
			returnFun: goto_query
		});

	}
	
	function update(){	
		var ret = selectDataTableRow();		
		if (ret) {
			if(ret.status==2){
				art.dialog({content: "工程退款单状态为审核，不允许进行编辑!",width: 200});
				return false;
			}
			if(ret.status==3){
				art.dialog({content: "工程退款单状态为取消，不允许进行编辑!",width: 200});
				return false;
			}				
			Global.Dialog.showDialog("setItemQuotaUpdate",{			
				title:"套餐材料定额--编辑",
				url:"${ctx}/admin/setItemQuota/goSave",
				postData:{
					m_umState:"M",
					no:ret.no
				},
				height: 750,
				width:850,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}


	function view(){	
		var ret = selectDataTableRow();		
		if (ret) {			
			Global.Dialog.showDialog("setItemQuotaView",{			
				title:"套餐材料定额--查看",
				url:"${ctx}/admin/setItemQuota/goSave",
				postData:{
					m_umState:"V",
					no:ret.no
				},
				height: 750,
				width:850,
				returnFun: goto_query
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
	}
	
	function doExcel(){
		var url = "${ctx}/admin/setItemQuota/doExcel";
		doExcelAll(url);
	}

</script>
</html>
