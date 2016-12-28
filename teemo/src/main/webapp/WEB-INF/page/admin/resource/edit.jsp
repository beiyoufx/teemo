<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 资源编辑</title>

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
    <form class="form-horizontal m-t" id="validationSaveResourceFrom" action="#" method="post">
        <input id="resourceId" name="resourceId" type="hidden" class="form-control" maxlength="32" value="${resource.id}"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源关键字：</label>
            <div class="col-sm-10">
                <input id="resourceKey" name="resourceKey" type="text" class="form-control" value="${resource.resourceKey}" ${resource.resourceKey != null ? "readonly" : "" }/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源名称：</label>
            <div class="col-sm-10">
                <input id="resourceValue" name="resourceValue" type="text" class="form-control" value="${resource.resourceValue}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源地址：</label>
            <div class="col-sm-10">
                <input id="url" name="url" type="text" class="form-control" maxlength="256" placeholder="最多256个字符" value="${resource.url}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">资源类型：</label>
            <div class="col-sm-10">
                <select id="type" name="type" class="form-control">
                    <c:forEach var="resourceType" items="${resourceTypes}">
                        <option value="${resourceType}" ${resourceType == resource.type ? "selected" : ""}>${resourceType.info}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">菜单图标：</label>
            <div class="col-sm-10">
                <input id="menuIcon" name="menuIcon" type="text" class="form-control" maxlength="128" placeholder="菜单图标" value="${resource.menuIcon}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">上级资源：</label>
            <div class="col-sm-10">
                <select id="parentId" name="parentId" class="form-control">
                    <c:forEach var="res" items="${allResources}">
                        <option value="${res.id}" ${res.id == resource.parentId ? "selected" : ""}>${res.resourceValue}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label" for="available">是否可用：</label>
            <div class="col-sm-10">
                <input id="available" name="available" type="checkbox" class="i-checks" ${resource.available == true ? "checked" : ""}>
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
        // 验证表单-保存资源
        $("#saveButton").bind("click", function() {
            $('#validationSaveResourceFrom').submit();
        });
        $("#validationSaveResourceFrom").validate({
            rules : {
                resourceValue : {
                    required : true,
                    maxlength : 128
                },
                resourceKey : {
                    maxlength : 128
                },
                url : {
                    maxlength : 256
                },
                menuIcon : {
                    maxlength : 128
                }
            },
            messages : {
                resourceValue : {
                    required : "请输入资源名称",
                    maxlength : "资源名称长度至多为{0}个字符"
                },
                resourceKey : {
                    maxlength : "资源关键字至多为{0}个字符"
                },
                url : {
                    maxlength : "资源地址长度至多为{0}个字符"
                },
                menuIcon : {
                    maxlength : "资源图标class长度至多为{0}个字符"
                }
            },
            submitHandler : function(form) {
                $.ajax({
                    dataType : "json",
                    url : "${ctx}/sys/resource/save",
                    type : "post",
                    data : {
                        id : $('#resourceId').val(),
                        resourceKey : $('#resourceKey').val(),
                        resourceValue : $('#resourceValue').val(),
                        url : $("#url").val(),
                        type : $("#type").val(),
                        menuIcon : $("#menuIcon").val(),
                        parentId : $("#parentId").val(),
                        available : $("#available").prop("checked")
                    },
                    complete : function(response) {
                        var result = response.responseJSON;
                        if (result.code == 1) {
                            parent.layer.msg("保存资源信息成功，请刷新后查看", {icon : 1});
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
