
package com.ajidroid.hissab.ui.member

//import com.ajidroid.hissab.ui.AppViewModelProvider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajidroid.hissab.data.member1
import com.ajidroid.hissab.ui.HissabTopAppBar
import com.ajidroid.hissab.ui.viewModelProvider
import kotlinx.coroutines.launch

//@Composable
//fun AddMemberScreen(
//    navigateBack: () -> Unit,
//    onNavigateUp: () -> Unit,
//    canNavigateBack: Boolean = true,
//    viewModel: MemberEntryViewModel = viewModel(factory = viewModelProvider.Factory)
//){
//    val coroutineScope = rememberCoroutineScope()
//    Scaffold (
//        topBar = {
//            HissabTopAppBar(
//                title = "Member Entry",
//                canNavigateBack = canNavigateBack,
//                navigateUp = onNavigateUp
//            )
//        }
//    ){ innerPadding->
//        MemberEntryBody(
//            memberUiState = viewModel.memberUiState,
//            onMemberValueChange = viewModel::updateUiState,
//            onSaveClick = {
//                coroutineScope.launch {
//                    viewModel.saveMember()
//                    navigateBack
//                }
//            },
//            modifier = Modifier
//                .padding(
//                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
//                    top = innerPadding.calculateTopPadding()
//                )
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//        )
//    }
//}
//
//
//@Composable
//private fun MemberEntryBody(
//    memberUiState: MemberUiState,
//    onMemberValueChange: (MemberDetails) -> Unit,
//    onSaveClick: () -> Unit,
//    modifier: Modifier = Modifier
//){
//    Column(
//        modifier = modifier.padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(20.dp)
//    ){
//        MemberInputForm(
//            memberDetails = memberUiState.memberDetails,
//            onValueChange = onMemberValueChange,
//            modifier = Modifier.fillMaxWidth()
//        )
//        Button(
//            onClick = onSaveClick,
//            enabled = memberUiState.isEntryValid,
//            shape = MaterialTheme.shapes.small,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "Save")
//        }
//    }
//
//}
//
//@Composable
//private fun MemberInputForm(
//    memberDetails: MemberDetails,
//    onValueChange : (MemberDetails) -> Unit = {},
//    enabled: Boolean = true,
//    modifier: Modifier = Modifier,
//    ){
//    Column(modifier.padding(horizontal = 16.dp)){
//        Text(text = "Please enter the new member name")
//        OutlinedTextField(
//            value = memberDetails.memberName,
//            onValueChange = {onValueChange(memberDetails.copy(memberName = it))},
//            label = { Text("Enter Member Name") },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//            ),
//            modifier = Modifier.fillMaxWidth(),
//            enabled = enabled,
//            singleLine = true
//        )
//    }
//}
//
//@Composable
//@Preview
//fun PreviewChecker(){
//    MemberInputForm(member1.toMemberDetails())
//}