package ru.yanin.stashbox.bot.keyboard.section_managment

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.yanin.stashbox.service.setion.SectionService

@Component
class SectionManagementKeyboard(
    private val sectionService: SectionService,
) {
    fun getInlineKeyboard(userId: Long): InlineKeyboardMarkup {
        val buttons = sectionService.loadAllSectionsByUserId(userId).map { (key, value) ->
            InlineKeyboardRow().apply {
                add(
                    InlineKeyboardButton.builder()
                        .text(key)
                        .callbackData("section:$value")
                        .build(),
                )
            }
        }.toList()

        return InlineKeyboardMarkup.builder()
            .keyboard(buttons)
            .build()
    }

    fun getReplyKeyboard(): ReplyKeyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(
            listOf(
                KeyboardRow().apply {
                    add(KeyboardButton(SectionsManagementButtons.CREATE.txt))
                    add(KeyboardButton(SectionsManagementButtons.DElETE.txt))
                },
                KeyboardRow().apply {
                    add(KeyboardButton(SectionsManagementButtons.DELETE_ALL.txt))
                },
            ),
        ).resizeKeyboard(true)
        .isPersistent(true)
        .build()
}
