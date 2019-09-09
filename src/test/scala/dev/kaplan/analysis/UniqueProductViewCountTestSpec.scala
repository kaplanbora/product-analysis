package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UniqueProductViewCount.findUniqueProductViews
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UniqueProductViewCountTestSpec extends TestSpec {
  "Unique product view counter" should "count only view events" in {
    val testInput = Seq(
      UserEvent(0, productId = 100, View,   userId = 30),
      UserEvent(0, productId = 100, View,   userId = 20),
      UserEvent(0, productId = 100, Remove, userId = 40),
      UserEvent(0, productId = 200, Add,    userId = 20),
      UserEvent(0, productId = 200, View,   userId = 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findUniqueProductViews(events).collect()
    val expectedOutput = Seq(
      UniqueProductViewCount(productId = 100, uniqueViews = 2),
      UniqueProductViewCount(productId = 200, uniqueViews = 1),
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
  
  it should "count only unique events" in {
    val testInput = Seq(
      UserEvent(0, productId = 100, View, userId = 30),
      UserEvent(0, productId = 100, View, userId = 30),
      UserEvent(0, productId = 100, View, userId = 20),
      UserEvent(0, productId = 200, View, userId = 40),
      UserEvent(0, productId = 200, View, userId = 40),
      UserEvent(0, productId = 300, View, userId = 30),
      UserEvent(0, productId = 300, View, userId = 30),
      UserEvent(0, productId = 400, View, userId = 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findUniqueProductViews(events).collect()
    val expectedOutput = Seq(
      UniqueProductViewCount(productId = 100, uniqueViews = 2),
      UniqueProductViewCount(productId = 200, uniqueViews = 1),
      UniqueProductViewCount(productId = 300, uniqueViews = 1),
      UniqueProductViewCount(productId = 400, uniqueViews = 1),
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
