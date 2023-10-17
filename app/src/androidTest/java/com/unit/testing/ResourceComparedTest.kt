package com.unit.testing


import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.unit.testing.utils.ResourceCompared
import org.junit.Before
import org.junit.Test

class ResourceComparedTest {

    private lateinit var resourceCompared: ResourceCompared

    @Before
    fun setup() {
        resourceCompared = ResourceCompared()
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompared.isEqual(context, R.string.app_name, "UnitTesting")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceCompared.isEqual(context, R.string.app_name, "ABC")
        assertThat(result).isFalse()
    }
}