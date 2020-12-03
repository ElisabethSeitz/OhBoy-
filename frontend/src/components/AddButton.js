import React from 'react';
import styled from 'styled-components/macro';
import { BsPlus } from 'react-icons/bs';

export default function AddButton({ children, ...rest }) {
  return (
    <AddButtonStyled {...rest}>
      <BsPlusStyled />
    </AddButtonStyled>
  );
}

const AddButtonStyled = styled.button`
  height: 45px;
  width: 45px;
  border: none;
  border-radius: 60px;
  background-color: var(--blue-main);
  position: absolute;
  bottom: 15px;
  right: 20px;
`;

const BsPlusStyled = styled(BsPlus)`
  color: white;
  height: var(--size-xl);
  width: var(--size-xl);
`;
