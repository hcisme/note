package io.github.hcisme.note.service

import io.github.hcisme.note.entity.pojo.TodoItem
import io.github.hcisme.note.entity.query.TodoItemQuery

/**
 * 待办事项表 业务接口
 */
interface TodoItemService {
    fun findListByParam(param: TodoItemQuery): List<TodoItem>

    fun findCountByParam(param: TodoItemQuery): Int

    fun createItem(
        userId: String,
        title: String,
        content: String,
        completed: Int,
        startTime: String,
        endTime: String
    )

    fun updateItem(
        id: Int,
        userId: String,
        title: String,
        content: String,
        completed: Int,
        startTime: String,
        endTime: String
    )

    fun deleteItem(todoItemId: Int)
}