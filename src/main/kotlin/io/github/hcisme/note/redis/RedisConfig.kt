package io.github.hcisme.note.redis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class RedisConfig<V> {
    @Bean("redisConfigTemplate")
    fun redisTemplate(factory: RedisConnectionFactory?): RedisTemplate<String, V> {
        val template = RedisTemplate<String, V>()
        template.connectionFactory = factory

        template.keySerializer = RedisSerializer.string()
        template.valueSerializer = RedisSerializer.json()

        template.hashKeySerializer = RedisSerializer.string()
        template.hashValueSerializer = RedisSerializer.json()

        return template
    }

    @Bean
    fun container(connectionFactory: RedisConnectionFactory): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        return container
    }
}
