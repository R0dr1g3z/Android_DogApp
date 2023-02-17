package com.example.dogapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dogapp.data.Dog
import com.example.dogapp.data.dogs
import com.example.dogapp.ui.theme.DogAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogAppTheme {
                DogApp()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DogApp() {
    Scaffold(topBar = {
        DogTopBar()
    }) {
        LazyColumn {
            items(dogs) {
                DogItem(dog = it)
            }
        }
    }
}

@Composable
fun DogItem(dog: Dog) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.padding(8.dp), elevation = 4.dp) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                DogHobby(dogHobby = dog.hobbies)
            }
        }
    }
}

@Composable
fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.expand_button_content_description),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun DogHobby(@StringRes dogHobby: Int) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 16.dp
        )
    ) {
        Text(
            stringResource(R.string.about),
            style = MaterialTheme.typography.h3
        )
        Text(
            stringResource(dogHobby),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun DogIcon(@DrawableRes dogIcon: Int) {
    Image(
        modifier = Modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        painter = painterResource(dogIcon),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int) {
    Column {
        Text(
            text = stringResource(dogName),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.h2
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun DogTopBar() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_woof_logo), contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
        )
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview
@Composable
fun DogPreview() {
    DogAppTheme {
        DogApp()
    }
}

@Preview
@Composable
fun DarkDogPreview() {
    DogAppTheme(darkTheme = true) {
        DogApp()
    }
}
