package dev.kaplan

import org.apache.flink.api.scala.ExecutionEnvironment
import org.scalatest._

trait TestSpec extends FlatSpec with Matchers {
  val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
}
