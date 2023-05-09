package de.hpi.naumann.dp2023.uccalgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.results.Result;
import de.metanome.backend.input.file.DefaultFileInputGenerator;
import de.metanome.backend.result_receiver.ResultCache;
import de.metanome.backend.result_receiver.ResultReader;
import de.metanome.backend.result_receiver.ResultReceiver;
import de.metanome.backend.results_db.ResultType;
import jersey.repackaged.com.google.common.collect.Sets;

public class Testrunner {

	private final TestableAlgorithm<? super ResultReceiver> algorithm;
	private final String inputConfigName;
	private final ResultType resultType;

	public Testrunner(TestableAlgorithm<? super ResultReceiver> algorithm, String inputConfigName,
			ResultType resultType) {
		this.algorithm = algorithm;
		this.inputConfigName = inputConfigName;
		this.resultType = resultType;
	}

	public TestResult execute(Dataset dataset) throws Exception {
		try (RelationalInputGenerator inputGenerator = new DefaultFileInputGenerator(dataset.getConfigSetting())) {
			List<ColumnIdentifier> acceptedColumns = getAcceptedColumns(inputGenerator);
			ResultCache resultReceiver = new ResultCache("Testrunner", acceptedColumns);
			algorithm.setResultReceiver(resultReceiver);
			algorithm.setRelationalInputConfigurationValue(inputConfigName, inputGenerator);

			algorithm.execute();

			List<Result> results = resultReceiver.fetchNewResults();
			ClassLoader classLoader = getClass().getClassLoader();
			Path targetPath = Paths.get(classLoader.getResource("gold/" + dataset.name()).toURI());
			if (!Files.exists(targetPath)) {
				throw new FileNotFoundException("Could not find gold standard for " + dataset.name());
			}
			return compareResults(results, targetPath);
		}
	}

	private TestResult compareResults(List<Result> results, Path targetPath) throws IOException {
		ResultReader<? extends Result> reader = new ResultReader<>(resultType);
		Set<Result> fileResults = new HashSet<>(reader.readResultsFromFile(targetPath.toString()));
		Set<Result> resultSet = new HashSet<>(results);
		if (!fileResults.equals(resultSet)) {
			Set<Result> additionalResults = Sets.difference(resultSet, fileResults);
			Set<Result> missingResults = Sets.difference(fileResults, resultSet);
			return new TestResult(additionalResults, missingResults);
		}
		return new TestResult(Collections.emptySet(), Collections.emptySet());
	}

	private static List<ColumnIdentifier> getAcceptedColumns(RelationalInputGenerator relationalInputGenerator)
			throws InputGenerationException, AlgorithmConfigurationException {
		List<ColumnIdentifier> acceptedColumns = new ArrayList<>();
		RelationalInput relationalInput = relationalInputGenerator.generateNewCopy();
		String tableName = relationalInput.relationName();
		for (String columnName : relationalInput.columnNames())
			acceptedColumns.add(new ColumnIdentifier(tableName, columnName));
		return acceptedColumns;
	}

}
