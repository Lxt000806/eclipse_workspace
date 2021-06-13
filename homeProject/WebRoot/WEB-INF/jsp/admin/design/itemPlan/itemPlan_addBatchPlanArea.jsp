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
	<title>预算管理空间添加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<style type="text/css">
      .SelectBG{
          background-color:#198ede!important;
            color:rgb(255,255,255)
            }
 	</style>
	<script type="text/javascript">
	$(function() {
		oldDatas=JSON.stringify($("#dataForm").jsonForm());
		Global.JqGrid.initJqGrid("dataTable",{
			height:$(document).height()-$("#content-list").offset().top-70,
			cellEdit:true,
			cellsubmit:'clientArray',
			colModel : [
				{name: "type", index: "type", width: 155, label:'空间类型编号',sortable : false,align: "left",hidden:true},
				{name: "typedesrname", index: "typedesrname", width: 115, label:'空间类型',sortable : false,align: "left",},
				{name: "typedesr", index: "typedesr", width: 70, label:'数量',sortable : false,align: "left",},
				{name: "list1", index: "list1", width: 155,height:26, label:'空间名称',sortable : false,align: "center",editable:true,edittype:'text'},
				{name: "list2", index: "list2", width: 155, label:'空间名称',sortable : false,align: "center",editable:true,edittype:'text'},
				{name: "list3", index: "list3", width: 155, label:'空间名称',sortable : false,align: "center",editable:true,edittype:'text'},
				{name: "list4", index: "list4", width: 155, label:'空间名称',sortable : false,align: "center",editable:true,edittype:'text'},
				{name: "list5", index: "list5", width: 155, label:'空间名称',sortable : false,align: "center",editable:true,edittype:'text'},
		    ],
		    onCellSelect: function(id,iCol,cellParam,e){
		    	var ids = $("#dataTable").jqGrid("getDataIDs");  
			    for(var i=0;i<ids.length;i++){
					if(i!=id-1){
						$('#'+ids[i]).find("td").removeClass("SelectBG");
					}
					$('#'+ids[id-1]).find("td").addClass("SelectBG");
				}
			},
		});
		var fixAreas="${fixAreas}".split(",");
		var fixTypes="${fixTypes}".split(",");
		for(var i=1 ;i<=fixAreas.length;i++){
			$("#dataTable").addRowData(i, {"type":fixTypes[i-1],"typedesrname":fixAreas[i-1],"typedesr":'<select id='+i+' style="width:60px; height:18px;size:10px;float:left; border-radius: 4px;center; " class="form-control-m" onchange="selectChange('+i +',this)"><option selected>0</option><option >1</option><option>2</option> <option>3</option><option>4</option><option>5</option></select></div>',}, "last");
		}
		$("#saveBtn").on("click",function(){
		    var ids = $("#dataTable").jqGrid("getDataIDs");  
			var detailJson =[];
			var dispSeq=parseInt($("#dispSeq").val());
			for(var i=1;i<=ids.length;i++){
				for(var j=1;j<=5;j++){
					var param =$("#dataTable").jqGrid("getRowData",i);
					if($.trim(param["list"+j])!=""){
						if(i=="1"){
							detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"2.6",paveType:"1",dispSeq:dispSeq});
							dispSeq++;
						}else if(i=="7"){
							detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"2.6",paveType:"1",dispSeq:dispSeq});
							dispSeq++;
						}else if(i=="3"||i=="5"||i=="6"){
							detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"2.4",paveType:"1",dispSeq:dispSeq});
							dispSeq++;
						}else if(i=="2"||i=="4"){
							if(i=="2"){
								detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"2.8",paveType:"2",dispSeq:dispSeq});
								dispSeq++;
							}else{
								detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"2.8",paveType:"1",dispSeq:dispSeq});
								dispSeq++;
							}
						}else{
							detailJson.push({"type":fixTypes[i-1],"descr":$.trim(param["list"+j]),"height":"0",paveType:"1",dispSeq:dispSeq});
							dispSeq++;
						}
					}
				}
			}
			var param ;
			param.detailJson=JSON.stringify(detailJson),
			$("#saveBtn").attr("disabled",true);
			Global.Form.submit("dataForm","${ctx}/admin/itemPlan/doBatchAddPrePlanArea",param,function(ret){
				if(ret.rs==true){
					art.dialog({
						content:ret.msg,
						time:1000,
						beforeunload:function(){
							closeWin();
						}
					});				
				}else{
					$("#saveBtn").removeAttr("disabled",true);
					$("#_form_token_uniq_id").val(ret.token.token);
					art.dialog({
						content:ret.msg,
						width:200
					});
				}
			}); 
		});
	});
	 
	function selectChange(j,e){
		var param =$("#dataTable").jqGrid("getRowData",e.id);
		var name="${fixAreas}".split(",")[j-1];
		for(var i=1;i<=e.value;i++){
			if($.trim(param["list"+i])==""){
				$("#dataTable").setCell(e.id,'list'+i,name);
				//$("#dataTable").setRowProp("list"+i,{editable:true});
				//$("#dataTable").setColProp("list"+i,{editable:true});
				//$("#dataTable").editRow(e.id,true,e.id);
				
			}	
		}
		for(var i=parseInt(e.value)+1;i<=5;i++){
				$("#dataTable").setCell(e.id,'list'+i,' ');
		}
	}
	</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system">
			    <div class="panel-body">
			    	<div class="btn-group-xs">
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin()">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" hidden="true">  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" id="custCode" name="custCode" value="${prePlanArea.custCode}"/>
						<input type="hidden" id="dispSeq" name="dispSeq" value="${prePlanArea.dispSeq}"/>
						<input type="text" id="module" name="module" value="${prePlanArea.module}"/>
					</form>
				</div>
			</div>
			<div id="content-list">
				<table id="dataTable"></table>
			</div>
		</div>
	</body>	
</html>
