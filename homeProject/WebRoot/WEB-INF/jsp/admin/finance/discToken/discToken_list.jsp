<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>优惠券查询</title>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${discToken.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
				    <li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${discToken.address }" />
					</li>
					<li>
						<label>状态</label>
						<house:xtdmMulit id="status" dictCode="DISCTOKENSTATUS" selectedValue="${discToken.status }"></house:xtdmMulit>
					</li>
					 <li>
						<label>券号</label>
						<input type="text" id="no" name="no" style="width:160px;" value="${discToken.no }" />
					</li>
					<li><label>材料类型1</label> <select id="itemType1" name="itemType1" ></select>
					</li>
					<li> 
					    <label>日期类型</label>
						<select id="dateType" name="dateType">
							<option value="1">生效日期</option>
							<option value="2">登记日期</option>
							<option value="3">失效日期</option>
							<option value="4">核销日期</option>
						</select>
					</li>
					<li>
					<label>日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${discToken.dateFrom}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"  onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${discToken.dateTo}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${discToken.expired}" 
							onclick="hideExpired(this)" ${discToken!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="DISCTOKENQUERY_EXPIRED">
					<button type="button" class="btn btn-system" id="updateBtn" onclick="update()">
						<span>过期</span>
					</button>
				</house:authorize>
				<house:authorize authCode="DISCTOKENQUERY_RECOVER">
					<button type="button" class="btn btn-system" onclick="recover()">
						<span>恢复</span>
					</button>
				</house:authorize>
				
				<house:authorize authCode="DISCTOKENQUERY_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
					<span>输出到excel</span>
					</button>
				</house:authorize>
				
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
			Global.LinkSelect.setSelect({firstSelect:"itemType1",
										firstValue:"${discToken.itemType1}",		
			});
			var postData = $("#page_form").jsonForm();
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/discTokenQuery/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"custcode", index:"custcode", width:70, label:"客户编号", sortable:true, align:"left"},
					{name:"address", index:"address", width:120, label:"楼盘", sortable:true, align:"left"},
					{name:"custstatusdescr", index:"custstatusdescr", width:70, label:"客户状态", sortable:true, align:"left"},
					{name:"itemtype1", index:"itemtype1", width:60, label:"材料类型1", sortable:true, align:"left",hidden:true},
					{name:"itemtype1descr", index:"itemtype1descr", width:80, label:"材料类型1", sortable:true, align:"left"},
					{name:"no", index:"no", width:60, label:"券号", sortable:true, align:"left"},
					{name:"descr", index:"descr", width:120, label:"券名", sortable:true, align:"left"},
					{name:"amount", index:"amount", width:60, label:"金额", sortable:true, align:"right"},
					{name:"statusdescr", index:"statusdescr", width:60, label:"状态", sortable:true, align:"left"},
					{name:"status", index:"status", width:60, label:"状态", sortable:true, align:"left",hidden:true},
		    		{name:"enabledate", index:"enabledate", width:80, label:"生效日期", sortable:true, align:"left", formatter:formatDate},
		    		{name:"crtdate", index:"crtdate", width:80, label:"登记日期", sortable:true, align:"left", formatter:formatDate},
		    		{name:"useaddress", index:"useaddress", width:120, label:"使用楼盘", sortable:true, align:"left"},
		    		{name:"usestepdescr", index:"usestepdescr", width:80, label:"使用阶段", sortable:true, align:"left"},
		    		{name:"chgno", index:"chgno", width:75, label:"增减单号", sortable:true, align:"left"},
		    		{name:"checkdesdate", index:"checkdesdate", width:80, label:"核销日期", sortable:true, align:"left", formatter:formatDate},
		    		{name:"checkdesamount", index:"checkdesamount", width:75, label:"核销金额", sortable:true, align:"right"},
		    		{name:"expireddate", index:"expireddate", width:80, label:"失效日期", sortable:true, align:"left", formatter:formatDate},
		    	],
				
			});
		});
		
		function doExcel(){
			var url = "${ctx}/admin/discTokenQuery/doExcel";
			doExcelAll(url);
		}
		
		function update(){
	       var ret = selectDataTableRow();
	       if (ret) {   
	    	    if(ret.status!="2"){
	    	    	art.dialog({
		    			content: "只能对已生效的记录进行过期操作"
		    		});
	    	    	return;
	    	    }
				Global.Dialog.showDialog("update", {
					title : "优惠券查询 ——优惠券过期",
					url : "${ctx}/admin/discTokenQuery/goUpdate?id="+ret.no,
					height:250,
					width : 445,
					returnFun : goto_query
				});
	        } else {
	        	art.dialog({
	    			content: "请选择一条记录"
	    		});
	        }
		}
		
		function recover(){
			var ret = selectDataTableRow();
	       if (ret) {   
	    	    if(ret.status!="5"){
	    	    	art.dialog({
		    			content: "只能对已失效的记录进行过期操作"
		    		});
	    	    	return;
	    	    }
	    	    
	    	    art.dialog({
					content:"是否确定恢复该优惠券?",
					lock: true,
					width: 200,
					height: 100,
					ok: function () {
						$.ajax({
							url:"${ctx}/admin/discTokenQuery/doRecover",
							type:"post",
							data:{no: ret.no},
							dataType:"json",
							cache:false,
							error:function(obj){
								showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
							},
							success:function(obj){
								art.dialog({
									content: obj.msg,
								});
								$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
							}
						});
					},
					cancel: function () {
						return true;
					}
				});	
				
				
	        } else {
	        	art.dialog({
	    			content: "请选择一条记录"
	    		});
	        }
		}
		function clearForm(){
			$("#page_form input[type='text']").val('');
			$("#page_form select").val('');
			$("#status").val('');
			$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
		}
	</script>
</body>
</html>
