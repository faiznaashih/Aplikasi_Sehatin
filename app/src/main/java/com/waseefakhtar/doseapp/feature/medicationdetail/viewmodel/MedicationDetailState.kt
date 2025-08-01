package com.waseefakhtar.doseapp.feature.medicationdetail.viewmodel

import com.waseefakhtar.doseapp.domain.model.Medication

data class MedicationDetailState(
    val medication: Medication? = null,
    val isLoading: Boolean = false
)