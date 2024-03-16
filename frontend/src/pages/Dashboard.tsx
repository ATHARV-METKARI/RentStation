import { useAuthStore } from '../store/useAuthStore';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
  const { user, logout } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="p-8 max-w-6xl mx-auto">
      <header className="flex justify-between items-center mb-8 border-b border-gray-700 pb-4">
        <h1 className="text-3xl font-bold">Dashboard</h1>
        <div className="flex items-center gap-4">
          <span className="text-gray-400">Logged in as {user?.mobileNumber}</span>
          {user?.role === 'ADMIN' && (
            <button onClick={() => navigate('/admin')} className="px-4 py-2 bg-gray-800 rounded hover:bg-gray-700">Admin Panel</button>
          )}
          <button onClick={handleLogout} className="px-4 py-2 bg-red-500/20 text-red-400 rounded hover:bg-red-500/30">Logout</button>
        </div>
      </header>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-gray-800 p-6 rounded-xl border border-gray-700">
          <h2 className="text-xl font-bold mb-2 text-brand-primary">My Rentals</h2>
          <p className="text-gray-400">You have no active rentals.</p>
        </div>
        <div className="bg-gray-800 p-6 rounded-xl border border-gray-700">
          <h2 className="text-xl font-bold mb-2 text-brand-accent">My Listings</h2>
          <p className="text-gray-400">You are not renting out any games.</p>
        </div>
        <div className="bg-gray-800 p-6 rounded-xl border border-gray-700">
          <h2 className="text-xl font-bold mb-2">Wishlist</h2>
          <p className="text-gray-400">0 items saved.</p>
        </div>
      </div>
    </div>
  );
}
