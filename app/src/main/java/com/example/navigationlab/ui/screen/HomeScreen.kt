package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.navigationlab.ui.home.HomeUiState

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onOpenDetail: (Int) -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAbout: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("home_screen"),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Home", style = MaterialTheme.typography.headlineMedium)
                Text("Pilih mahasiswa untuk melihat detail.")
                Button(
                    onClick = { onOpenDetail(2026) },
                    modifier = Modifier.fillMaxWidth().testTag("open_detail_2026")
                ) { Text("Buka Detail Mahasiswa 2026") }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = onOpenProfile, modifier = Modifier.weight(1f)) {
                        Text("Profile")
                    }
                    OutlinedButton(onClick = onOpenAbout, modifier = Modifier.weight(1f)) {
                        Text("About")
                    }
                }
            }
        }
        item {
            Text(
                "Daftar Mahasiswa (${uiState.students.size})",
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (uiState.students.isEmpty()) {
            item { Text("Belum ada data mahasiswa.") }
        }
        items(uiState.students, key = { it.id }) { student ->
            Card(
                onClick = { onOpenDetail(student.id) },
                modifier = Modifier.fillMaxWidth().testTag("student_${student.id}")
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("ID: ${student.id}", style = MaterialTheme.typography.labelLarge)
                    Text(student.name, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
