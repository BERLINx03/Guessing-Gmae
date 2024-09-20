package com.example.guessing.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.guessing.R

@Composable
fun EndGameDialog(
    onDismissRequest: () -> Unit,
    image: Int
){
    Dialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .size(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Game Over",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onErrorContainer,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Button(onClick = { onDismissRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )) {
                Text(text = "Play Again")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndGameDialogPreview() {
    EndGameDialog(
        onDismissRequest = {},

        image = R.drawable.rounded_emoji_events_24
    )
}