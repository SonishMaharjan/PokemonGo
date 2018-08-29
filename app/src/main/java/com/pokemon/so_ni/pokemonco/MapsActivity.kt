package com.pokemon.so_ni.pokemonco

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var playerPower:Int= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
        loadPokemons()
    }


    var ACCESS_L0CATION = 1234
    fun checkPermission()
    {
       if(Build.VERSION.SDK_INT>=23)
       {

           //call ActivityCompat.checkSefPermission to check permission
           if(ActivityCompat.checkSelfPermission(
                   this,Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
           {

               requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_L0CATION)
               return
           }

       }

        getUserLocation()
    }




     private  fun getUserLocation()
    {
        Toast.makeText(this,"User location access on",Toast.LENGTH_LONG).show()

        var myLocation = MyLocationListener()
        //LocationManager provides access to system location service
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,1f,myLocation)

        var myThread = MyThread()
        myThread.start()

    }

    var userLocation:Location?= null
    inner class MyLocationListener: LocationListener
    {


        constructor()
        {
            userLocation = Location("Start")
            userLocation!!.longitude =0.0
            userLocation!!.latitude = 0.0

        }
        override fun onLocationChanged(location: Location?) {
            userLocation = location
        }


        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

    var pokemon:Pokemon?= null
    var pokPos:LatLng?= null

    inner class MyThread:Thread
    {
        constructor():super()

        override fun run()
        {
            Log.d("he","hi override run")
            while(true)
            {
                try{
                    runOnUiThread()
                    {

                        mMap!!.clear()
                        val kathmandu = LatLng(userLocation!!.latitude, userLocation!!.longitude)
                        //  mMap.addMarker(MarkerOptions().position(kathmandu).title("Marker in Sydney"))
                        // mMap.moveCamera(CameraUpdateFactory.newLatLng(kathmandu))

                        mMap!!.addMarker(MarkerOptions().position(kathmandu).title("Me").
                                snippet("here i am").icon(BitmapDescriptorFactory.fromResource(R.drawable.pika)))

                     //   mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(kathmandu,24f))

                        //show pokemons
                        /*=
                            pokemon=Pokemon("HappyCry",R.drawable.happycry,90,"Bhotebahar",27.696762, 85.309859)
                            val pokPos = LatLng(pokemon!!.lat!!, pokemon!!.log!!)
                            mMap!!.addMarker(MarkerOptions().position(pokPos).title(pokemon!!.name).
                                    snippet("here i am").icon(BitmapDescriptorFactory.fromResource(pokemon!!.image!!)))

*/


                        for(i in 0..(listOfPokemons.size-1))
                        {

                            pokemon=listOfPokemons[i]

                            if(!pokemon!!.isCatch!!)
                            {
                                pokPos = LatLng(pokemon!!.location!!.latitude, pokemon!!.location!!.longitude)
                                mMap!!.addMarker(MarkerOptions().position(pokPos!!).title(pokemon!!.name).
                                        snippet( pokemon!!.des).icon(BitmapDescriptorFactory.fromResource(pokemon!!.image!!)))

                                 if(userLocation!!.distanceTo(pokemon!!.location)<200)
                                 {
                                     playerPower += pokemon!!.power!!
                                     pokemon!!.catchPokemon(applicationContext,playerPower!!)
                                 }
                            }



                        }
                    }

                    Thread.sleep(1000)
                }catch (e:Exception)
                {

                }
            }
        }




    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode)
        {
            ACCESS_L0CATION ->
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    getUserLocation()
                }
                else
                {

                    Toast.makeText(this,"We can't access your location",Toast.LENGTH_LONG).show()
                }
            }



        }

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        }


    var listOfPokemons= ArrayList<Pokemon>()
    fun loadPokemons()
    {
        listOfPokemons.add(Pokemon("HappyCry",R.drawable.happycry,90,"Im in Bhotebahar",27.696762, 85.309859))
        listOfPokemons.add(Pokemon("Laugh",R.drawable.laugh,80,"Im in Basantapur",27.703544, 85.307241))
        listOfPokemons.add(Pokemon("Wink",R.drawable.wink,75,"Im in Samakhusi",27.725799, 85.312840))
        listOfPokemons.add(Pokemon("Silly",R.drawable.silly,80,"Im in Mhepi",27.727421, 85.308664))


    }



}
