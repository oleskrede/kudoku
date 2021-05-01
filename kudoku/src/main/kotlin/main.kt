import InputHandler.Companion.stringToBoard

fun main(args: Array<String>) {
//    var line = readLine()
//    while (line != null) {
//        val (a, b) = line.split(' ');
//        // solve test case and output answer
//        line = readLine();
//    }
    val inputTestCase =
        """
            1x8xxxx3x
            2xx1x5xxx
            xx7x89154
            x8x59xx4x
            5xx4x3xx8
            x4xx18x2x
            81396x4xx
            xxx3x7xx2
            x2xxxx3x6
        """.trimIndent()

    val inputTestSolution =
        """
            158674239
            294135687
            637289154
            382596741
            571423968
            946718523
            813962475
            469357812
            725841396
        """.trimIndent()

    val testCase = stringToBoard(inputTestCase)
    println("Test case:")
    println(testCase)

    val testSolution = stringToBoard(inputTestSolution)
    println("Test solution:")
    println(testSolution)
}

