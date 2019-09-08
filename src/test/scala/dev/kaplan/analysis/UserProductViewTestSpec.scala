package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UserProductView.findViewedProductsForUser
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UserProductViewTestSpec extends TestSpec {
  "User product views finder" should "find views of only given user" in {
    val testInput = Seq(
      UserEvent(0, productId = 100, View,  userId = 30),
      UserEvent(0, productId = 100, Add,   userId = 30),
      UserEvent(0, productId = 100, Add,   userId = 30),
      UserEvent(0, productId = 200, Click, userId = 20),
      UserEvent(0, productId = 200, View,  userId = 20),
      UserEvent(0, productId = 200, View,  userId = 30),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findViewedProductsForUser(30, events).collect()
    val expectedOutput = Seq(100, 200)
    
    testOutput should contain theSameElementsAs expectedOutput
  }
  
  it should "find only view events" in {
    val testInput = Seq(
      UserEvent(0, productId = 100, View,   userId = 20),
      UserEvent(0, productId = 100, Add,    userId = 20),
      UserEvent(0, productId = 200, Click,  userId = 20),
      UserEvent(0, productId = 200, Remove, userId = 20),
      UserEvent(0, productId = 200, View,   userId = 20),
    )
  
    val events = env.fromCollection(testInput)
  
    val testOutput = findViewedProductsForUser(20, events).collect()
    val expectedOutput = Seq(100, 200)
  
    testOutput should contain theSameElementsAs expectedOutput
  }
}
