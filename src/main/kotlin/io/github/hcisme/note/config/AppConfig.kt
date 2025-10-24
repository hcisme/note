package io.github.hcisme.note.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Value("\${admin.account}")
    val account: String = ""

    @Value("\${admin.password}")
    val password: String = ""

    @Value("\${project.folder}")
    val projectFolder: String = ""
}