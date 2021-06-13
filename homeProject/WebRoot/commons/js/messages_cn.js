/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
        required: "必填字段",
		remote: "请修正该字段",
		email: "格式错误",
		url: "网址不合法",
		date: "格式错误",
		dateISO: "格式错误 (ISO).",
		number: "数字类型",
		digits: "整数类型",
		creditcard: "信用卡号不合法",
		equalTo: "请再次输入相同的值",
		accept: "后缀名不合法",
		maxlength: jQuery.validator.format("长度不超过{0}个字符"),
		minlength: jQuery.validator.format("长度不少于{0}个字符"),
		rangelength: jQuery.validator.format("长度介于 {0} 和 {1} 之间"),
		range: jQuery.validator.format("值介于 {0} 和 {1} 之间"),
		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min: jQuery.validator.format("请输入一个最小为 {0} 的值"),
		ajaxValidate: "已存在",
		validIllegalChar: "非法字符"
});