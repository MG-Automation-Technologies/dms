/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (C) 2006  GIT Consultors
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.git.openkm.kea.modelcreator;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.git.openkm.kea.metadata.WorkspaceHelper;
import es.git.openkm.kea.stemmers.Stemmer;
import es.git.openkm.kea.stopwords.Stopwords;

/**
 * Class that demonstrates how Kea works.
 * Alternatively use the command line as described in readme
 * 
 * @author Olena Medelyan
 *
 */
public class ModelBuilder {
	
	private static Logger log = LoggerFactory.getLogger(ModelBuilder.class);

	private KEAModelBuilder km;
	private KEAKeyphraseExtractor ke;
	
	private void setOptionsTraining(String trainingFilesPath, String modelFilePath, String vocabularyFilePath,
									String vocabularyType, String documentEncoding, String lang, 
									Stemmer stemmer, Stopwords stopwords) {
		
		km = new KEAModelBuilder();
		
		// A. required arguments (no defaults):
		
		// 1. Name of the directory -- give the path to your directory with documents and keyphrases
		//    documents should be in txt format with an extention "txt"
		//    keyphrases with the same name as documents, but extension "key"
		//    one keyphrase per line!
		km.setDirName(trainingFilesPath);
		
		// 2. Name of the model -- give the path to where the model is to be stored and its name
		km.setModelName(modelFilePath);
		 
		// 3. Name of the vocabulary -- name of the file (without extension) that is stored in VOCABULARIES
		//    or "none" if no Vocabulary is used (free keyphrase extraction).		
		km.setVocabulary(vocabularyFilePath);
		
		// 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none", use "skos" or "txt" otherwise.
		km.setVocabularyFormat(vocabularyType);
		
		// B. optional arguments if you want to change the defaults
		// 5. Encoding of the document
		km.setEncoding(documentEncoding);
		
		// 6. Language of the document -- use "es" for Spanish, "fr" for French
		//    or other languages as specified in your "skos" vocabulary 
		km.setDocumentLanguage(lang); // es for Spanish, fr for French
		
		// 7. Stemmer -- adjust if you use a different language than English or if you want to alterate results
		// (We have obtained better results for Spanish and French with NoStemmer) 		
		km.setStemmer(stemmer); 
		
		// 8. Stopwords -- adjust if you use a different language than English!
		km.setStopwords(stopwords);
		
		// 9. Maximum length of a keyphrase
		km.setMaxPhraseLength(5);
		
		// 10. Minimum length of a keyphrase
		km.setMinPhraseLength(1);
		
		// 11. Minumum occurrence of a phrase in the document -- use 2 for long documents!
		km.setMinNumOccur(2);
	
	//  Optional: turn off the keyphrase frequency feature	
	//	km.setUseKFrequency(false);
		
	}
	
	
	private void setOptionsTesting(String m_testdir, String trainingFilesPath, String modelFilePath, String vocabularyFilePath,
								   String vocabularyType, String documentEncoding, String lang, 
								   Stemmer stemmer, Stopwords stopwords) {
		
		ke = new KEAKeyphraseExtractor(stopwords);
		
		// A. required arguments (no defaults):
		
		// 1. Name of the directory -- give the path to your directory with documents
		//    documents should be in txt format with an extention "txt".
		//    Note: keyphrases with the same name as documents, but extension "key"
		//    one keyphrase per line!
		
		ke.setDirName(m_testdir);
		
		// 2. Name of the model -- give the path to the model 
		ke.setModelName(modelFilePath);
		 
		// 3. Name of the vocabulary -- name of the file (without extension) that is stored in VOCABULARIES
		//    or "none" if no Vocabulary is used (free keyphrase extraction).
		ke.setVocabulary(vocabularyFilePath);
		
		// 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none", use "skos" or "txt" otherwise.
		ke.setVocabularyFormat(vocabularyType);
		
		// B. optional arguments if you want to change the defaults
		// 5. Encoding of the document
		ke.setEncoding(documentEncoding);
		
		// 6. Language of the document -- use "es" for Spanish, "fr" for French
		//    or other languages as specified in your "skos" vocabulary 
		ke.setDocumentLanguage(lang); // es for Spanish, fr for French
		
		// 7. Stemmer -- adjust if you use a different language than English or want to alterate results
		// (We have obtained better results for Spanish and French with NoStemmer)
		ke.setStemmer(stemmer);
		
		// 8. Stopwords
		ke.setStopwords(stopwords);
		
		// 9. Number of Keyphrases to extract
		ke.setNumPhrases(10);
		
		// 10. Set to true, if you want to compute global dictionaries from the test collection
		ke.setBuildGlobal(false);			
	}

	/**
	 * createModel
	 */
	private void createModel(Stopwords stopwords) {
		try {
			km.buildModel(km.collectStems(), stopwords);
			km.saveModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * extractKeyphrases
	 */
	private void extractKeyphrases() {
		try {
			ke.loadModel();
			ke.extractKeyphrases(ke.collectStems());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Example file on how to use KEA from your Java code
	 * with sample documents included in this package.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ModelBuilder modelBuilder = new ModelBuilder();
		
		String trainingFilesPath = "testdocs/en/train";
		String modelFilePath = "vocabulary/agrovoc.model";
		String vocabularyFilePath = new StringBuilder().append(WorkspaceHelper.getWorkingDir())
		  											   .append(File.separator)
		  											   .append("vocabulary")
		  											   .append(File.separator)
		  											   .append("agrovoc.rdf").toString();
		String vocabularyType = "skos";
		String documentEncoding = "UTF-8";
		String lang = "en";
		
		String stemerClassName = "es.git.openkm.kea.stemmers.PorterStemmer";
		Stemmer stemmer = null;
		if (stemerClassName != null) {
			try {
				Class clazz = Class.forName(stemerClassName);
				stemmer = (Stemmer) clazz.newInstance();
			} catch (Exception e) {
				log.error("Error creating class instance", e);
			}
		}
		
		// Must change stopwords path used by stopwords class
		WorkspaceHelper.KEA_STOPWORDS_PATH = new StringBuilder().append(WorkspaceHelper.getWorkingDir())
		   														.append(File.separator)
		   														.append("vocabulary")
		   														.append(File.separator)
		   														.append("stopwords_en.txt").toString();
		
		String stopwordsClassName = "es.git.openkm.kea.stopwords.StopwordsEnglish";
        Stopwords stopwords = null;
		if (stopwordsClassName != null) {
			try {
				Class clazz = Class.forName(stopwordsClassName);
				stopwords = (Stopwords) clazz.newInstance();
			} catch (Exception e) {
				log.error("Error creating class instance", e);
			}
		}
		
		String m_testdir = "testdocs/en/test";
		
		// to create a model from manually indexed documents,
		log.info("Creating the model... ");
		modelBuilder.setOptionsTraining(trainingFilesPath, modelFilePath, vocabularyFilePath, vocabularyType, documentEncoding, 
										lang, stemmer, stopwords);
		modelBuilder.createModel(stopwords);
		
		// to extract keyphrases from new documents,
		log.info("Extracting keyphrases from test documents... ");

		modelBuilder.setOptionsTesting(m_testdir,trainingFilesPath, modelFilePath, vocabularyFilePath, vocabularyType, documentEncoding, 
									   lang, stemmer, stopwords);
		modelBuilder.extractKeyphrases();
		log.info("Look into " + m_testdir + " to see the results");
		log.info("and compare them to " + m_testdir + "/manual_keyphrases/.");
	}
}