package com.kanke.ml

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MlApplication

object MlApplication extends App{
  SpringApplication.run(classOf[MlApplication], args :_*)
}
