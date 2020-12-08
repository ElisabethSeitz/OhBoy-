import React from 'react';
import styled from 'styled-components/macro';

export default function List({ children, ...rest }) {
  return <ListStyled {...rest}>{children}</ListStyled>;
}

const ListStyled = styled.ul`
  overflow: scroll;
  margin: 0;
  padding: 0 var(--size-xxl);

  list-style: none;

  display: grid;
  grid-auto-rows: min-content;
  gap: var(--size-l);

  li:last-child:after {
    content: '';
    display: block;
    height: 45px;
  }
`;
