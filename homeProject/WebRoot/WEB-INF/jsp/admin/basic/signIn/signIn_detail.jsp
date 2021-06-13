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
	<script type="text/javascript">
		$(function(){
			Global.JqGrid.initJqGrid("dataTable_signInPic",{
				url:"${ctx}/admin/signIn/goJqGridSignInPicList",
				postData:{
					no:"${signIn.no}"
				},
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
					"margin-top":-top*(big.height()-250)/(show.height()-100),
					"margin-left":-left*(big.width()-250)/(show.width()-100)
				}); 
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
			$("#dataTable_signInPic").jqGrid("setGridParam",{
				url:"${ctx}/admin/signIn/goJqGridSignInPicList",
				postData:{
					no:"${signIn.no}"
				},
				page:1
			}).trigger("reloadGrid");
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
</head>
<body>
<div class="body-box-form" >
	<div class="panel panel-system" >
	    <div class="panel-body" >
	      <div class="btn-group-xs" >
		      <button type="button" class="btn btn-system "   onclick="closeWin()">关闭</button>
	      </div>
	    </div>
	</div>
	<div class="panel panel-info" >  
         <div class="panel-body">
             <form role="form" class="form-search" id="dataForm"  action="" method="post" target="targetFrame">
					<house:token></house:token>
					<input type="hidden" name="m_umState" id="m_umState" value="A"/>
				<ul class="ul-form">
					<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode"   value="${signIn.custCode}" />
							</label>
					</li>
					<li>
							<label>楼盘</label>
							<input type="text" id="custAddress" name="custAddress"   value="${signIn.custAddress}" />
							</label>
					</li>
					<li>	
							<label>签到日期</label>
							<input type="text" id="crtDate" name="crtDate"   value="<fmt:formatDate value='${signIn.crtDate}'/>" />
							</label>
					</li>
					<li>
							<label>一级部门</label>
							<input type="text" id="department1" name="department1"   value="${signIn.department1}" />
							</label>
					</li>
					<li>	
							<label>二级部门</label>
							<input type="text" id="department2" name="department2"   value="${signIn.department2}" />
							</label>
					</li>
					<li>	
							<label>位置异常</label>
							<house:xtdm id="errPosi" dictCode="YESNO" value="${signIn.errPosi }"></house:xtdm>
							</label>
					</li>
					<li>		
							<label>地址</label>
							<input type="text" id="address" name="address" style="width:448px;"  value="${signIn.address}" />
							</label>
					</li>
					<li>	
							<label>施工节点</label>
							<input type="text" id="prjItem" name="prjItem"   value="${signIn.prjItem}" />
							</label>
					</li>
					<li>	
							<label>初检通过</label>
							<input type="text" id="isPass" name="isPass"   value="${signIn.isPass}" />
							</label>
					</li>
					<li>	
							<label>备注</label>
							<input type="text" id="remarks" name="remarks" style="width:448px;" value="${signIn.remarks}" />
							</label>
					</li>
					<li>	
							<label>初检通过</label>
							<input type="text" id="custScoreSignIn" name="custScoreSignIn" readonly="true"  value="${signIn.custScoreSignIn}" />
							</label>
					</li>
					<li>	
							<label>客户评价</label>
							<input type="text" id="custRemarks" name="custRemarks" readonly="true" style="width:448px;" value="${signIn.custRemarks}" />
							</label>
					</li>
				</ul>		
			</form>
		</div>
	</div>
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
		<div id="showPictureDiv" style="float:left;width:320px;margin-top:10px"> 
			<img id="showPicture" src="" onload="AutoResizeImage(300,270, 'showPicture');"alt="没有相关图片" >  
		</div>
		<div id="bigPictureDiv" style="float:right;margin-left:10px;margin-top:10px;width:250px;height:250px;border:1px solid #ddd;display:none;overflow:hidden;">
			<img id="bigPicture" src="" onload="AutoResizeImage(1920,1080, 'bigPicture');" alt="没有相关图片" >  
		</div>
	</div>
</div>
</body>
</html>
