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
	<title>巡检图片</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>

<script type="text/javascript">
$(function(){
	var x=0;
	 Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProg.custCode }',Type:'3',PrjItem:'${prjProg.prjItem}'},
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "Type", index: "Type", width: 175, label: "Type", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
	}); 
	
	$("#next").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张
	$("#pre").on("click",function(){
			x--;
			var prjphoto = Global.JqGrid.allToJson("dataTable","PhotoName");
				arry = prjphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
});

</script>

</head>
<body>
	<div class="panelBar">
		  <ul>
			<li>
				<a href="javascript:void(0)" class="a1" id="next">
					<span>下一张</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0)" class="a1" id="pre">
					<span>上一张</span>
				</a>
			</li>
          </ul>		   
	</div>
			<form action="" method="post" id="dataForm">
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr >	
							<td class="td-label"><span class="required">*</span>xxx客户编号</td>
							<td class="td-value">
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProg.custCode}" />
							</td>
							<td class="td-label">施工项目</td>
							<td class="td-value">
								<input type="text" id="prjItem" name="prjItem" style="width:220px;"  value="${prjProg.prjItem}" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
	
	
	<div>
		<table id="dataTable"></table>
	</div>
	<div  style="width:48%;float: left;margin-left: 1px; class="container" >
			<input style="width:160px" value="${prjProg.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
		 <div >  
           	 <div >  
          		 <img id="conPicture" src="${prjProg.photoPath }">  
		  	 </div>  
	  	</div> 
	</div>
</body>
</html> 
