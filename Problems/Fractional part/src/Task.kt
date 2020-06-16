import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val number = scanner.nextDouble()
    val result = (number * 10).toInt() % 10

    print(result)
    // put your code here
}