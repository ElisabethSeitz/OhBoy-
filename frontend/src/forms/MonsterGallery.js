import React, { useEffect, useState } from 'react';
import styled from 'styled-components/macro';
import { GrFormNext, GrFormPrevious } from 'react-icons/gr';

const images = [
  '/monsterImages/monster0.png',
  '/monsterImages/monster1.png',
  '/monsterImages/monster2.png',
  '/monsterImages/monster3.png',
  '/monsterImages/monster4.png',
];

export default function MonsterGallery({
  savedMonsterImage,
  handleImageChange,
  actionType,
}) {
  const savedMonsterIndex = savedMonsterImage
    ? images.indexOf(savedMonsterImage)
    : -1;

  const [imageIndex, setImageIndex] = useState(Math.max(savedMonsterIndex, 0));
  const currentImage = images[imageIndex];

  useEffect(() => {
    handleImageChange(currentImage);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currentImage]);

  function nextImage() {
    setImageIndex((imageIndex + 1) % images.length);
  }

  function previousImage() {
    setImageIndex((imageIndex > 0 ? imageIndex : images.length) - 1);
  }

  return (
    <GallerySection>
      <button className="prev" type="button" onClick={previousImage}>
        <GrFormPreviousStyled />
      </button>

      <ImageContainer>
        <img className="monsterImage" src={currentImage} alt="monster" />
      </ImageContainer>

      <button className="next" type="button" onClick={nextImage}>
        <GrFormNextStyled />
      </button>
      <DisplayAddOrEditText />
    </GallerySection>
  );

  function DisplayAddOrEditText() {
    if (actionType === 'create') {
      return <p className="createText">add your monster</p>;
    }
    if (actionType === 'edit') {
      return <p className="editText">edit your monster</p>;
    }
  }
}

const GallerySection = styled.section`
  display: grid;
  grid-template-columns: min-content 1fr min-content;
  grid-template-rows: min-content min-content;
  padding: 0 var(--size-xxl);
  justify-items: center;
  align-items: center;

  .prev,
  .next {
    background-color: rgba(255, 255, 255, 0);
    border: none;
    height: min-content;
  }

  .createText,
  .editText {
    grid-column: span 3;
  }
`;

const ImageContainer = styled.div`
  height: 150px;
  width: 150px;
  border-radius: 90px;
  background-color: rgba(255, 255, 255, 0.7);
  box-shadow: var(--grey-shadow);
  margin: var(--size-xl) 0 var(--size-m) 0;

  .monsterImage {
    padding: 25px 0 0 25px;
  }
`;

const GrFormPreviousStyled = styled(GrFormPrevious)`
  font-size: var(--size-xl);
`;

const GrFormNextStyled = styled(GrFormNext)`
  font-size: var(--size-xl);
`;
