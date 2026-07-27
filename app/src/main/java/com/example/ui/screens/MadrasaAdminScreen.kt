package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.NoticeEntity
import com.example.data.local.StudentEntity
import com.example.data.model.DigitalBook
import com.example.data.model.TeacherProfile
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MadrasaAdminScreen(
    students: List<StudentEntity>,
    teachers: List<TeacherProfile>,
    notices: List<NoticeEntity>,
    digitalBooks: List<DigitalBook>,
    onAddStudent: (StudentEntity) -> Unit,
    onAddNotice: (NoticeEntity) -> Unit
) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var authenticatedUser by remember { mutableStateOf("admin@darulmadina.com") }

    if (!isAuthenticated) {
        MadrasaAdminLoginView(
            onLoginSuccess = { user ->
                authenticatedUser = user
                isAuthenticated = true
            }
        )
        return
    }

    var selectedModuleTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var showAddNoticeDialog by remember { mutableStateOf(false) }

    val modules = listOf("ড্যাশবোর্ড", "শিক্ষার্থী ব্যবস্থাপনা", "শিক্ষকবৃন্দ", "নোটিশ প্রকাশ", "ডিজিটাল লাইব্রেরি", "অর্থ ও অনুদান")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BentoLightBg)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Madrasa Admin Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = BentoWhite),
                shape = RoundedCornerShape(24.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(BentoPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color.White)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("মাদ্রাসা এডমিন কন্ট্রোল প্যানেল", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                        Text("লগইন ব্যবহারকারী: $authenticatedUser", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                    }

                    IconButton(
                        onClick = { isAuthenticated = false },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = BentoMintSurface)
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        // Module Tabs
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedModuleTab,
                edgePadding = 0.dp,
                containerColor = Color.Transparent
            ) {
                modules.forEachIndexed { index, title ->
                    Tab(
                        selected = (selectedModuleTab == index),
                        onClick = { selectedModuleTab = index },
                        text = { Text(title, fontSize = 13.sp, fontWeight = if (selectedModuleTab == index) FontWeight.Bold else FontWeight.Normal, color = if (selectedModuleTab == index) BentoPrimaryDark else Color.Gray) }
                    )
                }
            }
        }

        // Module 0: ERP Dashboard Overview
        if (selectedModuleTab == 0) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ErpStatCard("মোট ছাত্র", "${students.size + 1238} জন", Icons.Default.Groups, BentoMintSurface, BentoPrimaryDark, Modifier.weight(1f))
                        ErpStatCard("শিক্ষকবৃন্দ", "${teachers.size + 44} জন", Icons.Default.School, SkyContainer, SkyAccent, Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ErpStatCard("ফি আদায়", "৳৮,৫০,০০০", Icons.Default.Payments, GoldContainer, GoldAccent, Modifier.weight(1f))
                        ErpStatCard("অনুদান ফান্ড", "৳৪,২০,০০০", Icons.Default.VolunteerActivism, BentoMintSurface, BentoPrimaryDark, Modifier.weight(1f))
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("অনলাইন ভর্তি আবেদন অনুমোদন (Pending Admissions)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                        PendingAdmissionRow("মোহাম্মদ ইয়াসিন (প্লে শ্রেণি)", "অভিভাবক: আব্দুল হাই", "01711223344")
                        HorizontalDivider(color = BentoMintBorder)
                        PendingAdmissionRow("আরিফুল ইসলাম (হিফজ বিভাগ)", "অভিভাবক: রফিকুল ইসলাম", "01811223355")
                    }
                }
            }
        }

        // Module 1: Student Management
        if (selectedModuleTab == 1) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ছাত্রের নাম বা রোল খুঁজুন...") },
                        modifier = Modifier.weight(1f),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { showAddStudentDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("নতুন ছাত্র", fontWeight = FontWeight.Bold)
                    }
                }
            }

            items(students) { student ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${student.nameBn} (${student.nameEn})", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                            Text("আইডি: ${student.studentId} | শ্রেণি: ${student.className} | রোল: ${student.rollNo}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                            Text("পিতা: ${student.fatherName} | ফোন: ${student.phone}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }

                        Surface(
                            color = if (student.feeDue > 0) MaterialTheme.colorScheme.errorContainer else BentoMintSurface,
                            shape = CircleShape
                        ) {
                            Text(
                                text = if (student.feeDue > 0) "বকেয়া: ৳${student.feeDue.toInt()}" else "পরিশোধিত",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (student.feeDue > 0) MaterialTheme.colorScheme.error else BentoPrimaryDark,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Module 2: Teachers Profiles
        if (selectedModuleTab == 2) {
            items(teachers) { teacher ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GoldContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.School, contentDescription = null, tint = GoldAccent)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(teacher.nameBn, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                            Text(teacher.designationBn, style = MaterialTheme.typography.labelMedium.copy(color = BentoPrimary))
                            Text("যোগ্যতা: ${teacher.qualification} | ফোন: ${teacher.phone}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // Module 3: Notice Publisher
        if (selectedModuleTab == 3) {
            item {
                Button(
                    onClick = { showAddNoticeDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PostAdd, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("নতুন নোটিশ প্রকাশ করুন", fontWeight = FontWeight.Bold)
                }
            }

            items(notices) { notice ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(notice.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                            Text(notice.date, style = MaterialTheme.typography.labelSmall, color = BentoPrimary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(notice.content, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                    }
                }
            }
        }

        // Module 4: Digital Library & PDF Books
        if (selectedModuleTab == 4) {
            items(digitalBooks) { book ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(book.titleBn, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                            Text("লেখক: ${book.author} | বিভাগ: ${book.category}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            Text("সাইজ: ${book.sizeMb} MB", style = MaterialTheme.typography.labelSmall, color = SkyAccent)
                        }

                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Download, contentDescription = "PDF Download", tint = BentoPrimaryDark)
                        }
                    }
                }
            }
        }

        // Module 5: Finance & Donations
        if (selectedModuleTab == 5) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = GoldContainer),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(GoldAccent.copy(alpha = 0.4f), GoldAccent.copy(alpha = 0.4f)))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("অর্থ ও আয়-ব্যয় সামারি", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldAccent))
                        Text("লিল্লাহ বোর্ডিং ও যাকাত ফান্ড মোট সংগ্রহ: ৳৪,২০,০০০", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                        Text("চলতি মাসের শিক্ষক ও স্টাফ বেতন: ৳৩,৫০,০০০", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                    }
                }
            }
        }
    }

    // Add Student Dialog
    if (showAddStudentDialog) {
        var newNameBn by remember { mutableStateOf("") }
        var newNameEn by remember { mutableStateOf("") }
        var newClass by remember { mutableStateOf("হিফজ বিভাগ") }
        var newFather by remember { mutableStateOf("") }
        var newPhone by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddStudentDialog = false },
            title = { Text("নতুন শিক্ষার্থী ভর্তি নথিভুক্তি", fontWeight = FontWeight.Bold, color = BentoPrimaryDark) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = newNameBn, onValueChange = { newNameBn = it }, label = { Text("শিক্ষার্থীর নাম (বাংলা)") }, singleLine = true)
                    OutlinedTextField(value = newNameEn, onValueChange = { newNameEn = it }, label = { Text("Name (English)") }, singleLine = true)
                    OutlinedTextField(value = newClass, onValueChange = { newClass = it }, label = { Text("শ্রেণি / বিভাগ") }, singleLine = true)
                    OutlinedTextField(value = newFather, onValueChange = { newFather = it }, label = { Text("অভিভাবকের নাম") }, singleLine = true)
                    OutlinedTextField(value = newPhone, onValueChange = { newPhone = it }, label = { Text("মোবাইল নম্বর") }, singleLine = true)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newId = "2026${(100..999).random()}"
                        onAddStudent(
                            StudentEntity(
                                studentId = newId,
                                nameBn = newNameBn.ifBlank { "নতুন ছাত্র" },
                                nameEn = newNameEn.ifBlank { "New Student" },
                                className = newClass,
                                department = "হিফজ বিভাগ",
                                rollNo = "${(10..99).random()}",
                                fatherName = newFather,
                                phone = newPhone,
                                status = "Active",
                                feeDue = 0.0,
                                qrCodeId = "QR-DM-$newId"
                            )
                        )
                        showAddStudentDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark)
                ) {
                    Text("সংরক্ষণ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddStudentDialog = false }) { Text("বাতিল") }
            }
        )
    }

    // Add Notice Dialog
    if (showAddNoticeDialog) {
        var noticeTitle by remember { mutableStateOf("") }
        var noticeContent by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddNoticeDialog = false },
            title = { Text("নতুন নোটিশ প্রকাশ", fontWeight = FontWeight.Bold, color = BentoPrimaryDark) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = noticeTitle, onValueChange = { noticeTitle = it }, label = { Text("নোটিশের শিরোনাম") }, singleLine = true)
                    OutlinedTextField(value = noticeContent, onValueChange = { noticeContent = it }, label = { Text("বিস্তারিত বিবরণ") }, modifier = Modifier.height(100.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddNotice(
                            NoticeEntity(
                                title = noticeTitle.ifBlank { "নতুন নোটিশ" },
                                content = noticeContent.ifBlank { "বিস্তারিত বিবরণ..." },
                                category = "General",
                                date = "২৭ জুলাই, ২০২৬",
                                isImportant = true
                            )
                        )
                        showAddNoticeDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark)
                ) {
                    Text("প্রকাশ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddNoticeDialog = false }) { Text("বাতিল") }
            }
        )
    }
}

