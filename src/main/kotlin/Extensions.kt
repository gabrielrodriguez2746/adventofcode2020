fun String.readInput(): List<String> = object {}.javaClass.getResource(this).readText().lines()
fun String.readInput(delimiter: String): List<String> = object {}.javaClass.getResource(this).readText()
    .split(delimiter)