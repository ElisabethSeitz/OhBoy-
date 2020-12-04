import { useHistory } from 'react-router-dom';
import React from 'react';
import ListItem from '../components/ListItem';
import { BsCheck, BsStarFill } from 'react-icons/bs';
import styled from 'styled-components/macro';
import ToggleButton from '../components/ToggleButton';

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

  return (
    <ListItem>
      <ContentStyled onClick={handleEdit}>
        <p className="rewardDescription">{reward.description}</p>
        <p className="rewardScore">
          <BsStarFillStyled />
          {' ' + reward.score}
        </p>
      </ContentStyled>
      <ToggleButton status={reward.status} onClick={handleClick} />
    </ListItem>
  );

  function handleClick() {
    editStatus(reward.id);
  }
}

const ContentStyled = styled.div`
  display: grid;
  grid-template-rows: min-content min-content;
  margin: 2px 2px 2px 2px;
  background-color: rgba(255, 255, 255, 0.6);

  .rewardDescription {
    margin: 0;
    padding: var(--size-m);
    border-radius: var(--size-s) 0 0 0;
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
