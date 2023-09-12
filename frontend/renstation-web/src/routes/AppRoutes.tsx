import { Routes, Route } from 'react-router-dom';

// Placeholder route configuration
export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<div>Home</div>} />
      <Route path="/games" element={<div>Games</div>} />
      <Route path="/login" element={<div>Login</div>} />
      <Route path="/register" element={<div>Register</div>} />
      <Route path="/profile" element={<div>Profile</div>} />
      <Route path="/admin" element={<div>Admin</div>} />
      <Route path="*" element={<div>404 Not Found</div>} />
    </Routes>
  );
};
