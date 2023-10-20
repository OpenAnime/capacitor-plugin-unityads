import { Plugin, PluginListenerHandle } from '@capacitor/core';

interface Error {
  error: string
}

export interface IListeners {
    initialized: [];
    initializationError: [error: Error];
    adLoaded: [];
    adLoadError: [error: Error];
    adShowError: [error: Error];
    adShowStart: [];
    adShowClick: [];
    adShown: [];
}

interface IUnityAdsPlugin extends Plugin {
  addListener<E extends keyof IListeners>(eventName: E, listenerFunc: (...args: IListeners[E]) => any): Promise<PluginListenerHandle>
}

export interface UnityAdsWeb extends IUnityAdsPlugin {
  initAds(options: {
    unityGameId: string
    testMode: boolean,
  }): void;
  loadAds(options: {
    adUnitId: string,
  }): void
  displayAd(): void
}
