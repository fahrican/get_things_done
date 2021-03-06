package com.onecosys.get_things_done.model.dto

import com.onecosys.get_things_done.model.Priority
import java.time.LocalDateTime

data class TaskDto(
    val id: Long?,
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    val createdOn: LocalDateTime,
    val startedOn: LocalDateTime?,
    val finishedOn: LocalDateTime?,
    val timeInterval: String,
    val timeTaken: Int?,
    val priority: Priority
)