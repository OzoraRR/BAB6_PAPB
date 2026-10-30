package com.example.navigationlab

import android.os.ParcelFileDescriptor
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.navigationlab.navigation.NavigationLabApp
import com.example.navigationlab.navigation.Routes
import com.example.navigationlab.ui.theme.NavigationLabTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationLabTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test fun homeIsStartDestinationAndShowsAllFiveStudents() {
        rule.onNodeWithText("Home").assertIsDisplayed()
        listOf("Alya Putri", "Bima Pratama", "Citra Lestari", "Dimas Saputra", "Eka Maharani")
            .forEach { name ->
                rule.onNodeWithTag("home_screen").performScrollToNode(hasText(name))
                rule.onNodeWithText(name).assertIsDisplayed()
            }
        rule.onNodeWithTag("home_screen").performScrollToIndex(0)
        screenshot("06_home_daftar_mahasiswa")
    }

    @Test fun detail2026ShowsAllFieldsAndButtonReturnsHome() {
        rule.onNodeWithTag("open_detail_2026").performClick()
        rule.onNodeWithText("ID: 2026").assertIsDisplayed()
        rule.onNodeWithText("Nama: Alya Putri").assertIsDisplayed()
        rule.onNodeWithText("Program: Teknik Informatika").assertIsDisplayed()
        rule.onNodeWithText("Angkatan: 2026").assertIsDisplayed()
        screenshot("02_detail_2026")
        rule.onNodeWithTag("back_button").performClick()
        rule.onNodeWithText("Home").assertIsDisplayed()
        screenshot("05_home_setelah_back")
    }

    @Test fun everyListItemNavigatesUsingItsOwnId() {
        val students = listOf(2026 to "Alya Putri", 2027 to "Bima Pratama",
            2028 to "Citra Lestari", 2029 to "Dimas Saputra", 2030 to "Eka Maharani")
        students.forEach { (id, name) ->
            rule.onNodeWithTag("home_screen").performScrollToNode(hasTestTag("student_$id"))
            rule.onNodeWithTag("student_$id").performClick()
            rule.onNodeWithText("ID: $id").assertIsDisplayed()
            rule.onNodeWithText("Nama: $name").assertIsDisplayed()
            if (id == 2030) screenshot("07_detail_mahasiswa_baru")
            rule.onNodeWithTag("back_button").performClick()
            rule.onNodeWithText("Home").assertIsDisplayed()
        }
    }

    @Test fun profileAndAboutCanOpenAndReturnHome() {
        rule.onNodeWithText("Profile").performClick()
        rule.onNodeWithText("NIM: 245150207111102").assertIsDisplayed()
        screenshot("08_profile")
        rule.onNodeWithText("Kembali").performClick()
        rule.onNodeWithText("Home").assertIsDisplayed()
        rule.onNodeWithText("About").performClick()
        rule.onNodeWithText("NavigationLab").assertIsDisplayed()
        screenshot("09_about")
        rule.onNodeWithText("Kembali").performClick()
        rule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test fun systemBackReturnsFromDetailToHome() {
        rule.onNodeWithTag("open_detail_2026").performClick()
        rule.onNodeWithText("ID: 2026").assertIsDisplayed()
        rule.runOnUiThread { rule.activity.onBackPressedDispatcher.onBackPressed() }
        rule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test fun detailSurvivesActivityRecreation() {
        rule.onNodeWithTag("open_detail_2026").performClick()
        rule.onNodeWithText("Nama: Alya Putri").assertIsDisplayed()
        rule.activityRule.scenario.recreate()
        rule.onNodeWithText("ID: 2026").assertIsDisplayed()
        rule.onNodeWithText("Nama: Alya Putri").assertIsDisplayed()
        rule.onNodeWithTag("back_button").performClick()
        rule.onNodeWithText("Home").assertIsDisplayed()
    }

    @Test fun unknownIdShowsErrorAndReturnsHome() {
        lateinit var controller: NavHostController
        rule.runOnUiThread {
            rule.activity.setContent {
                NavigationLabTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()
                        SideEffect { controller = navController }
                        NavigationLabApp(navController)
                    }
                }
            }
        }
        rule.waitForIdle()
        rule.runOnIdle { controller.navigate(Routes.detail(9999)) }
        rule.onNodeWithText("Student ID 9999 tidak ditemukan").assertIsDisplayed()
        screenshot("10_student_tidak_ditemukan")
        rule.onNodeWithTag("back_button").performClick()
        rule.onNodeWithText("Home").assertIsDisplayed()
    }

    private fun screenshot(name: String) {
        rule.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        // Folder publik tetap tersedia setelah Gradle menghapus APK pengujian.
        fun shell(command: String) {
            ParcelFileDescriptor.AutoCloseInputStream(
                instrumentation.uiAutomation.executeShellCommand(command)
            ).use { it.readBytes() }
        }
        shell("mkdir -p /sdcard/Pictures/NavLabPraktikum06")
        shell("screencap -p /sdcard/Pictures/NavLabPraktikum06/$name.png")
    }
}
