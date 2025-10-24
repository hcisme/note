package io.github.hcisme.note.entity.constants

object Constants {
    private const val REDIS_KEY_PREFIX = "note"

    /**
     * redis 相关
     */
    const val REDIS_KEY_CAPTCHA_KEY = "$REDIS_KEY_PREFIX:captcha:"

    const val TOKEN_KEY = "token"

    /**
     * token
     */
    const val REDIS_KEY_TOKEN = "$REDIS_KEY_PREFIX:$TOKEN_KEY:"

    /**
     * 密码正则
     *
     *  密码必须是8到18个字符，至少包含一个数字，至少包含一个字母，可以包含特殊字符 ~!@#$%^&*_
     */
    const val REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,18}$"

    /**
     * 单位 ms
     *
     * 一分钟
     */
    const val REDIS_TIME_1MIN = 60000

    /**
     * 单位 ms
     *
     * 一天
     */
    const val REDIS_KEY_EXPIRES_ONE_DAY = REDIS_TIME_1MIN * 60 * 24

    /**
     * token 失效时间 15天 单位 ms
     */
    const val REDIS_KEY_TOKEN_EXPIRES_15_DAY = REDIS_KEY_EXPIRES_ONE_DAY * 15

    const val ZERO = 0
    const val LENGTH_11 = 11
    const val LENGTH_8 = 8

    const val USERID_PREFIX_KEY = "U"

    const val UPLOAD_FOLDER = "file/"

    const val UPLOAD_APP_TEMP_FOLDER = "temp/"
    const val UPLOAD_APP_FOLDER = "app/"
    const val APP_SUFFIX = ".apk"
    const val TEMP_APP_SUFFIX = ".part"

    const val REDIS_KEY_UPLOADING_FILE = "$REDIS_KEY_PREFIX:uploading"
}
