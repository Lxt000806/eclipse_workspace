<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>薪酬人员档案-列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#page_form input[type='text'] ").val('');
		$("#page_form select[id!='scopeType'][id!='scopeOperate']").val('');
		$.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
		$("#department1").val('');
	} 
	
	$(function(){
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1","department2","department3");
	});
	
	function save(){
		Global.Dialog.showDialog("salaryEmpAdd",{
			  title:"薪酬人员--新增",
			  url:"${ctx}/admin/salaryEmp/goSave",
			  height: 715,
			  width:720,
			  returnFun: goto_query
		});
	}
	
	function seriesSave(){
		Global.Dialog.showDialog("salaryEmpAdd",{
			  title:"薪酬人员--连续新增",
			  url:"${ctx}/admin/salaryEmp/goSave",
			  postData:{isSeries:"1"},
			  height: 715,
			  width:720,
			  returnFun: goto_query
		});
	}
	
	function update(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("salaryEmpUpdate",{
			  title:"薪酬人员--修改",
			  url:"${ctx}/admin/salaryEmp/goUpdate",
			  postData:{
			  	empCode:ret.empcode,
			  },
			  height: 715,
			  width:720,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function view(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("salaryEmpView",{
			  title:"薪酬人员--查看",
			  url:"${ctx}/admin/salaryEmp/goView",
			  postData:{
			  	empCode:ret.empcode,
			  },
			  height: 715,
			  width:720
		  });
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function empSync(){
		Global.Dialog.showDialog("empSync",{
			  title:"薪酬人员--员工信息同步",
			  url:"${ctx}/admin/salaryEmp/goEmpSync",
			  height:650,
			  width:1230,
			  returnFun: goto_query
		});
	}

	function doExcel() {
		var url = "${ctx}/admin/salaryEmp/doExcel";
		doExcelAll(url);
	} 
	
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI : "Bootstrap",
			url:"${ctx}/admin/salaryEmp/goJqGrid",
			postData: $("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
						{name: "empcode", index: "empcode", width: 60, label: "工号", sortable: true, align: "left"},
						{name: "empname", index: "empname", width: 60, label: "姓名", sortable: true, align: "left"},
						{name: "department1descr", index: "department1descr", width: 80, label: "一级部门", sortable: true, align: "left"},
						{name: "department2descr", index: "department2descr", width: 80, label: "二级部门", sortable: true, align: "left"},
						{name: "categorydescr", index: "categorydescr", width: 70, label: "员工类别", sortable: true, align: "left"},
						{name: "consigncmpdescr", index: "consigncmpdescr", width: 80, label: "签约公司", sortable: true, align: "left",},
						{name: "idnum", index: "idnum", width: 120, label: "身份证号", sortable: true, align: "left"},
						{name: "financialcode", index: "financialcode", width: 70, label: "财务编码", sortable: true, align: "left",},
						{name: "positiondescr", index: "positiondescr", width: 70, label: "职位", sortable: true, align: "left",},
						{name: "workingdays", index: "workingdays", width: 80, label: "工作日天数", sortable: true, align: "right",},
						/* {name: "posiclassdescr", index: "posiclassdescr", width: 80, label: "岗位类别", sortable: true, align: "left"},
						{name: "posileveldescr", index: "posileveldescr", width: 70, label: "岗位级别", sortable: true, align: "left"}, */
						{name: "statusdescr", index: "statusdescr", width: 60, label: "状态", sortable: true, align: "left"},
						{name: "joindate", index: "joindate", width: 80, label: "入职日期", sortable: true, align: "left", formatter: formatDate},
						{name: "regulardate", index: "regulardate", width: 80, label: "转正日期", sortable: true, align: "left", formatter: formatDate},
						{name: "leavedate", index: "leavedate", width: 80, label: "离开日期", sortable: true, align: "left", formatter: formatDate},
						/* {name: "salary", index: "salary", width: 70, label: "工资", sortable: true, align: "right"}, */
						{name: "basicsalary", index: "basicsalary", width: 70, label: "基本工资", sortable: true, align: "right"},
						{name: "insurbase", index: "insurbase", width: 70, label: "缴费标准", sortable: true, align: "right"},
						{name: "posisalary", index: "posisalary", width: 70, label: "职务工资", sortable: true, align: "right"},
						{name: "skillsubsidy", index: "skillsubsidy", width: 70, label: "技能补贴", sortable: true, align: "right"},
						{name: "otherbonuse", index: "otherbonuse", width: 70, label: "其他奖励", sortable: true, align: "right"},
						{name: "perfbonuse", index: "perfbonuse", width: 70, label: "绩效", sortable: true, align: "right"},
						{name: "starsubsidy", index: "starsubsidy", width: 70, label: "星级补贴", sortable: true, align: "right"},
						{name: "othersubsidy", index: "othersubsidy", width: 70, label: "其他补贴", sortable: true, align: "right"},
						{name: "socialinsurparamdescr", index: "socialinsurparamdescr", width: 110, label: "社保公积金参数", sortable: true, align: "left",formatter:paramBtn},
						{name: "socialinsurparam", index: "socialinsurparam", width: 90, label: "社保公积金参数", sortable: true, align: "left",hidden:true},
						{name: "edminsurmon", index: "edminsurmon", width: 120, label: "养老保险开始月份", sortable: true, align: "left",},
						{name: "medinsurmon", index: "medinsurmon", width: 95, label: "医保开始月份", sortable: true, align: "left",},
						{name: "houfundmon", index: "houfundmon", width: 118, label: "公积金开始月份", sortable: true, align: "left",},
						{name: "paymodedescr", index: "paymodedescr", width: 70, label: "发放方式", sortable: true, align: "left"},
						{name: "paymodeview", index: "paymodeview", width: 70, label: "发放方案", sortable: true, align: "left",formatter:payModeBtn},
						{name: "banktypedescr", index: "banktypedescr", width: 80, label: "银行卡", sortable: true, align: "left",formatter:bankBtn},
						{name: "istaxabledescr", index: "istaxabledescr", width: 70, label: "是否计税", sortable: true, align: "left"},
						{name: "remarks", index: "remarks", width: 180, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 120, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 95, label: "最后更新人员", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 70, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 70, label: "操作日志", sortable: true, align: "left"},
						{name: "paymode", index: "paymode", width: 80, label: "发放方式", sortable: true, align: "left",hidden:true},
						], 
					});
		onCollapse(44);
	});

	function bankBtn(v,x,r){
		return v==null?"":"<a href='#' onclick='viewBank("+x.rowId+");'>"+v+"</a>";
	}   		
	
	function paramBtn(v,x,r){
		return v==null?"":"<a href='#' onclick='viewParam("+x.rowId+");'>"+v+"</a>";
	}   
	
	function payModeBtn(v,x,r){
		return v==null?"":"<a href='#' onclick='viewPayMode("+x.rowId+");'>"+v+"</a>";
	}  
	
	function viewBank(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		Global.Dialog.showDialog("ViewBank",{
		  title:"银行账户",
		  url:"${ctx}/admin/salaryEmp/goViewBank",
		  postData:{
		  	empCode:ret.empcode,
		  },
		  height:400,
		  width:800
		});
	} 	
	
	function viewParam(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		Global.Dialog.showDialog("socialInsurParamView", {
            title: "社保及公积金参数",
            url: "${ctx}/admin/socialInsurParam/goView",
            postData: {pk: ret.socialinsurparam},
            height: 660,
            width: 700
        });
	} 	
	
	function viewPayMode(id){
		var ret = $("#dataTable").jqGrid('getRowData', id);
		var height=ret.paymode=="3"?400:350;
		Global.Dialog.showDialog("ViewBank",{
		    title:"薪酬发放方式详情",
		    url:"${ctx}/admin/salaryEmp/goViewPayMode",
		    postData:{
		  	   empCode:ret.empcode,
		    },
		    height:height,
		    width:750
		});
	} 			
	
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" id="expired"  name="expired" />
				<ul class="ul-form">
					<li>
						<label style="width:120px">签约公司</label>
						<house:dict id="conSignCmp" dictCode=""
							sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
							sqlLableKey="descr" sqlValueKey="code" />
					</li>
					<li>
						<label style="width:120px">人员类别</label>
						<house:xtdm id="category" dictCode="SALEMPCATEGORY" ></house:xtdm>
					</li>
					<%-- <li>
						<label style="width:120px">前后端</label>
						<house:xtdm id="isFront" dictCode="SALISFRONT" ></house:xtdm>
					</li> --%>
					<li>
                        <label style="width:120px">一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
					<%-- <li>
						<label style="width:120px">岗位类别</label>
						<house:dict id="posiClass" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiClass a where a.Expired='F' " 
						 sqlValueKey="pk" sqlLableKey="descr"></house:dict>							
					</li>
					<li>
						<label style="width:120px">岗位级别</label>
						<house:dict id="posiLevel" dictCode="" sql="select pk,cast(pk as nvarchar(20))+' '+descr descr from tSalaryPosiLevel a where a.Expired='F' " 
						 sqlValueKey="pk" sqlLableKey="descr"></house:dict>							
					</li> --%>
					<li>
						<label style="width:120px">人员状态</label>
						<house:xtdm id="status" dictCode="EMPSTS"></house:xtdm>
					</li>
					<li>
						<label style="width:120px">查询条件</label>
						<input type="text" id="queryCondition" name="queryCondition" placeholder="姓名/工号/身份证/财务编码"/>
					</li>
					<li><label style="width:120px">
						<select id="dateType" name="dateType" style="width:80px">
							  <option value="JoinDate">入职时间</option>
					          <option value="LeaveDate">离职时间</option>
					    </select>从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
					</li>
					<li>						
						<label style="width:120px">至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
					</li>
					<li>
						<label style="width:120px">发放方式</label>
						<house:xtdm id="payMode" dictCode="SALPAYMODE"></house:xtdm>
					</li>
					<li>
						<label style="width:60px"></label>
						<select id="scopeType" name="scopeType" style="width:100px">
					          <option value="BasicSalary">基本工资</option>
					          <option value="EdmInsurMon">养老保险开始月份</option>
					          <option value="MedInsurMon">医保开始月份</option>
					          <option value="HouFundMon">公积金开始月份</option>
					          <option value="InsurLimit">保险费用额度</option>
					          <option value="WorkingDays">工作日天数</option>
					          <option value="InsurBase">缴费标准</option>
					          <option value="PosiSalary">职务工资</option>
					          <option value="PerfBonuse">绩效</option>
					          <option value="OtherSubsidy">其他补贴</option>
					          <option value="OtherBonuse">其他奖励</option>
					    </select>
					    <select id="scopeOperate" name="scopeOperate" style="width:80px">
					        <option value="=">等于</option>
					        <option value=">">大于</option>
					        <option value=">=">大于等于</option>
					        <option value="&lt;">小于</option>
					        <option value="&lt;=">小于等于</option>
				        </select>
				        <input style="width:60px" type="text" id="scopeNum" name="scopeNum" placeholder="数值"/>
					</li>
					<li>
						<label style="width:90px">职位</label>
						<house:DictMulitSelect id="position" dictCode="" sql=" select Rtrim(Code) Code,Desc2 Descr from tPosition where Expired = 'F' "  
							sqlValueKey="Code" sqlLableKey="Descr"></house:DictMulitSelect>
					</li>
					<li class="form-validate">
						<label style="width:120px">人员属性</label>	
						<house:xtdm id="belongType" dictCode="EMPBELONGTYPE" value="1" ></house:xtdm>
					</li>
					<li id="loadMore" >
						<button type="button" class="btn  btn-system btn-sm" onclick="goto_query();"  >查询</button>
						<button type="button" class="btn btn-system btn-sm" onclick="clearForm();"  >清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
			        <house:authorize authCode="SALARYEMP_SAVE">
						<button type="button" class="btn btn-system " onclick="save()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SALARYEMP_SAVE">
						<button type="button" class="btn btn-system " onclick="seriesSave()">
							<span>连续新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SALARYEMP_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SALARYEMP_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SALARYEMP_EMPSYNC">
						<button type="button" class="btn btn-system " onclick="empSync()">
							<span>员工信息同步</span>
						</button>
					</house:authorize>
					<house:authorize authCode="SALARYEMP_EXCEL">
						<button type="button" class="btn btn-system " onclick="doExcel()">
							<span>导出Excel</span>
						</button>
					</house:authorize>
				</div>
		</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>	
</body>
</html>
