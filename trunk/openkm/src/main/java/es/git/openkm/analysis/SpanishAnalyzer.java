package es.git.openkm.analysis;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class SpanishAnalyzer extends Analyzer {

	/**
	 * An array containing some common Spanish words that are usually not  
     * useful for searching. Imported from http://www.unine.ch/info/clef/.
     * http://members.unine.ch/jacques.savoy/clef/spanishSmart.txt
     */
	private static final String SPANISH_STOP_WORDS[] = {
		"un", "una", "unas", "unos", "uno", "sobre", "todo", "también", "tras",
		"otro", "algún", "alguno", "alguna",
		"algunos", "algunas", "ser", "es", "soy", "eres", "somos", "sois", "estoy",
		"esta", "estamos", "estais",
		"estan", "en", "para", "atras", "porque", "por qué", "estado", "estaba",
		"ante", "antes", "siendo",
		"ambos", "pero", "por", "poder", "puede", "puedo", "podemos", "podeis",
		"pueden", "fui", "fue", "fuimos",
		"fueron", "hacer", "hago", "hace", "hacemos", "haceis", "hacen", "cada",
		"fin", "incluso", "primero",
		"desde", "conseguir", "consigo", "consigue", "consigues", "conseguimos",
		"consiguen", "ir", "voy", "va",
		"vamos", "vais", "van", "vaya", "bueno", "ha", "tener", "tengo", "tiene",
		"tenemos", "teneis", "tienen",
		"el", "la", "lo", "las", "los", "su", "aqui", "mio", "tuyo", "ellos",
		"ellas", "nos", "nosotros", "vosotros",
		"vosotras", "si", "dentro", "solo", "solamente", "saber", "sabes", "sabe",
		"sabemos", "sabeis", "saben",
		"ultimo", "largo", "bastante", "haces", "muchos", "aquellos", "aquellas",
		"sus", "entonces", "tiempo",
		"verdad", "verdadero", "verdadera", "cierto", "ciertos", "cierta",
		"ciertas", "intentar", "intento",
		"intenta", "intentas", "intentamos", "intentais", "intentan", "dos", "bajo",
		"arriba", "encima", "usar",
		"uso", "usas", "usa", "usamos", "usais", "usan", "emplear", "empleo",
		"empleas", "emplean", "ampleamos",
		"empleais", "valor", "muy", "era", "eras", "eramos", "eran", "modo", "bien",
		"cual", "cuando", "donde",
		"mientras", "quien", "con", "entre", "sin", "trabajo", "trabajar",
		"trabajas", "trabaja", "trabajamos",
		"trabajais", "trabajan", "podria", "podrias", "podriamos", "podrian",
		"podriais", "yo", "aquel", "mi",
		"de", "a", "e", "i", "o", "u"};

	/** 
     * Contains the stopwords used with the StopFilter. 
     */  
    private Set<Object> stopTable = new HashSet<Object>();  
      
    /** 
     * Contains words that should be indexed but not stemmed. 
     */  
    private Set<Object> exclTable = new HashSet<Object>();  
      
    /** 
     * Builds an analyzer with the default stop words. 
     */  
    public SpanishAnalyzer() {  
        stopTable = StopFilter.makeStopSet(SPANISH_STOP_WORDS);  
    }  
  
    /** Builds an analyzer with the given stop words. */  
    public SpanishAnalyzer(String[] stopWords) {  
        stopTable = StopFilter.makeStopSet(stopWords);  
    }  
      
    /** 
     * Builds an analyzer with the given stop words from file. 
     * @throws IOException  
     */  
    public SpanishAnalyzer(File stopWords) throws IOException {  
        stopTable = new HashSet(WordlistLoader.getWordSet(stopWords));  
    }  
      
    /** Constructs a {@link StandardTokenizer} filtered by a {@link 
     * StandardFilter}, a {@link LowerCaseFilter}, a {@link StopFilter} 
     * and a {@link SpanishStemFilter}. */  
    public final TokenStream tokenStream(String fieldName, Reader reader) {  
        TokenStream result = new StandardTokenizer(reader);  
        result = new StandardFilter(result);  
        result = new LowerCaseFilter(result);  
        result = new StopFilter(result, stopTable);  
        result = new SpanishStemFilter(result);  
        return result;  
    }
}
