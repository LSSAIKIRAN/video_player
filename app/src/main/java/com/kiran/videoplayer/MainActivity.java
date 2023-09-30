package com.kiran.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.kiran.videoplayer.databinding.ActivityMainBinding;
import android.Manifest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<File> fileList;
    File path = new File(Objects.requireNonNull(System.getenv("EXTERNAL_STORAGE")));
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        askPermission();


    }

    private void askPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayFiles();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Enable storage", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    private void displayFiles() {
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        fileList = new ArrayList<>();


        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = Environment.getExternalStorageDirectory();
            fileList.addAll(findVideos(path));
            customAdapter = new CustomAdapter(this, fileList);
            customAdapter.setHasStableIds(true);
            binding.recyclerview.setAdapter(customAdapter);
        } else {
            Toast.makeText(this, "External storage is not available.", Toast.LENGTH_SHORT).show();
        }

    }

    private Collection<? extends File> findVideos(File file) {
        ArrayList<File> myVideos = new ArrayList<>();
        File[] allFiles = file.listFiles();

        if (allFiles != null) {
            for (File singleFile : allFiles) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    myVideos.addAll(findVideos(singleFile));
                } else if (singleFile.getName().toLowerCase().endsWith(".mp4")) {
                    myVideos.add(singleFile);
                }
            }
        }
        return myVideos;
    }


}