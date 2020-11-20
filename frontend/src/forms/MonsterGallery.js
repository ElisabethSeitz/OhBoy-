import React from 'react';

export default function MonsterGallery() {
  const amount = 5;
  let images = [];
  let currentImage = 0;

  function initImages() {
    for (let i = 0; i < amount; i++) {
      images[i] = 'monster' + i + '.jpg';
    }
  }
  initImages();

  function setImage() {
    const currentlyDisplayedImage = window.document.getElementById(
      'currentImage'
    );
    currentlyDisplayedImage.src = images[currentImage];
  }

  function nextImage() {
    if (currentImage < amount - 1) {
      currentImage++;
      setImage();
    }
  }

  function previousImage() {
    if (currentImage > 0) {
      currentImage--;
      setImage();
    }
  }

  return (
    <div>
      <button type="button" onClick={previousImage}>
        prev
      </button>

      <img src="monster0.jpg" alt="monster image" id="currentImage" />

      <button type="button" id="next" onClick={nextImage}>
        next
      </button>
    </div>
  );
}
