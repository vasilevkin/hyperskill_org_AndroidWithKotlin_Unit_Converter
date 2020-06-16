fun main() {
    val string = readLine()!!

//    val inputArray = string
//            .replace("\\s+", "")
////            .replaceAll("\\s+"," ")
////            .replace("\\s+".toRegex(), " ")
//            .replace("degree", "")
//            .replace("degrees", "")
//            .split(" ")

    val inputArray = string
            .replace("degree", "")
            .replace("degrees", "")
            .split(" ")
            .filter { it != "" }

//    val new = inputArray.filter { it != "" }

//    val inputArray = string.split(" ", "degree", "degrees")

//    println(new)
    print(inputArray)


//    print(string.toLowerCase().indexOf("the") ?: "-1")
    // write your code here    
}
