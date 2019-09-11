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
    
    val events: DataSet[UserEvent] = env.readCsvFile[RawUserEvent](
      filePath = inputFile,
      fieldDelimiter = "|", 
      ignoreFirstLine = true
    ).map(_.enrich)
    
    val solutions = Map(
      "problem1" -> findUniqueProductViews(events),
      "problem2" -> findUniqueEventCounts(events),
      "problem3" -> findTopUsersByMostEvents(numOfTopUsers, events),
      "problem4" -> countEventsForUser(userIdForEvents, events),
      "problem5" -> findViewedProductsForUser(userIdForProducts, events),
    )
    
    solutions.map { case (problem, solution) =>
        solution.writeAsCsv(s"$outputDir/$problem.txt", fieldDelimiter = "|", writeMode = FileSystem.WriteMode.OVERWRITE)
    }
    
    env.execute("Product Analysis")
  }
}
