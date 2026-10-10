import { useState } from "react";
import {
  FaPlus,
  FaStickyNote,
  FaSearch,
  FaEdit,
  FaTrash,
  FaBook,
} from "react-icons/fa";

export default function Notes() {

  const [search, setSearch] = useState("");

  const notes = [
    {
      id: 1,
      title: "OSI Model",
      subject: "Computer Networks",
      updated: "Today"
    },
    {
      id: 2,
      title: "Normalization",
      subject: "DBMS",
      updated: "Yesterday"
    },
    {
      id: 3,
      title: "Process Scheduling",
      subject: "Operating Systems",
      updated: "2 Days Ago"
    },
    {
      id: 4,
      title: "Machine Learning Basics",
      subject: "Artificial Intelligence",
      updated: "Last Week"
    }
  ];

  const filteredNotes = notes.filter(
    note =>
      note.title.toLowerCase().includes(search.toLowerCase()) ||
      note.subject.toLowerCase().includes(search.toLowerCase())
  );

  return (

    <div className="min-h-screen bg-slate-950 text-white p-8">

      <div className="flex justify-between items-center mb-8">

        <div>

          <h1 className="text-4xl font-bold">
            Notes 📝
          </h1>

          <p className="text-gray-400 mt-2">
            Organize and manage your study notes.
          </p>

        </div>

        <button className="bg-cyan-500 hover:bg-cyan-600 px-5 py-3 rounded-lg flex items-center gap-2 transition">

          <FaPlus />

          Create Note

        </button>

      </div>

      <div className="relative mb-8">

        <FaSearch className="absolute left-4 top-4 text-gray-400" />

        <input
          type="text"
          placeholder="Search notes..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full bg-slate-800 border border-slate-700 rounded-lg py-3 pl-12 pr-4 outline-none focus:border-cyan-400"
        />

      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">

        {filteredNotes.map((note) => (

          <div
            key={note.id}
            className="bg-slate-900 rounded-xl p-6 border border-slate-800 hover:border-cyan-500 hover:shadow-xl transition duration-300"
          >

            <div className="flex justify-between">

              <FaStickyNote className="text-cyan-400 text-3xl" />

              <div className="flex gap-4">

                <button className="text-cyan-400 hover:text-cyan-300">

                  <FaEdit />

                </button>

                <button className="text-red-400 hover:text-red-300">

                  <FaTrash />

                </button>

              </div>

            </div>

            <h2 className="text-2xl font-bold mt-6">

              {note.title}

            </h2>

            <div className="flex items-center gap-2 mt-4 text-gray-400">

              <FaBook />

              {note.subject}

            </div>

            <p className="mt-5 text-sm text-gray-500">

              Last Updated : {note.updated}

            </p>

          </div>

        ))}

      </div>

      {filteredNotes.length === 0 && (

        <div className="text-center mt-20">

          <FaStickyNote className="text-6xl mx-auto text-gray-600" />

          <h2 className="text-2xl mt-6">

            No Notes Found

          </h2>

          <p className="text-gray-400 mt-2">

            Create your first note.

          </p>

        </div>

      )}

    </div>

  );

}