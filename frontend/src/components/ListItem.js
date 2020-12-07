import React from 'react';
import styled, { css } from 'styled-components/macro';

export default function ListItem({ children, ...rest }) {
  return <ItemStyled {...rest}>{children}</ItemStyled>;
}

const ItemStyled = styled.section`
  display: grid;
  grid-template-columns: 4fr 1fr;
  box-shadow: var(--grey-shadow);
  border: var(--orange-border);
  border-radius: var(--size-s);
  background-color: rgba(255, 255, 255, 0.6);

  ${(props) =>
    props.itemType === 'task' &&
    css`
      border: var(--blue-border);
    `}

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      border: var(--green-border);
    `}
`;
