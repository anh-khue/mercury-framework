package framework.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public class ConnectionConfig implements Serializable {
    private static ConnectionInfor connectionInfor = makeConnectionInfor();

    private static ConnectionInfor makeConnectionInfor() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("app/config.xml");
        File config = new File(url.getFile());

        System.out.println(config.getAbsolutePath());

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(config);
            document.getDocumentElement().normalize();

            System.out.println("Root: " + document.getDocumentElement().getNodeName());
            NodeList nodeList = document.getElementsByTagName("Connection");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                System.out.println(node.getNodeName());
            }

            Node node = document.getElementsByTagName("Connection").item(0);

            return getConnectionInfor(node);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ConnectionInfor getConnectionInfor(Node node) {
        ConnectionInfor connectionInfor = null;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String driverClass = element.getElementsByTagName("driver").item(0).getTextContent();
            System.out.println(driverClass);
            String url = element.getElementsByTagName("url").item(0).getTextContent();
            System.out.println(url);
            String username = element.getElementsByTagName("username").item(0).getTextContent();
            System.out.println(username);
            String password = element.getElementsByTagName("password").item(0).getTextContent();
            System.out.println(password);

            connectionInfor = new ConnectionInfor(driverClass, url, username, password);
        }
        return connectionInfor;
    }

    public static ConnectionInfor getConnectionInfor() {
        return connectionInfor;
    }
}
