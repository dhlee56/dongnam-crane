/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.samples.crane.base.CraneDrawer
import androidx.compose.samples.crane.base.CraneTabBar
import androidx.compose.samples.crane.base.CraneTabs
import androidx.compose.samples.crane.base.ExploreSection
import androidx.compose.samples.crane.data.ExploreModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

typealias OnExploreItemClicked = (ExploreModel) -> Unit

enum class CraneScreen {
    Fly, Sleep, Eat
}

@Composable
fun CraneHome(
    onExploreItemClicked: OnExploreItemClicked,
    modifier: Modifier = Modifier,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var drawerFlag by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { padding ->
        if(drawerFlag) DialogWithImage(
            onDismissRequest = { drawerFlag = false },
            onConfirmation = { drawerFlag = false },
            imageDescription = ""
        )
        else
        CraneHomeContent(
            modifier = modifier.padding(padding),
            onExploreItemClicked = onExploreItemClicked,
            openDrawer = {
                // TODO Codelab: rememberCoroutineScope step - open the navigation drawer
                // scaffoldState.drawerState.open()
                Log.d("KOTLINCLASS", "openDrawer: ")
                drawerFlag = true
//                coroutineScope.launch {
//                    snackBarHostState.showSnackbar(
//                        message = "Hello SnackBar",
//                        duration = SnackbarDuration.Indefinite,
//                        actionLabel = "label",
//                        withDismissAction = true,
//                    )
//                }
            }
        )
    }
}
@Composable
fun CraneHomeContent(
    onExploreItemClicked: OnExploreItemClicked,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {
    // TODO Codelab: collectAsStateWithLifecycle step - consume stream of data from the ViewModel
    val suggestedDestinations: List<ExploreModel> = remember { emptyList() }

    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
    var tabSelected by remember { mutableStateOf(CraneScreen.Fly) }

    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTabBar(openDrawer, tabSelected, onTabSelected = { tabSelected = it })
        }) { paddingValues ->
            Column(Modifier.padding(paddingValues)) {
                SearchContent(
                    tabSelected,
                    viewModel,
                    onPeopleChanged
                )
                when (tabSelected) {
                    CraneScreen.Fly -> {
                        ExploreSection(
                            title = "Explore Flights by Destination",
                            exploreList = suggestedDestinations,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Sleep -> {
                        ExploreSection(
                            title = "Explore Properties by Destination",
                            exploreList = viewModel.hotels,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                    CraneScreen.Eat -> {
                        ExploreSection(
                            title = "Explore Restaurants by Destination",
                            exploreList = viewModel.restaurants,
                            onItemClicked = onExploreItemClicked
                        )
                    }
                }
            }

        }
}

@Composable
private fun HomeTabBar(
    openDrawer: () -> Unit,
    tabSelected: CraneScreen,
    onTabSelected: (CraneScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    CraneTabBar(
        modifier = modifier,
        onMenuClicked = openDrawer
    ) { tabBarModifier ->
        CraneTabs(
            modifier = tabBarModifier,
            titles = CraneScreen.values().map { it.name },
            tabSelected = tabSelected,
            onTabSelected = { newTab -> onTabSelected(CraneScreen.values()[newTab.ordinal]) }
        )
    }
}

@Composable
private fun SearchContent(
    tabSelected: CraneScreen,
    viewModel: MainViewModel,
    onPeopleChanged: (Int) -> Unit
) {
    when (tabSelected) {
        CraneScreen.Fly -> FlySearchContent(
            onPeopleChanged = onPeopleChanged,
            onToDestinationChanged = { viewModel.toDestinationChanged(it) }
        )
        CraneScreen.Sleep -> SleepSearchContent(
            onPeopleChanged = onPeopleChanged
        )
        CraneScreen.Eat -> EatSearchContent(
            onPeopleChanged = onPeopleChanged
        )
    }
}
