/**
 * Created by zzr on 2019/06/01.
 */
define([],function(){
	'use strict';
	function directive($compile, dao){
		return {
			restrict: 'E',
			scope:{
				scope: "=scope",
				label: "@label",
				addcallback: "&addcallback",
				delcallback: "&delcallback",
				delall: "=delall",
				indexs: "=indexs",
				detailnum: "@detailnum",
				afterdetailnum: "&afterdetailnum",
			},
			replace:true,
			template: " <div style='width:100%;'><div style='width: 100%;'></div>"
					+ " <button class='button' ng-click='opElement(true, $event)' "
					+ " style='min-height:38px;height:38px;max-height:38px;line-height:38px;background-color:white;color:#387ef5;width:100%;border:0px;border-bottom:1px #ddd solid;'>增加明细</button></div>",
			link: function (scope, element, attrs) {
				if(!scope.scope){
					console.error("scope is require");
					return;
				}
				var innerHTML = "";
				if(element && element.context && element.context.innerHTML && element.context.innerHTML != ""){
					innerHTML = element.context.innerHTML.trim();
				}
				scope.count = 0;
				scope.elementCount = 0;
				scope.opElement = function(op, event){
					if(op){
						var dividerHTML = "<ion-item class='item-divider'><span>"+(scope.label && scope.label != "" ? scope.label : "明细")
										+ "("+(scope.count+1)+")</span><button class='button button-clear' ng-click='opElement(false, $event)' "
										+ " style='min-height:22px;height:22px;max-height:22px;line-height:22px;background: #387ef5;color:white;position:absolute;"
										+ " right:5px;'>删除</button></ion-item>";
						var divider = $compile(dividerHTML)(scope);
						var templateHTML = innerHTML.replace(/\?/g, scope.count);
						var template = $compile(templateHTML)(scope.scope);
						var divHTML = "<div divindex='"+scope.count+"'></div>";
						var div = $compile(divHTML)(scope);
						div.append(divider);
						div.append(template);
						element.children("div").append(div);
						scope.indexs.push(scope.count);
						if(scope.addcallback){
							scope.addcallback({index:scope.count});
						}
						scope.count++;
						scope.elementCount++;
					}else{
						var ele = event.target.parentNode ;
						if(scope.elementCount == 1){
							dao.popup.alert("只剩一条明细，无法删除");
							return;
						}
						var tipInnerHTML = ele.childNodes[0].innerHTML;
						tipInnerHTML = tipInnerHTML && tipInnerHTML != "" ? tipInnerHTML.replace("(", "").replace(")", "").trim() : "";
						dao.popup.confirm("确定要删除"+tipInnerHTML+"吗？", function(){
							ele = ele.parentNode;
							var index = ele.getAttribute("divindex");
							for(var i = 0; i < scope.indexs.length; i++){
								if(scope.indexs[i] == index){
									scope.indexs.splice(i, 1);
									break;
								}
							}
							if(scope.delcallback){
								scope.delcallback({index:index});
							}
							ele.remove();
							scope.elementCount--;
						}, function(){});
					}
				}
				scope.opElement(true);
				
				scope.firstFlag = true;
				
				scope.$watch("delall", function(newValue, oldValue){
					if(scope.firstFlag == true){
						scope.firstFlag = false;
						return;
					}
					if(newValue){
						scope.delall = false;
						var elements = element.children("div")[0].childNodes;
						while(elements && elements.length > 0){
							if(scope.delcallback){
								scope.delcallback({index:elements[elements.length - 1].getAttribute("divindex")});
							}
							elements[elements.length - 1].remove();
							scope.elementCount--;
						}
						scope.indexs = [];
						scope.opElement(true);
					}
				});

				scope.$watch("detailnum", function(newValue, oldValue){
					if(newValue != oldValue && newValue > 0){
						for(var i = scope.elementCount;i < newValue;i++){
							scope.opElement(true);
						}
						if(scope.afterdetailnum){
							scope.afterdetailnum();
						}
					}
				});
			}
		};
	}
	return directive;
})
