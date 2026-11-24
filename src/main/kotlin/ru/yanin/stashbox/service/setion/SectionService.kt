package ru.yanin.stashbox.service.setion

import kotlin.uuid.Uuid

interface SectionService {
    fun loadAllSectionsByUserId(userId: Long): Map<String, Uuid>
}
