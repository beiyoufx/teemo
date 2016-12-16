/**
 * Created by yongjie.teng on 16-12-15.
 */
/**
 * 只允许数字、字母、下划线的验证
 */
$.validator.addMethod("alnumunderline", function(value, element){
        return this.optional(element) ||/^[a-zA-Z0-9/_]+$/.test(value);
    }, "只能包括英文字母、数字和下划线");

/**
 * 只允许数字、字母的验证
 */
$.validator.addMethod("alnum", function(value, element){
    return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
}, "只能包括英文字母和数字");

/**
 * 手机号码验证
 */
jQuery.validator.addMethod("mobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请填写正确的手机号码");