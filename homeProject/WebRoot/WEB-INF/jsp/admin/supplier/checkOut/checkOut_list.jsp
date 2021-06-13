<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
	String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>结算列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
function del(){
	var url = "${ctx}/admin/supplierCheckOut/doDelete";
	beforeDel(url);
}
function doExcel(){
	var url = "${ctx}/admin/supplierCheckOut/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplierCheckOut/goJqGrid",
			height:$(document).height()-$("#content-list").offset().top-85,
			colModel : [
			  {name : 'no',index : 'no',width : 100,label:'结算单号',sortable : true,align : "left"},
			  {name : 'date',index : 'date',width : 150,label:'结算日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'status',index : 'status',width : 100,label:'结算单状态',sortable : true,align : "left",hidden: false},
		      {name : 'statusdescr',index : 'statusdescr',width : 100,label:'结算单状态',sortable : true,align : "left"},
		      {name : 'remark',index : 'remark',width : 200,label:'备注',sortable : true,align : "left"},
		      {name: 'paydate', index: 'paydate', width: 120, label: '付款日期', sortable: true, align: "left", formatter: formatTime},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后更新日期',sortable : true,align : "left",formatter:formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 100,label:'最后更新人员',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 100,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 100,label:'操作',sortable : true,align : "left"}
            ]
		});
        // 新增结算
        $("#addSplCheckOut").on("click",function(){
            //新增窗口
        	Global.Dialog.showDialog("splCheckOutAdd",{
        	  title:" 新增结算",
        	  url:"${ctx}/admin/supplierCheckOut/goSave",
        	  height: 700,
        	  width:1000,
        	  returnFun:goto_query
        	});
        });
        // 修改结算
        $("#updateSplCheckOut").on("click",function(){
        	var ret = selectDataTableRow();
            if (ret) {
            	if ($.trim(ret.status)!='1'){
            		art.dialog({
            			content: "只有已申请状态的结算单才能修改"
            		});
            		return;
            	}
            	Global.Dialog.showDialog("splCheckOutUpdate",{
              	  title:" 修改结算",
              	  url:"${ctx}/admin/supplierCheckOut/goUpdate?id="+ret.no,
              	  height: 700,
              	  width:1000,
              	  returnFun:goto_query
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
        });
      // 查看结算
        $("#viewSplCheckOut").on("click",function(){
        	var ret = selectDataTableRow();
            if (ret) {
            	Global.Dialog.showDialog("splCheckOutView",{
              	  title:" 查看结算",
              	  url:"${ctx}/admin/supplierCheckOut/goDetail?id="+ret.no,
              	  height: 700,
              	  width:1000
              	});
            } else {
            	art.dialog({
        			content: "请选择一条记录"
        		});
            }
        });
     // 明细查询
        $("#viewSplCheckOutAll").on("click",function(){
           	Global.Dialog.showDialog("splCheckOutViewAll",{
           	  title:" 明细查询",
           	  url:"${ctx}/admin/supplierCheckOut/goDetailAll",
           	  height: 700,
           	  width:1000
           	});
        });
     
	$("#print").on("click", function() {
	    var ret = selectDataTableRow("dataTable")
	
	    if (!ret) {
	        art.dialog({content: "请选择一条记录"})
	        return
	    }
	
	    if (ret.status.trim() !== '2') {
	        art.dialog({content: "非已审核状态，不可以打印"})
	        return
	    }
	
	    $.ajax({
	        url: "${ctx}/admin/supplierCheck/doPrintBefore",
	        type: "post",
	        data: {no: ret.no},
	        dataType: "json",
	        cache: false,
	        error: function(obj) {
	            art.dialog({
	                content: "访问出错,请重试",
	                time: 3000,
	                beforeunload: function() {}
	            })
	        },
	        success: function(data) {
	            if (data) {
	                Global.Print.showPrint("supplierCheckOut_report1.jasper",
	                    {
	                        no: data.no,
	                        companyName: data.cmpnyName,
	                        titles: data.titles,
	                        LogoFile: "<%=basePath%>jasperlogo/logo.jpg",
	                        SUBREPORT_DIR: "<%=jasperPath%>"
	                    }
	                )
	            } else {
	                art.dialog({content: "打印出错"})
	            }
	        }
	    })
	
	})
});
</script>

</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${splCheckOut.expired}" />
				<input type="hidden" name="jsonString" value="" />
					<ul class="ul-form">
						<li>
							<label>结算日期从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${splCheckOut.dateFrom}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li style="width: 220px;">					
							<label style="width: 20px;">至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${splCheckOut.dateTo}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>结算单状态</label>
							<house:xtdmMulit id="status" dictCode="SPLCKOTSTATUS" selectedValue="${splCheckOut.status}"></house:xtdmMulit>
						</li>
						<li style="width: 150px;">
							<label style="width: 10px;"></label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${splCheckOut.expired}" onclick="hideExpired(this)" ${splCheckOut.expired!='F'?'':'checked' }/>只显示未结清&nbsp;
						</li>
						<li>
							<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
						</li>
					</ul>
			</form>
		</div>
		<div class="btn-panel">
      		<div class="btn-group-sm">
      			<house:authorize authCode="SUPPLIER_CHECKOUT_ADD">
      			<button type="button" class="btn btn-system" id="addSplCheckOut">新增结算</button>
                </house:authorize>
                
                <house:authorize authCode="SUPPLIER_CHECKOUT_UPDATE">
                <button type="button" class="btn btn-system" id="updateSplCheckOut">修改结算</button>
                </house:authorize>
                 
                <house:authorize authCode="SUPPLIER_CHECKOUT_VIEW">
                <button type="button" class="btn btn-system" id="viewSplCheckOut">查看结算</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_CHECKOUT_VIEW">
				<button type="button" class="btn btn-system" id="viewSplCheckOutAll">明细查询</button>
				</house:authorize>
				<house:authorize authCode="SUPPLIER_CHECKOUT_PRINT">
					<button type="button" class="btn btn-system" id="print">打印</button>
				</house:authorize>
      		</div>
      	</div>
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
</body>
</html>


