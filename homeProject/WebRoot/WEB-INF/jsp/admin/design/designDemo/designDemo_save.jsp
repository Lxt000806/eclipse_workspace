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
	//console.log($("#targetFrame")[0].baseURI);
	$("#closeBtn").on("click",function(){
		closeWindows();
	});
	
	$("#saveBtn").on("click",function(){
		if($.trim($("#designMan").val())==""){
			art.dialog({
				content:"请先选择设计师编号",
			});
			return;
		}
		var Ids =$("#dataTable").getDataIDs();
		if(Ids==null||Ids==""){
			art.dialog({
				content:"图片为空,不能保存",
			});
			return false;
		}
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:"${ctx}/admin/designDemo/doSaveDesignDemo",
			type: "post",
			data:datas,
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj){
					art.dialog({
						content:obj.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});
				}else{
					art.dialog({
						content:"保存失败",
					});
				}
		    }
		 });
	});
	
	var titleCloseEle = parent.$("div[aria-describedby=dialog_Save]").children(".ui-dialog-titlebar");
	$(titleCloseEle[0]).children("button").remove();
	var childBtn=$(titleCloseEle).children("button");
	$(titleCloseEle[0]).append("<button type=\"button\" class=\"ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only ui-dialog-titlebar-close\" role=\"button\" "
								+"title=\"Close\"><span class=\"ui-button-icon-primary ui-icon ui-icon-closethick\"></span><span class=\"ui-button-text\">Close</span></button>");
	$(titleCloseEle[0]).children("button").on("click", closeWindows); 
	
	function closeWindows(){
		var custCode=$.trim($("#custCode").val());
		var no=$.trim($("#no").val());
		$.ajax({
			url:"${ctx}/admin/designDemo/doDeleteAllDemo",
			type: "post",
			data:{custCode:custCode,no:no},
			dataType: "json",
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj.rs){
					closeWin();
			    }else{
			    	closeWin();
		    	}
		    }
		 });
	}
	
	
	function changeCustCode(data){
		if(!data) return;
	    var obj = document.getElementById("isPushCust");
		$("#address").val(data.address);
		$("#area").val(data.area);
		$("#amount").val(data.contractfee);
		$("#isPushCust").val(data.ispushcust);
		$("#custName").val(data.descr);
		$("#designMan").openComponent_employee({showValue:data.designman,showLabel:data.designmandescr});	
		$("#builderCode").openComponent_builder({showValue:data.buildercode,showLabel:data.buildercodedescr});
	}
	$("#custCode").openComponent_customer({callBack:changeCustCode,condition:{designDemo:"designDemo"}});	
	$("#designMan").openComponent_employee();	
	$("#builderCode").openComponent_builder();	
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		multiselect:true,
		colModel : [
			{name:"pk",	index:"pk",	width:90,	label:"PK",	sortable:true,align:"left",hidden:true},
			{name:"photoname",	index:"photoname",	width:130,	label:"图片名称",	sortable:true,align:"left",},
			{name:"lastupdate",	index:"lastupdate",	width:95,	label:"最后修改时间",	sortable:true,align:"left" ,formatter:formatDate},
			{name:"lastupdatedby",	index:"lastupdatedby",	width:95,	label:"最后修改人员",	sortable:true,align:"left" ,hidden:true},
			{name:"lastupdatedby",	index:"lastupdatedby",	width:95,	label:"最后修改人员",	sortable:true,align:"left" ,},
		],
		onCellSelect: function(id,iCol,cellParam,e){
			var ids = $("#dataTable").jqGrid("getDataIDs");  
			var custCode =$.trim($("#code").val());
			var photoname = Global.JqGrid.allToJson("dataTable","photoname");
			var url =$.trim($("#url").val());
			var arr = new Array();
				arr = photoname.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1]=="png"||arry[1]=="jpg"||arry[1]=="gif"||arry[1]=="jpeg"||arry[1]=="bmp"){//jpg/gif/jpeg/png/bmp.
				document.getElementById("docPic").src =url+$.trim($("#custCode").val())+"/"+$.trim($("#no").val())+"/"+arr[id-1];	
			}else{
				document.getElementById("docPic").src ="";	
			}
			for(var i=0;i<ids.length;i++){
				if(i!=id-1){
					$("#"+ids[i]).find("td").removeClass("SelectBG");
				}
			}
			$("#"+ids[id-1]).find("td").addClass("SelectBG");
		
		},
		loadComplete: function () {  
			var ids = $("#dataTable").jqGrid("getDataIDs");  
            if(ids!=""){
            	$("#openComponent_customer_custCode").attr("readonly",true);
            	$("#openComponent_customer_custCode").siblings().attr("disabled",true);
            }
        }
	}
	$.extend(gridOption,{
		url:"${ctx}/admin/designDemo/findDesignPic",
		postData:{no:"${designDemo.no}"} ,
	});
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	$("#add").on("click",function(){
		/* if($.trim($("#custCode").val())==""){
			art.dialog({
				content:"请先选择客户编号",
			});
			return;
		} */
		var ret=selectDataTableRow();
		$("#m_umState").val("A");
		Global.Dialog.showDialog("Add",{
			title:"设计案例上传",
			url:"${ctx}/admin/designDemo/goAddPic",
			postData:{no:"${designDemo.no }",custCode:$("#custCode").val(),area:$("#area").val()
						,designMan:$("#designMan").val(),builderCode:$("#builderCode").val(),layout:$("#layout").val()
						,designSty:$("#dDesignStl").val(),designRemark:$("#designRemark").val(),isPushCust:$("#isPushCust").val()
						,amount:$("#amount").val()},
			height:550,
			width:950,
			returnFun:goto_query
		});
	});
	
	$("#del").on("click",function(){
		var custCode=$.trim($("#custCode").val());
		var no=$.trim($("#no").val());
	    var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
		if(ids==""||ids==null){
			art.dialog({
				content:"请选择一条或多条数据删除",
			});
			return false
		}
	    var arry =new Array();
		$.each(ids,function(k,id){
    		var row = $("#dataTable").jqGrid("getRowData", id);
    		arry.push(row.photoname);
    	});
			var photoNames="";
		for(var x=0;x<arry.length;x++){
			photoNames=photoNames+arry[x]+",";
		}
		art.dialog({
			 content:"是否确定删除",
			 lock: true,
			 width: 200,
			 height: 100,
			 ok: function () {
				$.ajax({
					url:"${ctx}/admin/designDemo/doDeleteDemo",
					type: "post",
					data:{custCode:custCode,photoName:photoNames,no:no},
					dataType: "json",
					cache: false,
					error: function(obj){
						showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
				    },
				    success: function(obj){
						if(obj){
							art.dialog({
								content:"删除成功",
								time:500,
							});
							document.getElementById("docPic").src ="";	
							$("#dataTable").jqGrid("setGridParam",{postData:{no:$("#no").val()},sortname:""}).trigger("reloadGrid");
						}else{
							art.dialog({
								content:"操作失败",
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
		var photoname = Global.JqGrid.allToJson("dataTable","photoname");
		var arr = new Array();
			arr = photoname.fieldJson.split(",");
		var custCode=$.trim($("#custCode").val());
        var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
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
		$("#dataTable").jqGrid("setGridParam",{postData:{no:$("#no").val()},sortname:""}).trigger("reloadGrid");
	}
</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="panel panel-system">
				<div class="panel-body">
					<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBtn">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="query-form">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" name="m_umState" id="m_umState" value="m_umState"/>
					<input type="hidden" id="no" name="no" value="${designDemo.no}"/>
					<input type="hidden" id="url" name="url" value="${url}"/>
					<ul class="ul-form">
						<li >
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;" value="" readonly="readonly"/>
						</li>
						<li >
							<label>客户名称</label>
							<input type="text" id="custName" name="custName" style="width:160px;" value=""/>
						</li>
						<li >
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;" value="" readonly="true"/>
						</li>
						<li >
							<label>合同金额</label>
							<input type="text" id="amount" name="amount" style="width:160px;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value=""/>
						</li>
						<li >
							<label>项目编号</label>
							<input type="text" id="builderCode" name="builderCode" style="width:160px;" value="" />
						</li>
						<li >
							<label>设计师</label>
							<input type="text" id="designMan" name="designMan" style="width:160px;" value=""/>
						</li>
						<li >
							<label>面积</label>
							<input type="text" id="area" name="area" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" style="width:160px;" value=""/>
						</li>
						<li class="form-validate">
							<label>户型</label>
							<house:xtdm id="layout" dictCode="DLAYOUT"  style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>设计风格</label>
							<house:xtdm id="designSty" dictCode="DDESIGNSTL"  style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>是否播报</label>
							<select id="isPushCust" name="isPushCust" style="width: 160px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</li>
						<li id="remark_li">						
							<label class="control-textarea">设计说明</label>
							<textarea id="designRemark" name="designRemark" ></textarea>
						</li>
					</ul>	
				</form>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>添加</span>
					</button>
					<button type="button" class="btn btn-system " id="download" >
						<span>下载</span>
					</button>				
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
					</button>
					<!-- <button type="button" class="btn btn-system " id="commitCheck">
						<span>提交审核</span>
					</button> -->
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
