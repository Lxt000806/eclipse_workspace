<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Supplier列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	if('${supplier.readonly}'=="1"){
		document.getElementById("itemType1").disabled=true;
	}
	if("${supplier.supplJob}"=="1"){
		document.getElementById("itemType1").disabled=true;
	}
	if("${supplier.isDisabled}"!="" && "${supplier.isDisabled}"!="0"){
		$("#itemType1").attr("disabled",true);
	}
	//var mobile1Label = "手机";
	var descrWidth = 100;
	var addressWidth = 100;
	//领料预申请设置Mobile1名称为手机1
    if("${supplier.preAppNo}" != "" && "${supplier.preAppNo}" !=null){
		//mobile1Label += "1";
		descrWidth = 150;
		addressWidth = 200;
    }
	var postData = $("#page_form").jsonForm();
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/supplier/goJqGrid",
			postData:{itemType1:'${supplier.itemType1}',preAppNo:'${supplier.preAppNo}',
				existSuppl:'${supplier.existSuppl}',actNo:'${supplier.actNo}',
				isActSuppl:'${supplier.isActSuppl}',itemRight:"${supplier.itemRight}",supplJob:"${supplier.supplJob}",
				custCode:"${supplier.custCode}",showAll:"0",jobType:"${supplier.jobType}",from:"${supplier.from}"},
			height:$(document).height()-$("#content-list").offset().top-70,
			styleUI: 'Bootstrap', 
			colModel : [
			  {name : 'Code',index : 'Code',width : 100,label:'商家编号',sortable : true,align : "left"},
		      {name : 'Descr',index : 'Descr',width : descrWidth,label:'名称',sortable : true,align : "left"},
		      {name : 'PrepayBalance',index : 'PrepayBalance',width : 100,label:'预付金余额',sortable : true,align : "right"},
		      {name : 'Address',index : 'Address',width : addressWidth,label:'地址',sortable : true,align : "left"},
		      {name : 'Contact',index : 'Contact',width : 100,label:'联系人',sortable : true,align : "left"},
		      {name : 'Phone1',index : 'Phone1',width : 100,label:'电话1',sortable : true,align : "left"},
		      {name : 'Phone2',index : 'Phone2',width : 100,label:'Phone2',sortable : true,align : "left"},
		      {name : 'Fax1',index : 'Fax1',width : 100,label:'传真1',sortable : true,align : "left"},
		      {name : 'Fax2',index : 'fax2',width : 100,label:'Fax2',sortable : true,align : "left",hidden:true},
		      {name : 'Mobile1',index : 'mobile1',width : 100,label:"手机1",sortable : true,align : "left"},
		      {name : 'Mobile2',index : 'mobile2',width : 100,label:'Mobile2',sortable : true,align : "left",hidden:true},
		      {name : 'Email1',index : 'email1',width : 100,label:'邮件地址1',sortable : true,align : "left"},
		      {name : 'Email2',index : 'email2',width : 100,label:'Email2',sortable : true,align : "left",hidden:true},
		      {name : 'IsSpecDay',index : 'isSpecDay',width : 100,label:'是否指定结算日期',sortable : true,align : "left",hidden:true},
		      {name : 'SpecDay',index : 'specDay',width : 100,label:'指定结算日期',sortable : true,align : "left",hidden:true},
		      {name : 'BillCycle',index : 'billCycle',width : 100,label:'结算周期',sortable : true,align : "left",hidden:true},
		      {name : 'LastUpdate',index : 'lastUpdate',width : 100,label:'LastUpdate',sortable : true,align : "left",hidden:true},
		      {name : 'LastUpdatedBy',index : 'lastUpdatedBy',width : 100,label:'LastUpdatedBy',sortable : true,align : "left",hidden:true},
		      {name : 'Expired',index : 'expired',width : 100,label:'Expired',sortable : true,align : "left",hidden:true},
		      {name : 'ActionLog',index : 'actionLog',width : 100,label:'ActionLog',sortable : true,align : "left",hidden:true},
		      {name : 'ItemType1',index : 'itemType1',width : 100,label:'材料类型1',sortable : true,align : "left"},
		      {name : 'ActName',index : 'ActName',width : 100,label:'户名',sortable : true,align : "left",hidden:true},
		      {name : 'Bank',index : 'Bank',width : 100,label:'开户行',sortable : true,align : "left",hidden:true},
		      {name : 'CardId',index : 'CardId',width : 100,label:'账号',sortable : true,align : "left",hidden:true},
		      {name : 'IsContainTax',index : 'IsContainTax',width : 100,label:'含税价',sortable : true,align : "left",hidden:true},
		      {name : 'PreOrderDay', index : 'PreOrderDay', width : 100, label : '订货提前天数', sortable : true, align : "right", hidden:true},
            ],
            ondblClickRow:function(rowid,iRow,iCol,e){
				var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
            	//getCode(selectRow.Code,selectRow.Descr,'${supplier.fetchId }')
            	Global.Dialog.returnData = selectRow;
            	Global.Dialog.closeDialog();
            }
		});
		//领料预申请设置列的显示隐藏
	    if("${supplier.preAppNo}" != "" && "${supplier.preAppNo}" !=null){
			$("#dataTable").jqGrid( "hideCol", "Phone2");
			$("#dataTable").jqGrid( "hideCol", "ItemType1");
	    }
		//初始化材料类型1
		//Global.LinkSelect.initSelect("${ctx}/admin/item/itemType","itemType1");
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1","itemType2","itemType3");
		Global.LinkSelect.setSelect({firstSelect:'itemType1',
										firstValue:'${supplier.itemType1 }',
									});
		if('${supplier.readAll}'=="1"){
			$("#dataTable").jqGrid('hideCol', "Address");
			$("#dataTable").jqGrid('hideCol', "Contact");
	        $("#dataTable").jqGrid('hideCol', "Phone1");
	        $("#dataTable").jqGrid('hideCol', "Phone2");
	        $("#dataTable").jqGrid('hideCol', "Fax1");
	        $("#dataTable").jqGrid('hideCol', "Email1");
	        $("#dataTable").jqGrid('hideCol', "PrepayBalance");

        }
        // 传入材料类型1  并不可修改
		if('${supplier.readonly}'=="1"){
			document.getElementById("itemType1").disabled=true;
		}		
        if("${supplier.showLastSendSupplier}" == "1"){
        	$("#lastSendSupplierLi").removeAttr("hidden");
        }						
});
function clearForm(){
	$("#page_form input[type='text']").val('');
	if('${supplier.readonly}'!="1"){
		$("#page_form select").val('');
	}
}
function changeBox(){
	var value=$("#showAll").val();
	if(value=="0"){
		$("#showAll").val("1");
	}else{
		$("#showAll").val("0");
	}
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" id="expired" name="expired" value="${supplier.expired}" />
					<ul class="ul-form">
							<li> 
							<label>供应商编号</label>
							
								<input type="text" id="code" name="code" value="${supplier.code}" />
							</li>
							<li> 
								<label>供应商名称</label>
							
								<input type="text" id="descr" name="descr"  value="${supplier.descr}" />
							</li>
							<li> 
								<label>供应商类型</label>
								<select id="itemType1" name="itemType1"  value="${supplier.itemType1}"></select>
							</li>
							<li id="lastSendSupplierLi" hidden>
								<label>土建最后发货商</label>
								<input type="text" id="lastSendSupplierDescr" name="lastSendSupplierDescr"  value="${supplier.lastSendSupplierDescr}"/>
							</li>
							<li>
								<label>显示所有供应商</label> 
								<input type="checkbox" id="showAll" name="showAll" value="0" onclick="changeBox()">
							</li>
							<li style="display: none">
								 <label>readonly</label>
							
								<input type="text" id="readonly" name="readonly"  value="${supplier.readonly}" />
							</li>
							<li>
								<label></label>
									<input type="checkbox" id="expired_show" name="expired_show"
													value="${supplier.expired}" onclick="hideExpired(this)"
													${supplier.expired!='T' ?'checked':'' }/>隐藏过期 
							</li>
							<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-system btn-sm"
								onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>	
</body>
</html>


