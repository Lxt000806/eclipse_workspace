/**
 * Created by Administrator on 2016/4/18.
 */
define([],function(){
  'use strict';
  function  dao($ionicPopup, $ionicModal, $http, $q, $ionicLoading,$cordovaCamera,$cordovaFileTransfer,$rootScope,$state,$timeout) {
    //提示框
    var popup = {
      alert: function (content, callback, title) {
        var opt = {
          title: '提示',
          template: content,
          okText: "确定"
        };
        if (title) angular.extend(opt, {title: title});
        $ionicPopup.alert(opt).then(function (res) {
          if (callback) callback();
        });
      },
      confirm: function (content, yesFn, noFn, title,ok,cancel) {
        var opt = {
          title: '提示',
          template: content,
          okText: "确定",
          cancelText: "取消"
        }
        if (title) angular.extend(opt, {title: title});
        if(ok) angular.extend(opt,{okText:ok});
        if(cancel) angular.extend(opt,{cancelText:cancel});
        $ionicPopup.confirm(opt).then(function (res) {
          if (res) {
            yesFn();
          } else {
            noFn();
          }
        });
      },
      show: function (content, title, scope) {
        var opt = {
          title: "提示",
          template: content
        };
        if (title) angular.extend(opt, {title: title});
        if (scope) angular.extend(opt, {scope: scope});
        return $ionicPopup.show(opt);
      }
    };
    //模版
    var modal = {
      init: function ($scope, url, animation, openFn, closeFn) {
        $ionicModal.fromTemplateUrl(url, {
          scope: $scope,
          animation: animation,
          backdropClickToClose:false
        }).then(function (modal) {
          $scope.modal = modal;
        });
        if (openFn) {
          $scope.openModal = openFn;
        } else {
          $scope.openModal = function () {
            $scope.modal.show();
          }
        }

        if (closeFn) {
          $scope.closeModal = closeFn;
        } else {
          $scope.closeModal = function () {
            $scope.modal.hide();
          }
        }
      },
      //简单搜索
      simpleInit:function($scope,mapService){
        $ionicModal.fromTemplateUrl('templates/modals/address.html', {// modal窗口选项
          scope: $scope,
          animation: 'slide-in-up',
          backdropClickToClose:false
        }).then(function (modal) {
          $scope.modal = modal;
        });
        $scope.openModal = function(flag) {
          if (!flag){
            $scope.modal.show();
          }
        };
        $scope.closeModal = function(address,code) {
          if(address){
            if (!$scope.data){
              $scope.data={};
            }
            $scope.data.address = address;
            $scope.data.code = code;
          }
          $scope.modal.hide();
        };
        //当我们用完模型时，清除它！
        $scope.$on('$destroy', function() {
          $scope.modal.remove();
        });
        $scope.searchAddresses = function (searchName) {
          mapService.getHouses(searchName).success(function (data) {
            $scope.addresses = data.datas;
          })
        }
      },
      //任务处理人搜索，code任务类型编号
      jobDealManInit:function($scope,prjJobService,code){
        $ionicModal.fromTemplateUrl('templates/modals/jobDealMan.html', {// modal窗口选项
          scope: $scope,
          animation: 'slide-in-up',
          backdropClickToClose:false
        }).then(function (modal) {
          $scope.modal_jobDealMan = modal;
        });
        $scope.openModal_jobDealMan = function(flag) {
          if (!flag){
            $scope.modal_jobDealMan.show();
          }
        };
        $scope.closeModal_jobDealMan = function(jobDealManDescr,jobDealMan) {
          if(jobDealManDescr){
            if (!$scope.data){
              $scope.data={};
            }
            $scope.data.jobDealManDescr = jobDealManDescr;
            $scope.data.jobDealMan = jobDealMan;
          }
          $scope.modal_jobDealMan.hide();
        };
        //当我们用完模型时，清除它！
        $scope.$on('$destroy', function() {
          $scope.modal_jobDealMan.remove();
        });
        $scope.jobDealManFlag = false;
        $scope.jobDealManPageNow = 1;
        $scope.jobDealMans = [];
        $scope.jobDealMans_Number = [];
        $scope.jobDealMan_Descr = "";
        $scope.jobDealMan_Code = "";
        $scope.searchJobDealMan = function (searchJobDealManDescr) {
          if ($scope.jobDealMan_Code == code && $scope.jobDealMan_Descr == searchJobDealManDescr){
            //alert("==="+$scope.jobDealManPageNow);
          }else{
            $scope.jobDealManFlag = false;
            $scope.jobDealManPageNow = 1;
            $scope.jobDealMans = [];
            $scope.jobDealMans_Number = [];
            $scope.jobDealMan_Descr = searchJobDealManDescr;
            $scope.jobDealMan_Code = code;
          }
          prjJobService.getPrjJobEmployeeList($scope.jobDealManPageNow,code,searchJobDealManDescr).success(function (data) {
            if (data && data.datas) {
              var datas = data.datas;
              for (var i = 0; i < datas.length; i++) {
                if ($scope.jobDealMans_Number.indexOf(datas[i].number)==-1){
                  $scope.jobDealMans_Number.push(datas[i].number);
                  $scope.jobDealMans.push({
                    "jobDealMan": datas[i].number,
                    "jobDealManDescr": datas[i].nameChi,
                    "phone": datas[i].phone
                  });
                }
              }
              $scope.jobDealManFlag = data.hasNext;
              $scope.jobDealManPageNow++;
            }
            $timeout(
              function () {
                $scope.$broadcast('scroll.refreshComplete');
                $scope.$broadcast('scroll.infiniteScrollComplete');
              }, 100
            );
          })
        }
      }
    };
    //http请求
    var https = {
      ajax: function (method, url, param, data, success, fail, isLoading) {
        if($state.current.name!='registerUser'&&$state.current.name!='resetPassword'&&$state.current.name!='login'){
               if(!localStorage){
                 $state.go('login');
               }
        }
        if (url.indexOf("https://") < 0 && url.indexOf("http://") < 0) {
          url = basePath + url;
        }
        var opts = {
          url: url,
          method: method,
          params: param,
          data: data
        };
        angular.extend(opts, {
          headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept': 'application/json'
          }
        });
        if (isLoading)  showLoading($ionicLoading);
        var deferred = $q.defer(), promise = deferred.promise;
        $http(opts).success(function (data, status, headers, config, statusText) {
          if (success)  success(data);
          deferred.resolve(data);
          if (isLoading)  $ionicLoading.hide();
        }).error(function (data, status, headers, config, statusText) {
          if (fail) fail(data);
          deferred.reject(data);
          if (isLoading)  $ionicLoading.hide();
        });
        definePromise(promise);
        return promise;
      },
      jsonp: function (url, param, success, fail, isLoading) {
        return https.ajax("JSONP", url, param, {}, success, fail, isLoading);
      },
      post: function (url, data, success, fail, isLoading) {
        return https.ajax("POST", url, {}, data, success, fail, isLoading);
      }
    };
    //日期组件
    var datepicker = {
      init: function ($scope, weekDaysList, callback) {
        // 禁用日期
//        var disabledDates = [
//            new Date(1437719836326),
//            new Date(),
//            new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
//            new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
//            new Date("08-14-2015"), //Short format
//            new Date(1439676000000) //UNIX format
//        ];
        var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "10月", "11月", "12月"];
        var datepickerObject = {
          titleLabel: '请选择时间',  //Optional
          todayLabel: '当前日期',  //Optional
          closeLabel: '取消',  //Optional
          setLabel: '确认',  //Optional
          setButtonType: 'button-assertive',  //Optional
          todayButtonType: 'button-assertive',  //Optional
          closeButtonType: 'button-assertive',  //Optional
          inputDate: new Date(),  //用于html页面显示
          mondayFirst: true,  //Optional
          //  disabledDates:'empty array', //disabledDates
          //Optional
          monthList: monthList,  //Optional
          weekDaysList: ["周六", "周日", "周一", "周二", "周三", "周四", "周五"],//全局会跳周数,
          templateType: 'modal', //Optional
          showTodayButton: 'true', //Optional
          modalHeaderColor: 'bar-positive', //Optional
          modalFooterColor: 'bar-positive', //Optional
//        from: new Date(2012, 8, 2), //九月2号到九月25号可选
//        to: new Date(2018, 8, 25),
          callback: callback,  //Mandatory
          dateFormat: 'yyyy-MM-dd', //Optional
          closeOnSelect: false //Optional
        };
        if (weekDaysList) angular.extend(datepickerObject, {weekDaysList: weekDaysList});
        $scope.datepickerObject = datepickerObject;
      }
    }
    //照片
    var photo={
      initPhotoOptions:function(opt){
         var photoOptions = {
          //这些参数可能要配合着使用，比如选择了sourcetype是0，destinationtype要相应的设置
          //quality: 50,  //相片质量0-100
          destinationType: 1, //返回类型：DATA_URL= 0，返回作为 base64 編碼字串。 FILE_URI=1，返回影像档的 URI。NATIVE_URI=2，返回图像本机URI (例如，資產庫)
          sourceType: 1, //从哪里选择图片：PHOTOLIBRARY=0，相机拍照=1，SAVEDPHOTOALBUM=2。0和1其实都是本地图库
          allowEdit: false,                                       //在选择之前允许修改截图
          encodingType: 0,//保存的图片格式： JPEG = 0, PNG = 1
          //targetWidth: 719,//照片宽度
          //targetHeight: 1280,//照片高度
          mediaType: 0,//可选媒体类型：圖片=0，只允许选择图片將返回指定DestinationType的参数。 視頻格式=1，允许选择视频，最终返回 FILE_URI。ALLMEDIA= 2，允许所有媒体类型的选择。
          cameraDirection: 0,//枪后摄像头类型：Back= 0,Front-facing = 1
          // popoverOptions: CameraPopoverOptions,
          saveToPhotoAlbum: true //保存进手机相册
        };
        return angular.extend(photoOptions,opt);
      },
      getPicture:function(opt,success,fail){
        var photoOptions=this.initPhotoOptions(opt);
        $cordovaCamera.getPicture(photoOptions).then(function (imageData) {
         if(success) success(imageData);
          //image.src = "data:image/jpeg;base64," + imageData;
        },function(error){
          if(fail) fail(error);
        });
      },
      initUploadOptions:function(fileKey, fileName, mimeType, chunkedMode, params){
        var options = new FileUploadOptions();
        options.fileKey = fileKey;
        options.fileName = fileName;
        options.mimeType = mimeType;
        if (params) {
          options.params = params;
        } else {
          options.params = {};
        }
        options.chunkedMode = chunkedMode;
        return options;
      },
      upload:function(url,params,uri,success,fail){
        var fileName = url.substr(url.lastIndexOf('/') + 1);
        var options = this.initUploadOptions("file", fileName, "image/jpeg", false, params);
        showLoading($ionicLoading);
        $cordovaFileTransfer.upload(encodeURI(basePath + uri), url, options).then(
          function win(r) {
            if(success) success(r);
            $ionicLoading.hide();
          }, function fail(error) {
            if(fail) fail(error);
            $ionicLoading.hide();
          });
      }
    }
    //返回调用
    return {
      popup: popup,
      modal: modal,
      https: https,
      datepicker: datepicker,
      photo:photo
    };
  }
  dao.$inject=['$ionicPopup', '$ionicModal', '$http', '$q', '$ionicLoading','$cordovaCamera','$cordovaFileTransfer','$rootScope','$state','$timeout'];
  return dao;
})
