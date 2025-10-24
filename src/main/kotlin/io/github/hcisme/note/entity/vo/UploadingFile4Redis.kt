package io.github.hcisme.note.entity.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class UploadingFile4Redis : Serializable {
    var uploadId: String? = null
    var chunkIndex: Int? = null
    var chunks: Int? = null
    var fileSize: Long = 0L
    var filePath: String? = null
    var versionCode: Int? = null
}