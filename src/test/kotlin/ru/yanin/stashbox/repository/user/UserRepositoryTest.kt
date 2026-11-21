package ru.yanin.stashbox.repository.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace.NONE
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import ru.yanin.stashbox.domain.User
import java.time.Duration
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertTrue

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class UserRepositoryTest(
    @Autowired private val repository: UserRepository,
) {
    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:18-alpine")
    }

    @AfterEach
    fun clean() {
        repository.deleteAll()
    }

    @Test
    fun `should return 1 if user doesn't exist`() {
        val id = 12L

        val affectedRows = repository.insertIfNotExists(id, "john", "en")
        val now = LocalDateTime.now()

        val user = repository.findById(id).get()
        assertThat(user.id).isEqualTo(id)
        assertThat(affectedRows).isEqualTo(1)
        assertTrue { Duration.between(user.startUseBotAt, now).abs() < Duration.ofSeconds(1) }
    }

    @Test
    fun `should return 0 if user exists`() {
        val id = 12L
        val languageCode = "en"
        val user = User(id, "john", languageCode)
        repository.saveAndFlush(user)

        val affectedRows = repository.insertIfNotExists(id, "john", "ru")

        val userFromDb = repository.findById(id).get()
        assertThat(affectedRows).isEqualTo(0)
        assertThat(userFromDb.languageCode).isEqualTo(languageCode)
    }
}
