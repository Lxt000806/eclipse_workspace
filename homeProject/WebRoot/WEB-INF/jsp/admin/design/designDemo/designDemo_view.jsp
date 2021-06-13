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
	<title>设计案例上传管理</title>
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
	function changeCustCode(){
		console.log(1);
	}
	$("#isPushCust").val("${designDemo.isPushCust}");
	$("#expired").val("${designDemo.expired}");
	$("#custCode").openComponent_customer({callBack:changeCustCode,showLabel:"${customer.descr}",showValue:"${designDemo.custCode}",readonly:true});	
	$("#designMan").openComponent_employee({showLabel:"${employee.nameChi}",showValue:"${designDemo.designMan}",readonly:true});	
	$("#builderCode").openComponent_builder({showLabel:"${builder.descr}",showValue:"${designDemo.builderCode}",readonly:true});	
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		multiselect:true,
		colModel : [
			{name:'pk',	index:'pk',	width:90,	label:'PK',	sortable:true,align:"left",hidden:true},
			{name:'photoname',	index:'photoname',	width:130,	label:'图片名称',	sortable:true,align:"left",},
			{name:'lastupdate',	index:'lastupdate',	width:95,	label:'最后修改时间',	sortable:true,align:"left" ,formatter:formatDate},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后修改人员',	sortable:true,align:"left" ,hidden:true},
			{name:'lastupdatedby',	index:'lastupdatedby',	width:95,	label:'最后修改人员',	sortable:true,align:"left" ,},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			var custCode =$.trim($("#code").val());
			var photoname = Global.JqGrid.allToJson("dataTable","photoname");
			var url =$.trim($("#url").val());
			var arr = new Array();
				arr = photoname.fieldJson.split(",");
				console.log(arr);
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1].toLowerCase()=="png"||arry[1].toLowerCase()=='jpg'||arry[1].toLowerCase() =='gif'
				||arry[1].toLowerCase()=='jpeg'||arry[1].toLowerCase()=='bmp' ){//jpg/gif/jpeg/png/bmp.
				document.getElementById("docPic").src =url+$.trim($("#custCode").val())+"/"+$.trim($("#no").val())+"/"+arr[id-1];	
			$(document).ready(function () {
				$("#docPic").blowup({
					background : "#FCEBB6"
				});
			});
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
	}
	$.extend(gridOption,{
		url:"${ctx}/admin/designDemo/findDesignPic",
		postData:{no:"${designDemo.no}"} ,
	});
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	$("#download").on("click",function(){
		var photoname = Global.JqGrid.allToJson("dataTable","photoname");
		var arr = new Array();
			arr = photoname.fieldJson.split(",");
		var custCode=$.trim($("#custCode").val());
        var ids = $("#dataTable").jqGrid('getGridParam', 'selarrrow');
		var photoNameArr = new Array();
		var i=0;
		if(ids.length==0){
        	Global.Dialog.infoDialog("请选择下载记录！");	
        	return;
       	}
		for(var i=0;i<ids.length;i++){
			photoNameArr[i]=arr[ids[i]-1];
		}
		window.open("${ctx}/admin/designDemo/download?photoNameArr="+photoNameArr+"&designDemoDescr='设计案例'"+"&custCode="+custCode+"&no="+$.trim($("#no").val()));
	});		
	
});

	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:{no:$("#no").val()},sortname:''}).trigger("reloadGrid");
	}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
					<input type="hidden" name="m_umState" id="m_umState" value="m_umState"/>
					<input type="hidden" id="no" name="no" value="${designDemo.no}" />
					<input type="hidden" id="url" name="url" value="${url}" />
					<ul class="ul-form">
						<li >
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" value=""  readonly="readonly"/>
						</li>
						<li >
							<label>名称</label>
							<input type="text" id="custName" name="custName" style="width:160px;" value="${designDemo.custName }" readonly="true"/>
						</li>
						<li >
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="${customer.address }" readonly="true"/>
						</li>
						<li >
							<label>项目编号</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="" />
						</li>
						<li >
							<label>设计师</label>
							<input type="text" id="designMan" name="address" style="width:160px;" value=""/>
						</li>
						<li >
							<label>面积</label>
							<input type="text" id="area" name="area" style="width:160px;" value="${designDemo.area}" readonly="true"/>
						</li>
						<li class="form-validate">
							<label>户型</label>
							<house:xtdm id="layout" dictCode="DLAYOUT"  style="width:160px;" value="${designDemo.layout}" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>设计风格</label>
							<house:xtdm id="dDesignStl" dictCode="DDESIGNSTL"  style="width:160px;" value="${designDemo.designSty}" disabled="true"></house:xtdm>
						</li>
						<li>
							<label>是否播报</label>
							<select id="isPushCust" name="isPushCust" style="width: 160px;" >
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</li>
						<li id="remark_li">						
							<label class="control-textarea">设计说明</label>
							<textarea id="designRemark" name="designRemark" >${designDemo.designRemark}</textarea>
						</li>
						<li>
							<label>是否过期</label>
							<input type="checkbox" id="expired_show" name="expired_show" value="${designDemo.expired }",
								onclick="checkExpired(this)" ${designDemo.expired=="T"?"checked":"" }/>
						</li>
					</ul>	
				</form>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="download" >
						<span>下载</span>
					</button>				
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
