package ru.yanin.stashbox.bot.command.handlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.yanin.stashbox.bot.TelegramUpdateHandler
import ru.yanin.stashbox.bot.keyboard.getInlineKeyboard
import ru.yanin.stashbox.bot.keyboard.getReplyKeyboard
import kotlin.uuid.Uuid

@Component
class StartCommandHandler(
    val telegramClient: TelegramClient,
) : TelegramUpdateHandler {
    companion object {
        private val log = LoggerFactory.getLogger(StartCommandHandler::class.java)
    }

    override fun canHandle(update: Update): Boolean =
        update.hasMessage() &&
            update.message.hasText() &&
            update.message.text.substringBefore(' ').trim() == Command.START.cmd

    override fun handle(update: Update): BotApiMethod<*>? {
        val message = update.message
        val chatId = message.chatId
        log.info("Received message with chatId: {}", chatId.toString())

        telegramClient.execute(
            SendMessage.builder()
                .chatId(chatId)
                .text("Hi! I'm Stashbox. Send me the material to save it!")
                .replyMarkup(getReplyKeyboard())
                .build(),
        )

        telegramClient.execute(
            SendMessage.builder()
                .chatId(chatId.toString())
                .text("Select a section")
                .replyMarkup(
                    getInlineKeyboard(
                        mapOf(
                            Pair("Section1", Uuid.random()),
                            Pair("Section2", Uuid.random()),
                        ),
                    ),
                ) // todo fill the map<String,Uuid> of section by db
                .build(),
        )
        return null
    }
}
