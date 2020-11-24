import React, { useEffect, useState } from 'react';

export default function MonsterGallery({
  savedMonsterImage,
  imageChangedHandler,
}) {
  const images = [
    '/monsterImages/monster0.jpg',
    '/monsterImages/monster1.jpg',
    '/monsterImages/monster2.jpg',
    '/monsterImages/monster3.jpg',
    '/monsterImages/monster4.jpg',
  ];

  const savedMonsterIndex = savedMonsterImage
    ? images.indexOf(savedMonsterImage)
    : -1;

  const [imageIndex, setImageIndex] = useState(Math.max(savedMonsterIndex, 0));
  const currentImage = images[imageIndex];

  useEffect(() => {
    imageChangedHandler(currentImage);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currentImage]);

  function nextImage() {
    setImageIndex((imageIndex + 1) % images.length);
  }

  function previousImage() {
    setImageIndex((imageIndex > 0 ? imageIndex : images.length) - 1);
  }

  return (
    <section>
      <button type="button" onClick={previousImage}>
        prev
      </button>

      <img src={currentImage} alt="monster" />

      <button type="button" onClick={nextImage}>
        next
      </button>
    </section>
  );
}
