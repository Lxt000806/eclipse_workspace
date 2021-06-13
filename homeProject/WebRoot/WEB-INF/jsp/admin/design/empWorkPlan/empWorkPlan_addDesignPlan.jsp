<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<!DOCTYPE html>
<html>
<head>
	<title>新增设计计划</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
$(function() {
	$("#weekType").val("${empWorkPlan.weekType}").attr("disabled",true);
	Global.JqGrid.initEditJqGrid("dataTable",{
		height:300,		
		datatype: 'local',
		colModel : [		
	      {name : 'date',index : 'date',width : 80,label:'日期',sortable : true,align : "left",formatter: formatDate},
	      {name : 'week',index : 'week',width : 80,label:'星期',sortable : true,align : "left",hidden:true},
	      {name : 'weekdescr',index : 'weekdescr',width : 80,label:'星期',sortable : true,align : "left"},
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

function reloadJqGrid(){
	var week=0;
	var weekType=$("#weekType").val();
	if(weekType=="2"){
		week=7;
	}
	var planBeginDate=getAddDate($("#oldPlanBeginDate").val(), week);
	$("#planBeginDate").val(planBeginDate);
	var dataList = [
       {"id": "1", "date": planBeginDate, "week": "0","weekdescr": "星期一", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "2", "date": getAddDate(planBeginDate,1), "week": "1","weekdescr": "星期二", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "3", "date": getAddDate(planBeginDate,2), "week": "2","weekdescr": "星期三", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "4", "date": getAddDate(planBeginDate,3), "week": "3","weekdescr": "星期四", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "5", "date": getAddDate(planBeginDate,4), "week": "4","weekdescr": "星期五", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "6", "date": getAddDate(planBeginDate,5), "week": "5","weekdescr": "星期六", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
       {"id": "7", "date": getAddDate(planBeginDate,6), "week": "6","weekdescr": "星期日", "planmeasure": "0", "planarrive": "0", "planset": "0", "plansign": "0"},
	];
	$("#dataTable").setGridParam({data: dataList}).trigger('reloadGrid');
}
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
						<input type="hidden" id="m_umState" name="m_umState" value="A" />
						<input type="hidden" id="planBeginDate" name="planBeginDate" class="i-date" value="<fmt:formatDate value='${empWorkPlan.planBeginDate}' pattern='yyyy-MM-dd'/>" />
						<input type="hidden" id="oldPlanBeginDate" name="oldPlanBeginDate" class="i-date" value="<fmt:formatDate value='${empWorkPlan.planBeginDate}' pattern='yyyy-MM-dd'/>" />
						<house:token></house:token>
						<ul class="ul-form">
							<div class="form-validate row">
								<li class="form-validate">
									<label >计划人类型</label>
									<house:xtdm id="planCzyType" dictCode="PLANCZYTYPE" value="2" disabled="true"></house:xtdm>                     
								</li>
								<li class="form-validate">
									<label >计划周</label>
									<select id="weekType">
										<option value="1">本周</option>
										<option value="2">下周</option>
									</select>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
 		<div class="container-fluid">
			<ul class="nav nav-tabs">
				<li id="tabsalesInvoice" class="active"><a href="#tab_detail"
					data-toggle="tab">设计计划明细</a></li>
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
