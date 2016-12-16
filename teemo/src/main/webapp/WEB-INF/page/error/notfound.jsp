<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 404</title>

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link rel="bookmark" href="${staticPath}/static/favicon.ico"/>
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">页面不存在</h3>

    <div class="error-desc">
        您访问的页面不存在...
        <br/>您可以返回主页看看
        <br/><a href="${ctx}/home" class="btn btn-primary m-t">主页</a>
    </div>
</div>

<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>
</body>
</html>
