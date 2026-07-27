package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val studentId: String,
    val nameBn: String,
    val nameEn: String,
    val className: String,
    val department: String,
    val rollNo: String,
    val fatherName: String,
    val phone: String,
    val status: String = "Active", // Active, Graduated, Suspended
    val feeDue: Double = 0.0,
    val qrCodeId: String
)

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val category: String, // General, Exam, Holiday, Admission, Emergency
    val date: String,
    val isImportant: Boolean = false,
    val publishedBy: String = "Madrasa Authority"
)

@Entity(tableName = "donations")
data class DonationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val donorName: String,
    val amount: Double,
    val category: String, // Zakat, Sadaqah, Lillah, Orphan Sponsorship, General Fund
    val paymentMethod: String, // bKash, Nagad, Rocket, Bank, SSLCommerz
    val transactionId: String,
    val date: String,
    val receiptNo: String
)

@Entity(tableName = "prayer_times")
data class PrayerTimeEntity(
    @PrimaryKey val id: Int = 1,
    val dateHijri: String,
    val dateGregorian: String,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)
