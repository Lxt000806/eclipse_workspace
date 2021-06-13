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
	    	placeholder: "@placeholder",
	    	conditions: "=conditions",
	    	elementid: "@elementid"
	    },
	    replace:true,
	    template: " <div class='item item-input'> "
	    	    + "		<span class='input-label'>{{label}}</span> "
				+ "		<infolist showvalue='{{showvalue}}' showlabel='{{showlabel}}' placeholder='{{placeholder}}' "
				+ " 			  showvaluevar='number' showlabelvar='nameChi' "
				+ "				  callback='infoListCallback(data)' templateurl='templates/modals/employee.html' dataurl='client/employee/goJqGridEmployee' "
				+ "				  conditions='conditions'></infolist> "
				+ " </div> ",
		link: function(scope, element, attrs, controller){
			scope.infoListCallback = function(data){
				if(data){
					if(scope.idvar && scope.idvar != ""){
						scope.id = data.data[scope.idvar];
					}
					if(scope.elementid){
						data.elementid = scope.elementid;
					}
				}
				if(scope.callback){
					scope.callback({
						data:data
					});
				}
			};
		}
	  };
	}
	return directive;
})
