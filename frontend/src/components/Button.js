import React from 'react';
import styled, { css } from 'styled-components/macro';

export default function Button({ children, name, ...rest }) {
  return (
    <Container name={name}>
      <ButtonStyled name={name} {...rest}>
        {children}
      </ButtonStyled>
    </Container>
  );
}

const Container = styled.div`
  display: grid;
  grid-template-rows: min-content;
  justify-items: center;
  padding: var(--size-m);

  ${(props) =>
    props.name === 'save' &&
    css`
      padding-top: var(--size-xxl);
    `}
`;

const ButtonStyled = styled.button`
  color: white;
  border-radius: var(--size-s);
  width: 200px;
  height: var(--size-xxl);

  ${(props) =>
    props.name === 'save' &&
    css`
      background-color: rgba(168, 206, 0, 0.7);
      border: var(--green-border);
    `}

  ${(props) =>
    props.name === 'cancel' &&
    css`
      background-color: rgba(175, 169, 169, 0.7);
      border: var(--grey-border);
    `};

  ${(props) =>
    props.name === 'delete' &&
    css`
      background-color: rgba(209, 48, 22, 0.7);
      border: var(--orange-border);
    `};
`;
