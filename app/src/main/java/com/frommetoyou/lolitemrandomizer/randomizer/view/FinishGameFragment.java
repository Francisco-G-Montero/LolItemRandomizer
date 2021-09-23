package com.frommetoyou.lolitemrandomizer.randomizer.view;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frommetoyou.lolitemrandomizer.BuildConfig;
import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.FragmentFinishGameBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FinishGameFragment extends Fragment implements View.OnClickListener {
    private FragmentFinishGameBinding binding;
    private final int CAMERA_PERM_CODE = 101;
    private final int CAMERA_INTENT_CODE = 102;
    private String winDescription = "";
    private Bitmap bitmap = null;
    private String currentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFinishGameBinding.inflate(inflater);
        winDescription = FinishGameFragmentArgs.fromBundle(getArguments()).getWinDescription();
        if (!winDescription.equals("")) binding.tvWinDescription.setText(winDescription);
        else binding.tvWinDescription.setText(getString(R.string.empty_description));
        binding.btnTakePhoto.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.btnShareImage.setOnClickListener(this);
        binding.btnCopyCode.setOnClickListener(this);
        return binding.getRoot();
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        else dispatchTakePictureIntent();//openCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                //openCamera();
                dispatchTakePictureIntent();

            else
                Toast.makeText(getContext(), "No se aceptaron los permisos de cámara.", Toast.LENGTH_SHORT).show();
        }

    }

    private void openCamera() {
        Toast.makeText(getContext(), "camera", Toast.LENGTH_SHORT).show();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_INTENT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_INTENT_CODE) {
       /*     Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            binding.ivGamePhoto.setVisibility(View.VISIBLE);
            binding.ivGamePhoto.setImageBitmap(bitmap);
            this.bitmap = bitmap;
            binding.btnShareImage.setEnabled(true);*/
            binding.ivGamePhoto.setVisibility(View.VISIBLE);
            setPic();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = getString(R.string.app_name) + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Error al tratar de crear la imagen", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_INTENT_CODE);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = binding.ivGamePhoto.getWidth();
        int targetH = binding.ivGamePhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 1;

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        binding.btnShareImage.setEnabled(true);
        binding.ivGamePhoto.setImageBitmap(bitmap);
    }
    @Override
    public void onClick(View v) {
        if (v == binding.btnTakePhoto)
            askCameraPermissions();
        else if (v == binding.btnBack) {
            new MaterialAlertDialogBuilder(getActivity(), R.style.MaterialAlertDialog_MaterialComponents_Title_Icon)
                    .setTitle(R.string.main_are_you_sure_finish_game)
                    .setMessage(R.string.main_alert_message1_finish_game)
                    .setPositiveButton(R.string.confirmar, (dialog, which) -> {
                        NavHostFragment.findNavController(this).navigate(FinishGameFragmentDirections.actionFinishGameFragmentToCreateOrJoinFragment());
                    })
                    .setNegativeButton(R.string.cancelar, null)
                    .show();
        } else if (v == binding.btnShareImage) {
            //String pathofBmp = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "LoLItemRandomizerImage", null);
            Uri bmpUri = Uri.parse(currentPhotoPath);
            Intent shareImage = new Intent(Intent.ACTION_SEND);
            shareImage.setType("image/*");
            shareImage.putExtra(Intent.EXTRA_STREAM, bmpUri);
            startActivity(Intent.createChooser(shareImage, getString(R.string.finish_game_share)));
        } else if ( v == binding.btnCopyCode){
            String message;
            if (!binding.tvWinDescription.getText().toString().equals(getString(R.string.empty_description))) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getActivity().getString(R.string.app_name), binding.tvWinDescription.getText().toString());
                clipboard.setPrimaryClip(clip);
                message = "El texto ha sido copiado con éxito";
            } else {
                message = "No hay descripción útil para copiar";
            }
            Snackbar.make(binding.getRoot(),message,Snackbar.LENGTH_SHORT).show();
        }
    }
}