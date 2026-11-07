package ru.yanin.stashbox.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class TelegramUpdateRouter(
    private val handlers: List<TelegramUpdateHandler>,
) {
    fun handle(update: Update): BotApiMethod<*>? = handlers.firstOrNull { it.canHandle(update) }?.handle(update)
}
