package TanachMarkovModel.MarkovModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import TanachMarkovModel.DataPreparation.TanachDataPreparation;

public class MarkovModel {

    public static final String startString = "s";
    public static final String endString = "e";

    public static Map<String, Map<String, Double>> generateTransitionMatrix(Iterable<String> lines) {
        HashMap<String, Map<String, Double>> transitionMatrix = new HashMap<>();
        HashMap<String, Map<String, Integer>> transitionOccurences = new HashMap<>();
        transitionOccurences.put(startString, new HashMap<>());
        for (String line : lines) {
            String[] words = line.split(" ");
            if (!transitionOccurences.get(startString).containsKey(words[0])) transitionOccurences.get(startString).put(words[0], 0);
            transitionOccurences.get(startString).put(words[0], transitionOccurences.get(startString).get(words[0]) + 1);
            for (int i = 0; i < words.length - 1; i++) {
                if (!transitionOccurences.containsKey(words[i])) transitionOccurences.put(words[i], new HashMap<>());
                if (!transitionOccurences.get(words[i]).containsKey(words[i+1])) transitionOccurences.get(words[i]).put(words[i+1], 0);
                transitionOccurences.get(words[i]).put(words[i+1], transitionOccurences.get(words[i]).get(words[i+1]) + 1);
            }
            if (!transitionOccurences.containsKey(words[words.length-1])) transitionOccurences.put(words[words.length -1], new HashMap<>());
            if (!transitionOccurences.get(words[words.length-1]).containsKey(endString)) transitionOccurences.get(words[words.length-1]).put(endString, 1);
            else transitionOccurences.get(words[words.length-1]).put(endString, transitionOccurences.get(words[words.length - 1]).get(endString) + 1);
        }
        for (String word : transitionOccurences.keySet()) {
            Map<String, Double> transitions = new HashMap<>();
            double totalOccurences = transitionOccurences.get(word).values().stream().mapToDouble(Integer::doubleValue).sum();
            for (String tWord : transitionOccurences.get(word).keySet()) transitions.put(tWord ,(double)transitionOccurences.get(word).get(tWord)/totalOccurences);
            transitionMatrix.put(word, transitions);
        }
        return transitionMatrix;
    }

    public static String generateSentence(Map<String, Map<String, Double>> transitionMatrix) {
        Random random = new Random();
        String sentence = "";
        String currentWord = startString;
        while (currentWord != endString) {
            if (currentWord != startString) sentence += currentWord + " ";
            double rnd = random.nextDouble();
            for (Map.Entry<String, Double> transition : transitionMatrix.get(currentWord).entrySet()) {
                rnd -= transition.getValue();
                if (rnd <= 0) {
                    currentWord = transition.getKey();
                    break;
                }
            }
        }
        return sentence;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Map<String, Double>> transitionMatrix = generateTransitionMatrix(TanachDataPreparation.getLines());
        int num = 1;
        if (args.length == 1) {
            if ( args[0].equals( "save" )) {
                TanachDataPreparation.saveTransitionMatrix(transitionMatrix);
            } else {
                num = Integer.parseUnsignedInt(args[0]);
                for (int i = 0; i<num; i++) System.out.println(generateSentence(transitionMatrix));
            }
        } else {
            if (args[0].equals( "save" )) {
                TanachDataPreparation.saveTransitionMatrix(transitionMatrix);
                num = Integer.parseUnsignedInt(args[1]);
                for (int i = 0; i<num; i++) System.out.println(generateSentence(transitionMatrix));
            } else if (args[1].equals("save")) {
                TanachDataPreparation.saveTransitionMatrix(transitionMatrix);
                num = Integer.parseUnsignedInt(args[0]);
                for (int i = 0; i<num; i++) System.out.println(generateSentence(transitionMatrix));
            }
        }
    }
}
