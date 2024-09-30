package episode11

import java.lang.Long.max

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

// episode3.readString() via sequence is still slightly faster than Scanner
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
    val q = readlnInt()

    repeat(q){
        val (n, h, b) = readlnInts()
        val board = arrayOf(readln(), readln())
        println(solve(n, h.toLong(), b.toLong(), board))
    }


}

fun solve(n: Int, h: Long, b: Long, board: Array<String>): Long {
    var posSheep = arrayOf(-1, -1)
    for (i in board.indices){
        for (j in board[0].indices){
            if (board[i][j] == 'S'){
                posSheep = arrayOf(i, j)
            }
        }
    }
    val cnts = arrayOf(arrayOf(0L, 0L, 0L),arrayOf(0L, 0L, 0L))
    for (i in board.indices){
        for (j in board[0].indices){
            if (board[i][j] == 'W'){
                if(j < posSheep[1]){
                    cnts[i][0] = cnts[i][0] + h
                }
                else if(j == posSheep[1]){
                    cnts[i][1] = cnts[i][1] + h
                }
                else{
                    cnts[i][2] = cnts[i][2] + h
                }
            }
        }
    }
    var ans = 0L
    // kill adjacent wolves
    if(board[1 - posSheep[0]][posSheep[1]] == 'W'){
        cnts[1 - posSheep[0]][1] = cnts[1 - posSheep[0]][1] - h
        ans += h
    }
    if(posSheep[1] - 1 >= 0 && board[posSheep[0]][posSheep[1] - 1] == 'W'){
        cnts[posSheep[0]][0] = cnts[posSheep[0]][0] - h
        ans += h
    }
    if(posSheep[1] + 1 < board[0].length && board[posSheep[0]][posSheep[1] + 1] == 'W'){
        cnts[posSheep[0]][2] = cnts[posSheep[0]][2] - h
        ans += h
    }
    // no wolves
    if(cnts[0].contentEquals(arrayOf(0, 0, 0)) && cnts[1].contentEquals(arrayOf(0, 0, 0))){
        return ans;
    }

    var blockOnly = (if (posSheep[1] > 0)  b else 0L) + b + (if (posSheep[1] < board[0].length - 1)  b else 0L)
    var killLeft = cnts[0][0] + cnts[1][0] + b + (if (posSheep[1] < board[0].length - 1)  b else 0L)
    var killRight = cnts[0][2] + cnts[1][2] +  (if (posSheep[1] > 0)  b else 0L) + b
    var killAll = cnts[0][0] + cnts[1][0] + cnts[0][2] + cnts[1][2]

    return ans + minOf<Long>(blockOnly, killLeft, killRight, killAll)
}
