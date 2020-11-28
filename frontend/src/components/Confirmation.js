import React from 'react';

class Confirmation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      label: props.label,
      question: props.question,
      confirmed: false,
    };
  }
  render() {
    return (
      <div>
        {(this.state.confirmed !== true && (
          <button
            type="button"
            onClick={() => {
              this.setState({ confirmed: true });
            }}
          >
            {this.state.label}
          </button>
        )) || (
          <div>
            <span>{this.state.question}</span>
            <button
              type="button"
              onClick={(event) => {
                this.props.onClick(event);
              }}
            >
              Yes
            </button>
            <button
              type="button"
              onClick={() => {
                this.setState({ confirmed: false });
              }}
            >
              No
            </button>
          </div>
        )}
      </div>
    );
  }
}

export default Confirmation;
