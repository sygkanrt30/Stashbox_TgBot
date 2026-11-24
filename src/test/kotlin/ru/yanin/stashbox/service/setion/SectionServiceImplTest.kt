package ru.yanin.stashbox.service.setion

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import ru.yanin.stashbox.domain.Section
import ru.yanin.stashbox.domain.User
import ru.yanin.stashbox.repository.section.SectionRepository

class SectionServiceImplTest {
    private lateinit var sectionRepository: SectionRepository
    private lateinit var sectionService: SectionServiceImpl

    @BeforeEach
    fun setUp() {
        sectionRepository = mock(SectionRepository::class.java)
        sectionService = SectionServiceImpl(sectionRepository)
    }

    @Test
    fun `loadAllSectionsByUserId returns map of titles to ids`() {
        val user = User(id = 42L, username = "john", languageCode = "en")
        val first = Section(title = "first", user = user)
        val second = Section(title = "second", user = user)
        `when`(sectionRepository.findAllByUserId(42L)).thenReturn(mutableSetOf(first, second))

        val result = sectionService.loadAllSectionsByUserId(42L)

        assertThat(result).isEqualTo(mapOf("first" to first.id, "second" to second.id))
        verify(sectionRepository).findAllByUserId(42L)
        verifyNoMoreInteractions(sectionRepository)
    }

    @Test
    fun `loadAllSectionsByUserId returns empty map when user has no sections`() {
        `when`(sectionRepository.findAllByUserId(42L)).thenReturn(mutableSetOf())

        val result = sectionService.loadAllSectionsByUserId(42L)

        assertThat(result).isEmpty()
        verify(sectionRepository).findAllByUserId(42L)
        verifyNoMoreInteractions(sectionRepository)
    }
}
