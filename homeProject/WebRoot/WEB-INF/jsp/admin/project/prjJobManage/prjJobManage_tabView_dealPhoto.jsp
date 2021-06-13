<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<% String no = request.getParameter("no"); %>

<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable_dealMaterial",{
		url:"${ctx}/admin/prjJobManage/goPrjJobPhotoListJqGrid?type=2&prjJobNo="+$("#no_materialPhoto").val(),
		height:300,
		width:250,
		styleUI:'Bootstrap',
		rowNum:10000,
		colModel : [
					{name : 'PhotoName',index : 'PhotoName',width : 180,label:'图片名称',sortable : true,align : "center"}
					],
		onSelectRow : function() {
			var ret = selectDataTableRow("dataTable_dealMaterial");
			if(ret != null){
				getPhotoPath(ret.PhotoName,"choice");
			}
		},
		gridComplete:function(){
			var rowsCount= $("#dataTable_dealMaterial").getCol('PhotoName',false);
			$("#allNoGc_deal").val(rowsCount.length);
			if(rowsCount.length > 0){
				$("#nowNoGc_deal").val("1");
			}else{
				$("#nowNoGc_deal").val("0");
			}	
		}
		          
	});
	$("#showDealPicture").on("mouseover",function(){
		if($("#showDealPicture").attr("src") != ""){
			$("#bigDealPictureDiv").css("display","block");
		}
	});
	$("#showDealPicture").on("mouseout",function(){
		$("#bigDealPictureDiv").css("display","none");
	});
	$("#showDealPicture").on("mousemove",function(e){
		var showDiv = $("#showDealPictureDiv");
		var show = $("#showDealPicture");
		var bigDiv = $("#bigDealPictureDiv");
		var big = $("#bigDealPicture");
		
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
function getPhotoPath(photoName,from){
	var nowNoGc_deal = parseInt($("#nowNoGc_deal").val().trim());
	var allNoGc_deal = parseInt($("#allNoGc_deal").val().trim());
	if(from == "pre"){
		if(nowNoGc_deal>1){
			$("#nowNoGc_deal").val(nowNoGc_deal-1);
			$("#dataTable_dealMaterial").jqGrid('setSelection',nowNoGc_deal-1);
		}else{
			showTips("已经是第一张");
			return;
		}
	}else if(from == "next"){
		if(nowNoGc_deal<allNoGc_deal){
			$("#nowNoGc_deal").val(nowNoGc_deal+1);
			$("#dataTable_dealMaterial").jqGrid('setSelection',nowNoGc_deal+1);
		}else{
			showTips("已经是最后一张");
			return;
		}
	}else if(from == "choice"){
		var arry = getDealPhotoNameList();
		for(var i=0;i<arry.length;i++){
			if(arry[i] == photoName){
				$("#nowNoGc_deal").val(i+1);
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
			$("#showDealPicture").attr("src", obj.photoPath);
			$("#bigDealPicture").attr("src", obj.photoPath).css({"margin-top":0,"margin-left":0});
		}
	});
}
function getDealPhotoNameList(){
	var photos = Global.JqGrid.allToJson("dataTable_dealMaterial","PhotoName");
	var arry = photos.fieldJson.split(",");
	return arry;
}
function refresh_deal(){
	$("#dataTable_dealMaterial").jqGrid("setGridParam",{url:"${ctx}/admin/prjJobManage/goPrjJobPhotoListJqGrid",postData:{prjJobNo:$("#no_materialPhoto").val(),type:"2"},page:1}).trigger("reloadGrid");
}
function next_deal(){
	var arry = getDealPhotoNameList();
	getPhotoPath(arry[parseInt($("#nowNoGc_deal").val().trim())],"next");
}
function pre_deal(){
	var arry = getDealPhotoNameList();
	getPhotoPath(arry[parseInt($("#nowNoGc_deal").val().trim())-2],"pre");
}

</script>
<input type="hidden" value="<%=no %>" id="no_materialPhoto"/>
<div class="panel panel-system" >
    <div class="panel-body" >
    	<div class="btn-group-xs buttons" >
			<button type="button" class="btn btn-system "   onclick="refresh_deal()">刷新图片列表</button>
			<button type="button" class="btn btn-system "   onclick="pre_deal()">上一张</button>
			<button type="button" class="btn btn-system "   onclick="next_deal()">下一张</button>
			<span>总共：</span>
			<input type="text" id="allNoGc_deal" name="allNoGc_deal" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,
			<span>当前第：</span>
			<input type="text" id="nowNoGc_deal" name="nowNoGc_deal" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,	
				
			<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标移动到图片上，查看局部放大图</span>	
		</div>
	</div>
</div>
<div  style="width:350px;float: left;margin-left: 0px;border-right:1px solid #ddd">
	<table id="dataTable_dealMaterial" style="overflow: auto;"></table>
</div>
<div style="position:absolute;left:360px;" >
	<div id="showDealPictureDiv" style="float:left;width:350px;margin-top:10px"> 
		<img id="showDealPicture" src="" onload="AutoResizeImage(340,310,'showDealPicture');"alt="没有相关图片" >  
	</div>
	<div id="bigDealPictureDiv" style="float:right;margin-left:10px;margin-top:10px;width:310px;height:310px;border:1px solid #ddd;display:none;overflow:hidden;">
		<img id="bigDealPicture" src="" onload="AutoResizeImage(1920,1080,'bigDealPicture');" alt="没有相关图片" >  
	</div>
</div>
