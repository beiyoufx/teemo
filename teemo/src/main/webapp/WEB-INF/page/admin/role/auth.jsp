<%@ page import="com.teemo.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Teemo后台 - 角色授权</title>
</head>
<body>
<%
    Role role = (Role) request.getAttribute("role");
%>
授权权限给角色<%=role.getRoleValue()%>

</body>
</html>
