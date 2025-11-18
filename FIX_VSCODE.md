# Fix VSCode JavaFX Errors - Step by Step

Since the linter shows no actual errors, this is likely a VSCode caching/display issue. Try these steps **in order**:

## Step 1: Complete Cleanup
1. Close VSCode completely (Cmd+Q)
2. Delete these folders if they exist:
   - `.vscode/.jdt`
   - `.metadata`
   - `.settings`
   - `out/` (if it exists)

## Step 2: Reopen and Clean
1. Reopen VSCode
2. Open the project folder
3. Press `Cmd+Shift+P`
4. Run: **"Java: Clean Java Language Server Workspace"**
5. Select **"Restart and delete"** when prompted

## Step 3: Wait for Indexing
- Look at the bottom-right of VSCode
- Wait for "Java Projects" to finish loading/indexing
- This may take 1-2 minutes

## Step 4: Check Output Panel
1. Go to **View → Output** (or `Cmd+Shift+U`)
2. In the dropdown, select **"Language Support for Java"**
3. Look for any error messages about JavaFX
4. Copy any errors you see

## Step 5: Verify Java Version
1. Press `Cmd+Shift+P`
2. Type: **"Java: Configure Java Runtime"**
3. Make sure Java 21 is selected

## Step 6: Manual Test
Open a terminal in VSCode and try:
```bash
javac -cp "/Users/prathushneelagiri/Downloads/javafx-sdk-21.0.9/lib/*" src/photos/controller/LoginController.java
```

If this compiles without errors, then the code is fine and it's just a VSCode display issue.

## Alternative: Ignore the Red Underlines
If the code actually compiles and runs (which it should based on the linter), you can ignore the red underlines. They're just VSCode's editor warnings, but the code will still work when you compile and run it.

