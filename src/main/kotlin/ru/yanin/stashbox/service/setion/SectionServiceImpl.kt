package ru.yanin.stashbox.service.setion

import org.springframework.stereotype.Service
import ru.yanin.stashbox.repository.section.SectionRepository
import kotlin.uuid.Uuid

@Service
class SectionServiceImpl(
    private val sectionRepository: SectionRepository,
) : SectionService {
    override fun loadAllSectionsByUserId(userId: Long): Map<String, Uuid> = sectionRepository.findAllByUserId(userId)
        .associate { section -> section.title to section.id }
}
