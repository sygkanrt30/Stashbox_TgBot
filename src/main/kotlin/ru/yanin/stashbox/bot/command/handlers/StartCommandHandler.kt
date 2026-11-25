package ru.yanin.stashbox.bot.command.handlers

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.yanin.stashbox.bot.message.utils.MessageFactory
import ru.yanin.stashbox.service.user.UserService

@Component
class StartCommandHandler(
    private val telegramClient: TelegramClient,
    private val userService: UserService,
    private val messageFactory: MessageFactory,
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
        val chatId = message.chatId.toString()
        log.info("Received message with chatId: {}", chatId)

        userService.ensureExists(message.from)

        telegramClient.execute(messageFactory.createFirstHiMessage(chatId))
        telegramClient.execute(messageFactory.createSelectSectionMessage(chatId, message.from.id))
        return null
    }
}
