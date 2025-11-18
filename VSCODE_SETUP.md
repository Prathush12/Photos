# VSCode Setup for JavaFX

VSCode is showing errors because it can't find JavaFX libraries. Here's how to fix it:

## Option 1: Install JavaFX SDK and Configure Path

1. **Download JavaFX SDK 21** from: https://openjfx.io/
   - Extract it to a location like `/Users/yourusername/javafx-sdk-21`

2. **Update the `.vscode/settings.json` file** with your actual JavaFX path:
   ```json
   {
       "java.project.referencedLibraries": [
           "/Users/yourusername/javafx-sdk-21/lib/*.jar"
       ]
   }
   ```

3. **Reload VSCode** (Command Palette → "Developer: Reload Window")

## Option 2: Use Java Extension Pack Settings

1. Install the **Java Extension Pack** in VSCode if you haven't already

2. Open Command Palette (Cmd+Shift+P) and run:
   - "Java: Clean Java Language Server Workspace"
   - "Developer: Reload Window"

3. Set the JavaFX path in VSCode settings:
   - Open Settings (Cmd+,)
   - Search for "java.project.referencedLibraries"
   - Add the path to your JavaFX lib folder: `["/path/to/javafx-sdk-21/lib/*.jar"]`

## Option 3: Manual Classpath Configuration

If the above doesn't work, you can manually configure the classpath:

1. Create a file called `.vscode/settings.json` with:
```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "out",
    "java.project.referencedLibraries": [
        "/absolute/path/to/javafx-sdk-21/lib/javafx.base.jar",
        "/absolute/path/to/javafx-sdk-21/lib/javafx.controls.jar",
        "/absolute/path/to/javafx-sdk-21/lib/javafx.fxml.jar",
        "/absolute/path/to/javafx-sdk-21/lib/javafx.graphics.jar"
    ]
}
```

2. Replace `/absolute/path/to/javafx-sdk-21` with your actual JavaFX installation path

## Verify Installation

After setup, the red underlines should disappear. If they persist:
1. Restart VSCode
2. Run "Java: Clean Java Language Server Workspace" from Command Palette
3. Check that JavaFX SDK 21 is installed and the path is correct

