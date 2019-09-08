package dev.kaplan

import org.apache.flink.api.scala._
import dev.kaplan.analysis.UniqueProductView.findUniqueProductViews
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.analysis.UserEventCount.countEventsForUser
import dev.kaplan.event.{RawUserEvent, UserEvent}

object ProductAnalysis {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    
    val rawEvents: DataSet[RawUserEvent] = env.readCsvFile[RawUserEvent](
      filePath = "/tmp/flink/case.csv",
      fieldDelimiter = "|", 
      ignoreFirstLine = true
    )
    
    val events: DataSet[UserEvent] = rawEvents.map(_.enrich)

    val uniqueProductViews = findUniqueProductViews(events)
    val uniqueEventCounts = findUniqueEventCounts(rawEvents)
    val eventCountsForUser = countEventsForUser(47, rawEvents)
    
    uniqueProductViews.print()
    uniqueEventCounts.print()
    eventCountsForUser.print()
    
    env.execute("Product Analysis")
  }
}
