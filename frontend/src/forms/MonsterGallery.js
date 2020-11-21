import React from 'react';

export default function MonsterGallery({ monsterImage }) {
  const imageToDisplay = !monsterImage
    ? '/monsterImages/monster0.jpg'
    : monsterImage;

  const numberOfDisplayedImage = parseInt(imageToDisplay.slice(-5, -4));

  const amount = 5;
  let images = [];
  let currentImage = numberOfDisplayedImage;

  function initImages() {
    for (let i = 0; i < amount; i++) {
      images[i] = '/monsterImages/monster' + i + '.jpg';
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

      <img src={imageToDisplay} alt="monster" id="currentImage" />

      <button type="button" id="next" onClick={nextImage}>
        next
      </button>
    </div>
  );
}
