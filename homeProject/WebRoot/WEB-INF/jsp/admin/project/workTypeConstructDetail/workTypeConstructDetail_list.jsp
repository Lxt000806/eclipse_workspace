<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>各种施工情况分析</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function goto_query(){
   	if($("#workType12").val()==''){
		art.dialog({content: "工种不能为空",width:200});
		return false;
	}
   	if($.trim($("#beginDate").val())==''){
		art.dialog({content: "申请日期不能为空",width: 200});
		return false;
	} 
	if($("#endDate").val()==''){
		art.dialog({content: "结束时间不能为空",width:200});
		return false;
	}											//bzsgfx 的控制层名称为   workTypeConstructDetailController
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/bzsgfx/goJqGrid",datatype:'json',postData:$("#page_form").jsonForm(),page:1,sortname:''}).trigger("reloadGrid");

}

/**初始化表格*/
$(function(){
	$("#code").openComponent_worker();
       //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{			
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI: 'Bootstrap',
		colModel : [
		  {name : 'workercode',index : 'workercode',width : 75,label:'工人编号',sortable : true,align : "left",count:true,frozen : true},
		  {name : 'namechi',index : 'namechi',width : 75,label:'工人姓名',sortable : true,align : "left",frozen : true},
		  {name : 'issigndescr',index : 'issigndescr',width : 75,label:'签约类型',sortable : true,align : "left",frozen : true},			      
		  {name : 'worktype12descr',index : 'worktype12descr',width : 75,label:'工种类型',sortable : true,align : "left",frozen : true},			      
	      {name : 'leveldescr',index : 'leveldescr',width : 60,label:'星级',sortable : true,align : "left"},
	      {name : 'ondo',index : 'ondo',width : 100,label:'在建施工数',sortable : true,align : "right",sum:true},
	      {name : 'cmpdo',index : 'cmpdo',width : 100,label:'在建停工数',sortable : true,align : "right",sum:true},
	      {name : 'stopdo',index : 'stopdo',width : 100,label:'在建完工数',sortable : true,align : "right",sum:true},
	      {name : 'builder',index : 'builder',width : 100,label:'当前在建套数',sortable : true,align : "right",sum:true},
	      {name : 'arrange',index : 'arrange',width : 75,label:'安排套数',sortable : true,align : "right",sum:true},
	      {name : 'signintimenum',index : 'signintimenum',width : 100,label:'及时签到套数',sortable : true,align : "right",sum:true},
	      {name : 'signintimerate',index : 'signintimerate',width : 95,label:'及时签到率',sortable : true,align : "right",},
	      {name : 'signrate',index : 'signrate',width : 95,label:'签到率',sortable : true,align : "right",}, 
	      {name : 'crtdate',index : 'crtdate',width :75,label:'签到天数',sortable : true,align : "right",frozen: true,sum:true},
	      {name : 'avgcompletedays',index : 'avgcompletedays',width : 100,label:'平均施工天数',sortable : true,align : "right",formatter: formatRound},
	      {name : 'intimetotaldays',index : 'intimetotaldays',width : 100,label:'及时完成平均施工天数',sortable : true,align : "right",formatter: formatRound},
	      {name : 'complete',index : 'complete',width : 75,label:'完工套数',sortable : true,align : "right",sum:true},
	      {name : 'notsignnum',index : 'notsignnum',width : 95,label:'未签到套数',sortable : true,align : "right",sum:true},
	      {name : 'intimeconfirm',index : 'intimeconfirm',width : 95,label:'及时完成套数',sortable : true,align : "right",sum:true},
	      {name : 'intimeconfirmrate',index : 'intimeconfirmrate',width : 85,label:'及时完成率',sortable : true,align : "right",},
		  {name : 'workload',index : 'workload',width : 90,label:'完工工作量',sortable : true,align : "right",sum:true},
		  {name : 'firnum',index : 'firnum',width : 80,label:'一级工地数',sortable : true,align : "right",sum:true},
	      {name : 'firper',index : 'firper',width : 110,label:'一级工地占比',sortable : true,align : "right",},
	      {name : 'nopass',index : 'nopass',width : 110,label:'验收不通过次数',sortable : true,align : "right",frozen: true,sum:true},
	      {name : 'confirmamount',index : 'confirmamount',width : 90,label:'工资发放额',sortable : true,align : "right",frozen: true,sum:true},
	      {name : 'workernum',index : 'workernum',width : 90,label:'班组人数',sortable : true,align : "right",frozen: true,sum:true},
	       {name : 'worktype12',index : 'worktype12',width : 75,label:'工种类型12',sortable : true,align : "left",hidden:true},
		 
           ],
		rowNum:100000,
		loadonce: true,
		pager :'1', 
		gridComplete:function(){
			var sumcomplete=0;
			var sumfirstnum=0;
			var sumintimeconfirm=0;
			var sumsignintimenum=0;
			var sumarrange=0;  
			var complete= Global.JqGrid.allToJson("dataTable","complete"); // 完工套数
			var firstnum = Global.JqGrid.allToJson("dataTable","firnum"); // 一级工地数	
			var intimeconfirm= Global.JqGrid.allToJson("dataTable","intimeconfirm"); // 及时完成
			arr1 = complete.fieldJson.split(",");	
			arr2 = firstnum.fieldJson.split(",");	
			arr3 = intimeconfirm.fieldJson.split(",");	
			var signintimenum= Global.JqGrid.allToJson("dataTable","signintimenum"); // 及时签到
			var arrange= Global.JqGrid.allToJson("dataTable","arrange"); // 安排套数
			var arr4 = signintimenum.fieldJson.split(",");	
			var arr5 = arrange.fieldJson.split(","); 
			for(var i = 0;i < arr1.length; i++){			
				sumcomplete=sumcomplete+parseInt(arr1[i]==""?0:arr1[i]);
				sumfirstnum=sumfirstnum+parseInt(arr2[i]==""?0:arr2[i]);
				sumintimeconfirm+=	parseInt(arr3[i]==""?0:arr3[i]);
				sumsignintimenum+=  parseInt(arr4[i]==""?0:arr4[i]);
				sumarrange+= parseInt(arr5[i]==""?0:arr5[i])	;	
			}	
			var avgfirper;
			var avgintimeconfirmrate ;
			var avtsignintime;
			if (sumcomplete==0){
				avgfirper=0;
				avgintimeconfirmrate=0;
			}else{
				avgfirper = sumfirstnum/sumcomplete*100;
				avgintimeconfirmrate = sumintimeconfirm/sumcomplete*100;
			} 
			if(sumarrange==0){
				avtsignintime=0;
			}else{
				avtsignintime=sumsignintimenum/sumarrange*100;
			}
	   	   $("#dataTable").footerData("set",{"firper":(avgfirper).toFixed(1)+"%",
	   	   									"intimeconfirmrate":(avgintimeconfirmrate).toFixed(1)+"%",
	   	   									"signintimerate":(avtsignintime).toFixed(1)+"%"},false);
           frozenHeightReset("dataTable");
		},
	});
	$("#dataTable").jqGrid('setFrozenColumns');
	
	function formatRound(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return cellvalue;
		}
      	if(cellvalue == 0){
      		return 0;
      	}
      	return myRound(cellvalue, 1);
   	}
});  
	
