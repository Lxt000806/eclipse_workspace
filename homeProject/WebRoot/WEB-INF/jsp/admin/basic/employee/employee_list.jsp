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
<title>员工信息--列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
	function clearForm(){
		$("#type").val('');
		$("#page_form input[type='text']").val('');
		$("#page_form select").val('');
		$.fn.zTree.getZTreeObj("tree_positionType").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_position").checkAllNodes(false);
		$.fn.zTree.getZTreeObj("tree_depType").checkAllNodes(false);
		$("#positionType").val('');
		$("#position").val('');
		$("#depType").val('');
		
		$("#department1").val('');
	    $("#department1"+"_NAME").val('');
	    $.fn.zTree.getZTreeObj("tree_department1").checkAllNodes(false);
	    $("#department2").val('');
	    $("#department2"+"_NAME").val('');
	    $.fn.zTree.getZTreeObj("tree_department2").checkAllNodes(false);
	    $.fn.zTree.init($("#tree_department2"), {}, []);
	    $("#department3").val('');
	    $("#department3"+"_NAME").val('');
	    $.fn.zTree.getZTreeObj("tree_department3").checkAllNodes(false);
	    $.fn.zTree.init($("#tree_department3"), {}, []);
	} 

	function goAdd(){
		Global.Dialog.showDialog("employeeAdd",{
			  title:"员工信息--新增",
			  url:"${ctx}/admin/employee/goSave",
			  postData:"1",
			  height: 700,
			  width:795,
			  returnFun: goto_query
			});
	}
	function goUpdate(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("employeeUpdate",{
			  title:"员工信息--修改",
			  url:"${ctx}/admin/employee/goUpdate",
			  postData:{
			  	id:ret.number,
			  	seniority:ret.seniority,
			  },
			  height:700,
			  width:795,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function goCopy(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("employeeCopy",{
			  title:"员工信息--复制",
			  url:"${ctx}/admin/employee/goCopy",
			  postData:{
			  	id:ret.number,
			  },
			  height:700,
			  width:795,
			  returnFun: goto_query
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function goView(){
		var ret = selectDataTableRow();
	    if (ret) {
	      Global.Dialog.showDialog("employeeView",{
			  title:"员工信息--查看",
			  url:"${ctx}/admin/employee/goDetail",
			  postData:{
			  	id:ret.number,
			  	seniority:ret.seniority,
			  },
			  height:700,
			  width:795
			});
	    } else {
	    	art.dialog({
				content: "请选择一条记录"
			});
	    }
	}
	
	function del(){
		var url = "${ctx}/admin/employee/doDelete";
		beforeDel(url);
	}
	//导出EXCEL

	function doExcel() {
		var url = "${ctx}/admin/employee/doExcel";
		doExcelAll(url);
	} 
	function goSalaryInput(){
		Global.Dialog.showDialog("SalaryInput",{			
		  title:"员工信息--薪资录入",
		  url:"${ctx}/admin/employee/goSalaryInput",
		  height: 720,
		  width:1100,
		});
	}
	function goSalaryView(){
		Global.Dialog.showDialog("SalaryView",{			
		  title:"员工信息--工资查询",
		  url:"${ctx}/admin/employee/goSalaryView",
		  height: 720,
		  width:1100,
		});
	}
	function goEmpTranLogView(){
		Global.Dialog.showDialog("SalaryView",{			
		  title:"员工信息--修改日志查询",
		  url:"${ctx}/admin/employee/goEmpTranLogView",
		  height: 720,
		  width:1100,
		});
	}
	
	/**初始化表格*/
	$(function(){
		Global.JqGrid.initJqGrid("dataTable",{
			styleUI : "Bootstrap",
			url:"${ctx}/admin/employee/goJqGrid",
			postData: $("#page_form").jsonForm(),
			height:$(document).height()-$("#content-list").offset().top-70,
			colModel : [
						{name: "number", index: "number", width: 100, label: "员工编号", sortable: true, align: "left"},
						{name: "namechi", index: "namechi", width: 100, label: "中文名", sortable: true, align: "left"},
						{name: "nameeng", index: "nameeng", width: 80, label: "艺名", sortable: true, align: "left"},
						{name: "idnum", index: "idnum", width: 140, label: "身份证号", sortable: true, align: "left"},
						{name: "marrystatusdescr", index: "marrystatusdescr", width: 60, label: "婚姻", sortable: true, align: "left"},
						{name: "refcode", index: "refcode", width: 80, label: "参考编码", sortable: true, align: "left", hidden: true},
						{name: "icnum", index: "icnum", width: 84, label: "IC卡号", sortable: true, align: "left", hidden: true},
						{name: "birth", index: "birth", width: 120, label: "出生日期", sortable: true, align: "left", formatter: formatDate},
						{name: "sexdesc", index: "sexdesc", width: 70, label: "性别", sortable: true, align: "left"},
						{name: "birtplace", index: "birtplace", width: 70, label: "籍贯", sortable: true, align: "left"},
						{name: "department1descr", index: "department1descr", width: 100, label: "一级部门", sortable: true, align: "left"},
						{name: "department2descr", index: "department2descr", width: 100, label: "二级部门", sortable: true, align: "left"},
						{name: "department3descr", index: "department3descr", width: 100, label: "三级部门", sortable: true, align: "left"},
						{name: "basicwage", index: "basicwage", width: 70, label: "基本工资", sortable: true, align: "right",hidden:true},
						{name: "posdesc", index: "posdesc", width: 80, label: "职位", sortable: true, align: "left"},
						{name: "positiondescr", index: "positiondescr", width: 80, label: "副职", sortable: true, align: "left"},
						{name: "isleaddesc", index: "isleaddesc", width: 70, label: "是否领导", sortable: true, align: "left"},
						{name: "leadleveldesc", index: "leadleveldesc", width: 70, label: "领导级别", sortable: true, align: "left"},
						{name: "typedescr", index: "typedescr", width: 70, label: "员工类型", sortable: true, align: "left"},
						{name: "phone", index: "phone", width: 100, label: "电话", sortable: true, align: "left"},
						{name: "address", index: "address", width: 140, label: "地址", sortable: true, align: "left"},
						{name: "joindate", index: "joindate", width: 110, label: "加入日期", sortable: true, align: "left", formatter: formatDate},
						{name: "posichgdate", index: "posichgdate", width: 110, label: "调岗日期", sortable: true, align: "left", formatter: formatDate},
						{name: "seniority", index: "seniority", width: 88, label: "工龄", sortable: true, align: "left"},
						{name: "edudescr", index: "edudescr", width: 70, label: "文化程度", sortable: true, align: "left"},
						{name: "school", index: "school", width: 120, label: "毕业院校", sortable: true, align: "left"},
						{name: "graduationdate", index: "graduationdate", width: 86, label: "毕业日期", sortable: true, align: "left", formatter: formatDate},
						{name: "schdept", index: "schdept", width: 70, label: "专业", sortable: true, align: "left"},
						{name: "leavedate", index: "leavedate", width: 110, label: "离开日期", sortable: true, align: "left", formatter: formatDate},
						{name: "returndate", index: "returndate", width: 110, label: "返岗日期", sortable: true, align: "left", formatter: formatDate},
						{name: "statusdesc", index: "statusdesc", width: 70, label: "状态", sortable: true, align: "left"},
						{name: "ispretraindesc", index: "ispretraindesc", width: 124, label: "是否参加岗前培训", sortable: true, align: "left"},
						{name: "ispretrainpassdesc", index: "ispretrainpassdesc", width: 123, label: "岗前培训是否毕业", sortable: true, align: "left"},
						{name: "numsdescr", index: "numsdescr", width: 102, label: "课程期数名称", sortable: true, align: "left"},
						{name: "managerregulardate", index: "managerregulardate", width: 100, label: "代理转正时间", sortable: true, align: "left", formatter: formatDate},
						{name: "olddeptdate", index: "olddeptdate", width: 134, label: "业绩归属原部门时间", sortable: true, align: "left", formatter: formatDate},
						{name: "perfbelongmodedecr", index: "perfbelongmodedecr", width: 120, label: "业绩归属模式", sortable: true, align: "left"},
						{name: "olddeptdescr", index: "olddeptdescr", width: 120, label: "原部门", sortable: true, align: "left"},
						{name: "idvaliddate", index: "idvaliddate", width: 98, label: "身份证有效期", sortable: true, align: "left", formatter: formatDate},
						{name: "personchgdate", index: "personchgdate", width: 99, label: "人事调岗时间", sortable: true, align: "left", formatter: formatDate},
						{name: "isschemedesignerdesc", index: "isschemedesignerdesc", width: 100, label: "方案设计师", sortable: true, align: "left"},
						{name: "consigncmpdescr", index: "consigncmpdescr", width: 100, label: "合同签约公司", sortable: true, align: "left"},
						{name: "conbegindate", index: "conbegindate", width: 99, label: "合同开始时间", sortable: true, align: "left", formatter: formatDate},
						{name: "conenddate", index: "conenddate", width: 99, label: "合同截止时间", sortable: true, align: "left", formatter: formatDate},
						{name: "remarks", index: "remarks", width: 120, label: "备注", sortable: true, align: "left"},
						{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
						{name: "lastupdatedby", index: "lastupdatedby", width: 120, label: "最后更新人员", sortable: true, align: "left"},
						{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
						{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
						], 
		      			ondblClickRow:function(){
			      			var message = $("#validVP").val();
		      				if (message=="1"){
								goView();	
							}
		       			}
					});
					onCollapse(44);
				});
			$(function(){
				var message = $("#validVP").val();
				if (message=="2") {
					$("#dataTable").hideCol("idnum");
					$("#dataTable").hideCol("basicwage");
					$("#dataTable").hideCol("school");
					$("#dataTable").hideCol("schdept");
					$("#dataTable").hideCol("leavedate");
					$("#dataTable").hideCol("lastupdate");
					$("#dataTable").hideCol("lastupdatedby");
					$("#dataTable").hideCol("expired");
					$("#dataTable").hideCol("actionLog");
					$("#dataTable").hideCol("managerregulardate");
					$("#dataTable").hideCol("olddeptdate");
					$("#dataTable").hideCol("perfbelongmodedecr");
					$("#dataTable").hideCol("olddeptdescr");
					$("#dataTable").hideCol("graduationdate");
					$("#dataTable").hideCol("address");
					$("#dataTable").hideCol("edudescr");
					$("#dataTable").hideCol("idvaliddate");
					$("#dataTable").hideCol("conbegindate");
					$("#dataTable").hideCol("conenddate");
					$("#dataTable").hideCol("consigncmpdescr");
				}
			});	
			function posChange(){
		        $.ajax({
					url : "${ctx}/admin/employee/getProTypeTreeOpt",
					data : {
						postype:$("#type").val()
					},
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType: "json",
					type: "post",
					cache: false,
				    success: function(obj){
				    	setMulitOption("position",obj.html);
					}
				});
			}
			//获取当前日期准备和身份证日期作对比 
			function hideIdNumExpired(obj){
			    if ($(obj).is(':checked')){
			        var time = $("#time").val();
			        $("#idNumExpired").val(time);
		 	 	}else{
		 	 		$("#idNumExpired").val(null);
		 	 	}
			}
</script>
</head> 
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
			<input type="hidden" name="jsonString" value="" />
			<input type="hidden" name="validVP" id="validVP" value="${employee.validVP}" />
			<input type="hidden" name="viewEmpType" value="0"/>
			<input type="hidden" id="expired"  name="expired" />
			<input type="hidden"  id="idNumExpired" name="idNumExpired"/>
			<input type="hidden" id="time" name="time" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.idValidDate}' pattern='yyyy-MM-dd'/>" />
			<input type="hidden" id="empAuthority" name="empAuthority" value="1" />
				<ul class="ul-form">
					<li><label style="width:120px">员工编号</label> <input type="text" id="number" name="number" /></li>
				    <li><label style="width:120px">姓名</label> <input type="text" id="nameChi" name="nameChi" /></li>
					<li>
						<label style="width:120px">职位类型</label>
						<house:DictMulitSelect id="positionType" dictCode="" 
							sql="select cbm,note from txtdm where id='POSTIONTYPE' order by cbm " onCheck="checkPosType()"
							sqlLableKey="note" sqlValueKey="cbm" selectedValue="${employee.type }"></house:DictMulitSelect>
					</li>
					<li>
						<label style="width:120px">职位</label>
						<house:DictMulitSelect id="position" dictCode="" sql="select rtrim(Code) fd1,rtrim(Desc2) fd2 from tPosition where Expired='F' order by code " 
							sqlLableKey="fd2" sqlValueKey="fd1" selectedValue="${employee.position }"></house:DictMulitSelect>
					</li>
					<li>
						<label style="width:120px">岗前培训是否毕业</label>
						<house:dict id="isPreTrainPass" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
						 sqlValueKey="cbm" sqlLableKey="note"></house:dict>							
					</li>
					<li>
						<label style="width:120px">员工状态</label>
						<house:xtdm id="status" dictCode="EMPSTS"></house:xtdm>
					<%-- 	<house:dict id="status" dictCode="" sql="select distinct status,status+''+note note from tEmployee a left outer join tXTDM  d on a.Status=d.CBM and d.ID='EMPSTS' where a.Status in ('WRT','ACT','OTH','SUS') " 
						 sqlValueKey="status" sqlLableKey="note"></house:dict> --%>							
					</li>
 					<li>
                        <label style="width:120px">一级部门</label>
                        <house:DictMulitSelect id="department1" dictCode="" sql="select code, desc1 from tDepartment1 where Expired='F'" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment1()"></house:DictMulitSelect>
                    </li>
					<li>
                        <label style="width:120px">二级部门</label>
                        <house:DictMulitSelect id="department2" dictCode="" sql="select code, desc1 from tDepartment2 where 1=2" 
                            sqlLableKey="desc1" sqlValueKey="code" onCheck="checkDepartment2()"></house:DictMulitSelect>
                    </li>
					<li id="loadMore">
			          <button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
			          <button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
			          <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
			        </li>
			        <div class="collapse " id="collapse">
			        	<ul>
							<li>
	                            <label style="width:120px">三级部门</label>
	                            <house:DictMulitSelect id="department3" dictCode="" sql="select code, desc1 from tDepartment3 where 1=2"
	                               sqlLableKey="desc1" sqlValueKey="code"></house:DictMulitSelect>
	                        </li>
		 					<li>
								<label style="width:120px">团队</label>
								<house:dict id="teamCode" dictCode="" sql="select code,code+' '+desc1 desc1 from tTeam where Expired='F' order by Code " 
								 sqlValueKey="code" sqlLableKey="desc1"></house:dict>							
							</li> 
							<li>
								<label style="width:120px">是否参加岗前培训</label>
								<house:dict id="isPreTrain" dictCode="" sql="select cbm,cbm+' '+note note from tXTDM a where a.ID='YESNO' " 
								 sqlValueKey="cbm" sqlLableKey="note"></house:dict>							
							</li>
							
							<li><label style="width:120px">课程期数名称</label> <input type="text" id="nums" name="nums" /></li>
							<li>
								<label style="width:120px">入职时间从</label>
								<input type="text" id="joinDateFrom" name="joinDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.joinDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label style="width:120px">至</label>
								<input type="text" id="joinDateTo" name="joinDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.joinDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label style="width:120px">离职时间从</label>
								<input type="text" id="leaveDateFrom" name="leaveDateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.leaveDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label style="width:120px">至</label>
								<input type="text" id="leaveDateTo" name="leaveDateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.leaveDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label style="width:120px">电话号码</label>
								<input type="text" id="phone" name="phone"/>
							</li>
							<li>
								<label style="width:120px">部门类型</label>
								<house:DictMulitSelect id="depType" dictCode="" sql="select rtrim(CBM) fd1,rtrim(NOTE) fd2,CBM from tXTDM where ID='DEPTYPE' order by IBM asc " sqlLableKey="fd2" sqlValueKey="fd1" ></house:DictMulitSelect>
							</li>
							<li>
								<label style="width:120px">合同开始时间从</label>
								<input type="text" id="conBeginDate" name="conBeginDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.conBeginDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>						
								<label style="width:120px">至</label>
								<input type="text" id="conEndDate" name="conEndDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${employee.conEndDate}' pattern='yyyy-MM-dd'/>" />
							</li>
							<li>
								<label style="width:120px">方案设计师</label>
								<house:xtdm id="isSchemeDesigner" dictCode="YESNO"></house:xtdm>
							</li>
							<li>
								<label style="width:120px">员工类型</label>
								<house:xtdm id="type" dictCode="EMPTYPE" unShowValue="3"></house:xtdm>
							</li>
							<li>
								<label style="width:120px">合同签约公司</label>
								<house:dict id="conSignCmp" dictCode=""
									sql="select code, code+' '+descr descr from tConSignCmp where Expired='F' order by code  "
									sqlLableKey="descr" sqlValueKey="code" />
							</li>
							<li class="search-group-shrink">
					              <button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">收起</button>
					              <input type="checkbox"  id="expired_show" name="expired_show" onclick="hideExpired(this)" checked/>   
								  <P>隐藏过期记录</p>
								  <input type="checkbox"  id="idNumExpired_show" name="idNumExpired_show" onclick="hideIdNumExpired(this)"/>  
								  <P>身份证过期</p>
					              <button type="button" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
					              <button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					        </li>
						</ul>
					</div>		
				</ul>
				
			</form>
		</div>
		<div class="btn-panel">
			<div class="btn-group-sm">
			        <house:authorize authCode="employee_ADD">
						<button type="button" class="btn btn-system " onclick="goAdd()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="employee_COPY">
						<button type="button" class="btn btn-system " onclick="goCopy()">
							<span>复制</span>
						</button>
					</house:authorize>
					<house:authorize authCode="employee_UPDATE">
						<button type="button" class="btn btn-system " onclick="goUpdate()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="employee_SALARYINPUT">
						<button type="button" class="btn btn-system " onclick="goSalaryInput()">
							<span>薪资录入</span>
						</button>
					</house:authorize>
					<house:authorize authCode="employee_SALARYVIEW">
						<button type="button" class="btn btn-system " onclick="goSalaryView()">
							<span>工资查询</span>
						</button>
					</house:authorize>
					<house:authorize authCode="EMPLOYEE_EMPTRANLOGVIEW">
						<button type="button" class="btn btn-system " onclick="goEmpTranLogView()">
							<span>修改日志查询</span>
						</button>
					</house:authorize>
					
					<house:authorize authCode="employee_VIEW">
						<button type="button" class="btn btn-system " onclick="goView()">
							<span>查看</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
				</div>
		</div>
		<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
		</div>
	</div>	
</body>
</html>