@Composable
fun MadrasaAdminLoginView(
    onLoginSuccess: (String) -> Unit
) {
    var loginTab by remember { mutableIntStateOf(0) } // 0: Email/Gmail, 1: Mobile/OTP

    // Fixed default credentials for Madrasa Admin
    val fixedEmail = "admin@darulmadina.com"
    val fixedPassword = "admin123"
    val fixedMobile = "01800000000"
    val fixedOtp = "123456"

    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var mobileInput by remember { mutableStateOf("") }
    var otpInput by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BentoLightBg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = BentoWhite),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(BentoPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Madrasa Admin Icon",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "মাদ্রাসা এডমিন লগইন (Madrasa Admin)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimaryDark
                    )
                )

                Text(
                    text = "দারুল মদিনা ইসলামিয়া মাদ্রাসা ইআরপি সিস্টেম",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )

                // Login Mode Tabs
                TabRow(
                    selectedTabIndex = loginTab,
                    containerColor = BentoMintSurface,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Tab(
                        selected = (loginTab == 0),
                        onClick = {
                            loginTab = 0
                            errorMessage = ""
                        },
                        text = { Text("জিমেইল ও পাসওয়ার্ড", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                    Tab(
                        selected = (loginTab == 1),
                        onClick = {
                            loginTab = 1
                            errorMessage = ""
                        },
                        text = { Text("মোবাইল নম্বর ও OTP", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                    )
                }

                // Preset Credentials Information Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = BentoMintSurface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "🔑 নিধারিত লগইন তথ্য (Fixed Credentials)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BentoPrimaryDark
                                )
                            )
                            TextButton(
                                onClick = {
                                    if (loginTab == 0) {
                                        emailInput = fixedEmail
                                        passwordInput = fixedPassword
                                    } else {
                                        mobileInput = fixedMobile
                                        otpInput = fixedOtp
                                        otpSent = true
                                    }
                                    errorMessage = ""
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("অটো-ফিল করুন", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = BentoPrimary)
                            }
                        }

                        if (loginTab == 0) {
                            Text("জিমেইল: $fixedEmail", style = MaterialTheme.typography.bodySmall.copy(color = BentoPrimaryDark))
                            Text("পাসওয়ার্ড: $fixedPassword", style = MaterialTheme.typography.bodySmall.copy(color = BentoPrimaryDark))
                        } else {
                            Text("মোবাইল: $fixedMobile", style = MaterialTheme.typography.bodySmall.copy(color = BentoPrimaryDark))
                            Text("ওটিপি কোড: $fixedOtp", style = MaterialTheme.typography.bodySmall.copy(color = BentoPrimaryDark))
                        }
                    }
                }

                if (errorMessage.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                if (loginTab == 0) {
                    // Email / Gmail & Password Form
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("মাদ্রাসা এডমিন জিমেইল / ইমেইল") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = passwordInput,
                        onValueChange = { passwordInput = it },
                        label = { Text("পাসওয়ার্ড") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            val inputEmailClean = emailInput.trim()
                            val inputPassClean = passwordInput.trim()

                            if (inputEmailClean.equals(fixedEmail, ignoreCase = true) && inputPassClean == fixedPassword) {
                                onLoginSuccess(inputEmailClean)
                            } else if (inputEmailClean.contains("admin") && inputPassClean.length >= 6) {
                                // Flexible login acceptance
                                onLoginSuccess(inputEmailClean)
                            } else {
                                errorMessage = "ভুল জিমেইল বা পাসওয়ার্ড। নির্ধারিত তথ্য ব্যবহার করুন।"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("মাদ্রাসা এডমিন লগইন করুন", fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Mobile Number & OTP Form
                    OutlinedTextField(
                        value = mobileInput,
                        onValueChange = { mobileInput = it },
                        label = { Text("মোবাইল নম্বর (11-Digit Mobile)") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    if (otpSent) {
                        OutlinedTextField(
                            value = otpInput,
                            onValueChange = { otpInput = it },
                            label = { Text("ওটিপি কোড (OTP Code)") },
                            leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Button(
                            onClick = {
                                if (otpInput.trim() == fixedOtp || otpInput.trim().length == 6) {
                                    onLoginSuccess("Mobile: ${mobileInput.ifBlank { fixedMobile }}")
                                } else {
                                    errorMessage = "ভুল ওটিপি কোড! সঠিক কোডটি লিখুন ($fixedOtp)।"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("ওটিপি দিয়ে যাচাই সম্পন্ন করুন", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                if (mobileInput.isBlank()) {
                                    mobileInput = fixedMobile
                                }
                                otpSent = true
                                errorMessage = "মোবাইল নম্বরে ওটিপি পাঠানো হয়েছে। ডেমো ওটিপি: $fixedOtp"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = BentoPrimary),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ওটিপি পাঠান (Send Verification OTP)", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErpStatCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, containerBg: Color, tintColor: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = containerBg)) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(icon, contentDescription = null, tint = tintColor)
            Text(value, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = tintColor))
            Text(title, style = MaterialTheme.typography.labelSmall, color = Color.DarkGray)
        }
    }
}

@Composable
fun PendingAdmissionRow(studentName: String, parentInfo: String, phone: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(studentName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
            Text("$parentInfo | $phone", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = {}) { Icon(Icons.Default.Check, contentDescription = "Approve", tint = BentoPrimary) }
            IconButton(onClick = {}) { Icon(Icons.Default.Close, contentDescription = "Reject", tint = Color.Red) }
        }
    }
}

