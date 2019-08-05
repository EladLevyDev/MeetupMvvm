package com.elad.meetup.utils

import java.text.SimpleDateFormat
import java.util.*


fun Date.simpleDateFormat(): String {
  return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(this)
}

fun Date.simpleTimeFormat(): String {
  return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}