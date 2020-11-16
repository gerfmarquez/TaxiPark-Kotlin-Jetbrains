package nicestring

fun String.isNice(): Boolean {

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 * Copyright 2020, Gerardo Marquez.
 */

    val noBadSubstring = setOf("bu","ba","be").none{ this.contains(it) }

    val hasThreeVowels = count { it in "aeiou" } >= 3

    val hasDoubleLetter = this.zipWithNext().any{ it.first == it.second }

    return listOf(noBadSubstring,hasThreeVowels,hasDoubleLetter).count{ it } >= 2
}