package vn.edu.usth.weather;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherAndForecastFragment extends Fragment {

    private static final String ARG_CITY_NAME = "city_name";

    public static WeatherAndForecastFragment newInstance(String cityName) {
        WeatherAndForecastFragment fragment = new WeatherAndForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_and_forecast, container, false);

        String cityName = getArguments() != null ? getArguments().getString(ARG_CITY_NAME) : "Unknown City";

        WeatherFragment weatherFragment = WeatherFragment.newInstance(cityName);
        ForecastFragment forecastFragment = new ForecastFragment();

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.weather_fragment_container, weatherFragment);
        transaction.replace(R.id.forecast_fragment_container, forecastFragment);
        transaction.commit();

        return view;
    }
}

