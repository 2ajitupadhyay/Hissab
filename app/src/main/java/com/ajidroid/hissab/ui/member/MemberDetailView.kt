//@file:OptIn(ExperimentalMaterial3Api::class)

package com.ajidroid.hissab.ui.member

//import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
//import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ajidroid.hissab.data.Transaction
import com.ajidroid.hissab.data.transaction1
import com.ajidroid.hissab.data.transaction2
import com.ajidroid.hissab.ui.HissabTopAppBar
import com.ajidroid.hissab.ui.viewModelProvider

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MemberDetailView(
//    modifier: Modifier = Modifier,
//    viewModel: MemberDetailViewModel = viewModel(factory = viewModelProvider.Factory),
//    onAddTransactionClick: () -> Unit
//) {
////    LaunchedEffect(memberId) {
////        viewModel.loadMember(memberId)
////    }
//
//    val uiState =  viewModel.uiState.collectAsState()
////    val netBalance by viewModel.netBalance.collectAsState()
//    val coroutineScope = rememberCoroutineScope()// for deleting the member
//
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//
//    Scaffold(
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            HissabTopAppBar(
//                title = uiState.value.memberDetails.memberName,//"MembersName",
//                canNavigateBack = true,
//                scrollBehavior = scrollBehavior
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//                containerColor = if (uiState.value.memberDetails.toGive) Color.Red else Color.Green,
//                content = {
//                    Text(
//                        text = if (!uiState.value.memberDetails.toGive) "You will receive: ₹${"%.2f".format(uiState.value.memberDetails.totalAmount)}"
//                        else "You owe: ₹${"%.2f".format(uiState.value.memberDetails.totalAmount)}",
////                        color = if (!uiState.value.memberDetails.toGive) Color.GREEN else Color.RED,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(modifier = Modifier.padding(paddingValues)) {
//            if (uiState.value.noTransactions) {
//                Text(
//                    "No transactions yet",
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.padding(16.dp)
//                )
//            } else {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(uiState.value.memberDetails.transactionList) { transaction ->
//                        TransactionItem(transaction)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TransactionItem(transaction: Transaction, modifier: Modifier = Modifier) {
//    val color = if (transaction.toGive) Color.Red else Color.Green
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(color),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                Text(
//                    text = if(transaction.toGive)"To Give ₹${transaction.amount}" else "To Take ₹${transaction.amount}",
////                    color = color,
//                    style = MaterialTheme.typography.displayMedium
//                )
//                if (!transaction.description.isNullOrEmpty()) {
//                    Text(transaction.description, style = MaterialTheme.typography.titleSmall)
//                }
//            }
////            Text(
////                text = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(Date(transaction.timestamp)),
////                style = MaterialTheme.typography.caption
////            )
//        }
//    }
//}
//
//@Composable
//@Preview
//fun TransactionPreview(){
//    TransactionItem(transaction2, modifier = Modifier)
//}
//
////@Composable
////@Preview
////fun TransactionDetailPreview(){
////    TransactionItem(transaction2, modifier = Modifier)
////}