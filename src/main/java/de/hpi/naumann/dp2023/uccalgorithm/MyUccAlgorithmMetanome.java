package de.hpi.naumann.dp2023.uccalgorithm;

import java.util.ArrayList;

import com.google.common.base.Joiner;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.AlgorithmExecutionException;
import de.metanome.algorithm_integration.algorithm_types.UniqueColumnCombinationsAlgorithm;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirement;
import de.metanome.algorithm_integration.configuration.ConfigurationRequirementRelationalInput;
import de.metanome.algorithm_integration.input.RelationalInputGenerator;
import de.metanome.algorithm_integration.result_receiver.UniqueColumnCombinationResultReceiver;

public class MyUccAlgorithmMetanome extends MyUccAlgorithm
		implements UniqueColumnCombinationsAlgorithm, TestableAlgorithm<UniqueColumnCombinationResultReceiver> {

	public enum Identifier {
		INPUT_GENERATOR
	};

	@Override
	public String getAuthors() {
		return "DP2023 students";
	}

	@Override
	public String getDescription() {
		return "Our first UCC algorithm";
	}

	@Override
	public ArrayList<ConfigurationRequirement<?>> getConfigurationRequirements() {
		ArrayList<ConfigurationRequirement<?>> conf = new ArrayList<>();
		conf.add(new ConfigurationRequirementRelationalInput(Identifier.INPUT_GENERATOR.name()));
		return conf;
	}

	@Override
	public void setRelationalInputConfigurationValue(String identifier, RelationalInputGenerator... values)
			throws AlgorithmConfigurationException {
		if (!MyUccAlgorithmMetanome.Identifier.INPUT_GENERATOR.name().equals(identifier)) {
			throw new AlgorithmConfigurationException(
					"Unknown configuration: " + identifier + " -> [" + Joiner.on(',').join(values) + "]");
		}
		this.inputGenerator = values[0];
	}

	@Override
	public void setResultReceiver(UniqueColumnCombinationResultReceiver resultReceiver) {
		this.resultReceiver = resultReceiver;
	}

	@Override
	public void execute() throws AlgorithmExecutionException {
		super.execute();
	}
}
