import java.util.ArrayList

fun sayHello(name:String) = "Hello e ${name}"

fun main(args: Array<String>) {
	var a = 10
	var b = 20
	a = b.also { b = a }
	println("$a $b")
}