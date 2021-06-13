/**
 * Created by zzr on 2019/06/01.
 */
define([],function(){
	'use strict';
	function directive(dao){
	  return {
	    restrict: 'E',
	    scope:{
	    	showvalue: "@showvalue",
	    	showlabel: "@showlabel",
	    	callback: "&callback",
	    	placeholder: "@placeholder",
	    	templateurl: "@templateurl",
	    	animation: "@animation",
	    	dataurl: "@dataurl",
	    	showlabelvar: "@showlabelvar",
	    	showvaluevar: "@showvaluevar",
	    	conditions: "=conditions"
	    },
	    replace:true,
	    transclude: true,
	    template: " <input type='text' ng-model='show' placeholder='{{placeholder}}' readonly style='background: white' ng-click='openModal()'/> ",
		link: function(scope, element, attrs, controller){

			scope.show = "";
			
			scope.setShow = function(value, label){
				scope.show = "";
				if(value){
					scope.show = value;
				}
				if(scope.show && typeof scope.show == "string" && scope.show.length > 0){
					scope.show += "|";
				}
				if(label){
					scope.show += label;
				}
			};
			
			scope.setShow(scope.showvalue, scope.showlabel);
			
			scope.emit = function(data){
				if(scope.callback){
					scope.callback({
						data: data
					});
				}				
			};
			
			scope.data = {};
			scope.datas = [];
			scope.pageNo = 1;
			scope.pageSize = 10;
			scope.hasNext = false;
			
			scope.init = function(){
				scope.datas = [];
				scope.pageNo = 1;
				scope.pageSize = 10;
				scope.hasNext = false;
			}
			
			scope.search = function(data){
				scope.init();
				scope.getDatas(data);
			}
			
			scope.getDatas = function(data, moreFlag){
				if(!scope.dataurl || scope.dataurl == ""){
					return;
				}
				if(!data){
					data = {};
				}
				for(var key in scope.conditions){
					data[key] = scope.conditions[key];
				}
				data.pageNo = scope.pageNo;
				data.pageSize = scope.pageSize;
				data.callback = "JSON_CALLBACK";
				dao.https.jsonp(scope.dataurl, data).success(function(ret){
					if(ret.success && ret.pageNo == scope.pageNo){
						scope.datas = scope.datas.concat(ret.datas);
						scope.pageNo++;
						scope.hasNext = ret.hasNext;
					}
					if(moreFlag){
						scope.$broadcast('scroll.infiniteScrollComplete');
					}
				}).error(function(){
					if(moreFlag){
						scope.$broadcast('scroll.infiniteScrollComplete');
					}
				});
			}
			
			dao.modal.init(scope, scope.templateurl, scope.animation, 
				function(){
					scope.init();
					scope.modal.show();
				}, 
				function(ret){
					if(ret){
						scope.setShow(ret[scope.showvaluevar], ret[scope.showlabelvar]);
						scope.emit({
							data: ret
						});
					}
		            scope.modal.hide();
				}
			);
			
			scope.$watch("showvalue", function(newValue, oldValue){
				if(newValue != oldValue){
					scope.showvalue = newValue;
					scope.setShow(scope.showvalue, scope.showlabel);
				}
			});
			
			scope.$watch("showlabel", function(newValue, oldValue){
				if(newValue != oldValue){
					scope.showlabel = newValue;
					scope.setShow(scope.showvalue, scope.showlabel);
				}
			});
		}
	  };
	}
	return directive;
})
