import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val a = scanner.nextInt()
    val b = scanner.nextInt()
    val c = scanner.nextInt()
    val d = scanner.nextInt()
    var maxValue = a

    maxValue = Math.max(maxValue, a)
    maxValue = Math.max(maxValue, b)
    maxValue = Math.max(maxValue, c)
    maxValue = Math.max(maxValue, d)

    print(maxValue)
    // write your code here
}