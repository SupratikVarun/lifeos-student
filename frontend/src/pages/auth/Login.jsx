import { useState } from "react";
import { login } from "../../services/authService";
import { useAuth } from "../../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";


const Login = () => {

    const navigate = useNavigate();
    const { login: saveToken } = useAuth();

    const [form, setForm] = useState({
        collegeEmail: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {

        e.preventDefault();

        setLoading(true);
        setError("");

        try {

            const response = await login(form);

            saveToken(response.data.token);

            navigate("/dashboard");

        } catch (err) {

            setError(
                err.response?.data?.message || "Login Failed"
            );

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="min-h-screen bg-slate-950 flex items-center justify-center">

            <div className="w-full max-w-md bg-slate-900 rounded-2xl shadow-2xl p-8">

                <h1 className="text-4xl font-bold text-cyan-400 text-center">
                    LifeOS Student
                </h1>

                <p className="text-gray-400 text-center mt-2">
                    Smart AI Learning Platform
                </p>

                <form
                    onSubmit={handleSubmit}
                    className="mt-8 space-y-5"
                >

                    <input
                        type="email"
                        name="collegeEmail"
                        placeholder="College Email"
                        value={form.collegeEmail}
                        onChange={handleChange}
                        className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
                    />

                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={form.password}
                        onChange={handleChange}
                        className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
                    />

                    {
                        error &&
                        <p className="text-red-500 text-sm">
                            {error}
                        </p>
                    }

                    <button
                        className="w-full bg-cyan-500 hover:bg-cyan-600 transition p-3 rounded-lg text-white font-semibold"
                    >

                        {
                            loading
                                ? "Signing In..."
                                : "Login"
                        }

                    </button>

                </form>

                <p className="text-center text-gray-400 mt-6">

                    Don't have an account?

                    <Link
                        to="/register"
                        className="text-cyan-400 ml-2"
                    >
                        Register
                    </Link>

                </p>

            </div>

        </div>

    );

};

export default Login;