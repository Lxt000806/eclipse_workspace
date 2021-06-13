<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>任务管理</title>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
		<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
		<META HTTP-EQUIV="expires" CONTENT="0"/>
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
		<%@ include file="/commons/jsp/common.jsp"%>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<style type="text/css">
			.checkbox-label {
				text-align: left !important;
				margin-left: -3px !important;
			}
		</style>
	<script type="text/javascript">
    /**初始化表格*/
    var ids=0;
    $(function () {
      //初始化材料类型1，2，3三级联动
    	Global.LinkSelect.initSelect("${ctx}/admin/prjJobManage/prjTypeByItemType1Auth", "itemType1", "jobType");
    	
    	
        Global.JqGrid.initJqGrid("dataTable", {
	        height: $(document).height() - $("#content-list").offset().top - 70,
			colModel : [
				{name: "no", index: "no", width: 67, label: "编号", sortable: true, align: "left",frozen:true},
				{name: "statusdescr", index: "statusdescr", width: 47, label: "状态", sortable: true, align: "left",frozen:true},
				{name: "address", index: "address", width: 138, label: "楼盘地址", sortable: true, align: "left",frozen:true},
				{name: "regiondescr", index: "regiondescr", width: 60, label: "区域", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 70, label: "客户类型", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 79, label: "材料类型1", sortable: true, align: "left"},
				{name: "jobtypedescr", index: "jobtypedescr", width: 109, label: "任务类型", sortable: true, align: "left"},
				{name: "appczydescr", index: "appczydescr", width: 65, label: "申请人", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 103, label: "电话", sortable: true, align: "left"},
				{name: "appdepartment2", index: "appdepartment2", width: 87, label: "工程部", sortable: true, align: "left"},
				{name: "designdepartment1", index: "designdepartment1", width: 77, label: "事业部", sortable: true, align: "left"},
				{name: "date", index: "date", width: 94, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "remarks", index: "remarks", width: 223, label: "备注", sortable: true, align: "left"},
				{name: "suppldescr", index:"suppldescr", width:80, label:"供应商", sortable:true, align:"left"},
				{name: "supplremarks", index:"supplremarks", width1:80, label:"供应商备注", sortable:true, align:"left"},
				{name: "dealczy", index: "dealczy", width: 87, label: "处理人编码", sortable: true, align: "left"},
				{name: "dealczydescr", index: "dealczydescr", width: 65, label: "处理人", sortable: true, align: "left"},
				{name: "plandate", index: "plandate", width: 102, label: "计划处理时间", sortable: true, align: "left", formatter: formatDate},
				{name: "dealdate", index: "dealdate", width: 100, label: "实际处理时间", sortable: true, align: "left", formatter: formatTime},
				{name: "enddescr", index: "enddescr", width: 119, label: "处理结果", sortable: true, align: "left"},
				{name: "warbranddescr", index: "warbranddescr", width: 111, label: "衣柜", sortable: true, align: "left"},
				{name: "cupbranddescr", index: "cupbranddescr", width: 115, label: "橱柜", sortable: true, align: "left"},
				{name: "suppldescr", index: "suppldescr", width: 98, label: "供应商", sortable: true, align: "left"},
				{name: "dealremark", index: "dealremark", width: 159, label: "处理说明", sortable: true, align: "left"},
				{name: "jcdesign", index: "jcdesign", width: 85, label: "集成设计师", sortable: true, align: "left"},
				{name: "jcdepartment2", index: "jcdepartment2", width: 78, label: "集成部", sortable: true, align: "left"},
				{name: "mainmandescr", index: "mainmandescr", width: 70, label: "主材管家", sortable: true, align: "left"},
				{name: "errposidescr", index: "errposidescr", width: 78, label: "定位异常", sortable: true, align: "left"},
				{name: "gpsaddress", index: "gpsaddress", width: 194, label: "定位地点", sortable: true, align: "left",},
				{name: "lastupdate", index: "lastupdate", width: 103, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 75, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 77, label: "操作日志", sortable: true, align: "left"}
			],
			ondblClickRow: function(){
				view();
			},
			loadComplete: function(){
             	$('.ui-jqgrid-bdiv').scrollTop((ids-1)*($("#1").height()+1));
		        $("#dataTable").setSelection(ids);
          	  	ids=0;
            },
            gridComplete:function(){
	            frozenHeightReset("dataTable");
			},
		});
		$("#dealCzy").openComponent_employee(({showLabel:'${uc.zwxm}',showValue:'${uc.czybh}'}));
		$("#appCzy").openComponent_employee();
		$("#dataTable").jqGrid("setFrozenColumns");
				
    });
    function doExcel() {
	    var url = "${ctx}/admin/prjJobManage/doExcel";
	    doExcelAll(url);
    }
    function goto_query() {
    	var postData = $("#page_form").jsonForm();
        $("#dataTable").jqGrid("setGridParam", {
	        url: "${ctx}/admin/prjJobManage/goJqGrid",
	        postData: postData,
	        page: 1,
	        sortname: ''
    	}).trigger("reloadGrid");
    }
    function receive() {
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.statusdescr!="提交"&&ret.statusdescr!="指派"){
	    		art.dialog({
	        		content: "该任务单不在提交，或指派状态，无法进行接收操作！"
	        	});
	        	return;
	    	}
	    	if(ret.dealczy!=""&&ret.dealczy!="${uc.czybh}".trim()){
	    		art.dialog({
	        		content: "只有处理人为空，或者为当前操作员的任务单才能进行接收操作！"
	        	});
	        	return;
	    	}
	    	Global.Dialog.showDialog("prjJobReceive", {
	            title: "任务管理--接收",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=R",
	            height: 700,
	            width: 1350,
        		returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }
    function assign() {
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.statusdescr!="提交"&&ret.statusdescr!="指派"){
	    		art.dialog({
	        		content: "该任务单不在提交，或指派状态，无法进行指派操作！"
	        	});
	        	return;
	    	}
	    	Global.Dialog.showDialog("prjJobAssign", {
	            title: "任务管理--指派",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=Z",
	            height: 700,
	            width: 1350,
       			returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }
    
    function specSuppl() {
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.statusdescr!="提交"&&ret.statusdescr!="指派"){
	    		art.dialog({
	        		content: "该任务单不在提交，或指派状态，无法进行指派操作！"
	        	});
	        	return;
	    	}
	    	Global.Dialog.showDialog("prjJobAssign", {
	            title: "任务管理--指派",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=S",
	            height: 700,
	            width: 1350,
       			returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }
    
    function finish() {
		var ret = selectDataTableRow();
	    if (ret) {
	    	if(ret.statusdescr!="指派"){
	    		art.dialog({
	        		content: "只能对指派状态进行完成操作!"
	        	});
	        	return;
	    	}
	    	Global.Dialog.showDialog("prjJobFinish", {
	            title: "任务管理--完成",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=W",
	            height: 700,
	            width: 1350,
        		returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }  
    function cancel() {
		var ret = selectDataTableRow();
	    if (ret) {
			if(ret.statusdescr!="提交"&&ret.statusdescr!="指派"){
	    		art.dialog({
	        		content: "只能对提交和指派状态进行取消操作!"
	        	});
	        	return;
	    	}
	    	Global.Dialog.showDialog("prjJobCancel", {
	            title: "任务管理--取消",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=C",
	            height: 700,
	            width: 1350,
        		returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }    
    function view() {
		var ret = selectDataTableRow();
		var rowid = $("#dataTable").jqGrid("getGridParam", "selrow");
		ids=rowid;
	    if (ret) {
	    	Global.Dialog.showDialog("prjJobView", {
	            title: "任务管理--查看",
	            url: "${ctx}/admin/prjJobManage/goOperation?no="+ret.no+"&&m_umState=V",
	            height: 700,
	            width: 1350,
        		returnFun: goto_query
    		});	    	
	    }else{
	    	art.dialog({
	        	content: "请选择一条记录"
	        });
	    }
    }  
    function supplJobList() {
		Global.Dialog.showDialog("supplJobList", {
            title: "任务管理--查询",
            url: "${ctx}/admin/prjJobManage/goSupplJobList",
            height: 700,
            width: 1350,
   		});	 
    } 
    function save(){
    	Global.Dialog.showDialog("save", {
            title: "任务管理--新增",
            url: "${ctx}/admin/prjJobManage/goSave",
            height: 700,
            width: 1350,
            returnFun: goto_query
   		});	 
    }
    
    function resfreshJobTypes() {
    
        $.ajax({
            url: "${ctx}/admin/prjJobManage/prjTypeByItemType1Auth/2/" + $("#itemType1").val(),
            dataType: "json",
            cache: false,
            success: setJobTypes,
            error: clearJobTypes
        })
        
    }
    
    function setJobTypes(data) {
    
        $("#jobType_NAME").val("")
        $("#jobType").val("")
    
        var treeObj = $.fn.zTree.getZTreeObj("tree_jobType")        
        var root = treeObj.getNodeByTId("tree_jobType_1")
        treeObj.removeChildNodes(root)
        
        for (i = 0; i < data.data.length; i++) {
            treeObj.addNodes(root, {id: data.data[i].id, name: data.data[i].id + " " + data.data[i].name})
        }
               
    }
    
    function clearJobTypes() {
    
        $("#jobType_NAME").val("")
        $("#jobType").val("")
        
        var treeObj = $.fn.zTree.getZTreeObj("tree_jobType")        
        var root = treeObj.getNodeByTId("tree_jobType_1")
        treeObj.removeChildNodes(root)
    }
    
	function checkbox(obj) {
		if ($(obj).is(":checked")){
			$(obj).val("1");
		}else{
			$(obj).val("0");
		}
	}
  	</script>
	</head>
	<body>
		<div class="body-box-list">
  			<div class="query-form">
    			<form action="" method="post" id="page_form" class="form-search">
     				<input type="hidden" id="expired" name="expired" value="${customer.expired}"/>
      				<input type="hidden" name="jsonString" value=""/>
      				<ul class="ul-form">
       			 		<li>
          					<label>编号</label>

          					<input id="no" name="no"/>
       			 		</li>
        				<li>
          					<label>材料类型1</label>
          					<select id="itemType1" name="itemType1" onchange="resfreshJobTypes()"></select>
        				</li>
        				<li>
        				    <label>任务类型</label>
        				    <house:DictMulitSelect id="jobType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
        				        sql="select rtrim(Code) Code, Descr from tJobType where 1 = 2 order by Code"></house:DictMulitSelect>
        				</li>
        				<li>
          					<label>楼盘</label>
          					<input type="text" id="address" name="address"/>
        				</li>
        				<li>
          					<label>集成部</label>
        					<house:department2 id="Department2jc" dictCode="15"></house:department2>
        				</li>
        				<li>
          					<label>申请日期</label>
          					<input type="text" id="dateFrom" name="dateFrom" class="i-date"
                 				   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                 				   value="<fmt:formatDate value='${prjJob.dateFrom}' pattern='yyyy-MM-dd'/>"/>
        				</li>
        				<li>
          					<label>至</label>
				          	<input type="text" id="dateTo" name="dateTo" class="i-date"
				                   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				                   value="<fmt:formatDate value='${prjJob.dateTo}' pattern='yyyy-MM-dd'/>"/>
       					</li>
       					<li>
				             <label>申请人</label>
				             <input type="text" id="appCzy" name="appCzy" />
			            </li>
			            <li>
							<label>工程部</label>
							<house:dict id="department2" dictCode="" sql=" select Code,Desc1 from tDepartment2 where DepType='3' and Expired='F' order by DispSeq "  sqlValueKey="Code" sqlLableKey="Desc1" ></house:dict>
						</li>
        				<li>
          					<label>状态</label>
				          	 <house:xtdmMulit id="status" dictCode=""
					         	sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='PRJJOBSTATUS'"
					            selectedValue="2,3"></house:xtdmMulit>
        				</li>
       					<li>
				             <label>处理人</label>
				             <input type="text" id="dealCzy" name="dealCzy" />
			            </li>
			            <li>
			            	<label>客户阶段</label>
			            	<select name="custStage" id="custStage">
			            		<option value="">请选择...</option>
			            		<option value="sale">售中</option>
			            		<option value="afterSale">售后</option>
			            	</select>
			            </li>
			            <li>
							<input type="checkbox" id="isNotTr" name="isNotTr" onclick="checkbox(this)" value="0" />
							<label class="checkbox-label" for="isNotTr">包含待触发</label>
						</li>
        				<li>
          					<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
        				</li>
   					</ul>
   				</form>
  			</div>
  			<div class="btn-panel">
    			<div class="btn-group-sm">
    				<house:authorize authCode="PRJJOBMANAGE_SAVE">
        				<button type="button" class="btn btn-system " onclick="save()">新增</button>
      				</house:authorize>
    				<house:authorize authCode="PRJJOBMANAGE_RECEIVE">
        				<button type="button" class="btn btn-system " onclick="receive()">接收</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_ASSIGN">
        				<button type="button" class="btn btn-system" onclick="assign()">指派</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_SPECSUPPL">
        				<button type="button" class="btn btn-system" onclick="specSuppl()">指派供应商</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_FINISH">
        				<button type="button" class="btn btn-system " onclick="finish()">完成</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_CANCEL">
        				<button type="button" class="btn btn-system" onclick="cancel()">取消</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_VIEW">
        				<button type="button" class="btn btn-system " onclick="view()">查看</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_SUPPLJOBLIST">
        				<button type="button" class="btn btn-system " onclick="supplJobList()">供应商任务查询</button>
      				</house:authorize>
      				<house:authorize authCode="PRJJOBMANAGE_EXCEL">
        				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
      				</house:authorize>
    			</div>
  			</div>
  			<div class="pageContent">
    			<div id="content-list">
      				<table id="dataTable"></table>
      				<div id="dataTablePager"></div>
    			</div>
  			</div>
		</div>
	</body>
</html>


