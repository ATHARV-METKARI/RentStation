import { Link } from 'react-router-dom';

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-4">
      <h1 className="text-5xl font-bold mb-4 bg-gradient-to-r from-brand-primary to-brand-accent bg-clip-text text-transparent">RenStation</h1>
      <p className="text-xl text-gray-400 mb-8">Peer to Peer Game Rentals</p>
      <div className="flex gap-4">
        <Link to="/login" className="px-6 py-2 bg-brand-primary rounded-lg font-medium hover:bg-brand-primary/80 transition-colors">Login / Register</Link>
      </div>
    </div>
  );
}
