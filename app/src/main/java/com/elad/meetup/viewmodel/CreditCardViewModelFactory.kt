package com.elad.meetup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject



class CreditCardViewModelFactory @Inject constructor(
    private val creditCardViewModel: CreditCardViewModel) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(CreditCardViewModel::class.java)) {
      return creditCardViewModel as T
    }
    throw IllegalArgumentException("Unknown class preffix")
  }
}