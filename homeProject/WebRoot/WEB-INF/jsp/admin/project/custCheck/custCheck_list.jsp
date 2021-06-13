<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>SignIn列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
    <script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function add(){
	Global.Dialog.showDialog("custCheckAdd",{
		title:"新增工地结算信息",
		url:"${ctx}/admin/CustCheck/goAdd",
		height:200,
		width:400
	});
}
function finCpl(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("custCheckFinCpl",{
			title:"财务结算完成",
			url:"${ctx}/admin/CustCheck/goUpdateFinCpl",
			postData:{address:ret.address,appDate:ret.appdate,mainStatusDescr:ret.mainstatusdescr,mainRcvDate:ret.mainrcvdate,
						mainCZY:ret.mainczy,mainCplDate:ret.maincpldate,mainRemark:ret.mainremark,intStatusDescr:ret.intstatusdescr,
						intRcvDate:ret.intrcvdate,intCZY:ret.intczy,intCplDate:ret.intcpldate,intRemark:ret.intremark,
						softStatusDescr:ret.softstatusdescr,softRcvDate:ret.softrcvdate,softCZY:ret.softczy,softCplDate:ret.softcpldate,
						softRemark:ret.softremark,confirmCZY:ret.confirmczy,confirmDate:ret.confirmdate,finStatusDescr:ret.finstatusdescr,
						finCZY:ret.finczy,finRcvDate:ret.finrcvdate,finCplDate:ret.fincpldate,finRemark:ret.finremark,custCode:ret.custcode
						},
			height:675,
			width:700,
			returnFun:goto_query
		});
	}else{
	    art.dialog({
			content: "请选择一条记录"
		});
	}
}
function view(){
	var ret = selectDataTableRow();
	if(ret){
		Global.Dialog.showDialog("custCheckView",{
			title:"查看工地结算信息",
			url:"${ctx}/admin/CustCheck/goView",
			postData:{address:ret.address,appDate:ret.appdate,mainStatusDescr:ret.mainstatusdescr,mainRcvDate:ret.mainrcvdate,
						mainCZY:ret.mainczy,mainCplDate:ret.maincpldate,mainRemark:ret.mainremark,intStatusDescr:ret.intstatusdescr,
						intRcvDate:ret.intrcvdate,intCZY:ret.intczy,intCplDate:ret.intcpldate,intRemark:ret.intremark,
						softStatusDescr:ret.softstatusdescr,softRcvDate:ret.softrcvdate,softCZY:ret.softczy,softCplDate:ret.softcpldate,
						softRemark:ret.softremark,confirmCZY:ret.confirmczy,confirmDate:ret.confirmdate,finStatusDescr:ret.finstatusdescr,
						finCZY:ret.finczy,finRcvDate:ret.finrcvdate,finCplDate:ret.fincpldate,finRemark:ret.finremark
						},
			height:675,
			width:700
		});
	}else{
	    art.dialog({
			content: "请选择一条记录"
		});
	}
}
function remark(type){
	var ret = selectDataTableRow();
    if (ret) {
    	var datas = {address:ret.address,appDate:ret.appdate,custCode:ret.custcode};
    	var titlePart = "";
    	if(type=="Main"){
    		titlePart = "主材";
			$.extend(datas,{
				mainStatus:ret.mainstatus,
				mainCplDate:ret.maincpldate,
				mainRcvDate:ret.mainrcvdate,
				mainCZY:ret.mainczy,
				mainStatusDescr:ret.mainstatusdescr,
				mainRemark:ret.mainremark,
				type:"Main"
			});
		}else if(type=="Soft"){
    		titlePart = "软装";
			$.extend(datas,{
				softSatus:ret.softsatus,
				softCplDate:ret.softcpldate,
				softRcvDate:ret.softrcvdate,
				softCZY:ret.softczy,
				softStatusDescr:ret.softstatusdescr,
				softRemark:ret.softremark,
				type:"Soft"
			});
		}else if(type=="Int"){
    		titlePart = "集成";
			$.extend(datas,{
				intStatus:ret.intstatus,
				intCplDate:ret.intcpldate,
				intRcvDate:ret.intrcvdate,
				intCZY:ret.intczy,
				intStatusDescr:ret.intstatusdescr,
				intRemark:ret.intremark,
				type:"Int"
			});
		}else{
    		titlePart = "财务";
			$.extend(datas,{
				finStatus:ret.finstatus,
				finCplDate:ret.fincpldate,
				finRcvDate:ret.finrcvdate,
				finCZY:ret.finczy,
				finStatusDescr:ret.finstatusdescr,
				finRemark:ret.finremark,
				type:"Fin"
			});
		}
		Global.Dialog.showDialog("custCheck"+type+"Remark",{
			title:titlePart+"备注",
			url:"${ctx}/admin/CustCheck/goUpdate"+type+"Rmk",
			postData:datas,
			height:600,
			width:700,
			returnFun:goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }	
}
function recv(type){
	var ret = selectDataTableRow();
    if (ret) {
    	art.dialog({
    		content:'确定要进行接收?',
    		ok:function(){
		    	$.ajax({
						url:'${ctx}/admin/CustCheck/DoRcv'+type+'?custCode='+ret.custcode,
						type: 'post',
						dataType: 'json',
						cache: false,
						error: function(obj){			    		
							art.dialog({
								content: '接收出错,请重试',
								time: 1000,
								beforeunload: function () {
				    				goto_query();
							    }
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
					    		$("#_form_token_uniq_id").val(obj.token.token);
					    		art.dialog({
									content: obj.msg,
									width: 200
								});
					    	}
					    }
				}); 
    		},
    		cancel:function(){
    			goto_query();
    		}
    	});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	setMulitDefault("mainStatus","");
	setMulitDefault("intStatus","");
	setMulitDefault("softSatus","");
	setMulitDefault("finStatus","");
	setMulitDefault("custType","");
}

function goto_query(){
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/CustCheck/goJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");

}
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
        styleUI: 'Bootstrap',
		colModel : [
	      {name : 'custcode',index : 'custcode',width : 80,label:'客户编号',sortable : true,align : "left"},
	      {name : 'documentno',index : 'documentno',width : 80,label:'档案号',sortable : true,align : "left"},
	      {name : 'address',index : 'address',width : 165,label:'楼盘',sortable : true,align : "left"},
	      {name : 'custtypedescr',index : 'custtypedescr',width : 95,label:'客户类型',sortable : true,align : "left"},
	      {name : 'constructtypedescr',index : 'constructtypedescr',width : 95,label:'施工方式',sortable : true,align : "left"},
	      {name : 'projectmandescr',index : 'projectmandescr',width : 100,label:'项目经理',sortable : true,align : "left"},
	      {name : 'appdate',index : 'appdate',width : 140,label:'申请日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'confirmdate',index : 'confirmdate',width : 140,label:'竣工验收日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'housekeeper',index : 'housekeeper',width : 100,label:'主材管家',sortable : true,align : "left"},
	      {name : 'mainstatus',index : 'mainstatus',width : 165,label:'mainstatus',sortable : true,align : "left",hidden:true},
	      {name : 'mainstatusdescr',index : 'mainstatusdescr',width : 115,label:'主材结算状态',sortable : true,align : "left"},
	      {name : 'mainrcvdate',index : 'mainrcvdate',width : 140,label:'主材接收日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'mainczy',index : 'mainczy',width : 100,label:'主材接收人',sortable : true,align : "left"},
	      {name : 'maincpldate',index : 'maincpldate',width : 140,label:'主材完成日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'mainremark',index : 'mainremark',width : 200,label:'主材备注',sortable : true,align : "left"},
	      {name : 'intstatus',index : 'intstatus',width : 165,label:'intstatus',sortable : true,align : "left",hidden:true},
	      {name : 'intstatusdescr',index : 'intstatusdescr',width : 115,label:'集成结算状态',sortable : true,align : "left"},
	      {name : 'intrcvdate',index : 'intrcvdate',width : 140,label:'集成接收日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'intczy',index : 'intczy',width : 100,label:'集成接收人',sortable : true,align : "left"},
	      {name : 'intcpldate',index : 'intcpldate',width : 140,label:'集成完成日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'intremark',index : 'intremark',width : 200,label:'集成备注',sortable : true,align : "left"},
	      {name : 'softsatus',index : 'softsatus',width : 165,label:'softsatus',sortable : true,align : "left",hidden:true},
	      {name : 'softstatusdescr',index : 'softstatusdescr',width : 115,label:'软装结算状态',sortable : true,align : "left"},
	      {name : 'softrcvdate',index : 'softrcvdate',width : 140,label:'软装接收日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'softczy',index : 'softczy',width : 100,label:'软装接收人',sortable : true,align : "left"},
	      {name : 'softcpldate',index : 'softcpldate',width : 140,label:'软装完成日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'softremark',index : 'softremark',width : 200,label:'软装备注',sortable : true,align : "left"},
	      {name : 'confirmczy',index : 'confirmczy',width : 100,label:'竣工验收人',sortable : true,align : "left"},
	      {name : 'finstatus',index : 'finstatus',width : 165,label:'finstatus',sortable : true,align : "left",hidden:true},
	      {name : 'finstatusdescr',index : 'finstatusdescr',width : 115,label:'财务结算状态',sortable : true,align : "left"},
	      {name : 'finczy',index : 'finczy',width : 100,label:'财务接收人',sortable : true,align : "left"},
	      {name : 'finrcvdate',index : 'finrcvdate',width : 140,label:'财务接收日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'fincpldate',index : 'fincpldate',width : 140,label:'财务完成日期',sortable : true,align : "left",formatter:formatTime},
	      {name : 'finremark',index : 'finremark',width : 200,label:'财务备注',sortable : true,align : "left"}
	          ]
	});
	
	$("#housekeeper").openComponent_employee({condition: {position: 433}})
});
function first(){
	if((typeof $("#btnMainRecv").html())!="undefined"){
		setMulitDefault("mainStatus","1,2");
	}

	if((typeof $("#btnSoftRecv").html())!="undefined"){
		setMulitDefault("softSatus","1,2");
	}

	if((typeof $("#btnIntRecv").html())!="undefined"){
		setMulitDefault("intStatus","1,2");
	}

	if((typeof $("#btnFinRecv").html())!="undefined"){
		$("#isConfirm option[value='']").removeAttr("selected");
		$("#isConfirm option[value='1']").attr("selected","selected");
		setMulitDefault("finStatus","1,2");
		setMulitDefault("intStatus","3");
		setMulitDefault("mainStatus","3");
		setMulitDefault("softSatus","3");
	}
}
function setMulitDefault(id,values){
	$("#"+id).val('');
	$.fn.zTree.getZTreeObj("tree_"+id).checkAllNodes(false);
	zTree = $.fn.zTree.getZTreeObj("tree_"+id);
	var idArr = values.split(',');
	var nameArr = [];
	for(var i=0; i<idArr.length; i++){
		var nodes = zTree.getNodesByParam("id", idArr[i], null);
		if(nodes.length > 0){
			zTree.checkNode(nodes[0], true, true);
			nameArr.push(nodes[0]['name']);
		}
	}
	$("#"+id).val(values);
	$("#"+id+"_NAME").val(nameArr.join(', '));
}
window.onload = function() { 
	first();
	setTimeout(goto_query,1);
}; 

function doExcel(){
	var url = "${ctx}/admin/CustCheck/doExcel";
	doExcelAll(url);
}
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" value="${custCheck.address }" />
					</li>
					<li>
						<label>申请日期从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custCheck.dateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>	
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${custCheck.dateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
                        <label>主材管家</label>
                        <input type="text" id="housekeeper" name="housekeeper"/>
                    </li>
					<li>
						<label>主材结算状态</label>
						<house:xtdmMulit id="mainStatus" dictCode="CUSTCHECKSTS" selectedValue="${custCheck.mainStatus }"></house:xtdmMulit>
					</li>
					<li>
						<label>集成结算状态</label>
						<house:xtdmMulit id="intStatus" dictCode="CUSTCHECKSTS" selectedValue="${custCheck.intStatus }"></house:xtdmMulit>
					</li>
					<li>
						<label>软装结算状态</label>
						<house:xtdmMulit id="softSatus" dictCode="CUSTCHECKSTS" selectedValue="${custCheck.softSatus }"></house:xtdmMulit>
					</li>
					<li>
						<label>财务结算状态</label>
						<house:xtdmMulit id="finStatus" dictCode="CUSTCHECKSTS" selectedValue="${custCheck.finStatus }"></house:xtdmMulit>
					</li>
					<li>
						<label>是否验收</label>
						<house:xtdm id="isConfirm" dictCode="YESNO" value="${custCheck.isConfirm }"></house:xtdm>
					</li>
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType"  selectedValue="${custCheck.custType}"></house:custTypeMulit>
					</li>
					<li>
						<label>施工方式</label>
						<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${custCheck.constructType }"></house:xtdm>
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		
		<div class="clear_float"> </div>

		<div class="btn-panel" >
			<div class="btn-group-sm"  >
           		<house:authorize authCode="CUSTCHECK_ADD">
					<button id="add" type="button" class="btn btn-system " onclick="add()">新增</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEMAINSTS">
					<button id="btnMainRecv" type="button" class="btn btn-system " onclick="recv('Main')">主材接收</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEMAINRMK">
					<button id="btnMainRemark" type="button" class="btn btn-system " onclick="remark('Main')">主材备注</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATESOFTSTS">
					<button id="btnSoftRecv" type="button" class="btn btn-system " onclick="recv('Soft')">软装接收</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATESOFTRMK">
					<button id="btnSoftRemark" type="button" class="btn btn-system " onclick="remark('Soft')">软装备注</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEINTSTS">
					<button id="btnIntRecv" type="button" class="btn btn-system " onclick="recv('Int')">集成接收</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEINTRMK">
					<button id="btnIntRemark" type="button" class="btn btn-system " onclick="remark('Int')">集成备注</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEFINSTS">
					<button id="btnFinRecv" type="button" class="btn btn-system " onclick="recv('Fin')">财务接收</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEFINRMK">
					<button id="btnFinRemark" type="button" class="btn btn-system " onclick="remark('Fin')">财务备注</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_UPDATEFINCPL">
					<button id="btnFinCPL" type="button" class="btn btn-system " onclick="finCpl()">财务完成</button>
           		</house:authorize>
           		<house:authorize authCode="CUSTCHECK_VIEW">
					<button id="btnView" type="button" class="btn btn-system " onclick="view()">查看</button>
           		</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
		</div> 
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
