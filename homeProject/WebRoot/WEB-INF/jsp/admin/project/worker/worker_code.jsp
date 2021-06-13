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

<script type="text/javascript">
/**初始化表格*/
$(function(){
	
	Global.LinkSelect.initSelect("${ctx}/admin/worker/region","region","region2");
	Global.LinkSelect.setSelect({firstSelect:'region',
								firstValue:'${worker.region }',
								secondSelect:'region2',
								secondValue:'${worker.region2 }',});


	var postData = $("#page_form").jsonForm();
    //初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/worker/goCodeJqGrid",
		postData: postData,
		height:320,
		styleUI: 'Bootstrap', 
		colModel : [
		  {name : 'Code',index : 'Code',width : 100,label:'工人编号',sortable : true,align : "left"},
		  {name : 'NameChi',index : 'NameChi',width : 100,label:'工人名称',sortable : true,align : "left"},
		  {name : 'CardID',index : 'CardID',width : 100,label:'卡号1',sortable : true,align : "left"},
		  {name : 'CardId2',index : 'CardId2',width : 100,label:'卡号2',sortable : true,align : "left"},
		  {name : 'WorkType12',index : 'WorkType12',width : 100,label:'工人工种',sortable : true,align : "left",hidden:true},
		  {name : 'worktype12descr',index : 'worktype12descr',width : 90,label:'工人工种',sortable : true,align : "left"},
		  {name : 'issigndescr',index : 'issigndescr',width : 90,label:'签约类型',sortable : true,align : "left"},
		  {name : 'Num',index : 'Num',width : 70,label:'工人人数',sortable : true,align : "left"},
		  {name : 'ondo',index : 'ondo',width : 70,label:'在建套数',sortable : true,align : "left"},
		  {name : 'region2descr',index : 'region2descr',width : 80,label:'居住区域',sortable : true,align : "left"},
		  {name : 'department1descr',index : 'department1descr',width : 80,label:'专属事业部',sortable : true,align : "left"},
		  {name : 'spcbuilderdescr',index : 'spcbuilderdescr',width : 80,label:'专属专盘',sortable : true,align : "left"},
		  {name : 'belongregiondescr',index : 'belongregiondescr',width : 80,label:'专属区域',sortable : true,align : "left"},
		  {name : 'Remarks',index : 'Remarks',width : 110,label:'备注',sortable : true,align : "left"},
		  {name : 'qualityfeebegin',index : 'qualityfeebegin',width : 110,label:'质保起始金',sortable : true,align : "left",hidden:true},
		  {name : 'IDNum',index : 'IDNum',width : 110,label:'身份证号',sortable : true,align : "left",hidden:true},
		  {name : 'CardId3',index : 'CardId3',width : 110,label:'CardId3',sortable : true,align : "left",hidden:true},
		  {name : 'CardId4',index : 'CardId4',width : 110,label:'CardId4',sortable : true,align : "left",hidden:true},
		  {name : 'NameChi3',index : 'NameChi3',width : 110,label:'NameChi3',sortable : true,align : "left",hidden:true},
		  {name : 'NameChi4',index : 'NameChi4',width : 110,label:'NameChi4',sortable : true,align : "left",hidden:true},
        ],
        ondblClickRow:function(rowid,iRow,iCol,e){
			var selectRow = $("#dataTable").jqGrid('getRowData', rowid);
        	Global.Dialog.returnData = selectRow;
        	Global.Dialog.closeDialog();
        }
	});
	
	$("#view").on("click",function(){
		var ret = selectDataTableRow();
		if(ret){
			Global.Dialog.showDialog("View",{
				title:"在建工地——查看",
				url:"${ctx}/admin/worker/goViewOnDoDetail",
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
	
	$("#nameChi").focus();
});
</script>
</head>
    
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search" >
					<input type="hidden" id="workType12" name="workType12" style="width:160px;"  value="${worker.workType12}" />
					<input type="hidden" id="appType" name="appType" style="width:160px;"  value="${worker.appType}" />
					<input type="hidden" id="custCode" name="custCode" style="width:160px;"  value="${worker.custCode}" />
					<input type="hidden" id="refCustCode" name="refCustCode" style="width:160px;"  value="${worker.refCustCode}" />
					<input type="hidden" id="workType1" name="workType1" style="width:160px;"  value="${worker.workType1}" />
					
					<input type="hidden" id="expired" name="expired" value="${worker.expired}"/>
						<ul class="ul-form">
							<li> 
								<label >工人编号</label>
								<input type="text" style="width:160px"id="code" name="code"  value="${worker.code}" />
							</li>
							<li> 
								<label >工人名称</label>
								<input type="text" id="nameChi" style="width:160px" name="nameChi"  value="${worker.nameChi}" />
							</li>
							<!-- <li> 
								<label >是否签约</label>
								<house:xtdm  id="isSign" dictCode="YESNO"   style="width:160px;" value="${worker.isSign}"></house:xtdm>
							</li> -->
							<li>
								<label>签约类型</label>
								<house:xtdm id="isSign" dictCode="WSIGNTYPE"  value="${worker.isSign}"></house:xtdm>
							</li>
							<li> 
								<label >专属事业部</label>
								<house:department1 id="Department1"    depType="3"  ></house:department1>
							</li>
							
							<li >
								<label>专属专盘</label>
								<house:dict id="spcBuilder" dictCode="" sql="select code,descr from tSpcBuilder where 1=1 and expired='F' " 
								sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
							</li>
							<li >
								<label>专属区域</label>
								<house:dict id="belongRegion" dictCode="" sql="select code,descr from tRegion where 1=1 and expired='F' and isSpcWorker='1' " 
								sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>
							</li>
							<li >	
								<label>一级区域</label>
								<select id="region" name="region" ></select>
							</li>
							<li >
								<label>二级区域</label>
								<select id="region2" name="region2" ></select>
							</li>
							
							
						<li id="loadMore">
							<input type="checkbox" id="expired_show" name="expired_show" value="${worker.expired}" 
								onclick="hideExpired(this)" ${worker.expired!='T' ?'checked':'' }/>
							<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label><!-- 增加隐藏选项--add by zb -->
							<button type="button" class="btn btn-system btn-sm"
								onclick="goto_query();">查询</button>
						</li>
					</ul>
			</form>
		</div>
		<div class="btn-panel" >
    			  <div class="btn-group-sm"  >
								<button type="button" class="btn btn-system "  id="view"><span>查看</span></button>
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


