import { create } from 'zustand';

interface AppState {
  // Placeholder store
  isInitialized: boolean;
}

export const useAppStore = create<AppState>((set) => ({
  isInitialized: false,
}));
