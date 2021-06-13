<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html>
<head>
	<title>材料加工管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 

/**初始化表格*/
$(function(){

	Global.LinkSelect.initSelect("${ctx}/admin/item/itemTypeByAuthority","itemType1");
	if("${itemProcess.m_umState}"!="A"){
		Global.LinkSelect.setSelect({firstSelect:"itemType1",
			firstValue:"${itemProcess.itemType1}",						
		});	
		
		$("#itemType1").attr("disabled",true);
	}
	if ("${itemProcess.m_umState}"!="V") {
		$("#saveBtn").show();
	}
	
	if ("${itemProcess.m_umState}"=="A" || "${itemProcess.m_umState}"=="M") {
		$("#updateBtn").show();
		$("#addBtn").show();
		$("#delBtn").show();	
	}
	
    $("#processItemWHCode").openComponent_wareHouse({
    		showValue:"${itemProcess.processItemWHCode}",
    		showLabel:"${itemProcess.processItemWHCodeDescr}",
    		condition:{czybh:'${czybh}'},
    		callBack:function(){
    			validateRefresh('openComponent_wareHouse_processItemWHCode')},
    });
    
    $("#sourceItemWHCode").openComponent_wareHouse({
    	showValue:"${itemProcess.sourceItemWHCode}",
    	showLabel:"${itemProcess.sourceItemWHCodeDescr}",
    	condition:{czybh:'${czybh}'},
	 	callBack:function(){validateRefresh('openComponent_wareHouse_sourceItemWHCode')},
    });
    
	Global.JqGrid.initJqGrid("itemProcessDataTable",{
		    url: "${itemProcess.m_umState}"=="A"? "":"${ctx}/admin/itemProcess/goItemProcessDetailJqGrid?no="+"${itemProcess.no}", 
			height:$(document).height()-$("#content-list").offset().top-70,
			rowNum:10000,
			colModel : [
				{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true },
				{name:"no", index:"no", width:80, label:"编号", sortable:true ,align:"left",hidden:true},
				{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left"},
				{name:"itemdescr", index:"itemdescr", width:140, label:"材料名称", sortable:true ,align:"left"},
				{name:"itemtransformno", index:"itemtransformno", width:80, label:"材料转换编号", sortable:true ,align:"left",hidden:true},
				{name:"qty", index:"qty", width:80, label:"加工数量", sortable:true ,align:"right"},
				{name:"uomdescr", index:"uomdescr", width:80, label:"单位", sortable:true ,align:"right"},
				{name:"cost", index:"cost", width:80, label:"成本价", sortable:true ,align:"right",hidden:true},
				{name:"sumcost", index:"sumcost", width:80, label:"总价", sortable:true ,align:"left",hidden:true},
				{name:"itemtransformremarks", index:"itemtransformremarks", width:150, label:"加工说明", sortable:true ,align:"left"},     
				{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
				{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
				{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
				{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
								
            ], 
	});
	
	Global.JqGrid.initJqGrid("itemSourceDataTable",{
		url: "${itemProcess.m_umState}"=="A"? "":"${ctx}/admin/itemProcess/goItemProcessSourceDetailJqGrid?no="+"${itemProcess.no}", 
		height:$(document).height()-$("#content-list").offset().top-70,
		rowNum:10000,
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true },
			{name:"processdetailpk", index:"processdetailpk", width:80, label:"processdetailpk", sortable:true ,align:"left",hidden:true },
			{name:"itemtransformno", index:"itemtransformno", width:80, label:"材料转换编号", sortable:true ,align:"left",hidden:true},
			{name:"itemcode", index:"itemcode", width:80, label:"材料编号", sortable:true ,align:"left"},
			{name:"itemdescr", index:"itemdescr", width:140, label:"材料名称", sortable:true ,align:"left"},
			{name:"qty", index:"qty", width:80, label:"源材料数量", sortable:true ,align:"right"},
			{name:"uomdescr", index:"uomdescr", width:80, label:"单位", sortable:true ,align:"right"},
			{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
			{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
			{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
			{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"},  					
        ], 
	});
	
	//表单验证
	$("#dataForm").bootstrapValidator({
		message :"This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating :"glyphicon glyphicon-refresh"
		},
		fields: {  
			itemType1:{  
				validators: {  
					notEmpty: {  
						message:"材料类型1不能为空"  
					}
				}  
			},
			
			openComponent_wareHouse_processItemWHCode:{  
				validators: {  
					 remote: {
				           message: '',
				           url: '${ctx}/admin/wareHouse/getWareHouse',
				           data: getValidateVal,  
				           delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				    },
					notEmpty: {  
						message:"加工材料仓库不能为空"  
					}
				}  
			},
			openComponent_wareHouse_sourceItemWHCode:{  
				validators: {  
					 remote: {
				           message: '',
				           url: '${ctx}/admin/wareHouse/getWareHouse',
				           data: getValidateVal,  
				           delay: 4 //这里特别要说明，必须要加此属性，否则用户输入一个字就会访问后台一次，会消耗大量的系统资源，
				     },
					 notEmpty: {  
						message:"源材料仓库不能为空"  
					 }
				}  
			},
			
		},
		submitButtons :"input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	
	
});

//新增
function add(){
	var itemType1=$("#itemType1").val();
	if(!itemType1){
		art.dialog({
			content : "请选择材料类型1！",
			width : 200
		});
		return;
	}		
	Global.Dialog.showDialog("add",{
		title:"加工材料新增",
		url:"${ctx}/admin/itemProcess/goDetailAdd",
		postData:{
			m_umState : "A", itemType1:itemType1
	    },
		height:500,
		width:760,
		returnFun:function(data){
			if(data){
				var json = {
					itemcode: data.itemCode,
					itemdescr: data.itemDescr,
			        uomdescr:data.uomDescr,  
					itemtransformno: data.itemTransformNo,
					itemtransformremarks: data.itemTransformRemarks,
					qty: data.qty,
					lastupdate: new Date(),
					lastupdatedby:"${czybh}",
					expired:"F",
					actionlog:"ADD",
				}
				Global.JqGrid.addRowData("itemProcessDataTable",json);
				$("#itemType1").attr("disabled",true);
			}
			
			if(data.tableData){
				$.each(data.tableData,function(k,v){
	               	var json = { 
	               		itemtransformno: data.itemTransformNo,
	              		itemcode: v.itemcode,
	              		itemdescr: v.itemdescr,
	                    qty:v.qty,   
	                    uomdescr:v.uomdescr,   
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdate: new Date(),
	                	lastupdatedby:"${czybh }",
	               	}                     
					Global.JqGrid.addRowData("itemSourceDataTable",json); 	
				});  
			}	
		} 	
	});
};

//编辑
function update(m_umState){
	var itemType1=$("#itemType1").val();
	var ret = selectDataTableRow("itemProcessDataTable");
	if(!ret){
		art.dialog({
			content:"请选择一条明细进行编辑",
		});
		return
	}
	var title="加工材料--编辑";
	if(m_umState=="V"){
		title="加工材料--查看";	
	} 
	Global.Dialog.showDialog("addUpdate",{
		title: title,
		url:"${ctx}/admin/itemProcess/goDetailAdd",
		height:500,
		width:760,
		postData:{
			m_umState : m_umState, itemType1: itemType1, itemTransformNo: ret.itemtransformno,
			itemCode : ret.itemcode, itemTransformRemarks: ret.itemtransformremarks,
			itemDescr : ret.itemdescr, qty: ret.qty, cost: ret.qty
	    },
		returnFun:function(data){
			if(data.tableData){
				delItemSourceDataTable(ret.itemtransformno);
				$.each(data.tableData,function(k,v){
	               	var json = { 
	               		itemtransformno: data.itemTransformNo,
	              		itemcode: v.itemcode,
	                	itemtransformno: data.itemTransformNo,
	              		itemcode: v.itemcode,
	              		itemdescr: v.itemdescr,
	                    qty:v.qty,   
	                    uomdescr:v.uomdescr,   
	                    expired: "F",
	                    actionlog: "ADD",
	                    lastupdate: new Date(),
	                	lastupdatedby:"${czybh }",
	               	}  
					Global.JqGrid.addRowData("itemSourceDataTable",json);
				});   	
			}
			if(data){
				var id = $("#itemProcessDataTable").jqGrid("getGridParam","selrow");
				var json = {
					itemcode: data.itemCode,
					itemtransformno: data.itemTransformNo,
		     		itemtransformremarks: data.itemTransformRemarks,
					qty: data.qty,
					lastupdate: new Date(),
					lastupdatedby:"${czybh }",
					expired:"F",
					actionlog:"ADD",
				}
				$("#itemProcessDataTable").setRowData(id, json);
			}
		} 
	});
}

function del(){
	var id = $("#itemProcessDataTable").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({content:"请选择一条记录进行删除操作！",width: 200});
		return false;
	}
	art.dialog({
		content:"删除该记录？",
		lock: true,
		width: 100,
		height: 80,
		ok: function () {
			var ret = selectDataTableRow("itemProcessDataTable");
			delItemSourceDataTable(ret.itemtransformno);
			Global.JqGrid.delRowData("itemProcessDataTable",id);
	
			var recordData=$('#itemProcessDataTable').jqGrid('getGridParam','records');
			if(recordData==0){
				$("#itemType1").removeAttr("disabled");
			}
			
		},
		cancel: function () {
			return true;
		}
	});	
	
}

function delItemSourceDataTable(itemTransformNo){
	if(!itemTransformNo){
		return false;
	}
	var ids = $("#itemSourceDataTable").getDataIDs();
    $.each(ids,function(k, id){
	 	var row = $("#itemSourceDataTable").jqGrid('getRowData', id);
	 	if(row.itemtransformno==itemTransformNo){
			$("#itemSourceDataTable").jqGrid('delRowData',id);
		}
	});	
	 
}

//保存
function save(){
	$("#dataForm").bootstrapValidator("validate");
	if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	var detailJson=$('#itemProcessDataTable').jqGrid("getRowData");
	var param = {"detailJson": JSON.stringify(detailJson)};
	Global.Form.submit("dataForm","${ctx}/admin/itemProcess/doSave",param,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000, 
				beforeunload: function () {
    				closeWin();
			    }
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
    		art.dialog({
				content: ret.msg,
				width: 200
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
					<button type="button" class="btn btn-system" id="saveBtn" onclick="save()" style="display: none">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<input type="hidden" name="m_umState" id="m_umState" value="${itemProcess.m_umState}"/>
						<ul class="ul-form">
							<div class="validate-group row">
							    <li>
									<label>材料加工编号</label>
									<input type="text" id="no" name="no" style="width:160px;" placeholder="保存自动生成" value="${itemProcess.no }" readonly="readonly"/>                                             
								</li>
								<li>
									<label>申请人</label>
										<input type="hidden" id="appCzy" name="appCzy" style="width:160px;" value="${itemProcess.appCzy}" disabled='true'/>
										<input type="text" id="appCzy" name="appCzy" style="width:160px;" value="${itemProcess.appCzyDescr}" disabled='true' />
								</li>
								<li>
									<label>审核人</label>
									<input type="hidden" id="confirmCzy" name="confirmCzy" style="width:160px;"  value="${itemProcess.confirmCzy}" disabled='true'/>
									<input type="text" id="confirmCzyDescr" name="confirmCzyDescr" style="width:160px;"  value="${itemProcess.confirmCzyDescr}" disabled='true'/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>材料类型1</label> 
									 <select id="itemType1" name="itemType1"></select>
								</li>
								
								<li>
									<label>申请日期</label>
									<input type="text" style="width:160px;" id="appDate" name="appDate" class="i-date" value="<fmt:formatDate value='${itemProcess.appDate}' pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
								</li>
								<li>
									<label>审核日期</label>
									<input type="text" style="width:160px;" id="confirmDate" name="confirmDate" class="i-date" value="<fmt:formatDate value='${itemProcess.confirmDate}'  pattern='yyyy-MM-dd'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" disabled='true'/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>加工材料仓库</label>
									<input type="text" id="processItemWHCode" name="processItemWHCode"  value="${itemProcess.processItemWHCode }"/>
								</li>
								
								<li>
									<label>状态</label>
									<house:xtdm id="status" dictCode="PROCESSSTATUS"  value="${itemProcess.status }" disabled="true"></house:xtdm>
								</li>
								

							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>源材料仓库</label>
									<input type="text" id="sourceItemWHCode" name="sourceItemWHCode" value="${itemProcess.sourceItemWHCode }"/>
								</li>
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2" style="width:450px" >${itemProcess.remarks }</textarea>
								</li>
								
						   	</div>
							
						</ul>	
				</form>
			</div>
		</div>
		<div  class="container-fluid" id="id_detail">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">加工材料明细</a></li>
				<li class=""><a href="#tab2" data-toggle="tab">源材料明细</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab1" class="tab-pane fade in active">
					<div class="panel panel-system" >
					    <div class="panel-body" >
					    	<div class="btn-group-xs" >
								<button type="button" class="btn btn-system" id="addBtn" onclick="add()"  style="display: none" >
									<span>新增</span>
								</button>
								<button type="button" class="btn btn-system" id="updateBtn" onclick="update('M')" style="display: none">
									<span>编辑</span>
								</button>
								<button type="button" class="btn btn-system" id="delBtn" onclick="del()" style="display: none">
									<span>删除</span>
								</button>
								<button type="button" class="btn btn-system" id="viewBtn"
									 onclick="doExcelNow('加工材料','itemProcessDataTable','dataForm');">
									<span>导出excel</span>
								</button>
								
								<button type="button" class="btn btn-system" id="viewBtn" onclick="update('V')">
									<span>查看</span>
								</button>
								
							</div>
						</div>
					</div>
					<div id="content-list">
						<table id="itemProcessDataTable"></table>
					</div>
				</div>
				<div id="tab2" class="tab-pane fade ">
					<div id="content-list2">
						<table id="itemSourceDataTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
