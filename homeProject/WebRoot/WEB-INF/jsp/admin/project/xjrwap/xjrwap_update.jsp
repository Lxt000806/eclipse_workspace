<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>巡检任务安排编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
  </head>
  <body>
  	 <div class="body-box-form">
  		<div class="content-form">
  			<div class="panel panel-system" >
   				<div class="panel-body" >
      				<div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="save()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system "   onclick="exitPage()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
  			<div class="infoBox" id="infoBoxDiv"></div>
  			<div class="panel panel-info" >  
			<div class="panel-body" style="vertical-align:middle;margin:-10px;">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
							<li>
								<label>编号</label>
									<input type="text" id="No" name="No" style="width:160px;"   value="${progCheckPlan.no }" readonly="readonly"/>                                             
							</li>
							<li>
								<label>巡检类型</label>
								<house:xtdm id="type" dictCode="CHECKPLANTYPE"  value="${progCheckPlan.type }"  ></house:xtdm>
							</li>	
							<li>
								<label>巡检人</label>
								<input type="text" id="checkCZY" name="checkCZY" style="width:160px;"  value="${progCheckPlan.checkCZY }" />                                             
							</li>
							</div>
							<div class="validate-group row" >
							<li class="form-validate">
								<label><span class="required">*</span>计划巡检日期</label>
								<input type="text" id="crtDate" name="crtDate" class="i-date" 
								onchange="validateRefresh()" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${progCheckPlan.crtDate}' pattern='yyyy-MM-dd'/>"  />
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${progCheckPlan	.remarks }</textarea>
  							</li>
							<li class="search-group">					
								<input type="checkbox"  id="expired" name="expired"
								 value="${progCheckPlan.expired }" onclick="isExpired(this)" 
								 ${progCheckPlan!='F'?'':'checked' } /><p>过期</p>
							</li>
  							</div>
						</ul>
  				</form>
  				</div>
  			</div>
			<div class="btn-panel" >
    			  <div class="btn-group-sm" style="vertical-align:middle;margin-top:-10px;" >
								<button type="button" class="btn btn-system " id="add" >
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system " id="froAdd" >
									<span>从前端导入</span>
								</button>
								<button type="button" class="btn btn-system " id="delDetail">
									<span>删除</span>
									</button>
								<button type="button" class="btn btn-system " onclick="doExcelNow('巡检任务安排明细')" title="导出当前excel数据" >
									<span>导出excel</span>
								</button>
				</div>
			</div>
			<ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">巡检楼盘</a></li>
	   		<li class="nav-checkbox">
		      	<span>前端申请数量：</span>
					<input type="text" id="froNum" name="froNum" style="width:20px; outline:none; background:transparent; 
					border:none"  value="${froNum}" readonly="true"/>条
	      	</li>
	   	
	   	 </ul>
				<div id="content-list">
					<table id= "dataTable"></table>
				</div>	
  		</div>
  	</div> 
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var isClose=1;
function isExpired(obj){
	if ($(obj).is(':checked')){
		$('#expired').val('T');
	}else{
		$('#expired').val('F');
	}
}
function exitPage(){
	if(isClose==0){
		art.dialog({
			 content:"是否保存被更改的数据？",
			 lock: true,
			 width: 180,
			 height: 90,
			 okValue:'是',
			 cancelValue:'否',
			 ok: function () {
				save();
			},
			cancel: function () {
				closeWin();
			}
		});	
	}else{
		closeWin();
	}
}
$(function() {
			$("#page_form").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					crtDate:{  
						validators: {  
							notEmpty: {  
								message: '计划巡检日期不能为空'  
							}
						}  
					},
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});
function save(){
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var param= Global.JqGrid.allToJson("dataTable");
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==''){
			art.dialog({
				content:'明细表为空，不能保存',
			});
			return false;
		}
		Global.Form.submit("page_form","${ctx}/admin/xjrwap/doUpdate",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:'保存成功',
					time:1000,
					beforeunload:function(){
						closeWin();
					}
				});				
			}else{
				art.dialog({
					content:"保存失败",
					width:200
				});
			}
		}); 
}
$(function(){
	$("#checkCZY").openComponent_employee({showValue:'${progCheckPlan.checkCZY}',showLabel:'${progCheckPlan.checkCZYDescr}',readonly:true});
	var num = $.trim($("#froNum").val());
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/xjrwap/goDetailJqGrid",
		postData:{planNo:'${progCheckPlan.no}'},
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		sortable: true,
		colModel : [
			{name: "froNum", index: "froNum", width: 50, label: "froNum", sortable: true, align: "left", hidden: true},
			{name: "pk", index: "pk", width: 50, label: "编号", sortable: true, align: "left", hidden: true},
			{name: "apppk", index: "apppk", width: 50, label: "申请编号", sortable: true, align: "left", hidden: true},
			{name: "address", index: "address", width: 170, label: "楼盘", sortable: true, align: "left",count:true},
			{name: "custcode", index: "custcode", width: 112, label: "客户编号", sortable: true, align: "left",},
			{name: "status", index: "status", width: 115, label: "状态", sortable: true, align: "left",hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 115, label: "状态", sortable: true, align: "left"},
			{name: "appczydescr", index: "appczydescr", width: 105, label: "巡检人员", sortable: true, align: "left"},
			{name: "appdate", index: "appdate", width: 129, label: "巡检日期", sortable: true, align: "left", formatter: formatDate},
			{name: "appczy", index: "appczy", width: 129, label: "申请人", sortable: true, align: "left",hidden:true},
			{name: "checkno", index: "checkno", width: 101, label: "巡检单号", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 101, label: "最后修改时间", sortable: true, align: "left",hidden:true},
			{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后修改人员", sortable: true, align: "left",hidden:true},
			{name: "expired", index: "expired", width: 101, label: "是否过期", sortable: true, align: "left",hidden:true},
			{name: "planno", index: "planno", width: 101, label: "planno", sortable: true, align: "left",hidden:true},
		],
		 gridComplete:function(){
			var Ids =$("#dataTable").getDataIDs();
			if(Ids!=""){
				$("#type").attr('disabled','disabled')
			}	
	      },
	    loadonce: true,
	};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/xjrwap/goDetailJqGrid",}).trigger("reloadGrid");
	}
	
	$("#add").on("click",function(){
		var checkCZY = $.trim($("#checkCZY").val());
		var type = $.trim($("#type").val());
		if(checkCZY ==''){
			art.dialog({content: "请选择巡检人",width: 200});
			return false;
		}
		if(type ==''){
			art.dialog({content: "请选择巡检类型",width: 200});
			return false;
		}
		
		var custcode = Global.JqGrid.allToJson("dataTable","custcode");
		var arr = new Array();
			arr = custcode.fieldJson.split(",");
			
		Global.Dialog.showDialog("save",{
			  title:"巡检楼盘——增加",
			  url:"${ctx}/admin/xjrwap/goAdd",
			  postData:{custCodes:arr,type:type,czybh:checkCZY},
			  height: 680,
			  width:1250,
			    returnFun:function(data){
				  if(data){
					 $.each(data,function(k,v){
						var json = {
							address:v.address,
							custcode:v.code,
							status:'1',
							statusdescr:'待执行',
						};
						Global.JqGrid.addRowData("dataTable",json);
						$("#checkCZY").setComponent_employee({readonly:true});
					  	$("#type").attr('disabled','disabled')
					});
				  }
			  } 
		 });
	});
	
	
	
	//从前端导入
	$("#froAdd").on("click",function(){
		var checkCZY = $.trim($("#checkCZY").val());
		var type = $.trim($("#type").val());
		if(checkCZY ==''){
			art.dialog({content: "请选择巡检人",width: 200});
			return false;
		}
		if(type ==''){
			art.dialog({content: "请选择巡检类型",width: 200});
			return false;
		}
		
		var pk = Global.JqGrid.allToJson("dataTable","pk");
		var arr = new Array();
			arr = pk.fieldJson.split(",");
		Global.Dialog.showDialog("froAdd",{
			  title:"巡检楼盘——增加",
			  url:"${ctx}/admin/xjrwap/goFroAdd",
			  postData:{arr:arr},
			  height: 680,
			  width:1250,
			    returnFun:function(data){
				  if(data){
					 $.each(data,function(k,v){
						var json = {
						 	froNum:1,
							apppk:v.pk,
							address:v.address,
							custcode:v.custcode,
							status:'1',
							statusdescr:'待执行',
						};
						isClose=0;
						var a;
					  	$("#froNum").val($("#froNum").val()-1);
						Global.JqGrid.addRowData("dataTable",json);
						$("#checkCZY").setComponent_employee({readonly:true});
					  	$("#type").attr('disabled','disabled')
					});
				  }
			  } 
		 });
	});
	
	$("#delDetail").on("click",function(){
		isClose=0;
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		var DelIds =$("#dataTable").getDataIDs();
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		var x=0;
		for( var i=0;i<DelIds.length;i++){
			if(DelIds[i]==id){
				x=i;
			}
		}
		Global.JqGrid.delRowData("dataTable",id);
		var froNum = Global.JqGrid.allToJson("dataTable","froNum");
			arr = froNum.fieldJson.split(",");
			var s=0;
			for(var i=0;i<arr.length;i++){
				if(arr[i]==''){
					arr[i]=0;
				}
				s=s+parseInt(arr[i]);
			}
		$('#froNum').val(parseInt(num)-parseInt(s));
		var Ids =$("#dataTable").getDataIDs();
		if(Ids.length>x){
            $("#dataTable").jqGrid('setSelection',Ids[x], true);
		}else{
		    $("#dataTable").jqGrid('setSelection',Ids[Ids.length-1], true);
		}
		if(Ids==0){
			$("#type").removeAttr("disabled");
			$("#checkCZY").setComponent_employee({readonly:false});
		}	
	
	});
});
function validateRefresh(){
		$('#page_form').data('bootstrapValidator')
                    .updateStatus('crtDate', 'NOT_VALIDATED',null)  
                    .validateField('crtDate')  ;
}
</script>
  </body>
</html>

















