package com.eduardo.appwithfaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.eduardo.appwithfaker.ui.theme.AppWithFakerTheme
import com.github.javafaker.Faker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : ComponentActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private val faker = Faker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mapView = MapView(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setContent {
            AppWithFakerTheme {
                ShowContent()
            }
        }
    }

    @Composable
    fun ShowContent(){
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.BottomStart) {
                MapScreen()
                PersonInfoScreen()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val locations = listOf(
            LatLng(16.8958594, -92.0672737),
            LatLng(16.8777072, -92.1068749),
            // Agregado por Eduardo Antonio
            LatLng(16.90382048532684, -92.08386172114714)
        )

        for (location in locations) {
            val title = "Location: ${location.latitude}, ${location.longitude}"
            val snippet = "This is a fixed location"

            mMap.addMarker(MarkerOptions().position(location).title(title).snippet(snippet))
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    @Composable
    fun MapScreen(modifier: Modifier = Modifier) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Gray)

        ) {
            AndroidView(
                factory = { mapView },
                modifier = modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }

    }


    @Composable
    fun PersonInfoScreen() {
        val name = faker.name().fullName()
        val address = faker.address().fullAddress()
        val phoneNumber = faker.phoneNumber().phoneNumber()

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Nombre: $name",
                style = TextStyle(Color.DarkGray, fontSize = 25.sp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = "Dirección: $address",
                style = TextStyle(Color.Gray, fontSize = 15.sp)
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = "Teléfono: $phoneNumber",
                style = TextStyle(Color.Gray, fontSize = 15.sp)
            )
        }
    }
}





