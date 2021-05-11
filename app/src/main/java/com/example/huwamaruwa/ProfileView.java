package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Progress.LoadingProgress;
import com.example.huwamaruwa.addProduct.AddNewItem;
import com.example.huwamaruwa.buyerRentalRequestManage.EditSingleSellerRequest;
import com.example.huwamaruwa.buyerRentalRequestManage.ProductImageFullScreen;
import com.example.huwamaruwa.buyerRentalRequestManage.SentRentalRequestBySeller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileView extends AppCompatActivity {

    TextView name, email, phone, address, userType;
    Button btnProfilePicGallery, btnProfilePicUpload, btnDeleteUser;
    CircularImageView profImage;
    String userId, userImageURL;
    ArrayList<Uri> profileImage;
    DatabaseReference dbf;
    StorageReference sdbRefe;
    String FinalImageString;
    Context context;
    LoadingProgress loadingProgress;
    public static final String EXTRA_MESSAGE2 = "aab";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        //Hooks
        name = findViewById(R.id.ProfileView_name);
        email = findViewById(R.id.ProfileView_email);
        phone = findViewById(R.id.ProfileView_phone);
        address = findViewById(R.id.ProfileView_Address);
        userType = findViewById(R.id.ProfileView_userType);
        btnProfilePicGallery = findViewById(R.id.btnProfile_gallery);
        btnProfilePicUpload = findViewById(R.id.btnProfile_picUpload);
        profImage = findViewById(R.id.ProfPic_circularView);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);

        loadingProgress = new LoadingProgress(ProfileView.this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login",user.getUid());
        userId = user.getUid();

        dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    name.setText(snapshot.child("name").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    phone.setText(snapshot.child("phoneNo").getValue().toString());
                    address.setText(snapshot.child("address").getValue().toString());
                    userType.setText(snapshot.child("userType").getValue().toString());
                    userImageURL = snapshot.child("userImage").getValue().toString();
                    FinalImageString = userImageURL;
                    Glide.with(ProfileView.this).load(userImageURL).into(profImage);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnProfilePicGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnProfilePicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileImage != null){
                    updateProfilePicture();

                }else{
                    Toast.makeText(getApplicationContext(), "Profile Picture Not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileView.this, ProfPicFullScreen.class);
                intent.putExtra(EXTRA_MESSAGE2,FinalImageString);
                startActivity(intent);
            }
        });

     btnDeleteUser.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             AlertDialog.Builder deleteAccount  = new AlertDialog.Builder(view.getContext());
             deleteAccount.setTitle("Delete Account");
             deleteAccount.setMessage("Are You Sure you want to delete the account?");

             deleteAccount.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     dbf = FirebaseDatabase.getInstance().getReference().child("Users");
                     dbf.keepSynced(true);
                     dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                             dbf.removeValue();

                             user.delete()
                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if (task.isSuccessful()) {
                                                 Log.d("DELETE", "User account deleted.");
                                             }
                                         }
                                     });

                             Toast.makeText(context, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();

                             Intent intent = new Intent(context,Login.class);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                             context.startActivity(intent);
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Error", error.getMessage());
                         }
                     });
                 }
             });
             deleteAccount.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     //return to base activity
                 }
             });
             deleteAccount.create().show();
         }
     });
    }

    public void editProfile(View view){
        Intent intent = new Intent(getApplicationContext(), UserProfileEdit.class);
        startActivity(intent);
    }

    public void imageChooser(){
        FishBun.with(this).setImageAdapter(new GlideAdapter()).setMaxCount(1).setRequestCode(100).startAlbum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            profileImage = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            profImage.setImageURI(profileImage.get(0));

            Toast.makeText(getApplicationContext(), "Profile Picture selected", Toast.LENGTH_SHORT).show();

        }
    }
    public void updateProfilePicture(){
        loadingProgress.startProgress();
        sdbRefe = FirebaseStorage.getInstance().getReference().child("User Profile Pictures");

        StorageReference storageReference  = sdbRefe.child(System.currentTimeMillis() +"."+ GetFileExtension(profileImage.get(0)));
        storageReference.putFile(profileImage.get(0)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FinalImageString = uri.toString();
                        updateInRealTimeDB();
                        loadingProgress.dismissProgress();
                        Toast.makeText(getApplicationContext(), "FinalImageString set", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingProgress.dismissProgress();
                Toast.makeText(getApplicationContext(), "Failed Upload", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void updateInRealTimeDB(){
        dbf = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap hashMap = new HashMap();
        hashMap.put("userImage",FinalImageString);

        dbf.child(userId).updateChildren(hashMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed Upload", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getApplicationContext(), "Real time DB updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileView.this, MainActivity.class);
        startActivity(intent);
    }

    public void LogoutMethod(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent =new Intent(ProfileView.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}