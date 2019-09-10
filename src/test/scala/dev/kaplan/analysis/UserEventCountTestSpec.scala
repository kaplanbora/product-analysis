package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.UserEventCount.countEventsForUser
import dev.kaplan.event._
import org.apache.flink.api.scala._

class UserEventCountTestSpec extends TestSpec {
  "User event counter" should "only count given userId" in {
    val testInput = Seq(
      UserEvent(0, 0, View,  userId = 10),
      UserEvent(0, 0, View,  userId = 20),
      UserEvent(0, 0, Add,   userId = 20),
      UserEvent(0, 0, Add,   userId = 20),
      UserEvent(0, 0, Click, userId = 30),
      UserEvent(0, 0, Click, userId = 30),
      UserEvent(0, 0, Click, userId = 20),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = countEventsForUser(20, events).collect()
    val expectedOutput = Seq(
      View  -> 1,
      Add   -> 2,
      Click -> 1,
    )
    
    testOutput should contain theSameElementsAs expectedOutput
  }
}
