/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
  'use strict';
  function  dao($ionicPopup, $ionicModal, $http, $q, $ionicLoading,$cordovaCamera,$cordovaFileTransfer,$rootScope,$state,$cordovaImagePicker) {
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
      show: function (content, title, scope,buttons) {
        var opt = {
          title: "提示",
          template: content
        };
        if (title) angular.extend(opt, {title: title});
        if (scope) angular.extend(opt, {scope: scope});
        if(buttons) angular.extend(opt,{buttons:buttons});
        return $ionicPopup.show(opt);
      }
    };
    //模版
    var modal = {
      init: function ($scope, url, animation, openFn, closeFn,modalName,modalOpen,modalClose) {
        $ionicModal.fromTemplateUrl(url, {
          scope: $scope,
          animation: animation,
          backdropClickToClose:false
        }).then(function (modal) {
          if(modalName) $scope[modalName]=modal;
          else $scope.modal = modal;
        });
        if(modalName){
          if(openFn) $scope[modalOpen]=openFn;
          else $scope[modalOpen]=function(){
            $scope[modalName].show();
          }
          if(closeFn) $scope[modalClose]=closeFn;
          else $scope[modalClose]=function(){
            $scope[modalName].hide();
          }
          return;
        }else{
			if (openFn) $scope.openModal = openFn;
			else $scope.openModal = function () {
				$scope.modal.show();
			}
			if (closeFn) $scope.closeModal = closeFn;
			else $scope.closeModal = function () {
				$scope.modal.hide();
			}
        }

        //当我们用完模型时，清除它！
        $scope.$on('$destroy', function() {
          if($scope[modalName]){
            $scope[modalName].remove();
          }
        });
      }
    };
    //http请求
    var https = {
      ajax: function (method, url, param, data, success, fail, isLoading) {
        if (url.indexOf("https://") < 0 && url.indexOf("http://") < 0) {
          url = basePath + url;
        }
        if(!data){
          data={};
        }
        if(!param){
          param={};
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
    var photo= {
      initPhotoOptions: function (opt) {
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
        return angular.extend(photoOptions, opt);
      },
      getPicture: function (opt, success, fail) {
        var photoOptions = this.initPhotoOptions(opt);
        $cordovaCamera.getPicture(photoOptions).then(function (imageData) {
          if (success) success(imageData);
          //image.src = "data:image/jpeg;base64," + imageData;
        }, function (error) {
          if (fail) fail(error);
        });
      },
      imagePicker: function (opt, success, fail) {
        $cordovaImagePicker.getPictures(opt).then(function (results) {
          if (success) success(results);
        }, function (error) {
          if (fail) fail(error);
        });
      },
      initUploadOptions: function (fileKey, fileName, mimeType, chunkedMode, params) {
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
      upload: function (url, params, uri, success, fail,pickerPhotoFlag,cur,len) {
        var fileName = url.substr(url.lastIndexOf('/') + 1);
        var options = this.initUploadOptions("file", fileName, "image/jpeg", false, params);
        if(pickerPhotoFlag == true){
          if(cur == 0) loading.load("<ion-spinner class='spinner-light' icon='ios'></ion-spinner><br/><span style='color: white'><br/>图片加载中...");
        }else{
          showLoading($ionicLoading);
        }
        $cordovaFileTransfer.upload(encodeURI(basePath + uri), url, options).then(
          function win(r) {
            if (success) success(r);
            if(pickerPhotoFlag == true){
              if(cur == len-1) loading.hide();
            }else{
              $ionicLoading.hide();
            }
          }, function fail(error) {
            if (fail) fail(error);
            if(pickerPhotoFlag == true){
              if(cur == len-1) loading.hide();
            }else{
              $ionicLoading.hide();
            }
          });
      },
      takePhoto: function (obj, uri, params,opt) {
        var photo=this;
        if (opt) {
          var opt = opt;
        } else {
          var opt = {quality: 50, targetWidth: 719, targetHeight: 1280};
        }
        photo.getPicture(opt, function (imageData) {
          photo.upload(imageData, params, uri, function (r) {
            var response = angular.fromJson(r.response);
            var json = {};
            json.src = imageData;
            json.photoName = response.photoNameList[0];
            //保存已选照片的url,和名字
           obj.push(json);
          }, function (error) {
            alert("上传失败,请重新上传");
          })
        })
      },
      pickerPhoto: function (obj, uri, params,opt) {
        var photo=this;
        if (opt) {
          var opt = opt;
        } else {
          var opt = {maxImages: 9, width: 719, height: 1280, quality: 50};
        }
        photo.imagePicker(opt, function (results) {
          var cur=0,len=results.length;
          photo.circleUpload(cur,len,results,params, uri,obj);
        });
      },
      circleUpload:function(cur,len,results,params, uri,obj){
        if(cur==len) return;
        this.upload(results[cur], params, uri, function (r) {
          var response = angular.fromJson(r.response);
          var json = {};
          json.src = response.photoPathList[0];
          json.photoName = response.photoNameList[0];
          //保存已选照片的url,和名字
          obj.push(json);
          cur++;
          photo.circleUpload(cur,len,results,params, uri,obj);
        }, function (error) {
          alert("上传失败,请重新上传");
        },true,cur,len)
      }
    }
    var loading={
      templateStyle:function(){
        return  "<style> .loading-container .loading { padding: 20px;border-radius: 5px;background-color: rgba(0, 0, 0, 0.7);color: #fff;text-align: center;text-overflow: ellipsis;font-size: 15px;}</style>";
      },
      load:function(content){
        $ionicLoading.show({
          template:this.templateStyle()+content
        });
      },
      hide:function(){
        $ionicLoading.hide();
      },
      alert:function(content){
        $ionicLoading.show({
          template:this.templateStyle()+content+'&nbsp<i class="icon ion-alert"></i>',
          duration: 1000
        });
      },
      fail:function(content){
        $ionicLoading.show({
          template:this.templateStyle()+content+'&nbsp<i class="icon ion-android-close"></i>',
          duration: 1000
        });
      },
      success:function(content){
        $ionicLoading.show({
          template:this.templateStyle()+content+'&nbsp<i class="icon ion-android-done"></i>',
          duration: 1000
        });
      }
    }
    //返回调用
    return {
      popup: popup,
      modal: modal,
      https: https,
      datepicker: datepicker,
      photo:photo,
      loading:loading
    };
  }
  dao.$inject=['$ionicPopup', '$ionicModal', '$http', '$q', '$ionicLoading','$cordovaCamera','$cordovaFileTransfer','$rootScope','$state','$cordovaImagePicker'];
  return dao;
})
