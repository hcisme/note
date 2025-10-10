package io.github.hcisme.note.mappers

import org.apache.ibatis.annotations.Param

interface BaseMapper<T, P> {
    /**
     * selectList:(根据参数查询集合)
     */
    fun selectList(@Param("query") p: P): List<T>

    /**
     * selectCount:(根据集合查询数量)
     */
    fun selectCount(@Param("query") p: P): Int

    /**
     * insert:(插入)
     */
    fun insert(@Param("bean") t: T): Int

    /**
     * insertOrUpdate:(插入或者更新)
     */
    fun insertOrUpdate(@Param("bean") t: T): Int

    /**
     * insertBatch:(批量插入)
     */
    fun insertBatch(@Param("list") list: List<T>): Int

    /**
     * insertOrUpdateBatch:(批量插入或更新)
     */
    fun insertOrUpdateBatch(@Param("list") list: List<T>): Int

    /**
     * updateByParam:(多条件更新)
     */
    fun updateByParam(@Param("bean") t: T, @Param("query") p: P): Int

    /**
     * deleteByParam:(多条件删除)
     */
    fun deleteByParam(@Param("query") p: P): Int
}