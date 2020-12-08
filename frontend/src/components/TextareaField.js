import React from 'react';
import styled, { css } from 'styled-components/macro';

export default function TextareaField({ children, ...rest }) {
  return (
    <TextareaFieldStyled>
      <LabelStyled {...rest}>{children}</LabelStyled>
      <TextareaStyled {...rest} />
    </TextareaFieldStyled>
  );
}

const TextareaFieldStyled = styled.div`
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

const TextareaStyled = styled.textarea`
  grid-row: 2;
  border: var(--orange-border);
  border-radius: var(--size-s);
  height: 50px;
  width: 300px;
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
    `}

  ${(props) =>
    props.itemType === 'task' &&
    css`
      border: var(--blue-border);
    `}
`;
