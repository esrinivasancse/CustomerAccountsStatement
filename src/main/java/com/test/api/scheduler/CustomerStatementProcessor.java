package com.test.api.scheduler;

import static com.test.api.constants.Constants.BATCH_FILE_FLAG_PATH;
import static com.test.api.constants.Constants.CSV_FILE_OUTPUT_PATH;
import static com.test.api.constants.Constants.STATUS_FAILED;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.test.api.models.Record;
import com.test.api.models.Report;

/**
 * The Class CustomerStatementProcessor.
 */
@Component
public class CustomerStatementProcessor {

	/** The df 2. */
	private static DecimalFormat df2 = new DecimalFormat("#.##");

	/** The logger. */
	Logger logger = LogManager.getLogger(CustomerStatementProcessor.class);

	/** The customer records CSV. */
	@Autowired
	private CustomerRecordsCSV customerRecordsCSV;

	/** The customer records XML. */
	@Autowired
	private CustomerRecordsXML customerRecordsXML;

	/**
	 * Instantiates a new customer statement processor.
	 */
	public CustomerStatementProcessor() {
	}

	/**
	 * Instantiates a new customer statement processor.
	 *
	 * @param customerRecordsCSV
	 *            the customer records CSV
	 * @param customerRecordsXML
	 *            the customer records XML
	 */
	public CustomerStatementProcessor(CustomerRecordsCSV customerRecordsCSV, CustomerRecordsXML customerRecordsXML) {
		this.customerRecordsCSV = customerRecordsCSV;
		this.customerRecordsXML = customerRecordsXML;
	}

	/**
	 * Start customer statement processor.
	 */
	@Scheduled(cron = "${cron.value}")
	public void startCustomerStatementProcessor() {
		List<Record> allRecords = new ArrayList<>();
		File flag = new File(BATCH_FILE_FLAG_PATH);
		if (!flag.exists()) {
			try {
				flag.createNewFile();
				List<Record> csvRecords = customerRecordsCSV.loadCustomerRecordsFromCsv();
				List<Record> xmlRecords = customerRecordsXML.loadCustomerRecordsFromXml();
				allRecords.addAll(csvRecords);
				allRecords.addAll(xmlRecords);
				Map<Integer, Integer> referenceNoCountMap = findReferenceNumberCount(allRecords);
				List<Report> reports = generateFailedRecordsReport(allRecords, referenceNoCountMap);
				generateCsv(reports);
				flag.delete();
			} catch (Exception ex) {
				logger.error("Exception occured while processing : {}", ex.getMessage());
			}
		} else {
			logger.info("### Already one Batch is Running ###");
		}
	}

	/**
	 * Generate csv.
	 *
	 * @param reports
	 *            the reports
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CsvDataTypeMismatchException
	 *             the csv data type mismatch exception
	 * @throws CsvRequiredFieldEmptyException
	 *             the csv required field empty exception
	 */
	private void generateCsv(List<Report> reports)
			throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		try (Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_OUTPUT_PATH))) {
			StatefulBeanToCsv<Report> beanToCsv = new StatefulBeanToCsvBuilder<Report>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(reports);
		}
	}

	/**
	 * Generate failed records report.
	 *
	 * @param allRecords
	 *            the all records
	 * @param referenceNoCountMap
	 *            the reference no count map
	 * @return the list
	 */
	private List<Report> generateFailedRecordsReport(List<Record> allRecords,
			Map<Integer, Integer> referenceNoCountMap) {
		List<Report> reports = new ArrayList<>();
		for (Record record : allRecords) {
			Double startBalance = record.getStartBalance();
			Double mutation = record.getMutation();
			Double endBalance = record.getEndBalance();
			df2.setRoundingMode(RoundingMode.UP);
			Double computedEndBalance = startBalance + mutation;
			Double computedEndBalanceRoundedUp = new Double(df2.format(computedEndBalance));
			if (!computedEndBalanceRoundedUp.equals(endBalance)
					|| referenceNoCountMap.get(record.getTransactionReferenceNumber()) > 1) {
				Report report = new Report();
				report.setTransactionReferenceNumber(record.getTransactionReferenceNumber());
				report.setDescription(record.getDescription());
				report.setStatus(STATUS_FAILED);
				reports.add(report);
			}
		}
		return reports;
	}

	/**
	 * Find reference number count.
	 *
	 * @param allRecords
	 *            the all records
	 * @return the map
	 */
	private Map<Integer, Integer> findReferenceNumberCount(List<Record> allRecords) {
		Map<Integer, Integer> referenceNumberCountMap = new HashMap<>();
		for (Record record : allRecords) {
			if (referenceNumberCountMap.isEmpty()) {
				referenceNumberCountMap.put(record.getTransactionReferenceNumber(), 1);
			} else {
				referenceNumberCountMap.put(record.getTransactionReferenceNumber(),
						referenceNumberCountMap.getOrDefault(record.getTransactionReferenceNumber(), 0) + 1);
			}
		}
		return referenceNumberCountMap;
	}
}
