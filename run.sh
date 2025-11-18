#!/bin/bash

# Run script for Photo Application
# Update JAVAFX_PATH to point to your JavaFX SDK installation

# Example: export JAVAFX_PATH=/path/to/javafx-sdk-21/lib

if [ -z "$JAVAFX_PATH" ]; then
    echo "JAVAFX_PATH not set. Please set it to your JavaFX SDK lib directory."
    echo "Example: export JAVAFX_PATH=/path/to/javafx-sdk-21/lib"
    exit 1
fi

# Run the application
java -cp "out:$JAVAFX_PATH/*" --module-path "$JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml photos.Photos

