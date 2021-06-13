<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
var isCopy=false;
$(function(){
	Global.JqGrid.initJqGrid("detailDataTable",{
			url:"${ctx}/admin/baseBatchTemp/goAreaJqGrid",
			postData:{no:"${baseBatchTemp.no}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:10000000,
			searchBtn:true,
			onSortColEndFlag:true,
			height:380,
			colModel : [
				{name: "no", index: "no", width: 80, label: "区域编号", sortable: true, align: "left"},
				{name: "descr", index: "descr", width: 100, label: "区域名称", sortable: true, align: "left"},
				{name: "areatype", index: "areatype", width: 108, label: "区域类型", sortable: true, align: "left",hidden:true},
				{name: "areatypedescr", index: "areatypedescr", width: 108, label: "区域类型", sortable: true, align: "left"},
				{name: "dispseq", index: "dispseq", width: 95, label: "显示顺序", sortable: true, align: "left",hidden:true},
				{name: "lastupdate", index: "lastupdate", width: 139, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 97, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 108, label: "修改操作", sortable: true, align: "left"}
			],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#detailDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("detailDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("detailDataTable", v);
					});
			},
		});
	});
	//新增
	function add(){
		Global.Dialog.showDialog("item", {
			title : "模板区间管理--增加",
			url : "${ctx}/admin/baseBatchTemp/goItem",
			postData:{no:$("#no").val(),descr:$("#descr").val(),m_umState:"A"},
		    height:700,
		    width:1300,
			returnFun : function(v) {
				goto_query("detailDataTable");
			}
		});
	}
	//编辑
	function update(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("item", {
			title : "模板区间管理--编辑",
			url : "${ctx}/admin/baseBatchTemp/goItem",
		    height:700,
		    width:1300,
		    postData:{
		   		no:$("#no").val(),descr:$("#descr").val(),m_umState:"M",
		   		areaNo:ret.no,areaDescr:ret.descr,areaType:ret.areatype
		    },
			returnFun : function(v) {
						goto_query("detailDataTable");
			}
		});
	}
	//查看
	function view(){
		var id = $("#detailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#detailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		} 
		Global.Dialog.showDialog("item", {
			title : "模板区间管理--查看",
			url : "${ctx}/admin/baseBatchTemp/goItem",
		    height:700,
		    width:1300,
		    postData:{
		   		no:$("#no").val(),descr:$("#descr").val(),m_umState:"V",
		   		areaNo:ret.no,areaDescr:ret.descr,areaType:ret.areatype
		    },
		});
	}
	//删除
	function del(){
		var ret = selectDataTableRow("detailDataTable");
		if (ret) {
			art.dialog({
				content : "是否删除此模板区间？",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/baseBatchTemp/doSaveItem?areaNo="+ret.no+"&m_umState=D",
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query("detailDataTable");
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query("detailDataTable");
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
					goto_query("detailDataTable");
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	//向上
	function up(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"detailDataTable");
				scrollToPosi(replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				updateDispSeq(ret1.no,ret2.dispseq);//更新表
				updateDispSeq(ret2.no,ret1.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//向下
	function down(){
		var rowId = $("#detailDataTable").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#detailDataTable").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#detailDataTable").jqGrid("getRowData", rowId);
				var ret2 = $("#detailDataTable").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"detailDataTable");
				replace(rowId,replaceId,"detailDataTable");
				$("#detailDataTable").setCell(rowId,"dispseq",ret1.dispseq);
				$("#detailDataTable").setCell(replaceId,"dispseq",ret2.dispseq);
				updateDispSeq(ret1.no,ret2.dispseq);//更新表
				updateDispSeq(ret2.no,ret1.dispseq);
				$("#dataChanged").val("1");
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	function updateDispSeq(areaNo,dispSeq){
		$.ajax({
			url : "${ctx}/admin/baseBatchTemp/updateDispSeq?areaNo="+areaNo+"&dispSeq="+dispSeq,
			type : "post",
			dataType : "json",
			cache : false,
			async:false
		});
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system view" 
					onclick="add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="update()">
					<span>编辑 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system view"
					onclick="del()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					onclick="view()">
					<span>查看 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="up()">
					<span>向上 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="down()">
					<span>向下 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="doExcelNow('模板区间管理','detailDataTable','form')">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="detailDataTable"></table>
	</div>
</div>



