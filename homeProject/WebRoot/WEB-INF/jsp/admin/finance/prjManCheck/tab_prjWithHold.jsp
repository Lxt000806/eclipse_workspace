<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript"> 
$(function(){
	if("${prjCheck.checkStatus}"!="2"){
		$("#btnAdd").hide();
		$("#btnUpdate").hide();
		$("#btnDel").hide();
		$("#btnGenerate").hide();
		$("#btnUpdateQualityFee").hide();
	}
	if("${prjCheck.m_umState}"=="V"||"${prjCheck.m_umState}"=="GB"||"${prjCheck.m_umState}"=="GC"){
		$("#btnAdd").hide();
		$("#btnUpdate").hide();
		$("#btnDel").hide();
		$("#btnGenerate").hide();
		$("#btnUpdateQualityFee").hide();
	}
	if("${prjCheck.prjCtrlType}"=="2"){ // "${prjCheck.custTypeType}"=="2" 改成按发包方式判断
		$("#btnGenerate").hide();
		$("#btnUpdateQualityFee").hide();
		$("#btnJcszxx").hide();
	}
	
	
	
	if("${prjCheck.hasCheckCZY}"!=""){
		$("#sysTips").show();
	}
	
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={			
		url:"${ctx}/admin/prjManCheck/goJqGrid_prjWithHold",	
		postData:{custCode:"${prjCheck.custCode}"},
		ondblClickRow: function(){
	              	$("#btnView").on();
	              },
	          height:140,
		styleUI: 'Bootstrap',
		cellEdit:true,			
		colModel : [		
		 	{name: "pk", index: "pk", width: 30, label: "编号", sortable: true, align: "left", count: true, hidden: true},
			{name: "custcode", index: "custcode", width: 74, label: "客户编号", sortable: true, align: "left", hidden: true},
			{name: "worktype1descr", index: "worktype1descr", width: 80, label: "工种分类1", sortable: true, align: "left"},
			{name: "worktype2descr", index: "worktype2descr", width: 80, label: "工种分类2", sortable: true, align: "left"},
			{name: "typedescr", index: "typedescr", width: 60, label: "类型", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 60, label: "金额", sortable: true, align: "left", sum: true},
			{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
			{name: "iscreatedescr", index: "iscreatedescr", width: 70, label: "是否生成", sortable: true, align: "left"},
			{name: "itemappno", index: "itemappno", width: 70, label: "领料单号", sortable: true, align: "left",hidden:true},
			{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "最后更新人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 70, label: "过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 70, label: "操作", sortable: true, align: "left"},
			{name: "worktype1", index: "worktype1", width: 80, label: "工种分类1", sortable: true, align: "left",hidden:true},
			{name: "worktype2", index: "worktype2", width: 80, label: "工种分类2", sortable: true, align: "left",hidden:true},
			{name: "type", index: "type", width: 60, label: "类型", sortable: true, align: "left",hidden:true}
	    ], 
        loadonce: true,
        gridComplete:function(){
  			if ("${prjCheck.custTypeType}"=="2") {
        		$("#dataTable_prjWithHold").jqGrid('showCol', "itemappno");
          	}	
        },
 		beforeSaveCell:function(rowId,name,val,iRow,iCol){
			lastCellRowId = rowId;	
		},
		afterSaveCell:function(rowId,name,val,iRow,iCol){
		    var rowData = $("#dataTable_prjWithHold").jqGrid("getRowData",rowId);
		    rowData["sumcost"] = (parseFloat(rowData.qty)*parseFloat(rowData.cost)).toFixed(2);
			Global.JqGrid.updRowData("dataTable_prjWithHold",rowId,rowData);	
	  		 },
	  		 beforeSelectRow:function(id){
	       	   relocate(id,"dataTable_prjWithHold");
	      
	        }	   
		}; 	     
    //初始化送货申请明细
	Global.JqGrid.initEditJqGrid("dataTable_prjWithHold",gridOption); 
		
	//新增
	$("#btnAdd").on("click",function(){	 
		Global.Dialog.showDialog("Save", {
			title : "预扣录入--新增",
			url : "${ctx}/admin/prjWithHold/goSave",
			postData:{custCode:"${prjCheck.custCode}",custDescr :"${prjCheck.custDescr}",address:"${prjCheck.address}",m_umState:"A"},
			height: 330,
			width:700,
			returnFun:function(){
				loadPrjWithHoldTab();
				$("#isYklrMainExit").val("1");	
			}
		});
	});
	$("#btnUpdate").on("click",function(){
			var ret = selectDataTableRow("dataTable_prjWithHold");
			if(ret){
				Global.Dialog.showDialog("add",{
					title:"预扣录入——编辑",
					url:"${ctx}/admin/prjWithHold/goUpdate?id="+ret.pk,
					height: 330,
					width:700,
				    returnFun:function(){
						loadPrjWithHoldTab();
						$("#isYklrMainExit").val("1");
					}
				});
			}else{
				art.dialog({
						content:"请选择一条数据",
				});
				return;
			}	
	});
	//查看 
	$("#btnView").on("click",function(){
		var ret = selectDataTableRow("dataTable_prjWithHold");
		if(ret){
			Global.Dialog.showDialog("add",{
				title:"预扣录入——查看",
				url:"${ctx}/admin/prjWithHold/goDetail?id="+ret.pk,
				height: 330,
				width:700,
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
	});		
	//删除
	$("#btnDel").on("click",function(){
		var ret = selectDataTableRow("dataTable_prjWithHold");
		if(!ret){
			art.dialog({
				content:"请选择一条数据 进行删除",
			});
			return;
		}
		art.dialog({
       		content: '是删除该记录？',
	        lock: true,
	        width: 160,
	        height: 50,
	        ok: function () {
	       		$.ajax({
					url:"${ctx}/admin/prjWithHold/doDelete?id="+ret.pk,
					type: "post",
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
				    	if(obj.rs){
				    		art.dialog({
								content: "删除成功",
								time: 1000,
							});
							loadPrjWithHoldTab();
							$("#isYklrMainExit").val("1");
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
		    cancel: function () {
	      		return;
		    }
	   	});
		
	}); 

	//明细输出到Excel
	$("#btnExcel").on("click",function(){
		doExcelNow('${prjCheck.address}'+'-预扣录入','dataTable_prjWithHold', 'dataForm');	
	});
	//基础信息查看
	$("#btnJcszxx").on("click",function(){
		/* var recordDate=$("#dataTable_prjWithHold").jqGrid('getGridParam','records');
		if(recordDate==0){
			art.dialog({
				content:"请先检索出相应的信息"
			});
			return;
		}else{
			Global.Dialog.showDialog("jcszxx",{
				title:"基础收支信息【"+"${prjCheck.address}"+"】",
				url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+"${prjCheck.custCode}",
				height:730,
				width:1350
			});	
		} */
		Global.Dialog.showDialog("jcszxx",{
				title:"基础收支信息【"+"${prjCheck.address}"+"】",
				url:"${ctx}/admin/itemSzQuery/goJcszxx?code="+"${prjCheck.custCode}",
				height:730,
				width:1350
			});	
	});	
	//预扣生成
	$("#btnGenerate").on("click",function(){
		var datas=$("#dataForm").jsonForm();
		datas.m_umState="D";	
		$.ajax({
			url:'${ctx}/admin/prjManCheck/doCheck',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,	
					});
					loadPrjWithHoldTab();
					$("#isYklrMainExit").val("1");	
					$("#_form_token_uniq_id").val(obj.token.token);
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 });
		
	});	

});

function loadPrjWithHoldTab(){
	$("#dataTable_prjWithHold").jqGrid("setGridParam",{url:"${ctx}/admin/prjManCheck/goJqGrid_prjWithHold",datatype:'json',postData:{custCode:"${prjCheck.custCode}"},page:1}).trigger("reloadGrid");
}

</script>	
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system" >
	    <div class="panel-body" >
	    	<div class="btn-group-xs" >
				<button type="button" class="btn btn-system "   id="btnAdd" >新增</button>
				<button type="button" class="btn btn-system "   id="btnUpdate">编辑</button>
				<button type="button" class="btn btn-system "  id="btnDel">删除</button>
				<button type="button" class="btn btn-system "  id="btnView">查看</button>
				<button type="button" class="btn btn-system "  id="btnExcel">输出到Excel</button>	
				<button type="button" class="btn btn-system "  id="btnGenerate">预扣生成</button>	
				<button type="button" class="btn btn-system " id="btnJcszxx">基础收支信息</button>
				<span id="sysTips" hidden="true" style="font-size: 12px;color: red;margin-left:5px">说明：该客户已经完成项目经理结算数据生成操作！</span>
				<input hidden="true" id="isYklrMainExit" value="0" />
			</div>
		</div>
	</div>
	<table id="dataTable_prjWithHold" style="overflow: auto;"></table>

</div>	
		




