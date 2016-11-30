<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Teemo后台管理 - 注册</title>
    <meta name="keywords" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">
    <meta name="description" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen   animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">Teemo</h1>

        </div>
        <h3>欢迎注册 Teemo</h3>
        <p>创建一个Teemo新账户</p>
        <form class="m-t" id="validationRegisterFrom" action="${ctx}/login" method="post">
            <div class="form-group">
                <input id="username" name="username" type="text" class="form-control" placeholder="请输入用户名" maxlength="32">
            </div>
            <div class="form-group">
                <input id="password" name="password" type="password" class="form-control" placeholder="请输入密码" maxlength="16">
            </div>
            <div class="form-group">
                <input id="repeatPassword" name="repeatPassword" type="password" class="form-control" placeholder="请输入确认密码" maxlength="16">
            </div>
            <div class="form-group">
                <input id="nickname" name="nickname" type="text" class="form-control" placeholder="请输入姓名" maxlength="32">
            </div>
            <div class="form-group">
                <input id="email" name="email" type="email" class="form-control" placeholder="请输入邮箱地址" maxlength="32">
            </div>
            <div class="form-group text-left">
                <label class="no-padding checkbox i-checks"><input id="agree" name="agree" type="checkbox"><i></i> 我同意注册协议</label>
                <label id="agree-error" class="error" for="agree"></label>
            </div>
            <button id="registerButton" type="button" class="btn btn-primary block full-width m-b">注 册</button>

            <p class="text-muted text-center"><small>已经有账户了？</small><a href="${ctx}/login">点此登录</a>
            </p>

        </form>
    </div>
</div>

<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>
<!-- validate -->
<script src="${staticPath}/static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${staticPath}/static/js/login.js"></script>
<!-- iCheck -->
<script src="${staticPath}/static/js/plugins/iCheck/icheck.min.js"></script>
<script>
    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green'
        });
    });
</script>

</body>
</html>

