package com.unit.testing.ui


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.unit.testing.R
import com.unit.testing.data.local.entity.ShoppingItem
import com.unit.testing.getOrAwaitValue
import com.unit.testing.launchFragmentInHiltContainer
import com.unit.testing.presentation.ui.fragment.AddShoppingItemFragment
import com.unit.testing.presentation.ui.fragment.ShoppingFragmentFactory
import com.unit.testing.presentation.viewmodel.ShoppingViewModel
import com.unit.testing.repo.FakeShoppingRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun clickInsertIntDB_shoppingItemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            viewModel = testViewModel
        }
        val name = "Shopping Item"
        val amount = 5
        val price = 5.5f

        onView(withId(R.id.etShoppingItemName)).perform(replaceText(name))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText(amount.toString()))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText(price.toString()))
        onView(withId(R.id.btnAddShoppingItem)).perform()

        assertThat(testViewModel.shoppingItems.getOrAwaitValue().contains(ShoppingItem(name, amount, price, ""))).isTrue()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }
}