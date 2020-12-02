import React from 'react';
import { useHistory } from 'react-router-dom';
import ListItem from '../components/ListItem';
import { BsCheck, BsStarFill } from 'react-icons/bs';
import { IoTrophyOutline } from 'react-icons/io5';
import styled from 'styled-components/macro';

export default function Task({ task, monsterId, editStatus }) {
  const history = useHistory();

  function handleEdit() {
    const statusOpen = task.status === 'OPEN';
    if (statusOpen) {
      return history.push('/monsters/' + monsterId + '/tasks/edit/' + task.id);
    }
    return null;
  }

  function DisplayChangeStatusIcons() {
    const statusOpen = task.status === 'OPEN';
    if (statusOpen) {
      return (
        <ButtonStyled onClick={handleClick}>
          <BsCheckStyled />
        </ButtonStyled>
      );
    }
    return (
      <ButtonStyled onClick={handleClick}>
        <BsTrophyStyled />
      </ButtonStyled>
    );
  }

  return (
    <ListItem>
      <ContentStyled onClick={handleEdit}>
        <p className="taskDescription">{task.description}</p>
        <p className="taskScore">
          <BsStarFillStyled />
          {' ' + task.score}
        </p>
      </ContentStyled>
      <DisplayChangeStatusIcons />
    </ListItem>
  );

  function handleClick() {
    editStatus(task.id);
  }
}

const BsCheckStyled = styled(BsCheck)`
  justify-self: end;
  width: 30px;
  height: 30px;
  color: white;
`;

const BsTrophyStyled = styled(IoTrophyOutline)`
  justify-self: end;
  width: 30px;
  height: 30px;
  color: white;
`;

const ButtonStyled = styled.button`
  background-color: var(--blue-main);
  opacity: 0.7;
  border: none;
  border-radius: 0 var(--size-s) var(--size-s) 0;
  padding: 0;
`;

const ContentStyled = styled.div`
  display: grid;
  grid-template-rows: min-content min-content;

  .taskDescription {
    margin: 0;
    padding: var(--size-m);
  }

  .taskScore {
    margin: 0;
    padding: var(--size-xs) var(--size-m);
    border-radius: 0 0 0 var(--size-s);
    background-color: var(--blue-main);
    opacity: 0.7;
    color: white;
  }
`;

const BsStarFillStyled = styled(BsStarFill)`
  height: var(--size-m);
  color: white;
`;
