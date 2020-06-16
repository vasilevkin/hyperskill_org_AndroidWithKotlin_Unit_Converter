data class Comment(val id: Int, val body: String, val author: String)

fun printComments(commentsData: Array<Comment>) {
    for ((_, body, author) in commentsData) {
        println("Author: $author; Text: $body")
    }
    // write you code here
}