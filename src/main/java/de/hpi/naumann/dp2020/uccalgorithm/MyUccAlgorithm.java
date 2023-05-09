package de.hpi.naumann.dp2020.uccalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.ColumnIdentifier;
import de.metanome.algorithm_integration.input.InputGenerationException;
import de.metanome.algorithm_integration.input.InputIterationException;
import de.metanome.algorithm_integration.input.RelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.ColumnNameMismatchException;
import de.metanome.algorithm_integration.result_receiver.CouldNotReceiveResultException;
import de.metanome.algorithm_integration.result_receiver.UniqueColumnCombinationResultReceiver;
import de.metanome.algorithm_integration.results.UniqueColumnCombination;

public class MyUccAlgorithm {
	protected RelationalInputGenerator inputGenerator = null;
	protected UniqueColumnCombinationResultReceiver resultReceiver = null;

	protected String relationName;
	protected List<String> columnNames;

	public void execute() throws AlgorithmExecutionException {

		////////////////////////////////////////////
		// THE DISCOVERY ALGORITHM LIVES HERE :-) //
		////////////////////////////////////////////

		// Example: Initialize
		initialize();

		// Example: Read input data
		List<List<String>> records = readInput();

		// Example: Print what the algorithm read (to test that everything works)
		print(records);

		// Example: Generate some results (usually, the algorithm should really
		// calculate them on the data)
		List<UniqueColumnCombination> results = generateResults();

		// Example: To test if the algorithm outputs results
		emit(results);

		/////////////////////////////////////////////

	}

	protected void initialize() throws InputGenerationException, AlgorithmConfigurationException {
		RelationalInput input = inputGenerator.generateNewCopy();
		relationName = input.relationName();
		columnNames = input.columnNames();
	}

	protected List<List<String>> readInput()
			throws InputGenerationException, AlgorithmConfigurationException, InputIterationException {
		List<List<String>> records = new ArrayList<>();
		RelationalInput input = inputGenerator.generateNewCopy();
		while (input.hasNext())
			records.add(input.next());
		return records;
	}

	protected void print(List<List<String>> records) {
		System.out.println();

		// Print schema
		System.out.print(relationName + "(");
		System.out.print(String.join(",", columnNames));
		System.out.println(")");

		// Print records
		for (List<String> record : records) {
			System.out.print("|");
			System.out.print(String.join("|", record));
			System.out.println("|");
		}
	}

	protected List<UniqueColumnCombination> generateResults() {
		UniqueColumnCombination exampleUcc = new UniqueColumnCombination(
				new ColumnIdentifier(relationName, columnNames.get(0)));
		return Collections.singletonList(exampleUcc);
	}

	protected void emit(List<UniqueColumnCombination> results)
			throws CouldNotReceiveResultException, ColumnNameMismatchException {
		for (UniqueColumnCombination od : results)
			resultReceiver.receiveResult(od);
	}

}
