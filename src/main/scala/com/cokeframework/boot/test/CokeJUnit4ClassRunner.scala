package com.cokeframework.boot.test

import com.cokeframework.boot.CokeApplication

object CokeJUnit4ClassRunner {


  def run(testInstance: Any): Unit = {
    CokeApplication.run(testInstance)
  }


}
