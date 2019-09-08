package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UserEventCount.countEventsForUser
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UserEventCountTestSpec extends TestSpec {
  "User event counter" should "only count given userId" in {
    val testInput = Seq(
      RawUserEvent(0, 0, "view",  userId = 10),
      RawUserEvent(0, 0, "view",  userId = 20),
      RawUserEvent(0, 0, "add",   userId = 20),
      RawUserEvent(0, 0, "add",   userId = 20),
      RawUserEvent(0, 0, "click", userId = 30),
      RawUserEvent(0, 0, "click", userId = 30),
      RawUserEvent(0, 0, "click", userId = 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = countEventsForUser(20, events).collect()
    val expectedOutput = Seq(
      UserEventCount("view", 1),
      UserEventCount("add", 2),
      UserEventCount("click", 1),
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
