package ru.yanin.stashbox.bot.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.generics.TelegramClient
import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot
import ru.yanin.stashbox.bot.TelegramUpdateRouter

@Configuration
@EnableConfigurationProperties(TelegramConfig::class)
class TelegramBotConfig(
    private val telegramConfig: TelegramConfig
) {
    @Bean
    fun telegramClient(): TelegramClient = OkHttpTelegramClient(telegramConfig.token)

    @Bean
    fun telegramWebhookBot(
        client: TelegramClient,
        updateHandler: TelegramUpdateRouter,
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
