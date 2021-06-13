<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script src="${resourceRoot}/js/iss.core.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	findDelivCode();//获取交房批次信息中默认选中的code
	var code=$("#builderDelivCode").val();
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/builderNum/goJqGrid2",
			postData:{builderDelivCode:code},
		    rowNum:10000000,
			height:390,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
					{name: "pk", index: "pk", width: 158, label: "pk", sortable: true, align: "left",hidden:true},
					{name: "builderdelivcode", index: "builderdelivcode", width: 158, label: "builderdelivcode", sortable: true, align: "left",hidden:true},
					{name: "buildernum", index: "buildernum", width: 158, label: "楼号", sortable: true, align: "left",editable:true}
			], 
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable2").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable2"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable2", v);
					});
				},
			beforeSaveCell:function(rowId,name,val,iRow,iCol){
				var ret = $("#dataTable2").jqGrid('getRowData', rowId);
				if(ret.buildernum!=val){
				art.dialog({
				content : "数据发生变动，是否修改",
				ok : function() {
							$.ajax({
									url:'${ctx}/admin/builderNum/doUpdate',
									type: 'post',
									data:{pk:ret.pk,builderNum:val,builderDelivCode:ret.builderdelivcode},
									dataType: 'json',
									cache: false,
									error: function(obj){
										showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
								    },
								    success: function(obj){
										if(obj){
											art.dialog({
												content:'修改成功',
												time:500,
											});
										}else{
											queryTable2();
											art.dialog({
												content:obj.msg,
												time:1500,
											});
										}
								    }
								 });
							},
							cancel : function() {
								queryTable2();
							}
						});
		 			
					}
				},
 		};
	   Global.JqGrid.initEditJqGrid("dataTable2",gridOption);
});
 	//增加
	function no_add() {
		var ret = selectDataTableRow();
		if(ret){
		var builderDelivCode=$("#builderDelivCode").val();
		Global.Dialog.showDialog("builderNumAdd",{
		  title:"楼号信息--增加",
		  url:"${ctx}/admin/builderNum/goSave?builderDelivCode="+builderDelivCode,
		  height: 230,
		  width:400,
		  returnFun: function(){
					  		queryTable2();
					  	 }
		  });
		}else{
			art.dialog({
				content: "请选择一条交房批次信息"
			});
		}
	}
	//编辑
	function no_update() {
		var id = $("#dataTable2").jqGrid("getGridParam","selrow");
		var ret=$("#dataTable2").jqGrid('getRowData',id);
		if(id){
		var builderNum=(ret.buildernum).replace("#","%23").replace("+","%2B").replace("&","%26");
			Global.Dialog.showDialog("builderNumUpdate",{
			  title:"楼号信息--修改",
			  url:"${ctx}/admin/builderNum/goUpdate?pk="+ret.pk+"&&builderNum="+builderNum+"&&builderDelivCode="+ret.builderdelivcode,
			  height: 230,
			  width:400,
			  returnFun: function(){
					  		queryTable2();
					  	 }
			}); 
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//查看
	function no_view() {
		var id = $("#dataTable2").jqGrid("getGridParam","selrow");
		var ret=$("#dataTable2").jqGrid('getRowData',id);
		if(id){
		var builderNum=(ret.buildernum).replace("#","%23").replace("+","%2B").replace("&","%26");
			Global.Dialog.showDialog("builderNumDetail",{
			  title:"楼号信息--查看",
			  url:"${ctx}/admin/builderNum/goDetail?pk="+ret.pk+"&&builderNum="+builderNum+"&&builderDelivCode="+ret.builderdelivcode,
			  height: 230,
			  width:400,
			  returnFun: function(){
			  				queryTable2();
					  	 }
			});
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//批量增加
	function no_multiAdd(){
		var ret = selectDataTableRow();
		if(ret){
			var builderDelivCode=$("#builderDelivCode").val();
			Global.Dialog.showDialog("builderNumMultiAdd",{
			  title:"楼号信息--批量增加",
			  url:"${ctx}/admin/builderNum/goMultiAdd?builderDelivCode="+builderDelivCode,
			  height: 300,
			  width:400,
			  returnFun: function(){
			  				queryTable2();
					  	 }
			});
		}else{
			art.dialog({
				content: "请选择一条交房批次信息"
			});
		}
	};
	//删除
	function no_del(){
		var id = $("#dataTable2").jqGrid("getGridParam","selrow");
		var ret=$("#dataTable2").jqGrid('getRowData',id);
		if (id) {
			art.dialog({
				content : "确认删除该记录",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/builderNum/doDelete?deleteIds=" + ret.pk,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									queryTable2();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								queryTable2();
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
					queryTable2();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	};
	/** 刷新表格 **/
	function queryTable2(){
		var code=$("#builderDelivCode").val();
		$("#dataTable2").jqGrid("setGridParam",{postData:{builderDelivCode:code},page:1,sortname:''}).trigger("reloadGrid");
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form2" action=""
				method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					id="no_add" onclick="no_add()" >新增</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="no_update" onclick="no_update()" >编辑</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="no_multiAdd" onclick="no_multiAdd()" >批量增加</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="no_del"  onclick="no_del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					id="no_view" onclick="no_view()">
					<span>查看 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dataTable2"></table>
	</div>
</div>