function view(){
	var ret = selectDataTableRow();
	if(ret){
		var code=ret.workercode;
		var beginDate = $.trim($("#beginDate").val());
		var endDate = $.trim($("#endDate").val());
		Global.Dialog.showDialog("UpdateApp",{
			title:"班组施工情况——查看",
			url:"${ctx}/admin/bzsgfx/goView",		
			postData:{code:code,beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),
					workType12:ret.worktype12,statisticalMethods:$("#statisticalMethods").val()
			},
			height:600,
			width:1200
		});
	}else{
		art.dialog({content:"请选择一条数据",width:200});
	}
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_worker_code").val('');
	$("#workType12").val("");
	$("#workType12_NAME").val("");
	$("#isSign_NAME").val("");
	$("#isSign").val("");
	$("#custType").val("");
	$("#custType_NAME").val("");
	$("#workerClassify").val("");
	$("#workerClassify_NAME").val("");
	$("#department2").val("");
	$("#department2_NAME").val("");
	
	$.fn.zTree.getZTreeObj("tree_isSign").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_workerClassify").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_custType").checkAllNodes(false);
	$.fn.zTree.getZTreeObj("tree_workType12").checkAllNodes(false);
} 
</script>
</head>
<body >
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${worker.expired}" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>工种</label>
						<house:xtdmMulit id="workType12" dictCode="" sql="select rtrim(Code)Code,Descr from tWorkType12 a where  (
						(select PrjRole from tCZYBM where CZYBH='${czybm }') is null 
						or( select PrjRole from tCZYBM where CZYBH='${czybm }') ='' ) or  Code in(
							select WorkType12 From tprjroleworktype12 pr where pr.prjrole = 
							(select PrjRole from tCZYBM where CZYBH='${czybm }') or pr.prjrole = ''
							 )   order by a.DispSeq " 
						sqlValueKey="Code" sqlLableKey="Descr"   selectedValue='${worker.workType12}'></house:xtdmMulit>
					</li>
					<li>
						<label>工人编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${worker.workerCode }"/>
					</li>
					<li>
						<label >开始日期</label>
						<input type="text" id="beginDate" name="beginDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${worker.beginDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label >结束日期</label>
						<input type="text" id="endDate" name="endDate" class="i-date"  
						onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
						value="<fmt:formatDate value='${worker.endDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<label>签约类型</label>
						<house:xtdmMulit id="isSign" dictCode="WSIGNTYPE"  
						sqlValueKey="Code" sqlLableKey="Descr"   selectedValue='0,1,2'></house:xtdmMulit>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  expired="F" ></house:custTypeMulit>
					</li>
					<li>
						<label>有在建工地</label>
						<select id="hasBuild" name="hasBuild"  >
							<option value="">请选择...</option>
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</li>
					<li>
						<label>工人分类</label>
						<house:xtdmMulit id="workerClassify" dictCode="WORKERCLASSIFY"></house:xtdmMulit>                     
					</li>
					<li>							
						<label>工程大区</label>
						<house:dict id="prjRegionCode" dictCode="" sql="SELECT Code,(Code+' '+Descr) Descr FROM  dbo.tPrjRegion WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					<li>							
						<label>工种分组</label>
						<house:DictMulitSelect id="workType12Dept" dictCode="" sql="SELECT Code,(Code+' '+Descr) Descr FROM  dbo.tWorkType12Dept WHERE expired='F' ORDER BY CAST(Code as Integer) ASC" sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>	
					<li>
						<label>工程部</label>
						<house:DictMulitSelect id="department2" dictCode="" sql="select Code,desc1 desc1  from dbo.tDepartment2 where  deptype='3' and Expired='F' order By dispSeq  " 
						sqlLableKey="desc1" sqlValueKey="code"   ></house:DictMulitSelect>
					</li>
					<li>
						<label>状态</label>
						<house:xtdm id="isLeave" dictCode="WORKERSTS" value="0"></house:xtdm>
					</li>
					<li>
						<label>统计方式</label>
						<select id="statisticalMethods" name="statisticalMethods">
							<option value="1">按施工工种</option>
							<option value="2">按工人工种</option>
						</select>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show"
									value="${worker.expired}" onclick="hideExpired(this)"
									${worker.expired!='T' ?'checked':'' }/>隐藏过期&nbsp;
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="clear_float"> </div>
		<div class="pageContent">
			<div class="btn-panel">
		    	<div class="btn-group-sm">
		    		<house:authorize authCode="bzsgfx_VIEW">
						<button type="button" class="btn btn-system " onclick="view()"><span>查看</span></button>
					</house:authorize>
						<button type="button" class="btn btn-system " onclick="doExcelNow('各班组施工情况分析')">导出excel</button>
		    	</div>
			</div>
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div>
	</div>
</body>
</html>


