<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
</head>

<body>
	<div class="body-box-form">
			<div class="panel panel-system">
				<div class="panel-body">
					 <div class="btn-group-xs">
						<button type="button" class="btn btn-system view" onclick="doSave()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system view" onclick="autoAssign()">
							<span>自动分配</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
						<span id="tip" style="color:red;margin-left:100px" >操作说明：选择共享人 > 点击自动分配 > 保存</span>
					</div>
				</div>
			</div>
			<ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab">选择共享人</a></li>
			    <li role="presentation"><a href="#second" aria-controls="second" role="tab" data-toggle="tab">共享客户</a></li>
			</ul>
			<div class="tab-content">
			    <div role="tabpanel" class="tab-pane active" id="first">
			    	<div style="padding:10px">
					    <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
								<input type="hidden" id="empAuthority" name="empAuthority" value="1">
								<ul class="ul-form">
								<li>	
									<label>员工编号</label>
									<input type="text" id="number" name="number" value="${employee.number }" />
								</li>
								<li>		
									<label>姓名</label>
									<input type="text" id="nameChi" name="nameChi"  value="${employee.nameChi }" />
								</li>
				    			<c:if test="${employee.onlyPrjMan !='1'}">
									<li>
										<label>职位</label>
										<house:position id="position" value="${employee.position }"></house:position>
									</li>
								</c:if>	
								<c:if test="${employee.onlyPrjMan =='1'}">
									<li>
										<label>职位</label>
										<house:dict id="position" dictCode=""
				                                  sql="select code ,desc2 descr  from tPosition where Type = '6' "
				                                  sqlLableKey="descr" sqlValueKey="code" />
									</li>	
								</c:if>	  
								
								<li class="emp_show">
									<label>一级部门</label>
									<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')" value="${employee.department1 }"></house:department1>
								</li>
								<li class="emp_show" >
									<label>二级部门</label>
									<house:department2 id="department2" dictCode="${employee.department1 }" value="${employee.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')"></house:department2>
								</li>
								<li class="emp_show" >
									<label>三级部门</label>
									<house:department3 id="department3" dictCode="${employee.department2 }" value="${employee.department3 }"></house:department3>
								</li>
								<li class="prj_show" hidden="true">
									<label>工程部</label>
									<house:DictMulitSelect id="prjDepartment2" dictCode="" sql="select a.Code, a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
											left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1' and e.expired='F'
											 where  a.deptype='3' and a.Expired='F' order By dispSeq " 
									sqlLableKey="desc1" sqlValueKey="code" selectedValue="${employee.prjDepartment2 }"  ></house:DictMulitSelect>
								</li>
								<li class="prj_show" hidden="true">
									<label>项目经理星级</label>
									<house:dict id="prjLevel" dictCode=""
			                                  sql="select code, descr from tPrjLevel where Expired='F' order by code  "
			                                  sqlLableKey="descr" sqlValueKey="code" value="${employee.prjLevel}"  />
								</li>	
								<li class="search-group" >
									<input type="checkbox" id="expired_show" name="expired_show"
										value="${employee.expired}" onclick="hideExpired(this)"
										${employee.expired!='T' ?'checked':'' }/><p>隐藏过期</p>
									<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
									<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
								</li>
							</ul>
						</form>
					</div>
					<div id="content-list">
						<table id="dataTable"></table>
						<div id="dataTablePager"></div>
					</div>
			    </div>
			    <div role="tabpanel" class="tab-pane" id="second">
			    	<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<ul class="ul-form">
							<div class="row">
								<input id="numbers" type="hidden" name="numbers">
								<input id="codes" type="hidden" name="codes" value="${resrCust.codes}">
								
								<li class="form-validate">
									<label><span class="required">*</span>共享时限</label>
									<input type="text" id="shareDate" name="shareDate" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd', minDate: '${resrCust.shareDate}'})"
										value="<fmt:formatDate value='${resrCust.shareDate}' pattern='yyyy-MM-dd'/>" />
								</li>
							</div>
						</ul>
					</form>
					<div class="body-box-list" style="margin-top: 0px;">
						<div class="panel panel-system">
							<div class="panel-body">
								<form role="form" class="form-search" id="send_form" method="post">
									<input type="hidden" name="jsonString" value="" /> 
								</form>
								<div class="btn-group-xs">
									<button style="align:left" type="button" id="update"
										class="btn btn-system " onclick="update()">
										<span>修改共享人 </span>
									</button>
									<button style="align:left" type="button" id="del"
										class="btn btn-system " onclick="del()">
										<span>删除 </span>
									</button>
								</div>
							</div>
						</div>
						<div class="clear_float"></div>
						<!--query-form-->
						<div id="content-list">
							<table id="dataTable_resr"></table>
						</div>
					</div>
			    </div>
			</div>
		</div>
	</div>
