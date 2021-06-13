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
<title>工资出账处理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}"
	type="text/javascript"></script>
<script type="text/javascript"> 
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/workCostDetail/goMemberJqGrid",
			postData:{no:"${workCost.no}"},
		    rowNum:10000000,
			height:400,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "namechi", index: "namechi", width: 70, label: "成员户名", sortable: true, align: "left"},
				{name: "idnum", index: "idnum", width: 150, label: "身份证号", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 150, label: "成员卡号", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 80, label: "发卡行", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 90, label: "本次发放金额", sortable: true, align: "right", sum: true},
				{name: "cashamount", index: "cashamount", width: 90, label: "现金发放金额", sortable: true, align: "right", sum: true,editable:true, editrules: {number:true,required:true}},
				{name: "cmpamount", index: "cmpamount", width: 90, label: "公司发放金额", sortable: true, align: "right", sum: true,editable:true, editrules: {number:true,required:true}},
				{name: "laborcmpdescr", index: "laborcmpdescr", width: 80, label: "劳务公司", sortable: true, align: "left",editable: true, 
				edittype:"select",
					editoptions:{ 
			  			dataUrl: '${ctx}/admin/workCostDetail/getLaborCompny',
						buildSelect: function(e){
							var lists = JSON.parse(e);
							var html = "<option value=\"\" ></option>";
							for(var i = 0; lists && i < lists.length; i++){
								html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
							}
							return "<select style=\"font-family:宋体;font-size:12px\"> " + html + "</select>";
						},
			  			dataEvents:[{
		  					type:'change',
		  					fn:function(e){ 
		  						updateWorker(e);
		  					}
			  			}, 
			  		]}
		 		},
				{name: "thisyearprovidedamount", index: "thisyearprovidedamount", width: 90, label: "本年劳务已发放金额", sortable: true, align: "right", sum: true},
				{name: "thisyearunprovideamount", index: "thisyearunprovidamount", width: 90, label: "本年可发放金额", sortable: true, align: "right", sum: true},
				{name: "thismonprovidedamount", index: "thismonprovidedamount", width: 90, label: "当月劳务已发放金额", sortable: true, align: "right", sum: true},
				{name: "groupleadername", index: "groupleadername", width: 80, label: "班组长姓名", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 60, label: "工种", sortable: true, align: "left"},
				{name: "worktype12", index: "worktype12", width: 60, label: "工种", sortable: true, align: "left",hidden:true},
				{name: "workercode", index: "workercode", width: 60, label: "班组编号", sortable: true, align: "left",hidden:true},
				{name: "code", index: "code", width: 60, label: "班组成员编号", sortable: true, align: "left",hidden:true},
				{name: "laborcmpcode", index: "laborcmpcode", width: 80, label: "劳务公司编号", sortable: true, align: "left" ,hidden:true},
				],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#memberDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("memberDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("memberDataTable", v);
					});
				}, 
			beforeSelectRow: function (id) {
	            setTimeout(function () {
	                relocate(id, 'memberDataTable');
	            }, 100);
	        },
		    afterSaveCell:function(id,name,val,iRow,iCol){
              var rowData = $("#memberDataTable").jqGrid('getRowData',id);
              if(name=='cashamount'){
              	  rowData.cmpamount=parseFloat(rowData.amount)-parseFloat(rowData.cashamount);
              }else if(name=='cmpamount'){
                  rowData.cashamount=parseFloat(rowData.amount)-parseFloat(rowData.cmpamount);
              }
              $("#memberDataTable").setRowData(id, rowData);
           },
 		};
       //初始化班组成员工资明细
	   Global.JqGrid.initEditJqGrid("memberDataTable",gridOption);
	   
	   $("#getMemSalary").on("click",function(){
	   	art.dialog({
			content:"将覆盖原有数据，是否确认生成？",
			lock: true,
			ok: function () {
				$.ajax({
				     url : '${ctx}/admin/workCostDetail/getMemberSalary',
				     type : 'post',
				     data : {
				          'no' : "${workCost.no}"
				     },
				     dataType : 'json',
				     cache : false,
				     error : function(obj) {
				          showAjaxHtml({
				                "hidden" : false,
				                "msg" : '保存数据出错~'
				          });
				     },
				     success : function(datas) {
				     	Global.JqGrid.clearJqGrid("memberDataTable"); 
			   			$.each(datas,function(k,v){
							Global.JqGrid.addRowData("memberDataTable", v);
						});
				     }
			   });
				return true;
			},
			cancel: function () {
				return true;
			}
		});
	   });
	   
	   $("#saveBtn").on("click",function(){
	   		var rets = $("#memberDataTable").jqGrid("getRowData");
			var params = {"workCostDetailJson": JSON.stringify(rets)};
			Global.Form.submit("page_form","${ctx}/admin/workCostDetail/doWorkerCostProvide",params,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
	   });
});

//劳务公司下拉框修改触发
function updateWorker(e){
	var rowid = $("#memberDataTable").getGridParam("selrow");
    $("#memberDataTable").jqGrid('setCell',rowid,'laborcmpcode',$(e.target).val()); 
}

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system " id="saveBtn">
						<span>保存</span>
					</button>
					<button type="button" class="btn btn-system " id="getMemSalary">
						<span>生成组员工资</span>
					</button>
					<button type="button" class="btn btn-system " id="closeBut"
						onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<form action="" method="post" id="page_form" class="form-search">
			<input type="hidden" name="no" id="no" value="${workCost.no }" />
		</form>
		<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">班组成员工资明细</a></li>
	   	</ul>
		<div class="pageContent">
			<div id="content-list">
				<table id="memberDataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>
