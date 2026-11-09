package ru.yanin.stashbox.bot.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import kotlin.uuid.Uuid

fun getInlineKeyboard(sections: Map<String, Uuid>): InlineKeyboardMarkup {
    val buttons = sections.map { (key, value) ->
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
