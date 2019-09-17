package episode2

// https://kotlinlang.org/docs/tutorials/competitive-programming.html
// https://stackoverflow.com/questions/41283393/reading-console-input-in-kotlin

private fun readln() = readLine()!!
private fun readlnByte() = readln().toByte()
private fun readlnShort() = readln().toShort()
private fun readlnInt() = readln().toInt()
private fun readlnLong() = readln().toLong()
private fun readlnFloat() = readln().toFloat()
private fun readlnDouble() = readln().toDouble()
private fun readlnBigInt(radix: Int = 10) = readln().toBigInteger(radix)
private fun readlnBigDecimal() = readln().toBigDecimal()

private fun lineSequence(limit: Int = Int.MAX_VALUE) = generateSequence { readLine() }.constrainOnce().take(limit)
private fun readlnStrings() = readln().split(' ')
private fun readlnBytes() = readlnStrings().map { it.toByte() }
private fun readlnShorts() = readlnStrings().map { it.toShort() }
private fun readlnInts() = readlnStrings().map { it.toInt() }
private fun readlnLongs() = readlnStrings().map { it.toLong() }
private fun readlnFloats() = readlnStrings().map { it.toFloat() }
private fun readlnDoubles() = readlnStrings().map { it.toDouble() }

private fun readByteArray() = readlnStrings().run { ByteArray(size) { get(it).toByte() } }
private fun readShortArray() = readlnStrings().run { ShortArray(size) { get(it).toShort() } }
private fun readIntArray() = readlnStrings().run { IntArray(size) { get(it).toInt() } }
private fun readLongArray() = readlnStrings().run { LongArray(size) { get(it).toLong() } }
private fun readFloatArray() = readlnStrings().run { FloatArray(size) { get(it).toFloat() } }
private fun readDoubleArray() = readlnStrings().run { DoubleArray(size) { get(it).toDouble() } }

private fun readlnByteArray(n: Int) = ByteArray(n) { readlnByte() }
private fun readlnShortArray(n: Int) = ShortArray(n) { readlnShort() }
private fun readlnIntArray(n: Int) = IntArray(n) { readlnInt() }
private fun readlnLongArray(n: Int) = LongArray(n) { readlnLong() }
private fun readlnFloatArray(n: Int) = FloatArray(n) { readlnFloat() }
private fun readlnDoubleArray(n: Int) = DoubleArray(n) { readlnDouble() }

private fun readByteArray2d(rows: Int, cols: Int) = Array(rows) { readByteArray().also { require(it.size == cols) } }
private fun readShortArray2d(rows: Int, cols: Int) = Array(rows) { readShortArray().also { require(it.size == cols) } }
private fun readLongArray2d(rows: Int, cols: Int) = Array(rows) { readLongArray().also { require(it.size == cols) } }
private fun readIntArray2d(rows: Int, cols: Int) = Array(rows) { readIntArray().also { require(it.size == cols) } }
private fun readFloatArray2d(rows: Int, cols: Int) = Array(rows) { readFloatArray().also { require(it.size == cols) } }
private fun readDoubleArray2d(rows: Int, cols: Int) =
    Array(rows) { readDoubleArray().also { require(it.size == cols) } }


private fun isWhiteSpace(c: Char) = c in " \r\n\t"

// JVM-only targeting code follows next

// readString() via sequence is still slightly faster than Scanner
private fun readString() = generateSequence { System.`in`.read().toChar() }
    .dropWhile { isWhiteSpace(it) }.takeWhile { !isWhiteSpace(it) }.joinToString("")

private fun readByte() = readString().toByte()
private fun readShort() = readString().toShort()
private fun readInt() = readString().toInt()
private fun readLong() = readString().toLong()
private fun readFloat() = readString().toFloat()
private fun readDouble() = readString().toDouble()
private fun readBigInt(radix: Int = 10) = readString().toBigInteger(radix)
private fun readBigDecimal() = readString().toBigDecimal()

private fun readBytes(n: Int) = generateSequence { readByte() }.take(n)
private fun readShorts(n: Int) = generateSequence { readShort() }.take(n)
private fun readInts(n: Int) = generateSequence { readInt() }.take(n)
private fun readLongs(n: Int) = generateSequence { readLong() }.take(n)
private fun readFloats(n: Int) = generateSequence { readFloat() }.take(n)
private fun readDoubles(n: Int) = generateSequence { readDouble() }.take(n)

private fun printIntArray(a: IntArray) {
    println(a.joinToString(", "))
}

private fun printLongArray(a: LongArray) {
    println(a.joinToString(", "))
}

private fun printDoubleArray(a: DoubleArray) {
    println(a.joinToString(", "))
}

private fun printFloatArray(a: FloatArray) {
    println(a.joinToString(", "))
}

private fun printStringArray(a: Array<String>) {
    println(a.joinToString(", "))
}


private fun main() {
    val n = readlnInt()
    val a = readlnInts()
    val m = mutableMapOf<Int, Int>()
    for (i in a){
        m[i]= m.getOrElse(i, {0}) + 1
    }
    var ub = 1
    while (ub <= n){
        if(!m.containsKey(ub) || m[ub]!=2){
            break;
        }
        ub++
    }
    val leftmost = IntArray(ub){-1}
    val rightmost = IntArray(ub){-1}
    for (i in 0 until n){
        if(a[i] < ub){
            if(leftmost[a[i]] == -1){
                leftmost[a[i]] = i
            }
            else{
                rightmost[a[i]] = i
            }
        }
    }
    val leftmostTree = build(0,n)
    val rightmostTree = build(0,n)
    var good = 1
    while (good < ub){
        inc(leftmostTree, leftmost[good])
        inc(rightmostTree, rightmost[good])
        val q1 = query(leftmostTree, 0, leftmost[good])
        val q2 = query(rightmostTree, 0, rightmost[good])
        if(q1 != q2){
            break;
        }
        good++
    }
    val sb = StringBuilder()
    for (i in 0 until n){
        if(a[i] < good){
            if(leftmost[a[i]] == i){
                sb.append('R')
            }
            else{
                sb.append('G')
            }
        }
        else{
            sb.append('B')
        }
    }
    println(sb.toString())
}

private data class Node(var v: Int, val lb:Int, val ub:Int, var left: Node?, var right: Node?)

private fun build(left:Int, right:Int):Node{
    val n:Node = Node(0, left, right, null, null )
    if(left < right){
        n.left = build(left, (left+right)/2)
        n.right = build((left+right)/2+1, right)
        n.v = n.left!!.v + n.right!!.v
    }

    return n
}

private fun inc(n:Node, i:Int){
    if(n.lb <= i && n.ub >= i){
        if(n.lb == n.ub){
            n.v += 1
        }
        else if(n.left != null && n.right != null){
            inc(n.left!!, i)
            inc(n.right!!, i)
            n.v = n.left!!.v + n.right!!.v
        }
    }
}

private fun query(n:Node?, left:Int, right:Int):Int{
    if(n == null){
        return 0
    }
    if(n.lb > right || n.ub < left){
        return 0
    }
    if(left <= n.lb && n.ub <= right){
        return n.v
    }
    return query(n.left, left, right) + query(n.right, left, right)
}