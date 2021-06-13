<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>工人排班管理</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
                <input type="hidden" id="expired"  name="expired" />
				<ul class="ul-form">
					<li>
						<label>工人编号</label>
						<input type="text" id="workerCode" name="workerCode" style="width: 160px;"/>
					</li>
					<li>	
						<label>工种分类</label>
						<select id="work_type12" name="workType12" value="${workerArrange.workType12}"></select>
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${workerArrange.address}"/>
					</li>
					<li>
						<label>预约</label>
						<house:dict id="booked" dictCode="" sql="select CBM, (CBM + ' ' + NOTE) Descr from tXTDM where ID = 'YESNO'"
						 	sqlValueKey="CBM" sqlLableKey="Descr" value="${workerArrange.booked}"/>
					</li>
					<li>
						<label>日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${workerArrange.fromDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${workerArrange.toDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${workerArrange.expired}" 
							onclick="hideExpired(this)" ${workerArrange.expired != 'T' ? 'checked' : ''}/>
						<label for="expired_show" style="margin-left: -3px;text-align: left;">隐藏过期</label>
						<button type="button" class="btn btn-system btn-sm" onclick="goto_query()">查询</button>
						<button type="button" class="btn btn-system btn-sm" id="clear" onclick="clearForm()">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="WORKERARRANGE_BATCHARRANGE">
					<button type="button" class="btn btn-system" id="batchArrange">
						<span>批量排班</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKERARRANGE_BATCHDEL">
					<button type="button" class="btn btn-system" id="batchDel">
						<span>批量删除</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKERARRANGE_ORDER">
					<button type="button" class="btn btn-system" id="order">
						<span>预约</span>
					</button>
				</house:authorize>
				<house:authorize authCode="WORKERARRANGE_RETURN">
					<button type="button" class="btn btn-system" id="return">
						<span>退号</span>
					</button>
				</house:authorize>	
				<house:authorize authCode="WORKERARRANGE_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>

<script type="text/javascript">

var postData = $("#page_form").jsonForm();

$(function(){
	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","work_type12","work_type12_dept")
	
    $("#workerCode").openComponent_worker()

	Global.JqGrid.initJqGrid("dataTable", {
		sortable: true,
		url: "${ctx}/admin/workerArrange/goJqGridList",
		postData: postData,
		multiselect: true,
		height: $(document).height() - $("#content-list").offset().top - 80,
		styleUI: "Bootstrap",
		colModel: [
			{name: "pk", index: "pk", width: 80, label: "编号", sortable: true, align: "left"},
			{name: "workername", index: "workername", width: 70, label: "工人", sortable: true, align: "left"},
			{name: "worktype12descr", index: "worktype12descr", width: 70, label: "工种", sortable: true, align: "left"},
			{name: "date", index: "date", width: 80, label: "日期", sortable: true, align: "left", formatter: formatDate},
			{name: "daytypedescr", index: "daytypedescr", width: 50, label: "班次", sortable: true, align: "left"},
			{name: "no", index: "no", width: 80, label: "预约编号", sortable: true, align: "left"},
			{name: "booked", index: "booked", width: 60, label: "已预约", sortable: true, align: "left", formatter: function(value) {return value === 1 ? '是' : '否';}},
			{name: "custworkpk", index: "custworkpk", width: 120, label: "工地工人信息主键", sortable: true, align: "left"},
			{name: "address", index: "address", width: 160, label: "预约楼盘", sortable: true, align: "left"},
			{name: "area", index: "area", width: 50, label: "面积", sortable: true, align: "left"},
			{name: "operator", index: "operator", width: 80, label: "预约操作员", sortable: true, align: "left"},
			{name: "orderdate", index: "orderdate", width: 130, label: "预约时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 50, label: "操作", sortable: true, align: "left"}
		],
		ondblClickRow: function(){
		    view();
		}
	});
	
	$("#batchArrange").on("click", function() {
		Global.Dialog.showDialog("Save", {
			title: "工人排班信息——批量排班",
			url: "${ctx}/admin/workerArrange/goBatchArrange",
			height: 630,
			width: 1050,
			returnFun: goto_query
		});
	});
	
	$("#batchDel").on("click", function() {
	 	var selectedRows = selectDataTableRows()
	 	
	 	if (selectedRows.length < 1) {
	 		art.dialog({
	 		    content: '请选择一条或多条记录'
	 		})
	 		return
	 	}
	 	
	 	var pks = []
	 	for (var i = 0; i < selectedRows.length; i++) {
	 		if (selectedRows[i].booked === '是') {
	 			art.dialog({
	 			    content: '存在已预约记录，请先退号'
	 			})
	 			return
	 		}
	 		
	 		pks.push(selectedRows[i].pk)
	 	}
		
		Global.Dialog.showDialog("batchDel", {
			title: "工人排班信息——批量删除",
			url: "${ctx}/admin/workerArrange/goBatchDel",
			postData: {
			    "pks": pks,
			    "expired": $('#expired').val()
			},
			height: 400,
			width: 600,
			returnFun: goto_query
		})
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		Global.Dialog.showDialog("update",{
			title:"工人信息明细——修改",
			url:"${ctx}/admin/worker/goUpdate",
			postData:{
			    id: ret.code
			},
			height:715,
			width:1050,
			returnFun:goto_query
		});
	});
	
	$("#return").on("click", function(){
	
        var rows = selectDataTableRows()
        if (rows.length < 1) {
            art.dialog({
                content: "请选择一条记录进行退号"
            })
            return
        }
        
        if (rows.length > 1) {
            art.dialog({
                content: "一次只可以选择一条记录进行退号"
            })
            return
        }
	
		var row = selectDataTableRow();
		if (row.booked === '否') {
			art.dialog({
			    content: "此号还未预约"
			});
			return;
		}
		
		art.dialog({
			 content:'确定要取消此预约吗？',
			 lock: true,
			 ok: function() {
				$.ajax({
					url: "${ctx}/admin/workerArrange/doReturn",
					data: {
					    "pk": row.pk
					},
					dataType: "json",
					type: "post",
					cache: false,
					error: function() {
						art.dialog({
						    content: "取消预约异常"
						});
					},
					success: function(obj) {
						if (obj.rs) {
							art.dialog({
								content:obj.msg,
								time:1000,
								beforeunload: goto_query
							})
						} else {
							art.dialog({
								content: obj.msg
							})
						}
					}
				})
			}, 
			cancel: function() {}
		});
		
	});
	
	$("#order").on("click", function() {
	
        var rows = selectDataTableRows()
		if (rows.length < 1) {
			art.dialog({
			    content: "请选择一条记录进行预约"
			})
			return
		}
		
        if (rows.length > 1) {
            art.dialog({
                content: "只可以选择一条记录进行预约"
            })
            return
        }
		
		var ret = selectDataTableRow()
		if (ret.booked === '是') {
			art.dialog({
			    content: "此号已经预约，不能重复预约"
			})
			return
		}
		
		var today =  new Date()
		today.setHours(0, 0, 0, 0)
		
		if (new Date(ret.date) < today) {
		    art.dialog({
		        content: "不能预约早于今天的日期"
		    })
		    return
		}
				
		Global.Dialog.showDialog("update",{
			title: "工人排班信息——预约",
			url: "${ctx}/admin/workerArrange/goOrder",
			postData: {
			    id: ret.pk
			},
			height: 420,
			width: 400,
			returnFun: goto_query
		})
	})
	
})

function view(){
	var ret=selectDataTableRow();
	if (!ret) {
		art.dialog({
		    content: "请选择一条记录"
		});
		return;
	}
	
	Global.Dialog.showDialog("update",{
		title: "工人排班信息——查看",
		url: "${ctx}/admin/workerArrange/goView",
		postData: {
		    id: ret.pk
		},
		height: 350,
		width: 800,
		returnFun: goto_query
	});
}

</script>
</html>
