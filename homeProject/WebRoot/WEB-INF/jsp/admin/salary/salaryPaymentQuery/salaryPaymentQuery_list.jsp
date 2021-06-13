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
		var salaryMon = $("#salaryMon").val();
		var salaryScheme = $("#salaryScheme").val();
		var paymentDef = $("#paymentDef").val();
   		var url ="${ctx}/admin/salaryCalc/doExcel";
		
		$.ajax({
			url:"${ctx}/admin/salaryCalc/getSalaryStatus",
			type: "post",
			data: {salaryMon: salaryMon, salaryScheme: salaryScheme},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		    	if(obj != "3"){
		    		art.dialog({
		    			content:"导出失败，请等待结算完成后再操作",
		    		});
		    		return;
		    	} else {
		    		if(paymentDef == ""){
		    			url ="${ctx}/admin/salaryCalc/doExcel";
						doExcelAll(url);
		    		} else {
		    			url ="${ctx}/admin/salaryPaymentQuery/doExcel";
						doExcelAll(url);
		    		}
		    	}
		    }
		});
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
	
	$(function (){
		var jqGridOption = {
			height: $(document).height() - $("#content-list").offset().top-70,
			styleUI:"Bootstrap",
			colModel:defaultColModel,
		};
		Global.JqGrid.initJqGrid("dataTable", jqGridOption);
		
		chgScheme();
	});
	
	function setPaymentDefOption(opt){
		$("#paymentDef").find("option").remove();
		$("#paymentDef").append("<option value=\"\">请选择...</option>");
		var value = "";
		if(opt.length > 0 ){
			for(var i = 0; i < opt.length; i++){
				$("#paymentDef").append("<option value=\""+opt[i].PK+"\">"+opt[i].Descr+"</option>");
				if(i == 0){
					value = opt[i].PK;
				}
				if(i == opt.length - 1){
					$("#paymentDef").val(value);
				}
			}
		}
		$("#paymentDef").searchableSelect();
	}
	
	function setSchemeOption(opt){
		$("#salaryScheme").val("");
		$("#salaryScheme").find("option").remove();
		$("#salaryScheme").append("<option value=\"\">请选择...</option>")
		
		if(opt.length > 0 ){
			for(var i = 0; i < opt.length; i++){
				$("#salaryScheme").append("<option value=\""+opt[i].SalaryScheme+"\">"+opt[i].Descr+"</option>")
			}
		}
		$("#salaryScheme").searchableSelect();
		
		$("#paymentDef").val("");
		$("#paymentDef").find("option").remove();
		$("#paymentDef").append("<option value=\"\">请选择...</option>");
		$("#paymentDef").searchableSelect();
	}
	
	function chgPaymentDef(){
		var salaryScheme = $("#salaryScheme").val();
		$.ajax({
			url:"${ctx}/admin/salaryPaymentQuery/getPaymentDefList",
			type: "post",
			data: {salaryScheme: salaryScheme},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		   		setPaymentDefOption(obj);
		    }
		});
	}
	
	function chgScheme(){
		var salaryMon = $("#salaryMon").val();
		$.ajax({
			url:"${ctx}/admin/salaryPaymentQuery/getSchemeList",
			type: "post",
			data: {salaryMon: salaryMon},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
		   		setSchemeOption(obj);
		   		
		    }
		});
	}
	
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
   	
   	function view(name, rowId){
   	
   		var rowData = $("#dataTable").jqGrid("getRowData",rowId);
   		var describe = rowData[name+"_tip"];
   		var postData = {describe: describe};
   		if(isNaN(describe)){
   			postData = {describe: describe};
   		} else {
   			postData = {pk: describe, describe: describe};
   		}
   		
   		Global.Dialog.showDialog("salaryChgList",{
			title:"查看算法",
			postData: postData,
			url:"${ctx}/admin/salaryCalc/goViewCalcDescr",
			height:330,
			width:680,
	        resizable: true,
			returnFun:goto_query
		});	
   	}
   	
	function getMainPageData(flag){
		var salaryMon = $("#salaryMon").val();
		var salaryScheme = $("#salaryScheme").val();
		var department1 = $("#department1").val();
		var paymentDef = $("#paymentDef").val();
		var signCmpCode = $("#conSignCmp").val();
		var dateFrom = $("#dateFrom").val();
		var dateTo = $("#dateTo").val();
		var empStatus = $("#empStatus").val();
		var salaryStatus = $("#salaryStatus").val();
		var empName = $("#empName").val();
		var company = $("#company").val();
		var belongType = $("#belongType").val();
		
		if(salaryMon == ""){
			art.dialog({
				content:"薪酬月份不能为空",
			});
			$("#readding").attr("hidden","true");
			return;
		}
		if(salaryScheme == ""){
			art.dialog({
				content:"薪酬方案不能为空",
			});
			$("#readding").attr("hidden","true");
			return;
		}
		
		if(paymentDef == ""){
			art.dialog({
				content:"报表类型不能为空",
			});
			$("#readding").attr("hidden","true");
			return;
		}
		
		var url="${ctx}/admin/salaryPaymentQuery/getMainPageData";
		var title = "";
		var list = {};
		var colModel=[];
		var leftTitle = ["姓名", "入职日期","工号", "转正日期", "所属公司", "二级部门", "状态", "月份","备注","财务编码","一级部门","状态","所属公司","类型","职位",
						 "身份证号", "岗位类别", "财务编码", "离职日期", "类型","岗位级别","签约公司","一级部门","卡号","户名","银行",];
		var frozenTitle = ["月份","工号","姓名","身份证号"];
		var groupDescr = ""
		var titleGroup = [];
		var groupMap = {};
		var tipList = [];
		var frozenColModel =[];
		var postData = {salaryMon: salaryMon, paymentDef: paymentDef, salaryStatus: salaryStatus, belongType: belongType,
						salaryScheme: salaryScheme,empStatus: empStatus, empName: empName, dateFrom: dateFrom, dateTo: dateTo, company: company}; 
		if(paymentDef == ""){
			url="${ctx}/admin/salaryCalc/getMainPageData";
			postData={salaryScheme: salaryScheme, salaryMon: salaryMon,empStatus: empStatus,
					dateFrom: dateFrom, dateTo: dateTo,empName: empName, salaryStatus: salaryStatus};
		}
		$.ajax({
			url: url,
			type: "post",
			data: postData,
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

							list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,};
							
							if(key.split("tip").length>1){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true};
							}
							if(key.split("hid").length>1){
								list = {name: title, index: title, width: 75, label: title, sortable: true, align: position,hidden:true};
							}
							
							//包含日期字 formatter
							if(key.split("日期").length>1){
								list = {name: title, index: title, width: 90, label: title, sortable: true, align: position,formatter:formatDate};
							}
							
							//需要冻结列的单独保存起来
							if(frozenTitle.indexOf(key)>=0){
								if(key == "身份证号"){
									list = {name: title, index: title, width: 120, label: title, sortable: true, align: position,frozen: true};
								} else {
									list = {name: title, index: title, width: 70, label: title, sortable: true, align: position,frozen: true};
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
						height: $(document).height() - $("#content-list").offset().top-100,
						styleUI:"Bootstrap",
						colModel:colModel,
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
					/* $.each(obj, function (k, v) {
			   			$("#dataTable").addRowData(myRound(k)+1, v);
			   		}); */
			   		
			   		$("#dataTable").jqGrid('setGridParam',{
					      datatype:'local',
					      data : obj,  
					      page:1,
					}).trigger("reloadGrid");
				} else {
					var jqGridOption = {
						height: $(document).height() - $("#content-list").offset().top-100,
						styleUI:"Bootstrap",
						colModel:defaultColModel,
					};
					Global.JqGrid.initJqGrid("dataTable", jqGridOption);
				}
				
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
</script>
</head>

<body style="scrollOffset: 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form" style="padding-top: 15px">
					<li class="form-validate">
						<label>月份</label>
						<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon where 1=1 order by salaryMon desc"  
							 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryPayment.salaryMon }" onchange="chgScheme()"></house:dict>
					</li>
					<li class="form-validate">
						<label>薪酬方案</label>
						<select id = "salaryScheme" name = "salaryScheme" value="" onchange="chgPaymentDef()">
							<option value="">请选择...</option>
						</select>
					</li>
					<li class="form-validate">
						<label>报表类型</label>
						<select id = "paymentDef" name = "paymentDef" value="">
							<option value="">请选择...</option>
						</select>
					</li>
					<li>
						<label style="">人员状态</label>
						<house:xtdm id="empStatus" dictCode="EMPSTS"></house:xtdm>
					</li>
					<li>
						<label style="">薪酬状态</label>
						<house:xtdm id="salaryStatus" dictCode="SALARYSTATUS"></house:xtdm>
					</li>
					<li class="form-validate">
						<label>公司</label>
						<house:dict id="company" dictCode="" sql=" select Code, Descr from tConSignCmp where expired = 'F' "  
							 sqlValueKey="Code" sqlLableKey="Descr"></house:dict>
					</li>
					 <li class="form-validate">
						<label>人员属性</label>	
						<house:xtdm id="belongType" dictCode="EMPBELONGTYPE" value="1" ></house:xtdm>
					</li>
					<li>
						<label style="">人员</label>
						<input type="text" id="empName" name="empName" placeholder="姓名/工号/身份证"/>
					</li>
					<li>
						<label style="">入职时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
					</li>
					<li>						
						<label style="">至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${salaryEmp.joinDate}' pattern='yyyy-MM-dd'/>" />
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
				<house:authorize authCode="SALARYPAYMENTQUERY_EXCEL">
					<button type="button" class="btn btn-system" onclick="doExcel()">
						导出到Excel
					</button>
				</house:authorize>
			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
			<div id="readding" style="position: absolute; left:45%;top: 50%;width: 80px; height: 30px; background: rgb(217,237,247);text-align: center;line-height: 30px;border: solid 1px rgb(240,240,240)" hidden="true">读取中...</div>
		</div>
	</div>
</body>
</html>
