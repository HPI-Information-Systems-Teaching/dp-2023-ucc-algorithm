package de.hpi.naumann.dp2020.uccalgorithm;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import de.metanome.algorithm_integration.AlgorithmConfigurationException;
import de.metanome.algorithm_integration.configuration.ConfigurationSettingFileInput;

public enum Dataset {
	AGE("WDC_age", ',', true), APPEARANCES("WDC_appearances", ',', true), ASTROLOGY("WDC_astrology", ',', true),
	ASTRONOMICAL("WDC_astronomical", ',', true), GAME("WDC_game", ',', true), KEPLER("WDC_kepler", ',', true),
	PLANETS("WDC_planets", ',', true), SATELLITES("WDC_satellites", ',', true), SYMBOLS("WDC_symbols", ',', true),
	SCIENCE("WDC_science", ',', true), TEST_NULL("test_null", ',', true),;

	private final String inputDatasetName;
	private final String inputFolderPath = "data" + File.separator;
	private final String inputFileEnding = ".csv";
	private final String inputFileNullString = "";
	private final char inputFileSeparator;
	private final char inputFileQuotechar = '\"';
	private final char inputFileEscape = '\\';
	private final int inputFileSkipLines = 0;
	private final boolean inputFileStrictQuotes = false;
	private final boolean inputFileIgnoreLeadingWhiteSpace = true;
	private final boolean inputFileHasHeader;
	private final boolean inputFileSkipDifferingLines = true;

	Dataset(String filename, char inputFileSeperator, boolean hasHeader) {
		this.inputDatasetName = filename;
		this.inputFileSeparator = inputFileSeperator;
		this.inputFileHasHeader = hasHeader;
	}

	public ConfigurationSettingFileInput getConfigSetting() throws AlgorithmConfigurationException, URISyntaxException {
		String filename = inputFolderPath + inputDatasetName + inputFileEnding;
		ClassLoader classLoader = getClass().getClassLoader();
		String file = Paths.get(classLoader.getResource(filename).toURI()).toAbsolutePath().toString();

		return new ConfigurationSettingFileInput(file, true, inputFileSeparator, inputFileQuotechar, inputFileEscape,
				inputFileStrictQuotes, inputFileIgnoreLeadingWhiteSpace, inputFileSkipLines, inputFileHasHeader,
				inputFileSkipDifferingLines, inputFileNullString);
	}
}