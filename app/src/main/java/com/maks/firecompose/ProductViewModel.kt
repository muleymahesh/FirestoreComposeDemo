package com.maks.firecompose

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductViewModel: ViewModel() {


    fun getProducts( results: (products: List<Product>) ->Unit ){
        val list = mutableListOf<Product>()
        Firebase.firestore.collection("products")
            .get()
            .addOnSuccessListener {
                it.documents.forEach {
                    list.add(Product(it.data?.get("name") as String, it.data?.get("price") as Number))
                    Log.e("Product @@@",list[0].toString())
                }
                results(list)
                Log.e("@@@@@","result is empty ?? "+it.isEmpty)

            }
            .addOnFailureListener {
                Log.e("@@@@@",""+it.message)
            }
    }
    fun addProduct(name: String,price: Number,result: (value: String)->Unit){
        Firebase.firestore.collection("products")
            .add(hashMapOf("name" to name, "price" to price))
            .addOnCompleteListener {
                if (it.isSuccessful)result("success") else result("failed")}

    }

    data class Product(
        val name: String,
        val price: Number
    )

}