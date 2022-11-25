package com.maks.firecompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maks.firecompose.ui.theme.FireComposeTheme

class MainActivity : ComponentActivity() {

    val list = mutableStateListOf<ProductViewModel.Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Greeting(list)

                }
            }
        }
        ProductViewModel().getProducts { list.addAll(it) }

    }

}

@Composable
fun Greeting(platList: SnapshotStateList<ProductViewModel.Product>) {

Scaffold(topBar = {
    TopAppBar(
        title = {
            Row {
                Text("Firestore Demo")
            }
        })
},
    content = {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "\uD83C\uDF3F  Products",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(1.dp)
                        .background(MaterialTheme.colors.primary)
                ) {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(1f),
                            text = " Product name",
                            style = MaterialTheme.typography.h5,
                            color = Color.White

                        )
                    }
                    Column(
                        Modifier.padding(8.dp)
                    ) {
                        Text(
                            "Price",
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                    }
                }
            }
            items(platList) { product ->
                Card(
                    modifier = Modifier.padding(5.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 5.dp,
                    backgroundColor = MaterialTheme.colors.surface
                )  {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Column(
                            Modifier.weight(1f)
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(1f),
                                text = " ${product.name}",
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Column(
                        ) {
                            Text(
                                " ${product.price}",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FireComposeTheme {
    }
}