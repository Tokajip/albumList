package com.ptokaji.albumlist

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ptokaji.albumlist.ui.main.MainActivity
import com.ptokaji.albumlist.ui.main.MainViewScreen
import com.ptokaji.albumlist.ui.main.model.AlbumUiItem
import com.ptokaji.albumlist.ui.main.model.MainUiState
import com.ptokaji.albumlist.ui.theme.AlbumListTheme
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testContentState() {
        val mockAlbumList: MutableLiveData<MainUiState> = MutableLiveData(
            MainUiState.Content(
                listOf(
                    AlbumUiItem("username", "thumbnail", "albumTitle", "photoTitle"),
                    AlbumUiItem("username2", "thumbnail2", "albumTitle2", "photoTitle2"),
                )
            )
        )

        composeTestRule.activity.setContent {
            AlbumListTheme {
                MainViewScreen(uiState = mockAlbumList)
            }
        }

        composeTestRule.onNodeWithText("albumTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("albumTitle2").assertIsDisplayed()
        composeTestRule.onNodeWithText("photoTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText("photoTitle2").assertIsDisplayed()
        composeTestRule.onNodeWithText("username").assertIsDisplayed()
        composeTestRule.onNodeWithText("username2").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        val mockAlbumList: MutableLiveData<MainUiState> = MutableLiveData(
            MainUiState.Error("mockError")
        )

        composeTestRule.activity.setContent {
            AlbumListTheme {
                MainViewScreen(uiState = mockAlbumList)
            }
        }

        composeTestRule.onNodeWithText("mockError").assertIsDisplayed()
        composeTestRule.onNodeWithText("Something went wrong!").assertIsDisplayed()
        composeTestRule.onNodeWithText("More details:").assertIsDisplayed()
    }

    @Test
    fun testLoadingState() {
        val mockAlbumList: MutableLiveData<MainUiState> = MutableLiveData(
            MainUiState.Loading
        )

        composeTestRule.activity.setContent {
            AlbumListTheme {
                MainViewScreen(uiState = mockAlbumList)
            }
        }
        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }
}