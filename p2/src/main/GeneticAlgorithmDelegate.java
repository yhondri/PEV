package main;

import entities.Solution;

public interface GeneticAlgorithmDelegate {
    void didEvaluateGeneration(int generation, Solution solution);

    /**
     * Deshabilita los elementos de ejecución de la UI para evitar el colapso de la aplicación al ejecutar el problema
     * mientras se está ejecuntado otro.
     * @param enabled Define si los elementos tienen que estar habilitados o deshabilitados.
     */
    void areButtonsEnabled(boolean enabled);
}

