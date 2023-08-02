package com.veripark.interview

import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    /**
     * Data Binding variable for the activity_main.xml.
     */
    val serviceData: ServiceModel = ServiceModel()

    /**
     * Data table recyclerview adapter.
     */
    lateinit var historyAdapter: HistoryAdapter

    /**
     * Define the slab to bill conversion.
     * You can alter the slab but carefully with edge case.
     * e.g. minimum 0 - 100 means need to give min as 0 and max as 100,
     * if you give min as 1 and max as 100 means 1 - 100 = 99. then the output will differ.
     */
    private val slabTable = listOf(
        Slab(0, 100, 5.0),
        Slab(101, 500, 8.0),
        Slab(501, Int.MAX_VALUE, 10.0)
    )

    private var previousReadingMap: MutableMap<String, ArrayList<Pair<Int, Double>>> =
        mutableMapOf()

    private var localServiceNumber: String = ""
    private var localMeterReading: Int = 0
    private var localConsumptionCost: Double = 0.0

    /**
     * Click action method of SUBMIT button.
     * Method use to validate and check the previous reading and show the result.
     * @param view Button instance directly from xml.
     */
    fun calculateConsumptionCost(view: View) {
        clearError()
        localServiceNumber = serviceData.serviceNumber
        localMeterReading = if (serviceData.meterReading.isNotEmpty() && serviceData.meterReading.isNotBlank()){
            serviceData.meterReading.toInt()
        }else{
            0
        }
        var actualMeterReading = localMeterReading
        var validated = true
        var Compvalidated = true
        if (localServiceNumber.length != 10) {
            serviceData.serviceNumberError = "Service Number Must Be 10 Digit."
            validated = false
        }
        if (localMeterReading <= 0) {
            serviceData.meterReadingError = "Invalid reading"
            validated = false
        }
        if (!validated) return
        if (previousReadingMap.contains(localServiceNumber)) {
            val readList = previousReadingMap[localServiceNumber]
            val previousReading = readList?.firstOrNull()?.first ?: 0
            actualMeterReading = localMeterReading.minus(previousReading)
            if (actualMeterReading < 0) {
                serviceData.meterReadingError =
                    "New reading must be higher or equal to previous reading"
                Compvalidated = false
            }
        }
        if (!Compvalidated) return
        clearError()
        val consumptionCost = calculateCost(actualMeterReading)
        serviceData.consCost = String.format("%.2f", consumptionCost)
        localConsumptionCost = String.format("%.2f", consumptionCost).trim().toDouble()
    }

    /**
     * Method use to clear the error message.
     */
    private fun clearError() {
        serviceData.meterReadingError = ""
        serviceData.serviceNumberError = ""
    }

    /**
     * Method use to calculate the electric bill base on slabs.
     * @param consumption Electric meter reading.
     */
    private fun calculateCost(consumption: Int): Double {
        var totalCost = 0.0
        var remainingUnits = consumption

        for (slab in slabTable) {
            val slabUnits = (slab.maxUnits - slab.minUnits).coerceAtMost(remainingUnits)
            totalCost += slabUnits * slab.rate
            remainingUnits -= slabUnits

            if (remainingUnits <= 0) {
                break
            }
        }
        return totalCost
    }

    /**
     * Click action method of SAVE button.
     * Method use to save data in the list and display in the recyclerview adapter.
     * @param view Button instance directly from xml.
     */
    fun saveReadingAndCost(view: View) {

        if (previousReadingMap.contains(localServiceNumber.trim())) {
            val readList = previousReadingMap.get(localServiceNumber)
            if (readList != null) {
                if (!readList.contains(Pair(localMeterReading, localConsumptionCost))) {
                    readList.add(0, Pair(localMeterReading, localConsumptionCost))
                }
            }
        } else {
            val previousReadings: ArrayList<Pair<Int, Double>> = arrayListOf()
            previousReadings.add(0, Pair(localMeterReading, localConsumptionCost))
            previousReadingMap.put(localServiceNumber.trim(), previousReadings)
        }
        previousReadingMap[localServiceNumber]?.let { historyAdapter.setArrayList(it) }
    }

}