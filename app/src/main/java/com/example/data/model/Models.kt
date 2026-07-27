package com.example.data.model

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    BANGLA("bn", "Bangla", "বাংলা"),
    ENGLISH("en", "English", "English"),
    ARABIC("ar", "Arabic", "العربية")
}

enum class UserRole(val roleName: String, val displayName: String, val iconName: String) {
    SUPER_ADMIN("super_admin", "Super Admin", "Shield"),
    MADRASA_ADMIN("madrasa_admin", "Madrasa Admin", "AdminPanelSettings"),
    TEACHER("teacher", "Teacher", "School"),
    STUDENT("student", "Student", "Person"),
    PARENT("parent", "Parent", "FamilyRestroom"),
    ACCOUNTANT("accountant", "Accountant", "Payments"),
    LIBRARIAN("librarian", "Librarian", "MenuBook"),
    HOSTEL_MANAGER("hostel_manager", "Hostel Manager", "Hotel"),
    ADMISSION_OFFICER("admission_officer", "Admission Officer", "HowToReg")
}

data class Department(
    val id: String,
    val nameBn: String,
    val nameEn: String,
    val nameAr: String,
    val descriptionBn: String,
    val headTeacher: String,
    val iconName: String
)

data class Course(
    val id: String,
    val titleBn: String,
    val titleEn: String,
    val departmentId: String,
    val duration: String,
    val subjects: List<String>
)

data class TeacherProfile(
    val id: String,
    val nameBn: String,
    val designationBn: String,
    val qualification: String,
    val department: String,
    val phone: String,
    val photoRes: String? = null
)

data class Advertisement(
    val id: String,
    val title: String,
    val type: String, // Banner, Homepage, Sidebar, Popup, Video
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val targetUrl: String,
    val impressions: Int = 0,
    val clicks: Int = 0,
    val active: Boolean = true
)

data class DailyVerse(
    val arabicText: String,
    val bnTranslation: String,
    val enTranslation: String,
    val reference: String
)

data class DailyHadith(
    val arabicText: String,
    val bnTranslation: String,
    val enTranslation: String,
    val bookReference: String
)

data class ExamResult(
    val studentId: String,
    val studentName: String,
    val className: String,
    val examName: String, // Annual, Half-Yearly, Quran Hifz Test
    val marksObtained: Int,
    val totalMarks: Int,
    val gpaOrGrade: String,
    val qrVerificationCode: String
)

data class DigitalBook(
    val id: String,
    val titleBn: String,
    val author: String,
    val category: String, // Tafsir, Hadith, Fiqh, Arabic Grammar
    val sizeMb: Double,
    val downloadUrl: String
)

data class LiveClassSession(
    val id: String,
    val subject: String,
    val teacherName: String,
    val className: String,
    val timeSlot: String,
    val platform: String, // Zoom, Google Meet
    val joinLink: String,
    val isLiveNow: Boolean = false
)

data class AuditLog(
    val id: String,
    val timestamp: String,
    val userRole: String,
    val action: String,
    val details: String,
    val ipAddress: String
)
