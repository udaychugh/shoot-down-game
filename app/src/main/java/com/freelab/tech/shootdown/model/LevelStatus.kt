package com.freelab.tech.shootdown.model

import com.freelab.tech.shootdown.R

enum class LevelStatus(val msg: Int) {
    COMPLETED(R.string.level_completed_message),
    FAILED(R.string.level_failed_message)
}