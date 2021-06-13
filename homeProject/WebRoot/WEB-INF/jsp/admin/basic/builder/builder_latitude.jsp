<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>根据地址查询经纬度</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=1.3"></script>
</head>
<body onkeydown="keyQuery()">
	<div class="body-box-list">
		<div class="panel panel-system">
			<div class="panel-body">
				<div class="btn-group-xs" style="float:left">
					<button type="button" class="btn btn-system" onclick="save()">获取地址</button>
					<button type="button" class="btn btn-system"
						onclick="closeWin(false)">关闭</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<%-- ‘项目名称’改成‘地点名称’，增强通用性 modify by zb on 20190522 --%>
					<li><label>地点名称</label> <input type="text" id="text_"
						name="text_" style="width:160px;" value="${builder.descr}" />
					</li>
					 <input type="hidden" id="longitude"
						name="longitude" style="width:160px;" value="${builder.longitude}" readonly/>
					<input type="hidden" id="latitude"
						name="latitude" style="width:160px;" value="${builder.latitude}" readonly/>
					<div class="btn-panel">
						<div class="btn-group-sm">
							<button id="btnQuery" type="button" class="btn btn-system "
								onclick="searchByStationName()">查询</button>
						</div>
					</div>
				</ul>
			</form>
			<div id="container"
				style="position: absolute;
                margin-top:30px; 
                width: 950px; 
                height: 500px; 
                top: 50; 
                border: 1px solid gray;
                overflow:hidden;">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	
    var map = new BMap.Map("container");
    "${builder.latitude}"==""?map.centerAndZoom("福州",15):map.centerAndZoom(new BMap.Point("${builder.longitude}", "${builder.latitude}"), 15);
    var marker = new BMap.Marker(new BMap.Point("${builder.longitude}", "${builder.latitude}"));  // 创建标注，为要查询的地方对应的经纬度
	        map.addOverlay(marker);
    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
    map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
    map.addEventListener("click",function(e){
      var marker = new BMap.Marker(e.point);
      $("#longitude").val(e.point.lng);//设置进度
	  $("#latitude").val(e.point.lat);//设置纬度
      map.clearOverlays();         
	  map.addOverlay(marker);
	});
	//添加地图类型控件
	map.addControl(new BMap.MapTypeControl({
		mapTypes:[
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
        ]}));	
    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
	function searchByStationName() {
	    map.clearOverlays();//清空原来的标注
	    var keyword = document.getElementById("text_").value;
	    localSearch.setSearchCompleteCallback(function (searchResult) {
	        var poi = searchResult.getPoi(0);
	            map.centerAndZoom(new BMap.Point(poi.point.lng,  poi.point.lat), 80);
	        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
	        $("#longitude").val(poi.point.lng);//设置进度
	        $("#latitude").val(poi.point.lat);//设置纬度
	        map.addOverlay(marker);
	        var content = document.getElementById("text_").value + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
	        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
	        marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
	        // marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	    });
  
    localSearch.search(keyword);
} 

	function save(){
		var longitude=$("#longitude").val();
		var latitude=$("#latitude").val();
		if(latitude=="" || longitude==""){
			art.dialog({
					content:"无法获取此项目地址，您可以尝试输入更精确的项目名称！",
					width: 370
				});
		}else{
			var json = {};
			json.latitude= longitude+"|"+latitude;
			Global.Dialog.returnData =json;
	        Global.Dialog.closeDialog();
		}
	}
	//回车键搜索
	function keyQuery(){
	 	if (event.keyCode==13)  //回车键的键值为13
	  	$("#btnQuery").click(); //调用登录按钮的登录事件
	}
</script>
</html>
