/**
 * Created by Administrator on 2016/4/18.
 */
require.config({
  paths: {
    "jquery": "plugins/jquery-1.11.3.min",
    "ionic.bundle":"../lib/ionic/js/ionic.bundle",
    'map':"plugins/map",
    "constant":"constant",
  }
})
require(['ionic.bundle','map','jquery','constant'],function(){
  require.config({
    deps: ['bootstrap']
  })
})
