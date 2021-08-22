package com.zelyder.chilldev.domain.models

import com.zelyder.chilldev.R
import com.zelyder.chilldev.ui.kidname.Item

enum class KidIcon(val rank: Int, val resId: Int) {
    ONE(1, R.drawable.ic_avas1),
    TWO(2, R.drawable.ic_avas2),
    THREE(3, R.drawable.ic_avas3),
    FOUR(4, R.drawable.ic_avas4),
    FIVE(5, R.drawable.ic_avas5),
    SIX(6, R.drawable.ic_avas6),
    SEVEN(7, R.drawable.ic_avas7),
    EIGHT(8, R.drawable.ic_avas8),
    NINE(9, R.drawable.ic_avas9),
    TEN(10, R.drawable.ic_avas10);

    override fun toString(): String {
        return rank.toString()
    }

    companion object {
        fun getForPosition(position: Int) : KidIcon {
            return values().find { it.rank == position} ?: ONE
        }
        fun getCarouselItems(): List<Item> {
            return values().map { Item(it.resId) }
        }
    }
}
