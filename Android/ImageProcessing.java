// This is the MainActivity.java file from an image processing application written for my mobile applications class.
// A user is able capture images from the camera and apply several different image filters, varying from edge detection to colorizing
// Once user is happy with the results, they can save or share the image.
// Application was called "Cartoonator"

package cartoonator.coreymccown.com.cartoonator_cnm;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class MainActivity extends Activity {

    private final int CAMERA_REQUEST = 1873;
    private final int IMAGE_PICKER_SELECT = 1874;
    private File mostRecentFile;
    private Bitmap currentBitmap;   // store bitmap for orientation change
    private Bitmap cartoon;         // store cartoon version of BMP
    private ImageButton ib_TakePic;
    private Button btn_Save;
    private Button btn_PickPic;
    private Button btn_Cartoonize;
    private Button btn_ESepia;
    private Button btn_EBlackWhite;
    private Button btn_EVintage;
    private Button btn_SharePic;
    private ProgressBar bar;
    private TextView text_Header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_Header = (TextView) findViewById(R.id.txt_header);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
        ib_TakePic = (ImageButton) findViewById(R.id.imageButton);
        ib_TakePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePhoto();
            }
        });
        btn_Save = (Button) findViewById(R.id.btn_save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savePhoto(false);
            }
        });
        btn_PickPic = (Button) findViewById(R.id.btn_pick);
        btn_PickPic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pickPhoto();
            }
        });
        btn_Cartoonize = (Button) findViewById(R.id.btn_cartoonize);
        btn_Cartoonize.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new CartoonizeOperation().execute("");
            }
        });
        btn_ESepia = (Button) findViewById(R.id.btn_sepia);
        btn_ESepia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { addEffect(5,10.2,6.6,2.0);
            }
        });
        btn_EBlackWhite = (Button) findViewById(R.id.btn_bw);
        btn_EBlackWhite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { addEffect(5,1.2,1.2,1.2);
            }
        });
        btn_EVintage = (Button) findViewById(R.id.btn_vin);
        btn_EVintage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { addEffect(5,10.0,0.0,0.0);
            }
        });
        btn_SharePic = (Button) findViewById(R.id.btn_share);
        btn_SharePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { savePhoto(true);
            }
        });

        toggle_buttons(false);

        if (currentBitmap != null)
            setPicture(currentBitmap);
    }

    public void toggle_buttons(boolean hasPhoto)
    {
        if(hasPhoto)
        {
            btn_Cartoonize.setVisibility(View.VISIBLE);
            btn_Save.setVisibility(View.VISIBLE);
            btn_ESepia.setVisibility(View.VISIBLE);
            btn_EBlackWhite.setVisibility(View.VISIBLE);
            btn_EVintage.setVisibility(View.VISIBLE);
            btn_SharePic.setVisibility(View.VISIBLE);
            btn_Cartoonize.setText(getResources().getString(R.string.button_cartoonize));
            btn_PickPic.setText(getResources().getString(R.string.button_pick_new));
            text_Header.setVisibility(View.INVISIBLE);
        } else {
            btn_Cartoonize.setVisibility(View.INVISIBLE);
            btn_Save.setVisibility(View.INVISIBLE);
            btn_ESepia.setVisibility(View.INVISIBLE);
            btn_EBlackWhite.setVisibility(View.INVISIBLE);
            btn_EVintage.setVisibility(View.INVISIBLE);
            btn_SharePic.setVisibility(View.INVISIBLE);
            text_Header.setVisibility(View.VISIBLE);

            btn_PickPic.setText(getResources().getString(R.string.button_pick_idle));
        }
    }

    public void toggle_buttons_enabled(boolean on)
    {
        if(on){
            ib_TakePic.setEnabled(true);
            btn_Save.setEnabled(true);
            btn_PickPic.setEnabled(true);
            btn_Cartoonize.setEnabled(true);
            btn_ESepia.setEnabled(true);
            btn_EBlackWhite.setEnabled(true);
            btn_EVintage.setEnabled(true);
            btn_SharePic.setEnabled(true);
        } else {
            ib_TakePic.setEnabled(false);
            btn_Save.setEnabled(false);
            btn_PickPic.setEnabled(false);
            btn_Cartoonize.setEnabled(false);
            btn_ESepia.setEnabled(false);
            btn_EBlackWhite.setEnabled(false);
            btn_EVintage.setEnabled(false);
            btn_SharePic.setEnabled(false);
        }
    }
    private class CartoonizeOperation extends AsyncTask<String, Void, Void> {
        private Bitmap temp1;
        private Bitmap edges;
        @Override
        protected Void doInBackground(String... params) {
            temp1 = Bitmap.createScaledBitmap(currentBitmap,
                    (int) (720.0 * currentBitmap.getWidth() / currentBitmap.getHeight()), 720, true);

            edges = Bitmap.createScaledBitmap(currentBitmap,
                    (int) (720.0 * currentBitmap.getWidth() / currentBitmap.getHeight()), 720, true);

            //Broad Median Filter 1
            for (int x = 1; x < temp1.getWidth() - 1; x += 2) {
                for (int y = 1; y < temp1.getHeight() - 1; y += 2) {
                    int[] rpixels = new int[9];
                    int[] gpixels = new int[9];
                    int[] bpixels = new int[9];
                    // grab 9-pixel window
                    for (int r = -1; r <= 1; r++)
                        for (int c = -1; c <= 1; c++) {
                            rpixels[(r + 1) * 3 + (c + 1)] = Color.red(temp1.getPixel(x + c, y + r));
                            gpixels[(r + 1) * 3 + (c + 1)] = Color.green(temp1.getPixel(x + c, y + r));
                            bpixels[(r + 1) * 3 + (c + 1)] = Color.blue(temp1.getPixel(x + c, y + r));
                        }
                    Arrays.sort(rpixels);
                    Arrays.sort(gpixels);
                    Arrays.sort(bpixels);

                    // calculate median color
                    int RGB = Color.rgb(rpixels[4], gpixels[4], bpixels[4]);

                    // set four pixels to that median
                    temp1.setPixel(x, y, RGB);
                    temp1.setPixel(x - 1, y, RGB);
                    temp1.setPixel(x, y - 1, RGB);
                    temp1.setPixel(x - 1, y - 1, RGB);
                }
            }

            //Apply Saturation
            for (int x = 0; x < temp1.getWidth(); x++) {
                for (int y = 0; y < temp1.getHeight(); y++) {
                    float[] HSV = {0, 0, 0};
                    Color RGB;
                    int pixel = temp1.getPixel(x, y);
                    Color.colorToHSV(pixel, HSV);
                    if (HSV[1] > 0.33)
                        HSV[1] = 1.0f;
                    else
                        HSV[1] *= 3;

                    temp1.setPixel(x, y, Color.HSVToColor(HSV));
                }
            }

            //Broad Median Filter 2
            for (int x = 1; x < temp1.getWidth() - 1; x += 2) {
                for (int y = 1; y < temp1.getHeight() - 1; y += 2) {
                    int[] rpixels = new int[9];
                    int[] gpixels = new int[9];
                    int[] bpixels = new int[9];
                    // grab 9-pixel window
                    for (int r = -1; r <= 1; r++)
                        for (int c = -1; c <= 1; c++) {
                            rpixels[(r + 1) * 3 + (c + 1)] = Color.red(temp1.getPixel(x + c, y + r));
                            gpixels[(r + 1) * 3 + (c + 1)] = Color.green(temp1.getPixel(x + c, y + r));
                            bpixels[(r + 1) * 3 + (c + 1)] = Color.blue(temp1.getPixel(x + c, y + r));
                        }
                    Arrays.sort(rpixels);
                    Arrays.sort(gpixels);
                    Arrays.sort(bpixels);

                    // calculate median color
                    int RGB = Color.rgb(rpixels[4], gpixels[4], bpixels[4]);

                    // set four pixels to that median
                    temp1.setPixel(x, y, RGB);
                    temp1.setPixel(x - 1, y, RGB);
                    temp1.setPixel(x, y - 1, RGB);
                    temp1.setPixel(x - 1, y - 1, RGB);
                }
            }

            //Darken Edges
            for (int x = 1; x < edges.getWidth() - 1; x += 1) {
                for (int y = 1; y < edges.getHeight() - 1; y += 1) {
                    int[][] rpixels = new int[3][3];
                    int[][] gpixels = new int[3][3];
                    int[][] bpixels = new int[3][3];
                    // grab 9-pixel window
                    for (int r = -1; r <= 1; r++)
                        for (int c = -1; c <= 1; c++) {
                            rpixels[r+1][c+1] = Color.red(temp1.getPixel(x + c, y + r));
                            gpixels[r+1][c+1] = Color.green(temp1.getPixel(x + c, y + r));
                            bpixels[r+1][c+1] = Color.blue(temp1.getPixel(x + c, y + r));
                        }
                    int red = Math.abs(-rpixels[0][0]+rpixels[0][2]-2*rpixels[1][0]+2*rpixels[1][2]-rpixels[2][0]+rpixels[2][2])+
                            Math.abs(-rpixels[0][0]+rpixels[2][0]-2*rpixels[0][1]+2*rpixels[2][1]-rpixels[0][2]+rpixels[2][2]);
                    int green = Math.abs(-gpixels[0][0]+gpixels[0][2]-2*gpixels[1][0]+2*gpixels[1][2]-gpixels[2][0]+gpixels[2][2])+
                            Math.abs(-gpixels[0][0]+gpixels[2][0]-2*gpixels[0][1]+2*gpixels[2][1]-gpixels[0][2]+gpixels[2][2]);
                    int blue = Math.abs(-bpixels[0][0]+bpixels[0][2]-2*bpixels[1][0]+2*bpixels[1][2]-bpixels[2][0]+bpixels[2][2])+
                            Math.abs(-bpixels[0][0]+bpixels[2][0]-2*bpixels[0][1]+2*bpixels[2][1]-bpixels[0][2]+bpixels[2][2]);
                    int gray = Math.min(255,(3*red + 4*green + blue)/8);
                    int RGB = Color.rgb(255-gray,255-gray,255-gray);

                    if (gray > 175)
                        edges.setPixel(x, y, RGB);
                    else
                        edges.setPixel(x,y, temp1.getPixel(x,y));
                }

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn_Cartoonize.setText(getResources().getString(R.string.button_cartoonizing));
            toggle_buttons_enabled(false);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setPicture(edges);
            cartoon = edges;
            btn_Cartoonize.setText(getResources().getString(R.string.button_cartoonize_again));
            toggle_buttons_enabled(true);
            bar.setVisibility(View.GONE);
        }
    }

    //Neat image processing method to add color effects, etc.
    //Source: http://www.c-sharpcorner.com/UploadFile/e14021/adding-effects-to-image-in-android-using-android-studio/
    public void addEffect(int depth, double red, double green, double blue) {
        if (currentBitmap == null) {
            Toast toast = Toast.makeText(this, "Pick an image, or take a picture!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if(cartoon == null)
                cartoon = currentBitmap;

            int width = cartoon.getWidth();
            int height = cartoon.getHeight();

            Bitmap finalBitmap = Bitmap.createBitmap(width, height, cartoon.getConfig());

            final double grayScale_Red = 0.3;
            final double grayScale_Green = 0.59;
            final double grayScale_Blue = 0.11;

            int channel_alpha, channel_red, channel_green, channel_blue;
            int pixel;

            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {

                    pixel = cartoon.getPixel(x, y);
                    channel_alpha = Color.alpha(pixel);
                    channel_red = Color.red(pixel);
                    channel_green = Color.green(pixel);
                    channel_blue = Color.blue(pixel);

                    channel_blue = channel_green = channel_red = (int) (grayScale_Red * channel_red + grayScale_Green * channel_green + grayScale_Blue * channel_blue);

                    channel_red += (depth * red);
                    if (channel_red > 255) {
                        channel_red = 255;
                    }
                    channel_green += (depth * green);
                    if (channel_green > 255) {
                        channel_green = 255;
                    }
                    channel_blue += (depth * blue);
                    if (channel_blue > 255) {
                        channel_blue = 255;
                    }

                    finalBitmap.setPixel(x, y, Color.argb(channel_alpha, channel_red, channel_green, channel_blue));
                }
            }
            setPicture(finalBitmap);
            currentBitmap = finalBitmap;
            cartoon = finalBitmap;
        }
    }

    public void setPicture(Bitmap photo)
    {
        int bmpHeight = photo.getHeight();
        int bmpWidth = photo.getWidth();
        int ibHeight = ib_TakePic.getHeight();
        int ibWidth = ib_TakePic.getWidth();

        double ratio = (double)bmpHeight / bmpWidth;

        Bitmap scaledPhoto;

        if (bmpHeight > bmpWidth)
            scaledPhoto = Bitmap.createScaledBitmap(photo,(int) (ibHeight / ratio), ibHeight, true);
        else
            scaledPhoto = Bitmap.createScaledBitmap(photo, ibWidth, (int) (ibWidth * ratio), true);

        ib_TakePic.setImageBitmap(scaledPhoto);

    }

    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();
            mostRecentFile = photoFile;
        } catch (IOException ex) {
            Toast toast = Toast.makeText(this, "Error saving picture!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (photoFile != null) {
            Uri fileUri = Uri.fromFile(photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            toggle_buttons(true);
        }

    }
    protected File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = BitmapFactory.decodeFile(mostRecentFile.getPath());
                currentBitmap = photo;
                setPicture(photo);

            }
        }

        if(requestCode == IMAGE_PICKER_SELECT && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                setPicture(bitmap);
                currentBitmap = bitmap;
                toggle_buttons(true);
            } else {
                Toast toast = Toast.makeText(this, "Please Select a Valid Image.", Toast.LENGTH_SHORT);
                toast.show();
                pickPhoto();
            }

            cursor.close();
        }
    }

    public void pickPhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICKER_SELECT);
    }

    public void savePhoto(boolean share){
        Bitmap picture;
        if (cartoon == null)
            picture = currentBitmap;
        else
            picture = cartoon;

        if(picture != null)
        {
            if (Environment.getExternalStorageState().equals("mounted")){
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File picFile = new File(storageDir, imageFileName);
                FileOutputStream out = null;

                try{
                    out = new FileOutputStream(picFile);
                    picture.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.close();
                    Toast toast = Toast.makeText(this,"File Saved:"+picFile.getPath(), Toast.LENGTH_SHORT);
                    toast.show();
                    if(share){
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/jpg");
                        Uri uri = Uri.fromFile(picFile);
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Cartoonized Photo");
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Check out this photo I made in Corey's Cartoonizer!");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(Intent.createChooser(shareIntent, "Share image using"));
                    }
                }
                catch (IOException e)
                {
                    Toast toast = Toast.makeText(this, "Error saving file...", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } else {
                Toast toast = Toast.makeText(this, "No storage device...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
