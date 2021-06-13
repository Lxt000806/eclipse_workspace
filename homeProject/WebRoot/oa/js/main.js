require.config({
	paths: {
		"jquery": "plugins/jquery-1.11.3.min",
		"ionic.bundle":"../lib/ionic/js/ionic.bundle",
		'map':"plugins/map",
		"constant":"constant",
	}
})
require(['jquery','ionic.bundle','map','constant'],function(){
  require.config({
    deps: ['bootstrap']
  })
})
