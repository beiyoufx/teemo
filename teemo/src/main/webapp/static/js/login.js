/**
 * Created by yongjie.teng on 16-11-29.
 */
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}

$().ready(function () {
    // 按回车键触发登录事件
    $(document).keydown(function(event) {
        var key = window.event ? event.keyCode : event.which;
        if (key == 13) {
            $('#validationLoginFrom').submit();
        }
    });

    // 验证登录表单
    $("#loginButton").bind("click", function() {
        $('#validationLoginFrom').submit();
    });
    $("#validationLoginFrom").validate({
        rules : {
            username : {
                required : true,
                minlength : 6,
                maxlength : 32
            },
            password : {
                required : true,
                minlength : 6,
                maxlength : 16
            }
        },
        messages : {
            username : {
                required : "请输入用户名",
                minlength : "用户名长度至少为{0}个字符",
                maxlength : "用户名长度至多为{0}个字符"
            },
            password : {
                required : "请输入密码",
                minlength : "密码长度至少为{0}个字符",
                maxlength : "密码长度至多为{0}个字符"
            }
        },
        submitHandler : function(form) {
            $.ajax({
                dataType : "json",
                url : getContextPath() + "/login",
                type : "post",
                data : {
                    username : $('#username').val(),
                    password : $('#password').val(),
                    rememberMe : $("#rememberMe").val()
                },
                complete : function(response) {
                    var result = response.responseJSON;
                    if (result.code == 1) {
                        document.location.href = getContextPath() + "/home";
                    } else if (result.code == -1) {
                        parent.layer.msg(result.message);
                    } else if (result.code == -2) {
                        parent.layer.msg(result.message);
                    } else if (result.code == -3) {
                        parent.layer.msg(result.message);
                    } else {
                        parent.layer.msg(result.message);
                    }
                }
            });
        },
        invalidHandler : function(form) {
        }
    });

    // 验证注册表单
    $("#registerButton").bind("click", function() {
        $('#validationRegisterFrom').submit();
    });
    $("#validationRegisterFrom").validate({
        rules : {
            username : {
                required : true,
                minlength : 6,
                maxlength : 32
            },
            password : {
                required : true,
                minlength : 6,
                maxlength : 16
            },
            repeatPassword : {
                required : true,
                minlength : 6,
                maxlength : 16,
                equalTo : "#password"
            },
            nickname : {
                required : true,
                maxlength : 32
            },
            email : {
                required : true,
                email : true,
                maxlength : 32
            },
            agree : {
                required : true
            }
        },
        messages : {
            username : {
                required : "请输入用户名",
                minlength : "用户名长度至少为{0}个字符",
                maxlength : "用户名长度至多为{0}个字符"
            },
            password : {
                required : "请输入密码",
                minlength : "密码长度至少为{0}个字符",
                maxlength : "密码长度至多为{0}个字符"
            },
            repeatPassword : {
                required : "请输入确认密码",
                minlength : "密码长度至少为{0}个字符",
                maxlength : "密码长度至多为{0}个字符",
                equalTo : "确认密码和密码不一致"
            },
            nickname : {
                required : "请输入姓名",
                maxlength : "姓名长度至多为{0}个字符"
            },
            email : {
                required : "请输入邮箱地址",
                email : "请输入正确的邮箱地址",
                maxlength : "邮箱地址长度至多为{0}个字符"
            },
            agree : {
                required : "您还未接受用户协议",
                element : "#agree-error"
            }
        },
        submitHandler : function(form) {
            $.ajax({
                dataType : "json",
                url : getContextPath() + "/register",
                type : "post",
                data : {
                    username : $('#username').val(),
                    password : $('#password').val(),
                    nickname : $("#nickname").val(),
                    email : $("#email").val()
                },
                complete : function(response) {
                    var result = response.responseJSON;
                    if (result.code == 1) {
                        document.location.href = getContextPath() + "/login";
                    } else if (result.code == -1) {
                        parent.layer.msg(result.message);
                    } else {
                        parent.layer.msg(result.message);
                    }
                }
            });
        },
        invalidHandler : function(form) {
        }
    });
});