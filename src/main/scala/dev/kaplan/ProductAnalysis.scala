package dev.kaplan

import org.apache.flink.api.scala._
import com.typesafe.scalalogging.Logger
import dev.kaplan.analysis.UniqueProductView.findUniqueProductViews
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.event.{RawUserEvent, UserEvent}

object ProductAnalysis {
  def main(args: Array[String]): Unit = {
    val logger = Logger(this.getClass)
    val env = ExecutionEnvironment.getExecutionEnvironment
    
    val rawEvents: DataSet[RawUserEvent] = env.readCsvFile[RawUserEvent](
      filePath = "/tmp/flink/case.csv",
      fieldDelimiter = "|", 
      ignoreFirstLine = true
    )
    
    val events: DataSet[UserEvent] = rawEvents.map(_.enrich)

    val uniqueProductViews = findUniqueProductViews(events)
    val uniqueEventCounts = findUniqueEventCounts(rawEvents)
    
    uniqueProductViews.print()
    uniqueEventCounts.print()
    
    env.execute("Product Analysis")
  }
}
