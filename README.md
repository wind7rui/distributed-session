# distributed-session
基于Redis实现的分布式会话管理

# 更新
- 使用客户端jedis的一致性哈稀进行分片存储

- 兼容浏览器禁止cookie的情况，从请求中获取

# 本地运行说明
应用启动前，需要在本地启动两个redis实例或修改applicationContext.xml中redisSessionManager的redisServer属性值。
