<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/itemReturn/goReturnDetailJqGrid",
			postData:{sendBatchNo:"${itemSendBatch.no}"},
			height:260,
			rowNum:10000000,
			styleUI: 'Bootstrap', 
			colModel : [
				{name: "no", index: "no", width: 106, label: "退货单号", sortable: true, align: "left"},
				{name: "appczy", index: "appczy", width: 68, label: "申请人", sortable: true, align: "left",hidden:true},
				{name: "appczydescr", index: "appczydescr", width: 68, label: "申请人", sortable: true, align: "left"},
				{name: "phone", index: "phone", width: 102, label: "手机", sortable: true, align: "left"},
				{name: "custcode", index: "custcode", width: 82, label: "客户编号", sortable: true, align: "left"},
				{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
				{name: "status", index: "status", width: 74, label: "退货状态", sortable: true, align: "left",hidden:true},
				{name: "statusdescr", index: "statusdescr", width: 74, label: "退货状态", sortable: true, align: "left"},
				{name: "address", index: "address", width: 150, label: "楼盘地址", sortable: true, align: "left"},
				{name: "itemtype1", index: "itemtype1", width: 117, label: "材料类型1", sortable: true, align: "left",hidden:true},
				{name: "itemtype1descr", index: "itemtype1descr", width: 117, label: "材料类型1", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 126, label: "备注", sortable: true, align: "left"},
				{name: "region", index: "region", width: 94, label: "配送区域", sortable: true, align: "left",hidden:true},
				{name: "regiondescr", index: "regiondescr", width: 94, label: "配送区域", sortable: true, align: "left"},
				{name: "carrytype", index: "carrytype", width: 76, label: "搬运类型", sortable: true, align: "left",hidden:true},
				{name: "carrytypedescr", index: "carrytypedescr", width: 76, label: "搬运类型", sortable: true, align: "left"},
				{name: "floor", index: "floor", width: 61, label: "楼层", sortable: true, align: "right"},
				{name: "autotransfee", index: "autotransfee", width: 110, label: "车费（自动计算）", sortable: true, align: "right", sum: true},
				{name: "autocarryfee", index: "autocarryfee", width: 110, label: "搬运费（自动）", sortable: true, align: "right", sum: true},
				{name: "transfee", index: "transfee", width: 68, label: "车费", sortable: true, align: "right", sum: true},
				{name: "carryfee", index: "carryfee", width: 69, label: "搬运费", sortable: true, align: "right", sum: true},
				{name: "lastupdate", index: "lastupdate", width: 100, label: "最后修改时间", sortable: true, align: "left",formatter: formatTime,hidden:true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 100, label: "最后修改人", sortable: true, align: "left",hidden:true},
			],
		    ondblClickRow:function(){
				d_view();
	        },	
 		};
 	
       //初始化退货明细
	   Global.JqGrid.initJqGrid("returnDetailDataTable",gridOption);
});

	//新增发货
	function r_add(){
		var nos = $("#returnDetailDataTable").getCol("no", false).join(",");
		var ret=$("#returnDetailDataTable").jqGrid('getRowData',1);
		Global.Dialog.showDialog("view", {
			title : "新增发货",
			url : "${ctx}/admin/itemReturn/goAddRetunDetail",
			postData : {
				'nos':nos
			},
 			height:680,
			width:1350,
			returnFun:function(data){
					if(data.length > 0){
						var ids = $("#returnDetailDataTable").jqGrid("getDataIDs");
						$.each(data, function(i,rowData){
							$("#returnDetailDataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
						});	
					}
			}
		});
	}
	//配送费录入
	function r_sendFee(){
		var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
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
				'no' : ret.no
			},
			height : 300,
			width : 500,
			returnFun : function(data) {
						$("#returnDetailDataTable").jqGrid('setCell',id,"transfee",data.transFee);
						$("#returnDetailDataTable").jqGrid('setCell',id,"carryfee",data.carryFee);
			}
		});
	}
	//搬运信息录入
	function r_carryInfo(){
		var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
	 	var ids = $("#returnDetailDataTable").getDataIDs();
		var rows = $("#returnDetailDataTable").jqGrid("getRowData");
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
				'floor':ret.floor==' '?0:ret.floor,
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
						$("#returnDetailDataTable").jqGrid("clearGridData");
						$.each(rows, function(i,rowData){
							$("#returnDetailDataTable").jqGrid("addRowData", i+1, rowData);
						});	
			}
		});
	}
	//生成搬运费
	function r_crtCarryFee(){
		var selrow=[];
		var totalPerWeight;//总重量
		var ids = $("#returnDetailDataTable").getDataIDs();
		var rows = $("#returnDetailDataTable").jqGrid("getRowData");
		//遍历所有表格数据,检查是否符合自动生成搬运费条件
		 for(var i=0;i<ids.length;i++){
		 	if(rows[i].region==" " || rows[i].carrytype==" " || (rows[i].carrytype=="2" && rows[i].floor==" ")){
		 		art.dialog({content: "有退货单搬运信息未录入，请检查",width: 300});
				return false;
		 	}
		 }
		 $.ajax({
				url : '${ctx}/admin/itemReturn/autoCteateFee',
				type : 'post',
				data : {
					'itemReturnJson' : JSON.stringify(Global.JqGrid.allToJson("returnDetailDataTable"))
				},
				async : false,
				dataType : 'json',
				cache : false,
				error : function(obj) {
					showAjaxHtml({
						"hidden" : false,
						"msg" : '保存数据出错~'
					});
				},
				success : function(list) {
					selrow.push(list);//把计算后返回的每条记录存入数组，统一添加
				}
		 });				
 		$("#returnDetailDataTable").jqGrid("clearGridData");//清空表格
		$.each(selrow, function(i,rowData){
			$("#returnDetailDataTable").jqGrid("addRowData", i+1, rowData);
		});	
		art.dialog({content: "搬运费计算完成！",width: 200}); 
	}

	//删除
	function r_delete(){
	 	var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		if(ret.status>3){
			art.dialog({content: "状态除指定司机的不允许删除",width: 300});
			return false;
		}
		art.dialog({
			 content:"是否确认要删除退货单["+ret.no+"]的记录",
			 lock: true,
			 width: 300,
			 ok: function () {
				Global.JqGrid.delRowData("returnDetailDataTable",id);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//退回
	function r_return(){
	 	var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
		if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
		if(ret.status!=3){
			art.dialog({content: "只有指定司机的允许退回操作",width: 300});
			return false;
		}
		art.dialog({
			 content:"是否确认要退回退货单["+ret.no+"]的记录",
			 lock: true,
			 width: 300,
			 ok: function () {
				Global.JqGrid.delRowData("returnDetailDataTable",id);
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//撤销
	function r_cancel(){
		var id = $("#returnDetailDataTable").jqGrid("getGridParam","selrow");
	 	var ret = $("#returnDetailDataTable").jqGrid('getRowData',id);
	 	if(!id){
			art.dialog({content: "请选择一条记录！",width: 200});
			return false;
		}
 		if(ret.status!=4){
			art.dialog({content: "只有收货的需要进行撤销操作",width: 300});
			return false;
		} 
		art.dialog({
			 content:"本次操作将删除该退货单到货图片和数量,是否确认要撤销到货["+ret.no+"]操作",
			 lock: true,
			 width: 500,
			 ok: function () {
				$("#returnDetailDataTable").jqGrid('setCell',id,"status",3);
				$("#returnDetailDataTable").jqGrid('setCell',id,"statusdescr","指定司机");
				$("#isCancel").val("1");
				$.ajax({//查询退货单号图片
						url : '${ctx}/admin/itemAppSendPhoto/getPhotoList?sendNo='+ret.no,
						type : 'post',
						async : false,
						dataType : 'json',
						cache : false,
						error : function(obj) {
							showAjaxHtml({
								"hidden" : false,
								"msg" : '保存数据出错~'
							});
						},
						success : function(photoNames) {
							var str="";
							if(photoNames.length>0){
								for(var i=0;i<=photoNames.length-1;i++){
									str+=photoNames[i].photoName+",";
								}
								str=str.substring(0,str.length-1);
							}
							if(str.length>0){
								$.ajax({//删除图片文件
									url : '${ctx}/client/driverItemAppReturn/deleteItemAppReturnPhoto?id='+str,
									type : 'post',
									async : false,
									dataType : 'json',
									cache : false,
									error : function(obj) {
										showAjaxHtml({
											"hidden" : false,
											"msg" : '保存数据出错~'
										});
									},
									success : function(obj) {
										
									}
								});
								$.ajax({//从数据库删除图片名字
									url : '${ctx}/admin/itemAppSendPhoto/delPhotoList?id='+ret.no,
									type : 'post',
									async : false,
									dataType : 'json',
									cache : false,
									error : function(obj) {
										showAjaxHtml({
											"hidden" : false,
											"msg" : '保存数据出错~'
										});
									},
									success : function(obj) {
										
									}
								});
							}

						}
					});
					
			},
			cancel: function () {
					return true;
			}
		}); 
	}
	//查看
	function r_returnInfo() {
		var rowId = $("#returnDetailDataTable")
				.jqGrid("getGridParam", "selrow");
		var ret = $("#returnDetailDataTable").jqGrid('getRowData', rowId);
		if (rowId) {
			Global.Dialog.showDialog("view", {
				title : "退货明细查看",
				url : "${ctx}/admin/itemReturnDetail/goReturnInfo",
				postData : {
					'no' : ret.no
				},
				height : 500,
				width : 1000,
			});
		} else {
			art.dialog({
				content : "请选择一条记录！"
			});
		}
	}
	
	//查看图片
	function r_viewPhoto() {
		var rowId = $("#returnDetailDataTable").jqGrid("getGridParam", "selrow");
		var ret = $("#returnDetailDataTable").jqGrid('getRowData', rowId);
		if (rowId) {
			Global.Dialog.showDialog("returnView", {
				title : "退货图片信息--查看",
				url : "${ctx}/admin/itemReturn/goViewReturnPhoto",
				postData : {
					'no' : ret.no
				},
				height : 600,
				width : 1000,
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
			<form role="form" class="form-search" id="return_form" method="post">
				<input type="hidden" name="jsonString" value="" /> <input
					type="hidden" id="isCancel">
			</form>
			<div class="btn-group-xs">
				<button style="align:left" id="r_add" type="button"
					class="btn btn-system " onclick="r_add()">
					<span>新增 </span>
				</button>
				<button style="align:left" type="button" id="r_delete"
					class="btn btn-system " onclick="r_delete()">
					<span>删除 </span>
				</button>
				<button style="align:left" type="button" id="r_return"
					class="btn btn-system " onclick="r_return()">
					<span>退回 </span>
				</button>
				<button style="align:left" type="button" id="r_cancel"
					class="btn btn-system " onclick="r_cancel()">
					<span>撤销到货 </span>
				</button>
				<button style="align:left" type="button" id="r_sendFee"
					class="btn btn-system " onclick="r_sendFee()">
					<span>配送费录入</span>
				</button>
				<button style="align:left" type="button" id="r_carryInfo"
					class="btn btn-system " onclick="r_carryInfo()">
					<span>录入搬运信息</span>
				</button>
				<button style="align:left" type="button" id="r_crtCarryFee"
					class="btn btn-system " onclick="r_crtCarryFee()">
					<span>生成搬运费 </span>
				</button>
				<button style="align:left" type="button" id="r_returnInfo"
					class="btn btn-system " onclick="r_returnInfo()">
					<span>退货详情 </span>
				</button>
				<button style="align:left" type="button" id="r_picture"
					class="btn btn-system " onclick="r_viewPhoto()">
					<span>查看图片 </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="returnDetailDataTable"></table>
	</div>
</div>