</body>	
<script>
$(function() {
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/employee/goJqGrid",
		height:350,
		postData: $("#page_form").jsonForm(),
		styleUI: 'Bootstrap',
		multiselect: true,
		colModel : [
		  {name : 'number',index : 'number',width : 100,label:'员工编号',sortable : true,align : "left"},
	      {name : 'namechi',index : 'namechi',width : 100,label:'中文名',sortable : true,align : "left"},
	      {name : 'nameeng',index : 'nameeng',width : 100,label:'英文名',sortable : true,align : "left"},
	      {name : 'posdesc',index : 'posdesc',width : 100,label:'职位',sortable : true,align : "left"},
	      {name : 'prjleveldescr',index : 'prjleveldescr',width : 60,label:'星级',sortable : true,align : "left",hidden: true},
	      {name : 'department1descr',index : 'department1descr',width : 100,label:'一级部门',sortable : true,align : "left"},
	      {name : 'department2descr',index : 'department2descr',width : 100,label:'二级部门',sortable : true,align : "left"},
	      {name : 'department3descr',index : 'department3descr',width : 100,label:'三级部门',sortable : true,align : "left"},
	      {name : 'phone',index : 'phone',width : 100,label:'手机号码',sortable : true,align : "left",hidden: true},
	      {name : 'department1',index : 'department1',width : 100,label:'一级部门',sortable : true,align : "left",hidden: true},
	      {name : 'department2',index : 'department2',width : 100,label:'二级部门',sortable : true,align : "left",hidden: true},
	      {name : 'department3',index : 'department3',width : 80,label:'三级部门',sortable : true,align : "left",hidden: true},
          {name : 'scenedesicustcount',index : 'scenedesicustcount',width : 80,label:'在建套数',sortable : true,align : "right",hidden: true},
          {name : 'nowcount',index : 'nowcount',width : 80,label:'当前持单量',sortable : true,align : "right",hidden: true},
          {name : 'thismonthcount',index : 'thismonthcoun',width : 80,label:'本月排单量',sortable : true,align : "right",hidden: true},
          {name : 'czybh',index : 'czybh',width : 80,label:'操作员编号',sortable : true,align : "right",hidden:true},
          {name : 'leadercode',index : 'leadercode',width : 80,label:'部门领导',sortable : true,align : "right",hidden:true},
          {name : 'leaderdescr',index : 'leaderdescr',width : 80,label:'部门领导',sortable : true,align : "right",hidden:true},
          {name : 'busidrc',index : 'busidrc',width : 80,label:'业务主任',sortable : true,align : "right",hidden:true},
          {name : 'busidrcdescr',index : 'busidrcdescr',width : 80,label:'业务主任',sortable : true,align : "right",hidden:true},
          {name : 'deptypedescr',index : 'deptypedescr',width : 80,label:'部门类型',sortable : true,align : "right",hidden:true},
          {name : 'expensedate',index : 'expensedate',width : 80,label:'预支最后修改时间',sortable : true,align : "right",formatter:formatDate,hidden:true},
          {name : 'prjqualityfee',index : 'prjqualityfee',width : 80,label:'项目经理质保金余额',sortable : true,align : "left",hidden:true},
         ],
	});
	
	Global.JqGrid.initJqGrid("dataTable_resr",{
		url:"${ctx}/admin/ResrCust/goJqGrid",
		postData:{codes:"${resrCust.codes}",custRight:"${resrCust.custRight}",czybh:"${resrCust.czybh }",type:"${resrCust.type }"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		rowNum:100000000,
		colModel : [
			{name:'code',	index:'code',	width:80,	label:'资源客户编号',	sortable:true,align:"left",hidden:true},
			{name:'descr',	index:'descr',	width:80,	label:'客户名称',	sortable:true,align:"left",},
			{name:'address',	index:'address',	width:200,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'shareczydescr',	index:'shareczydescr',	width:80,	label:'共享人',	sortable:true,align:"left",},
			{name:'shareczy',	index:'shareczy',	width:80,	label:'共享人',	sortable:true,align:"left",hidden:true},
			{name:'businessman',	index:'businessman',	width:80,	label:'跟单人',	sortable:true,align:"left",hidden:true},
			{name:'businessmandescr',	index:'businessmandescr',	width:80,	label:'跟单人',	sortable:true,align:"left"},
		],
		loadonce:true
	});
	
	$("#dataForm").bootstrapValidator({
		excluded: ':disabled',
		fields: {  
			shareDate: {
				validators: {
					notEmpty: {
	                    message:'请选择共享时限'
	                }
				}
			},
 		},
	});
		
});

function doSave() {
	var rets = $("#dataTable_resr").jqGrid("getRowData");
	var params = {"resrCustJson": JSON.stringify(rets)};
	var resrArr = $("#dataTable_resr").jqGrid("getRowData");//资源数组
	var canPass=true;
	for(var i=0;i<resrArr.length;i++){
		if(resrArr[i].shareczy==""){
			canPass=false;
			break;
		}
	}
	if(!canPass){
		art.dialog({
			content:"存在共享人为空的记录，请核实",
		});
		return;
	}
	
	Global.Form.submit("dataForm","${ctx}/admin/ResrCust/doShareCust",params,function(ret){
		if(ret.rs==true){
			art.dialog({
				content: ret.msg,
				time: 1000,
				beforeunload: function () {
    				closeWin();
			    }
			});
		}else{
			$("#_form_token_uniq_id").val(ret.token.token);
    		art.dialog({
				content: ret.msg,
				width: 200
			});
		}
	});
}

