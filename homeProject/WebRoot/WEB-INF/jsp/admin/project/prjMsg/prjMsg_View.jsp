<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>施工任务监控</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var gridOption ={
		url:"${ctx}/admin/prjMsg/goReasonJqGrid",
		postData:{pk:"${personMessage.pk}"},
		height:180,
		rowNum : 10000000,
		styleUI:"Bootstrap",
		colModel : [
			{name: "date", index: "date", width: 125, label: "调整时间", sortable: true, align: "left", formatter: formatTime},
			{name: "czydescr", index: "czydescr", width: 70, label: "调整人员", sortable: true, align: "right"},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "操作人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作代码", sortable: true, align: "left"}
		]
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
});
function save(){	
	$("#dataForm").bootstrapValidator('validate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/prjMsg/doSave',
		type: 'post',
		data: datas,
		dataType: 'json',
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
	    },
	    success: function(obj){
	    	if(obj.rs){
	    		art.dialog({
					content: obj.msg,
					time: 1000,
					beforeunload: function () {
	    				closeWin();
				    }
				});
	    	}else{
				$("#_form_token_uniq_id").val(obj.token.token);
				art.dialog({
					content: obj.msg,
					width: 200
				});
	    	}
	    }
	 });
};
</script>
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address"  value="${personMessage.address }" readonly="true"  />
					</li>
					<li>
					<label >部门</label>
						<input type="text" id="department2descr" name="department2descr"  value="${personMessage.department2Descr }" readonly="true"  />
					</li>
					<li>
						<label>执行人</label>
						<input type="text" id="rcvCzy" name="rcvCzy" style="width:79px;"  value="${personMessage.rcvCzy }"  readonly="true" />
						<input type="text" id="rcvCzyDescr" name="rcvCzyDescr" style="width:79px;"  value="${personMessage.rcvCzyDescr }"  readonly="true" />
					</li>
					<li>	
						<label >是否超时</label>
						<input type="text" id="deal" name="deal"    Value="${personMessage.deal}" readonly="true" />
					</li>
					<li>	
					<label >状态</label>
						<house:xtdm  id="RcvStatus" dictCode="PERSRCVSTATUS"    value="${personMessage.rcvStatus}" disabled="true" ></house:xtdm>
					</li>
					<li>	
						<label>触发日期</label>
						<input type="text" id="sendDate" name="sendDate" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${personMessage.sendDate}' pattern='yyyy-MM-dd'/>"  disabled="true"/>	
					</li>
					<li>	
						<label>处理日期</label>
						<input type="text" id="revDate" name="revDate"    Value="${personMessage.rcvDate}"  readonly="true"/>
					</li>
					<li>	
						<label>计划处理日期</label>
						<input type="text" id="planDealDate" name="planDealDate" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${personMessage.planDealDate}' pattern='yyyy-MM-dd'/>"  disabled="true"/>	
					</li>
					<li>	
						<label>计划完成日期</label>
						<input type="text" id="planArrDate" name="planArrDate" class="i-date"  
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${personMessage.planArrDate}' pattern='yyyy-MM-dd'/>"  disabled="true"/>	
					</li>
					<li>	
						<label>标题</label>
							<input type="text" id="title" name="title"  value="${personMessage.title}" readonly="true"/>
					</li>
					<li hidden="true">
						<input type="text" id="pk" name="pk"  value="${personMessage.pk}" readonly="true"/>
					</li>
				</ul>
				<ul class="ul-form">			
					<li class="form-validate">
						<label class="control-textarea" >任务描述：</label>
						<textarea id="remarks" name="remarks" readonly="true">${personMessage.msgText }</textarea>
					</li>	
				</ul>
			</form>
		</div>
	</div>
</div>
<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="#reason" data-toggle="tab">延后记录</a>
			</li>
			<li class="">
				<a href="#msgByWorkType12" data-toggle="tab">同工种已触发任务</a>
			</li>
		</ul>
		<div class="tab-content">
			<div id="reason" class="tab-pane active">
				<div class="pageContent">
					<div class="body-box-list" style="margin-top: 5px;">
						<table id="dataTable"></table>
					</div>
				</div>
			</div>
			<div id="msgByWorkType12" class="tab-pane fade "> 
	         	<jsp:include page="msgByWorkType12.jsp"></jsp:include>
	        </div> 
		</div>
	</div> 
</body>
</html>


