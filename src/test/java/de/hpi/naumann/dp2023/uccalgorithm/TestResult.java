package de.hpi.naumann.dp2023.uccalgorithm;

import java.util.Set;

import de.metanome.algorithm_integration.results.Result;

class TestResult {

	private final Set<Result> addititionalResults;
	private final Set<Result> missingResults;

	public TestResult(Set<Result> addititionalResults, Set<Result> missingResults) {
		this.addititionalResults = addititionalResults;
		this.missingResults = missingResults;
	}

	public Set<Result> getAddititionalResults() {
		return addititionalResults;
	}

	public Set<Result> getMissingResults() {
		return missingResults;
	}

}