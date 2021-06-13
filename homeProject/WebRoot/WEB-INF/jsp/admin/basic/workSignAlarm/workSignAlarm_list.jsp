<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>工人签到提醒列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("workSignAlarmAdd",{
		  title:"添加工人签到提醒",
		  url:"${ctx}/admin/workSignAlarm/goSave",
		  height: 500,
		  width:800,
		  returnFun: goto_query
		});
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("workSignAlarmUpdate",{
		  title:"修改工人签到提醒",
		  url:"${ctx}/admin/workSignAlarm/goUpdate?id="+ret.pk,
		  height:500,
		  width:800,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("workSignAlarmView",{
		  title:"查看工人签到提醒",
		  url:"${ctx}/admin/workSignAlarm/goDetail?id="+ret.pk,
		  height:400,
		  width:800
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function del(){
	var ret = selectDataTableRow();
    if (ret) {
    	art.dialog({
    		content:"确认删除该记录",
    		ok:function(){
    			$.ajax({
    				url:"${ctx}/admin/workSignAlarm/doDelete?pk="+ret.pk,
    				type:"post",
    				dataType:"json",
    				cache:false,
    				error:function(obj){
    					art.dialog({
    						content:"删除出错,请重试",							
    						time: 1000,
							beforeunload: function () {
								goto_query();
							}
    					});
    				},
    				success:function(obj){
    					if(obj.rs){
							goto_query();
						}else{
							$("#_form_token_uniq_id").val(obj.token.token);
							art.dialog({
								content: obj.msg,
								width: 200
							});
						}
    				}
    			});
    		},
    		cancel:function(){
    			goto_query();
    		}
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/workSignAlarm/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/workSignAlarm/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-90,
        	styleUI: 'Bootstrap',
			colModel : [
			  {name : 'pk',index : 'pk',width : 50,label:'pk',sortable : true,align : "left",key : true,hidden:true},
		      {name : 'prjitem2',index : 'prjitem2',width : 100,label:'施工项目2',sortable : true,align : "left",hidden:true},
		      {name : 'iscomplete',index : 'iscomplete',width : 100,label:'阶段是否完成',sortable : true,align : "left",hidden:true},
		      {name : 'isneedreq',index : 'isneedreq',width : 100,label:'是否判断需求',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype1',index : 'itemtype1',width : 100,label:'材料类型1',sortable : true,align : "left",hidden:true},
		      {name : 'itemtype2',index : 'itemtype2',width : 100,label:'材料类型2',sortable : true,align : "left",hidden:true},
		      {name : 'jobtype',index : 'jobtype',width : 100,label:'任务类型',sortable : true,align : "left",hidden:true},
		      {name : 'confirmprjitem',index : 'confirmprjitem',width : 100,label:'验收节点',sortable : true,align : "left",hidden:true},
		      {name : 'prjitem2descr',index : 'prjitem2descr',width : 100,label:'施工项目2',sortable : true,align : "left"},
		      {name : 'typedescr',index : 'typedescr',width : 80,label:'提醒类型',sortable : true,align : "left"},
		      {name : 'iscompletedescr',index : 'iscompletedescr',width : 100,label:'阶段是否完成',sortable : true,align : "left"},
		      {name : 'isneedreqdescr',index : 'isneedreqdescr',width : 100,label:'是否判断需求',sortable : true,align : "left"},
		      {name : 'itemtype1descr',index : 'itemtype1descr',width : 100,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'itemtype2descr',index : 'itemtype2descr',width : 100,label:'材料类型2',sortable : true,align : "left"},
		      {name : 'jobtypedescr',index : 'jobtypedescr',width : 100,label:'任务类型',sortable : true,align : "left"},
		      {name : 'notexistoffworktype12descr',index : 'notexistoffworktype12descr',width : 120,label:'不存在人工工种',sortable : true,align : "left"},
		      {name : 'confirmprjitemdescr',index : 'confirmprjitemdescr',width : 100,label:'验收节点',sortable : true,align : "left"},
		      {name : 'workerclassifydescr',index : 'workerclassifydescr',width : 100,label:'工人分类',sortable : true,align : "left"},
		      {name : 'lastupdate',index : 'lastupdate',width : 100,label:'最后跟新时间',sortable : true,align : "left", formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后跟新人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作日志',sortable : true,align : "left"}
            ]
		});
   		
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>施工项目2</label>
						<house:dict id="prjItem2" dictCode="" 
							sql="select a.Code,a.code+' '+a.descr descr  from tPrjItem2 a  where  a.Expired='F' order By Seq " 
							sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.prjItem2}"  >
						</house:dict>
					</li>
					<li>
						<label>阶段是否完成</label> 
						<house:xtdm id="isComplete" dictCode="YESNO" value="${workSignAlarm.isComplete}"></house:xtdm>
					</li>
					<li class="form-validate">
								<label>任务类型</label>
								<house:dict id="jobType" dictCode="" 
										sql="select a.Code,a.code+' '+a.descr descr  from  tJobType a  where  a.Expired='F' " 
										sqlValueKey="Code" sqlLableKey="Descr" value="${workSignAlarm.jobType}"  >
								</house:dict>
					</li>
					<li class="form-validate">
                        <label>提醒类型</label> 
                        <house:xtdm id="type" dictCode="WORKALARMTYPE" value="${workSignAlarm.type}"></house:xtdm>
                    </li>
					<li class="search-group">
						<input type="checkbox" id="expired" name="expired"
										value="${workSignAlarm.expired}" onclick="hideExpired(this)"
										${workSignAlarm.expired!='T' ?'checked':'' }/><p>隐藏过期</p>  
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>
		
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
					<button type="button" class="btn btn-system " onclick="update()">编辑</button>
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
					<button id="btnDel" type="button" class="btn btn-system " onclick="del()">删除</button>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>

			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
