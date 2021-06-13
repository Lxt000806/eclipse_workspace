<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<% String no = request.getParameter("no"); %>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable_material",{
		url:"${ctx}/admin/prjJobManage/goPrjJobPhotoListJqGrid?type=1&prjJobNo="+$("#no_materialPhoto").val(),
		height:300,
		width:250,
		styleUI:'Bootstrap',
		rowNum:10000,
		colModel : [
					{name : 'PhotoName',index : 'PhotoName',width : 180,label:'图片名称',sortable : true,align : "center"}
					],
		onSelectRow : function() {
			var ret = selectDataTableRow("dataTable_material");
			if(ret != null){
				getDealPhotoPath(ret.PhotoName,"choice");
			}
		},
		gridComplete:function(){
			var rowsCount= $("#dataTable_material").getCol('PhotoName',false);
			$("#allNoGc").val(rowsCount.length);
			if(rowsCount.length > 0){
				$("#nowNoGc").val("1");
			}else{
				$("#nowNoGc").val("0");
			}	
		}
		          
	});
	$("#showPicture").on("mouseover",function(){
		if($("#showPicture").attr("src") != ""){
			$("#bigPictureDiv").css("display","block");
		}
	});
	$("#showPicture").on("mouseout",function(){
		$("#bigPictureDiv").css("display","none");
	});
	$("#showPicture").on("mousemove",function(e){
		var showDiv = $("#showPictureDiv");
		var show = $("#showPicture");
		var bigDiv = $("#bigPictureDiv");
		var big = $("#bigPicture");
		
		var left = e.clientX-show.offset().left-50;
		var top = e.clientY-show.offset().top-50;
		
		if(left<0){
			left = 0;
		}else if(left > show.width()-100){
			left = show.width()-100;
		}
		if(top<0){
			top = 0;
		}else if(top > show.height()-100){
			top = show.height()-100;
		}
		
 		big.css({
			"margin-top":-top*(big.height()-310)/(show.height()-100),
			"margin-left":-left*(big.width()-310)/(show.width()-100)
		}); 
	});
});
function showTips(tips){
	art.dialog({
		content: tips,
		time: 3000,
		beforeunload: function () {
		}
	});
}
function getDealPhotoPath(photoName,from){
	var nowNoGc = parseInt($("#nowNoGc").val().trim());
	var allNoGc = parseInt($("#allNoGc").val().trim());
	if(from == "pre"){
		if(nowNoGc>1){
			$("#nowNoGc").val(nowNoGc-1);
			$("#dataTable_material").jqGrid('setSelection',nowNoGc-1);
		}else{
			showTips("已经是第一张");
			return;
		}
	}else if(from == "next"){
		if(nowNoGc<allNoGc){
			$("#nowNoGc").val(nowNoGc+1);
			$("#dataTable_material").jqGrid('setSelection',nowNoGc+1);
		}else{
			showTips("已经是最后一张");
			return;
		}
	}else if(from == "choice"){
		var arry = getPhotoNameList();
		for(var i=0;i<arry.length;i++){
			if(arry[i] == photoName){
				$("#nowNoGc").val(i+1);
				break;
			}
		}
	}
	$.ajax({
		url:"${ctx}/admin/prjJob/getprjJobPhoto?photoName="+photoName,
		type:'post',
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
		},
		success:function(obj){
			$("#showPicture").attr("src", obj.photoPath);
			$("#bigPicture").attr("src", obj.photoPath).css({"margin-top":0,"margin-left":0});
		}
	});
}
function getPhotoNameList(){
	var photos = Global.JqGrid.allToJson("dataTable_material","PhotoName");
	var arry = photos.fieldJson.split(",");
	return arry;
}
function refresh(){
	$("#dataTable_material").jqGrid("setGridParam",{url:"${ctx}/admin/prjJobManage/goPrjJobPhotoListJqGrid",postData:{prjJobNo:$("#no_materialPhoto").val(),type:"1"},page:1}).trigger("reloadGrid");
}
function next(){
	var arry = getPhotoNameList();
	getDealPhotoPath(arry[parseInt($("#nowNoGc").val().trim())],"next");
}
function pre(){
	var arry = getPhotoNameList();
	getDealPhotoPath(arry[parseInt($("#nowNoGc").val().trim())-2],"pre");
}

</script>
<input type="hidden" value="<%=no %>" id="no_materialPhoto"/>
<div class="panel panel-system" >
    <div class="panel-body" >
    	<div class="btn-group-xs buttons" >
			<button type="button" class="btn btn-system "   onclick="refresh()">刷新图片列表</button>
			<button type="button" class="btn btn-system "   onclick="pre()">上一张</button>
			<button type="button" class="btn btn-system "   onclick="next()">下一张</button>
			<span>总共：</span>
			<input type="text" id="allNoGc" name="allNoGc" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,
			<span>当前第：</span>
			<input type="text" id="nowNoGc" name="nowNoGc" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,	
				
			<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标移动到图片上，查看局部放大图</span>	
		</div>
	</div>
</div>
<div  style="width:350px;float: left;margin-left: 0px;border-right:1px solid #ddd">
	<table id="dataTable_material" style="overflow: auto;"></table>
</div>
<div style="position:absolute;left:360px;" >
	<div id="showPictureDiv" style="float:left;width:350px;margin-top:10px"> 
		<img id="showPicture" src="" onload="AutoResizeImage(340,310,'showPicture');"alt="没有相关图片" >  
	</div>
	<div id="bigPictureDiv" style="float:right;margin-left:10px;margin-top:10px;width:310px;height:310px;border:1px solid #ddd;display:none;overflow:hidden;">
		<img id="bigPicture" src="" onload="AutoResizeImage(1920,1080,'bigPicture');" alt="没有相关图片" >  
	</div>
</div>
