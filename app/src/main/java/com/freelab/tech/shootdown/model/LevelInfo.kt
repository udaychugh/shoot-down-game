package com.freelab.tech.shootdown.model

enum class LevelInfo(val score: Int, val time: Long) {
    LEVEL_ONE(30, 60_000L),
    LEVEL_TWO(-1, 120_000L),
    LEVEL_THREE(60, 60_000L),
    LEVEL_FOUR(60, 60_000L),
    LEVEL_FIVE(60, 60_000L)
}