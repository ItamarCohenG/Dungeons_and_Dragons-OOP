package CommandLine;

import tiles.Tile;
import tiles.units.enemies.Enemy;
import tiles.units.player.Player;
import game.Position;
import game.GameBoard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    protected Player player;

    public Reader(Player player) {
        this.player = player;
    }

    public GameBoard read(String filename, MessageCallBack messageCallBack) throws IOException {
        List<Tile> tiles = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(new FileReader(filename));
        List<String> list = buffer.lines().collect(Collectors.toList());
        buffer.close();

        int x = 0;
        TileFactory tc = new TileFactory();

        for (String str : list) {
            for (int y = 0; y < str.length(); y++) {
                Position p = new Position(y, x);
                if (str.charAt(y) == '@') {
                    this.player.initialize(p);
                    tiles.add(player);
                } else if (str.charAt(y) == '#' || str.charAt(y) == '.') {
                    Tile tile = tc.createTile(str.charAt(y));
                    tile.initialize(p);
                    tiles.add(tile);
                } else {
                    Enemy e = tc.createEnemy(str.charAt(y));
                    e.initialize(p, UI::print,
                            () -> messageCallBack.send(e.getName() + " was defeated!"));
                    enemies.add(e);
                    tiles.add(e);
                }
            }
            x++;
        }

        GameBoard board = new GameBoard(messageCallBack, list.get(0).length(), x);
        board.initialize(tiles, enemies, player, list.get(0).length(), x);
        return board;
    }
}
