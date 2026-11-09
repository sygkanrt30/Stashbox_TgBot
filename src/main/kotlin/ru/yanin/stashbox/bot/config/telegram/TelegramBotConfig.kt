package ru.yanin.stashbox.bot.config.telegram

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.generics.TelegramClient
import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot
import ru.yanin.stashbox.bot.TelegramUpdateRouter

@Configuration
@EnableConfigurationProperties(TelegramConfig::class)
class TelegramBotConfig {
    @Bean
    fun telegramWebhookBot(
        client: TelegramClient,
        updateHandler: TelegramUpdateRouter,
        telegramConfig: TelegramConfig,
    ): SpringTelegramWebhookBot {
        val path = telegramConfig.webhookPath.trim('/')
        val webhookUrl = "${telegramConfig.webhookBaseUrl.trimEnd('/')}/$path"

        val setWebhook = SetWebhook.builder()
            .url(webhookUrl)
            .build()

        val deleteWebhook = DeleteWebhook.builder()
            .build()

        return SpringTelegramWebhookBot.builder()
            .botPath(path)
            .updateHandler(updateHandler::handle)
            .setWebhook { client.execute(setWebhook) }
            .deleteWebhook { client.execute(deleteWebhook) }
            .build()
    }
}
