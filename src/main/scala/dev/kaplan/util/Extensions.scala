package dev.kaplan.util

object Extensions {
  implicit class LongOps(l: Long) {
    def tupled: Tuple1[Long] = Tuple1(l)
  }
}
