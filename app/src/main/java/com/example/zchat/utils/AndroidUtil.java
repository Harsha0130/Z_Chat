package com.example.zchat.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.zchat.model.UserModel;
import com.google.firebase.firestore.auth.User;

public class AndroidUtil {

   public static void showToast(Context context, String message) {
      Toast.makeText(context, message, Toast.LENGTH_LONG).show();
   }

   public static void passUserModeelAsIntent(Intent intent, UserModel model) {
      intent.putExtra("username", model.getUsername());
      intent.putExtra("Phone", model.getPhone());
      intent.putExtra("userId", model.getUserId());
   }

   public static UserModel getUserNameFromIntent(Intent intent) {
      UserModel userModel = new UserModel();
      userModel.setUsername(intent.getStringExtra("username"));
      userModel.setPhone(intent.getStringExtra("Phone"));
      userModel.setUserId(intent.getStringExtra("userId"));
      return userModel;
   }

   public static void setProfilePic(Context context, Uri imageUri, ImageView imageView) {
      Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
   }

}
