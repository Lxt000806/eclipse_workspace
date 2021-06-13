<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>巡检图片</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	//隐藏
	Global.JqGrid.initJqGrid("dataTable_xj",{
			height:195,
			styleUI: 'Bootstrap', 
			colModel : [
				{name : 'refno',index : 'refno',width : 90,label:'refnob',sortable : false,align : "center",hidden:true},
				{name : 'prjitem',index : 'prjitem',width : 90,label:'施工项目',sortable : false,align : "center"},
				{name : 'type',index : 'type',width : 90,label:'type',sortable : false,align : "center", },
		      	{name : 'photoname',index : 'photoname',width :150,label:'图片名称',sortable : false,align : "center" ,},
            ],
            
            gridComplete:function(){
				 var b= $("#dataTable_xj").getCol('photoname',false) ;
				 Name=b[0],
				 $('#photoPath').val(Name);
				 $.ajax({
					url:"${ctx}/admin/prjProg/ajaxGetPicture",
					type:'post',
					data:{photoName:b[0],readonly:'3'},
					dataType:'json',
					cache:false,
					error:function(obj){
						showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
					},
					success:function(obj){
		            	y=0;
						$('#xjPhotoPath').val(obj.xjPhotoPath);
						document.getElementById("conXjPicture").src = obj.xjPhotoPath;	
						$('#allNoXj').val(b.length);
						if(b.length>0){
							$('#nowNoXj').val('1');
						}else{
							$('#nowNoXj').val('0');
						}
					}
				});
	          },
            
           
         });
         	//显示
         	Global.JqGrid.initJqGrid("dataTable_xj1",{
				url:"${ctx}/admin/prjProgCheck/goJqGrid",
				postData:{custCode:'${customer.code}'},
		styleUI: 'Bootstrap', 
				height:480,
				colModel : [
					{name : 'PrjItem',index : 'PrjItem',width : 90,label:'priItem',sortable : false,align : "center",hidden:true},
					{name : 'No',index : 'No',width : 90,label:'编号',sortable : false,align : "center",},
			      	{name : 'photoname',index : 'photoname',width :150,label:'图片名称',sortable : false,align : "center" ,hidden:true},
			      	{name : 'Address',index : 'Address',width :170,label:'巡检地址',sortable : false,align : "center"},
			       	{name : 'prjitemdescr',index : 'prjitemdescr',width : 120,label:'施工项目',sortable : false,align : "center",},
			       	{name : 'Date',index : 'Date',width : 140,label:'巡检日期',sortable : false,align : "center",formatter:formatTime},
	            ],
	             onSelectRow : function(id) {
            		var row = selectDataTableRow("dataTable_xj1");
            		$("#dataTable_xj").jqGrid('setGridParam',{url : "${ctx}/admin/prjProgPhoto/goXjPrjJqGrid",postData:{refNo:row.No,type:'3,4',prjItem:row.PrjItem},});
            		$("#dataTable_xj").jqGrid().trigger('reloadGrid');
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
				<input type="text" id="allNoXj" name="allNoXj" style="width:20px; outline:none; background:transparent; 
					border:none"  readonly="true"/>张,				
				<span>当前第：</span>
				<input type="text" id="nowNoXj" name="nowNoXj" style="width:20px; outline:none; background:transparent; 
					border:none"  readonly="true"/>张,				
	</div>
	</div>
	</div>
	<div class="pageContent" style="width:1057px">
		<div  style="width:48%;float:left ;margin-left: 1px; class="container" >
			<div hidden="true" >
				<table id="dataTable_xj" style="overflow: auto;" ></table>
			</div>
			<div id="content-list">
 				<table id="dataTable_xj1" style="overflow: auto;" ></table>
					<div id="dataTablePager"></div>
			</div>
		</div>
		
		<div style="width:50%;float: right;margin-left: 1px; class="container" >
			<input style="width:160px" value="${prjProg.xjPhotoPath }" id="xjPhotoPath" name="xjPhotoPath" hidden="true" />
			 <div >  
           		 <div >  
             		 <img id="conXjPicture" src="${prjProg.xjPhotoPath }" onload="AutoResizeImage(450,470,'conXjPicture');" width="500" height="490"  >  
	          	 </div>  
	  		 </div> 
		</div>
	
	</div>
	
	
	
</body>
</html>
