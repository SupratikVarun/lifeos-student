import { useState } from "react";
import {
  FaPlus,
  FaSearch,
  FaTrash,
  FaEdit,
  FaCheckCircle,
  FaClock,
  FaExclamationCircle,
} from "react-icons/fa";

export default function Tasks() {
  const [search, setSearch] = useState("");

  const tasks = [
    {
      id: 1,
      title: "Complete DBMS Assignment",
      dueDate: "Oct 15",
      priority: "High",
      status: "Pending",
    },
    {
      id: 2,
      title: "Read CN Unit 3",
      dueDate: "Oct 17",
      priority: "Medium",
      status: "Completed",
    },
    {
      id: 3,
      title: "Create AI Flashcards",
      dueDate: "Oct 20",
      priority: "Low",
      status: "Pending",
    },
    {
      id: 4,
      title: "Submit Mini Project",
      dueDate: "Oct 22",
      priority: "High",
      status: "In Progress",
    },
  ];

  const filteredTasks = tasks.filter((task) =>
    task.title.toLowerCase().includes(search.toLowerCase())
  );

  const priorityColor = (priority) => {
    switch (priority) {
      case "High":
        return "text-red-400";
      case "Medium":
        return "text-yellow-400";
      default:
        return "text-green-400";
    }
  };

  const statusIcon = (status) => {
    switch (status) {
      case "Completed":
        return <FaCheckCircle className="text-green-400" />;
      case "In Progress":
        return <FaClock className="text-yellow-400" />;
      default:
        return <FaExclamationCircle className="text-red-400" />;
    }
  };

  return (
    <div className="min-h-screen bg-slate-950 text-white p-8">

      <div className="flex justify-between items-center mb-8">

        <div>
          <h1 className="text-4xl font-bold">Tasks ✅</h1>
          <p className="text-gray-400 mt-2">
            Track your assignments and daily work.
          </p>
        </div>

        <button className="bg-cyan-500 hover:bg-cyan-600 px-5 py-3 rounded-lg flex items-center gap-2 transition">
          <FaPlus />
          Add Task
        </button>

      </div>

      <div className="relative mb-8">

        <FaSearch className="absolute left-4 top-4 text-gray-400" />

        <input
          type="text"
          placeholder="Search Tasks..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full bg-slate-800 border border-slate-700 rounded-lg py-3 pl-12 pr-4 outline-none focus:border-cyan-400"
        />

      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">

        {filteredTasks.map((task) => (

          <div
            key={task.id}
            className="bg-slate-900 border border-slate-800 rounded-xl p-6 hover:border-cyan-500 transition duration-300"
          >

            <div className="flex justify-between">

              <h2 className="text-2xl font-bold">
                {task.title}
              </h2>

              <div className="flex gap-4">

                <button className="text-cyan-400 hover:text-cyan-300">
                  <FaEdit />
                </button>

                <button className="text-red-400 hover:text-red-300">
                  <FaTrash />
                </button>

              </div>

            </div>

            <div className="mt-6 flex justify-between">

              <div>

                <p className="text-gray-400">
                  Due Date
                </p>

                <p className="font-semibold">
                  {task.dueDate}
                </p>

              </div>

              <div>

                <p className="text-gray-400">
                  Priority
                </p>

                <p className={priorityColor(task.priority)}>
                  {task.priority}
                </p>

              </div>

            </div>

            <div className="flex items-center gap-3 mt-6">

              {statusIcon(task.status)}

              <span>
                {task.status}
              </span>

            </div>

          </div>

        ))}

      </div>

      {filteredTasks.length === 0 && (

        <div className="text-center mt-20">

          <FaCheckCircle className="text-6xl text-gray-600 mx-auto" />

          <h2 className="text-2xl mt-6">
            No Tasks Found
          </h2>

        </div>

      )}

    </div>
  );
}