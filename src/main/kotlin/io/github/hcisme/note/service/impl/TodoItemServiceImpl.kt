package io.github.hcisme.note.service.impl

import io.github.hcisme.note.entity.pojo.TodoItem
import io.github.hcisme.note.entity.query.TodoItemQuery
import io.github.hcisme.note.mappers.TodoItemMapper
import io.github.hcisme.note.service.TodoItemService
import io.github.hcisme.note.utils.toDate
import jakarta.annotation.Resource
import org.springframework.stereotype.Service
import java.util.*

/**
 * 待办事项表 业务接口实现
 */
@Service("todoItemService")
class TodoItemServiceImpl : TodoItemService {
    @Resource
    private lateinit var todoItemMapper: TodoItemMapper<TodoItem, TodoItemQuery>

    override fun findListByParam(param: TodoItemQuery): List<TodoItem> {
        return this.todoItemMapper.selectList(param);
    }

    override fun findCountByParam(param: TodoItemQuery): Int {
        return this.todoItemMapper.selectCount(param);
    }

    override fun createItem(
        userId: String,
        title: String,
        content: String,
        completed: Int,
        startTime: String,
        endTime: String
    ) {
        val date = Date()

        val todoItem = TodoItem().apply {
            this.userId = userId
            this.title = title
            this.content = content
            this.completed = completed
            this.startTime = startTime.toDate()
            this.endTime = endTime.toDate()
            this.createdTime = date
            this.updatedTime = date
        }

        todoItemMapper.insert(todoItem)
    }

    override fun updateItem(
        id: Int,
        userId: String,
        title: String,
        content: String,
        completed: Int,
        startTime: String,
        endTime: String
    ) {
        val param = TodoItemQuery().apply {
            this.id = id
            this.userId = userId
        }

        val item = TodoItem().apply {
            this.id = id
            this.userId = userId
            this.title = title
            this.content = content
            this.completed = completed
            this.startTime = startTime.toDate()
            this.endTime = endTime.toDate()
            this.updatedTime = Date()
        }

        todoItemMapper.updateByParam(item, param)
    }

    override fun deleteItem(todoItemId: Int) {
        todoItemMapper.deleteById(todoItemId)
    }
}