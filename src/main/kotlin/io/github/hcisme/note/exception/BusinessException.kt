package io.github.hcisme.note.exception

import io.github.hcisme.note.entity.enums.ResponseCodeEnum

class BusinessException : RuntimeException {
    override var message: String? = null
        private set

    var codeEnum: ResponseCodeEnum? = null
        private set

    var code: Int? = null
        private set

    constructor(message: String) : super(message) {
        this.message = message
    }

    constructor(codeEnum: ResponseCodeEnum) : super(codeEnum.msg) {
        this.codeEnum = codeEnum
        this.code = codeEnum.code
        this.message = codeEnum.msg
    }

    constructor(e: Throwable) : super(e)

    constructor(message: String, e: Throwable) : super(message, e) {
        this.message = message
    }

    constructor(code: Int, message: String) : super(message) {
        this.code = code
        this.message = message
    }

    /**
     * 重写fillInStackTrace 业务异常不需要堆栈信息，提高效率.
     */
    override fun fillInStackTrace(): Throwable = this
}