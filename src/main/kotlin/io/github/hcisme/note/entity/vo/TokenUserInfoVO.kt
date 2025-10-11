package io.github.hcisme.note.entity.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class TokenUserInfoVO : Serializable {
    var id: String? = null
    var username: String? = null
    var email: String? = null
    var createdTime: String? = null
    var updatedTime: String? = null
    var lastLoginTime: String? = null
    var token: String? = null
    var expireAt: Long? = null
}