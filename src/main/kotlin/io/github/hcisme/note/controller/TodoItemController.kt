package io.github.hcisme.note.controller

import io.github.hcisme.note.annotation.Access
import io.github.hcisme.note.entity.pojo.TodoItem
import io.github.hcisme.note.entity.query.TodoItemQuery
import io.github.hcisme.note.entity.vo.CreateTodoItemVO
import io.github.hcisme.note.entity.vo.ResponseVO
import io.github.hcisme.note.entity.vo.UpdateTodoItemVO
import io.github.hcisme.note.service.TodoItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.annotation.Resource
import jakarta.validation.constraints.NotEmpty
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * 待办事项表 Controller
 */
@Tag(name = "待办事项管理")
@RestController("todoItemController")
@RequestMapping("/todoItem")
class TodoItemController : ABaseController() {
    @Resource
    private lateinit var todoItemService: TodoItemService

    @Operation(summary = "获取待办列表")
    @Access
    @Validated
    @GetMapping("/list")
    fun getTodoList(@RequestParam @NotEmpty(message = "时间不能为空") time: String): ResponseVO<List<TodoItem>> {
        val userInfo = getUserInfoByToken()!!

        val todoItemQuery = TodoItemQuery().apply {
            this.userId = userInfo.id
            this.createdTimeStart = time
            this.createdTimeEnd = time
            this.orderBy = "t.start_time ASC"
        }
        val items = todoItemService.findListByParam(todoItemQuery)
        return getSuccessResponseVO(items)
    }

    @Operation(summary = "通过id查询代办")
    @Access
    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Int): ResponseVO<TodoItem?> {
        val item = todoItemService.findTodoItemById(id = id)
        return getSuccessResponseVO(item)
    }

    @Operation(summary = "创建待办")
    @Access
    @PostMapping("/createItem")
    fun createItem(@Validated @RequestBody item: CreateTodoItemVO): ResponseVO<Any?> {
        val userInfo = getUserInfoByToken()!!
        todoItemService.createItem(
            userId = userInfo.id!!,
            title = item.title,
            content = item.content,
            completed = item.completed!!,
            startTime = item.startTime,
            endTime = item.endTime
        )
        return getSuccessResponseVO(null)
    }

    @Operation(summary = "根据 id 更新待办")
    @Access
    @PutMapping("/updateItem")
    fun updateItem(@Validated @RequestBody item: UpdateTodoItemVO): ResponseVO<Any?> {
        val userInfo = getUserInfoByToken()!!
        todoItemService.updateItem(
            id = item.id!!,
            userId = userInfo.id!!,
            title = item.title,
            content = item.content,
            completed = item.completed!!,
            startTime = item.startTime,
            endTime = item.endTime
        )
        return getSuccessResponseVO(null)
    }

    @Operation(summary = "删除待办")
    @Access
    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Int): ResponseVO<Any?> {
        todoItemService.deleteItem(id)
        return getSuccessResponseVO(null)
    }
}