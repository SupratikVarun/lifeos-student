import {
    FaHome,
    FaBook,
    FaStickyNote,
    FaTasks,
    FaCalendarAlt,
    FaFilePdf,
    FaSearch,
} from "react-icons/fa";

import { Link, useLocation } from "react-router-dom";

export default function Sidebar() {

    const location = useLocation();

    const menus = [

        { icon: <FaHome />, text: "Dashboard", path: "/dashboard" },
        { icon: <FaBook />, text: "Subjects", path: "/subjects" },
        { icon: <FaStickyNote />, text: "Notes", path: "/notes" },
        { icon: <FaTasks />, text: "Tasks", path: "/tasks" },
        { icon: <FaCalendarAlt />, text: "Calendar", path: "/calendar" },
        { icon: <FaFilePdf />, text: "Documents", path: "/documents" },
        { icon: <FaSearch />, text: "Smart Search", path: "/search" },

    ];

    return (

        <div className="w-72 min-h-screen bg-slate-900 text-white shadow-xl">

            <div className="p-8 border-b border-slate-700">

                <h1 className="text-3xl font-bold text-cyan-400">
                    LifeOS
                </h1>

                <p className="text-slate-400 text-sm mt-2">
                    Student Edition
                </p>

            </div>

            <div className="mt-6 px-4">

                {menus.map((menu, index) => (

                    <Link
                        key={index}
                        to={menu.path}
                        className={`w-full flex items-center gap-4 p-4 rounded-xl transition-all duration-300 mb-2 ${
                            location.pathname === menu.path
                                ? "bg-cyan-500 text-white"
                                : "hover:bg-cyan-500 hover:text-white"
                        }`}
                    >

                        <span className="text-xl">
                            {menu.icon}
                        </span>

                        <span className="font-medium">
                            {menu.text}
                        </span>

                    </Link>

                ))}

            </div>

        </div>

    );

}