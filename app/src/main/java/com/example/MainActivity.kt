package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.MadrasaDatabase
import com.example.data.local.NoticeEntity
import com.example.data.local.StudentEntity
import com.example.data.model.AppLanguage
import com.example.data.model.UserRole
import com.example.data.repository.GeminiRepository
import com.example.data.repository.MadrasaRepository
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var database: MadrasaDatabase
    private lateinit var repository: MadrasaRepository
    private val geminiRepository = GeminiRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        database = MadrasaDatabase.getDatabase(this)
        repository = MadrasaRepository(database)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            var currentLanguage by remember { mutableStateOf(AppLanguage.BANGLA) }
            var currentRole by remember { mutableStateOf(UserRole.MADRASA_ADMIN) }
            var currentNavTab by remember { mutableIntStateOf(0) } // 0: Home, 1: Super Admin, 2: Madrasa Admin, 3: Learning, 4: Role Portal, 5: AI Assistant

            // Modals & Dialogs State
            var showSplashAd by remember { mutableStateOf(true) }
            var showPaymentModal by remember { mutableStateOf(false) }
            var initialPaymentCategory by remember { mutableStateOf("Student Fee") }
            var showQrVerifyModal by remember { mutableStateOf(false) }

            val coroutineScope = rememberCoroutineScope()

            // Seed initial data if empty on startup
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    repository.seedInitialDataIfEmpty()
                }
            }

            // Room Flows
            val students by repository.allStudents.collectAsStateWithLifecycle(initialValue = emptyList())
            val notices by repository.allNotices.collectAsStateWithLifecycle(initialValue = emptyList())
            val prayerTimes by repository.prayerTimes.collectAsStateWithLifecycle(initialValue = null)

            // Static Data
            val departments = remember { repository.getDepartments() }
            val teachers = remember { repository.getTeachers() }
            val sampleAds = remember { repository.getSampleAds() }
            val digitalBooks = remember { repository.getSampleDigitalBooks() }
            val liveClasses = remember { repository.getSampleLiveClasses() }
            val sampleResults = remember { repository.getSampleResults() }

            DarulMadinaTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBrandingBar(
                            currentLanguage = currentLanguage,
                            onLanguageSelected = { currentLanguage = it },
                            currentRole = currentRole,
                            onRoleSelected = { role ->
                                currentRole = role
                                if (role == UserRole.SUPER_ADMIN) currentNavTab = 1
                                else if (role == UserRole.MADRASA_ADMIN) currentNavTab = 2
                                else currentNavTab = 4
                            },
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = { isDarkMode = !isDarkMode },
                            onOpenAiAssistant = { currentNavTab = 5 },
                            onOpenQrVerify = { showQrVerifyModal = true }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = BentoWhite,
                            tonalElevation = 2.dp,
                            windowInsets = WindowInsets.navigationBars
                        ) {
                            val navColors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BentoPrimaryDark,
                                selectedTextColor = BentoPrimaryDark,
                                indicatorColor = BentoMintSurface,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray
                            )

                            NavigationBarItem(
                                selected = (currentNavTab == 0),
                                onClick = { currentNavTab = 0 },
                                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                                label = { Text("হোম", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                            NavigationBarItem(
                                selected = (currentNavTab == 1),
                                onClick = { currentNavTab = 1 },
                                icon = { Icon(Icons.Default.Shield, contentDescription = "Super Admin") },
                                label = { Text("সুপার এডমিন", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                            NavigationBarItem(
                                selected = (currentNavTab == 2),
                                onClick = { currentNavTab = 2 },
                                icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Madrasa ERP") },
                                label = { Text("এডমিন", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                            NavigationBarItem(
                                selected = (currentNavTab == 3),
                                onClick = { currentNavTab = 3 },
                                icon = { Icon(Icons.Default.OndemandVideo, contentDescription = "Learning") },
                                label = { Text("এলএমএস", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                            NavigationBarItem(
                                selected = (currentNavTab == 4),
                                onClick = { currentNavTab = 4 },
                                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Portals") },
                                label = { Text("প্রোফাইল", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                            NavigationBarItem(
                                selected = (currentNavTab == 5),
                                onClick = { currentNavTab = 5 },
                                icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI") },
                                label = { Text("এআই", fontWeight = FontWeight.Bold) },
                                colors = navColors
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = currentNavTab,
                            label = "NavTransition"
                        ) { targetTab ->
                            when (targetTab) {
                                0 -> PublicHomeScreen(
                                    prayerTimes = prayerTimes,
                                    notices = notices,
                                    departments = departments,
                                    teachers = teachers,
                                    currentLanguage = currentLanguage,
                                    onOpenAdmission = {
                                        initialPaymentCategory = "Student Fee"
                                        showPaymentModal = true
                                    },
                                    onOpenPayment = { category ->
                                        initialPaymentCategory = category
                                        showPaymentModal = true
                                    },
                                    onOpenQrVerify = { showQrVerifyModal = true },
                                    onOpenAiAssistant = { currentNavTab = 5 }
                                )
                                1 -> SuperAdminScreen(
                                    sampleAds = sampleAds,
                                    onSaveSettings = {}
                                )
                                2 -> MadrasaAdminScreen(
                                    students = students,
                                    teachers = teachers,
                                    notices = notices,
                                    digitalBooks = digitalBooks,
                                    onAddStudent = { student ->
                                        coroutineScope.launch { repository.addStudent(student) }
                                    },
                                    onAddNotice = { notice ->
                                        coroutineScope.launch { repository.addNotice(notice) }
                                    }
                                )
                                3 -> OnlineLearningScreen(
                                    liveClasses = liveClasses
                                )
                                4 -> PortalViewScreen(
                                    currentRole = currentRole,
                                    onOpenPayment = { cat ->
                                        initialPaymentCategory = cat
                                        showPaymentModal = true
                                    },
                                    onOpenQrVerify = { showQrVerifyModal = true }
                                )
                                5 -> AiAssistantScreen(
                                    currentLanguage = currentLanguage,
                                    geminiRepo = geminiRepository
                                )
                            }
                        }

                        // Splash Ad Dialog on First Entrance
                        if (showSplashAd) {
                            SplashAdDialog(
                                adTitle = "হিফজ ও কিতাব বিভাগে ২০২৬ সেশনে ভর্তি ফর্ম পাওয়া যাচ্ছে!",
                                onDismiss = { showSplashAd = false }
                            )
                        }

                        // Online Payment & Fee / Donation Dialog
                        if (showPaymentModal) {
                            OnlinePaymentDialog(
                                initialCategory = initialPaymentCategory,
                                onDismiss = { showPaymentModal = false },
                                onPaymentSuccess = { name, amount, cat, method ->
                                    // Payment processed successfully
                                }
                            )
                        }

                        // QR Certificate & Marksheet Verification Dialog
                        if (showQrVerifyModal) {
                            QrVerifyDialog(
                                sampleResults = sampleResults,
                                onDismiss = { showQrVerifyModal = false }
                            )
                        }
                    }
                }
            }
        }
    }
}
