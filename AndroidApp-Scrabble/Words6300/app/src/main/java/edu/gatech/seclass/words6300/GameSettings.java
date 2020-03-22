package edu.gatech.seclass.words6300;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSettings implements Cloneable {
    private int maxTurns;
    private Map<Character, LetterSetting> letterSettingsMap;

    public GameSettings(int maxTurns, List<LetterSetting> letterSettings) {
        this.maxTurns = maxTurns;
        this.letterSettingsMap = BuildLetterSettingsMap(letterSettings);
    }

    public GameSettings(int maxTurns) {
        this.maxTurns = maxTurns;
        this.letterSettingsMap = BuildLetterSettingsMap(BuildDefaultLetterSettings());
    }

    public Object clone() {
        List<LetterSetting> newSettings = new ArrayList<>();
        for (LetterSetting setting : letterSettingsMap.values()) {
            newSettings.add(new LetterSetting(setting.getLetter(), setting.getFrequency(), setting.getPoints()));
        }
        return new GameSettings(maxTurns, newSettings);
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) throws IllegalArgumentException {
        if (maxTurns <= 0) {
            throw new IllegalArgumentException();
        }
        this.maxTurns = maxTurns;
    }

    public void updateLetterFrequency(char letter, int frequency) throws IllegalArgumentException {

        if ((!Character.isLetter(letter)) && (frequency < 0)) {
            throw new IllegalArgumentException();
        }
        LetterSetting setting = getLetterSetting(letter);

        setting.setFrequency(frequency);
    }

    public void updateLetterPoints(char letter, int points) throws IllegalArgumentException {

        if (points < 0) {
            throw new IllegalArgumentException();
        }

        LetterSetting setting = getLetterSetting(letter);

        setting.setPoints(points);
    }

    public int getLetterFrequency(char letter) {
        return getLetterSetting(letter).getFrequency();
    }

    public int getLetterPoints(char letter) {
        return getLetterSetting(letter).getPoints();
    }

    private LetterSetting getLetterSetting(char letter) {
        if (!letterSettingsMap.containsKey(letter)) {
            throw new IllegalStateException("There are no LetterSettings for character " + letter);
        }

        return letterSettingsMap.get(letter);
    }

    private Map<Character, LetterSetting> BuildLetterSettingsMap(List<LetterSetting> letterSettings) {
        Map<Character, LetterSetting> map = new HashMap<>();
        for (LetterSetting setting : letterSettings) {
            map.put(setting.getLetter(), setting);
        }
        return map;
    }

    private List<LetterSetting> BuildDefaultLetterSettings() {
        // Letter distributions reference found here:
        // https://en.wikipedia.org/wiki/Scrabble_letter_distributions

        List<LetterSetting> settingsList = new ArrayList<>();
        //    1 point: E ×12, A ×9, I ×9, O ×8, N ×6, R ×6, T ×6, L ×4, S ×4, U ×4
        int currentPoints = 1;
        settingsList.add(new LetterSetting('E', 12, currentPoints));
        settingsList.add(new LetterSetting('A', 9, currentPoints));
        settingsList.add(new LetterSetting('I', 9, currentPoints));
        settingsList.add(new LetterSetting('O', 8, currentPoints));
        settingsList.add(new LetterSetting('N', 6, currentPoints));
        settingsList.add(new LetterSetting('R', 6, currentPoints));
        settingsList.add(new LetterSetting('T', 6, currentPoints));
        settingsList.add(new LetterSetting('L', 4, currentPoints));
        settingsList.add(new LetterSetting('S', 4, currentPoints));
        settingsList.add(new LetterSetting('U', 4, currentPoints));

        //    2 points: D ×4, G ×3
        currentPoints = 2;
        settingsList.add(new LetterSetting('D', 4, currentPoints));
        settingsList.add(new LetterSetting('G', 3, currentPoints));

        //    3 points: B ×2, C ×2, M ×2, P ×2
        currentPoints = 3;
        settingsList.add(new LetterSetting('B', 2, currentPoints));
        settingsList.add(new LetterSetting('C', 2, currentPoints));
        settingsList.add(new LetterSetting('M', 2, currentPoints));
        settingsList.add(new LetterSetting('P', 2, currentPoints));

        //    4 points: F ×2, H ×2, V ×2, W ×2, Y ×2
        currentPoints = 4;
        settingsList.add(new LetterSetting('F', 2, currentPoints));
        settingsList.add(new LetterSetting('H', 2, currentPoints));
        settingsList.add(new LetterSetting('V', 2, currentPoints));
        settingsList.add(new LetterSetting('W', 2, currentPoints));
        settingsList.add(new LetterSetting('Y', 2, currentPoints));

        //    5 points: K ×1
        currentPoints = 5;
        settingsList.add(new LetterSetting('K', 1, currentPoints));

        //    8 points: J ×1, X ×1
        currentPoints = 8;
        settingsList.add(new LetterSetting('J', 1, currentPoints));
        settingsList.add(new LetterSetting('X', 1, currentPoints));

        //    10 points: Q ×1, Z ×1
        currentPoints = 10;
        settingsList.add(new LetterSetting('Q', 1, currentPoints));
        settingsList.add(new LetterSetting('Z', 1, currentPoints));

        return settingsList;
    }
}
