package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

// O programador perde uma vida
public class MemoryFault extends Abyss {

    public MemoryFault(int id, String name) {
        super(4, "Falha de Memória");
    }

    @Override
    public void affectPlayer(Player player) {

    }
}
