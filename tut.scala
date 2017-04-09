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
  def re() = real
  def im() = imaginary
}

