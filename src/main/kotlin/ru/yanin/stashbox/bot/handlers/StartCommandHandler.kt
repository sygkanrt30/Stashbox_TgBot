package ru.yanin.stashbox.bot.handlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.yanin.stashbox.bot.TelegramUpdateHandler

@Component
class StartCommandHandler : TelegramUpdateHandler {
    companion object {
        private val log = LoggerFactory.getLogger(StartCommandHandler::class.java)
    }

    override fun canHandle(update: Update): Boolean =
        update.hasMessage() &&
            update.message.hasText() &&
            update.message.text
                .substringBefore(' ')
                .trim() == Command.START.cmd

    override fun handle(update: Update): BotApiMethod<*>? {
        val message = update.message
        log.info("Received message with chatId: {}", message.chatId.toString())
        return SendMessage.builder()
            .chatId(message.chatId.toString())
            .text("Hi! I'm Stashbox. Send me the material to save it!")
            .build()
    }
}
