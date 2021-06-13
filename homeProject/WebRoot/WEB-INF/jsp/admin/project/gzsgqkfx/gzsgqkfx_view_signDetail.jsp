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
	<title>工地施工情况——签到明细</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	$(function(){
		// 签到明细			
		var gridOption ={
			url: "${ctx}/admin/custWorker/goViewSignJqGrid",
			postData: {custCode:"${custCode}", workType12:"${workType12}"},
			rowNum: 10000000,
			height: $(document).height()-$("#content-list-signDetail").offset().top-36,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "custcode", index: "custcode", width: 70, label: "客户编号", sortable: true, align: "left",},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",},
				{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种类型", sortable: true, align: "left"},
				{name: "workerdescr", index: "workerdescr", width: 75, label: "工人名称", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 120, label: "签到时间", sortable: true, align: "left",formatter: formatTime},
				{name: "prjitem2descr", index: "prjitem2descr", width: 80, label: "施工阶段", sortable: true, align: "left"},
				{name: "isend", index: "isend", width: 80, label: "是否完成", sortable: true, align: "left"},
				{name: "no", index: "no", width: 80, label: "no", sortable: true, align: "left",hidden:true},
				{name: "photonum", index: "photonum", width: 80, label: "图片数量", sortable: true, align: "right"
					,formatter:function(cellvalue, options, rowObject){
        				if(rowObject==null || !rowObject.photonum || rowObject.photonum == '0'){
          				return '0';
          				}
        				return "<a href=\"javascript:void(0)\" style=\"color:blue!important\" onclick=\"goViewPic('"
        					+rowObject.no+"','"+rowObject.custcode+"')\">"+rowObject.photonum+"</a>";
	    			},
	    		},
				{name: "custscore", index: "custscore", width: 80, label: "客户评分", sortable: true, align: "right"},
				{name: "custremarks", index: "custremarks", width: 220, label: "客户评价", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "custcode", sortable: true, align: "left",hidden:true},
			], 
		};
		Global.JqGrid.initJqGrid("dataTable_signDetail",gridOption);
	});
	
	function goViewPic(no, custCode){
		Global.Dialog.showDialog("goUpdate",{
			title:"签到明细——查看图片",
			postData:{no: no, custCode: custCode},
			url:"${ctx}/admin/workTypeConstructDetail/goViewPic",
			height:720,
			width:1230,
			returnFun:goto_query
		});	
	}
	
	function viewPic(){
		var ret = selectDataTableRow("dataTable_signDetail");
		if(!ret){
			art.dialog({
				content:"请选择一条数据进行查看",
			});
			return;
		}
		if(ret.photonum == "0"){
			art.dialog({
				content:"该楼盘没有签到图片",
			});
			return;
		}
		goViewPic(ret.no, ret.custcode);
	}
	
	</script>
</head>
<body>
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
				<button type="button" class="btn btn-system " id="viewPic" onclick="viewPic(false)">
					<span>查看图片</span>
				</button>
				<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="body-box-list" style="margin-top: 0px;">
		<div class="clear_float"></div>
		<div id="content-list-signDetail" >
			<table id="dataTable_signDetail"></table>
		</div>
	</div>
</body>
</html>
