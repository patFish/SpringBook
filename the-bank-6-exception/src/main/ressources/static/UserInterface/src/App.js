import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);

    this.state= {accountNummer: "", amount: "text"};
    this.jsondaten={id:"", accnr:""  ,balance:""};
    this.link={linkfetch:""};
    this.handleTextChange=this.handleTextChange.bind(this);
    this.handleAmount=this.handleAmount.bind(this);
    this.fetchmethod=this.fetchmethod.bind(this);

  }

  handleTextChange(){
    debugger;
    var newValue= this.refs.textField.value;
    this.state={accountNummer:newValue};
    this.link={linkfetch:"http://localhost:8080/bank/findAccount/"+this.state.accountNummer};
    console.log(this.state.accountNummer);
    this.fetchmethod();
  }
  handleAmount(){
    var newValueamount= this.refs.amountfield.value;
    this.setState({amount: newValueamount});
  }

  fetchmethod(){
    fetch(this.link.linkfetch).then(function(res){
      return res.text();
    }).then(function(body){
      debugger;
      console.log('response', body);
      console.log('length', body.length);
      console.log('balance', JSON.parse(body).balance);
      this.setJsondaten({name: JSON.parse(body).name});

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
          <div>
          <table>
            <tr>
              <td> {this.jsondaten.name}</td>
            </tr>
          </table>
          <h2>{this.state.accountNummer}</h2>  <h2>{this.state.amount}</h2>
            <div>
            <div>
            <input type="text" placeholder={this.state.accountNummer} ref="textField" placeholder='AccountNummer'  />
            <input type="submit" onClick={this.handleTextChange} /> <br />

            <input type="text" placeholder={this.state.amount} ref="amountfield" placeholder='Amount'  /><br />
            <input type="submit" onClick={this.handleAmount} /> <br />

            </div>
            </div>
          </div>
      </div>
    );
  }
}


export default App;
