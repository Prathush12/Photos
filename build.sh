#!/bin/bash

# Build script for Photo Application
# Update JAVAFX_PATH to point to your JavaFX SDK installation

# Example: export JAVAFX_PATH=/path/to/javafx-sdk-21/lib

if [ -z "$JAVAFX_PATH" ]; then
    echo "JAVAFX_PATH not set. Please set it to your JavaFX SDK lib directory."
    echo "Example: export JAVAFX_PATH=/path/to/javafx-sdk-21/lib"
    exit 1
fi

# Create output directory
mkdir -p out

# Compile Java files
echo "Compiling Java files..."
javac -cp "$JAVAFX_PATH/*" -d out -sourcepath src src/photos/**/*.java

# Copy resources to output directory
echo "Copying resources..."
mkdir -p out/photos/resources
cp -r src/photos/resources/* out/photos/resources/

echo "Build complete! Output in out/ directory"

