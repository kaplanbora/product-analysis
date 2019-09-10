package dev.kaplan.analysis

import dev.kaplan.TestSpec
import dev.kaplan.analysis.TopUser.findTopUsersByMostEvents
import dev.kaplan.event._
import org.apache.flink.api.scala._

class TopUserTestSpec extends TestSpec {
  "Top users finder" should "find users that have completed all events in descending order" in {
    val testInput = Seq(
      UserEvent(0, 0, View,    userId = 10),
      UserEvent(0, 0, Add,     userId = 10),
      UserEvent(0, 0, Click,   userId = 10),
      UserEvent(0, 0, View,    userId = 10),
      
      UserEvent(0, 0, View,    userId = 20),
      UserEvent(0, 0, Add,     userId = 20),
      UserEvent(0, 0, Click,   userId = 20),
      UserEvent(0, 0, View,    userId = 20),
      UserEvent(0, 0, Remove,  userId = 20),
      UserEvent(0, 0, Add,     userId = 20),
  
      UserEvent(0, 0, View,    userId = 30),
      UserEvent(0, 0, View,    userId = 30),
      UserEvent(0, 0, Add,     userId = 30),
      UserEvent(0, 0, Click,   userId = 30),
      UserEvent(0, 0, Remove,  userId = 30),
      UserEvent(0, 0, View,    userId = 30),
      UserEvent(0, 0, Add,     userId = 30),
      UserEvent(0, 0, Click,   userId = 30),
      UserEvent(0, 0, Click,   userId = 30),
      UserEvent(0, 0, Remove,  userId = 30),
    )
    
    val events = env.fromCollection(testInput)
    
    val testOutput = findTopUsersByMostEvents(5, events).collect()
    val expectedOutput = Seq(
      30 -> 2,
      20 -> 1,
    )
    
    testOutput should contain theSameElementsInOrderAs expectedOutput
  }
}
