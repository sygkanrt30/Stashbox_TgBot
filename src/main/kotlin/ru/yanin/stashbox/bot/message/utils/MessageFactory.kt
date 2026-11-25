package ru.yanin.stashbox.bot.message.utils

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.yanin.stashbox.bot.keyboard.section_managment.SectionManagementKeyboard
import ru.yanin.stashbox.bot.message.MessageText

@Component
class MessageFactory(
    private val sectionManagementKeyboard: SectionManagementKeyboard,
) {
    fun createFirstHiMessage(chatId: String): BotApiMethod<*> = getSampleOfMessage(chatId)
        .text(MessageText.HI.value)
        .replyMarkup(sectionManagementKeyboard.getReplyKeyboard())
        .build()

    fun createSelectSectionMessage(chatId: String, userId: Long): BotApiMethod<*> = getSampleOfMessage(chatId)
        .text(MessageText.SELECT_SECTION.value)
        .replyMarkup(sectionManagementKeyboard.getInlineKeyboard(userId))
        .build()

    private fun getSampleOfMessage(chatId: String): SendMessage.SendMessageBuilder<*, *> = SendMessage.builder()
        .chatId(chatId)
}
