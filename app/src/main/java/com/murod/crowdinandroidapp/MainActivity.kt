package com.murod.crowdinandroidapp

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.BaseContextWrappingDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crowdin.platform.Crowdin
import com.crowdin.platform.CrowdinConfig
import com.murod.crowdinandroidapp.ui.theme.MyApplicationTheme
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Crowdin.init(
            applicationContext,
            CrowdinConfig.Builder()
                .withDistributionHash("04273d65834118ac24ad77a1pl3")
                .build()
        )

        setContent {
            MyApplicationTheme {
                SimpleScreen()
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Crowdin.wrapContext(newBase))
    }
}

@Preview
@Composable
fun SimpleScreen() {
    var currentLocale by remember { mutableStateOf(Locale.getDefault()) }
    val activity = LocalActivity.current

//    LaunchedEffect(currentLocale) {
//        if (activity != null) {
//            changeLocale(activity, currentLocale.language)
//        }
//    }

    CompositionLocalProvider(LocalConfiguration provides Configuration().apply {
        setLocale(currentLocale)
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var isKgSelected by remember { mutableStateOf(currentLocale.language == "kg") }
            var isRuSelected by remember { mutableStateOf(currentLocale.language == "ru") }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "KG")
                Switch(
                    checked = isKgSelected,
                    onCheckedChange = { isChecked ->
                        isKgSelected = isChecked
                        isRuSelected = !isChecked
                        currentLocale = if (isChecked) Locale("ky", "KG") else Locale("ru", "RU")
                    }
                )

                Text(text = "RU")
                Switch(
                    checked = isRuSelected,
                    onCheckedChange = { isChecked ->
                        isRuSelected = isChecked
                        isKgSelected = !isChecked
                        currentLocale = if (isChecked) Locale("ru", "RU") else Locale("kg", "KG")
                    }
                )
            }

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Примеры текстов", fontSize = 24.sp
            )

            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.Start),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Пример обычного текста", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.text1), fontSize = 16.sp)

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Пример текста cо знаками препинания", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.text2), fontSize = 16.sp)

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Пример текста с форматированием", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.text3, "Downloads"), fontSize = 16.sp)

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Пример длинного текста", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.text4), fontSize = 16.sp)

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Пример текста с переносом строк", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.text5), fontSize = 16.sp)
            }
        }
    }
}

fun changeLocale(activity: Activity, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration(activity.resources.configuration)
    config.setLocale(locale)

    activity.resources.updateConfiguration(config, activity.resources.displayMetrics)
}