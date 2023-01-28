package cpen221.mp1;

import cpen221.mp1.autocompletion.AutoCompletor;
import cpen221.mp1.cities.DataAnalyzer;
import cpen221.mp1.searchterm.SearchTerm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.List;


public class Task3Tests {

    private static final String citiesData = "data/cities.txt";
    private static DataAnalyzer cityAnalyzer;
    private static AutoCompletor ac;

    @BeforeAll
    public static void setupTests() {
        cityAnalyzer = new DataAnalyzer(citiesData);
        ac = new AutoCompletor(cityAnalyzer.getSearchTerms());
    }


    @Test
    public void test_San_3() {
        SearchTerm[] st = ac.topKMatches("San", 3);

        SearchTerm santiago = new SearchTerm("Santiago, Chile", 4837295);
        SearchTerm santoDomingo = new SearchTerm("Santo Domingo, Dominican Republic", 2201941);
        SearchTerm sanaa = new SearchTerm("Sanaa, Yemen", 1937451);
        SearchTerm[] expectedST = new SearchTerm[]{santiago, santoDomingo, sanaa};

        Assertions.assertArrayEquals(expectedST, st);
    }

    @Test
    public void testCities2() {
        SearchTerm[] st = ac.topKMatches("Saint Petersburg", 3);

        SearchTerm russia = new SearchTerm("Saint Petersburg, Russia", 4039745);
        SearchTerm usa = new SearchTerm("Saint Petersburg, Florida, United States", 244769);
        SearchTerm[] expectedST = new SearchTerm[]{russia, usa};

        Assertions.assertArrayEquals(expectedST, st);
    }

    @Test
    public void testCities3() {
        SearchTerm[] st = ac.topKMatches("Smiths");

        SearchTerm smiths1 = new SearchTerm("Smiths Falls, Ontario, Canada", 10553);
        SearchTerm smiths2 = new SearchTerm("Smiths Station, Alabama, United States", 4926);
        SearchTerm smiths3 = new SearchTerm("Smithsburg, Maryland, United States", 2975);
        SearchTerm[] expectedST = new SearchTerm[]{smiths1, smiths2, smiths3};

        Assertions.assertArrayEquals(expectedST, st);

        String customCitiesData = "data/cities_reversed.txt";
        DataAnalyzer customAnalyzer = new DataAnalyzer(customCitiesData);
        AutoCompletor acCustom = new AutoCompletor(customAnalyzer.getSearchTerms());
        SearchTerm[] abc = acCustom.allMatches("Smiths");

        Assertions.assertArrayEquals(expectedST, abc);
    }

    @Test
    public void testCities2Custom() {
        SearchTerm[] st = ac.allMatches("Saint Peters");

        SearchTerm russia = new SearchTerm("Saint Petersburg, Russia", 4039745);
        SearchTerm usa = new SearchTerm("Saint Petersburg, Florida, United States", 244769);
        SearchTerm usa2 = new SearchTerm("Saint Peters, Missouri, United States", 52575);

        SearchTerm[] expectedST = new SearchTerm[]{russia, usa, usa2};

        Assertions.assertArrayEquals(expectedST, st);
        Assertions.assertEquals(3, ac.numberOfMatches("Saint Peters"));
    }

    @Test
    public void searchTermComparatorTest() {
        SearchTerm usa = new SearchTerm("Saint Petersburg, Florida, United States", 244769);
        SearchTerm usa2 = new SearchTerm("Saint Peters, Missouri, United States", 52575);

        usa.compareTo(usa2);
    }

    @Test
    public void sortCitiesTest() {
        String customCitiesData = "data/cities_reversed.txt";
        DataAnalyzer customAnalyzer = new DataAnalyzer(customCitiesData);
        AutoCompletor acCustom = new AutoCompletor(customAnalyzer.getSearchTerms());
        SearchTerm[] st = acCustom.allMatches("Saint Peters");

        SearchTerm russia = new SearchTerm("Saint Petersburg, Russia", 4039745);
        SearchTerm usa = new SearchTerm("Saint Petersburg, Florida, United States", 244769);
        SearchTerm usa2 = new SearchTerm("Saint Peters, Missouri, United States", 52575);

        SearchTerm[] expectedST = new SearchTerm[]{russia, usa, usa2};

        Assertions.assertArrayEquals(expectedST, st);
    }

    @Test
    public void nGramsTest() {
        List<Map<String, Long>> nGramsResults = List.of(
                Map.of("good", 1L, "textbook", 2L, "written", 3L, "by", 4L, "him", 5L),
                Map.of("good textbook", 6L, "textbook written", 7L, "written by", 8L, "by him", 9L),
                Map.of("good textbook written", 10L, "textbook written by", 11L, "written by him", 12L)
        );

        AutoCompletor acCustom = new AutoCompletor(nGramsResults);
        SearchTerm[] abc = acCustom.allMatches("written");

        SearchTerm written1 = new SearchTerm("written", 3L);
        SearchTerm written2 = new SearchTerm("written by", 8L);
        SearchTerm written3 = new SearchTerm("written by him", 12L);

        SearchTerm[] expectedST = new SearchTerm[]{written3, written2, written1};

        Assertions.assertArrayEquals(expectedST, abc);
    }

    @Test
    public void nGramsTestSameWeights() {
        List<Map<String, Long>> nGramsResults = List.of(
                Map.of("good", 1L, "textbook", 2L, "written", 3L, "by", 4L, "him", 5L),
                Map.of("good textbook", 6L, "textbook written", 7L, "written by", 3L, "by him", 9L),
                Map.of("good textbook written", 10L, "textbook written by", 11L, "written by him", 12L)
        );

        AutoCompletor acCustom = new AutoCompletor(nGramsResults);
        SearchTerm[] abc = acCustom.allMatches("written");

        SearchTerm written1 = new SearchTerm("written", 3L);
        SearchTerm written2 = new SearchTerm("written by", 3L);
        SearchTerm written3 = new SearchTerm("written by him", 12L);

        SearchTerm[] expectedST = new SearchTerm[]{written3, written1, written2};

        Assertions.assertArrayEquals(expectedST, abc);
    }

    @Test
    public void nGramsTestIdenticalEntries() {
        List<Map<String, Long>> nGramsResults = List.of(
                Map.of("good", 1L, "textbook", 2L, "written", 3L, "by", 4L, "him", 5L),
                Map.of("good textbook", 6L, "textbook written", 7L, "written", 3L, "by him", 9L),
                Map.of("good textbook written", 10L, "textbook written by", 11L, "written by him", 12L)
        );

        AutoCompletor acCustom = new AutoCompletor(nGramsResults);
        SearchTerm[] abc = acCustom.allMatches("written");

        SearchTerm written1 = new SearchTerm("written", 3L);
        SearchTerm written2 = new SearchTerm("written", 3L);
        SearchTerm written3 = new SearchTerm("written by him", 12L);

        SearchTerm[] expectedST = new SearchTerm[]{written3, written1, written2};
        Assertions.assertArrayEquals(expectedST, abc);
    }
}