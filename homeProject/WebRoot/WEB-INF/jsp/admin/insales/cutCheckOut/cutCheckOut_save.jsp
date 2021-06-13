<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%
UserContext uc = (UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
String czybh=uc.getCzybh();
%>
<!DOCTYPE html>
<html>
<head>
<title>瓷砖加工管理--新增</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<style type="text/css">
.checkColor {
	background-color: yellow;
}
.panel-body {
    padding: 0px 1px 1px;
    padding-top: 1px;
    padding-right: 1px;
    padding-bottom: 1px;
    padding-left: 1px;
}
.form-search .ul-form li {
    min-height: 34px;
    max-height: 40px;
    padding-left: 1px;
}
.form-search .ul-form li input {
    width: 130px;
}
.btn-panel{
    padding: 0px 0px 1px;
}
.panel {
    margin-bottom: 5px;
}
</style>
<script type="text/javascript">
var hasEdit=false;//是否修改
function add(){
 	var refpks = $("#dataTable").getCol("refpk", false).join(",");
	Global.Dialog.showDialog("add",{
		  title:"新增明细",
		  url:"${ctx}/admin/cutCheckOut/goAddDetail",
		  postData:{refpks:refpks},
		  height:750,
		  width:1380,
		  resizable:false, 
		  returnFun: function(data){
			 var arr=$("#dataTable").jqGrid("getRowData");
			 $.each(data,function(k,v){
				 hasEdit=true;
				 v.lastupdate=getNowFormatDate();
				 v.lastupdatedby='${cutCheckOut.crtCzy}';
				 v.actionlog="ADD";
				 v.expired="F";
				 v.iscomplete="0";
			 });
	  		 var totalArr=arr.concat(data);
	  		 var params=JSON.stringify(totalArr); 
	  		 $("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/cutCheckOut/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");
		}
	});
}

function del(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
	var ret = selectDataTableRow();
	if(ret.iscomplete=="1"){
		art.dialog({
			content: "已经加工完成的不允许删除"
		});
		return;
	}
    if (ret) {
	   	art.dialog({
			 content:'确定删除该记录吗？',
			 lock: true,
			 ok: function () {
			 	hasEdit=true;
			    var rowNum=$("#dataTable").jqGrid('getGridParam','records');
			    $('#dataTable').delRowData(rowId);
			    var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/cutCheckOut/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");  
			    setTimeout(function(){moveToNext(rowId,rowNum);$("#delBtn").focus();},200);
			},
			cancel: function () {
				return true;
			}
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function delMul(){
	var rowId = $('#dataTable').jqGrid('getGridParam', 'selrow');
	var ret = selectDataTableRow();
    if (ret) {
	   	art.dialog({
			 content:'确定删除该领料单所有记录吗？',
			 lock: true,
			 ok: function () {
			 	var ids = $("#dataTable").jqGrid("getDataIDs");//资源id数组
			    for(var i=0;i<ids.length;i++){
			     	 var rowData = $("#dataTable").jqGrid('getRowData',ids[i]);
			     	 if(ret.ordername==rowData.ordername){
			     	 	if(rowData.iscomplete!='0'){
			     	 		art.dialog({
								content: "存在已经加工完成的，不允许删除"
							});
							return;
			     	 	}
			     	 	$('#dataTable').delRowData(ids[i]);
			     	 }
			    }
			 	hasEdit=true;
			    var rowNum=$("#dataTable").jqGrid('getGridParam','records');
			    var params=JSON.stringify($("#dataTable").jqGrid("getRowData"));
				$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/cutCheckOut/goTmpJqGrid",postData:{'params':params,'orderBy':'ordername'},page:1}).trigger("reloadGrid");  
			    setTimeout(function(){moveToNext(rowId,rowNum);$("#delBtn").focus();},200);
			},
			cancel: function () {
				return true;
			}
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
/**初始化表格*/
$(function(){
	
	$("#crtCzy").openComponent_employee({
		showValue:"${cutCheckOut.crtCzy}",
		showLabel:"${cutCheckOut.crtCzyDescr}",
		readonly:true
	});
	var canEdit=true;
	if('${cutCheckOut.m_umState}'=='V'){
		$(".onlyview").attr("disabled",true);
		canEdit=false;
	}
	Global.JqGrid.initJqGrid("dataTable",{
		height:$(document).height()-$("#content-list").offset().top-70,
		url:'${cutCheckOut.m_umState}'=='A'?'':'${ctx}/admin/cutCheckOut/goDetailJqGrid',
		postData:{no:'${cutCheckOut.no}'},
		cellEdit:canEdit,
		cellsubmit:'clientArray',
		//multiselect:true,
		//multiSelectBoxOnly: true,
		rowNum:10000,
		colModel : [
			{name: "ordername", index: "ordername", width: 107, label: "排序", sortable: true, align: "left"},
			{name: "iano", index: "iano", width: 107, label: "领料单号", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 84, label: "楼盘", sortable: true, align: "left",hidden:true},
			{name: "pk", index: "pk", width: 107, label: "pk", sortable: true, align: "left",hidden:true},
			//{name: "isselect", index: "isselect", width: 84, label: "是否选中", sortable: true, align: "left"},
			//{name: "selectBtn", index: "selectBtn", width: 84, label: "<input role='checkbox' id='selAll' class='cbox' type='checkbox' > ", sortable: false, align: "center", formatter:selectBtn,np:true},
			{name: "itemdescr", index: "itemdescr", width: 150, label: "材料名称", sortable: true, align: "left"},
			{name: "fixareadescr", index: "fixareadescr", width: 107, label: "区域", sortable: true, align: "left"},
			{name: "fixareapk", index: "fixareapk", width: 98, label: "区域", sortable: true, align: "left",hidden:true},
			{name: "qty", index: "qty", width: 55, label: "数量", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "cuttype", index: "cuttype", width: 55, label: "切割方式", sortable: false, align: "left",hidden:true},
			{name: "cuttypedescr", index: "cuttypedescr", width: 85, label: "切割方式", sortable: true, align: "left",editable: true, 
			edittype:"select",
				editoptions:{ 
		  			dataUrl: '${ctx}/admin/cutCheckOut/getCutTypeBySize',
					postData: function(){
						var ret = selectDataTableRow("dataTable");
						if(ret){
							return {
								size: ret.size
							};
						}
						return {size: ""};
					},
					buildSelect: function(e){
						var lists = JSON.parse(e);
						var html = "<option value=\"-0\" ></option>";
						for(var i = 0; lists && i < lists.length; i++){
							html += "<option value=\""+ lists[i].Code +"\">" + lists[i].Descr + "</option>"
						}
						return "<select style=\"font-family:宋体;font-size:12px\"> " + html + "</select>";
					},
		  			dataEvents:[{
	  					type:'change',
	  					fn:function(e){ 
	  						cutTypeClick(e);
	  					}
		  			}, 
		  		]}
	 		},
			{name: "cutfee", index: "cutfee", width: 72, label: "加工单价", sortable: true, align: "right",editable:true,edittype:'text',editrules:{number:true,required:true}},
			{name: "total", index: "total", width: 60, label: "总价", sortable: true, align: "right", sum: true,summaryType:'sum'},
			{name: "remarks", index: "remarks", width: 150, label: "加工备注", sortable: true, align: "left",editable:true,edittype:'textarea'},
			{name: "oldcuttypedescr", index: "oldcuttypedescr", width: 85, label: "原切割方式", sortable: true, align: "left"},
			{name: "itemreqremarks", index: "itemreqremarks", width: 150, label: "材料需求备注", sortable: true, align: "left"},
			{name: "iscompletedescr", index: "iscompletedescr", width: 70, label: "是否完成", sortable: true, align: "left",},
			{name: "completedate", index: "completedate", width: 120, label: "完成时间", sortable: true, align: "left", formatter: formatTime},
			{name: "refpk", index: "refpk", width: 95, label: "领料明细pk", sortable: true, align: "left"},
			{name: "itemcode", index: "itemcode", width: 84, label: "材料编号", sortable: true, align: "left"},
			{name: "size", index: "size", width: 56, label: "规格", sortable: true, align: "left",hidden:true},
			{name: "iaqty", index: "iaqty", width: 70, label: "领料数量", sortable: true, align: "right",hidden:true},
			{name: "allowmodify", index: "allowmodify", width: 60, label: "是否允许修改单价", sortable: true, align: "left",hidden:true},
			{name: "iscomplete", index: "iscomplete", width: 70, label: "是否完成", sortable: false, align: "left",hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
		],
        grouping : true , // 是否分组,默认为false
		groupingView : {
			groupField : [ 'ordername'], // 按照哪一列进行分组
			groupColumnShow : [ false], // 是否显示分组列
			groupText : ['<b>{0}({1})</b>'], // 表头显示的数据以及相应的数据量
			groupCollapse : false , // 加载数据时是否只显示分组的组信息
			groupDataSorted : false , // 分组中的数据是否排序
			groupOrder : [ 'asc'], // 分组后的排序
			groupSummary : [ true], // 是否显示汇总.如果为true需要在colModel中进行配置
			summaryType : 'max' , // 运算类型，可以为max,sum,avg</div>
			summaryTpl : '<b>Max: {0}</b>' ,
			showSummaryOnHide : true //是否在分组底部显示汇总信息并且当收起表格时是否隐藏下面的分组
		},
		beforeSaveCell: function (id, name, val, iRow, iCol) {
    		var rowData = $("#dataTable").jqGrid("getRowData", id);
      		if (name=="qty" && parseFloat(val)>parseFloat(rowData.iaqty)){
   				art.dialog({
                 	content: "不能大于领料数量！"
               	});
   				return rowData.iaqty;
      		}
    	 },
         afterSaveCell:function(id,name,val,iRow,iCol){
              hasEdit=true;
              var rowData = $("#dataTable").jqGrid('getRowData',id);
              rowData.lastupdate=new Date();
              rowData.lastupdatedby="${USER_CONTEXT_KEY.czybh}";
              rowData.actionlog="EDIT"; 
              var ids = $("#dataTable").jqGrid("getDataIDs");//资源id数组
              var sumTotal=0;
              $("#dataTable").setRowData(id, rowData);
              if(name=='qty'){
    		  	  $("#dataTable").setCell(id,'total',accMul(val,rowData.cutfee));
              }else if(name=='cutfee'){
              	  $("#dataTable").setCell(id,'total',accMul(val,rowData.qty));
              }
              for(var i=0;i<ids.length;i++){
              	  var ret = $("#dataTable").jqGrid('getRowData',ids[i]);
              	  if(ret.ordername==rowData.ordername){
              	  	sumTotal+=parseFloat(ret.total);
              	  }
              }
              setGridFootSum(id,'total',myRound(sumTotal,2));
              var total=myRound($("#dataTable").getCol('total',false,'sum'),2);   
              $("#dataTable").footerData('set', { "total": total});
              $("#amount").val(total);
           },
           gridComplete:function(){
           	   var total=$("#dataTable").getCol('total',false,'sum');   
               $("#amount").val(total);
               var ids = $("#dataTable").getDataIDs();
               for(var i=0;i<ids.length;i++){
                   var id = ids[i];
                   isAllowModify(id);
               }
               
               $(".jqfoot").each(function(i){
	        		var total= myRound($(this).children("td[aria-describedby=dataTable_total]").text(),2);
	        		$(this).children("td[aria-describedby=dataTable_total]").text(total);
	           });
           },
           beforeSelectRow:function(id){
        	    setTimeout(function(){
           			relocate(id);
          	    },200);
           },
	});
});

function selectBtn(v,x,r){
	if(r.ischeck=="1") return "<input type='checkbox' checked onclick='selectRow("+x.rowId+",this)' />";
	else return "<input type='checkbox' onclick='selectRow("+x.rowId+",this)' />";
}

function selectRow(id,e){
	if($(e).is(':checked')) {
		$("#dataTable").setCell(id,'isselect',"1");
		$(e).parent().parent().find("td").addClass("checkColor");
	}else{
		$("#dataTable").setCell(id,'isselect',"0");
		$(e).parent().parent().find("td").removeClass("checkColor");
	}
}

//切割费类型下拉框修改触发
function cutTypeClick(e){
	var rowid = $("#dataTable").getGridParam("selrow");
    var rowData = $("#dataTable").jqGrid('getRowData',rowid);
    var arr=$(e.target).val().split("-");
    var cutType=arr[0];
    var cutFee=parseFloat(arr[1]);
    var allowModify=arr[2];
    var qty=parseFloat(rowData.qty);
    $("#dataTable").setRowData(rowid, {
    	cuttype:cutType,
    	cutfee:cutFee,
    	total:accMul(cutFee,qty),
    	allowmodify:allowModify
    });
   
    var ids = $("#dataTable").jqGrid("getDataIDs");//资源id数组
    var sumTotal=0;
    for(var i=0;i<ids.length;i++){
     	 var ret = $("#dataTable").jqGrid('getRowData',ids[i]);
     	 if(ret.ordername==rowData.ordername){
     	 	sumTotal+=parseFloat(ret.total);
     	 }
    }
    setGridFootSum(rowid,'total',myRound(sumTotal,2));
    var total=myRound($("#dataTable").getCol('total',false,'sum'),2);   
    $("#dataTable").footerData('set', { "total": total});
    isAllowModify(rowid);
}

//设置切割费单价是否可编辑
function isAllowModify(id){
	var rowData = $("#dataTable").jqGrid('getRowData',id);
	$("#dataTable").jqGrid('setCell', id, 'cutfee', '', 'review-'+id);//初始化cell样式
	$("#dataTable").jqGrid('setCell', id, 'cuttypedescr', '', 'review2-'+id);//初始化cell样式
	$("#dataTable").jqGrid('setCell', id, 'remarks', '', 'review3-'+id);//初始化cell样式
	$("#dataTable").jqGrid('setCell', id, 'qty', '', 'review4-'+id);//初始化cell样式
	if(rowData.allowmodify!='1'){
		$("#dataTable").jqGrid('setCell', id, 'cutfee', '', 'not-editable-cell');//单元格不可编辑
	}else{
		$(".review-"+id).removeClass('not-editable-cell');//单元格可编辑
	}
	
	if('${cutCheckOut.m_umState}'=='M'){
		if(rowData.iscomplete=='1'){
			$("#dataTable").jqGrid('setCell', id, 'cuttypedescr', '', 'not-editable-cell');//单元格不可编辑
			$("#dataTable").jqGrid('setCell', id, 'remarks', '', 'not-editable-cell');//单元格不可编辑
			$("#dataTable").jqGrid('setCell', id, 'qty', '', 'not-editable-cell');//单元格不可编辑
		}else{
			$(".review2-"+id).removeClass('not-editable-cell');//单元格可编辑
			$(".review3-"+id).removeClass('not-editable-cell');//单元格可编辑
			$(".review4-"+id).removeClass('not-editable-cell');//单元格可编辑
		}
	}
}

function save(){
	var cutCheckOutDetailJson = Global.JqGrid.allToJson("dataTable");
	var datas=cutCheckOutDetailJson.datas;
	if(datas.length==0){
		art.dialog({
			content: "明细为空，不允许保存",
		});
		return;
	}
	for(var i in datas){
		if(datas[i].cuttype==""){
			art.dialog({
				content: "存在切割类型为空的记录，不允许保存",
			});
			return;
		}
		if(datas[i].total==0){
			art.dialog({
				content: "存在加工费总价为0的记录，不允许保存",
			});
			return;
		}
	}
	var param = {"cutCheckOutDetailJson": JSON.stringify(cutCheckOutDetailJson)};
	Global.Form.submit("dataForm","${ctx}/admin/cutCheckOut/doSave",param,function(ret){
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

//arg1乘以arg2的精确结果
function accMul(arg1, arg2){
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try { m += s1.split(".")[1].length; } catch (e) { }
	try { m += s2.split(".")[1].length; } catch (e) { }
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

function doCloseWin(){
 	if(hasEdit && '${cutCheckOut.m_umState}'!='V'){
 		art.dialog({
       		content: '是否保存被更改的数据？',
	        lock: true,
	        ok: function () {
	       		save();
	       		
	        },
		    cancel: function () {
	      		Global.Dialog.closeDialog(false);
		    }
	   	});
 	}else{
 		Global.Dialog.closeDialog(false);
 	}	
} 

function viewCheckIn(){
	Global.Dialog.showDialog("viewCheckIn",{
		title:"加工入库明细",
	  	url:"${ctx}/admin/cutCheckOut/goViewCheckIn",
	  	height:600,
		width:950,
		postData:{no:"${cutCheckOut.no }"},
	});
}
</script>
</head>
<body>
	<div class="body-box-form">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs">
					<button type="button" id="saveBtn" class="btn btn-system onlyview" onclick="save()">保存</button>
					<c:if test="${cutCheckOut.m_umState!='A'}">
						<button id="addBtn"  type="button" class="btn btn-system " onclick="viewCheckIn()" >加工入库明细</button>
					</c:if>
					<button type="button" class="btn btn-system " onclick="doCloseWin()">关闭</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info">
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" /> 
					<input type="hidden" name="m_umState" value="${cutCheckOut.m_umState }" /> 
					<input type="hidden" name="no" value="${cutCheckOut.no }" /> 
					<ul class="ul-form">
						<li><label>创建日期</label> <input type="text" id="crtDate"
							name="crtDate" class="i-date" style="width:160px;"
							value="<fmt:formatDate value='${cutCheckOut.crtDate }' pattern='yyyy-MM-dd'/>"
							readonly="readonly"  />
						</li>
						<li><label>创建人</label> <input type="text" id="crtCzy"
							name="crtCzy" style="width:160px;" value="${cutCheckOut.crtCzy }" />
						</li>
						<li><label>加工费</label> <input type="text" id="amount"
							name="amount" value="${cutCheckOut.amount}" readonly="readonly"/>
						</li>
						<li>
							<label>状态</label>
							<house:xtdm  id="status" dictCode="CUTCHECKOUTSTAT" style="width:160px;" disabled="true" value="${cutCheckOut.status }"></house:xtdm>
						</li>
						<c:if test="${cutCheckOut.m_umState=='V'}">
							<li><label>出库时间</label> <input type="text" id="submitDate"
								name="submitDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${cutCheckOut.submitDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"
								readonly="readonly"  />
							</li>
							<li><label>完成时间</label> <input type="text" id="completeDate"
								name="completeDate" class="i-date" style="width:160px;"
								value="<fmt:formatDate value='${cutCheckOut.completeDate }' pattern='yyyy-MM-dd HH:mm:ss'/>"
								readonly="readonly"  />
							</li>
						</c:if>
						<li >
							<label class="control-textarea">备注</label>
							<textarea id="remarks" name="remarks">${cutCheckOut.remarks }</textarea>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button id="addBtn"  type="button" class="btn btn-system onlyview" onclick="add()" >新增</button>
				<button id="delBtn" type="button" class="btn btn-system onlyview" onclick="del()">删除</button>
				<button id="delMulBtn" type="button" class="btn btn-system onlyview" onclick="delMul()">整单删除</button>
			</div>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</div>
</body>
</html>


