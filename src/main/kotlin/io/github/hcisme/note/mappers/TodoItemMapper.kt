package io.github.hcisme.note.mappers

import org.apache.ibatis.annotations.Param

/**
 * 待办事项表 数据库操作接口
 */
interface TodoItemMapper<T, P> : BaseMapper<T, P> {
    /**
     * 根据Id更新
     */
    fun updateById(@Param("bean") t: T, @Param("id") id: Int): Int


    /**
     * 根据Id删除
     */
    fun deleteById(@Param("id") id: Int): Int


    /**
     * 根据Id获取对象
     */
    fun selectById(@Param("id") id: Int): T?
}
