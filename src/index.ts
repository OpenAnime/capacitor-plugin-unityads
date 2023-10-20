import { registerPlugin } from '@capacitor/core';

import type { UnityAdsWeb } from './definitions';

const UnityAds = registerPlugin<UnityAdsWeb>('UnityAds', {
  web: () => import('./web').then(m => new m.UnityAdsWeb()),
});

export * from './definitions';
export { UnityAds };
