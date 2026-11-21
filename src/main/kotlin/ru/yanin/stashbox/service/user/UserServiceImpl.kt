package ru.yanin.stashbox.service.user

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yanin.stashbox.domain.User
import ru.yanin.stashbox.repository.user.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    companion object {
        private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    @Transactional
    override fun ensureExists(userFromTg: org.telegram.telegrambots.meta.api.objects.User) {
        val inserted = userRepository.insertIfNotExists(
            id = userFromTg.id,
            username = userFromTg.userName,
            languageCode = userFromTg.languageCode,
        )
        if (inserted > 0) {
            log.debug("Saved user ${userFromTg.id}")
        } else {
            log.debug("User ${userFromTg.id} already exists, skipping")
        }
    }

    override fun findById(id: Long): User? = userRepository.findByIdOrNull(id)
}
