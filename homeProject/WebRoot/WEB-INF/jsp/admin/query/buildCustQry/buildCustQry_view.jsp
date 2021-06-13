<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>修改PrjProg</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	
<script type="text/javascript"> 
var x=-1;//施工
var y=123;//巡检
var z=-1;//验收
$(function(){
		//查看
		$("#addView").on("click",function(){
			var ret= selectDataTableRow('dataTable');
			if(ret==null){
				art.dialog({
					 content:"请先检索出数据",
				});
				return false;
			}
           	Global.Dialog.showDialog("copy",{ 
           	  title:"查看",
           	  url:"${ctx}/admin/prjProg/goViewView",
           	  postData:{pk:ret.PK,
           	  			prjItem:ret.PrjItem,
           	  			planBegin:ret.PlanBegin,
           	  			beginDate:ret.BeginDate,
           	  			planEnd:ret.PlanEnd,
           	  			endDate:ret.EndDate},
           	  height: 450,
           	  width:650,
           	  returnFun:goto_query
            });
		});
		
		$("#viewConsPhoto").on("click",function(){
			var ret= selectDataTableRow('dataTable');
			if(ret==null){
				art.dialog({
					 content:"请先检索出数据",
				});
				return false;
			}
           	Global.Dialog.showDialog("viewConsPhoto",{ 
           	  title:"查看施工图片",
           	  url:"${ctx}/admin/prjProg/goViewConsPhoto",
           	  postData:{prjItem:ret.PrjItem,custCode:ret.CustCode},
           	  height: 650,
           	  width:750,
           	  returnFun:goto_query
            });
		});
		
		$("#viewCheckPhoto").on("click",function(){
			var ret= selectDataTableRow('dataTable_check');
			if(ret==null){
				art.dialog({
					 content:"请选择一条数据",
				});
				return false;
			}
           	Global.Dialog.showDialog("viewConsPhoto",{ 
           	  title:"查看巡检图片",
           	  url:"${ctx}/admin/prjProg/goViewCheckPhoto",
           	  postData:{refNo:ret.no,custCode:"${customer.code}",prjItem:ret.prjitem},
           	  height: 650,
           	  width:750,
           	  returnFun:goto_query
            });
		});
		
		$("#viewConfPhoto").on("click",function(){
			var ret= selectDataTableRow('dataTable_confirm');
			if(ret==null){
				art.dialog({
					 content:"请选择一条数据",
				});
				return false;
			}
           	Global.Dialog.showDialog("viewConsPhoto",{ 
           	  title:"查看验收图片",
           	  url:"${ctx}/admin/prjProg/goViewConfPhoto",
           	  postData:{prjItem:ret.prjitem,custCode:ret.custcode,refNo:ret.no},
           	  height: 650,
           	  width:750,
           	  returnFun:goto_query
            });
		});
		
	//查看巡检图片
	$("#viewPicture_xj").on("click",function(){
		y=0;
			var prjphoto = Global.JqGrid.allToJson("dataTable_xj","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[y]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[y],readonly:'3'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#xjPhotoPath').val(obj.xjPhotoPath);
						document.getElementById("conXjPicture").src = obj.xjPhotoPath;	
						$('#allNoXj').val(arry.length);
						if(arry.length>0){
							$('#nowNoXj').val('1');
						}else{
							$('#nowNoXj').val('0');
						}
					}
				});
			}else{
			y--;
				art.dialog({
					content:'已经是最后一张',
				});
			}	
	});	
	
	//查看施工图片
	$("#viewPicture").on("click",function(){
		x=0;
			var prjphoto = Global.JqGrid.allToJson("dataTable_GCJD","photoname");
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
						$('#allNoGc').val(arry.length);
						if(arry.length>0){
							$('#nowNoGc').val('1');
						}else{
							$('#nowNoGc').val('0');
						}
					}
				});
			}else{
			x--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//查看验收图片
	$("#viewPicture_ys").on("click",function(){
		z=0;
			var prjphoto = Global.JqGrid.allToJson("dataTable_ys","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[z]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[z],readonly:'2'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conYsPicture").src = obj.ysPhotoPath;	
						$('#allNoYs').val(arry.length);
						if(arry.length>0){
							$('#nowNoYs').val('1');
						}else{
							$('#nowNoYs').val('0');
						}
					}
				});
			}else{
			z--;
				art.dialog({
					content:'已经是最后一张',
				});
			}	
	});	
	
	//下一张
	$("#next").on("click",function(){
			x++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_GCJD","photoname");
			var type = Global.JqGrid.allToJson("dataTable_GCJD","type");
				arry = prjphoto.fieldJson.split(",");
				arryy = type.fieldJson.split(",");
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
						$('#nowNoGc').val(x+1);
						$('#allNoGc').val(arry.length);
						
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
			var prjphoto = Global.JqGrid.allToJson("dataTable_GCJD","photoname");
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
						$('#nowNoGc').val(x+1);
						
					}
				});
			}else{
			x++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//下一张 巡检
	$("#nextXJ").on("click",function(){
			y++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_xj","photoname");
			var type = Global.JqGrid.allToJson("dataTable_xj","type");
				arry = prjphoto.fieldJson.split(",");
				arryy = type.fieldJson.split(",");
			if(arry[y]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[y],readonly:'3'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#xjPhotoPath').val(obj.xjPhotoPath);
						document.getElementById("conXjPicture").src = obj.xjPhotoPath;
						$('#nowNoXj').val(y+1);
						$('#allNoXj').val(arry.length);
					}
				});
			}else{
				y--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张 巡检
	$("#preXJ").on("click",function(){
			y--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_xj","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[y]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[y],readonly:'3'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#xjPhotoPath').val(obj.xjPhotoPath);
						document.getElementById("conXjPicture").src = obj.xjPhotoPath;	
						$('#nowNoXj').val(y+1);
					}
				});
			}else{
			y++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//下一张  验收
	$("#nextYS").on("click",function(){
			z++;
			var prjphoto = Global.JqGrid.allToJson("dataTable_ys","photoname");
			var type = Global.JqGrid.allToJson("dataTable_ys","type");
				arry = prjphoto.fieldJson.split(",");
				arryy = type.fieldJson.split(",");
			if(arry[z]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[z],readonly:'2'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conYsPicture").src = obj.ysPhotoPath;	
						$('#nowNoYs').val(z+1);
						$('#allNoYs').val(arry.length);
							
					}
				});
			}else{
			z--;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	//上一张 验收
	$("#preYS").on("click",function(){
			z--;
			var prjphoto = Global.JqGrid.allToJson("dataTable_ys","photoname");
				arry = prjphoto.fieldJson.split(",");
			if(arry[z]){
				$.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[z],readonly:'2'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conYsPicture").src = obj.ysPhotoPath;	
						$('#nowNoYs').val(z+1);	
					}
				});
			}else{
			z++;
				art.dialog({
					content:'已经是最后一张',
				});
			}
	});	
	
	// $("#address").val($.trim("${customer.address}").substr(0,$.trim("${customer.address}").length-4)+'**'+$.trim("${customer.address}").substr($.trim("${customer.address}").length-2));
		
});
</script>
</head>
<body >
<div class="body-box-form" >
	<div class="content-form">
		<div class="panel panel-system" >
			<div class="panel-body" >
				<div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
					<input type="hidden" name="m_umState" id="m_umState" value="M"/>
						<ul class="ul-form">
							<li>
								<label>客户编号</label>
								<input type="text" id="code" name="code" style="width:160px;"  value="${customer.code}" />
							</li>
							<li>
								<label>楼盘</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${customer.address}" />
							</li>
						</ul>	
					</form>
				</div>
			</div>
		</div>
		<div class="container-fluid" >  
			<ul class="nav nav-tabs" > 
		        <li class="active"><a href="#tab_PictureYS" data-toggle="tab">工地验收</a></li>
 			    <li class=""><a href="#tab_PictureXJ" data-toggle="tab">工地巡检</a></li>
		    </ul> 
		    <div class="tab-content">  
		        <div id="tab_PictureYS" class="tab-pane fade in active"> 
		         	<jsp:include page="tab_prjConfirm.jsp"></jsp:include>
		        </div> 
		        <div id="tab_PictureXJ" class="tab-pane fade "> 
		         	<jsp:include page="tab_prjCheck.jsp"></jsp:include>
		        </div> 
		    </div>  
		</div>
</div>
</body>
</html>
