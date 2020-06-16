package converter

import java.util.*


enum class Category() {
    WEIGHT,
    DISTANCE,
    TEMPERATURE
}

enum class Units(val valueInBase: Double, val singleUnit: String, val multiUnit: String, val category: Category) {
    // weight, base = 1 gram
    GRAM(1.0, "gram", "grams", Category.WEIGHT),
    KILOGRAM(1000.0, "kilogram", "kilograms", Category.WEIGHT),
    MILLIGRAM(0.001, "milligram", "milligrams", Category.WEIGHT),
    POUND(453.592, "pound", "pounds", Category.WEIGHT),
    OUNCE(28.3495, "ounce", "ounces", Category.WEIGHT),

    // distance, base = 1 meter
    METER(1.0, "meter", "meters", Category.DISTANCE),
    KILOMETER(1000.0, "kilometer", "kilometers", Category.DISTANCE),
    CENTIMETER(0.01, "centimeter", "centimeters", Category.DISTANCE),
    MILLIMETER(0.001, "millimeter", "millimeters", Category.DISTANCE),
    MILE(1609.35, "mile", "miles", Category.DISTANCE),
    YARD(0.9144, "yard", "yards", Category.DISTANCE),
    FOOT(0.3048, "foot", "feet", Category.DISTANCE),
    INCH(0.0254, "inch", "inches", Category.DISTANCE),

    // distance, base = 0.0 for all no base
    CELSIUS(0.0, "degree Celsius", "degrees Celsius", Category.TEMPERATURE),
    FAHRENHEIT(0.0, "degree Fahrenheit", "degrees Fahrenheit", Category.TEMPERATURE),
    KELVIN(0.0, "Kelvin", "Kelvins", Category.TEMPERATURE),

    NONE(0.0, "???", "???", Category.TEMPERATURE),
}


class Converter() {

    data class ConvertFromTo(val isPossible: Boolean, val fromNumber: Double, val fromUnit: Units, val toNumber: Double, val toUnit: Units)

    // Public
    fun convertUnits(input: String): String {
        if (input.startsWith("exit")) return "exit"

        val conversion = parseUserInput(input) ?: return "Parse error"

        return if (conversion.isPossible) {
            val result = convert(conversion)
            val fromUnit = if (result.fromNumber == 1.0) result.fromUnit.singleUnit else result.fromUnit.multiUnit
            val toUnit = if (result.toNumber == 1.0) result.toUnit.singleUnit else result.toUnit.multiUnit
            "${result.fromNumber} $fromUnit is ${result.toNumber} $toUnit"
        } else {
            if (conversion.fromNumber < 0) {
                if (conversion.fromUnit.category == Category.DISTANCE) {
                    return "Length shouldn't be negative."
                }
                if (conversion.fromUnit.category == Category.WEIGHT) {
                    return "Weight shouldn't be negative."
                }
            }
            "Conversion from ${conversion.fromUnit.multiUnit} to ${conversion.toUnit.multiUnit} is impossible"
        }
    }

    // Private

    private fun parseUserInput(input: String): ConvertFromTo? {
        val inputArray = input
                .toLowerCase()
                .replace("degrees", "")
                .replace("degree", "")
                .split(" ")
                .filter { it != "" }
        val fromNumber = inputArray[0].toDoubleOrNull() ?: return null
        val fromUnit = getUnitFromInput(inputArray[1])
        val random = inputArray[2]
        val toUnit = getUnitFromInput(inputArray[3])
        val isPossible = (((fromUnit != Units.NONE) && (toUnit != Units.NONE)) &&
                (fromUnit.category == toUnit.category) &&
                (((fromNumber >= 0) && ((fromUnit.category == Category.WEIGHT) || (fromUnit.category == Category.DISTANCE))) || (fromUnit.category == Category.TEMPERATURE))
                )

        return ConvertFromTo(isPossible, fromNumber, fromUnit, 1.0, toUnit)
    }

    private fun convert(conversion: ConvertFromTo): ConvertFromTo {
        return if (conversion.fromUnit.category == Category.TEMPERATURE) {
            val temp = convertTemperature(conversion.fromNumber, conversion.fromUnit, conversion.toUnit)

            conversion.copy(toNumber = temp)
        } else {
            val base = convertToBase(conversion.fromNumber, conversion.fromUnit)
            val result = convertFromBaseTo(base, conversion.toUnit)
            return conversion.copy(toNumber = result)
        }
    }

    private fun convertTemperature(number: Double, from: Units, to: Units): Double {
        var temp = number
        when {
            (to == Units.CELSIUS) && (from == Units.FAHRENHEIT) -> {
                temp = (number - 32) * 5 / 9
            }
            (to == Units.CELSIUS) && (from == Units.KELVIN) -> {
                temp = number - 273.15
            }
            (to == Units.FAHRENHEIT) && (from == Units.KELVIN) -> {
                temp = number * 9 / 5 - 459.67
            }
            (to == Units.FAHRENHEIT) && (from == Units.CELSIUS) -> {
                temp = number * 9 / 5 + 32
            }
            (to == Units.KELVIN) && (from == Units.FAHRENHEIT) -> {
                temp = (number + 459.67) * 5 / 9
            }
            (to == Units.KELVIN) && (from == Units.CELSIUS) -> {
                temp = number + 273.15
            }

        }

        return temp
    }

    private fun convertToBase(number: Double, unitFrom: Units): String {
        val value = number * unitFrom.valueInBase
        val convertedFromUnit = if (number == 1.0) unitFrom.singleUnit else unitFrom.multiUnit

        return "$number $convertedFromUnit is $value"
    }

    private fun convertFromBaseTo(number: String, unitTo: Units): Double {
        val inputArray = number.split(" ")
        val numberInGrams = inputArray[3].toDouble()
        val value = numberInGrams / unitTo.valueInBase
        val convertedToUnit = if (value == 1.0) unitTo.singleUnit else unitTo.multiUnit

        return value
    }

    private fun getUnitFromInput(input: String): Units {
        return when (input.toLowerCase()) {
            "g", "gram", "grams" -> Units.GRAM
            "kg", "kilogram", "kilograms" -> Units.KILOGRAM
            "mg", "milligram", "milligrams" -> Units.MILLIGRAM
            "lb", "pound", "pounds" -> Units.POUND
            "oz", "ounce", "ounces" -> Units.OUNCE

            "m", "meter", "meters" -> Units.METER
            "km", "kilometer", "kilometers" -> Units.KILOMETER
            "cm", "centimeter", "centimeters" -> Units.CENTIMETER
            "mm", "millimeter", "millimeters" -> Units.MILLIMETER
            "mi", "mile", "miles" -> Units.MILE
            "yd", "yard", "yards" -> Units.YARD
            "ft", "foot", "feet" -> Units.FOOT
            "in", "inch", "inches" -> Units.INCH

            "Celsius", "celsius", "dc", "c" -> Units.CELSIUS
            "Fahrenheit", "fahrenheit", "df", "f" -> Units.FAHRENHEIT
            "Kelvin", "Kelvins", "kelvin", "kelvins", "k" -> Units.KELVIN

            else -> Units.NONE
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val converter = Converter()

    while (true) {
        println("Enter what you want to convert (or exit):")

        val userInput = scanner.nextLine()

        val result = converter.convertUnits(userInput)
        if (result == "exit") {
            break
        } else {
            println(result)
        }
    }
}
