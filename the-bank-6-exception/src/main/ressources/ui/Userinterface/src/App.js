import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);

    this.state= {accountNummer: "text", amount: "text"};

    this.handleTextChangeInMyComponent = this.handleTextChangeInMyComponent.bind(this);
    this.handleAmountChangeInMyComponent= this.handleAmountChangeInMyComponent.bind(this);
  }

  handleTextChangeInMyComponent(value){
    this.setState({accountNummer: value});
  }
  handleAmountChangeInMyComponent(value){

    this.setState({amount:value});
  }

  render() {
    fetch('http://localhost:8080/findAccount/').then(function(res){
      return res.text();
    }).then(function(body){
      console.log('response', body);
    })

    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />


        </div>
          <div>
          <h2>{this.state.accountNummer}</h2>  <h2>{this.state.amount}</h2>
          <MyComponent accountNummer={this.state.accountNummer}  callback2={this.handleAmountChangeInMyComponent} callback={this.handleTextChangeInMyComponent}></MyComponent>
          </div>
      </div>
    );
  }
}

class MyComponent extends Component {
  constructor(props){
    super(props);
    this.state= {accountNummer: this.props.accountNummer, amount:this.props.amount};
    this.handleTextChange=this.handleTextChange.bind(this);
    this.handleAmount=this.handleAmount.bind(this);
  }
  handleTextChange(){
    var newValue= this.refs.textField.value;
    this.setState({accountNummer: newValue});
    this.props.callback(newValue);
    console.log(newValue);
  }
  handleAmount(){
    var newValueamount= this.refs.amountfield.value;
    this.setState({amount: newValueamount});
    this.props.callback2(newValueamount);
    console.log(newValueamount);
  }
  render() {
    return(
      <div>
      <input type="text" placeholder={this.state.accountNummer} ref="textField" placeholder='AccountNummer'  />
      <input type="submit" onClick={this.handleTextChange} /> <br />
      <input type="text" placeholder={this.state.amount} ref="amountfield" placeholder='Amount'  /><br />
      <input type="submit" onClick={this.handleAmount} /> <br />

      </div>
    );

  }
}


export default App;
