package dev.kaplan

import org.apache.flink.api.scala.{AggregateDataSet, DataSet, ExecutionEnvironment}
import org.apache.flink.streaming.api.scala._

object ProductAnalysis {
  case class CaseRaw(date: Long, productId: Long, eventName: String, userId: Long)
 
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment

    val cases: DataSet[CaseRaw] = env.readCsvFile[CaseRaw](
      filePath = "/tmp/flink/case.csv",
      fieldDelimiter = "|", 
      ignoreFirstLine = true
    )

    println(cases.count())

    env.execute("Product Analysis")
  }
}
