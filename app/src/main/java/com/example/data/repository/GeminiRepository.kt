package com.example.data.repository

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun askIslamicAssistant(userQuery: String, languageCode: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getOfflineAssistantResponse(userQuery, languageCode)
        }

        try {
            val systemPrompt = when (languageCode) {
                "bn" -> "আপনি দারুল মদিনা ইসলামিয়া মাদ্রাসার এআই সহায়তা সহকারী। শিক্ষার্থীদের ভর্তি, শিক্ষা নিয়ম, ইসলামিক বিধান ও মাদ্রাসা সংক্রান্ত যেকোনো প্রশ্নের উত্তর বিনম্র ও ইসলামিক শিষ্টাচার বজায় রেখে সহজ ভাষায় দিন।"
                "ar" -> "أنت المساعد الذكي لمدرسة دار المدينة الإسلامية. أجب عن أسئلة الطلاب وأولياء الأمور بأدب واحترام وبلغة عربية فصحى بسيطة."
                else -> "You are the AI Assistant for Darul Madina Islamia Madrasha. Provide polite, clear, and informative answers regarding admissions, Islamic guidance, and madrasa management."
            }

            val requestJson = JSONObject().apply {
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().put(JSONObject().put("text", systemPrompt)))
                })
                put("contents", JSONArray().put(
                    JSONObject().apply {
                        put("parts", JSONArray().put(JSONObject().put("text", userQuery)))
                    }
                ))
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = requestJson.toString().toRequestBody(mediaType)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string() ?: ""

            if (response.isSuccessful && responseString.isNotBlank()) {
                val jsonResponse = JSONObject(responseString)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).optString("text", "No response text.")
                    }
                }
            }
            return@withContext getOfflineAssistantResponse(userQuery, languageCode)
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext getOfflineAssistantResponse(userQuery, languageCode)
        }
    }

    suspend fun generateNoticeDraft(topic: String, category: String, languageCode: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext getOfflineNoticeDraft(topic, category, languageCode)
        }

        try {
            val prompt = "Generate an official, professional Islamic Madrasa notice draft for Darul Madina Islamia Madrasha regarding '$topic' in category '$category'. Format with title, date placeholder, body with polite Islamic greetings (Assalamu Alaikum), instructions, and signoff (Madrasa Authority)."

            val requestJson = JSONObject().apply {
                put("contents", JSONArray().put(
                    JSONObject().apply {
                        put("parts", JSONArray().put(JSONObject().put("text", prompt)))
                    }
                ))
            }

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = requestJson.toString().toRequestBody(mediaType)

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string() ?: ""

            if (response.isSuccessful && responseString.isNotBlank()) {
                val jsonResponse = JSONObject(responseString)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).optString("text", "No response text.")
                    }
                }
            }
            return@withContext getOfflineNoticeDraft(topic, category, languageCode)
        } catch (e: Exception) {
            return@withContext getOfflineNoticeDraft(topic, category, languageCode)
        }
    }

    private fun getOfflineAssistantResponse(query: String, lang: String): String {
        val q = query.lowercase()
        return when {
            q.contains("ভর্তি") || q.contains("admission") -> {
                if (lang == "bn") "দারুল মদিনা ইসলামিয়া মাদ্রাসায় প্লে থেকে দাওরায়ে হাদীস (টাইটেল) পর্যন্ত অনলাইনে ভর্তি নেওয়া হচ্ছে। ভর্তির আবেদনের জন্য 'অনলাইন ভর্তি' বাটনে ক্লিক করুন অথবা মাদ্রাসার অফিসে সরাসরি যোগাযোগ করুন। ফোন: 01700-000000।"
                else "Admissions are open at Darul Madina Islamia Madrasha from Play to Dawra-e-Hadith. Click on 'Online Admission' to fill the application form."
            }
            q.contains("ফি") || q.contains("fee") || q.contains("দান") || q.contains("zakat") || q.contains("সদকা") -> {
                if (lang == "bn") "আপনি বিকাশ, নগদ, রকেট এবং কার্ডের মাধ্যমে সহজেই শিক্ষার্থীদের মাসিক ফি, এতিম তহবিল, জাকাত ও সদকা প্রদান করতে পারেন। প্রতিটি পেমেন্টের জন্য স্বয়ংক্রিয় ডিজিটাল রসিদ দেওয়া হয়।"
                else "You can securely pay fees, Zakat, Sadaqah, and donations via bKash, Nagad, Rocket, or SSLCommerz. Automatic receipts are generated."
            }
            q.contains("সময়") || q.contains("prayer") || q.contains("নামাজ") -> {
                if (lang == "bn") "আজকের ফজর: ৫:০৫ মি., জোহর: ১২:১৫ মি., আসর: ৪:৩০ মি., মাগরিব: ৬:৪৫ মি., এশা: ৮:০০ মি। দৈনিক নামাজের সময়সূচি হোমপেজে প্রদর্শিত হচ্ছে।"
                else "Today's Prayer Times - Fajr: 5:05 AM, Dhuhr: 12:15 PM, Asr: 4:30 PM, Maghrib: 6:45 PM, Isha: 8:00 PM."
            }
            else -> {
                if (lang == "bn") "আসসালামু আলাইকুম! দারুল মদিনা ইসলামিয়া মাদ্রাসার এআই অ্যাসিস্ট্যান্টে আপনাকে স্বাগতম। ভর্তি, ফলাফল, রুটিন বা দান-অনুদানের বিষয়ে আপনার কোনো প্রশ্ন থাকলে জানান।"
                else "Assalamu Alaikum! Welcome to Darul Madina AI Assistant. How can I help you today with admission, routine, exam results, or donations?"
            }
        }
    }

    private fun getOfflineNoticeDraft(topic: String, category: String, lang: String): String {
        return """
            বিসমিল্লাহির রহমানির রহিম
            
            দারুল মদিনা ইসলামিয়া মাদ্রাসা
            নোটিশ - বিষয়: $topic
            ক্যাটাগরি: $category
            তারিখ: ২৭ জুলাই, ২০২৬
            
            আসসালামু আলাইকুম ওয়া রহমাতুল্লাহ।
            
            দারুল মদিনা ইসলামিয়া মাদ্রাসার সকল ছাত্র, শিক্ষক, অভিভাবক ও শুভানুধ্যায়ীদের অবগতির জন্য জানানো যাচ্ছে যে, $topic সংক্রান্ত কার্যক্রম সম্পর্কে কর্তৃপক্ষ বিশেষ সিদ্ধান্ত গ্রহণ করেছে।
            
            বিস্তারিত তথ্যের জন্য মাদ্রাসা অফিসের নোটিশ বোর্ড বা ওয়েবসাইটে চোখ রাখুন।
            
            ওয়াসসালাম,
            মাদ্রাসা কর্তৃপক্ষ
            দারুল মদিনা ইসলামিয়া মাদ্রাসা
            www.darulmadinaislamiamadrasha.com
        """.trimIndent()
    }
}
