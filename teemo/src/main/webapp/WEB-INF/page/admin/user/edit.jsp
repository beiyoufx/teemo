<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 用户编辑</title>

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form class="form-horizontal m-t" id="validationSaveUserFrom" action="#" method="post">
        <input id="userId" name="userId" type="hidden" class="form-control" maxlength="32" value="${user.id}"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">用户名：</label>
            <div class="col-sm-10">
                <input id="username" name="username" type="text" class="form-control" value="${user.username}" readonly/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">姓名：</label>
            <div class="col-sm-10">
                <input id="nickname" name="nickname" type="text" class="form-control" maxlength="32" placeholder="最多32个汉字" value="${user.nickname}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">邮箱：</label>
            <div class="col-sm-10">
                <input id="email" name="email" type="email" class="form-control" maxlength="32" placeholder="请输入邮箱地址" value="${user.email}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">手机号：</label>
            <div class="col-sm-10">
                <input id="mobilePhone" name="mobilePhone" type="text" class="form-control" maxlength="16" placeholder="请输入手机号码" value="${user.mobilePhone}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">部门：</label>
            <div class="col-sm-10">
                <select id="departmentKey" name="departmentKey" class="form-control">
                    <c:forEach var="department" items="${departments}">
                    <option value="${department.departmentKey}" ${user.departmentKey == department.departmentKey ? "selected" : ""}>${department.departmentValue}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="status">是否可用：</label>
            <div class="col-sm-10">
                <input id="status" name="status" type="checkbox" class="i-checks" ${user.status == "normal" ? "checked" : ""}>
            </div>
        </div>
    </form>
    <div class="row">
        <div class="col-sm-5">
            <button id="saveButton" type="button" class="btn btn-primary pull-right">保 存</button>
        </div>
        <div class="col-sm-5">
            <button id="cancelButton" type="button" class="btn btn-default pull-left">取 消</button>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- validate -->
<script src="${staticPath}/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${staticPath}/static/js/admin/validate.js"></script>
<!-- iCheck -->
<script src="${staticPath}/static/js/plugins/iCheck/icheck.min.js"></script>
<!-- 后台通用js -->
<script src="${staticPath}/static/js/admin/common.js"></script>
<script>
    $().ready(function () {
        // 验证表单-保存用户
        $("#saveButton").bind("click", function() {
            $('#validationSaveUserFrom').submit();
        });
        $("#validationSaveUserFrom").validate({
            rules : {
                nickname : {
                    required : true,
                    maxlength : 32
                },
                email : {
                    maxlength : 32,
                    email : true
                },
                mobilePhone : {
                    minlength : 11,
                    maxlength : 16,
                    mobile : true
                }
            },
            messages : {
                nickname : {
                    required : "请输入姓名",
                    maxlength : "姓名长度至多为{0}个字符"
                },
                email : {
                    maxlength : "密码长度至多为{0}个字符",
                    email : "请输入正确的邮箱地址"
                },
                mobilePhone : {
                    minlength : "密码长度至少为{0}个字符",
                    maxlength : "密码长度至多为{0}个字符",
                    mobile : "请输入正确的手机号码"
                }
            },
            submitHandler : function(form) {
                $.ajax({
                    dataType : "json",
                    url : "${ctx}/sys/user/save",
                    type : "post",
                    data : {
                        id : $('#userId').val(),
                        username : $('#username').val(),
                        nickname : $("#nickname").val(),
                        email : $("#email").val(),
                        mobilePhone : $("#mobilePhone").val(),
                        departmentKey : $("#departmentKey").val(),
                        status : $("#status").prop("checked") == true ? "normal" : "blocked"
                    },
                    complete : function(response) {
                        var result = response.responseJSON;
                        if (result.code == 1) {
                            parent.layer.msg("保存用户信息成功，请刷新后查看", {icon : 1});
                            closeCurrentFrame();
                        } else  {
                            parent.layer.msg(result.message, {icon: 2});
                        }
                    }
                });
            },
            invalidHandler : function(form) {
            }
        });
    });
</script>
</body>
</html>
