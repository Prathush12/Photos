# Photo Application - Complete Testing Checklist

## Setup
1. **Start the application**
   ```bash
   cd /Users/prathushneelagiri/Desktop/Photos
   java --module-path /Users/prathushneelagiri/Downloads/javafx-sdk-21.0.9/lib --add-modules javafx.controls,javafx.fxml -cp "out:$(echo /Users/prathushneelagiri/Downloads/javafx-sdk-21.0.9/lib/*.jar | tr ' ' ':')" photos.Photos
   ```

---

## Test 1: Login System

### 1.1 Admin Login
- [ ] Enter username: **"admin"**
- [ ] Click "Login"
- [ ] Verify: Admin Panel opens
- [ ] Verify: Shows list of users (should include "stock")

### 1.2 Stock User Login
- [ ] Logout from admin
- [ ] Enter username: **"stock"**
- [ ] Click "Login"
- [ ] Verify: Albums screen opens
- [ ] Verify: "stock" album is visible (if photos exist in data/)

### 1.3 Regular User Login (after creation)
- [ ] Create a user first (see Test 2.2)
- [ ] Logout
- [ ] Enter the new username
- [ ] Click "Login"
- [ ] Verify: Albums screen opens

### 1.4 Invalid User
- [ ] Enter username: **"nonexistent"**
- [ ] Click "Login"
- [ ] Verify: Error message appears

### 1.5 Quit Application
- [ ] Click "Quit" button
- [ ] Verify: Application closes
- [ ] Verify: Data is saved (restart and verify users still exist)

---

## Test 2: Admin Subsystem

### 2.1 List Users
- [ ] Login as "admin"
- [ ] Verify: Users list displays all users
- [ ] Verify: "stock" and "admin" are in the list

### 2.2 Create User
- [ ] Enter new username in text field (e.g., "testuser")
- [ ] Click "Create User"
- [ ] Verify: Success message appears
- [ ] Verify: User appears in users list
- [ ] Verify: Empty username shows error
- [ ] Verify: Duplicate username shows error

### 2.3 Delete User
- [ ] Select a user (NOT "stock" or "admin")
- [ ] Click "Delete User"
- [ ] Verify: Success message appears
- [ ] Verify: User removed from list
- [ ] Try to delete "stock" → Should show error
- [ ] Try to delete "admin" → Should show error

### 2.4 Logout
- [ ] Click "Logout"
- [ ] Verify: Returns to login screen
- [ ] Verify: Data is saved

---

## Test 3: Album Management

### 3.1 Create Album
- [ ] Login as regular user
- [ ] Enter album name (e.g., "Vacation")
- [ ] Click "Create Album"
- [ ] Verify: Success message
- [ ] Verify: Album appears in list with "(0 photos)"
- [ ] Try duplicate name → Should show error

### 3.2 View Album Info
- [ ] Verify: Albums show:
  - Album name
  - Photo count
  - Date range (if photos exist)

### 3.3 Delete Album
- [ ] Select an album
- [ ] Click "Delete Album"
- [ ] Verify: Success message
- [ ] Verify: Album removed from list
- [ ] Verify: Try with no selection → Error message

### 3.4 Rename Album
- [ ] Select an album
- [ ] Click "Rename Album"
- [ ] Enter new name
- [ ] Verify: Success message
- [ ] Verify: Album name updated in list
- [ ] Try duplicate name → Error message

### 3.5 Open Album
- [ ] Select an album
- [ ] Click "Open Album"
- [ ] Verify: Album view screen opens
- [ ] Verify: Shows list of photos (or empty if no photos)

---

## Test 4: Photo Management

### 4.1 Add Photo
- [ ] Open an album
- [ ] Click "Add Photo"
- [ ] Select an image file (JPG, PNG, GIF, or BMP)
- [ ] Verify: Success message
- [ ] Verify: Photo appears in list
- [ ] Verify: Photo caption shows filename (if no caption set)
- [ ] Try adding same photo again → Error message

