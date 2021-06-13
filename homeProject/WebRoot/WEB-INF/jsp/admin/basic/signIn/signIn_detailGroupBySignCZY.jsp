<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<%@ include file="/commons/jsp/common.jsp" %>
	</head>
	<script type="text/javascript">
		$(function(){

			Global.JqGrid.initJqGrid("dataTable_signInPic",{
				height:250,
				width:250,
				styleUI:"Bootstrap",
				rowNum:10000,
				colModel:[
					{name: "photoname", index: "photoname", width: 180, label:"图片名称", sortable: true, align: "center"}
				],
				onSelectRow:function(){
					var ret = selectDataTableRow("dataTable_signInPic");
					if(ret != null){
						getPhotoPath(ret.photoname, "choice");
					}
				},
				gridComplete:function(){
					var rowsCount= $("#dataTable_signInPic").getCol("photoname", false);
					$("#allNoGc").val(rowsCount.length);
					if(rowsCount.length > 0){
						$("#nowNoGc").val("1");
					}else{
						$("#nowNoGc").val("0");
					}	
					var ret = selectDataTableRow("dataTable_signInPic");
					if(ret){
						getPhotoPath(ret.photoname, "choice");
					}else{
						$("#showPicture").attr("src", "");
						$("#bigPicture").attr("src", "").css({
							"margin-top":0,
							"margin-left":0
						});
					}
				}
				          
			});
			$("#showPicture").on("mouseover",function(){
				if($("#showPicture").attr("src") != ""){
					$("#bigPictureDiv").css("display", "block");
				}
			});
			$("#showPicture").on("mouseout",function(){
				$("#bigPictureDiv").css("display", "none");
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
					"margin-top":-top*(big.height()-270)/(show.height()-100),
					"margin-left":-left*(big.width()-270)/(show.width()-100)
				}); 
			});		
		
			Global.JqGrid.initJqGrid("signInType2dataTable",{
				url:"${ctx}/admin/signIn/goJqGrid",
				height:200,
				postData:{
	                department1:"${signIn.department1}",
	                department2:"${signIn.department2}",
	                errPosi:"${signIn.errPosi}",
	                signCzy:"${signIn.signCzy}",
	                dateFrom: "<fmt:formatDate value='${signIn.dateFrom}' pattern='yyyy-MM-dd'/>",
	                dateTo: "<fmt:formatDate value='${signIn.dateTo}' pattern='yyyy-MM-dd'/>",
	                signInType2:"${signIn.signInType2}"
	            },
				styleUI: "Bootstrap",
				colModel : [
				  {name : "pk",index : "pk",width : 50,label:"pk",sortable : true,align : "left", hidden:true},
			      {name : "custcode",index : "custcode",width : 70,label:"客户编号",sortable : true,align : "left"},
			      {name : "custaddress",index : "custaddress",width : 145,label:"楼盘",sortable : true,align : "left"},
			      {name : "crtdate",index : "crtdate",width : 140,label:"签到日期",sortable : true,align : "left",formatter:formatTime},
			      {name : "signczydescr",index : "signczydescr",width : 70,label:"签到人",sortable : true,align : "left"},
			      {name : "department1",index : "department1",width : 75,label:"一级部门",sortable : true,align : "left"},
			      {name : "department2",index : "department2",width : 75,label:"二级部门",sortable : true,align : "left"},
			      {name : "address",index : "address",width : 380,label:"地址",sortable : true,align : "left"},
			      {name : "errposidescr",index : "errposidescr",width :75 ,label:"位置异常",sortable : true,align : "left"},
			      {name : "signintype2descr",index : "signintype2descr",width :75 ,label:"服务类型",sortable : true,align : "left"},
			      {name : "custscore",index : "custscore",width :75 ,label:"客户评分",sortable : true,align : "left"},
			      {name : "custremarks",index : "custremarks",width :175 ,label:"客户评价",sortable : true,align : "left"},
			      {name : "no",index : "no",width :75 ,label:"no",sortable : true,align : "left", hidden:true}
	            ],
	            gridComplete:function(){
	            	refresh();
	            },
		        beforeSelectRow:function(rowid, e){
		        	var ret = $("#signInType2dataTable").jqGrid("getRowData", rowid);
		        	$("#dataTable_signInPic").jqGrid("setGridParam", {
		        		url:"${ctx}/admin/signIn/goJqGridSignInPicList",
		        		postData:{
		        			no:ret.no
		        		},
		        		page:1
		        	}).trigger("reloadGrid");
		        }
			});
		});

		function showTips(tips){
			art.dialog({
				content: tips,
				time: 3000,
				beforeunload: function(){}
			});
		}
		function getPhotoPath(photoName, from){
			var nowNoGc = parseInt($("#nowNoGc").val().trim());
			var allNoGc = parseInt($("#allNoGc").val().trim());
			if(from == "pre"){
				if(nowNoGc>1){
					$("#nowNoGc").val(nowNoGc-1);
					$("#dataTable_signInPic").jqGrid("setSelection", nowNoGc-1);
				}else{
					showTips("已经是第一张");
					return;
				}
			}else if(from == "next"){
				if(nowNoGc<allNoGc){
					$("#nowNoGc").val(nowNoGc+1);
					$("#dataTable_signInPic").jqGrid("setSelection", nowNoGc+1);
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
				url:"${ctx}/admin/signIn/getSignInPic?photoName="+photoName,
				type:"post",
				dataType:"json",
				cache:false,
				error:function(obj){
					showAjaxHtml({
						"hidden": false, 
						"msg": "获取数据出错~"
					});
				},
				success:function(obj){
					$("#showPicture").attr("src", obj.photoUrl);
					$("#bigPicture").attr("src", obj.photoUrl).css({
						"margin-top":0,
						"margin-left":0
					});
				}
			});
		}
		function getPhotoNameList(){
			var photos = Global.JqGrid.allToJson("dataTable_signInPic", "photoname");
			var arry = photos.fieldJson.split(",");
			return arry;
		}
		function refresh(){
			var ret = selectDataTableRow("signInType2dataTable");
			if(ret){
				$("#dataTable_signInPic").jqGrid("setGridParam",{
					url:"${ctx}/admin/signIn/goJqGridSignInPicList",
					postData:{
						no:ret.no
					},
					page:1
				}).trigger("reloadGrid");
			}else{
				art.dialog({
					content:"请选择一条记录"
				});
			}
		}
		function next(){
			var arry = getPhotoNameList();
			getPhotoPath(arry[parseInt($("#nowNoGc").val().trim())], "next");
		}
		function pre(){
			var arry = getPhotoNameList();
			getPhotoPath(arry[parseInt($("#nowNoGc").val().trim())-2], "pre");
		}
	</script>
	<body>
		<div class="body-box-form" >
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
				      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
			      </div>
			    </div>
			</div>
		</div>
		<table id= "signInType2dataTable"></table>
		<div id="signInType2dataTablePager"></div>
		<div class="panel panel-system" >
		    <div class="panel-body" >
		    	<div class="btn-group-xs buttons" >
					<button type="button" class="btn btn-system " onclick="refresh()">刷新图片列表</button>
					<button type="button" class="btn btn-system " onclick="pre()">上一张</button>
					<button type="button" class="btn btn-system " onclick="next()">下一张</button>
					<span>总共：</span>
					<input type="text" id="allNoGc" name="allNoGc" style="width:20px; outline:none; background:transparent; border:none" readonly="true"/>张,
					<span>当前第：</span>
					<input type="text" id="nowNoGc" name="nowNoGc" style="width:20px; outline:none; background:transparent; border:none" readonly="true"/>张,	
					<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;鼠标移动到图片上，查看局部放大图</span>	
				</div>
			</div>
		</div>
		<div  style="width:350px;float: left;margin-left: 0px;border-right:1px solid #ddd">
			<table id="dataTable_signInPic" style="overflow: auto;"></table>
		</div>
		<div style="position:absolute;left:360px;" >
			<div id="showPictureDiv" style="float:left;width:350px;margin-top:10px"> 
				<img id="showPicture" src="" onload="AutoResizeImage(300,270, 'showPicture');"alt="没有相关图片" >  
			</div>
			<div id="bigPictureDiv" style="float:right;margin-left:10px;margin-top:10px;width:270px;height:270px;border:1px solid #ddd;display:none;overflow:hidden;">
				<img id="bigPicture" src="" onload="AutoResizeImage(1920,1080, 'bigPicture');" alt="没有相关图片" >  
			</div>
		</div>
	</body>
</html>
