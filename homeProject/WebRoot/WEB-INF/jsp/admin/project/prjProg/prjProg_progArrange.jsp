<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>修改PrjProg</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_prjProgTemp.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
	var progTempDt = {};//工程进度模板明细
$(function(){
	var lastCellRowId;
	function getData(data){
		if (!data) return;
		if(data.CustType!="" && data.CustType!="${customer.custType}"){
			$("#openComponent_prjProgTemp_prjProgTempNo").val("");
			$("#prjProgTempNoDescr").val("");
			art.dialog({
				content:"模板适用客户类型不匹配！",
			});
			return false;
		}
		$('#prjProgTempNoDescr').val(data.Descr);
	} 
	$("#prjProgTempNo").openComponent_prjProgTemp({callBack:getData,showValue:'${customer.prjProgTempNo}'});
	$("#openComponent_prjProgTemp_prjProgTempNo").attr("readonly",true);
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/prjProg/goJqGrid",
			postData:{custCode:"${customer.code}",prjProgTempNo:$("#prjProgTempNo").val()},
		    rowNum:10000000,
			height:380,
			cellEdit:true,
			cellsubmit: "clientArray",
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "PK", index: "PK", width: 170, label: "PK", sortable: true, align: "left",hidden:true},
				{name: "CustCode", index: "CustCode", width: 170, label: "custcode", sortable: false, align: "left",hidden:true},
				{name: "PrjItem", index: "PrjItem", width: 170, label: "施工项目", sortable: false, align: "left", hidden:true},
				{name: "prjdescr", index: "prjdescr", width: 120, label: "施工项目", sortable: false, align: "left"},
				{name: "consday", index: "consday", width: 120, label: "施工天数", sortable: false, align: "right",editable:true,edittype:'text'},
				{name: "PlanBegin", index: "PlanBegin", width: 80, label: "计划开始日", sortable: false, align: "left",formatter: formatDate},
				{name: "PlanEnd", index: "planEnd", width: 80, label: "计划结束日", sortable: false, align: "left",formatter: formatDate},
				{name: "consseq", index: "consseq", width: 80, label: "施工顺序", sortable: false, align: "right"},
				{name: "spaceday", index: "spaceday", width: 80, label: "间隔天数", sortable: false, align: "right", hidden: true},
				{name: "LastUpdate", index: "LastUpdate", width: 143, label: "最后修改时间", sortable: false, align: "left", formatter: formatTime},
				{name: "LastUpdatedBy", index: "LastUpdatedby", width: 70, label: "操作人员", sortable: false, align: "left"},
				{name: "Expired", index: "Expired", width: 70, label: "是否过期", sortable: false, align: "left"},
				{name: "ActionLog", index: "ActionLog", width: 70, label: "操作代码", sortable: false, align: "left"},
			], 
			loadonce:true,
			 beforeSelectRow:function(id){
        	    setTimeout(function(){
           			relocate(id);
          	    },100);
             },
 		};
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
	
	//从模板导入
	$("#importTemp").on("click",function(){
		var prjItems = $("#dataTable").getCol("PrjItem", false).join(",");
		var tempNo= $("#prjProgTempNo").val();
        if(tempNo==""){
	        art.dialog({
				content:"请先选择模板编号！",
			});
			return;
        }
       	Global.Dialog.showDialog("importTemp",{ 
       	  title:"工程进度——从模板导入",
       	  url:"${ctx}/admin/prjProg/goImportTemp",
       	  postData:{tempNo:tempNo,prjItems:prjItems,custCode:"${customer.code}"},
       	  height: 600,
       	  width:900,
       	  returnFun:function(data){
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,rowData){
						var json={
							PrjItem:rowData.prjitem,prjdescr:rowData.note,
							consday:rowData.consday,consseq:i+1+ids.length,spaceday:rowData.spaceday,
							LastUpdate:new Date().getTime(),LastUpdatedBy:'${USER_CONTEXT_KEY.czybh}',
							Expired:"F",ActionLog:"ADD"
						};
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), json);
					});	
				}
		  }
        });
	});
	//新增
	$("#add").on("click",function(){
		var ids=$("#dataTable").jqGrid("getDataIDs");
		var prjItems = $("#dataTable").getCol("PrjItem", false).join(",");
       	Global.Dialog.showDialog("add",{ 
       	  title:"工程进度——新增",
       	  url:"${ctx}/admin/prjProg/goArrangeAdd",
       	  postData:{prjItems:prjItems},
       	  height:300,
		  width:400,
       	  returnFun:function(data){
				var ids = $("#dataTable").jqGrid("getDataIDs");
				var json={
					PrjItem:data.prjItem,prjdescr:data.prjDescr,
					consday:data.consDay,consseq:1+ids.length,
					LastUpdate:new Date().getTime(),LastUpdatedBy:'${USER_CONTEXT_KEY.czybh}',
					Expired:"F",ActionLog:"ADD"
				};
				getProgTempDt(data.prjItem);
				if (null == progTempDt.spaceDay) json.spaceday = 0;
				else json.spaceday = progTempDt.spaceDay;
				$("#dataTable").jqGrid("addRowData", (1+ids.length), json);
		  }
        });
	});
	//插入
	$("#insert").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		var prjItems = $("#dataTable").getCol("PrjItem", false).join(",");
		var posi=id-1;
		var rowData = $("#dataTable").jqGrid('getRowData');
		var ids=$("#dataTable").jqGrid("getDataIDs");
		var ret = $("#dataTable").jqGrid('getRowData',ids[ids.length-1]);
		if(!id){
			art.dialog({content: "请选择一条记录！"});
			return false;
		} 
		Global.Dialog.showDialog("insert", {
			title : "工程进度--插入",
			url : "${ctx}/admin/prjProg/goArrangeAdd",
			postData:{prjItems:prjItems},
		    height:300,
		    width:400,
			returnFun : function(data) {
				var json={
					PrjItem:data.prjItem,prjdescr:data.prjDescr,
					consday:data.consDay,
					LastUpdate:new Date().getTime(),LastUpdatedBy:'${USER_CONTEXT_KEY.czybh}',
					Expired:"F",ActionLog:"ADD"
				};
				getProgTempDt(data.prjItem);
				if (null == progTempDt.spaceDay) json.spaceday = 0;
				else json.spaceday = progTempDt.spaceDay;
				rowData.splice(posi++,0,json);	
				$("#dataTable").jqGrid("clearGridData");
				$.each(rowData, function(i,r){
					r['consseq']=i;//设置顺序
					$("#dataTable").jqGrid("addRowData", i+1, r);
				});
				$("#dataTable").jqGrid("setSelection",id);//选中此行
				$('.ui-jqgrid .ui-jqgrid-bdiv').scrollTop(24*id);//滚动条滚动
			}
		});
	});
	//删除
	$("#del").on("click",function(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！"});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
				var rowData = $("#dataTable").jqGrid('getRowData');
				$("#dataTable").jqGrid("clearGridData");
				$.each(rowData, function(i,r){
					r['consseq']=i;//设置顺序
					$("#dataTable").jqGrid("addRowData", i+1, r);
				});
			},
			cancel: function () {
					return true;
			}
		}); 
	});	
		
	//保存
	$("#saveBtn").on("click",function(){
			var ids=$("#dataTable").jqGrid("getDataIDs");
			if(ids.length==0){
				art.dialog({
					content:"未编排进度！",
				});
				return;
			}
			if($("#confirmBegin").val()==""){
				art.dialog({
					content:"开工日期不能为空！",
				});
				return;
			}
			count();//保存前重新生成开工日期
			var rets = $("#dataTable").jqGrid("getRowData");
			var params = {"prjProgJson": JSON.stringify(rets)};
			Global.Form.submit("page_form","${ctx}/admin/prjProg/doProgArrange",params,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			});
		});
			
});
//获取工程进度模板明细
function getProgTempDt(prjItem) {
	progTempDt = {};
	$.ajax({
		url:"${ctx}/admin/prjProg/getProgTempDt",
		type:"post",
		data:{
			tempNo:$("#prjProgTempNo").val(),
			prjItem:prjItem
		},
		dataType:"json",
		async:false,
		error: function (obj) {
			showAjaxHtml({"hidden": false, "msg": "获取数据出错"});
		},
		success:function (obj) {
			progTempDt = obj;
		}
	});
}
function query(){
	$("#dataTable").jqGrid("setGridParam",{postData:{custCode:"${customer.code}"},page:1}).trigger("reloadGrid");
}
//向上
function up(){
	var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
	var replaceId = parseInt(rowId)-1;
	if(rowId){
		if(rowId>1){
		    var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
			var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
			replace(rowId,replaceId,"dataTable");
			scrollToPosi(replaceId,"dataTable");
			$("#dataTable").setCell(rowId,"consseq",ret1.consseq);
			$("#dataTable").setCell(replaceId,"consseq",ret2.consseq);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
//向下
function down(){
	var rowId = $("#dataTable").jqGrid("getGridParam", "selrow");
	var replaceId = parseInt(rowId)+1;
	var rowNum = $("#dataTable").jqGrid("getGridParam","records");
	if(rowId){
		if(rowId<rowNum){
			var ret1 = $("#dataTable").jqGrid("getRowData", rowId);
			var ret2 = $("#dataTable").jqGrid("getRowData", replaceId);
			scrollToPosi(replaceId,"dataTable");
			replace(rowId,replaceId,"dataTable");
			$("#dataTable").setCell(rowId,"consseq",ret1.consseq);
			$("#dataTable").setCell(replaceId,"consseq",ret2.consseq);
		}
	}else{
		art.dialog({
			content: "请选择一条记录"
		});
	}
}
//自动计算施工工期
function count(){
	var confirmBegin=$("#confirmBegin").val();
	var sumConsDay=0;
	if(confirmBegin==""){
		art.dialog({
			content:"请选择开工日期！",
		});
		return;
	}
	var ids = $("#dataTable").getDataIDs();
	if(ids.length==0){
		art.dialog({
			content:"请先添加数据！",
		});
		return;
	}
	var rows = $("#dataTable").jqGrid("getRowData");
	//设置第一个节点的开始结束时间
	rows[0].PlanBegin=confirmBegin;//第一个节点开始时间为楼盘开工时间
	rows[0].PlanEnd=getNewDate(confirmBegin, rows[0].consday-1);//第一个节点结束时间为开工时间+节点施工天数-1
	sumConsDay+=parseFloat(rows[0].consday);//累加总施工工期
	lastPlanEnd=rows[0].PlanEnd;//保存上一个节点的结束时间
	for(var i=1;i<ids.length;i++){
		rows[i].PlanBegin=getNewDate(lastPlanEnd, 1+parseInt(rows[0].spaceday));//开始时间为上一个节点的结束时间+1+工程进度模板明细对应施工项目间隔天数 modify by zb on 20200403
		rows[i].PlanEnd=getNewDate(lastPlanEnd, rows[i].consday);//结束时间为上一个节点的结束时间+节点施工工期
		lastPlanEnd=rows[i].PlanEnd;//更新上一个节点的结束时间
		sumConsDay+=parseFloat(rows[i].consday);//累加总施工工期
	}
	$("#constructDay").val(sumConsDay);
	$("#dataTable").jqGrid("clearGridData");
	$.each(rows, function(i,r){
		$("#dataTable").jqGrid("addRowData", i+1, r);
	});
}
//日期加上天数得到新的日期  
//dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD  
function getNewDate(dateTemp, days) {  
    var dateTemp = dateTemp.split("-");  
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式    
    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);  
    var rDate = new Date(millSeconds);  
    var year = rDate.getFullYear();  
    var month = rDate.getMonth() + 1;  
    if (month < 10) month = "0" + month;  
    var date = rDate.getDate();  
    if (date < 10) date = "0" + date;  
    return (year + "-" + month + "-" + date);  
}
</script>

</head>
    
<body onunload="closeWin()">
<div class="body-box-form" >
	<div class="content-form">
		<!--panelBar-->
			<div class="panel panel-system" >
    			<div class="panel-body" >
    				 <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="infoBox" id="infoBoxDiv"></div>
        <div class="panel panel-info" >  
			<div class="panel-body">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${customer.code}" readonly="readonly"/>
							</li>
							<li>
								<label>客户名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;"  value="${customer.descr}" readonly="readonly"/>
							</li>
							<li>
								<label>客户状态</label>
								<house:xtdm id="status" dictCode="CUSTOMERSTATUS" value="${customer.status}" disabled="true" readonly="readonly"></house:xtdm>	
							</li>
							<li>
								<label>设计师</label>
								<input type="text" id="designManDescr" name="designManDescr" style="width:160px;"  value="${customer.designManDescr}" readonly="readonly"/>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${customer.address}" readonly="readonly"/>
							</li>
							<li>
								<label>施工工期</label>
								<input type="text" id="constructDay" name="constructDay" style="width:160px;"  value="${customer.constructDay}" readonly="readonly"/>
							</li>
							<li>
								<label>模板编号</label>
								<input type="text" id="prjProgTempNo" name="prjProgTempNo" style="width:160px;"  value="${customer.prjProgTempNo}"  readonly="readonly"/>
							</li>
							<li>
								<label>模板名称</label>
								<input type="text" id="prjProgTempNoDescr" name="prjProgTempNoDescr" style="width:160px;"  value="${customer.prjProgTempNoDescr}" readonly="readonly"/>
							</li>
							<li>
								<label>开工日期</label>
								<input type="text" id="confirmBegin" name="confirmBegin" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${customer.confirmBegin }' pattern='yyyy-MM-dd'/>" />
							</li>
						</ul>	
			</form>
			</div>
			</div>
		<div class="btn-panel" >
    	<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system " id="importTemp">
					<span>从模板导入</span>
				</button>
				<button type="button" class="btn btn-system " id="add">
					<span>新增</span>
				</button>
				<button type="button" class="btn btn-system " id="insert">
					<span>插入</span>
				</button>
				<button type="button" class="btn btn-system " id="del">
					<span>删除</span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="up()">
					<span>向上 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="down()">
					<span>向下 </span>
				</button>
				<button style="align:left" type="button" class="btn btn-system "
					 onclick="count()">
					<span>自动计算施工工期 </span>
				</button>
		</div>
	</div>
	<div class="pageContent">
		<div id="content-list">
			<table id= "dataTable"></table>
		</div> 
	</div>
	</div>
</div>
</body>
</html>
