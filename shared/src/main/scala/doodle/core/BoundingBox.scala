package doodle.core

/**
  * Represents a bounding box around an image. The coordinate system
  * follows the canvas convention, meaning that the origin is the top
  * left. However, the coordinate system is not the global coordinate
  * system of the canvas but one that is local to the image. We
  * require the image is centered at the origin, and thus left and top
  * will usually be negative.
  */
final case class BoundingBox(left: Double, top: Double, right: Double, bottom: Double) {
  // TODO: Are bounding boxes ever not symmetric around any given
  // axis? Can we replace l/t/r/b with just width and height?
  val height: Double =
    bottom - top

  val width: Double =
    right - left
}

object BoundingBox {
  def apply(image: Image): BoundingBox = image match {
    case Circle(r) =>
      BoundingBox(-r, -r, r, r)

    case Rectangle(w, h) =>
      BoundingBox(-w/2, -h/2, w/2, h/2)

    case Triangle(w, h) =>
      BoundingBox(-w/2, -h/2, w/2, h/2)

    case Overlay(t, b) =>
      val BoundingBox(l1, t1, r1, b1) = BoundingBox(t)
      val BoundingBox(l2, t2, r2, b2) = BoundingBox(b)
      BoundingBox(l1 min l2, t1 min t2, r1 max r2, b1 max b2)

    case Beside(l, r) =>
      val boxL = BoundingBox(l)
      val boxR = BoundingBox(r)
      BoundingBox(
        -(boxL.width + boxR.width) / 2,
        boxL.top min boxR.top,
        (boxL.width + boxR.width) / 2,
        boxL.bottom max boxR.bottom
      )

    case Above(t, b) =>
      val boxT = BoundingBox(t)
      val boxB = BoundingBox(b)

      BoundingBox(
        boxT.left min boxB.left,
        -(boxT.height + boxB.height) / 2,
        boxT.right max boxB.right,
        (boxT.height + boxB.height) / 2
      )

    case ContextTransform(f, i) =>
      BoundingBox(i)

    case d: Drawable =>
      BoundingBox(d.draw)
  }
}
