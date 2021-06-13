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
	<title>工地工人安排——签到明细</title>
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
				postData: {custCode:"${custCode}", workType12:"${workType12}", pk: "${pk}",dateFrom:formatDate("${beginDate}"),dateTo:formatDate("${endDate}")},
				rowNum: 10000000,
				height: $(document).height()-$("#content-list-signDetail").offset().top-36,
				styleUI: "Bootstrap", 
				colModel : [
					{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left",},
					{name: "worktype12descr", index: "worktype12descr", width: 80, label: "工种类型", sortable: true, align: "left"},
					{name: "workerdescr", index: "workerdescr", width: 75, label: "工人名称", sortable: true, align: "left"},
					{name: "worksignpk", index: "worksignpk", width: 120, label: "工人签到主键", sortable: true, align: "left", hidden: true},
					{name: "crtdate", index: "crtdate", width: 120, label: "签到时间", sortable: true, align: "left",formatter: formatTime},
					{name: "prjitem2descr", index: "prjitem2descr", width: 80, label: "施工阶段", sortable: true, align: "left"},
					{name: "iscomplete", index: "iscomplete", width: 80, label: "是否完成", sortable: true, align: "left", hidden: true},
					{name: "isend", index: "isend", width: 80, label: "是否完成", sortable: true, align: "left"},
					{name: "no", index: "no", width: 80, label: "no", sortable: true, align: "left",hidden:true},
					{name: "photonum", index: "photonum", width: 80, label: "图片数量", sortable: true, align: "right"},
					{name: "custscore", index: "custscore", width: 80, label: "客户评分", sortable: true, align: "right"},
					{name: "custremarks", index: "custremarks", width: 220, label: "客户评价", sortable: true, align: "left"},
					{name: "custcode", index: "custcode", width: 80, label: "custcode", sortable: true, align: "left",hidden:true},
				], 
			};
			Global.JqGrid.initJqGrid("dataTable_signDetail",gridOption);
			
			$("#viewAll").on("click",function(){
				var ret = selectDataTableRow("dataTable_signDetail");
		       	  if (ret) {	
		           	Global.Dialog.showDialog("ViewAllGDYS",{ 
		            	  title:"查看签到图片",
		            	  url:"${ctx}/admin/custWorker/goViewAllPic",
		            	  postData:{no:ret.no,custCode:ret.custcode},
		            	  height: 800,
		            	  width:1200,
		            	  returnFun:goto_query
		            	});
		          } else {
		          	art.dialog({
		      			content: "请选择一条记录"
		      		});
		          }
			});
		});
		
		function doComplete() {
		    var row = selectDataTableRow("dataTable_signDetail")
		
		    if (row.iscomplete === '1') {
		        art.dialog({content: "此记录已完成"})
		        return
		    }
		
		    art.dialog({
		        content: '确定要设置此记录为完成吗？',
		        lock: true,
		        ok: function() {
		            $.ajax({
		                url: "${ctx}/admin/custWorker/doComplete",
		                type: "post",
		                data: {workSignPk: row.worksignpk},
		                dataType: "json",
		                cache: false,
		                error: function(obj) {
		                    showAjaxHtml({"hidden": false, "msg": "保存出错"})
		                },
		                success: function(obj) {
		                    if (obj.rs) {
		                        art.dialog({
		                            content: obj.msg,
		                            time: 1000,
		                            beforeunload: function() { goto_query("dataTable_signDetail") }
		                        })
		                    } else {
		                        art.dialog({
		                            content: obj.msg,
		                        })
		                    }
		                }
		            })
		        },
		        cancel: function() {
		        }
		    })
		
		}
		
		function undoComplete() {
		    var row = selectDataTableRow("dataTable_signDetail")
		
		    if (row.iscomplete === '0') {
		        art.dialog({content: "此记录未完成"})
		        return
		    }
		
		    art.dialog({
		        content: '确定要设置此记录为未完成吗？',
		        lock: true,
		        ok: function() {
		            $.ajax({
		                url: "${ctx}/admin/custWorker/undoComplete",
		                type: "post",
		                data: {workSignPk: row.worksignpk},
		                dataType: "json",
		                cache: false,
		                error: function(obj) {
		                    showAjaxHtml({"hidden": false, "msg": "保存出错"})
		                },
		                success: function(obj) {
		                    if (obj.rs) {
		                        art.dialog({
		                            content: obj.msg,
		                            time: 1000,
		                            beforeunload: function() { goto_query("dataTable_signDetail") }
		                        })
		                    } else {
		                        art.dialog({
		                            content: obj.msg,
		                        })
		                    }
		                }
		            })
		        },
		        cancel: function() {
		        }
		    })
		}
		
	</script>
</head>
<body>
	<div class="panel panel-system" >
		<div class="panel-body" >
			<div class="btn-group-xs" >
				<c:if test="${fromModule != 'bzsgfx' }">
					<button type="button" class="btn btn-system " id="viewAll" >
						<span>查看图片</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doComplete()">
	                    <span>完成</span>
	                </button>
					<button type="button" class="btn btn-system" onclick="undoComplete()">
	                    <span>撤销完成</span>
	                </button>
				</c:if>
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
