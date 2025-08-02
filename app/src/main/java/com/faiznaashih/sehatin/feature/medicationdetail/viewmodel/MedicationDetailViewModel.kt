package com.waseefakhtar.doseapp.feature.medicationdetail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waseefakhtar.doseapp.domain.model.Medication
import com.waseefakhtar.doseapp.feature.medicationdetail.usecase.DeleteMedicationUseCase
import com.waseefakhtar.doseapp.feature.medicationdetail.usecase.GetMedicationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailViewModel @Inject constructor(
    private val getMedicationUseCase: GetMedicationUseCase,
    private val deleteMedicationUseCase: DeleteMedicationUseCase, // Menggunakan UseCase baru
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val medicationId: Long = checkNotNull(savedStateHandle["medicationId"])

    private val _medicationState = MutableStateFlow(MedicationDetailState())
    val medicationState: StateFlow<MedicationDetailState> = _medicationState.asStateFlow()

    private val _isMedicationDeleted = MutableStateFlow(false)
    val isMedicationDeleted: StateFlow<Boolean> = _isMedicationDeleted.asStateFlow()

    init {
        loadMedication()
    }

    private fun loadMedication() {
        viewModelScope.launch {
            _medicationState.update { it.copy(isLoading = true) }
            val medication = getMedicationUseCase(medicationId)
            _medicationState.update { it.copy(medication = medication, isLoading = false) }
        }
    }

    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            deleteMedicationUseCase(medication) // Memanggil UseCase untuk hapus
            _isMedicationDeleted.value = true
        }
    }
}