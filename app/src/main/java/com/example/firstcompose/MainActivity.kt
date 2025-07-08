package com.example.firstcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.example.firstcompose.ui.theme.FirstComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    MyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnBoardingScreen({ shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) {
        it.toString()
    }
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 4.dp)
        ) {
            LazyColumn {
                items(names) { name ->
                    Greeting(name)
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
//            tween(
//            durationMillis = 400
//        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(
                        bottom = extraPadding
                            .coerceAtLeast(0.dp)
                    )
            ) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            ElevatedButton(
                onClick = { expanded.value = !expanded.value },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    if (expanded.value) stringResource(R.string.show_less)
                    else stringResource(R.string.show_more)
                )
            }
        }
    }
}

@Composable
fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab")
            Button(
                onClick = onContinueClicked,
                modifier = Modifier.padding(24.dp)
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(
    showBackground = true, widthDp = 320, heightDp = 320, uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = false
)
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
private fun OnBoardingPreview() {
    FirstComposeTheme {
        OnBoardingScreen({})
    }

}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    FirstComposeTheme {
        Greetings()
    }
}