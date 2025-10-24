package io.github.hcisme.note.mappers

import org.apache.ibatis.annotations.Param

/**
 * APP版本管理表 数据库操作接口
 */
interface AppVersionMapper<T, P> : BaseMapper<T, P> {
    /**
     * 根据Id更新
     */
    fun updateById(@Param("bean") t: T, @Param("id") id: Long): Int


    /**
     * 根据Id删除
     */
    fun deleteById(@Param("id") id: Long): Int


    /**
     * 根据Id获取对象
     */
    fun selectById(@Param("id") id: Long): T?

    /**
     * 根据VersionCode更新
     */
    fun updateByVersionCode(@Param("bean") t: T, @Param("versionCode") versionCode: Int): Int


    /**
     * 根据VersionCode删除
     */
    fun deleteByVersionCode(@Param("versionCode") versionCode: Int): Int


    /**
     * 根据VersionCode获取对象
     */
    fun selectByVersionCode(@Param("versionCode") versionCode: Int): T?


    /**
     * 根据VersionName更新
     */
    fun updateByVersionName(@Param("bean") t: T, @Param("versionName") versionName: String): Int


    /**
     * 根据VersionName删除
     */
    fun deleteByVersionName(@Param("versionName") versionName: String): Int


    /**
     * 根据VersionName获取对象
     */
    fun selectByVersionName(@Param("versionName") versionName: String): T?

    fun selectMaxVersion(): T?
}
