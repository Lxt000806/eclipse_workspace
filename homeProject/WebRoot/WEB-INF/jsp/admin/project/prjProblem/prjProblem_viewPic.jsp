<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>送货图片信息--查看</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){
	var x=-1;
	var lastCellRowId;
	/**初始化表格*/
        Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/prjProblem/goPicJqGrid",
			postData:{no:'${prjProblem.no}'},
			height:480,
			width:200,
		styleUI: 'Bootstrap', 
			colModel : [
		       {name : 'pk',index : 'pk',width : 90,label:'pk',sortable : false,align : "center",hidden:true},
		       {name : 'sendno',index : 'sendno',width : 90,label:'发货单号',sortable : false,align : "center",hidden:true},
		       {name : 'photoname',index : 'photoname',width :230,label:'图片名称',sortable : false,align : "center",hidden:true},
            ],
             gridComplete:function(){
				 var a= $("#dataTable").getCol('photoname',false) ;
				 Name=a[0],
				 $('#photoPath').val(Name);
				 $.ajax({
					url:"${ctx}/admin/prjProblem/ajaxGetPicture",
					type:'post',
					data:{photoName:a[0]},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
					 x=0;
						$('#photoPath').val(obj.photoPath);
						document.getElementById("conPicture").src = obj.photoPath;
						$('#allNoGc').val(a.length);
						if(a.length>0){
							$('#nowNoGc').val('1');
						}else{
							$('#nowNoGc').val('0');
						}
					}
				});
	          },
         });
         
     //下一张
	$("#next").on("click",function(){
			x++;
			var sendphoto = Global.JqGrid.allToJson("dataTable","photoname");
				arry = sendphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProblem/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x]},
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
			var sendphoto = Global.JqGrid.allToJson("dataTable","photoname");
				arry = sendphoto.fieldJson.split(",");
			if(arry[x]){
				$.ajax({
					url:"${ctx}/admin/prjProblem/ajaxGetPicture",
					type:'post',
					data:{photoName:arry[x]},
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
         
});

</script>
</head>

<body>
	<div class="body-box-list" style="margin-top: 0px;">
		<div class="panel panel-system" >
    		<div class="panel-body" >
     			 <div class="btn-group-xs" >
					<button type="button" class="btn btn-system " id="next">
						<span>下一张</span>
					</button>
					<button type="button" class="btn btn-system " id="pre">
						<span>上一张</span>
					</button>
					<span>总共：</span>
					<input type="text" id="allNoGc" name="allNoGc" style="width:20px; outline:none; background:transparent; 
						border:none"  readonly="true"/>张,				
					<span>当前第：</span>
					<input type="text" id="nowNoGc" name="nowNoGc" style="width:20px; outline:none; background:transparent; 
						border:none"  readonly="true"/>张,				
				</div>
			</div>
		</div>
		
			<div class="panel-body">
			  <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame" >
				<house:token></house:token>
				<input type="hidden" name="m_umState" id="m_umState" value="M"/>
						<ul class="ul-form">
							<li hidden="true">
								<label>no</label>
								<input type="text" id="no" name="no" style="width:160px;"  value="${prjProblem.no}" />
							</li>
						</ul>	
			</form>
			</div>
		
		<div class="pageContent" >
			<div  style="width:30%;float: left;margin-left: 1px; class="container" hidden="true" >
				<div id="content-list" >
					<table id="dataTable"></table>
				</div>
			</div>
			
			<div  style="width:48%;float: left;margin-left: 1px; class="container" >
				<input style="width:160px" value="${prjProblem.photoPath }" id="photoPath" name="photoPath" hidden ="true"/>
				 <div >  
	           		 <div >  
	             		 <img id="conPicture" src="${prjProblem.photoPath } " onload="AutoResizeImage(600,460,'conPicture');" width="700" height="490" >  
		          	 </div>  
		  		 </div> 
			</div>
		</div>
	</div>
</body>
</html>
