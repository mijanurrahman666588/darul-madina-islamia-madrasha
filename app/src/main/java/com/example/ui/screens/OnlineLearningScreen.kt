package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import com.example.data.model.LiveClassSession
import com.example.ui.theme.*

@Composable
fun OnlineLearningScreen(
    liveClasses: List<LiveClassSession>
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("লাইভ ক্লাস (Live)", "রেকর্ডেড লেকচার", "কুইজ ও অ্যাসাইনমেন্ট", "লেকচার শিট (PDF)")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
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
                    Surface(color = SkyContainer, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(48.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.OndemandVideo, contentDescription = null, tint = SkyAccent)
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("অনলাইন লার্নিং পোর্টাল", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = SkyAccent))
                        Text("Zoom ও Google Meet লাইভ ক্লাস, রেকর্ডেড ক্লাস ও কুইজ", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        // Tabs
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
                        text = { Text(title, fontSize = 13.sp, fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal) }
                    )
                }
            }
        }

        if (selectedTab == 0) {
            items(liveClasses) { session ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(session.subject, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            if (session.isLiveNow) {
                                Surface(color = Color.Red, shape = RoundedCornerShape(4.dp)) {
                                    Text("● LIVE NOW", color = Color.White, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }

                        Text("শিক্ষক: ${session.teacherName} | শ্রেণি: ${session.className}", style = MaterialTheme.typography.bodySmall)
                        Text("সময়সূচি: ${session.timeSlot} (${session.platform})", style = MaterialTheme.typography.labelSmall, color = EmeraldPrimary)

                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = if (session.isLiveNow) Color.Red else EmeraldPrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.VideoCall, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (session.isLiveNow) "লাইভ ক্লাসে যোগ দিন (Join ${session.platform})" else "ক্লাস শুরু হবে ${session.timeSlot}")
                        }
                    }
                }
            }
        }

        if (selectedTab == 1) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("রেকর্ডেড লেকচার ভিডিও", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                        VideoLectureRow("সহীহ বুখারী - কিতাবুল ঈমান ১ম পর্ব", "মুফতি আব্দুর রশীদ", "১ ঘন্টা ২০ মিনিট")
                        HorizontalDivider()
                        VideoLectureRow("তাজবীদ শিক্ষা - মাখরাজ ও মাদ্দ", "হাফেজ মাওলানা নূরুল ইসলাম", "৪৫ মিনিট")
                    }
                }
            }
        }

        if (selectedTab == 2) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("অনলাইন কুইজ ও প্র্যাকটিস টেস্ট", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                        QuizRow("আকাইদ ও ফিকহ প্র্যাকটিস কুইজ - ১", "১০ টি প্রশ্ন | ১০ মিনিট", true)
                        HorizontalDivider()
                        QuizRow("আরবি ব্যাকরণ (নাহু ও সরফ) কুইজ", "১৫ টি প্রশ্ন | ১৫ মিনিট", false)
                    }
                }
            }
        }

        if (selectedTab == 3) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(12.dp)) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("ক্লাস লেকচার শিট ও নোটস (PDF)", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

                        PdfNoteRow("নূরাণী তাজবীদ গাইড ২০২৬.pdf", "সাইজ: ৩.৪ MB")
                        HorizontalDivider()
                        PdfNoteRow("দাওরায়ে হাদীস বুখারী সংকলন নোটস.pdf", "সাইজ: ১২.১ MB")
                    }
                }
            }
        }
    }
}

@Composable
fun VideoLectureRow(title: String, teacher: String, duration: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Text("$teacher | সময়: $duration", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        IconButton(onClick = {}) { Icon(Icons.Default.PlayCircle, contentDescription = "Play", tint = EmeraldPrimary) }
    }
}

@Composable
fun QuizRow(title: String, details: String, active: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Text(details, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        Button(
            onClick = {},
            enabled = active,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
        ) {
            Text("কুইজ শুরু")
        }
    }
}

@Composable
fun PdfNoteRow(fileName: String, sizeInfo: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(fileName, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Text(sizeInfo, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        IconButton(onClick = {}) { Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", tint = RedAccent()) }
    }
}

@Composable
fun RedAccent(): Color = Color(0xFFDC2626)
