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
	var Name;
	 Global.JqGrid.initJqGrid("dataTable_XJ",{
		url:"${ctx}/admin/prjProgPhoto/goJqGrid",
		postData:{CustCode:'${prjProgCheck.custCode  }',Type:'3',PrjItem:'${prjProgCheck.prjItem}'},
		colModel : [
				{name: "PK", index: "PK", width: 175, label: "pk", sortable: true, align: "left",hidden:true},
				{name:'prjitemdescr',index:'prjitemdescr',width:175, label:'施工项目', sortable:true ,align:'left'},
				{name: "PhotoName", index: "PhotoName", width: 175, label: "图片名称", sortable: true, align: "left",},
				{name: "Type", index: "Type", width: 175, label: "Type", sortable: true, align: "left",},
				{name: "LastUpdate", index: "LastUpdate", width: 175, label: "上传图片时间", sortable: true, align: "left",formatter:formatTime},
			],
			  gridComplete:function(){
				 var b= $("#dataTable_XJ").getCol('PhotoName',false) ;
				 Name=b[0],
				 $('#photoPath').val(Name);
				 $.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:b[0],readonly:'1'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#photoPath').val(obj.photoPath);
						document.getElementById("xjPicture").src =obj.photoPath;
						$('#allNo').val(b.length);
						if(b.length>0){
							$('#nowNo').val('1');
						}else{
							$('#nowNo').val('0');
						}
					}
				});
	          },
	}); 
	
	$("#nextXJ").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_XJ","PhotoName");
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
						$('#nowNo').val(x+1);
						document.getElementById("xjPicture").src = obj.photoPath;	
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
	$("#preXJ").on("click",function(){
			x--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_XJ","PhotoName");
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
						$('#nowNo').val(x+1);
						document.getElementById("xjPicture").src = obj.photoPath;	
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
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
				<button type="button" class="btn btn-system " id="nextXJ">
					<span>下一张</span>
					</button>
				<button type="button" class="btn btn-system " id="preXJ">
					<span>上一张</span>
					</button>
				<span>总共：</span>
				<input type="text" id="allNo" name="allNo" style="width:20px; outline:none; background:transparent; 
					border:none" value="${prjProg.allNo }" readonly="true"/>张,				
				<span>当前第：</span>
				<input type="text" id="nowNo" name="nowNo" style="width:20px; outline:none; background:transparent; 
					border:none" value="${prjProg.nowNo }" readonly="true"/>张,				
		</div>
		</div>
	</div>
			<form action="" method="post" id="dataForm">
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
				<table cellspacing="0" cellpadding="0" width="100%">
					<col  width="72"/>
					<col  width="150"/>
					<col  width="72"/>
					<col  width="150"/>
					<tbody>
						<tr hidden="true" >	
							<td class="td-label"><span class="required">*</span>客户编号</td>
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
		<table id="dataTable_XJ"></table>
	</div>
	<div  style="width:48%;float: left;margin-left: 1px; class="container" >
			<input style="width:160px;border:none" value="${prjProg.photoPath }" id="photoPath" name="photoPath" hidden="true"/>
		 <div >  
           	 <div style="border:1px solid #e7e7e7;width:563px;margin-left: -1px;">  
          		 <img id="xjPicture" src="${prjProg.photoPath }" onload="AutoResizeImage(495,450,'xjPicture');" width="499" height="450" style="border:none" >  
		  	 </div>  
	  	</div> 
	</div>
</body>
</html> 
