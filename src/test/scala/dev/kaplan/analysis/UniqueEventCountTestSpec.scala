package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UniqueEventCountTestSpec extends TestSpec {
  "Unique event counter" should "not count duplicate events" in {
    val testInput = Seq(
      UserEvent(900, 100, View,  30),
      UserEvent(900, 100, View,  30),
      UserEvent(900, 100, Add,   30),
      UserEvent(902, 100, Add,   30),
      UserEvent(800, 200, Click, 20),
      UserEvent(800, 200, Click, 20),
      UserEvent(600, 200, Click, 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findUniqueEventCounts(events).collect()
    val expectedOutput = Seq(
      UniqueEventCount(View, 1),
      UniqueEventCount(Add, 2),
      UniqueEventCount(Click, 2),
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