function autoAssign(){
	var empArr = selectDataTableRows();//分享人数组
	var resrArr = $("#dataTable_resr").jqGrid("getRowData");//资源数组
	var resrIds = $("#dataTable_resr").jqGrid("getDataIDs");//资源id数组
	var avgNum=parseInt(resrArr.length / empArr.length) ;//平均每人应分资源数
	var leftArrIndex=[];//平均分后剩下的资源下标
	if (empArr.length < 1) {
		art.dialog({
			content: "至少选择一个共享人"
		});
		return;
	} 
	
	//选择人数大于资源数，前几个人分
	if(empArr.length > resrArr.length ){
		for(var i=0;i<resrArr.length;i++){
			for(var j=i;j<empArr.length;j++){
				if(resrArr[i].businessman!=empArr[j].number){
					resrArr[i].shareczy=empArr[j].number;
					resrArr[i].shareczydescr=empArr[j].namechi;
					$("#dataTable_resr").setRowData(resrIds[i], resrArr[i]);
					break;
				}
			}
		}
	}
	
	//平均分
	for(var j = 0; j < empArr.length; j++){
		var perGotNum=0;
		
	 	//优先取跟单人为其他人的，防止后面剩下与其他人跟单人重复的
		for(var k=0; k < resrArr.length;k++){
			if(resrArr[k]!=undefined && empArr[j].number!=resrArr[k].businessman){//排除跟单人为本人
				for(var m = 0; m < empArr.length; m++){
					if(resrArr[k]!=undefined && empArr[m].number==resrArr[k].businessman && perGotNum<avgNum ){
						resrArr[k].shareczy=empArr[j].number;
						resrArr[k].shareczydescr=empArr[j].namechi;
						$("#dataTable_resr").setRowData(resrIds[k], resrArr[k]);
						delete resrArr[k];
						perGotNum++;
					}
				} 
			}
		} 
		
		//未达到平均数的，继续分
		if(perGotNum<avgNum){
			for(var k=0; k < resrArr.length;k++){
				if(resrArr[k]!=undefined && empArr[j].number!=resrArr[k].businessman){//排除跟单人为本人
					if(resrArr[k]!=undefined && empArr[j].number!=resrArr[k].businessman){
						resrArr[k].shareczy=empArr[j].number;
						resrArr[k].shareczydescr=empArr[j].namechi;
						$("#dataTable_resr").setRowData(resrIds[k], resrArr[k]);
						delete resrArr[k];
						perGotNum++;
						if(perGotNum>=avgNum){
							break;
						}
					}
				}
			}
		}
		
	}
	
  	//找到未分配的下标，存到数组里面
	for(var i=0;i<resrArr.length;i++){
		if(resrArr[i]!=undefined){
			leftArrIndex.push(i);
		}
	}
	//剩下的分给前几个人
 	for(var i=0;i<leftArrIndex.length;i++){
		for(var j=i;j<empArr.length;j++){
			if(resrArr[leftArrIndex[i]].businessman!=empArr[j].number){
				resrArr[leftArrIndex[i]].shareczy=empArr[j].number;
				resrArr[leftArrIndex[i]].shareczydescr=empArr[j].namechi;
				$("#dataTable_resr").setRowData(resrIds[leftArrIndex[i]], resrArr[leftArrIndex[i]]);
				break;
			}
		}
	} 
}

function del(){
	var id = $("#dataTable_resr").jqGrid("getGridParam","selrow");
	if(!id){
		art.dialog({
			content: "请选择一条记录进行删除操作"
		});
		return false;
	}
	art.dialog({
		 content:"是否确认要删除",
		 lock: true,
		 ok: function () {
			Global.JqGrid.delRowData("dataTable_resr",id);
		},
		cancel: function () {
				return true;
		}
	}); 
}

function update(){
	var ret = selectDataTableRow("dataTable_resr");
	var id = $("#dataTable_resr").jqGrid("getGridParam","selrow");
    if (ret) {
      Global.Dialog.showDialog("update",{
		  title:"修改共享人",
		  url:"${ctx}/admin/ResrCust/goUpdateShareCzy",
		  postData:{shareCzy:ret.shareczy,shareCzyDescr:ret.shareczydescr},
		  height:200,
		  width:400,
		  returnFun: function(data){
		  		if(ret.businessman==data.shareCzy){
		  			art.dialog({
						content: "共享人不能与跟单人相同"
					});
					return;
		  		}
		  		ret.shareczy=data.shareCzy;
		  		ret.shareczydescr=data.shareCzyDescr;
		  		$("#dataTable_resr").setRowData(id,ret);
		  }
		});
    } else {
    	art.dialog({
			content: "请选择一条记录"
		});
    }
}
</script>
</html>
