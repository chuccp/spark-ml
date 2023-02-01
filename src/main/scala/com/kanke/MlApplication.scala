package com.kanke

import com.cokeframework.boot.CokeApplication
import com.cokeframework.context.annotation.CokeBootApplication

@CokeBootApplication object MlApplication {
  def main(array: Array[String]): Unit = {
    CokeApplication.run(MlApplication.getClass, array)
  }
}
