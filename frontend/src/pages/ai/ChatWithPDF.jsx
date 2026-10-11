import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axiosConfig from "../../api/axiosConfig";
import ReactMarkdown from "react-markdown";


export default function ChatWithPDF() {

    const { id } = useParams();

    const [question, setQuestion] = useState("");
    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(false);

    const [document, setDocument] = useState(null);

    const sendQuestion = async () => {

        if (!question.trim()) return;

        const userMessage = {
            sender: "user",
            text: question
        };

        setMessages(prev => [...prev, userMessage]);

        setLoading(true);

        try {

            const response = await axiosConfig.post(
                `/chat/${id}`,
                {
                    question: question
                }
            );

            const aiMessage = {
                sender: "ai",
                text: response.data.response
            };

            setMessages(prev => [...prev, aiMessage]);

            setQuestion("");

        } catch (error) {

            setMessages(prev => [
                ...prev,
                {
                    sender: "ai",
                    text: "Something went wrong."
                }
            ]);

        } finally {

            setLoading(false);

        }

    };

    
    const fetchDocument = async () => {

    try {

        const response = await axiosConfig.get(`/documents/${id}`);

        setDocument(response.data);

    } catch (error) {

        console.log(error);

    }

};

useEffect(() => {

    fetchDocument();

}, []);

    return (

        <div className="min-h-screen bg-slate-950 text-white p-10">

            <h1 className="text-4xl font-bold text-cyan-400 mb-8">
                AI Chat with PDF
            </h1>
{document && (

    <div className="mb-6">

        <p className="text-gray-400 text-sm">
            Current PDF
        </p>

        <h2 className="text-2xl font-semibold">
            📄 {document.fileName}
        </h2>

    </div>

)}
            <div className="bg-slate-900 rounded-2xl h-[550px] overflow-y-auto p-6 space-y-4">

                {messages.length === 0 && (

                    <p className="text-gray-400">
                        Ask anything about this PDF...
                    </p>

                )}

                {messages.map((msg, index) => (

                    <div
    key={index}
    className={`max-w-[75%] p-4 rounded-xl ${
        msg.sender === "user"
            ? "bg-cyan-600 ml-auto"
            : "bg-slate-700"
    }`}
>
    {msg.sender === "user" ? (
        msg.text
    ) : (
        <div className="prose prose-invert max-w-none">
            <ReactMarkdown>
                {msg.text}
            </ReactMarkdown>
        </div>
    )}
</div>

                ))}

            </div>

            <div className="flex gap-4 mt-6">

                <input
                    type="text"
                    placeholder="Ask something..."
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    className="flex-1 bg-slate-800 rounded-xl p-4 outline-none"
                />

                <button
                    onClick={sendQuestion}
                    disabled={loading}
                    className="bg-cyan-500 px-8 rounded-xl hover:bg-cyan-600"
                >
                    {loading ? "Thinking..." : "Send"}
                </button>

            </div>

        </div>

    );

}