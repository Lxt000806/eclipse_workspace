/**
 * Created by xzp on 2016/9/7.
 */
define([],function(){
  'use strict';
  function  service($q, $http, $ionicLoading,dao) {
    return {
      findPageBySql: function (pageNow, address) {
        var data = new Object();
        data.portalType = 1;
        data.id =localStorage.czyNumber;
        data.address = address;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerList', data);
      },
      //根据客户权限获取客户列表接口
      getCustomerListByCustRight:function(pageNow, address,stakeholder,haveGd){
        var data = new Object();
        data.portalType = 1;
        data.czybh =localStorage.czyNumber;
        data.address = address;
        data.stakeholder=stakeholder;
        data.haveGd=haveGd;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerListByCustRight', data);
      },
      //获取客户详情
      getCustomerDetail: function (code) {
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerDetail', data);
      },
      //获取付款详情
      getCustomerDetailPayInfo: function (code) {
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerPayDetail', data);
      },
      //获取成本支出详情
      getCustomerDetailCostInfo: function (code) {
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerCostDetail', data);
      },
      //获取付款明细列表
      getPayInfoDetailList: function (code) {
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getPayInfoDetailList', data);
      },
      //获取干系人列表
      getGxrList: function (code) {
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getGxrList', data);
      },
      //获取客户消息列表
      getCustomerMessageList: function (code,pageNow) {
        var data = new Object();
        data.portalType = 1;
        data.msgRelCustCode = code;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/personMessage/getPersonMessageList', data);
      },
      //获取领料列表
      getCustomerDetailItemApp: function (code,itemType1,pageNow) {
        //code = 'CT003449';
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.itemType1 = itemType1;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerItemAppList', data);
      },
      //获取领料详情列表
      getItemAppDetailList: function (no,pageNow) {
        //no = 'IA00000043';
        var data = new Object();
        data.portalType = 1;
        data.id = no;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerItemAppDetailList', data);
      },
      //获取基础变更列表
      getBaseItemChgList: function (code,pageNow) {
        //code = 'CT008524';
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getBaseItemChgList', data);
      },
      //获取基础变更详情列表
      getBaseItemChgDetailList: function (no,pageNow) {
        var data = new Object();
        data.portalType = 1;
        data.id = no;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getBaseItemChgDetailList', data);
      },
      //获取基础需求列表
      getBaseItemReqList: function (code,fixAreaDescr,pageNow) {
        //code = 'CT000291';
        var data = new Object();
        data.portalType = 1;
        data.custCode = code;
        data.fixAreaDescr = fixAreaDescr;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getBaseItemReqList', data);
      },
      //获取材料变更列表
      getCustomerDetailMaterialChange: function (code,pageNow) {
        //code = 'CT003449';
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerItemChangeList', data);
      },
      //获取材料变更详情列表
      getMaterialChangeDetail: function (no,pageNow) {
        //no = '2013041925';
        var data = new Object();
        data.portalType = 1;
        data.id = no;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerItemChangeDetailList', data);
      },
      //获取材料需求列表
      getCustomerDetailMaterialNeeds: function (code,itemType1,itemDescr,pageNow) {
        //code = 'CT003449';
        var data = new Object();
        data.portalType = 1;
        data.id = code;
        data.itemType1 = itemType1;
        data.itemDescr = itemDescr;
        data.pageSize = pageSize;
        data.pageNo = pageNow;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/customer/getCustomerItemReqList', data);
      },
      //获取材料类型1
      getItemType1List: function () {
        var data = new Object();
        data.portalType = 1;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/basic/getItemType1List', data);
      },
      //获取材料类型2
      getItemType2List: function (itemType1) {
        var data = new Object();
        data.portalType = 1;
        data.itemType1 = itemType1;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/basic/getItemType2List', data);
      },
      //根据ID获取系统代码列表
      getXtdmListById: function (id) {
        var data = new Object();
        data.portalType = 1;
        data.id = id;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/basic/getXtdmListById', data);
      },
      //获取进度细节列表
      getProgressDetailList: function (clientId) {
        var data = new Object();
        data.portalType = 1;
        data.id = clientId;
        data.pageSize = -1;
        data.pageNow = 1;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp("client/prjProg/getPrjProgDetailList", data, "", "", true);
      },
      //获取进度详情
      getProgressDetail: function (pk) {
        var data = new Object();
        data.portalType = 1;
        data.id = pk;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp("client/prjProg/getPrjProgDetail", data);
      },
      getPrjProgDate:function(clientId){
        var data = new Object();
        data.portalType = 1;
        data.code=clientId;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp("client/prjProg/getPrjProgDate", data);
      }
    }
  }
  service.$inject=['$q', '$http', '$ionicLoading','dao'];
  return service;
})
