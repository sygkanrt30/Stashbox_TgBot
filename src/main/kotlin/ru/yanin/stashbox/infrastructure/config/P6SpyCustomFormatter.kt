package ru.yanin.stashbox.infrastructure.config

import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl
import org.hibernate.engine.jdbc.internal.Formatter
import java.util.*

class P6SpyCustomFormatter : MessageFormattingStrategy {

    private val formatter : Formatter = BasicFormatterImpl()

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?,
    ): String {

        if (Objects.isNull(sql) || sql!!.trim { it <= ' ' }.isEmpty()) {
            return ""
        }

        return String.format(
            """
                 
                 Execution time: %d ms | Category: %s | Connection ID: %d
                 %s
                 
                 """.trimIndent(),
            elapsed, category, connectionId, formatter.format(sql)
        )
    }
}
