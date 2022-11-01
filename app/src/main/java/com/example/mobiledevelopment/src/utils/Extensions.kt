package com.example.mobiledevelopment.src.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1