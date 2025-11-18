# Troubleshooting JavaFX Errors in VSCode

If you're still seeing red underlines under `javafx` imports, try these steps:

## Step 1: Verify Java Extension Pack
1. Open Extensions (`Cmd+Shift+X`)
2. Search for "Java Extension Pack" by Microsoft
3. Install it if not already installed
4. Make sure all extensions in the pack are enabled

## Step 2: Clean and Reload
1. Press `Cmd+Shift+P`
2. Run: "Java: Clean Java Language Server Workspace"
3. Confirm the cleanup
4. Press `Cmd+Shift+P` again
5. Run: "Developer: Reload Window"

## Step 3: Check Java Version
Make sure you're using Java 21:
- Press `Cmd+Shift+P`
- Type "Java: Configure Java Runtime"
- Verify Java 21 is selected

## Step 4: Manual Verification
Open a terminal in VSCode and run:
```bash
ls /Users/prathushneelagiri/Downloads/javafx-sdk-21.0.9/lib/javafx.fxml.jar
```

If this file doesn't exist, the JavaFX SDK might be in a different location.

## Step 5: Alternative - Use Maven/Gradle
If the above doesn't work, you could set up a Maven or Gradle project, but for this assignment, the manual classpath should work.

## Step 6: Check VSCode Output
1. Go to View → Output (`Cmd+Shift+U`)
2. Select "Language Support for Java" from the dropdown
3. Look for any error messages about JavaFX

## Still Not Working?
Try restarting VSCode completely (quit the application and reopen it).

