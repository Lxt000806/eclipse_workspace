/**
 * Created by Administrator on 2015/12/13.
 */
var pageSize = 10;
var portalType = 1;
var gb = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
var CityList = [{code: 'fz', desr: '福州', path: "https://app.u-om.com:20002/"},
  {code: 'xm', desr: '厦门', path: "http://xmapp.u-om.com:20003/"},
  {code: 'cl', desr: '长乐', path: "http://clapp.u-om.com:20003/"},
  {code: 'kf', desr: '开发', path: "http://192.168.0.225:8080/"},
  {code: 'cs', desr: '测试', path: "https://app.u-om.com:20002/cs/"}];
var basePath = "";
for (var i = 0; i < CityList.length; i++) {
  if (localStorage.city == CityList[i].code) {
    basePath = CityList[i].path;
    break;
  }
}
function doCameraSomeThing($scope,dao) {
  //        var ft = new FileTransfer();
//
//        ft.upload(imageURI,encodeURI(basePath+"client/prjProg/prjProgUploadApp"), win, fail,
//            options);
  $scope.takePhoto = function () {
    dao.photo.getPicture({quality: 50,targetWidth: 719,targetHeight: 1280},function(imageData){
      var params = new Object();
      params.custCode = $scope.items[0].custCode;
      params.prjItem = $scope.items[0].prjItem;
      dao.photo.upload(imageData,params,"client/prjProg/prjProgUploadApp",function win(r) {
//            $("<img src="+imageURI+">").css({"width":"200px","height":"200px"}).on('click',function(){
//                $("#rightDispla").show();
//                $("#tp").attr("src",imageURI);
//                }).appendTo(myImage);
        var response = angular.fromJson(r.response);
        var json = {};
        json.src = imageData;
        json.photoName=response.photoNameList[0];
        $scope.photoList.push(json);
        $scope.photoSize++;
//                    var myImage= $("<div></div>")
////                    myImage.attr('id','myImage');
//                    myImage.addClass("hscroller-card")
//                $("<img src="+imageURI+">").addClass("hscroller-img").appendTo(myImage);
//                $("<div></div>").addClass("hscroller-label").appendTo(myImage);
//                myImage.appendTo("#photo");
//               // $("<div></div>").addClass("hscroller-label").

//        alert("Code = " + r.responseCode);//状态码
//        alert("Response = " + r.response);//返回内容
//        alert("Sent = " + r.bytesSent)//传送子节大小

      }, function fail(error) {
        alert("上传失败,请重新上传");
//        alert("An error has occurred: Code = " + error.code);
//        alert("upload error source " + error.source);
//        alert("upload error target " + error.target)

      })
    });
  }
}
function showLoading(obj) {
  $ionicLoading = obj;
  $ionicLoading.show({
    noBackdrop: true,
    duration: 3000
  });
}
function definePromise(obj) {
  obj.success = function (fn) {
    obj.then(fn);
    return obj;
  }
  obj.error = function (fn) {
    obj.then(null, fn);
    return obj;
  }
}
function checkUpdate(dao,$cordovaFileTransfer, $cordovaFileOpener2, version,suceessFn) {
  var data = new Object();
  data.portalType = device.platform == 'Android' ? 1 : 0;
  data.versionNo = version;
  data.name=data.portalType==1?'client-android':'client-ios';
  data.callback = "JSON_CALLBACK";
  var url = basePath + "client/version/checkVersion";
  if(suceessFn){
    dao.https.jsonp(url,data,suceessFn);
    return;
  }
  dao.https.jsonp(url,data,function (response) {
    //存在新版本
    if (response.existNew) {
      dao.popup.confirm(response.downLoadRemark,function(){
        //升级
        window.open(response.downLoadUrl, '_system');
        if (response.isForce) {
          $ionicPopup.alert({
            title: '提示',
            template: '请更新到最新版本',
            okText: '确定'
          }).then(function () {
            // ionic.Platform.exitApp();
            navigator.app.exitApp();
          })
        }
      },function(){
        //不升级
        //必须升级
        if (response.isForce) {
          //退出程序
          //ionic.Platform.exitApp();
          navigator.app.exitApp();
        } else {
          //非强制升级
          return;
        }
      },'有家装饰已有新版本' + response.versionNo,'查看更新','暂不更新');
    }
  })
}
//获取材料类型1
function getItemType1List($rootScope, sitesService) {
  if (!$rootScope.itemType1List) {
    sitesService.getItemType1List().success(function (data) {
      $rootScope.itemType1List = data.datas;
      //$rootScope.itemType1List.splice(0,0,{"code":"","descr":""});
    })
  }
}
//获取材料类型2
function getItemType2List($rootScope, sitesService, itemType1) {
  if (!$rootScope.itemType2List) {
    $rootScope.itemType2List = {};
  }
  if (!itemType1) return;
  if (!$rootScope.itemType2List[itemType1]) {
    sitesService.getItemType2List(itemType1).success(function (data) {
      $rootScope.itemType2List[itemType1] = data.datas;
    })
  }
}
//根据ID获取系统代码列表
function getXtdmListById($rootScope, sitesService, id, unShowId) {
  if (!$rootScope.xtdmList) {
    $rootScope.xtdmList = {};
  }
  if (!$rootScope.xtdmList[id]) {
    sitesService.getXtdmListById(id).success(function (data) {
      if (data && data.datas){
        $rootScope.xtdmList[id] = data.datas;
        if (unShowId){
          var index = getIndexInArrayByKey($rootScope.xtdmList[id],unShowId,'cbm');
          if (index>=0){
            $rootScope.xtdmList[id].splice(index,1);
          }
        }
      }
    })
  }
}
//获取任务类型列表
function getJobTypeList($rootScope, prjJobService, itemType1) {
    prjJobService.getJobTypeList(itemType1).success(function (data) {
      $rootScope.jobTypeList = data.datas;
    });
}
//转换日期格式为yyyy-MM-dd
function transDate(val) {
  var year = val.getFullYear();
  var month = val.getMonth() + 1 < 10 ? '0' + (val.getMonth() + 1) : val.getMonth() + 1;
  var day = val.getDate() < 10 ? '0' + val.getDate() : val.getDate();
  var date = year + '-' + month + '-' + day;
  return date;
}
//计算时间差
function GetDateDiff(startTime, endTime, diffType) {
//将xxxx-xx-xx的时间格式，转换为 xxxx/xx/xx的格式
  startTime = startTime.replace(/\-/g, "/");
  endTime = endTime.replace(/\-/g, "/");

//将计算间隔类性字符转换为小写
  diffType = diffType.toLowerCase();
  var sTime = new Date(startTime); //开始时间
  var eTime = new Date(endTime); //结束时间
//作为除数的数字
  var divNum = 1;
  switch (diffType) {
    case"second":
      divNum = 1000;
      break;
    case"minute":
      divNum = 1000 * 60;
      break;
    case"hour":
      divNum = 1000 * 3600;
      break;
    case"day":
      divNum = 1000 * 3600 * 24;
      break;
    default:
      break;
  }
  return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}
