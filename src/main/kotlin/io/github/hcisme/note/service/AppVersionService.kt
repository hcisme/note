package io.github.hcisme.note.service

import io.github.hcisme.note.entity.pojo.AppVersion
import io.github.hcisme.note.entity.query.AppVersionQuery


/**
 * APP版本管理表 业务接口
 */
interface AppVersionService {
    /**
     * 根据条件查询列表
     */
    fun findListByParam(param: AppVersionQuery): List<AppVersion>

    /**
     * 新增
     */
    fun add(bean: AppVersion): Int

    /**
     * 根据Id查询对象
     */
    fun getAppVersionById(id: Long): AppVersion?


    /**
     * 根据Id修改
     */
    fun updateAppVersionById(bean: AppVersion, id: Long): Int


    /**
     * 根据Id删除
     */
    fun deleteAppVersionById(id: Long): Int

    /**
     * 根据VersionCode查询对象
     */
    fun getAppVersionByVersionCode(versionCode: Int): AppVersion?

    /**
     * 根据VersionCode修改
     */
    fun updateAppVersionByVersionCode(bean: AppVersion, versionCode: Int): Int

    /**
     * 根据VersionCode删除
     */
    fun deleteAppVersionByVersionCode(versionCode: Int): Int

    /**
     * 根据VersionName查询对象
     */
    fun getAppVersionByVersionName(versionName: String): AppVersion?

    /**
     * 根据VersionName修改
     */
    fun updateAppVersionByVersionName(bean: AppVersion, versionName: String): Int

    /**
     * 根据VersionName删除
     */
    fun deleteAppVersionByVersionName(versionName: String): Int

    /**
     * 上传应用相关信息
     */
    fun uploadAppInfo(versionCode: Int, versionName: String, fileMd5: String, updateContent: String, uploadId: String)

    fun selectMaxVersion(): AppVersion?
}