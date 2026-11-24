package ru.yanin.stashbox.bot.filters

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class IsBotFilter : Filter {
    companion object {
        private val log = LoggerFactory.getLogger(IsBotFilter::class.java)
    }

    override fun filter(update: Update): Boolean {
        val user = update.message.from
        if (user.isBot) {
            log.debug("Ignoring bot {}/{}", user.id, user.userName)
            return false
        }
        return true
    }
}
