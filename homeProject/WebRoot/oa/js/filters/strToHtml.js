/**
 * Created by zzr on 2019/5/24.
 */
define([], function () {
	'use strict';
	function filter($sce){
		return function(text){
			console.log(text);
			console.log($sce);
			return $sce.trustAsHtml(text);
		}
	}
	return filter;
})
