<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
<title>优惠券信息获取编号</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
	function clearForm(){
			$("#page_form input[type='text']").val("");
			$("#type").val("");
		}	
	$(function(){
		var postData = $("#page_form").jsonForm();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/discTokenQuery/goJqGrid",
			postData:postData,
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			colModel : [
				{name:"custcode", index:"custcode", width:60, label:"客户编号", sortable:true, align:"left",hidden:true},
				{name:"address", index:"address", width:120, label:"楼盘", sortable:true, align:"left"},
				{name:"itemtype1", index:"itemtype1", width:60, label:"材料类型1", sortable:true, align:"left",hidden:true},
				{name:"itemtype1descr", index:"itemtype1descr", width:80, label:"材料类型1", sortable:true, align:"left"},
				{name:"no", index:"no", width:60, label:"券号", sortable:true, align:"left"},
				{name:"descr", index:"descr", width:80, label:"券名", sortable:true, align:"left"},
				{name:"amount", index:"amount", width:60, label:"金额", sortable:true, align:"right"},
				{name:"statusdescr", index:"statusdescr", width:60, label:"状态", sortable:true, align:"left"},
				{name:"enabledate", index:"enabledate", width:80, label:"生效日期", sortable:true, align:"left", formatter:formatDate},
				{name:"crtdate", index:"crtdate", width:80, label:"登记日期", sortable:true, align:"left", formatter:formatDate},
				{name:"useaddress", index:"useaddress", width:120, label:"使用楼盘", sortable:true, align:"left"},
				{name:"usestepdescr", index:"usestepdescr", width:60, label:"使用阶段", sortable:true, align:"left"},
				{name:"chgno", index:"chgno", width:75, label:"增减单号", sortable:true, align:"left"},
				{name:"checkdesdate", index:"checkdesdate", width:80, label:"核销日期", sortable:true, align:"left", formatter:formatDate},
				{name:"checkdesamount", index:"checkdesamount", width:75, label:"核销金额", sortable:true, align:"right"},
				{name:"expireddate", index:"expireddate", width:80, label:"失效日期", sortable:true, align:"right"},	
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
	        	Global.Dialog.returnData = selectRow;
	        		Global.Dialog.closeDialog();
        	}
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
				<input type="hidden" id="unSelectNo" name="unSelectNo" value="${discToken.unSelectNo}" />
				<input type="hidden" id="custCode" name="custCode" value="${discToken.custCode}" />
				<input type="hidden" id="itemType1" name="itemType1" value="${discToken.itemType1}" />
				<input type="hidden" id="status" name="status" value="${discToken.status}" />
				<input type="hidden" id="oldCustCode" name="oldCustCode" value="${discToken.oldCustCode}" />
				<input type="hidden" id="hasSelectNo" name="hasSelectNo" value="${discToken.hasSelectNo}" />
				<input type="hidden" id="containHasSelect" name="containHasSelect" value="${discToken.containHasSelect}" />
				<input type="hidden" id="useStep" name=""useStep"" value="${discToken.useStep}" />
				<ul class="ul-form">
					<li class="form-validate"><label>编码</label> <input type="text"
						id="no"  name="no" style="width:160px;"
						value="${discToken.no}" />
					</li>
					<li class="form-validate"><label>名称</label> <input type="text"
						id="descr" name="descr" style="width:160px;" value="${discToken.descr}"/>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${discToken.expired}"
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
