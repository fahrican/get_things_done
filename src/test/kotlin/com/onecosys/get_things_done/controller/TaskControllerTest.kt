package com.onecosys.get_things_done.controller

import com.onecosys.get_things_done.dto.TaskDto
import com.onecosys.get_things_done.service.TaskService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.time.LocalDateTime


@RunWith(SpringRunner::class)
@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TaskController::class])
internal class TaskControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var mockService: TaskService

    val dummyDto1 = TaskDto(
        33, "test1",
        isReminderSet = false,
        isTaskOpen = false,
        createdOn = LocalDateTime.now(),
        startedOn = null,
        finishedOn = null,
        timeInterval = "1d",
        timeTaken = 1
    )

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `given tasks when fetch happen then check for size`() {
        val taskDto2 = TaskDto(
            44, "test2",
            isReminderSet = false,
            isTaskOpen = false,
            createdOn = LocalDateTime.now(),
            startedOn = null,
            finishedOn = null,
            timeInterval = "2d",
            timeTaken = 2
        )

        `when`(mockService.getAllTasks()).thenReturn(listOf(dummyDto1, taskDto2))

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size()").value(2))
    }

    @Test
    fun `given one task`() {
        `when`(mockService.getTaskById(33)).thenReturn(dummyDto1)

        mockMvc.perform(MockMvcRequestBuilders.get("/task/${dummyDto1.id}"))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description").value("test1"));
    }
}