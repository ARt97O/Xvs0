package com.example.xvso

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.res.Configuration
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.xvso.databinding.ActivitySettingsBinding
import com.squareup.picasso.BuildConfig
import java.io.File


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private var currentLocale: Locale? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadLocale() // Завантаження встановленої локалі перед ініціалізацією UI

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnVisitWebsite.setOnClickListener{
            visitWebsite()
        }

        initLanguageSpinner() // Ініціалізація spinner з врахуванням збереженої локалі

    }

    private fun initLanguageSpinner() {
        // Отримання збереженої локалі з SharedPreferences
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPref.getString("Language", "uk") ?: "uk"
        val country = sharedPref.getString("Country", "") ?: ""

        // Встановлення вибраної позиції в spinner
        val selectedPosition = when {
            language == "uk" && country.isEmpty() -> 0
            language == "en" && country == "GB" -> 1
            language == "pl" && country.isEmpty() -> 2 // Додано польську мову
            else -> 0 // Значення за замовчуванням
        }
        binding.languageSpinner.setSelection(selectedPosition)

        // Встановлення слухача для зміни мови
        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> updateLocale("uk") // Позиція 0 - українська мова
                    1 -> updateLocale("en", "GB") // Позиція 1 - англійська мова
                    2 -> updateLocale("pl") // Позиція 2 - польська мова
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }
    private fun updateLocale(language: String, country: String = "") {
        val newLocale = if (country.isNotEmpty()) Locale(language, country) else Locale(language)
        if (newLocale != currentLocale) {
            // Показуємо діалогове вікно перед збереженням налаштувань і перезавантаженням
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.change_language))
                setMessage(getString(R.string.language_change_restart))
                setPositiveButton(getString(R.string.yes)) { dialog, which ->
                    proceedWithLocaleChange(language, country, newLocale)
                    finishAffinity()
                }
                setNegativeButton(getString(R.string.no), null)
                show()
            }
        }
    }

    private fun proceedWithLocaleChange(language: String, country: String, newLocale: Locale) {
        currentLocale = newLocale

        // Збереження локалі в SharedPreferences
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("Language", language)
        sharedPref.putString("Country", country)
        sharedPref.apply()

        // Оновлення конфігурації
        val config = Configuration()
        config.setLocale(newLocale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Перезавантаження активності
        if (!isChangingConfigurations) {
            recreate()
        }
    }


    private fun loadLocale() {
        val sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPref.getString("Language", "uk") ?: "uk"
        val country = sharedPref.getString("Country", "") ?: ""
        currentLocale = if (country.isNotEmpty()) Locale(language, country) else Locale(language)
        Locale.setDefault(currentLocale)

        val config = Configuration(resources.configuration)
        config.setLocale(currentLocale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    //Оновлення
    private fun checkForUpdate() {
        // Використання Coroutine для асинхронного запиту
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val latestReleaseInfo = withContext(Dispatchers.IO) {
                    getLatestReleaseInfo()
                }
                // Порівняння версій
                if (isNewVersionAvailable(BuildConfig.VERSION_NAME, latestReleaseInfo.version)) {
                    Toast.makeText(this@SettingsActivity, "Нова версія доступна: ${latestReleaseInfo.version}", Toast.LENGTH_SHORT).show()
                    // Запускаємо процес завантаження та оновлення
                    // Наприклад, downloadAndInstallApk(latestReleaseInfo.apkUrl)
                } else {
                    Toast.makeText(this@SettingsActivity, "Ви використовуєте останню версію", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@SettingsActivity, "Помилка під час перевірки оновлень", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Функція для виконання мережевого запиту (приклад)
    private suspend fun getLatestReleaseInfo(): ReleaseInfo {
        // Тут ви здійснюєте HTTP запит до GitHub API або до вашого серверу, де розміщено інформацію про останній реліз
        // Цей код лише ілюстрація, вам потрібно буде імплементувати реальний запит, використовуючи наприклад OkHttp або інший HTTP клієнт
        // Припустимо, що ми отримали JSON відповідь з інформацією про останній реліз і парсимо його
        return ReleaseInfo(version = "1.0.1", apkUrl = "https://example.com/app-release.apk")
    }

    private fun visitWebsite() {
        val url = "https://art97o.github.io/BeatSeekerSite/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
// Порівняння поточної версії з версією останнього релізу
private fun isNewVersionAvailable(currentVersion: String, latestVersion: String): Boolean {
    return currentVersion != latestVersion // Просте порівняння, можливо потрібно більш складне порівняння версій
}

//private fun downloadAndInstallApk(apkUrl: String) {
//    val client = OkHttpClient()
//    val request = Request.Builder().url(apkUrl).build()
//
//    GlobalScope.launch(Dispatchers.IO) {
//        client.newCall(request).execute().use { response ->
//            val apkFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "app-release.apk")
//            FileOutputStream(apkFile).use { fos ->
//                response.body?.byteStream()?.copyTo(fos)
//            }
//
//            withContext(Dispatchers.Main) {
//                installApk(apkFile, context = this@SettingsActivity)
//            }
//        }
//    }
//}



private fun installApk(apkFile: File, context: Context) {
    val apkUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", apkFile)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(apkUri, "application/vnd.android.package-archive")
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent) // Start activity with context
}

private suspend fun getLatestReleaseInfo(): ReleaseInfo {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://example.com/api/latestRelease") // URL вашого API для отримання останнього релізу
        .build()

    return withContext(Dispatchers.IO) {
        client.newCall(request).execute().use { response ->
            val jsonResponse = response.body?.string() ?: throw Exception("Response body is null")
            val jsonObject = JSONObject(jsonResponse)
            ReleaseInfo(
                version = jsonObject.getString("version"),
                apkUrl = jsonObject.getString("apkUrl")
            )
        }
    }
}
// Допоміжний клас для зберігання інформації про реліз
data class ReleaseInfo(val version: String, val apkUrl: String)

