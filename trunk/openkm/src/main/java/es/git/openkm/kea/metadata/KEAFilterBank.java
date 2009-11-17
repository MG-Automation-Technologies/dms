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

package es.git.openkm.kea.metadata;

import es.git.openkm.kea.filter.KEAFilter;
import es.git.openkm.kea.stemmers.SremovalStemmer;
import es.git.openkm.kea.stemmers.Stemmer;
import es.git.openkm.kea.stopwords.StopwordsEnglish;
import es.git.openkm.kea.stopwords.Stopwords;

import java.util.HashMap;
import java.util.Date;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KEAFilterBank {

	private static Logger log = LoggerFactory.getLogger(KEAFilterBank.class);

    public static String IPSV = "IPSV";
    public static String GEN = "NONE";
    public static String ISMT = "ISMT";

    private static KEAFilterBank instance;

    private HashMap<String, KEAFilter> filters = new HashMap<String, KEAFilter>();


    public static KEAFilterBank getInstance() throws MetadataExtractionException {
        if (instance==null) {
            instance = new KEAFilterBank();
        }
        return instance;
    }

    private KEAFilterBank() throws MetadataExtractionException {
        Date start = new Date();
        // Comentado ahora solo generamos el filtro ismt
        //KEAFilter ipsvFilter = buildIPSVFilter();
        //filters.put(IPSV,ipsvFilter);
        //KEAFilter generalFilter = buildGeneralFilter();
        //filters.put(GEN,generalFilter);
        KEAFilter ismtFilter = buildIsmtFilter();
        filters.put(ISMT,ismtFilter);
        Date stop = new Date();
        long time = (stop.getTime() - start.getTime());
        log.info("KEA filters built in " + time + "ms");
    }

    public static KEAFilter getFilter(String filterID) throws MetadataExtractionException {
        log.info("Using " + filterID + " filter");
        return getInstance().filters.get(filterID);
    }



    private KEAFilter buildIPSVFilter() throws MetadataExtractionException {

        KEAFilter filter = null;
        Stemmer stemmer = new SremovalStemmer();
        Stopwords stopwords = new StopwordsEnglish();
        try {

            String modelPath = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                                  .append(File.separator).append("ipsv_model")
                                                  .toString();
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream(modelPath));
            ObjectInputStream ois = new ObjectInputStream(bis);
            filter = (KEAFilter) ois.readObject();
            String vocabulary = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                            .append(File.separator)
                                            .append("ipsv-skos.rdf")
                                            .toString();
            filter.setVocabulary(vocabulary);
            filter.setDocumentLanguage("en");
            filter.setVocabularyFormat("skos");
            filter.setStemmer(stemmer);
            filter.setStopwords(stopwords);
            filter.loadThesaurus(stemmer,stopwords);
            filter.setNumPhrases(12);

            return filter;

        } catch (FileNotFoundException e) {
            log.error("Unable to find IPSV KEA model file",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for details.");
        } catch (IOException e) {
            log.error("Cannot read IPSV KEA model from stream",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (ClassNotFoundException e) {
            log.error("problem loading IPSV KEA model.",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        }
    }

    private KEAFilter buildGeneralFilter() throws MetadataExtractionException {

        KEAFilter filter = null;
        Stemmer stemmer = new SremovalStemmer();
        Stopwords stopwords = new StopwordsEnglish();
        try {

            String modelPath = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                                  .append(File.separator).append("general_model")
                                                  .toString();
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream(modelPath));
            ObjectInputStream ois = new ObjectInputStream(bis);
            filter = (KEAFilter) ois.readObject();
            String vocabulary = "none";
            filter.setVocabulary(vocabulary);
            filter.setVocabularyFormat(null);
            filter.setDocumentLanguage("en");
            filter.setStemmer(stemmer);
            filter.setStopwords(stopwords);
            filter.setNumPhrases(5);

            return filter;

        } catch (FileNotFoundException e) {
            log.error("Unable to find General KEA model file",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for details.");
        } catch (IOException e) {
            log.error("Cannot read genenal KEA model from stream",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (ClassNotFoundException e) {
            log.error("Class cast- General KEA model.",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (Throwable e) {
            log.error("Unexpected error with general model",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        }
    }

    private KEAFilter buildIsmtFilter() throws MetadataExtractionException {

        KEAFilter filter = null;
        Stemmer stemmer = new SremovalStemmer();
        Stopwords stopwords = new StopwordsEnglish();
        try {

            String modelPath = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                                  .append(File.separator).append("ismt_model")
                                                  .toString();
            BufferedInputStream bis =
                    new BufferedInputStream(new FileInputStream(modelPath));
            ObjectInputStream ois = new ObjectInputStream(bis);
            filter = (KEAFilter) ois.readObject();
            String vocabulary = new StringBuilder().append(WorkspaceHelper.getDataDir())
                                            .append(File.separator)
                                            .append("ismt.rdf")
                                            .toString();
            
            filter.setVocabulary(vocabulary);
            filter.setVocabularyFormat("skos");
            filter.setDocumentLanguage("en");
            filter.setStemmer(stemmer);
            filter.setStopwords(stopwords);
            filter.loadThesaurus(stemmer,stopwords);
            filter.setNumPhrases(5);

            return filter;

        } catch (FileNotFoundException e) {
            log.error("Unable to find ISMT KEA model file",e);
            e.printStackTrace();
            throw new MetadataExtractionException("Subject Extraction failed (see trace for details.");
        } catch (IOException e) {
            log.error("Cannot read ISMT KEA model from stream",e);
            e.printStackTrace();
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
            log.error("Class cast- ISMT KEA model.",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (Throwable e) {
        	e.printStackTrace();
            log.error("Unexpected error with ISMT model",e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        }
    }

}
