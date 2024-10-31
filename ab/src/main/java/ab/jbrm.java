import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            // Load XML files
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc1 = builder.parse(new File("src/file1.xml")); // First XML file
            Document doc2 = builder.parse(new File("src/file2.xml")); // Second XML file

            // Normalize the documents
            doc1.getDocumentElement().normalize();
            doc2.getDocumentElement().normalize();

            // Print header for differences
            System.out.println("Differences:");

            // Compare the two documents
            compareNodes1(doc1.getDocumentElement(), doc2.getDocumentElement(), "/");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Boolean checkElementNode(Node node) {
        NodeList children = node.getChildNodes();
        int coun = 0;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE && countElement(children.item(i)) == 0) {
                coun++;
            }
        }
        if (coun > 0) {
            return false;
        }
        return true;
    }

    private static void compareNodes1(Node node1, Node node2, String path) {

        NodeList children1 = node1.getChildNodes();
        NodeList children2 = node2.getChildNodes();

        Boolean isElementNode1 = checkElementNode(node1);
        Boolean isElementNode2 = checkElementNode(node2);

        if (!isElementNode1.equals(isElementNode2)) {
            System.out.println("Error for one is element other not");
            return;
        }

        if (isElementNode1) {
            Integer elementNodeCount1 = countElement(node1);
            Integer elementNodeCount2 = countElement(node2);

            if (!elementNodeCount1.equals(elementNodeCount2)) {
                System.out.println("Error for count of child node is different");
                return;
            }

            int coun = 1;
            for (int i = 0; i < children1.getLength(); i++) {
                if (!children1.item(i).getTextContent().trim().isEmpty()) {
                    String childPath = path + "/" + children1.item(i).getNodeName() + "[" + (coun) + "]";
                    compareNodes1(children1.item(i), children2.item(i), childPath);
                    coun++;
                }
            }
        } else {
            Map<Object, Node> mapChild1 = convertNodeListToList(children1);
            Map<Object, Node> mapChild2 = convertNodeListToList(children2);

            compareElementNode(mapChild1, mapChild2, path);
        }
    }

    private static Integer countElement(Node node) {
        NodeList children = node.getChildNodes();
        int coun = 0;
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                coun++;
            }
        }
        return coun;
    }

    public static Map<Object, Node> convertNodeListToList(NodeList nodeList) {
        Map<Object, Node> map = new HashMap<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                map.put(nodeList.item(i).getNodeName(), nodeList.item(i));
            }
        }
        return map;
    }

    public static Map<Object, Integer> getNodeTagIndex(Map<Object, Node> c1) {
        Map<Object, Integer> map = new HashMap<>();
        int coun = 1;
        for (Object key : c1.keySet()) {
            map.put(key, coun);
            coun++;
        }
        return map;
    }

    public static void compareElementNode(Map<Object, Node> c1, Map<Object, Node> c2, String path) {
        Map<Object, Integer> ma1 = getNodeTagIndex(c1);
        Map<Object, Integer> ma2 = getNodeTagIndex(c2);

        List<Object> Li = new ArrayList<>();
        for (Object key : c1.keySet()) {
            boolean exists = c2.containsKey(key);  // returns true
            if (exists) {//System.out.println(key);
                Li.add(key);
                if (!c1.get(key).getTextContent().trim().equals(c2.get(key).getTextContent().trim())) {
                    if (countElement(c1.get(key)) == 0) {
                        String childPath = path + "/" + key;
                        System.out.println("Error in value : " + childPath);
                    } else {
                        String childPath = path + "/" + key + "[" + ma1.get(key) + "]";
                        compareNodes1(c1.get(key), c2.get(key), childPath);
                    }
                }
            }
        }
        for (Object x : Li) {
            c1.remove(x);
            c2.remove(x);
        }

        for (Object key : c1.keySet()) {
            String childPath = path + "/" + key;
            System.out.println("One of the child nodes is extra at path at 1st file: " + childPath);
        }

        for (Object key : c2.keySet()) {
            String childPath = path + "/" + key;
            System.out.println("One of the child nodes is extra at path at 2nd file: " + childPath);
        }
    }
}
