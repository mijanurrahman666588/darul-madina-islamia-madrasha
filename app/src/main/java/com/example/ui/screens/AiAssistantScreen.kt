package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.data.model.AppLanguage
import com.example.data.repository.GeminiRepository
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class ChatMessage(val sender: String, val text: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantScreen(
    currentLanguage: AppLanguage,
    geminiRepo: GeminiRepository
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by remember { mutableIntStateOf(0) }
    
    // Chat State
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage("AI Assistant", "আসসালামু আলাইকুম! আমি দারুল মদিনা ইসলামিয়া মাদ্রাসার এআই সহকারী। ভর্তি, অনলাইন সার্ভিস, রুটিন বা যেকোনো প্রশ্ন করতে পারেন।", false)
            )
        )
    }
    var userInput by remember { mutableStateOf("") }
    var isChatLoading by remember { mutableStateOf(false) }

    // Draft State
    var noticeTopic by remember { mutableStateOf("অফিসিয়াল ভর্তি নোটিশ ২০২৬") }
    var noticeCategory by remember { mutableStateOf("Admission") }
    var generatedDraft by remember { mutableStateOf("") }
    var isDraftLoading by remember { mutableStateOf(false) }

    val tabs = listOf("এআই অ্যাসিস্ট্যান্ট চ্যাট", "এআই নোটিশ ড্রাফট জেনারেটর")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Top Banner
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
                Surface(color = GoldContainer, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(48.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("স্মার্ট এআই ইসলামিক ফিচারস", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldAccent))
                    Text("Gemini AI চালিত রিয়েল-টাইম প্রশ্নোত্তর ও অটো-ড্রাফট জেনারেটর", style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
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

        Spacer(modifier = Modifier.height(12.dp))

        // Content Tab 0: Chat
        if (selectedTab == 0) {
            Column(modifier = Modifier.weight(1f)) {
                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(messages) { msg ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = if (msg.isUser) Alignment.CenterEnd else Alignment.CenterStart
                        ) {
                            Surface(
                                color = if (msg.isUser) EmeraldPrimary else MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                                shadowElevation = 1.dp
                            ) {
                                Text(
                                    text = msg.text,
                                    color = if (msg.isUser) Color.White else MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                    if (isChatLoading) {
                        item {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = EmeraldPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = userInput,
                        onValueChange = { userInput = it },
                        placeholder = { Text("আপনার প্রশ্নটি লিখুন...") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    IconButton(
                        onClick = {
                            if (userInput.isNotBlank() && !isChatLoading) {
                                val query = userInput
                                messages = messages + ChatMessage("User", query, true)
                                userInput = ""
                                isChatLoading = true

                                coroutineScope.launch {
                                    val reply = geminiRepo.askIslamicAssistant(query, currentLanguage.code)
                                    messages = messages + ChatMessage("AI Assistant", reply, false)
                                    isChatLoading = false
                                }
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = EmeraldPrimary)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }

        // Content Tab 1: Notice Generator
        if (selectedTab == 1) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = noticeTopic,
                    onValueChange = { noticeTopic = it },
                    label = { Text("নোটিশের মূল বিষয় (Topic)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = noticeCategory,
                    onValueChange = { noticeCategory = it },
                    label = { Text("ক্যাটাগরি (Category)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        isDraftLoading = true
                        coroutineScope.launch {
                            generatedDraft = geminiRepo.generateNoticeDraft(noticeTopic, noticeCategory, currentLanguage.code)
                            isDraftLoading = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isDraftLoading
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isDraftLoading) "অটো-ড্রাফটিং হচ্ছে..." else "এআই নোটিশ ড্রাফট জেনারেট করুন")
                }

                if (generatedDraft.isNotBlank()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("জেনারেটকৃত নোটিশ খসড়া:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = EmeraldPrimary))
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                            Text(generatedDraft, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
