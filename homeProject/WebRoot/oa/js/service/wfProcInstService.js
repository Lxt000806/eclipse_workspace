/**
 * Created by zzr on 2019/5/24.
 */
define([],function(){
	'use strict';
	function  service($q, $http,dao) {
		return {
			getOperator: function (el, pdId, department, wfProcNo) {
				var data = new Object();
				data.el = el;
				data.pdID = pdId;
				data.department = department;
				data.wfProcNo = wfProcNo;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/getOperator", data);
			},
			getDeptCode: function(){
				var data = new Object();
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/getDeptCode", data);
			},
			goJqGridEmployee: function(pageNo, pageSize, wfProcNo, taskKey, nameChi){
				var data = new Object();
				data.pageNo = pageNo;
				data.pageSize = pageSize;
				data.wfProcNo = wfProcNo;
				data.taskKey = taskKey;
				data.nameChi = nameChi;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/employee/goJqGridEmployee", data);
			},
			getCustStakeholder: function(roll, custCode){
				var data = new Object();
				data.roll = roll;
				data.custCode = custCode;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/getCustStakeholder", data);
			},
			doApply: function(subDatas){
				var data = new Object();
				for(var key in subDatas){
					data[key] = subDatas[key];
				}
		        return dao.https.post("client/wfProcInst/doApply", data);
			},
			findWfProcInst: function(pageNo, pageSize, type){
				var data = new Object();
				data.pageNo = pageNo;
				data.pageSize = pageSize;
				data.type = type;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/findWfProcInst", data);
			},
			getWfProcInstData: function(processInstanceId){
				var data = new Object();
				data.processInstanceId = processInstanceId;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/getWfProcInstData", data);
			},
			doCancelProcInst: function(taskId, comment){
				var data = new Object();
				data.taskId = taskId;
				data.comment = comment;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/doCancelProcInst", data);
			},
			doApprovalTask: function(taskId, comment){
				var data = new Object();
				data.taskId = taskId;
				data.comment = comment;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/doApprovalTask", data);
			},
			doRejectTask: function(taskId, comment){
				var data = new Object();
				data.taskId = taskId;
				data.comment = comment;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/doRejectTask", data);
			},
			goJqGridByProcInstNo: function(wfProcInstNo, type, procDefKey){
				var data = new Object();
				data.wfProcInstNo = wfProcInstNo;
				data.type = type;
				data.procDefKey = procDefKey;
				data.callback = "JSON_CALLBACK";
				return dao.https.jsonp("client/wfProcInst/goJqGridByProcInstNo", data);
			}
		}
	}
	service.$inject=['$q', '$http','dao'];
	return service;
})
