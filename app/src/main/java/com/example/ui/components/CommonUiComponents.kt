package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.UserRole
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBrandingBar(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    currentRole: UserRole,
    onRoleSelected: (UserRole) -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onOpenAiAssistant: () -> Unit,
    onOpenQrVerify: () -> Unit
) {
    var langMenuExpanded by remember { mutableStateOf(false) }
    var roleMenuExpanded by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Column {
            // Official Website Marquee / Announcement Ribbon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(EmeraldDark, EmeraldPrimary, GoldAccent)
                        )
                    )
                    .padding(vertical = 4.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Website",
                            tint = GoldLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "www.darulmadinaislamiamadrasha.com",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📞 01700-000000",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                        )
                        Text(
                            text = "✉️ info@darulmadina.edu.bd",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                        )
                    }
                }
            }

            // Main Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Branding Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(EmeraldContainer)
                            .border(1.dp, GoldAccent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon_1785170808390),
                            contentDescription = "Logo",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Column {
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.BANGLA -> "দারুল মদিনা ইসলামিয়া মাদ্রাসা"
                                AppLanguage.ARABIC -> "مدرسة دار المدينة الإسلامية"
                                AppLanguage.ENGLISH -> "Darul Madina Islamia Madrasha"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.BANGLA -> "স্মার্ট অনলাইন মাদ্রাসা ম্যানেজমেন্ট সিস্টেম"
                                AppLanguage.ARABIC -> "نظام إدارة المدرسة الإسلامية الذكي"
                                AppLanguage.ENGLISH -> "Smart Islamic Madrasa Management System"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Controls: Language, Role, Dark Mode, AI, QR
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // QR Code Scanner Action
                    IconButton(onClick = onOpenQrVerify) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = "QR Verify",
                            tint = EmeraldPrimary
                        )
                    }

                    // AI Assistant Action
                    IconButton(onClick = onOpenAiAssistant) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Assistant",
                            tint = GoldAccent
                        )
                    }

                    // Theme Toggle
                    IconButton(onClick = onToggleDarkMode) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Theme Toggle",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Language Selector Button
                    Box {
                        AssistChip(
                            onClick = { langMenuExpanded = true },
                            label = { Text(currentLanguage.nativeName, fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = SkyContainer,
                                labelColor = SkyAccent
                            )
                        )

                        DropdownMenu(
                            expanded = langMenuExpanded,
                            onDismissRequest = { langMenuExpanded = false }
                        ) {
                            AppLanguage.entries.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text("${lang.nativeName} (${lang.displayName})") },
                                    onClick = {
                                        onLanguageSelected(lang)
                                        langMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Role Switcher Button
                    Box {
                        IconButton(onClick = { roleMenuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Role Switcher",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = roleMenuExpanded,
                            onDismissRequest = { roleMenuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text("বর্তমান ভূমিকা: ${currentRole.displayName}", fontWeight = FontWeight.Bold)
                                },
                                onClick = {},
                                enabled = false
                            )
                            HorizontalDivider()
                            UserRole.entries.forEach { role ->
                                DropdownMenuItem(
                                    text = { Text(role.displayName) },
                                    onClick = {
                                        onRoleSelected(role)
                                        roleMenuExpanded = false
                                    },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = when(role) {
                                                UserRole.SUPER_ADMIN -> Icons.Default.Shield
                                                UserRole.MADRASA_ADMIN -> Icons.Default.AdminPanelSettings
                                                UserRole.TEACHER -> Icons.Default.School
                                                UserRole.STUDENT -> Icons.Default.Person
                                                UserRole.PARENT -> Icons.Default.FamilyRestroom
                                                UserRole.ACCOUNTANT -> Icons.Default.Payments
                                                UserRole.LIBRARIAN -> Icons.Default.MenuBook
                                                UserRole.HOSTEL_MANAGER -> Icons.Default.Hotel
                                                UserRole.ADMISSION_OFFICER -> Icons.Default.HowToReg
                                            },
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdBannerCard(
    adTitle: String,
    targetUrl: String,
    modifier: Modifier = Modifier,
    onAdClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onAdClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = GoldContainer
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(listOf(GoldAccent.copy(alpha = 0.4f), GoldAccent.copy(alpha = 0.4f)))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    color = GoldAccent,
                    shape = CircleShape
                ) {
                    Text(
                        text = "বিজ্ঞাপন",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Text(
                    text = adTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimaryDark
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = "Open Link",
                tint = GoldAccent,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun SplashAdDialog(
    adTitle: String,
    onDismiss: () -> Unit
) {
    var countdown by remember { mutableIntStateOf(5) }

    LaunchedEffect(Unit) {
        while (countdown > 0) {
            kotlinx.coroutines.delay(1000)
            countdown--
        }
    }

    AlertDialog(
        onDismissRequest = { if (countdown == 0) onDismiss() },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "দারুল মদিনা তথ্য কেন্দ্র",
                    style = MaterialTheme.typography.titleMedium.copy(color = EmeraldPrimary)
                )
                Surface(
                    color = EmeraldContainer,
                    shape = CircleShape
                ) {
                    Text(
                        text = if (countdown > 0) "$countdown সিসেকন্ড" else "বন্ধ করুন",
                        style = MaterialTheme.typography.labelSmall.copy(color = EmeraldDark),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = adTitle,
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    text = "অনলাইনে নতুন সেশনে আসন সংখ্যা সীমিত! এখনই আবেদন সম্পূর্ণ করুন।",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                enabled = countdown == 0,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
            ) {
                Text(if (countdown == 0) "প্রবেশ করুন" else "অপেক্ষা করুন ($countdown)")
            }
        }
    )
}
