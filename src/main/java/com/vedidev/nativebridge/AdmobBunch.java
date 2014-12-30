package com.vedidev.nativebridge;

import com.google.android.gms.ads.*;
import android.content.Context;
import android.widget.LinearLayout;

import org.json.JSONObject;
import com.vedidev.nativebridge.Bunch;
import com.vedidev.nativebridge.ProcessorEngine;

/**
 * @author smallkot
 *         date 19/12/14
 */

@SuppressWarnings("UnusedDeclaration")
public class AdmobBunch implements Bunch {
    private Context context;
    private AdView adView;
    private InterstitialAd interstitial;

    public AdmobBunch() {
        registerProcessor("createBanner", new ProcessorEngine.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                String adUnitID = params.getString("adUnitID");
                int adSizeBanner = params.getInt("adSizeBanner");
                double mX = params.getDouble("mX");
                double mY = params.getDouble("mY");
                createBanner(adUnitID);
            }
        });

        registerProcessor("showBanner", new ProcessorEngine.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                showBanner();
            }
        });

        registerProcessor("createInterstitial", new ProcessorEngine.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                String adUnitID = params.getString("adUnitID");
                createInterstitial(adUnitID);
            }
        });

        registerProcessor("showInterstitial", new ProcessorEngine.CallHandler() {
            @Override
            public void handle(JSONObject params, JSONObject retParams) throws Exception {
                showInterstitial();
            }
        });

    }

    private void createBanner(String adUnitID) {
        // Создание экземпляра adView.
        adView = new AdView(this.context);
        adView.setAdUnitId(adUnitID);
        adView.setAdSize(AdSize.BANNER);

        // Поиск разметки LinearLayout (предполагается, что ей был присвоен
        // атрибут android:id="@+id/mainLayout").
        LinearLayout layout = new LinearLayout(this.context);//(LinearLayout)findViewById(R.id.mainLayout);

        // Добавление в разметку экземпляра adView.
        layout.addView(adView);
    }

    private void showBanner() {
        // Инициирование общего запроса.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Загрузка adView с объявлением.
        adView.loadAd(adRequest);
    }

    private void createInterstitial(String adUnitID) {
        // Создание межстраничного объявления.
        interstitial = new InterstitialAd(this.context);
        interstitial.setAdUnitId(adUnitID);

        // Создание запроса объявления.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Запуск загрузки межстраничного объявления.
        if(adRequest!=null && interstitial !=null)
            interstitial.loadAd(adRequest);
    }

    private void showInterstitial() {
        // Загрузка adView с объявлением.
        displayInterstitial();
    }

    // Вызовите displayInterstitial(), когда будете готовы показать межстраничное объявление.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    private void registerProcessor(String key, ProcessorEngine.CallHandler callHandler) {
        ProcessorEngine.getInstance().registerProcessor("AdmobBunch", key, callHandler);
    }
}

