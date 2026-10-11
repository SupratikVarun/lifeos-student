import { useEffect, useState } from "react";
import {
  FaPlus,
  FaSearch,
  FaTrash,
  FaEdit,
  FaCalendarAlt,
} from "react-icons/fa";

import {
  getEvents,
  createEvent,
  updateEvent,
  deleteEvent,
} from "../../services/calendarService";

export default function Calendar() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);

  const [search, setSearch] = useState("");

  const [showModal, setShowModal] = useState(false);

  const [editingEvent, setEditingEvent] = useState(null);

  const [newEvent, setNewEvent] = useState({
    title: "",
    description: "",
    eventDate: "",
    eventType: "General",
  });

  useEffect(() => {
    loadEvents();
  }, []);

  const loadEvents = async () => {
    try {
      const response = await getEvents();
      setEvents(response.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async () => {
    if (
      !newEvent.title ||
      !newEvent.eventDate ||
      !newEvent.eventType
    ) {
      alert("Please fill all required fields.");
      return;
    }

    try {
      if (editingEvent) {
        await updateEvent(editingEvent.id, newEvent);
      } else {
        await createEvent(newEvent);
      }

      loadEvents();

      setShowModal(false);

      setEditingEvent(null);

      setNewEvent({
        title: "",
        description: "",
        eventDate: "",
        eventType: "General",
      });
    } catch (err) {
      console.error(err);
      alert("Operation failed.");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this event?")) return;

    try {
      await deleteEvent(id);
      loadEvents();
    } catch (err) {
      console.error(err);
    }
  };

  const handleEdit = (event) => {
    setEditingEvent(event);

    setNewEvent({
      title: event.title,
      description: event.description,
      eventDate: event.eventDate,
      eventType: event.eventType,
    });

    setShowModal(true);
  };

  const filteredEvents = events.filter(
    (event) =>
      event.title.toLowerCase().includes(search.toLowerCase()) ||
      event.eventType.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) {
    return (
      <div className="min-h-screen bg-slate-950 flex justify-center items-center text-white text-2xl">
        Loading Events...
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-slate-950 text-white p-8">

      <div className="flex justify-between items-center mb-8">

        <div>

          <h1 className="text-4xl font-bold">
            Calendar 📅
          </h1>

          <p className="text-gray-400 mt-2">
            Manage your events.
          </p>

        </div>

        <button
          onClick={() => {
            setEditingEvent(null);

            setNewEvent({
              title: "",
              description: "",
              eventDate: "",
              eventType: "General",
            });

            setShowModal(true);
          }}
          className="bg-cyan-500 hover:bg-cyan-600 px-5 py-3 rounded-lg flex items-center gap-2"
        >
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
          onChange={(e) => setSearch(e.target.value)}
          className="w-full bg-slate-800 border border-slate-700 rounded-lg py-3 pl-12 pr-4 outline-none"
        />

      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">

        {filteredEvents.map((event) => (

          <div
            key={event.id}
            className="bg-slate-900 rounded-xl p-6 border border-slate-800 hover:border-cyan-500 transition"
          >

            <div className="flex justify-between">

              <div>

                <h2 className="text-2xl font-bold">
                  {event.title}
                </h2>

                <p className="text-gray-400 mt-2">
                  {event.description}
                </p>

              </div>

              <div className="flex gap-3">

                <button
                  onClick={() => handleEdit(event)}
                  className="text-cyan-400"
                >
                  <FaEdit />
                </button>

                <button
                  onClick={() => handleDelete(event.id)}
                  className="text-red-400"
                >
                  <FaTrash />
                </button>

              </div>

            </div>

            <div className="mt-5 flex justify-between">

              <div>

                <p className="text-gray-500">
                  Date
                </p>

                <p>{event.eventDate}</p>

              </div>

              <div>

                <p className="text-gray-500">
                  Type
                </p>

                <p>{event.eventType}</p>

              </div>

            </div>

          </div>

        ))}

      </div>

      {filteredEvents.length === 0 && (

        <div className="text-center mt-20">

          <FaCalendarAlt className="text-6xl mx-auto text-gray-600" />

          <h2 className="text-2xl mt-6">
            No Events Found
          </h2>

        </div>

      )}

      {showModal && (

        <div className="fixed inset-0 bg-black/70 flex justify-center items-center z-50">

          <div className="bg-slate-900 rounded-xl p-6 w-full max-w-lg">

            <h2 className="text-2xl font-bold mb-6">
              {editingEvent ? "Edit Event" : "Create Event"}
            </h2>

            <input
              type="text"
              placeholder="Title"
              value={newEvent.title}
              onChange={(e) =>
                setNewEvent({
                  ...newEvent,
                  title: e.target.value,
                })
              }
              className="w-full bg-slate-800 p-3 rounded-lg mb-4"
            />

            <textarea
              rows={4}
              placeholder="Description"
              value={newEvent.description}
              onChange={(e) =>
                setNewEvent({
                  ...newEvent,
                  description: e.target.value,
                })
              }
              className="w-full bg-slate-800 p-3 rounded-lg mb-4"
            />

            <input
              type="date"
              value={newEvent.eventDate}
              onChange={(e) =>
                setNewEvent({
                  ...newEvent,
                  eventDate: e.target.value,
                })
              }
              className="w-full bg-slate-800 p-3 rounded-lg mb-4"
            />

            <select
              value={newEvent.eventType}
              onChange={(e) =>
                setNewEvent({
                  ...newEvent,
                  eventType: e.target.value,
                })
              }
              className="w-full bg-slate-800 p-3 rounded-lg mb-6"
            >
              <option>General</option>
              <option>Exam</option>
              <option>Assignment</option>
              <option>Meeting</option>
              <option>Holiday</option>
            </select>

            <div className="flex justify-end gap-3">

              <button
                onClick={() => setShowModal(false)}
                className="bg-gray-600 px-5 py-2 rounded-lg"
              >
                Cancel
              </button>

              <button
                onClick={handleSave}
                className="bg-cyan-500 px-5 py-2 rounded-lg"
              >
                {editingEvent ? "Update" : "Save"}
              </button>

            </div>

          </div>

        </div>

      )}

    </div>
  );
}