package cpen221.mp1;

import cpen221.mp1.ratemyprofessor.DataAnalyzer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Tests {

    private static DataAnalyzer da1;
    private static DataAnalyzer da2;
    private static DataAnalyzer da3;

    @BeforeAll
    public static void setUpTests() throws FileNotFoundException {
        da1 = new DataAnalyzer("data/reviews1.txt");
        da2 = new DataAnalyzer("data/reviews2.txt");
        da3 = new DataAnalyzer("data/ratemyprofessor_data.txt");
    }

    @Test
    public void testGood() {
        String query = "good";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 1L,
                "MM", 0L,
                "WM", 0L,
                "MH", 1L,
                "WH", 1L
        );
        assertEquals(expected, da1.getHistogram(query));
    }

    @Test
    public void testIon() {
        String query = " i";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 1L,
                "WM", 0L,
                "MH", 4L,
                "WH", 0L
        );
        assertEquals(expected, da2.getHistogram(query));
    }
    @Test
    public void testCover() {
        String query = "she is";
        Map<String, Long> expected = Map.of(
                "ML", 4L,
                "WL", 588L,
                "MM", 6L,
                "WM", 369L,
                "MH", 11L,
                "WH", 1720L
        );
        assertEquals(expected, da3.getHistogram(query));
    }

    @Test
    public void testSpace() {
        String query = "she       is";
        Map<String, Long> expected = Map.of(
                "ML", 4L,
                "WL", 588L,
                "MM", 6L,
                "WM", 369L,
                "MH", 11L,
                "WH", 1720L
        );
        assertEquals(expected, da3.getHistogram(query));
    }
    @Test
    public void testHeIs() {
        String query = "he is";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 3L,
                "WM", 0L,
                "MH", 3L,
                "WH", 0L
        );
        assertEquals(expected, da2.getHistogram(query));
    }

    @Test
    public void testProfessorWasBad() throws FileNotFoundException {
        String query = "professor was bad";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 0L,
                "WM", 0L,
                "MH", 0L,
                "WH", 0L
        );
        assertEquals(expected, da2.getHistogram(query));
    }

    //Tests for how good the program is at dealing with separated words and compound words//
    @Test
    public void testHeWas() throws FileNotFoundException {
        String query = "hewas";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 0L,
                "WM", 0L,
                "MH", 0L,
                "WH", 0L
        );
        assertEquals(expected, da3.getHistogram(query));
    }

    @Test
    public void testClassRoom() throws FileNotFoundException {
        String query = "class room";
        Map<String, Long> expected = Map.of(
                "ML", 2L,
                "WL", 0L,
                "MM", 1L,
                "WM", 0L,
                "MH", 1L,
                "WH", 2L
        );
        assertEquals(expected, da3.getHistogram(query));
    }

    @Test
    public void testHG() throws FileNotFoundException {
        String query = "gh";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 0L,
                "WM", 0L,
                "MH", 0L,
                "WH", 0L
        );
        assertEquals(expected, da3.getHistogram(query));
    }
    @Test
    public void testYou() throws FileNotFoundException {
        String query = "YOU";
        Map<String, Long> expected = Map.of(
                "ML", 2L,
                "WL", 0L,
                "MM", 0L,
                "WM", 0L,
                "MH", 7L,
                "WH", 0L
        );
        assertEquals(expected, da2.getHistogram(query));
    }

    @Test
    public void testOnlyIfYou() throws FileNotFoundException {
        String query = "only if you";
        Map<String, Long> expected = Map.of(
                "ML", 2L,
                "WL", 0L,
                "MM", 1L,
                "WM", 0L,
                "MH", 4L,
                "WH", 3L
        );
        assertEquals(expected, da3.getHistogram(query));
    }

    @Test
    public void testChemistryIs() throws FileNotFoundException {
        String query = "chemistry is";
        Map<String, Long> expected = Map.of(
                "ML", 0L,
                "WL", 0L,
                "MM", 0L,
                "WM", 0L,
                "MH", 2L,
                "WH", 0L
        );
        assertEquals(expected, da3.getHistogram(query));
    }
}
