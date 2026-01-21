package com.ajidroid.hissab.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.abs

fun Int.asCurrency(
    locale: Locale = Locale.getDefault(),
    currency: Currency = Currency.getInstance(locale)
): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    formatter.currency = currency
    formatter.maximumFractionDigits = 0 // for Int amounts
    return formatter.format(abs(this))
}