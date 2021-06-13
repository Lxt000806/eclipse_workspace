<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>验收图片</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>


<script type="text/javascript">
$(function(){
		var lastCellRowId;
		/**初始化表格*/
		Global.JqGrid.initJqGrid("dataTable_ys",{
			height:390,
		styleUI: 'Bootstrap', 
			colModel : [
		  		{name : 'prjitem',index : 'PrjItem',width : 90,label:'施工项目',sortable : false,align : "center",hidden:true},
		      	{name : 'photoname',index : 'photoname',width :150,label:'图片名称',sortable : false,align : "center" ,},
		      	{name : 'addresscheck',index : 'addresscheck',width :170,label:'验收地址',sortable : false,align : "center"},
		       	{name : 'prjdescr',index : 'PrjItem',width : 200,label:'施工项目',sortable : false,align : "center",},
            	{name : 'confrimdate',index : 'confrimdate',width : 140,label:'验收时间',sortable : false,align : "center",formatter:formatTime},
 			 ],
 			 gridComplete:function(){
				 var c= $("#dataTable_ys").getCol('photoname',false) ;
				 Name=c[0],
				 $('#photoPath').val(Name);
				 $.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:c[0],readonly:'2'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
						z=0;
						$('#ysPhotoPath').val(obj.ysPhotoPath);
						document.getElementById("conYsPicture").src = obj.ysPhotoPath;	
						$('#allNoYs').val(c.length);
						if(c.length>0){
							$('#nowNoYs').val('1');
						}else{
							$('#nowNoYs').val('0');
						}
					}
				});
	          },
 			 
 			 
         });
		    
		Global.JqGrid.initJqGrid("dataTable_ys1",{
			url:"${ctx}/admin/prjProgConfirm/goJqGrid",
			postData:{custCode:'${customer.code}',type:'2'},
			height:480,
		styleUI: 'Bootstrap', 
			colModel : [
		  		{name : 'PrjItem',index : 'PrjItem',width : 90,label:'施工项目',sortable : false,align : "center",hidden:true},
		  		{name : 'prjitemdescr',index : 'prjitemdescr',width : 140,label:'施工项目',sortable : false,align : "center",},
		      	{name : 'Address',index : 'Address',width :150,label:'验收地址',sortable : false,align : "center" ,},
		      	{name : 'Date',index : 'Date',width :150,label:'验收时间',sortable : false,align : "center" ,formatter:formatTime},
		      	{name : 'CustCode',index : 'CustCode',width :150,label:'CustCode',sortable : false,align : "center" ,hidden:true},
            
 			 ],
 			  onSelectRow : function(id) {
           		var row = selectDataTableRow("dataTable_ys1");
           		$("#dataTable_ys").jqGrid('setGridParam',{url : "${ctx}/admin/prjProgPhoto/goYsPrjJqGrid",postData:{custCode:'${customer.code}',type:'2',prjItem:row.PrjItem},});
           		$("#dataTable_ys").jqGrid().trigger('reloadGrid');
           	  } 
         });	    
});



</script>
</style>
</head>
<body>
	<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
				<button type="button" class="btn btn-system " id="nextYS">
					<span>下一张</span>
				</button>
				<button type="button" class="btn btn-system "id="preYS">
					<span>上一张</span>
				</button>
				<span>总共：</span>
				<input type="text" id="allNoYs" name="allNoYs" style="width:20px; outline:none; background:transparent; 
					border:none"  readonly="true"/>张,				
				<span>当前第：</span>
				<input type="text" id="nowNoYs" name="nowNoYs" style="width:20px; outline:none; background:transparent; 
					border:none"  readonly="true"/>张,				
		</div>
		</div>
		</div>
			<div class="pageContent" style="width:1057px">
		
		<div style="width:48%;float:left ;margin-left: 1px; class="container" >
			<div  hidden="true">
				<table id="dataTable_ys" style="overflow: auto;"></table>
			</div>
			<table id="dataTable_ys1" style="overflow: auto;"></table>
		</div>
		<div  style="width:50%;float: right;margin-left: 1px; class="container" >
			<input style="width:160px" value="${prjProg.ysPhotoPath }" id="ysPhotoPath" name="ysPhotoPath" hidden="true" />
			 <div >  
           		 <div >  
             		 <img id="conYsPicture" src="${prjProg.ysPhotoPath }" onload="AutoResizeImage(450,460,'conYsPicture');" width="500" height="490"  >  
	          	 </div>  
	  		 </div> 
		</div>
		</div>	
</body>
</html>
