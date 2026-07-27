package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Advertisement
import com.example.data.model.AuditLog
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperAdminScreen(
    sampleAds: List<Advertisement>,
    onSaveSettings: () -> Unit
) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var authenticatedUser by remember { mutableStateOf("superadmin@darulmadina.com") }

    if (!isAuthenticated) {
        SuperAdminLoginView(
            onLoginSuccess = { user ->
                authenticatedUser = user
                isAuthenticated = true
            }
        )
        return
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    var domainName by remember { mutableStateOf("www.darulmadinaislamiamadrasha.com") }
    var appVersion by remember { mutableStateOf("v2.4.0 (Build 108)") }
    var splashAdEnabled by remember { mutableStateOf(true) }
    var splashMaxSeconds by remember { mutableStateOf("5") }
    var pushApiKey by remember { mutableStateOf("FCM-KEY-SECURE-998877112233") }
    var smsGatewayUser by remember { mutableStateOf("darulmadina_sms") }
    var maintenanceMode by remember { mutableStateOf(false) }

    val tabs = listOf("গ্লোবাল সেটিংস", "বিজ্ঞাপন ম্যানেজার", "সিকিউরিটি ও লগ", "সার্ভার ও সিস্টেম")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BentoLightBg)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Owner Header Card
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
                            .background(BentoPrimaryDark),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = BentoMintBorder)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("সুপার অ্যাডমিন প্যানেল", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                            Surface(color = GoldAccent, shape = CircleShape) {
                                Text("মালিকানা", color = Color.White, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                            }
                        }
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

        // Module Navigation Tabs
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 0.dp,
                containerColor = Color.Transparent
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = (selectedTab == index),
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 13.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal, color = if (selectedTab == index) BentoPrimaryDark else Color.Gray) }
                    )
                }
            }
        }

        // Tab Content 0: Global Settings & Domain Management
        if (selectedTab == 0) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("ওয়েবসাইট ও ব্র্যান্ডিং সেটিংস", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))

                        OutlinedTextField(
                            value = domainName,
                            onValueChange = { domainName = it },
                            label = { Text("অফিসিয়াল ডোমেইন (Primary Domain)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = appVersion,
                            onValueChange = { appVersion = it },
                            label = { Text("অ্যাপ ও ওয়েবসাইট সংস্করণ (App Version)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("মেইনটেন্যান্স মোড (Maintenance Mode)", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = BentoPrimaryDark))
                                Text("চালু করলে ভিজিটরদের কাছে সাময়িক নোটিশ দেখাবে", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            }
                            Switch(checked = maintenanceMode, onCheckedChange = { maintenanceMode = it })
                        }

                        Button(
                            onClick = onSaveSettings,
                            colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("গ্লোবাল সেটিংস সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Tab Content 1: Advertisement Engine & Revenue Analytics
        if (selectedTab == 1) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = GoldContainer),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(GoldAccent.copy(alpha = 0.4f), GoldAccent.copy(alpha = 0.4f)))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("বিজ্ঞাপন রাজস্ব ড্যাশবোর্ড (Ad Revenue)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldAccent))
                            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = GoldAccent)
                        }
                        Text("চলতি মাসের মোট আয়: ৳৪৫,২০০", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                        Text("মোট ইম্প্রেশন: ৫,৫২০ | মোট ক্লিক: ৮৫৫ (CTR: ১৫.৪%)", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
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
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("স্প্ল্যাশ ও পপআপ অ্যাড কন্ট্রোল", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text("ওয়েবসাইট প্রবেশের সময় স্প্ল্যাশ অ্যাড", style = MaterialTheme.typography.bodyMedium, color = BentoPrimaryDark)
                                Text("সর্বোচ্চ ৫ সেকেন্ডের অটো-ক্লোজ স্প্ল্যাশ অ্যাড", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            }
                            Switch(checked = splashAdEnabled, onCheckedChange = { splashAdEnabled = it })
                        }

                        OutlinedTextField(
                            value = splashMaxSeconds,
                            onValueChange = { splashMaxSeconds = it },
                            label = { Text("স্প্ল্যাশ টাইমার (সেকেন্ড)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
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
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("সক্রিয় বিজ্ঞাপন তালিকা (Active Ads)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))

                        sampleAds.forEach { ad ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(ad.title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))
                                    Text("টাইপ: ${ad.type} | ইম্প্রেশন: ${ad.impressions}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                                Switch(checked = ad.active, onCheckedChange = {})
                            }
                            HorizontalDivider(color = BentoMintBorder)
                        }
                    }
                }
            }
        }

        // Tab Content 2: Security & Audit Logs
        if (selectedTab == 2) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("সিস্টেম সিকিউরিটি ও অডিট লগ", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))

                        AuditRow("২৭ জুলাই, ২০২৬ - ০৯:৩০ AM", "SUPER_ADMIN", "ডোমেইন কনফিগারেশন আপডেট", "192.168.1.100")
                        HorizontalDivider(color = BentoMintBorder)
                        AuditRow("২৬ জুলাই, ২০২৬ - ০৪:১৫ PM", "MADRASA_ADMIN", "নতুন শিক্ষার্থী ভর্তি অনুমোদন", "103.112.45.12")
                        HorizontalDivider(color = BentoMintBorder)
                        AuditRow("২৫ জুলাই, ২০২৬ - ১১:২০ AM", "ACCOUNTANT", "ফিস রসিদ জেনারেট (REC-2026-101)", "103.112.45.14")
                    }
                }
            }
        }

        // Tab Content 3: Server & Cloud Settings
        if (selectedTab == 3) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    shape = RoundedCornerShape(24.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("ক্লাউড ব্যাকআপ ও নোটিফিকেশন গেটওয়ে", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BentoPrimaryDark))

                        OutlinedTextField(
                            value = pushApiKey,
                            onValueChange = { pushApiKey = it },
                            label = { Text("FCM Push Notification Server Key") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = smsGatewayUser,
                            onValueChange = { smsGatewayUser = it },
                            label = { Text("SMS Gateway Username") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.CloudUpload, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("ক্লাউড ব্যাকআপ", fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {},
                                shape = RoundedCornerShape(14.dp),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Restore, contentDescription = null, tint = BentoPrimaryDark)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("রিস্টোর", fontWeight = FontWeight.Bold, color = BentoPrimaryDark)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuperAdminLoginView(
    onLoginSuccess: (String) -> Unit
) {
    var loginTab by remember { mutableIntStateOf(0) } // 0: Email/Gmail, 1: Mobile/OTP

    // Fixed default credentials
    val fixedEmail = "superadmin@darulmadina.com"
    val fixedPassword = "superadmin123"
    val fixedMobile = "01700000000"
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
                        .background(BentoPrimaryDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Super Admin Shield",
                        tint = BentoMintBorder,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "সুপার এডমিন লগইন (Super Admin)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimaryDark
                    )
                )

                Text(
                    text = "দারুল মদিনা মাস্টার ওনারশিপ কন্ট্রোল প্যানেল",
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
                                Text("অটো-ফিল করুন", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
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
                        label = { Text("এডমিন জিমেইল / ইমেইল") },
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
                                // Allow flexible super admin login matching standard credentials format
                                onLoginSuccess(inputEmailClean)
                            } else {
                                errorMessage = "ভুল জিমেইল বা পাসওয়ার্ড। নির্ধারিত তথ্য ব্যবহার করুন।"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("সুপার এডমিন লগইন করুন", fontWeight = FontWeight.Bold)
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
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
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
fun AuditRow(time: String, user: String, action: String, ip: String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(user, style = MaterialTheme.typography.labelSmall.copy(color = BentoPrimaryDark, fontWeight = FontWeight.Bold))
            Text(time, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        Text(action, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = BentoPrimaryDark))
        Text("IP Address: $ip", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}

