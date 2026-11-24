package ru.yanin.stashbox.repository.section

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yanin.stashbox.domain.Section
import kotlin.uuid.Uuid

@Repository
interface SectionRepository : JpaRepository<Section, Uuid> {
    fun findAllByUserId(userId: Long): MutableSet<Section>
}
