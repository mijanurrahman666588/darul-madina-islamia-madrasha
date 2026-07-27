package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.NoticeEntity
import com.example.data.local.PrayerTimeEntity
import com.example.data.model.AppLanguage
import com.example.data.model.Department
import com.example.data.model.TeacherProfile
import com.example.ui.components.AdBannerCard
import com.example.ui.theme.*

@Composable
fun PublicHomeScreen(
    prayerTimes: PrayerTimeEntity?,
    notices: List<NoticeEntity>,
    departments: List<Department>,
    teachers: List<TeacherProfile>,
    currentLanguage: AppLanguage,
    onOpenAdmission: () -> Unit,
    onOpenPayment: (String) -> Unit,
    onOpenQrVerify: () -> Unit,
    onOpenAiAssistant: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BentoLightBg),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Bento Card: Hijri Date, Verse of the Day & Language Tag
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(BentoPrimaryDark)
                    .border(1.dp, BentoMintBorder.copy(alpha = 0.3f), RoundedCornerShape(28.dp))
            ) {
                // Background Decorative Mosque Graphic
                Image(
                    painter = painterResource(id = R.drawable.img_quran_bg_1785170860470),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .matchParentSize()
                        .clip(RoundedCornerShape(28.dp)),
                    alpha = 0.15f
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = (prayerTimes?.dateHijri ?: "১৫ সফর, ১৪৪৮ হিজরী").uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = BentoMintBorder,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "দৈনিক আয়াত ও বাণী",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Surface(
                            color = BentoPrimary,
                            shape = CircleShape
                        ) {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.BANGLA -> "বাংলা / EN / AR"
                                    AppLanguage.ARABIC -> "عربي / BN"
                                    AppLanguage.ENGLISH -> "EN / BN / AR"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = BentoMintBorder,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }

                    Text(
                        text = "“وَابْتَغِ فِيمَا آتَاكَ اللَّهُ الدَّارَ الْآخِرَةَ”",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "“আল্লাহ তোমাকে যা দান করেছেন, তার মাধ্যমে পরকালের কল্যাণ অনুসন্ধান করো...” (সূরা আল-ক্বসাস: ৭৭)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.9f),
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }

        // 2. Action Bento Grid Layout (2-Column Grid)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Left Column Bento: Large Academic Stats Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp)
                        .clickable { onOpenAdmission() },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = BentoWhite),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(BentoMintSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BarChart,
                                    contentDescription = null,
                                    tint = BentoPrimaryDark,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "একাডেমিক তথ্য",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BentoPrimaryDark
                                )
                            )
                            Text(
                                text = "১,২৪০+ শিক্ষার্থী নিবন্ধিত",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "আজকের উপস্থিতি",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BentoPrimaryDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "৮৫%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BentoPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            LinearProgressIndicator(
                                progress = { 0.85f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(CircleShape),
                                color = BentoPrimary,
                                trackColor = BentoMintSurface
                            )
                        }
                    }
                }

                // Right Column Bento (Vertical Stack: Fees & Zakat + Prayer Times)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(220.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Top Right Square Bento Card: Fees & Zakat
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable { onOpenPayment("Zakat") },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = BentoMintSurface),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = BentoPrimaryDark,
                                    modifier = Modifier.size(22.dp)
                                )
                                Surface(
                                    color = BentoWhite,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "অনলাইন",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = BentoPrimaryDark,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "ফি ও জাকাত",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BentoPrimaryDark
                                    )
                                )
                                Text(
                                    text = "সহজ অনলাইন অনুদান",
                                    style = MaterialTheme.typography.labelSmall.copy(color = BentoPrimary)
                                )
                            }
                        }
                    }

                    // Bottom Right Square Bento Card: Prayer Times
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = BentoSlate)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = BentoMintBorder,
                                    modifier = Modifier.size(22.dp)
                                )
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "পরবর্তী: জোহর",
                                        style = MaterialTheme.typography.labelSmall.copy(color = BentoMintBorder.copy(alpha = 0.8f))
                                    )
                                    Text(
                                        text = prayerTimes?.dhuhr ?: "১২:১৪ PM",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                            Text(
                                text = "সালাতের সময়সূচি",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }

        // 3. Quick Action Buttons Ribbon (Bento Pill Cards)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onOpenAdmission,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BentoPrimaryDark),
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(Icons.Default.HowToReg, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("অনলাইন ভর্তি", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onOpenPayment("Zakat") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("দান / জাকাত", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onOpenQrVerify,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = BentoPrimaryDark, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("যাচাই", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = BentoPrimaryDark)
                }
            }
        }

        // 4. Principal Message Bento Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BentoWhite),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "মুহতামিম সাহেবের বাণী (Principal Message)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BentoPrimaryDark
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_principal_avatar_1785170841766),
                            contentDescription = "Principal Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .border(2.dp, GoldAccent, CircleShape)
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "মুফতি আব্দুর রশীদ",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BentoPrimaryDark
                                )
                            )
                            Text(
                                text = "মহাপরিচালক, দারুল মদিনা ইসলামিয়া মাদ্রাসা",
                                style = MaterialTheme.typography.labelMedium.copy(color = BentoPrimary)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "“কোরআন ও সুন্নাহর বিশুদ্ধ জ্ঞানের সাথে আধুনিক প্রযুক্তির সমন্বয়ে প্রতিটি শিক্ষার্থীকে একজন আদর্শ ও দক্ষ মুসলিম হিসেবে গড়ে তোলাই আমাদের অঙ্গীকার।”",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray),
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // 5. Ad Banner Card
        item {
            AdBannerCard(
                adTitle = "হিফজ ও কিতাব বিভাগে সীমিত আসনে অনলাইন আবেদন চলছে!",
                targetUrl = "https://www.darulmadinaislamiamadrasha.com/admission",
                onAdClick = onOpenAdmission
            )
        }

        // 6. Departments Bento Carousel
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "শিক্ষা বিভাগসমূহ (Departments)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimaryDark
                    )
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(departments) { dept ->
                        DepartmentBentoCard(dept = dept)
                    }
                }
            }
        }

        // 7. Notice Board Bento Box
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = BentoWhite),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(GoldContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Campaign,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "নোটিশ ও সংবাদ (Notices)",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BentoPrimaryDark
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (notices.isEmpty()) {
                        Text("কোনো সাম্প্রতিক নোটিশ নেই।", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    } else {
                        notices.take(3).forEachIndexed { index, notice ->
                            NoticeRowItem(notice = notice)
                            if (index < notices.take(3).lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    color = BentoMintBorder
                                )
                            }
                        }
                    }
                }
            }
        }

        // 8. AI Assistant Bento Banner Float
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenAiAssistant() },
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = BentoWhite),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(BentoPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AI", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "মাদ্রাসা এআই অ্যাসিস্ট্যান্ট",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BentoPrimaryDark
                            )
                        )
                        Text(
                            text = "ভর্তি বা ফলাফল সম্পর্কে প্রশ্ন করুন...",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = BentoPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun DepartmentBentoCard(dept: Department) {
    Card(
        modifier = Modifier
            .width(230.dp)
            .height(135.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = BentoWhite),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(listOf(BentoMintBorder, BentoMintBorder))
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(BentoMintSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = BentoPrimaryDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = dept.nameBn,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BentoPrimaryDark
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = dept.descriptionBn,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "প্রধান: ${dept.headTeacher}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = GoldAccent,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun NoticeRowItem(notice: NoticeEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (notice.isImportant) {
                    Surface(color = Color(0xFFDC2626), shape = RoundedCornerShape(6.dp)) {
                        Text(
                            text = "জরুরী",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = notice.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = BentoPrimaryDark
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = notice.content,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = notice.date,
            style = MaterialTheme.typography.labelSmall.copy(
                color = BentoPrimary,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
