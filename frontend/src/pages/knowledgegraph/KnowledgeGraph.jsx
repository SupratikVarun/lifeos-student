import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axiosConfig from "../../api/axiosConfig";

import dagre from "dagre";

import ReactFlow, {
    Background,
    Controls,
    MiniMap,
    MarkerType
} from "reactflow";

import "reactflow/dist/style.css";

const dagreGraph = new dagre.graphlib.Graph();

dagreGraph.setDefaultEdgeLabel(() => ({}));

const nodeWidth = 220;
const nodeHeight = 80;

const getLayoutedElements = (nodes, edges) => {

    dagreGraph.setGraph({
        rankdir: "TB",
        ranksep: 150,
        nodesep: 100
    });

    nodes.forEach((node) => {

        dagreGraph.setNode(node.id, {
            width: nodeWidth,
            height: nodeHeight
        });

    });

    edges.forEach((edge) => {

        dagreGraph.setEdge(edge.source, edge.target);

    });

    dagre.layout(dagreGraph);

    nodes.forEach((node) => {

        const position = dagreGraph.node(node.id);

        node.position = {
            x: position.x - nodeWidth / 2,
            y: position.y - nodeHeight / 2
        };

    });

    return { nodes, edges };

};

export default function KnowledgeGraph() {

    const { id } = useParams();

    const [nodes, setNodes] = useState([]);
    const [edges, setEdges] = useState([]);

    const fetchGraph = async () => {

        try {

            const response = await axiosConfig.get("/knowledge-graph");

            const graph = response.data;

            const flowNodes = graph.nodes.map((node) => ({
                id: node.name,
                data: {
                    label: node.name
                },
                position: {
                    x: 0,
                    y: 0
                }
            }));

            const flowEdges = graph.relations.map((relation, index) => ({
                id: String(index),
                source: relation.source,
                target: relation.target,
                label: relation.relation,
                animated: true,
                type: "smoothstep",
                markerEnd: {
                    type: MarkerType.ArrowClosed
                }
            }));

            const layout = getLayoutedElements(
                flowNodes,
                flowEdges
            );

            setNodes(layout.nodes);
            setEdges(layout.edges);

        } catch (error) {

            console.log(error);

        }

    };

    const generateGraph = async () => {

        try {

            await axiosConfig.post(
                `/knowledge-graph/${id}/generate`
            );

            await fetchGraph();

        } catch (error) {

            console.log(error);

        }

    };

    useEffect(() => {

        generateGraph();

    }, []);

    return (

        <div className="h-screen bg-slate-950">

            <div className="p-6">

                <h1 className="text-4xl font-bold text-cyan-400">
                    Knowledge Graph
                </h1>

                <p className="text-gray-400 mt-2">
                    AI Generated Concept Map
                </p>

            </div>

            <div
                style={{
                    width: "100%",
                    height: "85%"
                }}
            >

                <ReactFlow
                    nodes={nodes}
                    edges={edges}
                    fitView
                    fitViewOptions={{
                        padding: 0.5
                    }}
                    nodesDraggable
                    panOnDrag
                    zoomOnScroll
                    minZoom={0.2}
                    maxZoom={2}
                >

                    <MiniMap />

                    <Controls />

                    <Background />

                </ReactFlow>

            </div>

        </div>

    );

}