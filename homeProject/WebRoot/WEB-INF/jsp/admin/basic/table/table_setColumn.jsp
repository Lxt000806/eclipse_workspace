<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>设置表格字段</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
		.rowBox{
			display: -webkit-flex; /* Safari */
  			flex-direction: row ;
  			flex-wrap: wrap;
  			justify-content: space-between;
  			margin-bottom: 10px;
		}
		.query-form {
		    border: 0px ;
		}
		.common-label {
		    display: inline-block;
		    margin-bottom: 0px;
		    margin-top : 1px;
		    font-weight: normal;
		    cursor:pointer;
		}
		input[type='checkbox'] {
			cursor:pointer
		}
		td[role='gridcell']{
			cursor:move
		}
	</style>
	<script type="text/javascript">
	var hasChanged = "0";
	$(function(){
		//初始化全选框状态
		var selectAllStatus = "${selectAllStatus}";
		if(selectAllStatus == "2"){
			$("#all").prop("checked",true);
		}else if(selectAllStatus == "0"){
			$("#all").prop("indeterminate", true);
			$("#all").prop("checked",false);
		}else{
			$("#all").prop("checked",false);
		}
	});
	
	function doSave() {
		var isEnable = $("#isEnable").val();
		if(isEnable != "1"){
			art.dialog({
				content:"模板未启用，是否启用模板？",
				okValue: "是",
				ok: function () {
					$("#isEnable").prop("checked",true).val("1");
					save("已启用模板");
				},
				cancelValue: "否", 
				cancel: function () {
					$("#isEnable").val("0");
					save("已恢复系统默认模板");
				}
			});
		}else{
			save("已启用模板");
		}
	}
	
	function doClose(isCallBack){
		if(hasChanged == "1"){
			art.dialog({
				content:"检测到你调整了模板，是否确认退出？",
				okValue: "是",
				ok: function () {
					Global.Dialog.closeDialog(isCallBack);
				},
				cancelValue: "否", 
				cancel: function () {
					return true;
				}
			});
		}else{
			Global.Dialog.closeDialog(isCallBack);
		}
	}
	
	function save(content){
		var rets = $("#dataTable").jqGrid("getRowData");
		for(var i=0;i<rets.length;i++){
			rets[i].DispSeq=i+1;
		}
		var params = {"detailJson": JSON.stringify(rets)};
		Global.Form.submit("dataForm","${ctx}/admin/table/doSave",params,function(ret){
			if(ret.rs==true){
				art.dialog({
					content: content,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
			}else{
				$("#_form_token_uniq_id").val(ret.token.token);
	    		art.dialog({
					content: ret.msg,
					width: 200
				});
			}
		}); 
	}
	
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/table/goJqGrid",
			postData:{moduleUrl:"${table.moduleUrl}"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: 'Bootstrap', 
			rowNum:100000000,
			colModel : [
				{name:'ColumnPK',	index:'ColumnPK',	width:100,	label:'字段pk',align:"left",hidden:true},
				{name:'DispSeq',	index:'DispSeq',	width:40,	label:'顺序',	align:"left",hidden:true}, 
				{name:'Descr',	index:'Descr',	width:110,	label:'当前选定的字段',align:"left"},
				{name:'Option',	index:'Option',	width:50,	label:'删除',	align:"center",formatter:delBtn},
			],
			loadonce:true,
			beforeSelectRow: function (rowid, e) {
				return false;
			},
			gridComplete:function(){
				$("#dataTable").jqGrid("resetSelection");
			} 
		});
		$("#dataTable").jqGrid('sortableRows');
		
		$("#frozenNum_span,#isEnable_span").popover({trigger:"hover"}); 
		
		$("#frozenNum").bind("input propertychange", function(){
			var frozenNum = $(this).val();
			var records = $("#dataTable").jqGrid('getGridParam', 'records');
			if(frozenNum > records){
				art.dialog({
					content: "固定列数不能大于总列数",
				});
				frozenNum = records;
				$(this).val(frozenNum);
			}
			$("#frozenNum_span").attr("data-content","前"+frozenNum+"列设为固定列");
		});
	});
	
	function del(rowId){
		var rowData = $("#dataTable").getRowData(rowId);
		$('#'+rowData.ColumnPK).prop("checked", false);
		Global.JqGrid.delRowData("dataTable",rowId);
		
		compareNum();
		
		if(hasChanged == "0"){
			hasChanged = "1";
		}
	}
	
	//比较总列数和选中列数，设置全选框的状态
	function compareNum(){
		var allNums = $("input[name='child']").length;
		var records = $("#dataTable").jqGrid('getGridParam', 'records');
		if(records == 0){
			$("#all").prop("checked",false);
			$("#all").prop("indeterminate", false);
		}else if(allNums == records){
			$("#all").prop("checked",true);
			$("#all").prop("indeterminate", false);
		}else{
			$("#all").prop("indeterminate", true);
			$("#all").prop("checked",false);
		}
	}
	
	function delBtn(v,x,r){
		return "<button type='button' class='btn btn-system btn-xs' style='cursor:pointer'"
				+"onclick='del("+x.rowId+")'>X</button>";
	}
	
	function clickBox(obj){
		var ids=$("#dataTable").jqGrid("getDataIDs");
		var newId=1;
		if(ids.length>0){
			newId=parseInt(ids[ids.length-1],0)+1;
		}
		var columnPk = $(obj).val();
		var descr = $(obj).parent().next("td").text();
		var rowData = {ColumnPK:columnPk,Descr:descr};//保存时候再统一设置顺序
		if ($(obj).is(":checked")){
			$("#dataTable").jqGrid("addRowData", newId++, rowData);
		}else{
			var ids = $("#dataTable").jqGrid("getDataIDs");
			for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable").getRowData(ids[i]);
				if(rowData.ColumnPK == columnPk){
					Global.JqGrid.delRowData("dataTable",ids[i]);
					break;
				}
			}
		}
		
		compareNum();
		
		if(hasChanged == "0"){
			hasChanged = "1";
		}
	}
	
	function changeEnable(obj){
		var isCheck = $(obj).is(":checked");
		if(isCheck){
			$(obj).val("1");
		}else{
			$(obj).val("0");
		}
	}
	
	function selectAll(obj){
		var isChecked = $(obj).is(":checked");
		$("#dataTable").jqGrid("clearGridData");
		if(isChecked){
			$("input[name='child']").prop("checked",true);
			$("input[name='child']").each(function(index, value){
				var rowData = {
					ColumnPK:$(value).val(),
					Descr:$(value).parent().next("td").text()
				};
				$("#dataTable").jqGrid("addRowData", index+1, rowData);
			});
		}else{
			$("input[name='child']").prop("checked",false);
		}
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="saveBut"
						onclick="doSave()">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="doClose(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-system" style="background-color:white;padding-left:1px;">
			<table id="selectAllTable">
				<tbody>
					<tr style="border: none;">
						<td style="width: 20px;border:none;">
							<input type="checkbox" style="margin-top:0px"
							id="all" onclick="selectAll(this)">
						</td>
						<td class="td-value" style="border:none">
							<label class="common-label" style="margin-top:1px;color:#757474" for="all">全选/不选</label>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="rowBox">
			<div style="width:75%;height:460px;float:left;overflow:auto;">
				<div class="query-form" style="padding-top:1px">
					<form action="" method="post" id="page_form">
					<input type="hidden" id="tableCzyMapperPK" value="" >
						<table id="selectTable">
							<tbody>
								${columnHtml}
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<div class="body-box-list" style="margin-top: 0px;width:25%">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" id="tableCzyMapperPk" name="tableCzyMapperPk" value="${tableCzyMapperMap.PK}"/>
					<input type="hidden" id="pk" name="pk" value="${tableCzyMapperMap.TablePK}"/>
					<house:token></house:token>
					<ul class="ul-form">
						<div class="row" >
							<li>
								<label style="width:70px">固定列数</label>
								<input type="text" style="width:50px;" id="frozenNum" name="frozenNum" 
								onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\.\d]/g,'');"
								value="${tableCzyMapperMap.FrozenNum}" />
								<span class="glyphicon glyphicon-question-sign" id = "frozenNum_span" 
									data-container="body"  
									data-content="前${tableCzyMapperMap.FrozenNum}列设为固定列" 
									data-placement="bottom" 
									style="font-size: 15px;color:rgb(25,142,222);margin-left: 0px">
								</span>
							</li>
						</div>
						<div class="row" >
							 <li>
							 	<label style="width:70px">启用模板</label> 
							 	<input type="checkbox" id="isEnable" name="isEnable" onclick="changeEnable(this)"
							 	value="${tableCzyMapperMap.IsEnable}" ${tableCzyMapperMap.IsEnable== "1"?"checked":""}/>
							 </li>
							 <span class="glyphicon glyphicon-question-sign" id = "isEnable_span" 
								data-container="body"  
								data-content="勾选后，显示你配置的字段模板，否则，恢复系统默认的字段模板" 
								data-placement="bottom" 
								style="font-size: 15px;color:rgb(25,142,222);margin-left: 5px;margin-top: 5px">
							 </span>
						</div>
					</ul>
			  </form>
					<!--query-form-->
				<div id="content-list">
					<table id="dataTable"></table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>