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
	<title>集成资料管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
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
$(function() {
	
	//Global.JqGrid.initJqGrid("dataTable",{
	var gridOption = {	
		//url:"${ctx}/admin/custDoc/goDocJqGrid",
		//postData:{custCode:'${customer.code}',docType1:'1'} ,
		height:$(document).height()-$("#content-list").offset().top-82,
		multiselect:true,
		rowNum:1000,
		//cellEdit:true,		
		colModel : [
			{name:'PK',	index:'PK',	width:90,	label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'Descr',	index:'Descr',	width:90,	label:'资料名称',	sortable:true,align:"left",},
			{name:'DocName',	index:'DocName',	width:90,	label:'资料名称',	sortable:true,align:"left",hidden:true},
			{name:'typedescr',	index:'typedescr',	width:120,	label:'资料类型',	sortable:true,align:"left" ,},
			{name:'uploaddescr',	index:'uploaddescr',	width:80,	label:'上传人员',	sortable:true,align:"left" ,},
			{name:'UploadDate',	index:'UploadDate',	width:130,	label:'上传时间',	sortable:true,align:"left" ,formatter:formatTime},
			{name:'LastUpdate',	index:'LastUpdate',	width:95,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'LastUpdatedBy',	index:'LastUpdatedBy',	width:110,	label:'最后修改人员',	sortable:true,align:"left" ,hidden:true},
				{name:'Remark',	index:'Remark',	width:175,	label:'备注',	sortable:true,align:"left" ,},
			{name:'updatedescr',	index:'updatedescr',	width:95,	label:'最后修改人员',	sortable:true,align:"left" ,},
		],
		/* beforeSelectRow: function (rowid, e) {  
		    var $myGrid = $(this),  
		        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),  
		        cm = $myGrid.jqGrid('getGridParam', 'colModel'); 
		    return (cm[i].name === 'cb');  
			}, */
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			var custCode =$.trim($("#code").val());
			var url =$.trim($("#url").val());
			var docName = Global.JqGrid.allToJson("dataTable","DocName");
			var arr = new Array();
				arr = docName.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1]=="png"||arry[1]=='jpg'||arry[1]=='gif'||arry[1]=='jpeg'||arry[1]=='bmp'){//jpg/gif/jpeg/png/bmp.
				document.getElementById("docPic").src =url+arr[id-1];	
			}else{
				document.getElementById("docPic").src ="";	
			}
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$('#'+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$('#'+ids[id-1]).find("td").addClass("SelectBG");
		},
		/* afterSaveCell:function(id,name,val,iRow,iCol){
			alert(id+","+name+","+val+","+iRow+","+iCol);
             		    alert(ret.PK);
		}, */
	}
	$.extend(gridOption,{
		url:"${ctx}/admin/custDoc/goDocJqGrid",
		postData:{custCode:'${customer.code}',docType1:'4'} ,
	});
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	
	
	$("#add").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("Add",{
			title:"项目资料管理——合同资料",
			url:"${ctx}/admin/custDoc/goAdd",
			postData:{custCode:'${customer.code }',docType1:'4'},
			height:550,
			width:950,
			returnFun:goto_query
		});
	});
	
	$("#volumeAdd").on("click",function(){
		var ret=selectDataTableRow();
		Global.Dialog.showDialog("VolumeAdd",{
			title:"合同资料——批量上传",
			url:"${ctx}/admin/custDoc/goVolumeAdd",
			postData:{custCode:'${customer.code }',docType1:'4'},
			height:550,
			width:950,
			returnFun:goto_query
		});
	});	
	
	$("#del").on("click",function(){
		var custCode=$.trim($("#code").val());
		var id = $("#dataTable").jqGrid("getGridParam", "selrow");
		var docName = Global.JqGrid.allToJson("dataTable","DocName");
		var arr = new Array();
			arr = docName.fieldJson.split(",");
		var path="D:/homePhoto/custDoc/"+custCode+"/"+arr[id-1];
		var ret=selectDataTableRow();
		var lastUpdatedBy=$.trim($("#lastUpdatedBy").val());
		var hasAuthByCzybh=$.trim($("#hasAuthByCzybh").val());
		var updatedescr=$.trim(ret.LastUpdatedBy);
	
	    var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
	    if(ids==""||ids==null){
			art.dialog({
				content:'请选择一条或多条数据删除',
			});
			return false
		}
		
		if(updatedescr!=lastUpdatedBy&&hasAuthByCzybh!='true'){
			art.dialog({
				content:'只能删除本人上传的图片',
			});
			return false;
		}
		
	    
	    var arry =new Array();
		$.each(ids,function(k,id){
    		var row = $("#dataTable").jqGrid('getRowData', id);
    		arry.push(row.DocName);
    	});
			var docNames="";
		for(var x=0;x<arry.length;x++){
			docNames=docNames+arry[x]+',';
		}

		art.dialog({
				 content:"是否确定删除",
				 lock: true,
				 width: 200,
				 height: 100,
				 ok: function () {
					$.ajax({
						url:'${ctx}/admin/custDoc/doDeleteDoc',
						type: 'post',
						data:{custCode:custCode,path:path,docName:docNames},
						dataType: 'json',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
							if(obj){
								art.dialog({
									content:'删除成功',
									time:500,
								});
								$("#dataTable").jqGrid("setGridParam",{postData:{custCode:'${customer.code }',docType1:'4'},page:1,sortname:''}).trigger("reloadGrid");
							}else{
								art.dialog({
									content:'操作失败',
									time:500,
								});
							}
					    }
					 });
				},
				cancel: function () {
						return true;
				}
		});	
	});
	
	$("#download").on("click",function(){
		var docName = Global.JqGrid.allToJson("dataTable","DocName");
		var arr = new Array();
			arr = docName.fieldJson.split(",");
		var custCode=$.trim($("#code").val());
        var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		var docNameArr = new Array();
		var i=0;
		if(ids.length==0){
        	Global.Dialog.infoDialog("请选择下载记录！");	
        	return;
       	}
		for(var i=0;i<ids.length;i++){
			docNameArr[i]=arr[ids[i]-1];
		}
			/* $.ajax({
				url:'${ctx}/admin/custDoc/download',
				type: 'post',
				data:{docNameArr:docNameArr,docType2Descr:'合同资料'},
				dataType: 'json',
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
			    },
			    success: function(obj){
					alert(1);
				}	
			 }); */
		window.open("${ctx}/admin/custDoc/download?docNameArr="+docNameArr+"&docType2Descr="+'集成资料'+"&custCode="+custCode);
	});
		
});
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:{custCode:'${customer.code }',docType1:'4'},page:1,sortname:''}).trigger("reloadGrid");
	}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" id="lastUpdatedBy" name="lastUpdatedBy" value="${customer.lastUpdatedBy }" />
					<input type="hidden" id="hasAuthByCzybh" name="hasAuthByCzybh" value="${hasAuthByCzybh}" />
					<input type="hidden" id="url" name="url" value="${url}" />
						<ul class="ul-form">
							<li >
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;" value="${customer.code }"  readonly="readonly"/>
							</li>
							<li >
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;" readonly="true" value="${customer.address }"/>
							</li>
							<li>
								<label class="control-textarea">审核说明</label>
								<textarea id="remarks" name="remarks" rows="2" readonly="true">${confirmRemark }</textarea>
	 						</li>
						</ul>	
				</form>
				</div>
				<div class="btn-panel" >
    			 <div class="btn-group-sm"  >
						<button type="button" class="btn btn-system " id="download" >
									<span>下载</span></button>
				</div>
			</div>
			</div>
<div style="width:53%; float:left; margin-left:0px; ">
<div class="body-box-form" >
			<div class="container-fluid" style="whith:800px">  
				<div id="content-list" style="whith:800px">
					<table id= "dataTable"></table>
				</div>	
			</div>
		</div>
		</div>
		<div style="width:46.5%; float:right; margin-left:0px; ">
				<img id="docPic" src=" " onload="AutoResizeImage(500,500,'docPic');" width="521" height="510" >  
		</div>	
	</body>	
</html>
