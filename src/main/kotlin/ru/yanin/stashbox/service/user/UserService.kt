package ru.yanin.stashbox.service.user

import ru.yanin.stashbox.domain.User

interface UserService {
    fun ensureExists(userFromTg: org.telegram.telegrambots.meta.api.objects.User)

    fun findById(id: Long): User?
}
