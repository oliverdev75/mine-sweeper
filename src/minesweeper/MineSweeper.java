/**
 * Mine Sweeper
 * <p>
 * Description:
 * <p>
 * This is the game of Mine Sweeper, where you have a board with a series of hidden mines
 * distributed along it, and you have to discover all the positions where there are no
 * mines to win, guided by the count of mines around the positions you are indicating.
 * <p>
 * If you discover a mine you loose and the game will show you the rest of mines, also
 * asks you for his name to save it with all the game information
 * (board size, board mines, etc.) in the games logs.
 * <p>
 * If you want to save a game to continue it later, you can run the 'save' command, and
 * the game will ask you for the name which you give to the game, so that later you can
 * identify and select it.
 * <p>
 * Also you can leave the game without saving or logging it by running 'leave'.
 * <p>
 * Operation:
 * <p>
 * When you execute the game the first you see is the main menu, with these options:
 * <p>
 * 1.New game
 * 2.Continue a game
 * 3.Show games
 * 4.Exit
 * <p>
 * - New game:
 * <p>
 * Will show you two more menus where you will decide the board size and its quantity
 * of mines:
 * <p>
 * Size:
 * <p>
 * 1.Small (4x4)
 * 2.Medium (8x8)
 * 3.Big (12x12)
 * <p>
 * Board mines:
 * <p>
 * 1.Few (20% of mines)
 * 2.Medium (30% of mines)
 * 3.Big (40% of mines)
 * <p>
 * Later you will see the board where you will play giving to the prompt (> ) the
 * position that you want to discover giving the combination of row and column, in
 * this order, separated by a hyphen (2-4).
 * <p>
 * Here's an example (8x8):
 * <p>
 * 1  2  3  4  5  6  7  8
 * 1 ·  ·  ·  ·  ·  ·  ·  ·
 * 2 ·  ·  ·  ·  ·  ·  ·  ·
 * 3 ·  ·  ·  ·  2  ·  ·  ·
 * 4 ·  ·  ·  ·  ·  ·  ·  ·
 * 5 ·  ·  ·  ·  ·  ·  ·  ·
 * 6 ·  ·  ·  ·  ·  ·  ·  ·
 * 7 ·  ·  ·  ·  ·  ·  ·  ·
 * 8 ·  ·  ·  ·  ·  ·  ·  ·
 * <p>
 * >3-5
 * <p>
 * * The '·' are the indiscovered positions and the hideout of the mines unless you
 * have enabled the mines positions revealing, then you will see '*' as mines.
 * See cheat mode *
 * <p>
 * <p>
 * Additional game options:
 * <p>
 * - Game saving: allows you to save the game in the state that it was to
 * continue is later. You have to give a name game to identify it.
 * Run: save
 * <p>
 * - Game leaving: this option gets you out of the game without saving or logging
 * it.
 * Run: leave
 * <p>
 * - cheat mode: enables the revealing of mines positions when you see the board.
 * Run: cheat
 * <p>
 * - Normal mode: disables the cheat mode, hidding the mines positions again
 * (default mode).
 * Run: normal
 * <p>
 * <p>
 * - Continue a game:
 * <p>
 * This choice will list you all saved games so you can choose one:
 * <p>
 * 1.First game
 * 2.One game
 * <p>
 * Choose an option: 2
 * <p>
 * Then the game will load the chosen game so you can play from where you leave off.
 * The playing operation is the same as "New Game". See New Game *
 * <p>
 * - Show games:
 * <p>
 * List all the games logs showing this information: player name, board size, mines quanity,
 * win or lost, date and time of gameplay.
 * <p>
 * - Exit:
 * <p>
 * Asks you if you want to get out of the game and if the answer is 'y' or 'Y' the game will
 * stop its execution leaving you out of it. If the answer isn't any of these the game will
 * keep going.
 *
 * @author Oliver Ros
 */
