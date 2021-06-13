<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>赠送项目管理列表</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","discAmtType");
		var dataSet2 = {
			firstSelect:"discAmtType",
			firstValue:'${gift.discAmtType}',
		}; 
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/gift/goJqGrid",
			ondblClickRow: function(){
            	view();
            },
            styleUI: 'Bootstrap',
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
			  {name : 'pk',index : 'no',width : 70,label:'编号',sortable : true,align : "left",hidden:true},
		      {name : 'descr',index : 'descr',width : 150,label:'优惠标题',sortable : true,align : "left"},
		      {name : 'remarks',index : 'remarks',width : 200,label:'优惠项目说明',sortable : true,align : "left"},
		      {name : 'actname',index : 'actname',width : 70,label:'活动名称',sortable : true,align : "left"},			    
		      {name : 'begindate',index : 'begindate',width : 90,label:'开始时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'enddate',index : 'enddate',width : 90,label:'结束时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'minarea',index : 'minarea',width : 90,label:'适用最小面积',sortable : true,align : "right"},
		      {name : 'maxarea',index : 'maxarea',width : 90,label:'适用最大面积',sortable : true,align : "right"},
		      {name : 'typedescr',index : 'typedescr',width : 70,label:'类型',sortable : true,align : "left"},
		      {name : 'quotemoduledescr',index : 'quotemoduledescr',width : 70,label:'报价模块',sortable : true,align : "left"},
		      {name : 'disctypedescr',index : 'disctypedescr',width : 90,label:'优惠折扣类型',sortable : true,align : "left"},
		      {name : 'discper',index : 'discper',width : 70,label:'优惠比例',sortable : true,align : "right",},
		      {name : 'discamttypedescr',index : 'discamttypedescr',width : 90,label:'优惠金额分类',sortable : true,align : "left"},
		      {name : 'maxdiscamtexpr',index : 'maxdiscamtexpr',width : 120,label:'最高优惠金额公式',sortable : true,align : "left"},
		      {name : 'perfdisctypedescr',index : 'perfdisctypedescr',width : 90,label:'业绩扣减方式',sortable : true,align : "left",},
		      {name : 'perfdiscper',index : 'perfdiscper',width : 90,label:'业绩扣减比例',sortable : true,align : "right"},
		      {name : 'calcdiscctrlper',index : 'calcdiscctrlper',width : 130,label:'计入优惠额度控制',sortable : true,align : "right",},
		      {name : 'isservicedescr',index : 'isservicedescr',width : 110,label:'是否服务性产品',sortable : true,align : "left",},
		      {name : 'isoutsetdescr',index : 'isoutsetdescr',width : 90,label:'是否套外项目',sortable : true,align : "left",},
		      {name : 'issofttokendescr',index : 'issofttokendescr',width : 80,label:'是否软装券',sortable : true,align : "left",},
		      {name : 'islimititemdescr',index : 'islimititemdescr',width : 120,label:'是否限定报价项目',sortable : true,align : "left",},
		      {name : 'iscupboarddescr',index : 'iscupboarddescr',width : 120,label:'是否橱柜',sortable : true,align : "left",},
		      {name : 'discamtcalctypedescr',index : 'discamtcalctypedescr',width : 120,label:'优惠额度计算方式',sortable : true,align : "left",},
		      {name : 'isadvancedescr',index : 'isadvancedescr',width : 100,label:'额度预支项目',sortable : true,align : "left",},
		      {name : 'dispseq',index : 'dispseq',width : 70,label:'显示顺序',sortable : true,align : "right"},
		      {name : 'lastupdate',index : 'lastupdate',width : 150,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 70,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ]    
		});
	});  

function add(){	
	Global.Dialog.showDialog("giftAdd",{			
	  title:"赠送项目管理--新增",
	  url:"${ctx}/admin/gift/goSave",
	  height: 700,
	  width:1350,
	  returnFun: goto_query
	});
}

