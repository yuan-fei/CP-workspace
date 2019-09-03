import java.util.ArrayList

fun sayHello(name:String) = "Hello e ${name}"

fun main(args: Array<String>) {
	var l = ArrayList<Int>()
	l.add(11)
	println(l.get(0))
	println(sayHello("fei"))
}