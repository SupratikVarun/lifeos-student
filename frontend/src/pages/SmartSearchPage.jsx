import { useState } from "react";
import { FaSearch, FaSpinner, FaLayerGroup } from "react-icons/fa";
import { search } from "../services/searchService";

const TYPE_COLORS = {
    SUBJECT: "text-cyan-400 bg-cyan-400/10",
    NOTE: "text-violet-400 bg-violet-400/10",
    DOCUMENT: "text-emerald-400 bg-emerald-400/10",
    CONCEPT: "text-amber-400 bg-amber-400/10",
};

const SmartSearchPage = () => {
    const [keyword, setKeyword] = useState("");
    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [searched, setSearched] = useState(false);

    const handleSearch = async () => {
        if (!keyword.trim()) return;

        setError(null);
        setSearched(false);

        try {
            setLoading(true);
            const data = await search(keyword.trim());
            setResults(data);
        } catch (err) {
            console.error("Smart Search error:", err);
            setError("Something went wrong while searching. Please try again.");
            setResults([]);
        } finally {
            setLoading(false);
            setSearched(true);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") {
            handleSearch();
        }
    };

    return (
        <div className="bg-slate-950 min-h-screen w-full px-10 py-8">

            {/* Header */}
            <div className="mb-8">
                <h2 className="text-4xl font-bold text-white flex items-center gap-3">
                    <FaSearch className="text-cyan-400" />
                    Smart Search
                </h2>
                <p className="text-slate-400 mt-2 text-lg">
                    Search across subjects, notes, documents, and concepts instantly.
                </p>
            </div>

            {/* Search Bar */}
            <div className="flex items-center gap-4 bg-slate-900 border border-slate-700 rounded-2xl px-5 py-4 shadow-lg focus-within:border-cyan-500 transition-colors duration-300 mb-8">
                <FaSearch className="text-slate-400 text-lg flex-shrink-0" />
                <input
                    id="smart-search-input"
                    type="text"
                    placeholder="Search anything — subjects, notes, documents, concepts..."
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                    onKeyDown={handleKeyDown}
                    className="flex-1 bg-transparent text-white placeholder-slate-500 text-base outline-none"
                />
                <button
                    id="smart-search-button"
                    onClick={handleSearch}
                    disabled={loading || !keyword.trim()}
                    className="flex items-center gap-2 bg-cyan-500 hover:bg-cyan-400 disabled:bg-slate-700 disabled:cursor-not-allowed text-white font-semibold px-6 py-2 rounded-xl transition-all duration-300"
                >
                    {loading ? (
                        <>
                            <FaSpinner className="animate-spin" />
                            Searching…
                        </>
                    ) : (
                        <>
                            <FaSearch />
                            Search
                        </>
                    )}
                </button>
            </div>

            {/* Error State */}
            {error && (
                <div className="bg-red-500/10 border border-red-500/30 rounded-2xl p-5 mb-6 text-red-400">
                    {error}
                </div>
            )}

            {/* Loading Skeleton */}
            {loading && (
                <div className="space-y-4">
                    {[1, 2, 3].map((i) => (
                        <div
                            key={i}
                            className="bg-slate-900 rounded-2xl p-6 animate-pulse border border-slate-800"
                        >
                            <div className="h-3 w-20 bg-slate-700 rounded mb-3" />
                            <div className="h-5 w-2/5 bg-slate-700 rounded mb-2" />
                            <div className="h-3 w-4/5 bg-slate-800 rounded" />
                        </div>
                    ))}
                </div>
            )}

            {/* Results */}
            {!loading && searched && !error && (
                <>
                    {results.length === 0 ? (
                        <div className="flex flex-col items-center justify-center py-20 text-slate-500">
                            <FaLayerGroup className="text-5xl mb-4 text-slate-700" />
                            <p className="text-lg text-center">
                                No matching subjects, notes, documents, or concepts found.
                            </p>
                            <p className="text-sm mt-2">Try a different keyword.</p>
                        </div>
                    ) : (
                        <div className="space-y-4">
                            <p className="text-slate-400 text-sm mb-2">
                                {results.length} result{results.length !== 1 ? "s" : ""} for{" "}
                                <span className="text-cyan-400 font-semibold">"{keyword}"</span>
                            </p>

                            {results.map((result, index) => {
                                const typeKey = (result.type || "").toUpperCase();
                                const colorClass =
                                    TYPE_COLORS[typeKey] || "text-slate-400 bg-slate-400/10";

                                return (
                                    <div
                                        key={index}
                                        className="bg-slate-900 border border-slate-800 rounded-2xl p-6 hover:border-cyan-500/40 hover:shadow-cyan-500/10 hover:shadow-lg transition-all duration-300"
                                    >
                                        {/* Type Badge */}
                                        <span
                                            className={`inline-block text-xs font-bold uppercase tracking-widest px-3 py-1 rounded-full mb-3 ${colorClass}`}
                                        >
                                            {result.type || "Unknown"}
                                        </span>

                                        {/* Title */}
                                        <h3 className="text-white text-xl font-semibold mb-1">
                                            {result.title || "Untitled"}
                                        </h3>

                                        {/* Description */}
                                        {result.description && (
                                            <p className="text-slate-400 text-sm leading-relaxed line-clamp-3">
                                                {result.description}
                                            </p>
                                        )}
                                    </div>
                                );
                            })}
                        </div>
                    )}
                </>
            )}

        </div>
    );
};

export default SmartSearchPage;