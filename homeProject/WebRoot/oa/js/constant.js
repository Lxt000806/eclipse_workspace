/**
 * Created by zzr on 2019/5/24.
 */
var basePath = "";

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
