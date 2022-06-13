package com.onecosys.get_things_done.service

import com.onecosys.get_things_done.dto.TaskDto
import com.onecosys.get_things_done.model.Task
import com.onecosys.get_things_done.repository.TaskRepository
import com.onecosys.get_things_done.request.CreateTaskRequest
import com.onecosys.get_things_done.request.UpdateTaskRequest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

internal class TaskServiceTest {

    @RelaxedMockK
    private lateinit var repository: TaskRepository

    private lateinit var objectUnderTest: TaskService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        objectUnderTest = TaskService(repository)
    }

    @AfterEach
    fun tearDown() {
        // to be implemented
    }

    @Test
    fun `when all tasks get fetched then check if the given size is correct`() {
        val expectedTasks = listOf(Task(), Task())
        every { repository.findAll() } returns expectedTasks.toMutableList()

        val actualList: List<TaskDto> = objectUnderTest.getAllTasks()
        assertThat(actualList.size).isEqualTo(expectedTasks.size)
    }

    @Test
    fun `when task gets created`() {
        val taskRequest = CreateTaskRequest("test task", false, false, LocalDateTime.now(), null, null, "0d", 0)
        val task = Task()
        task.description = taskRequest.description
        task.isReminderSet = taskRequest.isReminderSet
        task.isTaskOpen = taskRequest.isTaskOpen
        task.createdOn = taskRequest.createdOn
        task.startedOn = taskRequest.startedOn
        task.finishedOn = taskRequest.finishedOn
        task.timeTaken = taskRequest.timeTaken

        every { repository.save(any()) } returns task
        val actualTask: Task = objectUnderTest.createTask(taskRequest)

        assertThat(actualTask.description).isEqualTo(taskRequest.description)
    }

    @Test
    fun `when get task by id is called then expect a task with id 2`() {
        val actualTask = Task()
        every {repository.findTaskById(2) } returns actualTask
        val expectedTaskDto = objectUnderTest.getTaskById(2)
        assertThat(actualTask.taskId).isEqualTo(expectedTaskDto.id)
    }

/*    @Test
    fun `update the task`(){
        val actualTask = Task()
        Mockito.`when`(repository.findTaskById(2)).thenReturn(actualTask)
        Mockito.`when`(repository.save(actualTask)).thenReturn(actualTask)

        val updateTaskRequest = UpdateTaskRequest(222, "test task", false, false, LocalDateTime.now(), null, null, "0d", 0)
        val expectedDTo = objectUnderTest.updateTask(updateTaskRequest)
        assertThat(actualTask.taskId).isEqualTo(expectedDTo.id)
    }*/

    @Test
    fun `when delete task by id`() {

    }

}