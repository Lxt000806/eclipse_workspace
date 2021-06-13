<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Item列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_worker.js?v=${v}" type="text/javascript"></script>

<script type="text/javascript">
/**初始化表格*/
$(function(){
	if("${worker.m_umState}"!="E"){
		$("#update").attr("disabled","disabled");
	}
	$("#projectMan").openComponent_employee({showValue:'${customer.projectMan}',showLabel:'${employee.nameChi}',readonly:true});
	$("#beforeSameWorkType12Descr").openComponent_worker({showValue:'${befWorkType12Worker.code}',showLabel:'${befWorkType12Worker.nameChi}',readonly:true});

  	Global.LinkSelect.initSelect("${ctx}/admin/worker/workType12","workType12","workType12Dept");
	Global.LinkSelect.initSelect("${ctx}/admin/worker/region","region","region2");
	Global.LinkSelect.setSelect({firstSelect:'region',
								firstValue:'${worker.region }',
								secondSelect:'region2',
								secondValue:'${worker.region2 }',});
	
	Global.LinkSelect.setSelect({firstSelect:'workType12',
								firstValue:'${worker.workType12Query }',
								secondSelect:'workType12Dept',
								secondValue:'${worker.workType12Dept }'
								});	

	var postData = $("#page_form").jsonForm();
    //初始化表格
	var gridOption ={
		//url:"${ctx}/admin/worker/goCodeJqGrid",
		postData: postData,
		height:295,
		styleUI: 'Bootstrap', 
		colModel : [
		  {name : 'Code',index : 'Code',width : 70,label:'工人编号',sortable : true,align : "left"},
		  {name : 'NameChi',index : 'NameChi',width : 70,label:'工人名称',sortable : true,align : "left",},
		  {name : 'descr',index : 'descr',width : 100,label:'工人名称',sortable : true,align : "left",hidden:true},
		  {name : 'WorkType12',index : 'WorkType12',width : 70,label:'工人工种',sortable : true,align : "left",hidden:true},
		  {name : 'worktype12descr',index : 'worktype12descr',width :70,label:'工人工种',sortable : true,align : "left"},
		  {name : 'workerleveldescr',index : 'workerleveldescr',width :70,label:'工人星级',sortable : true,align : "left"},
		  {name : 'Num',index : 'Num',width : 70,label:'工人人数',sortable : true,align : "right"},
		  {name : 'ondo',index : 'ondo',width : 90,label:'在建施工数',sortable : true,align : "right"},
		  {name : 'cmpdo',index : 'cmpdo',width : 90,label:'在建完工数',sortable : true,align : "right"},
		  {name : 'stopdo',index : 'stopdo',width : 90,label:'在建停工数',sortable : true,align : "right"},
		  {name : 'canreceiptdate',index:'canreceiptdate',width:80,label:'可接单日期',	sortable:true,align:"left",formatter: formatTime},
		  {name : 'Efficiency',index : 'Efficiency',width : 70,label:'工作效率',sortable : true,align : "right"},
		  {name : 'vehicledescr',index : 'vehicledescr',width : 70,label:'交通工具',sortable : true,align : "left"},
		  {name : 'rcvtypedescr',index : 'rcvtypedescr',width : 70,label:'承接工地',sortable : true,align : "left"},
		  {name : 'belongregiondescr',index : 'belongregiondescr',width : 200,label:'施工区域',sortable : true,align : "left"},
		  {name : 'Address',index : 'Address',width : 70,label:'住址',sortable : true,align : "left"},
		  {name : 'spcbuilderdescr',index : 'spcbuilderdescr',width : 70,label:'专属专盘',sortable : true,align : "left"},
		  {name : 'Remarks',index : 'Remarks',width : 110,label:'备注',sortable : true,align : "left"},
		  {name : 'issigndescr',index : 'issigndescr',width : 90,label:'签约类型',sortable : true,align : "left"},
		  {name : 'prjregiondescr',index : 'prjregiondescr',width : 70,label:'工程大区',sortable : true,align : "left"},
		  {name : 'region2descr',index : 'region2descr',width : 80,label:'居住区域',sortable : true,align : "left"},
		  {name : 'department1descr',index : 'department1descr',width : 80,label:'专属事业部',sortable : true,align : "left"},
        
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	};
		var workType12 = $.trim($("#workType12").val());
		if(workType12==''){
			Global.JqGrid.initJqGrid("dataTable",gridOption);
		}else{
			Global.JqGrid.initJqGrid("dataTable", $.extend(gridOption, {url:"${ctx}/admin/worker/goCodeJqGrid",}));
		}
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/worker/goViewOnDoDetail",
				postData:{code:ret.Code},
				height:600,
				width:1100,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#update").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("update",{
				title:"工人信息——编辑",
				url:"${ctx}/admin/worker/goUpdateWorker",
				postData:{code:ret.Code},
				height:600,
				width:850,
				returnFun:goto_query
			});
		}else{
			art.dialog({
				content:"请选择一条数据",
			});
		}
	});
	
	$("#viewNormDay").on("click",function(){
		var code = $.trim($("#code").val());
		var normConstructDay = $.trim($("#normConstructDay").val());
			Global.Dialog.showDialog("View",{
				title:"工作量——查看",
				url:"${ctx}/admin/CustWorkerApp/goViewWorker",
				postData:{custCode:code,prjnormday:normConstructDay},
				height:700,
				width:1050,
				returnFun:goto_query
			});
	});
	
	$("#viewWorkerArr").on("click",function(){
		var code = $.trim($("#code").val());
			Global.Dialog.showDialog("viewWorkerArr",{
				title:"上阶段工人——查看",
				url:"${ctx}/admin/CustWorkerApp/goViewWorkerArr",
				postData:{code:code},
				height:700,
				width:1050,
				returnFun:goto_query
			});
	});
	
	window.goto_query = function(){
		var workType12 = $.trim($("#workType12").val());
		if(workType12==''){
			art.dialog({
				content:"请选择工种",
			});
			return false;
		}
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,url:"${ctx}/admin/worker/goCodeJqGrid",}).trigger("reloadGrid");
	}
	
	$("#nameChi").focus();
});
</script>
</head>
    
