<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	var lastCellRowId;

	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable_ZG",{
		url:"${ctx}/admin/prjProgPhoto/goXjPrjJqGrid",
		postData:{refNo:$("#no_materialPhotoZG").val(),Type:'4'},
		height:300,
		width:250,
		styleUI:'Bootstrap',
		rowNum:10000,
		colModel : [
					{name : 'photoname',index : 'photoname',width : 180,label:'图片名称',sortable : true,align : "center"}
					],
		onSelectRow : function() {
			var ret = selectDataTableRow("dataTable_ZG");			
			if(ret != null){
				getPhotoPathXJ(ret.photoname,"choiceZG");
			}
		},
		gridComplete:function(){
			var rowsCount= $("#dataTable_ZG").getCol('photoname',false);
			$("#allNoZG").val(rowsCount.length);
			if(rowsCount.length > 0){
				$("#nowNoZG").val("1");
			}else{
				$("#nowNoZG").val("0");
			}	
		}
		          
	});
	$("#showPictureZG").on("mouseover",function(){
		if($("#showPictureZG").attr("src") != ""){
			$("#bigPictureDiv").css("display","block");
		}
	});
	$("#showPictureZG").on("mouseout",function(){
		$("#bigPictureDiv").css("display","none");
	});
	$("#showPictureZG").on("mousemove",function(e){
		var showDiv = $("#showPictureZGDiv");
		var show = $("#showPictureZG");
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
function getPhotoPathXJ(photoName,from){
	var nowNoZG = parseInt($("#nowNoZG").val().trim());
	var allNoZG = parseInt($("#allNoZG").val().trim());
	if(from == "pre"){
		if(nowNoZG>1){
			$("#nowNoZG").val(nowNoZG-1);
			$("#dataTable_ZG").jqGrid('setSelection',nowNoZG-1);
		}else{
			showTips("已经是第一张");
			return;
		}
	}else if(from == "next"){
		if(nowNoZG<allNoZG){
			$("#nowNoZG").val(nowNoZG+1);
			$("#dataTable_ZG").jqGrid('setSelection',nowNoZG+1);
		}else{
			showTips("已经是最后一张");
			return;
		}
	}else if(from == "choiceZG"){
		var arry = getPhotoNameListXJ();
		for(var i=0;i<arry.length;i++){
			if(arry[i] == photoName){
				$("#nowNoZG").val(i+1);
				break;
			}
		}
	}
	
	$.ajax({
		url:"${ctx}/admin/prjProg/ajaxGetPicture",
		type:'post',
		data:{photoName:photoName,readonly:'3'},
		dataType:'json',
		cache:false,
		error:function(obj){
			showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});		
		},
		success:function(obj){	
			$("#showPictureZG").attr("src",obj.xjPhotoPath);
		}
	});
}
function getPhotoNameListXJ(){
	var photos = Global.JqGrid.allToJson("dataTable_ZG","photoname");
	var arry = photos.fieldJson.split(",");
	return arry;
}
function refresh(){
	$("#dataTable_ZG").jqGrid("setGridParam",{url:"${ctx}/admin/prjProgPhoto/goXjPrjJqGrid",postData:{refNo:$("#no_materialPhotoZG").val(),Type:'4'},page:1}).trigger("reloadGrid");
}
function next(){
	var arry = getPhotoNameListXJ();
	getPhotoPathXJ(arry[parseInt($("#nowNoZG").val().trim())],"next");
}
function pre(){
	var arry = getPhotoNameListXJ();
	getPhotoPathXJ(arry[parseInt($("#nowNoZG").val().trim())-2],"pre");
}

</script>
<input type="text" value="${prjProgCheck.no}" id="no_materialPhotoZG" hidden="true"/>
<div class="panel panel-system" >
    <div class="panel-body" >
    	<div class="btn-group-xs buttons" >
			<button type="button" class="btn btn-system "   onclick="refresh()">刷新图片列表</button>
			<button type="button" class="btn btn-system "   onclick="pre()">上一张</button>
			<button type="button" class="btn btn-system "   onclick="next()">下一张</button>
			<span>总共：</span>
			<input type="text" id="allNoZG" name="allNoZG" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,
			<span>当前第：</span>
			<input type="text" id="nowNoZG" name="nowNoZG" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,		
		</div>
	</div>
</div>
<div  style="width:350px;float: left;margin-left: 0px;border-right:1px solid #ddd">
	<table id="dataTable_ZG" style="overflow: auto;"></table>
</div>
<div style="position:absolute;left:360px;" >
	<div id="showPictureZGDiv" style="float:left;width:350px;margin-top:10px"> 
		<img id="showPictureZG" src="" onload="AutoResizeImage(340,310,'showPictureZG')" alt="没有相关图片" >  
	</div>
</div>
