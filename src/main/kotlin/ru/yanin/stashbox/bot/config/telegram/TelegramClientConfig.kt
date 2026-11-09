package ru.yanin.stashbox.bot.config.telegram

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.util.concurrent.TimeUnit

@Configuration
class TelegramClientConfig(
    private val telegramConfig: TelegramConfig,
) {
    companion object {
        private val log = LoggerFactory.getLogger(TelegramClientConfig::class.java)
        private const val TIMEOUT = 15L
        private val TOKEN_REGEX = "(?<=bot)[^/]*(?=/)".toRegex()
    }

    @Bean
    fun telegramClient(): TelegramClient = OkHttpTelegramClient(buildOkHttpClient(), telegramConfig.token)

    private fun buildOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            log.debug("OkHttp: ${maskingToken(message)}")
        }.apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun maskingToken(message: String): String = message.replace(TOKEN_REGEX, "<token>")
}
