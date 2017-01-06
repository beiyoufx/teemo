<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title>Teemo后台 - 属性编辑</title>

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
    <form class="form-horizontal m-t" id="validationSavePropertyFrom" action="#" method="post">
        <input id="dynamicPropertyId" name="dynamicPropertyId" type="hidden" class="form-control" maxlength="32" value="${dynamicProperty.id}"/>
        <div class="form-group">
            <label class="col-sm-2 control-label">属性关键字：</label>
            <div class="col-sm-10">
                <input id="dynamicPropertyKey" name="dynamicPropertyKey" type="text" class="form-control" maxlength="32" placeholder="请输入4-32位字母" value="${dynamicProperty.dynamicPropertyKey}" ${dynamicProperty.dynamicPropertyKey != null ? "readonly" : ""}/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">属性值：</label>
            <div class="col-sm-10">
                <input id="dynamicPropertyValue" name="dynamicPropertyValue" type="text" class="form-control" maxlength="32" placeholder="最多32个汉字" value="${dynamicProperty.dynamicPropertyValue}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">描述：</label>
            <div class="col-sm-10">
                <textarea id="description" name="description" class="form-control" maxlength="128" placeholder="最多32个汉字" >${dynamicProperty.description}</textarea>
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
        // 验证表单-保存属性
        $("#saveButton").bind("click", function() {
            $('#validationSavePropertyFrom').submit();
        });
        $("#validationSavePropertyFrom").validate({
            rules : {
                dynamicPropertyKey : {
                    required : true,
                    minlength : 4,
                    maxlength : 32
                },
                dynamicPropertyValue : {
                    required : true,
                    minlength : 2,
                    maxlength : 32
                },
                description : {
                    maxlength : 128
                }
            },
            messages : {
                key : {
                    required : "请输入属性关键字",
                    minlength : "属性关键字长度至少为{0}个字符",
                    maxlength : "属性关键字长度至多为{0}个字符"
                },
                value : {
                    required : "请输入属性名称",
                    minlength : "属性名称长度至少为{0}个字符",
                    maxlength : "属性名称长度至多为{0}个字符"
                },
                description : {
                    maxlength : "描述长度至多为{0}个字符"
                }
            },
            submitHandler : function(form) {
                $.ajax({
                    dataType : "json",
                    url : "${ctx}/sys/property/save",
                    type : "post",
                    data : {
                        id : $('#dynamicPropertyId').val(),
                        dynamicPropertyKey : $('#dynamicPropertyKey').val(),
                        dynamicPropertyValue : $("#dynamicPropertyValue").val(),
                        description : $("#description").val()
                    },
                    complete : function(response) {
                        var result = response.responseJSON;
                        if (result.code == 1) {
                            parent.layer.msg("保存属性信息成功，请刷新后查看", {icon : 1});
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
