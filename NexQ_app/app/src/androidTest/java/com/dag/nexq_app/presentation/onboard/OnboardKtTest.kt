package com.dag.nexq_app.presentation.onboard

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dag.nexq_app.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
class OnboardKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var onboardVM: OnboardVM
    private val testContext: Context = ApplicationProvider.getApplicationContext()


    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun onboard() {
        composeTestRule.setContent {
            Onboard(onboardVM)
        }

        composeTestRule.onNodeWithText(testContext.getString(R.string.onboard_next))
            .performClick()

        composeTestRule.onNodeWithText(testContext.getString(R.string.onboard_next))
            .performClick()

        composeTestRule.onNodeWithText(testContext.getString(R.string.onboard_start))
            .assertIsDisplayed()
    }
}