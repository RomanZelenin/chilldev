package com.zelyder.chilldev.domain.models

import com.zelyder.chilldev.R

enum class KidNameIconType(val rank: Int, val resId: Int) {
    ONE(1, R.drawable.ic_avas1),
    ICON_TWO(2, R.drawable.ic_avas2),
    ICON_THREE(3, R.drawable.ic_avas3),
    ICON_FOUR(4, R.drawable.ic_avas4),
    ICON_FIVE(5, R.drawable.ic_avas5),
    ICON_SIX(6, R.drawable.ic_avas6),
    ICON_SEVEN(7, R.drawable.ic_avas7),
    ICON_EIGHT(8, R.drawable.ic_avas8),
    ICON_NINE(9, R.drawable.ic_avas9),
    ICON_TEN(10, R.drawable.ic_avas10);

    override fun toString(): String {
        return rank.toString()
    }

    companion object {
        fun getForPosition(position: Int) : KidNameIconType {
            return values().find { it.rank == position + 1 } ?: ONE
        }
    }
}
