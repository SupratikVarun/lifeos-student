import {
  FaBook,
  FaStickyNote,
  FaTasks,
  FaFilePdf,
} from "react-icons/fa";

export default function Dashboard() {

    const cards = [
        {
            title: "Subjects",
            count: 6,
            icon: <FaBook className="text-3xl text-cyan-400" />
        },
        {
            title: "Notes",
            count: 18,
            icon: <FaStickyNote className="text-3xl text-cyan-400" />
        },
        {
            title: "Tasks",
            count: 4,
            icon: <FaTasks className="text-3xl text-cyan-400" />
        },
        {
            title: "Documents",
            count: 12,
            icon: <FaFilePdf className="text-3xl text-cyan-400" />
        }
    ];

    return (

        <div className="bg-slate-950 min-h-screen w-full px-10 py-8">

           <div className="mb-8">

    <h2 className="text-4xl font-bold text-white">
        Welcome Back 👋
    </h2>

    <p className="text-slate-400 mt-2 text-lg">
        Your AI-powered learning assistant
    </p>

</div>

            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-8">

                {cards.map((card, index) => (

                    <div
                        key={index}
                        className="bg-slate-900 rounded-2xl p-6 shadow-lg hover:shadow-cyan-500/20 hover:scale-105 transition duration-300"
                    >

                        <div className="flex justify-between items-center">

                            <h2 className="text-cyan-400 text-xl font-semibold">
                                {card.title}
                            </h2>

                            {card.icon}

                        </div>

                        <h1 className="text-5xl font-bold text-white mt-8">
                            {card.count}
                        </h1>

                    </div>

                ))}

            </div>

        </div>

    );

}