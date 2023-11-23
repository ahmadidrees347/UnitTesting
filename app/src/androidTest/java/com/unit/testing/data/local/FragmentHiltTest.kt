package com.unit.testing.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.test.filters.SmallTest
import com.unit.testing.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FragmentHiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<Fragment> {

        }
    }
}