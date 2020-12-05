import React from 'react';
import styled, { css } from 'styled-components/macro';

export default function ListItem({ children, ...rest }) {
  return <ItemStyled {...rest}>{children}</ItemStyled>;
}

const ItemStyled = styled.section`
  display: grid;
  grid-template-columns: 4fr 1fr;
  box-shadow: var(--grey-shadow);
  border: var(--blue-border);
  border-radius: var(--size-s);

  ${(props) =>
    props.itemType === 'reward' &&
    css`
      border: var(--green-border);
    `}
`;
