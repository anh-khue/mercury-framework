package framework.util;

import framework.builder.abstract_builder.QueryBuilder;
import framework.config.ConnectionConfig;
import framework.config.ConnectionInfor;
import framework.model_mapping.model.Model;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class Query implements Serializable {

    private static QueryBuilder queryBuilder = getQueryBuilder();

    public static QueryBuilder table(Class<? extends Model> model) {
        return queryBuilder.table(model);
    }

    private static QueryBuilder getQueryBuilder() {
        ConnectionInfor connectionInfor = ConnectionConfig.getConnectionInfor();
        Class<? extends QueryBuilder> implementationClass =
                QueryBuilderProvider.getQueryBuilderImplementation(connectionInfor.getDbms());
        try {
            assert implementationClass != null;
//            return implementationClass.newInstance();
            return implementationClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class QueryBuilderProvider {

        private static Class<? extends QueryBuilder> getQueryBuilderImplementation(String dbms) {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource("framework/config/querybuilder_provider.xml");
            assert url != null;
            File config = new File(url.getFile());

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;

            try {
                documentBuilder = builderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(config);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("implementation");

                String className = getImplementationClass(nodeList, dbms);
                return (Class<? extends QueryBuilder>) Class.forName(className);
            } catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        private static String getImplementationClass(NodeList nodeList, String dbms) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodeList.item(i);
                    String dbmsName = element.getElementsByTagName("dbms-name").item(0).getTextContent();
                    if (dbms.equalsIgnoreCase(dbmsName)) {
                        return element.getElementsByTagName("class-name").item(0).getTextContent();
                    }
                }
            }
            return "No implementation found!";
        }
    }
}
