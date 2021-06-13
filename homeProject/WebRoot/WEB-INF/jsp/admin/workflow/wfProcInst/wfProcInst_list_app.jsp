<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
		<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
		<META HTTP-EQUIV="expires" CONTENT="0" />
		<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
		<title>流程实例列表</title>
		<link href="${resourceRoot}/mescroll/mescroll.min.css" rel="stylesheet" />
		<script src="${resourceRoot}/mescroll/mescroll.min.js"></script>
		<%@ include file="/commons/jsp/common.jsp" %>
		
		<script type="text/javascript">
		var origin = "";
		var count = 0;
		$(function(){  
			window.addEventListener("message", function(e){
				origin = e.origin;
			}, false);
			
			//创建MeScroll对象
			var mescroll = new MeScroll("mescroll", {
				down: {
					auto: false, //是否在初始化完毕之后自动执行下拉回调callback; 默认true
					callback: downCallback //下拉刷新的回调
				},
				up: {
					auto: false, //是否在初始化时以上拉加载的方式自动加载第一页数据; 默认false
					isBounce: false, //此处禁止ios回弹,解析(务必认真阅读,特别是最后一点): http://www.mescroll.com/qa.html#q10
					callback: upCallback, //上拉回调,此处可简写; 相当于 callback: function (page) { upCallback(page); }
				}
			});
			
			function setData(data){
				$("#newsList").append("<li id=\"index_"+count+"\" class=\"item\" processInstanceId=\""+data.actprocinstid+"\" taskId=\""+data.taskid+"\" procdefname=\""+data.procdefname+"\">"
									  +data.title+"<br/>"+new Date(data.starttime).format("yyyy-MM-dd hh:mm:ss")+"<br/>"
									  +data.statusdescr+"<br/><br/>摘要：<br/>"
									  +(data.summary && data.summary != "" ? data.summary : "无")+"</li>");
				$("#index_"+count).on("click", function(){
					var data = {
						respType: 1,
						taskId: $(this).attr("taskId"),
						processInstanceId: $(this).attr("processInstanceId"),
						title: $(this).attr("procdefname"),
						m_umState: $("#m_umState").val()
					};
    				top.postMessage(data, origin);
				});
				count++;
			}
			
			function getDatas(pageNo, pageSize, flag){
				setTimeout(function(){
				 	$.ajax({
						url: "${ctx}/admin/wfProcInst/findWfProcInst?appType=${appType}&type=${type}",
						data: {
							page: pageNo,
							rows: pageSize
						},
						type: "get",
						dataType: "json",
						error: function(obj){
		               		mescroll.endErr();
						},
						success: function(obj){
							for(var i = 0; i < obj.rows.length; i++){
								setData(obj.rows[i]);
							}
							if(obj.total == 0){
								mescroll.endSuccess(obj.rows.length, false);
							}else{
								mescroll.endByPage(obj.rows.length, obj.total);
							}
						}
					});	
				}, 1000);
			}
			
			function downCallback(){
				$("#newsList").html("");
				mescroll.setPageNum(1);
				mescroll.resetUpScroll(false);
				count = 0;
			}
			
			function upCallback(page){
				getDatas(page.num, page.size, false);
			}
			mescroll.triggerDownScroll();
		}); 
		</script>
		<style type="text/css">
			* {
				margin: 0;
				padding: 0;
				-webkit-touch-callout:none;
				-webkit-user-select:none;
				-webkit-tap-highlight-color:transparent;
			}
			body{background-color: #F5F5F5}
			ul{list-style-type: none}
			
			/*列表*/
			.mescroll{
				position: fixed;
				top: 0px;
				bottom: 0;
				height: auto;
			}
			/*展示上拉加载的数据列表*/
			.news-list li{
				padding: 16px;
			}
			.news-list .new-content{
				font-size: 14px;
				margin-top: 6px;
				margin-left: 10px;
				color: #666;
			}
			
			.item {
				margin: 10px auto;
				width: 95%;
				border-radius: 10px;
				background: white;
			}
		</style>
	</head>
	<body>
		<!--滑动区域-->
		<input type="hidden" id="m_umState" value="${m_umState}"/>
		<div id="mescroll" class="mescroll">
			<ul id="newsList" class="news-list">
			</ul>
		</div>
	</body>
</html>


