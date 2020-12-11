/**
 * --- Day 7: Handy Haversacks ---
You land at the regional airport in time for your next flight. In fact, it looks like you'll even have time to grab some food: all flights are currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their contents; bags must be color-coded and must contain specific quantities of other color-coded bags. Apparently, nobody responsible for these regulations considered how long they would take to enforce!

For example, consider the following rules:

light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty, every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually, contain at least one shiny gold bag?)

In the above rules, the following options would be available to you:

A bright white bag, which can hold your shiny gold bag directly.
A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make sure you get all of it.)

--- Part Two ---
It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous number of bags you need to buy!

Consider again your shiny gold bag and the rules from the above example:

faded blue bags contain 0 other bags.
dotted black bags contain 0 other bags.
vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.
So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!

Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!

Here's another example:

shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.
In this example, a single shiny gold bag must contain 126 other bags.

How many individual bags are required inside your single shiny gold bag?


 */
fun main() {
    val input  = "Day7Input".readInput()
    val searchedBag = "shiny gold"
    println("The amount of bags that could contain $searchedBag is :: ${countPossibleBagContained(input, searchedBag)}")
    println("The amount of bags inside the $searchedBag is :: ${countBagsInsideSearchedBag(input, searchedBag)}")
}

/**
 * Given a list of String representing a Bag (name -> color) with internal bags with and quantity and name
 * count all possible ExternalBags containing at least  one shiny bag
 * Assumptions: External bags are always three words where the last one is "bag" and the first two the bag name
 * Assumptions: Internal bags are always four words where the last one is "bag" and the first is the quantity and the second and third the bag name
 * Important "no other bags." Represents not internal bags
 * Important "contain" divide te external bag from the others
 * Important "," separate the internal bags
 */
internal fun countPossibleBagContained(input: List<String>, searchedBag: BagType): Int {
    // map the input to External Bag
    val bags = input.toExternalBags()
    // make a recursive method
    return bags.values.fold(0) { count, item ->
        count + countBagsRecursively(bags, item, searchedBag, 0)
    }
}

internal fun countBagsInsideSearchedBag(input: List<String>, searchedBag: BagType): Int {
    // map the input to External Bag
    val bags = input.toExternalBags()
    // make a recursive method
    return countBagsInsideSearchedBag(bags, bags.getValue(searchedBag), searchedBag)
}

/**
 * This really need to be improved the fold inside the recursive does not look very good...
 * Two break conditions, no more bags to iterate or bag already found (this is for the fold)
 */
private fun countBagsInsideSearchedBag(externalBags: ExternalBags, internalBags: InternalBags, searchedBag: BagType) : Int {
    return when {
        internalBags.isEmpty() -> 0
        else -> internalBags.keys.fold(0) { sum, item ->
            val bagQuantity = internalBags.getValue(item)
            sum + bagQuantity + bagQuantity * countBagsInsideSearchedBag(externalBags, externalBags.getValue(item), searchedBag)
        }
    }
}

/**
 * This really need to be improved the fold inside the recursive does not look very good...
 * Two break conditions, no more bags to iterate or bag already found (count == 1)
 */
private fun countBagsRecursively(externalBags: ExternalBags, internalBags: InternalBags, searchedBag: BagType, count: Int) : Int {
    return when {
        count == 1 -> count
        internalBags.isEmpty() -> 0
        internalBags.containsKey(searchedBag) -> 1
        else -> internalBags.keys.fold(0) { sum, item ->
            countBagsRecursively(externalBags, externalBags.getValue(item), searchedBag, sum)
        }
    }
}

// map the external bags
private fun List<String>.toExternalBags() : ExternalBags = map {
    // split string by "contain" this gives me the external bag and the internals
    val (externalBag, internalBags) = it.split(" contain ")
    externalBag.substringBefore(" bags") to internalBags.toInternalBags()
}.toMap()


// map the internal bags
private fun String.toInternalBags() : InternalBags {
    return if (trim() == "no other bags.") {
        emptyMap()
    } else {
        trim().split(", ").map { notMappedBags ->
            val internalBag = notMappedBags.substringBefore(" bags").takeIf { it != notMappedBags } ?: notMappedBags.substringBefore(" bag")
            val quantity = internalBag.substringBefore(" ").toInt()
            val bag = internalBag.substringAfter(" ").trim()
            bag to quantity
        }.toMap()
    }
}

typealias BagType = String
typealias InternalBags = Map<BagType, Int>
typealias ExternalBags = Map<BagType, InternalBags>