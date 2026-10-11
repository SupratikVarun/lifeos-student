import { useState } from "react";
import { search } from "../services/searchService";

const SmartSearchPage = () => {

    const [keyword, setKeyword] = useState("");

    const [results, setResults] = useState([]);

    const [loading, setLoading] = useState(false);

    const handleSearch = async () => {

        if (!keyword.trim()) return;

        try {

            setLoading(true);

            const data = await search(keyword);

            setResults(data);

        } catch (error) {

            console.error(error);

        } finally {

            setLoading(false);

        }

    };

    return (

        <div className="smart-search-page">

            <h1>Smart Search</h1>

            <div className="search-bar">

                <input
                    type="text"
                    placeholder="Search anything..."
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                />

                <button onClick={handleSearch}>
                    Search
                </button>

            </div>

        </div>

    );

};

export default SmartSearchPage;