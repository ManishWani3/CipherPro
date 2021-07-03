package com.manish.cipher_pro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class Image extends AppCompatActivity {

    private static final String FILE_NAME_ENC = System.currentTimeMillis() + ".txt";
    private static final String FILE_NAME_DEC = System.currentTimeMillis() + ".jpg";

    Button button2, encro_image, share_button;
    RadioButton radio_encr, radio_decr;
    EditText secretkey1, iv1;
    TextView path;
    Bitmap bitmap;
    Uri uri, uri1;
    String url;

    int rqenc = 43;
    int rqdec = 10;

    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        radio_encr = findViewById(R.id.radio_encrypt);
        radio_decr = findViewById(R.id.radio_decrypt);
        encro_image = findViewById(R.id.encro_image);
        secretkey1 = findViewById(R.id.editTextTextPassword1);
        iv1 = findViewById(R.id.editTextTextPasswordI1);
        path = findViewById(R.id.editTextTextMultiLine1);
        button2 = findViewById(R.id.button2);
        share_button = findViewById(R.id.share_button);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Image.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    if (encro_image.getText() == "ENCRYPT & UPLOAD") {
                        startencSearch();
                    } else {
                        startdecSearch();
                    }
                } else {
                    ActivityCompat.requestPermissions(Image.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 88);
                }
            }
        });


        radio_encr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_encr.setTextColor(Color.WHITE);
                radio_decr.setTextColor(Color.rgb(128, 128, 128));
                encro_image.setText("ENCRYPT & UPLOAD");
                button2.setText("SELECT IMAGE");
                secretkey1.setHint("Create Secret-Key");
                iv1.setHint("Create Initialization-Vector");
                share_button.setEnabled(true);
                share_button.setVisibility(View.VISIBLE);
            }
        });

        radio_decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_decr.setTextColor(Color.WHITE);
                radio_encr.setTextColor(Color.rgb(128, 128, 128));
                encro_image.setText("DECRYPT");
                button2.setText("SELECT FILE");
                secretkey1.setHint("Enter Secret-Key");
                iv1.setHint("Enter Initialization-Vector");

                share_button.setEnabled(false);
                share_button.setVisibility(View.GONE);
            }
        });

        encro_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if ((encro_image.getText()) == "ENCRYPT & UPLOAD") {

                    if (secretkey1.length() == 0) {
                        secretkey1.setError("Create Secret Key");
                    }
                    if (iv1.length() > 16 || iv1.length() < 16) {
                        iv1.setError("Length should be 16-Bytes");
                    }
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(Image.this.getContentResolver(), uri);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        InputStream is = new ByteArrayInputStream(stream.toByteArray());

                        File outputFileEncrypt0 = new File(Environment.getExternalStorageDirectory(), "Cipher-Pro/Images");
                        if (!outputFileEncrypt0.exists()) {
//                                outputFileEncrypt0.mkdirs();
                            if (!outputFileEncrypt0.mkdirs()) {
                                Log.d("App", "failed to create directory");
                            }
                        }
                        File outputFileEncrypt = new File(outputFileEncrypt0, FILE_NAME_ENC);

                        Algorithm.encrypt_files(secretkey1.getText().toString(), iv1.getText().toString(), is, new FileOutputStream(outputFileEncrypt));
                        Toast.makeText(Image.this, "Encrypted", Toast.LENGTH_SHORT).show();

                        //uploading process

                        uri1 = Uri.fromFile(outputFileEncrypt);
                        if (uri1 != null) {
                            uploadImage(uri1);
                        } else {
                            Toast.makeText(Image.this, "Image is not yet encrypted", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    if (secretkey1.length() == 0) {
                        secretkey1.setError("Enter Secret Key");
                    }
                    if (iv1.length() > 16 || iv1.length() < 16) {
                        iv1.setError("Length should be 16-Bytes");
                    }
                    try {
                        File outputFileDecrypt0 = new File(Environment.getExternalStorageDirectory(), "Cipher-Pro/Images");
                        if (!outputFileDecrypt0.exists()) {
//                                outputFileEncrypt0.mkdirs();
                            if (!outputFileDecrypt0.mkdirs()) {
                                Log.d("App", "failed to create directory");
                            }
                        }
                        File outputFileDecrypt = new File(outputFileDecrypt0, FILE_NAME_DEC);

                        InputStream iss = getContentResolver().openInputStream(uri);
                        Algorithm.decrypt_files(secretkey1.getText().toString(), iv1.getText().toString(), iss, new FileOutputStream(outputFileDecrypt));
                        Toast.makeText(Image.this, "Decrypted Successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Image.this, SendMessages.class);
                intent.putExtra("uploadedFile_url", url);
                intent.putExtra("uploadedFile__key", secretkey1.getText().toString());
                intent.putExtra("uploadedFile_iv", iv1.getText().toString());
                intent.putExtra("sendingUser_Email", mAuth.getCurrentUser().getEmail());
                startActivity(intent);
            }
        });
    }


    private void uploadImage(Uri uri1) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File......");
        progressDialog.setProgress(0);
        progressDialog.show();


        String En_imageName = System.currentTimeMillis() + ".txt";
        mStorageRef = storage.getReference();
        mStorageRef.child("Encrypted_Images").child(En_imageName).putFile(uri1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url = uri.toString();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Image.this, "Failed to upload file, Try again!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.cancel();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Image.this, "Failed to upload file, Try again!", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentProgress = (int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }


    public void startencSearch() {

        Intent myfile = new Intent(Intent.ACTION_GET_CONTENT);
        myfile.setType("image/*");
        startActivityForResult(myfile, rqenc);
    }

    public void startdecSearch() {

        Intent myfile = new Intent(Intent.ACTION_GET_CONTENT);
        myfile.setType("text/*");
        startActivityForResult(myfile, rqdec);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == rqenc && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            Toast.makeText(this, "Uri:" + uri, Toast.LENGTH_LONG).show();
            path.setText(uri.toString());
        } else if (requestCode == rqdec && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            Toast.makeText(this, "Uri:" + uri, Toast.LENGTH_LONG).show();
            path.setText(uri.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 88 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (encro_image.getText() == "ENCRYPT & UPLOAD") {
                startencSearch();
            } else {
                startdecSearch();
            }
        } else {
            Toast.makeText(Image.this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }
}

