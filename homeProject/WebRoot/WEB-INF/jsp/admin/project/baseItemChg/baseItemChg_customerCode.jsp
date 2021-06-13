<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
  	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
  	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
  	<META HTTP-EQUIV="expires" CONTENT="0"/>
  	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
  	<title>搜寻--客户编号</title>
  	<%@ include file="/commons/jsp/common.jsp" %>

  	<script type="text/javascript">
    $(function () {
      	$("#status_NAME").attr("disabled", "disabled");
      	//初始化表格
      	Global.JqGrid.initJqGrid("dataTable", {
        	url: "${ctx}/admin/customer/goJqGrid?status=4",
        	height: $(document).height() - $("#content-list").offset().top - 70,
			colModel : [
		  	{name : "code",index : "code",width : 70,label:"客户编号",sortable : true,align : "left"},
		  	{name : "descr",index : "descr",width : 60,label:"客户名称",sortable : true,align : "left"},
		  	{name : "isinitsign",index : "isinitsign",width : 60,label:"草签状态",sortable : true,align : "left",hidden:true},
		  	{name : "custtype",index : "custtype",width : 100,label:"客户类型",sortable : true,align : "left",hidden:true},
		  	{name : "saletype",index : "saletype",width : 200,label:"销售类型",sortable : true,align : "left",hidden:true},
		  	{name : "custtypedescr",index : "custtypedescr",width : 70,label:"客户类型",sortable : true,align : "left"},
		  	{name : "address",index : "address",width : 200,label:"楼盘",sortable : true,align : "left"},
		  	{name : "layoutdescr",index : "layoutdescr",width : 40,label:"户型",sortable : true,align : "left"},
		  	{name : "area",index : "area",width : 40,label:"面积",sortable : true,align : "right"},
		  	{name : "statusdescr",index : "statusdescr",width : 70,label:"客户状态",sortable : true,align : "left"},
		  	{name : "checkstatusdescr",index : "checkstatusdescr",width : 85,label:"客户结算状态",sortable : true,align : "left"},
		  	{name : "custcheckdate",index : "custcheckdate",width : 85,label:"客户结算时间",sortable : true,align : "left",formatter: formatTime},
		  	{name : "designmandescr",index : "designmandescr",width : 50,label:"设计师",sortable : true,align : "left"},
		  	{name : "businessmandescr",index : "businessmandescr",width : 50,label:"业务员",sortable : true,align : "left"},
		  	{name : "projectman",index : "projectman",width : 60,label:"项目经理",sortable : true,align : "left",hidden:true},
		  	{name : "projectmandescr",index : "projectmandescr",width : 60,label:"项目经理",sortable : true,align : "left"},
		  	{name : "projectmanphone",index : "projectmanphone",width : 200,label:"项目经理电话",sortable : true,align : "left",hidden:true},
		  	{name : "custtypetype",index : "custtypetype",width : 200,label:"客户类型的类型",sortable : true,align : "left",hidden:true},
		  	{name : "canusecombaseitem",index : "canusecombaseitem",width : 200,label:"是否可报公用基础项目",sortable : true,align : "left",hidden:true}
			],
        	ondblClickRow: function (rowid, iRow, iCol, e) {
          		var selectRow = $("#dataTable").jqGrid("getRowData", rowid);
          		 if (selectRow.isinitsign === '1') {
	          		art.dialog({
	          			content:"草签楼盘无法进行操作！",
	          		});
	          		return
	          	}
          		if (selectRow.custtypetype=="1" && "${operType}"=="J"){//清单客户
          			art.dialog({
        				content: "非套餐客户不能做套餐内减项！"
        			});
          			return;
          		}
          		var sTitle = "基装增减";
          		if ("${operType}"=="J"){
          			sTitle = "基装增减套餐内";
          		}
          		Global.Dialog.showDialog("baseItemChg_Add", {
                   	title: sTitle,
                   	url: "${ctx}/admin/baseItemChg/goBaseItemChgAdd",
                   	postData: {
                     	"custCode": selectRow.code,
                     	"address": selectRow.address,
                     	"custType": selectRow.custtype,
                     	"itemType1": "${itemType1}",
                     	"custtypetype": selectRow.custtypetype,
                     	"operType": "${operType}",
                     	"canUseComBaseItem":selectRow.canusecombaseitem
                   	},
                   	height: window.screen.height - 130,
                   	width: window.screen.width - 180,
                   	returnFun: function () {
                     	setTimeout(function () {
                       		closeWin();
                     	}, 100);
                   	}
               	});
        	}
    	});
    });

    function clearForm() {
      $("#page_form input[type='text'][id!='status_NAME']").val("");
      $("#page_form select").val("");
    }
 	</script>
</head>
<body>
<div class="body-box-list">
  	<div class="query-form">
    	<input id="itemType1" name="itemType1" value="${itemType1}" style="display:none"/>

    	<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
      		<ul class="ul-form">
        		<li>
          			<label>客户名称</label>
          			<input type="text" id="descr" name="descr" value="${customer.descr }"/>
        		</li>
        		<li>
          			<label>楼盘</label>
          			<input type="text" id="address" name="address" value="${customer.address }"/>
        		</li>
        		<li>
          			<label>项目经理</label>
          			<input type="text" id="projectManDescr" name="projectManDescr" value="${customer.projectManDescr }"/>
        		</li>
        		<li>
          			<label>客户状态</label>
          			<house:xtdmMulit id="status" dictCode=""
                           sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='CUSTOMERSTATUS' and CBM='4' "
                           selectedValue="4"></house:xtdmMulit>
        		</li>
        		<li>
          			<label id="mobile">电话</label>
          			<input type="text" id="mobile1" name="mobile1" value="${customer.mobile1 }"/>
        		</li>
        		<li id="loadMore">
          			<button type="button" class="btn btn-system btn-sm" onclick="goto_query();">查询</button>
          			<button type="button" class="btn btn-system btn-sm" onclick="clearForm();">清空</button>
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
