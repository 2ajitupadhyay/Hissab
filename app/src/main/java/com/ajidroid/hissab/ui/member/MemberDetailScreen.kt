//@file:OptIn(ExperimentalMaterial3Api::class)

@file:OptIn(ExperimentalMaterial3Api::class)

package com.ajidroid.hissab.ui.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ajidroid.hissab.R
import com.ajidroid.hissab.data.Transactions
import com.ajidroid.hissab.ui.HissabTopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Do not call all the Transaction item of an user at once only 20 at first; if the users scrolls more above then call the next 20
// Done <-- Maybe change the repository and DAO methods to Flow based instead of making them suspended; find the best approach for this.

@Composable
fun MemberDetailScreen(
    memberId: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MemberDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(memberId) {
        viewModel.loadMember(memberId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HissabTopAppBar(
                title = uiState.memberName.ifEmpty { "Member" },
                showNavigationIcon = true,
                onNavigateUp = onNavigateUp,
                showActionIcon = true,
                onActionClick = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        MemberDetailContent(
            uiState = uiState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}

@Composable
private fun MemberDetailContent(
    uiState: MemberDetailUiState,
    modifier: Modifier = Modifier
) {
    when {
        uiState.isLoading -> {
            LoadingState(modifier)
        }

        uiState.error != null -> {
            ErrorState(
                message = uiState.error,
                modifier = modifier
            )
        }

        uiState.isEmpty -> {
            EmptyState(
                modifier = modifier,
                message = stringResource(R.string.no_transactions_yet)
            )
        }

        else -> {
            TransactionList(
                transactions = uiState.transactions,
                modifier = modifier
            )
        }
    }
}

@Composable
fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ErrorState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun TransactionList(
    transactions: List<Transactions>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true,
        contentPadding = PaddingValues(
            vertical = 12.dp,
            horizontal = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = transactions,
            key = { it.transactionId }
        ) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: Transactions,
    modifier: Modifier = Modifier
) {
    val isGive = transaction.toGive

    val amountText = if (isGive) {
        "-₹${transaction.amount}"
    } else {
        "+₹${transaction.amount}"
    }

    val amountColor = if (isGive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary // Color(0xFF2E7D32) pleasant green, not neon
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                if (!transaction.description.isNullOrBlank()) {
                    Text(
                        text = transaction.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = formatTime(transaction.time),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = amountText,
                style = MaterialTheme.typography.titleMedium,
                color = amountColor
            )
        }
    }
}

private fun formatTime(timeMillis: Long): String {
    val formatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}

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