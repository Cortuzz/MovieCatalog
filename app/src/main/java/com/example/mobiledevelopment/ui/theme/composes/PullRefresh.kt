package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun PullRefresh(onRefresh: () -> Unit, Context: @Composable () -> Unit) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { onRefresh() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = AccentColor,
                shape = RoundedCornerShape(16.dp)
            )
        }
    ) {
        Context()
    }
}