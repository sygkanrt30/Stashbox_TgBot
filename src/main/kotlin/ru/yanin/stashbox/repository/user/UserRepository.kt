package ru.yanin.stashbox.repository.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.yanin.stashbox.domain.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Modifying
    @Query(
        value = """
            INSERT INTO tg_user (id, username, language_code)
            VALUES (:id, :username, :languageCode)
            ON CONFLICT (id) DO NOTHING
        """,
        nativeQuery = true,
    )
    fun insertIfNotExists(
        @Param("id") id: Long,
        @Param("username") username: String,
        @Param("languageCode") languageCode: String,
    ): Int
}
