package main;

import entities.Solution;

public interface GeneticAlgorithmDelegate {
    void onStarSearch();
    void didEvaluateGeneration(int generation, Solution solution);
    void onEndSearch(Solution solution);

    /**
     * Deshabilita los elementos de ejecucion de la UI para evitar el colapso de la aplicacion al ejecutar el problema
     * mientras se esta ejecuntado otro.
     * @param enabled Define si los elementos tienen que estar habilitados o deshabilitados.
     */
    void areButtonsEnabled(boolean enabled);
}

