package ru.yanin.stashbox.bot

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update

interface TelegramUpdateHandler {
    fun canHandle(update: Update): Boolean

    fun handle(update: Update): BotApiMethod<*>?
}
