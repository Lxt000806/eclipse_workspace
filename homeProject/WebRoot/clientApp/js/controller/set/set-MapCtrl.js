/**
 * Created by Administrator on 2016/4/15.
 */
define([],function(){
  'use strict';
  function ctrl($scope, $state, mapService, dao,$timeout) {
    $scope.data = {};
    var latitude;
    var lontitude;
    //显示地图
    function showMap() {
      var map = new BMap.Map("map");
      map.centerAndZoom(new BMap.Point(lontitude, latitude), 15);
      mapService.getFormattedAddress(lontitude,latitude).success(function(data){
          $("#title").text(data.result.pois[0].name);
          $("#address").text(data.result.pois[0].addr);
          $("#lontitude").text(lontitude);
          $("#latitude").text(latitude);
        }
      );
      // 创建地理编码实例
      //var myGeo = new BMap.Geocoder();
      // 根据坐标得到地址描述
      //myGeo.getLocation(new BMap.Point(lontitude, latitude), function (result) {
      //});
      //添加鼠标滚动缩放
      //map.enableScrollWheelZoom();
      //添加缩略图控件
      // map.addControl(new BMap.OverviewMapControl({isOpen:false,anchor:BMAP_ANCHOR_BOTTOM_RIGHT}));
      //添加缩放平移控件
      // map.addControl(new BMap.NavigationControl());
      //添加比例尺控件
      // map.addControl(new BMap.ScaleControl());
      //添加地图类型控件
      // map.addControl(new BMap.MapTypeControl());
      // 创建交通流量图层实例
//                var traffic = new BMap.TrafficLayer();
//                map.addTileLayer(traffic);
      //设置标注的图标
      var icon = new BMap.Icon("img/icon_gcoding.png", new BMap.Size(20, 20));
      //设置标注的经纬度
      var marker = new BMap.Marker(new BMap.Point(lontitude, latitude), {icon: icon});
      //把标注添加到地图上
      map.addOverlay(marker);
//                var content = "<table >";
//                content = content + "<tr><td> 编号：001</td></tr>";
//                content = content + "<tr><td> 地点：黎明</td></tr>";
//                content = content + "<tr><td> 时间："+transDate(new Date())+"</td></tr>";
//                content += "</table>";
//                var infowindow = new BMap.InfoWindow(content);
//                marker.addEventListener("click",function(){
//                    this.openInfoWindow(infowindow);
//                });
//                marker.addEventListener("mouseout",function(){
//                    this.closeInfoWindow(infowindow);
//                });

      //点击地图，获取经纬度坐标
//            map.addEventListener("click",function(e){
//                document.getElementById("aa").innerHTML = "经度坐标："+e.point.lng+" &nbsp;纬度坐标："+e.point.lat;
//            });
    }

    function getBigMap() {
      var map = new BMap.Map("bigMap");
      map.centerAndZoom(new BMap.Point(lontitude, latitude), 15);
      var icon = new BMap.Icon("img/icon_gcoding.png", new BMap.Size(40, 40));
      var marker = new BMap.Marker(new BMap.Point(lontitude, latitude), {icon: icon});
      map.addOverlay(marker);
      mapService.getFormattedAddress(lontitude,latitude).success(function(data){
          $scope.items = data.result.pois;
        }
      );
      map = new BMap.Map("map");
      map.centerAndZoom(new BMap.Point(lontitude, latitude), 15);
      icon = new BMap.Icon("img/icon_gcoding.png", new BMap.Size(20, 20));
      marker = new BMap.Marker(new BMap.Point(lontitude, latitude), {icon: icon});
      map.addOverlay(marker);
    }

    $scope.getPosition = function () {
      if (ionic.Platform.isAndroid()) {
        baidu_location.getCurrentPosition(function (data) {
          latitude = data.latitude;
          lontitude = data.lontitude
          showMap()
        }, function () {
        })
      } else {
        navigator.geolocation.getCurrentPosition(function (position) {
          mapService.getNewPoint(position.coords.longitude,position.coords.latitude).success(function(data){
             lontitude=decbase64(data.x)
             latitude=decbase64(data.y);
              showMap();
          });
        });
      }

    }

    $scope.initMap = function () {
      if (ionic.Platform.isAndroid()) {
        baidu_location.getCurrentPosition(function (data) {
          latitude = data.latitude;
          lontitude = data.lontitude;
          getBigMap();
        }, function () {
        })
      } else {
        navigator.geolocation.getCurrentPosition(function (position) {
          //var point = new BMap.Point(position.coords.longitude, position.coords.latitude);
          //BMap.Convertor.translate(point, 0, function (newPoint) {
          //  latitude = newPoint.lat;
          //  lontitude = newPoint.lng;
          //  getBigMap();
          //});
          mapService.getNewPoint(position.coords.longitude,position.coords.latitude).success(function(data){
            lontitude=decbase64(data.x)
            latitude=decbase64(data.y);
            getBigMap();
          });
        });
      }

    }
    $scope.getSignInCount = function () {
      mapService.getSignInCount().success(function (data) {
        $scope.count = data.number;
      })
    }
    $scope.confirmAddress = function () {
      var title = $("input[name=sign]:checked").next().children("span:eq(0)").text()
      var address = $("input[name=sign]:checked").next().children("span:eq(1)").text();
      $("#title").text(title);
      $("#address").text(address);
      $("#lontitude").text(lontitude);
      $("#latitude").text(latitude);
      $state.go('map');
    }
    $scope.sign = function () {
      var custCode = $scope.code;
      if (!custCode) {
        dao.popup.alert('请选择楼盘');
        return;
      }
      var address = '(' + $("#title").text() + ')' + $("#address").text();
      if (!$("#lontitude").text() || !$("#latitude").text()) {
        dao.popup.alert('获取地理位置失败,请检查网络连接正常');
      } else {
        mapService.saveSignIn(custCode, $("#lontitude").text(), $("#latitude").text(), address).success(
          function () {
            dao.popup.alert('已成功签到', function () {
              $scope.count++;
            });
          }
        )
      }
    }
    dao.modal.init($scope, "my-modal.html", "slide-in-up", function () {
      mapService.getHouses().success(function (data) {
        $scope.houses = data.datas;
      })
      $scope.modal.show();
    }, function (house, code) {
      $scope.data.house = house;
      $scope.code = code;
      $scope.modal.hide();
    });
    $scope.searchHouse = function (searchName) {
      mapService.getHouses(searchName).success(function (data) {
        $scope.houses = data.datas;
      })
    }
    var weekDaysList = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
    $scope.date = transDate(new Date())
    $scope.week = weekDaysList[new Date().getDay()].substring(1);
    dao.datepicker.init($scope, weekDaysList, function (val) {
      if (val == undefined) {

      } else {
        var i = 0;
        if (val.getDay() != 0) {
          i = val.getDay() - 1;
        } else {
          i = 6
        }
        $scope.week = weekDaysList[i].substring(1);
        $scope.date = transDate(val);
        $scope.getSignInList(transDate(val));
      }
    })
    $scope.getSignInList = function (date) {
      if (!date) {
        date = transDate(new Date())
      }
      mapService.getSignInList(date).success(function (data) {
        $scope.SignInList = data.datas
      })
    }
    $scope.$on('$ionicView.beforeEnter', function () {
      if ($state.current.name == 'map-adjust') {
        $scope.initMap();
      }
    })
    //关键字搜索
//        $scope.search=function() {
//            var keyword = $("#keyword").val();
//            var local = new BMap.LocalSearch(map, {
//                renderOptions: {map: map}
//            });
//            local.search(keyword);
//        }
  }
  ctrl.$inject=['$scope', '$state', 'mapService', 'dao','$timeout'];
  return ctrl;
})
