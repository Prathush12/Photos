# Photo Application

A single-user photo application that allows storage and management of photos in one or more albums.

## Requirements

- JDK 21
- JavaFX SDK 21

## Setup

1. Place 5-10 stock photos in the `data/` directory. Supported formats: JPG, JPEG, PNG, GIF, BMP
2. The stock photos will be automatically loaded under the "stock" user in the "stock" album
3. Compile and run the application

## Usage

### Login
- Use "admin" to access the admin panel
- Use "stock" to view stock photos
- Use any other username to access user features (users must be created by admin first)

### Admin Features
- List all users
- Create new users
- Delete users (cannot delete "stock" or "admin")

### User Features
- Create, delete, and rename albums
- Add photos to albums (photos are stored as file paths, not copied)
- Remove photos from albums
- Add captions to photos
- Add tags to photos (with custom tag types)
- Delete tags from photos
- Copy photos between albums
- Move photos between albums
- View photos with details (caption, date, tags)
- Navigate through photos (previous/next)
- Search photos by date range or tags
- Create albums from search results

## Project Structure

```
Photos/
├── src/
│   └── photos/
│       ├── model/          # Data model classes
│       ├── view/           # FXML files (in resources/)
│       ├── controller/     # Controller classes
│       └── Photos.java     # Main application class
├── data/                   # Stock photos directory
└── docs/                   # Javadoc documentation
```

## Building and Running

### Compile
```bash
javac -cp "path/to/javafx-sdk-21/lib/*" -d out src/photos/**/*.java
```

### Run
```bash
java -cp "out:path/to/javafx-sdk-21/lib/*" photos.Photos
```

### Generate Javadoc
```bash
# Set JavaFX path
export JAVAFX_PATH=/path/to/javafx-sdk-21/lib

# Run the script
./generate-javadoc.sh
```

Or on Windows:
```cmd
set JAVAFX_PATH=C:\path\to\javafx-sdk-21\lib
generate-javadoc.bat
```

## Data Persistence

User data is stored in `data/users.dat` using Java serialization. Each user's albums and photos are persisted between sessions.

