package main;

import csv.Apk;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class AlgorithmResults {

    List<Apk> apks;
    Map<Apk, HashMap<Apk, Float>> distances;

    public String toString() {
        return "Apks: " + apks + "\nDistances: " + distances;
    }
}
