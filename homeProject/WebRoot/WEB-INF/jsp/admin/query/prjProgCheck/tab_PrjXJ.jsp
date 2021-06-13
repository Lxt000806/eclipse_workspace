<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	var lastCellRowId;

	/**初始化表格*/
	Global.JqGrid.initJqGrid("dataTable_XJ",{
		url:"${ctx}/admin/prjProgPhoto/goXjPrjJqGrid",
		postData:{refNo:$("#no_materialPhotoXJ").val(),Type:'3'},
		height:300,
		width:250,
		styleUI:'Bootstrap',
		rowNum:10000,
		colModel : [
					{name : 'photoname',index : 'photoname',width : 180,label:'图片名称',sortable : true,align : "center"}
					],
		onSelectRow : function() {
			var ret = selectDataTableRow("dataTable_XJ");
			if(ret != null){
				getPhotoPath(ret.photoname,"choiceXJ");
			}
		},
		gridComplete:function(){
			var rowsCount= $("#dataTable_XJ").getCol('photoname',false);
			$("#allNoXJ").val(rowsCount.length);
			if(rowsCount.length > 0){
				$("#nowNoXJ").val("1");
			}else{
				$("#nowNoXJ").val("0");
			}	
		}
		          
	});
	$("#showPictureXJ").on("mouseover",function(){
		if($("#showPictureXJ").attr("src") != ""){
			$("#bigPictureDiv").css("display","block");
		}
	});
	$("#showPictureXJ").on("mouseout",function(){
		$("#bigPictureDiv").css("display","none");
	});
	$("#showPictureXJ").on("mousemove",function(e){
		var showDiv = $("#showPictureXJDiv");
		var show = $("#showPictureXJ");
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
function getPhotoPath(photoName,from){
	var nowNoXJ = parseInt($("#nowNoXJ").val().trim());
	var allNoXJ = parseInt($("#allNoXJ").val().trim());
	if(from == "preXJ"){
		if(nowNoXJ>1){
			$("#nowNoXJ").val(nowNoXJ-1);
			$("#dataTable_XJ").jqGrid('setSelection',nowNoXJ-1);
		}else{
			showTips("已经是第一张");
			return;
		}
	}else if(from == "nextXJ"){
		if(nowNoXJ<allNoXJ){
			$("#nowNoXJ").val(nowNoXJ+1);
			$("#dataTable_XJ").jqGrid('setSelection',nowNoXJ+1);
		}else{
			showTips("已经是最后一张");
			return;
		}
	}else if(from == "choiceXJ"){
		var arry = getPhotoNameList();
		for(var i=0;i<arry.length;i++){
			if(arry[i] == photoName){
				$("#nowNoXJ").val(i+1);
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
			$("#showPictureXJ").attr("src",obj.xjPhotoPath);
		}
	});
}
function getPhotoNameList(){
	var photos = Global.JqGrid.allToJson("dataTable_XJ","photoname");
	var arry = photos.fieldJson.split(",");
	return arry;
}
function refreshXJ(){
	$("#dataTable_XJ").jqGrid("setGridParam",{url:"${ctx}/admin/prjProgPhoto/goXjPrjJqGrid",postData:{refNo:$("#no_materialPhotoXJ").val(),Type:'3'},page:1}).trigger("reloadGrid");
}
function nextXJ(){
	var arry = getPhotoNameList();
	getPhotoPath(arry[parseInt($("#nowNoXJ").val().trim())],"nextXJ");
}
function preXJ(){
	var arry = getPhotoNameList();
	getPhotoPath(arry[parseInt($("#nowNoXJ").val().trim())-2],"preXJ");
}

</script>
</head>

</script>
<input type="text" value="${prjProgCheck.no}" id="no_materialPhotoXJ" hidden="true"/>
<div class="panel panel-system" >
    <div class="panel-body" >
    	<div class="btn-group-xs buttons" >
			<button type="button" class="btn btn-system "   onclick="refreshXJ()">刷新图片列表</button>
			<button type="button" class="btn btn-system "   onclick="preXJ()">上一张</button>
			<button type="button" class="btn btn-system "   onclick="nextXJ()">下一张</button>
			<span>总共：</span>
			<input type="text" id="allNoXJ" name="allNoXJ" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,
			<span>当前第：</span>
			<input type="text" id="nowNoXJ" name="nowNoXJ" style="width:20px; outline:none; background:transparent; 
				border:none"  readonly="true"/>张,		
		</div>
	</div>
</div>
<div  style="width:350px;float: left;margin-left: 0px;border-right:1px solid #ddd">
	<table id="dataTable_XJ" style="overflow: auto;"></table>
</div>
<div style="position:absolute;left:360px;" >
	<div id="showPictureXJDiv" style="float:left;width:350px;margin-top:10px"> 
		<img id="showPictureXJ" src="" onload="AutoResizeImage(340,310,'showPictureXJ');"alt="没有相关图片" >  
	</div>
</div>
