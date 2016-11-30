<%--
  User: yongjie.teng
  Date: 16-11-15
  Time: 下午5:32
  主要用来定义和引用一些通用的数据
--%>
<%@ page import="com.teemo.core.util.DPUtil" %>
<%@ page import="com.teemo.core.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String staticPath = DPUtil.getDynamicProperty(Constants.DP_STATIC_PATH);
    staticPath = staticPath != null ? staticPath : request.getContextPath();
    request.setAttribute("staticPath", staticPath);
%>
<c:set var="staticPath" value="${staticPath}"/>