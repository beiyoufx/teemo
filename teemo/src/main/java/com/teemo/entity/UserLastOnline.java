package com.teemo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.teemo.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户的最后在线信息
 * @author yongjie.teng
 * @version 1.0
 * @date 16-10-26
 * @email yongjie.teng@foxmail.com
 * @package com.teemo.entity
 * @project teemo
 */
@Entity
@Table(name = "user_last_online")
public class UserLastOnline extends BaseEntity {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    /**
     * 在线的用户
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "username", length = 32)
    private String username;

    /**
     * 最后退出时的uid
     */
    private String uid;

    /**
     * 用户主机地址
     */
    @Column(name = "host")
    private String host;

    /**
     * 用户浏览器类型
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 用户登录时系统IP
     */
    @Column(name = "system_host")
    private String systemHost;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lastLoginTime;

    /**
     * 最后退出时间
     */
    @Column(name = "last_quit_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date lastQuitTime;

    /**
     * 登录次数
     */
    @Column(name = "login_count")
    private Integer loginCount = 0;

    /**
     * 总的在线时长（秒为单位）
     */
    @Column(name = "total_online_time")
    private Long totalOnlineTime = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Long getTotalOnlineTime() {
        return totalOnlineTime;
    }

    public void setTotalOnlineTime(Long totalOnlineTime) {
        this.totalOnlineTime = totalOnlineTime;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastQuitTime() {
        return lastQuitTime;
    }

    public void setLastQuitTime(Date lastQuitTime) {
        this.lastQuitTime = lastQuitTime;
    }

    public void incLoginCount() {
        setLoginCount(getLoginCount() + 1);
    }

    public void incTotalOnlineTime() {
        long onlineTime = getLastQuitTime().getTime() - getLastLoginTime().getTime();
        setTotalOnlineTime(getTotalOnlineTime() + onlineTime / 1000);
    }

}
