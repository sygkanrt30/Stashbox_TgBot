package ru.yanin.stashbox.service.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import ru.yanin.stashbox.domain.User
import ru.yanin.stashbox.repository.user.UserRepository
import java.util.Optional
import org.telegram.telegrambots.meta.api.objects.User as TelegramUser

class UserServiceImplTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        userService = UserServiceImpl(userRepository)
    }

    @Test
    fun `ensureExists inserts new user with telegram data`() {
        val telegramUser = buildTgUser()
        `when`(userRepository.insertIfNotExists(anyLong(), anyString(), anyString()))
            .thenReturn(1)

        userService.ensureExists(telegramUser)

        verify(userRepository).insertIfNotExists(42L, "john", "en")
        verifyNoMoreInteractions(userRepository)
    }

    @Test
    fun `ensureExists completes when user already exists`() {
        val telegramUser = buildTgUser()
        `when`(userRepository.insertIfNotExists(anyLong(), anyString(), anyString()))
            .thenReturn(0)

        userService.ensureExists(telegramUser)

        verify(userRepository).insertIfNotExists(42L, "john", "en")
        verifyNoMoreInteractions(userRepository)
    }

    @Test
    fun `findById returns user when found`() {
        val user = User(id = 42L, username = "john", languageCode = "en")
        `when`(userRepository.findById(42L)).thenReturn(Optional.of(user))

        val result = userService.findById(42L)

        assertThat(result).isEqualTo(user)
        verify(userRepository).findById(42L)
        verifyNoMoreInteractions(userRepository)
    }

    @Test
    fun `findById returns null when not found`() {
        `when`(userRepository.findById(42L)).thenReturn(Optional.empty())

        val result = userService.findById(42L)

        assertThat(result).isNull()
        verify(userRepository).findById(42L)
        verifyNoMoreInteractions(userRepository)
    }

    private fun buildTgUser(): TelegramUser {
        val telegramUser = TelegramUser.builder()
            .id(42L)
            .firstName("any")
            .isBot(false)
            .userName("john")
            .languageCode("en")
            .build()
        return telegramUser
    }
}
