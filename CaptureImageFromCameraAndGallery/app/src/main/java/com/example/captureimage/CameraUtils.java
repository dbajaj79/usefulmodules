package com.example.captureimage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.util.Pair;

import com.example.captureimage.util.AppConstants;
import com.example.captureimage.util.OSVersionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.captureimage.util.OSVersionUtils.isAboveNoughat;


public class CameraUtils
{
    public static final String TYPE_IMAGE = "image";
    private static final String DOWNLOAD_ = "Download_";
    private static final int MAX_WIDTH = 1600;
    private static final int MAX_HEIGHT = 1600;
    private static final int QUALITY = 85;
    private static final String SUPPORTED_TYPE = "image/jpg|image/jpeg|image/png|image/bmp|application/pdf";
    private static final String TYPE_JPEG = "image/jpeg";
    private static final String TYPE_JPG = "image/jpg";
    private static final String TYPE_PNG = "image/png";
    private static final String TYPE_BMP = "image/bmp";
    private static final String SELECT_PICTURE = "Select Picture";
    private static final String numberPic_DIR_NAME = "waysapp";
    private static final String JPG = ".jpg";
    private static final String PREFIX = "waysapp_";
    private static final String EXTERNAL_STORAGE = "ExternalStorage";
    private static final String SCANNED = "Scanned ";
    private static final String COM_ANDROID_EXTERNALSTORAGE_DOCUMENTS = "com.android.externalstorage.documents";
    private static final String COM_ANDROID_PROVIDERS_DOWNLOADS_DOCUMENTS = "com.android.providers.downloads.documents";
    private static final String COM_ANDROID_PROVIDERS_MEDIA_DOCUMENTS = "com.android.providers.media.documents";
    private static final String COM_IANHANNIBALLAKE_LOCALSTORAGE_DOCUMENTS = "com.ianhanniballake.localstorage.documents";
    private static final String COM_GOOGLE_ANDROID_APPS_PHOTOS_CONTENTPROVIDER = "com.google.android.apps.photos.contentprovider";
    private static final String PRIMARY = "primary";

    private static final String CONTENT_DOWNLOADS_PUBLIC_DOWNLOADS = "content://downloads/public_downloads";
    private static final String SLASH_DELIMITER = "/";
    public static final String TYPE_PDF = "application/pdf";
    private static final String COLUMN_DATA = "_data";
    private static final String PDF = ".pdf";

    private static final int REQUEST_CODE_GALLERY_PERMISSIONS = 456;
    private static final int REQUEST_CODE_EXTERNAL_STORAGE_PERMISSIONS = 789;
    private static final int BYTES_LENGTH = 4096;
    private static final String CONTENT = "content";
    private static final String FILE = "file";

    private static Uri capturedFileUri;


    // Delimiters
    public static final String EMPTY = "";
    public static final String COLON = ":";
    public static final String FORWARD_SLASH = "/";


    public static final int ACTION_CHOOSE_FILE = 1;
    public static final int ACTION_TAKE_PHOTO = 2;
    public static final String FILEPROIVDER = ".fileprovider";


