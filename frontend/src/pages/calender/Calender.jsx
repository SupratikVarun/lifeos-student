import { useState } from "react";
import {
  FaCalendarAlt,
  FaPlus,
  FaSearch,
  FaClock,
  FaEdit,
  FaTrash
} from "react-icons/fa";

export default function Calender() {

  const [search, setSearch] = useState("");

  const events = [
    {
      id: 1,
      title: "DBMS Lab",
      date: "15 Oct 2026",
      time: "10:00 AM"
    },
    {
      id: 2,
      title: "AI Assignment Submission",
      date: "18 Oct 2026",
      time: "11:59 PM"
    },
    {
      id: 3,
      title: "Operating Systems Quiz",
      date: "20 Oct 2026",
      time: "09:30 AM"
    },
    {
      id: 4,
      title: "Mini Project Review",
      date: "24 Oct 2026",
      time: "02:00 PM"
    }
  ];

  const filteredEvents = events.filter(event =>
    event.title.toLowerCase().includes(search.toLowerCase())
  );

  return (

    <div className="min-h-screen bg-slate-950 text-white p-8">

      <div className="flex justify-between items-center mb-8">

        <div>

          <h1 className="text-4xl font-bold">
            Calendar 📅
          </h1>

          <p className="text-gray-400 mt-2">
            Manage your academic schedule.
          </p>

        </div>

        <button className="flex items-center gap-2 bg-cyan-500 hover:bg-cyan-600 px-5 py-3 rounded-lg transition">

          <FaPlus />

          Add Event

        </button>

      </div>

      <div className="relative mb-8">

        <FaSearch className="absolute left-4 top-4 text-gray-400" />

        <input
          type="text"
          placeholder="Search Events..."
          value={search}
          onChange={(e)=>setSearch(e.target.value)}
          className="w-full bg-slate-800 border border-slate-700 rounded-lg py-3 pl-12 pr-4 outline-none focus:border-cyan-400"
        />

      </div>

      <div className="space-y-5">

        {filteredEvents.map(event=>(

          <div
            key={event.id}
            className="bg-slate-900 rounded-xl border border-slate-800 hover:border-cyan-500 p-6 transition duration-300"
          >

            <div className="flex justify-between">

              <div>

                <h2 className="text-2xl font-bold">
                  {event.title}
                </h2>

                <div className="flex items-center gap-3 mt-4 text-gray-400">

                  <FaCalendarAlt />

                  {event.date}

                </div>

                <div className="flex items-center gap-3 mt-2 text-gray-400">

                  <FaClock />

                  {event.time}

                </div>

              </div>

              <div className="flex gap-5 text-xl">

                <button className="text-cyan-400 hover:text-cyan-300">

                  <FaEdit />

                </button>

                <button className="text-red-400 hover:text-red-300">

                  <FaTrash />

                </button>

              </div>

            </div>

          </div>

        ))}

      </div>

      {filteredEvents.length===0 &&(

        <div className="text-center mt-20">

          <FaCalendarAlt className="text-6xl text-gray-600 mx-auto"/>

          <h2 className="text-2xl mt-5">
            No Events Found
          </h2>

        </div>

      )}

    </div>

  );

}