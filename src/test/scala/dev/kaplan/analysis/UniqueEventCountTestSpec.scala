package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UniqueEventCountTestSpec extends TestSpec {
  "Unique event counter" should "not count duplicate events" in {
    val testInput = Seq(
      RawUserEvent(900, 100, "view", 30),
      RawUserEvent(900, 100, "view", 30),
      RawUserEvent(900, 100, "add", 30),
      RawUserEvent(902, 100, "add", 30),
      RawUserEvent(800, 200, "click", 20),
      RawUserEvent(800, 200, "click", 20),
      RawUserEvent(600, 200, "click", 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findUniqueEventCounts(events).collect()
    val expectedOutput = Seq(
      UniqueEventCount("view", 1),
      UniqueEventCount("add", 2),
      UniqueEventCount("click", 2),
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
