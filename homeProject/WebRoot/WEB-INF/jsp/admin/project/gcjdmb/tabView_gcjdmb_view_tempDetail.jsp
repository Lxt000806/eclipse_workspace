<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<script type="text/javascript">
	$(function(){
		if($("#m_umState").val() != "A"){
			url="${ctx}/admin/gcjdmb/goJqGridProgTempDt?no="+$("#no").val();
		}else{
			url="#";
		}
		

		Global.JqGrid.initJqGrid("dataTable_tempDetail", {
			height:350,
			width:1200,
			url:url,
			autowidth:false,
			styleUI:"Bootstrap",
			onSortColEndFlag:true,
			rowNum:100000,
			colModel:[
				{name: "note", index: "note", width: 130, label: "施工项目", sortable: true, align: "left"},
				{name: "planbegin", index: "planbegin", width: 140, label: "计划开始日（第N日）", sortable: true, align: "right"},
				{name: "planend", index: "planend", width: 80, label: "计划结束日", sortable: true, align: "right"},
				{name: "befprjitemdescr", index: "befprjitemdescr", width: 130, label: "上一节点", sortable: true, align: "left",hidden:$("#type").val()==="1"},
				{name: "befprjitem", index: "befprjitem", width: 70, label: "上一节点", sortable: true, align: "left",hidden:true},
				{name: "spaceday", index: "spaceday", width: 70, label: "间隔天数", sortable: true, align: "right"},
				{name: "type", index:"type", width:80, label:"计算类型", sortable:true, align:"left",hidden:true},
				{name: "typedescr", index:"typedescr", width:80, label:"计算类型", sortable:true, align:"left",hidden:$("#type").val()==="1"},
				{name: "baseconday", index:"baseconday", width:80, label:"基础天数", sortable:true, align:"right",hidden:$("#type").val()==="1"},
				{name: "basework", index:"basework", width:80, label:"基础工作量", sortable:true, align:"right",hidden:$("#type").val()==="1"},
				{name: "daywork", index:"daywork", width:80, label:"每日工作量", sortable:true, align:"right",hidden:$("#type").val()==="1"},
				{name: "lastupdate", index: "lastupdate", width: 85, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 70, label: "操作人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 70, label: "操作代码", sortable: true, align: "left"},
				
				{name: "prjitem", index: "prjitem", width: 80, label: "prjitem", sortable: true, align: "left", hidden:true},
				{name: "pk", index: "pk", width: 80, label: "pk", sortable: true, align: "left", hidden:true},
				{name: "tempno", index: "tempno", width: 80, label: "tempno", sortable: true, align: "left", hidden:true},
				{name: "dispseq", index: "dispseq", width: 80, label: "显示顺序", sortable: true, align: "right"}
			],
			ondblClickRow:function(rowid,iRow,iCol,e){
            	var type=$("#type").val();
				var ret = $("#dataTable_tempDetail").jqGrid("getRowData",rowid);
				
				if(ret){
					var postData={
							planBegin:ret.planbegin,
							planEnd:ret.planend,
							spaceDay:ret.spaceday,
							prjItem:ret.prjitem,
							tmpType:type,
							pk:ret.pk,
			       	  		mm_umState:"${data.m_umState}",
			       	  		lastUpdate:ret.lastupdate,
			       	  		lastUpdatedBy:ret.lastupdatedby
						};
			       	Global.Dialog.showDialog("tempDetailView",{
			       		title:"模板明细--查看",
			       	  	url:"${ctx}/admin/gcjdmb/goView_tempDetail",
			       	  	postData:postData,
			       	  	height: 350,
			       	  	width:450
			       	});
				}
            },
			onSortCol:function(index, iCol, sortorder){
				var rows = $("#dataTable_tempDetail").jqGrid("getRowData");
	   			rows.sort(function (a, b) {
	   				var reg = /^[0-9]+.?[0-9]*$/;
					if(reg.test(a[index])){
	   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
					}else{
	   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
					}  
	   			});    
	   			Global.JqGrid.clearJqGrid("dataTable_tempDetail"); 
	   			$.each(rows,function(k,v){
					Global.JqGrid.addRowData("dataTable_tempDetail", v);
				});
			}
		}); 
		if($("#m_umState").val() != "A"&&$("#type").val()==="2"){
			$("#dataTable_tempDetail").setGridParam().hideCol("planbegin");
			$("#dataTable_tempDetail").setGridParam().hideCol("planend");
		}
	});
	function hasAlarmInProgTempDt(ret){
		var tipEventPrjItems = $("#dataTable_tipEvent").jqGrid("getCol", "prjitem", true);
		var result = false;
		console.log(tipEventPrjItems);
		for(var i = 0;i < tipEventPrjItems.length;i++){
			if(ret.prjitem == tipEventPrjItems[i].prjitem){
				result = true;
				break;
			}
		}
		return result;
	}
	function tempDetailAdd(){
		var type=$("#type").val();
		if(!type){
			art.dialog({
				content:"请先选择模版类型"
			});
			return;
		}
		var dispseq=$("#dataTable_tempDetail").getGridParam("reccount");
       	var width=type=="1"?450:700;
       	Global.Dialog.showDialog("tempDetailAdd",{
       		title:"模板明细--增加",
       	  	url:"${ctx}/admin/gcjdmb/goSave_tempDetail",
       	  	postData:{
       	  		mm_umState:"${data.m_umState}",
       	  		tempNo:"${data.no }",
       	  		nowPk:$("#nowPk").val(),
       	  		dispSeq:dispseq,
       	  		tmpType:type
       	  	},
       	  	height: 350,
       	  	width:width,
       	  	returnFun:function(data){
       	  		if(data && data.length > 0){
					Global.JqGrid.addRowData("dataTable_tempDetail", data[0]);
					autoSort();
					$("#nowPk").val(data[0].pk);
					$("#isTipExit").val("1");
       	  		}
       	  		getProgTempDt();
       	  	}
       	});
	}
	function tempDetailFastAdd(){
		var type=$("#type").val();
		if(!type){
			art.dialog({
				content:"请先选择模版类型"
			});
			return;
		}
		var width=type=="1"?450:700;
		var dispseq=$("#dataTable_tempDetail").getGridParam("reccount");
       	Global.Dialog.showDialog("tempDetailFastAdd",{
       		title:"模板明细--快速增加",
       	  	url:"${ctx}/admin/gcjdmb/goFastSave_tempDetail",
       	  	postData:{
       	  		mm_umState:"${data.m_umState}",
       	  		tempNo:"${data.no }",
       	  		nowPk:$("#nowPk").val(),
       	  		dispSeq:dispseq,
       	  		tmpType:type
       	  	},
       	  	height: 350,
       	  	width:width,
       	  	returnFun:function(data){
       	  		if(data && data.length > 0){
       	  			$.each(data, function(index, value){
						Global.JqGrid.addRowData("dataTable_tempDetail", value);
						autoSort();
						$("#nowPk").val(value.pk);
       	  			});
					$("#isTipExit").val("1");
       	  		}
       	  		getProgTempDt();
       	  	}
       	});
	}
	function tempDetailCopy(){
		var type=$("#type").val();
		var width=type=="1"?450:700;
		var id = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tempDetail").jqGrid("getRowData", id);
		}
		var ids = $("#dataTable_tempDetail").getDataIDs();
       	var rowData = $("#dataTable_tempDetail").getRowData(ids[ids.length-1]);
		if(ret){
			var postData={
					planBegin:ret.planbegin,
					planEnd:ret.planend,
					spaceDay:ret.spaceday,
					prjItem:ret.prjitem,
	       	  		mm_umState:"${data.m_umState}",
	       	  		tempNo:"${data.no }",
	       	  		rowId:id,
       	  			nowPk:$("#nowPk").val(),
       	  			befPrjItem:ret.befPrjIte,
       	  			dispSeq:rowData.dispseq,
       	  			tmpType:type,
       	  			type:ret.type,
       	  			baseConDay:ret.baseconday,
       	  			baseWork:ret.basework,
       	  			dayWork:ret.daywork
			};
	       	Global.Dialog.showDialog("tempDetailCopy",{
	       		title:"模板明细--复制",
	       	  	url:"${ctx}/admin/gcjdmb/goCopy_tempDetail",
	       	  	postData:postData,
	       	  	height: 350,
	       	  	width:width,
	       	  	returnFun:function(data){
	       	  		if(data && data.length > 0){
						Global.JqGrid.addRowData("dataTable_tempDetail", data[0]);
						autoSort();
						$("#nowPk").val(data[0].pk);
						$("#isTipExit").val("1");
	       	  		}
	       	  		getProgTempDt();
	       	  	}
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function tempDetailUpdate(){
		var type=$("#type").val();
		var width=type=="1"?450:700;
		var id = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tempDetail").jqGrid("getRowData", id);
		}
		if(ret){
			var postData={
					planBegin:ret.planbegin,
					planEnd:ret.planend,
					spaceDay:ret.spaceday,
					prjItem:ret.prjitem,
					pk:ret.pk,
	       	  		mm_umState:"${data.m_umState}",
	       	  		tempNo:"${data.no }",
	       	  		rowId:id,
       	  			befPrjItem:ret.befPrjIte,
       	  			tmpType:type,
       	  			type:ret.type,
       	  			baseConDay:ret.baseconday,
       	  			baseWork:ret.basework,
       	  			dayWork:ret.daywork
				};
	       	Global.Dialog.showDialog("tempDetailUpdate",{
	       		title:"模板明细--编辑",
	       	  	url:"${ctx}/admin/gcjdmb/goUpdate_tempDetail",
	       	  	postData:postData,
	       	  	height: 350,
	       	  	width:width,
	       	  	returnFun:function(data){
	       	  		if(data && data.length > 0){
	       	  			$("#dataTable_tempDetail").jqGrid("setRowData", data[0].rowId, data[0]);
						autoSort();
						$("#isTipExit").val("1");
	       	  		}
	       	  		getProgTempDt();
	       	  	}
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function tempDetailDel(){
		var id = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tempDetail").jqGrid("getRowData", id);
		}
		if(ret){
			art.dialog({
				content:"是否要删除数据",
				ok:function(){
					var hasAlarmInProgTempDtFlag = hasAlarmInProgTempDt(ret);
					if(hasAlarmInProgTempDtFlag){
						art.dialog({
							content:"该模板明细存在提醒事项,是否继续",
							ok:function(){
								del(id, ret);
								$("#isTipExit").val("1");
								getProgTempDt();
								getProgTempAlarm();
							},
							cancel:function(){}
						});
					}
					if(!hasAlarmInProgTempDtFlag){
						del(id, ret);
						$("#isTipExit").val("1");
						getProgTempDt();
						getProgTempAlarm();
					}
				},
				cancel:function(){}
			});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function del(id, ret){
		if($("#m_umState").val() == "E"){
			console.log("248");
			ajaxPost("${ctx}/admin/gcjdmb/doDelProgTempDt", {
				pk:ret.pk,
				tempNo:$("#no").val(),
				prjItem:ret.prjitem
			}, function(data){
				if(!data.rs){
					art.dialog({
						content:data.msg
					});
				}
			});
		}else{
			$("#dataTable_tempDetail").jqGrid("delRowData", id);
			var tipEventPrjItems = $("#dataTable_tipEvent").jqGrid("getCol", "prjitem", true);
			$.each(tipEventPrjItems, function(index, value){
				if(ret.prjitem == value.prjitem){
					$("#dataTable_tipEvent").jqGrid("delRowData", value.id);
				}
			});
		}
		autoSort();
	}
	function tempDetailView(){
		var type=$("#type").val();
		var width=type=="1"?450:700;
		var id = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var ret;
		if(id){
			ret = $("#dataTable_tempDetail").jqGrid("getRowData", id);
		}
		if(ret){
			var postData={
					planBegin:ret.planbegin,
					planEnd:ret.planend,
					spaceDay:ret.spaceday,
					prjItem:ret.prjitem,
					pk:ret.pk,
	       	  		mm_umState:"${data.m_umState}",
	       	  		lastUpdate:ret.lastupdate,
	       	  		lastUpdatedBy:ret.lastupdatedby,
       	  			befPrjItem:ret.befPrjIte,
       	  			tmpType:type,
       	  			type:ret.type,
       	  			baseConDay:ret.baseconday,
       	  			baseWork:ret.basework,
       	  			dayWork:ret.daywork
				};
	       	Global.Dialog.showDialog("tempDetailView",{
	       		title:"模板明细--查看",
	       	  	url:"${ctx}/admin/gcjdmb/goView_tempDetail",
	       	  	postData:postData,
	       	  	height: 350,
	       	  	width:width
	       	});
		}else{
			art.dialog({
				content:"请选择一条记录"
			});
		}
	}
	function autoSort(){
		if($("#type").val()==="2"){
			$("#dataTable_tempDetail").setGridParam().hideCol("planbegin");
			$("#dataTable_tempDetail").setGridParam().hideCol("planend");
		}
		var rows = $("#dataTable_tempDetail").jqGrid("getRowData");
		rows.sort(function (a, b) {
			var reg = /^[0-9]+.?[0-9]*$/;
			if(reg.test(a["dispseq"])){
				return (a["dispseq"]-b["dispseq"]);
			}
		});    
		Global.JqGrid.clearJqGrid("dataTable_tempDetail"); 
		var rowId = 1;
		$.each(rows, function(k,v){
			v.dispseq=rowId;
			$("#dataTable_tempDetail").jqGrid("addRowData", rowId++, v);
		});
	}
	function getProgTempDt(){
		if($("#m_umState").val() == "E"){
			$("#dataTable_tempDetail").jqGrid("setGridParam",{
				url:"${ctx}/admin/gcjdmb/goJqGridProgTempDt?no="+$("#no").val(),
				page:1,
				sortname:''
			}).trigger("reloadGrid");
		}
	}
	
	function up(){
		var rowId = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)-1;
		if(rowId){
			if(rowId>1){
			    var ret1 = $("#dataTable_tempDetail").jqGrid("getRowData", rowId);
				var ret2 = $("#dataTable_tempDetail").jqGrid("getRowData", replaceId);
				replace(rowId,replaceId,"dataTable_tempDetail");
				scrollToPosi(replaceId,"dataTable_tempDetail");
				$("#dataTable_tempDetail").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable_tempDetail").setCell(replaceId,"dispseq",ret2.dispseq);
				if($("#m_umState").val() !== "A"){
					changeDispSeq(ret1.pk,ret2.dispseq,ret2.pk,ret1.dispseq);
				}
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	//向下
	function down(){
		var rowId = $("#dataTable_tempDetail").jqGrid("getGridParam", "selrow");
		var replaceId = parseInt(rowId)+1;
		var rowNum = $("#dataTable_tempDetail").jqGrid("getGridParam","records");
		if(rowId){
			if(rowId<rowNum){
				var ret1 = $("#dataTable_tempDetail").jqGrid("getRowData", rowId);
				var ret2 = $("#dataTable_tempDetail").jqGrid("getRowData", replaceId);
				scrollToPosi(replaceId,"dataTable_tempDetail");
				replace(rowId,replaceId,"dataTable_tempDetail");
				$("#dataTable_tempDetail").setCell(rowId,"dispseq",ret1.dispseq);
				$("#dataTable_tempDetail").setCell(replaceId,"dispseq",ret2.dispseq);
				if($("#m_umState").val() !== "A"){
					changeDispSeq(ret1.pk,ret2.dispseq,ret2.pk,ret1.dispseq);
				}
			}
		}else{
			art.dialog({
				content: "请选择一条记录"
			});
		}
	}
	
	function changeDispSeq(pk1,dispSeq2,pk2,dispSeq1){
		ajaxPost("${ctx}/admin/gcjdmb/doUpdateDispSeq", {
				pk1:pk1,
				dispSeq2:dispSeq2,
				pk2:pk2,
				dispSeq1:dispSeq1
			}, function(data){
				if(!data.rs){
					art.dialog({
						content:data.msg
					});
				}
			});
	}
</script>
<div class="btn-panel" style="margin-top:2px">
	<div class="btn-group-sm"  >
		<button id="tempDetailAddBtn" type="button" class="btn btn-system " onclick="tempDetailAdd()">新增</button>
		<button id="tempDetailFastAddBtn" type="button" class="btn btn-system " onclick="tempDetailFastAdd()">快速新增</button>
		<button id="tempDetailCopyBtn" type="button" class="btn btn-system " onclick="tempDetailCopy()">复制</button>
		<button id="tempDetailUpdateBtn" type="button" class="btn btn-system " onclick="tempDetailUpdate()">编辑</button>
		<button id="tempDetailDelBtn" type="button" class="btn btn-system " onclick="tempDetailDel()">删除</button>
		<button id="tempDetailViewBtn" type="button" class="btn btn-system " onclick="tempDetailView()">查看</button>
		<button id="tempDetailUpBtn" style="align:left" type="button" class="btn btn-system " onclick="up()">向上</button>
		<button id="tempDetailDownBtn" style="align:left" type="button" class="btn btn-system " onclick="down()">向下 </button>
	</div>
</div>	
<table id="dataTable_tempDetail"></table>
