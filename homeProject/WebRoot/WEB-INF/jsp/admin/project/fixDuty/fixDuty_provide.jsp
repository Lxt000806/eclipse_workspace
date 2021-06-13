<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>工人定责管理-发放</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	//清空
	function clearForm(){
		$("#page_form input[type='text']").val("");
		$("select").val("");
	} 
	/**初始化表格*/
	$(function(){

		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/fixDuty/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			postData:{status:"6",type:"1"},
			multiselect: true,
			colModel : [
				{name: "no", index: "no", width: 100, label: "定责申请编号", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "address", index: "address", width: 120, label: "楼盘", sortable: true, align: "left"},
				{name: "cwstatusdescr", index: "cwstatusdescr", width: 80, label: "工地状态", sortable: true, align: "left"},
				{name: "appmantypedescr", index: "appmantypedescr", width: 110, label: "申请人类型", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 90, label: "状态", sortable: true, align: "left"},
				{name: "appdate", index: "appdate", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "appmandescr", index: "appmandescr", width: 80, label: "申请人", sortable: true, align: "left"},
				{name: "fixamount", index: "fixamount", width: 100, label: "发放金额", sortable: true, align: "right", sum: true},
				{name: "offerprj", index: "offerprj", width: 100, label: "人工金额", sortable: true, align: "right"},
				{name: "material", index: "material", width: 120, label: "材料金额", sortable: true, align: "right"},
				{name: "remarks", index: "remarks", width: 100, label: "描述", sortable: true, align: "left"},
				{name: "status", index: "status", width: 120, label: "状态", sortable: true, align: "left",hidden:true},
				{name: "prjconfirmdate", index: "prjconfirmdate", width: 120, label: "项目经理确认时间", sortable: true, align: "left", formatter: formatTime},
				{name: "prjmandescr", index: "prjmandescr", width: 90, label: "确认项目经理", sortable: true, align: "left"},
				{name: "prjremark", index: "prjremark", width: 110, label: "项目经理确认说明", sortable: true, align: "left"},
				{name: "cfmdate", index: "cfmdate", width: 120, label: "金额确认日期", sortable: true, align: "left", formatter: formatTime},
				{name: "cfmmandescr", index: "cfmmandescr", width: 80, label: "金额确认人", sortable: true, align: "left"},
				{name: "cfmofferprj", index: "cfmofferprj", width: 90, label: "确认人工金额", sortable: true, align: "right"},
				{name: "cfmmaterial", index: "cfmmaterial", width: 90, label: "确认材料金额", sortable: true, align: "right"},
				{name: "cfmremark", index: "cfmremark", width: 110, label: "专员确认说明", sortable: true, align: "left"},
				{name: "dutymandescr", index: "dutymandescr", width: 80, label: "判责人", sortable: true, align: "left"},
				{name: "dutydate", index: "dutydate", width: 120, label: "判责时间", sortable: true, align: "left", formatter: formatTime},
				{name: "managecfmdate", index: "managecfmdate", width: 120, label: "经理审批时间", sortable: true, align: "left", formatter: formatTime},
				{name: "managecfmmandescr", index: "managecfmmandescr", width: 80, label: "经理审批人", sortable: true, align: "left"},
				{name: "providemandescr", index: "providemandescr", width: 80, label: "发放人", sortable: true, align: "left"},
				{name: "providedate", index: "providedate", width: 120, label: "发放时间", sortable: true, align: "left", formatter: formatTime},
				{name: "cancelremark", index: "cancelremark", width: 110, label: "取消说明", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 101, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 74, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 76, label: "操作日志", sortable: true, align: "left"},
				{name: "designriskfund", index: "designriskfund", width: 76, label: "风控基金", sortable: true, align: "left",hidden:true}
			]
		});
	});
	function updateMultyStatus(content,newStatus){
		var nos="";
		var flag=true;
		var ids =$("#dataTable").jqGrid('getGridParam', 'selarrrow');
		if(ids.length<1){
			art.dialog({
				content:"请至少选择一条数据",
			});
			return;
		}
		for (var i=0;i<ids.length;i++){
			var ret=$("#dataTable").jqGrid('getRowData', ids[i]);
			nos+=ret.no;
			if(i<ids.length-1)nos+=",";
			if(ret.status!="6"){
				flag=false;
				break;
			}
		}
		if(!flag){
			art.dialog({
				content:"选中了状态不为已审批的记录，无法进行发放！",
			});
			return;
		}
		/*$.ajax({
			url:"${ctx}/admin/fixDuty/updateMultyStatus",
			type:"post",
			data:{oldStatus:'6',nos:nos,status:'7'},
			dataType:"json",
			cache:false,
			async:false,
			error:function(obj){
				showAjaxHtml({"hidden": false, "msg": "获取数据出错~"});
			},
			success:function(obj){
				if(obj>0){
					art.dialog({
						content : "发放成功",
						time : 1000,
						beforeunload : function() {
							goto_query();
						}
					});
				}else{
					art.dialog({
						content : "发放失败，请检查状态是否已被更新！",
					});
				}
			}
		});*/
		
		var param= Global.JqGrid.selectToJson("dataTable");
		Global.Form.submit("page_form","${ctx}/admin/fixDuty/updateMultyStatus",param,function(ret){
			if(ret.rs==true){
				art.dialog({
					content:ret.msg,
					time:1000,
					beforeunload:function(){
						goto_query();
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
		
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="updateMultyStatus()">
						<span>发放</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut"
						onclick="closeWin()">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<ul class="ul-form">
					<li>
						<label>楼盘地址</label> 
						<input type="text" id="address" name="address" />
					</li>
					<li>
						<label>申请人类型</label> 
						<house:xtdm id="appManType" dictCode="APPMANTYPE"></house:xtdm>
					</li>
					<li>
						<label>判责日期</label> 
						<input type="text" id="dutyDateFrom" name="dutyDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="dutyDateTo" name="dutyDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>经理审批日期</label> 
						<input type="text" id="manageCfmDateFrom" name="manageCfmDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label> 
						<input type="text" id="manageCfmDateTo" name="manageCfmDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
						name="expired_show" value="${fixDuty.expired}"
						onclick="hideExpired(this)" ${fixDuty.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
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
