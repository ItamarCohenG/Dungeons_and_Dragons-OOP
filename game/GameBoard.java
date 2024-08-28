package game;

import CommandLine.MessageCallBack;
import CommandLine.Control;
import CommandLine.UI;
import tiles.EmptyTile;
import tiles.Tile;
import tiles.units.enemies.Enemy;
import tiles.units.player.Player;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class GameBoard {
    private Control control;
    private Tile[][] board;
    private List<Enemy> enemies;
    private int height;
    private int width;
    protected MessageCallBack messageCallBack;

    public GameBoard(MessageCallBack messageCallBack, int height, int width) {
        this.messageCallBack = messageCallBack;
        this.height = height;
        this.width = width;
        this.board = new Tile[height][width];
        this.enemies = new ArrayList<>();
        initializeBoard(); // Initializing the board with empty tiles
    }

    public boolean isValid(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    public void initialize(List<Tile> tiles, List<Enemy> enemies, Player player, int boardWidth, int boardHeight) {
        this.height = boardHeight;
        this.width = boardWidth;
        this.board = new Tile[height][width];

        initializeBoard(); // Reinitialize the board with empty tiles

        for (Tile tile : tiles) {
            Position pos = tile.getPosition();
            if (isValid(pos)) {
                board[pos.getY()][pos.getX()] = tile;
            }
        }
        this.enemies = enemies;
        for (Enemy e : enemies) {e.setGameBoard(this);}

        Position playerPos = player.getPosition();
        if (isValid(playerPos)) {
            board[playerPos.getY()][playerPos.getX()] = player;
        }
    }

    public Optional<Tile> get(Position p) {
        if (isValid(p)) {
            return Optional.ofNullable(board[p.getY()][p.getX()]); // תיקון בשורת מיקום
        } else {
            return Optional.empty();
        }
    }

    public boolean isOccupied(Position pos) {
        if (isValid(pos)) {
            Tile tile = board[pos.getY()][pos.getX()];
            return !(tile instanceof EmptyTile);
        }
        return false;
    }

    public boolean isOccupiedWithPlayer(Position pos) {
        if (isValid(pos)) {
            Tile tile = board[pos.getY()][pos.getX()];
            return (tile instanceof Player);
        }
        return false;
    }

    public void playerTurn(String move, Player player) {
        Position oldPosition = player.getPosition();
        Position newPosition = getPlayerMove(move, player);
        player.updatePoints(player);
        //UI.print("Player moved to " + newPosition);

        if (isValid(newPosition)) {
            Optional<Tile> tile = get(newPosition);

            if (tile.isPresent() && tile.get() instanceof Enemy) {
                Enemy enemy = (Enemy) tile.get();
                CombatSystem.engageCombat(player, enemy); // needs to call to combat system
            } else if (!isOccupied(newPosition)) {
                Tile nextTile = tile.orElseThrow(() -> new IndexOutOfBoundsException("No Such Tile"));
                swapTiles(player, nextTile);

                if (isValid(oldPosition)) {
                    board[oldPosition.getY()][oldPosition.getX()] = new EmptyTile(oldPosition);
                }
            } else {
                if (move.equals("q")) {UI.print("U chose the skip a turn");}
                else if (move.equals("e")) {UI.printWithoutLn("");}
                else  UI.print("Cannot move to that position.");

            }
        } else {
            UI.print("Cannot move to that position.");
        }

    }

    public Position getPlayerMove(String move, Player player) {
        Position temp = player.getPosition();
        switch (move) {
            case "e" -> player.castAbility(enemies);
            case "d" -> temp = player.getPosition().translate(1, 0);
            case "a" -> temp = player.getPosition().translate(-1, 0);
            case "w" -> temp = player.getPosition().translate(0, -1);
            case "s" -> temp = player.getPosition().translate(0, 1);
            case "q" -> {}
            default -> {
                UI.print("Wrong input, please try again.");
                UI.printWithoutLn("Press W/A/S/D to move : ");
                String newMove = UI.input();
                temp = getPlayerMove(newMove, player);
            }
        }
        return temp;
    }

    public void swapTiles(Tile tile1, Tile tile2) {
        if (tile1 != tile2) {
            Position p1 = tile1.getPosition();
            Position p2 = tile2.getPosition();
            tile1.setPosition(p2);
            tile2.setPosition(p1);
            board[p1.getY()][p1.getX()] = tile2; // תיקון בשורת מיקום
            board[p2.getY()][p2.getX()] = tile1; // תיקון בשורת מיקום
        }
    }

    public void removeDeadEnemies(Player player) {
    List<Enemy> deadEnemies = new ArrayList<>();
    for (Enemy e : enemies) {
        if (e.isDead()) {
            deadEnemies.add(e);
            Position pos = e.getPosition();
            if (isValid(pos)) {
                board[pos.getY()][pos.getX()] = new EmptyTile(pos);
            }
        }
    }
    for (Enemy e : deadEnemies) {
        enemies.remove(e);
        String msg = e.getName() + " died. " + player.getName() + " gained " + e.getExperienceValue().getCapacity() + " experience points.";
        UI.print("┌─" + "─".repeat(msg.length()) + "─┐");
        UI.print("│ " + msg + " │");
        UI.print("└─" + "─".repeat(msg.length()) + "─┘");
        player.gainExperience(e.getExperienceValue().getCapacity());

    }
    enemies.removeAll(deadEnemies);
}

    public void enemyTurn(Player player) {
        for (Enemy e : enemies) {
            Position oldPosition = e.getPosition();
            Position actionPos = e.action(player);

            if (isValid(actionPos)) {
            Optional<Tile> tile = get(actionPos);

            if (tile.isPresent() && tile.get() instanceof Player) {
                CombatSystem.engageCombat(e, player); // needs to call to combat system
            } else if (!isOccupied(actionPos)) {
                Tile nextTile = tile.orElseThrow(() -> new IndexOutOfBoundsException("No Such Tile"));
                swapTiles(e, nextTile);

                if (isValid(oldPosition)) {
                    board[oldPosition.getY()][oldPosition.getX()] = new EmptyTile(oldPosition);
                }
            }
        }

            if (isValid(actionPos) && !isOccupied(actionPos)) {
                Tile tempTile = get(actionPos).orElseThrow(() -> new IndexOutOfBoundsException("No Such Tile"));
                e.interact(tempTile);
                swapTiles(e, tempTile);
            }
        }
    }


    public boolean levelEnd() {
        return enemies.isEmpty();
    }

    public void visit(Control control) {
        this.control = control;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔").append("═".repeat(board[0].length + 2) ).append("╗"); //╚║╝╔╟╗═
        for (int i = 0; i < height; i++) {
            if (i == 0) {
                sb.append("\n");
            }
            sb.append("║ ");
            for (int j = 0; j < width; j++) {
                if (board[i][j] != null) {
                    sb.append(board[i][j].toString());
                } else {
                    sb.append(' ');
                }
            }
            sb.append(" ║").append('\n');
        }
        sb.append("╚").append("═".repeat(board[0].length + 2)).append("╝");
        return sb.toString();
    }

    public String toString1() {
        StringBuilder sb = new StringBuilder();
        sb.append("║").append("---------╚").append("═".repeat(board[0].length -8) ).append("╗"); //╚║╝╔╟╗═
        for (int i = 0; i < height; i++) {
            if (i == 0) {
                sb.append("\n");
            }
            sb.append("║ ");
            for (int j = 0; j < width; j++) {
                if (board[i][j] != null) {
                    sb.append(board[i][j].toString());
                } else {
                    sb.append(' ');
                }
            }
            sb.append(" ║").append('\n');
        }
        sb.append("╚").append("═".repeat(board[0].length + 2)).append("╝");
        return sb.toString();
    }


    public void initializeBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new EmptyTile(new Position(j, i));
            }
        }
    }
}
