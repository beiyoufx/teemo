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