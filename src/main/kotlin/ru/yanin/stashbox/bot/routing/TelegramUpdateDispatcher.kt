package ru.yanin.stashbox.bot.routing

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.yanin.stashbox.bot.filters.Filter

@Component
class TelegramUpdateDispatcher(
    private val router: TelegramUpdateRouter,
    private val filters: List<Filter>,
) {
    companion object {
        private val log = LoggerFactory.getLogger(TelegramUpdateDispatcher::class.java)
    }

    fun dispatch(update: Update): BotApiMethod<*>? {
        for (filter in filters) {
            if (!filter.filter(update)) {
                return null
            }
            log.debug("Message passed filter {}", filter.javaClass.simpleName)
        }
        return router.handle(update)
    }
}
