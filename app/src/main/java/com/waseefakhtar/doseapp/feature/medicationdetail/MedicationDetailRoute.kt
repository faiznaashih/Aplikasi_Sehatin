package com.waseefakhtar.doseapp.feature.medicationdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.waseefakhtar.doseapp.R
import com.waseefakhtar.doseapp.domain.model.Medication
import com.waseefakhtar.doseapp.feature.medicationdetail.viewmodel.MedicationDetailViewModel
import com.waseefakhtar.doseapp.util.toFormattedDateString

@Composable
fun MedicationDetailRoute(
    medicationId: Long?,
    onBackClicked: () -> Unit,
    viewModel: MedicationDetailViewModel = hiltViewModel()
) {
    val medicationState by viewModel.medicationState.collectAsState()
    val isMedicationDeleted by viewModel.isMedicationDeleted.collectAsState()

    LaunchedEffect(isMedicationDeleted) {
        if (isMedicationDeleted) {
            onBackClicked()
        }
    }

    MedicationDetailScreen(
        medication = medicationState.medication,
        onBackClicked = onBackClicked,
        onDeleteClicked = {
            val medicationToDelete = medicationState.medication
            if (medicationToDelete != null) {
                viewModel.deleteMedication(medicationToDelete)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationDetailScreen(
    medication: Medication?,
    onBackClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.medication_details)) },
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDeleteClicked) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Medication"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            medication?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoRow(label = "Dosage:", value = "${it.dosage} ${it.unit}")
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(label = "Start Date:", value = it.startDate.toFormattedDateString())
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(label = "End Date:", value = it.endDate.toFormattedDateString())
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(label = "Frequency:", value = it.frequency)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(label = "Timing:", value = it.medicationTime.toFormattedDateString())
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}