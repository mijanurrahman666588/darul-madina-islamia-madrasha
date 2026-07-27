package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MadrasaRepository(private val db: MadrasaDatabase) {

    val allStudents: Flow<List<StudentEntity>> = db.studentDao().getAllStudents()
    val allNotices: Flow<List<NoticeEntity>> = db.noticeDao().getAllNotices()
    val allDonations: Flow<List<DonationEntity>> = db.donationDao().getAllDonations()
    val totalDonations: Flow<Double?> = db.donationDao().getTotalDonations()
    val prayerTimes: Flow<PrayerTimeEntity?> = db.prayerTimeDao().getPrayerTimes()

    suspend fun addStudent(student: StudentEntity) = db.studentDao().insertStudent(student)
    suspend fun addNotice(notice: NoticeEntity) = db.noticeDao().insertNotice(notice)
    suspend fun addDonation(donation: DonationEntity) = db.donationDao().insertDonation(donation)
    suspend fun updatePrayerTimes(times: PrayerTimeEntity) = db.prayerTimeDao().insertPrayerTimes(times)

    // Pre-seeded static lists for public website features
    fun getDepartments(): List<Department> = listOf(
        Department("1", "হিফজ বিভাগ (Hifz)", "Hifz Division", "قسم التحفيظ", "পবিত্র কোরআন শরিফ হিফজ করার আন্তর্জাতিক মানসম্পন্ন প্রশিক্ষণ কেন্দ্র।", "মাওলানা আব্দুল বারী", "Book"),
        Department("2", "কিতাব বিভাগ (Kitab)", "Kitab Division", "قسم الكتب", "ইবতেদায়ী থেকে দাওরায়ে হাদীস (মাস্টার্স সমমান) পর্যন্ত দ্বীনি শিক্ষা।", "মুফতি মাহমুদুল হাসান", "MenuBook"),
        Department("3", "নূরাণী ও নাজেরা (Nurani)", "Nurani & Najera", "القسم النوراني", "শিশুদের জন্য বিশুদ্ধ কোরআন তিলওয়াত ও প্রাক-প্রাথমিক ইসলামিক শিক্ষা।", "হাফেজ কারী শফিকুল ইসলাম", "School"),
        Department("4", "কম্পিউটার ও তথ্যপ্রযুক্তি", "IT & Computer Science", "قسم الحاسوب", "আধুনিক তথ্যপ্রযুক্তি, সফটওয়্যার ও অনলাইন দাওয়াহ ট্রেনিং।", "প্রকৌশলী তাওহীদুল ইসলাম", "Computer"),
        Department("5", "আরবি ভাষা ও সাহিত্য", "Arabic Literature", "قسم اللغة العربية", "আরবি ভাষায় দক্ষতা অর্জনের বিশেষ কোর্স ও আন্তর্জাতিক বিনিময়।", "ড. কারী মিজানুর রহমান", "Translate")
    )

    fun getTeachers(): List<TeacherProfile> = listOf(
        TeacherProfile("T01", "মুফতি আব্দুর রশীদ", "মহাপরিচালক (Principal)", "দাওরায়ে হাদীস, কায়রো বিশ্ববিদ্যালয় (মিশর)", "প্রশাসন", "01711-123456"),
        TeacherProfile("T02", "হাফেজ মাওলানা নূরুল ইসলাম", "প্রধান শিক্ষক (হিফজ)", "আন্তর্জাতিক হাফেজ ও কারী", "হিফজ বিভাগ", "01822-234567"),
        TeacherProfile("T03", "মুফতি মাহমুদুল হাসান", "মুহাদ্দিস ও বিভাগীয় প্রধান", "মুফতি (দেওবন্দ, ভারত)", "কিতাব বিভাগ", "01933-345678"),
        TeacherProfile("T04", "মাওলানা তারেক মনোয়ার", "প্রভাষক (আরবি সাহিত্য)", "এম.এ (আরবি, ঢাকা বিশ্ববিদ্যালয়)", "আরবি বিভাগ", "01544-456789")
    )

    fun getSampleAds(): List<Advertisement> = listOf(
        Advertisement("AD1", "হিফজ বিভাগে নতুন সেশনে ভর্তি চলছে", "Homepage", null, null, "https://www.darulmadinaislamiamadrasha.com/admission", 1420, 230, true),
        Advertisement("AD2", "যাকাত ও লিল্লাহ বোর্ডিংয়ে দান করুন", "Popup", null, null, "https://www.darulmadinaislamiamadrasha.com/donate", 3200, 510, true),
        Advertisement("AD3", "বার্ষিক ওয়াজ মাহফিল ও দস্তারবন্দী অনুষ্ঠান", "Banner", null, null, "https://www.darulmadinaislamiamadrasha.com/events", 890, 115, true)
    )

    fun getSampleDigitalBooks(): List<DigitalBook> = listOf(
        DigitalBook("B01", "তাফসীরে ইবনে কাছীর (১ম-৮ম খণ্ড)", "আল্লামা ইবনে কাছীর (র.)", "তাফসীর", 45.5, "https://www.darulmadinaislamiamadrasha.com/pdf/tafsir.pdf"),
        DigitalBook("B02", "সহীহ আল-বুখারী (সম্পূর্ণ আরবী-বাংলা)", "ইমাম বুখারী (র.)", "হাদীস", 68.0, "https://www.darulmadinaislamiamadrasha.com/pdf/bukhari.pdf"),
        DigitalBook("B03", "রিয়াদুস সালেহীন", "ইমাম নববী (র.)", "হাদীস", 18.2, "https://www.darulmadinaislamiamadrasha.com/pdf/riyad.pdf"),
        DigitalBook("B04", "ফিকহুস সুন্নাহ (১ম-৩য় খণ্ড)", "সায়্যিদ সাবিক (র.)", "ফিকহ", 32.4, "https://www.darulmadinaislamiamadrasha.com/pdf/fiqh.pdf")
    )

    fun getSampleLiveClasses(): List<LiveClassSession> = listOf(
        LiveClassSession("LC1", "সহীহ বুখারী পাঠ (কিতাবুল ঈমান)", "মুফতি আব্দুর রশীদ", "দাওরায়ে হাদীস", "১০:০০ AM - ১১:৩০ AM", "Zoom", "https://zoom.us/j/123456789", true),
        LiveClassSession("LC2", "হিফজ রিভিশন ও তাজবীদ মাস্ক", "হাফেজ মাওলানা নূরুল ইসলাম", "হিফজ ক্লাস", "০২:৩০ PM - ০৪:০০ PM", "Google Meet", "https://meet.google.com/abc-defg-hij", false)
    )

    fun getSampleResults(): List<ExamResult> = listOf(
        ExamResult("2026001", "মোহাম্মদ আব্দুল্লাহ", "দাওরায়ে হাদীস", "বার্ষিক পরীক্ষা ২০২৬", 895, 1000, "A+ (মুমতাজ)", "QR-DM-2026-001"),
        ExamResult("2026002", "আহমেদ মারুফ", "হিফজ বিভাগ", "হিফজ সমাপনী পরীক্ষা", 98, 100, "A+ (মুমতাজ)", "QR-DM-2026-002"),
        ExamResult("2026003", "আরিফুল ইসলাম", "নূরাণী ৩য় শ্রেণি", "সাময়িক পরীক্ষা ২০২৬", 450, 500, "A+ (জায়্যিদ জিদ্দান)", "QR-DM-2026-003")
    )

    suspend fun seedInitialDataIfEmpty() {
        // Seed prayer times
        db.prayerTimeDao().insertPrayerTimes(
            PrayerTimeEntity(
                id = 1,
                dateHijri = "১৫ সফর, ১৪৪৮ হিজরী",
                dateGregorian = "২৭ জুলাই, ২০২৬",
                fajr = "০৪:১২ AM",
                sunrise = "০৫:৩২ AM",
                dhuhr = "১২:১৪ PM",
                asr = "০৪:৩৬ PM",
                maghrib = "০৬:৫৫ PM",
                isha = "০৮:১৫ PM"
            )
        )

        // Seed initial sample notices
        db.noticeDao().insertNotice(
            NoticeEntity(
                title = "২০২৬-২০২৭ শিক্ষাবর্ষে অনলাইনে ভর্তি কার্যক্রম শুরু",
                content = "দারুল মদিনা ইসলামিয়া মাদ্রাসায় প্লে থেকে দাওরায়ে হাদীস পর্যন্ত সর্বস্তরে অনলাইন ভর্তি চলছে। অভিভাবকগণ সরাসরি ওয়েবসাইটের মাধ্যমে ফরম পূরণ করতে পারবেন।",
                category = "Admission",
                date = "২৭ জুলাই, ২০২৬",
                isImportant = true
            )
        )
        db.noticeDao().insertNotice(
            NoticeEntity(
                title = "হিফজ বিভাগের অর্ধবার্ষিক পরীক্ষা ও হিফজ প্রতিযোগিতা",
                content = "আগামী ১০ আগস্ট ২০২৬ খ্রিঃ হিফজ বিভাগের বিশেষ তাজবীদ ও হিফজ প্রতিযোগিতা অনুষ্ঠিত হবে। বিজয়ীদের মাঝে সম্মাননা ও পুরষ্কার বিতরণ করা হবে।",
                category = "Exam",
                date = "২৫ জুলাই, ২০২৬",
                isImportant = false
            )
        )

        // Seed initial sample students
        db.studentDao().insertStudent(
            StudentEntity(
                studentId = "2026001",
                nameBn = "মোহাম্মদ আব্দুল্লাহ",
                nameEn = "Md. Abdullah",
                className = "দাওরায়ে হাদীস",
                department = "কিতাব বিভাগ",
                rollNo = "০১",
                fatherName = "মাওলানা তৌহিদুল ইসলাম",
                phone = "01711000001",
                status = "Active",
                feeDue = 0.0,
                qrCodeId = "QR-DM-2026-001"
            )
        )
        db.studentDao().insertStudent(
            StudentEntity(
                studentId = "2026002",
                nameBn = "আহমেদ মারুফ",
                nameEn = "Ahmed Maruf",
                className = "হিফজ বিভাগ",
                department = "হিফজ বিভাগ",
                rollNo = "০৫",
                fatherName = "মোহাম্মদ রফিকুল ইসলাম",
                phone = "01811000002",
                status = "Active",
                feeDue = 1500.0,
                qrCodeId = "QR-DM-2026-002"
            )
        )

        // Seed initial donations
        db.donationDao().insertDonation(
            DonationEntity(
                donorName = "আলহাজ্ব শফিকুর রহমান",
                amount = 25000.0,
                category = "Zakat",
                paymentMethod = "bKash",
                transactionId = "BKASH-9988231",
                date = "২৬ জুলাই, ২০২৬",
                receiptNo = "REC-2026-101"
            )
        )
        db.donationDao().insertDonation(
            DonationEntity(
                donorName = "আয়েশা খাতুন",
                amount = 10000.0,
                category = "Orphan Sponsorship",
                paymentMethod = "Nagad",
                transactionId = "NGD-8811223",
                date = "২৭ জুলাই, ২০২৬",
                receiptNo = "REC-2026-102"
            )
        )
    }
}
