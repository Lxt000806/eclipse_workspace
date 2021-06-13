<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script src="${resourceRoot}/js/iss.core.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/builderDeliv/goJqGrid",
			postData:{builderCode:"${builder.code}"},
		    rowNum:10000000,
			height:390,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
					{name: "ischeckout", index: "ischeckout", width: 90, label: "是否记账", sortable: true, align: "left", hidden: true},
					{name: "code", index: "code", width: 80, label: "编号", sortable: true, align: "left"},
					{name: "delivdate", index: "delivdate", width: 100, label: "交房时间", sortable: true, align: "left", formatter: formatDate},
					{name: "delivnum", index: "delivnum", width: 100, label: "交房户数", sortable: true, align: "right"},
					{name: "buildertypedescr", index: "buildertypedescr", width: 80, label: "项目类型", sortable: true, align: "left"},
					{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"}
			], 
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
				},
			onSelectRow:function(){
                    findDelivCode();
					$("#dataTable2").jqGrid("setGridParam",{
						postData:{builderDelivCode:$("#builderDelivCode").val()},
						page:1,
						sortname:''
					}).trigger("reloadGrid");
                },
 		};
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
});
 
	function info_add() {
		Global.Dialog.showDialog("builderDelivAdd",{
		  title:"交房批次信息--增加",
		  url:"${ctx}/admin/builderDeliv/goSave?builderCode=${builder.code}",
		  height: 370,
		  width:700,
		  returnFun: goto_query
		});
	}
	
	function info_update() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("builderDelivUpdate", {
				title : "交房批次信息--修改",
				url : "${ctx}/admin/builderDeliv/goUpdate?id=" + ret.code,
				height: 370,
				width:700,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	function info_view() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("builderDelivDetail", {
				title : "交房批次信息--查看",
				url : "${ctx}/admin/builderDeliv/goDetail?id=" + ret.code,
				height: 370,
				width:700,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	//删除
	function info_del(){
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content : "确认删除该记录",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/builderDeliv/doDelete?deleteIds=" + ret.code,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query();
							} else {
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content : obj.msg,
									width : 200
								});
							}
						}
					});
				},
				cancel : function() {
					goto_query();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	};
	
	//返回DelivCode
	function findDelivCode(){
		var ret = selectDataTableRow();
		//若选中行没有值，则查数据库的第一个
		if(!ret){
			$.ajax({
				url:'${ctx}/admin/builderDeliv/findFirstDelivCode?builderCode=${builder.code}',
				type: 'post',
				dataType: 'json',
				cache: false,
				async: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
			    	$("#builderDelivCode").val(obj.Code);
			    }
		 	});
		}else{
			$("#builderDelivCode").val(ret.code);
		}
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="builderDelivCode" name="builderDelivCode">
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					id="info_add" onclick="info_add()" >新增</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="info_update" onclick="info_update()" >编辑</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="info_del"  onclick="info_del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="info_view" onclick="info_view()">
					<span>查看 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dataTable"></table>
	</div>
</div>



