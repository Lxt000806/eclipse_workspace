/**
 * Created by zzr on 2019/06/01.
 */
define([],function(){
	'use strict';
	function directive(dao){
	  return {
	    restrict: 'A',
	    scope: {
	    	inputfocus: "=inputfocus"
	    },
		link: function(scope, element, attrs){
			element.on("keydown", function(){
				window.focus();
				this.focus();
			});
		}
	  };
	}
	return directive;
})
