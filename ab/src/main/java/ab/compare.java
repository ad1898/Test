package ab;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class compare {

    private static int differenceCount = 0; // To track the number of differences

    public static void main(String[] args) {
        try {
            // Load XML files
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(new File("src/main/java/ab/file1.xml")); // First XML file
            Document doc2 = builder.parse(new File("src/main/java/ab/file2.xml")); // Second XML file

            // Normalize the documents
            doc1.getDocumentElement().normalize();
            doc2.getDocumentElement().normalize();

            // Print header for differences
            System.out.println("Differences:");

            // Compare the two documents
            compareNodes(doc1.getDocumentElement(), doc2.getDocumentElement(), "/", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compareNodes(Node node1, Node node2, String path, int index) {
        // Check if both nodes are the same type
        if (node1.getNodeType() != node2.getNodeType()) {
            printDifference(path, node1, node2, "Node type differs");
            return;
        }

        // Compare node names
        if (!node1.getNodeName().equals(node2.getNodeName())) {
            printDifference(path, node1, node2, "Node name differs");
            return;
        }

        // Compare node values (with null checks)
        String value1 = node1.getNodeValue();
        String value2 = node2.getNodeValue();

        if (value1 == null) value1 = ""; // Consider empty if null
        if (value2 == null) value2 = ""; // Consider empty if null

        if (!value1.equals(value2)) {
            printDifference(path, node1, node2, "Node value differs");
            return;
        }

        // Compare child nodes
        NodeList children1 = node1.getChildNodes();
        NodeList children2 = node2.getChildNodes();

        for (int i = 0; i < Math.max(children1.getLength(), children2.getLength()); i++) {
            Node child1 = (i < children1.getLength()) ? children1.item(i) : null;
            Node child2 = (i < children2.getLength()) ? children2.item(i) : null;

            String childPath = path + "/" + ((child1 != null) ? child1.getNodeName() : "null") + "[" + (i + 1) + "]";
            if (child1 == null || child2 == null) {
                System.out.println("One of the child nodes is missing at path: " + childPath);
            } else {
                compareNodes(child1, child2, childPath, i + 1);
            }
        }
    }

    private static void printDifference(String path, Node node1, Node node2, String message) {
        differenceCount++;
        String nodePath1 = path.replace("/", "->").replace("->null", "") + "->" + node1.getNodeName() + "[" + (getNodeIndex(node1) + 1) + "]";
        String nodePath2 = path.replace("/", "->").replace("->null", "") + "->" + node2.getNodeName() + "[" + (getNodeIndex(node2) + 1) + "]";

        String parentNodeXpath1 = path;
        String parentNodeXpath2 = path;

        System.out.println("Difference" + differenceCount + ":");
        System.out.println("nodePath1=" + nodePath1 + ", nodeValue=" + (node1.getNodeValue() != null ? node1.getNodeValue() : "null") + ", parentNodeXpath1=" + parentNodeXpath1);
        System.out.println("nodePath2=" + nodePath2 + ", nodeValue=" + (node2.getNodeValue() != null ? node2.getNodeValue() : "null") + ", parentNodeXpath2=" + parentNodeXpath2);
        System.out.println();
    }

    private static int getNodeIndex(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) return 0;

        NodeList siblings = parent.getChildNodes();
        int index = 0;
        for (int i = 0; i < siblings.getLength(); i++) {
            if (siblings.item(i) == node) {
                return index;
            }
            if (siblings.item(i).getNodeType() == Node.ELEMENT_NODE) {
                index++;
            }
        }
        return -1; // Not found
    }
}
