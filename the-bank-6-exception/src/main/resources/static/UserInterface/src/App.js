import React, { Component } from 'react';
import logo from './Spirale.gif';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);

    this.state= {accountNumber: "", amount: "", balance: ""};

    this.handleTextChange=this.handleTextChange.bind(this);
    this.depositAmount=this.depositAmount.bind(this);
    this.fetchgetBalance=this.fetchgetBalance.bind(this);
    this.withdrawalAmount=this.withdrawalAmount.bind(this);
    this.fetchDeposit=this.fetchDeposit.bind(this);
    this.fetchWithDrawal=this.fetchWithDrawal.bind(this);

  }

  handleTextChange(){

    var newValue= this.refs.textField.value;


    console.log(this.state.accountNumber);
    this.fetchgetBalance(newValue,(account) => {
      console.log(account.balance);
      this.setState({accountNumber:account.accountNumber});
      this.setState({balance: account.balance});
      console.log(this.state);
    });
  }
  depositAmount(){
    var newValueamount= this.refs.amountfield.value;
    this.setState({amount: newValueamount});


    //this.state={accountNumber:newValue};
    console.log("state", this.state);
    console.log("accountnr", this.state.accountNumber)
    console.log("amount",newValueamount);

    this.fetchDeposit(this.state.accountNumber,newValueamount,(account) => {
      console.log(account.balance);
      this.setState({balance: account.balance});
    });
  }
  withdrawalAmount(){
    var newValueamount= this.refs.amountfield.value;
    this.setState({amount: newValueamount});

    this.fetchWithDrawal(this.state.accountNumber,newValueamount,(account) => {
      console.log(account.balance);
      this.setState({balance: account.balance});
    })
  }

  fetchgetBalance(newValue,callback){
    var link="http://localhost:8080/bank/findAccount/"+newValue;
    fetch(link).then(function(res){
      return res.json();
    }).then(function(body){
      console.log('response', body);
      console.log('length', body.length);
      console.log('balance', body.balance);

      callback(body);

      //console.log('id' body.id);
      //this.variabll.id=body.id;
    })
  }
  fetchDeposit(accountnr, newValueamount, callback){
    var link="http://localhost:8080/bank/deposit/"+accountnr +"/" +newValueamount;
    fetch(link).then(function(res){
      return res.json();
    }).then(function(body){
      console.log('balance', body.balance);

      callback(body);

      //console.log('id' body.id);
      //this.variabll.id=body.id;
    })
  }

  fetchWithDrawal(accountnr, newValueamount, callback){
    var link="http://localhost:8080/bank/withdraw/"+accountnr +"/" +newValueamount;
    fetch(link).then(function(res){
      return res.json();
    }).then(function(body){
      console.log('balance', body.balance);
      callback(body);

    })
  }


  render() {
    return (
      <div className="App">
      <div className="App-header">
          <p  color="white">The Bank Team NULL</p>
      <img src={logo} className="App-logo" alt="TeamNullo" />



      </div>

      <div class="row">
      <div class="col-lg-6">
      <div class="input-group">
      <span class="input-group-btn">

      <button class="btn btn-default" type="button" onClick={this.handleTextChange} >Go!</button>
      </span>
      <input type="text" class="form-control" placeholder={this.state.accountNumber} ref="textField"  />
      </div>
      </div>
      </div>

      <div class= "row">
      <ul>
      <li>Balance: {this.state.balance} </li>
      </ul>
      </div>

      <div class="row">
      <div class="col-lg-6">
      <div class="input-group">
      <span class="input-group-btn">
      <button class="btn btn-default" type="button" onClick={this.depositAmount} >Einzahlen!</button>

      </span>
      <input type="text" class="form-control" placeholder={this.state.amount} ref="amountfield" />
      <button class="btn btn-default" type="button" onClick={this.withdrawalAmount} >Abheben!</button>
      </div>
      </div>
      </div>
      </div>






    );
  }
}


export default App;
