#!/bin/bash

# Script to download stock photos for the Photo Application
# Downloads free stock photos from Unsplash (via their source API)

cd data

echo "Downloading stock photos..."

# Download 8 sample photos (low resolution for GitHub)
# Using Unsplash Source API which provides free stock photos

curl -L "https://source.unsplash.com/400x300/?nature" -o stock1.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?city" -o stock2.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?animals" -o stock3.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?food" -o stock4.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?travel" -o stock5.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?architecture" -o stock6.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?people" -o stock7.jpg
sleep 1
curl -L "https://source.unsplash.com/400x300/?technology" -o stock8.jpg

echo "Done! Downloaded 8 stock photos to data/ directory"
echo "Files: stock1.jpg through stock8.jpg"

