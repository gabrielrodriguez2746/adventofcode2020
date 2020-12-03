/**
 * --- Day 2: Password Philosophy ---
Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via toboggan.

The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers; we can't log in!" You ask if you can take a look.

Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the Official Toboggan Corporate Policy that was in effect when they were chosen.

To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted database) and the corporate policy when that password was set.

For example, suppose you have the following list:

1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

How many passwords are valid according to their policies?

--- Part Two ---
While it appears you validated the passwords correctly, they don't seem to be what the Official Toboggan Corporate Authentication System is expecting.

The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.

Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

Given the same example list from above:

1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
How many passwords are valid according to the new interpretation of the policies?
 */

fun main() {
    val input = "Day2Input".readInput()
    println("The count of valid password is :: ${validPasswordsCounter(input)}")
    println("The count of valid password with new method is :: ${validPasswordsCounterNewCompany(input)}")
}

internal fun validPasswordsCounterNewCompany(input: List<String>): Int {
    // Given a list of elements represented as a String with the format $passwordChecker':' $password
    // count the number of valid password on a list where is valid is only achieve if the
    // $password is validated by the $passwordChecker
    // $passwordChecker is described as: firstValidPosition'-'secondValidPosition letter
    // Important Exactly one of these positions must contain the given letter 
    // Important position 1 is the first element

    // Question not clear:
    // 1. Can I trust the format of the provided input has always the format $passwordChecker':' $password
    // 2. Can I trust the password are always lower case? If it does not lowerCase count as UpperCase?
    // 3. Can I trust the password are just letters
    // 4. Can I trust the passwordChecker format is always the same?
    // 5. Can I trust that empty space in the password are not allowed?

    // Assumption -> Yes, I can trust in all the previous question
    // I am not sure of the order of this because of the String lengths
    var validPasswordCount = 0
    // Need to iterate over the list
    input.forEach {
        // Need to split the checker from the password
        val (passwordChecker, password) = it.split(":")
        // Need to split the checker in concurrency range (minimum and maximum) and pattern (letter)
        val (concurrency, pattern) = passwordChecker.split(" ")
        val letterPattern = pattern.first()
        // Create a List<Char> with the character in the password
        val passwordList = password.trim().toList()

        // get positions an validate with pattern
        val (firstValidPosition, secondValidPosition) = concurrency.split("-")
        val elementInFirstValidPosition = passwordList[firstValidPosition.toInt() -1]
        val elementInSecondValidPosition = passwordList[secondValidPosition.toInt() -1]

        if ((elementInFirstValidPosition == letterPattern && elementInSecondValidPosition != letterPattern) ||
            (elementInFirstValidPosition != letterPattern && elementInSecondValidPosition == letterPattern)) {
            validPasswordCount++
        }
    }
    return validPasswordCount
}

internal fun validPasswordsCounter(input: List<String>): Int {
    // Given a list of elements represented as a String with the format $passwordChecker':' $password
    // count the number of valid password on a list where is valid is only achieve if the
    // $password is validated by the $passwordChecker
    // $passwordChecker is described as: numberOfMinimumConcurrency'-'numberOfMaximumConcurrency letter

    // Question not clear:
    // 1. Can I trust the format of the provided input has always the format $passwordChecker':' $password
    // 2. Can I trust the password are always lower case? If it does not lowerCase count as UpperCase?
    // 3. Can I trust the password are just letters
    // 4. Can I trust the passwordChecker format is always the same?
    // 5. Can I trust that empty space in the password are not allowed?

    // Assumption -> Yes, I can trust in all the previous question
    // I am not sure of the order of this because of the String lengths
    var validPasswordCount = 0
    // Need to iterate over the list
    input.forEach {
        // Need to split the checker from the password
        val (passwordChecker, password) = it.split(":")
        // Need to split the checker in concurrency range (minimum and maximum) and pattern (letter)
        val (concurrency, pattern) = passwordChecker.split(" ")
        val letterPattern = pattern.first()
        // Create a Map<Char, Int> with the count of each char in the password
        val passwordMap = password.trim().mapToCharCountMap()

        // Compare with pattern
        passwordMap[letterPattern]?.let { patternCount ->
            val (minimumConcurrency, maximumConcurrency) = concurrency.split("-")
            if (patternCount in minimumConcurrency.toInt() .. maximumConcurrency.toInt()) {
                validPasswordCount++
            }
        }
    }
    return validPasswordCount
}

private fun String.mapToCharCountMap(): Map<Char, Int> {
    return groupingBy { character -> character }
        .eachCount()
        .toMap()
}