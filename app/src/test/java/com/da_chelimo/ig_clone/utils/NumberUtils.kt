package com.da_chelimo.ig_clone.utils

import org.junit.Test

class NumberUtils {
    @Test
    fun toFormattedNum() {
        assert("120" == 120.toFormattedNumWithSymbols())
        assert("1,200" == 1200.toFormattedNumWithSymbols())
        assert("12.0K" == 12_000.toFormattedNumWithSymbols())
        assert("12.3K" == 12_350.toFormattedNumWithSymbols())
//        assert("122M" == 122_000_000.toFormattedNum())
    }
}