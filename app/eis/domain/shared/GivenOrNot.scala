package eis.domain.shared

/**
 * Author: Wang Yibin
 * Date: 13-4-12
 * Time: 上午10:40
 */
sealed abstract class GivenOrNot[+A] {
    /** Returns true if the option is $none, false otherwise.
      */
    def isEmpty: Boolean

    /** Returns true if the option is an instance of $some, false otherwise.
      */
    def isDefined: Boolean = !isEmpty

    /** Returns the option's value.
      *  @note The option must be nonEmpty.
      *  @throws Predef.NoSuchElementException if the option is empty.
      */
    def get: A

    /** Returns the option's value if the option is nonempty, otherwise
      * return the result of evaluating `default`.
      *
      *  @param default  the default expression.
      */
    @inline final def getOrElse[B >: A](default: => B): B =
        if (isEmpty) default else this.get

    @inline final def map[B](f: A => B): GivenOrNot[B] =
        if (isEmpty) NotGiven else Given(f(this.get))

    @inline def toOption: Option[A] = if (isEmpty) None else Some(get)

    /** Returns the result of applying $f to this $option's value if
      * this $option is nonempty.
      * Returns $none if this $option is empty.
      * Slightly different from `map` in that $f is expected to
      * return an $option (which could be $none).
      *
      *  @param  f   the function to apply
      *  @see map
      *  @see foreach
      */
    @inline final def flatMap[B](f: A => GivenOrNot[B]): GivenOrNot[B] =
        if (isEmpty) NotGiven else f(this.get)
}

case object NotGiven extends GivenOrNot[Nothing] {
    def isEmpty = true
    def get = throw new NoSuchElementException("NotExisted.get")
}
final case class Given[+A](x: A) extends GivenOrNot[A] {
    def isEmpty = false
    def get = x
}
