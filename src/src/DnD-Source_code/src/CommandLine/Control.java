package CommandLine;

import tiles.units.player.Player;
import game.GameBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Control {
    protected GameBoard board;
    protected Player player;
    protected Reader reader;
    protected List<String> levels;
    protected MessageCallBack message;
    protected int currentLevel = 0;

    public Control() {}

    public void setMessage(MessageCallBack m) {
        this.message = m;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public Player getPlayer() {
        return player;
    }

    public void accept(GameBoard board) {
        board.visit(this);
    }

    public void call(String m) {
        message.send(m);
    }

    public void inter() {
        int x = Math.max( (this.player.getAbilityName().length()) - 6 ,0);
        UI.print("╔══════════════╗");
        UI.print("║ Move options ║");
        UI.print("╟--------------╚════════════" + "═".repeat(x)         + "╗");
        UI.print("║ Press [D] to move right  " + " ".repeat(x)         + " ║");
        UI.print("║ Press [A] to move left   " + " ".repeat(x)         + " ║");
        UI.print("║ Press [W] to move up     " + " ".repeat(x)         + " ║");
        UI.print("║ Press [S] to move down   " + " ".repeat(x)         + " ║");
        UI.print("║ Press [Q] to skip a turn " + " ".repeat(x)         + " ║");
        UI.print("║ Press [E] to cast "+ this.player.getAbilityName() + "  ║");
        UI.print("╚═══════════════════════════" + "═".repeat(x)         + "╝");
    }

    public void start(List<String> levels) throws IOException {
        setMessage(this::call);
        setLevels(levels);
        TileFactory tc = new TileFactory();
        List<Player> playerList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 10; i++) {
            Player p = tc.createPlayer(i);
            playerList.add(p);
            String intro = p.intro();
            if (i == 9) {
                sb.append("║ ").append(i).append('.').append(intro).append("  ║");
            } else{
                sb.append("║ ").append(i).append('.').append(intro).append("  ║").append("\n");
                sb.append("║" + "──────────────────────────────────────────────────────────────────────────────────────────" + "║").append("\n");}

        }
        Player Itamar = tc.createPlayer(10);
        playerList.add(Itamar);

        UI.print(AsciiPrinter.GameStart());
        UI.print(AsciiPrinter.by() + "\n");
        UI.print("╔═════════════════════╗");
        UI.print("║ Pick your champion! ║");
        UI.print("╟─────────────────────╚════════════════════════════════════════════════════════════════════╗");
        UI.print(sb.toString());
        UI.print("╚══════════════════════════════════════════════════════════════════════════════════════════╝");
        UI.printWithoutLn("\nYour pick: ");
        int x = Integer.parseInt(UI.input());
        while (x < 1 || x > 10) {
            UI.print("╔═════════════════╗");
            UI.print("║ Invalid choice. ║");
            UI.print("╚═════════════════╝" + "\n");
            UI.printWithoutLn( "Please choose a valid player number: ");
            x = Integer.parseInt(UI.input());
        }
        this.player = playerList.get(x - 1);
        if (x == 10){
            UI.print("╔═════════════════════════════════════════════════╗ \n" +
                     "║ U found the easter egg! the tester of the game! ║ \n" +
                     "║         Press [E] to skip the level             ║ \n" +
                     "╚═════════════════════════════════════════════════╝   " );}
        else{
            int y = (playerList.get(x - 1).getName().length()) + 1; // how long is the name
            int num = Math.max(0, y - 10);                      // how many spaces to add
            int num2 = Math.max(0, 10 - y);                     // how many spaces to add (negative)


            String r = "─".repeat(num); // ┌ ─ ┐ │ │ └ ─ ┘
            UI.print("");
            UI.print("┌───────────" + r + "─┐");
            UI.print("│ You chose:" + " ".repeat(num) + " │" );
            UI.print("│ " + playerList.get(x - 1).getName() + '.' + " ".repeat(num2) + " │");
            UI.print("└───────────" + r + "─┘\n");}

        inter();
        this.reader = new Reader(player);
        letsPlay(levels);

    }

    private void turn() {
        UI.printWithoutLn("\n" + "╔═════════╗" +                      "\n"
                               + "║ Level " + (currentLevel) + " ║" + "\n"
                               + board.toString1() +                  "\n"
                               + player.description2() +              "\n\n"
                               + "Press W/A/S/D to move :"
                        );
    }


    public void letsPlay(List<String> levels) throws IOException {
        while (currentLevel < levels.size()) {

            setBoard(levels, currentLevel, message);
            turn();
            while (!levelEnd() && player.isAlive()) {
                String move = UI.input();
                UI.print("");
                if (move.equals("reset")) { // press reset to restart the game
                    restartGame();
                }
                accept(board);
                board.playerTurn(move, getPlayer());
                board.removeDeadEnemies(player);
                board.enemyTurn(getPlayer());

                if (!player.isAlive()) {
                player.setCharTile('X');
                break;
            }
                if ((currentLevel == levels.size()) && levelEnd()) {
                    UI.print("Congratulations! You have won the game! \n\n\n");
                    break;}

                turn();

            }
            if (player.isDead()) {
                break;
            }
            if (levelEnd() && player.isAlive() && currentLevel < levels.size()) {
                UI.print("Good job! The next level is: " + (currentLevel + 1) + "\n");
            }
        }
        gameOver();
    }

    public void setBoard(List<String> levels, int currentLevel, MessageCallBack message) throws IOException {

        this.board = reader.read(levels.get(currentLevel), message);
        this.currentLevel += 1;
    }

    public void gameOver() {
        if (getPlayer().getCharTile() == 'X') {
            UI.print("You lost.");
            UI.print(board.toString());
            UI.print("\n" + AsciiPrinter.GameOver());
            UI.print(AsciiPrinter.skull());
            UI.print(AsciiPrinter.youLost());
        } else {
            UI.print("\n" + AsciiPrinter.GameOver());
            UI.print(AsciiPrinter.trophy());
            UI.print(AsciiPrinter.youWon());
            UI.print("");
        }
        UI.print("Press [Enter] to restart the game.");
        UI.print("Press any other key to exit.");
        String restart = UI.input();
        if (restart.equals("")){
        restartGame();}
    }

    public Boolean levelEnd() {
        accept(board);
        return board.levelEnd();
    }

    private void restartGame() {
            this.currentLevel = 0;
            this.player = null;
            this.board = null;
            // Restart the game
            try {
                start(this.levels);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}
