package io.github.hcisme.note.entity.query

/**
 * 待办事项表参数
 */
class TodoItemQuery : BaseParam() {
    /**
     *
     */
    var id: Int? = null

    /**
     * 创建者ID
     */
    var userId: String? = null

    var userIdFuzzy: String? = null

    /**
     * 标题
     */
    var title: String? = null

    var titleFuzzy: String? = null

    /**
     * 内容
     */
    var content: String? = null

    var contentFuzzy: String? = null

    /**
     * 是否完成 (0-未完成, 1-已完成)
     */
    var isCompleted: Int? = null

    /**
     * 开始时间
     */
    var startTime: String? = null

    var startTimeStart: String? = null

    var startTimeEnd: String? = null

    /**
     * 结束时间
     */
    var endTime: String? = null

    var endTimeStart: String? = null

    var endTimeEnd: String? = null

    /**
     * 创建时间
     */
    var createdTime: String? = null

    var createdTimeStart: String? = null

    var createdTimeEnd: String? = null

    /**
     * 最后修改时间
     */
    var updatedTime: String? = null

    var updatedTimeStart: String? = null

    var updatedTimeEnd: String? = null
}