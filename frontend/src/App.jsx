import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import Dashboard from "./pages/dashboard/Dashboard";
import Subjects from "./pages/subjects/Subjects";
import Notes from "./pages/notes/Notes";
import Tasks from "./pages/tasks/Tasks";
import Calendar from "./pages/calender/Calender";
import Documents from "./pages/documents/Documents";
import ChatWithPDF from "./pages/ai/ChatWithPDF";
import MainLayout from "./components/layout/MainLayout";
import KnowledgeGraph from "./pages/knowledgegraph/KnowledgeGraph";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<Login />} />
<Route path="/register" element={<Register />} />
        <Route element={<MainLayout />}>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/subjects" element={<Subjects />} />
          <Route path="/notes" element={<Notes />} />
          <Route path="/tasks" element={<Tasks />} />
          <Route path="/calendar" element={<Calendar />} />
          <Route path="/documents" element={<Documents />} />

          <Route path="/chat/:id" element={<ChatWithPDF />} />
         <Route
    path="/knowledge-graph/:id"
    element={<KnowledgeGraph />}
/>

        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;