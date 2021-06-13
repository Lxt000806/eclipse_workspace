<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>工程进度查看施工图片</title>
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
        Global.JqGrid.initJqGrid("dataTable_GCJD1",{
			url:"${ctx}/admin/prjProgPhoto/goPrjJqGrid",
			postData:{custCode:'${prjProgPhoto.custCode}',prjItem:'${prjProgPhoto.prjItem}',type:'1'},
			height:480,
			width:200,
		styleUI: 'Bootstrap', 
			colModel : [
		       {name : 'maxpk',index : 'maxpk',width : 90,label:'pk',sortable : false,align : "center",hidden:true},
		       {name : 'prjitem',index : 'PrjItem',width : 90,label:'施工项目',sortable : false,align : "center",hidden:true},
		       {name : 'custcode',index : 'custcode',width : 90,label:'custcode',sortable : false,align : "center",hidden:true},
		       {name : 'photoname',index : 'photoname',width :230,label:'图片名称',sortable : false,align : "center",hidden:true},
		       {name : 'prjdescr',index : 'PrjItem',width : 250,label:'施工项目',sortable : false,align : "center",},
		       {name : 'type',index : 'type',width : 250,label:'施工项目',sortable : false,align : "center",},
            ],
             gridComplete:function(){
				 var a= $("#dataTable_GCJD1").getCol('photoname',false) ;
				 Name=a[0],
				 $('#photoPath').val(Name);
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
			var prjphoto = Global.JqGrid.allToJson("dataTable_GCJD1","photoname");
			var type = Global.JqGrid.allToJson("dataTable_GCJD1","type");
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
			var prjphoto = Global.JqGrid.allToJson("dataTable_GCJD1","photoname");
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
								<label>客户编号</label>
								<input type="text" id="custCode" name="custCode" style="width:160px;"  value="${prjProgPhoto.custCode}" />
							</li>
							<li hidden="true">
								<label>施工节点</label>
								<input type="text" id="address" name="address" style="width:160px;"  value="${prjProgPhoto.prjItem}" />
							</li>
						</ul>	
			</form>
			</div>
		
		<div class="pageContent" >
			<div  style="width:30%;float: left;margin-left: 1px; class="container" hidden="true" >
				<div id="content-list" >
					<table id="dataTable_GCJD1"></table>
				</div>
			</div>
			
			<div  style="width:48%;float: left;margin-left: 1px; class="container" >
				<input style="width:160px" value="${prjProg.photoPath }" id="photoPath" name="photoPath" hidden ="true"/>
				 <div >  
	           		 <div >  
	             		 <img id="conPicture" src="${prjProg.photoPath } " onload="AutoResizeImage(600,460,'conPicture');" width="700" height="490" >  
		          	 </div>  
		  		 </div> 
			</div>
		</div>
	</div>
</body>
</html>
