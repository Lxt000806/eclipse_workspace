<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>施工合同信息列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#status").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}

function update(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerUpdate",{
		  title:"施工合同信息录入",
		  url:"${ctx}/admin/customerSghtxx/goUpdate?id="+ret.code,
		  height:720,
		  width:1374,
		  returnFun: goto_query
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function zsg() {
    var ret = selectDataTableRow()
    if (!ret) {
        art.dialog({content: "请选择一条记录"})
        return
    }

    $.ajax({
        url: "${ctx}/admin/customerSghtxx/getNotify?id=" + ret.code + "&status=" + ret.status,
        type: "post",
        dataType: "json",
        cache: false,
        error: function(obj) {
            showAjaxHtml({"hidden": false, "msg": "保存数据出错~"})
        },
        success: function(obj) {
            if ($.trim(obj.msg) === "") {
                doZsg(ret)
            } else if (obj.msg === "【下定时间】未设置【签单时间】已设置!") {
                art.dialog({
                    content: "当前楼盘未转订单，确定继续转施工吗？",
                    ok: function() { doZsg(ret) },
                    cancel: function() {}
                })
            } else {
                art.dialog({content: obj.msg})
                return
            }
        }
    })

}

function doZsg(ret) {
    Global.Dialog.showDialog("zsg", {
        title: "转施工",
        url: "${ctx}/admin/customerSghtxx/doGoZsg?id=" + ret.code + "&status=" + ret.status + "&czybh=${czybh}",
        height: 660,
        width: 700,
        returnFun: goto_query
    })
}

function resign(){
	var ret = selectDataTableRow();
    if (ret) {
	    $.ajax({
			url:"${ctx}/admin/customerSghtxx/resignNotify?custCode="+ret.code+"&status="+ret.status+"&endCode="+ret.endcode,
			type: "post",
			data: {},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if($.trim(obj.msg)==""){
			    	Global.Dialog.showDialog("resign",{
					  	title:"施工合同重签",
			  			url:"${ctx}/admin/customerSghtxx/goResign?id="+ret.code,
					  	height:300,
					  	width:700,
					  	returnFun: goto_query
					});
		    	}else{
		    		if($.trim(obj.msg)=="notify"){
		    			art.dialog({
							content:"此客户有增减单,你确定要对["+ret.code+"]["+ret.descr+"]客户进行重签操作吗?",
							lock: true,
							width: 200,
							height: 100,
							ok: function () {
								Global.Dialog.showDialog("resign",{
								  	title:"施工合同重签",
						  			url:"${ctx}/admin/customerSghtxx/goResign?id="+ret.code,
								  	height:300,
								  	width:700,
								  	returnFun: goto_query
								});	
							},
							cancel: function () {
									return true;
							}
						});	
		    		}else{
			    		art.dialog({
			    			content:obj.msg,
			    		});
			    		return;
		    		}
		    	}
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function doReturn(){
	var ret = selectDataTableRow();
    if (ret) {
		$.ajax({
			url:"${ctx}/admin/customerSghtxx/doReturn",
			type: "post",
			data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"0"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
			    	art.dialog({
						content:"你确认要对["+ret.code+"]["+ret.descr+"]客户进行施工退回操作吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/customerSghtxx/doReturn",
								type: "post",
								data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"1"},
								dataType: "json",
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
							    },
							    success: function(obj){
							    	if(obj.rs==true){
										$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerSghtxx/goJqGrid"}).trigger("reloadGrid");
										art.dialog({
											content:obj.msg,
											time:500,
											beforeunload:function(){
											
												closeWin();
											}
										});	
									}else{
										art.dialog({
											content:obj.msg,
											width:200
										});
									}
							    }
							});
						},
						cancel: function () {
								return true;
						}
					});	
				}else{
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function gjReturn(){
	var ret = selectDataTableRow();
    if (ret) {
	    $.ajax({
			url:"${ctx}/admin/customerSghtxx/doGjReturn",
			type: "post",
			data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"0"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
		    		art.dialog({
						content:obj.msg+"你确认要对["+ret.code+"]["+ret.descr+"]客户进行施工高级退回操作吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/customerSghtxx/doGjReturn",
								type: "post",
								data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"1"},
								dataType: "json",
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
							    },
							    success: function(obj){
							    	if(obj.rs==true){
							    		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerSghtxx/goJqGrid"}).trigger("reloadGrid");
										art.dialog({
											content:obj.msg,
											time:500,
											beforeunload:function(){
												closeWin();
											}
										});				
									}else{
										$("#_form_token_uniq_id").val(obj.token.token);
										art.dialog({
											content:obj.msg,
											width:200
										});
									}
							    }
							});	
						},
						cancel: function () {
								return true;
						}
					});				
				}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
     
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function jzReturn(){
	var ret = selectDataTableRow();
	/* if(ret.endcode=="2" && ret.constructtype=="0"){
		art.dialog({
			content:"纯设计的结转单不允许退回",
		});
		return;
	} */
    if (ret) {
	    $.ajax({
			url:"${ctx}/admin/customerSghtxx/doJzReturn",
			type: "post",
			data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"0"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
		    		art.dialog({
						content:"你确认要对["+ret.code+"]["+ret.descr+"]客户进行施工结转操作吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							$.ajax({
								url:"${ctx}/admin/customerSghtxx/doJzReturn",
								type: "post",
								data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"1"},
								dataType: "json",
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
							    },
							    success: function(obj){
							    	if(obj.rs==true){
										art.dialog({
											content:obj.msg,
											time:500,
											beforeunload:function(){
												$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerSghtxx/goJqGrid"}).trigger("reloadGrid");
												closeWin();
											}
										});	
									}else{
										$("#_form_token_uniq_id").val(obj.token.token);
										art.dialog({
											content:obj.msg,
											width:200
										});
									}
							    }
							});
						},
						cancel: function () {
								return true;
						}
					});	
				}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function zszyReturn(){
	var ret = selectDataTableRow();
	if(ret.endcode !="5"){
		art.dialog({
			content:"只能退回暂时结转状态的客户",
		});
		return;
	}
    if (ret) {
	    $.ajax({
			url:"${ctx}/admin/customerSghtxx/doJzReturn",
			type: "post",
			data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"0"},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj.rs==true){
		    		art.dialog({
						content:"你确认要对["+ret.code+"]["+ret.descr+"]客户进行施工结转操作吗？",
						lock: true,
						width: 200,
						height: 100,
						ok: function () {
							 $.ajax({
								url:"${ctx}/admin/customerSghtxx/doJzReturn",
								type: "post",
								data: {custCode:ret.code,status:ret.status,endCode:ret.endcode,checkStatus:"1"},
								dataType: "json",
								cache: false,
								error: function(obj){
									showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
							    },
							    success: function(obj){
							    	if(obj.rs==true){
										art.dialog({
											content:obj.msg,
											time:500,
											beforeunload:function(){
												$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerSghtxx/goJqGrid"}).trigger("reloadGrid");
												closeWin();
											}
										});	
									}else{
										$("#_form_token_uniq_id").val(obj.token.token);
										art.dialog({
											content:obj.msg,
											width:200
										});
									}
							    }
							});
						},
						cancel: function () {
								return true;
						}
					});	
				}else{
					$("#_form_token_uniq_id").val(obj.token.token);
					art.dialog({
						content:obj.msg,
						width:200
					});
				}
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function jz(){
	var ret = selectDataTableRow();
    if (ret) {
    	if ("1" == ret.status || "2" == ret.status) { //add by zb on 20190419
    		art.dialog({
				content: "客户状态为“未到公司”、“已到公司”不允许做结转操作"
			});
			return;
    	}
	    $.ajax({
			url:"${ctx}/admin/customerSghtxx/getJzNotify?id="+ret.code+"&status="+ret.status,
			type: "post",
			data: {},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if($.trim(obj.msg)==""){
			    	Global.Dialog.showDialog("jz",{
					  	title:"施工合同信息——结转",
			  			url:"${ctx}/admin/customerSghtxx/goJz?id="+ret.code+"&status="+ret.status,
					  	height:300,
					  	width:700,
					  	returnFun: goto_query
					});
		    	}else{
		    		art.dialog({
		    			content:obj.msg,
		    		});
		    		return;
		    	}
		    }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}

function resignView(){
	Global.Dialog.showDialog("resignView",{
		title:"合同重签——查看",
		url:"${ctx}/admin/customerSghtxx/goResignView",
		height:700,
		width:1200
	});
}

function view(){
	var ret = selectDataTableRow();
    if (ret) {
      Global.Dialog.showDialog("customerView",{
		  title:"施工合同信息查看",
		  url:"${ctx}/admin/customerSghtxx/goDetail?id="+ret.code,
		  height:720,
		  width:1370
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
//导出EXCEL
function doExcel(){
	var url = "${ctx}/admin/customerSghtxx/doExcel";
	doExcelAll(url);
}
/**初始化表格*/
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		//url:"${ctx}/admin/customerSghtxx/goJqGrid",
		height:$(document).height()-$("#content-list").offset().top-100,
		colModel : [
	      {name : 'cmpcode',index : 'cmpcode',width : 70,label:'公司编号',sortable : true,align : "left",frozen: true,hidden: true},
	      {name : 'cmpdescr',index : 'cmpdescr',width : 90,label:'公司',sortable : true,align : "left",frozen: true},
	      {name : 'code',index : 'code',width : 70,label:'客户编号',sortable : true,align : "left",frozen: true},
	      {name : 'documentno',index : 'documentno',width : 70,label:'档案号',sortable : true,align : "left",frozen: true},
	      {name : 'descr',index : 'descr',width : 70,label:'客户名称',sortable : true,align : "left",frozen: true},
	      {name : 'address',index : 'address',width : 150,label:'楼盘',sortable : true,align : "left",frozen: true},
	      {name : 'area',index : 'area',width : 90,label:'面积(平方)',sortable : true,align : "right"},
	      {name : 'layoutdescr',index : 'layoutdescr',width : 50,label:'户型',sortable : true,align : "left"},
	      {name : 'setdate',index : 'setdate',width : 110,label:'下定时间',sortable : true,align : "left",formatter: formatDate},
	      {name : 'signdate',index : 'signdate',width : 110,label:'合同签订时间',sortable : true,align : "left",formatter: formatDate},
	      {name : 'regiondescr', index: 'regiondescr', width: 60, label: '片区', sortable: true, align: "left"},
	      {name : 'custtypedescr',index : 'custtypedescr',width : 70,label:'客户类型',sortable : true,align : "left"},
	      {name : 'status',index : 'status',width : 70,label:'客户状态',sortable : true,align : "left",hidden:true},
	      {name : 'endcode',index : 'endcode',width : 70,label:'结束代码',sortable : true,align : "left",hidden:true},
	      {name : 'statusdescr',index : 'statusdescr',width : 70,label:'客户状态',sortable : true,align : "left"},
	      {name : 'contractstatusdescr',index : 'contractstatusdescr',width : 70,label:'电子合同',sortable : true,align : "left"},
	      {name : 'initsigndescr',index : 'initsigndescr',width : 70,label:'草签',sortable : true,align : "left"},
	      {name : 'constructtype',index : 'constructtype',width : 70,label:'施工方式',sortable : true,align : "left",hidden:true},
	      {name : 'constructtypedescr',index : 'constructtypedescr',width : 70,label:'施工方式',sortable : true,align : "left"},
	      {name : 'endcodedescr',index : 'endcodedescr',width : 70,label:'结束原因',sortable : true,align : "left"},
	      {name : 'designmandescr',index : 'designmandescr',width : 60,label:'设计师',sortable : true,align : "left"},
	      {name : 'businessmandescr',index : 'businessmandescr',width : 60,label:'业务员',sortable : true,align : "left"},
	      {name : 'contractfee',index : 'contractfee',width : 70,label:'工程造价',sortable : true,align : "right",sum:true},
	      {name : 'designfee',index : 'designfee',width : 60,label:'设计费',sortable : true,align : "right",sum:true},
	      {name : 'measurefee',index : 'measurefee',width : 60,label:'量房费',sortable : true,align : "right" ,sum:true},
	      {name : 'drawfee',index : 'drawfee',width : 70,label:'制图费',sortable : true,align : "right",sum:true},
	      {name : 'colordrawfee',index : 'colordrawfee',width : 70,label:'效果图费',sortable : true,align : "right",sum:true},
	      {name : 'managefee',index : 'managefee',width : 70,label:'管理费',sortable : true,align : "right",sum:true},
	      {name : 'basefee',index : 'basefee',width : 70,label:'基础费',sortable : true,align : "right",sum:true},
	      {name : 'basedisc',index : 'basedisc',width : 90,label:'基础协议优惠',sortable : true,align : "right",sum:true},
	      {name : 'basefee_dirct',index : 'basefee_dirct',width : 80,label:'基础直接费',sortable : true,align : "right",sum:true},
	      {name : 'basefee_comp',index : 'basefee_comp',width : 80,label:'基础综合费',sortable : true,align : "right",sum:true},
	      {name : 'mainfee',index : 'mainfee',width : 70,label:'主材费',sortable : true,align : "right",sum:true},
	      {name : 'maindisc',index : 'maindisc',width : 70,label:'主材优惠',sortable : true,align : "right",sum:true},
	     // {name : 'czamount',index : 'czamount',width : 70,label:'瓷砖金额',sortable : true,align : "right",sum:true},
	      //{name : 'wyamount',index : 'wyamount',width : 70,label:'卫浴金额',sortable : true,align : "right",sum:true},
	      {name : 'mainservfee',index : 'mainservfee',width : 95,label:'服务性产品费',sortable : true,align : "right",sum:true},
	      {name : 'softfee',index : 'softfee',width : 70,label:'软装费',sortable : true,align : "right",sum:true},
	      {name : 'softdisc',index : 'softdisc',width : 70,label:'软装优惠',sortable : true,align : "right",sum:true},
	      {name : 'softother',index : 'softother',width : 95,label:'软装其他费用',sortable : true,align : "right",sum:true},
	      {name : 'integratefee',index : 'integratefee',width : 70,label:'集成费',sortable : true,align : "right",sum:true},
	      {name : 'integratedisc',index : 'integratedisc',width : 70,label:'集成优惠',sortable : true,align : "right",sum:true},
	      {name : 'cupboardfee',index : 'cupboardfee',width : 70,label:'橱柜费',sortable : true,align : "right",sum:true},
	      {name : 'cupboarddisc',index : 'cupboarddisc',width : 70,label:'橱柜优惠',sortable : true,align : "right",sum:true},
	      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left"},
	      {name : 'toconstructdate',index : 'toconstructdate',width : 120,label:'转施工时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'setadd', index : 'setadd', width : 100, label: '套外基础增项', sortable : true, align : "right"},
	      {name : 'basepersonalplan', index : 'basepersonalplan', width : 100, label:'个性化基础预算', sortable : true, align : "right"},
	      {name : 'managefee_basepersonal', index : 'managefee_basepersonal', width : 100, label:'个性化基础管理费', sortable : true, align : "right"},
	      {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
	      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
	      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
	      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
        ],
        loadComplete: function(){
           	$('.ui-jqgrid-bdiv').scrollTop(0);
	        $("#dataTable").setSelection('1');
        	frozenHeightReset("dataTable");
        	
        },
        ondblClickRow: function(){
			view();
		}
	});
	$("#dataTable").jqGrid('setFrozenColumns');
	$("#designMan").openComponent_employee();
	$("#businessMan").openComponent_employee();
	$("#code").openComponent_customer();
	window.goto_query = function(){
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1,url: "${ctx}/admin/customerSghtxx/goJqGrid"}).trigger("reloadGrid");
	}
});
</script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
				<input type="hidden" id="expired" name="expired" value="${customer.expired }" />
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>客户名称</label>
						<input type="text" id="descr" name="descr" style="width:160px;" value="${customer.descr }" />
					</li>
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" />
					</li>
					<li>
						<label>客户编号</label>
						<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" />
					</li>
					<li>
						<label>设计师编号</label>
						<input type="text" id="designMan" name="designMan" style="width:160px;" value="${customer.designMan}" />
					</li>
					<li>
						<label>业务员编号</label>
						<input type="text" id="businessMan" name="businessMan" style="width:160px;" value="${customer.businessMan}" />
					</li>
					<li>
						<label>签订时间从</label>
						<input type="text" style="width:160px;" id="signDateFrom" name="signDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label>
						<input type="text" style="width:160px;" id="signDateTo" name="signDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.signDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>施工方式</label>
						<house:xtdm id="constructType" dictCode="CONSTRUCTTYPE" value="${customer.constructType }"></house:xtdm>
					</li>
					<li>
						<label>转施工时间从</label>
						<input type="text" style="width:160px;" id="toConstructDateFrom" name="toConstructDateFrom" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.toConstructDateFrom }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>到</label>
						<input type="text" style="width:160px;" id="toConstructDateTo" name="toConstructDateTo" class="i-date" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${customer.toConstructDateTo }' pattern='yyyy-MM-dd'/>"/>
					</li>
					<li>
						<label>客户状态</label>
						<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="${customer.status }"></house:xtdmMulit>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<!--query-form-->
		<div class="btn-panel">
			 <div class="btn-group-sm">
            	<house:authorize authCode="SGHTXX_UPDATE">
            	<button type="button" class="btn btn-system" onclick="update()">合同信息录入</button>
                </house:authorize>
                
                <house:authorize authCode="SGHTXX_ZSG">
                <button type="button" class="btn btn-system" onclick="zsg()">转施工</button>
				</house:authorize>
				
				<house:authorize authCode="SGHTXX_SGRETURN">
				<button type="button" class="btn btn-system" onclick="doReturn()">施工回退</button>
				</house:authorize>
				
				<house:authorize authCode="SGHTXX_GJRETURN">
				<button type="button" class="btn btn-system" onclick="gjReturn()">高级施工回退</button>
				</house:authorize>
				
				<house:authorize authCode="SGHTXX_JZ">
				<button type="button" class="btn btn-system" onclick="jz()">结转</button>
				</house:authorize>
				
                <house:authorize authCode="SGHTXX_JZRETURN">
                <button type="button" class="btn btn-system" onclick="jzReturn()">结转回退</button>
                </house:authorize>
                
                <house:authorize authCode="SGHTXX_ZSZYRETURN">
                <button type="button" class="btn btn-system" onclick="zszyReturn()">暂时转移回退</button>
                </house:authorize>
                
                <house:authorize authCode="SGHTXX_RESIGN">
                <button type="button" class="btn btn-system" onclick="resign()">重签</button>
                </house:authorize>
                
                <house:authorize authCode="SGHTXX_RESIGNVIEW">
                <button type="button" class="btn btn-system" onclick="resignView()">重签查看</button>
                </house:authorize>
                
                <house:authorize authCode="customerSghtxx_VIEW">
                <button type="button" class="btn btn-system" onclick="view()">查看</button>
				</house:authorize>
				<button type="button" class="btn btn-system" onclick="doExcel()">导出excel</button>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>


