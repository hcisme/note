package io.github.hcisme.note.service

import io.github.hcisme.note.entity.pojo.TodoItem
import io.github.hcisme.note.entity.query.TodoItemQuery


/**
 * 待办事项表 业务接口
 */
interface TodoItemService {
    fun findListByParam(param: TodoItemQuery): List<TodoItem>

    fun findCountByParam(param: TodoItemQuery): Int

    fun createTodoItem(userId: String, title: String, content: String, startTime: String, endTime: String)
}