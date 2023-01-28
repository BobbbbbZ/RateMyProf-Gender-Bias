package cpen221.mp1.autocompletion;

import cpen221.mp1.searchterm.SearchTerm;

import java.util.*;

public class AutoCompletor {

    private static final int DEFAULT_SEARCH_LIMIT = 10;
    private List<SearchTerm> allSearchTerms = new ArrayList<>();
    private int numMatches = 0;

    /**
     * Main constructor, sets up ArrayList using values from input array for easier management.
     *
     * @param searchTerms An array of searchTerms to be queried from. Not null and not empty.
     */
    public AutoCompletor(SearchTerm[] searchTerms) {
        allSearchTerms = Arrays.asList(searchTerms);
    }

    /**
     * Alternate constructor, converts List of Maps to an ArrayList of SearchTerms for easier management.
     *
     * @param searchTerms A List of Maps to be queried from. searchTerms need contain n-grams. Not null and not empty.
     */
    public AutoCompletor(List<Map<String, Long>> searchTerms) {
        for (Map<String, Long> m : searchTerms) {
            for (Map.Entry<String, Long> e : m.entrySet()) {
                SearchTerm newTerm = new SearchTerm(e.getKey(), e.getValue());
                allSearchTerms.add(newTerm);
            }
        }
    }

    /**
     * Gets all SearchTerms that match the prefix from searchResultsList.
     * Sorts them by their number priority then by lexicographically.
     *
     * @param prefix A string search term to query results with. Not null, not empty, a typical string.
     * @return An array of SearchTerms that match prefix
     */
    public SearchTerm[] allMatches(String prefix) {
        List<SearchTerm> searchResultsList = new ArrayList<>();

        for (SearchTerm s : allSearchTerms) {
            if (s.query.startsWith(prefix)) {
                searchResultsList.add(s);
                numMatches++;
            }
        }

        sortSearchResults(searchResultsList);

        SearchTerm[] searchResults = new SearchTerm[searchResultsList.size()];
        searchResults = searchResultsList.toArray(searchResults);

        return searchResults;
    }

    /**
     * Gets K SearchTerms that match the prefix from searchResultsList.
     * Sorts them by their number priority then by lexicographically.
     *
     * @param prefix A string search term to query results with. Not null, not empty, a typical string.
     * @param limit  The number of search terms K to be limited to. Not null, a typical int.
     * @return An array of K SearchTerms that match prefix
     */
    public SearchTerm[] topKMatches(String prefix, int limit) {
        List<SearchTerm> searchResultsList = new ArrayList<>();

        for (SearchTerm s : allSearchTerms) {
            if (s.query.startsWith(prefix)) {
                searchResultsList.add(s);
                limit--;
            }

            if (limit == 0) {
                break;
            }
        }

        sortSearchResults(searchResultsList);

        SearchTerm[] searchResults = new SearchTerm[searchResultsList.size()];
        searchResults = searchResultsList.toArray(searchResults);

        return searchResults;
    }

    /**
     * Gets DEFAULT_SEARCH_LIMIT SearchTerms that match the prefix from
     * searchResultsList when a limit is not provided as a parameter.
     * Sorts them by their number priority then by lexicographically.
     *
     * @param prefix A string search term to query results with. Not null, not empty, a typical string.
     * @return An array of DEFAULT_SEARCH_LIMIT SearchTerms that match prefix
     */
    public SearchTerm[] topKMatches(String prefix) {
        return topKMatches(prefix, DEFAULT_SEARCH_LIMIT);
    }

    /**
     * Gets the number of matches for an input prefix.
     *
     * @param prefix A string search term to query results with. Not null, not empty, a typical string.
     * @return The number of matches found.
     */
    public int numberOfMatches(String prefix) {
        numMatches = 0;
        allMatches(prefix);

        return numMatches;
    }

    /**
     * Sorts an input ArrayList of SearchTerms using the comparator interface.
     *
     * @param searchResultList A list of SearchTerms. Not null, not empty.
     */
    public void sortSearchResults(List<SearchTerm> searchResultList) {
        searchResultList.sort(byWeightThenPrefix());
    }

    /**
     * Comparator for sorting an
     *
     * @return A Comparator for sorting first by weight then lexicographically.
     */
    public Comparator<SearchTerm> byWeightThenPrefix() {
        return new Comparator<SearchTerm>() {
            @Override
            public int compare(SearchTerm o2, SearchTerm o1) {
                long temp = o1.weight - o2.weight;
                int val1 = temp < 0 ? -1 : (temp > 0 ? 1 : 0);
                if (val1 == 0) {
                    int val2 = o2.query.compareTo(o1.query);
                    if (val2 == 0) {
                        return o2.query.compareTo(o1.query);
                    } else {
                        return val2;
                    }
                }
                return val1;
            }
        };
    }


}
