package com.example.student.homework1images;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button button;
    private TextView textView;
    private ProgressBar progressBar;
    private TextView textLoading;
    private List<ImageModel> list = new ArrayList<>();
    private int position;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addList();
        findId();
        setRecyclerView();
        Permissions();
        setDownload();
    }

    public void findId() {

        recyclerView = findViewById(R.id.recycler);
        button = findViewById(R.id.button_dowload);
        textView = findViewById(R.id.text_view);
        progressBar = findViewById(R.id.progress_bar);
        textLoading = findViewById(R.id.text_progress_bar);
    }

    public void updateText(final int position) {
        this.position = position;
        textView.setText(list.get(position).getHttp());
    }


    public void setRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        ImageAdapter adapter = new ImageAdapter(MainActivity.this, getResources().getStringArray(R.array.image_array));
        recyclerView.setAdapter(adapter);
    }

    public void addList() {
        list.add(new ImageModel("https://cde.laprensa.e3.pe/ima/0/0/1/8/7/187350.jpg", false, "image2"));
        list.add(new ImageModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlVvpW23qpKjwVj9Aqjp74jZQTndtvPkhcj-yr5vzuQzL8byF6", false, "image 3"));
        list.add(new ImageModel("https://i.dailymail.co.uk/i/pix/2017/01/16/20/332EE38400000578-4125738-image-a-132_1484600112489.jpg", false, "image 4"));
        list.add(new ImageModel("http://www.blueplanetheart.it/wp-content/uploads/2018/07/titano.jpg", false, "image 4"));
    }


    @SuppressLint("StaticFieldLeak")
    private class MyAsynkTask extends AsyncTask<URL, Void, String> {

        MainActivity mainActivity;

        public void setContext(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            textLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            int count;

            try {

                URL url = urls[0];
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                InputStream input = new BufferedInputStream(url.openStream(),
                        10000);
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title" + String.valueOf(position), "Description");
                OutputStream output = new FileOutputStream(Environment.getRootDirectory().getAbsolutePath()
                        + list.get(position));
                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.INVISIBLE);
            textLoading.setVisibility(View.INVISIBLE);

        }

    }

    private void setDownload() {

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!textView.getText().toString().isEmpty()) {
                    if (!list.get(position).isSaved()) {

                       list.get(position).setSaved(true);
                        MyAsynkTask myAsynkTask = new MyAsynkTask();
                        myAsynkTask.setContext(MainActivity.this);
                        try {
                            myAsynkTask.execute(new URL(list.get(position).getHttp()));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        MyDialogFragment dialogFragment = new MyDialogFragment(list, position);
                        dialogFragment.show(getSupportFragmentManager(), "Kay");
                    }

                }
            }
        });
    }

    private void Permissions() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                        1001);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CONTACTS
                        },
                        1001);
            }
        }
    }

}
