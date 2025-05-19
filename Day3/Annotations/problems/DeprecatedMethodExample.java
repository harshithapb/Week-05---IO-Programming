package org.example.problems;

class LegacyAPI {
    @Deprecated
    public void oldFeature() {
        System.out.println("LegacyAPI: This is the old feature. Please use newFeature().");
    }

    public void newFeature() {
        System.out.println("LegacyAPI: This is the new and improved feature.");
    }
}

public class DeprecatedMethodExample {
    public static void main(String[] args) {
        LegacyAPI legacyAPI = new LegacyAPI();

        legacyAPI.oldFeature();
        legacyAPI.newFeature();
    }
}