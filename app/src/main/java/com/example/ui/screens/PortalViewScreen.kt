package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.UserRole
import com.example.ui.theme.*

@Composable
fun PortalViewScreen(
    currentRole: UserRole,
    onOpenPayment: (String) -> Unit,
    onOpenQrVerify: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(color = EmeraldContainer, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(48.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (currentRole) {
                                    UserRole.TEACHER -> Icons.Default.School
                                    UserRole.STUDENT -> Icons.Default.Person
                                    UserRole.PARENT -> Icons.Default.FamilyRestroom
                                    UserRole.ACCOUNTANT -> Icons.Default.Payments
                                    UserRole.LIBRARIAN -> Icons.Default.MenuBook
                                    UserRole.HOSTEL_MANAGER -> Icons.Default.Hotel
                                    UserRole.ADMISSION_OFFICER -> Icons.Default.HowToReg
                                    else -> Icons.Default.AccountCircle
                                },
                                contentDescription = null,
                                tint = EmeraldPrimary
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("পোর্টাল: ${currentRole.displayName}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EmeraldPrimary))
                        Text("দারুল মদিনা ইসলামিয়া মাদ্রাসা ইউজার এক্সেস", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        when (currentRole) {
            UserRole.TEACHER -> {
                item {
                    TeacherPortalContent()
                }
            }
            UserRole.STUDENT -> {
                item {
                    StudentPortalContent(onOpenQrVerify = onOpenQrVerify)
                }
            }
            UserRole.PARENT -> {
                item {
                    ParentPortalContent(onOpenPayment = onOpenPayment)
                }
            }
            UserRole.ACCOUNTANT -> {
                item {
                    AccountantPortalContent(onOpenPayment = onOpenPayment)
                }
            }
            UserRole.LIBRARIAN -> {
                item {
                    LibrarianPortalContent()
                }
            }
            UserRole.HOSTEL_MANAGER -> {
                item {
                    HostelManagerPortalContent()
                }
            }
            UserRole.ADMISSION_OFFICER -> {
                item {
                    AdmissionOfficerPortalContent()
                }
            }
            else -> {}
        }
    }
}

@Composable
fun TeacherPortalContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("শিক্ষক প্যানেল - দৈনিক রুটিন ও হাজিরা", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("আজকের ক্লাস: সহীহ বুখারী পাঠ (১০:০০ AM) | দাওরায়ে হাদীস", style = MaterialTheme.typography.bodySmall)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("অনলাইন ডিজিটাল হাজিরা নিন")
                }
            }
        }
    }
}

@Composable
fun StudentPortalContent(onOpenQrVerify: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = EmeraldContainer), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("ছাত্র প্রোফাইল - মোহাম্মদ আব্দুল্লাহ", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EmeraldDark))
                Text("আইডি: 2026001 | শ্রেণি: দাওরায়ে হাদীস | রোল: ০১", style = MaterialTheme.typography.bodySmall)
                Text("উপস্থিতি: ৯৬% | স্থান: ১ম স্থান", style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
                Button(onClick = onOpenQrVerify, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Icon(Icons.Default.QrCode, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ফলাফল ও মার্কশিট QR ডাউনলোড")
                }
            }
        }
    }
}

@Composable
fun ParentPortalContent(onOpenPayment: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("অভিভাবক পোর্টাল - সন্তানের তথ্য ও ফি", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("সন্তানের নাম: আহমদ মারুফ (হিফজ বিভাগ)", style = MaterialTheme.typography.bodySmall)
                Text("বকেয়া টিউশন ফি: ৳১,৫০০ (জুলাই ২০২৬)", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red, fontWeight = FontWeight.Bold))
                Button(onClick = { onOpenPayment("Student Fee") }, colors = ButtonDefaults.buttonColors(containerColor = GoldAccent)) {
                    Icon(Icons.Default.Payment, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("অনলাইনে ফি পরিশোধ করুন (Pay Fee)")
                }
            }
        }
    }
}

@Composable
fun AccountantPortalContent(onOpenPayment: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("হিসাবরক্ষক প্যানেল - ক্যাশ ও পেমেন্ট রসিদ", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("আজকের আদায়কৃত ফি: ৳৪৫,০০০ | অনুদান: ৳২৫,০০০", style = MaterialTheme.typography.bodySmall)
                Button(onClick = { onOpenPayment("General Fund") }, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Icon(Icons.Default.Receipt, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ম্যানুয়াল ক্যাশ পেমেন্ট রসিদ কাটুন")
                }
            }
        }
    }
}

@Composable
fun LibrarianPortalContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("লাইব্রেরিয়ান প্যানেল - কিতাব ইস্যু ও ডিজিটাল বুকস", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("মোট কিতাব সংখ্যা: ৪,৫০০ টি | ডিজিটাল ই-বুক: ১,২০০ টি", style = MaterialTheme.typography.bodySmall)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = SkyAccent)) {
                    Icon(Icons.Default.Book, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("নতুন কিতাব এন্ট্রি করুন")
                }
            }
        }
    }
}

@Composable
fun HostelManagerPortalContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("হোস্টেল ও আবাসন ম্যানেজার প্যানেল", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("মোট আবাসন সিট: ৩৫০ টি | খালি সিট: ২৪ টি", style = MaterialTheme.typography.bodySmall)
                Text("লিল্লাহ বোর্ডিং খাবার সরবরাহ সক্রিয়", style = MaterialTheme.typography.labelSmall, color = EmeraldPrimary)
            }
        }
    }
}

@Composable
fun AdmissionOfficerPortalContent() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("ভর্তি অফিসার প্যানেল", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                Text("অনলাইনে নতুন আবেদন জমা পড়েছে: ১২ টি", style = MaterialTheme.typography.bodySmall)
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)) {
                    Icon(Icons.Default.AssignmentInd, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ভর্তি ফরমসমূহ রিভিউ করুন")
                }
            }
        }
    }
}
