/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  function  service($http, dao) {
    return {
      saveSignIn: function (custCode, lontitude, latitude, address) {
        var data = new Object();
        data.portalType = 1;
        data.custCode = custCode;
        data.signCzy = localStorage.prjNumber;
        data.longitude = lontitude;
        data.latitude = latitude;
        data.address = address;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('client/signIn/saveSignIn', data);
      },
      getHouses: function (address) {
        var data = {};
        data.portalType = 1;
        data.id = localStorage.prjNumber;
        data.address = address;
        data.pageSize = 20;
        data.pageNo = 1;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('client/customer/getCustomerList', data);
      },
      getSignInList: function (date) {
        var data = {};
        data.portalType = portalType;
        data.signCzy = localStorage.prjNumber;
        data.crtDate = date
        data.pageSize = 20;
        data.pageNo = 1;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('client/signIn/getSignInList', data);
      },
      getSignInCount: function () {
        var data = {};
        data.portalType = portalType;
        data.signCzy = localStorage.prjNumber;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('client/signIn/getSignInCount', data);
      },
      getNewPoint:function(longitude,latitude){
        var data = {};
        data.from =0;
        data.to =4;
        data.x=longitude;
        data.y=latitude;
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('http://api.map.baidu.com/ag/coord/convert', data);
      },
      getFormattedAddress:function(longitude,latitude){
        var data = {};
        data.ak='Zoxpyo6dprrwoys064DMDRXT';
        data.location=latitude+','+longitude;
        data.output='json';
        data.pois=1;
        data.mcode=';com.house.app';
        data.callback = 'JSON_CALLBACK';
        return dao.https.jsonp('http://api.map.baidu.com/geocoder/v2/', data);
      }
    }
  }
  service.$inject=['$http', 'dao'];
  return service;
})
