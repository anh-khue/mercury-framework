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

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(config);
            document.getDocumentElement().normalize();

            Node node = document.getElementsByTagName("Connection").item(0);

            createConnectionInfor(node);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return connectionInfor;
    }

    private static void createConnectionInfor(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String dbms = element.getElementsByTagName("dbms").item(0).getTextContent();
            String driverClass = element.getElementsByTagName("driver").item(0).getTextContent();
            String url = element.getElementsByTagName("url").item(0).getTextContent();
            String username = element.getElementsByTagName("username").item(0).getTextContent();
            String password = element.getElementsByTagName("password").item(0).getTextContent();

            connectionInfor = new ConnectionInfor(dbms, driverClass, url, username, password);
        }
    }

    public static ConnectionInfor getConnectionInfor() {
        return connectionInfor;
    }
}
