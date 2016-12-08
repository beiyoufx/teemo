<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../core.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>Teemo - 个人资料</title>

    <meta name="keywords" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">
    <meta name="description" content="teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。">

    <link rel="shortcut icon" href="${staticPath}/static/favicon.ico">
    <link href="${staticPath}/static/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticPath}/static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${staticPath}/static/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/static/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row animated fadeInRight">
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>个人资料</h5>
                </div>
                <div>
                    <div class="ibox-content no-padding border-left-right">
                        <img alt="image" class="img-responsive" src="${staticPath}/static/img/profile_girl.jpg">
                    </div>
                    <div class="ibox-content profile-content">
                        <h4><strong>${CurrentUser.nickname}</strong></h4>
                        <p><i class="fa fa-map-marker"></i> 上海市浦东新区郭守敬路498号浦东软件园</p>
                        <h5>
                            关于我
                        </h5>
                        <p>
                            teemo是一个Java EE企业级通用开发框架，提供底层抽象和常用功能。项目地址：<a href="https://github.com/beiyoufx" target="_blank">https://github.com/beiyoufx</a>
                        </p>
                        <div class="row m-t-lg">
                            <div class="col-sm-4">
                                <span class="bar">5,3,9,6,5,9,7,3,5,2</span>
                                <h5><strong>169</strong> 文章</h5>
                            </div>
                            <div class="col-sm-4">
                                <span class="line">5,3,9,6,5,9,7,3,5,2</span>
                                <h5><strong>28</strong> 关注</h5>
                            </div>
                            <div class="col-sm-4">
                                <span class="bar">5,3,2,-1,-3,-2,2,3,5,2</span>
                                <h5><strong>240</strong> 关注者</h5>
                            </div>
                        </div>
                        <div class="user-button">
                            <div class="row">
                                <div class="col-sm-6">
                                    <button type="button" class="btn btn-primary btn-sm btn-block"><i class="fa fa-envelope"></i> 发送消息</button>
                                </div>
                                <div class="col-sm-6">
                                    <button type="button" class="btn btn-default btn-sm btn-block"><i class="fa fa-coffee"></i> 赞助</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>最新动态</h5>
                </div>
                <div class="ibox-content">
                    <div>
                        <div class="feed-activity-list">

                            <div class="feed-element">
                                <a href="${ctx}/sys/user/profile" class="pull-left">
                                    <img alt="image" class="img-circle" src="${staticPath}/static/img/a1.jpg">
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right text-navy">1天前</small>
                                    <strong>奔波儿灞</strong> 关注了 <strong>灞波儿奔</strong>.
                                    <br>
                                    <small class="text-muted">54分钟前 来自 皮皮时光机</small>
                                    <div class="actions">
                                        <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                        <a class="btn btn-xs btn-danger"><i class="fa fa-heart"></i> 收藏</a>
                                    </div>
                                </div>
                            </div>

                            <div class="feed-element">
                                <a href="${ctx}/sys/user/profile" class="pull-left">
                                    <img alt="image" class="img-circle" src="${staticPath}/static/img/profile.jpg">
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right">5分钟前</small>
                                    <strong>作家崔成浩</strong> 发布了一篇文章
                                    <br>
                                    <small class="text-muted">今天 10:20 来自 iPhone 6 Plus</small>

                                </div>
                            </div>

                            <div class="feed-element">
                                <a href="${ctx}/sys/user/profile" class="pull-left">
                                    <img alt="image" class="img-circle" src="${staticPath}/static/img/a2.jpg">
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right">2小时前</small>
                                    <strong>作家崔成浩</strong> 抽奖中了20万
                                    <br>
                                    <small class="text-muted">今天 09:27 来自 Koryolink iPhone</small>
                                    <div class="well">
                                        抽奖，人民币2000元，从转发这个微博的粉丝中抽取一人。11月16日平台开奖。随手一转，万一中了呢？
                                    </div>
                                    <div class="pull-right">
                                        <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                        <a class="btn btn-xs btn-white"><i class="fa fa-heart"></i> 收藏</a>
                                        <a class="btn btn-xs btn-primary"><i class="fa fa-pencil"></i> 评论</a>
                                    </div>
                                </div>
                            </div>
                            <div class="feed-element">
                                <a href="${ctx}/sys/user/profile" class="pull-left">
                                    <img alt="image" class="img-circle" src="${staticPath}/static/img/a3.jpg">
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right">2天前</small>
                                    <strong>天猫</strong> 上传了2张图片
                                    <br>
                                    <small class="text-muted">11月7日 11:56 来自 微博 weibo.com</small>
                                    <div class="photos">
                                        <a target="_blank" href="http://24.media.tumblr.com/20a9c501846f50c1271210639987000f/tumblr_n4vje69pJm1st5lhmo1_1280.jpg">
                                            <img alt="image" class="feed-photo" src="${staticPath}/static/img/p1.jpg">
                                        </a>
                                        <a target="_blank" href="http://37.media.tumblr.com/9afe602b3e624aff6681b0b51f5a062b/tumblr_n4ef69szs71st5lhmo1_1280.jpg">
                                            <img alt="image" class="feed-photo" src="${staticPath}/static/img/p3.jpg">
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="feed-element">
                                <a href="${ctx}/sys/user/profile" class="pull-left">
                                    <img alt="image" class="img-circle" src="${staticPath}/static/img/a4.jpg">
                                </a>
                                <div class="media-body ">
                                    <small class="pull-right text-navy">5小时前</small>
                                    <strong>在水一方Y</strong> 关注了 <strong>那二十年的单身</strong>.
                                    <br>
                                    <small class="text-muted">今天 10:39 来自 iPhone客户端</small>
                                    <div class="actions">
                                        <a class="btn btn-xs btn-white"><i class="fa fa-thumbs-up"></i> 赞 </a>
                                        <a class="btn btn-xs btn-white"><i class="fa fa-heart"></i> 收藏</a>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <button class="btn btn-primary btn-block m"><i class="fa fa-arrow-down"></i> 显示更多</button>

                    </div>

                </div>
            </div>

        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${staticPath}/static/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticPath}/static/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${staticPath}/static/js/content.js?v=1.0.0"></script>


<!-- Peity -->
<script src="${staticPath}/static/js/plugins/peity/jquery.peity.min.js"></script>

<!-- Peity -->
<script src="${staticPath}/static/js/demo/peity-demo.js"></script>

</body>

</html>

