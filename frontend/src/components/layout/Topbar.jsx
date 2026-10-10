import {
    FaBell,
    FaMoon,
    FaUserCircle
} from "react-icons/fa";

export default function Topbar() {

    return (

        <div className="h-20 bg-slate-900 border-b border-slate-700 flex items-center justify-between px-8">

            <h1 className="text-3xl font-bold text-white">
                Dashboard
            </h1>

            <div className="flex items-center gap-6 text-white">

                <FaBell
                    size={22}
                    className="cursor-pointer hover:text-cyan-400"
                />

                <FaMoon
                    size={22}
                    className="cursor-pointer hover:text-cyan-400"
                />

                <FaUserCircle
                    size={34}
                    className="cursor-pointer hover:text-cyan-400"
                />

            </div>

        </div>

    );

}