package io.github.hcisme.note.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Access(
    /**
     * 是否需要登录权限
     */
    val isRequiredLoginAccess: Boolean = true,
)