package ru.yanin.stashbox.bot.filters

import org.telegram.telegrambots.meta.api.objects.Update

interface Filter {
    fun filter(update: Update): Boolean
}
