package vn.edu.usth.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastFragment extends Fragment {

    private ImageView usthLogoImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout parentLayout = new LinearLayout(getActivity());
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.setPadding(20, 20, 20, 20);
        parentLayout.setBackgroundColor(Color.argb(128, 0, 0, 255));

        LinearLayout titleLayout = new LinearLayout(getActivity());
        titleLayout.setOrientation(LinearLayout.HORIZONTAL);
        titleLayout.setPadding(0, 0, 0, 40);

        ImageView titleIcon = new ImageView(getActivity());
        titleIcon.setImageResource(R.drawable.meteorology);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(150, 150);
        titleIcon.setLayoutParams(iconParams);

        TextView titleTextView = new TextView(getActivity());
        titleTextView.setText("Weather Forecast for this Week");
        titleTextView.setTextSize(24);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setPadding(20, 0, 0, 0);

        titleLayout.addView(titleIcon);
        titleLayout.addView(titleTextView);

        parentLayout.addView(titleLayout);

        String[] days = {"Monday-Sunny", "Tuesday-Cloudy", "Wednesday-Rainy", "Thursday-Sunny", "Friday-Stormy", "Saturday-Windy", "Sunday-Cloudy"};
        int[] icons = {R.drawable.sun, R.drawable.cloudy, R.drawable.rainy, R.drawable.sun, R.drawable.storm, R.drawable.wind, R.drawable.cloudy};
        int[] temperatures = {30, 27, 29, 31, 28, 27, 29};

        for (int i = 0; i < days.length; i++) {
            LinearLayout horizontalLayout = new LinearLayout(getActivity());
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setPadding(0, 20, 0, 20);

            ImageView weatherIcon = new ImageView(getActivity());
            weatherIcon.setImageResource(icons[i]);
            LinearLayout.LayoutParams weatherIconParams = new LinearLayout.LayoutParams(100, 100);
            weatherIcon.setLayoutParams(weatherIconParams);

            TextView dayTextView = new TextView(getActivity());
            dayTextView.setText(days[i]);
            dayTextView.setTextSize(18);
            dayTextView.setTextColor(Color.BLACK);
            dayTextView.setPadding(20, 0, 20, 0);

            TextView tempTextView = new TextView(getActivity());
            tempTextView.setText(temperatures[i] + "°C");
            tempTextView.setTextSize(18);
            tempTextView.setTextColor(Color.BLACK);
            tempTextView.setPadding(20, 0, 20, 10);

            horizontalLayout.addView(weatherIcon);
            horizontalLayout.addView(dayTextView);
            horizontalLayout.addView(tempTextView);
            parentLayout.addView(horizontalLayout);
        }

        usthLogoImageView = new ImageView(getActivity());
        LinearLayout.LayoutParams logoParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
        usthLogoImageView.setLayoutParams(logoParams);
        usthLogoImageView.setPadding(0, 50, 0, 0);
        parentLayout.addView(usthLogoImageView);
        new DownloadImageTask().execute("http://ict.usth.edu.vn/wp-content/");

        return parentLayout;
    }

    // AsyncTask to download the image in the background
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                usthLogoImageView.setImageBitmap(bitmap);
            }
        }
    }
}
