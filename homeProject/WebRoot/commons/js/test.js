function formatObj(obj) {
	if (obj) {
		var str = JSON.stringify(obj);
		str = str.replace(/{/g, '<br>{');
		$("#respString").html(str);
	}
}
function ajaxGet(url) {
	$.ajax({
		url : url,
		data : {},
		dataType : 'json',
		type : 'get',
		cache : false,
		error : function() {
		},
		success : function(obj) {
			formatObj(obj);
		}
	});
}
function ajaxPost(url, datas) {
	$.ajax({
		url : url,
		data : datas,
		dataType : 'json',
		type : 'post',
		cache : false,
		error : function() {
		},
		success : function(obj) {
			formatObj(obj);
		}
	});
}
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] != undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};