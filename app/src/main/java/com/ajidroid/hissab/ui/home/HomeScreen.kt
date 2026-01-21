package com.ajidroid.hissab.ui.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ajidroid.hissab.R
import com.ajidroid.hissab.data.Member
import com.ajidroid.hissab.data.membersList
import com.ajidroid.hissab.ui.HissabTopAppBar
import com.ajidroid.hissab.ui.member.EmptyState
import com.ajidroid.hissab.ui.member.ErrorState
import com.ajidroid.hissab.ui.member.LoadingState
import com.ajidroid.hissab.utils.asCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToMemberEntry: () -> Unit,
    navigateToMemberDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showAddMemberDialog by remember { mutableStateOf(false) }
    var showTransactionDialog by remember { mutableStateOf(false) }
    var showRenameMemberDialog by remember { mutableStateOf(false) }
    var selectedMemberIdAddTransaction by remember { mutableStateOf<Int?>(null) }
    var showActionSheet by remember { mutableStateOf(false) }
    var selectedMemberMoreAction by remember { mutableStateOf<Member?>(null) }


    if (showActionSheet && selectedMemberMoreAction != null) {
        MemberActionSheet(
            memberName = selectedMemberMoreAction!!.memberName,
            onDismiss = { showActionSheet = false }
        ) { action ->
            showActionSheet = false // remove this from here and make it only for the specific feature like delete and clearTab
            when (action) {
                MemberAction.Rename -> showRenameMemberDialog = true
                MemberAction.ClearTab -> {}
                MemberAction.Delete -> viewModel.deleteMember(selectedMemberMoreAction!!)
            }
        }
    }

    if (showRenameMemberDialog && selectedMemberMoreAction != null) {
        AddMemberDialog(
            titleText = stringResource(R.string.rename_member),
            onDismiss = { showRenameMemberDialog = false } ,
            onConfirm = { name ->
                viewModel.renameMemberName(name, selectedMemberMoreAction!!.id)
                showRenameMemberDialog = false
            }
        )
    }


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HissabTopAppBar(
                title = stringResource(R.string.app_name),
                showActionIcon = true,
                onActionClick = {},
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showAddMemberDialog = true},//navigateToMemberEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
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
        }, // Enhance the floating action button, make it inside the container or something
    ) { innerPadding ->
        HomeScreenContent(
            uiState = uiState,
            onCardClick = {
                navigateToMemberDetail(it)
            },
            onAddTransactionClick = { memberId ->
                selectedMemberIdAddTransaction = memberId
                showTransactionDialog = true
            },
            onMoreClick = {
                selectedMemberMoreAction = it
                showActionSheet = true
                          },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )

        if (showAddMemberDialog) {
            AddMemberDialog(
                titleText = stringResource(R.string.add_member),
                onDismiss = { showAddMemberDialog = false },
                onConfirm = { name ->
                    viewModel.addMember(name)
                    showAddMemberDialog = false
                }
            )
        }

        if (showTransactionDialog && selectedMemberIdAddTransaction != null) {
            AddTransactionDialog(
                onDismiss = {
                    showTransactionDialog = false
                    selectedMemberIdAddTransaction = null
                },
                onConfirm = { amount, toGive, description ->
                    viewModel.addTransaction(
                        memberId = selectedMemberIdAddTransaction!!,
                        amount = amount,
                        toGive = toGive,
                        description = description
                    )
                    showTransactionDialog = false
                    selectedMemberIdAddTransaction = null
                }
            )
        }
    }
}
// Done <--Use Dynamic color scheme for android 12 and above.
// The home screen top app bar should have, split wise icon.
// Use Animation APIs for creating animation when navigating between one screen to another; mainly when the card is clicked.
// Use better elevation for the cards
// Create necessary icons with vector: like split wise, maybe add transaction,
// Also provide daily push notification of total to take or to give
// Make it CI-CD integrated through github for updating and removing feature.
// Mark last transaction as invalid but only for the last or for until 1 hour of registered; the transaction will be there with a darker
// background and maybe a line over the whole transaction item, but its value will not be calculated into the result

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onCardClick: (Int) -> Unit,
    onAddTransactionClick: (Int) -> Unit,
    onMoreClick: (Member) -> Unit,
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

        uiState.members.isEmpty() -> {
            EmptyState(
                modifier = modifier,
                message = stringResource(R.string.no_members_found),
            )
        }

        else -> {
            HomeBody(
                memberList = uiState.members,
                onCardClick = onCardClick,
                onAddTransactionClick = onAddTransactionClick,
                onMoreClick = onMoreClick,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun HomeEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_members_found),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HomeLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeBody(
    memberList: List<Member>,
    onCardClick: (Int) -> Unit,
    onAddTransactionClick: (Int) -> Unit,
    onMoreClick: (Member) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    MemberGrid(
        members = memberList,
        onCardClick = onCardClick,
        onAddTransactionClick = onAddTransactionClick,
        onMoreClick = onMoreClick,
        contentPadding = contentPadding,
        modifier = modifier
    )
}

@Composable
private fun MemberGrid(
    members: List<Member>,
    onCardClick: (Int) -> Unit,
    onAddTransactionClick: (Int) -> Unit,
    onMoreClick: (Member) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = contentPadding.calculateTopPadding(),
            bottom = contentPadding.calculateBottomPadding()
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = members,
            key = { it.id } // VERY important for recomposition + scroll stability
        ) { member ->
            MemberGridItem(
                member = member,
                onCardClick = { onCardClick(member.id) },
                onAddTransactionClick = {
                    onAddTransactionClick(member.id)
                },
                onMoreClick = {
                    onMoreClick(member)
                }
            )
        }
    }
}

