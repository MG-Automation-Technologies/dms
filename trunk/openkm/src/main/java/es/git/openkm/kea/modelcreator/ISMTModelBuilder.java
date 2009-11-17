
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

import es.git.openkm.kea.stemmers.PorterStemmer;
import es.git.openkm.kea.stopwords.StopwordsEnglish;
import es.git.openkm.kea.metadata.WorkspaceHelper;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: gregory
 * Date: 11-Dec-2008
 * Time: 17:52:16
 * To change this template use File | Settings | File Templates.
 */
public class ISMTModelBuilder {
    private KEAModelBuilder km;

	private void setUpModelBuilder() {

        km = new KEAModelBuilder();

		// A. required arguments (no defaults):

		// 1. Name of the directory -- give the path to your directory with documents and keyphrases
		//    documents should be in txt format with an extention "txt"
		//    keyphrases with the same name as documents, but extension "key"
		//    one keyphrase per line!
        km.setDirName("/home/jllort/Escritorio/resources");

		// 2. Name of the model -- give the path to where the model is to be stored and its name
		km.setModelName("/home/jllort/Escritorio/resources/ismt_model");

		// 3. Name of the vocabulary -- name of the file (without extension) that is stored in VOCABULARIES
		//    or "none" if no Vocabulary is used (free keyphrase extraction).
        //km.setVocabulary("ipsv-skos");
        String vocabPath = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                              .append(File.separator)
                                              .append("ismt.rdf").toString();
        
        vocabPath="/home/jllort/Escritorio/resources/ismt.rdf";
        
        
        System.out.println(vocabPath);


        km.setVocabulary(vocabPath);

        // 4. Format of the vocabulary in 3. Leave empty if vocabulary = "none", use "skos" or "txt" otherwise.
		//km.setVocabularyFormat("");
        km.setVocabularyFormat("skos");

//		 B. optional arguments if you want to change the defaults
		// 5. Encoding of the document
		km.setEncoding("UTF-8");

		// 6. Language of the document -- use "es" for Spanish, "fr" for French
		//    or other languages as specified in your "skos" vocabulary
		km.setDocumentLanguage("en"); // es for Spanish, fr for French

		// 7. Stemmer -- adjust if you use a different language than English or if you want to alterate results
		// (We have obtained better results for Spanish and French with NoStemmer)
		km.setStemmer(new PorterStemmer());

		// 8. Stopwords -- adjust if you use a different language than English!
		km.setStopwords(new StopwordsEnglish());

		// 9. Maximum length of a keyphrase
		km.setMaxPhraseLength(5);

		// 10. Minimum length of a keyphrase
		km.setMinPhraseLength(1);

		// 11. Minumum occurrence of a phrase in the document -- use 2 for long documents!
		km.setMinNumOccur(1);

	}

	private void createModel() {
		try {
			km.buildModel(km.collectStems());
			km.saveModel();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

    public static void main(String[] args) {

        ISMTModelBuilder test = new ISMTModelBuilder();

        System.err.println("Creating the model... ");
        test.setUpModelBuilder();
        test.createModel();

    }

}
