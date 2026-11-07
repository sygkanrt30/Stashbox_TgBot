package ru.yanin.stashbox.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.updates.DeleteWebhook
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.generics.TelegramClient
import org.telegram.telegrambots.webhook.starter.SpringTelegramWebhookBot

@Configuration
class TelegramBotConfig(
    @Value($$"${telegram.bot-token}")
    private val botToken: String,
    @Value($$"${telegram.webhook-base-url}")
    private val webhookBaseUrl: String,
    @Value($$"${telegram.webhook-path}")
    private val webhookPath: String,
) {
    @Bean
    fun telegramClient(): TelegramClient = OkHttpTelegramClient(botToken)

    @Bean
    fun telegramWebhookBot(
        client: TelegramClient,
        updateHandler: TelegramUpdateRouter,
    ): SpringTelegramWebhookBot {
        val path = webhookPath.trim('/')
        val webhookUrl = "${webhookBaseUrl.trimEnd('/')}/$path"

        val setWebhook = SetWebhook.builder()
            .url(webhookUrl)
            .build()

        val deleteWebhook = DeleteWebhook.builder()
            .build()

        return SpringTelegramWebhookBot
            .builder()
            .botPath(path)
            .updateHandler(updateHandler::handle)
            .setWebhook { client.execute(setWebhook) }
            .deleteWebhook { client.execute(deleteWebhook) }
            .build()
    }
}
