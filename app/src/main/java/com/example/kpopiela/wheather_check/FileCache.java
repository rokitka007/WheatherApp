package com.example.kpopiela.wheather_check;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) throws IOException {
        // Find the dir to save cached images
        Boolean created = Boolean.FALSE;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(
                    android.os.Environment.getExternalStorageDirectory(),
                    "JsonParseTutorialCache");
        else
            cacheDir = context.getCacheDir();
        Log.i("Look", cacheDir.getAbsolutePath());
        if (!cacheDir.exists())
            created = cacheDir.mkdirs();
        if (created)
           Log.i("Look","Cache Dir created!");
    }

    public File getFile(String url) {
        if (!cacheDir.exists())
            Log.i("Look", "Cache dir does not exist");
        else
            Log.i("Look", "Cache dir is ok ");
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File cache_file = new File(cacheDir, filename);
        return cache_file;

    }

    public void clear() throws IOException {
        Boolean deleted = Boolean.FALSE;
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files) {
            deleted = f.delete();
            if (!deleted)
                throw new IOException("File " + f.getName() + " wasn't deleted");
        }
    }

}

