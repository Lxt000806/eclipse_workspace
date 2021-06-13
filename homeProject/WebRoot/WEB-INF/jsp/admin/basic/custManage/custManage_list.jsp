<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>客户管理列表页</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable", {
				height:$(document).height()-$("#content-list").offset().top-70,
				url:"${ctx}/admin/custManage/goJqGrid",
				colModel : [			
					{name: "pk", index: "pk", width: 75, label: "pk", sortable: true, align: "left", hidden:true},
					{name: "nickname", index: "nickname", width: 70, label: "用户昵称", sortable: true, align: "left"},
					{name: "mobile1", index: "mobile1", width: 80, label: "手机号", sortable: true, align: "left"},
					{name: "registerdate", index: "registerdate", width: 140, label: "注册时间", sortable: true, align: "left", formatter: formatTime},
					{name: "picaddr", index: "picaddr", width: 500, label: "头像地址", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"}
	            ]
			});
		});
		
		function goSave(){
			Global.Dialog.showDialog("custManageSave",{
				  title: "新增客户",
				  url: "${ctx}/admin/custManage/goSave",
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
				Global.Dialog.showDialog("custManageUpdate", {
					title: "编辑客户",
					url: "${ctx}/admin/custManage/goUpdate",
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
		
		function goView(){
			var ret = selectDataTableRow();
		    if (ret) {
				Global.Dialog.showDialog("custManageView", {
					title: "查看客户",
					url: "${ctx}/admin/custManage/goView",
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
					<input type="hidden" id="expired" name="expired" value="${custAccount.expired}"/>
					<ul class="ul-form">
						<li>
							<label>手机号</label>
							<input type="text" id="mobile1" name="mobile1" value="${custAccount.mobile1}" />
						</li>
							
						<li>
							<label>注册日期从</label>
							<input type="text" id="registerDateFrom" name="registerDateFrom" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="registerDateTo" name="registerDateTo" class="i-date" 
									onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>存在关联楼盘</label> 
							<house:xtdm id="hasBindAddress" dictCode="YESNO" value="${custAccount.hasBindAddress }" ></house:xtdm>
						</li>
						<li class="search-group-shrink">
							<input type="checkbox" id="expired_show" name="expired_show"
									value="${custAccount.expired}" onclick="hideExpired(this)"
									${custAccount.expired!='T'?'checked':'' }/><p>隐藏过期&nbsp;&nbsp;&nbsp;&nbsp;</p>
							<button type="button" class="btn btn-sm btn-system" onclick="goto_query()">查询</button>
							<button type="button" class="btn btn-sm btn-system" onclick="clearForm()">清空</button>
						</li>
					
					</ul>
				</form>
			</div>
			<div class="clear_float"> </div>
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="CUSTMANAGE_SAVE">
						<button type="button" class="funBtn funBtn-system" onclick="goSave()">新增</button>
					</house:authorize>
					<house:authorize authCode="CUSTMANAGE_UPDATE">
						<button type="button" class="funBtn funBtn-system" onclick="goUpdate()">编辑</button>
					</house:authorize>
					<house:authorize authCode="CUSTMANAGE_DETAIL">
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


