<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>富文本管理列表</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-95,
				url:"${ctx}/admin/advert/goJqGrid",
				colModel : [			
					{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left", hidden:true},
					{name: "advtypedescr", index: "advtypedescr", width: 70, label: "广告类型", sortable: true, align: "left"},
					{name: "picaddr", index: "picaddr", width: 430, label: "图片地址", sortable: true, align: "left"},
					{name: "title", index: "title", width: 70, label: "广告标题", sortable: true, align: "left"},
					{name: "dispseq", index: "dispseq", width: 70, label: "显示顺序", sortable: true, align: "right"},
					{name: "content", index: "content", width: 350, label: "内容", sortable: true, align: "left", formatter:function(cellvalue, options, rowObject){return decodeURI(cellvalue).replace(/>/g, "&gt;").replace(/</g, "&lt;");}},
					{name: "outurl", index: "outurl", width: 120, label: "外部链接", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后更新人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
	            ]
			});
		});
		
		function goSave(){
			Global.Dialog.showDialog("advertSave",{
				  title: "新增广告",
				  url: "${ctx}/admin/advert/goSave",
				  height: 600,
				  width: 1100,
				  returnFun: goto_query,
				  close: function(){
					goto_query();
				  }
				});
		}
		
		function goUpdate(){
			var ret = selectDataTableRow();
		    if (ret) {
				Global.Dialog.showDialog("advertUpdate", {
					title: "编辑广告",
					url: "${ctx}/admin/advert/goUpdate",
					postData: {
						pk: ret.pk
					},
					height: 600,
					width: 1100,
					returnFun: goto_query,
					close: function(){
						goto_query();
					}
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		
		function doDelete(){
			var ret = selectDataTableRow();
		    if (ret) {
		    	art.dialog({
		    		content: "是否继续删除?",
		    		ok: function(){
						$.ajax({
							url: "${ctx}/admin/advert/doDelete",
							type: "post",
						   	data: {
								pk: ret.pk
						   	},
							dataType: "json",
							cache: false,
							error: function(obj){			    		
								art.dialog({
									content: "访问出错,请重试",
									time: 3000,
									beforeunload: function () {}
								});
							},
							success: function(res){
								art.dialog({
									content: res.msg,
									ok: function(){
										if(res.rs){
											goto_query();
										}
									}
								});
							}
						});			    		
		    		},
		    		cancel: function(){}
		    	});
				
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		
		function goView(){
			var ret = selectDataTableRow();
		    if (ret) {
				Global.Dialog.showDialog("advertView", {
					title: "查看广告",
					url: "${ctx}/admin/advert/goView",
					postData: {
						pk: ret.pk
					},
					height: 600,
					width: 1100,
					returnFun: goto_query,
					close: function(){
						goto_query();
					}
				});
		    } else {
		    	art.dialog({
					content: "请选择一条记录"
				});
		    }
		}
		</script>
	</head>
	    
	<body>
		<div class="body-box-list" style="overflow-x:hidden">
			<div class="query-form">
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value="${data.expired}"/>
					<ul class="ul-form">
						<li>
							<label>广告类型</label>
							<house:xtdm id="advType" dictCode="ADVTYPE"></house:xtdm>
						</li>
							
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${data.expired}" onclick="hideExpired(this)"
									${data.expired!='T'?'checked':'' }/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="ADVERT_SAVE">
						<button type="button" class="funBtn funBtn-system" onclick="goSave()">新增</button>
					</house:authorize>
					<house:authorize authCode="ADVERT_UPDATE">
					<button type="button" class="funBtn funBtn-system" onclick="goUpdate()">编辑</button>
					</house:authorize>
					<house:authorize authCode="ADVERT_DEL">
					<button type="button" class="funBtn funBtn-system" onclick="doDelete()">删除</button>
					</house:authorize>
					<house:authorize authCode="ADVERT_DETAIL">
					<button type="button" class="funBtn funBtn-system" onclick="goView()">查看</button>
					</house:authorize>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</body>
</html>


