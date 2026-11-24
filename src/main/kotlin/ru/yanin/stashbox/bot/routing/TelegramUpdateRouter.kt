package ru.yanin.stashbox.bot.routing

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.yanin.stashbox.bot.command.handlers.TelegramUpdateHandler

@Component
class TelegramUpdateRouter(
    private val handlers: List<TelegramUpdateHandler>,
) {
    fun handle(update: Update): BotApiMethod<*>? = handlers.firstOrNull { it.canHandle(update) }?.handle(update)
}