//截取选定字符之间的字符(不包括2边)
function getNeedStr(str, str1, str2) {
  var i = str.indexOf(str1) + 1;
  if (str2 == '0') {
    return str.substring(0, i - 1);
  }
  if (str2 != null) {
    var j = str.indexOf(str2);
    return str.substring(i, j);
  } else {
    return str.substring(i);
  }
}
//判断第一个匹配元素在数组中的下标
function getIndexInArray(arr, ele) {
  for (var i = 0; i < arr.length; i++) {
    if (arr[i] == ele) {
      return i;
    }
  }
  return -1;
}
function getIndexInArrayByKey(arr, ele, key) {
  if (!key){return -1}
  if (!arr instanceof Array){return -1}
  if (typeof ele == 'object'){
    for (var i = 0; i < arr.length; i++) {
      if (arr[i][key] == ele[key]) {
        return i;
      }
    }
  }else{
    for (var i = 0; i < arr.length; i++) {
      if (arr[i][key] == ele) {
        return i;
      }
    }
  }
  return -1;
}
//过滤左右2边的空格
function trim(str) {
  return str.replace(/(^\s*)|(\s*$)/g, "");
}
//删除json对象属性
function deleteScope(obj) {
  for (var prop in obj) {
    if (prop.substring(0, 1) !== '$' && typeof (obj[prop]) != 'function') {
      delete obj[prop];
    }
  }
}
function decbase64(a) {
  var b = "",
    c,
    d,
    e = "",
    g,
    i = "",
    j = 0;
  g = /[^A-Za-z0-9\+\/\=]/g;
  if (!a || g.exec(a)) return a;
  a = a.replace(/[^A-Za-z0-9\+\/\=]/g, "");
  do{
    //每四位截取一次，比如第一次为MTE2
    c = gb.indexOf(a.charAt(j++));
    d = gb.indexOf(a.charAt(j++));
    g = gb.indexOf(a.charAt(j++));
    i = gb.indexOf(a.charAt(j++));
    // console.log(j+'::'+c+':'+d+':'+g+':'+i);
    c = c << 2 | d >> 4;
    d = (d & 15) << 4 | g >> 2;
    e = (g & 3) << 6 | i;
    // console.log(j+'::'+c+':'+d+':'+e);
    b += String.fromCharCode(c);
    // console.log(j+'::'+c+':'+b);
    64 != g && (b += String.fromCharCode(d));
    64 != i && (b += String.fromCharCode(e));
  }while (j < a.length);
  return b
}
//全局变量
var globalVar={};
//退货申请全局变量
globalVar.itemReturn={};
globalVar.itemReturn.viewTitle = "新增退货申请";
globalVar.itemReturn.xmlData=[];
globalVar.itemReturn.searchData={};
globalVar.itemReturn.data={};
//项目任务全局变量
globalVar.prjJob={};
globalVar.prjJob.viewTitle = "新增项目任务";
globalVar.prjJob.searchData={};
globalVar.prjJob.data={};

