import React from 'react';
import styled from 'styled-components/macro';
import { BsPlus } from 'react-icons/bs';
import { useHistory } from 'react-router-dom';

export default function AddButton({ monster, task, currentMonsterId, add }) {
  const history = useHistory();
  return <DisplayAdd />;

  function DisplayAdd() {
    if (add) {
      return (
        <AddButtonStyled onClick={createAddLink(currentMonsterId)}>
          <BsPlusStyled />
        </AddButtonStyled>
      );
    }
    return null;
  }

  function createAddLink(monsterId) {
    if (monster) {
      return () => history.push('/monsters/create');
    }
    if (task) {
      return () => history.push('/monsters/' + monsterId + '/tasks/create');
    } else
      return () => history.push('/monsters/' + monsterId + '/rewards/create');
  }
}

const AddButtonStyled = styled.button`
  height: 50px;
  width: 50px;
  border: none;
  border-radius: 60px;
  background-color: var(--blue-main);
  position: absolute;
  bottom: 20px;
  right: 20px;
  box-shadow: var(--grey-shadow);
`;

const BsPlusStyled = styled(BsPlus)`
  color: white;
  height: var(--size-xl);
  width: var(--size-xl);
`;
