# distributed-session
基于Redis实现的分布式会话管理

# 更新
- 使用客户端jedis的一致性哈稀进行分片存储

- 兼容浏览器禁止cookie的情况，从请求中获取

# 本地启动
应用启动前，需要在本地启动两个redis实例或修改

    <bean id="cookieConfig" class="com.wind.web.session.CookieConfig">
        <property name="domain" value=""/>
        <property name="secure" value="false"/>
        <property name="maxAge" value="1800"/>
    </bean>

    <bean id="redisSessionManager" class="com.wind.web.redis.RedisSessionManager">
        <property name="redisServer" value="localhost#6379#foobared,localhost#6380#foobared"/>
        <property name="connectionTimeout" value="3000"/>
        <property name="readTimeout" value="3000"/>
        <property name="maxTotal" value="1000"/>
        <property name="maxIdle" value="50"/>
        <property name="maxWaitMillis" value="3000"/>
    </bean>

    <bean id="sessionFilter" class="com.wind.web.filter.SessionFilter">
        <property name="ignorePath" value="/user/"/>
        <property name="cookieConfig" ref="cookieConfig"/>
        <property name="redisSessionManager" ref="redisSessionManager" />
    </bean>
