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
	<title>新增项目任务</title>
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
	//初始化材料类型1，2，3三级联动
    Global.LinkSelect.initSelect("${ctx}/admin/prjJobManage/prjTypeByItemType1Auth", "itemType1", "jobType");
    	
	$("#closeBtn").on("click",function(){
		var ids =$("#dataTable").getDataIDs();
		if(ids.length>0){
			art.dialog({
				 content:"您的项目任务数据不被保存，是否离开？",
				 lock: true,
				 ok: function () {
					closeWindows();
				 },
				 cancel: function () {
				 	return true;
				 }
			 });	
		}else{
			closeWindows();
		}
	});
	
	$("#saveBtn").on("click",function(){
		if($.trim($("#custCode").val())==""){
			art.dialog({
				content:"请先选择客户编号！",
			});
			return;
		}
		if($.trim($("#itemType1").val())==""){
			art.dialog({
				content:"请先选择材料类型1！",
			});
			return;
		}
		if($.trim($("#jobType").val())==""){
			art.dialog({
				content:"请先选择任务类型！",
			});
			return;
		}
	    if($.trim($("#jobType").val())=='01' && !$("#warBrand").val() && !$("#cupBrand").val()){
	       	art.dialog({
				content:"集成测量时，衣柜品牌和橱柜品牌最少必须选择一个！",
			});
	        return;
        }
		var Ids =$("#dataTable").getDataIDs();
		/* 20201117 mark by lp 允许图片列表为空
		if($("#isNeedPic").val()=="1" && (Ids==null||Ids=="")){
			art.dialog({
				content:"请先上传图片！",
			});
			return false;
		}
		*/
		var photoNames = $("#dataTable").getCol("photoname", false).join(",");
		$("#photoString").val(photoNames);
		art.dialog({
			 content:"是否直接提交项目任务？",
			 lock: true,
			 ok: function () {
				$("#status").val("2");
				doSave();
			 },
			 cancel: function () {
			 	$("#status").val("1");
				doSave();
			 }
		 });	
	});
	
	replaceCloseEvt("save", closeWindows);
	
	function closeWindows(){
		var ids=$("#dataTable").jqGrid("getDataIDs");
	    var arry =new Array();
		$.each(ids,function(k,id){
    		var row = $("#dataTable").jqGrid("getRowData", id);
    		arry.push(row.photoname);
    	});
		var photoNames="";
		for(var x=0;x<arry.length;x++){
			photoNames=photoNames+arry[x]+",";
		}
		if(ids.length>0){
			$.ajax({
				url:"${ctx}/admin/prjJobManage/doDeletePic",
				type: "post",
				data:{photoName:photoNames},
				dataType: "json",
				cache: false,
				error: function(obj){
					showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
			    },
			    success: function(obj){
					closeWin();
			    }
			 });
		}else{
			closeWin();
		}
	}
	
	$("#custCode").openComponent_customer();	
	var gridOption = {	
		height:$(document).height()-$("#content-list").offset().top-82,
		multiselect:true,
		colModel : [
			{name:"photoname",	index:"photoname",	width:160,	label:"图片名称",	sortable:true,align:"left",},
			{name:"lastupdate",	index:"lastupdate",	width:95,	label:"最后修改时间",	sortable:true,align:"left" ,formatter:formatDate},
			{name:"lastupdatedby",	index:"lastupdatedby",	width:95,	label:"最后修改人员",	sortable:true,align:"left" ,},
		],
		onCellSelect: function(id,iCol,cellParam,e){
 			var ids = $("#dataTable").jqGrid("getDataIDs");  
			var photoname = Global.JqGrid.allToJson("dataTable","photoname");
			var url =$.trim($("#url").val());
			var arr = new Array();
				arr = photoname.fieldJson.split(",");
			var arry = new Array();
				arry = arr[id-1].split(".");
			if(arry[1]=="png"||arry[1]=="jpg"||arry[1]=="gif"||arry[1]=="jpeg"||arry[1]=="bmp"){//jpg/gif/jpeg/png/bmp.
				document.getElementById("docPic").src =url+arr[id-1].substring(0,5)+"/"+arr[id-1];	
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
	};
	Global.JqGrid.initEditJqGrid("dataTable",gridOption);
	
	$("#add").on("click",function(){
		var ret=selectDataTableRow();
		$("#m_umState").val("A");
		Global.Dialog.showDialog("Add",{
			title:"图片上传",
			url:"${ctx}/admin/prjJobManage/goAddPic",
			postData:{no:"${no}"},
			height:550,
			width:950,
			returnFun:function(data){
				if(data.length > 0){
					var ids = $("#dataTable").jqGrid("getDataIDs");
					$.each(data, function(i,photoName){
						var rowData={
							lastupdatedby:"${USER_CONTEXT_KEY.czybh}",
							lastupdate:new Date(),
							photoname:photoName
						};
						$("#dataTable").jqGrid("addRowData", (i+1+ids.length), rowData);
					});	
				}
			}
		});
	});
	
	$("#del").on("click",function(){
	    var ids = $("#dataTable").jqGrid("getGridParam", "selarrrow");
	    console.log(ids);
		if(ids==""||ids==null){
			art.dialog({
				content:"请选择一条或多条数据删除",
			});
			return false;
		}
		 art.dialog({
			 content:"是否确定删除",
			 lock: true,
			 ok: function () {
			 	var arry =new Array();
			 	for(var i=0;i<ids.length;){
			 		var row = $("#dataTable").jqGrid("getRowData", ids[0]);
		    		arry.push(row.photoname);
		    		Global.JqGrid.delRowData("dataTable",ids[0]);
			 	}
				var photoNames="";
				for(var x=0;x<arry.length;x++){
					photoNames=photoNames+arry[x]+",";
				}
				$.ajax({
					url:"${ctx}/admin/prjJobManage/doDeletePic",
					type: "post",
					data:{photoName:photoNames},
					dataType: "json",
					async:false,
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
});
	
	function goto_query(){
		$("#dataTable").jqGrid("setGridParam",{postData:{no:$("#no").val()},sortname:""}).trigger("reloadGrid");
	}
	//修改任务类型
	function changeJobType(){
		var jobType=$("#jobType").val();
		if(jobType=="01"){
			$("#warBrand,#cupBrand").parent().removeClass("hidden");
		}else{
			$("#warBrand,#cupBrand").parent().addClass("hidden");
		}
		$.ajax({
			url:"${ctx}/admin/prjJobManage/getIsNeedPic",
			type: "post",
			data:{jobType:jobType},
			cache: false,
			async:false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				$("#isNeedPic").val(obj);
		    }
		 });
		 
	}
	
	function doSave(){
		 var datas = $("#dataForm").serialize();
		 $.ajax({
			url:"${ctx}/admin/prjJobManage/doSave",
			type: "post",
			data:datas,
			dataType: "json",
			async:false,
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
		    },
		    success: function(obj){
				if(obj.rs){
					art.dialog({
						content:obj.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});
				}else{
					art.dialog({
						content:obj.msg,
					});
				}
		    }
		 });  
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
					<input type="hidden" name="isNeedPic" id="isNeedPic" value="0"/>
					<input type="hidden" name="url" id="url" value="${url }"/>
					<input type="hidden" name="photoString" id="photoString" />
					<input type="hidden" name="status" id="status" />
					<ul class="ul-form">
						<div class="row">
							<li class="validate-group">
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;" value="" readonly="readonly"/>
							</li>
							<li class="validate-group">
	          					<label>材料类型1</label>
	
	          					<select id="itemType1" name="itemType1"></select>
	        				</li>
	        				<li class="validate-group">
	          					<label>任务类型</label>
	          					<house:dict id="jobType" dictCode="" sql="select rtrim(Code)Code,Descr from tJobType where Expired='F' order by Code " 
											sqlValueKey="Code" sqlLableKey="Descr" onchange="changeJobType()" ></house:dict>   
	        				</li>
						</div>
						<div class="row">
							<li class="validate-group hidden">
	          					<label>衣柜品牌</label>
	          					<house:dict id="warBrand" dictCode="" sql="select rtrim(Code)Code,Descr from tIntMeasureBrand where Expired='F' and Type='1' order by Code " 
											sqlValueKey="Code" sqlLableKey="Descr"  ></house:dict>   
	        				</li>
	        				<li class="validate-group hidden">
	          					<label>橱柜品牌</label>
	          					<house:dict id="cupBrand" dictCode="" sql="select rtrim(Code)Code,Descr from tIntMeasureBrand where Expired='F' and Type='2' order by Code " 
											sqlValueKey="Code" sqlLableKey="Descr" ></house:dict>   
	        				</li>
							<li>						
								<label class="control-textarea">任务说明</label>
								<textarea id="remarks" name="remarks" ></textarea>
							</li>
						</div>
					</ul>	
				</form>
			</div>
			<div class="btn-panel" >
    			<div class="btn-group-sm"  >
					<button type="button" class="btn btn-system " id="add" >
						<span>添加</span>
					</button>
					<button type="button" class="btn btn-system " id="del">
						<span>删除</span>
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
				<img id="docPic" src=" " onload="AutoResizeImage(450,450,'docPic');" width="521" height="450" >  
		</div>	
	</body>	
</html>
