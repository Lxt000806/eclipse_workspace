<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>薪酬计算主页</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
<META HTTP-EQUIV="expires" CONTENT="0"/>
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
<%@ include file="/commons/jsp/common.jsp"%>
<style type="text/css">
	::-webkit-input-placeholder { /* Chrome */
	  color: #cccccc;
	}

	</style>
<script type="text/javascript">
	/**导出EXCEL*/
	function doExcel() {
		var url ="${ctx}/admin/salaryCalc/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	var defaultColModel =[
			{name:"pk", index:"pk", label:"pk", width:100, sortable: true, align:"left",hidden:true}, 
			{name:"empcode", index:"empcode", label:"员工编号", width:100, sortable: true, align:"left",hidden:true}, 
			{name:"empname", index:"empname", label:"员工姓名", width:80, sortable: true, align:"left"}, 
			{name:"dept1descr", index:"dept1descr", label:"小巴", sortable: true, width:80,align:"left",}, 
			{name:"dept2descr", index:"dept2descr", label:"部门", sortable: true, width:80,align:"left",}, 
			{name:"positionclassdescr", index:"positionclassdescr", label:"岗位", width:80, sortable: true, align:"left",}, 
			{name:"joindate", index:"joindate", label:"入职日期", width:100, sortable: true, align:"left",formatter:formatDate}, 
			{name:"leavedate", index:"leavedate", label:"离职日期", width:100, sortable: true, align:"left",formatter:formatDate}, 
			{name:"salarymon", index:"salarymon", label:"月份",width:60, sortable: true, align:"left",}, 
			{name:"basicsalary", index:"basicsalary", label:"基本工资", sortable: true, width:80, align:"left",}, 
		];
		
	$(function() {
		//写在标签里面无法修改值
		$("#salaryMon").attr("disabled","true");
		$("#salaryScheme").attr("disabled","true");
		
		var jqGridOption = {
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel:defaultColModel,
		};
		Global.JqGrid.initJqGrid("dataTable", jqGridOption);
		
		$("#readding").removeAttr("hidden","true");
		
		getMainPageData("1");
		
		if($.trim($("#salaryMon").val()) != "" && $.trim($("#salaryScheme").val()) != ""){
			getSalaryStatusCtrl($("#salaryMon").val(),$("#salaryScheme").val());
			isLastCheckedCtrl($("#salaryMon").val(),$("#salaryScheme").val());
		}
	});
	
	function clickOpt(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return 0;
		}
		
      	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"view('"
      			+options.colModel.name+"','"+options.rowId+"')\">"+cellvalue+"</a>";
   	}
   	
   	function formatNameClick(cellvalue, options, rowObject){
      	if(rowObject==null){
        	return '';
		}
		if(cellvalue == null){
			return cellvalue;
		}
      	return "<a href=\"javascript:void(0)\" style=\"\" onclick=\"goDetail('"
      			+options.rowId+"')\">"+cellvalue+"</a>";
   	}
   	
   	function view(name, rowId){
   	
   		var rowData = $("#dataTable").jqGrid("getRowData",rowId);
   		var describe = rowData[name+"_tip"];
   		
   		Global.Dialog.showDialog("salaryChgList",{
			title:"查看算法",
			postData:{pk: describe},
			url:"${ctx}/admin/salaryCalc/goViewCalcDescr",
			height:330,
			width:680,
	        resizable: true,
			returnFun:goto_query
		});	
   	}
   	
   	function goDetail(rowId){
		var  rowData= $("#dataTable").jqGrid("getRowData",rowId);
		var salaryScheme = $("#salaryScheme").val();
		var salaryMon = $("#salaryMon").val();
		
   		Global.Dialog.showDialog("salaryChgList",{
			title:"工资明细",
			postData:{empName: rowData["工号"],salaryScheme: salaryScheme,salaryMon: salaryMon},
			url:"${ctx}/admin/salaryCalc/goViewDetail",
			height:700,
			width:1000,
	        resizable: true,
			returnFun:goto_query
		});	
   	}
	
	function getMainPageData(flag){
		var salaryMon = $("#salaryMon").val();
		var salaryScheme = $("#salaryScheme").val();
		var dept1Code = $("#dept1Code").val();
		var positionClass= $("#positionClass").val();
		var dateFrom = $("#dateFrom").val();
		var dateTo = $("#dateTo").val();
		var empStatus = $("#empStatus").val();
		var empName = $("#empName").val();
	
		var title = "";
		var list = {};
		var colModel=[];
		var leftTitle = ["姓名", "入职日期","工号", "转正日期", "所属公司", "二级部门", "状态", "月份",
						 "身份证号", "岗位类别", "财务编码", "离职日期", "类型","岗位级别","签约公司","一级部门","职位"];
		var frozenTitle = ["月份","工号","姓名","身份证号"];
		var groupDescr = ""
		var titleGroup = [];
		var groupMap = {};
		var tipList = [];
		var frozenColModel =[];
		
		$.ajax({
			url:"${ctx}/admin/salaryCalc/getMainPageData",
			type: "post",
			data: {salaryScheme:salaryScheme,salaryMon:salaryMon,empStatus:empStatus,
					dept1Code:dept1Code,positionClass:positionClass ,dateFrom:dateFrom,dateTo:dateTo,empName:empName},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
	    		$("#content-list").remove();//强制清除grid容器的html,这个标签在渲染表格后由jqgrid创建的表格最外层标签
				$("body").append('<div id="content-list"><table id="dataTable"></table><div id="dataTablePager"></div><div id="readding" style="position: absolute; left:45%;top: 50%;width: 80px; height: 30px; background: rgb(217,237,247);text-align: center;line-height: 30px;border: solid 1px rgb(240,240,240)" hidden="true">读取中...</div></div>');
		    	
				if(obj && obj.length > 0){
					for(var key in obj[0]){
						if(key.split("_tip").length>1){
							tipList.push(key.split("_tip")[0]);
						}
					}
				
					for(var key in obj[0]){
						var position = "right";
						if(leftTitle.indexOf($.trim(key))>=0){
							position = "left";
						}
						
						var titleSplit =key.split("__");
						//判断是否需要按表头分组 
						if(titleSplit.length>1){
							//{"一级表头1":[{col1},{col2}],"一级表头2":[{col3},{col4}]}
							groupDescr = titleSplit[0];
							title = titleSplit[1];
							
							if(title.split("tip").length>1){
								//_tip字段的 隐藏掉不显示
								list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,hidden:true};
							}else {
								//判断是否存在对应的tip 有的话设置格式化为可点击
								if(tipList.indexOf($.trim(key))>=0){
									list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,formatter:clickOpt};
								} else {
									//判断是否默认隐藏字段
									if(title.split("hid").length>1){
										list = {name: key, index: key, width: 75, label: title, sortable: true, align: position,hidden:true};
									} else {
										list = {name: key, index: key, width: 75, label: title, sortable: true, align: position};
									}
								}
							}
							
							//将需要分组的列保存单独起来
							if(!groupMap[groupDescr]){
								titleGroup = [];
								titleGroup.push(list);
								groupMap[groupDescr] = titleGroup;
							} else {
								titleGroup = groupMap[groupDescr];
								titleGroup.push(list);
								groupMap[groupDescr] = titleGroup;
							}
						} else {
							
							title = key;

							list = {name: title, index: title, width: 75, label: title, sortable: true, align: position};
							
							if(key.split("tip").length>1){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true};
							}
							if(key.split("hid").length>1){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true};
							}
							
							//包含日期字 formatter
							if(key.split("日期").length>1){
								list = {name: title, index: title, width: 90, label: title, sortable: true, align: position,formatter: formatDate};
							}
							if(key=="姓名"){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,formatter: formatNameClick};
							}
							
							//需要冻结列的单独保存起来
							if(frozenTitle.indexOf(key)>=0){
								if(key == "身份证号"){
									list = {name: title, index: title, width: 120, label: title, sortable: true, align: position,frozen: true};
								} else {
									list = {name: title, index: title, width: 70, label: title, sortable: true, align: position,frozen: true};
								}
								if(key=="姓名"){
									list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,frozen: true,formatter: formatNameClick};
								}
								frozenColModel.push(list);
								continue;
							}
							
							if(tipList.indexOf($.trim(key))>=0){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,formatter:clickOpt};
							}
							colModel.push(list);
						}
						
					}
					
					var groupHeaders = [];
					//冻结列跟普通列结合 冻结列放前面
					colModel = frozenColModel.concat(colModel);
					
					//遍历将多个双表头分组添加到jqGrid 的 colModel
					for(var groupKey in groupMap){
						var groupSet = {};
						groupSet.startColumnName = groupMap[groupKey][0].name;
						groupSet.numberOfColumns = groupMap[groupKey].length;
						groupSet.titleText = groupKey;
						groupHeaders.push(groupSet);
						
						colModel = colModel.concat(groupMap[groupKey]);
					}
					
					var jqGridOption = {
						height: $(document).height() - $("#content-list").offset().top-92,
						styleUI:"Bootstrap",
						colModel:colModel,
						rowNum:50,
						rowList: [50, 200, 500, 1000],
						gridComplete:function(){
				        	frozenHeightReset("dataTable");
				        },
					};
					//初始化jqGrid
					Global.JqGrid.initJqGrid("dataTable", jqGridOption);
					$("#dataTable").jqGrid('setGroupHeaders', {
						useColSpanStyle: true, 
						groupHeaders:groupHeaders
					});
					
					$("#dataTable").jqGrid('setFrozenColumns');
					//遍历插入数据
					/*$.each(obj, function (k, v) {
			   			$("#dataTable").addRowData(myRound(k)+1, v);
			   		}); */
			   		
					 $("#dataTable").jqGrid('setGridParam',{
					      datatype:'local',
					      data : obj,  
					      page:1,
					}).trigger("reloadGrid");
			   		//$("#dataTable").addRowData('id', obj);
				} else {
					var jqGridOption = {
						height: $(document).height() - $("#content-list").offset().top-72,
						styleUI:"Bootstrap",
						colModel:defaultColModel,
					};
					Global.JqGrid.initJqGrid("dataTable", jqGridOption);
				}
		    }
		});
	}
	
	// 设置页脚的合计
	function setFooterData(dataTableId){
		var sumCols = getSumCol();
		var value = 0;
		var sum = 0;
		if(sumCols.length > 0){
 	    	var footerData = {};
 	    	footerData["rn"] = "合计";
 	    	$.each(sumCols,function(k,v){
 	    		sum = 0;
    			var d = $("#"+dataTableId).getCol(v);
    			if(d.length > 0){
	    			for(var i = 0; i< d.length; i++){
	 	    			var suffix = d[i].split("\">");
	 	    			if(suffix.length>1){
	 	    				value = suffix[1].split("</a>")[0];
	 	    				sum+=myRound(value,2);
	 	    			} else {
	 	    				value = d[i];
	 	    				sum+=myRound(value,2);
	 	    			}
	    			}
	    			var map = {};
	    			map[v] = myRound(sum,2);
	    			$("#"+dataTableId).footerData("set", map, false);
    			}
    		});
    	}
	}
	
	function updateCalcCondition(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		
		Global.Dialog.showDialog("updateCalcCondition",{
			title:"薪酬计算条件——修改",
			postData:{salaryScheme:salaryScheme, salaryMon:salaryMon, status:$("#status").val()},
			url:"${ctx}/admin/salaryCalc/goUpdateCalcCondition",
			height:330,
			width:580,
	        resizable: true,
			returnFun:function(data){
				$("#salaryScheme").val(data.salaryScheme);
				$("#salaryMon").val(data.salaryMon);
				getSalaryStatusCtrl(data.salaryMon, data.salaryScheme);
				isLastCheckedCtrl(data.salaryMon, data.salaryScheme);
				query('0');
			}
		});	
	}
	
	function goSalaryChgList(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
	
		Global.Dialog.showDialog("salaryChgList",{
			title:"薪酬计算——薪酬调整列表",
			postData:{salaryScheme:salaryScheme,salaryMon:salaryMon},
			url:"${ctx}/admin/salaryCalc/goSalaryChgList",
			height:750,
			width:1314,
	        resizable: true,
			returnFun:goto_query
		});	
		
		/* if(!ret){
			art.dialog({
				content:"请选择需要调整的员工",
			});
			return;
		}
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());

		Global.Dialog.showDialog("salaryChgList",{
			title:"薪酬计算——薪酬调整",
			postData:{salaryEmp:ret["工号"],salaryScheme:salaryScheme,salaryMon:salaryMon},
			url:"${ctx}/admin/salaryCalc/goSalaryChgList",
			height:430,
			width:780,
	        resizable: true,
			returnFun:goto_query
		});	 */
	}
	
	function goSchemeEmpList(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		
		if(salaryScheme ==""){
			art.dialog({
				content:"薪酬项目为空，请先修改薪酬项目"
			});
			return;
		}
		
		Global.Dialog.showDialog("updateCalcCondition",{
			title:"薪酬计算——薪酬方案人员名单",
			postData:{salaryScheme:salaryScheme},
			url:"${ctx}/admin/salaryCalc/goSchemeEmpList",
			height:700,
			width:1080,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function goCalc(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		
		if(salaryScheme ==""){
			art.dialog({
				content:"薪酬项目为空，请先修改薪酬项目"
			});
			return;
		}
		
		if(salaryMon ==""){
			art.dialog({
				content:"计算月份为空，请先修改月份"
			});
			return;
		}
		
		Global.Dialog.showDialog("goCalc",{
			title:"薪酬计算",
			postData:{salaryScheme:salaryScheme, salaryMon:salaryMon},
			url:"${ctx}/admin/salaryCalc/goCalc",
			height:338,
			width:536,
	        resizable: true,
			returnFun:function(data){
				if(data){
					$("#readding").removeAttr("hidden","true");
					$("#goCalc").attr("disabled","disabled");
					$.ajax({
						url:"${ctx}/admin/salaryCalc/doCalc",
						type: "post",
						data: {salaryScheme:salaryScheme,salaryMon:salaryMon,calcAll:data.calcAll},
						dataType: "json",
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
					    },
					    success: function(obj){
					    	if(obj.rs){
	 							art.dialog({
									content: "计算成功",
									time: 1500,
									beforeunload: function () {
										getMainPageData("0");
										$("#goCalc").removeAttr("disabled","disabled");
										getSalaryStatusCtrl($("#salaryMon").val(),$("#salaryScheme").val());
										isLastCheckedCtrl($("#salaryMon").val(),$("#salaryScheme").val());
									}
								});
							} else {
								art.dialog({
									content: obj.msg,
									time: 1500,
									beforeunload: function () {
										$("#readding").attr("hidden","true");
										$("#goCalc").removeAttr("disabled","true");
										getSalaryStatusCtrl($("#salaryMon").val(),$("#salaryScheme").val());
										isLastCheckedCtrl($("#salaryMon").val(),$("#salaryScheme").val());
										return;
									}
								});
							}
					    }
					});
				}
			}
		});	
	}
	
	function doCheck(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		$("#firstCalcTime").val("1");
		
		$.ajax({
			url:"${ctx}/admin/salaryCalc/hasFirstCalcTime?salaryMon="+salaryMon,
			type: "post",
			data: {},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    async:false,
		    success: function(obj){
		    	$("#firstCalcTime").val(obj.msg);
		    }
		});
		
		if($("#firstCalcTime").val() == "0"){
			art.dialog({
				content:"首次试算时间为空，请先进行薪酬计算",
			});
			return;
		}
				
		if(salaryScheme ==""){
			art.dialog({
				content:"薪酬项目为空，请先修改薪酬项目"
			});
			return;
		}
		
		if(salaryMon ==""){
			art.dialog({
				content:"计算月份为空，请先修改月份"
			});
			return;
		}
		
		art.dialog({
			content:"是否结算该薪酬",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/salaryCalc/doCheck",
					type: "post",
					data: {salaryScheme:salaryScheme,salaryMon:salaryMon},
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
						if(obj.rs){
 							art.dialog({
								content: "结算成功",
								time: 1500,
								beforeunload: function () {
									$("#salaryMon").val(obj.datas);
									getSalaryStatusCtrl($("#salaryMon").val(),$("#salaryScheme").val());
									isLastCheckedCtrl($("#salaryMon").val(),$("#salaryScheme").val());
									query();
								}
							});
						}else {
							art.dialog({
								content: obj.msg,
								time: 1500,
								beforeunload: function () {
									//closeWin(false);
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
	
	function doCheckReturn(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		if(salaryScheme ==""){
			art.dialog({
				content:"薪酬项目为空，请先修改薪酬项目"
			});
			return;
		}
		
		if(salaryMon ==""){
			art.dialog({
				content:"计算月份为空，请先修改月份"
			});
			return;
		}
		
		art.dialog({
			content:"是否确认结算退回",
			lock: true,
			ok: function () {
				$.ajax({
					url:"${ctx}/admin/salaryCalc/doCheckReturn",
					type: "post",
					data: {salaryScheme:salaryScheme,salaryMon:salaryMon},
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
						if(obj.rs){
 							art.dialog({
								content: "结算退回成功",
								time: 1500,
								beforeunload: function () {
									getSalaryStatusCtrl($("#salaryMon").val(),$("#salaryScheme").val());
									isLastCheckedCtrl($("#salaryMon").val(),$("#salaryScheme").val());
								}
							});
						}else {
							art.dialog({
								content: obj.msg,
								time: 1500,
								beforeunload: function () {
									//closeWin(false);
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
	
	function clearQueryForm(){
		$("#dept1Code").val("");
		$("#positionClass").val("");
		$("#dateFrom").val("");
		$("#dateTo").val("");
		$("#empStatus").val("");
	}
	
	function query(flag){
		$("#readding").removeAttr("hidden","true");
		getMainPageData(flag);
	}
	
	function getSalaryStatusCtrl(mon, salaryScheme){
		$.ajax({
			url:"${ctx}/admin/salaryCalc/getSalaryStatusCtrl?salaryMon="+mon+"&salaryScheme="+salaryScheme,
			type: "post",
			data: {},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	$("#monSchemeInfo").text(obj.schemeInfo);
		    	$("#status").val(obj.status);
		    	//薪酬状态为已结算，只能查询信息
		    	if(obj.schemeInfo.indexOf("已结算") != -1){
					$(".showCtrl").attr("disabled",true);
				}else{
					$(".showCtrl").removeAttr("disabled");
				}
		    }
		});
	}
	
	function paymentQuery(){
		var salaryScheme = $.trim($("#salaryScheme").val());
		var salaryMon = $.trim($("#salaryMon").val());
		var salarySchemeDescr = $("#salaryScheme").find("option:selected").text();
		
		Global.Dialog.showDialog("paymentQuery",{
			title:"薪酬分账表"+"【"+salarySchemeDescr+"】",
			postData:{salaryScheme: salaryScheme, salaryMon: salaryMon},
			url:"${ctx}/admin/salaryCalc/goPaymentQuery",
			height:725,
			width:1180,
	        resizable: true,
			returnFun:goto_query
		});	
	}
	
	function isLastCheckedCtrl(mon, salaryScheme){
		$.ajax({
			url:"${ctx}/admin/salaryCalc/isLastCheckedCtrl?salaryMon="+mon+"&salaryScheme="+salaryScheme,
			type: "post",
			data: {},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	//只有当前“月份+薪酬方案”是最后一个已结算的【按月份+薪酬方案类型顺序排序】，才激活结算退回按钮
		    	if(obj.msg == "1"){
					$("#doCheckReturn").removeAttr("disabled");
				}else{
					$("#doCheckReturn").attr("disabled",true);
				}
		    }
		});
	}
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="status" name="status" />
				<input type="hidden" id="firstCalcTime" name="firstCalcTime" />
				<ul class="ul-form" style="margin-top:10px">
					<div class="validate-group row">
						<li class="form-validate">
							<label style="width:80px">计算月份</label>
							<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon"  
								 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }"></house:dict>
						</li>
						<li class="form-validate">
							<label style="width:80px">计算薪酬方案</label>
							<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
								 sqlValueKey="pk" sqlLableKey="descr" value="${salaryData.salaryScheme }"></house:dict>
						</li>
						<li class="search-group">
							<button type="button" class="btn  btn-sm btn-system" onclick="updateCalcCondition();">修改</button>
						</li>
					</div>
					<span id="monSchemeInfo" style="margin-left:100px;color:red"></span>
				</ul>
				
				<div class="clear_float" style="border-bottom:1px solid rgb(212,208,200);width:94%;margin-left:3%;padding:5px"></div>
				
				<ul class="ul-form" style="padding-top: 15px">
					<li>
						<label style="width:80px">一级部门</label>
							<house:dict id="dept1Code" dictCode="" sql="select code,code+' '+desc1 desc1 from tDepartment1 where Expired='F'"  
								sqlValueKey="code" sqlLableKey="desc1"></house:dict>
                    </li>
					<li>
						<label style="width:80px">人员</label>
						<input type="text" id="empName" name="empName" placeholder="姓名/工号/身份证"/>
					</li>
                    <li>
						<label style="width:80px">人员状态</label>
						<house:xtdm id="empStatus" dictCode="EMPSTS"></house:xtdm>
					</li>
					<li>
						<label style="width:80px">入职时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label style="width:80px">至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li hidden="true">
						<label style="width:80px">岗位类别</label>
						<house:dict id="positionClass" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiClass a where a.Expired='F' " 
						 sqlValueKey="pk" sqlLableKey="descr"></house:dict>							
					</li>
					<li class="search-group">
						<button type="button" class="btn  btn-sm btn-system" onclick="query('0');">查询</button>
						<button type="button" class="btn btn-sm btn-system" onclick="clearQueryForm();">清空</button></li>
				</ul>
			</form>
		</div>
		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SALARYCALC_CALC">
					<button type="button" class="btn btn-system showCtrl"  id="goCalc" onclick="goCalc()">
						薪酬计算
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_SCHEMEEMPLIST">
					<button type="button" class="btn btn-system showCtrl" onclick="goSchemeEmpList()">
						方案人员名单
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_SALARYCHGLIST">
					<button type="button" class="btn btn-system showCtrl" onclick="goSalaryChgList()">
						薪酬调整列表
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_CHECK">
					<button type="button" class="btn btn-system showCtrl" onclick="doCheck()">
						结算
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_CHECKRETURN">
					<button type="button" class="btn btn-system" id="doCheckReturn" onclick="doCheckReturn()" >
						结算回退
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_PAYMENTQUERY">
					<button type="button" class="btn btn-system" onclick="paymentQuery()">
						分账查询
					</button>
				</house:authorize>
				<house:authorize authCode="SALARYCALC_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						导出到Excel
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="readding" style="position: absolute; left:45%;top: 50%;width: 80px; height: 30px; background: rgb(217,237,247);text-align: center;line-height: 30px;border: solid 1px rgb(240,240,240)" hidden="true">读取中...</div>
		</div>
	</div>
</body>
</html>
