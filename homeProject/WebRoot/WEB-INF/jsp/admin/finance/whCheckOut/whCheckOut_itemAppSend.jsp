<%@ page language="java" import="com.house.framework.commons.utils.PathUtil" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czylb=uc.getCzylb();
String czybh="";
if("2".equals(czylb)) czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
	<title>ItemAppSend列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
</head>
    
<body>
	<div class="body-box-list">
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs" >
					<button type="button" class="btn btn-system "  id="saveBtn">确认新增</button>
					<button type="button" class="btn btn-system "  id="closeBut" onclick="closeWin()">关闭</button>
				</div>
			</div>
		</div>
<!-- 		<div class="panel panel-info" >  
			<div class="panel-body"> -->
		<div>  
			<div class="query-form">			
				<form action="" method="post" id="page_form" class="form-search">
					<input type="hidden" id="expired" name="expired" value="${item.expired}" />
					<ul class="ul-form">
						<li>
							<label>发货单号</label>
							<input type="text" id="no" name="no" value="${itemAppSend.no}" />
						</li>
						<li>
							<label>领料单号</label>
							<input type="text" id="iaNo" name="iaNo" value="${itemAppSend.iaNo}" />
						</li>
						<li>				
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" />
						</li>
						<li>				
							<label>楼盘</label>
							<input type="text" id="address" name="address" />
						</li>
						<li>
							<label>发货日期</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemAppSend.date}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>				
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${itemAppSend.date}' pattern='yyyy-MM-dd'/>" />
						</li>
						<li>
							<label>仓库</label>
							<input type="text" id="whCode" name="whCode" value="${itemAppSend.whCode}" />
						</li>
						<li>
							<label>材料类型1</label>
							<select id="itemType1" name="itemType1" ></select>
						</li>
						<li>
							<label>材料类型2</label>
							<select id="itemType2" name="itemType2" ></select>
						</li>
						<li>
							<label>备注</label>
							<input type="text" id="remarks" name="remarks" value="${itemAppSend.remarks}" />
						</li>
						<li class="search-group">				
							<input type="checkbox" id="expired_show" name="expired_show"
								value="${itemAppSend.expired}" onclick="hideExpired(this)"
								${item.expired!='T' ?'checked':'' }/><p>隐藏过期</p> 
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>		
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>	
<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
var selectRows = [];
var sUnSelected="${itemAppSend.unSelected}"
function goto_query(){
	var postData=$("#page_form").jsonForm();
		postData.unSelected=sUnSelected;
	$("#dataTable").jqGrid("setGridParam",{postData:postData,page:1}).trigger("reloadGrid");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
} 
function closeWin(){
	if (selectRows.length>0){
		Global.Dialog.returnData = selectRows;	
		Global.Dialog.closeDialog(true);
	}else{
		Global.Dialog.closeDialog(false);
	}
	
} 

