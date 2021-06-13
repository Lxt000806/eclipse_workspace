/**
 * Created by zzr on 2019/06/01.
 */
define([],function(){
	'use strict';
	function directive(){
	  return {
	    restrict: 'E',
	    scope:{
	    	id: "=id",
	    	idvar: "@idvar",
	    	label: "@label",
	    	showvalue: "@showvalue",
	    	showlabel: "@showlabel",
	    	callback: "&callback",
	    	placeholder: "@placeholder"
	    },
	    replace:true,
	    template: " <div class='item item-input'> "
	    	    + "		<span class='input-label'>{{label}}</span> "
				+ "		<infolist showvalue='{{showvalue}}' showlabel='{{showlabel}}' placeholder='{{placeholder}}' "
				+ " 			  showvaluevar='code' showlabelvar='descr' "
				+ "				  callback='infoListCallback(data)' templateurl='templates/modals/customer.html' dataurl='client/customer/goJqGridForOA'></infolist> "
				+ " </div> ",
		link: function(scope, element, attrs, controller){
			scope.infoListCallback = function(data){
				if(data){
					if(scope.idvar && scope.idvar != ""){
						scope.id = data.data[scope.idvar];
					}
				}
				if(scope.callback){
					scope.callback({
						data:data.data
					});
				}
			};
		}
	  };
	}
	return directive;
})
