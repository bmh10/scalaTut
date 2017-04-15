import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._ // _ same as using * in Java

object HelloWorld {
  def main(args: Array[String]) {
    println("Hello world");
  }
}

// object keyword defines singleton instance
// static methods don't exist in Scala. Must use singleton objects instead.

// scalac is used to compile files
// To run : scala -classpath . <ClassName>

// Interfacing with Java

// java.lang imported by default
// can inherit Java class and implement Java interfaces directly

object FrenchDate {
  def main(args: Array[String]) {
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    println(df format now)
  }
}


// Everything is an object

// Numbers and functions are objects unlike in Java
// Scala's lexer using longest match rule for tokens

object Timer {
  def oncePerSecond(callback: () => Unit) { // Param is function. Unit same as void
    while (true) {
      callback()
      Thread sleep 1000
    }
  }

  def timeFlies() {
    println("time flies...) // Predefined println rather that System.out
  }

  def main(args: Array[String]) {
    oncePerSecond(timeFlies);
  }
}

// Anonymous functions

object TimerAnon {
  def oncePerSecond(callback: () => Unit) {
    while (true) { callback(); Thread sleep 1000 }
  }
  
  def main(args: Array[String]) {
    oncePerSecond(() => println("time flies...")) // Anon function
  }
}

// Classes

// Classes can have params

class Complex(real: Double, imaginary: Double) {
  def re() = real      // Note: no return types
  def im() = imaginary
}

// Methods w/o args

object ComplexNumbers {
  def main(args: Array[String]) {
    val c = new Complex(1.2, 3.4)
    println("Imaginary part: " + c.im())
  }
}

// So that we don't have to use (), we can define re and im without parenthesis.
class Complex2(real: Double, imaginary: Double) {
  def re = real
  def im = imaginary
}

// Inheritance and overriding

// All classes inherit from a super-class
// When no super-class specified, scala.AnyRef is used implicitly
// Overriding requires mandatory override keyword

class Complex3(real: Double, imaginary: Double) {
  def re = real
  def im = imaginary
  override def toString() = "" re + "+" + im + "i"
}

// Case classes and pattern matching

abstract class Tree
case class Sum(l: Tree, r: Tree) extends Tree
case class Var(n: String) extends Tree
case Class Const(v: Int) extends Tree

// Case classes are different from standard classes in that:
// new keyword not mandatory to create instances of these classes
// getter funcs are automatically defined
// default definitions for equals and hashCode are provided which work on the structure of the instances
// default toString implementation prints value in 'source form'
// instances of these classes can be decomposed through pattern matching

type Environment = String => Int

// Pattern matching example:
def eval(t: Tree, env: Environment): Int = t match {
  case Sum(l, r) => eval(l, env) + eval(r, env)
  case Var(n)    => env(n)
  case Const(v)  => v
}

// Another pattern matching example:
def derive(t: Tree, v: String): Tree = t match {
  case Sum(l,r) => Sum(derive(l,v), derive(r,v))
  case Var(n) if (v == n) => Const(1)             // Contains extra guard condition
  case _ => Const(0)                              // _ matches any value
}

def mainPatternMatch(args: Array[String]) {
  val exp: Tree = Sum(Sum(Var("x"), Var("x")), Sum(Const(7), Var("y")))
  val env: Environment = { case "x" => 5 case "y" => 7 }
  println("Expression: " + exp)
  println("Evaluation with x=5, y=7: " + eval(exp, env))
  println("Derivative relative to x:\n " + derive(exp, "x"))
  println("Derivative relative to y:\n " + derive(exp, "y"))
}

// Traits

// In addition to inheriting from super-class, Scala class can import code from one or several traits.

trait Ord {
  def <  (that: Any): Boolean
  def <= (that: Any) = this < that || this == that
  def >  (that: Any): !(this <= that)
  def >= (that: Any): !(this < that)
}

class Date(y: Int, m: Int, d: Int) extends Ord {
  def year = y
  def month = m
  def day = d

  override def toString(): String = year + "-" + month + "-" + day

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Date] && {
      val o = that.asInstanceOf[Date] &&
      o.day == day && o.month == month && o.year == year
    }

  def <(that: Any): Boolean = {
    if (!that.instanceOf[Date])
      error("cannot compare " ++ that ++ " and a Date")

    val o = that.asInstanceOf[Date]
    year < o.year || (year == o.year && (month < o.month || (month == o.month && day < o.day)))
  }
}

// Genericity

class Reference[T] {
  private var contents: T = _ // Default value

  def set(value: T) { contents = value }
  def get: T = contents
}


