import enumeratum._
import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema
import sttp.tapir.codec.enumeratum.TapirCodecEnumeratum

object Main extends App {}

case class ClassWithOuter(i: Outer)       // compiles fine
case class ClassWithInner(i: Outer.Inner) // won't compile

object ClassWithInner {
  implicit val codec: Codec[ClassWithInner]   = deriveCodec[ClassWithInner]
  implicit val schema: Schema[ClassWithInner] = Schema.derived[ClassWithInner]
}

sealed trait Outer extends EnumEntry

object Outer extends Enum[Outer] with CirceEnum[Outer] with TapirCodecEnumeratum {

  case object TestOnj extends Outer
  sealed trait Inner  extends Outer

  object Inner extends Enum[Inner] with CirceEnum[Inner] with TapirCodecEnumeratum {
    case object TestInner extends Inner
    override def values: IndexedSeq[Inner] = findValues
  }

  // uncomment this to fix compile
  //implicit val innerSchema: Schema[Inner] = Inner.schemaForEnumEntry[Inner]

  override def values: IndexedSeq[Outer] = findValues
}

/*
 * ambiguous implicit values:
 both method schemaForEnumEntry in trait TapirCodecEnumeratum of type [E <: enumeratum.EnumEntry](implicit enum: enumeratum.Enum[E]): sttp.tapir.Schema[E]
 and method schemaForEnumEntry in trait TapirCodecEnumeratum of type [E <: enumeratum.EnumEntry](implicit enum: enumeratum.Enum[E]): sttp.tapir.Schema[E]
 match expected type sttp.tapir.Schema.Typeclass[Outer.Inner]
  implicit val schema: Schema[ClassWithInner] = Schema.derived[ClassWithInner]

 * magnolia: could not find Schema.Typeclass for type Outer.Inner
 in parameter 'i' of product type ClassWithInner
  implicit val schema: Schema[ClassWithInner] = Schema.derived[ClassWithInner]
 * */
