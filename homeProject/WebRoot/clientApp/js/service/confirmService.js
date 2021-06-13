/**
 * Created by xzp on 2016/8/1.
 */
define([],function(){
  'use strict';
  function service(dao){
    return{
      getSiteConfirmList:function(pageNow){
        var data={};
        data.portalType=portalType;
        data.id=localStorage.czyNumber;
        data.pageSize=10;
        data.pageNo=pageNow;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/prjProgConfirm/getSiteConfirmList",data);
      },
      getPrjItemDescr:function(id){
        var data={};
        data.portalType=portalType;
        data.id=id;
        data.pageSize=-1;
        data.pageNo=1;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/prjProgConfirm/getPrjItemDescr",data);
      },
      savePrjProgConfirm:function(code,prjItem,remarks,address,isPass,photoNameList){
        var data={};
        data.portalType=2;
        data.custCode=code;
        data.prjItem=prjItem;
        data.remarks=remarks;
        data.address=address;
        data.isPass=isPass;
        data.photoNameList=photoNameList;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/prjProgConfirm/savePrjProgConfirm",data,"","",true);
      },
      prjProgPhotoDelete:function(id){
        var data={};
        data.id=id;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/prjProgConfirm/prjProgPhotoDelete",data);
      },
      getCheckCustomerByPage:function(pageNow,address){
        var data={};
        data.id=localStorage.czyNumber;
        data.pageSize=10;
        data.pageNo=pageNow;
        data.status=4;
        data.address=address;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/basic/getCheckCustomer",data);
      },
      getSiteConfirmDetail:function(id){
        var data={};
        data.id=id;
        data.callback="JSON_CALLBACK";
        return dao.https.jsonp("client/prjProgConfirm/getSiteConfirmDetail",data);
      },
      getPrjProgConfirmPhotoList: function(id,type){
        var data = new Object();
        data.portalType = portalType;
        data.id = id;
        data.type=type;
        data.callback = "JSON_CALLBACK";
        return dao.https.jsonp('client/prjProgCheck/getPrjProgCheckPhotoList', data);
      }
    }
  }
  service.$inject=['dao'];
  return service;
})
