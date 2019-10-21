import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import React, {Component} from "react";
//import Home from './componentes/Home';
import Menu from './componentes/Menu';
import CreateAccount from './componentes/CreateAccount';
import './App.css';
import Login from './componentes/Login';
import NewQuestion from './componentes/NewQuestion';


class App extends Component {

  render() {
    require('dotenv').config();
    return (
      <BrowserRouter>




      <div>
        <Redirect from="/" to="/login"/>
        <Switch>
          <Route path="/newQuestion" component={NewQuestion}/>
          <Route path="/login" component={Login}/>
          <Route path="/menu" component={Menu}/>
          <Route path="/CreateAccount" component={CreateAccount}/>

        </Switch>
      </div>
    </BrowserRouter>);
  }
}

export default App;
