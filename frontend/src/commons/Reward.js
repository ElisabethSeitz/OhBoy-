import { useHistory } from 'react-router-dom';
import React from 'react';
import ListItem from '../components/ListItem';
import { BsCheck, BsStarFill } from 'react-icons/bs';
import { GiPayMoney } from 'react-icons/gi';
import styled from 'styled-components/macro';

export default function Reward({ reward, monsterId, editStatus }) {
  const history = useHistory();

  function handleEdit() {
    const statusOpen = reward.status === 'OPEN';
    if (statusOpen) {
      return history.push(
        '/monsters/' + monsterId + '/tasks/edit/' + reward.id
      );
    }
    return null;
  }

  function DisplayChangeStatusIcons() {
    const statusOpen = reward.status === 'OPEN';
    if (statusOpen) {
      return (
        <ButtonStyled onClick={handleClick}>
          <BsCheckStyled />
        </ButtonStyled>
      );
    }
    return (
      <ButtonStyled onClick={handleClick}>
        <GiPayMoneyStyled />
      </ButtonStyled>
    );
  }

  return (
    <ListItem>
      <ContentStyled onClick={handleEdit}>
        <p className="rewardDescription">{reward.description}</p>
        <p className="rewardScore">
          <BsStarFillStyled />
          {' ' + reward.score}
        </p>
      </ContentStyled>
      <DisplayChangeStatusIcons />
    </ListItem>
  );

  function handleClick() {
    editStatus(reward.id);
  }
}

const BsCheckStyled = styled(BsCheck)`
  justify-self: end;
  width: 30px;
  height: 30px;
  color: white;
`;

const GiPayMoneyStyled = styled(GiPayMoney)`
  justify-self: end;
  width: 30px;
  height: 30px;
  color: white;
`;

const ButtonStyled = styled.button`
  background-color: var(--green-main);
  opacity: 0.7;
  border: none;
  border-radius: 0 var(--size-s) var(--size-s) 0;
  margin: 2px;
`;

const ContentStyled = styled.div`
  display: grid;
  grid-template-rows: min-content min-content;
  margin: 0 0 2px 2px;

  .rewardDescription {
    margin: 0;
    padding: var(--size-m);
  }

  .rewardScore {
    margin: 0;
    padding: var(--size-xs) var(--size-m);
    border-radius: 0 0 0 var(--size-s);
    background-color: var(--green-main);
    opacity: 0.7;
    color: white;
  }
`;

const BsStarFillStyled = styled(BsStarFill)`
  height: var(--size-m);
  color: white;
`;
