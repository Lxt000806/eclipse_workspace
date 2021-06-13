<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加客户接触跟踪信息</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	//验证
	validateRefresh('conDate');
	if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:'${ctx}/admin/ResrCust/doSaveCon',
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
	    				closeWin(false);
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
}

//校验函数
$(function() {
	$("#conMan").openComponent_employee({showLabel:"${resrCust.businessManDescr}",showValue:"${resrCust.businessMan}",readonly:true});
	  $("#dataForm").bootstrapValidator({
            message : 'This value is not valid',
            feedbackIcons : {/*input状态样式图片*/
                validating : 'glyphicon glyphicon-refresh'
            },
            fields: {  
	       remarks:{
                validators:{
                    notEmpty: {
                        message: '跟踪内容不能为空'
                    },
                    stringLength : {
                        min :'${minLength}',
                        message : '跟踪内容长度不能小于'+'${minLength}'
                    }
                }
            },
    	},
            submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
        }).on('success.form.bv', function (e) {
   		 e.preventDefault();
   		 save();
   		
	});
	
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goConJqGrid",
		postData:{code:"${resrCust.code}"},
		height:255,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
			{name:'custcode',	index:'custcode',	width:85,	label:'客户编号',	sortable:true,align:"left",},
			{name:'typedescr',	index:'typedescr',	width:90,	label:'跟踪类型',	sortable:true,align:"left",},
			{name:'conmandescr',	index:'conmandescr',	width:90,	label:'跟踪人',	sortable:true,align:"left",},
			{name:'condate',	index:'condate',	width:140,	label:'跟踪日期',	sortable:true,align:"left",formatter: formatTime},
			{name:'nextcondate',	index:'nextcondate',	width:95,	label:'下次联系时间',	sortable:true,align:"left",formatter: formatDate},
			{name:'conwaydescr',	index:'conwaydescr',	width:80,	label:'跟踪方式',	sortable:true,align:"left",},
			{name:'remarks',	index:'remarks',	width:400,	label:'跟踪说明',	sortable:true,align:"left",},
			{name:'callrecordpath',	index:'callrecordpath',	width:80,	label:'功能',	sortable:true,align:"left",formatter: formatBtns}
		],
	});	
});
	
function formatBtns(value, selectInfo) {
	if(value && value != "") {
		return "<a onclick=\"downloadFileView('" + selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">下载</a>&nbsp;&nbsp;<a onclick=\"listenCallRecoardView('" 
				+ selectInfo.gid + "', " + selectInfo.rowId + ", '" + "${ossCdnAccessUrl}" + value + "')\">播放</a>";
	}
	return "";
}

function downloadFileView(dataTable, rowId, path) {
 	$("#downloadElem")[0].href = path;
	$("#downloadElem")[0].click(); 
}

function listenCallRecoardView(dataTable, rowId, path) {
	Global.Dialog.showDialog("CallRecord", {
		title:"录音",
		url: "${ctx}/admin/callRecord/goView",
		postData: {
			path: path
		},
		height:150,
		width:600,
	});
}
	
</script>

</head>
    
<body>
<a id="downloadElem" download style="display: none"></a>
<div class="body-box-form" >
<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
      <button type="button" id="saveBtn" class="btn btn-system "   onclick="validateDataForm()">保存</button>
      <button type="button" class="btn btn-system "   onclick="closeWin(false)">关闭</button>
      </div>
   </div>
	</div>
	 <div class="panel panel-info" style="height:100%">  
                <div class="panel-body">
                    <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
							<house:token></house:token>
							<input type="hidden" name="m_umState" id="m_umState" value="A"/>
						<ul class="ul-form">
							<div class="validate-group">
								<input type="hidden" name="resrCustCode" id="resrCustCode" value="${resrCust.code}"/>
								<input type="hidden" name="type" id="type" value="3"/>
								<li class="form-validate" >
									<label>跟踪时间 </label>
									<input type="text" id="conDate" name="conDate" class="i-date" value="<fmt:formatDate value='${resrCust.lastUpdate}' pattern='yyyy-MM-dd HH:mm:ss'/>" readonly />
								</li>
								<li class="form-validate">
									<label>跟踪人 </label>
									<input type="text" id="conMan" name="conMan"  />
								</li>
							</div>
							<div class="validate-group">
								<li class="form-validate" >
									<label>下次联系时间 </label>
									<input type="text" id="nextConDate" name="nextConDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
									value="<fmt:formatDate value='${resrCust.nextConDate}' pattern='yyyy-MM-dd'/>"  />
								</li>
								<li>
									<label>跟踪方式</label>
									<house:xtdm id="conWay" dictCode="CONWAY" value="${resrCust.conWay}"></house:xtdm>                     
								</li>
							</div>
							<div class="validate-group ">
								<li  class="form-validate">
								<label class="control-textarea"><span class="required">*</span>跟踪内容 </label>
									<textarea  id="remarks" name="remarks"  rows="3"></textarea>
								</li>
							</div>
						</ul>   
		            </form>
                </div>  
        </div>  
        <ul class="nav nav-tabs" >
	      	<li class="active"><a data-toggle="tab">跟踪日志</a></li>
	   	</ul>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
</div>
</body>
</html>
