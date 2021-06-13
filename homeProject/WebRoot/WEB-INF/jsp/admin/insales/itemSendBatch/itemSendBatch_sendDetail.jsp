<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemAppSend/goSendDetailJqGrid",
			postData:{sendBatchNo:"${itemSendBatch.no}"},
			height:260,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "ischeckout", index: "ischeckout", width: 90, label: "是否记账", sortable: true, align: "left", hidden: true},
				{name: "no", index: "no", width: 100, label: "发货单号", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 89, label: "领料单号", sortable: true, align: "left"},
				{name: "drivercode", index: "drivercode", width: 74, label: "司机编号", sortable: true, align: "left"},
				{name: "sendtype", index: "sendtype", width: 74, label: "发货类型", sortable: true, align: "left"},
				{name: "confirmdate", index: "confirmdate", width: 120, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
				{name: "date", index: "date", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "sendstatus", index: "sendstatus", width: 72, label: "送货状态", sortable: true, align: "left", hidden: true},
				{name: "sendstatusdescr", index: "sendstatusdescr", width: 72, label: "送货状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 145, label: "送货地址", sortable: true, align: "left"},
				{name: "takeaddress", index: "takeaddress", width: 147, label: "取货地址", sortable: true, align: "left"},
				{name: "supplierdescr", index: "supplierdescr", width: 117, label: "供应商", sortable: true, align: "left"},
				{name: "whcode", index: "whcode", width: 114, label: "仓库", sortable: true, align: "left",hidden:true},
				{name: "whdescr", index: "whdescr", width: 114, label: "仓库名称", sortable: true, align: "left"},
				{name: "itemtype1descr", index: "itemtype1descr", width: 79, label: "材料类型1", sortable: true, align: "left"},
				{name: "itemtype2descr", index: "itemtype2descr", width: 116, label: "材料类型2", sortable: true, align: "left"},
				{name: "arrivedate", index: "arrivedate", width: 120, label: "到货日期", sortable: true, align: "left", formatter: formatTime},
				{name: "arriveaddress", index: "arriveaddress", width: 106, label: "到货地址", sortable: true, align: "left"},
				{name: "driverremark", index: "driverremark", width: 141, label: "司机反馈", sortable: true, align: "left"},
				{name: "sendfee", index: "sendfee", width: 76, label: "配送费", sortable: true, align: "right", sum: true, hidden: true},
				{name: "region", index: "region", width: 73, label: "配送区域", sortable: true, align: "left",hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 73, label: "配送区域", sortable: true, align: "left"},
				{name: "carrytypedescr", index: "carrytypedescr", width: 79, label: "搬运类型", sortable: true, align: "left"},
				{name: "carrytype", index: "carrytype", width: 79, label: "搬运类型", sortable: true, align: "left",hidden:true},
				{name: "floor", index: "floor", width: 66, label: "楼层", sortable: true, align: "right"},
				{name: "autotransfee", index: "autotransfee", width: 85, label: "车费（自动）", sortable: true, align: "right", sum: true},
				{name: "autocarryfee", index: "autocarryfee", width: 90, label: "搬运费（自动）", sortable: true, align: "right", sum: true},
				{name: "autotransfeeadj", index: "autotransfeeadj", width: 100, label: "车费补贴（自动）", sortable: true, align: "right", sum: true},
				{name: "transfee", index: "transfee", width: 75, label: "车费", sortable: true, align: "right", sum: true},
				{name: "carryfee", index: "carryfee", width: 75, label: "搬运费", sortable: true, align: "right", sum: true},
				{name: "transfeeadj", index: "transfeeadj", width:75 , label: "车费补贴", sortable: true, align: "right", sum: true},
				{name: "manysendremarks", index: "manysendremarks", width: 114, label: "多次配送说明", sortable: true, align: "left"},
				{name: "sendno", index: "sendno", width: 100, label: "发货单号", sortable: true, align: "left",hidden:true},
				{name: "sendbatchno", index: "sendbatchno", width: 89, label: "批次号", sortable: true, align: "left",hidden:true},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后修改时间", sortable: true, align: "left",formatter: formatTime,hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后修改人", sortable: true, align: "left",hidden:true},
				
			],
		    ondblClickRow:function(){
				s_sendInfo();
	        },	
 		};
       //初始化发货明细
	   Global.JqGrid.initJqGrid("sendDetailDataTable",gridOption);
	   
	   	/**初始化表格*/
	var gridOption2 ={
			postData:{sendBatchNo:"${itemSendBatch.no}"},
			height:390,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "sendno", index: "sendno", width: 100, label: "发货单号", sortable: true, align: "left"},
				{name: "iano", index: "iano", width: 89, label: "领料单号", sortable: true, align: "left"},
				{name: "sendbatchno", index: "sendbatchno", width: 89, label: "批次号", sortable: true, align: "left"},
				{name: "drivercode", index: "drivercode", width: 74, label: "司机编号", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后修改时间", sortable: true, align: "left",formatter: formatTime,hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后修改人", sortable: true, align: "left",hidden:true},
				{name: "acitonlog", index: "acitonlog", width: 100, label: "日志", sortable: true, align: "left",hidden:true},
				{name: "expired", index: "expired", width: 100, label: "过期", sortable: true, align: "left",hidden:true},
				
				
			],
 		};
       //初始化发货明细-退货记录
	   Global.JqGrid.initJqGrid("sendBackDataTable",gridOption2);
});

	//查看
	function s_sendInfo() {
		var rowId = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
		var ret = $("#sendDetailDataTable").jqGrid('getRowData',rowId);
		if (rowId) {
		Global.Dialog.showDialog("view", {
			title : "发货明细查看",
			url : "${ctx}/admin/itemAppSendDetail/goSendInfo",
			postData : {
				'no':ret.no
			},
			height : 500,
			width : 1000,
		});
	  }else{
	  	art.dialog({    	
			content: "请选择一条记录！"
		});
	  }
	}
	
	//删除
	function s_delete(){
	 	var id = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#sendDetailDataTable").jqGrid('getRowData',id);
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		if(ret.sendstatus==2){
			art.dialog({content: "已到货状态的不允许删除",width: 300});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除送货单["+ret.no+"]的记录",
			 lock: true,
			 width: 300,
			 ok: function () {
				Global.JqGrid.delRowData("sendDetailDataTable",id);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//退回
	function s_return(){
	 	var id = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#sendDetailDataTable").jqGrid('getRowData',id);
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		if(ret.sendstatus!=1){
			art.dialog({content: "只有状为指定司机的送货单允许操作允许退回",width: 300});
			return false;
		}
		art.dialog({
			 content:"是否确认要退回送货单["+ret.no+"]的记录",
			 lock: true,
			 width: 300,
			 ok: function () {
				Global.JqGrid.delRowData("sendDetailDataTable",id);
			 	//添加到退货记录表
				ret['sendno']=ret.no;
				ret['iano']=ret.iano;
				ret['drivercode']=ret.drivercode;
				ret['sendbatchno']=$("#no").val();
				ret['lastupdate']=new Date().getTime();
				ret['lastupdatedby']="${czy}";
				ret['expired']='F';
				ret['actionlog']='ADD';
				Global.JqGrid.addRowData("sendBackDataTable", ret);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//新增发货
	function s_add(){
		var nos = $("#sendDetailDataTable").getCol("no", false).join(",");
		var ret=$("#sendDetailDataTable").jqGrid('getRowData',1);
		Global.Dialog.showDialog("view", {
			title : "新增发货",
			url : "${ctx}/admin/itemAppSend/goAddSendDetail",
			postData : {
				'nos':nos
			},
 			height:680,
			width:1350,
			returnFun:function(data){
					if(data.length > 0){
						var ids = $("#sendDetailDataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							$("#sendDetailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});	
					}
			}
		});
	}
	
	//配送费录入
	function s_sendFee(){
		var id = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#sendDetailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		Global.Dialog.showDialog("save", {
			title : "配送明细--配送费录入",
			url : "${ctx}/admin/itemSendBatch/goSendFee",
			postData : {
				'transFee' : ret.transfee,
				'carryFee' : ret.carryfee,
				'transFeeAdj' : ret.transfeeadj,
				'manySendRemarks' : ret.manysendremarks,
				'sendType':'S', //发货标记
				'no' : ret.no
			},
			height : 350,
			width : 500,
			returnFun : function(data) {
					$("#sendDetailDataTable").jqGrid('setCell',id,"transfee",data.transFee);
					$("#sendDetailDataTable").jqGrid('setCell',id,"carryfee",data.carryFee);
					$("#sendDetailDataTable").jqGrid('setCell',id,"transfeeadj",data.transFeeAdj);
					$("#sendDetailDataTable").jqGrid('setCell',id,"manysendremarks",data.manySendRemarks);
			}
		});
	}
	//搬运信息录入
	function s_carryInfo(){
		var id = $("#sendDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#sendDetailDataTable").jqGrid('getRowData',id);
	 	var ids = $("#sendDetailDataTable").getDataIDs();
		var rows = $("#sendDetailDataTable").jqGrid("getRowData");
	 	var address=ret.address;
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		Global.Dialog.showDialog("save", {
			title : "配送明细--录入搬运信息",
			url : "${ctx}/admin/itemSendBatch/goCarryInfo",
			postData : {
				'region' : ret.region,
				'regionDescr' : ret.regiondescr ,
				'no' : ret.no,
				'carryType':ret.carrytype==' '?'1':ret.carrytype,
				'floor':ret.floor==' '?'0':ret.floor,
				'carryTypeDescr':ret.carrytypedescr
			},
			height : 300,
			width : 500,
			returnFun : function(data) {
						//遍历所有表格数据,更新发货地址与所选记录相同的记录
						for(var i=0;i<ids.length;i++){
						 	if(rows[i].address==address){
						 		rows[i]['region']=data.region;
						 		rows[i]['regiondescr']=data.regionDescr;
						 		rows[i]['carrytype']=data.carryType;
						 		rows[i]['carrytypedescr']=data.carryTypeDescr;
						 		rows[i]['floor']=data.floor;
						 	}
						}
						$("#sendDetailDataTable").jqGrid("clearGridData");
						$.each(rows, function(i,rowData){
							$("#sendDetailDataTable").jqGrid("addRowData", i+1, rowData);
						});	
			}
		});
	}
	//生成搬运费
	function s_crtCarryFee(){
		var ids = $("#sendDetailDataTable").getDataIDs();
		var rows = $("#sendDetailDataTable").jqGrid("getRowData");
		//遍历所有表格数据,检查是否符合自动生成搬运费条件
		 for(var i=0;i<ids.length;i++){
		 	if(rows[i].region==" " || rows[i].carrytype==" " || (rows[i].carrytype=="2" && rows[i].floor==" ")){
		 		art.dialog({content: "有发货单搬运信息未录入，请检查",width: 300});
				return false;
		 	}
		 }
		 var itemAppSendJson = Global.JqGrid.allToJson("sendDetailDataTable");
		 var param = {"itemAppSendJson": JSON.stringify(itemAppSendJson)};
		 console.log(param);
		 Global.Form.submit("send_form","${ctx}/admin/itemAppSend/autoCteateFee",param,function(obj){
			console.log(obj);
			var list1=obj.list1;
			var ids = $("#sendDetailDataTable").getDataIDs();
			var rows = $("#sendDetailDataTable").jqGrid("getRowData");
			if(ids){
				for(var i=0;i<ids.length;i++){
					for(var j=0;j<=list1.length-1;j++){
						if(i==j){
							rows[i]['autotransfee']=list1[i].autotransfee;
							rows[i]['autocarryfee']=list1[i].autocarryfee;
							rows[i]['autotransfeeadj']=list1[i].autotransfeeadj;
						}
					}
					rows[i]['carryfee']=rows[i].autocarryfee;
					rows[i]['transfee']=rows[i].autotransfee;
					rows[i]['transfeeadj']=rows[i].autotransfeeadj;
		 		}
				$("#sendDetailDataTable").jqGrid("clearGridData");
				$.each(rows, function(i,rowData){
					$("#sendDetailDataTable").jqGrid("addRowData", i+1, rowData);
				});	
				art.dialog({content: "搬运费计算完成！",width: 200});
			}
		});
	}
	
	//查看图片
	function s_viewPhoto() {
		var rowId = $("#sendDetailDataTable").jqGrid("getGridParam", "selrow");
		var ret = $("#sendDetailDataTable").jqGrid('getRowData', rowId);
		if (rowId) {
			Global.Dialog.showDialog("sendView", {
				title : "送货图片信息--查看",
				url : "${ctx}/admin/itemAppSend/goViewSendPhoto",
				postData : {
					'no' : ret.no
				},
				height : 650,
				width : 750,
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="send_form" method="post">
				<input type="hidden" name="jsonString" value="" /> <input
					type="hidden" id="test">
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" id="s_add"
					class="btn btn-system " onclick="s_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" id="s_delete"
					class="btn btn-system " onclick="s_delete()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" id="s_return"
					class="btn btn-system " onclick="s_return()">
					<span>退回 </span>
				</button>
				<button style="align:left" type="button" id="s_sendFee"
					class="btn btn-system " onclick="s_sendFee()">
					<span>配送费录入</span>
				</button>
				<button style="align:left" type="button" id="s_carryInfo"
					class="btn btn-system " onclick="s_carryInfo()">
					<span>录入搬运信息</span>
				</button>
				<button style="align:left" type="button" id="s_crtCarryFee"
					class="btn btn-system " onclick="s_crtCarryFee()">
					<span>生成搬运费 </span>
				</button>
				<button style="align:left" type="button" id="s_sendInfo"
					class="btn btn-system " onclick="s_sendInfo()">
					<span>送货详情 </span>
				</button>
				<button style="align:left" type="button" id="r_viewPhoto"
					class="btn btn-system " onclick="s_viewPhoto()">
					<span>查看图片 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="sendDetailDataTable"></table>
	</div>
	<div id="content-list" class="hidden">
		<table id="sendBackDataTable"></table>
	</div>
</div>



