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
	var y=0;
	var name="";
	Global.JqGrid.initJqGrid("dataTable_ZG",{
			url:"${ctx}/admin/prjProgPhoto/goPrjJqGrid",
			postData:{custCode:'${prjProgCheck.custCode }',type:'4',prjItem:'${prjProgCheck.prjItem}'},
			height:200,
			colModel : [
				{name : 'prjitem',index : 'PrjItem',width : 90,label:'施工项目',sortable : false,align : "center",hidden:true},
		       	{name : 'type',index : 'type',width : 190,label:'type',sortable : false,align : "center",},
		      	{name : 'photoname',index : 'photoname',width :150,label:'图片名称',sortable : false,align : "center" },
		      	{name : 'addresscheck',index : 'addresscheck',width :230,label:'巡检地址',sortable : false,align : "center"},
		       	{name : 'prjdescr',index : 'PrjItem',width : 190,label:'施工项目',sortable : false,align : "center",},
		       	{name : 'checkdate',index : 'checkdate',width : 190,label:'巡检日期',sortable : false,align : "center",formatter:formatTime},
            ],
            gridComplete:function(){
				 var a= $("#dataTable_ZG").getCol('photoname',false) ;
				 name=a[0],
				  $.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:a[0],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src =obj.photoPath;
						$('#allNo1').val(a.length);
						if(a.length>0){
							$('#nowNo1').val('1');
						}else{
							$('#nowNo1').val('0');
						}
					}
				});
	         },
        });
		
	$("#nextZG").on("click",function(){
			y++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_ZG","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[y]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[y],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						$('#nowNo1').val(y+1);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			y--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张
	$("#preZG").on("click",function(){
			y--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_ZG","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[y]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[y],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						$('#nowNo1').val(y+1);
						document.getElementById("conPicture").src = obj.photoPath;	
					}
				});
			}else{
			y++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
});




</script>

</head>
<body >
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
				<button type="button" class="btn btn-system "id="nextZG">
					<span>下一张</span>
					</button>
				<button type="button" class="btn btn-system " id="preZG">
					<span>上一张</span>
					</button>
				<span>总共：</span>
				<input type="text" id="allNo1" name="allNo1" style="width:20px; outline:none; background:transparent; 
					border:none" value="${prjProg.allNo1 }" readonly="true"/>张,				
				<span>当前第：</span>
				<input type="text" id="nowNo1" name="nowNo1" style="width:20px; outline:none; background:transparent; 
					border:none" value="${prjProg.nowNo1 }" readonly="true"/>张,				
			</div>
		</div>
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
						<tr hidden="true" >	
							<td class="td-label"><span class="required">*</span>xxx客户编号</td>
							<td class="td-value">
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProgCheck.custCode}" />
							</td>
							<td class="td-label">施工项目</td>
							<td class="td-value">
								<input type="text" id="prjItem" name="prjItem" style="width:220px;"  value="${prjProg.prjItem}" />
							</td>
						</tr>
					</tbody>
				</table>
			</form>
	
	
	<div hidden="true" >
		<table id="dataTable_ZG" ></table>
	</div>
	<div  style="width:48%;float: left;margin-left: 1px; class="container"  >
			<input style="width:160px" value="${prjProg.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
		 <div >  
           	 <div class="panel-body" style="border:1px solid #e7e7e7;width:563px;margin-left: -1px;">  
          		 <img id="conPicture" src="${prjProg.photoPath }" onload="AutoResizeImage(495,440,'conPicture');" width="500" height="450" style="border:none"  >  
		  	 </div>  
	  	</div> 
	</div>
</body>
</html> 
