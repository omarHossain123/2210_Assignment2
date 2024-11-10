import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * This class implements the user interface for the dictionary program.
 * It reads data from an input file and allows user interaction through commands.
 * Each record has a label, type, and data, which is stored in an ordered dictionary.
 */
public class Interface {
    
    public static void main(String[] args) {
        // Check if an input file name is provided as an argument
        if (args.length < 1) {
            System.out.println("Please provide the input file name.");
            return;
        }

        // Create an ordered dictionary (Binary Search Tree) to store records
        BSTDictionary dictionary = new BSTDictionary();

        // Read records from the input file and populate the dictionary
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String label;
            while ((label = reader.readLine()) != null) { // Read each label line
                String typeDataLine = reader.readLine(); // Read the following data line
                Record record = parseRecord(label, typeDataLine); // Parse into a Record
                try {
                    dictionary.put(record); // Store record in dictionary
                } catch (DictionaryException e) {
                    System.out.println("Duplicate record: " + e.getMessage()); // Handle duplicate keys
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }

        // User interaction loop to process commands
        StringReader keyboard = new StringReader();
        while (true) {
            String command = keyboard.read("Enter next command: ");
            if (command.equalsIgnoreCase("exit")) {
                break; // Exit the program
            }
            processCommand(command, dictionary); // Handle command input
        }
    }

    
    // Parses a label and a type-data line from the file to create a Record object.
    private static Record parseRecord(String label, String typeDataLine) {
        label = label.toLowerCase(); // Convert label to lowercase for case insensitivity
        Key key;
        String data;
        int type;

        char prefix = typeDataLine.charAt(0); // First character to determine type

        // Determine the type and data based on prefix or suffix
        switch (prefix) {
            case '-':
                type = 3; // Sound file
                data = typeDataLine.substring(1);
                break;
            case '+':
                type = 4; // Music file
                data = typeDataLine.substring(1);
                break;
            case '*':
                type = 5; // Voice file
                data = typeDataLine.substring(1);
                break;
            case '/':
                type = 2; // Translation
                data = typeDataLine.substring(1);
                break;
            default:
                data = typeDataLine;
                // Infer type based on file extension or general data format
                if (typeDataLine.endsWith(".wav") || typeDataLine.endsWith(".mid")) {
                    type = 3; // Sound file
                } else if (typeDataLine.endsWith(".jpg")) {
                    type = 6; // Image file
                } else if (typeDataLine.endsWith(".gif")) {
                    type = 7; // Animated image file
                } else if (typeDataLine.endsWith(".html")) {
                    type = 8; // Webpage URL
                } else {
                    type = 1; // General definition
                }
                break;
        }

        key = new Key(label, type);
        return new Record(key, data); // Create a Record object
    }

    // Processes a command entered by the user and performs the requested action.
    private static void processCommand(String command, BSTDictionary dictionary) {
        StringTokenizer st = new StringTokenizer(command);
        String action = st.nextToken();
        String word = st.hasMoreTokens() ? st.nextToken() : null;

        switch (action.toLowerCase()) {
            case "define":
                printData(dictionary, word, 1, "The word " + word + " is not in the ordered dictionary");
                break;
            case "translate":
                printData(dictionary, word, 2, "There is no definition for the word " + word);
                break;
            case "sound":
                playMedia(dictionary, word, 3, "There is no sound file for " + word);
                break;
            case "play":
                playMedia(dictionary, word, 4, "There is no music file for " + word);
                break;
            case "say":
                playMedia(dictionary, word, 5, "There is no voice file for " + word);
                break;
            case "show":
                displayImage(dictionary, word, 6, "There is no image file for " + word);
                break;
            case "animate":
                displayImage(dictionary, word, 7, "There is no animated image file for " + word);
                break;
            case "browse":
                browsePage(dictionary, word, 8, "There is no webpage called " + word);
                break;
            case "delete":
                if (st.hasMoreTokens()) {
                    int type = Integer.parseInt(st.nextToken());
                    deleteRecord(dictionary, word, type);
                } else {
                    System.out.println("Invalid command format.");
                }
                break;
            case "add":
                if (st.hasMoreTokens()) {
                    int type = Integer.parseInt(st.nextToken());
                    String data = st.nextToken("").trim();
                    addRecord(dictionary, word, type, data);
                } else {
                    System.out.println("Invalid command format.");
                }
                break;
            case "first":
                printFirstOrLast(dictionary, true);
                break;
            case "last":
                printFirstOrLast(dictionary, false);
                break;
            default:
                System.out.println("Invalid command.");
        }
    }

    // Prints the data of a record from the dictionary, or a message if not found.
    private static void printData(BSTDictionary dictionary, String label, int type, String notFoundMessage) {
        Record record = dictionary.get(new Key(label, type));
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            System.out.println(notFoundMessage);
        }
    }

    // Plays media associated with the record, such as a sound or music file.
    private static void playMedia(BSTDictionary dictionary, String label, int type, String notFoundMessage) {
        Record record = dictionary.get(new Key(label, type));
        if (record != null) {
            try {
                new SoundPlayer().play(record.getDataItem());
            } catch (MultimediaException e) {
                System.out.println("Error playing media: " + e.getMessage());
            }
        } else {
            System.out.println(notFoundMessage);
        }
    }

    // Displays an image or animation associated with the record.
    private static void displayImage(BSTDictionary dictionary, String label, int type, String notFoundMessage) {
        Record record = dictionary.get(new Key(label, type)); // Retrieve the record
        if (record != null) {
            try {
                new PictureViewer().show(record.getDataItem()); // Attempt to display the image
            } catch (MultimediaException e) {
                System.out.println("Error displaying image: " + e.getMessage());
            }
        } else {
            System.out.println(notFoundMessage);
        }
    }

    // Opens a webpage associated with the record.
    private static void browsePage(BSTDictionary dictionary, String label, int type, String notFoundMessage) {
        Record record = dictionary.get(new Key(label, type)); // Retrieve the record

        if (record != null) {
            try {
                new ShowHTML().show(record.getDataItem()); // Attempt to open the webpage
            } catch (Exception e) {
                System.out.println("Error opening webpage: " + e.getMessage());
            }
        } else {
            System.out.println(notFoundMessage);
        }
    }

    // Deletes a record from the dictionary.
    private static void deleteRecord(BSTDictionary dictionary, String label, int type) {
        try {
            dictionary.remove(new Key(label, type)); // Remove the record from the dictionary
        } catch (DictionaryException e) {
            System.out.println("No record in the ordered dictionary has key (" + label + "," + type + ")");
        }
    }

    // Adds a record to the dictionary.
    private static void addRecord(BSTDictionary dictionary, String label, int type, String data) {
        try {
            dictionary.put(new Record(new Key(label, type), data)); // Add the record to the dictionary
        } catch (DictionaryException e) {
            System.out.println("A record with the given key (" + label + "," + type + ") is already in the ordered dictionary");
        }
    }

    
    // Prints the first (smallest) or last (largest) record in the dictionary.
    private static void printFirstOrLast(BSTDictionary dictionary, boolean isFirst) {
        Record record;
        
        // Find the first (smallest) record if isFirst is true, otherwise find the last (largest).
        if (isFirst) {
            record = dictionary.smallest();
        } else {
            record = dictionary.largest();
        }
    
        // Check if the record was found and print its data, otherwise indicate the dictionary is empty.
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            System.out.println("The ordered dictionary is empty.");
        }
    }
}    