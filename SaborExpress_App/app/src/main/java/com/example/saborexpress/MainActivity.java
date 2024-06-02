package com.example.saborexpress;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.saborexpress.databinding.ActivityMainBinding;


// El binding es una técnica que permite una forma más segura y eficiente de interactuar con las vistas en tu aplicación.
// En lugar de usar findViewById para cada vista, el binding genera automáticamente una clase de binding que contiene referencias
// directas a todas las vistas en un layout. Esto mejora la seguridad del tipo y evita errores de tiempo de ejecución
// al intentar acceder a vistas que podrían no existir o haber cambiado de nombre.
//
// En este caso, `ActivityMainBinding` es una clase generada automáticamente que corresponde al archivo de layout `activity_main.xml`.
// Este archivo se infla y se utiliza para acceder directamente a las vistas definidas en el layout sin necesidad de llamadas adicionales
// a `findViewById`.

// Actividad principal de la aplicación
public class MainActivity extends AppCompatActivity {

    // Variables para el binding de la actividad principal
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflamos el layout de la actividad principal usando el binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializamos la vista de navegación inferior (BottomNavigationView)
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configuramos la barra de aplicaciones para considerar cada ID de menú como un destino de nivel superior
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_shopping_list, R.id.navigation_search,
                R.id.navigation_profile)
                .build();

        // Inicializamos el controlador de navegación (NavController) y lo asociamos
        // con el fragmento de host de navegación.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Configuramos la barra de aplicaciones (ActionBar)
        // con el controlador de navegación y la configuración de la barra de aplicaciones
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Configuramos la vista de navegación inferior con el controlador de navegación
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}


