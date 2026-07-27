package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamResult
import com.example.ui.theme.*

@Composable
fun OnlinePaymentDialog(
    initialCategory: String = "Student Fee",
    onDismiss: () -> Unit,
    onPaymentSuccess: (String, Double, String, String) -> Unit // name, amount, category, method
) {
    var payerName by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("1500") }
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var selectedMethod by remember { mutableStateOf("bKash") }
    var phoneNo by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }
    var generatedReceiptNo by remember { mutableStateOf("") }
    var generatedTxnId by remember { mutableStateOf("") }

    val categories = listOf("Student Fee", "Zakat (জাকাত)", "Sadaqah (সদকা)", "Lillah (লিল্লাহ)", "Orphan Sponsorship (এতিম তহবিল)")
    val methods = listOf("bKash", "Nagad", "Rocket", "SSLCommerz", "Visa / Mastercard", "Bank Transfer")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isSuccess) "ডিজিটাল রসিদ (Receipt Generated)" else "অনলাইন পেমেন্ট ও দান ফান্ড",
                style = MaterialTheme.typography.titleLarge.copy(color = EmeraldPrimary, fontWeight = FontWeight.Bold)
            )
        },
        text = {
            if (isSuccess) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = "পেমেন্ট সফল হয়েছে! জাজাকাল্লাহু খাইরান।",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider()
                    Card(
                        colors = CardDefaults.cardColors(containerColor = EmeraldContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("রসিদ নম্বর: $generatedReceiptNo", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            Text("ট্রানজেকশন আইডি: $generatedTxnId", style = MaterialTheme.typography.bodySmall)
                            Text("প্রদানকারী: ${payerName.ifBlank { "বেনামী দাতা" }}", style = MaterialTheme.typography.bodySmall)
                            Text("খাত: $selectedCategory", style = MaterialTheme.typography.bodySmall)
                            Text("পরিমাণ: ৳$amountText", style = MaterialTheme.typography.titleMedium.copy(color = EmeraldDark, fontWeight = FontWeight.Bold))
                            Text("মাধ্যম: $selectedMethod", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Text(
                        text = "অফিসিয়াল কপি এসএমএস ও ইমেইলে পাঠানো হয়েছে।",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = payerName,
                        onValueChange = { payerName = it },
                        label = { Text("প্রদানকারীর নাম (Name)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("টাকার পরিমাণ (Amount in BDT ৳)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text("পেমেন্ট খাত (Category):", style = MaterialTheme.typography.labelMedium)
                    categories.forEach { cat ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCategory = cat }
                        ) {
                            RadioButton(selected = (selectedCategory == cat), onClick = { selectedCategory = cat })
                            Text(cat, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Text("পেমেন্ট গেটওয়ে (Method):", style = MaterialTheme.typography.labelMedium)
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        methods.chunked(2).forEach { rowMethods ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                rowMethods.forEach { method ->
                                    val isSelected = selectedMethod == method
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSelected) EmeraldContainer else LightCanvas)
                                            .border(1.dp, if (isSelected) EmeraldPrimary else LightBorder, RoundedCornerShape(8.dp))
                                            .clickable { selectedMethod = method }
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = method,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = if (isSelected) EmeraldDark else Color.DarkGray,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = phoneNo,
                        onValueChange = { phoneNo = it },
                        label = { Text("মোবাইল নম্বর (bKash/Nagad/Phone)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            if (isSuccess) {
                Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Text("সম্পন্ন করুন")
                }
            } else {
                Button(
                    onClick = {
                        val amt = amountText.toDoubleOrNull() ?: 1000.0
                        generatedReceiptNo = "REC-DM-${(100000..999999).random()}"
                        generatedTxnId = "${selectedMethod.uppercase()}-${(10000000..99999999).random()}"
                        onPaymentSuccess(payerName.ifBlank { "বেনামী দাতা" }, amt, selectedCategory, selectedMethod)
                        isSuccess = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("পেমেন্ট নিশ্চিত করুন (Pay ৳$amountText)")
                }
            }
        },
        dismissButton = {
            if (!isSuccess) {
                TextButton(onClick = onDismiss) {
                    Text("বাতিল")
                }
            }
        }
    )
}

@Composable
fun QrVerifyDialog(
    sampleResults: List<ExamResult>,
    onDismiss: () -> Unit
) {
    var qrInput by remember { mutableStateOf("QR-DM-2026-001") }
    var searchedResult by remember { mutableStateOf<ExamResult?>(null) }
    var hasSearched by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = EmeraldPrimary)
                Text("সনদ ও ফলাফল যাচাই (QR Verification)")
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "দারুল মদিনা ইসলামিয়া মাদ্রাসার অফিসিয়াল কিউআর কোড বা সনদ আইডি প্রবিষ্ট করুন:",
                    style = MaterialTheme.typography.bodySmall
                )

                OutlinedTextField(
                    value = qrInput,
                    onValueChange = { qrInput = it },
                    label = { Text("QR Certificate Code / ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            searchedResult = sampleResults.find { it.qrVerificationCode.equals(qrInput.trim(), ignoreCase = true) }
                            hasSearched = true
                        }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                )

                if (hasSearched) {
                    searchedResult?.let { result ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = EmeraldContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Default.Verified, contentDescription = null, tint = EmeraldPrimary)
                                    Text("যাচাইকৃত অফিসিয়াল সনদ (Verified)", style = MaterialTheme.typography.titleSmall.copy(color = EmeraldDark, fontWeight = FontWeight.Bold))
                                }
                                HorizontalDivider()
                                Text("শিক্ষার্থীর নাম: ${result.studentName}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                Text("শ্রেণি: ${result.className}", style = MaterialTheme.typography.bodySmall)
                                Text("পরীক্ষা: ${result.examName}", style = MaterialTheme.typography.bodySmall)
                                Text("প্রাপ্ত নম্বর: ${result.marksObtained} / ${result.totalMarks}", style = MaterialTheme.typography.bodySmall)
                                Text("গ্রেড / ফলাফল: ${result.gpaOrGrade}", style = MaterialTheme.typography.bodyMedium.copy(color = GoldAccent, fontWeight = FontWeight.Bold))
                            }
                        }
                    } ?: run {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Default.Error, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                                Text("কোনো বৈধ সনদ বা নম্বরপত্র পাওয়া যায়নি। সঠিক কিউআর কোড চেক করুন।", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    searchedResult = sampleResults.find { it.qrVerificationCode.equals(qrInput.trim(), ignoreCase = true) }
                    hasSearched = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text("সনদ যাচাই করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বন্ধ করুন")
            }
        }
    )
}
