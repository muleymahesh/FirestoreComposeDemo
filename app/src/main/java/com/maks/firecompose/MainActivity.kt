package com.maks.firecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maks.firecompose.ui.theme.FireComposeTheme

class MainActivity : ComponentActivity() {
    val vm = ProductViewModel()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireComposeTheme {
                var screenName by remember { mutableStateOf("Home") }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    if(screenName == "Home")
                    ProductListScreen(vm , { screenName = it })
                    else
                        AddProductScreen(vm , { screenName = it } )

                }
            }
        }

    }
}

@Composable
fun ProductListScreen(vm: ProductViewModel, setScreen:(name:String) -> Unit) {
    val productList = remember { mutableStateListOf<ProductViewModel.Product>() }

    vm.getProducts { productList.addAll(it) }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row {
                    Text("Firestore Demo")
                }
            })
    },
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item {
                    ProductListTitle()

                }
                items(productList) { product ->
                    ProductItem(product = product)
                }
            }

        }
        , floatingActionButton = {
            FloatingActionButton(
                onClick = { setScreen("Add") },
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    )

}

@Composable
fun AddProductScreen(vm: ProductViewModel, setScreen: (name: String) -> Unit){
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("0.0") }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row {
                    Text("Firestore Demo")
                }
            })
    },
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField( modifier = Modifier.fillMaxWidth(1f), value = name, onValueChange = { name = it}, label = {
                    Text(text = "Product name")
                })
                OutlinedTextField(modifier = Modifier.fillMaxWidth(1f), value = price, onValueChange = { price = it }, label = {
                    Text(text = "Price")
                })
                Button(onClick = { vm.addProduct(name,price.toDouble(),{ setScreen("Home")}) }) {
                    Text(text = "Save")
                }
            }

        })
}


@Composable
fun ProductListTitle(){
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
                style = MaterialTheme.typography.h6,
                color = Color.White

            )
        }
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(
                "Price",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }
    }
}
@Composable
fun ProductItem(product: ProductViewModel.Product){
    Card(
        modifier = Modifier
            .padding(5.dp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FireComposeTheme {
    }
}