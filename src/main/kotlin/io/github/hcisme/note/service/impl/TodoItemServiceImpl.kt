package io.github.hcisme.note.service.impl

import io.github.hcisme.note.entity.pojo.TodoItem
import io.github.hcisme.note.entity.query.TodoItemQuery
import io.github.hcisme.note.mappers.TodoItemMapper
import io.github.hcisme.note.service.TodoItemService
import jakarta.annotation.Resource
import org.springframework.stereotype.Service

/**
 * 待办事项表 业务接口实现
 */
@Service("todoItemService")
class TodoItemServiceImpl : TodoItemService {
    @Resource
    private lateinit var todoItemMapper: TodoItemMapper<TodoItem, TodoItemQuery>
}