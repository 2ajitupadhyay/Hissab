package com.ajidroid.hissab.ui.home

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajidroid.hissab.R
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.membersList
import com.ajidroid.hissab.ui.HissabTopAppBar
import com.ajidroid.hissab.ui.navigation.NavigationDestination
import com.ajidroid.hissab.ui.viewModelProvider


object HomeDestination : NavigationDestination{
    override val route = "home"
    override val titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToMemberEntry: () -> Unit,
    navigateToMemberUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(factory = viewModelProvider.Factory))
 {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HissabTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},//navigateToMemberEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            memberList = homeUiState.memberList,
            onItemClick = {},//navigateToMemberUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}


@Composable
private fun HomeBody(
    memberList: List<Member>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (memberList.isEmpty()) {
            Text(
                text = "No Data Found /n please add members",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            PersonGrid(
                membersList = memberList,
//                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp) // need to edit later
            )
        }
    }
}

@Composable
private fun PersonGrid(
    membersList : List<Member>,
    contentPadding: PaddingValues,
    modifier: Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        items(membersList){member ->
            Person(member = member, modifier)
        }
    }
}

@Composable
private fun Person(member: Member, modifier: Modifier){
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(text = member.memberName,
                style = MaterialTheme.typography.titleLarge
            )
            Box(
                contentAlignment = Alignment.Center
            ){
                Text(text = "Add Transaction")
            }
            Box(modifier = Modifier.background(color = if(member.toGive) Color.Red else Color.Green)){
                Column (modifier = modifier.padding(16.dp)){
                    Text(text = member.totalAmount.toString())
                    Text(text = if(member.toGive) "To Give" else "To Take")
                }
            }
        }
    }
}

@Composable
@Preview
fun PersonPreview(){
    Person(membersList[0], modifier = Modifier)
}

@Composable
@Preview
fun PersonGridPreview(){
    PersonGrid(membersList, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), modifier = Modifier)
}