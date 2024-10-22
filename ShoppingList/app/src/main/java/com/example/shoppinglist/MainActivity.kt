package com.example.shoppinglist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
//        val listView = findViewById<ListView>(R.id.listView)
//        val gridView = findViewById<GridView>(R.id.gridView)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val count = ArrayList<String>()

        val item = ArrayList<Item>()


        val priceRange  = IntRange(10,100)
        val array = resources.obtainTypedArray(R.array.image_list)
        for(index in 0 until array.length()){
            val photo = array.getResourceId(index,0)
            val name = "水果${index+1}"
            val price = priceRange.random()
            count.add("${index+1}個")
            item.add(Item(photo,name,price))
        }
        array.recycle()
        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,count)
//        gridView.numColumns = 3

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MyAdapter(item,R.layout.adapter_horizontal)
//        val gridLayoutManager = GridLayoutManager(this,3)
//        recyclerView.layoutManager = gridLayoutManager
//        recyclerView.adapter = MyAdapter(item,R.layout.adapter_vertical)

    }
}