package minesweeper;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MineSweeper {

    // This option is to enable or disable terminal cleaning. If your executing environment allows interface cleaning
    // then you can enable this option by changing its value to true.
    private static final boolean TERM_CLEANING = true;

    // These properties control the board output. You can change them as you want.
    private static final char TABLE_VISUAL_UNCHARTED_CELL = '.'; // Undiscovered positions
    private static final char TABLE_VISUAL_MINE = '*'; // If revealing mines is active this en every mine position will appear this
    private static final String PLAY_PROMPT = "> "; // This is the prompt used in the game


    // These are global properties that the game needs to work correctly.
    // ATTENTION: Should not change the properties name or its values unless you know the program.
    private static boolean clean = true;
    private static byte[][] board;
    private static short minesQuantity = 0;
    private static short freeCells = 0;
    private static short chosenSize = 0;
    private static short chosenMines = 0;
    private static boolean valid = false;
    private static boolean leaveGame = false;
    private static boolean quitPlay = false;
    private static boolean showMines = false;
    private static String gameName = null;
    private static String playerName = null;

    // These constants define how the control board is written.
    // ATTENTION: Should not change the property names unless you know the program.
    private static final int TABLE_UNCHARTED_CELL = 0;
    private static final int TABLE_CHARTED_CELL = 2;
    private static final int TABLE_MINE = 1;

    // These properties define files locations and names.
    // ATTENTION: If there are going to be changes in extension properties we highly recommend to use a dot before them.
    private static final String FILES_PATH = null; // The path where you want that the game files to be saved 
    private static final String GAMES_LOGS_FOLDER_NAME = "logs"; // 
    private static final String SAVED_GAMES_FOLDER_NAME = "saved_games";
    private static final String LOG_FILE_EXT = ".log";
    private static final String GAME_FILE_EXT = ".bmi";

    // This property is the prompt, is used by the game to receive data from the user.
    private static final Scanner prompt = new Scanner(System.in);

    /**
     * Contains the first code that will be executed when the game is executed.
     * In this case just contains, the main menu initializer.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        mainMenu();
    }

    /**
     * Shows the main menu of the game from which the user will begin to play.
     * Options:
     *  New game: Creates a new game without progress.
     *  Continue game: User can choose among the games that were saved in previous games, which are listed.
     *  Show games: Shows a list of all games played in the past.
     *  Exit: Leaves the game.
     */
    private static void mainMenu() {
        while (!leaveGame) {
            valid = false;
            String option = null;
            String[] options = {"New game", "Continue a game", "Show games", "Exit"};
            createMenu("Mine Sweeper", options);

            while (!valid) {
                String[] permittedValues = {"1", "2", "3", "4"};
                System.out.print("Choose an option (1-4): ");
                option = prompt.nextLine();
                valid = stringCheck(option, permittedValues);
            }

            switch (option) {
                case "1":
                    clean = false;
                    createNewGame();
                    break;
                case "2":
                    clean = false;
                    openGame();
                    break;
                case "3":
                    clean = false;
                    listGames();
                    break;
                case "4":
                    leaveGame();
            }
        }
    }

    /**
     * Cleans the terminal making space for the new content.
     */
    private static void cleanTerm() {
        ProcessBuilder clean;
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                clean = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                clean = new ProcessBuilder("clear");
            }

            clean.inheritIO().start().waitFor();
        } catch (Exception ex) {
            System.out.println("ERROR! There was an error cleaning the terminal.");
            System.out.println("Details: " + ex);
        }
    }


    /**
     * Displays the game's header
     */
    private static void gameHeader() {
        if (clean) {
            cleanTerm();
            System.out.println("Mine Sweeper\n");
        }
    }

    /**
     * Asks the user if he wants to leave the game, and if answers 'yes' he will be out of the game.
     */
    private static void leaveGame() {
        String answer = "n";
        char[] permittedValues = {'Y', 'y', 'N', 'n'};
        valid = false;
        while (!valid) {
            System.out.print("Are shure you want to leave the game? ");
            answer = prompt.nextLine();
            valid = charCheck(answer, permittedValues);
        }
        if (answer.equals("") || answer.charAt(0) == 'y' || answer.charAt(0) == 'Y') {
            leaveGame = true;
            System.out.println("\nBye, bye!");
        }
    }

    /**
     * Displays a menu with the given title and options.
     *
     * @param title the string that's shown in the banner of the menu
     * @param options the array which contains all the options that the user can enter
     */
    private static void createMenu(String title, String[] options) {
        char bannerChar = '*';
        int bannerSize = 30;
        if (TERM_CLEANING && clean) {
            cleanTerm();
        } else {
            System.out.println();
        }

        for (int symbol = 0; symbol < bannerSize; symbol++) {
            System.out.print(bannerChar);
        }

        System.out.println();
        System.out.println(bannerChar + "\t\t\t     " + bannerChar);

        System.out.println(bannerChar + "\t" + title + "\t     " + bannerChar);
        System.out.println(bannerChar + "\t\t\t     " + bannerChar);
        for (int symbol = 0; symbol < bannerSize; symbol++) {
            System.out.print(bannerChar);
        }

        System.out.println();
        for (int optionAv = 0; optionAv < options.length; optionAv++) {
            System.out.println((optionAv + 1) + "." + options[optionAv] + "\n");
        }
    }

    /**
     * Checks if a string is not empty and is equal to any of a permitted strings, if both aren't true
     * tells the user that the value is invalid.
     *
     * @param value is the string that will be checked
     * @param permitted is the group of permitted values
     * @return valid if the value is valid it will be true, otherwise will be false 
     */
    private static boolean stringCheck(String value, String[] permitted) {
        valid = false;
        if (!"".equals(value)) {
            for (int i = 0; i < permitted.length; i++) {
                if (permitted[i].equals(value))
                    valid = true;
            }
        }

        if (!valid) System.out.println("ERROR: This is an invalid option.");

        return valid;
    }

    /**
     * Checks if a character is equal to any of a permitted values, if isn't true tells
     * the user that the value is invalid.
     *
     * @param value is the character that will be checked
     * @param permitted is the group of permitted values
     * @return valid if the value is valid it will be true, otherwise will be false 
     */
    private static boolean charCheck(String value, char[] permitted) {
        valid = false;
        if (value.equals("")) {
            valid = true;
        } else {
            if (value.length() == 1) {
                for (int i = 0; i < permitted.length; i++) {
                    if (permitted[i] == value.charAt(0)) valid = true;
                }
            }
        }

        if (!valid) System.out.println("ERROR: This is an invalid option.");

        return valid;
    }

    /**
     * List all the games logs to show all the ended games information
     * (player name, board size, mines quantity, win or lost, date and time of the gameplay)
     */
    private static void listGames() {
        int statsSpaces = 6;
        String[] gamesList = getFiles("log");
        String[] gameProperties;
        String[] gameName;
        if (gamesList.length > 0) {
            System.out.println("\nName     Level\t\t                Won/Lost   Date/Time");
            for (int game = 0; game < gamesList.length; game++) {
                gameName = gamesList[game].split("\\.");
                gameProperties = getGameProps("log", gameName[0]);
                for (int prop = 0; prop < gameProperties.length; prop++) {
                    if (prop != 4)
                        System.out.print(gameProperties[prop]);
                    if (prop != 1) {
                        for (int space = 0; space < statsSpaces; space++) {
                            System.out.print(" ");
                        }
                    } else if (gameProperties[1].contains("12")) {
                        for (int space = 0; space < statsSpaces - 2; space++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int space = 0; space < statsSpaces; space++) {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.println();
            }
        } else {
            clean = false;
            System.out.println("There's no ended games.");
        }
    }

    /**
     * Show two menus to the user so the user choose the game board size and its mines quantity.
     */
    private static void createNewGame() {
        quitPlay = false;
        showMines = false;
        String sizeOption = null;
        String minesOption = null;

        valid = false;
        String[] options = {"Small (4x4)", "Medium (8x8)", "Big (12 x 12)"};
        createMenu("New Game", options);
        while (!valid) {
            String[] permittedValues = {"1", "2", "3"};
            System.out.print("Choose an option (1-3): ");
            sizeOption = prompt.nextLine();
            valid = stringCheck(sizeOption, permittedValues);
        }

        valid = false;
        options[0] = "Few (20% of mines)";
        options[1] = "Medium (30% of mines)";
        options[2] = "Many (40% of mines)";
        createMenu("New Game", options);
        while (!valid) {
            String[] permittedValues = {"1", "2", "3"};
            System.out.print("Choose an option (1-3): ");
            minesOption = prompt.nextLine();
            valid = stringCheck(minesOption, permittedValues);
        }

        switch (sizeOption) {
            case "1":
                chosenSize = 4;
                break;
            case "2":
                chosenSize = 8;
                break;
            case "3":
                chosenSize = 12;
        }

        switch (minesOption) {
            case "1":
                chosenMines = 20;
                break;
            case "2":
                chosenMines = 30;
                break;
            case "3":
                chosenMines = 40;
        }

        setNewBoard();
        setBoardMines();
        play();
    }

    /**
     * List all saved games and asks the user for what game he wants to continue.
     */
    private static void openGame() {
        String[] gameFiles = getFiles("game");

        if (gameFiles.length > 0) {
            String[] gameName;
            for (int file = 0; file < gameFiles.length; file++) {
                gameName = gameFiles[file].split("\\.");
                System.out.println((file + 1) + "." + gameName[0]);
            }

            String[] permittedValues = new String[gameFiles.length];
            for (int file = 0; file < gameFiles.length; file++) {
                permittedValues[file] = Integer.toString(file + 1);
            }
            String game = null;
            valid = false;
            while (!valid) {
                System.out.print("Choose a game (1-" + gameFiles.length + "): ");
                game = prompt.nextLine();
                valid = stringCheck(game, permittedValues);
            }

            gameName = gameFiles[Integer.parseInt(game) - 1].split("\\.");
            String[] gameProperties = getGameProps("game", gameName[0]);
            String[] gameBoard = getGameBoard(gameName[0], gameProperties[1]);
            chosenMines = (short) Integer.parseInt(gameProperties[2]);
            freeCells = (short) Integer.parseInt(gameProperties[3]);
            showMines = !gameProperties[4].equals("false");
            removeFile(gameName[0]);

            setBoard(gameBoard);
            play();
        } else {
            clean = false;
            System.out.println("There's no games saved.");
        }
    }

    /**
     * List all files of the directory type specified, depending on the 'type' parameter. There are only
     * two type of directories. Returns the files list that contains the directory.
     *
     * @param type specifies what type of directory is going to be listed
     * @return files is the list of files of the directory
     */
    private static String[] getFiles(String type) {
        String[] files = null;
        String folderName;
        String folderPath = "";
        if (type.equals("log")) {
            folderName = GAMES_LOGS_FOLDER_NAME;
        } else {
            folderName = SAVED_GAMES_FOLDER_NAME;
        }

        if (FILES_PATH != null) {
            folderPath = FILES_PATH;
        }

        folderPath += folderName;
        try {
            File folder = new File(folderPath);
            files = folder.list();
        } catch (Exception ex) {
            System.out.println("WARNING: There was an error obtaining the list of files.");
            System.out.println("Details: " + ex);
        }

        return files;
    }

    /**
     * Reads the file specified depending on the type of file and its filename, which both are given by parameters.
     *
     * @param type is the type of file which is to be read
     * @param fileName is the name of the file which is going to be read
     * @return fileData is the file information that was extracted from it
     */
    private static String[] getGameProps(String type, String fileName) {
        String[] fileData = null;
        Scanner fileReader;
        File file = new File(getPath(type, fileName));

        int line = 0;
        try {
            fileReader = new Scanner(file);
            fileData = new String[5];

            boolean leave = false;
            while (fileReader.hasNextLine() && !leave) {
                if (type.equals("game")) {
                    if (line == 5) leave = true;
                    else {
                        fileData[line] = fileReader.nextLine();
                    }
                } else if (type.equals("log")) {
                    fileData[line] = fileReader.nextLine();
                }
                line++;
            }
            fileReader.close();
        } catch (IOException ex) {
            System.out.println("WARNING: There was an error reading a file.");
            System.out.println("Details: " + ex);
        }

        return fileData;
    }

    /**
     * Reads the game file that has the name given by parameter to get the game board.
     *
     * @param gameName is the name of the game file to be read
     * @param boardSize is the board size that's used to declare the array size that will contain the extracted board
     * @return gameBoard is the array that will contain the board of the file
     */
    private static String[] getGameBoard(String gameName, String boardSize) {
        File gameFile = new File(getPath("game", gameName));
        Scanner gameFileReader = null;
        try {
            gameFileReader = new Scanner(gameFile);
        } catch (IOException ex) {
            System.out.println("WARNING: There was an error reading a file.");
            System.out.println("Details: " + ex);
        }

        int line, row;
        line = row = 0;
        String[] gameBoard = new String[Integer.parseInt(boardSize)];
        try {
            while (gameFileReader.hasNextLine()) {
                if (line > 4) {
                    gameBoard[row] = gameFileReader.nextLine();
                    row++;
                } else {
                    gameFileReader.nextLine();
                    line++;
                }
            }
            gameFileReader.close();
        } catch (Exception ex) {
            System.out.println("WARNING: There was an error reading a file.");
            System.out.println("Details: " + ex);
        }

        return gameBoard;
    }

    /**
     * Deletes the file which has the filename received by parameter.
     *
     * @param fileName is the name of the file that's going to be deleted
     */
    private static void removeFile(String fileName) {
        try {
            File file = new File(getPath("game", fileName));
            file.delete();
        } catch (Exception ex) {
            System.out.println("WARNING: There was an error deleting a file.");
            System.out.println("Details: " + ex);
        }
    }

    /**
     * Creates a new array with the size that contains the global property 'chosenSize'.
     */
    private static void setNewBoard() {
        board = new byte[chosenSize][chosenSize];
    }

    /**
     * Creates a new array which contains the content of the array given by parameter.
     *
     * @param gameBoard is the array which contains the board that will contain the new array
     */
    private static void setBoard(String[] gameBoard) {
        board = new byte[gameBoard.length][gameBoard.length];
        String[] boardLine;
        for (int row = 0; row < gameBoard.length; row++) {
            boardLine = gameBoard[row].split(":");
            for (int col = 0; col < gameBoard.length; col++) {
                board[row][col] = (byte) Integer.parseInt(boardLine[col]);
            }
        }
    }

    /**
     * Calculates the number of mines depending on the quantity that has the global property
     * 'chosenMines' to later be distributed along the game board.
     */
    private static void setBoardMines() {
        short row;
        short col;
        double percentage = (double) chosenMines / 100;
        int cellsQuantity = board.length * board.length;
        minesQuantity = (short) (cellsQuantity * percentage);

        for (int pos = 0; pos < minesQuantity; pos++) {
            row = (short) (Math.random() * board.length);
            col = (short) (Math.random() * board.length);

            if (board[row][col] != TABLE_MINE) {
                board[row][col] = TABLE_MINE;
            } else {
                pos--;
            }
        }
    }

    /**
     * Is the interface where the user plays the game while is watching the game board with
     * the moves that does the user.
     */
    private static void play() {
        if (freeCells == 0)
            freeCells = (short) ((board.length * board.length) - minesQuantity);
        String promptError = "";
        String option = "";
        while (!quitPlay) {
            if (TERM_CLEANING && clean) {
                cleanTerm();
            } else {
                System.out.println();
            }
            showBoard(promptError);
            promptError = "";
            valid = false;
            while (!valid) {
                System.out.print(PLAY_PROMPT);
                option = prompt.nextLine();
                if (option.equals("")) {
                    valid = false;
                } else if (option.contains("-")) {
                    valid = checkDigging(option);
                } else {
                    valid = true;
                }
            }

            if (option.contains("-"))
                dig(option);
            else {
                switch (option) {
                    case "leave":
                        quitPlay = true;
                        leaveGame = true;
                        break;
                    case "save":
                        clean = true;
                        saveGame();
                        break;
                    case "cheat":
                        clean = true;
                        showMines = true;
                        break;
                    case "normal":
                        clean = true;
                        showMines = false;
                        break;
                    case "help":
                        clean = false;
                        showManual();
                        break;
                    default:
                        clean = true;
                        promptError = "ERROR: This is an invalid option";
                        if (!TERM_CLEANING)
                            showManual();
                }
            }
        }
    }

    /**
     * Shows the game board with the moves that the user is doing. Also shows the mines quantity around
     * every discovered position and if 'cheat' option is enabled in game by the user, this method will
     * also reveal the mines positions.
     */
    private static void showBoard(String promptError) {
        System.out.println(promptError);
        for (int num = 1; num <= board.length; num++) {
            if (num != 1) {
                if (num < 10)
                    System.out.print("   " + num);
                else
                    System.out.print("  " + num);
            } else
                System.out.print("    " + num);
        }

        System.out.println();
        for (int row = 0; row < board.length; row++) {
            if (row < 9)
                System.out.print(" " + (row + 1) + "  ");
            else
                System.out.print((row + 1) + "  ");

            for (int col = 0; col < board.length; col++) {
                switch (board[row][col]) {
                    case 0:
                        System.out.print(TABLE_VISUAL_UNCHARTED_CELL + "   ");
                        break;
                    case 1:
                        if (!showMines) {
                            System.out.print(TABLE_VISUAL_UNCHARTED_CELL + "   ");
                        } else {
                            System.out.print(TABLE_VISUAL_MINE + "   ");
                        }
                        break;
                    case 2:
                        System.out.print(evolvedCell(row, col) + "   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Counts the mines around the position received, based on the coordinates of the board.
     * @param row is the row where is the position of which the method will count the mines around
     * @param col is the position, based on 'row' param, of which the method will count the mines around
     * @return mines the number of mines around the position
     */
    private static int evolvedCell(int row, int col) {
        int mines = 0;
        int rowBegin, rowEnd, colBegin, colEnd;
        rowBegin = rowEnd = row;
        colBegin = colEnd = col;

        if (row != 0) {
            rowBegin--;
        }
        if (col != 0) {
            colBegin--;
        }
        if (row != board.length - 1) {
            rowEnd++;
        }
        if (col != board.length - 1) {
            colEnd++;
        }

        for (int loopRow = rowBegin; loopRow <= rowEnd; loopRow++) {
            for (int loopCol = colBegin; loopCol <= colEnd; loopCol++) {
                if (board[loopRow][loopCol] == TABLE_MINE) {
                    mines++;
                }
            }
        }

        return mines;
    }

    /**
     * Shows the manual that helps the user how to use the play interface of the game
     */
    private static void showManual() {
        System.out.println("Mine Sweeper game manual:");
        System.out.println("    COMMAND            DESCRIPTION                                    EXAMPLE                                          NOTE");
        System.out.println("\n    ROW-COLUMN         Discovers this position in the game board.     5-8                                             Don't give a position which is out of the board");
        System.out.println("\n\n    leave              Leaves the game and return to the main Menu.");
        System.out.println("\n\n    save               Saves the game into a file named by you so you can continue this game in other moment.");
        System.out.println("\n\n    cheat               Reveals the mines positions in the game board every moment is shown");
        System.out.println("\n\n    normal             If the mines positions are being shown this option will make mines positions to not reveal anymore,");
        System.out.println("\n\n                       until 'cheat' option be reactivated.");
        System.out.println("\n\n    help               Shows this manual.");
    }

    /**
     * Checks the position which is given by param to determine if is valid, based on the board size.
     * @param value is the position to be checked
     * @return valid is the value which says if the position is valid or not
     */
    private static boolean checkDigging(String value) {
        valid = true;
        String[] position = value.split("-");
        int row, col;
        row = col = 0;
        try {
            row = Integer.parseInt(position[0]);
            col = Integer.parseInt(position[1]);
        } catch (Exception ex) {
            valid = false;
        }
        if (valid) {
            if (row < 1 || col < 1) valid = false;

            if (board.length == 4) {
                if (row > 4 || col > 4) valid = false;
            } else if (board.length == 8) {
                if (row > 8 || col > 8) valid = false;
            } else if (board.length == 12) {
                if (row > 12 || col > 12) valid = false;
            }
        }

        return valid;
    }

    /**
     * Makes the changes on the board based on the moves made by the user, and decides if he loses.
     * @param position is the position that indicated the user
     */
    private static void dig(String position) {
        String[] posDivision = position.split("-");
        int row = Integer.parseInt(posDivision[0]) - 1;
        int col = Integer.parseInt(posDivision[1]) - 1;
        switch (board[row][col]) {
            case TABLE_UNCHARTED_CELL:
                board[row][col] = TABLE_CHARTED_CELL;
                checkBoard();
                break;
            case TABLE_MINE:
                endGame(false);
                break;
            default:
                System.out.println("ERROR: This position is already cleared.");
        }
    }

    /**
     * Verifies if the user wins checking the board
     */
    private static void checkBoard() {
        int unchartedCells = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                if (board[row][col] == TABLE_CHARTED_CELL)
                    unchartedCells++;
            }
        }

        if (unchartedCells == freeCells)
            endGame(true);
    }

    /**
     * Asks the user for the name of the game to be saved.
     */
    private static void saveGame() {
        valid = false;
        while (!valid) {
            System.out.print("Please enter a game name: ");
            gameName = prompt.nextLine();
            valid = checkName("game", gameName);
        }

        saveGameFile();
    }

    /**
     * Ends the game, telling to the player if he has won, and asking for his name,
     * to save it into the log file.
     * @param win tells to the method if the user wins or loses
     */
    private static void endGame(boolean win) {
        quitPlay = true;
        showMines = true;
        if (fileExists("game", true, gameName))
            removeFile(gameName);
        gameHeader();
        if (win)
            System.out.println("Congratulations you won!!");
        else
            System.out.println("Sorry, you lost.");

        showBoard("");
        valid = false;
        while (!valid) {
            System.out.print("Please give me your name: ");
            playerName = prompt.nextLine();
            valid = checkName("log", playerName);
        }

        saveLog(win);
    }

    /**
     * Checks if a filename is valid based on the name indicated by the user,
     * and if another file with the same name exists in the system.
     * @param type specifies the type of file of which is going to be checked the name
     * @param value is the filename to be checked
     * @return valid is the value which says if the filename is valid or not
     */
    private static boolean checkName(String type, String value) {
        valid = true;
        if (value.charAt(0) < 65 || (value.charAt(0) > 90 && value.charAt(0) < 97) || value.charAt(0) > 122) {
            System.out.println("ERROR: Name has to begin with a letter.");
            valid = false;
        }
        if (type.equals("log"))
            valid = fileExists("log", false, value);
        else
            valid = fileExists("game", false, value);

        return valid;
    }

    /**
     * Obtains a series of information that's going to be saved in a log file.
     * @param win is the value that says fi the user has won or not.
     */
    private static void saveLog(boolean win) {

        LocalDateTime actualDate = LocalDateTime.now();
        int dateDay = actualDate.getDayOfMonth();
        int dateMonth = actualDate.getMonthValue();
        int dateYear = actualDate.getYear();
        int dateHour = actualDate.getHour();
        int dateMinute = actualDate.getMinute();
        String dateTime = dateDay + "/" + dateMonth + "/" + dateYear + " " + dateHour + ":" + dateMinute;

        String boardSize = "Board size: " + board.length + "x" + board.length;
        String mines = " Mines: " + chosenMines + "%";
        String level = boardSize + mines;
        String gameFinalState;
        if (win)
            gameFinalState = "Won";
        else
            gameFinalState = "Lost";

        saveLogFile(level, gameFinalState, dateTime);
    }

    /**
     * Creates and writes into the log file the game information of the game that was played.
     * @param level is the level in which was playing the user
     * @param gameFinalState contains the value which says if the user won or not
     * @param dateTime is the date and time when the user played.
     */
    private static void saveLogFile(String level, String gameFinalState, String dateTime) {
        setFolder("logs");
        try {
            File logFile = new File(getPath("log", playerName));
            PrintStream logFileWriter = new PrintStream(logFile);
            logFileWriter.println(playerName);
            logFileWriter.println(level);
            logFileWriter.println(gameFinalState);
            logFileWriter.println(dateTime);
            logFileWriter.close();
        } catch (NullPointerException ex) {
            System.out.println("WARNING: File couldn't be written.");
            System.out.println("Details: " + ex);
        } catch (Exception ex) {
            System.out.println("WARNING: There was an unknown error.");
            System.out.println("Details: " + ex);
        }
    }

    /**
     * Checks if a file exists based on the type of file and its name, both given by params.
     * Returns true or false depending on the caller of the method.
     * @param type is the type of file that's going to be checked
     * @param action specifies what type of answer is going to be returned
     * @param fileName is the name of the file to be checked
     * @return valid is value that says one or another answer depending on the 'action' param
     */
    private static boolean fileExists(String type, boolean action, String fileName) {
        valid = true;
        try {
            File file = new File(getPath(type, fileName));

            if (file.exists()) {
                if (!action)
                    valid = false;
                System.out.println("ERROR: File name is already in use.");
            }

        } catch (Exception ex) {
            System.out.println("WARNING: There was an unexpected error.");
            System.out.println("Details: " + ex);
        }

        return valid;
    }

    /**
     * Creates and writes into the file with a name that contains the global property 'gameName',
     * the game information necessary to be reloaded where the user left off.
     */
    private static void saveGameFile() {

        setFolder("games");
        try {
            File gameFile = new File(getPath("game", gameName));
            PrintStream gameFileWriter = new PrintStream(gameFile);

            gameFileWriter.println(gameName);
            gameFileWriter.println(board.length);
            gameFileWriter.println(chosenMines);
            gameFileWriter.println(freeCells);
            gameFileWriter.println(showMines);

            // Writes the game board into de file
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board.length; col++) {
                    gameFileWriter.print(board[row][col]);
                    if (col != board.length - 1)
                        gameFileWriter.print(":");
                }
                gameFileWriter.println();
            }
            gameFileWriter.close();
            System.out.println("Game saved successfully!");
        } catch (NullPointerException ex) {
            System.out.println("WARNING: File couldn't be written.");
            System.out.println("Details: " + ex);
        } catch (Exception ex) {
            System.out.println("WARNING: There was an unknown error.");
            System.out.println("Details: " + ex);
        }
    }

    /**
     * Returns the file path specified by the type and filename.
     * @param type is the type of file that's
     * @param fileName is the name of the file of which is going to return the path
     * @return filePath is the path to the file that will be obtained
     */
    private static String getPath(String type, String fileName) {
        String fileExt;
        String filePath = FILES_PATH;

        if (type.equals("log")) {
            if (FILES_PATH == null)
                filePath = GAMES_LOGS_FOLDER_NAME;
            else
                filePath += GAMES_LOGS_FOLDER_NAME;

            fileExt = LOG_FILE_EXT;
        } else {
            if (FILES_PATH == null)
                filePath = SAVED_GAMES_FOLDER_NAME;
            else
                filePath += SAVED_GAMES_FOLDER_NAME;

            fileExt = GAME_FILE_EXT;
        }


        filePath += File.separator + fileName + fileExt;

        return filePath;
    }

    /**
     * Creates the folder of the type received by param if it doesn't exist.
     * @param type is the type of the folder to be created, if it doesn't exist
     */
    private static void setFolder(String type) {
        String folderPath = "";
        String finalFolder;
        if (type.equals("logs"))
            finalFolder = GAMES_LOGS_FOLDER_NAME;
        else
            finalFolder = SAVED_GAMES_FOLDER_NAME;


        if (FILES_PATH == null)
            folderPath = finalFolder;
        else {
            for (int i = 0; i < FILES_PATH.length(); i++) {
                if (FILES_PATH.charAt(i) == '/' || FILES_PATH.charAt(i) == '\\')
                    folderPath += File.separator.charAt(0);
                else
                    folderPath += FILES_PATH.charAt(i);
            }
            folderPath += File.pathSeparator + finalFolder;
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception ex) {
                System.out.println("WARNING: Folder couldn't be created.");
                System.out.println("Deatils: " + ex);
            }
        } else if (!folder.isDirectory()) {
            System.out.println("WARNING: There's a file with the same name as folder, likewise we'll create it.");
            try {
                folder.mkdir();
            } catch (Exception ex) {
                System.out.println("WARNING: Folder couldn't be created.");
                System.out.println("Deatils: " + ex);
            }
        }
    }
}