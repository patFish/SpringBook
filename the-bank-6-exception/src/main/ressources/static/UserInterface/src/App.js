import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);

    this.state= {accountNummer: "", amount: "text", balance: ""};
    this.jsondaten={id:"", accnr:""  ,balance:""};
    this.link={linkfetch:""};
    this.handleTextChange=this.handleTextChange.bind(this);
    this.handleAmount=this.handleAmount.bind(this);
    this.fetchmethod=this.fetchmethod.bind(this);

  }

  handleTextChange(){

    var newValue= this.refs.textField.value;
    this.state={accountNummer:newValue};
    this.link={linkfetch:"http://localhost:8080/bank/findAccount/"+this.state.accountNummer};
    console.log(this.state.accountNummer);
    this.fetchmethod((account) => {
      console.log(account.balance);
      this.setState({balance: account.balance});
    });
  }
  handleAmount(){
    var newValueamount= this.refs.amountfield.value;
    this.setState({amount: newValueamount});
  }

  fetchmethod(callback){
    fetch(this.link.linkfetch).then(function(res){
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

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />


        </div>

            <div class="row">
              <div class="col-lg-6">
                <div class="input-group">
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onClick={this.handleTextChange} >Go!</button>
                  </span>
                  <input type="text" class="form-control" placeholder={this.state.accountNummer} ref="textField" placeholder='AccountNummer' />
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
                    <button class="btn btn-default" type="button" onClick={this.handleAmount} >Buche!</button>
                  </span>
                  <input type="text" class="form-control" placeholder={this.state.amount} ref="amountfield" placeholder='Amount' />
                  </div>
                </div>
            </div>
        </div>






    );
  }
}


export default App;
