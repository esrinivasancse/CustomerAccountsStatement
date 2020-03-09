package com.test.api.scheduler;

import static com.test.api.constants.Constants.XML_FILE_PATH;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.test.api.models.Record;

/**
 * The Class CustomerRecordsXML.
 */
@Component
public class CustomerRecordsXML {

	/** The logger. */
	Logger logger = LogManager.getLogger(CustomerRecordsCSV.class);

	/**
	 * Load customer records from xml.
	 *
	 * @return the list
	 */
	public List<Record> loadCustomerRecordsFromXml() {
		List<Record> records = new ArrayList<Record>();
		try {
			File file = new File(XML_FILE_PATH);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nList = document.getElementsByTagName("record");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Record record = new Record();
					record.setTransactionReferenceNumber(Integer.parseInt(eElement.getAttribute("reference")));
					record.setAccountNumber(eElement.getElementsByTagName("accountNumber").item(0).getTextContent());
					record.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
					record.setStartBalance(
							Double.parseDouble(eElement.getElementsByTagName("startBalance").item(0).getTextContent()));
					record.setMutation(
							Double.parseDouble(eElement.getElementsByTagName("mutation").item(0).getTextContent()));
					record.setEndBalance(
							Double.parseDouble(eElement.getElementsByTagName("endBalance").item(0).getTextContent()));
					records.add(record);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occured while processing XML Records : {}", e.getMessage());
			records = Collections.emptyList();
		}
		return records;
	}

}