### 4.2 Remove Photo
- [ ] Select a photo
- [ ] Click "Remove Photo"
- [ ] Verify: Success message
- [ ] Verify: Photo removed from list
- [ ] Try with no selection → Error message

### 4.3 View Photo
- [ ] Select a photo
- [ ] Click "View Photo"
- [ ] Verify: Photo display window opens
- [ ] Verify: Shows image
- [ ] Verify: Shows caption
- [ ] Verify: Shows date taken
- [ ] Verify: Shows tags (or "None")
- [ ] Click "Close" → Returns to album view

### 4.4 Caption Photo
- [ ] Select a photo
- [ ] Click "Caption Photo"
- [ ] Enter caption (e.g., "Beautiful sunset")
- [ ] Verify: Success message
- [ ] Verify: Caption updated in photo list
- [ ] View photo → Verify caption displays

---

## Test 5: Tag Management

### 5.1 Add Tag (Existing Type)
- [ ] Select a photo
- [ ] Click "Add Tag"
- [ ] Select tag type: "location"
- [ ] Enter value: "New York"
- [ ] Verify: Success message
- [ ] View photo → Verify tag appears

### 5.2 Add Tag (New Type)
- [ ] Select a photo
- [ ] Click "Add Tag"
- [ ] Select "Create New Tag Type..."
- [ ] Enter type: "event"
- [ ] Enter value: "wedding"
- [ ] Verify: Success messages (tag type created, tag added)
- [ ] Verify: New tag type available for future use

### 5.3 Add Multiple Tags (Same Type)
- [ ] Select a photo
- [ ] Add tag: person="Alice"
- [ ] Add tag: person="Bob"
- [ ] Verify: Both tags exist on photo
- [ ] View photo → Verify both tags displayed

### 5.4 Delete Tag
- [ ] Select a photo with tags
- [ ] Click "Delete Tag"
- [ ] Select a tag to delete
- [ ] Verify: Success message
- [ ] View photo → Verify tag removed

### 5.5 Duplicate Tag Prevention
- [ ] Try adding same tag (type + value) twice
- [ ] Verify: Error message on second attempt

---

## Test 6: Photo Navigation

### 6.1 Previous Photo
- [ ] Open album with multiple photos
- [ ] Select a photo (not first)
- [ ] Click "Previous"
- [ ] Verify: Previous photo displayed
- [ ] Verify: Wraps to last photo if at first

### 6.2 Next Photo
- [ ] Select a photo (not last)
- [ ] Click "Next"
- [ ] Verify: Next photo displayed
- [ ] Verify: Wraps to first photo if at last

### 6.3 Empty Album
- [ ] Open empty album
- [ ] Click "Previous" or "Next"
- [ ] Verify: Error message

---

## Test 7: Copy and Move Photos

### 7.1 Copy Photo
- [ ] Create two albums: "Album1" and "Album2"
- [ ] Add photo to "Album1"
- [ ] Select the photo
- [ ] Click "Copy Photo"
- [ ] Select "Album2"
- [ ] Verify: Success message
- [ ] Open "Album2" → Verify photo exists
- [ ] Verify: Photo still in "Album1"
- [ ] Edit caption in one album → Verify changes in both

### 7.2 Move Photo
- [ ] Select a photo in "Album1"
- [ ] Click "Move Photo"
- [ ] Select "Album2"
- [ ] Verify: Success message
- [ ] Open "Album1" → Verify photo removed
- [ ] Open "Album2" → Verify photo exists

---

## Test 8: Search Functionality

### 8.1 Search by Date Range
- [ ] Click "Search Photos"
- [ ] Select "Search by Date Range" radio button
- [ ] Select start date
- [ ] Select end date
- [ ] Click "Search"
- [ ] Verify: Results list shows matching photos
- [ ] Verify: Photos within date range appear

