import { useEffect, useState } from "react";
import {
  FaPlus,
  FaSearch,
  FaTrash,
  FaEdit,
  FaCheckCircle,
  FaClock,
  FaExclamationCircle,
} from "react-icons/fa";

import {
  getTasks,
  createTask,
  updateTask,
  deleteTask,
} from "../../services/taskService";

export default function Tasks() {

  const [search, setSearch] = useState("");

  const [tasks, setTasks] = useState([]);

  const [loading, setLoading] = useState(true);

  const [showModal, setShowModal] = useState(false);

  const [editingTask, setEditingTask] = useState(null);

  const [taskData, setTaskData] = useState({
    title: "",
    description: "",
    dueDate: "",
    priority: "Medium",
    status: "Pending",
  });

  useEffect(() => {
    loadTasks();
  }, []);

  const loadTasks = async () => {

    try {

      const response = await getTasks();

      setTasks(response.data);

    } catch (err) {

      console.error("Failed to load tasks", err);

    } finally {

      setLoading(false);

    }

  };

  const openCreateModal = () => {

    setEditingTask(null);

    setTaskData({
      title: "",
      description: "",
      dueDate: "",
      priority: "Medium",
      status: "Pending",
    });

    setShowModal(true);

  };

  const openEditModal = (task) => {

    setEditingTask(task);

    setTaskData({
      title: task.title,
      description: task.description,
      dueDate: task.dueDate,
      priority: task.priority,
      status: task.status,
    });

    setShowModal(true);

  };

  const handleSave = async () => {

    if (
      !taskData.title ||
      !taskData.description ||
      !taskData.dueDate
    ) {
      alert("Please fill all fields.");
      return;
    }

    try {

      if (editingTask) {

        await updateTask(editingTask.id, taskData);

      } else {

        await createTask(taskData);

      }

      setShowModal(false);

      loadTasks();

    } catch (err) {

      console.error(err);

      alert("Failed to save task.");

    }

  };

  const handleDelete = async (id) => {

    const confirmDelete = window.confirm(
      "Delete this task?"
    );

    if (!confirmDelete) return;

    try {

      await deleteTask(id);

      loadTasks();

    } catch (err) {

      console.error(err);

      alert("Delete failed.");

    }

  };

  const filteredTasks = tasks.filter((task) =>
    task.title.toLowerCase().includes(search.toLowerCase()) ||
    task.description.toLowerCase().includes(search.toLowerCase())
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

  if (loading) {

    return (
      <div className="min-h-screen bg-slate-950 flex justify-center items-center text-white text-2xl">
        Loading Tasks...
      </div>
    );

  }
    return (

    <div className="min-h-screen bg-slate-950 text-white p-8">

      <div className="flex justify-between items-center mb-8">

        <div>

          <h1 className="text-4xl font-bold">
            Tasks ✅
          </h1>

          <p className="text-gray-400 mt-2">
            Track your assignments and daily work.
          </p>

        </div>

        <button
          onClick={openCreateModal}
          className="bg-cyan-500 hover:bg-cyan-600 px-5 py-3 rounded-lg flex items-center gap-2 transition"
        >

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

      {filteredTasks.length === 0 ? (

        <div className="text-center mt-20">

          <FaCheckCircle className="text-6xl text-gray-600 mx-auto" />

          <h2 className="text-2xl mt-6">

            No Tasks Found

          </h2>

          <p className="text-gray-400 mt-2">

            Create your first task.

          </p>

        </div>

      ) : (

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">

          {filteredTasks.map((task) => (

            <div
              key={task.id}
              className="bg-slate-900 border border-slate-800 rounded-xl p-6 hover:border-cyan-500 transition duration-300"
            >

              <div className="flex justify-between items-start">

                <h2 className="text-2xl font-bold">

                  {task.title}

                </h2>

                <div className="flex gap-3">

                  <button
                    onClick={() => openEditModal(task)}
                    className="text-cyan-400 hover:text-cyan-300"
                  >

                    <FaEdit />

                  </button>

                  <button
                    onClick={() => handleDelete(task.id)}
                    className="text-red-400 hover:text-red-300"
                  >

                    <FaTrash />

                  </button>

                </div>

              </div>

              <p className="text-gray-300 mt-4">

                {task.description}

              </p>

              <div className="grid grid-cols-2 gap-4 mt-6">

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

      )}
            {showModal && (

        <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">

          <div className="bg-slate-900 rounded-xl w-full max-w-lg p-6">

            <h2 className="text-2xl font-bold mb-6">

              {editingTask ? "Edit Task" : "Create Task"}

            </h2>

            <input
              type="text"
              placeholder="Task Title"
              value={taskData.title}
              onChange={(e) =>
                setTaskData({
                  ...taskData,
                  title: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none"
            />

            <textarea
              rows={5}
              placeholder="Task Description"
              value={taskData.description}
              onChange={(e) =>
                setTaskData({
                  ...taskData,
                  description: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none resize-none"
            />

            <label className="text-gray-300 text-sm">
              Due Date
            </label>

            <input
              type="date"
              value={taskData.dueDate}
              onChange={(e) =>
                setTaskData({
                  ...taskData,
                  dueDate: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mt-2 mb-4 outline-none"
            />

            <label className="text-gray-300 text-sm">
              Priority
            </label>

            <select
              value={taskData.priority}
              onChange={(e) =>
                setTaskData({
                  ...taskData,
                  priority: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mt-2 mb-4 outline-none"
            >
              <option value="High">High</option>
              <option value="Medium">Medium</option>
              <option value="Low">Low</option>
            </select>

            <label className="text-gray-300 text-sm">
              Status
            </label>

            <select
              value={taskData.status}
              onChange={(e) =>
                setTaskData({
                  ...taskData,
                  status: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mt-2 mb-6 outline-none"
            >
              <option value="Pending">Pending</option>
              <option value="In Progress">In Progress</option>
              <option value="Completed">Completed</option>
            </select>

            <div className="flex justify-end gap-3">

              <button
                onClick={() => setShowModal(false)}
                className="px-5 py-2 rounded-lg bg-gray-600 hover:bg-gray-700"
              >
                Cancel
              </button>

              <button
                onClick={handleSave}
                className="px-5 py-2 rounded-lg bg-cyan-500 hover:bg-cyan-600"
              >
                {editingTask ? "Update Task" : "Save Task"}
              </button>

            </div>

          </div>

        </div>

      )}

    </div>

  );

}