import { useEffect, useState } from "react";
import {
  FaPlus,
  FaStickyNote,
  FaSearch,
  FaEdit,
  FaTrash,
  FaBook,
} from "react-icons/fa";

import {
  getNotes,
  createNote,
  updateNote,
  deleteNote,
} from "../../services/notesService";

import { getSubjects } from "../../services/subjectService";

export default function Notes() {

  const [search, setSearch] = useState("");

  const [notes, setNotes] = useState([]);

  const [subjects, setSubjects] = useState([]);

  const [loading, setLoading] = useState(true);

  const [showModal, setShowModal] = useState(false);

  const [isEditing, setIsEditing] = useState(false);

const [editingId, setEditingId] = useState(null);

  const [newNote, setNewNote] = useState({
    title: "",
    content: "",
    fileUrl: "",
    subjectId: "",
  });

  useEffect(() => {
    console.log("showModal =", showModal);
}, [showModal]);

  useEffect(() => {
    loadNotes();
    loadSubjects();
  }, []);

  const loadNotes = async () => {
    try {
      const response = await getNotes();
      setNotes(response.data);
    } catch (err) {
      console.error("Failed to load notes", err);
    } finally {
      setLoading(false);
    }
  };

  const loadSubjects = async () => {
    try {
      const response = await getSubjects();
      setSubjects(response.data);
    } catch (err) {
      console.error("Failed to load subjects", err);
    }
  };

  const handleEdit = (note) => {

    setIsEditing(true);

    setEditingId(note.id);

    setNewNote({
        title: note.title,
        content: note.content,
        fileUrl: note.fileUrl || "",
        subjectId: note.subjectId,
    });

    setShowModal(true);

};

  const handleCreateNote = async () => {

    if (
      !newNote.title ||
      !newNote.content ||
      !newNote.subjectId
    ) {
      alert("Please fill all fields.");
      return;
    }

    try {

      await createNote(newNote);

      setShowModal(false);

      setIsEditing(false);

setEditingId(null);

      setNewNote({
        title: "",
        content: "",
        fileUrl: "",
        subjectId: "",
      });

      loadNotes();

    } catch (err) {

      console.error(err);

      alert("Failed to create note.");

    }

  };

  const handleUpdateNote = async () => {

    try {

        await updateNote(editingId, newNote);

        setShowModal(false);

        setIsEditing(false);

        setEditingId(null);

        setNewNote({
            title: "",
            content: "",
            fileUrl: "",
            subjectId: "",
        });

        loadNotes();

    } catch (err) {

        console.error(err);

        alert("Failed to update note.");

    }

};

  const handleDelete = async (id) => {

    const confirmDelete = window.confirm(
      "Delete this note?"
    );

    if (!confirmDelete) return;

    try {

      await deleteNote(id);

      loadNotes();

    } catch (err) {

      console.error(err);

      alert("Delete failed.");

    }

  };

  const filteredNotes = notes.filter((note) =>
    note.title.toLowerCase().includes(search.toLowerCase()) ||
    note.content.toLowerCase().includes(search.toLowerCase())
  );

  console.log("Notes component rendered");
console.log("showModal =", showModal);

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-950 flex justify-center items-center text-white text-2xl">
        Loading Notes...
      </div>
    );
  }

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

    <button
  onClick={() => {
    console.log("Button clicked");
    setShowModal(true);
  }}
>
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

  {filteredNotes.length === 0 ? (

    <div className="text-center mt-20">

      <FaStickyNote className="text-6xl mx-auto text-gray-600" />

      <h2 className="text-2xl mt-6">
        No Notes Found
      </h2>

      <p className="text-gray-400 mt-2">
        Create your first note.
      </p>

    </div>

  ) : (

    <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">

      {filteredNotes.map((note) => (

        <div
          key={note.id}
          className="bg-slate-900 rounded-xl p-6 border border-slate-800 hover:border-cyan-500 transition"
        >

          <div className="flex justify-between items-start">

            <FaStickyNote className="text-cyan-400 text-3xl" />

            <div className="flex gap-3">

             <button
    onClick={() => handleEdit(note)}
    className="text-cyan-400 hover:text-cyan-300"
>
    <FaEdit />
</button>

              <button
                onClick={() => handleDelete(note.id)}
                className="text-red-400 hover:text-red-300"
              >

                <FaTrash />

              </button>

            </div>

          </div>

          <h2 className="text-2xl font-bold mt-5">

            {note.title}

          </h2>

          <div className="flex items-center gap-2 mt-4 text-gray-400">

            <FaBook />

            {
              subjects.find(
                (subject) =>
                  subject.id === note.subjectId
              )?.subjectName || "Unknown Subject"
            }

          </div>

          <p className="mt-5 text-gray-300 line-clamp-5">

            {note.content}

          </p>

        </div>

      ))}

    </div>

  )}
        {showModal && (

        <div className="fixed inset-0 bg-black/70 flex items-center justify-center z-50">

          <div className="bg-slate-900 rounded-xl w-full max-w-lg p-6">

            <h2 className="text-2xl font-bold mb-6">

              Create Note

            </h2>

            <input
              type="text"
              placeholder="Note Title"
              value={newNote.title}
              onChange={(e) =>
                setNewNote({
                  ...newNote,
                  title: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none"
            />

            <textarea
              rows={6}
              placeholder="Write your notes..."
              value={newNote.content}
              onChange={(e) =>
                setNewNote({
                  ...newNote,
                  content: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-4 outline-none resize-none"
            />

            <select
              value={newNote.subjectId}
              onChange={(e) =>
                setNewNote({
                  ...newNote,
                  subjectId: e.target.value,
                })
              }
              className="w-full bg-slate-800 rounded-lg p-3 mb-6 outline-none"
            >

              <option value="">

                Select Subject

              </option>

              {subjects.map((subject) => (

                <option
                  key={subject.id}
                  value={subject.id}
                >

                  {subject.subjectName}

                </option>

              ))}

            </select>

            <div className="flex justify-end gap-3">

              <button
                onClick={() => setShowModal(false)}
                className="px-5 py-2 rounded-lg bg-gray-600 hover:bg-gray-700"
              >

                Cancel

              </button>
<button
  onClick={isEditing ? handleUpdateNote : handleCreateNote}
  className="px-5 py-2 rounded-lg bg-cyan-500 hover:bg-cyan-600"
>
  {isEditing ? "Update Note" : "Save Note"}
</button>
            </div>

          </div>

        </div>

      )}

    </div>

  );

}
