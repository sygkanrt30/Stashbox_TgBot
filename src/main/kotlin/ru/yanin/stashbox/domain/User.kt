package ru.yanin.stashbox.domain

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.time.LocalDateTime

@Entity
@Table(name = "tg_user")
class User(
    @Id
    var id: Long,
    @Column(nullable = false, length = 50)
    var username: String,
    @Column(name = "language_code", length = 7)
    var languageCode: String,
    @Column(name = "start_use_bot_at")
    var startUseBotAt: LocalDateTime = LocalDateTime.now(),
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as User

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    override fun toString(): String = "User(username='$username', languageCode='$languageCode', startUseBotAt=$startUseBotAt)"
}
