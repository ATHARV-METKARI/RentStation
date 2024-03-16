import { create } from 'zustand';

interface User {
  id: string;
  mobileNumber: string;
  role: string;
}

interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  accessToken: string | null;
  setAuth: (user: User, token: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  isAuthenticated: false,
  user: null,
  accessToken: null,
  setAuth: (user, token) => set({ isAuthenticated: true, user, accessToken: token }),
  logout: () => set({ isAuthenticated: false, user: null, accessToken: null })
}));
