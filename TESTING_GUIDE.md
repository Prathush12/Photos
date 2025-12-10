# Testing Guide for Photo Application

This guide will help you test all the features that were fixed based on the rubric.

## 1. Testing Thumbnails Display (15 points)

**What to test:** Photos should display as thumbnails in the album view, not just text.

**Steps:**
1. Login as "stock" (password: "stock")
2. Open the "stock" album
3. **Verify:** You should see photo thumbnails (small images) displayed in a grid layout, not just a text list
4. **Verify:** Each thumbnail shows a small preview of the actual photo
5. **Verify:** Clicking on a thumbnail should highlight it (blue border)

## 2. Testing Manual Slideshow (15 points)

**What to test:** Previous/Next buttons should navigate through photos in the photo display window.

**Steps:**
1. Login as "stock" (password: "stock")
2. Open the "stock" album
3. Click on any photo thumbnail to view it
4. **Verify:** You see a large photo display with Previous and Next buttons at the bottom
5. Click "Next" button
6. **Verify:** The photo changes to the next photo in the album (same window, no new window opens)
7. Click "Previous" button
8. **Verify:** The photo changes back to the previous photo
9. **Verify:** Navigation wraps around (going forward from last photo goes to first, and vice versa)

## 3. Testing Search by Date Range (15 points)

**What to test:** Search should find photos within a date range.

**Steps:**
1. Login as any user (or create a new user)
2. Create an album and add some photos
3. Click "Search Photos" button
4. Select "Search by Date Range" radio button
5. Pick a start date and end date that includes some of your photos
6. Click "Search"
7. **Verify:** Results show as thumbnails (not just text)
8. **Verify:** Only photos within the date range are shown
9. Click on a result thumbnail
10. **Verify:** Photo opens in display window

## 4. Testing Search by Single Tag (10 points)

**What to test:** Search should find photos matching a single tag.

**Steps:**
1. Login as any user
2. Open an album
3. Select a photo and click "Add Tag"
4. Choose a tag type (e.g., "location") and enter a value (e.g., "Paris")
5. Click "Search Photos"
6. Select "Search by Tags" radio button
7. Enter the tag type and value you just added (e.g., location = Paris)
8. Click "Search"
9. **Verify:** Results show as thumbnails
10. **Verify:** Only photos with that tag are shown
11. **Verify:** You can click thumbnails to view photos

## 5. Testing Search by Multiple Tags - AND (10 points)

**What to test:** Search should find photos matching multiple tags with AND logic.

**Steps:**
1. Login as any user
2. Add tags to photos:
   - Photo 1: location=Paris, person=Alice
   - Photo 2: location=Paris, person=Bob
   - Photo 3: location=London, person=Alice
3. Click "Search Photos"
4. Select "Search by Tags"
5. Enter:
   - Tag Type 1: location, Value 1: Paris
   - Tag Type 2: person, Value 2: Alice
6. Select "AND" radio button
7. Click "Search"
8. **Verify:** Only Photo 1 is shown (has both location=Paris AND person=Alice)
9. **Verify:** Photo 2 is NOT shown (has location=Paris but person=Bob)
10. **Verify:** Photo 3 is NOT shown (has person=Alice but location=London)

## 6. Testing Search by Multiple Tags - OR (10 points)

**What to test:** Search should find photos matching multiple tags with OR logic.

**Steps:**
1. Using the same photos from test #5
2. Click "Search Photos"
3. Select "Search by Tags"
4. Enter:
   - Tag Type 1: location, Value 1: Paris
   - Tag Type 2: person, Value 2: Alice
5. Select "OR" radio button
6. Click "Search"
7. **Verify:** Photo 1 is shown (has location=Paris)
8. **Verify:** Photo 2 is shown (has location=Paris)
9. **Verify:** Photo 3 is shown (has person=Alice)
10. **Verify:** All three photos appear because they match at least one tag

## 7. Testing Create Album from Search Results (10 points)

**What to test:** Should be able to create a new album containing search results.

**Steps:**
1. Perform any search (date or tag) that returns results
2. **Verify:** Results are displayed as thumbnails
3. Click "Create Album from Results" button
4. Enter a new album name (e.g., "Search Results")
5. Click OK
6. **Verify:** Success message appears
7. Click "Back to Albums"
8. **Verify:** New album appears in the list
9. Open the new album
10. **Verify:** All search result photos are in the album
11. **Verify:** Photos are displayed as thumbnails

## 8. Testing Serialization (7 points)

**What to test:** Data should persist between application sessions.

**Steps:**
1. Login as any user
2. Create an album
3. Add some photos
4. Add tags to photos
5. Logout
6. Close the application completely
7. Restart the application
8. Login as the same user
9. **Verify:** Your albums are still there
10. **Verify:** Your photos are still there
11. **Verify:** Your tags are still there
12. Open an album
13. **Verify:** Everything is preserved

## 9. Testing Javadoc (10 points)

**What to test:** All classes should have proper Javadoc comments.

**Steps:**
1. Check that all Java files have:
   - Class-level Javadoc with @author tag
   - Method-level Javadoc with @param and @return tags where appropriate
2. Generate Javadoc:
   ```bash
   javadoc -d docs -sourcepath src -subpackages photos
   ```
3. **Verify:** HTML documentation is generated in docs/ folder
4. Open docs/index.html in a browser
5. **Verify:** All classes are documented

## 10. Testing Scalability (5 point penalty addressed)

**What to test:** Application should handle large numbers of photos efficiently.

**Steps:**
1. Create an album
2. Add 20+ photos to the album
3. Open the album
4. **Verify:** All thumbnails load and display properly
5. **Verify:** Scrolling works smoothly
6. **Verify:** No performance issues or lag
7. **Verify:** Clicking thumbnails works correctly even with many photos

## Quick Test Checklist

- [ ] Login works (admin, stock, regular users)
- [ ] Thumbnails display in album view (not just text)
- [ ] Previous/Next buttons work in photo display (slideshow)
- [ ] Search by date range works and shows thumbnails
- [ ] Search by single tag works and shows thumbnails
- [ ] Search by multiple tags (AND) works correctly
- [ ] Search by multiple tags (OR) works correctly
- [ ] Create album from search results works
- [ ] Data persists after closing and reopening app
- [ ] Large numbers of photos display efficiently

## Common Issues to Watch For

1. **Thumbnails not showing:** Make sure photos are in the correct format (jpg, png, gif, bmp)
2. **Slideshow not working:** Make sure you're clicking Previous/Next in the photo display window, not the album view
3. **Search not finding photos:** Make sure dates/tags are set correctly on photos
4. **Serialization issues:** Make sure you logout or close properly (don't force quit)

## Test Data Setup

For comprehensive testing, set up:
- At least 5 photos with different dates
- Photos with various tags (location, person, etc.)
- Multiple albums with different photos
- Some photos in multiple albums (copy feature)

