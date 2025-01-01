package com.freelab.tech.shootdown.model

enum class LevelInfo(val score: Int, val time: Long) {
    DASHBOARD(0, 0),
    LEVEL_ONE(20, 60_000L),
    LEVEL_TWO(-1, 120_000L),
    LEVEL_THREE(-1, 60_000L),
    LEVEL_FOUR(-1, 120_000L),
    LEVEL_FIVE(1, 10_000L)
}