<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工人工资审批</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function zhuli(){
	var ret = selectDataTableRow();
	var status=ret.status;
	var status1=ret.checkstatus;
	var IsWithHold=ret.iswithhold;
	if(status!=2){
	art.dialog({content: "只能对提交状态的工人工资进行审核操作!",width: 200});
		return false;
	}
	if((IsWithHold==0)&&(status1==3)){
		art.dialog({content: "预扣为【否】，客户结算状态是【项目经理要领】，不能进行审批！",width: 200});
		return false;
	}
	if((IsWithHold==0)&&(status1==4)){
		art.dialog({content: "预扣为【否】，客户结算状态是【项目经理已领】，不能进行审批！",width: 200});
		return false;
	}
	var m_s = "Z";	
	Global.Dialog.showDialog("PreWorkCostDetail",{						  
	  title:"工资审批—一级审核",
	  url:"${ctx}/admin/preWorkCostDetail/goSave",
	  postData:{pk:ret.pk,m_s:m_s,workLoad:ret.workload},		
	  height: 660,
	  width:1000,
	  returnFun: goto_query
	});
}

function jingli(){
	var ret = selectDataTableRow();
	var status=ret.status;
	var status1=ret.checkstatus;
	var IsWithHold=ret.iswithhold;
	var czy="${czybh}";
	var czy1=ret.applyman;
	var success;
	$.ajax({
		url:"${ctx}/admin/preWorkCostDetail/gojingli?status="+status,
		type:'post',
		postData:{status:ret.status},	
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
		},
		success:function(obj){
			if (obj.msg == "success"){			
				if(czy==czy1){
					art.dialog({content: "不允许对申请人是自己的工资进行审核！",width: 200});
					return false;
				}
			
				if((IsWithHold==0)&&(status1==3)){
					art.dialog({content: "预扣为【否】，客户结算状态是【项目经理要领】，不能进行审批！",width: 200});
					return false;
				}
				if((IsWithHold==0)&&(status1==4)){
					art.dialog({content: "预扣为【否】，客户结算状态是【项目经理已领】，不能进行审批！",width: 200});
					return false;
				}
				var m_s = "M";	
				Global.Dialog.showDialog("PreWorkCostDetail",{						  
					title:"工资审批—二级审核",
				  	url:"${ctx}/admin/preWorkCostDetail/goSave",
				  	postData:{pk:ret.pk,m_s:m_s,workLoad:ret.workload},		
				  	height: 660,
				  	width:1000,
				  	returnFun: goto_query
				});
			}else{
				art.dialog({content: obj.msg,width: 200});
			}
		}
	});
}

function zhulifan(){
	var ret = selectDataTableRow();
	var status=ret.status;

	//  如果是tWorkType2中字段“isconfirmtwo”为0（不需要二级审核），允许对“二级已审核”状态进行反审核。 --add by zb
	if ("0" == ret.isconfirmtwo) {
		if (!(status == 3||status == 4)) {// 当状态不为3或者4的时候
			art.dialog({content: "只能对已审核的工人工资进行反审核操作",width: 200});
			return false;
		}
	} else {
		if(status!=3){
			art.dialog({content: "只能对一级已审核的工人工资进行反审核操作!",width: 200});
			return false;
		}
	}
	
	var m_s = "ZF";	
	Global.Dialog.showDialog("PreWorkCostDetail",{						  
	    title:"工资审批—一级反审核",
	   	url:"${ctx}/admin/preWorkCostDetail/goSave",
	  	postData:{pk:ret.pk,m_s:m_s},		
	  	height: 660,
	  	width:1000,
	  	returnFun: goto_query
	});
}
function jinlifan(){
	var ret = selectDataTableRow();
	var status=ret.status;
	if(status!=4){
	art.dialog({content: "只能对二级已审核的工人工资进行反审核操作!",width: 200});
		return false;
	}
	var m_s = "MF";	
	Global.Dialog.showDialog("PreWorkCostDetail",{						  
	    title:"工资审批—二级反审核",
	  	url:"${ctx}/admin/preWorkCostDetail/goSave",
	  	postData:{pk:ret.pk,m_s:m_s},		
	  	height: 660,
	  	width:1000,
	  	returnFun: goto_query
	});
}
function View(){
	var ret = selectDataTableRow();
	var m_s = "C";	
	Global.Dialog.showDialog("PreWorkCostDetail",{						  
	    title:"工资审批—查看",
	  	url:"${ctx}/admin/preWorkCostDetail/goSave",
	  	postData:{pk:ret.pk,m_s:m_s,workLoad:ret.workload},		
	  	height: 660,
	  	width:1000,
	  	returnFun: goto_query
	});
}

