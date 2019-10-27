import java.io.File
import java.util.*

private var a: ArrayList<Double> = ArrayList()
private var b = 0.0
private var c = 0.0

private var epoch = 0

private var dataset: ArrayList<Double> = ArrayList()

fun main(args: Array<String>) {
    //println(activation(11.1,0.5, 0.1, -1.0))
    val input = Scanner(System.`in`)

    genDataset()

    val cmd = input.next()

    retrieveData()

    if (cmd == "learn") {
        val epochs = input.nextInt()

        for (i in (1..epochs)) {
            epoch++
            epoch()
            saveData()
        }
    } else if (cmd == "activate") {
        val teta = input.nextDouble()
        val c2 = input.nextDouble()
        //println("a: $a ; b: $b ; c: $c ; activation: ${activation(a, b[(c2 * 100).toInt()], c2, teta)}")
        println("result: ${activation(a[(c2 * 100).toInt()], b, c2, teta)}")
    } else {
        return
    }


//    for(i in (0..100) step 1){
//        println("c[$i]: ${dataset[i]}")
//    }

}

private fun saveData() {
    var str = "$b "
    for (a2 in a) {
        str += "${a2} "
    }
    str += "$c $epoch\n"
    File("data.txt").bufferedWriter().use { out ->
        out.write(str)
    }
}

private fun retrieveData() {
    try {
        val res = File("data.txt").readText().split(" ")
        b = res[0].toDouble()
        for (a2 in (1..a.size)) {
            a.add(res[a2].toDouble())
        }
        c = res[101].toDouble()
        epoch = res[102].toInt()

    } catch (e: Exception) {
    }
}

private fun activation(a: Double, b: Double, c: Double, teta: Double): Double {
    return c + ((1 - c) / (1 + Math.pow(Math.E, a * (teta - b))))
}

private fun stopCondition(a: Double, b: Double, c: Double): Boolean {
    return ((-(activation(a, b, c, b) - ((1 + c) / 2))) >= (-0.01 / epoch)) && ((activation(
        a,
        b,
        c,
        b
    ) - ((1 + c) / 2)) <= (0.01 / epoch))
}

fun epoch() {
    for (i in (1..100)) {
        for (j in (0..1000)) {
            nc(dataset[i], i - 1)
            if (stopCondition(a[i - 1], b, c)) {
//                println("a: $a ; b: $b ; c: $c ; activation: ${activation(a, b[i], c, b[i])}")
                println("Success $i step with: ${activation(a[i - 1], b, c, b) - ((1 + c) / 2)}")
                break
            } else {
                println("Continue $i step with: ${activation(a[i - 1], b, c, b) - ((1 + c) / 2)}")
                a[i - 1] += 0.00001
            }
        }
    }
}

fun nc(c2: Double, i: Int) {
    c = c2
    b = c + ((1 - c) / (1 + Math.pow(Math.E, a[i])))
}

private fun genDataset() {
    for (i in (0..100) step 1) {
        dataset.add((i + 0.0) / 100)
        a.add(0.1)
    }
}