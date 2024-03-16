import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/useAuthStore';
import api from '../services/api';

export default function Login() {
  const [mobile, setMobile] = useState('');
  const [otp, setOtp] = useState('');
  const [step, setStep] = useState(1);
  const navigate = useNavigate();
  const { setAuth } = useAuthStore();

  const handleSendOtp = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/auth/send-otp', { mobileNumber: mobile });
      setStep(2);
    } catch (err) {
      alert("Failed to send OTP");
    }
  };

  const handleVerify = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await api.post('/auth/verify-otp', { mobileNumber: mobile, otp });
      // In a real app, parse JWT to get user ID and Role
      setAuth({ id: '123', mobileNumber: mobile, role: 'CLIENT' }, res.data.data.accessToken);
      navigate('/dashboard');
    } catch (err) {
      alert("Invalid OTP");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen p-4">
      <div className="bg-gray-800 p-8 rounded-xl shadow-2xl w-full max-w-md border border-gray-700">
        <h2 className="text-2xl font-bold mb-6 text-center">Welcome Back</h2>
        {step === 1 ? (
          <form onSubmit={handleSendOtp} className="space-y-4">
            <div>
              <label className="block text-sm text-gray-400 mb-1">Mobile Number</label>
              <input type="text" value={mobile} onChange={e => setMobile(e.target.value)} 
                className="w-full px-4 py-2 bg-gray-900 border border-gray-700 rounded-lg focus:outline-none focus:border-brand-primary" 
                placeholder="+1234567890" required />
            </div>
            <button type="submit" className="w-full py-2 bg-brand-primary rounded-lg font-medium hover:bg-brand-primary/80">Send OTP</button>
          </form>
        ) : (
          <form onSubmit={handleVerify} className="space-y-4">
            <div>
              <label className="block text-sm text-gray-400 mb-1">Enter OTP</label>
              <input type="text" value={otp} onChange={e => setOtp(e.target.value)} 
                className="w-full px-4 py-2 bg-gray-900 border border-gray-700 rounded-lg focus:outline-none focus:border-brand-accent" 
                placeholder="123456" required />
            </div>
            <button type="submit" className="w-full py-2 bg-brand-accent rounded-lg font-medium hover:bg-brand-accent/80 text-gray-900">Verify & Login</button>
          </form>
        )}
      </div>
    </div>
  );
}
