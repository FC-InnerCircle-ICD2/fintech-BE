package com.inner.circle.core

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CalculatorTest :
    StringSpec({
        "addition should return the sum of two numbers" {
            val calculator = Calculator()
            calculator.add(2, 3) shouldBe 5
        }

        "subtraction should return the difference of two numbers" {
            val calculator = Calculator()
            calculator.subtract(5, 3) shouldBe 2
        }
    })
