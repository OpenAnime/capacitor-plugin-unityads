package com.paqu.unityads;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;

@CapacitorPlugin(name = "UnityAds")
public class UnityAdsPlugin extends Plugin {

    private String adUnitId;

    @PluginMethod
    public void initAds(PluginCall call) {
        IUnityAdsInitializationListener initListener = new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                notifyListeners("initialized", null);
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                JSObject ret = new JSObject();
                ret.put("error", message);
                notifyListeners("initializationError", ret);
            }
        };

        UnityAds.initialize(this.getActivity().getApplicationContext(), call.getString("unityGameId"), Boolean.TRUE.equals(call.getBoolean("testMode")), initListener);
        call.resolve();
    }

    @PluginMethod
    public void loadAds(PluginCall call) {
        IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                notifyListeners("adLoaded", null);
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                JSObject ret = new JSObject();
                ret.put("error", message);
                notifyListeners("adLoadError", ret);
            }
        };

        adUnitId = call.getString("adUnitId");

        UnityAds.load(adUnitId, loadListener);

        call.resolve();
    }

    @PluginMethod
    public void displayAd(PluginCall call) {
        IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                JSObject ret = new JSObject();
                ret.put("error", message);
                notifyListeners("adShowError", ret);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                notifyListeners("adShowStart", null);
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                notifyListeners("adShowClick", null);
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                JSObject ret = new JSObject();
                ret.put("state", state);
                notifyListeners("adShown", ret);
            }
        };

        UnityAds.show(getActivity(), adUnitId, showListener);
        call.resolve();
    }
}
