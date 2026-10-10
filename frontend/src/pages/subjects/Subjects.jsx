import { useState } from "react";
import {
  FaPlus,
  FaBook,
  FaSearch,
  FaUserGraduate,
  FaEdit,
  FaTrash,
} from "react-icons/fa";

export default function Subjects() {
  const [search, setSearch] = useState("");

  const subjects = [
    {
      id: 1,
      name: "Database Management Systems",
      code: "CS301",
      faculty: "Dr. Ramesh",
      semester: "Semester 5",
    },
    {
      id: 2,
      name: "Operating Systems",
      code: "CS302",
      faculty: "Dr. Priya",
      semester: "Semester 5",
    },
    {
      id: 3,
      name: "Computer Networks",
      code: "CS303",
      faculty: "Dr. Kumar",
      semester: "Semester 5",
    },
    {
      id: 4,
      name: "Artificial Intelligence",
      code: "CS304",
      faculty: "Dr. Sharma",
      semester: "Semester 5",
    },
  ];

  const filteredSubjects = subjects.filter(
    (subject) =>
      subject.name.toLowerCase().includes(search.toLowerCase()) ||
      subject.code.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="min-h-screen bg-slate-950 text-white p-8">

      {/* Header */}

      <div className="flex justify-between items-center mb-8">

        <div>
          <h1 className="text-4xl font-bold">Subjects 📚</h1>
          <p className="text-gray-400 mt-2">
            Manage all your academic subjects.
          </p>
        </div>

        <button className="flex items-center gap-2 bg-cyan-500 hover:bg-cyan-600 transition px-5 py-3 rounded-lg font-semibold">
          <FaPlus />
          Add Subject
        </button>

      </div>

      {/* Search */}

      <div className="relative mb-8">

        <FaSearch className="absolute left-4 top-4 text-gray-400" />

        <input
          type="text"
          placeholder="Search subjects..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full bg-slate-800 rounded-lg py-3 pl-12 pr-4 outline-none border border-slate-700 focus:border-cyan-400"
        />

      </div>

      {/* Cards */}

      <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">

        {filteredSubjects.map((subject) => (

          <div
            key={subject.id}
            className="bg-slate-900 rounded-xl p-6 border border-slate-800 hover:border-cyan-500 transition-all duration-300 hover:shadow-xl hover:scale-[1.02]"
          >

            <div className="flex justify-between items-start">

              <FaBook className="text-cyan-400 text-3xl" />

              <div className="flex gap-3">

                <button className="text-cyan-400 hover:text-cyan-300">
                  <FaEdit />
                </button>

                <button className="text-red-400 hover:text-red-300">
                  <FaTrash />
                </button>

              </div>

            </div>

            <h2 className="text-2xl font-bold mt-6">
              {subject.name}
            </h2>

            <p className="text-gray-400 mt-2">
              {subject.code}
            </p>

            <div className="mt-6 space-y-2">

              <div className="flex items-center gap-2 text-gray-300">

                <FaUserGraduate />

                {subject.faculty}

              </div>

              <div className="text-gray-400">

                {subject.semester}

              </div>

            </div>

          </div>

        ))}

      </div>

      {filteredSubjects.length === 0 && (

        <div className="text-center mt-20 text-gray-400">

          <FaBook className="text-6xl mx-auto mb-5 opacity-30" />

          <h2 className="text-2xl font-semibold">
            No Subjects Found
          </h2>

          <p className="mt-2">
            Try searching with another keyword.
          </p>

        </div>

      )}

    </div>
  );
}