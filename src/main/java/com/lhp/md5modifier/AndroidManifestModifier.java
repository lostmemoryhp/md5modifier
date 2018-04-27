package com.lhp.md5modifier;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liaohaiping
 * @Description:
 * @Date: Created in 2018/4/27 0027 16:32
 */
public class AndroidManifestModifier implements FileModifier {
    @Override
    public boolean canModify(File file) {
        return FileUtil.getFileExt(file).equalsIgnoreCase(".xml");
    }

    @Override
    public void modify(File file) {
        System.out.println("文件：" + file.getAbsolutePath());
        System.out.println("变更前md5： " + Md5Util.getMd5ByFile(file));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {

            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Element rootElement = doc.getDocumentElement();
            rootElement.normalize();
            NodeList childNodes = rootElement.getChildNodes();
            List<Node> removeList = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == Node.TEXT_NODE) {
                    if(item.getNodeValue().equals("\n")){
                        removeList.add(item);
                    }
                    continue;
                } else if (item.getNodeType() == Node.COMMENT_NODE) {
                    String nodeValue = item.getNodeValue();
                    if (AppendContentBuilder.isAppendContent(nodeValue)) {
                        removeList.add(item);
                    }
                    break;
                } else {
                    break;
                }
            }
            removeList.forEach(node->rootElement.removeChild(node));

            Comment comment = doc.createComment(AppendContentBuilder.newAppendContent());
            rootElement.insertBefore(comment, rootElement.getFirstChild());

            //write the updated document to file or console
            rootElement.normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(file);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("变更后md5： " + Md5Util.getMd5ByFile(file));
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    }

}
