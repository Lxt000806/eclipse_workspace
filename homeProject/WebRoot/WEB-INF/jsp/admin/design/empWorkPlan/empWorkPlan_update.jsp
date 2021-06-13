<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>编辑计划</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	Global.JqGrid.initEditJqGrid("dataTable",{
		url:"${ctx}/admin/empWorkPlan/goDetailJqGrid",
		postData:$("#dataForm").jsonForm(),
		height:300,		
		colModel : [		
	      {name : 'date',index : 'date',width : 80,label:'日期',sortable : true,align : "left",formatter: formatDate},
	      {name : 'week',index : 'week',width : 80,label:'星期',sortable : true,align : "left",hidden:true},
	      {name : 'weekdescr',index : 'weekdescr',width : 80,label:'星期',sortable : true,align : "left"},
	      {name : 'planaddcust',index : 'planaddcust',width : 120,label:'计划添加客户数',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false},	
	      {name : 'plancontactcust',index : 'plancontactcust',width : 120,label:'计划联系客户数',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true,hidden:"${empWorkPlan.planCzyType}".trim()=="2"?true:false},	
	      {name : 'planmeasure',index : 'planmeasure',width : 80,label:'计划量房',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true},	
	      {name : 'planarrive',index : 'planarrive',width : 80,label:'计划来客',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true},	
	      {name : 'planset',index : 'planset',width : 80,label:'计划下定',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true},
	      {name : 'plansign',index : 'plansign',width : 80,label:'计划签单',sortable : true,align : "right",editable:true,edittype:'text',editrules:{number:true,required:true},sum:true},		 
        ],
        beforeSelectRow:function(id){
     	    setTimeout(function(){
        			relocate(id);
       	    },100);
        },
        gridComplete:function(){
        	var nowDate=formatDate(new Date());
        	var ids = $("#dataTable").getDataIDs();
			for(var i=0;i<ids.length;i++){
				var rowData = $("#dataTable").getRowData(ids[i]);
				if(rowData.date<nowDate){//日期小于今天的，设置灰色不可选
					$("#dataTable #"+ids[i]).find("td").css({
						"background":"#bbb"
					});
					//单元格不可编辑
					$("#dataTable").jqGrid('setCell', ids[i], 'planaddcust', '', 'not-editable-cell');
					$("#dataTable").jqGrid('setCell', ids[i], 'plancontactcust', '', 'not-editable-cell');
					$("#dataTable").jqGrid('setCell', ids[i], 'planmeasure', '', 'not-editable-cell');
					$("#dataTable").jqGrid('setCell', ids[i], 'planarrive', '', 'not-editable-cell');
					$("#dataTable").jqGrid('setCell', ids[i], 'planset', '', 'not-editable-cell');
					$("#dataTable").jqGrid('setCell', ids[i], 'plansign', '', 'not-editable-cell');
				}
			}
        }
	});
	 reloadJqGrid();
});
		
function save(){
	var rets = $("#dataTable").jqGrid("getRowData");
	var params = {"empWorkPlanDetailJson": JSON.stringify(rets)};
	Global.Form.submit("dataForm","${ctx}/admin/empWorkPlan/doSave",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
			    	closeWin();
				}
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
			 art.dialog({
				content: ret.msg,
			});
		}
					
	}); 
};

</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system "
						onclick="save()">保存</button>
					<button type="button" class="btn btn-system "
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<div class="body-box-form">
					<form role="form" class="form-search" id="dataForm" action=""
						method="post" target="targetFrame">
						<input type="hidden" id="m_umState" name="m_umState" value="M" />
						<input type="hidden" id="pk" name="pk" value="${empWorkPlan.pk}" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="form-validate row">
								<li class="form-validate">
									<label >计划人类型</label>
									<house:xtdm id="planCzyType" dictCode="PLANCZYTYPE" value="${empWorkPlan.planCzyType}" disabled="true"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label >计划开始时间</label>
									<input type="text" id="planBeginDate" name="planBeginDate" class="i-date" value="<fmt:formatDate value='${empWorkPlan.planBeginDate}' pattern='yyyy-MM-dd'/>" readonly/>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
 		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<c:if test="${empWorkPlan.planCzyType.trim()=='2'}">
					<li id="tabsalesInvoice" class="active"><a href="#tab_detail"
						data-toggle="tab">设计计划明细</a></li>
				</c:if>
				<c:if test="${empWorkPlan.planCzyType.trim()=='1'}">
					<li id="tabsalesInvoice" class="active"><a href="#tab_detail"
						data-toggle="tab">业务计划明细</a></li>
				</c:if>
			</ul>
			 <div class="tab-content">
				<div id="tab_detail" class="tab-pane fade in active">
					<div id="content-list">
						<table id="dataTable"></table>
					</div>
				</div>
			</div> 
		</div> 
		<script type="text/javascript">    
</script>
</body>
</html>
