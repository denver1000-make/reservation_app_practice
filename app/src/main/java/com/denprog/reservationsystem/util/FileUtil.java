package com.denprog.reservationsystem.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtil {
    public static final String profileImagesFolder = "user_profile_images";
    public static final String personalProfileFolderAppend = "_prof";
    public static void insertBitmapToInternalStorage(Context context, Bitmap bitmap, String actionSpecificFolder, String personalFolder, String fileName) throws IOException {
        File file = new File(context.getFilesDir(), actionSpecificFolder);
        file.mkdir();
        File personalFolderFile = new File(file, personalFolder);
        personalFolderFile.mkdir();
        File actualFileName = new File(personalFolderFile, fileName);
        FileOutputStream fos = new FileOutputStream(actualFileName);
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
    }

    public static Bitmap convertUriToBitmap(Context context, Uri uri) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(inputStream);
    }

    public static String generateRandomKey(int length) {
        String characters = "abcdefghijklmnopqrstyuvwxyzABCDEFGHJIKLMNOPQRSTUVWXYZ";
        StringBuilder randomStr = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.ceil(Math.random() * characters.length());
            randomStr.append(characters.charAt(randomIndex));
        }
        return randomStr.toString();
    }
}
