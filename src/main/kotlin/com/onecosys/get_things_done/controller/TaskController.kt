package com.onecosys.get_things_done.controller

import com.onecosys.get_things_done.model.dto.TaskDto
import com.onecosys.get_things_done.model.request.TaskRequest
import com.onecosys.get_things_done.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("api")
class TaskController(private val service: TaskService) {

    @GetMapping("all-tasks")
    fun getAllTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(service.getAllTasks(), HttpStatus.OK)

    @GetMapping("open-tasks")
    fun getAllOpenTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(service.getAllOpenTasks(), HttpStatus.OK)

    @GetMapping("closed-tasks")
    fun getAllFinishedTasks(): ResponseEntity<List<TaskDto>> =
        ResponseEntity(service.getAllClosedTasks(), HttpStatus.OK)

    @GetMapping("task/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDto> =
        ResponseEntity(service.getTaskById(id), HttpStatus.OK)

    @PostMapping("create")
    fun createTask(@Valid @RequestBody taskRequest: TaskRequest): ResponseEntity<TaskDto> {
        val task = service.createTask(taskRequest)
        return ResponseEntity(
            TaskDto(
                task.id,
                task.description,
                task.isReminderSet,
                task.isTaskOpen,
                task.createdOn,
                task.startedOn,
                task.finishedOn,
                task.timeInterval,
                task.timeTaken,
                task.priority
            ), HttpStatus.OK
        )
    }

    @PutMapping("update")
    fun updateTaskWithObject(@Valid @RequestBody taskRequest: TaskRequest?): ResponseEntity<TaskDto> =
        ResponseEntity(service.updateTask(taskRequest), HttpStatus.OK)

    @PutMapping("update/{id}")
    fun updateTaskWithUri(@PathVariable id: Long, @Valid @RequestBody taskRequest: TaskRequest?): ResponseEntity<TaskDto> =
        ResponseEntity(service.updateTask(id, taskRequest), HttpStatus.OK)

    @DeleteMapping("delete/{id}")
    fun deleteTaskWithUri(@PathVariable id: Long): ResponseEntity<String> =
        ResponseEntity(service.deleteTask(id), HttpStatus.OK)

    @DeleteMapping("delete")
    fun deleteTaskWithParam(@RequestParam id: Long): ResponseEntity<String> =
        ResponseEntity(service.deleteTask(id), HttpStatus.OK)
}