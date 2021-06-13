<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>巡检任务安排明细新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
      .SelectBG_yellow{
          background-color:yellow;
         }
 </style>
<script type="text/javascript"> 
$(function(){

Global.LinkSelect.initSelect("${ctx}/admin/worker/region","region","region2");
	Global.LinkSelect.setSelect({firstSelect:'region',
								firstValue:'${customer.region }',
								secondSelect:'region2',
								secondValue:'${customer.region2 }',
								});

	$("#builderCode").openComponent_builder();
	$("#appCzy").openComponent_employee();
	$("#custCode").openComponent_customer();
	
	var postData = $("#page_form").jsonForm();
	var dateFrom = $.trim($("#dateFrom").val());
	var beginDate = $.trim($("#beginDate").val());
	if('${type}'=='3'){
		var department2 = $.trim("${customer.department2}");
		$("#department2").val(department2);
	}
	//初始化表格
	Global.JqGrid.initEditJqGrid("dataTable",{
		url:"${ctx}/admin/xjrwap/goAddJqGrid",
		postData:{custCodes:'${custCodes}',constructStatus:'1',checkStatus:'1',isCheckDept:${isCheckDept},
			dateFrom:dateFrom,beginDate:beginDate,checkType:'${type}',department2:$("#department2").val(),importancePrj:"0" },
		multiselect: true,
		cellEdit:true,	
		rowNum : 1000000,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		colModel : [
			{name: "code", index: "code", width: 84, label: "客户编号", sortable: true, align: "left", hidden: true},
			{name: "custdescr", index: "custdescr", width: 84, label: "客户名称", sortable: true, align: "left", hidden: true},
			{name: "regioncode", index: "regioncode", width: 120, label: "楼盘", sortable: true, align: "left",hidden:true},
			{name: "address", index: "address", width: 170, label: "楼盘", sortable: true, align: "left",count:true},
			{name: "builderdescr", index: "builderdescr", width: 115, label: "项目名称", sortable: true, align: "left",	},
			{name: "region1descr", index: "region1descr", width: 75, label: "片区1", sortable: true, align: "left"},
			{name: "region2descr", index: "region2descr", width: 75, label: "片区2", sortable: true, align: "left"},
			{name: "projectmandescr", index: "projectmandescr", width: 80, label: "项目经理", sortable: true, align: "left"},
			{name: "prjdeptdescr", index: "prjdeptdescr", width: 90, label: "归属工程部", sortable: true, align: "left"},
			{name: "nowspeeddescr", index: "nowspeeddescr", width: 110, label: "当前进度", sortable: true, align: "left"},
			{name: "prjitemdescr", index: "prjitemdescr", width: 119, label: "巡检进度", sortable: true, align: "left"},
			{name: "modifycount", index: "modifycount", width: 74, label: "整改次数", sortable: true, align: "right"},
			{name: "checkcount", index: "checkcount", width: 74, label: "已巡检次数", sortable: true, align: "right"},
			{name: "lastprjprogcheckdate", index: "lastprjprogcheckdate", width: 99, label: "最后巡检时间", sortable: true, align: "left", formatter: formatTime},
			{name: "nocheckdate", index: "nocheckdate", width: 80, label: "未巡检天数", sortable: true, align: "right"},
			{name: "distance", index: "distance", width: 118, label: "参考距离/千米", sortable: true, align: "right"},
			{name: "longitude", index: "longitude", width: 118, label: "经度", sortable: true, align: "left",hidden:true},
			{name: "latitude", index: "latitude", width: 118, label: "纬度", sortable: true, align: "left",hidden:true,}
		],
		 onSelectRow:function(id){
		      	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
				$('#nowNo').val(ids.length); 
				$('#nowNo1').val(ids.length); 
        }, 
        onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		
		},
        gridComplete:function(){
        	var distance = Global.JqGrid.allToJson("dataTable","distance");
			arr= distance.fieldJson.split(",");//经度
	    	
	    	if($("#auto").val()=='1'){
		    	autoClick();
	    	}else if($("#auto").val()=='2'){
				$("#auto").val("0");
	    	}
	    },
	});
	//全选
	$("#selAll").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",true);
		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
			$('#nowNo').val(ids.length); 
			$('#nowNo1').val(ids.length); 
	});
	//全不选
	$("#selNone").on("click",function(){
		Global.JqGrid.jqGridSelectAll("dataTable",false);
		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
			$('#nowNo').val(ids.length); 
			$('#nowNo1').val(ids.length); 
	});
	
	$('#cb_dataTable').click(function(){
		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
			$('#nowNo').val(ids.length); 
			$('#nowNo1').val(ids.length); 
	});
	
	//保存	
	$("#saveBtn").on("click",function(){
		var beginDate=new Date($("#beginDate").val());
		var autoNum=$.trim($("#autoNum").val());
		var dateSpan,
	        tempDate,
	        iDays;
		if(beginDate){
			if($("#beginDate").val()!=''){
		        sDate1 = Date.parse(beginDate);
		        sDate2 = Date.parse(new Date);
		        dateSpan = sDate2 - sDate1;
		        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
			}
			$("#lastDate").val(iDays);
		}
		$.ajax({
			url:'${ctx}/admin/xjrwap/doSaveCookie',
			type: 'post',
			data:{cookieDays:iDays,autoNum:autoNum},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	 
		    }
		 });
	
     	var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
     	if(ids.length==0){
     		Global.Dialog.infoDialog("请选择一条或多条记录进行新增操作！");	
     		return;
     	}
     	var selectRows = [];
 		$.each(ids,function(k,id){
 			var row = $("#dataTable").jqGrid('getRowData', id);
 			selectRows.push(row);
 		});
 		Global.Dialog.returnData = selectRows;
 		closeWin();
	  });
	  
	 //自动选择
	$("#autoChoice").on("click",function(){
		var ret = selectDataTableRow();
		var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		if(ids.length>1){
			art.dialog({
				content:"只能选择一个楼盘为参考点，请重新选择",
			});
			return false;
		}
		if(!ret){
			art.dialog({
				content:'请选择一条数据作为参考点',
			});
			return false;
		}
		if(ret.longitude==''){
			$("#longitude").val('0');
		}else{
			$("#longitude").val(ret.longitude);
		}
		if(ret.latitude==''){
			$("#latitude").val('0');
		}else{
			$("#latitude").val(ret.latitude);
		}
		$("#auto").val("1");
		$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
	});
		onCollapse(0);
});
function query(){
	var  dateFrom=new Date($("#dateFrom").val());
	if(dateFrom=="Invalid Date"||dateFrom==""){
		art.dialog({
			content:'从某日起未巡检的工地查询条件不能为空',
		});
		return false;
	}
	$("#dataTable").jqGrid("setGridParam",{url:"${ctx}/admin/xjrwap/goAddJqGrid",postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
}
function autoClick(){
	var ids = $("#dataTable").jqGrid('getDataIDs');
	var num=$.trim($("#autoNum").val());
	if(num==''){
		var ids = $("#dataTable").jqGrid('getDataIDs');
		for (var i=0; i< 10; i++) {
           $("#dataTable").jqGrid('setSelection',ids[i], true);
        }
	}else{
		var ids = $("#dataTable").jqGrid('getDataIDs');
		for (var i=0; i< num; i++) {
           $("#dataTable").jqGrid('setSelection',ids[i], true);
        }
	} 
	$("#auto").val("0");
}
function countDis(rlong,rlati,long,lati){
		var i;
		if(rlong==''){
			rlong=0;
		}
		if(rlati==''){
			rlati=0;
		}
		i=Math.sqrt((((rlong-long)*Math.PI*12656*Math.cos(((rlati+lati)/2)*Math.PI/180)/180)
		   *((rlong-long)*Math.PI*12656*Math.cos(((rlati+lati)/2)*Math.PI/180)/180) ) 
		   +(((rlati-lati)*Math.PI*12656/180)*((rlati-lati)*Math.PI*12656/180) )) ;
		return i;	
	}
function sign(obj){
	if ($(obj).is(':checked')){
		$('#checkStatus').val('1');
	}else{
		$('#checkStatus').val('');
	}
}

function importance(obj){
	if ($(obj).is(':checked')){
		$('#importancePrj').val('1');
	}else{
		$('#importancePrj').val('');
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="selAll">
							<span>全选</span>
						</button>
						<button type="button" class="btn btn-system " id="selNone">
							<span>不选</span>
						</button>
						<button type="button" class="btn btn-system " id="autoChoice">
							<span>自动选择</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>	
			</div>
			<div class="panel panel-info" >  
			<div class="panel-body" style="vertical-align:middle;margin:-15px;">
			  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" readonly="true" id="custCodes" name="custCodes" style="width:160px;" value="${custCodes}"/>
					<input type="hidden" readonly="true" id="type" name="type" style="width:160px;" value="${type}"/>
					<input type="hidden" readonly="true" id="auto" name="auto" style="width:160px;" value="${auto}"/>
					<input type="hidden" readonly="true" id=isCheckDept name="isCheckDept" style="width:160px;" value="${isCheckDept}"/>
					<input type="hidden" readonly="true" id="longitude" name="longitude" style="width:160px;" value="${longitude}"/>
					<input type="hidden" readonly="true" id="latitude" name="latitude" style="width:160px;" value="${latitude}"/>
					<input type="hidden" readonly="true" id="lastDate" name="lastDate" style="width:160px;" value="${lastDate}"/>
						<ul class="ul-form">
							<li >	
								<label>片区1</label>
								<select id="region" name="region" ></select>
							</li>
							<li >
								<label>片区2</label>
								<select id="region2" name="region2" ></select>
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }"/>
							</li>
							<li>	
								<input type="checkbox" id="importancePrj"  name="importancePrj" value="0" onclick="importance(this)" ${customer!='1'?'':'checked'  }/>
								<label autosize="false" LeftToRight="yes" for="importancePrj" style="margin-left:0px;;width:168px;text-align: left; ">只显示重点巡检工地</label>
							</li>
							<li>
								<label>工程部</label>
								<house:dict id="department2" dictCode="" 
									sql="select a.Code,a.code+' '+a.desc1+' '+isnull(e.NameChi,'') desc1  from dbo.tDepartment2 a
											left join tEmployee e on e.Department2=a.Code and e.IsLead='1' and e.LeadLevel='1'  and e.expired='F'
											 where  a.deptype='3' and a.Expired='F'  order By dispSeq " 
									sqlValueKey="Code" sqlLableKey="Desc1" value="${customer.department2 }" >
								</house:dict>
							</li>
							<li>
								<label>上次巡检早于</label>
								<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd'/>"  />
							</li>
							<li>
								<label>自动选择条数</label>
								<input type="text" id="autoNum" name="autoNum" style="width:160px;" value="${autoNum}"/>
							</li>
							<li id="loadMore" >
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse">更多</button>
								<span>选中条数:</span>
								<input type="text" id="nowNo" name="nowNo" style="width:40px; text-align:center; outline:none; background:transparent; 
								border:none" readonly="true"/>条			
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							<div  class="collapse " id="collapse" >
							<ul>
							<li>
								<label>项目名称</label>
								<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="${customer.builderCode }"/>
							</li>
							<li>
								<label>客户类型</label>
								<house:custTypeMulit id="custType"  selectedValue="${customer.custType}"></house:custTypeMulit>
							</li>
							<li>
								<label>施工状态</label>
								<house:xtdmMulit id="constructStatus" dictCode="CONSTRUCTSTATUS" selectedValue="1"></house:xtdmMulit>
							</li>
							<li>
								<label>开工时间早于</label>
								<input type="text" id="beginDate" name="beginDate" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<fmt:formatDate value='${customer.beginDate}' pattern='yyyy-MM-dd'/>"  />
							</li>
							<li>	
								<input type="checkbox" id="checkStatus"  name="checkStatus" value="1" onclick="sign(this)" ${customer!='1'?'checked':''  }/>
								<label autosize="false" LeftToRight="yes" for="checkStatus" style="margin-left:0px;;width:168px;text-align: left; ">排除最近两天安排的待巡检楼盘</label>
							</li>	
							<li>
								<label>实际进度</label>
								<house:xtdmMulit id="prjProgTempNo" dictCode=""  sql="select  CBM,NOTE from txtdm  where id='PRJITEM' and cbm<>'15'   " 
								sqlValueKey="CBM" sqlLableKey="NOTE"  selectedValue="${customer.prjProgTempNo }"></house:xtdmMulit>
							</li>
							<li>
								<label>户型</label>
								<house:xtdm id="layout" dictCode="LAYOUT"  style="width:160px;" value="${customer.layout }" ></house:xtdm>
							</li>
							<li>
								<label>工程事业部</label>
								<house:department1 id="Department1"  depType="3"  ></house:department1>
							</li>
							<li>
								<button data-toggle="collapse" class="btn btn-sm btn-link " data-target="#collapse" >收起</button>
								<span>选中条数:</span>
								<input type="text" id="nowNo1" name="nowNo1" style="width:40px; text-align:center; outline:none; background:transparent; 
								border:none" readonly="true"/>条			
								<button type="button" class="btn  btn-sm btn-system " onclick="query();"  >查询</button>
								<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
							</li>
							</ul>
							</div>
						</ul>
				</form>
				</div>
				</div>
			</div>
			<div class="clear_float"></div>
			<!--query-form-->
			<div class="pageContent" style="vertical-align:middle;margin-top:-15px;">
				<div id="content-list">
					<table id= "dataTable"></table>
				</div> 
			</div>
	</body>	
</html>
