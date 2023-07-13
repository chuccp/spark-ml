package com.kanke.ml

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class MlApplication

object MlApplication extends App{
  SpringApplication.run(classOf[MlApplication], args :_*)
}
