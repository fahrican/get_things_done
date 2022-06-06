package com.onecosys.get_things_done.service

import com.onecosys.get_things_done.dto.TaskDto
import com.onecosys.get_things_done.model.Task
import com.onecosys.get_things_done.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TaskService(private val repository: TaskRepository) {


    fun getAllTasks(): List<TaskDto> = repository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList())


    private fun convertEntityToDto(task: Task): TaskDto {
        return TaskDto(task.taskId, task.description, task.isReminderSet, task.isTaskOpen, task.createdOn, task.startedOn, task.finishedOn, task.timeInterval, task.timeTaken)
    }
}