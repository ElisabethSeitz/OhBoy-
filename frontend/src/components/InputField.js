import React from 'react';
import styled, { css } from 'styled-components/macro';

export default function InputField({ children, ...rest }) {
  return (
    <InputFieldStyled>
      <LabelStyled {...rest}>{children}</LabelStyled>
      <InputStyled {...rest} />
    </InputFieldStyled>
  );
}

const InputFieldStyled = styled.div`
  display: grid;
  grid-template-rows: min-content min-content;
  padding: var(--size-m) var(--size-xxl);
  justify-items: center;
`;

const LabelStyled = styled.label`
  grid-row: 1;
  color: var(--orange-main);
  font-size: var(--size-m);

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      color: var(--green-main);
    `}

  ${(props) =>
    props.itemType === 'task' &&
    css`
      color: var(--blue-main);
    `}
`;

const InputStyled = styled.input`
  grid-row: 2;
  border: var(--orange-border);
  border-radius: var(--size-s);
  height: var(--size-xxl);
  width: 150px;
  background-color: rgba(255, 255, 255, 0.6);
  padding: var(--size-s);
  text-align: center;
  ::placeholder {
    color: var(--grey-font);
  }

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      border: var(--green-border);
      width: 80px;
    `}

  ${(props) =>
    props.itemType === 'task' &&
    css`
      border: var(--blue-border);
      width: 80px;
    `}
`;
