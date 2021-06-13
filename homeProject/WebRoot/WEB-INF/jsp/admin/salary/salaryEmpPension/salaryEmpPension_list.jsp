<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
    <META HTTP-EQUIV="expires" CONTENT="0"/>
    <META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
    <title>工资补贴管理</title>
    <%@ include file="/commons/jsp/common.jsp" %>
</head>
<body>
<div class="body-box-list">
    <div class="query-form">
        <form role="form" class="form-search" id="page_form" method="post" target="targetFrame">
            <input type="hidden" name="jsonString" value=""/>
            <ul class="ul-form">
           	    <li>
					<label>补贴科目</label>	
					<house:xtdm id="type" dictCode="SALPENSIONTYPE"  ></house:xtdm>
				</li>
				<li>
					<label>薪酬月份</label>
					<input type="text" id="beginMon" name="beginMon" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMM'})"
						 value="<fmt:formatDate value='${salaryEmpPension.dateFrom}' pattern='yyyyMM'/>"  />
				</li>
            	<li>
					<label>查询条件</label>
					<input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证"/>
				</li>
                <li>
					<button type="button" class="btn  btn-sm btn-system "
						onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-sm btn-system "
						onclick="clearForm();">清空</button>
				</li>
            </ul>
        </form>
    </div>
    <div class="btn-panel">
        <div class="btn-group-sm">
            <house:authorize authCode="SALARYEMPPENSION_SAVE">
                <button type="button" class="btn btn-system" onclick="save()">
                    <span>新增</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYEMPPENSION_UPDATE">
                <button type="button" class="btn btn-system" onclick="update()">
                    <span>编辑</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYEMPPENSION_DELETE">
                <button type="button" class="btn btn-system" onclick="del()">
                    <span>删除</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYEMPPENSION_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">
                    <span>查看</span>
                </button>
            </house:authorize>
            <house:authorize authCode="SALARYEMPPENSION_EXCEL">
                <button type="button" class="btn btn-system" onclick="excel()">
                    <span>输出到Excel</span>
                </button>
            </house:authorize>
            <button type="button" class="btn btn-system " id="goImport" onclick="goImport()">
				<span>导入</span>
			</button>
        </div>
    </div>
    <div id="content-list">
        <table id="dataTable"></table>
        <div id="dataTablePager"></div>
    </div>
</div>
<script type="text/javascript">

    $(function() {

        Global.JqGrid.initJqGrid("dataTable", {
            url: "${ctx}/admin/salaryEmpPension/goJqGrid",
            postData: $("#page_form").jsonForm(),
            height: $(document).height() - $("#content-list").offset().top - 70,
            colModel: [
                {name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left",hidden:true},
                {name: "salaryemp", index: "salaryemp", width: 70, label: "工号", sortable: true, align: "left"},
                {name: "typedescr", index: "typedescr", width: 80, label: "补贴科目", sortable: true, align: "left",  },
                {name: "empname", index: "empname", width: 70, label: "姓名", sortable: true, align: "left",  },
                {name: "amount", index: "amount", width: 70, label: "金额", sortable: true, align: "right",  },
                {name: "beginmon", index: "beginmon", width: 80, label: "开始月份", sortable: true, align: "left",  },
                {name: "endmon", index: "endmon", width: 80, label: "截止月份", sortable: true, align: "left",  },
                {name: "effectdate", index: "effectdate", width: 85, label: "生效日期", sortable: true, align: "left", formatter: formatDate},
                {name: "remarks", index: "remarks", width: 300, label: "备注", sortable: true, align: "left"},
                {name: "lastupdate", index: "lastupdate", width: 130, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
                {name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后修改人员", sortable: true, align: "left"},
                {name: "expired", index: "expired", width: 50, label: "过期", sortable: true, align: "left"},
                {name: "actionlog", index: "actionlog", width: 80, label: "操作日志", sortable: true, align: "left"},
            ],
            ondblClickRow: view
        })

    })

    function save() {
        Global.Dialog.showDialog("salaryEmpPensionSave", {
            title: "工资补贴管理--新增",
            url: "${ctx}/admin/salaryEmpPension/goSave",
            height: 400,
            width: 700,
            returnFun: goto_query
        })
    }

    function update() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("salaryEmpPensionUpdate", {
            title: "工资补贴管理--编辑",
            url: "${ctx}/admin/salaryEmpPension/goUpdate",
            postData: {pk: ret.pk},
            height: 400,
            width: 700,
            returnFun: goto_query
        })
    }

    function view() {
        var ret = selectDataTableRow()

        if (!ret) {
            art.dialog({content: "请选择一条记录"})
            return
        }

        Global.Dialog.showDialog("salaryEmpPensionView", {
            title: "工资补贴管理--查看",
            url: "${ctx}/admin/salaryEmpPension/goView",
            postData: {pk: ret.pk},
            height: 400,
            width: 700,
        })
    }
    
    function del() {
        var ret = selectDataTableRow();
		if (!ret) {
			art.dialog({
				content: "请选择一条记录"
			});
			return;
		}
		art.dialog({
			content:"您确定要删除该信息吗？",
			lock: true,
			ok: function () {
				$.ajax({
					url : "${ctx}/admin/salaryEmpPension/doDelete",
					data : {deleteIds:ret.pk},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
					error: function(){
						art.dialog({
							content: "删除该信息出现异常"
						});
					},
					success: function(obj){
						if(obj.rs){
							art.dialog({
								content: obj.msg,
								time: 1000,
								beforeunload: function () {
									goto_query();
								}
							});
						}else{
							art.dialog({
								content: obj.msg
							});
						}
					}
				});
				return true;
			},
			cancel: function () {
				return true;
			}
		});
    }

    function excel() {
        doExcelAll("${ctx}/admin/salaryEmpPension/doExcel")
    }
    
    function goImport() {
        Global.Dialog.showDialog("goImport", {
            title: "工资补贴管理--查看",
            url: "${ctx}/admin/salaryEmpPension/goImport",
            postData: {},
            height: 600,
            width: 1100,
        })
    }

</script>
</body>
</html>
