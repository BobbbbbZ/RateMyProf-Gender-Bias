package cpen221.mp1.ngrams;

import java.text.BreakIterator;
import java.util.*;

public class NGrams {
    private String[] texts;
    private List<String[]> words = new ArrayList<String[]>();

    /**
     * Create an NGrams object
     * Read words from the text and add them into arraylist
     *
     * @param text all the text to analyze and create n-grams from;
     *             is not null and is not empty.
     */
    public NGrams(String[] text) {
        this.texts = text;
        for (String t : texts) {
            words.add(getWords(t));
        }
    }

    /**
     * Get words from the text and put them into arraylist
     *
     * @param text origin text provided, is not null or the empty string
     * @return a list of words
     */
    private String[] getWords(String text) {
        ArrayList<String> words = new ArrayList<>();
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(text);
        int start = wb.first();
        for (int end = wb.next();
             end != BreakIterator.DONE;
             start = end, end = wb.next()) {
            String word = text.substring(start, end).toLowerCase();
            word = word.replaceAll("^\\s*\\p{Punct}+\\s*", "").replaceAll("\\s*\\p{Punct}+\\s*$", "");
            word = word.replaceAll(" ", "");
            if (!word.equals("")) {
                words.add(word);
            }
        }
        String[] wordsArray = new String[words.size()];
        words.toArray(wordsArray);
        return wordsArray;
    }

    /**
     * Obtain the total number of unique 1-grams,
     * 2-grams, ..., n-grams.
     * <p>
     * Specifically, if there are m_i i-grams,
     * obtain sum_{i=1}^{n} m_i.
     *
     * @return the total number of 1-grams,
     * 2-grams, ..., n-grams
     */
    public long getTotalNGramCount(int n) {
        int sum = 0;
        ArrayList<String> dup = new ArrayList<String>();
        for (int j = 1; j <= n; j++) {
            for (String[] text : words) {
                if (text.length >= j) {
                    for (int i = 0; i < text.length - j + 1; i++) {
                        String[] ngram = Arrays.copyOfRange(text, i, i + j);
                        String ngram_str = String.join(" ", ngram);
                        if (!dup.contains(ngram_str)) {
                            dup.add(ngram_str);
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Get the n-grams, as a List, with the i-th entry being
     * all the (i+1)-grams and their counts.
     *
     * @return a list of n-grams and their associated counts,
     * with the i-th entry being all the (i+1)-grams and their counts
     */
    public List<Map<String, Long>> getAllNGrams() {
        int n = 1;
        for (String[] word : words) {
            n = Math.max(word.length, n);
        }
        List<Map<String, Long>> mapList = new ArrayList<Map<String, Long>>();
        for (int j = 1; j <= n; j++) {
            Map<String, Long> ngramMap = new HashMap<String, Long>();
            for (String[] text : words) {
                if (text.length >= j) {
                    for (int i = 0; i < text.length - j + 1; i++) {
                        String[] ngram = Arrays.copyOfRange(text, i, i + j);
                        String ngram_str = String.join(" ", ngram);
                        if (!ngramMap.containsKey(ngram_str)) {
                            ngramMap.put(ngram_str, 1L);
                        } else {
                            ngramMap.put(ngram_str, ngramMap.get(ngram_str) + 1);
                        }
                    }
                }
            }
            mapList.add(ngramMap);
        }
        return mapList;
    }

    /**
     * Get the n-grams, as a List, with the i-th entry being
     * up to 3-gram and their counts
     *
     * @return a list of 1 to 3-gram and their associated counts,
     * with the i-th entry being all the (i+1)-grams and their counts
     */
    public List<Map<String, Long>> getQueryNGrams() {
        List<Map<String, Long>> mapList = new ArrayList<Map<String, Long>>();
        for (int j = 1; j <= 3; j++) {
            Map<String, Long> ngramMap = new HashMap<String, Long>();
            for (String[] text : words) {
                if (text.length >= j) {
                    for (int i = 0; i < text.length - j + 1; i++) {
                        String[] ngram = Arrays.copyOfRange(text, i, i + j);
                        String ngram_str = String.join(" ", ngram);
                        if (!ngramMap.containsKey(ngram_str)) {
                            ngramMap.put(ngram_str, 1L);
                        } else {
                            ngramMap.put(ngram_str, ngramMap.get(ngram_str) + 1);
                        }
                    }
                }
            }
            mapList.add(ngramMap);
        }
        return mapList;
    }

    /**
     * Get the 1-gram, as a List
     *
     * @return a list of 1-gram and their associated counts
     */
    public Map<String, Long> getBagOfWords() {
        Map<String, Long> ngramMap = new HashMap<String, Long>();
        for (String[] text : words) {
            for (String ngram : text) {
                if (!ngramMap.containsKey(ngram)) {
                    ngramMap.put(ngram, 1L);
                } else {
                    ngramMap.put(ngram, ngramMap.get(ngram) + 1);
                }
            }
        }
        return ngramMap;
    }
}
