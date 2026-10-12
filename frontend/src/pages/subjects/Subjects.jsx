import { useEffect, useState } from "react";
import {
  FaPlus,
  FaBook,
  FaSearch,
  FaUserGraduate,
  FaEdit,
  FaTrash,
} from "react-icons/fa";

import {
  getSubjects,
  createSubject,
  updateSubject,
  deleteSubject,
} from "../../services/subjectService";

export default function Subjects() {

  const [subjects, setSubjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editingSubject, setEditingSubject] = useState(null);

  const emptyForm = {
    subjectName: "",
    subjectCode: "",
    facultyName: "",
    semester: "",
  };

  const [formData, setFormData] = useState(emptyForm);

  // ─── Load subjects on mount ───────────────────────────────────────────
  useEffect(() => {
    loadSubjects();
  }, []);

  const loadSubjects = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await getSubjects();
      setSubjects(response.data);
    } catch (err) {
      console.error("Failed to load subjects", err);
      setError("Failed to load subjects. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  // ─── Open Add modal ───────────────────────────────────────────────────
  const openCreateModal = () => {
    setEditingSubject(null);
    setFormData(emptyForm);
    setShowModal(true);
  };

  // ─── Open Edit modal ──────────────────────────────────────────────────
  const openEditModal = (subject) => {
    setEditingSubject(subject);
    setFormData({
      subjectName: subject.subjectName,
      subjectCode: subject.subjectCode,
      facultyName: subject.facultyName,
      semester: subject.semester,
    });
    setShowModal(true);
  };

  // ─── Save (create or update) ──────────────────────────────────────────
  const handleSave = async () => {
    const { subjectName, subjectCode, facultyName, semester } = formData;

    if (!subjectName || !subjectCode || !facultyName || !semester) {
      alert("Please fill in all fields.");
      return;
    }

    try {
      if (editingSubject) {
        await updateSubject(editingSubject.id, formData);
      } else {
        await createSubject(formData);
      }
      setShowModal(false);
      loadSubjects();
    } catch (err) {
      console.error(err);
      alert("Failed to save subject. Please try again.");
    }
  };

  // ─── Delete ───────────────────────────────────────────────────────────
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this subject?")) return;
    try {
      await deleteSubject(id);
      loadSubjects();
    } catch (err) {
      console.error(err);
      alert("Failed to delete subject. Please try again.");
    }
  };

  // ─── Filter ───────────────────────────────────────────────────────────
  const filteredSubjects = subjects.filter(
    (s) =>
      s.subjectName.toLowerCase().includes(search.toLowerCase()) ||
      s.subjectCode.toLowerCase().includes(search.toLowerCase())
  );

  // ─── Loading state ────────────────────────────────────────────────────
  if (loading) {
    return (
      <div className="min-h-screen bg-slate-950 flex justify-center items-center text-white text-2xl">
        Loading Subjects...
      </div>
    );
  }

  // ─── Error state ──────────────────────────────────────────────────────
  if (error) {
    return (
      <div className="min-h-screen bg-slate-950 flex flex-col justify-center items-center text-white gap-4">
        <FaBook className="text-6xl text-red-400" />
        <p className="text-xl text-red-400">{error}</p>
        <button
          onClick={loadSubjects}
          className="bg-cyan-500 hover:bg-cyan-600 px-6 py-3 rounded-lg transition"
        >
          Retry
        </button>
      </div>
    );
  }

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

        <button
          onClick={openCreateModal}
          className="flex items-center gap-2 bg-cyan-500 hover:bg-cyan-600 transition px-5 py-3 rounded-lg font-semibold"
        >
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
      {filteredSubjects.length === 0 ? (

        <div className="text-center mt-20 text-gray-400">

          <FaBook className="text-6xl mx-auto mb-5 opacity-30" />

          <h2 className="text-2xl font-semibold">
            {search ? "No subjects match your search." : "No subjects found."}
          </h2>

          {!search && (
            <p className="mt-2">
              Click <span className="text-cyan-400 font-semibold">Add Subject</span> to create one.
            </p>
          )}

        </div>

      ) : (

        <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">

          {filteredSubjects.map((subject) => (

            <div
              key={subject.id}
              className="bg-slate-900 rounded-xl p-6 border border-slate-800 hover:border-cyan-500 transition-all duration-300 hover:shadow-xl hover:scale-[1.02]"
            >

              <div className="flex justify-between items-start">

                <FaBook className="text-cyan-400 text-3xl" />

                <div className="flex gap-3">

                  <button
                    onClick={() => openEditModal(subject)}
                    className="text-cyan-400 hover:text-cyan-300"
                    title="Edit subject"
                  >
                    <FaEdit />
                  </button>

                  <button
                    onClick={() => handleDelete(subject.id)}
                    className="text-red-400 hover:text-red-300"
                    title="Delete subject"
                  >
                    <FaTrash />
                  </button>

                </div>

              </div>

              <h2 className="text-2xl font-bold mt-6">
                {subject.subjectName}
              </h2>

              <p className="text-gray-400 mt-2">
                {subject.subjectCode}
              </p>

              <div className="mt-6 space-y-2">

                <div className="flex items-center gap-2 text-gray-300">
                  <FaUserGraduate />
                  {subject.facultyName}
                </div>

                <div className="text-gray-400">
                  {subject.semester}
                </div>

              </div>

            </div>

          ))}

        </div>

      )}

      {/* Add / Edit Modal */}
      {showModal && (

        <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">

          <div className="bg-slate-900 rounded-xl w-full max-w-lg p-6">

            <h2 className="text-2xl font-bold mb-6">
              {editingSubject ? "Edit Subject" : "Add Subject"}
            </h2>

            <input
              type="text"
              placeholder="Subject Name"
              value={formData.subjectName}
              onChange={(e) =>
                setFormData({ ...formData, subjectName: e.target.value })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none border border-slate-700 focus:border-cyan-400"
            />

            <input
              type="text"
              placeholder="Subject Code (e.g. CS301)"
              value={formData.subjectCode}
              onChange={(e) =>
                setFormData({ ...formData, subjectCode: e.target.value })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none border border-slate-700 focus:border-cyan-400"
            />

            <input
              type="text"
              placeholder="Faculty Name"
              value={formData.facultyName}
              onChange={(e) =>
                setFormData({ ...formData, facultyName: e.target.value })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none border border-slate-700 focus:border-cyan-400"
            />

            <input
              type="text"
              placeholder="Semester (e.g. Semester 5)"
              value={formData.semester}
              onChange={(e) =>
                setFormData({ ...formData, semester: e.target.value })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-6 outline-none border border-slate-700 focus:border-cyan-400"
            />

            <div className="flex justify-end gap-3">

              <button
                onClick={() => setShowModal(false)}
                className="px-5 py-2 rounded-lg bg-gray-600 hover:bg-gray-700 transition"
              >
                Cancel
              </button>

              <button
                onClick={handleSave}
                className="px-5 py-2 rounded-lg bg-cyan-500 hover:bg-cyan-600 transition"
              >
                {editingSubject ? "Update Subject" : "Save Subject"}
              </button>

            </div>

          </div>

        </div>

      )}

    </div>
  );
}