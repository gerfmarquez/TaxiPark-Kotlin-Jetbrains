package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
     allDrivers.filter { driver -> trips.none { trip -> trip.driver == driver } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { passenger -> trips.count { trip -> trip.passengers.any { it == passenger }} >= minTrips}.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { passenger -> trips.count { trip -> passenger in trip.passengers  && driver == trip.driver} > 1}.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { passenger -> trips
                .filter { trip -> passenger in trip.passengers}
                .partition { trip -> (trip.discount?:0.0 in 0.1 .. 1.0)  }.let { it.first.size > it.second.size }}
                .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return  if(trips.isNotEmpty()) {
        (trips.minBy { it.duration } to trips.maxBy { it.duration })
                .let {
                    //IntRange(it.first?.duration?:0, )
                    val duration = it.second?.duration?:1
                    val max = duration - (duration % 10) + 10
                    var ranges = mutableListOf<IntRange>()
                    for(i in 0 until  max step 10) {
                        ranges.add(IntRange(i,i+9))
                        println(i+9)
                    }
                    ranges
                }.let {
                    it.maxBy { range ->
                        trips.count { trip -> trip.duration in range }
                    }
                }
    } else {null}
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if(trips.isEmpty()) return false

    var eightyPercentIncome = (trips.sumByDouble { it.cost } * .80)

    var driversIncomes = trips.associate { trip -> trip.driver to trips.filter { it.driver == trip.driver }.sumByDouble { it.cost }}.toList().sortedByDescending { it.second }

    val moreThanEightyPercentIncome : Boolean = driversIncomes.slice( 0 until (allDrivers.size * .20).toInt()).sumByDouble { it.second } >= eightyPercentIncome

    return moreThanEightyPercentIncome
}