import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosConfig from "../../api/axiosConfig";
import { Upload, FileText, Eye, Trash2, MessageCircle } from "lucide-react";
import { Link } from "react-router-dom";

export default function Documents() {

    const [file, setFile] = useState(null);
    const [documents, setDocuments] = useState([]);
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

   const fetchDocuments = async () => {

    try {

        const response = await axiosConfig.get("/documents");

        console.log(response.data);

        setDocuments(response.data);

    } catch (error) {

        console.log(error);

    }

};
    useEffect(() => {

        fetchDocuments();

    }, []);

    const uploadDocument = async () => {

        if (!file) {
            alert("Please choose a file");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {

            setLoading(true);

            await axiosConfig.post(
                "/documents/upload",
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                }
            );

            alert("Document Uploaded Successfully");

            setFile(null);

            fetchDocuments();

        } catch (error) {

            console.log(error);
            alert("Upload Failed");

        } finally {

            setLoading(false);

        }

    };

    // -----------------------------
    // DELETE FUNCTION
    // -----------------------------
    const deleteDocument = async (id) => {

        if (!window.confirm("Delete this document?")) return;

        try {

            await axiosConfig.delete(`/documents/${id}`);

            alert("Document Deleted");

            fetchDocuments();

        } catch (error) {

            console.log(error);

            alert("Delete Failed");

        }

    };

    return (

        <div className="p-10 text-white">

            <h1 className="text-4xl font-bold mb-8">
                Documents
            </h1>

            <div className="bg-slate-900 rounded-2xl p-8 mb-10">

    <input
        id="pdf-upload"
        type="file"
        accept=".pdf"
        className="hidden"
        onChange={(e) => setFile(e.target.files[0])}
    />

    <label
        htmlFor="pdf-upload"
        className="border-2 border-dashed border-cyan-500 rounded-2xl
        h-56 flex flex-col justify-center items-center cursor-pointer
        hover:bg-slate-800 transition duration-300"
    >

        <Upload className="w-14 h-14 text-cyan-400 mb-4" />

        <h2 className="text-2xl font-semibold">
            Upload PDF
        </h2>

        <p className="text-gray-400 mt-2">
            Click here to browse your files
        </p>

    </label>

    {file && (

        <div className="mt-6 bg-slate-800 rounded-xl p-4 flex justify-between items-center">

            <div className="flex items-center gap-3">

                <FileText className="text-cyan-400" />

                <div>

                    <p className="font-semibold">
                        {file.name}
                    </p>

                    <p className="text-gray-400 text-sm">
                        {(file.size / 1024).toFixed(2)} KB
                    </p>

                </div>

            </div>

            <button
                onClick={uploadDocument}
                className="bg-cyan-500 hover:bg-cyan-600 px-8 py-3 rounded-xl font-semibold transition"
            >
                {loading ? "Uploading..." : "Upload PDF"}
            </button>

        </div>

    )}

</div>

            <h2 className="text-2xl font-semibold mb-5">
                Uploaded Documents
            </h2>

            {
                documents.length === 0 ?

                    <p>No Documents Uploaded</p>

                    :

                    documents.map((doc) => (

                        <div
    key={doc.id}
    className="bg-slate-900 hover:bg-slate-800 transition rounded-2xl p-6 mb-5 flex justify-between items-center shadow-lg"
>

                            <div className="flex items-center gap-4">

    <FileText className="text-cyan-400 w-10 h-10" />

    <div>

        <h3 className="font-semibold text-lg">
            {doc.fileName}
        </h3>

        <p className="text-gray-400">
            {(doc.fileSize / 1024).toFixed(2)} KB
        </p>

    </div>

</div>

                            <div className="flex items-center gap-3 flex-wrap">

    <a
        href={`http://localhost:8080${doc.fileUrl}`}
        target="_blank"
        rel="noopener noreferrer"
        className="flex items-center gap-2 bg-cyan-500 hover:bg-cyan-600 px-4 py-2 rounded-lg transition"
    >
        <Eye size={18} />
        View
    </a>

    <Link
        to={`/ai/${doc.id}`}
        className="flex items-center gap-2 bg-purple-600 hover:bg-purple-700 px-4 py-2 rounded-lg transition"
    >
        <MessageCircle size={18} />
        AI Chat
    </Link>

    <Link
        to={`/knowledge-graph/${doc.id}`}
        className="flex items-center gap-2 bg-emerald-600 hover:bg-emerald-700 px-4 py-2 rounded-lg transition"
    >
        🕸 Graph
    </Link>

    <button
        onClick={() => deleteDocument(doc.id)}
        className="flex items-center gap-2 bg-red-500 hover:bg-red-600 px-4 py-2 rounded-lg transition"
    >
        <Trash2 size={18} />
        Delete
    </button>

</div>
</div>

                    ))

            }

        </div>

    );

}