function copy() {
    var ret = selectDataTableRow();
    if (ret) {      
        Global.Dialog.showDialog("giftCopy",{
            title:"赠送项目管理--复制",
            url:"${ctx}/admin/gift/goCopy?id="+ret.pk,
            height:700,
            width:1350,
            returnFun: goto_query
        });
    } else {
        art.dialog({
            content: "请选择一条记录"
        });
    }
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {    	
    	Global.Dialog.showDialog("giftUpdate",{
		    title:"赠送项目管理--修改",
		    url:"${ctx}/admin/gift/goUpdate?id="+ret.pk,
		    height:700,
		 	width:1350,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function setCustType() {
    Global.Dialog.showDialog("giftSetCustType", {
        title: "赠送项目管理--设定产品线",
        url: "${ctx}/admin/gift/goSetCustType",
        height: 650,
        width: 1000,
        returnFun: goto_query
    });
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {    	
        Global.Dialog.showDialog("giftview",{
		    title:"赠送项目管理--查看",
		  	url:"${ctx}/admin/gift/goDetail?id="+ret.pk,
		  	height:700,
		  	width:1350,
		  	returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function doExcel(){
	var url = "${ctx}/admin/gift/doExcel";
	doExcelAll(url);
}
function clearForm(){
	$("#page_form input[type='text'],input[type='number']").val('');
	$("#page_form select").val('');
}
</script>
</head>
    
<body>
	<div class="body-box-list">
        <div class="query-form"  >  
	        <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>							
						<label>优惠标题</label>
						<input type="text" id="descr" name="descr" style="width:160px;" />
					</li>
					<li><label>客户类型</label> <house:dict
							id="custType" dictCode="" sql="select a.ibm,cast(a.ibm as nvarchar(10))+' '+a.note descr  
							from tXTDM a   where a.ID='CUSTTYPE' and ibm !='0'  order By ibm "
							sqlValueKey="ibm" sqlLableKey="descr">
						</house:dict>
					</li>
					<li>						
						<label>类型</label>
						<house:xtdm id="type" dictCode="GIFTTYPE"></house:xtdm>													
					</li>
					<li>
						<label>优惠折扣类型</label>
						<house:xtdm id="discType" dictCode="GIFTDISCTYPE" ></house:xtdm>													
					</li>
					<li>
						<label>有效时间从</label> 
						<input type="text" id="dateFrom" name="dateFrom" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>到</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date"
							onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>							
						<label>适用面积从</label>
						<input type="number" id="minArea" name="minArea" style="width:160px;" />
					</li>
					<li>							
						<label>到</label>
						<input type="number" id="maxArea" name="maxArea" style="width:160px;" />
					</li>
					<li><label>优惠金额分类</label> <select id="discAmtType"
						name="discAmtType"></select>
					</li>
					<li class="search-group"><input type="checkbox" id="expired_show"
					name="expired_show" value="${gift.expired}"
					onclick="hideExpired(this)" ${gift.expired!='T' ?'checked':'' }/>
					<p>隐藏过期</p>
					<button type="button" class="btn  btn-sm btn-system "
						onclick="goto_query();">查询</button>
					<button type="button" class="btn btn-sm btn-system "
						onclick="clearForm();">清空</button></li>
			</form>
		</div>
		<div class="clear_float"> </div>
		<!--query-form-->
		<div class="pageContent">
			<div class="btn-panel" >
		    	<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " onclick="add()">新增</button>
					<button type="button" class="btn btn-system"  onclick="copy()">复制</button>
					<button type="button" class="btn btn-system "  onclick="update()">修改</button>
					<house:authorize authCode="GIFT_SETCUSTTYPE">
						<button type="button" class="btn btn-system " onclick="setCustType()">设定产品线</button>
					</house:authorize>
					<house:authorize authCode="GIFT_VIEW">
						<button type="button" class="btn btn-system "  onclick="view()">查看</button>
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


