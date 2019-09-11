package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UniqueEventCountTestSpec extends TestSpec {
  "Unique event counter" should "not count duplicate events" in {
    val testInput = Seq(
      UserEvent(0, 0, View,  userId = 30),
      UserEvent(0, 0, View,  userId = 30),
      UserEvent(0, 0, Add,   userId = 20),
      UserEvent(0, 0, Add,   userId = 30),
      UserEvent(0, 0, Click, userId = 10),
      UserEvent(0, 0, Click, userId = 20),
      UserEvent(0, 0, Click, userId = 40),
      UserEvent(0, 0, Click, userId = 40),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findUniqueEventCounts(events).collect()
    val expectedOutput = Seq(
      View  -> 1,
      Add   -> 2,
      Click -> 3,
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
