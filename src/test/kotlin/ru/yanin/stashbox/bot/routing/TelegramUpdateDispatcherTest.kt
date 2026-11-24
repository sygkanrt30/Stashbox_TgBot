package ru.yanin.stashbox.bot.routing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.yanin.stashbox.bot.filters.Filter

class TelegramUpdateDispatcherTest {
    private lateinit var router: TelegramUpdateRouter
    private lateinit var update: Update

    @BeforeEach
    fun setUp() {
        router = mock(TelegramUpdateRouter::class.java)
        update = Update()
    }

    @Test
    fun `dispatch passes update through all filters and returns router result`() {
        val firstFilter = passingFilter()
        val secondFilter = passingFilter()
        val response = sendMessage("reply")
        `when`(router.handle(update)).thenReturn(response)
        val dispatcher = TelegramUpdateDispatcher(router, listOf(firstFilter, secondFilter))

        val result = dispatcher.dispatch(update)

        assertThat(result).isSameAs(response)
        val inOrder = inOrder(firstFilter, secondFilter, router)
        inOrder.verify(firstFilter).filter(update)
        inOrder.verify(secondFilter).filter(update)
        inOrder.verify(router).handle(update)
        verifyNoMoreInteractions(firstFilter, secondFilter, router)
    }

    @Test
    fun `dispatch returns null and skips router when filter rejects update`() {
        val filter = rejectingFilter()
        val dispatcher = TelegramUpdateDispatcher(router, listOf(filter))

        val result = dispatcher.dispatch(update)

        assertThat(result).isNull()
        verify(filter).filter(update)
        verifyNoInteractions(router)
    }

    @Test
    fun `dispatch does not apply remaining filters when one rejects update`() {
        val rejecting = rejectingFilter()
        val next = passingFilter()
        val dispatcher = TelegramUpdateDispatcher(router, listOf(rejecting, next))

        val result = dispatcher.dispatch(update)

        assertThat(result).isNull()
        verify(rejecting).filter(update)
        verify(next, never()).filter(update)
        verifyNoInteractions(router)
    }

    @Test
    fun `dispatch routes update when there are no filters`() {
        val response = sendMessage("update message")
        `when`(router.handle(update)).thenReturn(response)
        val dispatcher = TelegramUpdateDispatcher(router, emptyList())

        val result = dispatcher.dispatch(update)

        assertThat(result).isSameAs(response)
        verify(router).handle(update)
        verifyNoMoreInteractions(router)
    }

    private fun passingFilter(): Filter =
        mock(Filter::class.java).also { filter ->
            `when`(filter.filter(update)).thenReturn(true)
        }

    private fun rejectingFilter(): Filter =
        mock(Filter::class.java).also { filter ->
            `when`(filter.filter(update)).thenReturn(false)
        }

    private fun sendMessage(text: String): BotApiMethod<*> =
        SendMessage.builder()
            .chatId("42")
            .text(text)
            .build()
}
