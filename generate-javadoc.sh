#!/bin/bash

# Script to generate Javadoc documentation
# Update JAVAFX_PATH to point to your JavaFX SDK installation

# Example paths (uncomment and modify as needed):
# JAVAFX_PATH="/path/to/javafx-sdk-21/lib"
# JAVAFX_PATH="/Library/Java/JavaVirtualMachines/javafx-sdk-21/lib"

# If JAVAFX_PATH is not set, try to find it automatically
if [ -z "$JAVAFX_PATH" ]; then
    echo "JAVAFX_PATH not set. Please set it to your JavaFX SDK lib directory."
    echo "Example: export JAVAFX_PATH=/path/to/javafx-sdk-21/lib"
    exit 1
fi

# Generate Javadoc
javadoc -d docs \
    -sourcepath src \
    -subpackages photos \
    -author \
    -classpath "$JAVAFX_PATH/*"

echo "Javadoc generated in docs/ directory"

