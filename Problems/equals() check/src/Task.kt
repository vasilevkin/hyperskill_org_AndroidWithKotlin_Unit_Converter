import java.util.*

data class Client(val name: String, val age: Int, val balance: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (name != other.name) return false
        if (age != other.age) return false

        return true
    }
}


fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val name1 = input.next()
    val age1 = input.nextInt()
    val balance1 = input.nextInt()
    val name2 = input.next()
    val age2 = input.nextInt()
    val balance2 = input.nextInt()

    val client1 = Client(name1, age1, balance1)
    val client2 = Client(name2, age2, balance2)

    print(client1 == client2)
//    print(client1.equals(client2))
    //implement your code here
}
