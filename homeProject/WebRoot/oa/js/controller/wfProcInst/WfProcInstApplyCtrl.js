/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
	'use strict';
	function ctrl($scope, $rootScope, $state, $stateParams, $compile, wfProcInstService, dao, $ionicScrollDelegate) {
		$scope.deptList = [];
		$scope.checkList = [];
		$scope.goback = function(){
	        if ($scope.type) {
	        	$rootScope.goback();
	        	$scope.commentModal.remove();
	        }else{
				$rootScope.postMessage({
					returnType: 2
				});
	        }
		}
		$scope.submit = function(){
			$scope.$broadcast("wfProcInstDoSubmit");
		}
		
		// 获取部门列表
		$scope.getDeptCode = function(){
			$scope.deptList = [];
			wfProcInstService.getDeptCode().success(function(data){
				if(data.success){
					$scope.deptList = data.deptList;
				}
				if($scope.deptList && $scope.deptList.length > 0){
					$scope.department = $scope.deptList[0].Department;
				}else{
					$scope.deptList = [];
				}
			});
		}
		
		//审批人选择modal初始化
		$scope.data = {};
		$scope.datas = [];
		$scope.pageNo = 1;
		$scope.pageSize = 10;
		$scope.hasNext = false;
		
		$scope.init = function(){
			$scope.datas = [];
			$scope.pageNo = 1;
			$scope.pageSize = 10;
			$scope.hasNext = false;
		}
		
		
		$scope.search = function(data){
			$scope.init();
			$scope.getDatas(data);
		}
		
		$scope.getDatas = function(data, moreFlag){
			if(!data){
				data = {};
			}
			wfProcInstService.goJqGridEmployee($scope.pageNo, $scope.pageSize, $scope.wfProcNo, $scope.taskKey, $scope.data.nameChi).success(function(ret){
				if(ret.success && ret.pageNo == $scope.pageNo){
					$scope.datas = $scope.datas.concat(ret.datas);
					$scope.pageNo++;
					$scope.hasNext = ret.hasNext;
				}
				if(moreFlag){
					$scope.$broadcast('scroll.infiniteScrollComplete');
				}
			}).error(function(){
				if(moreFlag){
					$scope.$broadcast('scroll.infiniteScrollComplete');
				}
			});
		}
		
		dao.modal.init($scope, "templates/modals/employee.html", null, 
			function(orderby, taskKey, index){
				$scope.taskKey = taskKey;
				$scope.orderby = orderby;
				$scope.init();
				$scope.modal.show();
			}, 
			function(ret){
				if(ret){
					$scope.operators[$scope.orderby].namechidescr = ret.nameChi;
					$scope.operators[$scope.orderby].assignee = ret.number;
				}
	            $scope.modal.hide();
			}
		);
		
		// 进入页面初始化元素
		$scope.$on("$ionicView.beforeEnter", function(){
			$scope.type = $stateParams.type;
			$scope.title = $stateParams.title;
			$scope.m_umState = $stateParams.m_umState;
			$scope.procKey = $stateParams.procKey;
			$scope.procID = $stateParams.procID;
			$scope.wfProcNo = $stateParams.wfProcNo;
			$scope.wfProcInstNo = $stateParams.wfProcInstNo;
			$scope.isEnd = $stateParams.isEnd;
			$scope.department = "";
			$scope.taskId = $stateParams.taskId;
			$scope.processInstanceId = $stateParams.processInstanceId;
			$scope.approvalComment = "";
			$scope.rejectComment = "";
			$scope.commentVarName = "";
			$scope.commentFlag = "";
			$scope.checkList = [];
			$scope.getDeptCode();
			$scope.setHtml($stateParams.procKey);
			if($scope.type){
				$scope.initCommentModal();
				$scope.goJqGridByProcInstNo();
			}
			if($scope.type == '3'){
				$scope.showReset = true;
			}
		});
		
		$scope.setHtml = function(procKey){
			var html = document.getElementById("html");
			if(window.angularjsBindJQuery){
				window.angularjsBindJQuery();
			}
			var script = document.createElement("script");
			script.src="templates/pages/"+procKey+".js?time="+(new Date().getTime());
			script.onload = function(){
				var elements = $compile("<div ng-include=\"'templates/pages/"+procKey+".html?time="+(new Date().getTime())+"'\"></div>")($scope);
				for(var i = 0; i < elements.length; i++){
					html.appendChild(elements[i]);
				}
			}
			html.appendChild(script);
		}
		
		// 获取审批人列表
		$scope.$on("getOperator", function(event, elMap){
			$scope.operators = [];
			wfProcInstService.getOperator(JSON.stringify(elMap), $scope.procID, $scope.department, $scope.wfProcNo).then(function(data){
				if(data.success){
					for(var i = 0; i < data.operateList.length; i++){
						var obj = {};
						if("object" != typeof data.operateList[i]){
							obj.orderby = i;
							obj.groupDescr = data.operateList[i].split("__")[0];
							obj.namechidescr = data.operateList[i].split("__")[1];
							obj.selectBtn = "";
							obj.taskKey = "";
						}else{
							obj.orderby = i;
							obj.groupDescr = data.operateList[i][0];
							obj.namechidescr = "请选择...";
							obj.selectBtn = "true";
							obj.taskKey = data.operateList[i][1];
						}
						obj.assignee = "";
						$scope.operators.push(obj);
					}
				}
			})
		});
		
		$scope.$on("wfProcInstSendDatas", function(event, datas){
			for(var key in datas){
				if(!datas[key] || datas[key] == ""){
					dao.popup.alert("请填写完整后再提交");
					return;
				}
			}
			for(var i = 0; i < $scope.operators.length; i++){
				if($scope.operators[i].taskKey != "" && $scope.operators[i].assignee == ""){
					dao.popup.alert("请选择审批人");
					return;
				}
			}
			datas.detailJson = JSON.stringify(angular.copy($scope.operators));
			datas.processDefinitionId = $scope.procID;
			datas.wfProcNo = $scope.wfProcNo;
			datas.department = $scope.department;
			datas.procKey = $scope.procKey;
			if($scope.type == '3' && !$scope.showReset){
				for(var key in datas){
					if(key && key.indexOf("WfProcInstNo") != -1){
						delete datas[key];
					}
				}
			}
			$scope.doApply(datas);
		});
		
		$scope.doApply = function(datas){
			wfProcInstService.doApply(datas).success(function(data){
				if(data.success){
					var show = dao.popup.show(data.returnInfo);
					setTimeout(function(){
						show.close();
						$scope.goback();
					}, 1500);
				}else{
					dao.popup.alert(data.returnInfo);
				}
			})
		}
		
		$scope.$on("beginGetWfProcInstData", function(){
			if($scope.type){
				$scope.getWfProcInstData();
				return;
			}
		});
		
		$scope.getWfProcInstData = function(){
			wfProcInstService.getWfProcInstData($scope.processInstanceId).success(function(data){
				if(data.success){
					$scope.$broadcast("initData", {
						submitData: data.datas,
						detailNum: data.detailNum
					});
				}
			});
		}
		
		$scope.doReset = function(){
			$scope.showReset = false;
		}
		
		$scope.doCancelReset = function(){
			$scope.showReset = true;
		}
		
		$scope.doCancel = function(){
			dao.popup.confirm("确定要撤销吗？", function(){
				wfProcInstService.doCancelProcInst($scope.taskId, "").success(function(data){
					if(data.success){
						var show = dao.popup.show(data.returnInfo);
						setTimeout(function(){
							show.close();
							$scope.goback();
						}, 1500);
					}else{
						dao.popup.alert(data.returnInfo);
					}
				})
			}, function(){});
		}
		
		$scope.initCommentModal = function(){
			dao.modal.init(
				$scope, 
				"templates/modals/comment.html", 
				null, 
				function(){
					var commentTextarea = document.getElementById("commentTextarea");
					if(commentTextarea){
						commentTextarea.value = $scope[$scope.commentVarName];
					}
					$scope["commentModal"].show();
				}, function(saveFlag){
					var commentTextarea = document.getElementById("commentTextarea");
					if(commentTextarea){
						$scope[$scope.commentVarName] = commentTextarea.value;
					}
					if(saveFlag == true){
						if($scope.commentFlag == "pass"){
							$scope.doApprovalTask();
						}else if($scope.commentFlag == "reject"){
							$scope.doRejectTask();
						}
						$scope.commentFlag = "";
						$scope.commentVarName = "";
					}
					$scope["commentModal"].hide();
				}, 
				"commentModal", 
				"openCommentModal", 
				"closeCommentModal"
			);
		}
		
		$scope.doPass = function(){
			$scope.commentFlag = "pass";
			$scope.commentVarName = "approvalComment";
			$scope.commentTitle = "请输入同意意见";
			$scope.openCommentModal();
		}
		
		$scope.doReject = function(){
			$scope.commentFlag = "reject";
			$scope.commentVarName = "rejectComment";
			$scope.commentTitle = "请输入拒绝意见";
			$scope.openCommentModal();
		}
		
		$scope.doApprovalTask = function(){
			wfProcInstService.doApprovalTask($scope.taskId, $scope.approvalComment).success(function(data){
				if(data.success){
					var show = dao.popup.show(data.returnInfo);
					setTimeout(function(){
						show.close();
						$scope.goback();
					}, 1500);
				}else{
					dao.popup.alert(data.returnInfo);
				}
			});
		}
		
		$scope.doRejectTask = function(){
			wfProcInstService.doRejectTask($scope.taskId, $scope.rejectComment).success(function(data){
				if(data.success){
					var show = dao.popup.show(data.returnInfo);
					setTimeout(function(){
						show.close();
						$scope.goback();
					}, 1500);
				}else{
					dao.popup.alert(data.returnInfo);
				}
			});
		}
		
		$scope.goJqGridByProcInstNo = function(){
			wfProcInstService.goJqGridByProcInstNo($scope.wfProcInstNo, $scope.type, $scope.procID).success(function(data){
				if(data.success){
					$scope.checkList = data.datas;
				}
			})
		}
		
	}
	ctrl.$inject=['$scope', '$rootScope', '$state', '$stateParams', '$compile', 'wfProcInstService', 'dao', '$ionicScrollDelegate'];
	return ctrl;
})