### 8.2 Search by Single Tag
- [ ] Click "Search Photos"
- [ ] Select "Search by Tags" radio button
- [ ] Enter tag type: "location"
- [ ] Enter value: "New York"
- [ ] Click "Search"
- [ ] Verify: Results show photos with that tag

### 8.3 Search by Two Tags (AND)
- [ ] Enter tag type 1: "person", value: "Alice"
- [ ] Enter tag type 2: "location", value: "New York"
- [ ] Select "AND" radio button
- [ ] Click "Search"
- [ ] Verify: Only photos with BOTH tags appear

### 8.4 Search by Two Tags (OR)
- [ ] Enter tag type 1: "person", value: "Alice"
- [ ] Enter tag type 2: "person", value: "Bob"
- [ ] Select "OR" radio button
- [ ] Click "Search"
- [ ] Verify: Photos with EITHER tag appear

### 8.5 Create Album from Search Results
- [ ] Perform a search
- [ ] Verify results appear
- [ ] Click "Create Album from Results"
- [ ] Enter album name
- [ ] Verify: Success message
- [ ] Go back to albums → Verify new album exists
- [ ] Open album → Verify contains search results

---

## Test 9: Data Persistence

### 9.1 Save on Logout
- [ ] Create albums, add photos, add tags
- [ ] Click "Logout"
- [ ] Login again
- [ ] Verify: All data still exists

### 9.2 Save on Quit
- [ ] Make changes (add album, photo, etc.)
- [ ] Click "Quit" (or close window)
- [ ] Restart application
- [ ] Login
- [ ] Verify: Changes persisted

### 9.3 Stock User Persistence
- [ ] Login as "stock"
- [ ] Add photos to stock album
- [ ] Logout
- [ ] Login as "stock" again
- [ ] Verify: Photos still there

---

## Test 10: Error Handling

### 10.1 Empty Inputs
- [ ] Try creating album with empty name → Error
- [ ] Try creating user with empty name → Error
- [ ] Try adding tag with empty value → Error

### 10.2 Invalid Operations
- [ ] Try deleting photo with no selection → Error
- [ ] Try opening album with no selection → Error
- [ ] Try searching with no dates/tags → Error

### 10.3 Duplicate Prevention
- [ ] Try duplicate album name → Error
- [ ] Try duplicate username → Error
- [ ] Try duplicate photo in same album → Error
- [ ] Try duplicate tag on same photo → Error

---

## Test 11: Edge Cases

### 11.1 Many Photos
- [ ] Add 20+ photos to an album
- [ ] Verify: All display correctly
- [ ] Verify: Navigation works
- [ ] Verify: Search works across all

### 11.2 Many Albums
- [ ] Create 10+ albums
- [ ] Verify: All display in list
- [ ] Verify: Can open any album

### 11.3 Many Tags
- [ ] Add 10+ tags to a photo
- [ ] View photo → Verify all tags display

### 11.4 Photo in Multiple Albums
- [ ] Copy photo to 3+ albums
- [ ] Edit caption → Verify changes in all albums
- [ ] Add tag → Verify tag in all albums

---

## Test 12: Stock Photos

### 12.1 Stock User Access
- [ ] Login as "stock"
- [ ] Verify: "stock" album exists
- [ ] Verify: Photos from data/ directory are loaded

### 12.2 Stock Photo Management
- [ ] Can add photos to stock album
- [ ] Can add tags to stock photos
- [ ] Can search stock photos

---

## Quick Test Sequence (Minimal)

1. Login as "admin"
2. Create user "test"
3. Logout
4. Login as "test"
5. Create album "My Album"
6. Add a photo
7. Add caption "Test Photo"
8. Add tag: location="Home"
9. View photo → Verify all info
10. Search by tag → Verify finds photo
11. Create album from search
12. Logout
13. Login again → Verify data persisted

---

## Notes

- All errors should show user-friendly messages (no console output)
- All operations should be undoable or have clear consequences
- UI should be responsive and not freeze during operations
- Photos should display with proper aspect ratio (if implemented)

