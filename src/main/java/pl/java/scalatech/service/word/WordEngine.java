package pl.java.scalatech.service.word;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WordEngine {
    private Map<String, CounterWord> words = newLinkedHashMap();
    
    private List<String> getWords(String text) {
        List<String> words = new ArrayList<>();
        if (text == null) return words;

        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            if(matcher.group().length() > 3)
            words.add(matcher.group());
        }        
        return words;
    }
    @Test
    public void test(){
        String text ="ala ma kota i pieska";
        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher(text.toLowerCase());
        while (matcher.find()) {
            if(matcher.group().length() > 3)
             log.info("++++ find {}",matcher);
        }        
    }
    private Map<String, CounterWord> getGroupAndSortedWords(List<String> wordsString) {
        Map<String, CounterWord> groupWords = new LinkedHashMap<>();
        for (String word : wordsString) {
            if (groupWords.containsKey(word)) {
                groupWords.put(word, new CounterWord(word, groupWords.get(word).getRepeat() + 1));
            } else {
                groupWords.put(word, new CounterWord(word, 1));
            }
        }
        groupWords.entrySet().stream()
                .sorted(Map.Entry.<String, CounterWord>comparingByValue().reversed())
                        // .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(s -> words.put(s.getKey(), s.getValue()));

        return words;
    }


    public Map<String, CounterWord> counterWords(String text) {
        return getGroupAndSortedWords(getWords(text));
    }
}
