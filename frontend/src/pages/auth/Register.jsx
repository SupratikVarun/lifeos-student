import { useState } from "react";
import { register } from "../../services/authService";
import { useNavigate, Link } from "react-router-dom";

const Register = () => {

    const navigate = useNavigate();

    const [form, setForm] = useState({
    fullName: "",
    collegeEmail: "",
    hallTicketNumber: "",
    college: "",
    branch: "",
    year: "",
    password: "",
    confirmPassword: ""
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

        if (form.password !== form.confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        setLoading(true);
        setError("");

        try {

            await register({
    fullName: form.fullName,
    collegeEmail: form.collegeEmail,
    hallTicketNumber: form.hallTicketNumber,
    college: form.college,
    branch: form.branch,
    year: Number(form.year),
    password: form.password
});

            alert("Registration Successful");

            navigate("/");

        } catch (err) {

            setError(
                err.response?.data?.message || "Registration Failed"
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
                    Create your account
                </p>

                <form
                    onSubmit={handleSubmit}
                    className="mt-8 space-y-5"
                >

                    <input
                        type="text"
                        name="fullName"
                        placeholder="Full Name"
                        value={form.fullName}
                        onChange={handleChange}
                        className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
                    />

                    <input
                        type="email"
                        name="collegeEmail"
                        placeholder="College Email"
                        value={form.collegeEmail}
                        onChange={handleChange}
                        className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
                    />

                    <input
                        type="text"
                        name="hallTicketNumber"
                        placeholder="Hall Ticket Number"
                        value={form.hallTicketNumber}
                        onChange={handleChange}
                        className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
                    />

                    <input
    type="text"
    name="college"
    placeholder="College Name"
    value={form.college}
    onChange={handleChange}
    className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
/>

<input
    type="text"
    name="branch"
    placeholder="Branch"
    value={form.branch}
    onChange={handleChange}
    className="w-full p-3 rounded-lg bg-slate-800 text-white outline-none"
/>

<input
    type="number"
    name="year"
    placeholder="Year"
    min="1"
    max="4"
    value={form.year}
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

                    <input
                        type="password"
                        name="confirmPassword"
                        placeholder="Confirm Password"
                        value={form.confirmPassword}
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
                                ? "Creating Account..."
                                : "Register"
                        }
                    </button>

                </form>

                <p className="text-center text-gray-400 mt-6">

                    Already have an account?

                    <Link
                        to="/"
                        className="text-cyan-400 ml-2"
                    >
                        Login
                    </Link>

                </p>

            </div>

        </div>

    );

};

export default Register;