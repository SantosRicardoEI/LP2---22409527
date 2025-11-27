package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.effect.PlayerEffect;

public class UndocumentedCode extends Abyss {

    public UndocumentedCode(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Projeto não documentado! O programador está confuso e precisa de alguns turnos....";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        int lastPosition = moveHistory.getPosition(player,0);
        int thisPosition = board.getPlayerPosition(player);

        if (thisPosition == lastPosition ) {
            player.updateEffects();

        } else {
            int lastRoll = moveHistory.getRoll(player, 0);
            int turnsToPass = Math.max(1, lastRoll / 2);
            player.setEffect(new PlayerEffect(PlayerState.CONFUSED, turnsToPass));
        }

    }
}