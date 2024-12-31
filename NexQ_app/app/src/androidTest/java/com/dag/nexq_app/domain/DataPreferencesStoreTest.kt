package com.dag.nexq_app.domain

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dag.nexq_app.domain.DataPreferencesStore
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DataPreferencesStoreTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var dataPreferencesStore: DataPreferencesStore
    private val testContext: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun init() {
        hiltRule.inject()
        dataPreferencesStore = DataPreferencesStore(testContext)
    }

    @Test
    fun value_can_be_added_then_data_can_be_read() = runTest {
        val key = booleanPreferencesKey("test")

        // Write value
        dataPreferencesStore.write(key, true)

        // Read and assert
        val value = dataPreferencesStore.read(key).first()
        assert(value == true) { "Value must be true" }
    }

}