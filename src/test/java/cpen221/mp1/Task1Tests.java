package cpen221.mp1.ngrams;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1 {

    @Test
    public void testTrivial() {
        String[] words = {"who"};
        NGrams ngrams = new NGrams(words);

        List<Map<String, Long>> expected = List.of(
                Map.of("who", 1L)
        );
        assertEquals(expected, ngrams.getAllNGrams());
    }

    @Test
    public void testNoRepeats() {
        String[] words = {"where in the world is carmen sandiego"};
        NGrams ngrams = new NGrams(words);

        List<Map<String, Long>> expected = List.of(
                Map.of(
                        "where", 1L,
                        "in", 1L,
                        "the", 1L,
                        "world", 1L,
                        "is", 1L,
                        "carmen", 1L,
                        "sandiego", 1L
                ),
                Map.of(
                        "where in", 1L,
                        "in the", 1L,
                        "the world", 1L,
                        "world is", 1L,
                        "is carmen", 1L,
                        "carmen sandiego", 1L
                ),
                Map.of(
                        "where in the", 1L,
                        "in the world", 1L,
                        "the world is", 1L,
                        "world is carmen", 1L,
                        "is carmen sandiego", 1L
                ),
                Map.of(
                        "where in the world", 1L,
                        "in the world is", 1L,
                        "the world is carmen", 1L,
                        "world is carmen sandiego", 1L
                ),
                Map.of(
                        "where in the world is", 1L,
                        "in the world is carmen", 1L,
                        "the world is carmen sandiego", 1L
                ),
                Map.of(
                        "where in the world is carmen", 1L,
                        "in the world is carmen sandiego", 1L
                ),
                Map.of(
                        "where in the world is carmen sandiego", 1L
                )
        );

        assertEquals(expected, ngrams.getAllNGrams());
    }

    @Test
    public void testInterestingSentence1() {
        // also tests for basic case conversion and punctuation
        String[] lydiaDavis = {"At a certain point in her life,", "she realises",
                "it is not so much that she wants to have a child",
                "as that she does not want not to have a child,", "or not to have had a child."};

        NGrams ngrams = new NGrams(lydiaDavis);

        Map<String, Long> expected1 = Map.of(
                "certain point", 1L,
                "it is", 1L,
                "is not", 1L,
                "not so", 1L,
                "she wants", 1L,
                "a certain", 1L,
                "in her", 1L,
                "she does", 1L,
                "want not", 1L
        );

        Map<String, Long> expected2 = Map.of(
                "or not", 1L,
                "at a", 1L,
                "much that", 1L,
                "so much", 1L,
                "to have", 3L,
                "have had", 1L,
                "she realises", 1L,
                "a child", 3L,
                "as that", 1L
        );

        Map<String, Long> expected3 = Map.of(
                "her life", 1L,
                "does not", 1L,
                "had a", 1L,
                "wants to", 1L,
                "not to", 2L,
                "have a", 2L,
                "point in", 1L,
                "not want", 1L,
                "that she", 2L
        );

        Map<String, Long> expected = new HashMap<>();
        expected.putAll(expected1);
        expected.putAll(expected2);
        expected.putAll(expected3);

        assertEquals(expected, ngrams.getAllNGrams().get(1));
    }

    @Test
    public void testInterestingSentence2() {
        // test to make sure strings are not concatenated
        String[] lydiaDavis = {"At a certain point in her life,", "she realises",
                "it is not so much that she wants to have a child",
                "as that she does not want not to have a child,", "or not to have had a child."};

        NGrams ngrams = new NGrams(lydiaDavis);

        Map<String, Long> expected12 = Map.of(
                "it is not so much that she wants to have a child", 1L
        );
        assertEquals(expected12, ngrams.getAllNGrams().get(11));

        Map<String, Long> expected11 = Map.of(
                "it is not so much that she wants to have a", 1L,
                "is not so much that she wants to have a child", 1L,
                "as that she does not want not to have a child", 1L
        );
        assertEquals(expected11, ngrams.getAllNGrams().get(10));

    }

    @Test
    public void testNGramCount1() {
        String[] words = {"where in the world is carmen sandiego"};
        NGrams ngrams = new NGrams(words);
        assertEquals(25, ngrams.getTotalNGramCount(5));
        assertEquals(28, ngrams.getTotalNGramCount(7));
        assertEquals(28, ngrams.getTotalNGramCount(100));
    }

    @Test
    public void testNGramCount2() {
        String[] lydiaDavis = {"At a certain point in her life,", "she realises",
                "it is not so much that she wants to have a child",
                "as that she does not want not to have a child,", "or not to have had a child."};
        NGrams ngrams = new NGrams(lydiaDavis);
        assertEquals(122, ngrams.getTotalNGramCount(5));
        assertEquals(152, ngrams.getTotalNGramCount(7));
        assertEquals(177, ngrams.getTotalNGramCount(12));
        assertEquals(177, ngrams.getTotalNGramCount(100));
    }

    @Test
    public void testMiscIssues() {
        String[] semiWeirdCases = {
                "this Is a Test!",
                "this is a test&",
                "'this is a 'test",
                "this is a test % test",
                "5/5 this is \\a test"
        };

        List<Map<String, Long>> expected = List.of(
                Map.of(
                        "a", 5L,
                        "test", 6L,
                        "5", 2L,
                        "this", 5L,
                        "is", 5L
                ),
                Map.of(
                        "test test", 1L,
                        "5 this", 1L,
                        "is a", 5L,
                        "5 5", 1L,
                        "this is", 5L,
                        "a test", 5L
                ),
                Map.of(
                        "this is a", 5L,
                        "5 this is", 1L,
                        "a test test", 1L,
                        "is a test", 5L,
                        "5 5 this", 1L
                ),
                Map.of(
                        "5 this is a", 1L,
                        "is a test test", 1L,
                        "this is a test", 5L,
                        "5 5 this is", 1L
                ),
                Map.of(
                        "5 this is a test", 1L,
                        "this is a test test", 1L,
                        "5 5 this is a", 1L
                ),
                Map.of(
                        "5 5 this is a test", 1L
                )
        );

        NGrams ngrams = new NGrams(semiWeirdCases);
        assertEquals(expected, ngrams.getAllNGrams());
    }
}