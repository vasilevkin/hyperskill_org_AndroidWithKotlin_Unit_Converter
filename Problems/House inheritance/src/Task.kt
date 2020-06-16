fun main() {
    val rooms = readLine()!!.toInt()
    val price = readLine()!!.toInt()
    val house = House(rooms, price)
    println(totalPrice(house))
}

class House(val rooms: Int, val price: Int) {}

fun totalPrice(house: House): Int {
    val coefficient: Double = when (house.rooms) {
        in Int.MIN_VALUE..0 -> 1.0
        1 -> 1.0
        in 2..3 -> 1.2
        4 -> 1.25
        in 5..7 -> 1.4
        in 8..10 -> 1.6
        else -> 1.6
    }

    val basePrice: Int = when (house.price) {
        in Int.MIN_VALUE..0 -> 0
        in 1_000_000..Int.MAX_VALUE -> 1_000_000
        else -> house.price
    }

    return (basePrice * coefficient).toInt()
}
