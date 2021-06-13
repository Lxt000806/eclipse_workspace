<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/intProgDetail/goJqGrid",
			postData:{custCode:"${custIntProg.custcode}"},
		    rowNum:10000000,
			height:390,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "pk", index: "pk", width: 60, label: "编号", sortable: true, align: "left", hidden: true},
                {name: "no", index: "no", width: 80, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "custcode", index: "custcode", width: 130, label: "客户编号", sortable: true, align: "left", hidden: true},
				{name: "cancelreson", index: "cancelreson", width: 110, label: "退单原因", sortable: true, align: "left", hidden: true},
				{name: "respart", index: "respart", width: 110, label: "责任方", sortable: true, align: "left", hidden: true},
				{name: "type", index: "type", width: 110, label: "类型", sortable: true, align: "left", hidden: true},
                {name: "iscupboard", index: "iscupboard", width: 106, label: "是否橱柜", sortable: true, align: "left", hidden: true},
				{name: "typedescr", index: "typedescr", width: 110, label: "类型", sortable: true, align: "left"},
				{name: "date", index: "date", width: 103, label: "日期", sortable: true, align: "left", formatter: formatDate},
				{name: "respartdescr", index: "respartdescr", width: 97, label: "责任方", sortable: true, align: "left"},
				{name: "cancelresondescr", index: "cancelresondescr", width: 130, label: "退单原因", sortable: true, align: "left"},
				{name: "iscup", index: "iscup", width: 106, label: "是否橱柜", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 225, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 151, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 91, label: "最后修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 96, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 93, label: "操作", sortable: true, align: "left"}
			], 
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
				},
 		};
       //初始化集成进度明细
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
});
 
	function add(button, type) {
		//用于判断按钮
		var flag="";
		if (button == '退单登记') {
			flag = "?isReturn=yes";
		}
		if (button == '不能安装') {
			flag = "?cantInstall=yes";
		}
		if (button == '加急单' || button == '售中/售后' ) {
			flag +=flag==""?"?showZrf=yes":"&&showZrf=yes";
		}
		Global.Dialog.showDialog("save", {
			title : "集成进度明细——" + button,
			url : "${ctx}/admin/intProgDetail/goSave" + flag,
			postData : {
				'custCode' : "${custIntProg.custcode}",
				'address' : "${custIntProg.address}",
				'type' : type
			},
			height : 400,
			width : 750,
			returnFun : function(data) {
				if (data) {
					$.each(data, function(k, v) {
						var json = {
							custcode : v.custCode,
							typedescr : v.typedescr,
							date : v.date,
							cancelresondescr : v.cancelResonDescr,
							iscup : v.isCup,
							remarks : v.remarks,
							lastupdate : new Date().getTime(),
							expired : "F",
							lastupdatedby : v.lastUpdatedBy,
							actionlog : "ADD",
							respartdescr : v.resPartDescr,
							type:v.type,
							iscupboard:v.isCupboard,
							cancelreson:v.cancelReson,
							respart:v.resPart
						};
						console.log(json);
						Global.JqGrid.addRowData("dataTable", json);
					});
				}
			}
		});
	}
	
	function view() {
		var ret = selectDataTableRow();
		//用于区别是否显示退单登记,责任方
		var flag = "";
		if (ret.cancelresondescr != '' && ret.iscup != '') {
			flag = "?isReturn=yes&cantInstall=yes";
		}
		else if (ret.cancelresondescr==''&&ret.iscup != '') {
			flag = "?cantInstall=yes";
		}
		if (ret.respart != '') {
			flag +=flag==""?"?showZrf=yes":"&&showZrf=yes";
		}
		Global.Dialog.showDialog("view", {
			title : "集成进度明细——查看",
			url : "${ctx}/admin/intProgDetail/goView" + flag,
			postData : {
				'custCode' : ret.custcode,
				'address' : "${custIntProg.address}",
				'date' : ret.date,
				'resPart' : ret.respart,
				'cancelReson' : ret.cancelreson,
				'isCupboard' : ret.iscupboard,
				'remarks' : ret.remarks,
				'lastUpdate' : ret.lastupdate,
				'lastUpdatedBy' : ret.lastupdatedby,
				'expired' : ret.expired,
				'actionLog' : ret.actionlog,
				'type':ret.type
			},
			height : 400,
			width : 800,

		});
	}
	
	//删除
	function del(){
		var id = $("#dataTable").jqGrid("getGridParam","selrow");
		if(!id){
			art.dialog({content: "请选择一条记录进行删除操作！",width: 200});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				Global.JqGrid.delRowData("dataTable",id);
			},
			cancel: function () {
					return true;
			}
		});
	};
	
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
				<div class="btn-group-xs">
					<house:authorize authCode="CUSTINTPROG_ERRORREGISTER">
						<button style="align:left" type="button" class="btn btn-system "
							id="errorRegister" onclick="add('出错登记','1')">出错登记</button>
					</house:authorize>
					<house:authorize authCode="CUSTINTPROG_CANTINSTALL">
						<button style="align:left" type="button" class="btn btn-system "
							id="cantInstall" onclick="add('不能安装','2')">不能安装</button>
					</house:authorize>
					<house:authorize authCode="CUSTINTPROG_CONPENREGISTER">
						<button style="align:left" type="button" class="btn btn-system "
							id="conpenRegister" onclick="add('赔付登记','3')">赔付登记</button>
					</house:authorize>
					<house:authorize authCode="CUSTINTPROG_URGENTCALL">
						<button style="align:left" type="button" class="btn btn-system "
							id="urgentCall" onclick="add('加急单','4')">加急单</button>
					</house:authorize>
					<house:authorize authCode="CUSTINTPROG_RETURNREGISTER">
						<button style="align:left" type="button" class="btn btn-system "
							id=returnRegister onclick="add('退单登记','5')">退单登记</button>
					</house:authorize>
					<button style="align:left" type="button" class="btn btn-system "
						id="view" onclick="view()">
						<span>查看 </span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="sale" onclick="add('售中/售后','6')">
						<span>售中/售后 </span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="delete" onclick="del()">
						<span>删除 </span>
					</button>
					<button style="align:left" type="button" class="btn btn-system "
						id="excel" onclick="doExcelNow('集成进度明细表','dataTable','form')">
						<span>导出excel </span>
					</button>
				</div>
		</div>
	</div>
	<div class="clear_float"></div>
			<!--query-form-->
				<div id="content-list" >
					<table id="dataTable"></table>
				</div>
		</div>



