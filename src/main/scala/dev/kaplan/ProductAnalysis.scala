package dev.kaplan


import org.apache.flink.api.scala._
import dev.kaplan.analysis.UniqueProductViewCount.findUniqueProductViews
import dev.kaplan.analysis.UniqueEventCount.findUniqueEventCounts
import dev.kaplan.analysis.UserEventCount.countEventsForUser
import dev.kaplan.analysis.UserProductView.findViewedProductsForUser
import dev.kaplan.analysis.TopUser.findTopUsersByMostEvents
import dev.kaplan.event.{RawUserEvent, UserEvent}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.core.fs.FileSystem

object ProductAnalysis {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    
    val inputFile = ParameterTool.fromArgs(args).get("input-file")
    val outputDir = ParameterTool.fromArgs(args).get("output-dir")
    val numOfTopUsers = ParameterTool.fromArgs(args).getInt("top-users")
    val userIdForEvents = ParameterTool.fromArgs(args).getLong("user-id-for-events")
    val userIdForProducts = ParameterTool.fromArgs(args).getLong("user-id-for-products")
    
    val rawEvents: DataSet[RawUserEvent] = env.readCsvFile[RawUserEvent](
      filePath = inputFile,
      fieldDelimiter = "|", 
      ignoreFirstLine = true
    )
    
    val events: DataSet[UserEvent] = rawEvents.map(_.enrich)
    
    val solutionsInOrder = List(
      findUniqueProductViews(events),
      findUniqueEventCounts(events),
      findTopUsersByMostEvents(numOfTopUsers, events),
      countEventsForUser(userIdForEvents, events),
      findViewedProductsForUser(userIdForProducts, events),
    )
    
    solutionsInOrder.zipWithIndex.map { case (solution, number) =>
        solution.writeAsCsv(s"$outputDir/problem-${number + 1}.txt", fieldDelimiter = "|", writeMode = FileSystem.WriteMode.OVERWRITE)
    }
    
    env.execute("Product Analysis")
  }
}
