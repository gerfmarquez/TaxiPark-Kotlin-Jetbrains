package nicestring

fun String.isNice(): Boolean {


    var firstCondition = 1//true
    var secondCondition = 0//false
    var thirdCondition = 0//false

    if(this.contains(Regex("bu")) || this.contains(Regex("ba")) ||
            this.contains(Regex("be"))) {
        firstCondition = 0//false
    }
    val vowels = "aeiou".sumBy { ch->
        this.count{ ch == it }
    }
    if(vowels >= 3) {
        secondCondition = 1//true
    }

    val doubleLetter = this.zipWithNext()
            .count { pair ->
                (pair.first == pair.second)
            }
    if(doubleLetter >= 1) {
        thirdCondition = 1//true
    }

    var total = firstCondition + secondCondition + thirdCondition
    return (total in 2..3)
}