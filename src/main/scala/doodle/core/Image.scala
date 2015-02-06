package doodle.core

sealed trait Image {
  def beside(right: Image): Image =
    Beside(this, right)

  def on(bottom: Image): Image =
    Overlay(this, bottom)

  def under(top: Image): Image =
    Overlay(top, this)

  def above(bottom: Image): Image =
    Above(this, bottom)

  def below(top: Image): Image =
    Above(top, this)

  def lineColour(colour: Colour): Image =
    ContextTransform(_.lineColour(colour), this)

  def lineWidth(width: Double): Image =
    ContextTransform(_.lineWidth(width), this)

  def fillColour(colour: Colour): Image =
    ContextTransform(_.fillColour(colour), this)
}
final case class Circle(r: Double) extends Image
final case class Rectangle(w: Double, h: Double) extends Image
final case class Triangle(w: Double, h: Double) extends Image
final case class Beside(l: Image, r: Image) extends Image
final case class Above(l: Image, r: Image) extends Image
final case class Overlay(t: Image, b: Image) extends Image
// final case class Path(elements: List[Path]) extends Image {
//   def +:(elt: PathElement): Path =
//     Path(elt +: elements)

//   def :+(elt: PathElement): Path =
//     Path(elements :+ elt)

//   def ++(path: Path): Path =
//     Path(elements ++ path.elements)
// }
final case class ContextTransform(f: DrawingContext => DrawingContext, image: Image) extends Image
