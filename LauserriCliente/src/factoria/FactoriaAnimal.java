package factoria;

import interfaces.InterfazAnimal;

/**
 * Factoria de animal con la cual se implementan los metodos de la clase
 * AnimalImplementacion
 *
 * @author Jonathan Camacho
 */
public class FactoriaAnimal {

    public static InterfazAnimal getInterfazAnimalImplementacion() {
        InterfazAnimal animalManager = new AnimalImplementacion();
        return animalManager;
    }
}
