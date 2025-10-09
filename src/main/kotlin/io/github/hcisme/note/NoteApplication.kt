package io.github.hcisme.note

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@MapperScan("io.github.hcisme.note.mappers")
@SpringBootApplication
class NoteApplication

fun main(args: Array<String>) {
    runApplication<NoteApplication>(*args)
}