    public static void takeCameraPhoto(Activity activity)
    {
        int hasCameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int hasGalleryPermisssion = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED)
        {
            if (hasGalleryPermisssion == PackageManager.PERMISSION_GRANTED)
            {
                startCameraIntent(activity);
            } else
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    Toast.makeText(activity, R.string.need_gallery_write_permission, Toast.LENGTH_LONG).show();
                } else
                {
                    // do nothing
                }

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE_PERMISSIONS);
            }
        } else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA))
            {
                Toast.makeText(activity, R.string.need_camera_permission, Toast.LENGTH_LONG).show();
            } else
            {
                // do nothing
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstants.REQUEST_CODE_CAMERA_AND_STORAGE_PERMISSIONS);
        }
    }

    public static void choosePhoto(Activity activity)
    {
        int hasGalleryPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasGalleryPermission == PackageManager.PERMISSION_GRANTED)
        {
            startGalleryIntent(activity);
        } else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Toast.makeText(activity, R.string.need_gallery_select_permission, Toast.LENGTH_LONG).show();
            } else
            {
                // do nothing
            }

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY_PERMISSIONS);
        }
    }


    private static final String TAG = "CameraUtils";
    private static void startCameraIntent(Activity activity)
    {
        File file = createExternalStoragePublicPicture(activity);
        if (file != null)
        {
            Callback numberPicHandlerCallback = getCallback(activity);
            String packageName = activity.getPackageName();
            Log.d(TAG, "startCameraIntent: "+packageName);
            String authority = new StringBuilder(2).append(packageName).append(FILEPROIVDER).toString();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            capturedFileUri = FileProvider.getUriForFile(activity, authority, file);
            numberPicHandlerCallback.setCapturedUri(capturedFileUri.toString());
            grantPermisssions(activity, takePictureIntent);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedFileUri);
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
            {
                activity.startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);
            }

        }
    }


    private static void grantPermisssions(Activity activity, Intent takePictureIntent)
    {
        if (OSVersionUtils.isKitkatAndBelow())
        {
            List<ResolveInfo> resolvedIntentActivities = activity.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities)
            {
                activity.grantUriPermission(resolvedIntentInfo.activityInfo.packageName, capturedFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else
        {
            // do nothing
        }
    }

    private static void revokePermissions(Activity activity)
    {
        if (OSVersionUtils.isKitkatAndBelow())
        {
            activity.revokeUriPermission(capturedFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else
        {
            //do nothing
        }
    }

    public static void startGalleryIntent(Activity activity)
    {
        Intent commonIntent = getFileChooserIntent();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType(SUPPORTED_TYPE);
        Intent chooserIntent = Intent.createChooser(commonIntent, SELECT_PICTURE);
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        activity.startActivityForResult(chooserIntent, ACTION_CHOOSE_FILE);
    }

    private static Intent getFileChooserIntent()
    {
        String[] mimeTypes = {TYPE_JPEG, TYPE_JPG, TYPE_PNG, TYPE_BMP, TYPE_PDF};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(SUPPORTED_TYPE);
        if (OSVersionUtils.isKitkatAndAbove())
        {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        } else
        {
            // do nothing
        }

        return intent;
    }



    public static void onRequestPermissionsResult(Activity activity, int requestCode, int[] grantResults)
    {
        switch (requestCode)
        {
            case AppConstants.REQUEST_CODE_CAMERA_AND_STORAGE_PERMISSIONS:
                onCameraPermissionResult(activity, grantResults);
                break;
            case REQUEST_CODE_EXTERNAL_STORAGE_PERMISSIONS:
                if (grantResults != null && grantResults.length > 0)
                {
                    onExternalStoragePermissionResult(activity, grantResults[0]);
                } else
                {
                    Toast.makeText(activity, R.string.need_gallery_write_permission, Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_GALLERY_PERMISSIONS:
                onGalleryPermissionResult(activity, grantResults);
                break;
            default:
                break;
        }
    }


    private static void onGalleryPermissionResult(Activity activity, int[] grantResults)
    {
        if (grantResults != null && grantResults.length > 0)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Permission Granted
                startGalleryIntent(activity);
            } else
            {
                // Permission Denied
                Toast.makeText(activity, R.string.need_gallery_select_permission, Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(activity, R.string.need_gallery_select_permission, Toast.LENGTH_LONG).show();
        }
    }

    private static void onExternalStoragePermissionResult(Activity activity, int grantResult)
    {
        if (grantResult == PackageManager.PERMISSION_GRANTED)
        {
            startCameraIntent(activity);
        } else
        {
            Toast.makeText(activity, R.string.need_gallery_write_permission, Toast.LENGTH_LONG).show();
        }
    }

    private static void onCameraPermissionResult(Activity activity, int[] grantResults)
    {
        if (grantResults != null && grantResults.length > 0)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                onExternalStoragePermissionResult(activity, grantResults[1]);
            } else
            {
                Toast.makeText(activity, R.string.need_camera_permission, Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(activity, R.string.need_camera_permission, Toast.LENGTH_LONG).show();
        }
    }

    public static void onCaptureRxResult(Activity activity, int requestCode, Intent data)
    {
        Callback callback = getCallback(activity);
        ProgressDialog progressDialog = ProgressDialog.show(activity, null, activity.getResources().getString(R.string.attaching_numberplate_pic), true, true);
        Observable.fromCallable(() -> getPathFromUri(activity, requestCode, data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(originalCompressedPair -> onCaptureImageResult(activity, progressDialog, callback, originalCompressedPair), throwable -> onCaptureImageError(activity, progressDialog, throwable));
    }



    private static void onCaptureImageError(Activity activity, ProgressDialog progressDialog, Throwable e)
    {
        //ExceptionHandler.handle(e, activity);
        dismissProgressDialog(progressDialog);
    }

    private static void onCaptureImageResult(Activity activity, ProgressDialog progressDialog, Callback callback, Pair<String, String> originalCompressedPair)
    {
        if (originalCompressedPair != null)
        {
            if (originalCompressedPair.second != null)
            {
                callback.onImageCaptured(originalCompressedPair);
            } else if(originalCompressedPair.first!=null)
            {
                callback.onImageCaptured(originalCompressedPair);

            }
            else
            {
                Toast.makeText(activity, R.string.number_plate_hint, Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(activity, R.string.number_plate_attach_error, Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog(progressDialog);
    }

    private static void dismissProgressDialog(ProgressDialog progressDialog)
    {
        if (progressDialog != null)
        {
            progressDialog.dismiss();
        } else
        {
            // do nothing
        }
    }

    @Nullable
    private static Pair<String, String> getPathFromUri(Activity activity, int requestCode, Intent data) throws IOException
    {
        switch (requestCode)
        {
            case ACTION_CHOOSE_FILE:
                return onFileSelected(activity, data);
            case ACTION_TAKE_PHOTO:
                revokePermissions(activity);
                return onPhotoCaptured(activity, data);
            default:
                return null;
        }
    }

    @Nullable
    private static Pair<String, String> onFileSelected(Context context, Intent intent) throws IOException
    {
        Uri selectedFileUri = intent.getData();
        String mimeType = getMimeType(selectedFileUri, context);
        if (mimeType.contains(TYPE_IMAGE))
        {
            return onDocumentSelected(context, selectedFileUri);
        } else
        {
            return null;
        }
    }

    @Nullable
    private static Pair<String, String> onDocumentSelected(Context context, Uri selectedFileUri) throws IOException
    {
        Pair<String, String> originalCompressedPair;
        String path;
        String documentPath = getDocumentPath(context, selectedFileUri);
        if (documentPath != null)
        {
            if (!isFileChoosenFromWaysappFolder(documentPath))
            {
                String compressedPath;
                String mimeType = getMimeType(selectedFileUri, context);
                if (mimeType.contains(TYPE_IMAGE))
                {
                    compressedPath = compressJpeg(context, selectedFileUri);
                } else
                {
                    compressedPath = EMPTY;
                }
                if (!TextUtils.isEmpty(compressedPath))
                {
                    //  numberPicStore.addOriginalCompressedPath(documentPath, compressedPath);
                    path = compressedPath;
                } else
                {
                    path = documentPath;
                }
                originalCompressedPair = new Pair<>(documentPath, path);
            }
            else
            {
                originalCompressedPair = new Pair<>(documentPath, null);
            }
            }else
        {
            originalCompressedPair = null;
        }
        return originalCompressedPair;
    }

    @Nullable
    private static String getCapturedImagePath(Context context, Uri uri) throws IOException
    {
        Cursor cursor = null;
        try
        {
            cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor == null)
            {
                return null;
            } else
            {
                cursor.moveToFirst();
                String documentId = cursor.getString(0);
                documentId = documentId.substring(documentId.lastIndexOf(COLON) + 1);
                cursor.close();
                cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, BaseColumns._ID + " = ? ", new String[]{documentId}, null);
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                    return cursor.getString(columnIndex);
                } else
                {
                    return null;
                }
            }
        } catch (CursorIndexOutOfBoundsException | NullPointerException ignored)
        {
            return null;
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            } else
            {
                // do nothing
            }
        }
    }

    private static Pair<String, String> onPhotoCaptured(Activity activity, Intent intent) throws IOException
    {
        setCapturedUri(intent, activity);
        String imagePath = getCapturedImagePath(activity, capturedFileUri);
        String path = EMPTY;
        if (capturedFileUri != null)
        {
            String compressedPath = compressJpeg(activity, capturedFileUri);
            if (!TextUtils.isEmpty(compressedPath))
            {
                path = compressedPath;
            } else
            {
                path = capturedFileUri.getPath();
            }
        } else
        {
            // do nothing
        }
        return new Pair<>(imagePath, path);
    }

    private static void setCapturedUri(Intent intent, Activity activity)
    {
        if (intent != null && intent.getData() != null)
        {
            capturedFileUri = intent.getData();
        } else if (capturedFileUri == null)
        {
            Callback numberPicHandlerCallback = getCallback(activity);
            capturedFileUri = Uri.parse(numberPicHandlerCallback.getCapturedUri());
        }
    }


    @Nullable
    private static String getDocumentPath(Context context, final Uri uri) throws IOException
    {
        if (OSVersionUtils.isKitkatAndAbove() && DocumentsContract.isDocumentUri(context, uri))
        {
            if (isLocalStorageDocument(uri))
            {
                return DocumentsContract.getDocumentId(uri);
            } else if (isExternalStorageDocument(uri))
            {
                return getExternalStorageDocumentPath(uri);
            } else if (isDownloadsDocument(uri))
            {
                return getDownloadDocumentPath(context, uri);
            } else if (isMediaDocument(uri))
            {
                return getMediaDocumentPath(context, uri);
            } else
            {
                return getDownloadedPath(context, uri);
            }
        } else if (isContentUri(uri))
        {
            if (isGooglePhotosUri(uri))
            {
                return uri.getLastPathSegment();
            } else
            {
                String localPath = getDataColumn(context, uri, null, null);
                if (!TextUtils.isEmpty(localPath))
                {
                    return localPath;
                } else
                {
                    return getDownloadedPath(context, uri);
                }
            }
        } else if (isFileUri(uri))
        {
            return uri.getPath();
        } else
        {
            return getDownloadedPath(context, uri);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Nullable
    private static String getMediaDocumentPath(Context context, Uri uri)
    {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(COLON);
        String type = split[0];
        Uri contentUri;
        if (TYPE_IMAGE.equals(type))
        {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String selection = BaseColumns._ID + "=?";
            String[] selectionArgs = new String[]{split[1]};
            return getDataColumn(context, contentUri, selection, selectionArgs);
        } else
        {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Nullable
    private static String getExternalStorageDocumentPath(Uri uri)
    {
        String docId = DocumentsContract.getDocumentId(uri);
        String[] split = docId.split(COLON);
        String type = split[0];

        if (PRIMARY.equalsIgnoreCase(type))
        {
            return Environment.getExternalStorageDirectory() + SLASH_DELIMITER + split[1];
        } else
        {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Nullable
    private static String getDownloadDocumentPath(Context context, Uri uri) throws IOException
    {
        if (isAboveNoughat())
        {
            String mimeType = getMimeType(uri, context);
            if (mimeType != null && mimeType.contains(TYPE_IMAGE))
            {
                return uri.getPath();
            } else
            {
                return getDownloadedPath(context, uri);
            }
        } else
        {
            String id = DocumentsContract.getDocumentId(uri);
            Uri contentUri = ContentUris.withAppendedId(Uri.parse(CONTENT_DOWNLOADS_PUBLIC_DOWNLOADS), Long.parseLong(id));

            return getDataColumn(context, contentUri, null, null);
        }
    }

    @Nullable
    private static String getDownloadedPath(Context context, Uri uri) throws IOException
    {
        InputStream input = null;
        OutputStream output = null;
        File file = getFile(context, uri);

        try
        {
            input = context.getContentResolver().openInputStream(uri);
            output = new FileOutputStream(file);

            byte[] data = new byte[BYTES_LENGTH];
            int count;
            while ((count = input.read(data)) != -1)
            {
                output.write(data, 0, count);
            }
        } finally
        {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        }
        if (file != null)
        {
            return file.getAbsolutePath();
        } else
        {
            return null;
        }
    }

    @Nullable
    private static File getFile(Context context, Uri uri)
    {
        String mimeType = getMimeType(uri, context);
        if (!TextUtils.isEmpty(mimeType))
        {
            if (mimeType.contains(TYPE_IMAGE))
            {
                return createExternalStorageDownloads(context);
            } else if (mimeType.equalsIgnoreCase(TYPE_PDF))
            {
                return createExternalStoragePublicDocument();
            } else
            {
                return null;
            }

        } else
        {
            return null;
        }

    }

    private static boolean isExternalStorageDocument(Uri uri)
    {
        return COM_ANDROID_EXTERNALSTORAGE_DOCUMENTS.equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri)
    {
        return COM_ANDROID_PROVIDERS_DOWNLOADS_DOCUMENTS.equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri)
    {
        return COM_ANDROID_PROVIDERS_MEDIA_DOCUMENTS.equals(uri.getAuthority());
    }

    private static boolean isLocalStorageDocument(Uri uri)
    {
        return COM_IANHANNIBALLAKE_LOCALSTORAGE_DOCUMENTS.equals(uri.getAuthority());
    }

    private static boolean isContentUri(Uri uri)
    {
        return CONTENT.equalsIgnoreCase(uri.getScheme());
    }

    private static boolean isFileUri(Uri uri)
    {
        return FILE.equalsIgnoreCase(uri.getScheme());
    }

    private static boolean isGooglePhotosUri(Uri uri)
    {
        return COM_GOOGLE_ANDROID_APPS_PHOTOS_CONTENTPROVIDER.equals(uri.getAuthority());
    }

    @Nullable
    private static String getDataColumn(Context context, Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        Cursor cursor = null;
        try
        {
            cursor = context.getContentResolver().query(uri, new String[]{COLUMN_DATA}, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst())
            {

                final int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATA);
                return cursor.getString(columnIndex);
            }
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return null;
    }

    @Nullable
    private static String compressJpeg(Context context, Uri selectedFileUri) throws IOException
    {
        Bitmap selectedImage = BitmapFileLoader.decodeFileFromUri(context, selectedFileUri, MAX_WIDTH, MAX_HEIGHT);
        File compressedRx = createExternalStoragePublicPicture(context);
        if (selectedImage != null && compressedRx != null)
        {
            FileOutputStream out = new FileOutputStream(compressedRx);
            selectedImage.compress(Bitmap.CompressFormat.JPEG, QUALITY, out);
            out.close();
            return compressedRx.getPath();
        } else
        {
            return null;
        }
    }

    private static boolean isFileChoosenFromWaysappFolder(String imagePath)
    {
        String fileName = imagePath.substring(imagePath.lastIndexOf(FORWARD_SLASH) + 1);
        if (TextUtils.isEmpty(fileName))
        {
            return false;
        } else
        {
            return fileName.startsWith(PREFIX);
        }
    }

    private static File createExternalStorageDownloads(Context context)
    {
        File directory = Environment.getExternalStoragePublicDirectory(new StringBuilder(2).append(Environment.DIRECTORY_DOWNLOADS).toString());
        directory.mkdirs();
        File file = new File(directory, new StringBuilder(3).append(DOWNLOAD_).append(String.valueOf(System.currentTimeMillis())).append(JPG).toString());

        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                (path, uri) -> Log.i(EXTERNAL_STORAGE, new StringBuilder(3).append(SCANNED).append(path).append(COLON).append(uri).toString()));
        return file;
    }

    private static File createExternalStoragePublicPicture(Context context)
    {
        File directory = Environment.getExternalStoragePublicDirectory(new StringBuilder(3).append(Environment.DIRECTORY_PICTURES).append(File.separator).append(numberPic_DIR_NAME).toString());
        directory.mkdirs();
        File file = new File(directory, new StringBuilder(3).append(PREFIX).append(System.currentTimeMillis()).append(JPG).toString());

        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                (path, uri) -> Log.i(EXTERNAL_STORAGE, new StringBuilder(3).append(SCANNED).append(path).append(COLON).append(uri).toString()));
        return file;
    }

    private static File createExternalStoragePublicDocument()
    {
        File directory = Environment.getExternalStoragePublicDirectory(new StringBuilder(3).append(Environment.DIRECTORY_DOWNLOADS).append(File.separator).append(numberPic_DIR_NAME).toString());
        directory.mkdirs();
        return new File(directory, new StringBuilder(3).append(PREFIX).append(String.valueOf(System.currentTimeMillis())).append(PDF).toString());
    }

    private static Callback getCallback(Activity activity)
    {
        Callback numberPicHandlerCallback = null;
        try
        {
            numberPicHandlerCallback = (Callback) activity;
        } catch (ClassCastException e)
        {
            // do nothing
        }
        return numberPicHandlerCallback;
    }

    @Nullable
    private static String getMimeType(Uri uri, Context context)
    {
        String mimeType;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT))
        {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else
        {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public interface Callback
    {
        String getCapturedUri();

        void setCapturedUri(String uri);

        void onImageCaptured(Pair<String, String> originalCompressedPair);
    }
}

