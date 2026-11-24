package ru.yanin.stashbox.domain

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.proxy.HibernateProxy
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime
import kotlin.uuid.Uuid

@Entity
@Table(
    name = "section",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_section_user_title", columnNames = ["user_id", "title"]),
    ],
)
class Section(
    @Id
    @Column(name = "id", nullable = false, length = 64)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var id: Uuid = Uuid.random(),
    @Column(name = "title", nullable = false, length = 25)
    var title: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as Section

        return id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    override fun toString(): String = "Section(id=$id, title='$title', createdAt=$createdAt)"
}