@Composable
fun MemberGridItem(
    member: Member,
    onCardClick: () -> Unit,
    onAddTransactionClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
//            .height(160.dp)
            .aspectRatio(2 / 3f)
            .clickable(onClick = onCardClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Main content
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = member.memberName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = member.totalAmount.asCurrency(),
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = if (member.totalAmount >= 0) stringResource(R.string.to_take) else stringResource(R.string.to_give),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (member.totalAmount < 0)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .align(Alignment.BottomCenter)
                    .clickable(onClick = onAddTransactionClick)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Transaction",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
                    .clickable(onClick = onMoreClick),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More actions",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
fun AddMemberDialog(
    titleText: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = titleText)
        },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Member name") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name) },
                enabled = name.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onConfirm: (amount: Int, toGive: Boolean, description: String?) -> Unit
) {
    var amountText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var toGive by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.add_transaction),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Amount
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { value ->
                        if (value.all { it.isDigit() }) {
                            amountText = value
                        }
                    },
                    label = { Text("Amount") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Give / Take selector
                GiveTakeToggle(
                    isGive = toGive,
                    onToggle = { toGive = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = amountText.isNotBlank(),
                onClick = {
                    onConfirm(
                        amountText.toInt(),
                        toGive,
                        description.takeIf { it.isNotBlank() }
                    )
                }
            ) {
                Text(
                    text = stringResource(R.string.add),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

@Composable
fun GiveTakeToggle(
    isGive: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val slideFraction by animateFloatAsState(
        targetValue = if (isGive) 0f else 1f,
        label = "toggleSlide"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isGive)
            MaterialTheme.colorScheme.errorContainer
        else
            MaterialTheme.colorScheme.primaryContainer,
        label = "toggleBackground"
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Transaction Type",
            style = MaterialTheme.typography.labelLarge
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(MaterialTheme.shapes.small)
                .background(backgroundColor)
                .clickable { onToggle(!isGive) }
                .padding(4.dp)
        ) {
            BoxWithConstraints {
                val halfWidth = this.maxWidth / 2

                Box(
                    modifier = Modifier
                        .offset(x = halfWidth * slideFraction)
                        .width(halfWidth)
                        .fillMaxHeight()
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isGive) stringResource(R.string.borrow) else stringResource(R.string.lend),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MemberActionSheet(
    memberName: String,
    onDismiss: () -> Unit,
    onActionSelected: (MemberAction) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {

            Text(
                text = memberName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )

            Divider(color = MaterialTheme.colorScheme.outlineVariant)

            ActionRow(
                icon = Icons.Default.Edit,
                text = stringResource(R.string.rename),
                tint = MaterialTheme.colorScheme.onSurface
            ) {
                onActionSelected(MemberAction.Rename)
            }

            ActionRow(
                icon = Icons.Default.Clear,
                text = stringResource(R.string.clear_tab),
                tint = MaterialTheme.colorScheme.primary
            ) {
                onActionSelected(MemberAction.ClearTab)
            }

            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            ActionRow(
                icon = Icons.Default.Delete,
                text = stringResource(R.string.delete),
                tint = MaterialTheme.colorScheme.error
            ) {
                onActionSelected(MemberAction.Delete)
            }
        }
    }
}

@Composable
private fun ActionRow(
    icon: ImageVector,
    text: String,
    tint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = tint
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = tint
        )
    }
}

@Composable
@Preview
fun PersonPreview(){
    MemberGridItem(membersList[4], onCardClick = {}, onAddTransactionClick = {}, modifier = Modifier.width(160.dp), onMoreClick = {})
}
//
//@Composable
//@Preview
//fun PersonGridPreview(){
//    MemberGrid(membersList, contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp), onItemClick = {} )
//}