<body>
	<div class="body-box-list">
	<div class="panel panel-info" >  
         <div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
			  		<house:token></house:token>
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li hidden="true">
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }" readonly="true" />
							</li>
							<li >
								<label>楼盘地址</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true" />
							</li>
							<li >
								<label>面积</label>
								<input type="text" id="area" name="area" style="width:160px;" value="${customer.area }" readonly="true"/>
							</li>
							<li >
								<label>客户类型</label>
								<house:xtdm  id="custType" dictCode="CUSTTYPE"   style="width:160px;" value="${customer.custType }" disabled="true"></house:xtdm>
							</li>
							<li >
								<label>项目经理</label>
								<input type="text" id="projectManDescr" name="projectManDescr" style="width:160px;" value="${customer.projectManDescr }" readonly="true"/>
							</li>
							<li >
								<label>项目经理电话</label>
								<input type="text" id="phone" name="phone" style="width:160px;" value="${employee.phone }" readonly="true"/>
							</li>
							<li >
								<label>工程事业部</label>
								<input type="text" id="department1" name="department1" style="width:160px;" value="${employee.department1 }" readonly="true"/>
								
							</li>
							<li >
								<label>是否签约</label>
								<input type="text" id="isSupvrDescr" name="isSupvrDescr" style="width:160px;" value="${employee.isSupvrDescr }" readonly="true"/>
							</li>
							<li >
								<label>星级</label>
								<input type="text" id="prjLevelDescr" name="prjLevelDescr" style="width:160px;" value="${employee.prjLevelDescr }" readonly="true"/>
							</li>
							<li >
								<label>专盘</label>
								<input type="text" id="spcBuilder" name="spcBuilder" style="width:160px;" value="${customer.spcBuilder}" />
							</li>
							<li>
								<label>工程大区</label>
								<input type="text" id="prjRegionDescr" name="prjRegionDescr" style="width:160px;" value="${prjRegionDescr}" />
							</li>
							<li >
								<label>标准工期</label>
								<input type="text" id="normConstructDay" name="normConstructDay" style="width:160px;" value="${customer.normConstructDay }" readonly="true"/>
								<button type="button" class="btn btn-system "  style="width:40px;height: 20px ;font-size: 5px;text-align: center "id="viewNormDay" >
									<span>查看</span>
								</button>
							</li>
							<li style="vertical-align:middle;margin-left:-25px;">
								<label style="width:72px ">上阶段工人</label>
								<input type="text" id="beforeSameWorkType12Descr" name="beforeSameWorkType12Descr" style="width:160px;" value="${befWorkType12Worker.code }"/>
								<button type="button" class="btn btn-system "  style="width:40px;height: 20px ;font-size: 5px;text-align: center "id="viewWorkerArr" >
									<span>查看</span>
								</button>
							</li>
						</ul>	
				</form>
		</div>
	</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
						<ul class="ul-form">
							<li> 
								<label >工人编号</label>
								<input type="text" style="width:160px"id="code" name="code"  value="${worker.code}" />
							</li>
							<li> 
								<label >工人名称</label>
								<input type="text" id="nameChi" style="width:160px" name="nameChi"  value="${worker.nameChi}" />
							</li>
							<li> 
								<label >签约类型</label>
								<house:xtdm  id="isSign" dictCode="WSIGNTYPE"   style="width:160px;" value="${worker.isSign}"></house:xtdm>
							</li>
							<li>
								<label>工程大区</label>
								<house:DataSelect id="prjRegionCode" className="PrjRegion" keyColumn="code" valueColumn="descr" 
										value="${worker.prjRegionCode }" ></house:DataSelect>
							</li>
							<li >
								<label>专属专盘</label>
								<house:dict id="spcBuilder" dictCode="" sql="select code,descr from tSpcBuilder where 1=1 and expired='F' " 
								sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
							</li>
							<li>
								<label>施工区域</label>
								<house:dict id="conRegion" dictCode="" sql="select Code,(Code+' '+Descr) Descr1 from tRegion where code<>''" sqlValueKey="Code" 
									sqlLableKey="Descr1"/>
							</li>
							<li> 
								<label >在建数少于</label>
								<input type="text" id="onDoNum" style="width:160px" name="onDoNum"  value="${worker.onDoNum}" />
							</li>
							<li> 
								<label >工人星级</label>
								<house:xtdm  id="level" dictCode="WORKERLEVEL"   style="width:160px;" value="${worker.level}" ></house:xtdm>
							</li>
							<li>
								<label>状态</label>
								<house:xtdm id="isLeave" dictCode="WORKERSTS" style="width:160px;" value="0"/>
							</li>
							<li >	
								<label>工种</label>
								<select id="workType12" name="workType12"  value="${worker.workType12Query }" ></select>
							</li>
							<li >
								<label>分组</label>
								<select id="workType12Dept" name="workType12Dept" ></select>
							</li>
							<li>
								<label>工人分类</label>
								<house:xtdm id="workerClassify" dictCode="WORKERCLASSIFY" style="width:160px;" value="${custType.workerClassify }"/>
							</li>
							
						<li id="loadMore">
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
						</li>
					</ul>
			</form>
		</div>
		<div class="btn-panel" >
			<div class="btn-group-sm"  >
				<button type="button" class="btn btn-system "  id="view">
					<span>查看</span>
				</button>
				<button type="button" class="btn btn-system "  id="update">
					<span>编辑</span>
				</button>
			</div>
		</div>
		<!--query-form-->
		<div class="pageContent">
			<div id="content-list">
				<table id= "dataTable"></table>
				<div id="dataTablePager"></div>
			</div> 
		</div>
	</div>	
</body>
</html>


