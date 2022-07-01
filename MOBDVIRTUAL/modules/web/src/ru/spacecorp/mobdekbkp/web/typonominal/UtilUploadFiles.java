package ru.spacecorp.mobdekbkp.web.typonominal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class UtilUploadFiles {

    protected void getUploadMap(File uploadfile) {
        List<Map<String, String>> mapList = null;
        mapList = new ArrayList<>();
        Map<String, String> map = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(uploadfile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("plmxml_cl:ICO");
            for (int i = 0; i < nodeList.getLength(); i++) {
                map = new HashMap<>();
                Element element = (Element) nodeList.item(i);
                for (int j = 0; j < element.getChildNodes().getLength(); j++) {
                    NodeList list = element.getChildNodes();
                    Node element1 = list.item(j);
                    if (element1.getNodeName() == "plmxml_cl:Property") {
                        String value = element1.getTextContent().replaceAll("\n", "");
                        String id = element1.getAttributes().getNamedItem("attributeId").getNodeValue();
                        if (value != null && id != null && value != "" && id != "") {
                            map.put(id, value);
                        }
                    }
                }
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (entry.getKey().equals("")) {

                    }
                }
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }


}
