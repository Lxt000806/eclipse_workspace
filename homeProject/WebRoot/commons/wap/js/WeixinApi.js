var WeixinApi = (function () {

  function weixinShare(theData, callbacks) {
  	callbacks = callbacks || {};
  	       WeixinJSBridge.on('menu:general:share', function(argv){
                var scene = 0;
                switch(argv.shareTo){
                    case 'friend'  : scene = 1; break;
                    case 'timeline': scene = 2; break;
                    case 'weibo'   : scene = 3; break;
                }
                    argv.generalShare({
                "img_url":theData.imgUrl,
                "img_width":"640",
                "img_height":"640",
                "link":theData.link,
                "desc":theData.title,
                "title":theData.desc 
                    }, function(resp){
                    	switch (resp.err_msg) {
                    case 'general_share:cancel':
                        callbacks.cancel && callbacks.cancel(scene);
                        break;
                    case 'general_share:fail':
                        callbacks.fail && callbacks.fail(scene);
                        break;
                    case 'general_share:ok':
                        callbacks.confirm && callbacks.confirm(scene);
                        break;
                }
                    });
            });
    }

    function weixinShareTimeline(data, callbacks) {
        callbacks = callbacks || {};
        var shareTimeline = function (theData) {
        	  callbacks.confirm && callbacks.confirm("android旧版本提前回调");
            WeixinJSBridge.invoke('shareTimeline', {
                "img_url":theData.imgUrl,
                "img_width":"120",
                "img_height":"120",
                "link":theData.link,
                "desc":theData.title,
                "title":theData.desc 
            }, function (resp) {

                switch (resp.err_msg) {

                    case 'share_timeline:cancel':
                        callbacks.cancel && callbacks.cancel(resp.err_msg);
                        break;

                    case 'share_timeline:fail':
                        callbacks.fail && callbacks.fail(resp.err_msg);
                        break;

                    case 'share_timeline:confirm':
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                    case 'share_timeline:ok':
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                }
                callbacks.all && callbacks.all(resp.err_msg);
            });
        };
        WeixinJSBridge.on('menu:share:timeline', function (argv) {
            if (callbacks.async && callbacks.ready) {
                if(!callbacks.__dataLoadedFuncInited) {
                    var loadedCb = callbacks.dataLoaded || new Function();
                    callbacks.dataLoaded = function (newData) {
                        loadedCb(newData);
                        shareTimeline(newData);
                    };
                    callbacks.__dataLoadedFuncInited = true;
                }
                callbacks.ready && callbacks.ready(argv);
            } else {
                callbacks.ready && callbacks.ready(argv);
                shareTimeline(data);
            }
        });
    }

    function weixinSendAppMessage(data, callbacks) {
        callbacks = callbacks || {};
        var sendAppMessage = function (theData,argv) {
            WeixinJSBridge.invoke('sendAppMessage', {
                "appid":theData.appId ? theData.appId : '',
                "img_url":theData.imgUrl,
                "link":theData.link,
                "desc":theData.desc,
                "title":theData.title,
                "img_width":"120",
                "img_height":"120"
            }, function (resp) {
                switch (resp.err_msg) {
                    case 'send_app_msg:cancel':
                        callbacks.cancel && callbacks.cancel(resp.err_msg);
                        break;
                    case 'send_app_msg:fail':
                        callbacks.fail && callbacks.fail(resp.err_msg);
                        break;
                    case 'send_app_msg:confirm':
                    if(argv.scene=="favorite"){return true;}
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                    case 'send_app_msg:ok':
                    if(argv.scene=="favorite"){return true;}
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                }

                callbacks.all && callbacks.all(resp.err_msg);
            });
        };
        WeixinJSBridge.on('menu:share:appmessage', function (argv) {       	
            if (callbacks.async && callbacks.ready) {
                if(!callbacks.__dataLoadedFuncInited) {
                    var loadedCb = callbacks.dataLoaded || new Function();
                    callbacks.dataLoaded = function (newData) {
                        loadedCb(newData);
                        sendAppMessage(newData,argv);
                    };
                    callbacks.__dataLoadedFuncInited = true;
                }
                callbacks.ready && callbacks.ready(argv);
            } else {
                callbacks.ready && callbacks.ready(argv);
                sendAppMessage(data,argv);
            }
        });
    }

    function weixinShareWeibo(data, callbacks) {
        callbacks = callbacks || {};
        var shareWeibo = function (theData) {
            WeixinJSBridge.invoke('shareWeibo', {
            		"content" : theData.title,
                "url"     : theData.link
            }, function (resp) {

                switch (resp.err_msg) {
                    case 'share_weibo:cancel':
                        callbacks.cancel && callbacks.cancel(resp.err_msg);
                        break;
                    case 'share_weibo:fail':
                        callbacks.fail && callbacks.fail(resp.err_msg);
                        break;
                    case 'share_weibo:confirm':
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                    case 'share_weibo:ok':
                        callbacks.confirm && callbacks.confirm(resp.err_msg);
                        break;
                }
                callbacks.all && callbacks.all(resp.err_msg);
            });
        };
        WeixinJSBridge.on('menu:share:weibo', function (argv) {
            if (callbacks.async && callbacks.ready) {
                if(!callbacks.__dataLoadedFuncInited) {
                    var loadedCb = callbacks.dataLoaded || new Function();
                    callbacks.dataLoaded = function (newData) {
                        loadedCb(newData);
                        shareWeibo(newData);
                    };
                    callbacks.__dataLoadedFuncInited = true;
                }
                callbacks.ready && callbacks.ready(argv);
            } else {

                callbacks.ready && callbacks.ready(argv);
                shareWeibo(data);
            }
        });
    }

    function showOptionMenu() {
        WeixinJSBridge.call('showOptionMenu');
    }
    function hideOptionMenu() {
        WeixinJSBridge.call('hideOptionMenu');
    }
    function showToolbar() {
        WeixinJSBridge.call('showToolbar');
    }
    function hideToolbar() {
        WeixinJSBridge.call('hideToolbar');
    }
    function getNetworkType(callback) {
        if (callback && typeof callback == 'function') {
            WeixinJSBridge.invoke('getNetworkType', {}, function (e) {
                callback(e.err_msg);
            });
        }
    }
    function wxJsBridgeReady(readyCallback) {
        if (readyCallback && typeof readyCallback == 'function') {
            var Api = this;
            var wxReadyFunc = function () {
                readyCallback(Api);
            };
            if (typeof window.WeixinJSBridge == "undefined"){
                if (document.addEventListener) {
                    document.addEventListener('WeixinJSBridgeReady', wxReadyFunc, false);
                } else if (document.attachEvent) {
                    document.attachEvent('WeixinJSBridgeReady', wxReadyFunc);
                    document.attachEvent('onWeixinJSBridgeReady', wxReadyFunc);
                }
            }else{
                wxReadyFunc();
            }
        }
    }

    return {
        version         :"1.2",
        ready           :wxJsBridgeReady,
        share           :weixinShare,
        shareToTimeline :weixinShareTimeline,
        shareToWeibo    :weixinShareWeibo,
        shareToFriend   :weixinSendAppMessage,
        showOptionMenu  :showOptionMenu,
        hideOptionMenu  :hideOptionMenu,
        showToolbar     :showToolbar,
        hideToolbar     :hideToolbar,
        getNetworkType  :getNetworkType
    };
})();