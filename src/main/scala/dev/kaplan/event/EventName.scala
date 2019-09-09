package dev.kaplan.event

sealed case class EventName(name: String) {
  override def toString: String = name
}

object Add    extends EventName("add")
object View   extends EventName("view")
object Remove extends EventName("remove")
object Click  extends EventName("click")

object EventName {
  def fromString(stringName: String): EventName = stringName match {
    case "add"    => Add
    case "view"   => View
    case "remove" => Remove
    case "click"  => Click
    case _        => throw new Exception("Unknown event type")
  }
}