function add(){
	Global.Dialog.showDialog("PreWorkCostDetail",{						  
	    title:"工资审批—新增",
	  	url:"${ctx}/admin/preWorkCostDetail/goAdd",
	  	height: 500,
	  	width:700,
	  	returnFun: goto_query
	});
}

function goto_query(tableId){	
	var bV = $.trim($("#beginValue").val());	
	if (bV==""){
		$("#beginValue").val("0");
	}
	if (!tableId || typeof(tableId)!="string"){
		tableId = "dataTable";
	}	
	if(document.getElementById("expired_show").checked==true){
		$("#expired").val("F");
	}else{ 
		$("#expired").val("T");
	}
		 
	$("#"+tableId).jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}

/**初始化表格*/
$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
	Global.LinkSelect.initSelect("${ctx}/admin/preWorkCostDetail/workTypeByAuthority","workType1","workType2");
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/preWorkCostDetail/goJqGrid",
			postData:{status:"2,3,4"},
			ondblClickRow: function(){
            	View();
            },
            styleUI: 'Bootstrap',   
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  	{name: "pk", index: "pk", width: 100, label: "申请编号", sortable: true, align: "left", hidden: true},
				{name: "documentno", index: "documentno", width: 80, label: "档案号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘", sortable: true, align: "left"},
				{name: "applyman", index: "applyman", width: 90, label: "申请人编号", sortable: true, align: "left",hidden:true},
				{name: "applymandescr", index: "applymandescr", width: 90, label: "申请人", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 60, label: "部门", sortable: true, align: "left"},
				{name: "applydate", index: "applydate", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "status", index: "status", width: 90, label: "状态", sortable: true, align: "left", hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "状态", sortable: true, align: "left"},
				{name: "checkstatus", index: "checkstatus", width: 100, label: "客户结算", sortable: true, align: "left", hidden:true},
				{name: "checkstatusdescr", index: "checkstatusdescr", width: 100, label: "客户结算状态", sortable: true, align: "left",hidden:true},				
				{name: "salarytypedescr", index: "salarytypedescr", width: 70, label: "工资类型", sortable: true, align: "left"},
				{name: "worktype1descr", index: "worktype1descr", width: 75, label: "工种类型1", sortable: true, align: "left"},
				{name: "worktype2descr", index: "worktype2descr", width: 75, label: "工种类型2", sortable: true, align: "left"},
				{name: "iswithhold", index: "iswithhold", width: 70, label: "是否预扣", sortable: true, align: "left", hidden:true},
				{name: "iswithholddescr", index: "iswithholddescr", width: 70, label: "是否预扣", sortable: true, align: "left"},
				{name: "withholdno", index: "withholdno", width: 70, label: "预扣单号", sortable: true, align: "left"},
				{name: "workercode", index: "workercode", width: 70, label: "工人编号", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 70, label: "工人姓名", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 170, label: "卡号", sortable: true, align: "left"},
				{name: "appamount", index: "appamount", width: 70, label: "申请金额", sortable: true, align: "right"},
				{name: "cfmamount", index: "cfmamount", width: 70, label: "审核金额", sortable: true, align: "right"},
				{name: "qualityfee", index: "qualityfee", width: 70, label: "质保金", sortable: true, align: "right"},
				{name: "realamount", index: "realamount", width: 70, label: "实发金额", sortable: true, align: "right"},
				{name: "isworkappdescr", index: "isworkappdescr", width: 100, label: "是否工人申请", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 80, label: "请款说明", sortable: true, align: "left"},
				{name: "confirmassistdescr", index: "confirmassistdescr", width: 80, label: "一级审核", sortable: true, align: "left"},
				{name: "assistconfirmdate", index: "assistconfirmdate", width: 140, label: "一级审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "confirmmanagerdescr", index: "confirmmanagerdescr", width: 80, label: "二级审核", sortable: true, align: "left"},
				{name: "managerconfirmdate", index: "managerconfirmdate", width: 140, label: "二级审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后更新人员", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "isconfirmtwo", index: "isconfirmtwo", width: 140, label: "工资是否二次审核", sortable: true, align: "left", hidden:true},
				{name: "workload", index: "workload", width: 100, label: "工作量", sortable: true, align: "left",hidden:true}
            ]
	});
});  
	$("#ProjectMan").openComponent_employee({showValue:"${pWorkCostDetail.custCode}"});
	$("#workerCode").openComponent_worker();
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#address").val('');
	$("#ProjectMan").val('');
	$("#Department2").val('');
	$("#isWorkApp").val('');
	$("#workType1").val('');
	$("#workType2").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	$("#workerCode").val('');
} 
function doExcel(){
	var url = "${ctx}/admin/preWorkCostDetail/doExcel";
	doExcelAll(url);
}
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
	      		<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="exportData" id="exportData">
				<input type="hidden" id="expired" name="expired" value="${pWorkCostDetail.expired}" />
				<ul class="ul-form">
						<li>
							<label>楼盘</label>
								<input type="text" id="address" name="address"   value="${pWorkCostDetail.address }" />
							</label>
						</li>
						<li>
							<label>项目经理</label>
							<input type="text" id="ProjectMan" name="ProjectMan"  value="${pWorkCostDetail.ProjectMan}" />
						</li>
						<li>
							<label>是否工人申请</label>
								<house:xtdm  id="isWorkApp" dictCode="YESNO"    value="${pWorkCostDetail.isWorkApp}"></house:xtdm>
						</li>
						<li>
							<label>工程部</label>
							<house:dict id="department2" dictCode="" 
								sql="select a.Code,a.code+' '+a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
										left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
										 where  a.deptype='3' and a.Expired='F'  order By dispSeq " 
								sqlValueKey="Code" sqlLableKey="Desc1"  >
							</house:dict>
						</li>
						<li>
							<label>工种类型1</label>
								<select id="workType1" name="workType1"   ></select>
						</li>
						<li>
							<label>工种类型2</label>
								<select id="workType2" name="workType2"   ></select>
						</li>
						<li>
							<label>状态</label>
								<house:xtdmMulit id="status" dictCode="PRECOSTSTATUS" selectedValue="2,3,4"></house:xtdmMulit>
						</li>
						<li>
							<label>工人编号</label>
								<input type="text" id="workerCode" name="workerCode"  value="${pWorkCostDetail.workerCode }" />
							</label>
						</li>
						<li id="loadMore" >							
							<input type="checkbox" id="expired_show" name="expired_show"/>不显示非本人专盘&nbsp				
							<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
				</ul>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system "  onclick="add()">新增</button>
					<house:authorize authCode="preWorkCostDetail_zlsh">
						<button type="button" class="btn btn-system " onclick="zhuli()">一级审核</button><!-- 原来是助理审核 -->
					</house:authorize>
					<house:authorize authCode="preWorkCostDetail_jlsh">
						<button type="button" class="btn btn-system "  onclick="jingli()">二级审核</button><!-- 原来是经理审核 -->
					</house:authorize>
					<house:authorize authCode="preWorkCostDetail_zlfsh">
						<button type="button" class="btn btn-system " onclick="zhulifan()">一级反审核</button>
					</house:authorize>
					<house:authorize authCode="preWorkCostDetail_jlfsh">
						<button type="button" class="btn btn-system "  onclick="jinlifan()">二级反审核</button>
					</house:authorize>
					<house:authorize authCode="preWorkCostDetail_view">
						<button type="button" class="btn btn-system "  onclick="View()">查看</button>
					</house:authorize>
						<button type="button" class="btn btn-system "  onclick="doExcel()">导出excel</button>
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


