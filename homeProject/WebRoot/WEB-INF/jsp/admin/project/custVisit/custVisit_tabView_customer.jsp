<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>客户回访管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	
</head>
<body>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm" style="padding-top: 5px;">
				<button type="button" class="btn btn-system" id="save">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system" id="delete">
					<span>删除</span>
				</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
		</div>
	</div>
</body>

<script type="text/javascript">
$(function(){
	var gridOption = {
		sortable: true,/* 列重排 */
		height : $(document).height()-$("#content-list").offset().top - 40,
		rowNum : 10000000,
		colModel : [
			{name: "code", index: "code", width: 140, label: "客户编号", sortable: true, align: "left", hidden: true},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left"},
			{name: "custtype", index: "custtype", width: 50, label: "客户类型num", sortable: true, align: "left", hidden: true},
			{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
			{name: "confirmbegin", index: "confirmbegin", width: 100, label: "开工日期", sortable: true, align: "left", formatter: formatDate},
			{name: "projectman", index: "projectman", width: 50, label: "项目经理num", sortable: true, align: "left", hidden: true},
			{name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
			{name: "gcdeptdescr", index: "gcdeptdescr", width: 70, label: "工程部", sortable: true, align: "left"},
			{name: "prjitemdescr", index: "prjitemdescr", width: 100, label: "当前进度", sortable: true, align: "left"}
		],
		loadonce: true,/* 只加载一次，之后变为本地数据  */
	};
	
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	$("#save").on("click", function() {
		var vType = $("#visitType").val().trim();/* 访问类型 */
		var codes = Global.JqGrid.allToJson("dataTable","code");
		var arr = new Array();
		arr = codes.fieldJson.split(",");/* 已存在的客户编号 */
		/* var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var beginDate = year+"-"+month+"-01 00:00:00";
		var endDate = new Date(year,month,0);
		endDate = year+"-"+month+"-"+endDate.getDate()+" 23:59:59";// 暂且无用——获取该月的头一天和最后一天 */
		
		//提取访问类型descr
		var vTypeText = $("#visitType").find("option:selected").text();
		var vTypeArr = vTypeText.split(" ");
	 	var vTypeDescr = vTypeArr[vTypeArr.length-1];
		
		if (!vType) {
			art.dialog({
				content: "请先选择回访类型"
			});
			return;
		}
		Global.Dialog.showDialog("save", {
			title : "客户回访--选择客户",
			url : "${ctx}/admin/custVisit/goCustSelect",
			postData : {visitType:vType,arr:arr},
			height : 700,
			width : 1100,
			returnFun : function(data){
				if(data){
					$.each(data,function(k,v){
						var json = {
							code : v.code,
							address : v.address,
							custtype : v.custtype.trim(),
							custtypedescr : v.custtypedescr,
							confirmbegin : v.confirmbegin,
							projectman : v.projectman,
							projectmandescr : v.projectmandescr,
							gcdeptdescr : v.gcdeptdescr,
							prjitemdescr : v.prjitemdescr
					  	};
					  	Global.JqGrid.addRowData("dataTable",json);
					  	/* 当有数据添加时，访问类型不可点 */
					  	var ids = $("#dataTable").getDataIDs();
						$("#visitType").attr("disabled",false);
						if (ids.length != 0) {
							$("#visitType").attr("disabled",true);
						}
					});
				}
			}
		});
		
	});
	
	$("#delete").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作",width: 220});
			return false;
		}
		art.dialog({
			content:"是否删除该记录？",
			lock: true,
			width: 200,
			height: 80,
			ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				var ids = $("#dataTable").getDataIDs();
				$("#visitType").attr("disabled",false);
				if (ids.length != 0) {
					$("#visitType").attr("disabled",true);
				}
			},
			cancel: function () {
				return true;
			}
		});
		
	});
	
});

</script>
</html>
