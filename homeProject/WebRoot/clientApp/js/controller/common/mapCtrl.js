/**
 * Created by xzp on 2016/8/3.
 */
define([],function(){
  'use strict';
  function ctrl($scope,dao,mapService){
    var latitude;
    var lontitude;
    dao.modal.init($scope,"templates/modals/map-adjust.html", "fade-in",function(){
      $scope.modal.show();
      if (ionic.Platform.isAndroid()) {
        baidu_location.getCurrentPosition(function (data) {
          latitude = data.latitude;
          lontitude = data.lontitude;
          getBigMap();
        }, function () {
        })
      } else {
        navigator.geolocation.getCurrentPosition(function (position) {
          mapService.getNewPoint(position.coords.longitude,position.coords.latitude).success(function(data){
            lontitude=decbase64(data.x)
            latitude=decbase64(data.y);
            getBigMap();
          });
        });
      }
    });
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
    }
    $scope.confirmAddress=function(){
      var title = $("input[name=sign]:checked").next().children("span:eq(0)").text()
      var address = $("input[name=sign]:checked").next().children("span:eq(1)").text();
      $scope.data.mapAddress=title+':'+address;
      $scope.modal.hide();
    }
    $scope.$on('$destroy', function() {
      $scope.modal.remove();
    });
  }
  ctrl.$inject=['$scope','dao','mapService'];
  return ctrl;
})
