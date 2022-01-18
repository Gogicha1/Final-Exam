package com.example.samazon.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.samazon.ProfileActivity
import com.example.samazon.R
import com.example.samazon.data.ProductInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class AddItemFragment : Fragment(R.layout.fragment_add_item) {

    private lateinit var add: FloatingActionButton
    private lateinit var exit: FloatingActionButton
    private lateinit var productName: EditText
    private lateinit var productPrice: EditText
    private lateinit var productDescription: EditText
    private val db = FirebaseDatabase.getInstance().getReference("products")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add = view.findViewById(R.id.add)
        exit = view.findViewById(R.id.exit)
        productName = view.findViewById(R.id.productName)
        productPrice = view.findViewById(R.id.productPrice)
        productDescription = view.findViewById(R.id.productDescription)

        registerListeners()

    }

    private fun registerListeners() {
        exit.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ProfileActivity::class.java)
                it.startActivity(intent)
            }
        }


        add.setOnClickListener {
            val name = productName.text.toString()
            val price = productPrice.text.toString()
            val description = productDescription.text.toString()

            val productInfo = ProductInfo(name, price, description)
            if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
            } else {
                db.child(name).setValue(productInfo).addOnSuccessListener {
                    productName.text.clear()
                    productPrice.text.clear()
                    productDescription.text.clear()
                    Toast.makeText(requireContext(), "პროდუქტი დაემატა", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}