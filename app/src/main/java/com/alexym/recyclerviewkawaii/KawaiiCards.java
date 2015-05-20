package com.alexym.recyclerviewkawaii;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class KawaiiCards extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{

    /*
Declarar instancias globales
*/
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private final String TAG = "KawaiiCards";
    SwipeRefreshLayout swipeRefresh;
    List items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kawaii_cards);
        // Inicializar Animes


        //Se adieren a la lista los objetos con su atributos

        items.add(new Anime(R.drawable.angel, "Angel Beats", 230));
        items.add(new Anime(R.drawable.death, "Death Note", 456));
        items.add(new Anime(R.drawable.fate, "Fate Stay Night", 342));
        items.add(new Anime(R.drawable.nhk, "Welcome to the NHK", 645));
        items.add(new Anime(R.drawable.suzumiya, "Suzumiya Haruhi", 459));

// Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        //lManager = new LinearLayoutManager(this);
        //recycler.setLayoutManager(lManager);
        //GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        //recycler.setLayoutManager(mLayoutManager);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
        recycler.setLayoutManager(mLayoutManager);

// Crear un nuevo adaptador
        adapter = new AnimeAdapter(items);
        recycler.setAdapter(adapter);
        recycler.addOnItemTouchListener(new RecyclerItemClickListener(KawaiiCards.this, recycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Log.i(TAG, "Esta es la position: " + String.valueOf(position));
                Intent i = new Intent(KawaiiCards.this, DetailActivity.class);
                Anime cardClick=(Anime)items.get(position);
                Log.i(TAG, String.valueOf(cardClick.getImagen()));
                i.putExtra("imagen",cardClick.getImagen());
                View sharedView = view.findViewById(R.id.imagen_view_card);

                String transitionName = getString(R.string.image_card_animation);

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(KawaiiCards.this, sharedView, transitionName);
                startActivity(i, transitionActivityOptions.toBundle());

            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

        this.setTitle("Anime");
        //Propiedades para el efecto de actualizar listado
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_dark,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kawaii_cards, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Evento que se ejecuta al actualizar el listado con swipe
    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {


// Finalizar swipeRefresh
                swipeRefresh.setRefreshing(false);
            }
        }, 8000);
    }
}
