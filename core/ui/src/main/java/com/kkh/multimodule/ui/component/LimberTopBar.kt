package com.kkh.multimodule.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kkh.multimodule.core.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LimberHomeTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigationClick: (() -> Unit)? = null,
    onActionClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    titleColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (title.isNotEmpty()) {
                Text(text = title, color = titleColor)
            }
        },
        navigationIcon = {
            if (onNavigationClick != null) {
                androidx.compose.foundation.Image(
                    painter = painterResource(R.drawable.logo_limber),
                    modifier = Modifier.size(65.dp, 20.dp),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (onActionClick != null) {
                IconButton(onClick = onActionClick, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "notification",
                        tint = Color.Black
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
//            titleContentColor = titleColor,
//            navigationIconContentColor = titleColor,
//            actionIconContentColor = titleColor
        )
    )
}