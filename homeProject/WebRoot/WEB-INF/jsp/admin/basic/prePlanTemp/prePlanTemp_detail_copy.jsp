<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>搜寻--快速预报价模板</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
		}	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prePlanTemp/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 123, label: "编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 210, label: "名称", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 152, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 106, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 101, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 116, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow:function(){
						art.dialog({
							 content:"是否复制模板？",
							 lock: true,
							 width: 200,
							 height: 100,
							 ok: function () {
									var ret = selectDataTableRow();
									$.ajax({
			                                url : '${ctx}/admin/prePlanTempDetail/findDetailByNo',
			                                type : 'post',
			                                data : {
			                                     'no' : ret.no
			                                },
			                                async:false,
			                                dataType : 'json',
			                                cache : false,
			                                error : function(obj) {
			                                     showAjaxHtml({
			                                           "hidden" : false,
			                                           "msg" : '保存数据出错~'
			                                     });
			                                },
			                                success : function(obj) {
			                                	console.log(obj);
			                                    Global.Dialog.returnData = obj;
			                                    closeWin(true);
			                                }
			                           });						
							 },
							 cancel: function () {
									return true;
							 }
						}); 
		    },	
		});
	});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li class="form-validate"><label>编码</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${prePlanTemp.no}" />
					</li>
					<li class="form-validate"><label>名称</label> <input type="text"
						id="descr" name="descr" style="width:160px;" value="${prePlanTemp.descr}"/>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${prePlanTemp.expired}"
						onclick="hideExpired(this)" ${prePlanTemp.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