/**初始化表格*/
$(function(){
	//初始化查询条件
	$("#whCode").openComponent_wareHouse({showValue:"${postParam.whCode}",showLabel:"${postParam.whDescr}",readonly:true});
	/* $("#custCode").openComponent_customer(); */
	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2");
	
	var optionSelect=$("#itemType1 option");
    var sValue=""
    optionSelect.each(function (i,e) {
    	sValue= $(e).text().replace(/[^a-z]+/ig,"");
        if(sValue=="JZ"){
            $(this).attr("selected","selected");
        }   
    });
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/whCheckOut/goWHCheckOutItemAppSendAddJqGrid",
		postData:{whCode:"${itemAppSend.whCode}" ,whCheckOutNo:"${itemAppSend.whCheckOutNo}",unSelected: sUnSelected,itemType1:$("#itemType1").val()},
		height:$(document).height()-$("#content-list").offset().top-110,
		multiselect:true,
        styleUI: 'Bootstrap',
		colModel : [
		  {name: "ischeckout", index: "ischeckout", width: 90, label: "是否记账", sortable: true, align: "left", count: true,hidden: true},
          {name: "no", index: "no", width: 100, label: "领料发货单号", sortable: true, align: "left"},
          {name: "date", index: "date", width: 75, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
          {name: "iano", index: "iano", width: 100, label: "领料申请编号", sortable: true, align: "left"},
          {name: "typedescr", index: "typedescr", width: 80, label: "领料单类型", sortable: true, align: "left"},
          {name: "type", index: "type", width: 80, label: "领料单类型", sortable: true, align: "left",hidden: true},
          {name: "documentno", index: "documentno", width: 70, label: "档案号", sortable: true, align: "left"},
          {name: "payeecode", index: "payeecode", width: 80, label: "签约公司", sortable: true, align: "left", hidden: true},
          {name: "payeedescr", index: "payeedescr", width: 80, label: "签约公司", sortable: true, align: "left"},
          {name: "address", index: "address", width: 167, label: "楼盘", sortable: true, align: "left"},
          {name: "projectmandescr", index: "projectmandescr", width: 70, label: "项目经理", sortable: true, align: "left"},
          {name: "descr", index: "descr", width: 70, label: "客户名", sortable: true, align: "left"},
          {name: "itemtype1descr", index: "itemtype1descr", width: 70, label: "材料类型1", sortable: true, align: "left"},
          {name: "itemtype2descr", index: "itemtype2descr", width: 110, label: "材料类型2", sortable: true, align: "left"},
          {name: "senddate", index: "senddate", width: 140, label: "送货日期", sortable: true, align: "left", formatter: formatTime},
          {name: "sendtype", index: "sendtype", width: 90, label: "送货类型", sortable: true, align: "left"},
          {name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
          {name: "itemsumcost", index: "itemsumcost", width: 80, label: "总金额", sortable: true, align: "right", sum: true},
          {name: "issetitemdescr", index: "issetitemdescr", width: 80, label: "套餐材料", sortable: true, align: "left"},
		  {name: "projectamount", index: "projectamount", width: 105, label: "项目经理结算价", sortable: true, align: "right", sum: true},
		  {name: "whfee", index: "whfee", width: 90, label: "仓储费", sortable: true, align: "right", sum: true},
		  {name: "longfee", index: "longfee", width: 80, label: "远程费", sortable: true, align: "right", sum: true},
          {name: "sendfee", index: "sendfee", width: 80, label: "配送费", sortable: true, align: "right", sum: true},
          {name: "carryfee", index: "carryfee", width: 80, label: "搬运费", sortable: true, align: "right", sum: true},
          {name: "projectothercost", index: "projectothercost", width: 85, label: "项目经理调整", sortable: true, align: "right", sum: true},
          {name: "remarks", index: "remarks", width: 140, label: "发货备注", sortable: true, align: "left"},
          {name: "itemappremarks", index: "itemappremarks", width: 200, label: "领料备注", sortable: true, align: "left"} ,
          {name: "checkstatusdescr", index: "checkstatusdescr", width: 90, label: "客户结算状态", sortable: true, align: "left",hidden: true},
          {name: "checkseq", index: "checkseq", width: 90, label: "显示顺序", sortable: true, align: "right"},
          {name: "whfeecosttype", index: "whfeecosttype", width: 90, label: "成本类型", sortable: true, align: "right", hidden: true},
          {name: "sendfeecosttype", index: "sendfeecosttype", width: 90, label: "配送费成本类型", sortable: true, align: "left", hidden: true},
       ]
       
	});
     if("<%=czylb %>"!="1"){
            $("#dataTable").jqGrid('hideCol', "sumcost");
            $("#dataTable").jqGrid('hideCol', "projectothercost");
            }
    //保存
    $("#saveBtn").on("click",function(){
    	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
    	if(ids.length==0){
    		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
    		return;
    	}
		$.each(ids,function(k,id){
			var row = $("#dataTable").jqGrid('getRowData', id);
			selectRows.push(row);
			sUnSelected=sUnSelected+","+row.no;
		});
		var len = ids.length;  
		for(var i = 0;i < len ;i++) {  
			$("#dataTable").jqGrid("delRowData", ids[0]); 
		}  
		//Global.Dialog.returnData = selectRows;
		//closeWin();
    });

});
</script>		
</body>
</html>


