package de.hpi.naumann.dp2023.uccalgorithm;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Collections;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import de.metanome.backend.results_db.ResultType;

public class MyUccAlgorithmTest {

	@ParameterizedTest
	@EnumSource(value = Dataset.class)
	public void testSimple(Dataset ds) throws Exception {
		TestResult result = getTestrunner().execute(ds);
		assertAll("result",
				() -> assertIterableEquals(Collections.emptySet(), result.getMissingResults(), "there are missing results: " + result.getMissingResults()),
				() -> assertIterableEquals(Collections.emptySet(), result.getAddititionalResults(),
						"found additional results: " + result.getAddititionalResults()));

	}

	private Testrunner getTestrunner() {
		return new Testrunner(new MyUccAlgorithmMetanome(), MyUccAlgorithmMetanome.Identifier.INPUT_GENERATOR.name(),
				ResultType.UCC);
	}
}
