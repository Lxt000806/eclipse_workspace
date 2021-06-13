/**
 * Created by zzr on 2019/06/01.
 */
define([],function(){
	'use strict';
	function directive(dao){
	  return {
	    restrict: 'A',
	    scope: {
	    	textareaauto: "=textareaauto"
	    },
		link: function(scope, element, attrs){
			element.bind("input", function(){
			    this.style.height = 'auto';  
			    this.style.height = this.scrollHeight + "px";  
			})
			element.on("keydown", function(){
				window.focus();
				this.focus();
			});
			scope.$watch("textareaauto", function(newValue, oldValue){
				element[0].innerHTML = newValue;
				element.triggerHandler("input");
			})
			
		}
	  };
	}
	return directive;
})
