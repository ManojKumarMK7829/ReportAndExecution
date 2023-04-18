package tests.javaPrograms;

import org.testng.annotations.Test;

import java.util.Arrays;


public class BasicProgram {

    @Test(groups = {"basics"})
    public void reapeatedCharacters() {
        String letter = "aabbccd";
        char arrayLetter [] = letter.toLowerCase().toCharArray();
        int i;
        String countOfLetters = "";
        for(i = 0; i< letter.length(); i++) {
            int count = 1;

            while ((i+1)<letter.length() && arrayLetter[i] == arrayLetter[i+1]) {
                count = count+1;
                i = i+1;
            }
            countOfLetters = countOfLetters+arrayLetter[i] + count;
        }
        System.out.println(countOfLetters);
    }

    @Test(groups = {"basics"})
    public void countNumberOfCharacters() {
        String words = "We'll look good together!";
        String character = words.replaceAll("[^A-Za-z]", "");
        System.out.println(character+ " " +character.length());
    }

    @Test(groups = {"basics"})
    public void countNumberOfCharactersOtherTHanAlphabets() {
        String words = "We'll look good together!";
        char[] arrayOfCharacters = words.toCharArray();
        int count = 0;
        for (char character:arrayOfCharacters) {
            if(character =='!' || character == ',' || character==';'|| character==':'
                    || character=='?' || character=='-' || character=='/' || character== '\'') {
                count++;
            }
        }
        System.out.println(count);
    }

    @Test(groups = {"basics"})
    public void countVowelsAndConsonants() {
        String words = "We'll look good together!";
        int vowelsCount = 0, consnantsCount = 0;
        char [] arrayOfLetter =  words.toLowerCase().toCharArray();
        for (char character:arrayOfLetter) {
            if(character == 'a' || character == 'e' || character == 'i' || character == 'o' || character == 'u')
                vowelsCount++;
            else
                consnantsCount++;
        }
        System.out.println(vowelsCount+ " "+ consnantsCount);
    }

    @Test(groups = {"basics"})
    public void checkEachCharactersAreSame() {
        String word1 = "Bras";
        String word2 = "Grab";
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        if(word1.length() != word2.length())
            System.out.println(word1+" and "+word2+ " are not anagram");
        else {
            char [] arr1 = word1.toCharArray();
            char [] arr2 = word2.toCharArray();
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            if (Arrays.equals(arr1, arr2))
                System.out.println("Both are anagrams");
            else
                System.out.println("Both are not anagrams");
        }
    }

    @Test(groups = {"basics"})
    public void printSubSets() {
        String word = "FUN";
        int length = word.length();
        int temp = 0;

        String[] arr = new String[length*(length+1)/2];

        for(int i = 0; i< length; i++) {
            for(int j=i; j<length; j++) {
                arr[temp] = word.substring(i,j+1);
                temp++;
            }
        }

        Arrays.stream(arr).forEach(System.out::println);
    }

    @Test(groups = {"basics"})
    public void printTheRepeatedLetter() {
        String str = "Great responsibility";
        char [] words = str.toLowerCase().toCharArray();
        String repeatedWords= "";

        for(int i = 0; i< str.length(); i++) {
            for (int j = i+1; j< str.length(); j++) {
                if(words[i]==words[j] && words[i] != ' ')
                    repeatedWords = repeatedWords + words[i];
            }
        }
        System.out.println("Repeated words are "+repeatedWords);
    }

    @Test(groups = {"basics"})
    public void reverseString() {
        String word1 = "Manoj";
        String reversed = "";

        for (int i = word1.length()-1; i>= 0; i--) {
            reversed = reversed + word1.toCharArray()[i];
        }
        System.out.println("Using logic ->"+reversed);

        /*      Using String Builder    */
        StringBuilder stringBuilder = new StringBuilder(word1);
        String reversed1 = String.valueOf(stringBuilder.reverse());
        System.out.println("Using string builder ->"+ reversed1);
    }
}


