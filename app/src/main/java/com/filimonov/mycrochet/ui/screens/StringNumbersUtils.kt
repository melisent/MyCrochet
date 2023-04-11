package com.filimonov.mycrochet.ui.screens

fun String.isPositiveInt() = (this.toIntOrNull() ?: 0) > 0

fun String.isPositiveFloat() = (this.toFloatOrNull() ?: 0f) > 0
