package ru.yanin.stashbox.bot.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "telegram")
data class TelegramConfig(
    val token: String,
    val webhookBaseUrl: String,
    val webhookPath: String
)