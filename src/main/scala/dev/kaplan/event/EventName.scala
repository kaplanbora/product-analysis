package dev.kaplan.event

sealed abstract class EventName(val name: String) {
  override def toString: String = name
}

case object Add    extends EventName("add")
case object View   extends EventName("view")
case object Remove extends EventName("remove")
case object Click  extends EventName("click")

object EventName {
  def fromString(stringName: String): EventName = stringName match {
    case "add"    => Add
    case "view"   => View
    case "remove" => Remove
    case "click"  => Click
    case _        => throw new Exception("Unknown event type")
  }
}
