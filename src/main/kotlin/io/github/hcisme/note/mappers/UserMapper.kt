package io.github.hcisme.note.mappers

import org.apache.ibatis.annotations.Param

/**
 * 用户表 数据库操作接口
 */
interface UserMapper<T, P> : BaseMapper<T, P> {
    /**
     * 根据Id更新
     */
    fun updateById(@Param("bean") t: T, @Param("id") id: String): Int


    /**
     * 根据Id删除
     */
    fun deleteById(@Param("id") id: String): Int


    /**
     * 根据Id获取对象
     */
    fun selectById(@Param("id") id: String): T?


    /**
     * 根据email更新
     */
    fun updateByEmail(@Param("bean") t: T, @Param("email") email: String): Int


    /**
     * 根据email删除
     */
    fun deleteByEmail(@Param("email") email: String): Int


    /**
     * 根据email获取对象
     */
    fun selectByEmail(@Param("email") email: String): T?
}
