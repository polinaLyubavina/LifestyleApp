package com.lifestyleapp.test

import com.lifestyleapp.BMRCalculators
import org.junit.Assert
import org.junit.Test

class BMRCalculatorsTest {

    @Test
    fun calculateBMI() {
        Assert.assertEquals(
            24.6,
            BMRCalculators.calculateBMI(177.0, 71.0),
            0.1
        )

        Assert.assertNotEquals(
            24.6,
            BMRCalculators.calculateBMI(150.0, 71.0),
            0.1
        )
    }

    @Test
    fun calculateBMR() {
        Assert.assertEquals(
            1778.1,
            BMRCalculators.calculateBMR(170.0, 71.0, 25.0, 1),
            0.1
        )

        Assert.assertNotEquals(
            1800.0,
            BMRCalculators.calculateBMR(170.0, 71.0, 25.0, 1),
            0.1
        )
    }

    @Test
    fun calculateCaloriesToEat() {
        Assert.assertEquals(
            2220.5,
            BMRCalculators.calculateCaloriesToEat(1778.1, -1.0, true),
            0.1
        )

        Assert.assertNotEquals(
            2220.5,
            BMRCalculators.calculateCaloriesToEat(1778.1, 2.0, false),
            0.1
        )

        Assert.assertNotEquals(
            2220.5,
            BMRCalculators.calculateCaloriesToEat(1778.1, 10.0, false),
            0.1
        )

        Assert.assertNotEquals(
            2220.5,
            BMRCalculators.calculateCaloriesToEat(2000.0, -1.0, true),
            0.1
        )
    }
}