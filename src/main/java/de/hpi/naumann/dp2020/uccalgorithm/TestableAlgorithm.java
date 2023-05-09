package de.hpi.naumann.dp2020.uccalgorithm;

import de.metanome.algorithm_integration.algorithm_types.RelationalInputParameterAlgorithm;

public interface TestableAlgorithm<K> extends RelationalInputParameterAlgorithm {

	public void setResultReceiver(K resultReceiver);

}
